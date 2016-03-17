package com.weebinar.maventest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;

public class PerformanceTest implements Runnable{

	static CloudantClient client= null;
	static{
		try {
			client = ClientBuilder.url(new URL("https://192.168.0.9:5984"))
					.disableSSLAuthentication()
					.build();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public PerformanceTest(){
		
	}
	public void run() {
		
	}
	
	
	public static void insert() throws InterruptedException{
		Database db = client.database("stat", false);
		for ( int i = 1; i <10; i++){
			Thread.sleep(1000);
			String shopId = "";
			if ( i%2==0) shopId = "a"; 
			else shopId = "b"; 
			DocBean bean = new DocBean(shopId, i, i);
			Response result = db.post(bean);
			System.out.println(result.getId()+ " shopId : " + bean.getStopId()+" date : " + new Date(bean.getCreateDt()).toGMTString());
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		PerformanceTest.insert();
	}

}
