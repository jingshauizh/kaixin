package com.kaixin001.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

public class PicMd5DBAdapter extends KXBaseDBAdapter
{
  public static final String[] COLUMN_FOR_QUERY = { "pid", "md5" };
  public static final String CREATE_TABLE_SQL = "create table cloudalbum (pid TEXT not null, md5 TEXT not null);";
  public static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS cloudalbum";
  public static final String _COLUMN_MD5 = "md5";
  public static final String _COLUMN_PID = "pid";
  public static final String _TABLE_NAME = "cloudalbum";

  public PicMd5DBAdapter(Context paramContext)
  {
    super(paramContext);
  }

  public int deletePicMd5(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return -1;
    return super.delete("cloudalbum", "pid = '" + paramString + "'", null);
  }

  public Cursor getMd5s()
  {
    return super.query("cloudalbum", COLUMN_FOR_QUERY, null, null, null, null, null);
  }

  public long insertPicMd5(String paramString1, String paramString2)
  {
    if ((TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2)))
      return -1L;
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("pid", paramString1);
    localContentValues.put("md5", paramString2);
    return super.insert("cloudalbum", null, localContentValues);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.db.PicMd5DBAdapter
 * JD-Core Version:    0.6.0
 */