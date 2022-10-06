package world.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import world.controller.Features;
import world.model.Result;

/**
 * This game view consist of 2 main panels.
 * The welcome panel and the game panel
 * The first part of game panel will have add player panel on the right.
 * Once player is added, it will remove this panel and replace with long
 * text panel to show the space info that will be displayed at the end of every turn
 *
 */
public class DefaultGameView implements GameView {
  static String GAMEPANEL = "game";
  static String WELCOMEPANEL = "welcome";
  private JFrame frame;
  private GamePanel gamePanel;
  private WelcomePanel welcomePanel;
  private JMenuBar menuBar;
  private JPanel mainPanel;
  private JMenuItem playCurrentMnItem;
  private JMenuItem playOtherMnItem;
  private JMenuItem quitMnItem;

  /**
   * Constructor that initializes the panel layout and add listeners.
   */
  public DefaultGameView() {
    // 1. frame
    this.frame = new JFrame();
    this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.frame.getContentPane().setBackground(WorldColor.BLACK.getColor());


    // 2. menu bar
    this.menuBar = createMenuBar();
    this.frame.setJMenuBar(this.menuBar);

    // 3. welcome panel
    this.welcomePanel = new WelcomePanel();


    // 4. game panel
    this.gamePanel = new GamePanel();

    //5. default placeholder panel
    this.mainPanel = new JPanel();
    this.mainPanel.setLayout(new CardLayout());

    //6. make card switch possible.
    this.mainPanel.add(WELCOMEPANEL, this.welcomePanel);
    this.mainPanel.add(GAMEPANEL, this.gamePanel);

    //7. make main frame visible
    this.frame.getContentPane().add(this.mainPanel);
    this.frame.pack();
    this.frame.setVisible(true);
    this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  @Override
  public void setFeatures(Features features) throws IllegalArgumentException {
    if (features == null) {
      throw new IllegalArgumentException("features cannot be null when setting features to view");
    }
    // set all listeners
    welcomePanel.setListeners(features);
    gamePanel.setListeners(features);
    this.setListeners(features);
  }

  @Override
  public void setContent(Result result, Features features)throws IllegalArgumentException  {
    if (result == null || features == null) {
      throw new IllegalArgumentException("image or features cannot be null.\n");
    } 

    welcomePanel.setWelcomeLabel(result.getName());
    gamePanel.setImage(result.getBufferedImage(), features);
    updateMapStatusAndActionResultPanel(result);
  }

  @Override
  public void switchLayoutToAddPlayer() {
    this.gamePanel.switchLayoutToAddPlayer();
  }
  
  @Override
  public void switchLayoutToLongText() {
    this.gamePanel.switchLayoutToLongText();
  }

  @Override
  public List<String> showAddPlayerInputDialog(List<String> spaceNames) {
    if (spaceNames == null) {
      throw new IllegalArgumentException("Space name list cannot be null.\n");
    }
    // Create the dialog.
    JDialog d = new JDialog(this.frame, "Dialog Example", true);
    d.setLayout(new GridLayout(3, 1));

    // 1. create name input field.
    JLabel namePromptLabel = new JLabel("Enter name of the player :");
    JTextField namePromptTextField = new JTextField();
    namePromptTextField.setPreferredSize(new Dimension(100, 25));
    JPanel name = new JPanel();
    name.setLayout(new FlowLayout());
    name.add(namePromptLabel);
    name.add(namePromptTextField);

    // 2. create space input field.
    JPanel spacePanel = new JPanel();
    JLabel spacePromptLabel = new JLabel("Choose a space :");
    JComboBox<String> spacesCombo = new JComboBox<String>(spaceNames.toArray(new String[0]));
    spacePanel.setLayout(new FlowLayout());
    spacePanel.add(spacePromptLabel);
    spacePanel.add(spacesCombo);

    // 3. create submit button field.
    JButton confirmButton = new JButton("OK");
    List<String> result =  new ArrayList<>();
    confirmButton.addActionListener((ActionEvent e) -> {
      result.add(namePromptTextField.getText());
      result.add(String.valueOf(spacesCombo.getSelectedIndex()));


      // close the dialog box.
      d.setVisible(false);
    });

    // Add components to the dialog
    d.add(name);
    d.add(spacePanel);
    d.add(confirmButton);

    // Add show the dialog
    d.setSize(500, 200);
    d.setLocationRelativeTo(null);
    d.setVisible(true);

    return result;
  }

  @Override
  public void updateMapStatusAndActionResultPanel(Result result) throws IllegalArgumentException {
    if (result == null) {
      throw new IllegalArgumentException("Result cannot be null.\n");
    }
    gamePanel.updateWorldPanel();
    gamePanel.updateActionResultPanel(result.getActionResult());
    gamePanel.updateGameStatusPanel(result.getStatus());
    gamePanel.updateLongTextPanel(result.getEveryTurnSpaceInfo());


  }

  @Override
  public void updateLookAround(Result result) throws IllegalArgumentException {
    if (result == null) {
      throw new IllegalArgumentException("Result cannot be null.\n");
    }
    gamePanel.showLookAroundDialog(frame, result);

  }


  @Override
  public void showPlayerInfo(String playerInfo) throws IllegalArgumentException {
    if (playerInfo == null) {
      throw new IllegalArgumentException("playerInfo cannot be null.\n");
    }
    JOptionPane.showMessageDialog(frame, playerInfo);
  }

  @Override
  public String askUserChooseItem(String msg, List<String> itemsToChooseFrom) {
    if (msg == null || "".equals(msg) || itemsToChooseFrom == null) {
      throw new IllegalArgumentException("msg/itemsToChooseFrom cannot be null.\n");
    }
    String[] itemsArr = itemsToChooseFrom.toArray(new String[0]);
    String optionSelected = (String) JOptionPane.showInputDialog(
        frame,
        msg,
        "Pick Item",
        JOptionPane.PLAIN_MESSAGE,
        null,
        itemsArr,
        itemsArr[0]);


    // get the index
    return optionSelected;
  }

  @Override
  public String askUserChoosePetSpace(List<String> spaceNames) {
    if (spaceNames == null) {
      throw new IllegalArgumentException("spaceNames cannot be null.\n");
    }
    String[] spaceArr = spaceNames.toArray(new String[0]);
    String optionSelected = (String) JOptionPane.showInputDialog(
        frame,
        "Where do you want the pet to move to?",
        "Pick a space",
        JOptionPane.PLAIN_MESSAGE,
        null,
        spaceArr,
        spaceArr[0]);


    return optionSelected;
  }

  @Override
  public int askUserMoveConfirmation(String spaceName) {
    validateString(spaceName);

    // - create the message
    StringBuilder sb = new StringBuilder();
    sb.append("Are you moving to ")
    .append(spaceName).append("?");

    // - show the message and get response
    int response = JOptionPane.showConfirmDialog(null, 
        sb.toString(), "Confirm",
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

    return response;
  }

  @Override
  public void showErrorDialogue(String msg) {
    validateString(msg);
    JOptionPane.showMessageDialog(frame, msg);
  }

  @Override
  public void resetFocus() {
    this.gamePanel.setFocusable(true);
    this.gamePanel.requestFocus();
  }

  @Override
  public void closeWindow() {
    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
  }

  @Override
  public void switchToGameLayout() {
    CardLayout cardLayout = (CardLayout) (mainPanel.getLayout());
    cardLayout.show(mainPanel, "game");
  }

  @Override
  public void switchToWelcomeLayout() {
    CardLayout cardLayout = (CardLayout) (mainPanel.getLayout());
    cardLayout.show(mainPanel, "welcome");
  }

  /* ************************** Helper methods ******************************/

  /**
   * Helper method to validate String. It checks for null and empty string.
   * @param someName to be checked.
   * @throws IllegalArgumentException when someName is not valid.
   */
  private void validateString(String someName) throws IllegalArgumentException {
    if (someName == null || "".equals(someName)) {
      throw new IllegalArgumentException("name arguments cannot be empty");
    } 
  }

  /**
   * Helper method to create menu bar at the top of the screen.
   * @return JmenuBar object.
   */
  private JMenuBar createMenuBar() {
    // menu bar
    JMenuBar menuBar = new JMenuBar();
    menuBar.setFont(new Font("Helvetica", Font.PLAIN, 14));
    menuBar.setBackground(new Color(70, 70, 70));
    menuBar.setOpaque(true);

    // Option menu
    JMenu menu = new JMenu("Option");
    menu.setFont(new Font("Helvetica", Font.TRUETYPE_FONT, 14));

    menu.setForeground(Color.WHITE);
    menu.setBackground(new Color(70, 70, 70));
    menu.setOpaque(true);
    menuBar.add(menu);

    // Option menu items
    final Font menuFont = new Font("Helvetica", Font.TRUETYPE_FONT, 14);
    playCurrentMnItem = new JMenuItem("Current World");
    playCurrentMnItem.setFont(menuFont);
    menu.add(playCurrentMnItem);


    playOtherMnItem = new JMenuItem("Other World");
    playOtherMnItem.setFont(menuFont);
    menu.add(playOtherMnItem);

    quitMnItem = new JMenuItem("Quit");
    quitMnItem.setFont(menuFont);
    menu.add(quitMnItem);
    return menuBar;
  }

  /**
   * Helper methods to set Listeners.
   * @param features to be called from view.
   */
  private void setListeners(Features features) {
    if (features == null) {
      throw new IllegalArgumentException("Features cannot be null.\n");
    }
    // menu items
    // 1. play current
    this.playCurrentMnItem.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        features.resetCurrentGame();
      }
    });

    // 2. play other game
    this.playOtherMnItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        // show file chooser
        JFileChooser fileChooser = new JFileChooser();

        // set filter
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt");
        fileChooser.setFileFilter(filter);


        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);
        int userChoice = fileChooser.showOpenDialog(frame);

        if (userChoice == JFileChooser.APPROVE_OPTION) {
          Readable file = null;
          
          try {
            file = new FileReader(fileChooser.getSelectedFile().getPath());
            features.loadNewGame(file);
          } catch (IllegalStateException | FileNotFoundException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage());
          }
        }
      }
    });

    // 3. quit game
    this.quitMnItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.quitGame();
      }
    });
  }
}
