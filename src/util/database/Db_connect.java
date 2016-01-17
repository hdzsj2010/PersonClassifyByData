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
   * @Description: 数据库连接工具包
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
     * 静态块设置C3P0连接池
     */
    static {   
        try {
            ds = new ComboPooledDataSource();   // 设置JDBC的Driver类   
            ds.setDriverClass(drivers);  // 参数由 Config 类根据配置文件读取   
            ds.setJdbcUrl(url);    // 设置JDBC的URL   
            ds.setUser(user);   // 设置数据库的登录用户名   
            ds.setPassword(password);  // 设置数据库的登录用户密码   
            ds.setMaxPoolSize(10);   // 设置连接池的最大连接数  10  
            ds.setMinPoolSize(1); // 设置连接池的最小连接数
            //---------- 添加 
//            ds.setMaxStatements(0);//设置连接池的缓存Statement的最大数,默认0
//            ds.setMaxStatementsPerConnection(0);
            ds.setMaxIdleTime(20);//最大空闲时间,20秒内未使用则连接被丢弃。若为0则永不丢弃
            //ds.setIdleConnectionTestPeriod(30);//每30秒检查所有连接池中的空闲连接
           // ds.setNumHelperThreads(3);
            //ds.setAcquireIncrement(50);
            
            ds.setCheckoutTimeout(1000);//1s没反应则抛出异常
            //---------- 添加
        } catch (PropertyVetoException e) {   
            e.printStackTrace();   
        }   
    } 
    
    /**
     * 增删改统一接口:简单增删改
     * @param sql
     * @return boolean 成功返回true，失败返回false
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
     * 统一查询接口：简单查询
     * 使用离线RowSet，关闭链接之后仍然能返回
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
	 * 执行存储过程
	 * @param sql
	 * @throws SQLException
	 */
	public static void procedures(String sql){
		// 建立用于执行数据库存储过程的CallableStatement对象
	    CallableStatement cs = null;
	    Connection conn=null;
		try {
	    	// 建立数据库连接
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
	 * 建立连接，获取C3P0数据库连接池中的一个连接
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
     * 关闭连接
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
     * 得到PreparedStatement对象
     * 
     * @param conn
     * @param sql
     * @return ps
     */
    public static  PreparedStatement getPreparedStatement(Connection conn, String sql) {
	PreparedStatement ps = null;
	if (sql == null || "".equals(sql)) {
	    System.out.println("SQL为空...");
	    return null;
	}
	if (conn == null) {
	    System.out.println("连接为空...");
	    return null;
	}
	try {
	    ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
		    ResultSet.CONCUR_UPDATABLE);// 后面的参数为生成允许滚动和更新的ResultSet对象
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return ps;
    }

    /**
     * 得到CallableStatement对象
     * 
     * @param conn
     * @param sql
     * @return cs
     */
    public static CallableStatement getCallableStatement(Connection conn, String sql) {
	CallableStatement cs = null;
	if (sql == null || "".equals(sql)) {
	    System.out.println("SQL为空...");
	    return null;
	}
	if (conn == null) {
	    System.out.println("连接为空...");
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
     * 执行CallableStatement对象，启动该过程
     * 
     * @param cstmt     
     */
    public static  void execute(CallableStatement cstmt) {
	try {
	    if (cstmt != null)
		cstmt.execute(); // 调用过程
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }
    
    public static  void execute(PreparedStatement ps) {
    	try {
    	    if (ps != null)
    		ps.execute(); // 调用过程
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	}
        }
    /**
     * 关闭PreparedStatement对象
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
     * 关闭PreparedStatement对象
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
     * 关闭CallableStatement对象
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
     * 关闭ResultSet结果集
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
     * 关闭CachedRowSet结果集
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

