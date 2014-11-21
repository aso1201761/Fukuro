package com.android.fukuro;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.View;

public class RankingMenu extends Activity implements View.OnClickListener,DownloadImageTaskCallback {

	AlertDialog.Builder errorD;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rankingmenu);
		errorD = new AlertDialog.Builder(this);
		//イメージボタンの設定
		ImageButton btnrank = (ImageButton)findViewById(R.id.btnRank);
		ImageButton btnlater = (ImageButton)findViewById(R.id.btnLater);
		btnrank.setOnClickListener(this);
		btnlater.setOnClickListener(this);
	}
	
	@Override
    protected void onResume(){
        super.onResume();
        DownloadImage();
    }
	
	public void DownloadImage(){
		DownloadImageTask dit = new DownloadImageTask(this, this);
	    dit.execute("toplater3");
	}
	
	@Override
	public void onSuccessDownloadImage(List<Bitmap> bitmaplist ,List<String> filenames) {
		// TODO 自動生成されたメソッド・スタブ
		//viewの生成
		ImageView oImg;
		ArrayList<Integer> imglist = new ArrayList<Integer>();
		imglist.add(R.id.rank1);
		imglist.add(R.id.rank2);
		imglist.add(R.id.rank3);
		imglist.add(R.id.later1);
		imglist.add(R.id.later2);
		imglist.add(R.id.later3);
		for(int i = 0 ; i < bitmaplist.size(); i++){
			oImg =(ImageView)findViewById(imglist.get(i));
			oImg.setImageBitmap(bitmaplist.get(i));
        }
	}

	@Override
	public void onFailedDownloadImage() {
		// TODO 自動生成されたメソッド・スタブ
	}
	
	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		switch(v.getId()){//どのボタンが押されたか判定
		case R.id.btnRank://btnRankが押された
			Intent intentRank = new Intent(this, Top50Activity.class);
			startActivity(intentRank);
			break;
		case R.id.btnLater://btnLaterが押された
			Intent intentLater = new Intent(this, Later50Activity.class);
			startActivity(intentLater);
			break;
		}
	}
}