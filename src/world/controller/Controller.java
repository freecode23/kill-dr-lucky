package world.controller;

/**
 * This is the controller for the MVC project, it
 * orchestrates the actions on view and model.
 */
public interface Controller {
  /**
   * Execute a single game of Doctor Lucky given a World Model. When the game is over,
   * the display method ends.
   */
  void display();
}
