package View;

/**
 * TODO
 */
public interface View {
  /**
   * Once a view is constructed, this method is called to make the view visible to the user.
   */
  void makeVisible();

  /**
   * Repaints the last updated frame.
   */
  void refresh();


}