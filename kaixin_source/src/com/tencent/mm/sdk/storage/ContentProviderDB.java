package com.tencent.mm.sdk.storage;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import com.tencent.mm.sdk.platformtools.Log;

public abstract class ContentProviderDB<T>
  implements ISQLiteDatabase
{
  private final Context q;

  public ContentProviderDB(Context paramContext)
  {
    this.q = paramContext;
  }

  public int delete(String paramString1, String paramString2, String[] paramArrayOfString)
  {
    Uri localUri = getUriFromTable(paramString1);
    if (localUri == null)
    {
      Log.e("MicroMsg.SDK.MContentProviderDB", "get uri from table failed, table=" + paramString1);
      return 0;
    }
    return this.q.getContentResolver().delete(localUri, paramString2, paramArrayOfString);
  }

  public boolean execSQL(String paramString1, String paramString2)
  {
    Log.e("MicroMsg.SDK.MContentProviderDB", "do not support, execSQL sql=" + paramString2);
    return false;
  }

  public abstract Uri getUriFromTable(String paramString);

  public long insert(String paramString1, String paramString2, ContentValues paramContentValues)
  {
    Uri localUri = getUriFromTable(paramString1);
    if (localUri == null)
    {
      Log.e("MicroMsg.SDK.MContentProviderDB", "get uri from table failed, table=" + paramString1);
      return -1L;
    }
    return ContentUris.parseId(this.q.getContentResolver().insert(localUri, paramContentValues));
  }

  public Cursor query(String paramString1, String[] paramArrayOfString1, String paramString2, String[] paramArrayOfString2, String paramString3, String paramString4, String paramString5)
  {
    Uri localUri = getUriFromTable(paramString1);
    Object localObject;
    if (localUri == null)
    {
      Log.e("MicroMsg.SDK.MContentProviderDB", "get uri from table failed, table=" + paramString1);
      localObject = new MatrixCursor(paramArrayOfString1);
    }
    do
    {
      return localObject;
      localObject = this.q.getContentResolver().query(localUri, paramArrayOfString1, paramString2, paramArrayOfString2, paramString5);
    }
    while (localObject != null);
    return (Cursor)new MatrixCursor(paramArrayOfString1);
  }

  public Cursor rawQuery(String paramString, String[] paramArrayOfString)
  {
    Log.e("MicroMsg.SDK.MContentProviderDB", "do not support, rawQuery sql=" + paramString);
    return null;
  }

  public long replace(String paramString1, String paramString2, ContentValues paramContentValues)
  {
    Log.e("MicroMsg.SDK.MContentProviderDB", "do not support, replace table=" + paramString1);
    return 0L;
  }

  public int update(String paramString1, ContentValues paramContentValues, String paramString2, String[] paramArrayOfString)
  {
    Uri localUri = getUriFromTable(paramString1);
    if (localUri == null)
    {
      Log.e("MicroMsg.SDK.MContentProviderDB", "get uri from table failed, table=" + paramString1);
      return 0;
    }
    return this.q.getContentResolver().update(localUri, paramContentValues, paramString2, paramArrayOfString);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.storage.ContentProviderDB
 * JD-Core Version:    0.6.0
 */