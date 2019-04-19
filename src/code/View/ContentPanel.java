package code.View;
import java.awt.*;

import javax.swing.*;

/**
 * TODO
 */
public abstract class ContentPanel extends JPanel {
  protected Font font = new Font("Calisto MT", Font.BOLD, 18); // TODO subject to change
  Font labelFont = new Font(this.font.getFontName(), this.font.getStyle(), this.font.getSize() - 2);

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
