package util.database;

import java.beans.PropertyVetoException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;


import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 
   * @ClassName: db_connect
   * @Description: ���ݿ����ӹ��߰�
   * @author jason
   * @date 2015-12-21
   *
 */
public class Db_connect {
    private static String drivers = Db_config.DRIVERS;
    private static String url = Db_config.URL;
    private static String user = Db_config.USER;
    private static String password = Db_config.PASSWORD;
    private static ComboPooledDataSource ds = null;   
    /**
     * ��̬������C3P0���ӳ�
     */
    static {   
        try {
            ds = new ComboPooledDataSource();   // ����JDBC��Driver��   
            ds.setDriverClass(drivers);  // ������ Config ����������ļ���ȡ   
            ds.setJdbcUrl(url);    // ����JDBC��URL   
            ds.setUser(user);   // �������ݿ�ĵ�¼�û���   
            ds.setPassword(password);  // �������ݿ�ĵ�¼�û�����   
            ds.setMaxPoolSize(10);   // �������ӳص����������  10  
            ds.setMinPoolSize(1); // �������ӳص���С������
            //---------- ��� 
//            ds.setMaxStatements(0);//�������ӳصĻ���Statement�������,Ĭ��0
//            ds.setMaxStatementsPerConnection(0);
            ds.setMaxIdleTime(20);//������ʱ��,20����δʹ�������ӱ���������Ϊ0����������
            //ds.setIdleConnectionTestPeriod(30);//ÿ30�����������ӳ��еĿ�������
           // ds.setNumHelperThreads(3);
            //ds.setAcquireIncrement(50);
            
            ds.setCheckoutTimeout(1000);//1sû��Ӧ���׳��쳣
            //---------- ���
        } catch (PropertyVetoException e) {   
            e.printStackTrace();   
        }   
    } 
    
    /**
     * ��ɾ��ͳһ�ӿ�:����ɾ��
     * @param sql
     * @return boolean �ɹ�����true��ʧ�ܷ���false
     */
    public static void update(String sql){
    	Connection conn = Db_connect.getConnection();
		PreparedStatement ps = null;
    	try {
		    ps = Db_connect.getPreparedStatement(conn,sql);
	    	ps.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			  Db_connect.close(ps);
			  Db_connect.close(conn);
		}    
	}
    /**
     * ͳһ��ѯ�ӿڣ��򵥲�ѯ
     * ʹ������RowSet���ر�����֮����Ȼ�ܷ���
     * @param sql
     * @return CachedRowSet
     */
	public static CachedRowSet query(String sql){
		PreparedStatement ps = null;
		ResultSet rs=null;
		CachedRowSet crs=null;
		Connection conn=null;
		if(sql.equals("") || sql == null){
	        return null;
	    }
		try {
	    	conn= Db_connect.getConnection();	
		    ps = Db_connect.getPreparedStatement(conn,sql);
			rs=ps.executeQuery();
			RowSetFactory factory=RowSetProvider.newFactory();
			crs=factory.createCachedRowSet();
			crs.populate(rs);	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			Db_connect.close(rs);
			Db_connect.close(ps);
			Db_connect.close(conn);	
		}
	    return crs;
	}
	/**
	 * ִ�д洢����
	 * @param sql
	 * @throws SQLException
	 */
	public static void procedures(String sql){
		// ��������ִ�����ݿ�洢���̵�CallableStatement����
	    CallableStatement cs = null;
	    Connection conn=null;
		try {
	    	// �������ݿ�����
		    conn= Db_connect.getConnection();
		    cs = Db_connect.getCallableStatement(conn, sql);
			cs.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
		    Db_connect.close(cs);
		    Db_connect.close(conn);
		}
	}
	/**
	 * �������ӣ���ȡC3P0���ݿ����ӳ��е�һ������
	 * @return
	 */
    public static Connection getConnection() {   
        Connection con = null;   
        try {   
            con = ds.getConnection();   
        } catch (SQLException e1) {   
            e1.printStackTrace();   
        }   
        return con;   
    }

    /**
     * �ر�����
     * @param conn
     */
    public static void closeConnection(Connection conn) {
	try {
	    if (conn != null && !conn.isClosed()) {
		conn.close();
	    }
	} catch (SQLException e) {
	    // TODO: handle exception
	    e.printStackTrace();
	}
    }

    /**
     * �õ�PreparedStatement����
     * 
     * @param conn
     * @param sql
     * @return ps
     */
    public static  PreparedStatement getPreparedStatement(Connection conn, String sql) {
	PreparedStatement ps = null;
	if (sql == null || "".equals(sql)) {
	    System.out.println("SQLΪ��...");
	    return null;
	}
	if (conn == null) {
	    System.out.println("����Ϊ��...");
	    return null;
	}
	try {
	    ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
		    ResultSet.CONCUR_UPDATABLE);// ����Ĳ���Ϊ������������͸��µ�ResultSet����
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return ps;
    }

    /**
     * �õ�CallableStatement����
     * 
     * @param conn
     * @param sql
     * @return cs
     */
    public static CallableStatement getCallableStatement(Connection conn, String sql) {
	CallableStatement cs = null;
	if (sql == null || "".equals(sql)) {
	    System.out.println("SQLΪ��...");
	    return null;
	}
	if (conn == null) {
	    System.out.println("����Ϊ��...");
	    return null;
	}
	try {
	    cs = conn.prepareCall(sql);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return cs;
    }    
 
    /**
     * ִ��CallableStatement���������ù���
     * 
     * @param cstmt     
     */
    public static  void execute(CallableStatement cstmt) {
	try {
	    if (cstmt != null)
		cstmt.execute(); // ���ù���
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }
    
    public static  void execute(PreparedStatement ps) {
    	try {
    	    if (ps != null)
    		ps.execute(); // ���ù���
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	}
        }
    /**
     * �ر�PreparedStatement����
     * 
     * @param conn2
     */
    public static  void close(Connection conn2) {
	try {
	    if (conn2 != null) {
		conn2.close();
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }
    
    /**
     * �ر�PreparedStatement����
     * 
     * @param pstmt
     */
    public static void close(PreparedStatement pstmt) {
	try {
	    if (pstmt != null) {
		pstmt.close();
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }
    
    /**
     * �ر�CallableStatement����
     * 
     * @param pstmt
     */
    public static void close(CallableStatement cstmt) {
	try {
	    if (cstmt != null) {
		cstmt.close();
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }
    
    /**
     * �ر�ResultSet�����
     * 
     * @param rs
     */
    public static void close(ResultSet rs) {
	try {
	    if (rs != null) {
		rs.close();
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }
    /**
     * �ر�CachedRowSet�����
     * 
     * @param rs
     */
    public static void close(CachedRowSet crs) {
    	try {
    	    if (crs != null) {
    		crs.close();
    	    }
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	}
        }

}

