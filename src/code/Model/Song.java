package code.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    public Integer currentPrice(Song s, DatabaseAPI db) {
        String sql = "SELECT song_value from song " +
                "WHERE " + s.getSpotifyID() +
                "= song.spotify_id";

        String stuff = "DUMMY STUFF";

        try {
            DBUtils util = new DBUtils("me", "you", "yurmom");
            // TODO change to authenticate stuff?
            Connection con = util.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
//
//            for (Song s : listSongs) {
//                pstmt.setString(1, s.getLastName());
//                pstmt.setString(2, s.getFirstName());
//                pstmt.setBoolean(3, s.isAcceptingNewPatients());
//                pstmt.setInt(4, getOrInsertSpecialty(s.getSpecialty()));
//                pstmt.execute();
//            }
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Integer.valueOf(stuff);
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
