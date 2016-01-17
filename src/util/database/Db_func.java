package util.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import main.TrainUser;
import model.PersonAction;

public class Db_func {
	//public static LinkedBlockingQueue<PersonAction> list = new LinkedBlockingQueue<PersonAction>();
	public static boolean flag = false;
	public static Map<String, Integer> get_pid(int pre,int next){
		Map<String,Integer> personMap = new HashMap();  
		String sql = "select * from info_train where id>"+pre+" and id<"+next;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		try {
			conn = Db_connect.getConnection();
			//ps = conn.prepareStatement(sql);
			ps = Db_connect.getPreparedStatement(conn, sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String userId = rs.getString("user_id");
				int classId = rs.getInt("user_class");
				personMap.put(userId, classId);
				//System.out.println("userid:"+rs.getString("user_id"));
			}
			
			System.out.println("---------------------");
			return personMap;
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}finally{
			Db_connect.close(rs);
			Db_connect.close(ps);
			Db_connect.close(conn);
		}
		
	}
	
	public static List<PersonAction> get_action(String pid,int cid){
		List<PersonAction> pActionList=new ArrayList<PersonAction>();
		String sql = "select * from log_train_filter where user_id="+pid;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		try {
			conn = Db_connect.getConnection();
			ps = Db_connect.getPreparedStatement(conn, sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				PersonAction personAction = new PersonAction();
				personAction.setUserId(rs.getString("user_id"));
				personAction.setCatId(rs.getString("cat_id"));
				personAction.setBrandId(rs.getString("brand_id"));
				personAction.setAction(rs.getString("action_type"));
				personAction.setUserClass(cid);
				pActionList.add(personAction);
			}
			//System.out.println("useractionnum:"+pActionList.size());
			return pActionList;
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}finally{
			Db_connect.close(rs);
			Db_connect.close(ps);
			Db_connect.close(conn);
		}
		
	}
	
	public static void get_classfy(){
		String sql = "select * from log_test1";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		try {
			conn = Db_connect.getConnection();
			ps = Db_connect.getPreparedStatement(conn, sql);
			rs = ps.executeQuery();
			int user_id = 15442;
			List<PersonAction> list = new ArrayList<>();
			
			while (rs.next()) {
				
				int user_id2 = rs.getInt("user_id");
				if (user_id2==user_id) {
					PersonAction personAction = new PersonAction();
					personAction.setUserId(""+user_id);
					personAction.setCatId(rs.getString("cat_id"));
					personAction.setBrandId(rs.getString("brand_id"));
					personAction.setAction(rs.getString("action_type"));
					list.add(personAction);
				}else{
					user_id = user_id2;
					TrainUser.trainlist.addAll(list);
					System.out.println("---One get---");  //打印测试
					System.out.println("trainlist:"+TrainUser.trainlist.size());
					list = new ArrayList<>();
					PersonAction personAction = new PersonAction();
					personAction.setUserId(""+user_id);
					personAction.setCatId(rs.getString("cat_id"));
					personAction.setBrandId(rs.getString("brand_id"));
					personAction.setAction(rs.getString("action_type"));
					list.add(personAction);
				}
				
			}
			flag = true;
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}finally{
			Db_connect.close(rs);
			Db_connect.close(ps);
			Db_connect.close(conn);
		}
		
	}
	
	/**
	 * 
	 * @param table	表名
	 * @param feature		取得Cat或者Brand
	 * @return	返回List<String>
	 */
	public List<String> getOverall(String table,String feature){
		List<String> list=new ArrayList<String>();
		Map<String,Integer> personMap = new HashMap();  
		String sql = "select "+feature+" from "+table;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		try {
			conn = Db_connect.getConnection();
			ps = Db_connect.getPreparedStatement(conn, sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String featureId = rs.getString(feature);
				list.add(featureId);
			}
			return list;
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}finally{
			Db_connect.close(rs);
			Db_connect.close(ps);
			Db_connect.close(conn);
		}
	}
}
