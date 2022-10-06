package world.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import world.controller.Features;
import world.model.Result;

/**
 * The panel that shows:
 * - the map of the board game, 
 * - add players panel 
 * - long text panel when the game started
 * - action result panel
 * - Game status label
 * - instruction label.
 *
 * gamepanel has a gridbox layout, it as statuspanel at top row and col 1,
 * worldmap in center with weight 70% row1,col0, switch panel -> 30% row1, col 1
 * actionresult in bottom row 2, col 0
 * instrcutions in bottom row 3, col 0
 * switch panel is cardlayout again with addplayer and info panel.
 */
public final class GamePanel extends JPanel {

  /**
   * serial version for the class.
   */
  private static final long serialVersionUID;
 
  static {
    serialVersionUID  = 5386368511908335835L;
  }
  
  
  // 1. the map
  private JScrollPane worldScrollPane;
  private Canvas canvas;
  
  // 2. status
  private JLabel statusLabel;
  
  // 3a add player panel
  private AddPlayerPanel addPlayerPanel;
  
  // 3b long text
  private JScrollPane longTextScrollPane;
  private JTextPane longTextPane;

  // 3c switchable CardlayoutPanel
  private JPanel switchablePanel;
  
  // 4. action result
  private JPanel actionResultPanel;
  private JLabel actionResultLabel;
  
  // 5. instruction
  private JLabel instructionLabel;
  

