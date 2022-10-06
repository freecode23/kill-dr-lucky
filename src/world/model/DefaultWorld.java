package world.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

/**
 * World stores spaces, characters, and items inside it. It also contains CPU
 * that controls some of the players in the game. It returns string status to
 * controller.
 */

public final class DefaultWorld implements World {
  // descriptions
  private RowCol size;
  private String name;

  // spaces, players, items, character
  private Pet pet;
  private TargetCharacter target;
  private List<Space> spaces;
  private List<Item> items;
  private List<Player> players;
  private Map<Integer, Cpu> cpuControllers;

  // reset objects
  private List<Space> spacesDefaultState;

  // dfs
  private List<Integer> dfsPath;
  private boolean isPetDfs;
  private int dfsMoveToId;

  // result and image
  private Result result;
  private DefaultImageGenerator imgGen;

  // util
  private int currentTurn;
  private final RandomIntGenerator randomGen;
  private final int maxTurn;
  private final int maxPlayers;
  private boolean gameStarted;
  

  /**
   * Constructs a world of target character, spaces, items cardinality of these.
   * 
   * @param file      the the text file of description of world of info
   * @param randomGen the random number generator object that uses Random class
   * @param maxTurn   the maximum number of turns
   * @param isPetDfs  the pet move mode
   * @throws IllegalArgumentException if:
   *                                  <ul>
   *                                  <li>If file is null
   *                                  <li>Zero room or zero item
   *                                  <li>number of space or items given does not
   *                                  match actual
   *                                  </ul>
   */
  public DefaultWorld(Readable file, RandomIntGenerator randomGen, int maxTurn, boolean isPetDfs)
      throws IllegalArgumentException {

    if (file == null || randomGen == null
            || maxTurn <= -1 || maxTurn > 50) {
      throw new IllegalArgumentException(
          "File or Random number generator is null when constructing the world");
    }
    if (maxTurn <= -1 || maxTurn > 50) {
      throw new IllegalArgumentException(
              "Max turns invalid when constructing the world");
    }

    // 1. Initialize final variables
    this.maxTurn = maxTurn;
    this.maxPlayers = 10;
    this.randomGen = randomGen;
    this.isPetDfs = isPetDfs;

    // 2. set up other variables by scanning the file
    initializeWorld(file);
  }

  @Override
  public void reloadNewGame(Readable file) throws IllegalArgumentException {
    if (file == null) {
      throw new IllegalArgumentException("File cannot be null when constructing the world");
    }
    initializeWorld(file);
  }

  @Override
  public void reset() {

    this.target.restoreHealthAndPosition();
    
    // clear space
    for (Space space : spacesDefaultState) {
      space.removeAllPlayers();  
    }
    this.spaces = spacesDefaultState;
    
    
    pet.move(spaces.get(0));
   

    // items stays
    this.players = new ArrayList<Player>();
    this.cpuControllers = new HashMap<Integer, Cpu>();

    // dfs
    this.dfsPath = new ArrayList<Integer>();
    dfsMoveToId = 1;
    this.createDfsPath();

    // util
    this.currentTurn = 0;
    this.gameStarted = false;

    // image
    final int adj = 60;
    List<Space> spacesClone = Collections.unmodifiableList(this.spaces);
    imgGen = new DefaultImageGenerator(spacesClone, size.getCol(), size.getRow(), adj);
    imgGen.drawWorld();
    imgGen.drawTarget(target);
    
    // space names
    List<String> spaceNames = new ArrayList<>();
    for (Space space : spaces) {
      spaceNames.add(space.getName());
    }

    // result
    this.result = new DefaultResult(name, imgGen.getImage(), spaceNames);
  }

  @Override
  public void startTheGame() throws IllegalStateException {
    
    if (players.size() == 0) { 
      throw new IllegalStateException("Cannot start game without any players");
    }
    this.gameStarted = true;
    this.setCurrentStatusAndLimitedSpaceInfo();
    this.setCurrentPlayerInfo();
    this.setCurrentPlayerAndSpaceItemNames();


  }
  
  @Override
  public void endTheGame() {
    this.gameStarted = false;
    this.currentTurn = maxTurn; 
    this.setCurrentStatusAndLimitedSpaceInfo();
  }

