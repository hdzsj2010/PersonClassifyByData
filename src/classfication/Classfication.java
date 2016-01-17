package classfication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.*;

public class Classfication {
	// cat和brand项目的权值
	static Float weight_cat = 0.7f;
	static Float weight_brand = 0.3f;
	// 用户行为的权值
	static Float click = 0.1f;
	static Float purchase = 0.5f;
	static Float favorite = 0.2f;
	static Float cart = 0.2f;
	// user_log的格式
	static int user_id = 0;
	static int cat_id = 1;
	static int brand_id = 2;
	static int action_type = 3;
	// 字典文件名
	public static String[] cat_dictname = { "cat_dict_class1.txt",
			"cat_dict_class2.txt", "cat_dict_class3.txt",
			"cat_dict_class4.txt", "cat_dict_class5.txt",
			"cat_dict_class6.txt", "cat_dict_class7.txt",
			"cat_dict_class8.txt", "cat_dict_class9.txt",
			"cat_dict_class10.txt", "cat_dict_class11.txt",
			"cat_dict_class12.txt" };
	public static String[] brand_dictname = { "brand_dict_class1.txt",
			"brand_dict_class2.txt", "brand_dict_class3.txt",
			"brand_dict_class4.txt", "brand_dict_class5.txt",
			"brand_dict_class6.txt", "brand_dict_class7.txt",
			"brand_dict_class8.txt", "brand_dict_class9.txt",
			"brand_dict_class10.txt", "brand_dict_class11.txt",
			"brand_dict_class12.txt" };
	// 用户行为及权值的Map
	static HashMap<String, Float> actionTypt_weight_map = null;

	static {
		actionTypt_weight_map = new HashMap<String, Float>();
		actionTypt_weight_map.put("0", click);
		actionTypt_weight_map.put("2", purchase);
		actionTypt_weight_map.put("3", favorite);
		actionTypt_weight_map.put("1", cart);
	}

	/*
	 * 输入： 单个用户所有词条String[][] user_log; 每个词条格式： String[] user_log_bysingle =
	 * {"user_id","cat_id","brand_id","action_type"}
	 * *********************************************** 输出： user_id与最终分类的Map
	 * *********************************************** 分类算法详解：
	 * 输入单个用户的每条信息，根据每条信息的cat和brand， 在每个类的cat、brand词典找到相应得分，并乘以权值，
	 * 累加得到该用户在第i+1类的综合得分；找出12类中得分最高的类得到分类结果。
	 */
	public static HashMap<String, Integer> classfy(List<PersonAction> user_log) {
		Float[] score = { 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f };// new
																			// Float[12];
		HashMap<Float, Integer> class_score_map = new HashMap<Float, Integer>();
		HashMap<String, Integer> user_class_map = new HashMap<String, Integer>();
		// Float[] cat_key = new Float[1];
		// Float[] brand_key = {};
		// ArrayList<Float> cat_key = new ArrayList<Float>();
		// ArrayList<Float> brand_key = new ArrayList<Float>();
		Float cat_key = 0f;
		Float brand_key = 0f;
		if (!user_log.isEmpty()) {
			// i+1类，第k+1条数据
			for (int i = 0; i < cat_dictname.length; i++) {
				Float score_k = 0f;
				for (int k = 0; k < user_log.size(); k++) {
					// cat_key.add(k,File_IO.readDictReturnsKey(cat_dictname[i],
					// user_log.get(k).getCatId()));
					// brand_key.add(k,File_IO.readDictReturnsKey(brand_dictname[i],
					// user_log.get(k).getBrandId()));
					// cat_key[k] = File_IO.readDictReturnsKey(cat_dictname[i],
					// user_log.get(k).getCatId());
					// brand_key[k] =
					// File_IO.readDictReturnsKey(brand_dictname[i],
					// user_log.get(k).getBrandId());
					cat_key = File_IO.readDictReturnsKey(cat_dictname[i],
							user_log.get(k).getCatId());
					brand_key = File_IO.readDictReturnsKey(brand_dictname[i],
							user_log.get(k).getBrandId());
					// score_k = cat_key.get(k) * weight_cat *
					// actionTypt_weight_map.get(user_log.get(k).getAction())
					// + brand_key.get(k) * weight_brand *
					// actionTypt_weight_map.get(user_log.get(k).getAction());
					score_k = cat_key
							* weight_cat
							* actionTypt_weight_map.get(user_log.get(k)
									.getAction())
							+ brand_key
							* weight_brand
							* actionTypt_weight_map.get(user_log.get(k)
									.getAction());
					score[i] += score_k;
				}

				class_score_map.put(score[i], i + 1);
			}
			if (score.length != 0) {
				user_class_map.put(user_log.get(0).getUserId(),
						class_score_map.get(sortReturnsMAX(score)));
				/* 遍历map输出CSV */
				Set<Map.Entry<String, Integer>> user_class_mapentrySet = user_class_map
						.entrySet();
				Iterator<Map.Entry<String, Integer>> user_class_mapentrySet_iter = user_class_mapentrySet
						.iterator();
				while (user_class_mapentrySet_iter.hasNext()) {
					Map.Entry<String, Integer> entry = user_class_mapentrySet_iter
							.next();
					String key = entry.getKey();
					Integer value = entry.getValue();
					File_IO.writeDict("result.csv", key + "," + value + "\r\n");
				}

			}
			return user_class_map;
		} else {
			return null;
		}

	}

	public static Float sortReturnsMAX(Float[] args) {
		for (int i = 0; i < args.length - 1; i++) {
			for (int j = i + 1; j < args.length; j++) {
				Float temp;
				if (args[i] < args[j]) {
					temp = args[j];
					args[j] = args[i];
					args[i] = temp;
				}
			}
		}
		return args[0];
	}
}
