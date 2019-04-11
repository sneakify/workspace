package code.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Overall frame that contains a navigation panel and displays the appropriate content panel.
 */
public class MainFrame extends JFrame implements ActionListener, View {//implements java.View.java.View { //fixme commented out to test main
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

  // Current Content Panel being displayed
  private ContentPanel curPanel;

  private final static String BROWSE = "BROWSE";
  private final static String PORTFOLIO = "PORTFOLIO";
  private final static String BUY = "BUY";
  private final static String SELL = "SELL";
  private final static String SETTINGS = "SETTINGS";

  /**
   * Constructor. Instantiates navigation panel and content panels. Adds panels to this frame.
   */
  public MainFrame() {

    // frame
    this.setVisible(true);
    this.setSize(600, 600); // TODO may want to abstract
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    // navigation bar
    this.makeNavigationBar();
    this.add(this.navigationPanel, BorderLayout.NORTH); // TODO may need to fix alignment

    // container for content panels
    this.mainPanel = new JPanel(new CardLayout());
    this.mainPanel.setSize(300, 400);
    this.mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    this.add(this.mainPanel, BorderLayout.CENTER);

    // content panels
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

    // current panel upon construction should be Browse Panel
    this.curPanel = this.browsePanel;


    // TODO remove. testing purposes only
    this.portfolioPanel.setBackground(Color.CYAN);
    this.settingsPanel.setBackground(Color.GREEN);

  }

  ///////////////////////////////////////////////////////
  // TODO - STUBS

  @Override
  public void makeVisible() {
    System.out.println("A STUB method was called: makeVisible on: " + this);
    // TODO - STUB
  }

  @Override
  public void refresh() {
    System.out.println("A STUB method was called: refresh on: " + this);
    // TODO - STUB
  }

  /////////////////////////////////////////////////////////////

  /**
   * Initializes navigation panel and adds 4 buttons: back, browse, portfolio, settings. Adds
   * listener for each button. Sets visibility of back button to false by default.
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

    // clicked Back button
    if (e.getSource() == this.backButton) {
      /*
      - if in BUY, go to BROWSE
      - if in SELL, go to PORTFOLIO
      - if in SETTINGS, BROWSE TODO change to something other than BROWSE?
       */
      if (this.curPanel == this.buyPanel || this.curPanel == this.settingsPanel) {
        cl.show(this.mainPanel, this.BROWSE);
        this.curPanel = this.browsePanel;
      } else if (this.curPanel == this.sellPanel) {
        cl.show(this.mainPanel, this.PORTFOLIO);
        this.curPanel = this.portfolioPanel;
      }

      // clicked Browse button
    } else if (e.getSource() == this.browseButton) {
      cl.show(this.mainPanel, this.BROWSE);
      this.curPanel = this.browsePanel;

      // clicked Portfolio button
    } else if (e.getSource() == this.portfolioButton) {
      cl.show(this.mainPanel, this.PORTFOLIO);
      this.curPanel = this.portfolioPanel;

      // clicked Settings button
    } else if (e.getSource() == this.settingsButton) {
      cl.show(this.mainPanel, this.SETTINGS);
      this.curPanel = this.settingsPanel;
    }

    // enable or disable Back button depending on current content panel
    this.setBackButton();
  }

  /**
   * Determines whether to enable Back button. Only enables button for Buy, Sell, & Settings panels.
   */
  private void setBackButton() {
    if (this.curPanel == this.buyPanel
            || this.curPanel == this.sellPanel
            || this.curPanel == this.settingsPanel) {
      this.backButton.setEnabled(true);
    } else {
      this.backButton.setEnabled(false);
    }
  }

}
