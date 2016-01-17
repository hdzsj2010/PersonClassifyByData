package classfication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import model.PersonAction;

public class PublicDict {
	static HashMap<Integer,ArrayList<HashMap<String,Float>>> class_cat=new HashMap<Integer,ArrayList<HashMap<String,Float>>>();
	static HashMap<Integer,ArrayList<HashMap<String,Float>>> class_brand=new HashMap<Integer,ArrayList<HashMap<String,Float>>>();
	
	static Float click = 0.005f;
	static Float purchase = 0.05f;
	static Float favorite = 0.01f;
	static Float cart = 0.03f;
	
	public void writeClassCatMapDict(PersonAction personaction) {
		switch (personaction.getAction()) {
		case "0": {
			if ( class_cat.get(personaction.getUserClass())== null) {
				ArrayList<HashMap<String,Float>> tempList=new ArrayList<>();
				HashMap<String,Float> m=new HashMap<String,Float>();
				m.put(personaction.getCatId(), click);
				tempList.add(m);
				class_cat.put(personaction.getUserClass(), tempList);
			} else {
				if (hasCatID(personaction)) {
					ArrayList<HashMap<String,Float>> tempList=class_cat.get(personaction.getUserClass());
					for (HashMap<String, Float> hashMap : tempList) {
						Iterator keys=hashMap.keySet().iterator();
						boolean flag=false;
						while(keys.hasNext()){
							String key=(String)keys.next();
							if(personaction.getCatId().equals(key)){
								Float scoretemp = hashMap.get(key);
								hashMap.remove(key);
								hashMap.put(personaction.getCatId(), scoretemp+click);
								flag=true;
								break;
							}
						}
						if(flag){
							break;
						}
					}
				}else{
					HashMap<String,Float> m=new HashMap<>();
					m.put(personaction.getCatId(), click);
					class_cat.get(personaction.getUserClass()).add(m);
				}
			}
		}
		case "2": {
			if ( class_cat.get(personaction.getUserClass())== null) {
				ArrayList<HashMap<String,Float>> tempList=new ArrayList<>();
				HashMap<String,Float> m=new HashMap<String,Float>();
				m.put(personaction.getCatId(), purchase);
				tempList.add(m);
				class_cat.put(personaction.getUserClass(), tempList);
			} else {
				if (hasCatID(personaction)) {
					ArrayList<HashMap<String,Float>> tempList=class_cat.get(personaction.getUserClass());
					for (HashMap<String, Float> hashMap : tempList) {
						Iterator keys=hashMap.keySet().iterator();
						boolean flag=false;
						while(keys.hasNext()){
							String key=(String)keys.next();
							if(personaction.getCatId().equals(key)){
								Float scoretemp = hashMap.get(key);
								hashMap.remove(key);
								hashMap.put(personaction.getCatId(), scoretemp+purchase);
								flag=true;
								break;
							}
						}
						if(flag){
							break;
						}
					}
				}else{
					HashMap<String,Float> m=new HashMap<>();
					m.put(personaction.getCatId(), purchase);
					class_cat.get(personaction.getUserClass()).add(m);
				}
			}
		}
		case "3": {
			if ( class_cat.get(personaction.getUserClass())== null) {
				ArrayList<HashMap<String,Float>> tempList=new ArrayList<>();
				HashMap<String,Float> m=new HashMap<String,Float>();
				m.put(personaction.getCatId(), favorite);
				tempList.add(m);
				class_cat.put(personaction.getUserClass(), tempList);
			} else {
				if (hasCatID(personaction)) {
					ArrayList<HashMap<String,Float>> tempList=class_cat.get(personaction.getUserClass());
					for (HashMap<String, Float> hashMap : tempList) {
						Iterator keys=hashMap.keySet().iterator();
						boolean flag=false;
						while(keys.hasNext()){
							String key=(String)keys.next();
							if(personaction.getCatId().equals(key)){
								Float scoretemp = hashMap.get(key);
								hashMap.remove(key);
								hashMap.put(personaction.getCatId(), scoretemp+favorite);
								flag=true;
								break;
							}
						}
						if(flag){
							break;
						}
					}
				}else{
					HashMap<String,Float> m=new HashMap<>();
					m.put(personaction.getCatId(), favorite);
					class_cat.get(personaction.getUserClass()).add(m);
				}
			}
		}
		case "1": {
			if ( class_cat.get(personaction.getUserClass())== null) {
				ArrayList<HashMap<String,Float>> tempList=new ArrayList<>();
				HashMap<String,Float> m=new HashMap<String,Float>();
				m.put(personaction.getCatId(), cart);
				tempList.add(m);
				class_cat.put(personaction.getUserClass(), tempList);
			} else {
				if (hasCatID(personaction)) {
					ArrayList<HashMap<String,Float>> tempList=class_cat.get(personaction.getUserClass());
					for (HashMap<String, Float> hashMap : tempList) {
						Iterator keys=hashMap.keySet().iterator();
						boolean flag=false;
						while(keys.hasNext()){
							String key=(String)keys.next();
							if(personaction.getCatId().equals(key)){
								Float scoretemp = hashMap.get(key);
								hashMap.remove(key);
								hashMap.put(personaction.getCatId(), scoretemp+cart);
								flag=true;
								break;
							}
						}
						if(flag){
							break;
						}
					}
				}else{
					HashMap<String,Float> m=new HashMap<>();
					m.put(personaction.getCatId(), cart);
					class_cat.get(personaction.getUserClass()).add(m);
				}
			}
			}
		}
	}	
	
