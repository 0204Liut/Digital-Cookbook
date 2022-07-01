package dataAccess;

/**
 * my driver class. It stores the user information that are used to create connection to the database.
 * @author kuangzheng
 *
 */
public class Driver {
	public static String url;
	public static String user;
	public static String password;
	
	/**
	 * set the user information that are used to create connection to the database.
	 * @param url:a database url of the form jdbc:subprotocol:subname
	 * @param user:the database user on whose behalf the connection is beingmade
	 * @param password:the user's password
	 */
	public static void setConnectionForAllTables(String url,String user,String password) {
		Driver.url=url;
		Driver.user=user;
		Driver.password=password;
	}

}
