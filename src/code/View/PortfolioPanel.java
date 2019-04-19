package code.View;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import code.Model.User;

/**
 * Content Panel that allows user to view their available funds, total number of shares owned,
 * and each owned stock. Clicking any one song launches Sell Panel.
 */
class PortfolioPanel extends ContentPanel {
    User user;

    // chart to display as table
    private JTable stocks;
    private double totalFunds = 0;
    private int totalShares = 0;

    private JLabel totalFundsLabel = new JLabel();
    private JLabel totalSharesLabel = new JLabel();

    /**
     * TODO
     */
    PortfolioPanel(MainFrame mainFrame, User user) {
        super(mainFrame);

        this.user = user;
        this.setLayout(new BorderLayout());
        this.makeTotalsPanel();

        // table TODO fill & set action listeners for Song column; selection mode?
        JLabel tableTitle = new JLabel("Your Stocks");
        tableTitle.setFont(this.font);
        this.add(tableTitle, BorderLayout.CENTER);
        String[] columnNames = {"Song", "# Shares", "Daily Plays", "Stock Price", "% Change"};

        // custom table model
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        this.stocks = new JTable(tableModel);
        this.add(new JScrollPane(this.stocks), BorderLayout.SOUTH);

        this.updateTotalLabels();
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
     * Updates label displaying user's total funds available and total number of shares owned.
     */
    private void updateTotalLabels() {
        this.totalFundsLabel.setText("$" + this.totalFunds);
        this.totalSharesLabel.setText(this.totalShares + " shares");
    }
}
