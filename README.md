# Semester Project
This repo represents the coursework for CS 5010, the Fall 2022 Edition!

Memmbers : Annanya Sah and Sherly Hartono

**Email:** hartono.s@northeastern.edu 
        sah.a@northeastern.edu

**Preferred Name:** Sherly, Annnaya


### About/Overview
Long semester project for a board game that is loosely inspired by the Doctor Lucky series of games

### List of Features
- Game has a UI to interact and play with
- The world map is shown with all the spaces as per the txt file and players, target are shown in different colors on the space they are occupying.
- The game player can play with current game or load new game through a different txt file chosen from UI.
- Add players to the game by clicking on buttons
- Move target to different spaces by clicking on the destination space.
- Create a graphical image with the mark of target Character in red
- See the neighbor of a space and the items belonging to the neighbors along with their damage point when looking around.
- Make an attempt to kill the target by clicking on target and choosing an item to hurt the target. If no items are present, player's still have an option to poke it in the eye.
- player can move a pet anywhere even if they are not in the same space by pressing 'P' and selecting the destination from the drop down menu.
- Quitting the game by key press and menu option selection.
- See the status of the game once it ends. The game play ends when target dies or total game turns are reached and Target still has any health points left.
- Hiding a space when pet is present in the room, there by making the room invisible from outside.


### How to Run
Put the jar file and a .txt file on the same directory and run:
(5 is the number of max turn)  
"java -jar annanya-sherly.jar mansion.txt 5"  

### How to Use the Program
Once the program starts to run. You'll be on the home screen.

- Click on 'Start' game button to start the game with the already loaded game file. This is the file mentioned on the command line argument.
- If you want to change the game, please click on options menu at the top and select 'Other Game' option. You'll see a file picker. Select a .txt file to load it. The default max turns in the game remains the same as the original arguments passed. This is a limitation.
- If you want to load Current game again, click on 'Current Game'.
- To Start playing the game, click on 'Play Game'.
- Click on 'Add CPU Player' to add CPU player, their names get automatically generated.
- Click on 'Add Human Player' to add human player. You'll be prompted to enter the name of the player. Enter name and choose a space to enter from the drop down menu.
- Maximum 10 players can be added as per requirement.
- To start the game, once players are added , click 'Start Game' button.
- To move a player to a different space, Click on the destination space on the map.
- To move the pet to a space, press 'P' and from the list of space shown in a pop-up dialog, select destination space and then Okay.
- To Look Around, press 'L' on the Keyboard.
- To make CPU player take turn, press 'C' on the Keyboard.
- To Quit the game, press 'Q' on the keyboard.
- To see the details of current player, click on your player icon.


### Example Runs 
As per requirement, please refer to the video to be uploaded later on.


### Design/Model Changes
Final design UMl with last changes can be found in res folder. "uml4.pdf"
1. Added arguments for all commands. The commands should have these along with the view,when required to work properly. Eg. LoadNewGame had just view, but now has Readable and max turns; HumanMove had just view but now has point and view. Similar changes were required for rest of the commands.
2. Added Results interface and DefaultResults classes to make the communication to pass relevant data between Model and view via Controller without actually modifying model's data.
3. World now has several methods used by the features, like reset(),startTheGame() and methods to check validity of points clicked on the screen.
4. DefaultWorld has several new methods to do the setting of results on a Result object later used. Eg : setCurrentPlayerAndSpaceName(), setCurrentPlayerInfo(),setCurrentStatusAndLimitedSpaceInfo() and validateString() for having all string null checks in one place.
5. Instead of just having an ImageGenerator class, we have a ImageGenerator interface, implemented by DefaultImageGenerator which World has to create all the images and it's modifications.
6. Replaced the ReadOnly model class to Results.
7. GamePanel now has a canvas , and not WorldPanel.
8. Removed GameStatus panel,UserPromptPanel,ActionResultsPanel,LongTextPanel as these are now just instance variables of GamePanel and are JPanel/JText and not seperate classes.
9. Removed Mouse and Button Adapter, made KeyListener being used by the view.
10. Added WorldColor enum to have a comman place for all colors used in the game's view.


### Assumptions
1. We assume that there is only 1 world per running program.
2. Quit means closing the game entirely and not just the current game. So we close the program.


### Limitations
Number of turns once read from the file by command line argument cannot be changed. We had to make this as at last minute, our validation was failing.
2) Spaces when very small have leave entities (player/target) 's name as the content only get cleared form the current space. We didn't want to regerate the entire space image and could not in the given time erase right most space.
3) Space coordinates don't overlap thus we only account for duplicate coordinate of spaces when checking for validity.

### Authors
Sherly Horonto and 
Annanya Sah
### Citations
https://stackoverflow.com/  
https://northeastern.instructure.com/courses/108354/assignments/1278111
https://piazza.com/class/kyasoq2z5kv3j0  
https://www.hackerearth.com/practice/algorithms/graphs/depth-first-search/tutorial/



