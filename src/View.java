/**
 * TODO
 */
public interface View {
  /**
   * Onve a view is constructed, this method is called to make the view visible to the user.
   */
  void makeVisible();

  /**
   * Repaints the last updated frame.
   */
  void refresh();
}