package code.View;
import java.awt.*;

import javax.swing.*;

import code.Model.DBUtils;
import code.Model.Login;

/**
 * TODO
 */
public abstract class ContentPanel extends JPanel {
  protected Font font = new Font("Calisto MT", Font.BOLD, 18); // TODO subject to change
  Font labelFont = new Font(this.font.getFontName(), this.font.getStyle(), this.font.getSize() - 2);

  DBUtils dbUtils = new DBUtils(Login.url, Login.usr, Login.pword); // TODO input correct url

  MainFrame mainFrame;

  ContentPanel(MainFrame mainFrame) {
    this.mainFrame = mainFrame;
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
