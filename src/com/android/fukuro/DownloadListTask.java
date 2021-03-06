package com.android.fukuro;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class DownloadListTask 
  extends AsyncTask<String, Integer, String> {
  private DownloadListTaskCallback callback;
  ProgressDialog dialog;
  Context context;
  String result = null;
  
  public DownloadListTask(Context context,DownloadListTaskCallback callback){
    this.context = context;
    this.callback = callback;
  }
  
  @Override
  protected String doInBackground(String... params) {
	  try {
	      HttpClient httpClient = new DefaultHttpClient();
	      HttpPost request = new HttpPost("http://koyoshi.php.xdomain.jp/php/"+params[0]+".php");

	      //Response
	      result = httpClient.execute(request, new ResponseHandler<String>(){
	          @Override
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
	return result;
  }

  @Override
  protected void onPostExecute(String result) {
//    if(dialog != null){
//      dialog.dismiss();
//    }
	  if (result == null) {
        // エラーをコールバックで返す
        callback.onFailedDownloadList();
      } else if(result == "unknown" || result == "404"){
        //エラーダイアログ表示
        callback.onFailedFound(result);
      }else{
        // ダウンロードしたリストをコールバックでを返す
        callback.onSuccessDownloadList(result);
      }
  }

  @Override
  protected void onPreExecute() {
//    dialog = new ProgressDialog(context);
//    dialog.setTitle("Please wait");
//    dialog.setMessage("Downloading...");
//    dialog.show();
  }  
}