package world.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import world.controller.Features;

/**
 * The panel on the right hand side that allows user to add CPU and human players.
 */
public final class AddPlayerPanel extends JPanel {

  /**
   * serial ID for this panel.
   */
  private static final long serialVersionUID;
  private JButton humanAddButton;
  private JButton cpuAddButton;
  private JButton startGameButton;
  
  static {
    serialVersionUID = 6593644679721308925L;

  }
  
  /**
   * Constructors that set the background and size of this panel.
   * @throws IllegalArgumentException if color is null
   */
  public AddPlayerPanel() throws IllegalArgumentException {

    // set the panel color
    this.setBackground(WorldColor.BLACK.getColor());
    this.setLayout(new GridLayout(3, 1, 100, 100));

    // buttons
    this.startGameButton =
        createJbutton(WorldColor.WHITE.getColor(),
        WorldColor.ORANGE.getColor(),
        "Start Game", new Dimension(286, 92));
    this.startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    this.humanAddButton = 
        createJbutton(WorldColor.BROWN.getColor(),
        WorldColor.ADDBUTTONBACK.getColor(),
        "Add Human Player", new Dimension(286, 92));

    this.cpuAddButton = 
        createJbutton(WorldColor.BROWN.getColor(),
        WorldColor.ADDBUTTONBACK.getColor(),
        "Add CPU Player", new Dimension(286, 92));

    this.add(humanAddButton);
    this.add(cpuAddButton);
    this.add(startGameButton);
  }

  /**
   * Set listeners to add players and start game buttons.
   * @param f the features
   * @throws IllegalArgumentException if feature is null
   */
  public void setListeners(Features f) throws IllegalArgumentException {
    if (f == null) {
      throw new IllegalArgumentException("features cannot be null when setting listeners");
    }
    
    humanAddButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        f.addHumanPlayer();
      }
    });
    
    cpuAddButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        f.addCpuPlayer();
      }
    });
    
    startGameButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        f.startGame();
      }
    });
    
  }
  
  /* ******************Helper Methods ******************/

  /**
   * Helper method to create JButtons.
   * @param fore color of foreground.
   * @param back color of Background.
   * @param name name of the element.
   * @param dimension dimension of the Button.
   * @return JButton component.
   */
  private JButton createJbutton(Color fore, Color back, String name, Dimension dimension) {
    if (fore == null || back == null
            || (name == null || "".equals(name))
            || dimension == null) {
      throw new IllegalArgumentException(
              new StringBuilder("features/colors/name/dimensions cannot be ")
                      .append("null when setting listeners")
                      .toString());
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
