package code.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import code.Model.Song;

/**
 * Panel that allows user to purchase some number of shares of  song. Launched from Browse Panel.
 */
public class BuyPanel extends TransactionPanel implements ActionListener {
    JTextField sharesToBuy = new JTextField();
    JLabel costLabel;
    JButton buyButton = new JButton("Buy");

    /**
     * Constructor.
     *
     * @param mainFrame reference to MainFrame
     * @param song song to be purchased
     */
    public BuyPanel(MainFrame mainFrame, Song song) {
        super(mainFrame, song);
        this.makeBuySubPanel();
    }

    /**
     * Sub-panel that displays labels, input field for number of shares, total cost, and buy button.
     */
    private void makeBuySubPanel() {
        // make panel and constraints
        JPanel buySubPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        // using wrapper panel to left-justify
        JPanel buySubPanelContainer = new JPanel(new BorderLayout());
        buySubPanelContainer.add(buySubPanel, BorderLayout.WEST);
        this.add(buySubPanelContainer, BorderLayout.SOUTH);

        JLabel buyLabel = new JLabel("Buy");
        buyLabel.setFont(this.labelFont);

        // user's purchasing power
        JLabel purchasingPower = new JLabel("Your Purchasing Power: $" + this.user.getPurchasing_power());
        purchasingPower.setFont(this.labelFont);

        this.sharesToBuy.setText("enter # shares to purchase");
        this.sharesToBuy.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                int parsed = parseTextField();
                updateTotalCost(parsed);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                this.keyTyped(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        int sharesToBuyInt = this.parseTextField();

        this.costLabel = new JLabel();
        this.costLabel.setFont(this.labelFont);
        this.updateTotalCost(sharesToBuyInt);

        this.buyButton.addActionListener(this);

        constraints.gridx = 0;
        constraints.gridy = 0;
        buySubPanel.add(buyLabel, constraints);
        constraints.gridy++;
        buySubPanel.add(purchasingPower, constraints);
        constraints.gridy++;
        buySubPanel.add(this.sharesToBuy, constraints);
        constraints.gridx++;
        buySubPanel.add(this.costLabel, constraints);
        constraints.gridx++;
        buySubPanel.add(this.buyButton, constraints);
    }

    /**
     * Attempts to make purchase if Buy Button is clicked. Displays popup error window otherwise.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.buyButton) {
            try {
                this.mainFrame.model.buy_shares(this.user, this.song, this.parseTextField());
            } catch (Exception ex) {
                this.showErrorPopup(ex.getMessage(), "ERROR: Could Not Buy Shares");
            }
        }
    }

    /**
     * Updates label showing total cost of purchase.
     *
     * @param numShares number of shares to purchase
     */
    private void updateTotalCost(int numShares) {
        double cost = (double) numShares * this.song.getSongValue();
        this.costLabel.setText("  Total Cost: $" + cost + "  ");
    }

    /**
     * Attempts to parse input field.
     * @return 0 or valid number
     */
    private int parseTextField() {
        int i = 0;
        try {
            i = Integer.parseInt(this.sharesToBuy.getText());
        } catch (NumberFormatException e) { }
        return i;
    }
}
