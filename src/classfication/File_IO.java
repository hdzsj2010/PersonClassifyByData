package classfication;
import java.io.*;
public class File_IO {
	String fileName = "";
	String content = "";
	/*
	 * 写文件
	 */
	public static void writeDict(String fileName, String content) {
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public static Float readDictReturnsKey(String fileName,String match) {
    	Float key =0f;
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
            	if(tempString.equals(match)){
            	key = Float.parseFloat(reader.readLine());break;
            	}
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return key;
    }
}
