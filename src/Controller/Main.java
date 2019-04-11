package Controller;

import Model.MyModel;
import Model.ModelSQLInterface;
import View.MainFrame;

public class Main {
  public static void main(String[] args) {
    System.out.println("The Program Started Running"); // (i.e. show the on button worked)

    ModelSQLInterface mod = new MyModel();






    MainFrame mf = new MainFrame();
  }
}
