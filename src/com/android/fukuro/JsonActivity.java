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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class JsonActivity extends Activity{

	ListView myListView;
	String result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_json);
		//StrictModeを設定 penaltyDeathを取り除く
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		
		String json = get();
		try {
			JSONObject rootObject = new JSONObject(json);
			JSONArray itemArray = rootObject.getJSONArray("item");
			for (int i = 0; i < itemArray.length(); i++) {
			    JSONObject jsonObject = itemArray.getJSONObject(i);
			 // アイテムを追加します
		        adapter.add(jsonObject.getString("ranking_item"));
			    Log.d("json",jsonObject.getString("ranking_item"));
			}
			System.out.print(rootObject);
		} catch (JSONException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		


        ListView listView = (ListView) findViewById(R.id.listView1);
        // アダプターを設定します
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }
    
    public String get(){
		try {
		      HttpClient httpClient = new DefaultHttpClient();
		      HttpPost request = new HttpPost("http://koyoshi.php.xdomain.jp/php/rank10.php");

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
}
