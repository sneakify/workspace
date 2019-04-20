package code.Model;

public class Song {

    private String spotifyID;
    private String title;
    private String artistID;
    private int song_value;
    private String albumID;

    /**
     Constructor
     */
    public Song(String spotifyID, String title, String artistID, int song_value, String albumID) {
        this.spotifyID = spotifyID;
        this.title = title;
        this.artistID = artistID;
        this.song_value = song_value;
        this.albumID = albumID;
    }

    /**
     * Convenience Constructor
     */
    public Song(String spotifyID) {
        this.spotifyID = spotifyID;
        this.title = "Default";
        this.artistID = "111";
        this.song_value = 111;
        this.albumID = "22222";
    }

    @Override
    public String toString() {
        return "Song{" +
                "spotifyID=" + spotifyID +
                ", title='" + title + '\'' +
                ", artistID='" + artistID + '\'' +
                ", song_value=" + song_value + '\'' +
                '}';
    }

    public String getSpotifyID() {
        return spotifyID;
    }

    public void setSpotifyID(String spotifyID) {
        this.spotifyID = spotifyID;
    }

    public String getTitle() {
        System.out.println(this.title);
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

    public int getSongValue() {
        return this.song_value;
    }

    public void setRank(int song_value) {
        this.song_value = song_value;
    }

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }
    
}
