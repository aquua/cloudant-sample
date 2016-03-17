package com.weebinar.maventest;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.cloudant.client.api.views.Key;
import com.cloudant.client.api.views.Key.ComplexKey;
import com.cloudant.client.api.views.ViewResponse;

public class Sample {

	public static CloudantClient client = ClientBuilder.account("pinky")
			.username("pinky")
			.password("pinkypinky")

			.build();
	private static Calendar start 	= Calendar.getInstance();
	private static Calendar end 	= Calendar.getInstance();
	static{
		start.set(Calendar.MONTH,0);
		start.set(Calendar.DAY_OF_MONTH, 1);
		start.set(Calendar.YEAR, 2016);

		end.set(Calendar.MONTH, 2);
		end.set(Calendar.DAY_OF_MONTH, 2);
		end.set(Calendar.YEAR, 2016);
	}

	public static void main(String agsp[])throws Exception {

		//		String url = "https://pinky.cloudant.com/dashboard.html#/database/stat/_design/view/_view/shop_multikey?keys=%5B%5B+4%2C+%22a%22+%5D%5D";
		//		System.out.println(URLDecoder.decode(url));

		System.out.println("start : " + new Date(start.getTimeInMillis()).toLocaleString());
		System.out.println("end   : " + new Date(end.getTimeInMillis()).toLocaleString());
		shopSumRange();
		shopRange();
		toDate();

		//		insert();
		client.shutdown();
	}


	public static void shopSumRange() throws IOException{
		System.out.println("shopSumRange method");

		Database db = client.database("stat", false);
		ViewResponse<ComplexKey, Object> r = db.getViewRequestBuilder("view","string_long")
				.newRequest(Key.Type.COMPLEX, Object.class)
				.startKey(Key.complex("a").add(start.getTimeInMillis()))
				.endKey(Key.complex("a").add(end.getTimeInMillis()))
				.build()
				.getResponse();
		System.out.println(r.getValues());
	}

	public static void shopRange() throws IOException{
		System.out.println("shopRange method");



		Database db = client.database("stat", false);
		List<DocBean> r = db.getViewRequestBuilder("view","shop_range")
				.newRequest(Key.Type.COMPLEX, Object.class)
				.startKey(Key.complex("a").add(start.getTimeInMillis()))
				.endKey(Key.complex("a").add(end.getTimeInMillis()))
				.includeDocs(true)
				.build()
				.getResponse()
				.getDocsAs(DocBean.class);

		for ( DocBean bean : r){
			System.out.println(new Date(bean.getCreateDt()).toLocaleString());
		}

	}

	public static void toDate() throws IOException{
		System.out.println("toDate method");
		Database db = client.database("stat", false);
		List<DocBean> r = db.getViewRequestBuilder("view","shop")
				.newRequest(Key.Type.COMPLEX, Object.class)
				.startKey(Key.complex("a").add(start.getTimeInMillis()))
				.endKey(Key.complex("a").add(end.getTimeInMillis()))
				.includeDocs(true)
				.build()
				.getResponse()
				.getDocsAs(DocBean.class);

		for ( DocBean bean : r){
			System.out.println(new Date(bean.getCreateDt()).toLocaleString());
		}

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
}

