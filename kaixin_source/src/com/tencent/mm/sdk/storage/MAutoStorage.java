package com.tencent.mm.sdk.storage;

import android.content.ContentValues;
import android.database.Cursor;
import com.tencent.mm.sdk.platformtools.Log;
import com.tencent.mm.sdk.platformtools.Util;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import junit.framework.Assert;

public abstract class MAutoStorage<T extends IAutoDBItem> extends MStorage
{
  private final ISQLiteDatabase bS;
  private final IAutoDBItem.MAutoDBInfo bT;
  private final String bU;

  public MAutoStorage(ISQLiteDatabase paramISQLiteDatabase, IAutoDBItem.MAutoDBInfo paramMAutoDBInfo, String paramString, String[] paramArrayOfString)
  {
    this.bS = paramISQLiteDatabase;
    this.bT = paramMAutoDBInfo;
    IAutoDBItem.MAutoDBInfo localMAutoDBInfo = this.bT;
    if (Util.isNullOrNil(this.bT.primaryKey));
    for (String str = "rowid"; ; str = this.bT.primaryKey)
    {
      localMAutoDBInfo.primaryKey = str;
      this.bU = paramString;
      List localList = getUpdateSQLs(this.bT, getTableName(), this.bS);
      for (int i = 0; i < localList.size(); i++)
        this.bS.execSQL(this.bU, (String)localList.get(i));
    }
    if (paramArrayOfString != null)
    {
      int j = paramArrayOfString.length;
      int k = 0;
      if (j > 0)
        while (k < paramArrayOfString.length)
        {
          this.bS.execSQL(this.bU, paramArrayOfString[k]);
          k++;
        }
    }
  }

  private static StringBuilder a(ContentValues paramContentValues, String[] paramArrayOfString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = paramArrayOfString.length;
    for (int j = 0; j < i; j++)
    {
      String str = paramArrayOfString[j];
      localStringBuilder.append(str + " = ? AND ");
      if (paramContentValues.get(str) == null)
        return null;
    }
    localStringBuilder.append(" 1=1");
    return localStringBuilder;
  }

  private boolean a(ContentValues paramContentValues)
  {
    ISQLiteDatabase localISQLiteDatabase = this.bS;
    String str1 = getTableName();
    String[] arrayOfString1 = this.bT.columns;
    String str2 = this.bT.primaryKey + " = ?";
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = Util.nullAsNil(paramContentValues.getAsString(this.bT.primaryKey));
    Cursor localCursor = localISQLiteDatabase.query(str1, arrayOfString1, str2, arrayOfString2, null, null, null);
    boolean bool = IAutoDBItem.checkIOEqual(paramContentValues, localCursor);
    localCursor.close();
    return bool;
  }

  private static String[] a(String[] paramArrayOfString, ContentValues paramContentValues)
  {
    String[] arrayOfString = new String[paramArrayOfString.length];
    for (int i = 0; i < arrayOfString.length; i++)
      arrayOfString[i] = Util.nullAsNil(paramContentValues.getAsString(paramArrayOfString[i]));
    return arrayOfString;
  }

  private void f(String paramString)
  {
    Log.d("MicroMsg.SDK.MAutoStorage", getTableName() + ":" + paramString);
  }

  private void g(String paramString)
  {
    Log.e("MicroMsg.SDK.MAutoStorage", getTableName() + ":" + paramString);
  }

  public static String getCreateSQLs(IAutoDBItem.MAutoDBInfo paramMAutoDBInfo, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("CREATE TABLE IF NOT EXISTS " + paramString + " ( ");
    localStringBuilder.append(paramMAutoDBInfo.sql);
    localStringBuilder.append(");");
    return localStringBuilder.toString();
  }

