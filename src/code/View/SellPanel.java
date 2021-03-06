package code.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.*;

import code.Model.Song;

/**
 * Panel that allows user to sell some number of shares of  song. Launched from Portfolio Panel.
 */
class SellPanel extends TransactionPanel implements ActionListener {
    JTextField sharesToSell = new JTextField();
    JLabel earningsLabel;
    JButton sellButton = new JButton("Sell");

    /**
     * Constructor. In addition to song information, displays number of shares owned of this.song.
     *
     * @param mainFrame reference to MainFrame
     * @param song song to be sold
     */
    SellPanel(MainFrame mainFrame, Song song) {
        super(mainFrame, song);

        this.makeSellSubPanel();
    }

    /**
     * Sub-panel that displays labels, input field for number of shares, total earnings, and sell
     * button.
     */
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

        HashMap<Song, Integer> portfolio = this.mainFrame.model.user_port(this.user);
        int sharesOwned = portfolio.get(this.song);
        JLabel numShares = new JLabel("Shares Owned: " + sharesOwned + " shares");
        numShares.setFont(this.labelFont);

        this.sharesToSell.setText("# shares");
        this.sharesToSell.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                int parsed = parseTextField();
                updateTotalEarnings(parsed);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                this.keyTyped(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        int sharesToSellInt = this.parseTextField();

        double earnings = (double) sharesToSellInt * this.song.getSongValue();
        this.earningsLabel = new JLabel("  Total Earnings: $" + earnings + "  ");
        this.earningsLabel.setFont(this.labelFont);

        this.sellButton.addActionListener(this);

        constraints.gridx = 0;
        constraints.gridy = 0;
        sellSubPanel.add(sellLabel, constraints);
        constraints.gridy++;
        sellSubPanel.add(numShares, constraints);
        constraints.gridy++;
        sellSubPanel.add(this.sharesToSell, constraints);
        constraints.gridx++;
        sellSubPanel.add(this.earningsLabel, constraints);
        constraints.gridx++;
        sellSubPanel.add(this.sellButton, constraints);
    }

    /**
     * Attempts to make sale if Sell Button is clicked. Displays popup error window otherwise.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.sellButton) {
            try {
                this.mainFrame.model.sell_shares(this.user, this.song, this.parseTextField());
            } catch (Exception ex) {
                this.showErrorPopup(ex.getMessage(), "ERROR: Could Not Sell Shares");
            }
        }
    }

    /**
     * Updates label showing total earnings.
     *
     * @param numShares number of shares to sell
     */
    private void updateTotalEarnings(int numShares) {
        double earnings = (double) numShares * this.song.getSongValue();
        this.earningsLabel.setText("  Total Earnings: $" + earnings + "  ");
    }

    /**
     * Attempts to parse input field.
     * @return 0 or valid number
     */
    private int parseTextField() {
        int i = 0;
        try {
            i = Integer.parseInt(this.sharesToSell.getText());
        } catch (NumberFormatException e) { }
        return i;
    }
}
