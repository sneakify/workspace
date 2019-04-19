package code.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import code.Model.Song;

/**
 * Content Panel that allows user to browse through and filter U.S. Top 50 chart. Clicking any one
 * song launches Buy Panel.
 */
class BrowsePanel extends ContentPanel implements ActionListener {
  // chart to display as table
  private JTable chart;
  // buttons for each song
  private HashMap<JButton, Song> songButtons;

  // list of songs
  ArrayList<Song> songs;

  // filters by which to search through chart
  private JTextField songFilter;
  private JTextField artistFilter;
  private JComboBox<String> genreFilter;

  // button to clear all existing filters
  private JButton clearFilters = new JButton("Clear Filters");

  /**
   * Constructor. Places filtering options above U.S. Top 50 chart and updates list of selectable
   * genres based on current top 50.
   */
  BrowsePanel(MainFrame mainFrame) {
    super(mainFrame);
    this.songs = this.dbUtils.all_songs();
    this.setLayout(new BorderLayout());
    this.makeFilterPanel();

    // table
    JLabel chartTitle = new JLabel("U.S. Top 50");
    chartTitle.setFont(this.font);
    this.add(chartTitle, BorderLayout.CENTER);
    String[] columnNames = {"Song", "Artist", "Daily Plays", "Stock Price", "% Change"}; // TODO remove % change

    // custom table model
    DefaultTableModel tableModel = new DefaultTableModel(new Object[50][5], columnNames) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    this.chart = new JTable(tableModel);
    this.add(new JScrollPane(this.chart), BorderLayout.SOUTH);

    this.populateChart(this.songs);
    this.updateGenres();

  }

  /**
   * Makes sub-panel for filtering fields and places it at the top of this panel. Sub-panel includes
   * button to clear all filters and input fields to filter by song, artist, and/or genre.
   */
  private void makeFilterPanel() {
    // make panel and constraints
    JPanel filterPanel = new JPanel(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.HORIZONTAL;
    this.add(filterPanel, BorderLayout.NORTH);

    // Filters label
    JLabel filtersLabel = new JLabel("Filters:");
    filtersLabel.setFont(this.font);

    // filter labels
    JLabel songLabel = new JLabel("Song:");
    songLabel.setFont(this.labelFont);
    JLabel artistLabel = new JLabel("Artist:");
    artistLabel.setFont(this.labelFont);
    JLabel genreLabel = new JLabel("Genre:");
    genreLabel.setFont(this.labelFont);

    // filter input fields
    this.songFilter = new JTextField(10);
    this.artistFilter = new JTextField(10);
    this.genreFilter = new JComboBox<String>();

    // set action listeners
    this.songFilter.addActionListener(this);
    this.artistFilter.addActionListener(this);
    this.genreFilter.addActionListener(this);
    this.clearFilters.addActionListener(this);

    // add components to filter panel
    // add Filters label
    constraints.anchor = GridBagConstraints.NORTHWEST;
    filterPanel.add(filtersLabel, constraints);
    // add Clear Filters button
    constraints.gridx = 6;
    constraints.gridy = 0;
    filterPanel.add(this.clearFilters, constraints);
    // add input fields & labels
    // add Song filter label & textfield
    constraints.insets = new Insets(5, 5, 5, 5);
    constraints.gridx = 0;
    constraints.gridy = 2;
    filterPanel.add(songLabel, constraints);
    constraints.gridx++;
    filterPanel.add(this.songFilter, constraints);
    // add Artist filter label & textfield
    constraints.gridx++;
    filterPanel.add(artistLabel, constraints);
    constraints.gridx++;
    filterPanel.add(this.artistFilter, constraints);
    // add Genre filter label & textfield
    constraints.gridx++;
    constraints.gridx++;
    filterPanel.add(genreLabel, constraints);
    constraints.gridx++;
    filterPanel.add(this.genreFilter, constraints);
  }

  private void populateChart(ArrayList<Song> list) {
    this.chart.removeAll();
    for (int row = 0; row < list.size(); row++) {
      Song song = list.get(row);
      for (int col = 0; col <= 4; col++) {
        if (col == 0) { // clickable song title
          JButton button = new JButton(song.getTitle());
          button.addActionListener(this);
          this.chart.setValueAt(button, row, col);
          this.songButtons.put(button, song);
        } else if (col == 1) { // artist
          this.chart.setValueAt(dbUtils.song_artist(song), row, col);
        } else if (col == 2) { // daily plays
          this.chart.setValueAt(song.getSongValue() * 10000, row, col);
        } else if (col == 3) { // stock price
          this.chart.setValueAt(song.getSongValue(), row, col);
        } else if (col == 4) { } // % change // TODO if we have time
      }
    }
  }

  private void updateGenres() {
    this.genreFilter.removeAllItems();
    this.genreFilter.addItem("Any Genre");
    ArrayList<String> genres = new ArrayList<String>();

    for (Song song: this.songs) {
      String genre = dbUtils.song_genre(song);
      if (!genres.contains(genre)) {
        genres.add(genre);
        this.genreFilter.addItem(genre);
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == this.songFilter
            || e.getSource() == this.artistFilter
            || e.getSource() == this.genreFilter) {
      // filter
      String s = this.songFilter.getText();
      String a = this.artistFilter.getText();
      String g = this.genreFilter.getSelectedItem().toString(); // fixme possible bug caused by toString
      this.populateChart(this.filter(s, a, g));
    } else if (e.getSource() == this.clearFilters) {
      // clear filters
      this.songFilter.setText("");
      this.artistFilter.setText("");
      this.genreFilter.setSelectedIndex(0);
    } else if (this.songButtons.containsKey(e.getSource())) {
      this.mainFrame.launchBuyPanel(this.songButtons.get(e.getSource()));
    }
  }

  private ArrayList<Song> filter(String s, String a, String g) {
    ArrayList<Song> filtered = new ArrayList<Song>();
    for (Song song : this.songs) {
      if (song.getTitle().contains(s)
              && dbUtils.song_artist(song).contains(a)
              && dbUtils.song_genre(song).equals(g)) {
        filtered.add(song);
      }
    }
    return filtered;
  }
}