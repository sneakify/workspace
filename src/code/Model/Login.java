package code.Model;

/**
 * This class has static fields which we use to pull out the URL, Username and Password for the MySQL system.
 * Optimally, these static fields would be populated from a file with permissions altered. We might have chosen to
 * read these values in as String Args in the main. ¯\_(ツ)_/¯
 */
public class Login {
    public static final String url = "jdbc:mysql://localhost:3306/spootify?serverTimezone=EST5EDT";
    public static final String usr = "murach";
    public static final String pword = "grendel";
}
