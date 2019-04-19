package code.View;

import java.awt.*;

import javax.swing.*;

import code.Model.Song;
import code.Model.User;

/**
 * TODO
 */
abstract class TransactionPanel extends ContentPanel {
  User user;
  Song song;

  TransactionPanel(MainFrame mainFrame, Song song) {
    super(mainFrame);
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
    titleLabel.setFont(this.labelFont);
    // artist
    JLabel artistLabel = new JLabel("Artist: " + this.song.getAlbumID()); // fixme use appropriate method
    artistLabel.setFont(this.labelFont);
    // album
    JLabel albumLabel = new JLabel("Album: " + this.song.getArtistID()); // fixme use appropriate method
    albumLabel.setFont(this.labelFont);
    // current stock price
    JLabel currentStockPriceLabel = new JLabel("Current Stock Price: $" + this.song.getSpotifyID()); // fixme use appropriate method
    currentStockPriceLabel.setFont(this.labelFont);

    // add labels to info panel
    constraints.gridx = 0;
    constraints.gridy = 0;
    infoPanel.add(titleLabel, constraints);
    constraints.gridy++;
    infoPanel.add(artistLabel, constraints);
    constraints.gridy++;
    infoPanel.add(albumLabel, constraints);
    infoPanel.add(currentStockPriceLabel, constraints);
  }
}
