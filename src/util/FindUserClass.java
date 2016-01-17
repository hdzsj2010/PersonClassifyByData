package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.database.Db_connect;

public class FindUserClass {
	public static List<String> userIdList = new ArrayList<>();
	public static boolean flag = false; 
	public static void main(String[] args) throws IOException{
		
		int pre = 0;
		int next = 1000;
		while(true){
			//List<String> list = new ArrayList<>();
			getUserId(pre,next);
			if (flag) {
				break;
			}
			//System.out.println(userIdList.size());
			pre+=1000;next+=1000;
		}
		WirteUserClass(userIdList);
	}
	
	public static List<String> getUserId(int pre,int next){
		String sql = "select user_id from log_test_self where num_flag>"+pre+" and num_flag<"+next;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		try {
			conn = Db_connect.getConnection();
			ps = Db_connect.getPreparedStatement(conn, sql);
			rs = ps.executeQuery();
			int i = 0;
			while (rs.next()) {
				i++;
				String user_id = rs.getString("user_id");
				if (!userIdList.contains(user_id)) {
					userIdList.add(user_id);
				}
			}
			if (i==0) {
				System.out.println("userIdList"+userIdList.size());
				flag = true;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			Db_connect.close(rs);
			Db_connect.close(ps);
			Db_connect.close(conn);
		}
		return userIdList;
	}
	
	public static void WirteUserClass(List<String> list) throws IOException{
		String logFile = "D:/info_test.csv"; 
		File file = new File(logFile);
        FileOutputStream out = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(out, "UTF8");

        BufferedWriter bw = new BufferedWriter(osw);
		for (String string : list) {
			String sql = "select * from info_train where user_id="+string;
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs=null;
			try {
				conn = Db_connect.getConnection();
				ps = Db_connect.getPreparedStatement(conn, sql);
				rs = ps.executeQuery();
				while (rs.next()) {
					bw.write(rs.getString("user_id")+",");
					bw.write(rs.getString("user_class")+"\r\n");
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				Db_connect.close(rs);
				Db_connect.close(ps);
				Db_connect.close(conn);
			}
		}
		
	}
}