  public static List<String> getUpdateSQLs(IAutoDBItem.MAutoDBInfo paramMAutoDBInfo, String paramString, ISQLiteDatabase paramISQLiteDatabase)
  {
    LinkedList localLinkedList = new LinkedList();
    HashMap localHashMap = new HashMap();
    Cursor localCursor = paramISQLiteDatabase.rawQuery("PRAGMA table_info( " + paramString + " )", new String[0]);
    if (localCursor == null)
      return localLinkedList;
    while (localCursor.moveToNext())
      localHashMap.put(localCursor.getString(localCursor.getColumnIndex("name")), localCursor.getString(localCursor.getColumnIndex("type")));
    localCursor.close();
    Iterator localIterator = paramMAutoDBInfo.colsMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str1 = (String)localEntry.getValue();
      String str2 = (String)localEntry.getKey();
      if ((str1 == null) || (str1.length() <= 0))
        continue;
      String str3 = (String)localHashMap.get(str2);
      if (str3 == null)
      {
        localLinkedList.add("ALTER TABLE " + paramString + " ADD COLUMN " + str2 + " " + str1 + ";");
        localHashMap.remove(str2);
        continue;
      }
      if (str3.equalsIgnoreCase(str1))
        continue;
      Log.e("MicroMsg.SDK.MAutoStorage", "conflicting alter table on column: " + str2 + ", " + str3 + "<o-n>" + str1);
      localHashMap.remove(str2);
    }
    return localLinkedList;
  }

  public boolean delete(long paramLong)
  {
    int i = 1;
    ISQLiteDatabase localISQLiteDatabase = this.bS;
    String str = getTableName();
    String[] arrayOfString = new String[i];
    arrayOfString[0] = String.valueOf(paramLong);
    if (localISQLiteDatabase.delete(str, "rowid = ?", arrayOfString) > 0);
    while (true)
    {
      if (i != 0)
        notify();
      return i;
      i = 0;
    }
  }

  public boolean delete(T paramT, String[] paramArrayOfString)
  {
    int i = 0;
    ContentValues localContentValues = paramT.convertTo();
    if ((localContentValues == null) || (localContentValues.size() <= 0))
      g("delete failed, value.size <= 0");
    while (true)
    {
      return i;
      if ((paramArrayOfString != null) && (paramArrayOfString.length > 0))
        break;
      f("delete with primary key");
      ISQLiteDatabase localISQLiteDatabase = this.bS;
      String str1 = getTableName();
      String str2 = this.bT.primaryKey + " = ?";
      String[] arrayOfString = new String[1];
      arrayOfString[0] = Util.nullAsNil(localContentValues.getAsString(this.bT.primaryKey));
      int j = localISQLiteDatabase.delete(str1, str2, arrayOfString);
      i = 0;
      if (j > 0)
        i = 1;
      if (i == 0)
        continue;
      doNotify();
      return i;
    }
    StringBuilder localStringBuilder = a(localContentValues, paramArrayOfString);
    if (localStringBuilder == null)
    {
      g("delete failed, check keys failed");
      return false;
    }
    if (this.bS.delete(getTableName(), localStringBuilder.toString(), a(paramArrayOfString, localContentValues)) > 0)
    {
      doNotify(this.bT.primaryKey);
      return true;
    }
    g("delete failed");
    return false;
  }

  public boolean get(long paramLong, T paramT)
  {
    ISQLiteDatabase localISQLiteDatabase = this.bS;
    String str = getTableName();
    String[] arrayOfString1 = this.bT.columns;
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = String.valueOf(paramLong);
    Cursor localCursor = localISQLiteDatabase.query(str, arrayOfString1, "rowid = ?", arrayOfString2, null, null, null);
    if (localCursor.moveToFirst())
    {
      paramT.convertFrom(localCursor);
      localCursor.close();
      return true;
    }
    localCursor.close();
    return false;
  }

  public boolean get(T paramT, String[] paramArrayOfString)
  {
    ContentValues localContentValues = paramT.convertTo();
    if ((localContentValues == null) || (localContentValues.size() <= 0))
    {
      g("get failed, value.size <= 0");
      return false;
    }
    if ((paramArrayOfString == null) || (paramArrayOfString.length <= 0))
    {
      f("get with primary key");
      ISQLiteDatabase localISQLiteDatabase = this.bS;
      String str1 = getTableName();
      String[] arrayOfString1 = this.bT.columns;
      String str2 = this.bT.primaryKey + " = ?";
      String[] arrayOfString2 = new String[1];
      arrayOfString2[0] = Util.nullAsNil(localContentValues.getAsString(this.bT.primaryKey));
      Cursor localCursor1 = localISQLiteDatabase.query(str1, arrayOfString1, str2, arrayOfString2, null, null, null);
      if (localCursor1.moveToFirst())
      {
        paramT.convertFrom(localCursor1);
        localCursor1.close();
        return true;
      }
      localCursor1.close();
      return false;
    }
    StringBuilder localStringBuilder = a(localContentValues, paramArrayOfString);
    if (localStringBuilder == null)
    {
      g("get failed, check keys failed");
      return false;
    }
    Cursor localCursor2 = this.bS.query(getTableName(), this.bT.columns, localStringBuilder.toString(), a(paramArrayOfString, localContentValues), null, null, null);
    if (localCursor2.moveToFirst())
    {
      paramT.convertFrom(localCursor2);
      localCursor2.close();
      return true;
    }
    localCursor2.close();
    f("get failed, not found");
    return false;
  }

  public Cursor getAll()
  {
    return this.bS.query(getTableName(), this.bT.columns, null, null, null, null, null);
  }

  public int getCount()
  {
    Cursor localCursor = rawQuery("select count(*) from " + getTableName(), new String[0]);
    int i = 0;
    if (localCursor != null)
    {
      localCursor.moveToFirst();
      i = localCursor.getInt(0);
      localCursor.close();
    }
    return i;
  }

  public String getPrimaryKey()
  {
    return this.bT.primaryKey;
  }

  public String getTableName()
  {
    return this.bU;
  }

  public boolean insert(T paramT)
  {
    ContentValues localContentValues = paramT.convertTo();
    if ((localContentValues == null) || (localContentValues.size() <= 0))
    {
      g("insert failed, value.size <= 0");
      return false;
    }
    paramT.systemRowid = this.bS.insert(getTableName(), this.bT.primaryKey, localContentValues);
    if (paramT.systemRowid <= 0L)
    {
      g("insert failed");
      return false;
    }
    localContentValues.put("rowid", Long.valueOf(paramT.systemRowid));
    doNotify(localContentValues.getAsString(this.bT.primaryKey));
    return true;
  }

  public Cursor rawQuery(String paramString, String[] paramArrayOfString)
  {
    return this.bS.rawQuery(paramString, paramArrayOfString);
  }

  public boolean replace(T paramT)
  {
    boolean bool;
    ContentValues localContentValues;
    int i;
    int j;
    if (!Util.isNullOrNil(this.bT.primaryKey))
    {
      bool = true;
      Assert.assertTrue("replace primaryKey == null", bool);
      localContentValues = paramT.convertTo();
      if (localContentValues != null)
      {
        i = localContentValues.size();
        j = paramT.getDBInfo().fields.length;
        if (!localContentValues.containsKey("rowid"))
          break label83;
      }
    }
    label83: for (int k = 1; ; k = 0)
    {
      if (i == k + j)
        break label89;
      g("replace failed, cv.size() != item.fields().length");
      return false;
      bool = false;
      break;
    }
    label89: if (a(localContentValues))
    {
      f("no need replace , fields no change");
      return true;
    }
    if (this.bS.replace(getTableName(), this.bT.primaryKey, localContentValues) > 0L)
    {
      doNotify(this.bT.primaryKey);
      return true;
    }
    g("replace failed");
    return false;
  }

  public boolean update(long paramLong, T paramT)
  {
    ContentValues localContentValues = paramT.convertTo();
    int i;
    if ((localContentValues == null) || (localContentValues.size() <= 0))
    {
      g("update failed, value.size <= 0");
      i = 0;
    }
    while (true)
    {
      return i;
      ISQLiteDatabase localISQLiteDatabase1 = this.bS;
      String str1 = getTableName();
      String[] arrayOfString1 = this.bT.columns;
      String[] arrayOfString2 = new String[1];
      arrayOfString2[0] = String.valueOf(paramLong);
      Cursor localCursor = localISQLiteDatabase1.query(str1, arrayOfString1, "rowid = ?", arrayOfString2, null, null, null);
      if (IAutoDBItem.checkIOEqual(localContentValues, localCursor))
      {
        localCursor.close();
        f("no need replace , fields no change");
        return true;
      }
      localCursor.close();
      ISQLiteDatabase localISQLiteDatabase2 = this.bS;
      String str2 = getTableName();
      String[] arrayOfString3 = new String[1];
      arrayOfString3[0] = String.valueOf(paramLong);
      if (localISQLiteDatabase2.update(str2, localContentValues, "rowid = ?", arrayOfString3) > 0);
      for (i = 1; i != 0; i = 0)
      {
        doNotify();
        return i;
      }
    }
  }

  public boolean update(T paramT, String[] paramArrayOfString)
  {
    int i = 0;
    ContentValues localContentValues = paramT.convertTo();
    if ((localContentValues == null) || (localContentValues.size() <= 0))
      g("update failed, value.size <= 0");
    while (true)
    {
      return i;
      if ((paramArrayOfString != null) && (paramArrayOfString.length > 0))
        break;
      f("update with primary key");
      if (a(localContentValues))
      {
        f("no need replace , fields no change");
        return true;
      }
      ISQLiteDatabase localISQLiteDatabase = this.bS;
      String str1 = getTableName();
      String str2 = this.bT.primaryKey + " = ?";
      String[] arrayOfString = new String[1];
      arrayOfString[0] = Util.nullAsNil(localContentValues.getAsString(this.bT.primaryKey));
      int j = localISQLiteDatabase.update(str1, localContentValues, str2, arrayOfString);
      i = 0;
      if (j > 0)
        i = 1;
      if (i == 0)
        continue;
      doNotify();
      return i;
    }
    StringBuilder localStringBuilder = a(localContentValues, paramArrayOfString);
    if (localStringBuilder == null)
    {
      g("update failed, check keys failed");
      return false;
    }
    if (this.bS.update(getTableName(), localContentValues, localStringBuilder.toString(), a(paramArrayOfString, localContentValues)) > 0)
    {
      doNotify(this.bT.primaryKey);
      return true;
    }
    g("update failed");
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.storage.MAutoStorage
 * JD-Core Version:    0.6.0
 */