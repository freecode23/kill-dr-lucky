package world.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Implementation of the space class in the world.
 */
public final class DefaultSpace implements Space {
  private final List<Space> neighbors;
  private final String name;
  private final RowCol upLeft;
  private final RowCol lowRight;
  private final int spaceId;
  private List<Item> items;
  private List<Player> players;
  private Pet pet;

  /**
   * Constructs a rectangular space inside the world that may contains items.
   * It also contains information about the neighbors.
   * @param spaceId the id of the 
   * @param name the name of the space.
   * @param upLeft the upper left coordinates of the space
   * @param lowRight the lower right coordinates of the space
   * @throws IllegalArgumentException if:
   *     <ul>
   *     <li> name is an empty string.
   *     <li> left row/column is greater than or equal to right row/column
   *     <li> space id is negative
   *     </ul>
   */
  public DefaultSpace(int spaceId, String name, RowCol upLeft, RowCol
      lowRight)
      throws IllegalArgumentException {
    
    // check null
    if (name == null || "".equals(name)) {
      throw new IllegalArgumentException("Space name cannot be empty");
    } else if (upLeft == null || lowRight == null) {
      throw new IllegalArgumentException("Coordinates cannot be null");
    } 
    
    // check validity
    if (spaceId < 0) {
      throw new IllegalArgumentException("Space id cannot be negative");
    } else if ((upLeft.getRow() >= lowRight.getRow()) || (upLeft.getCol() >= lowRight.getCol())) {
      throw new IllegalArgumentException(
              new StringBuilder("Upper left row or column should be strictly")
                      .append("smaller than lower right's row or column.")
                      .toString());
    }

    this.spaceId = spaceId;
    this.name = name;
    this.upLeft = upLeft;
    this.lowRight = lowRight;
    this.neighbors = new ArrayList<Space>();
    this.players = new ArrayList<Player>();
    this.items = new ArrayList<Item>(); 
    this.pet = null;
  }
  
  /**
   * Copy constructor.
   * @param space the space to copy
   */
  public DefaultSpace(Space space) {
    // check null
    if (space == null) {
      throw new IllegalArgumentException("Space cannot be null");
    } 
    
    this.spaceId = space.getId();
    this.name = space.getName();
    this.upLeft = space.getUpLeft();
    this.lowRight = space.getLowRight();
    this.players = new ArrayList<Player>();
    this.pet = null;
    
    this.neighbors = new ArrayList<Space>();
    for (int i = 0; i < space.getNeighborSize(); i++) {
      neighbors.add(space.getNeighborAt(i));
    }
    
    
    this.items = new ArrayList<Item>();
    for (int i = 0; i < space.getItemSize(); i++) {
      items.add(space.getItemAt(i));
    }
    
  }
  
  @Override
  public int getPlayersSize() {
    return this.players.size();
  }
  
  @Override
  public Player getPlayerByOrder(int orderIndex) throws IllegalArgumentException {
    final int maxPlayer = 10;
    if ((orderIndex < 0) || (orderIndex >= maxPlayer)) {
      throw new IllegalArgumentException("no player of this index");
    }
    return players.get(orderIndex);
  }

  @Override
  public int getId() {
    return this.spaceId;
  }
  
  @Override
  public String getName() {
    return this.name;
  }
  
  @Override
  public String getItemsAsString() {
    StringBuilder s = new StringBuilder();
    for (Item item : items) {
      s.append(item.getItemId()).append(".").append(item.getName())
          .append(",");
    }
 
    // return without the last comma
    return s.toString();
  }
  
  @Override
  public RowCol getUpLeft() {
    RowCol upLeftClone = new RowCol(this.upLeft.getRow(), this.upLeft.getCol());
    return upLeftClone;
  }
  
  @Override
  public RowCol getLowRight() {
    RowCol lowRightClone =  new RowCol(this.lowRight.getRow(), this.lowRight.getCol());
    return lowRightClone;   
  }

