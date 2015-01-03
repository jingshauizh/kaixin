package com.kaixin001.activity;


import com.kaixin001.engine.LoginEngine;
import com.kaixin001.engine.NewsEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.model.NewsModel;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class NewsActivity extends Activity {
	private final String TAG = "NewsActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.news, menu);
		return true;
	}
	
	public void ButtonClick(View v){
		Log.i(TAG,"ButtonClick");
		GetDataTask getDataTask = new GetDataTask();
		getDataTask.execute(new String[] { null});
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
	
	private class GetDataTask extends AsyncTask<String, Void, Integer>
	{
		@Override
		protected Integer doInBackground(String[] paramArrayOfString)
		{
			// TODO Auto-generated method stub

			NewsModel newsModel = new NewsModel();
			NewsEngine instance = NewsEngine.getInstance();
			try {
				instance.getNewsData(NewsActivity.this, newsModel, "0", "20", null, null, null, null);
			} catch (SecurityErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return -1;

		}
	}
}
