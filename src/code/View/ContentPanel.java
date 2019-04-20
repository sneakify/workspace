package code.View;
import java.awt.*;

import javax.swing.*;

import code.Model.DBUtils;
import code.Model.Login;

/**
 * Represents a panel that will be placed below the navigation bar.
 */
public abstract class ContentPanel extends JPanel {
  // fonts
  protected Font font = new Font("Calisto MT", Font.BOLD, 18);
  Font labelFont = new Font(this.font.getFontName(), this.font.getStyle(), this.font.getSize() - 2);

  // database utilities
  DBUtils dbUtils;

  // reference to main frame used to launch buy/sell panels as needed
  MainFrame mainFrame;

  /**
   * Constructor. Initializes this.mainFrame with given MainFrame.
   *
   * @param mainFrame reference to MainFrame used to launch buy/sell panels
   */
  ContentPanel(MainFrame mainFrame) {
    this.mainFrame = mainFrame;
    this.dbUtils = this.mainFrame.dbUtils;
  }


  /**
   * Displays popup window with given error message and window title.
   *
   * @param message error message to display
   * @param title   window title to set
   */
  void showErrorPopup(String message, String title) {
    JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
  }
}