  @Override
  public int getNeighborSize() {
    return this.neighbors.size();
  }
  
  @Override 
  public Space getNeighborAt(int id) throws IllegalArgumentException {
    if (id < 0) {
      throw new IllegalArgumentException(
              new StringBuilder("List id cannot be negative ")
                      .append("when getting a neighbour")
                      .toString());
    }
    return this.neighbors.get(id);
  }
  
  @Override 
  public List<Integer> getNeighborsIndices() {
    return this.neighbors
    .stream()
    .map(neighbor -> neighbor.getId())
        .collect(Collectors.toList());
    
  }
  
  @Override
  public int getItemSize() {
    return this.items.size();
  }
  
  @Override 
  public Item getItemAt(int id) throws IllegalArgumentException {
    if ((id < 0) || (items.size() == 0)) {
      throw new IllegalArgumentException(
              new StringBuilder("Set id cannot be negative when getting")
                      .append("an item from a space's set or there is no item present.")
                      .toString());
    }
    return this.items.get(id);
  }
 
  @Override
  public void addItem(Item item) throws IllegalArgumentException {
    if (item == null) {
      throw new IllegalArgumentException("Item added to a space cannot be null");
    }
    this.items.add(item);
  }
  
  @Override 
  public void addPet(Pet pet) throws IllegalArgumentException, IllegalStateException  {
    if (pet == null) {
      throw new IllegalArgumentException("Pet added to a space cannot be null");
    } else if (this.pet != null) {
      throw new IllegalStateException("A space cannot have more than 1 pet");
    }
    this.pet = pet;
  }
  
  @Override
  public void addNeighbor(Space other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException("Neighbor added to a space cannot be null");
    }

    // skip duplicate
    for (Space space : neighbors) {
      if (space.equals(other)) {
        return;
      }
    }
    
