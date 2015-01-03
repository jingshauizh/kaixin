package com.kaixin001.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class ActiveDBAdapter extends KXBaseDBAdapter
{
  public static final String CREATE_TABLE_SQL = "create table active (_rowid INTEGER primary key autoincrement, uid TEXT not null, activeflag TEXT not null, flag TEXT)";
  public static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS active";
  public static final String _ACTIVE_COLUMN_ACTIVE_FLAG = "activeflag";
  public static final String _ACTIVE_COLUMN_FLAG = "flag";
  public static final String _ACTIVE_COLUMN_ROWID = "_rowid";
  public static final String _ACTIVE_COLUMN_UID = "uid";
  public static final String _ACTIVE_TABLE_NAME = "active";
  public static final String[] _COLUMNS_FOR_QUERY = { "_rowid", "uid", "activeflag", "flag" };

  public ActiveDBAdapter(Context paramContext)
  {
    super(paramContext);
  }

  private ContentValues getContentValues(String paramString1, String paramString2, String paramString3)
  {
    ContentValues localContentValues = new ContentValues();
    if (paramString1 != null)
      localContentValues.put("uid", paramString1);
    localContentValues.put("activeflag", paramString2);
    localContentValues.put("flag", paramString3);
    return localContentValues;
  }

  public Cursor getActiveById(String paramString)
  {
    String[] arrayOfString = { paramString };
    return super.query("active", _COLUMNS_FOR_QUERY, "uid = ? ", arrayOfString, null, null, null);
  }

  public boolean getActiveFlagById(String paramString)
  {
    Cursor localCursor = getActiveById(paramString);
    boolean bool = false;
    if (localCursor != null)
    {
      bool = localCursor.getString(localCursor.getColumnIndex("activeflag")).equals("1");
      localCursor.close();
    }
    return bool;
  }

  public long insertOrUpdateActive(String paramString1, String paramString2, String paramString3)
  {
    String[] arrayOfString = { paramString1 };
    Cursor localCursor = super.query("active", _COLUMNS_FOR_QUERY, "uid = ? ", arrayOfString, null, null, null);
    long l;
    if ((localCursor != null) && (localCursor.moveToFirst()))
      l = super.update("active", getContentValues(null, paramString2, paramString3), "uid = ? ", arrayOfString);
    while (true)
    {
      if (localCursor != null)
        localCursor.close();
      return l;
      l = super.insert("active", null, getContentValues(paramString1, paramString2, paramString3));
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.db.ActiveDBAdapter
 * JD-Core Version:    0.6.0
 */