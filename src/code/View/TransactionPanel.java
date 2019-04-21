package code.View;

import java.awt.*;

import javax.swing.*;

import code.Model.Song;
import code.Model.User;

/**
 * Represents a panel that allows the user to make a transaction (either buy or sell shares)
 */
abstract class TransactionPanel extends ContentPanel {
  User user;
  Song song;

  /**
   * Constructor. Initializes this.mainFrame with given MainFrame.
   *
   * @param mainFrame reference to MainFrame used to launch buy/sell panels
   * @param song song to be purchased/sold
   */
  TransactionPanel(MainFrame mainFrame, Song song) {
    super(mainFrame);
    this.setLayout(new BorderLayout());
    this.user = this.mainFrame.user;
    this.song = song;

    this.makeInfoPanel();
  }

  /**
   * Sub-panel containing information about song associated with this transaction, including title,
   * artist, album, and current stock price.
   */
  private void makeInfoPanel() {
    // make panel and constraints
    JPanel infoPanel = new JPanel(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.HORIZONTAL;
    // using wrapper panel to left-justify info labels
    JPanel infoPanelContainer = new JPanel(new BorderLayout());
    infoPanelContainer.add(infoPanel, BorderLayout.WEST);
    this.add(infoPanelContainer, BorderLayout.NORTH);

    // labels
    // title
    JLabel titleLabel = new JLabel("Title: " + this.song.getTitle());
    titleLabel.setFont(this.font);
    // artist
    JLabel artistLabel = new JLabel("Artist: " + this.mainFrame.model.song_artist(this.song));
    artistLabel.setFont(this.font);
    // album
    JLabel albumLabel = new JLabel("Album: " + this.mainFrame.model.song_album(this.song));
    albumLabel.setFont(this.font);
    // current stock price
    JLabel currentStockPriceLabel = new JLabel("Current Stock Price: $" + this.song.getSongValue());
    currentStockPriceLabel.setFont(this.font);

    // add labels to info panel
    constraints.gridx = 0;
    constraints.gridy = 0;
    infoPanel.add(titleLabel, constraints);
    constraints.gridy++;
    infoPanel.add(artistLabel, constraints);
    constraints.gridy++;
    infoPanel.add(albumLabel, constraints);
    constraints.gridy++;
    infoPanel.add(currentStockPriceLabel, constraints);
  }
}
