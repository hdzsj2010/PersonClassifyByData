package classfication;

import model.*;
import java.util.*;

/**
 * 
 * @author jason
 *
 */
public class Dict {
	// ×ÖµäÎÄ¼þÃû
	public static String[] cat_dictname = { "cat_dict_class1.txt", "cat_dict_class2.txt", "cat_dict_class3.txt",
			"cat_dict_class4.txt", "cat_dict_class5.txt", "cat_dict_class6.txt", "cat_dict_class7.txt",
			"cat_dict_class8.txt", "cat_dict_class9.txt", "cat_dict_class10.txt", "cat_dict_class11.txt",
			"cat_dict_class12.txt" };
	public static String[] brand_dictname = { "brand_dict_class1.txt", "brand_dict_class2.txt", "brand_dict_class3.txt",
			"brand_dict_class4.txt", "brand_dict_class5.txt", "brand_dict_class6.txt", "brand_dict_class7.txt",
			"brand_dict_class8.txt", "brand_dict_class9.txt", "brand_dict_class10.txt", "brand_dict_class11.txt",
			"brand_dict_class12.txt" };
	static HashMap<String, Float>[] cat_dict = new HashMap[12];// cat_id ×Öµä
	static HashMap<String, Float>[] brand_dict = new HashMap[12];// Brand_id×Öµä
	static {
		for (int i = 0; i < 12; i++) {
			cat_dict[i] = new HashMap<String, Float>();
			brand_dict[i] = new HashMap<String, Float>();
		}
	}
	// ¸÷ÏîãÐÖµ,MapÖÐÐ¡ÓÚÕâ¸öÖµµÄÏî»á±»É¾³ý
	static Float cat_th = 20f;//cat_idµÄÈ¨Öµ
	static Float brand_th = 20f;//Brand_idµÄÈ¨Öµ
	// ÓÃ»§ÐÐÎªµÄ¼Ó·Ö
	static Float click = 0.5f;
	static Float purchase = 5f;
	static Float favorite = 1f;
	static Float cart = 3f;
	/*
	 * Ð´Èë×ÖµäÊý¾Ý Í³¼ÆÃ¿¸öcatºÍBrandµÄµÃ·Ö²¢Ð´Èë×Öµä ÊäÈëµ¥¸öÓÃ»§´ÊÌõ¸ñÊ½£º String[] user_log_bysingle =
	 * {"user_id","cat_id","brand_id","action_type"}
	 */
	public void writeMapDict(PersonAction personaction) {
		switch (personaction.getAction()) {
		/*case "0": {
			if (cat_dict[personaction.getUserClass()-1].get(personaction.getCatId()) == null) {
				cat_dict[personaction.getUserClass()-1].put(personaction.getCatId(), click);
			} else {
				cat_dict[personaction.getUserClass()-1].put(personaction.getCatId(),
						cat_dict[personaction.getUserClass()-1].get(personaction.getCatId()) + click);
			}
			if(brand_dict[personaction.getUserClass()-1].get(personaction.getBrandId()) == null){
				brand_dict[personaction.getUserClass()-1].put(personaction.getBrandId(), click);break;
			}
			else{
				brand_dict[personaction.getUserClass()-1].put(personaction.getBrandId(),
						brand_dict[personaction.getUserClass()-1].get(personaction.getBrandId()) + click);break;
			}
		}*/
		case "2": {
			if (cat_dict[personaction.getUserClass()-1].get(personaction.getCatId()) == null) {
				cat_dict[personaction.getUserClass()-1].put(personaction.getCatId(), purchase);
			} else {
				cat_dict[personaction.getUserClass()-1].put(personaction.getCatId(),
						cat_dict[personaction.getUserClass()-1].get(personaction.getCatId()) + purchase);
			}
			if(brand_dict[personaction.getUserClass()-1].get(personaction.getBrandId()) == null){
				brand_dict[personaction.getUserClass()-1].put(personaction.getBrandId(), purchase);break;
			}
			else{
				brand_dict[personaction.getUserClass()-1].put(personaction.getBrandId(),
						brand_dict[personaction.getUserClass()-1].get(personaction.getBrandId()) + purchase);break;
			}
		}
		case "3": {
			if (cat_dict[personaction.getUserClass()-1].get(personaction.getCatId()) == null) {
				cat_dict[personaction.getUserClass()-1].put(personaction.getCatId(), favorite);
			} else {
				cat_dict[personaction.getUserClass()-1].put(personaction.getCatId(),
						cat_dict[personaction.getUserClass()-1].get(personaction.getCatId()) + favorite);
			}
			if(brand_dict[personaction.getUserClass()-1].get(personaction.getBrandId()) == null){
				brand_dict[personaction.getUserClass()-1].put(personaction.getBrandId(), favorite);break;
			}
			else{
				brand_dict[personaction.getUserClass()-1].put(personaction.getBrandId(),
						brand_dict[personaction.getUserClass()-1].get(personaction.getBrandId()) + favorite);break;
			}
		}
		case "1": {
			if (cat_dict[personaction.getUserClass()-1].get(personaction.getCatId()) == null) {
				cat_dict[personaction.getUserClass()-1].put(personaction.getCatId(), cart);
			} else {
				cat_dict[personaction.getUserClass()-1].put(personaction.getCatId(),
						cat_dict[personaction.getUserClass()-1].get(personaction.getCatId()) + cart);
			}
			if(brand_dict[personaction.getUserClass()-1].get(personaction.getBrandId()) == null){
				brand_dict[personaction.getUserClass()-1].put(personaction.getBrandId(), cart);break;
			}
			else{
				brand_dict[personaction.getUserClass()-1].put(personaction.getBrandId(),
						brand_dict[personaction.getUserClass()-1].get(personaction.getBrandId()) + cart);break;
			}
		}
		}
	}

