package code.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import code.Model.Song;

/**
 * Content Panel that allows user to view their available funds, total number of shares owned,
 * and each owned stock. Clicking any one song launches Sell Panel.
 */
class PortfolioPanel extends ContentPanel implements ActionListener {
    // chart to display as table
    private JTable stocks;
    // buttons for each song
    private HashMap<JButton, Song> songButtons;

    // user's portfolio
    HashMap<Song, Integer> portfolio;

    private double totalFunds;
    private int totalShares;

    private JLabel totalFundsLabel = new JLabel();
    private JLabel totalSharesLabel = new JLabel();

    /**
     * TODO
     */
    PortfolioPanel(MainFrame mainFrame) {
        super(mainFrame);

        this.portfolio = dbUtils.user_port(this.mainFrame.user);
        this.setLayout(new BorderLayout());
        this.makeTotalsPanel();

        this.updateTotals();

        // table
        JLabel tableTitle = new JLabel("Your Stocks");
        tableTitle.setFont(this.font);
        this.add(tableTitle, BorderLayout.CENTER);
        String[] columnNames = {"Song", "# Shares", "Daily Plays", "Stock Price", "% Change"};

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

    private void updateTotals() {
        this.totalFunds = this.mainFrame.user.getPurchasing_power();
        this.totalShares = this.calculateTotalShares();
    }

    /**
     * Updates label displaying user's total funds available and total number of shares owned.
     */
    private void updateTotalLabels() {
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

    private void populateTable(HashMap<Song, Integer> hm) {
        this.songButtons = new HashMap<JButton, Song>();
        this.stocks.removeAll();
        ArrayList<Song> songs = new ArrayList<>(hm.keySet());
        for (int row = 0; row < songs.size(); row++) {
            Song song = songs.get(row);
            for (int col = 0; col <= 4; col++) {
                if (col == 0) { // clickable song title
                    JButton button = new JButton(song.getTitle());
                    button.addActionListener(this);
                    this.stocks.setValueAt(button, row, col);
                    this.songButtons.put(button, song);
                } else if (col == 1) { // # shares
                    this.stocks.setValueAt(hm.get(song), row, col);
                } else if (col == 2) { // daily plays
                    this.stocks.setValueAt(song.getSongValue() * 10000, row, col);
                } else if (col == 3) { // stock price
                    this.stocks.setValueAt(song.getSongValue(), row, col);
                } else if (col == 4) { } // % change // TODO if we have time
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.songButtons.containsKey(e.getSource())) {
            this.mainFrame.launchSellPanel(this.songButtons.get(e.getSource()));
        }
    }
}
