package com.kaixin001.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.text.TextUtils;

public class UserDBAdapter extends KXBaseDBAdapter
{
	public static final String COLUMN_ACCESS_TOKEN = "access_token";
	public static final String COLUMN_ACCOUNT = "account";
	public static final String COLUMN_EXPIRES_IN = "expires_in";
	public static final String COLUMN_ISLOGIN_IN = "islogin";
	public static final String COLUMN_OAUTH_TOKEN_SECRET = "oauth_token_secret";
	public static final String COLUMN_PASSWORD = "password";
	public static final String COLUMN_RECENT_TIME_LOGIN = "recent_time_login";
	public static final String COLUMN_REFRESH_TOKEN = "refresh_token";
	public static final String CREATE_TABLE_SQL = "create table users (_id INTEGER primary key autoincrement, account TEXT not null, password TEXT, uid TEXT not null, name TEXT, state TEXT, coverUrl TEXT, gender INTEGER, hometown TEXT, city TEXT, status INTEGER, logo50 TEXT, logo120 TEXT, access_token TEXT, refresh_token TEXT, expires_in INTEGER, oauth_token_secret TEXT, reserved TEXT, recent_time_login INTEGER, islogin INTEGER);";
	public static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS users";
	private static final String[] SELECT_PROJECTS = { "account", "password",
			"uid", "name", "state", "coverUrl", "logo50", "access_token",
			"refresh_token", "oauth_token_secret", "expires_in", "reserved",
			"_id", "islogin", "recent_time_login" };
	public static final String TABLE_NAME = "users";
	public static final int USER_STATE_LOGIN = 1;
	public static final int USER_STATE_UNLOGIN = 0;
	public static final String _CIRCLE_COLUMN_BLOCK_FLAG = "flag";
	public static final String _CIRCLE_COLUMN_CID = "circleid";
	public static final String _CIRCLE_COLUMN_RESERVED = "reserved";
	public static final String _CIRCLE_COLUMN_ROWID = "_rowid";
	public static final String _CIRCLE_COLUMN_UID = "uid";
	private static final String _CIRCLE_DEFAULT_SELECTION = " uid=?";
	private static final String _CIRCLE_EDIT_SELECTION = " uid=? and circleid=?";
	private static final String _CIRCLE_TABLE_CREATE = "create table circles (_rowid INTEGER primary key autoincrement, uid TEXT not null, circleid TEXT not null, flag INTEGER not null, reserved TEXT);";
	public static final String _CIRCLE_TABLE_NAME = "circles";
	private static final String[] _COLUMNS_FOR_QUERY = { "_rowid", "uid",
			"circleid", "flag", "reserved" };

	public UserDBAdapter(Context paramContext)
	{
		super(paramContext);
	}

	private ContentValues convertToContentValues(String paramString1,
			String paramString2, String paramString3, String paramString4,
			String paramString5, String paramString6, long paramLong,
			String paramString7, int paramInt)
	{
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("account", paramString1);
		localContentValues.put("password", paramString2);
		localContentValues.put("uid", paramString3);
		localContentValues.put("access_token", paramString4);
		localContentValues.put("refresh_token", paramString5);
		localContentValues.put("oauth_token_secret", paramString6);
		localContentValues.put("reserved", paramString7);
		localContentValues.put("expires_in", Long.valueOf(paramLong));
		localContentValues.put("islogin", Integer.valueOf(paramInt));
		return localContentValues;
	}

	public int addBlockedCircle(String paramString1, String paramString2,
			int paramInt, String paramString3)
	{
		Cursor localCursor = null;
		if ((!TextUtils.isEmpty(paramString1))
				&& (!TextUtils.isEmpty(paramString2)))
			localCursor = null;
		try
		{
			localCursor = super.query("circles", _COLUMNS_FOR_QUERY,
					" uid=? and circleid=?", new String[] { paramString1,
							paramString2 }, null, null, null, null);
			ContentValues localContentValues = new ContentValues();
			localContentValues.put("uid", paramString1);
			localContentValues.put("circleid", paramString2);
			localContentValues.put("flag", Integer.valueOf(paramInt));
			localContentValues.put("reserved", paramString3);
			if ((localCursor != null) && (localCursor.getCount() > 0))
			{
				if (localCursor.moveToFirst())
				{
					long l1 = localCursor.getLong(0);
					long l2 = super.update("circles", localContentValues,
							"_rowid=" + l1, null);
					int i = (int) l2;
					return i;
				}
			}
			else
				super.insert("circles", null, localContentValues);
			return -1;
		}
		finally
		{
			if (localCursor != null)
				localCursor.close();
		}

	}

	public long addLoginUserinfo(String paramString1, String paramString2,
			String paramString3, String paramString4, String paramString5,
			String paramString6, long paramLong, String paramString7)
	{
		ContentValues localContentValues1 = new ContentValues();
		localContentValues1.put("islogin", Integer.valueOf(0));
		update("users", localContentValues1, "islogin=1", null);
		ContentValues localContentValues2 = convertToContentValues(
				paramString1, paramString2, paramString3, paramString4,
				paramString5, paramString6, paramLong, paramString7, 1);
		localContentValues2.put("recent_time_login",
				Long.valueOf(System.currentTimeMillis()));
		return super.insert("users", null, localContentValues2);
	}

	public int clearAllUsersInfo()
	{
		super.update("users",
				convertToContentValues("", "", "", "", "", "", 0L, "", 0),
				null, null);
		return 0;
	}

