package com.android.fukuro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class JsonActivity extends Activity implements DownloadListTaskCallback {

	ListView myListView;
	String result = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_json);
		
		DownloadImage();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }
    

	public void DownloadImage(){
		DownloadListTask task = new DownloadListTask(this, this);
	     task.execute("");
	}

	@Override
	public void onSuccessDownloadList(String result) {
		// TODO 自動生成されたメソッド・スタブ
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		
		try {
			JSONObject rootObject = new JSONObject(result);
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
	public void onFailedDownloadList() {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
