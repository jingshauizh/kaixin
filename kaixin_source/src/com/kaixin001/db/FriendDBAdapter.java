package com.kaixin001.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

public class FriendDBAdapter extends KXBaseDBAdapter
{
  public static final String COLUMN_FUID = "fuid";
  public static final String CREATE_TABLE_SQL = "create table friends (_id INTEGER primary key autoincrement, uid TEXT not null, fuid TEXT not null, name TEXT not null, gender INTEGER, hometown TEXT, city TEXT, status INTEGER, logo50 TEXT, logo120 TEXT, birthday TEXT, bodyform INTEGER, blood INTEGER, phone TEXT, marriage INTEGER, company TEXT, school TEXT, isstar INTEGER, pinyin TEXT, fpy TEXT, online INTEGER, reserved TEXT);";
  public static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS friends";
  private static final String[] SELECT_PROJECTS = { "_id", "uid", "fuid", "name", "gender", "hometown", "city", "status", "logo50", "logo120", "birthday", "bodyform", "blood", "phone", "marriage", "company", "school", "isstar", "pinyin", "fpy", "online", "reserved" };
  public static final String TABLE_NAME = "friends";

  public FriendDBAdapter(Context paramContext)
  {
    super(paramContext);
  }

  private ContentValues convertToContentValues(String paramString1, String paramString2, String paramString3, int paramInt1, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, int paramInt2, int paramInt3, String paramString10, int paramInt4, String paramString11, String paramString12, boolean paramBoolean, String paramString13, String paramString14, int paramInt5, String paramString15)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("uid", paramString1);
    localContentValues.put("fuid", paramString2);
    localContentValues.put("name", paramString3);
    localContentValues.put("gender", Integer.valueOf(paramInt1));
    localContentValues.put("hometown", paramString4);
    localContentValues.put("city", paramString5);
    localContentValues.put("status", paramString6);
    localContentValues.put("logo50", paramString7);
    localContentValues.put("logo120", paramString8);
    localContentValues.put("birthday", paramString9);
    localContentValues.put("bodyform", Integer.valueOf(paramInt2));
    localContentValues.put("blood", Integer.valueOf(paramInt3));
    localContentValues.put("phone", paramString10);
    localContentValues.put("marriage", Integer.valueOf(paramInt4));
    localContentValues.put("company", paramString11);
    localContentValues.put("school", paramString12);
    localContentValues.put("isstar", Boolean.valueOf(paramBoolean));
    localContentValues.put("pinyin", paramString13);
    localContentValues.put("fpy", paramString14);
    localContentValues.put("online", Integer.valueOf(paramInt5));
    localContentValues.put("reserved", paramString15);
    return localContentValues;
  }

  public int deleteFriend(String paramString1, String paramString2)
  {
    if ((TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2)))
      return 0;
    return super.delete("friends", "uid = ? AND fuid = ?", new String[] { paramString1, paramString2 });
  }

  public Cursor getAllFriends(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return null;
    return super.query(false, "friends", SELECT_PROJECTS, "uid = ?", new String[] { paramString }, null, null, "ORDER BY pinyin ASC", null);
  }

  public Cursor getFriend(String paramString1, String paramString2)
  {
    if ((TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2)))
      return null;
    return super.query(false, "friends", SELECT_PROJECTS, "uid = ? AND fuid = ?", new String[] { paramString1, paramString2 }, null, null, null, null);
  }

  public long insertFriend(String paramString1, String paramString2, String paramString3, int paramInt1, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, int paramInt2, int paramInt3, String paramString10, int paramInt4, String paramString11, String paramString12, boolean paramBoolean, String paramString13, String paramString14, int paramInt5, String paramString15)
  {
    if ((TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2)) || (TextUtils.isEmpty(paramString3)))
      return -1L;
    return super.insert("friends", null, convertToContentValues(paramString1, paramString2, paramString3, paramInt1, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9, paramInt2, paramInt3, paramString10, paramInt4, paramString11, paramString12, paramBoolean, paramString13, paramString14, paramInt5, paramString15));
  }

  public long updateFriend(long paramLong, String paramString1, String paramString2, String paramString3, int paramInt1, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, int paramInt2, int paramInt3, String paramString10, int paramInt4, String paramString11, String paramString12, boolean paramBoolean, String paramString13, String paramString14, int paramInt5, String paramString15)
  {
    if ((TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2)) || (TextUtils.isEmpty(paramString3)))
      return -1L;
    return super.update("friends", convertToContentValues(paramString1, paramString2, paramString3, paramInt1, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9, paramInt2, paramInt3, paramString10, paramInt4, paramString11, paramString12, paramBoolean, paramString13, paramString14, paramInt5, paramString15), "_id = " + paramLong, null);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.db.FriendDBAdapter
 * JD-Core Version:    0.6.0
 */