package com.android.fukuro;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class PostExecuteSQL extends AsyncTask<String, Integer, Integer> {
	  ProgressDialog dialog;
	  Context context;
	  
	  public PostExecuteSQL(Context context){
	    this.context = context;
	  }
	  
	  @Override
	  protected Integer doInBackground(String... params) {

	    try {
	      String strJson = "[{\"user_id\":\"" + params[0] + "\",\"item_path\":\"" + params[1] + "\",\"thambnail_path\":\"" + params[2] + "\"}]";
	      
	      HttpClient httpClient = new DefaultHttpClient();
	      HttpPost request = new HttpPost("http://koyoshi.php.xdomain.jp/php/getexecutesql.php");
	      StringEntity body = new StringEntity(strJson);
	      request.addHeader("Content-type", "application/json");
	      request.setEntity(body);
	      Log.v("strJson", strJson);
	      //Response
	      String result = httpClient.execute(request, new ResponseHandler<String>(){
	            public String handleResponse(HttpResponse response) throws IOException{
	                switch(response.getStatusLine().getStatusCode()){
	                case HttpStatus.SC_OK:
	                    System.out.println(HttpStatus.SC_OK);
	                    return EntityUtils.toString(response.getEntity(), "UTF-8");
	                case HttpStatus.SC_NOT_FOUND:
	                    System.out.println(HttpStatus.SC_NOT_FOUND);
	                    return "404";
	                default:
	                    System.out.println("unknown");
	                    return "unknown";
	                }
	            }
	        });
	        System.out.println(result);
	        httpClient.getConnectionManager().shutdown();
	    } catch (ClientProtocolException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return 0;
	  }

	  @Override
	  protected void onPostExecute(Integer result) {
	    if(dialog != null){
	      dialog.dismiss();
	    }
	  }

	  @Override
	  protected void onPreExecute() {
	    dialog = new ProgressDialog(context);
	    dialog.setTitle("Please wait");
	    dialog.setMessage("Uploading...");
	    dialog.show();
	  }  
}
