package com.android.fukuro;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{
	
	private DBHelper dbHelper = new DBHelper(this);
	
	public static SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//読み書き可能なデータベースをオープン
		// 読み取り専用の場合はgetReadableDatabase()を用いる
		db = dbHelper.getWritableDatabase();
	}
	
	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		//ボタンをIDで探してボタン変数をつくる
		Button btnPost = (Button)findViewById(R.id.btnPost);
		Button btnC = (Button)findViewById(R.id.btnC);
		Button btnR = (Button)findViewById(R.id.btnR);
		Button btnM = (Button)findViewById(R.id.btnM);
		//ボタン変数にリスナーを登録する
		btnPost.setOnClickListener(this);
		btnC.setOnClickListener(this);
		btnR.setOnClickListener(this);
		btnM.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		switch(v.getId()){//どのボタンが押されたか判定
		case R.id.btnPost://btnPostが押された
    		uploadCoordi("0000001","/data/data/com.android.fukuro/Item/item_all2.png","/data/data/com.android.fukuro/Item/item_all3.png");
			//postGood("10003.png");
			break;
		case R.id.btnC:
			//インテントのインスタンス生成
			Intent intent =new Intent(MainActivity.this, JsonActivity.class);
			//次画面のアクティビティ起動
			startActivity(intent);
			break;
		case R.id.btnR:
			//インテントのインスタンス生成
			Intent intentR =new Intent(MainActivity.this, RegisterActivity.class);
			//次画面のアクティビティ起動
			startActivity(intentR);
			break;
		case R.id.btnM:
			Intent vIntent = new Intent(this, Uplist.class);
			startActivity(vIntent);
			break;
		}
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		dbHelper.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void uploadCoordi(String... str){
//		//Task生成
//	    UploadAsyncTask up = new UploadAsyncTask(this);
//		up.execute(str[0],str[1],str[2]);
	}
	
	public void postGood(String filename){
		//Task生成
	    GoodAsyncTask pg = new GoodAsyncTask(this);
		pg.execute(filename);
	}
}
