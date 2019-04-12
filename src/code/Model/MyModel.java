package code.Model;

public class MyModel implements ModelSQLInterface {

public void buy_shares(User u, Song s, int n) {
       String sql = "INSERT INTO buy (user_id, spotify_id, price, n_shares, purchase_time) VALUES" +
                     "('"+ u.getUserID()+","+ s.getSpotifyID()+ ","
                         + ", SELECT song_value FROM song WHERE song_id =" +
                     s.getSpotifyID()+"," +"CURRENT_TIMESTAMP)";       
}
  
public void sell_shares(User u, Song s, int n) {
  String sql = "INSERT INTO sell (user_id, spotify_id, price, n_shares, sale_time) VALUES" +
                "('"+ u.getUserID()+","+ s.getSpotifyID()+ ","
                    + ", SELECT song_value FROM song WHERE song_id =" +
                s.getSpotifyID()+"," +"CURRENT_TIMESTAMP)"; 
}
}
