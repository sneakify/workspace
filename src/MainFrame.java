import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * TODO
 */
public class MainFrame extends JFrame implements ActionListener {//implements View { //fixme commented out to test main
  // Navigation Panel & Buttons
  private JPanel navigationPanel;
  private JButton backButton = new JButton("⬅");
  private JButton browseButton = new JButton("Browse");
  private JButton portfolioButton = new JButton("Portfolio");
  private JButton settingsButton = new JButton("⚙");

  // Content Panels
  private JPanel mainPanel;
  protected BrowsePanel browsePanel;
  protected PortfolioPanel portfolioPanel;
  protected BuyPanel buyPanel;
  protected SellPanel sellPanel;
  protected SettingsPanel settingsPanel;

  private final static String BROWSE = "BROWSE";
  private final static String PORTFOLIO = "PORTFOLIO";
  private final static String BUY = "BUY";
  private final static String SELL = "SELL";
  private final static String SETTINGS = "SETTINGS";

  /**
   * TODO
   */
  public MainFrame() {

    // frame
    this.setVisible(true);
    this.setSize(500, 500); // TODO
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // navigation bar
    this.makeNavigationBar(); // TODO
    this.add(this.navigationPanel, BorderLayout.NORTH); // TODO may need to fix alignment

    // content panels
    this.mainPanel = new JPanel(new CardLayout());
    this.browsePanel = new BrowsePanel();
    this.portfolioPanel = new PortfolioPanel();
    this.buyPanel = new BuyPanel();
    this.sellPanel = new SellPanel();
    this.settingsPanel = new SettingsPanel();
    // add content panels to main panel
    this.mainPanel.add(this.browsePanel, BROWSE);
    this.mainPanel.add(this.portfolioPanel, PORTFOLIO);
    this.mainPanel.add(this.buyPanel, BUY);
    this.mainPanel.add(this.sellPanel, SELL);
    this.mainPanel.add(this.settingsPanel, SETTINGS);


    // TODO remove
    this.browsePanel.setBackground(Color.BLUE);
    this.portfolioPanel.setBackground(Color.CYAN);
    this.settingsPanel.setBackground(Color.GREEN);

    this.mainPanel.setSize(400, 500);
    this.add(this.mainPanel);

  }

  /**
   * TODO
   */
  private void makeNavigationBar() {
    // initialize navigation panel
    this.navigationPanel = new JPanel();

    // add buttons
    navigationPanel.add(this.backButton);
    navigationPanel.add(this.browseButton);
    navigationPanel.add(this.portfolioButton);
    navigationPanel.add(this.settingsButton);

    // add action listeners
    this.backButton.addActionListener(this);
    this.browseButton.addActionListener(this);
    this.portfolioButton.addActionListener(this);
    this.settingsButton.addActionListener(this);

    this.backButton.setEnabled(false); // TODO may abstract
  }

  /**
   * TODO
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    CardLayout cl = (CardLayout) this.mainPanel.getLayout();

    if (e.getSource() == this.backButton) {
      /* TODO
      - if in BUY, go to BROWSE
      - if in SELL, go to PORTFOLIO
      - if in SETTINGS, ???
       */
    } else if (e.getSource() == this.browseButton) {
      cl.show(this.mainPanel, BROWSE);
    } else if (e.getSource() == this.portfolioButton) {
      cl.show(this.mainPanel, PORTFOLIO);
    } else if (e.getSource() == this.settingsButton) {
      cl.show(this.mainPanel, SETTINGS);
    }
  }
}