	public void writeClassBrandMapDict(PersonAction personaction) {
		switch (personaction.getAction()) {
		case "0": {
			if ( class_brand.get(personaction.getUserClass())== null) {
				ArrayList<HashMap<String,Float>> tempList=new ArrayList<>();
				HashMap<String,Float> m=new HashMap<String,Float>();
				m.put(personaction.getBrandId(), click);
				tempList.add(m);
				class_cat.put(personaction.getUserClass(), tempList);
			} else {
				if (hasBrandID(personaction)) {
					ArrayList<HashMap<String,Float>> tempList=class_brand.get(personaction.getUserClass());
					for (HashMap<String, Float> hashMap : tempList) {
						Iterator keys=hashMap.keySet().iterator();
						boolean flag=false;
						while(keys.hasNext()){
							String key=(String)keys.next();
							if(personaction.getBrandId().equals(key)){
								Float scoretemp = hashMap.get(key);
								hashMap.remove(key);
								hashMap.put(personaction.getBrandId(), scoretemp+click);
								flag=true;
								break;
							}
						}
						if(flag){
							break;
						}
					}
				}else{
					HashMap<String,Float> m=new HashMap<>();
					m.put(personaction.getBrandId(), click);
					class_brand.get(personaction.getUserClass()).add(m);
				}
			}
		}
		case "2": {
			if ( class_brand.get(personaction.getUserClass())== null) {
				ArrayList<HashMap<String,Float>> tempList=new ArrayList<>();
				HashMap<String,Float> m=new HashMap<String,Float>();
				m.put(personaction.getBrandId(), purchase);
				tempList.add(m);
				class_cat.put(personaction.getUserClass(), tempList);
			} else {
				if (hasBrandID(personaction)) {
					ArrayList<HashMap<String,Float>> tempList=class_brand.get(personaction.getUserClass());
					for (HashMap<String, Float> hashMap : tempList) {
						Iterator keys=hashMap.keySet().iterator();
						boolean flag=false;
						while(keys.hasNext()){
							String key=(String)keys.next();
							if(personaction.getBrandId().equals(key)){
								Float scoretemp = hashMap.get(key);
								hashMap.remove(key);
								hashMap.put(personaction.getBrandId(), scoretemp+purchase);
								flag=true;
								break;
							}
						}
						if(flag){
							break;
						}
					}
				}else{
					HashMap<String,Float> m=new HashMap<>();
					m.put(personaction.getBrandId(), purchase);
					class_brand.get(personaction.getUserClass()).add(m);
				}
			}
		}
		case "3": {
			if ( class_brand.get(personaction.getUserClass())== null) {
				ArrayList<HashMap<String,Float>> tempList=new ArrayList<>();
				HashMap<String,Float> m=new HashMap<String,Float>();
				m.put(personaction.getBrandId(), favorite);
				tempList.add(m);
				class_cat.put(personaction.getUserClass(), tempList);
			} else {
				if (hasBrandID(personaction)) {
					ArrayList<HashMap<String,Float>> tempList=class_brand.get(personaction.getUserClass());
					for (HashMap<String, Float> hashMap : tempList) {
						Iterator keys=hashMap.keySet().iterator();
						boolean flag=false;
						while(keys.hasNext()){
							String key=(String)keys.next();
							if(personaction.getBrandId().equals(key)){
								Float scoretemp = hashMap.get(key);
								hashMap.remove(key);
								hashMap.put(personaction.getBrandId(), scoretemp+favorite);
								flag=true;
								break;
							}
						}
						if(flag){
							break;
						}
					}
				}else{
					HashMap<String,Float> m=new HashMap<>();
					m.put(personaction.getBrandId(), favorite);
					class_brand.get(personaction.getUserClass()).add(m);
				}
			}
		}
		case "1": {
			if ( class_brand.get(personaction.getUserClass())== null) {
				ArrayList<HashMap<String,Float>> tempList=new ArrayList<>();
				HashMap<String,Float> m=new HashMap<String,Float>();
				m.put(personaction.getBrandId(), cart);
				tempList.add(m);
				class_cat.put(personaction.getUserClass(), tempList);
			} else {
				if (hasBrandID(personaction)) {
					ArrayList<HashMap<String,Float>> tempList=class_brand.get(personaction.getUserClass());
					for (HashMap<String, Float> hashMap : tempList) {
						Iterator keys=hashMap.keySet().iterator();
						boolean flag=false;
						while(keys.hasNext()){
							String key=(String)keys.next();
							if(personaction.getBrandId().equals(key)){
								Float scoretemp = hashMap.get(key);
								hashMap.remove(key);
								hashMap.put(personaction.getBrandId(), scoretemp+cart);
								flag=true;
								break;
							}
						}
						if(flag){
							break;
						}
					}
				}else{
					HashMap<String,Float> m=new HashMap<>();
					m.put(personaction.getBrandId(), cart);
					class_brand.get(personaction.getUserClass()).add(m);
				}
			}
		}
		}
	}
	public void setClassCatAndBrandDict(List<String> Cat,List<String> Brand,List<PersonAction> user_log){
		for (int i = 0; i < user_log.size(); i++) {
			if(Cat.contains(user_log.get(i).getCatId())){
					this.writeClassCatMapDict(user_log.get(i));
			}
			if(Brand.contains(user_log.get(i).getBrandId())){
				    this.writeClassBrandMapDict(user_log.get(i));
			}
		}
	}
	
