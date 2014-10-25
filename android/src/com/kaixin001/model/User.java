package com.kaixin001.model;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.text.TextUtils;

import com.kaixin001.db.UserDBAdapter;
import com.kaixin001.util.KXLog;

//import com.kaixin001.activity.KXApplication;

public class User extends KXModel
{
	private static final String TAG = "User";
	private static volatile User instance;
	private static final long serialVersionUID = 1L;
	public int fnum = -1;
	public int imcomplete = -1;
	private int isLookAround = -1;
	private String mCoverId = "";
	private String mCoverPath = "";
	private String mCoverUrl = "";
	private long mExpiresIn = -1L;
	private boolean mNeedRefresh = false;
	private String mOnline = "0";
	private String mState = "";
	private UserLevel mUserLevel;
	private String strAccessToken = "";
	private String strAccount = "";
	private String strLogo = "";
	private String strLogo120 = "";
	private String strName = "";
	private String strOAuthTokenSecret = "";
	private String strPassword = "";
	private String strRefreshToken = "";
	private String strReserved = "";
	private String strUID = "";
	private String strWapVerify = "";

	private static void clearInstance()
	{
		instance = null;
	}

	public static User getInstance()
	{
		if (instance == null)
		{
			instance = new User();
		}

		User localUser = instance;
		return localUser;
	}

	public boolean GetLookAround()
	{
		return this.isLookAround == 1;
	}

	public void clear()
	{
		clearInstance();
	}

	public void clearAccout()
	{
		this.strAccessToken = "";
		logout();
	}

	public void clearLookAroundFlag()
	{
		this.isLookAround = 0;
	}

	public String getAccount()
	{
		return this.strAccount;
	}

	public String getCoverId()
	{
		return this.mCoverId;
	}

	public String getCoverPath()
	{
		return Environment.getExternalStorageDirectory() + "/kaixin001" + "/"
				+ "kx_home_bg_temp.jpg";
	}

	public String getCoverUrl()
	{
		return this.mCoverUrl;
	}

	public long getExpiresIn()
	{
		return this.mExpiresIn;
	}

	public String getLogo()
	{
		if (GetLookAround())
			return null;
		return this.strLogo;
	}

	public String getLogo120()
	{
		if (GetLookAround())
			return null;
		return this.strLogo120;
	}

	public String getOauthToken()
	{
		return this.strAccessToken;
	}

	public String getOauthTokenSecret()
	{
		return this.strOAuthTokenSecret;
	}

	public String getOnline()
	{
		return this.mOnline;
	}

	public String getRealName()
	{
		if (GetLookAround())
			return "GetLookAround";
		return this.strName;
	}

	public String getRefreshToken()
	{
		return this.strRefreshToken;
	}

	public String getReserved()
	{
		return this.strReserved;
	}

	public String getState()
	{
		return this.mState;
	}

	public String getUID()
	{
		return this.strUID;
	}

	public String getWapVerify()
	{
		return this.strWapVerify;
	}

	public UserLevel getmUserLevel()
	{
		return this.mUserLevel;
	}

	public boolean isNeedRefresh()
	{
		return this.mNeedRefresh;
	}

	public void loadDataIfEmpty(Context paramContext)
	{
		if (TextUtils.isEmpty(this.strAccessToken))
			loadUserData(paramContext);
	}

	// ERROR //
	public boolean loadUserData(Context paramContext)
	{
		// UserDBAdapter:getLoginedUser
		UserDBAdapter userDBAdapter = new UserDBAdapter(paramContext);
		Cursor userCursor = userDBAdapter.getLoginedUser();
		this.strPassword = userCursor.getString(userCursor
				.getColumnIndex("password"));
		this.strAccount = userCursor.getString(userCursor
				.getColumnIndex("account"));
		this.strUID = userCursor.getString(userCursor.getColumnIndex("uid"));
		this.strName = userCursor.getString(userCursor.getColumnIndex("name"));
		this.mState = userCursor.getString(userCursor.getColumnIndex("state"));
		this.mCoverUrl = userCursor.getString(userCursor
				.getColumnIndex("coverUrl"));
		this.strLogo = userCursor
				.getString(userCursor.getColumnIndex("logo50"));
		this.strAccessToken = userCursor.getString(userCursor
				.getColumnIndex("access_token"));
		this.strRefreshToken = userCursor.getString(userCursor
				.getColumnIndex("refresh_token"));
		this.strOAuthTokenSecret = userCursor.getString(userCursor
				.getColumnIndex("oauth_token_secret"));
		this.mExpiresIn = userCursor.getInt(userCursor
				.getColumnIndex("expires_in"));
		this.strReserved = userCursor.getString(userCursor
				.getColumnIndex("reserved"));
		return true;

		// (_id INTEGER primary key autoincrement, account TEXT not null,
		// password TEXT,
		// uid TEXT not null, name TEXT, state TEXT, coverUrl TEXT, gender
		// INTEGER,
		// hometown TEXT, city TEXT, status INTEGER, logo50 TEXT, logo120 TEXT,
		// access_token TEXT, refresh_token TEXT, expires_in INTEGER,
		// oauth_token_secret TEXT, reserved TEXT, recent_time_login INTEGER,
		// islogin INTEGER);";
	}

