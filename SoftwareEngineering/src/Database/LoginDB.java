package Database;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LoginDB {
	public LoginDB() {}
	
	public static void addUser(Object [] infos) { // register new user (sign up)
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO users(ID,PW,NickName) VALUES(?, ?, ?)";	// (userID), ID, PW, NickName
		
    	try {
    		pstmt = DB.getInstance().getConnection().prepareStatement(sql);
    		for (int i = 1; i <= infos.length; i++) {
				pstmt.setString(i, (String)infos[i-1]);
    		}
    		pstmt.executeUpdate();
    		
    		RankDB.addUserRank(getUserID((String)infos[0]));
		} catch (SQLException e) {
			System.out.println("addUser problem: ");
			e.printStackTrace();
		}
	}

	public void changeUserInfo(Object[] infos) { // update user's information (pw, nickname)
		PreparedStatement pstmt = null;
		String sql = "UPDATE users SET PW=?, NickName=? WHERE ID='" + (String)infos[1] + "'";
		    	
    	try {
    		pstmt = DB.getInstance().getConnection().prepareStatement(sql);
    		
    		for (int i = 1; i < infos.length-1; i++) {
				pstmt.setString(i, (String) infos[i+1]);
    		}
    		pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("changeUserInfo problem: ");
			e.printStackTrace();
		}
	}
    		
	public boolean checkLogin(String id, String pw) { // check ID & PW for login, log history if success 
		Statement st = null;
		ResultSet result = null;
		String sql = "SELECT * FROM users WHERE ID='"+id+"' AND PW='" + pw + "'";
		
		Statement history_stmt = null;

		InetAddress local;
		String UserIP = null;		
		
		try {
		    local = InetAddress.getLocalHost();
		    UserIP = local.getHostAddress();
		} catch (UnknownHostException e1) {
		    e1.printStackTrace();
		}
		
		String history_sql = "INSERT INTO log (userID, datetime, loginIP) VALUES('"+ getUserID(id) +"', now(), '"+UserIP+"')";
    		
    	try {
			st = DB.getInstance().getConnection().createStatement();
			history_stmt = DB.getInstance().getConnection().createStatement();
			
    		result = st.executeQuery(sql);
    		if (!result.next()) {
    			return false;
    		}
    		
    		history_stmt.executeUpdate(history_sql);
    		
    		DB.currentID = id;

		} catch (SQLException e) {
			System.out.println("checkLogin problem: ");
			e.printStackTrace();
		}
    	return true;
	}
	
	
	public boolean checkRedundantId(String id) { // check for redundant ID for sign up
		Statement st = null;
		ResultSet result = null;

    	try {
			st = DB.getInstance().getConnection().createStatement();
    		result = st.executeQuery("SELECT * FROM users WHERE ID='" + id +"'");

    		if (!result.next()) {return true;}
    		
		} catch (SQLException e) {
			System.out.println("checkRedundantId problem: ");
			e.printStackTrace();
		}
    	return false;
	}
	
	public boolean checkRedundantNick(String nickname) { // check for redundant NickName for sign up & update
		Statement st = null;
		ResultSet result = null;

    	try {
			st = DB.getInstance().getConnection().createStatement();
    		result = st.executeQuery("SELECT * FROM users WHERE NickName='" + nickname +"'");

    		if (!result.next()) {return true;}
    		
		} catch (SQLException e) {
			System.out.println("checkRedundantNick problem: ");
			e.printStackTrace();
		}
    	return false;
	}
	
	public static ArrayList<String> getUserInfo(String id) { // get user information (ID, PW, NickName)
    	Statement st = null;
		ResultSet result = null;
		String sql = "SELECT ID, PW, NickName FROM users WHERE ID='"+id+"'";
		ArrayList<String> infos = new ArrayList<String>();
    	
    	try {
    		st = DB.getInstance().getConnection().createStatement();
    		result = st.executeQuery(sql);
    		
    		while (result.next()) {
    	    	infos.add(result.getString("ID"));
    	    	infos.add(result.getString("PW"));
    	    	infos.add(result.getString("NickName"));
    	    	break;
    		}
		} catch (SQLException e) {
			System.out.println("getUserInfo problem: ");
			e.printStackTrace();
		}
    	
    	return infos;
	}
	
	public static int getUserID(String id) { // get user's unique ID number
		Statement st = null;
		ResultSet result = null;
		String sql = "SELECT userID FROM users WHERE ID='"+id+"'";
    	
    	try {
    		st = DB.getInstance().getConnection().createStatement();
    		result = st.executeQuery(sql);
    		
    		if(result.next()) {
    			return result.getInt("userID");
    		}
		} catch (SQLException e) {
			System.out.println("getUserID problem: ");
			e.printStackTrace();
		}
    	return -1; // if non-existing id
	}
	
	public static String getNickname(Integer userid) { // get user's nickname using userID
		Statement st = null;
		ResultSet result = null;
		String sql = "SELECT NickName FROM users WHERE userID='"+userid+"'";
    	
    	try {
    		st = DB.getInstance().getConnection().createStatement();
    		result = st.executeQuery(sql);
    		
    		if(result.next()) {
    			return result.getString("NickName");
    		}
		} catch (SQLException e) {
			System.out.println("NickName problem: ");
			e.printStackTrace();
		}
    	return null; // if non-existing userID
	}
}
