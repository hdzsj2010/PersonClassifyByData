package util.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
   * @ClassName: db_config
   * @Description: ���ݿ�������Ϣ����
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
	    //��ȡ�����ļ����������ļ�ת�������ļ�
	    inStr = ClassLoader.getSystemResourceAsStream(db_config);
	    //��ȡ�����б���ȡdb_config.properties�ļ��ڵ�����
	    props.load(inStr);	    
	    }catch(IOException e){
		e.printStackTrace();
	    }
	DRIVERS = props.getProperty("drivers");//�õ�Oracle���ݿ����������ַ���
	URL = props.getProperty("url");//�õ�Oracle���ݿ�����URL
	USER =props.getProperty("user");//�õ��û���
	PASSWORD = props.getProperty("pwd");//�õ�����	    
    }
}

