package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

import classfication.Classfication;
import model.PersonAction;
import util.database.Db_func;

public class TrainUser {
	public static int flag = 0;
	public static LinkedBlockingQueue<PersonAction> trainlist = new LinkedBlockingQueue<PersonAction>();
	private Thread getDataThread;
	private Thread classfyThread;
	
	public Thread getDataThread() {
		return getDataThread;
	}


	public Thread classfyThread() {
		return classfyThread;
	}
	
	/**
	 * @Title: main
	 * @Description: TODO
	 * @param @param args    设定文件
	 * @return void    返回类型
	 * @throws
	 */
	public TrainUser(){
		getDataThread = new Thread(new GetData());
		classfyThread = new Thread(new GetClassfy());
	}
	
	public void start(){
		getDataThread.start();
		classfyThread.start();
	}
	
	//获取用户数据
	private class GetData implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Db_func.get_classfy();
		}
	}
	
	
	//获取用户分类
		private class GetClassfy implements Runnable{
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while(true){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					List<PersonAction> cachelist = new ArrayList<>();
					System.out.println("trainlist:"+trainlist.size());   //打印测试
					trainlist.drainTo(cachelist);
					System.out.println("cachelist:"+cachelist.size());
					String id = "15442";
					List<PersonAction> cachelist2 = new ArrayList<>();
					if (cachelist!=null) {
						for (PersonAction personAction : cachelist) {
							if (personAction.getUserId().equals(id)) {
								cachelist2.add(personAction);
							}else{
								Classfication.classfy(cachelist2);
								System.out.println("---------one done-----------");
								cachelist2 = new ArrayList<>();
								id = personAction.getUserId();
								cachelist2.add(personAction);
							}
						}
					}
					if (Db_func.flag) {
						break;
					}
				}
			}
		}
	
	
}
