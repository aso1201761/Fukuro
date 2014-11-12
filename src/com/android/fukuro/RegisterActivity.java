package com.android.fukuro;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity implements View.OnClickListener , RegisterUserTaskCallback {

	AlertDialog alertDialog;
	EditText input;
	AlertDialog.Builder errorD;
	private DBHelper dbHelper = new DBHelper(this);
	public static SQLiteDatabase db;
	String user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_user);
		
		errorD = new AlertDialog.Builder(this);
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // アラートダイアログのタイトルを設定します
        alertDialogBuilder.setTitle("名前を入力してください");
        // アラートダイアログのEditTextを設定します
        input = new EditText(this);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        alertDialogBuilder.setView(input);
        // アラートダイアログのOKボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    	user = input.getText().toString();
                    	if(user == null || user.length() == 0){
                    		 
                    	    // ダイアログの設定
                    	    errorD.setTitle("入力エラー");          //タイトル
                    	    errorD.setMessage("名前を入力してください");      //内容
                    	 
                    	    errorD.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    	        public void onClick(DialogInterface dialog, int which) {
                    	            // TODO 自動生成されたメソッド・スタブ
                    	        }
                    	    });
                    	 
                    	    // ダイアログの作成と表示
                    	    errorD.create();
                    	    errorD.show();
                    	 
                    	}else{
                    		regUser(user);
                    		input.setText("");
                    	}
                    }
                });
        // アラートダイアログのCancelボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    	input.setText("");
                    }
                });
        // アラートダイアログのキャンセルが可能かどうかを設定します
        alertDialogBuilder.setCancelable(true);
        alertDialog = alertDialogBuilder.create();
	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		//ボタンをIDで探してボタン変数をつくる
		Button btnReg = (Button)findViewById(R.id.btnReg);
		//ボタン変数にリスナーを登録する
		btnReg.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		switch(v.getId()){//どのボタンが押されたか判定
		case R.id.btnReg://btnPostが押された
			// アラートダイアログを表示します
	        alertDialog.show();
			break;
		}
	}

	public void regUser(String user_name) {
		//Task生成
	    RegisterUserTask ru = new RegisterUserTask(this,this);
		ru.execute(user_name);
	}

	@Override
	public void onSuccessRegisterUser(String result) {
		// TODO 自動生成されたメソッド・スタブ
		//端末のDBにuser_idを登録
		//読み書き可能なデータベースをオープン
		// 読み取り専用の場合はgetReadableDatabase()を用いる
		db = dbHelper.getWritableDatabase();
		SQLiteStatement stmt = db.compileStatement("INSERT INTO User(user_id, user_name) VALUES(?, ?)");
		stmt.bindString(1, result);
		stmt.bindString(2, user);
		stmt.executeInsert();
		Toast.makeText(getApplicationContext(), "データを登録しました。",
		Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onFailedRegisterUser() {
		// TODO 自動生成されたメソッド・スタブ
		// ダイアログの設定
	    errorD.setTitle("エラーが発生しました");          //タイトル
	    errorD.setMessage("ネットワークエラー");      //内容
	 
	    errorD.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	            // TODO 自動生成されたメソッド・スタブ
	        }
	    });
	 
	    // ダイアログの作成と表示
	    errorD.create();
	    errorD.show();
	}

	@Override
	public void onFailedFound(String result) {
		// TODO 自動生成されたメソッド・スタブ
		if(result == "unknown"){
			// TODO 自動生成されたメソッド・スタブ
			// ダイアログの設定
			errorD.setTitle("エラーが発生しました");          //タイトル
			errorD.setMessage("原因不明のエラー");      //内容
		 
			errorD.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		            // TODO 自動生成されたメソッド・スタブ
		        }
		    });
		 
		    // ダイアログの作成と表示
		    errorD.create();
		    errorD.show();
		}else if(result == "404"){
			// TODO 自動生成されたメソッド・スタブ
			// ダイアログの設定
			errorD.setTitle("エラーが発生しました");          //タイトル
			errorD.setMessage("ファイルが見つかりませんでした");      //内容
		 
			errorD.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		            // TODO 自動生成されたメソッド・スタブ
		        }
		    });
		 
		    // ダイアログの作成と表示
		    errorD.create();
		    errorD.show();
		}
	}
}
