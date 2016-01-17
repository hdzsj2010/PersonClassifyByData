package test;

import classfication.*;

public class ClassficationTest {
	public static String[] cat_dictname = { "cat_dict_class1.txt", "cat_dict_class2.txt", "cat_dict_class3.txt", "cat_dict_class4.txt",
			"cat_dict_class5.txt", "cat_dict_class6.txt", "cat_dict_class7.txt", "cat_dict_class8.txt", "cat_dict_class9.txt",
			"cat_dict_class10.txt", "cat_dict_class11.txt", "cat_dict_class12.txt" };
	public static String[] brand_dictname = { "brand_dict_class1.txt", "brand_dict_class2.txt", "brand_dict_class3.txt",
			"brand_dict_class4.txt", "brand_dict_class5.txt", "brand_dict_class6.txt", "brand_dict_class7.txt", "brand_dict_class8.txt",
			"brand_dict_class9.txt", "brand_dict_class10.txt", "brand_dict_class11.txt", "brand_dict_class12.txt" };

	public static void main(String[] args) {
		String[][] user_log = test_log();
		Dict classfi = new Dict();
		//classfi.setMapDict(user_log,cat_dictname[1],brand_dictname[1]);
		//System.out.println(File_IO.readDictReturnsKey("brand_dict.txt", "1375"));
	}

	public static String[][] test_log() {
		// String[] user_log_0 = {"352728","1389","5537","0"};
		String[] user_log_0 = { "297001", "331", "1375", "3" };
		String[] user_log_1 = { "297006", "331", "1375", "0" };
		String[] user_log_2 = { "105986", "1196", "5994", "3" };
		String[] user_log_3 = { "40254", "1424", "7875", "2" };
		String[] user_log_4 = { "297001", "331", "1375", "1" };
		String[] user_log_5 = { "105983", "1196", "5994", "0" };
		String[] user_log_6 = { "255091", "1353", "6813", "2" };
		String[] user_log_7 = { "111482", "1377", "5991", "0" };
		String[] user_log_8 = { "51439", "954", "93", "3" };
		String[] user_log_9 = { "40255", "1424", "7875", "1" };
		String[][] user_log = { user_log_0, user_log_1, user_log_2, user_log_3, user_log_4, user_log_5, user_log_6,
				user_log_7, user_log_8, user_log_9 };
		return user_log;
	}

}