    if (this.isNeighborByCalc(other) || other.isNeighborByCalc(this)) {
      this.neighbors.add(other);
      other.addNeighbor(this);
    }
  }
   
  @Override
  public void addPlayer(Player player) throws IllegalArgumentException {
    if (player == null) {
      throw new IllegalArgumentException("Player added to a space cannot be null");
    } 
    
    // if player already exist, reject
    for (Player playerInList : players) {
      if (player.equals(playerInList)) {
        throw new IllegalArgumentException(
            "player already exist in this space");
      }
    }
    this.players.add(player);
  }
 
  @Override
  public void removeItem(Item item) throws IllegalArgumentException {
    if (item == null) {
      throw new IllegalArgumentException("Cannot remove null item from space");
    } 
    
    // check if item is present
    for (Item itemInSpace : items) {
      if (itemInSpace.equals(item)) {
        this.items.remove(item); // remove
        return;
      }
    }
    throw new IllegalArgumentException("Cannot remove non-existing item from this space");
  }
  
  @Override 
  public void removePet() {
    this.pet = null;
  }
  
  @Override
  public void removeAllPlayers() {
    this.players = new ArrayList<Player>();
  }
  
  @Override
  public void removePlayer(Player player) throws IllegalArgumentException {
    if (player == null) {
      throw new IllegalArgumentException("Cannot remove null player from space");
    } 
    
    // check if player is present
    for (Player playerInSpace : players) {
      if (playerInSpace.equals(player)) {
        this.players.remove(player);
        return;
      }
    }
    throw new IllegalArgumentException("Cannot remove non-existing player from this space");
  }

  @Override
  public boolean isPetHere() {
    return (this.pet != null);
  }
  
  @Override
  public boolean isItemPresent(Item other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException("Item cannot be null");
    }
    for (Item item : items) {
      if (item.equals(other)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean isNeighbor(Space other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException("Space cannot be null");
    }
    
    for (Space neighbor : neighbors) {
      if (neighbor.equals(other)) {
        return true;
      }
    }
    
    return false;
  }
  
  @Override
  public boolean isNeighborByCalc(Space other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException("Space cannot be null");
    }
    
    boolean isHorizontalNeighbor = false;
    // e is the element this
    // p is other (potential)
    // Check for left right neighbors
    // 1. if 's wall is on the left of p's wall or vice versa they might be neighbors
    boolean iseLeftOfp = (this.getUpLeft().getCol() == (other.getLowRight().getCol() + 1));
    boolean iseRightOfp = (this.getLowRight().getCol() == (other.getUpLeft().getCol() - 1));

    // 2. if e's top wall falls between p's vertical wall range
    boolean etopBetweenPverticalRange = 
        ((this.getUpLeft().getRow() >= other.getUpLeft().getRow())
            && (this.getUpLeft().getRow() <= other.getLowRight().getRow()));

    // 3. if e's bottom wall falls between p's vertical wall range
    boolean ebottomBetweenPverticalRange = 
        ((this.getLowRight().getRow() >= other.getUpLeft().getRow()) 
            && (this.getLowRight().getRow() <= other.getLowRight().getRow()));

    // 4. Combined the if logic together:
    if (iseLeftOfp || iseRightOfp) {
      if (etopBetweenPverticalRange || ebottomBetweenPverticalRange) {
        isHorizontalNeighbor = true;
      }
    }

    // Check for top bottom neighbors
    // 4. If e's wall is right below or above p's wall 
    boolean isVerticalNeighbor = false;
    boolean ebelowP = (this.getUpLeft().getRow() == other.getLowRight().getRow() + 1);
    boolean eaboveP = (this.getLowRight().getRow() == other.getUpLeft().getRow() - 1);   
    if (ebelowP || eaboveP) {

      // 5. If e's left wall falls between p's horizontal wall range
      boolean eleftBetweenPhorizontalRange = 
          (this.getUpLeft().getCol() >= other.getUpLeft().getCol() 
          && (this.getUpLeft().getCol() <= other.getLowRight().getCol()));


      // 6. If e's right wall falls between p's horizontal wall range
      boolean erightBetweenPhorizontalRange = 
          (this.getLowRight().getCol() >= other.getUpLeft().getCol())
          && (this.getLowRight().getCol() <= other.getLowRight().getCol());

      if (eleftBetweenPhorizontalRange || erightBetweenPhorizontalRange) {
        isVerticalNeighbor = true;
      }
    
    }
    return (isHorizontalNeighbor || isVerticalNeighbor);
  }
   
  @Override
  public boolean doNeighborsHavePlayersPetMode() {
    for (Space neighbor : neighbors) {
      if (!neighbor.isPetHere()) {
        if (neighbor.getPlayersSize() > 0) {
          return true;
        }
      }
    }
    return false;
  }
  
  @Override
  public boolean doNeighborsHavePlayers() {
    for (Space neighbor : neighbors) {
      if (neighbor.getPlayersSize() > 0) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * List neighbor as string.
   * @param hidePetRoom is set to true if we are hiding the pet room
   * @param neighborNameOnly is set to true if we only want to display the name of the neighbor
   * @return the string that contains name of neighbors of a space. 
   */
  private String neighborsAsString(boolean hidePetRoom, boolean neighborNameOnly) {
    StringBuilder s = new StringBuilder();
    
    if (!neighbors.isEmpty()) {
      Iterator<Space> iterator = neighbors.iterator();
      while (iterator.hasNext()) {
        Space neighbor = iterator.next();

        if (hidePetRoom) {
          if (neighbor.isPetHere()) { // skip if pet is present
            continue;
          }
        }

        // 1. Space name
        s.append("\n>>> ").append(neighbor.getName()).append("-")
          .append(neighbor.getId())
            .append(" <<<\n");

        if (!neighborNameOnly) { // print the info if it's not name only

          boolean playerNameOnly = false;
          if (neighbor.getItemSize() != 0) {
            s.append("           Item:\n").append(neighbor.itemsAsString());
          }

          if (neighbor.getPlayersSize() != 0) {
            s.append("\n           Players:\n").append(neighbor.playersAsString(playerNameOnly));
          }

          if (neighbor.isPetHere()) {
            s.append("\n           Pet:\n").append(neighbor.petAsString());
          }

          if (!"".equals(neighbor.petAsString())) {
            s.append("\n");
          }
        }

      }
    }
    return s.toString();
  }
  
  @Override
  public String petAsString() {
    StringBuilder s = new StringBuilder();
    if (isPetHere()) {
      s.append("           ").append(this.pet.getName())
      .append("\n");
    }
    return s.toString();
  }
  
  @Override
  public String playersAsString(boolean nameOnly) {
    StringBuilder s = new StringBuilder();
    if (!players.isEmpty()) {
      Iterator<Player> iterator = players.iterator();
      
      int i = 1;
      while (iterator.hasNext()) {
        // 1. show  number 1 to n
        s.append("           ").append(i).append(". ");
        
        // 2. if its name only
        if (nameOnly) {
          s.append(iterator.next().getName())
              .append("\n");
        } else { // 3. show max item and items

          s.append(iterator.next().toString())
              .append("\n");
        }
        i += 1;
      }
    }
    return s.toString();
  }
  
  @Override
  public String itemsAsString() {
    StringBuilder s = new StringBuilder();
    if (!items.isEmpty()) {
      Iterator<Item> iterator = items.iterator();
      
      int i = 1;
      while (iterator.hasNext()) {
        s.append("           ").append(i).append(". ");
        s.append(iterator.next().toString())
             .append("\n");
        i += 1;
      }
    }
    return s.toString();
  }
  
  @Override
  public String toStringIsLookAround(boolean isLookAround) {
    StringBuilder s = new StringBuilder();
    String title = "";
    boolean hidePetRoom = true;
    
    boolean playerNameOnly = true;
    boolean neighborNameOnly = true;
    // 1. title
    if (isLookAround) {
      title = "LOOKING AROUND FROM YOUR ROOM: ";
      playerNameOnly = false;
      neighborNameOnly = false;
    } else {
      title = "YOUR CURRENT ROOM: ";
    }
    s.append(title);
    
    // 2. sub title
    s.append(name).append("-").append(spaceId)
    .append("\n\n=================\n")
    .append("ROOM INFORMATION\n")
    .append("=================\n")
    
    // 3. item
    .append("\nITEM\n")
    .append("---------------\n")
    .append(this.itemsAsString())
    
    // 4. player
    .append("\nPLAYER\n")
    .append("---------------\n")
    .append(this.playersAsString(playerNameOnly));
    
    // 5. pet
    if (this.pet != null) {
      s.append("\nPET\n")
      .append("---------------\n")
          .append(this.petAsString());
    }

    // 6. neighbors
    s.append("\nNEIGHBORS\n") 
    .append("---------------\n")
    // neighbors with no pet visible
    // hide pet is true, name only false
    .append(this.neighborsAsString(hidePetRoom, neighborNameOnly))
        .append("\n");
    return s.toString();
  }
  
  
  /**
   * Will show the item, players and pet in the space without pet effect.
   * @return the string that listed all spaces without pet effect
   */
  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    
    // title
    s.append(name).append("-").append(spaceId)
    .append("\n\n=================\n")
    .append("ROOM INFORMATION\n")
    .append("=================\n")

    // 
    .append("\nITEM\n")
    .append("---------------\n")
    .append(this.itemsAsString())
    .append("\nPLAYERS\n")
    .append("---------------\n")
    .append(this.playersAsString(false)) 
    .append("\nPET\n")
    .append("---------------\n")
    .append(this.petAsString())
    .append("\nNEIGHBORS\n") 
    .append("---------------\n") // all neighbors visible
    .append(this.neighborsAsString(false, false)) // don't hide pet, name only false
    .append("\n");


    return s.toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Space)) {
      return false;
    }

    Space that = (Space) o;
    return (this.name.equals(that.getName()));

  }

}