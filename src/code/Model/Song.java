package code.Model;

public class Song {



    private int spotifyID;
    private String title;
    private int artistID;
    private int song_value;
    private int albumID;

    /**
     Constructor
     */
    public Song(int spotifyID, String title, int artistID, int song_value, int albumID) {
        this.spotifyID = spotifyID;
        this.title = title;
        this.artistID = artistID;
        this.song_value = song_value;
        this.albumID = albumID;
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

    public int getSpotifyID() {
        return spotifyID;
    }

    public void setSpotifyID(int spotifyID) {
        this.spotifyID = spotifyID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getArtistID() {
        return artistID;
    }

    public void setArtistID(int artistID) {
        this.artistID = artistID;
    }

    public int getSongValue() {
        return this.song_value;
    }

    public void setRank(int song_value) {
        this.song_value = song_value;
    }

    public int getAlbumID() {
        return albumID;
    }

    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }
    
}
