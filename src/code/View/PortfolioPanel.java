package code.View;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import code.Model.Song;

/**
 * Content Panel that allows user to view their available funds, total number of shares owned,
 * and each owned stock. Clicking any one song launches Sell Panel.
 */
class PortfolioPanel extends ContentPanel implements MouseListener {
    // chart to display as table
    private JTable stocks;
  // row of each song title in table
  private HashMap<Integer, Song> rowToSong;

    // user's portfolio
    HashMap<Song, Integer> portfolio;

    private double totalFunds;
    private int totalShares;

    private JLabel totalFundsLabel = new JLabel();
    private JLabel totalSharesLabel = new JLabel();

    /**
     * Constructor. Places available funds and total shares info above table of stocks.
     *
     * @param mainFrame reference to MainFrame used to launch Sell Panel when a song is clicked
     */
    PortfolioPanel(MainFrame mainFrame) {
        super(mainFrame);

        this.portfolio = this.mainFrame.model.user_port(this.mainFrame.user);
        this.setLayout(new BorderLayout());
        this.makeTotalsPanel();

        this.updateTotals();

        // table
        JLabel tableTitle = new JLabel("Your Stocks");
        tableTitle.setFont(this.font);
        this.add(tableTitle, BorderLayout.CENTER);
        String[] columnNames = {"Song", "# Shares", "Daily Plays", "Stock Price", "% Change"}; // TODO % change if time permits (unable to figure it out)

        // custom table model to make cells uneditable to user
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        this.stocks = new JTable(tableModel);
        this.add(new JScrollPane(this.stocks), BorderLayout.SOUTH);

        this.updateTotalLabels();
        this.populateTable(this.portfolio);
    }

    /**
     * Makes sub-panel to display user's total funds available and total number of shares owned.
     */
    private void makeTotalsPanel() {
        // make panel and constraints
        JPanel totalsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        // using wrapper panel to left-justify total labels
        JPanel totalsPanelContainer = new JPanel(new BorderLayout());
        totalsPanelContainer.add(totalsPanel, BorderLayout.WEST);
        this.add(totalsPanelContainer, BorderLayout.NORTH);

        // labels
        // total funds
        JLabel fundsLabel = new JLabel("Total Funds: ");
        fundsLabel.setFont(this.labelFont);
        this.totalFundsLabel.setFont(this.labelFont);
        // total # shares
        JLabel sharesLabel = new JLabel("Total Shares: ");
        sharesLabel.setFont(this.labelFont);
        this.totalSharesLabel.setFont(this.labelFont);

        // add components to totals panel
        // total funds
        constraints.gridx = 0;
        constraints.gridy = 0;
        totalsPanel.add(fundsLabel, constraints);
        constraints.gridx++;
        totalsPanel.add(this.totalFundsLabel, constraints);
        // total # shares
        constraints.gridx = 0;
        constraints.gridy++;
        totalsPanel.add(sharesLabel, constraints);
        constraints.gridx++;
        totalsPanel.add(this.totalSharesLabel, constraints);
    }

    /**
     * Retrieves updated purchasing power and total number of shares for this user from database.
     */
    private void updateTotals() {
        this.totalFunds = this.mainFrame.user.getPurchasing_power();
        this.totalShares = this.calculateTotalShares();
    }

    /**
     * Updates label displaying user's total funds available and total number of shares owned.
     */
    private void updateTotalLabels() {
        this.updateTotals();
        this.totalFundsLabel.setText("$" + this.totalFunds);
        this.totalSharesLabel.setText(this.totalShares + " shares");
    }

    private int calculateTotalShares() {
        int shares = 0;
        for (int num : this.portfolio.values()) {
            shares += num;
        }
        return shares;
    }

  /**
   * Fills this panel's table with the given list of Songs. Song information includes clickable
   * song title (launches Sell Panel), number of shares of this song owned by this user, number of
   * daily plays, stock price, and % change.
   *
   * @param hm map of songs to number of shares owned by this user of each song
   */
    private void populateTable(HashMap<Song, Integer> hm) {
        this.rowToSong = new HashMap<Integer, Song>();
        this.stocks.removeAll();
        ArrayList<Song> songs = new ArrayList<>(hm.keySet());
        for (int row = 0; row < songs.size(); row++) {
            Song song = songs.get(row);
            for (int col = 0; col <= 4; col++) {
                if (col == 0) { // clickable song title
                   this.stocks.setValueAt(song.getTitle(), row, col); //TODO - FIXME arrayoutofboundsexception
                   this.rowToSong.put(row, song);
                } else if (col == 1) { // # shares
                    this.stocks.setValueAt(hm.get(song), row, col);
                } else if (col == 2) { // daily plays
                    this.stocks.setValueAt(song.getSongValue() * 10000, row, col);
                } else if (col == 3) { // stock price
                    this.stocks.setValueAt(song.getSongValue(), row, col);
                } else if (col == 4) { } // % change // TODO % change if time permits (unable to figure it out)
            }
        }
        this.stocks.getColumnModel().getColumn(0).setCellRenderer(new ButtonRenderer());
        this.stocks.addMouseListener(this);
    }

  @Override
  public void mouseClicked(MouseEvent e) {
    JTable source = (JTable) e.getSource();
    int row = source.getSelectedRow();
    int col = source.getSelectedColumn();
    if (col == 0) {
      // clicked a song -> launch a Buy Panel for this song
      this.mainFrame.launchSellPanel(this.rowToSong.get(row));
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }
}
