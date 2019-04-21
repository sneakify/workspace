package code.View;

import code.Model.Model;

/**
 * Launches the application's user interface, given a model.
 */
public interface View {
  static void launchUI(Model model) {
    MainFrame mainFrame = new MainFrame(model);
  }
}