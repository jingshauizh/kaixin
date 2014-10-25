/*******************************************************************************************
 * @author Jason Zhang
 * email: jingshuaizh@163.com
 * blog:  http://blog.csdn.net/jingshuaizh
 * 
 * @date Oct 2, 2014
 ***********************************************************************************************/
package com.kaixin001.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.kaixin001.engine.LoginEngine;
import com.kaixin001.engine.SecurityErrorException;

public class LoginActivity extends Activity
{
	private TextView textView;
	private GetDataTask getDataTask;
	private AutoCompleteTextView txtID;
	private EditText txtPassword;
	private LoginEngine loginEngine;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		textView = (TextView) findViewById(R.id.kaixin_title_bar_center_text);
		Log.e("this.getString(R.string.login_btn)=",
				this.getString(R.string.login_btn));
		textView.setText(this.getString(R.string.login_btn));

		this.txtID = ((AutoCompleteTextView) findViewById(R.id.login_etID));
		this.txtPassword = ((EditText) findViewById(R.id.login_etPwd));
	}

	public void onClickGetPlataData(View v)
	{
		Log.e("loginClick", "loginClick");
		dologin();
	}

	private void dologin()
	{
		doLogin(null, null, "login");
	}

	private void doLogin(String paramString1, String paramString2,
			String paramString3)
	{
		String str1 = String.valueOf(this.txtID.getText()).trim();
		String str2 = String.valueOf(this.txtPassword.getText());
		doLogin(false, str1, str2, null, paramString1, paramString2, null,
				paramString3);
	}

	private void doLogin(boolean paramBoolean, String paramString1,
			String paramString2, String paramString3, String paramString4,
			String paramString5, String paramString6, String paramString7)
	{

		// this.loginEngine.enableNewMessageNotification(false);

		this.getDataTask = new GetDataTask();
		this.getDataTask.execute(new String[] { paramString1, paramString2,
				paramString3, paramString4, paramString5, paramString6,
				paramString7 });

		// if (paramString7.equals("look"));

	}

	private class GetDataTask extends AsyncTask<String, Void, Integer>
	{
		@Override
		protected Integer doInBackground(String[] paramArrayOfString)
		{
			// TODO Auto-generated method stub

			try
			{
				loginEngine = LoginEngine.getInstance();
				KXApplication app = KXApplication.getInstance();
				loginEngine.doLogin(LoginActivity.this, app,
						paramArrayOfString[0], paramArrayOfString[1],
						paramArrayOfString[2], paramArrayOfString[3],
						paramArrayOfString[4], paramArrayOfString[5],
						paramArrayOfString[6]);

			}
			catch (SecurityErrorException localSecurityErrorException)
			{
				return null;
			}
			return null;

		}
	}
}
