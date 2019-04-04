import java.awt.*;

import javax.swing.*;

public class testing {
  public static void main(String[] args) {

    JFrame f = new JFrame();
    f.setVisible(true);
    f.setSize(500, 500);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel navBar = new JPanel();
    f.add(navBar);

    JButton backButton = new JButton("⬅");
    JButton browseButton = new JButton("Browse");
    JButton portfolioButton = new JButton("Portfolio");
    JButton settingsButton = new JButton("⚙");

    navBar.add(backButton);
    backButton.setEnabled(false);
    navBar.add(browseButton);
    navBar.add(portfolioButton);
    navBar.add(settingsButton);

    JPanel pan1 = new JPanel();
    pan1.setBackground(Color.RED);
    JPanel pan2 = new JPanel();
    pan2.setBackground(Color.ORANGE);
    JPanel pan3 = new JPanel();
    pan3.setBackground(Color.YELLOW);
    JPanel pan4 = new JPanel();
    pan4.setBackground(Color.GREEN);
  }
}
