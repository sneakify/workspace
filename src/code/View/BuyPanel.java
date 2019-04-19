package code.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import code.Model.Song;

/**
 * TODO
 */
class BuyPanel extends TransactionPanel implements ActionListener {
    JTextField sharesToBuy = new JTextField();
    JButton buyButton = new JButton("Buy");

    /**
     * TODO
     * @param song
     */
    BuyPanel(Song song) {
        super(song);

        this.makeBuySubPanel();
    }

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

        this.sharesToBuy.setText("# shares");

        int sharesToBuyInt = this.parseTextField();

        double cost = (double) sharesToBuyInt * this.song.getRank(); // fixme replace getRank with current stock price
        JLabel costLabel = new JLabel("Total Cost: $" + cost);
        costLabel.setFont(this.labelFont);

        this.buyButton.addActionListener(this);

        constraints.gridx = 0;
        constraints.gridy = 0;
        buySubPanel.add(buyLabel, constraints);
        constraints.gridy++;
        buySubPanel.add(this.sharesToBuy, constraints);
        constraints.gridx++;
        buySubPanel.add(costLabel, constraints);
        constraints.gridx++;
        buySubPanel.add(this.buyButton, constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.buyButton) {
            try {
                // TODO use DB utils to buy specified number of shares (use parseTextField)
            } catch (Exception ex) {
                this.showErrorPopup(ex.getMessage(), "ERROR: Could Not Buy Shares");
            }
        }
    }

    private int parseTextField() {
        return Integer.parseInt(this.buyButton.getText());
    }
}
