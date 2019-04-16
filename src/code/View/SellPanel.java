package code.View;

import code.Model.Song;

/**
 * TODO
 */
class SellPanel extends TransactionPanel {
    int sharesOwned;

    /**
     * TODO
     * @param song
     * @param sharesOwned
     */
    SellPanel(Song song, int sharesOwned) {
        super(song);
        this.sharesOwned = sharesOwned;
        // TODO
    }
}
