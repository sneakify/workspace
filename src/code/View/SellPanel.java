package code.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.*;

import code.Model.Song;
import code.Model.User;

/**
 * TODO
 */
class SellPanel extends TransactionPanel implements ActionListener {
    JTextField sharesToSell = new JTextField();
    JButton sellButton = new JButton("Sell");

    SellPanel(MainFrame mainFrame, Song song) {
        super(mainFrame, song);

        JPanel shareInfoPanel = new JPanel(new BorderLayout());

        HashMap<Song, Integer> portfolio = dbUtils.user_port(this.user);
        int sharesOwned = portfolio.get(this.song);
        JLabel numShares = new JLabel("# Shares: " + sharesOwned + " shares");
        numShares.setFont(this.labelFont);
        shareInfoPanel.add(numShares, BorderLayout.WEST);
        this.add(shareInfoPanel);

        this.makeSellSubPanel();
    }

    private void makeSellSubPanel() {
        // make panel and constraints
        JPanel sellSubPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        // using wrapper panel to left-justify
        JPanel sellSubPanelContainer = new JPanel(new BorderLayout());
        sellSubPanelContainer.add(sellSubPanel, BorderLayout.WEST);
        this.add(sellSubPanelContainer, BorderLayout.SOUTH);

        JLabel sellLabel = new JLabel("Sell");
        sellLabel.setFont(this.labelFont);

        this.sharesToSell.setText("# shares");

        int sharesToSellInt = this.parseTextField();

        double earnings = (double) sharesToSellInt * this.song.getSongValue();
        JLabel earningsLabel = new JLabel("Total Earnings: $" + earnings);
        earningsLabel.setFont(this.labelFont);

        this.sellButton.addActionListener(this);

        constraints.gridx = 0;
        constraints.gridy = 0;
        sellSubPanel.add(sellLabel, constraints);
        constraints.gridy++;
        sellSubPanel.add(this.sharesToSell, constraints);
        constraints.gridx++;
        sellSubPanel.add(earningsLabel, constraints);
        constraints.gridx++;
        sellSubPanel.add(this.sellButton, constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.sellButton) {
            try {
                dbUtils.sell_shares(this.user, this.song, this.parseTextField());
            } catch (Exception ex) {
                this.showErrorPopup(ex.getMessage(), "ERROR: Could Not Sell Shares");
            }
        }
    }

    private int parseTextField() {
        int i = 0;
        try {
            i = Integer.parseInt(this.sharesToSell.getText());
        } catch (NumberFormatException e) { }
        return 0;
    }
}