  @Override
  public String getSpaceNameByCoord(Point coord) {
    if (coord == null) {
      throw new IllegalArgumentException("point coordinate cannot be null");
    }

    return this.imgGen.getSpaceAtPoint(coord).getName();

  }

  @Override
  public Result getResult() {
    return this.result;
  }

  @Override
  public String getSpaceInfo(int spaceId) throws IllegalArgumentException {
    if (spaceId <= -1) {
      throw new IllegalArgumentException("Cannot get info of a invalid space.\n");
    }
    validateSpaceId(spaceId, this.spaces.size());
    return spaces.get(spaceId).toString();
  }

  @Override
  public boolean isCurrentTurnCpu() {
    int currentPlayerId = (this.currentTurn) % players.size();
    if (this.cpuControllers.containsKey(currentPlayerId)) {
      return true;
    }
    return false;
  }

  @Override
  public boolean isGameOver() {

    if ((currentTurn > maxTurn - 1 && currentTurn != 0) || target.getHealth() == 0) {
      return true;
    }
    return false;
  }
  
  @Override
  public void validateGameTurn(boolean isCpuCall) throws IllegalStateException {

    if (players.size() == 0) {
      throw new IllegalStateException("Please add players before making any move.");

    } else if (isGameOver()) {
      throw new IllegalStateException("Game is over. Please starts a new game.");

    } else if (gameStarted == false) {
      throw new IllegalStateException("Game has not started.");

    } else if (isCpuCall && !this.isCurrentTurnCpu()) {
      throw new IllegalStateException("Current turn is not for cpu player");

    } else if (!isCpuCall && this.isCurrentTurnCpu()) {
      throw new IllegalStateException("Current turn is a cpu player");
    }
  }

  /*
   * 3. Commands group
   */
  @Override
  public void makeCpuTakeTurn() throws IllegalStateException {
    // 1. Validate state
    boolean isCpuCall = true;
    validateGameTurn(isCpuCall);

    StringBuilder resultMsg = new StringBuilder();

    // 2. get current player index and controllers
    int currPlayerId = (this.currentTurn) % players.size();
    Cpu currController = this.cpuControllers.get(currPlayerId);

    int oldPetSpaceIdx = this.pet.getCurrentSpace().getId();
    Space oldSpace = this.getCurrentPlayer().getCurrentSpace();

    // 3. get the random space to move pet to
    Space randomSpace = spaces.get(randomGen.getNextInt(0, spaces.size() - 1));

    // 4. take ranom action
    resultMsg.append(currController.takeCpuTurnCommand(this.target, this.pet, randomSpace));

    int newPetSpaceIdx = this.pet.getCurrentSpace().getId();
    Space newSpace = this.getCurrentPlayer().getCurrentSpace();

    // if CPU moved the pet, reset the nextMove index
    if (oldPetSpaceIdx != newPetSpaceIdx) {
      // resetPetDfsNextMove();
      createDfsPath();
    }

    // if the player moved
    if (!oldSpace.equals(newSpace)) {
      // draw and erase
      imgGen.redrawSpacesPlayers(oldSpace, newSpace);
    }

    moveTargetPetAndIncrementTurn();
    result.setActionResult(resultMsg.toString());

  }

  @Override
  public void makeHumanPickItem(int itemId) throws IllegalArgumentException, IllegalStateException {

    // 1. Validate argument
    if (itemId < 0 || itemId >= items.size()) {
      throw new IllegalArgumentException("Item index entered is not valid");
    }

    // 2. Validate state
    boolean isCpuCall = false;
    validateGameTurn(isCpuCall);

    // 3. get players and items to pick
    Player currPlayer = getCurrentPlayer();
    Item itemToPick = items.get(itemId);

    // 4. create status message
    StringBuilder resultMsg = new StringBuilder();
    resultMsg.append(currPlayer.getName()).append(" has ");

    // 5. Human pick item
    if (currPlayer.pickUpItem(itemToPick)) {
      moveTargetPetAndIncrementTurn();
      resultMsg.append("successfully picked ").append(itemToPick.getName()).append("\n");
    } else {
      resultMsg.append("failed to pick item.")
              .append(" Either item is not in space, or maximum item held is reached");
    }


    this.result.setActionResult(resultMsg.toString());
    return;
  }

