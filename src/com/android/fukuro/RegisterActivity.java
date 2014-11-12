package com.android.fukuro;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity implements View.OnClickListener{

	AlertDialog alertDialog;
	EditText input;
	AlertDialog.Builder errorD;

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
                    	String user = input.getText().toString();
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
	    RegisterUserTask ru = new RegisterUserTask(this);
		ru.execute(user_name);
	}
}
