package com.kaixin001.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class GiftDBAdapter extends KXBaseDBAdapter
{
  public static final String CREATE_TABLE_SQL = "create table gift (gid TEXT not null, gname INTEGER not null, pic TEXT not null, defaultPs TEXT not null);";
  public static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS gift";
  public static final String[] _COLUMNS_FOR_QUERY = { "gid", "gname", "pic", "defaultPs" };
  public static final String _GIFT_COLUMN_GID = "gid";
  public static final String _GIFT_COLUMN_GNAME = "gname";
  public static final String _GIFT_COLUMN_PIC = "pic";
  public static final String _GIFT_COLUMN_PS = "defaultPs";
  public static final String _GIFT_TABLE_NAME = "gift";

  public GiftDBAdapter(Context paramContext)
  {
    super(paramContext);
  }

  public int deleteGift()
  {
    return super.delete("gift", null, null);
  }

  public Cursor getAllGifts()
  {
    return super.query("gift", _COLUMNS_FOR_QUERY, null, null, null, null, null);
  }

  public long insertGift(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("gid", paramString1);
    localContentValues.put("gname", paramString2);
    localContentValues.put("pic", paramString3);
    localContentValues.put("defaultPs", paramString4);
    return super.insert("gift", null, localContentValues);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.db.GiftDBAdapter
 * JD-Core Version:    0.6.0
 */