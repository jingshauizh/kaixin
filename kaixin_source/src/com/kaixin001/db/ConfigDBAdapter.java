package com.kaixin001.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import java.util.ArrayList;

public class ConfigDBAdapter extends KXBaseDBAdapter
{
  public static final String CREATE_TABLE_SQL = "create table config (_rowid INTEGER primary key autoincrement, uid TEXT not null, field TEXT not null, value TEXT not null, reserved TEXT);";
  public static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS config";
  private static final String TAG = "ConfigDBAdapter";
  private static final String[] _COLUMNS_FOR_QUERY = { "_rowid", "uid", "field", "value", "reserved" };
  public static final String _CONFIG_COLUMN_FID = "field";
  public static final String _CONFIG_COLUMN_RESERVED = "reserved";
  public static final String _CONFIG_COLUMN_ROWID = "_rowid";
  public static final String _CONFIG_COLUMN_UID = "uid";
  public static final String _CONFIG_COLUMN_VALUE = "value";
  public static final String _CONFIG_TABLE_NAME = "config";

  public ConfigDBAdapter(Context paramContext)
  {
    super(paramContext);
  }

  // ERROR //
  public static String getConfig(Context paramContext, String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: new 2	com/kaixin001/db/ConfigDBAdapter
    //   5: dup
    //   6: aload_0
    //   7: invokespecial 49	com/kaixin001/db/ConfigDBAdapter:<init>	(Landroid/content/Context;)V
    //   10: astore 4
    //   12: aload 4
    //   14: aload_1
    //   15: aload_2
    //   16: invokevirtual 52	com/kaixin001/db/ConfigDBAdapter:getConfig	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   19: astore 7
    //   21: aload 4
    //   23: ifnull +8 -> 31
    //   26: aload 4
    //   28: invokevirtual 55	com/kaixin001/db/ConfigDBAdapter:close	()V
    //   31: aload 7
    //   33: areturn
    //   34: astore 5
    //   36: ldc 14
    //   38: ldc 57
    //   40: aload 5
    //   42: invokestatic 63	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   45: aload_3
    //   46: ifnull +7 -> 53
    //   49: aload_3
    //   50: invokevirtual 55	com/kaixin001/db/ConfigDBAdapter:close	()V
    //   53: aconst_null
    //   54: areturn
    //   55: astore 6
    //   57: aload_3
    //   58: ifnull +7 -> 65
    //   61: aload_3
    //   62: invokevirtual 55	com/kaixin001/db/ConfigDBAdapter:close	()V
    //   65: aload 6
    //   67: athrow
    //   68: astore 6
    //   70: aload 4
    //   72: astore_3
    //   73: goto -16 -> 57
    //   76: astore 5
    //   78: aload 4
    //   80: astore_3
    //   81: goto -45 -> 36
    //
    // Exception table:
    //   from	to	target	type
    //   2	12	34	java/lang/Exception
    //   2	12	55	finally
    //   36	45	55	finally
    //   12	21	68	finally
    //   12	21	76	java/lang/Exception
  }

  private ContentValues getContentValues(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("uid", paramString1);
    localContentValues.put("field", paramString2);
    localContentValues.put("value", paramString3);
    localContentValues.put("reserved", paramString4);
    return localContentValues;
  }

