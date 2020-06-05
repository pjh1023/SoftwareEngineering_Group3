package Server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RankDB {
	public RankDB() {}
	
	public static void updateWinLose(Integer userID, boolean won) { // update win/lose status of client
		Statement stmt = null;
		int win = (won)? 1:0;
		int lose = (won)? 0:1;
		ArrayList<Integer> user_rate = getWinLose(userID);
		double win_rate = (double)(user_rate.get(0)+win) / (double)((user_rate.get(0)+win)+(user_rate.get(1)+lose));
		int total_play = getTotalPlay(userID);
		
		String sql = "UPDATE ranking SET wins="+(user_rate.get(0)+win)+", loses="+(user_rate.get(1)+lose)+", rate="+win_rate+", total="+(total_play+1)+" WHERE userID="+userID;
		
    	try {
    		stmt = DB.getInstance().getConnection().createStatement();
    		stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("updateWinLose problem: ");
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Integer> getWinLose(Integer userID) { // get number of wins and loses
		Statement stmt = null;
		String sql = "SELECT wins, loses FROM ranking WHERE userID='"+userID+"'";
		ArrayList<Integer> rates = new ArrayList<Integer>();
		ResultSet result = null;

    	try {
			stmt = DB.getInstance().getConnection().createStatement();
    		result = stmt.executeQuery(sql);

    		while (result.next()) {
    			rates.add(result.getInt("wins"));
    			rates.add(result.getInt("loses"));
    			break;
    		}
    		
		} catch (SQLException e) {
			System.out.println("getWinLose problem: ");
			e.printStackTrace();
		}
    	return rates;
	}
	
	public static ArrayList<String> getTop5(){ // 승률 상위5명 정보 (동률시 play횟수순 -> userID순)
		Statement stmt = null;
		ResultSet result = null;
		String sql = "SELECT * FROM ranking ORDER BY rate DESC, total DESC, userID ASC limit 5";
		ArrayList<String> rank = new ArrayList<String>();
		
    	try {
			stmt = DB.getInstance().getConnection().createStatement();
    		result = stmt.executeQuery(sql);

    		while (result.next()) {
    			rank.add(LoginDB.getNickname(result.getInt("userID")));
    			rank.add(Integer.toString(result.getInt("wins")));
    			rank.add(Integer.toString(result.getInt("loses")));
    			rank.add(Double.toString(result.getDouble("rate")));
    		}
		} catch (SQLException e) {
			System.out.println("getTop10 problem: ");
			e.printStackTrace();
		}
		return rank;
	}
	
	public static void addUserRank(Integer id) { // register new user to ranking table
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO ranking VALUES("+id+", 0, 0, 0, 0)";
		
    	try {
    		pstmt = DB.getInstance().getConnection().prepareStatement(sql);
    		pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("addUserRank problem: ");
			e.printStackTrace();
		}
	}
	
	public static int getTotalPlay(Integer userID) { // check total play count of a player (10회 이상만 ranking)
		Statement stmt = null;
		String sql = "SELECT total FROM ranking WHERE userID='"+userID+"'";
		int total_play = 0;
		ResultSet result = null;

    	try {
			stmt = DB.getInstance().getConnection().createStatement();
    		result = stmt.executeQuery(sql);

    		while (!result.next()) {
    			total_play = result.getInt("total");
    			break;
    		}
    		
		} catch (SQLException e) {
			System.out.println("getTotalPlay problem: ");
			e.printStackTrace();
		}
    	return total_play;
	}
}
