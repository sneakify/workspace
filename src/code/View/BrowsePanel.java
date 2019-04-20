package code.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import code.Model.Song;

/**
 * Content Panel that allows user to browse through and filter U.S. Top 50 chart. Clicking any one
 * song launches a Buy Panel.
 */
class BrowsePanel extends ContentPanel implements ActionListener, MouseListener {
  // chart to display as table
  private JTable chart;
  // row of each song title in table
  private HashMap<Integer, Song> rowToSong;

  // list of all songs currently in U.S. Top 50
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
   *
   * @param mainFrame referene to MainFrame used to launch Buy Panel when a song is clicked
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
    String[] columnNames = {"Song", "Artist", "Daily Plays", "Stock Price", "% Change"}; // TODO % change if time permits (unable to figure it out)

    // custom table model to make cells uneditable to user
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

  /**
   * Fills this panel's table with the given list of Songs. Song information includes clickable
   * song title (launches Buy Panel), artist name, number of daily plays, stock price, and % change.
   *
   * @param list list of songs to display
   */
  private void populateChart(ArrayList<Song> list) {
    this.rowToSong = new HashMap<Integer, Song>();
    this.chart.removeAll();
    for (int row = 0; row < list.size(); row++) {
      Song song = list.get(row);
      for (int col = 0; col <= 4; col++) {
        if (col == 0) { // clickable song title
          this.chart.setValueAt(song.getTitle(), row, col);
          this.rowToSong.put(row, song);
        } else if (col == 1) { // artist
          this.chart.setValueAt(dbUtils.song_artist(song), row, col);
        } else if (col == 2) { // daily plays
          this.chart.setValueAt(song.getSongValue() * 10000, row, col);
        } else if (col == 3) { // stock price
          this.chart.setValueAt(song.getSongValue(), row, col);
        } else if (col == 4) { } // % change // TODO % change if time permits (unable to figure it out)
      }
    }
    this.chart.getColumnModel().getColumn(0).setCellRenderer(new ButtonRenderer());
    this.chart.addMouseListener(this);
  }

  /**
   * Clears and refills genre options in list for dropdown genre filter. Traverses U.S. Top 50 to
   * accumulate distinct genres to display.
   */
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

  /**
   * Filters list of any of three filter fields is updated. Clears all filters if "Clear Filters"
   * button is clicked. Launches Buy Panel through reference to MainFrame if a song is clicked.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == this.songFilter
            || e.getSource() == this.artistFilter
            || e.getSource() == this.genreFilter) {
      // filter by song, artist, and/or genre
      String s = this.songFilter.getText();
      String a = this.artistFilter.getText();
      String g = (String) this.genreFilter.getSelectedItem(); // fixme possible bug caused by casting
      this.populateChart(this.filter(s, a, g));
    } else if (e.getSource() == this.clearFilters) {
      // clear all filters
      this.songFilter.setText("");
      this.artistFilter.setText("");
      this.genreFilter.setSelectedIndex(0);
    }
  }

  /**
   * Given filters for song title, artist name, amd genre, traverses current U.S. Top 50 char to
   * find subset of songs that satisfy all parameters.
   *
   * @param s substring to find in song title
   * @param a substring to find in artist name
   * @param g genre to match
   * @return list of songs filtered by given paramaters
   */
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

  @Override
  public void mouseClicked(MouseEvent e) {
    JTable source = (JTable) e.getSource();
    int row = source.getSelectedRow();
    int col = source.getSelectedColumn();
    if (col == 0) {
      // clicked a song -> launch a Buy Panel for this song
      this.mainFrame.launchBuyPanel(this.rowToSong.get(row));
    }
  }

  @Override
  public void mousePressed(MouseEvent e) { }

  @Override
  public void mouseReleased(MouseEvent e) { }

  @Override
  public void mouseEntered(MouseEvent e) { }

  @Override
  public void mouseExited(MouseEvent e) { }
}