  @Override
  public void makeHumanMovePoint(Point coord) throws 
      IllegalArgumentException, IllegalStateException {
    // 1. validate argument
    if (coord == null) {
      throw new IllegalArgumentException(
          "Coordinate cannot be null when human is moving to this point");
    }
    
    Space space = imgGen.getSpaceAtPoint(coord);
    if (space == null) {
      throw new IllegalArgumentException("There is no space at this coordinate");
    }

    this.makeHumanMove(space.getId());
  }

  @Override
  public void makeHumanLook() throws IllegalStateException {
    // 1. Validate state
    boolean isCpuCall = false;
    validateGameTurn(isCpuCall);

    Player currPlayer = getCurrentPlayer();
    
    // 2. look around text
    StringBuilder resultMsg = new StringBuilder();
    result.setLookAroundText(currPlayer.lookAround());
    moveTargetPetAndIncrementTurn();

    // 3. action result

    resultMsg.append(currPlayer.getName()).append(" has looked around");

    result.setActionResult(resultMsg.toString());

  }

  @Override
  public void makeHumanMovePet(int spaceId) throws IllegalArgumentException, IllegalStateException {
    // 1. validate argument
    validateSpaceId(spaceId, this.spaces.size());

    // 2. Validate state
    boolean isCpuCall = false;
    validateGameTurn(isCpuCall);

    // 3. get players and space to move pet to
    Player currPlayer = getCurrentPlayer();
    currPlayer.movePet(this.pet, spaces.get(spaceId));

    // 4. create a new dfs path for the pet from this index.
    // turn and move target and pet
    createDfsPath();
    moveTargetPetAndIncrementTurn();

    // 5. create status message
    StringBuilder resultMsg = new StringBuilder();
    resultMsg.append(currPlayer.getName()).append(" has moved ").append(this.pet.getName())
    .append(" to ").append(spaces.get(spaceId).getName()).toString();

    result.setActionResult(resultMsg.toString());

  }

  @Override
  public void makeHumanKill(int itemId) throws IllegalArgumentException, IllegalStateException {
    // 1. Validate argument
    if (itemId <= -2 || itemId >= items.size()) {
      throw new IllegalArgumentException("Item index entered is not valid");
    }

    // 2. Validate state
    boolean isCpuCall = false;
    validateGameTurn(isCpuCall);

    // 3. if target is not in space
    if (this.getCurrentPlayer().getCurrentSpace().getId() != target.getCurrentSpaceId()) {
      throw new IllegalStateException("Target is not in the same space");

    } 

    // 4. create message
    StringBuilder resultMsg = new StringBuilder();
    resultMsg.append(this.getCurrentPlayer().getName()).append(" has ");

    String itemName;

    // 5. get item
    if (itemId == -1) {
      itemName = "eye poking";

    } else {
      itemName = items.get(itemId).getName();
    }

    // 6. if kill attempt successful
    if (this.getCurrentPlayer().attemptToKill(target, itemId)) {
      moveTargetPetAndIncrementTurn();
      resultMsg.append("successfuly attempted to kill ").append(target.getName())
      .append(" using ").append(itemName);

      // -grab target health
      if (target.getHealth() > 0) {
        resultMsg.append(". ")
                .append(target.getName())
                .append("'s health is now ")
        .append(target.getHealth()).toString();
      } else { // target dead
        this.gameStarted = false;
        resultMsg.append(". ").append(target.getName()).append(" is dead.");
      }
      resultMsg.toString();

    } else { // 7. if kill attempt fail
      moveTargetPetAndIncrementTurn();
      resultMsg.append("failed to kill ").append(target.getName())
      .append(" since there are players around").append(".Target's health is still ")
      .append(target.getHealth()).toString();
    }

    result.setActionResult(resultMsg.toString());
  }

  @Override
  public void generateImage() throws IllegalStateException {
    imgGen.saveWorldImage();
  }

