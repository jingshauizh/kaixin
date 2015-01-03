package com.kaixin001.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;

public class CircleDBAdapter extends KXBaseDBAdapter
{
  public static final String CREATE_TABLE_SQL = "create table circles ( _rowid INTEGER primary key autoincrement, uid TEXT not null, circleid TEXT not null, flag INTEGER not null, reserved TEXT);";
  public static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS circles";
  public static final String _CIRCLE_COLUMN_BLOCK_FLAG = "flag";
  public static final String _CIRCLE_COLUMN_CID = "circleid";
  public static final String _CIRCLE_COLUMN_RESERVED = "reserved";
  public static final String _CIRCLE_COLUMN_ROWID = "_rowid";
  public static final String _CIRCLE_COLUMN_UID = "uid";
  private static final String _CIRCLE_DEFAULT_SELECTION = " uid=?";
  private static final String _CIRCLE_EDIT_SELECTION = " uid=? and circleid=?";
  public static final String _CIRCLE_TABLE_NAME = "circles";
  private static final String[] _COLUMNS_FOR_QUERY = { "_rowid", "uid", "circleid", "flag", "reserved" };

  public CircleDBAdapter(Context paramContext)
  {
    super(paramContext);
  }

  private ContentValues getContentValues(String paramString1, String paramString2, int paramInt, String paramString3)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("uid", paramString1);
    localContentValues.put("circleid", paramString2);
    localContentValues.put("flag", Integer.valueOf(paramInt));
    localContentValues.put("reserved", paramString3);
    return localContentValues;
  }

  public long addBlockedCircle(String paramString1, String paramString2, int paramInt, String paramString3)
  {
    String[] arrayOfString = { paramString1, paramString2 };
    Cursor localCursor = super.query("circles", _COLUMNS_FOR_QUERY, "uid = ? and circleid = ?", arrayOfString, null, null, null, null);
    ContentValues localContentValues = getContentValues(paramString1, paramString2, paramInt, paramString3);
    long l1;
    if ((localCursor != null) && (localCursor.moveToFirst()))
    {
      long l2 = localCursor.getLong(0);
      l1 = super.update("circles", localContentValues, "_rowid=" + l2, null);
    }
    while (true)
    {
      if (localCursor != null)
        localCursor.close();
      return l1;
      l1 = super.insert("circles", null, localContentValues);
    }
  }

  public int delBlockedCircle(String paramString1, String paramString2, int paramInt, String paramString3)
  {
    int i = -1;
    String[] arrayOfString = { paramString1, paramString2 };
    Cursor localCursor = super.query("circles", _COLUMNS_FOR_QUERY, "uid = ? and circleid = ?", arrayOfString, null, null, null, null);
    if ((localCursor != null) && (localCursor.moveToFirst()))
    {
      long l = localCursor.getLong(0);
      i = super.delete("circles", "_rowid=" + l, null);
    }
    if (localCursor != null)
      localCursor.close();
    return i;
  }

  public ArrayList<String> loadBlockedCircles(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    String[] arrayOfString = { paramString };
    Cursor localCursor = super.query("circles", _COLUMNS_FOR_QUERY, "uid = ? ", arrayOfString, null, null, null, null);
    int i;
    if ((localCursor != null) && (localCursor.getCount() > 0))
      i = localCursor.getColumnIndex("circleid");
    while (true)
    {
      if (!localCursor.moveToNext())
      {
        if (localCursor != null)
          localCursor.close();
        return localArrayList;
      }
      localArrayList.add(localCursor.getString(i));
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.db.CircleDBAdapter
 * JD-Core Version:    0.6.0
 */