	public static void setCatAndBrandDict() {
		HashMap<Integer,ArrayList<HashMap<String,Float>>>  cat=class_cat;
		HashMap<Integer,ArrayList<HashMap<String,Float>>>  brand=class_brand;
		Iterator iterCat=cat.keySet().iterator();
		Iterator iterBrand=brand.keySet().iterator();
		while(iterCat.hasNext()){
			Integer key=(Integer)iterCat.next();
			ArrayList<HashMap<String,Float>> list=cat.get(key);
			StringBuffer content=new StringBuffer();
			content.append(key);
			for (int i = 0; i < list.size(); i++) {
				HashMap<String,Float> temp=list.get(i);
				Iterator itertemp=temp.keySet().iterator();
				String tempkeycat=(String) itertemp.next();
				Double tempvaluecat=temp.get(tempkeycat)/10.;
				content.append(" "+tempkeycat+":"+tempvaluecat);
			}
			File_IO.writeDict("Class_Cat.txt", content + "\r\n");
		}
		while(iterBrand.hasNext()){
			Integer key=(Integer)iterBrand.next();
			ArrayList<HashMap<String,Float>> list=brand.get(key);
			StringBuffer content=new StringBuffer();
			content.append(key);
			for (int i = 0; i < list.size(); i++) {
				HashMap<String,Float> temp=list.get(i);
				Iterator itertemp=temp.keySet().iterator();
				String tempkeybrand=(String) itertemp.next();
				Double tempvaluebran = temp.get(tempkeybrand)/10.;
				content.append(" "+tempkeybrand+":"+tempvaluebran);
			}
			File_IO.writeDict("Class_Brand.txt", content + "\r\n");
		}
	}
	
	public static boolean hasBrandID(PersonAction ps){
		ArrayList<HashMap<String,Float>> tempList=class_brand.get(ps.getUserClass());
		for (HashMap<String, Float> hashMap : tempList) {
			Iterator keys=hashMap.keySet().iterator();
			boolean flag=false;
			while(keys.hasNext()){
				String key=(String)keys.next();
				if(ps.getBrandId().equals(key)){
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean hasCatID(PersonAction ps){
		ArrayList<HashMap<String,Float>> tempList=class_cat.get(ps.getUserClass());
		for (HashMap<String, Float> hashMap : tempList) {
			Iterator keys=hashMap.keySet().iterator();
			boolean flag=false;
			while(keys.hasNext()){
				String key=(String)keys.next();
				if(ps.getCatId().equals(key)){
					return true;
				}
			}
		}
		return false;
	}

}
