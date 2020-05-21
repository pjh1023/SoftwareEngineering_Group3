package Database;
import java.sql.*;

/*	hgumarble (database)
 * 	
 * 	(tables)
 * 	users
 * 	ranking
 * 	chat
 * 	map
 *  log
 * 
 */

public class DB {
	private Connection con = null;
	private static DB db = new DB();

	public static String currentID;

	private DB() {        	

        String url = "jdbc:mysql://203.252.119.113:55551/hgumarble?serverTimezone=UTC";
        
        try {
			con = DriverManager.getConnection(url, "pjh", "wnsgur219");		// client용 계정 생성 필요. (limited access authorization)
		} catch (SQLException e) {
			System.out.println("connection problem: ");
			e.printStackTrace();
		}
        System.out.println("Connection Success");
    }
	
	public static DB getInstance() {return db;}
	public Connection getConnection() {return con;}
		
	public void closeConnection() {
		try {
            if(con != null && !con.isClosed())
                con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
}