	public int delBlockedCircle(String paramString1, String paramString2,
			int paramInt, String paramString3)
	{
		if ((!TextUtils.isEmpty(paramString1))
				&& (!TextUtils.isEmpty(paramString2)))
		{
			Cursor localCursor = null;
			try
			{
				localCursor = super.query("circles", _COLUMNS_FOR_QUERY,
						" uid=? and circleid=?", new String[] { paramString1,
								paramString2 }, null, null, null, null);
				if ((localCursor != null) && (localCursor.getCount() > 0)
						&& (localCursor.moveToFirst()))
				{
					long l = localCursor.getLong(0);
					int i = super.delete("circles", "_rowid=" + l, null);
					return i;
				}
			}
			finally
			{
				if (localCursor != null)
					localCursor.close();
			}
			if (localCursor != null)
				localCursor.close();
		}
		return -1;
	}

	public int deleteUserInfo(int paramInt)
	{
		return super.delete("users", "_id==" + paramInt, null);
	}

	public int deleteUserInfoByUid(int paramInt)
	{
		return super.delete("users", "uid==" + paramInt, null);
	}

	public Cursor getAllUserName() throws SQLException
	{
		Cursor localCursor = super.query(true, "users", SELECT_PROJECTS,
				"access_token NOT NULL and access_token != ''", null, null,
				null, "recent_time_login desc", null);
		if (localCursor != null)
			localCursor.moveToFirst();
		return localCursor;
	}

	public Cursor getLoginedUser() throws SQLException
	{
		Cursor localCursor = super
				.query(true,
						"users",
						SELECT_PROJECTS,
						"access_token NOT NULL and access_token != '''' and islogin== 1",
						null, null, null, null, null);
		if (localCursor != null)
			localCursor.moveToFirst();
		return localCursor;
	}

	public long insertUser(String paramString1, String paramString2,
			String paramString3, String paramString4, String paramString5,
			String paramString6, long paramLong, String paramString7)
	{
		ContentValues localContentValues = convertToContentValues(paramString1,
				paramString2, paramString3, paramString4, paramString5,
				paramString6, paramLong, paramString7, 0);
		localContentValues.put("recent_time_login",
				Long.valueOf(System.currentTimeMillis()));
		return super.insert("users", null, localContentValues);
	}

	public ArrayList<String> loadBlockedCircles(String paramString)
	{
		ArrayList localArrayList = new ArrayList();
		Cursor localCursor = null;
		if (!TextUtils.isEmpty(paramString))
			localCursor = null;
		try
		{
			localCursor = super.query("circles", _COLUMNS_FOR_QUERY, " uid=?",
					new String[] { paramString }, null, null, null, null);
			int i = 0;
			if ((localCursor != null) && (localCursor.getCount() > 0))
				i = localCursor.getColumnIndex("circleid");

			boolean bool = localCursor.moveToNext();
			if (!bool)
				return localArrayList;
			localArrayList.add(localCursor.getString(i));

		}
		finally
		{
			if (localCursor != null)
				localCursor.close();
			return localArrayList;
		}

	}

	public long saveUserLoginInfo(String paramString1, String paramString2,
			String paramString3, String paramString4, String paramString5,
			String paramString6, long paramLong, String paramString7)
	{
		Cursor localCursor = null;
		try
		{
			String str = "account = \"" + paramString1 + "\"";
			localCursor = super.query("users", new String[] { "_id" }, str,
					null, null, null, null);
			long l2;
			if ((localCursor != null) && (localCursor.getCount() > 0))
			{
				if (localCursor.moveToFirst())
				{
					l2 = localCursor.getLong(0);
					long l3 = updateUserLoginInfo(l2, paramString1,
							paramString2, paramString3, paramString4,
							paramString5, paramString6, paramLong, paramString7);
					if (l3 > 0L)
						return l2;
				}
			}
			else
			{
				long l1 = addLoginUserinfo(paramString1, paramString2,
						paramString3, paramString4, paramString5, paramString6,
						paramLong, paramString7);
				l2 = l1;
				return l2;
			}
			return -1L;
		}
		finally
		{
			if (localCursor != null)
				localCursor.close();
		}

	}

	public void switchLoginUser(String paramString)
	{
		ContentValues localContentValues1 = new ContentValues();
		localContentValues1.put("islogin", Integer.valueOf(0));
		update("users", localContentValues1, "islogin== 1", null);
		ContentValues localContentValues2 = new ContentValues();
		localContentValues2.put("islogin", Integer.valueOf(1));
		localContentValues2.put("recent_time_login",
				Long.valueOf(System.currentTimeMillis()));
		update("users", localContentValues2, "uid == " + paramString, null);
	}

	public long updateUserInfo(String paramString1, String paramString2,
			String paramString3, String paramString4, String paramString5,
			String paramString6)
	{
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("name", paramString2);
		localContentValues.put("state", paramString3);
		localContentValues.put("coverUrl", paramString6);
		localContentValues.put("logo50", paramString4);
		localContentValues.put("logo120", paramString5);
		return super.update("users", localContentValues, "uid='" + paramString1
				+ "'", null);
	}

	public long updateUserLoginInfo(long paramLong1, String paramString1,
			String paramString2, String paramString3, String paramString4,
			String paramString5, String paramString6, long paramLong2,
			String paramString7)
	{
		ContentValues localContentValues1 = new ContentValues();
		localContentValues1.put("islogin", Integer.valueOf(0));
		update("users", localContentValues1, "islogin== 1", null);
		ContentValues localContentValues2 = convertToContentValues(
				paramString1, paramString2, paramString3, paramString4,
				paramString5, paramString6, paramLong2, paramString7, 1);
		localContentValues2.put("recent_time_login",
				Long.valueOf(System.currentTimeMillis()));
		return super.update("users", localContentValues2, "_id=" + paramLong1,
				null);
	}
}

/*
 * Location:
 * C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name: com.kaixin001.db.UserDBAdapter JD-Core Version: 0.6.0
 */