  // ERROR //
  public static long setConfig(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +5 -> 6
    //   4: lconst_0
    //   5: lreturn
    //   6: aconst_null
    //   7: astore 5
    //   9: new 2	com/kaixin001/db/ConfigDBAdapter
    //   12: dup
    //   13: aload_0
    //   14: invokespecial 49	com/kaixin001/db/ConfigDBAdapter:<init>	(Landroid/content/Context;)V
    //   17: astore 6
    //   19: aload 6
    //   21: aload_1
    //   22: aload_2
    //   23: aload_3
    //   24: aload 4
    //   26: invokevirtual 79	com/kaixin001/db/ConfigDBAdapter:addConfig	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)J
    //   29: lstore 9
    //   31: aload 6
    //   33: ifnull +8 -> 41
    //   36: aload 6
    //   38: invokevirtual 55	com/kaixin001/db/ConfigDBAdapter:close	()V
    //   41: lload 9
    //   43: lreturn
    //   44: astore 7
    //   46: ldc 14
    //   48: ldc 57
    //   50: aload 7
    //   52: invokestatic 63	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   55: aload 5
    //   57: ifnull +8 -> 65
    //   60: aload 5
    //   62: invokevirtual 55	com/kaixin001/db/ConfigDBAdapter:close	()V
    //   65: lconst_0
    //   66: lreturn
    //   67: astore 8
    //   69: aload 5
    //   71: ifnull +8 -> 79
    //   74: aload 5
    //   76: invokevirtual 55	com/kaixin001/db/ConfigDBAdapter:close	()V
    //   79: aload 8
    //   81: athrow
    //   82: astore 8
    //   84: aload 6
    //   86: astore 5
    //   88: goto -19 -> 69
    //   91: astore 7
    //   93: aload 6
    //   95: astore 5
    //   97: goto -51 -> 46
    //
    // Exception table:
    //   from	to	target	type
    //   9	19	44	java/lang/Exception
    //   9	19	67	finally
    //   46	55	67	finally
    //   19	31	82	finally
    //   19	31	91	java/lang/Exception
  }

  public long addConfig(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    String[] arrayOfString = { paramString1, paramString2 };
    Cursor localCursor = super.query("config", _COLUMNS_FOR_QUERY, "uid = ? and field = ?", arrayOfString, null, null, null, null);
    ContentValues localContentValues = getContentValues(paramString1, paramString2, paramString3, paramString4);
    long l1;
    if ((localCursor != null) && (localCursor.moveToFirst()))
    {
      long l2 = localCursor.getLong(0);
      l1 = super.update("config", localContentValues, "_rowid=" + l2, null);
    }
    while (true)
    {
      if (localCursor != null)
        localCursor.close();
      return l1;
      l1 = super.insert("config", null, localContentValues);
    }
  }

  public int delConfigs(String paramString1, String paramString2, int paramInt, String paramString3)
  {
    int i = -1;
    String[] arrayOfString = { paramString1, paramString2 };
    Cursor localCursor = super.query("config", _COLUMNS_FOR_QUERY, "uid = ? and field = ?", arrayOfString, null, null, null, null);
    if ((localCursor != null) && (localCursor.moveToFirst()))
    {
      long l = localCursor.getLong(0);
      i = super.delete("config", "_rowid=" + l, null);
    }
    if (localCursor != null)
      localCursor.close();
    return i;
  }

  public String getConfig(String paramString1, String paramString2)
  {
    Object localObject1;
    if ((TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2)))
      localObject1 = null;
    while (true)
    {
      return localObject1;
      localObject1 = "";
      Cursor localCursor = null;
      String[] arrayOfString = { paramString1, paramString2 };
      try
      {
        localCursor = super.query("config", _COLUMNS_FOR_QUERY, "uid = ? and field = ?", arrayOfString, null, null, null, null);
        if (localCursor != null)
          while (true)
          {
            boolean bool = localCursor.moveToNext();
            if (!bool)
              return localObject1;
            String str = localCursor.getString(localCursor.getColumnIndex("value"));
            localObject1 = str;
          }
        return null;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return localObject1;
      }
      finally
      {
        if (localCursor != null)
          localCursor.close();
      }
    }
    throw localObject2;
  }

  public ArrayList<String> loadConfigs(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    String[] arrayOfString = { paramString };
    Cursor localCursor = super.query("config", _COLUMNS_FOR_QUERY, "uid = ? ", arrayOfString, null, null, null, null);
    int i;
    if ((localCursor != null) && (localCursor.getCount() > 0))
      i = localCursor.getColumnIndex("field");
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
 * Qualified Name:     com.kaixin001.db.ConfigDBAdapter
 * JD-Core Version:    0.6.0
 */