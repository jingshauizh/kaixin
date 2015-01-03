package com.kaixin001.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class TouchDBAdapter extends KXBaseDBAdapter
{
  public static final String CREATE_TABLE_SQL = "create table touch (gid TEXT not null, gname INTEGER not null, pic TEXT not null);";
  public static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS touch";
  public static final String[] _COLUMNS_FOR_QUERY = { "gid", "gname", "pic" };
  public static final String _TOUCH_COLUMN_GID = "gid";
  public static final String _TOUCH_COLUMN_GNAME = "gname";
  public static final String _TOUCH_COLUMN_PIC = "pic";
  public static final String _TOUCH_TABLE_NAME = "touch";

  public TouchDBAdapter(Context paramContext)
  {
    super(paramContext);
  }

  public int deleteTouch()
  {
    return super.delete("touch", null, null);
  }

  public Cursor getAllTouchs()
  {
    return super.query("touch", _COLUMNS_FOR_QUERY, null, null, null, null, null);
  }

  public long insertTouch(String paramString1, String paramString2, String paramString3)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("gid", paramString1);
    localContentValues.put("gname", paramString2);
    localContentValues.put("pic", paramString3);
    return super.insert("touch", null, localContentValues);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.db.TouchDBAdapter
 * JD-Core Version:    0.6.0
 */