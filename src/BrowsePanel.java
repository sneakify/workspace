import java.awt.*;

import javax.swing.*;

/**
 *
 */
public class BrowsePanel extends ContentPanel {
  // chart to display as table
  private JTable chart;

  // filters by which to search through chart
  private JTextField songFilter;
  private JTextField artistFilter;
  private JComboBox<String> genreFilter;

  // button to clear all existing filters
  private JButton clearFilters;

  BrowsePanel() {
    this.setLayout(new FlowLayout(FlowLayout.LEFT));

    // Filters label
    JLabel filtersLabel = new JLabel("Filters:");
    filtersLabel.setFont(this.font);
    this.add(filtersLabel);

    // filter labels
    Font labelFont = new Font(this.font.getFontName(), this.font.getStyle(), this.font.getSize() -2);
    JLabel songLabel = new JLabel("Song:");
    songLabel.setFont(labelFont);
    JLabel artistLabel = new JLabel("Artist:");
    artistLabel.setFont(labelFont);
    JLabel genreLabel = new JLabel("Genre:");
    genreLabel.setFont(labelFont);

    // filter input fields
    // TODO
  }
}