	public void logout()
	{
		this.strWapVerify = "";
		/*
		 * if (this.isLookAround != 1)
		 * saveUserLoginInfo(KXApplication.getInstance()); logoutClear();
		 * com.kaixin001.fragment.NewsFragment.mRefresh = true;
		 * com.kaixin001.activity.MainActivity.isRefresh = true;
		 */
	}

	public void logoutClear()
	{
		this.strUID = "";
		this.strName = "";
		this.mCoverUrl = "";
		this.mCoverId = "";
		this.mState = "";
		this.strLogo = "";
		this.strAccessToken = "";
		this.strRefreshToken = "";
		this.strOAuthTokenSecret = "";
		this.mExpiresIn = -1L;
		this.strReserved = "";
	}

	// ERROR //
	public long saveConfigData(Context paramContext, String paramString1,
			String paramString2)
	{
		return 3l;
	}

	// ERROR //
	public long saveUserInfo(Context paramContext)
	{
		return 3l;
	}

	// ERROR //
	public long saveUserLoginInfo(Context paramContext)
	{
		UserDBAdapter userDBAdapter = new UserDBAdapter(paramContext);
		userDBAdapter.saveUserLoginInfo(this.strAccount, this.strPassword,
				this.strUID, this.strAccessToken, this.strRefreshToken,
				this.strOAuthTokenSecret, this.mExpiresIn, this.strReserved);

		return 3l;
	}

	public void setAccount(String paramString)
	{
		this.strAccount = paramString;
	}

	public void setCoverId(String paramString)
	{
		if (!TextUtils.isEmpty(paramString))
		{
			KXLog.d("UserName", "set login url:" + paramString);
			this.mCoverId = paramString;
		}
	}

	public void setCoverUrl(String paramString)
	{
		if (!TextUtils.isEmpty(paramString))
		{
			KXLog.d("UserName", "set login url:" + paramString);
			this.mCoverUrl = paramString;
		}
	}

	public void setExpiresIn(long paramLong)
	{
		this.mExpiresIn = paramLong;
	}

	public void setImcomplete(int paramInt)
	{
		this.imcomplete = paramInt;
	}

	public void setLogo(String paramString)
	{
		this.strLogo = paramString;
	}

	public void setLogo120(String paramString)
	{
		this.strLogo120 = paramString;
	}

	public void setLookAround(Boolean paramBoolean)
	{
		if (paramBoolean.booleanValue())
		{
			this.isLookAround = 1;
			return;
		}
		this.isLookAround = 0;
	}

	public void setName(String paramString)
	{
		if (!TextUtils.isEmpty(paramString))
		{
			KXLog.d("UserName", "set login user name:" + paramString);
			this.strName = paramString;
		}
	}

	public void setNeedRefresh(boolean paramBoolean)
	{
		this.mNeedRefresh = paramBoolean;
	}

	public void setOauthToken(String paramString)
	{
		this.strAccessToken = paramString;
	}

	public void setOauthTokenSecret(String paramString)
	{
		this.strOAuthTokenSecret = paramString;
	}

	public void setOnline(String paramString)
	{
		if (!TextUtils.isEmpty(paramString))
		{
			KXLog.d("UserName", "set mOnline:" + paramString);
			this.mOnline = paramString;
		}
	}

	public void setPassword(String paramString)
	{
		this.strPassword = paramString;
	}

	public void setRefreshToken(String paramString)
	{
		this.strRefreshToken = paramString;
	}

	public void setReserved(String paramString)
	{
		this.strReserved = paramString;
	}

	public void setState(String paramString)
	{
		if (!TextUtils.isEmpty(paramString))
		{
			KXLog.d("UserName", "set state:" + paramString);
			this.mState = paramString;
		}
	}

	public void setUID(String paramString)
	{
		this.strUID = paramString;
	}

	public void setWapVerify(String paramString)
	{
		this.strWapVerify = paramString;
	}

	public void setmUserLevel(UserLevel paramUserLevel)
	{
		this.mUserLevel = paramUserLevel;
	}

	public boolean syncPhotoUnderWifi()
	{
		return true;
	}
}

/*
 * Location:
 * C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name: com.kaixin001.model.User JD-Core Version: 0.6.0
 */