	/*
	 * ½¨Á¢×Öµä ÊäÈëÕûÌåÓÃ»§´ÊÌõ¸ñÊ½£º String[][] user_log = {String[]
	 * user_log_bysingle£¬String[] user_log_bysingle£¬String[]
	 * user_log_bysingle......}
	 */
	public void setMapDict(List<PersonAction> user_log) {
		for (int i = 0; i < user_log.size(); i++) {
			this.writeMapDict(user_log.get(i));
		}
		/*
		 * ±éÀúcat_dictºÍbrand_dict É¾³ýµÃ·Ö¹ýÐ¡µÄcatÓëbrand
		 */
//		for (int i = 0; i < cat_dict.length; i++) {
//			Set<Map.Entry<String, Float>> cat_entrySet = cat_dict[i].entrySet();
//			Iterator<Map.Entry<String, Float>> cat_iter = cat_entrySet.iterator();
//			Set<Map.Entry<String, Float>> brand_entrySet = brand_dict[i].entrySet();
//			Iterator<Map.Entry<String, Float>> brand_iter = brand_entrySet.iterator();
//
//			while (cat_iter.hasNext()) {
//				Map.Entry<String, Float> entry = cat_iter.next();
//				String key = entry.getKey();
//				Float value = entry.getValue();
//				if (value < cat_th) {
//					cat_iter.remove();
//					continue;
//				}
//				File_IO.writeDict(cat_dictname[i], key + "\r\n" + value + "\r\n");
//			}
//			while (brand_iter.hasNext()) {
//				Map.Entry<String, Float> entry = brand_iter.next();
//				String key = entry.getKey();
//				Float value = entry.getValue();
//				if (value < brand_th) {
//					brand_iter.remove();
//					continue;
//				}
//				File_IO.writeDict(brand_dictname[i], key + "\r\n" + value + "\r\n");
//			}
//		}

	}
	
	public static void setDict() {
		for (int i = 0; i < cat_dict.length; i++) {
			Set<Map.Entry<String, Float>> cat_entrySet = cat_dict[i].entrySet();
			Iterator<Map.Entry<String, Float>> cat_iter = cat_entrySet.iterator();
			Set<Map.Entry<String, Float>> brand_entrySet = brand_dict[i].entrySet();
			Iterator<Map.Entry<String, Float>> brand_iter = brand_entrySet.iterator();

			while (cat_iter.hasNext()) {
				Map.Entry<String, Float> entry = cat_iter.next();
				String key = entry.getKey();
				Float value = entry.getValue();
				if (value < cat_th) {
					cat_iter.remove();
					continue;
				}
				File_IO.writeDict(cat_dictname[i], key + "\r\n" + value + "\r\n");
			}
			while (brand_iter.hasNext()) {
				Map.Entry<String, Float> entry = brand_iter.next();
				String key = entry.getKey();
				Float value = entry.getValue();
				if (value < brand_th) {
					brand_iter.remove();
					continue;
				}
				File_IO.writeDict(brand_dictname[i], key + "\r\n" + value + "\r\n");
			}
		}

	}

}
