package View;

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

  // button to clear all existing filters TODO add action listener
  private JButton clearFilters = new JButton("Clear Filters");

  BrowsePanel() {
    JPanel filterPanel = new JPanel(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.HORIZONTAL;

    this.setLayout(new BorderLayout());
    this.add(filterPanel, BorderLayout.NORTH);

    // Filters label
    JLabel filtersLabel = new JLabel("Filters:");
    filtersLabel.setFont(this.font);

    // filter labels
    Font labelFont = new Font(this.font.getFontName(), this.font.getStyle(), this.font.getSize() -2);
    JLabel songLabel = new JLabel("Song:");
    songLabel.setFont(labelFont);
    JLabel artistLabel = new JLabel("Artist:");
    artistLabel.setFont(labelFont);
    JLabel genreLabel = new JLabel("Genre:");
    genreLabel.setFont(labelFont);

    // filter input fields TODO set action listeners
    this.songFilter = new JTextField(10);
    this.artistFilter = new JTextField(10);
    this.genreFilter = new JComboBox<String>();
    // this.updateGenres(); TODO make this method

    // add components to filter panel

    constraints.anchor = GridBagConstraints.NORTHWEST;
    filterPanel.add(filtersLabel, constraints);

    constraints.gridx = 6;
    constraints.gridy = 0;
    filterPanel.add(this.clearFilters, constraints);

    constraints.insets = new Insets(5, 5, 5, 5);

    constraints.gridx = 0;
    constraints.gridy = 2;
    filterPanel.add(songLabel, constraints);
    constraints.gridx++;
    filterPanel.add(this.songFilter, constraints);

    constraints.gridx++;
    filterPanel.add(artistLabel, constraints);
    constraints.gridx++;
    filterPanel.add(this.artistFilter, constraints);

    constraints.gridx++;
    constraints.gridx++;
    filterPanel.add(genreLabel, constraints);
    constraints.gridx++;
    filterPanel.add(this.genreFilter, constraints);

//    constraints.gridwidth = 1;
//    constraints.fill = GridBagConstraints.HORIZONTAL;
//    constraints.anchor = GridBagConstraints.PAGE_END;
//    constraints.gridy++;
//    JLabel chartTitle = new JLabel("U.S. Top 50");
//    chartTitle.setFont(this.font);
//    filterPanel.add(chartTitle, constraints);

    // table

    JLabel chartTitle = new JLabel("U.S. Top 50");
    chartTitle.setFont(this.font);
    this.add(chartTitle, BorderLayout.CENTER);

    String[] columnNames = {"Song", "Artist", "Daily Plays", "Stock Price", "% Change"};
    this.chart = new JTable(new Object[50][5], columnNames);
    this.add(new JScrollPane(this.chart), BorderLayout.SOUTH);
  }
}