  /**
   * Constructor that initializes the panels inside the game panel.
   * The labels and image will be empty or null
   * It will be set once when the game starts
   */
  public GamePanel() {
    this.setBackground(WorldColor.BLACK.getColor());

    // Initialize panels
    worldScrollPane = new JScrollPane();
    addPlayerPanel = new AddPlayerPanel();

    // default placeholder panel
    switchablePanel = new JPanel();
    switchablePanel.setLayout(new CardLayout());
    
    initLongTextPanel();
    initStatusLabel();
    initActionResultPanel();
    initInstructionLabel();

    // 1.Set layout
    // 1.a Set layout on the base panel
    this.setLayout(new BorderLayout());

    // 1.b align status
    this.add(statusLabel, BorderLayout.PAGE_START);

    // 1.c align world panel
    this.add(worldScrollPane, BorderLayout.CENTER);

    // 1.c align switchable panel
    // make card switch possible.
    this.switchablePanel.add("addPlayerPanel", this.addPlayerPanel);
    this.switchablePanel.add("longTextPanel", this.longTextScrollPane);
    this.add(switchablePanel, BorderLayout.LINE_END);
    
    JPanel temp = new JPanel();
    temp.setBackground(WorldColor.BLACK.getColor());
    temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));
    
    this.instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    temp.add(instructionLabel);
    
    this.actionResultPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    temp.add(actionResultPanel);
    this.add(temp, BorderLayout.PAGE_END);
    
    
  }

  /**
   * Set the image in the game status panel and repaint.
   * It will also set listeners to this canvas.
   * @param image the image to be displayed on game panel
   * @param features the fetures to listens on the even on the canvas
   * @throws IllegalArgumentException if image is null
   */
  public void setImage(BufferedImage image, Features features)
          throws IllegalArgumentException {
    if (image == null || features == null) {
      throw new IllegalArgumentException(
              new StringBuilder("image or features cannot be null")
                      .append(" when setting image in game panel")
                      .toString());
    }
    
    this.remove(worldScrollPane);

    // 1) Jpanel  called canvas
    canvas = new Canvas(image);
    canvas.repaint();
    canvas.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
    
    // add listeners to the canvas
    canvas.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        
        // - Get the coordinates of the mouse click
        Point coord = e.getPoint();
        features.moveHumanOrDisplayPlayerInfo(coord);
      }
    });
    
    // 2) add this canvas to scroll pane
    worldScrollPane = new JScrollPane(canvas);
    worldScrollPane.getVerticalScrollBar().setUnitIncrement(8);
    worldScrollPane.getHorizontalScrollBar().setUnitIncrement(8);
    
    this.add(worldScrollPane, BorderLayout.CENTER);
  }
  
  /**
   * Set listeners to buttons and key presses.
   * @param features the features that will evoke some action in response to the event
   * @throws IllegalArgumentException if feature is null
   */
  public void setListeners(Features features) {
    if (features == null) {
      throw new IllegalArgumentException("features cannot be null when setting listeners");
    }
    
    // 1. add player panels
    addPlayerPanel.setListeners(features);
    
    // 2. this panel listeners
    // - key listeners
    Map<Character, Runnable> keyTypedMap = new HashMap<>();
    keyTypedMap.put('p', () -> features.humanMovePet());
    keyTypedMap.put('i', () -> features.humanPickItem());
    keyTypedMap.put('c', () -> features.takeCpuTurn());
    keyTypedMap.put('k', () -> features.humanKill());
    keyTypedMap.put('l', () -> features.humanLookAround());
    keyTypedMap.put('q', () -> features.quitGame());
    
    WorldKeyListener worldKeyListener = new WorldKeyListener();
    worldKeyListener.setMap(keyTypedMap);
    this.addKeyListener(worldKeyListener);
    
    this.setFocusable(true);
  }

  /**
   * Show a pop up of look around.
   * @param frame the main frame of the view
   * @param result the various text result from the world
   */
  public void showLookAroundDialog(JFrame frame, Result result) {
    if (frame == null || result == null) {
      throw new IllegalArgumentException("Features or result cannot be null.\n");
    }
    String lookAroundText = result.getLookAroundText();
    // 1. set the text
    lookAroundText.replaceAll("\\\\n", System.getProperty("line.separator"));
    JTextPane lookTextPane = new JTextPane();
    lookTextPane.setEditable(false);
    
    // 2. place text on text pane
    lookTextPane.setForeground(Color.WHITE);
    lookTextPane.setBackground(WorldColor.BROWN.getColor());
    lookTextPane.setPreferredSize(new Dimension(500, 500));
    lookTextPane.setText(lookAroundText);
    lookTextPane.setAlignmentX(SwingConstants.CENTER);
    
    // 3. place text pane on scroll pane
    JScrollPane lookScrollPane = new JScrollPane(lookTextPane);
    
    // 4. place scroll pane on dialog
    JDialog lookDialog = new JDialog(frame);
    lookDialog.add(lookScrollPane);
    lookDialog.setBounds(1130, 62, 500, 500);

    lookDialog.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        
        // if window is closed then look around finish
        // update the others
        updateActionResultPanel(result.getActionResult());
        updateGameStatusPanel(result.getStatus());
        updateLongTextPanel(result.getEveryTurnSpaceInfo());

      }
    });
    
    // 5. set visible
    lookDialog.setVisible(true);
  }
  
  /**
   * Update the image in the world panel.
   */
  public void updateWorldPanel() {
    // draw the image on canvas
    canvas.repaint();
   
  }
  
  /**
   * Update the string in the game status panel.
   * @param status the string to be displayed for the game status
   * @throws IllegalArgumentException if the string is empty
   */
  public void updateGameStatusPanel(String status) throws IllegalArgumentException {
    validateString(status);
    statusLabel.setText(status);
  }

  /**
   * Update action result panel that will have description of the result of every turn.
   * @param status the string to be displayed for the action result
   * @throws IllegalArgumentException if the string is empty
   */
  public void updateActionResultPanel(String status) throws IllegalArgumentException {
    validateString(status);
    actionResultLabel.setText(status);
  }
  
  /**
   * Update the long text panel that will have description of the space.
   * @param spaceInfo the long text of the space description
   * @throws IllegalArgumentException if the string is empty
   */
  public void updateLongTextPanel(String spaceInfo) throws IllegalArgumentException {
    validateString(spaceInfo);
    spaceInfo.replaceAll("\\\\n", System.getProperty("line.separator"));
    longTextPane.setText(spaceInfo);
  }

  /**
   * Switch game layout to have long text panel instead of 
   * add player panel.
   */
  public void switchLayoutToLongText() {
    CardLayout cardLayout = (CardLayout) (switchablePanel.getLayout());
    cardLayout.show(switchablePanel, "longTextPanel");
  }
  
  /**
   * Switch game layout to have add player panel 
   * add player panel.
   */
  public void switchLayoutToAddPlayer() {
    CardLayout cardLayout = (CardLayout) (switchablePanel.getLayout());
    cardLayout.show(switchablePanel, "addPlayerPanel");
  }

  /* ********************** Helper Methods ************************/

  /**
   * Until for validating string argument.
   * @param someName the string to be validated
   * @throws IllegalArgumentException if the string is empty
   */
  private void validateString(String someName) throws IllegalArgumentException {
    if (someName == null || "".equals(someName)) {
      throw new IllegalArgumentException("name arguments cannot be empty");
    }
  }

  /**
   * Helper method to initialise status label.
   */
  private void initStatusLabel() {
    statusLabel = new JLabel();
    statusLabel.setForeground(WorldColor.RED.getColor());
    statusLabel.setFont(new Font("Rockwell", Font.BOLD, 25));
    statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
    statusLabel.setPreferredSize(new Dimension(200, 60));


  }

  /**
   * Helper method to initialise Result panel.
   */
  private void initActionResultPanel() {
    // panel
    actionResultPanel = new JPanel();
    actionResultPanel.setLayout(new BorderLayout(0, 0));
    actionResultPanel.setBackground(WorldColor.ORANGE.getColor());
    actionResultPanel.setPreferredSize(new Dimension(898, 52));

    // label
    actionResultLabel = new JLabel();
    actionResultLabel.setFont(new Font("Rockwell", Font.BOLD, 14));
    actionResultLabel.setForeground(WorldColor.WHITE.getColor());
    actionResultLabel.setHorizontalAlignment(SwingConstants.CENTER);
    actionResultLabel.setVerticalAlignment(SwingConstants.CENTER);

    // add the label to panel
    actionResultPanel.add(actionResultLabel, BorderLayout.CENTER);

  }

  /**
   * Helper method to initialise instructions label.
   */
  private void initInstructionLabel() {
    StringBuilder sb = new StringBuilder();
    sb.append("<html><body>Press 'P' to move a pet; ")
            .append("'I to pick item; ")
            .append("'L' to look around; ")
            .append("'K' to make a kill attempt; ")
            .append("'C' to move CPU player; ")
            .append("'Q' to quit;")
            .append("</body></html>");
    instructionLabel = new JLabel(sb.toString());
    instructionLabel.setForeground(Color.GREEN);
    instructionLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
    instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);

  }

  /**
   * Helper method to initialise long text panel.
   */
  private void initLongTextPanel() {
    longTextPane = new JTextPane();
    longTextPane.setEditable(false);
    longTextPane.setForeground(Color.WHITE);
    longTextPane.setBackground(WorldColor.DARKBLUE.getColor());

    // add the text pane to scroll pane
    longTextScrollPane = new JScrollPane(longTextPane);


    //
    this.switchablePanel.add("longTextPanel", this.longTextScrollPane);
    this.add(switchablePanel, BorderLayout.LINE_END);
  }
}
