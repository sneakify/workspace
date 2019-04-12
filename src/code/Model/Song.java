package code.Model;

public class Song {



    private String spotifyID;
    private String title;
    private String artistID;
    private int rank;
    private String albumID;

    /**
     Constructor
     */
    public Song(String spotifyID, String title, String artistID, int rank, String albumID) {
        this.spotifyID = spotifyID;
        this.title = title;
        this.artistID = artistID;
        this.rank = rank;
        this.albumID = albumID;
    }

    /**
     The Convenience Constructor
     */
    public Song(String title, String artistID, int rank) {
        this.spotifyID = "DUMMY";
        this.title = title;
        this.artistID = artistID;
        this.rank = rank;
        this.albumID = "DUMMY";
    }

    @Override
    public String toString() {
        return "Song{" +
                "spotifyID=" + spotifyID +
                ", title='" + title + '\'' +
                ", artistID='" + artistID + '\'' +
                ", rank=" + rank + '\'' +
                '}';
    }

    public String getSpotifyID() {
        return spotifyID;
    }

    public void setSpotifyID(String spotifyID) {
        this.spotifyID = spotifyID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtistID() {
        return artistID;
    }

    public void setArtistID(String artistID) {
        this.artistID = artistID;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

}
