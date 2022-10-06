package world.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import world.controller.Features;

/**
 * 
 * The panel that shows a welcome screen.
 * It will allow user to start a new game with the default world specification
 * or play with new specficiation using the menu in the menu bar
 *
 */
public final class WelcomePanel extends JPanel {

  /**
   * the final serial id for this panel.
   */
  private static final long serialVersionUID;

  private JLabel welcomeLabel;
  private JLabel creditLabel;
  private final JButton playButton;

  static {
    serialVersionUID = 168654213178270777L;
  }
  
  /**
   * Constructor that initilizes the components on these panels.
   */
  public WelcomePanel() {
    this.setBackground(Color.BLACK);
    BorderLayout bl = new BorderLayout();
    bl.setHgap(20);
    bl.setVgap(20);
    this.setLayout(bl);

    // 1. initialize
    // buttons
    this.playButton =
        createJbutton(
                Color.WHITE,
                WorldColor.ORANGE.getColor(), "Play Game",
                new Dimension(250, 250));

    JPanel jp = new JPanel(new GridBagLayout());
    jp.setBackground(WorldColor.BLACK.getColor());

    GridBagConstraints c = new GridBagConstraints();
    c.weightx = 0.0;
    c.gridwidth = 5;
    c.gridx = 1;
    c.gridy = 1;
    jp.add(this.playButton, c);


    welcomeLabel = new JLabel();
    this.setBackground(WorldColor.BLACK.getColor());

    // labels
    welcomeLabel = new JLabel();
    creditLabel = new JLabel("Credits: Annanya and Sherly", SwingConstants.RIGHT);
    creditLabel.setForeground(Color.WHITE);
    creditLabel.setFont(new Font("Helvetica", Font.PLAIN, 25));

    // 2. set layout
    this.setLayout(new BorderLayout());
    this.add(jp, BorderLayout.CENTER);
    this.add(creditLabel, BorderLayout.SOUTH);
    this.add(welcomeLabel, BorderLayout.NORTH);
    
  }
  
  /**
   * Set listeners to buttons.
   * @param features used of the controller.
   * @throws IllegalArgumentException if feature is null
   */
  public void setListeners(Features features) throws IllegalArgumentException {
    if (features == null) {
      throw new IllegalArgumentException("features cannot be null when setting listeners");
    }
    
    // buttons 
    playButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.playGameMode();
      }
    });
  }

  /**
   * Reset the welcome string to this new world name.
   * @param worldName the new world name
   */
  public void setWelcomeLabel(String worldName) {
    if (worldName == null || "".equals(worldName)) {
      throw new IllegalArgumentException("WorldName cannot be null.\n");
    }
    StringBuilder intro = new StringBuilder();
    intro.append("Welcome to ").append(worldName);
    this.welcomeLabel.setText(intro.toString());
    this.welcomeLabel.setForeground(WorldColor.RED.getColor());
    this.welcomeLabel.setFont(new Font("Rockwell", Font.BOLD, 58));
    this.welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
    this.welcomeLabel.setPreferredSize(new Dimension(200, 100));

  }

  /* ****** Helper Methods *****************/

  /**
   * This Helper method creates a JButton of specified details.
   * @param fore color of button's foreground.
   * @param back color of button's background.
   * @param name label for the button.
   * @param dimension of the button.
   * @return created button.
   */
  private JButton createJbutton(Color fore, Color back, String name, Dimension dimension) {
    if (fore == null || back == null
        || (name == null || "".equals(name))
            || dimension == null) {
      throw new IllegalArgumentException("color,name or dimensions cannot be null.\n");
    }
    JButton button = new JButton(name);
    button.setFont(new Font("Rockwell", Font.BOLD, 23));
    button.setForeground(fore);
    button.setBackground(back);
    button.setPreferredSize(dimension);
    button.setOpaque(true);
    button.setBorderPainted(false);
    return button;
  }
}
