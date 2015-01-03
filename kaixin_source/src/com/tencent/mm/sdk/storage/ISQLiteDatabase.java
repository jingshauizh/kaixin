package com.tencent.mm.sdk.storage;

import android.content.ContentValues;
import android.database.Cursor;

public abstract interface ISQLiteDatabase
{
  public abstract int delete(String paramString1, String paramString2, String[] paramArrayOfString);

  public abstract boolean execSQL(String paramString1, String paramString2);

  public abstract long insert(String paramString1, String paramString2, ContentValues paramContentValues);

  public abstract Cursor query(String paramString1, String[] paramArrayOfString1, String paramString2, String[] paramArrayOfString2, String paramString3, String paramString4, String paramString5);

  public abstract Cursor rawQuery(String paramString, String[] paramArrayOfString);

  public abstract long replace(String paramString1, String paramString2, ContentValues paramContentValues);

  public abstract int update(String paramString1, ContentValues paramContentValues, String paramString2, String[] paramArrayOfString);
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.storage.ISQLiteDatabase
 * JD-Core Version:    0.6.0
 */