  /*
   * 4. Util command.
   */
  @Override
  public void addHumanPlayer(String name, int spaceId) throws IllegalArgumentException,
      IllegalStateException {

    // 1. Validate argument
    validateString(name);
    validateSpaceId(spaceId, this.spaces.size());


    // 2. Validate State
    // - game started
    if (this.gameStarted == true) {
      throw new IllegalStateException("Game has already started. Cannot add more players");
    }

    // - duplicate names
    StringBuilder resultMsg = new StringBuilder();
    for (Player player : players) {
      if (player.getName().equals(name)) {
        throw new IllegalArgumentException("There is a player with a duplicate name");
      }
    }
    
    // - max players
    if (players.size() == maxPlayers) {
      throw new IllegalStateException("You've reached maximum players. Start the game");
    }

    // 3. set status
    if (currentTurn == 0) {
      result.setStatus("Add up to 10 players and click start game to start playing");
    }
    
    // 4. Create players with random max number of item from 1 to 5
    int maxItem = randomGen.getNextInt(1, 5);
    Space playerSpace = spaces.get(spaceId);
    Player p1 = new DefaultPlayer(name, playerSpace, maxItem);

    // 5. Add players
    int playerId = players.size();
    this.players.add(p1); // add player to world

    
    resultMsg.append("The ").append(playerId + 1)
    .append("th player, ")
    .append(p1.getName()).append(" has been added to ")
      .append(playerSpace.getName());


    imgGen.drawPlayers(playerSpace);
    result.setActionResult(resultMsg.toString());
    return;

  }

  @Override
  public void addCpuPlayer() throws IllegalStateException {
    
    // 1. validate state
    // - game started
    if (currentTurn > 0) {
      throw new IllegalStateException("The game has started. Cannot add players");
    }
    // - max players
    if (players.size() == maxPlayers) {
      throw new IllegalStateException("You've reached maximum players. Start the game");
    }
    
    // 2. set status
    if (currentTurn == 0) {
      result.setStatus("Add up to 10 players and click start game to start playing");
    }

    // 3. Initialize random space
    Space playerSpace = spaces.get(randomGen.getNextInt(0, spaces.size() - 1));
    // instantiate player
    // will add itself to a space
    Cpu cpu = new DefaultCpu(randomGen, playerSpace);

    // 4. add to world
    this.players.add(cpu.getPlayer());

    // 5. add cpu to world and its player index number
    int playerId = players.size() - 1;
    this.cpuControllers.put(playerId, cpu);

    // 6. get success message
    StringBuilder resultMsg = new StringBuilder();
    resultMsg.append("The ").append(playerId + 1)
    .append("th player, ")
    .append(cpu.getPlayer().getName())
    .append(" has been added to ")
      .append(playerSpace.getName());
    
    imgGen.drawPlayers(playerSpace);
    result.setActionResult(resultMsg.toString());
  }

