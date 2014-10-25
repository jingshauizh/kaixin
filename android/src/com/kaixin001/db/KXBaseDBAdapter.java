package com.kaixin001.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public abstract class KXBaseDBAdapter
{
  public static final String CLIENT_UID = "client_uid";
  public static final String COLUMN_BIRTHDAY = "birthday";
  public static final String COLUMN_BLOOD = "blood";
  public static final String COLUMN_BODYFORM = "bodyform";
  public static final String COLUMN_CITY = "city";
  public static final String COLUMN_COMPANY = "company";
  public static final String COLUMN_COVERURL = "coverUrl";
  public static final String COLUMN_FULL_PINYIN = "fpy";
  public static final String COLUMN_GENDER = "gender";
  public static final String COLUMN_HOMETOWN = "hometown";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_IS_STAR = "isstar";
  public static final String COLUMN_LOGO120 = "logo120";
  public static final String COLUMN_LOGO50 = "logo50";
  public static final String COLUMN_MARRIAGE = "marriage";
  public static final String COLUMN_NAME = "name";
  public static final String COLUMN_ONLINE = "online";
  public static final String COLUMN_PHONE = "phone";
  public static final String COLUMN_PINYIN = "pinyin";
  public static final String COLUMN_RESERVED = "reserved";
  public static final String COLUMN_SCHOOL = "school";
  public static final String COLUMN_STATE = "state";
  public static final String COLUMN_STATUS = "status";
  public static final String COLUMN_UID = "uid";
  protected final Context mContext;
  protected SQLiteDatabase mDb = null;

  public KXBaseDBAdapter(Context paramContext)
  {
    this.mContext = paramContext;
    try
    {
      open();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public static final String getCreateTableSql()
  {
    return null;
  }

  public static final String getDropTableSql()
  {
    return null;
  }

  public static final String getTableName()
  {
    return null;
  }

  private void open()
    throws SQLException
  {
    this.mDb = KaixinDBHelper.getInstance(this.mContext).getWritableDatabase();
  }

  public void close()
  {
  }

  public int delete(String paramString1, String paramString2, String[] paramArrayOfString)
  {
    if ((paramString1 == null) || (this.mDb == null))
      return 0;
    return this.mDb.delete(paramString1, paramString2, paramArrayOfString);
  }

  public long insert(String paramString1, String paramString2, ContentValues paramContentValues)
  {
    if ((paramString1 == null) || (paramContentValues == null))
      return -1L;
    return this.mDb.insert(paramString1, paramString2, paramContentValues);
  }

  public Cursor query(String paramString1, String[] paramArrayOfString1, String paramString2, String[] paramArrayOfString2, String paramString3, String paramString4, String paramString5)
  {
    if (this.mDb == null)
      return null;
    return this.mDb.query(paramString1, paramArrayOfString1, paramString2, paramArrayOfString2, paramString3, paramString4, paramString5);
  }

  public Cursor query(String paramString1, String[] paramArrayOfString1, String paramString2, String[] paramArrayOfString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    if (this.mDb == null)
      return null;
    return this.mDb.query(paramString1, paramArrayOfString1, paramString2, paramArrayOfString2, paramString3, paramString4, paramString5, paramString6);
  }

  public Cursor query(boolean paramBoolean, String paramString1, String[] paramArrayOfString1, String paramString2, String[] paramArrayOfString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    if (this.mDb == null)
      return null;
    return this.mDb.query(paramBoolean, paramString1, paramArrayOfString1, paramString2, paramArrayOfString2, paramString3, paramString4, paramString5, paramString6);
  }

  public long update(String paramString1, ContentValues paramContentValues, String paramString2, String[] paramArrayOfString)
  {
    if ((paramString1 == null) || (paramContentValues == null))
      return -1L;
    return this.mDb.update(paramString1, paramContentValues, paramString2, paramArrayOfString);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.db.KXBaseDBAdapter
 * JD-Core Version:    0.6.0
 */