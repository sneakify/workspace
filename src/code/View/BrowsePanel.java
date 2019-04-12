package code.View;
import java.awt.*;

import javax.swing.*;
    /**
     * Content Panel that allows user to browse through and filter U.S. Top 50 chart. Clicking any one
     * song launches Buy Panel.
     */
    public static class BrowsePanel extends ContentPanel {
      // chart to display as table
      private JTable chart;

      // filters by which to search through chart
      private JTextField songFilter;
      private JTextField artistFilter;
      private JComboBox<String> genreFilter;

      // button to clear all existing filters TODO add action listener
      private JButton clearFilters = new JButton("Clear Filters");

      /**
       * Constructor. Places filtering options above U.S. Top 50 chart and updates list of selectable
       * genres based on current top 50.
       */
      BrowsePanel() {

        this.setLayout(new BorderLayout());
        this.makeFilterPanel();

        // table TODO fill & set action listeners for Song column; selection mode?
        JLabel chartTitle = new JLabel("U.S. Top 50");
        chartTitle.setFont(this.font);
        this.add(chartTitle, BorderLayout.CENTER);
        String[] columnNames = {"Song", "Artist", "Daily Plays", "Stock Price", "% Change"};

        // custom table model
        DefaultTableModel tableModel = new DefaultTableModel(new Object[50][5], columnNames) {
          @Override
          public boolean isCellEditable(int row, int column) {
            return false;
          }
        };

        this.chart = new JTable(tableModel);
        this.add(new JScrollPane(this.chart), BorderLayout.SOUTH);

        // this.updateGenres(); TODO make this method

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
    }