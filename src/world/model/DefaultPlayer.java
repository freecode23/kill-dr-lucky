package world.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;


/**
 * Player in the game the can be controlled by either human user or CPU.
 * Two players are the same if they have the same name.
 * Thus player name is unique.
 * MaxItem varies between each players from range 1 to 5.
 */
public class DefaultPlayer implements Player {
  private final String name;
  private final int maxItem;
  private final Set<Item> itemsHold;
  private Space currentSpace;
  
  /**
   * Construct a Player object using the given name
   * chosen space, and max item it can hold.
   *
   * @param name the name of the player
   * @param currentSpace the space it is in 
   * @param maxItem the maximum number of item
   * @throws IllegalArgumentException if any argument is null
   *     and if maxItem is negative.
   * 
   */
  public DefaultPlayer(String name, Space currentSpace,
      int maxItem) throws IllegalArgumentException {
    // check null and empty string
    if (name == null || "".equals(name) || currentSpace == null) {
      throw new IllegalArgumentException("Name or current space cannot be empty");
    }
    
    if (maxItem < 0) {
      throw new IllegalArgumentException("Max Item cannot be 0");
    }
    
    this.name = name;
    this.currentSpace = currentSpace;
    this.itemsHold = new HashSet<Item>();
    this.maxItem = maxItem;
    currentSpace.addPlayer(this);
  }
  
  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getItemIdWithMostDamage() {
    if (itemsHold.size() > 0) {
      Item itemMax = itemsHold.stream()
          .max((itemA, itemB) -> itemA.getDamagePoint() - itemB.getDamagePoint())
          .get();
      
      return itemMax.getItemId();
    }
    return -1; // item does not exist
  }
  
  @Override
  public String getItemNameById(int itemId) throws IllegalArgumentException {
    if (itemId < -2 
        || ((this.getItemAtId(itemId) == null) && (itemId > -1))) {
      throw new IllegalArgumentException("item index invalid");
    }
    
    if (itemId == -1) {
      return "eye poke";
    } else {
      return  this.getItemAtId(itemId).getName();
    }
  }
  
  @Override
  public String getItemsAsString() {
    StringBuilder s = new StringBuilder();
    s.append("-1.eye poke").append(",");
    for (Item item : itemsHold) {
      s.append(item.getItemId()).append(".").append(item.getName())
          .append(",");
    }
 
    // return without the last comma
    return s.toString();
  }
  
  @Override
  public int getMaxItem() {
    return this.maxItem;
  }

  @Override
  public Space getCurrentSpace() {
    return this.currentSpace;
  }
  
  @Override
  public boolean attemptToKill(TargetCharacter target, int itemId)
      throws IllegalArgumentException {
    if (target == null || (itemId <= -2) 
        || ((this.getItemAtId(itemId) == null) && (itemId > -1))) {
      throw new IllegalArgumentException("Cannot kill null target or itemId does not exist");
    } 
    
    if (target.getCurrentSpaceId() != this.currentSpace.getId()) {
      throw new IllegalArgumentException("Target given is not in the same space");
    }
    
    // if cannot be seen
    if (!this.canBeSeen()) {
      if (itemId == -1) { // poke eye
        target.reduceHealth(1);
        
      } else { // weapon
        Item item = this.getItemAtId(itemId);
        target.reduceHealth(item.getDamagePoint());
        itemsHold.remove(item);
      }
      return true;
    } else {
      Item item = this.getItemAtId(itemId);
      itemsHold.remove(item);
      return false;
    }
  }
 
  @Override
  public String lookAround() {
    return currentSpace.toStringIsLookAround(true);
  }
  
  @Override
  public void movePet(Pet pet, Space newSpace) throws IllegalArgumentException {
    if (newSpace == null || pet == null) {
      throw new IllegalArgumentException("Cannot move null pet to a space or to a null space");
    }
    pet.move(newSpace);
  }
  
  @Override
  public boolean moveToNeighbor(Space newSpace) throws IllegalArgumentException { 
    if (newSpace == null) {
      throw new IllegalArgumentException("Moving to null space");
    }
    if (!currentSpace.isNeighbor(newSpace)) {
      return false;
    }

    this.currentSpace.removePlayer(this);  // remove myself from the old space
    this.currentSpace = newSpace; // set own space's name
    this.currentSpace.addPlayer(this); // add myself to the new space    
    return true;
  }
  
  @Override
  public boolean pickUpItem(Item itemToPick) 
      throws IllegalArgumentException {
    if (itemToPick == null) {
      throw new IllegalArgumentException("picking up null item");
    }
    if ((this.itemsHold.size() == maxItem) || (!currentSpace.isItemPresent(itemToPick))) {
      return false; 
    }
    
    this.itemsHold.add(itemToPick); // add item to my bag
    this.currentSpace.removeItem(itemToPick); // remove item from the space
    return true;
  }
  
  @Override
  public boolean thinksCannotBeSeen() {
    if ((currentSpace.getPlayersSize() == 1) 
        && (!currentSpace.doNeighborsHavePlayersPetMode() || currentSpace.isPetHere() == true)) {
      return true;
    } else {
      return false; 
    }
  }

  @Override
  public boolean canSeePlayer(Player other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException(
          "Player cannot be null when checking if a player can see another player");
    }
    
    // same space
    if (this.getCurrentSpace().equals(other.getCurrentSpace())) {
      return true;
    } 
    
    // other is in neighboring space and the neighrboring space has no pet
    if (this.getCurrentSpace().isNeighbor(other.getCurrentSpace()) 
        && !other.getCurrentSpace().isPetHere()) {
      return true;
    }
    
    return false;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(name)
      .append(", max item: ").append(maxItem).append("\n");
   
    if (!itemsHold.isEmpty()) {
      Iterator<Item> iterator = itemsHold.iterator();
      
      while (iterator.hasNext()) {
        s.append("               > ")
         .append(iterator.next().toString())
             .append("\n");
      }
    }
      
    return s.toString();
  }
  
  @Override
  public boolean equals(Object o) {
    // Fast path for pointer equality:
    if (this == o) { // backward compatibility with default equals
      return true;
    }

    // If o isn't the right class then it can't be equal:
    if (!(o instanceof Player)) {
      return false;
    }

    // The successful instance of check means our cast will succeed:
    Player that = (Player) o;
    return name.equals(that.getName()); 
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  /* **********Helper Methods ****************************/

  /**
   * Check if this player can be seen by other player. This is the ground truth
   * A player can be seen if:
   *  - neighboring space have players and there is no pet present OR
   *  - current space has more than 1 player
   * @return true if there are players in neighbors
   *     including the rooms with pet.
   */
  private boolean canBeSeen() {
    if (this.currentSpace.doNeighborsHavePlayers() && (this.currentSpace.isPetHere() == false)
            || (this.currentSpace.getPlayersSize() > 1)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Get the item hold by this player with index specified.
   * @return the item if it exist else, returns null
   */
  private Item getItemAtId(int id) throws IllegalArgumentException {
    if (id <= -2) {
      throw new IllegalArgumentException("Item index does not exist");
    }
    try {
      // get the item
      Item item = itemsHold
              .stream()
              .filter(itemA -> itemA.getItemId() == id)
              .findAny()
              .get();
      return item;
    } catch (NoSuchElementException e) {
      return null;
    }
  }
}
