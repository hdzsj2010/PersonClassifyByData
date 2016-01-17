package util.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Db_test {
	public static void main(String[] args){
		//≤‚ ‘ ˝æ›ø‚≤È—Ø
		Thread testThread = new Thread(new Test());
		testThread.start();
	}
	
	static class Test implements Runnable{
		@Override
		public void run(){
			String sql = "select * from info_train where id='99001'";
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs=null;
			try {
				conn = Db_connect.getConnection();
				ps = Db_connect.getPreparedStatement(conn, sql);
				rs = ps.executeQuery();
				while (rs.next()) {
					String userId = rs.getString("user_id");
					System.out.println(userId);
				}
				
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally{
				Db_connect.close(rs);
				Db_connect.close(ps);
				Db_connect.close(conn);
			}
		}
		
	}
}
