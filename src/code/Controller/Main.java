package code.Controller;

import code.Model.Model;
import code.View.View;

/**
 * This program exercises the DPDatabaseAPI (MySQL implementation).
 * Notice that nothing other than the instantiation of the API shows us that
 * the underlying database is Relational, or MySQL.

 */
public class Main {
  // this is our driver, our main, the one which runs
  public static void main(String[] args) {
    // create instance of model and launch user interface
    Model api = new Model(1);
    View.launchUI(api);
  }
}