  /*
   * 6. Object Override group
   */
  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append("Name: ").append(name).append("\n").append("Size: ").append(size.getRow())
    .append(" X ").append(size.getCol()).append("\n").append("Target: ")
    .append(target.toString()).append("\n").append("Target's Position: ")
    .append(this.target.getCurrentSpaceId()).append("\n").append("Number of Rooms: ")
    .append(spaces.size()).append("\n").append("Number of Items: ").append(items.size())
    .append("\n");
    return s.toString();
  }

  @Override
  public boolean isCurrentPointHumanPlayer(Point point) throws IllegalArgumentException {
    if (point == null) {
      throw new IllegalArgumentException("point cannot be null");
    }
   

    Player playerP = null;
    try {
      playerP = imgGen.getPlayerAtPoint(point);
      // current player is human
      if (getCurrentPlayer().equals(playerP) && !this.isCurrentTurnCpu()) {
        return true;
      } else {
        return false;
      }
    } catch (IndexOutOfBoundsException ibe) {
      throw new IllegalArgumentException("Point index is out of the bounds.");
    }
  }

  @Override
  public boolean isCurrentPointHumanPlayerNeighbor(Point point) throws IllegalArgumentException {

    if (point == null) {
      throw new IllegalArgumentException("point cannot be null");
    }

    if (this.isCurrentTurnCpu()) {
      return false;
    }

    Space spaceS = null;
    try {
      spaceS = imgGen.getSpaceAtPoint(point);
      if (spaceS == null) {
        return false; // no space
      } else if (spaceS.isNeighbor(getCurrentPlayer().getCurrentSpace())) {
        return true;
      } else {
        return false;
      }
    } catch (IndexOutOfBoundsException ibe) {
      throw new IllegalArgumentException("Current point is out of bounds\n");
    } catch (NullPointerException ibe) {
      throw new IllegalArgumentException("Current player is at non invalid point.\n");
    }



  }

  @Override
  public boolean hasGameStarted() {
    return this.gameStarted;
  }

  @Override
  public void currentSpaceShouldHaveItems() {
    if (this.getCurrentPlayer().getCurrentSpace().getItemSize() < 1) {
      throw new IllegalArgumentException("There is no item in this space");
    }
    
  }

  /* ********************Helper Methods ****************************/

  /**
   * This helper method makes a human player move.
   * @param spaceId of the destination.
   * @throws IllegalArgumentException if there is problem in spaceID.
   * @throws IllegalStateException if there is problem
   *        with the move due to game rules.
   */
  private void makeHumanMove(int spaceId) throws IllegalArgumentException, IllegalStateException {
    // 1. validate argument
    validateSpaceId(spaceId, this.spaces.size());

    // 2. Validate state
    boolean isCpuCall = false;
    validateGameTurn(isCpuCall);

    // 3. get players and space to move
    Player currPlayer = getCurrentPlayer();
    Space oldSpace = getCurrentPlayer().getCurrentSpace();
    Space newSpace = spaces.get(spaceId);

    // 4. create status message
    StringBuilder resultMsg = new StringBuilder();
    resultMsg.append(currPlayer.getName()).append(" has ");

    // 5. move to neighbor
    if (currPlayer.moveToNeighbor(newSpace)) {
      moveTargetPetAndIncrementTurn();
      resultMsg.append("successfuly moved to ").append(newSpace.getName());

      // draw and erase
      imgGen.redrawSpacesPlayers(oldSpace, newSpace);
    } else {
      resultMsg.append("failed to move because space is not neighbor");
    }

    // set game status and action result
    result.setActionResult(resultMsg.toString());

  }

  /**
   * This helper method capitalizes the names.
   * @param name to be changed.
   * @return captilized string.
   * @throws IllegalArgumentException if there is problem with the name string.
   */
  private String capitalizeName(String name) throws IllegalArgumentException {
    if (name == null || "".equals(name)) {
      throw new IllegalArgumentException("name arguments cannot be empty");
    }
    StringBuffer capitalized = new StringBuffer();

    String[] fullName = name.split(" ");
    for (String word : fullName) {
      char[] trimmedWord = word.trim().toCharArray();
      trimmedWord[0] = Character.toUpperCase(trimmedWord[0]);
      word = new String(trimmedWord);
      capitalized.append(word).append(" ");

    }
    return capitalized.toString().trim();
  }

  /*
   * 5. Pet dfs
   */
  private void createDfsPath() {
    // track the spaces visited status
    boolean[] visited = new boolean[spaces.size()];

    // record the path used

    // Initialize all visited status with false
    for (int i = 0; i < spaces.size(); i++) {
      visited[i] = false;
    }

    // reset the list of path
    dfsPath.clear();
    List<ArrayList<Integer>> pathUsed = new ArrayList<ArrayList<Integer>>();


    traverse(this.pet.getCurrentSpace().getId(), -1, visited, pathUsed);
    dfsMoveToId = 1;
  }

  /*
   * util function for create dfs path
   */
  private void traverse(int currentId, int fromId, boolean[] visited,
                        List<ArrayList<Integer>> pathUsed) {

    if (currentId <= -2 || fromId <= -2) {
      throw new IllegalArgumentException("Pet position invalid.\n");
    }

    if (pathUsed == null || visited == null) {
      throw new IllegalArgumentException("DFS path or visited stack cannot be null.\n");
    }

    int visitedCount = 0;

    // Check if all the totalSpaces is visited or not
    // and count unvisited nodes
    for (int i = 0; i < spaces.size(); i++) {
      if (visited[i]) {
        visitedCount++;
      }
    }

    // Case 1: base case, all space have been visited
    // If all the unvisited index is visited return;
    if (visitedCount == spaces.size()) {
      return;
    }


    // Mark not visited totalSpaces as visited
    visited[currentId] = true;

    // record this path
    pathUsed.add(new ArrayList<Integer>(Arrays.asList(fromId, currentId)));

    // add this index to the sequence of path needs to be taken
    dfsPath.add(currentId);

    // Case 2: visit unvisited neighbors


    for (int neighborIdx : spaces.get(currentId).getNeighborsIndices()) {
      if (!visited[neighborIdx]) {
        traverse(neighborIdx, currentId, visited, pathUsed);
      }
    }



    // Case 3: no more neighbors to visit, backtrack
    for (int idx = 0; idx < pathUsed.size(); idx++) {
      // if the toId is currentIdo

      if (pathUsed.get(idx).get(1) == currentId) {
        traverse(pathUsed.get(idx).get(0), currentId, visited, pathUsed);
      }
    }
  }

  /**
   * Set the result's player description of the current player.
   */
  private void setCurrentPlayerInfo() {
    result.setPlayerDesciption(this.getCurrentPlayer().toString());
  }

  /**
   * Set the current player index and current turn to the result object.
   */
  private void setCurrentStatusAndLimitedSpaceInfo() {
    StringBuilder status = new StringBuilder();

    // 1. Game is over
    if (isGameOver()) {
      status.append("Game Over! ");
      if (target.getHealth() == 0) {

        // get winner
        int previousPlayerId = ((this.currentTurn - 1) % players.size());
        status.append(players.get(previousPlayerId).getName()).append(" wins\n");

      } else { // target is not dead. No one wins
        status.append("Maximum turn reached. ").append(target.getName())
                .append(" escaped and nobody wins\n");
      }
      result.setStatus(status.toString());
      return;
    }

    // 2. Game not over: for controller display
    // - target
    status.append("Target is at ").append(spaces.get(target.getCurrentSpaceId()).getName())
            .append("   ");

    // - whose turn
    status.append(this.getCurrentPlayer().getName()).append("'s turn").append("   ");

    // - turn number add 1 for user to see
    status.append("Turn:").append(currentTurn + 1).append("/").append(this.maxTurn);

    result.setStatus(status.toString());

    // 3. set the space info at the end of every turn
    if (!this.isCurrentTurnCpu()) {
      result.setEveryTurnSpaceInfo(
              this.getCurrentPlayer().getCurrentSpace().toStringIsLookAround(false));
    } else {
      result.setEveryTurnSpaceInfo("No space to be shown at this turn");
    }

  }

  /**
   * Check if a given name (world, space, or item) is alphanumeric only or
   * contains apostrophe.
   *
   * @param name the name to be checked if it's valid
   */
  private boolean isAlphaNumeric(String name) throws IllegalArgumentException {
    // 1. Check for alphanumeric character's name
    if (name == null || !(name.matches("^[a-zA-Z0-9' ]*$"))) {
      throw new IllegalArgumentException(
              new StringBuilder("Target's name should contain")
                      .append("alphanumeric characters and apostrophe only.")
                      .toString());
    }
    return true;
  }

  /**
   * Helper method to return current Player.
   * @return current Player.
   */
  private Player getCurrentPlayer() {
    if (players.size() == 0) {
      return null;
    }

    int currentPlayerId = (this.currentTurn) % players.size();
    return players.get(currentPlayerId);
  }

  /**
   * Helper methods to set the current player and space Items.
   */
  private void setCurrentPlayerAndSpaceItemNames() {
    // update current player item names
    // - get the list of items that the player has
    String itemNames = getCurrentPlayer().getItemsAsString();
    List<String> itemNamesList = new ArrayList<>(Arrays.asList(itemNames.split(",")));
    result.setCurentPlayerItemNames(itemNamesList);


    // update names of items in current space
    String spaceItemNames = getCurrentPlayer().getCurrentSpace().getItemsAsString();
    List<String> spaceItemNamesList =
            new ArrayList<>(Arrays.asList(spaceItemNames.split(",")));
    result.setCurentPlayerSpaceItemNames(spaceItemNamesList);
  }

  // 0. Helpers for constructor
  /**
   * Add space object to the list of spaces by parsing through the specification.
   *
   * @param sc      the scanner object that reads the spec
   * @param spaceId the id of the space
   * @throws IllegalArgumentException if spacesInfo is null
   *
   */
  private final void addSpace(Scanner sc, int spaceId, int totalSpace) {
    if (sc == null) {
      throw new IllegalArgumentException("scanner cannot be null");
    }

    validateSpaceId(spaceId, totalSpace);
    RowCol upLeft;
    RowCol lowRight;

    // 1. Initialize coordinates and name
    upLeft = new RowCol(sc.nextInt(), sc.nextInt());
    lowRight = new RowCol(sc.nextInt(), sc.nextInt());
    sc.skip("\\s");
    String spaceName;
    spaceName = capitalizeName(sc.nextLine());

    // 2. Check name validity and uniqueness
    Set<Space> set = new HashSet<Space>(this.spaces);
    if (set.size() < this.spaces.size()) {
      sc.close();
      throw new IllegalArgumentException("There are duplicate space names");
    }

    // 3. Check for existing duplicate rowCol
    if (!this.spaces.isEmpty()) {
      for (Space existingSpace : spaces) {
        boolean lowLeftEqual = existingSpace.getUpLeft().equals(upLeft);
        boolean upRightEqual = existingSpace.getLowRight().equals(lowRight);

        if (lowLeftEqual && upRightEqual) {
          sc.close();
          throw new IllegalArgumentException(
                  new StringBuilder("Duplicate coordinates")
                          .append(" for when adding space to list")
                          .toString());
        }
      }
    }

    Space space = new DefaultSpace(spaceId, spaceName, upLeft, lowRight);

    // 5. Instantiate Space object and add to list
    this.spaces.add(space);
  }

  /**
   * Add item object to the list of items by parsing through the specification.
   * Also add the item to space it belongs to
   *
   * @param scanner         the scanner object that reads the spec
   * @param itemId     the id of the item
   * @param totalSpace the size of the space in world
   * @throws IllegalArgumentException if scanner is null or itemId is negative or
   *                                  totalSpace is 0 or less
   *
   */
  private final void addItem(Scanner scanner, int itemId, int totalSpace) {
    if (scanner == null || itemId < 0 || totalSpace <= 0) {
      throw new IllegalArgumentException(
              new StringBuilder("scanner cannot be null or item index cannot be 0 or ")
                      .append("total space cannot be 0 or less when adding item to the world")
                      .toString());
    }
    int spaceId;
    int damagePoint;

    // 1. Get fields for items
    spaceId = scanner.nextInt();

    if (spaceId >= totalSpace) {
      scanner.close();
      throw new IllegalArgumentException(
              new StringBuilder("Room index of the item should ")
                      .append("be less than the total number of space")
                      .toString());
    }
    damagePoint = scanner.nextInt();
    scanner.skip("\\s"); // skip space
    String itemName;
    itemName = capitalizeName(scanner.nextLine());

    // 2. Check name validity
    isAlphaNumeric(itemName);

    // 3. Instantiate item object and add to list
    Item itemToAdd = new DefaultItem(itemName, itemId, damagePoint);
    this.items.add(itemToAdd);

    // 4. add this item to a space
    for (Space space : spaces) {
      if (space.getId() == spaceId) {
        space.addItem(itemToAdd);
      }
    }

    for (Space space : spacesDefaultState) {
      if (space.getId() == spaceId) {
        space.addItem(itemToAdd);
      }
    }
  }

  /**
   * Util function to determine neighbor. If spaces are neighbors this method will
   * add them to each other.
   */
  private void determineNeighbors() {

    for (Space space : this.spaces) {
      for (Space other : this.spaces) {
        if (space.isNeighborByCalc(other)) {
          space.addNeighbor(other);
        }
      }
    }

    // also set neighbor for the reset state
    for (Space space : this.spacesDefaultState) {
      for (Space other : this.spacesDefaultState) {
        if (space.isNeighborByCalc(other)) {
          space.addNeighbor(other);
        }
      }
    }
  }

  /**
   * Util for validating spaceId argument.
   *
   * @param spaceId the spaceId to be validated
   * @throws IllegalArgumentException if spaceId is greater than the size of
   *                                  spaces or if it's negative
   */
  private void validateSpaceId(int spaceId, int totalSpace) throws IllegalArgumentException {

    if (spaceId < 0 || spaceId >= totalSpace) {
      throw new IllegalArgumentException(
              new StringBuilder("Space index entered cannot be ")
                      .append("negative or greater than total space size")
                      .toString());
    }
  }

  /**
   * Util for validating name argument.
   *
   * @param someName the string to be validated
   * @throws IllegalArgumentException if the string is empty
   */
  private void validateString(String someName) throws IllegalArgumentException {
    if (someName == null || "".equals(someName)) {
      throw new IllegalArgumentException("name arguments cannot be empty");
    }
  }

  /*
   * Util to move target and increment currentTurn. Also set the string of current
   * status of the game.
   */
  private void moveTargetPetAndIncrementTurn() {


    // 1. target move and update the image accordingly
    imgGen.eraseTarget(target);
    target.move();
    imgGen.drawTarget(target);

    // 2. update turn
    currentTurn += 1;
    if (currentTurn == maxTurn) {
      this.gameStarted = false;
    }

    // 3. move pet if its on dfs mode. otherwise pet remains stationary
    if (isPetDfs) {
      Space petNewSpace = spaces.get(dfsPath.get(dfsMoveToId));
      pet.move(petNewSpace);

      // increment move to index
      dfsMoveToId = (dfsMoveToId + 1) % dfsPath.size();
    }

    // 4. set result object
    // update status
    this.setCurrentStatusAndLimitedSpaceInfo();
    this.setCurrentPlayerInfo();

    // - update current player item names
    this.setCurrentPlayerAndSpaceItemNames();
  }

  /**
   * Helper method to read the file.
   * @param file the wold specification for the world
   */
  private void initializeWorld(Readable file) {
    if (file == null) {
      throw new IllegalArgumentException("File cannot be null.\n");
    }
    this.currentTurn = 0;
    this.gameStarted = false;
    this.players = new ArrayList<Player>();
    this.cpuControllers = new HashMap<Integer, Cpu>();
    this.dfsPath = new ArrayList<Integer>();
    this.dfsMoveToId = 1;

    Scanner sc;
    sc = new Scanner(file);
    try {

      // 1. Get world
      int row = sc.nextInt();
      int col = sc.nextInt();
      this.size = new RowCol(row, col);
      this.name = sc.nextLine().trim();

      // 2. Get target
      int targetHealth = sc.nextInt();
      sc.skip("\\s");
      String targetName = sc.nextLine();

      // 3. Get pet
      String petName = sc.nextLine().trim();

      // 4. Get space
      int totalSpaces = sc.nextInt();
      sc.skip("\\s");

      // Check space size
      if (totalSpaces == 0) {
        sc.close();
        throw new IllegalArgumentException("There should be at least 1 space");
      }
      // add spaces to list
      this.spaces = new ArrayList<Space>(totalSpaces);
      int spaceId = 0;
      while (spaceId < totalSpaces) {
        addSpace(sc, spaceId, totalSpaces);
        spaceId++;
      }

      // create the default state to fall back to when game reset
      this.spacesDefaultState = new ArrayList<Space>();

      for (Space space : spaces) {
        Space spaceClone = new DefaultSpace(space.getId(),
                space.getName(), space.getUpLeft(), space.getLowRight());
        spacesDefaultState.add(spaceClone);
      }


      this.target = new DefaultTargetCharc(targetName, targetHealth, totalSpaces);
      this.pet = new DefaultPet(petName, spaces.get(0));


      // 5. Get item
      int totalItems = sc.nextInt();
      sc.skip("\\s");
      // check item size
      if (totalItems == 0) {
        sc.close();
        throw new IllegalArgumentException("There should be at least 1 item");
      }
      // add items to list
      this.items = new ArrayList<Item>();
      int itemId = 0;
      while (sc.hasNextLine()) {
        addItem(sc, itemId, totalSpaces);
        itemId++;
      }

      if (totalItems != itemId) {
        throw new IllegalArgumentException(
                new StringBuilder("Actual total items ")
                        .append("stated and the given number does not match")
                        .toString());
      }

      // draw wold
      final int adj = 60;
      List<Space> spacesClone = Collections.unmodifiableList(this.spaces);

      imgGen = new DefaultImageGenerator(spacesClone, size.getCol(), size.getRow(), adj);
      imgGen.drawWorld();
      imgGen.drawTarget(target);

      // 6.
      determineNeighbors();
      createDfsPath(); // start dfs from index 0
      sc.close();
      List<String> spaceNames = new ArrayList<>();
      for (Space space : spaces) {
        spaceNames.add(space.getName());
      }
      // initialize result
      this.result = new DefaultResult(name, imgGen.getImage(), spaceNames);

    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("Please check your world specification format");
    }
  }
}