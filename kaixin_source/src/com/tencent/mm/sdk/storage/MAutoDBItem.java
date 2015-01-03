package com.tencent.mm.sdk.storage;

import android.content.ContentValues;
import android.database.Cursor;
import com.tencent.mm.sdk.platformtools.Log;
import com.tencent.mm.sdk.platformtools.Util;
import java.lang.reflect.Field;
import java.util.HashMap;

public abstract class MAutoDBItem extends IAutoDBItem
{
  public void convertFrom(Cursor paramCursor)
  {
    int i = 0;
    String[] arrayOfString = paramCursor.getColumnNames();
    if (arrayOfString == null)
      Log.e("MicroMsg.SDK.MAutoDBItem", "convertFrom: get column names failed");
    int m;
    do
    {
      return;
      HashMap localHashMap = new HashMap();
      for (int j = 0; j < arrayOfString.length; j++)
        localHashMap.put(arrayOfString[j], Integer.valueOf(j));
      Field[] arrayOfField = getDBInfo().fields;
      int k = arrayOfField.length;
      while (true)
        if (i < k)
        {
          Field localField = arrayOfField[i];
          String str = getColName(localField);
          int n;
          if (!Util.isNullOrNil(str))
          {
            n = Util.nullAs((Integer)localHashMap.get(str), -1);
            if (n < 0);
          }
          try
          {
            CursorFieldHelper.setter(localField.getType()).invoke(localField, this, paramCursor, n);
            i++;
          }
          catch (Exception localException)
          {
            while (true)
              localException.printStackTrace();
          }
        }
      m = Util.nullAs((Integer)localHashMap.get("rowid"), -1);
    }
    while (m < 0);
    this.systemRowid = paramCursor.getLong(m);
  }

  public ContentValues convertTo()
  {
    ContentValues localContentValues = new ContentValues();
    Field[] arrayOfField = getDBInfo().fields;
    int i = arrayOfField.length;
    int j = 0;
    while (true)
      if (j < i)
      {
        Field localField = arrayOfField[j];
        try
        {
          CursorFieldHelper.getter(localField.getType()).invoke(localField, this, localContentValues);
          j++;
        }
        catch (Exception localException)
        {
          while (true)
            localException.printStackTrace();
        }
      }
    if (this.systemRowid > 0L)
      localContentValues.put("rowid", Long.valueOf(this.systemRowid));
    return localContentValues;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.storage.MAutoDBItem
 * JD-Core Version:    0.6.0
 */