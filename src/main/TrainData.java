package main;

import util.database.Db_func;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import classfication.Dict;
import classfication.PublicDict;
import model.PersonAction;


public class TrainData {
	private Thread getPersonThread;
	private Thread getActionThread;
	private Thread trainDict;
	private Thread trainCatAndBrand;

	public static Map<String, Integer> basePersonMap = new ConcurrentHashMap<String, Integer>();
	public static LinkedBlockingQueue<PersonAction> baseActionList = new LinkedBlockingQueue<PersonAction>();
	public static String[] cat_dictname = { "cat_dict_class1.txt", "cat_dict_class2.txt", "cat_dict_class3.txt", "cat_dict_class4.txt",
		"cat_dict_class5.txt", "cat_dict_class6.txt", "cat_dict_class7.txt", "cat_dict_class8.txt", "cat_dict_class9.txt",
		"cat_dict_class10.txt", "cat_dict_class11.txt", "cat_dict_class12.txt" };
	public static String[] brand_dictname = { "brand_dict_class1.txt", "brand_dict_class2.txt", "brand_dict_class3.txt",
		"brand_dict_class4.txt", "brand_dict_class5.txt", "brand_dict_class6.txt", "brand_dict_class7.txt", "brand_dict_class8.txt",
		"brand_dict_class9.txt", "brand_dict_class10.txt", "brand_dict_class11.txt", "brand_dict_class12.txt" };

	public Thread getPersonThread() {
		return getPersonThread;
	}


	public Thread getActionThread() {
		return getActionThread;
	}
	public Thread getTrainDict() {
		return trainDict;
	}
	public void setTrainDict(Thread trainDict) {
		this.trainDict = trainDict;
	}
	public Thread getTrainCatAndBrand() {
		return trainCatAndBrand;
	}
	


	/**
	 * @Title: main
	 * @Description: TODO
	 * @param @param args    设定文件
	 * @return void    返回类型
	 * @throws
	 */
	public TrainData(){
		getPersonThread = new Thread(new GetPerson());
		getActionThread = new Thread(new GetPersonAction());
		trainDict = new Thread(new TrainDict());
		trainCatAndBrand = new Thread(new GetTrainCatAndBrand());
	}

	
	public void start(){
		getPersonThread.start();
		getActionThread.start();
		trainDict.start();
	}
	
	public void start2(){
		getPersonThread.start();
		getActionThread.start();
		trainCatAndBrand.start();
	}
	
	//获取用户及其类别
	private class GetPerson implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			int pre = 0;
			int next = 1000;
			while(true){
				Map<String,Integer> personMap = Db_func.get_pid(pre, next);
				pre+=1000;
				next+=1000;
				if (personMap.size()!=0) {
					basePersonMap.putAll(personMap);
				}else {
					break;
				}
				System.out.println("mapsize:"+basePersonMap.size());
				try {
					Thread.sleep(10000);//等待20秒，即每20秒取1000个用户
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	//获取用户行为并用list封装
	private class GetPersonAction implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(21000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int i = 1;
			while(true){
				//遍历用户,进行行为查询
				
				if (!basePersonMap.isEmpty()) {
					Map<String, Integer> cacheMap = new HashMap<String, Integer>();
					cacheMap.putAll(basePersonMap);
					System.out.println("cachemapsize:"+cacheMap.size());
					basePersonMap.clear();
					
					for (Entry<String, Integer> entry : cacheMap.entrySet()) {
						List<PersonAction> list = Db_func.get_action(entry.getKey(),entry.getValue());
						//System.out.println("listsize:"+baseActionList.size());
						baseActionList.addAll(list);
						if (i%500==0) {
							System.out.println(i+" personAction get------");
						}
						i++;
					}
					System.out.println("------once-----");
				}else{
					break;
				}
			}		
		}

	}
	
	//获取用户行为并用list封装
		private class TrainDict implements Runnable{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(12000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while(true){
					try {
						Thread.sleep(20000);  //等待20秒，批量处理baseActionList
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//训练字典
					if (baseActionList.size()!=0) {
						List<PersonAction> list = new ArrayList<>();
						System.out.println("listsize:"+baseActionList.size());
						baseActionList.drainTo(list);
						System.out.println("---dictwork---");
						new Dict().setMapDict(list);
					}else{
						Dict.setDict();
						break;
					}
					
				}
			}		
		}
		
		//获取公共词典，并且去遍历用户行为
		private class GetTrainCatAndBrand implements Runnable{
			@Override
			public void run() {
				try {
					Thread.sleep(12000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				List<String> overallCat=new Db_func().getOverall("overall_cat", "cat_id");
				List<String> overallBrand=new Db_func().getOverall("overall_brand", "brand_id");
				
				while(true){
					try {
						Thread.sleep(20000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//训练类别与CatAndBrand
					if (baseActionList.size()!=0) {
						List<PersonAction> list = new ArrayList<>();
						System.out.println("listsize:"+baseActionList.size());
						baseActionList.drainTo(list);
						System.out.println("---brandwork---");
						new PublicDict().setClassCatAndBrandDict(overallCat,overallBrand,list);
					}else{
						PublicDict.setCatAndBrandDict();
						break;
					}
				}
			}
		}

}

