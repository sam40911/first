package edu.ntust.afda.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import edu.ntust.afda.R;

public class MainActivity extends Activity {

	ImageView imageViewSplash;
	int screenWidth;
	int screenHeight;
	WindowManager windowManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main2);
		
		 windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		 screenWidth = windowManager.getDefaultDisplay().getWidth();
		screenHeight = windowManager.getDefaultDisplay().getHeight();
		
//		findView();
		Loading();
		
	}

	private void Loading() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				jumpToIndex();
			}
		}, 2000);
		
	}

	protected void jumpToIndex() {
		Intent intent = new Intent();
		intent.setClass(MainActivity.this, ApkFileActivity.class);
		startActivity(intent);
		finish();
		
	}

	private void findView() {
		 imageViewSplash= (ImageView) findViewById(R.id.imageViewSplash);
		 imageViewSplash.getLayoutParams().height = screenHeight;
		 imageViewSplash.getLayoutParams().width = screenWidth;
		
	}

}
