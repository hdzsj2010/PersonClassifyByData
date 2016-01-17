package util.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
   * @ClassName: db_config
   * @Description: 数据库配置信息加载
   * @author jason
   * @date 2015-12-21 
   *
 */
public class Db_config {
	private static String db_config = "db_config.properties";
    public static String DRIVERS = null;
    public static String URL = null;
    public static String USER = null;
    public static String PASSWORD = null;
    
    static{    	
	Properties props = new Properties();
	InputStream inStr = null;
	try{
	    //读取属性文件，把属性文件转换成流文件
	    inStr = ClassLoader.getSystemResourceAsStream(db_config);
	    //读取属性列表，读取db_config.properties文件内的数据
	    props.load(inStr);	    
	    }catch(IOException e){
		e.printStackTrace();
	    }
	DRIVERS = props.getProperty("drivers");//得到Oracle数据库连接驱动字符串
	URL = props.getProperty("url");//得到Oracle数据库连接URL
	USER =props.getProperty("user");//得到用户名
	PASSWORD = props.getProperty("pwd");//得到密码	    
    }
}

