package com.kaixin001.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.concurrent.locks.ReentrantLock;

public class UpLoadTaskListDBAdapter extends KXBaseDBAdapter
{
  public static final String CREATE_TABLE_SQL = "create table uploadTaskListDBTable (nID INTEGER primary key autoincrement, DATA1 TEXT, DATA2 TEXT, DATA3 TEXT, DATA4 TEXT, DATA5 TEXT, DATA6 TEXT, DATA7 TEXT, DATA8 INTEGER, DATA9 TEXT, DATA10 INTEGER, DATA11 TEXT, DATA12 TEXT, DATA15 TEXT not null, DATA16 INTEGER, DATA17 TEXT, DATA18 TEXT, DATA19 TEXT, DATA20 TEXT, DATA21 TEXT);";
  public static final String DATA1 = "DATA1";
  public static final String DATA10 = "DATA10";
  public static final String DATA11 = "DATA11";
  public static final String DATA12 = "DATA12";
  public static final String DATA15 = "DATA15";
  public static final String DATA16 = "DATA16";
  public static final String DATA17 = "DATA17";
  public static final String DATA18 = "DATA18";
  public static final String DATA19 = "DATA19";
  public static final String DATA2 = "DATA2";
  public static final String DATA20 = "DATA20";
  public static final String DATA21 = "DATA21";
  public static final String DATA3 = "DATA3";
  public static final String DATA4 = "DATA4";
  public static final String DATA5 = "DATA5";
  public static final String DATA6 = "DATA6";
  public static final String DATA7 = "DATA7";
  public static final String DATA8 = "DATA8";
  public static final String DATA9 = "DATA9";
  public static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS uploadTaskListDBTable";
  public static final String NID = "nID";
  public static final String _UPLOAD_TASK_LIST_TABLE_NAME = "uploadTaskListDBTable";
  private ReentrantLock mLock = null;

  static
  {
    if (!UpLoadTaskListDBAdapter.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public UpLoadTaskListDBAdapter(Context paramContext)
  {
    super(paramContext);
  }

  public int deleteATask(int paramInt)
  {
    this.mLock.lock();
    try
    {
      int i = this.mDb.delete("uploadTaskListDBTable", "nID=" + paramInt, null);
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return 0;
    }
    finally
    {
      this.mLock.unlock();
    }
    throw localObject;
  }

  public int deleteTasks(String paramString)
  {
    this.mLock.lock();
    try
    {
      int i = this.mDb.delete("uploadTaskListDBTable", paramString, null);
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return 0;
    }
    finally
    {
      this.mLock.unlock();
    }
    throw localObject;
  }

  public int deleteTasks(String paramString, int paramInt)
  {
    assert (paramString.length() == 19);
    String str;
    if (paramInt != -1)
      str = "DATA11<" + paramString + " and " + "DATA16" + " = " + paramInt;
    while (true)
    {
      this.mLock.lock();
      try
      {
        int i = this.mDb.delete("uploadTaskListDBTable", str, null);
        return i;
        str = "DATA11<" + paramString;
        continue;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return 0;
      }
      finally
      {
        this.mLock.unlock();
      }
    }
    throw localObject;
  }

  public int deleteTasks(String paramString, int paramInt1, int paramInt2)
  {
    assert (paramString.length() == 19);
    String str;
    if (paramInt1 != -1)
      str = " DATA11 <" + paramString + " and DATA16" + " = " + paramInt1;
    while (true)
    {
      if (paramInt2 != -1)
        str = str + " and DATA8 = " + paramInt2;
      this.mLock.lock();
      try
      {
        int i = this.mDb.delete("uploadTaskListDBTable", str, null);
        return i;
        str = " DATA11 <" + paramString;
        continue;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return 0;
      }
      finally
      {
        this.mLock.unlock();
      }
    }
    throw localObject;
  }

  public int deleteTasks(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    assert (paramString1.length() == 19);
    assert (paramString2.length() == 19);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(" DATA12 < " + paramString1);
    if (paramString2 != null)
      localStringBuilder.append(" and DATA11 <" + paramString2);
    if (paramInt1 != -1)
      localStringBuilder.append("and DATA16 = " + paramInt1);
    if (paramInt2 != -1)
      localStringBuilder.append(" and DATA8 = " + paramInt2);
    this.mLock.lock();
    try
    {
      int i = this.mDb.delete("uploadTaskListDBTable", localStringBuilder.toString(), null);
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return 0;
    }
    finally
    {
      this.mLock.unlock();
    }
    throw localObject;
  }

  // ERROR //
  public TaskParameters getTask(int paramInt)
    throws android.database.SQLException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: new 94	java/lang/StringBuilder
    //   5: dup
    //   6: ldc 166
    //   8: invokespecial 99	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   11: iload_1
    //   12: invokestatic 171	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   15: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   18: invokevirtual 107	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   21: astore 4
    //   23: aload_0
    //   24: getfield 92	com/kaixin001/db/UpLoadTaskListDBAdapter:mDb	Landroid/database/sqlite/SQLiteDatabase;
    //   27: iconst_1
    //   28: ldc 58
    //   30: aconst_null
    //   31: aload 4
    //   33: aconst_null
    //   34: aconst_null
    //   35: aconst_null
    //   36: aconst_null
    //   37: aconst_null
    //   38: invokevirtual 175	android/database/sqlite/SQLiteDatabase:query	(ZLjava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   41: astore_2
    //   42: aconst_null
    //   43: astore 5
    //   45: aload_2
    //   46: ifnull +39 -> 85
    //   49: aload_2
    //   50: invokeinterface 180 1 0
    //   55: istore 6
    //   57: aconst_null
    //   58: astore 5
    //   60: iload 6
    //   62: ifeq +23 -> 85
    //   65: new 182	com/kaixin001/db/UpLoadTaskListDBAdapter$TaskParameters
    //   68: dup
    //   69: invokespecial 183	com/kaixin001/db/UpLoadTaskListDBAdapter$TaskParameters:<init>	()V
    //   72: astore 7
    //   74: aload_0
    //   75: aload 7
    //   77: aload_2
    //   78: invokevirtual 187	com/kaixin001/db/UpLoadTaskListDBAdapter:setTaskData	(Lcom/kaixin001/db/UpLoadTaskListDBAdapter$TaskParameters;Landroid/database/Cursor;)V
    //   81: aload 7
    //   83: astore 5
    //   85: aload_2
    //   86: ifnull +9 -> 95
    //   89: aload_2
    //   90: invokeinterface 190 1 0
    //   95: aload 5
    //   97: areturn
    //   98: astore_3
    //   99: aload_2
    //   100: ifnull +9 -> 109
    //   103: aload_2
    //   104: invokeinterface 190 1 0
    //   109: aload_3
    //   110: athrow
    //   111: astore_3
    //   112: goto -13 -> 99
    //
    // Exception table:
    //   from	to	target	type
    //   2	42	98	finally
    //   49	57	98	finally
    //   65	74	98	finally
    //   74	81	111	finally
  }

  public int getTaskCount(int paramInt)
  {
    Cursor localCursor = null;
    try
    {
      String str = "DATA8 = " + paramInt;
      localCursor = this.mDb.query(true, "uploadTaskListDBTable", new String[] { "count(*)" }, str, null, null, null, null, null);
      int i = 0;
      if (localCursor != null)
      {
        boolean bool = localCursor.moveToNext();
        i = 0;
        if (bool)
        {
          int j = localCursor.getInt(0);
          i = j;
        }
      }
      return i;
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
    throw localObject;
  }

  // ERROR //
  public java.util.ArrayList<TaskParameters> getTasks(int paramInt1, int paramInt2, int paramInt3, String paramString)
    throws android.database.SQLException
  {
    // Byte code:
    //   0: new 202	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 203	java/util/ArrayList:<init>	()V
    //   7: astore 5
    //   9: aconst_null
    //   10: astore 6
    //   12: iconst_0
    //   13: invokestatic 208	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   16: astore 7
    //   18: new 94	java/lang/StringBuilder
    //   21: dup
    //   22: invokespecial 154	java/lang/StringBuilder:<init>	()V
    //   25: astore 8
    //   27: iload_1
    //   28: iconst_m1
    //   29: if_icmpeq +34 -> 63
    //   32: aload 8
    //   34: new 94	java/lang/StringBuilder
    //   37: dup
    //   38: ldc 210
    //   40: invokespecial 99	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   43: iload_1
    //   44: invokestatic 171	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   47: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   50: invokevirtual 107	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   53: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   56: pop
    //   57: iconst_1
    //   58: invokestatic 208	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   61: astore 7
    //   63: iload_2
    //   64: iconst_m1
    //   65: if_icmpeq +50 -> 115
    //   68: aload 7
    //   70: invokevirtual 213	java/lang/Boolean:booleanValue	()Z
    //   73: ifeq +11 -> 84
    //   76: aload 8
    //   78: ldc 139
    //   80: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   83: pop
    //   84: aload 8
    //   86: new 94	java/lang/StringBuilder
    //   89: dup
    //   90: ldc 215
    //   92: invokespecial 99	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   95: iload_2
    //   96: invokestatic 171	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   99: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   102: invokevirtual 107	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   105: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   108: pop
    //   109: iconst_1
    //   110: invokestatic 208	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   113: astore 7
    //   115: aload 4
    //   117: ifnull +42 -> 159
    //   120: aload 7
    //   122: invokevirtual 213	java/lang/Boolean:booleanValue	()Z
    //   125: ifeq +11 -> 136
    //   128: aload 8
    //   130: ldc 139
    //   132: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   135: pop
    //   136: aload 8
    //   138: new 94	java/lang/StringBuilder
    //   141: dup
    //   142: ldc 217
    //   144: invokespecial 99	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   147: aload 4
    //   149: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   152: invokevirtual 107	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   155: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   158: pop
    //   159: aload 8
    //   161: invokevirtual 107	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   164: invokevirtual 128	java/lang/String:length	()I
    //   167: ifle +82 -> 249
    //   170: aload 8
    //   172: invokevirtual 107	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   175: astore 9
    //   177: iload_3
    //   178: ifgt +77 -> 255
    //   181: aconst_null
    //   182: astore 10
    //   184: aload_0
    //   185: getfield 92	com/kaixin001/db/UpLoadTaskListDBAdapter:mDb	Landroid/database/sqlite/SQLiteDatabase;
    //   188: iconst_1
    //   189: ldc 58
    //   191: aconst_null
    //   192: aload 9
    //   194: aconst_null
    //   195: aconst_null
    //   196: aconst_null
    //   197: aconst_null
    //   198: aload 10
    //   200: invokevirtual 175	android/database/sqlite/SQLiteDatabase:query	(ZLjava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   203: astore 12
    //   205: aload 12
    //   207: astore 6
    //   209: aload 6
    //   211: ifnull +23 -> 234
    //   214: aconst_null
    //   215: astore 13
    //   217: aload 6
    //   219: invokeinterface 180 1 0
    //   224: istore 15
    //   226: iload 15
    //   228: ifne +36 -> 264
    //   231: aload 13
    //   233: pop
    //   234: aload 6
    //   236: ifnull +10 -> 246
    //   239: aload 6
    //   241: invokeinterface 190 1 0
    //   246: aload 5
    //   248: areturn
    //   249: aconst_null
    //   250: astore 9
    //   252: goto -75 -> 177
    //   255: iload_3
    //   256: invokestatic 171	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   259: astore 10
    //   261: goto -77 -> 184
    //   264: new 182	com/kaixin001/db/UpLoadTaskListDBAdapter$TaskParameters
    //   267: dup
    //   268: invokespecial 183	com/kaixin001/db/UpLoadTaskListDBAdapter$TaskParameters:<init>	()V
    //   271: astore 16
    //   273: aload_0
    //   274: aload 16
    //   276: aload 6
    //   278: invokevirtual 187	com/kaixin001/db/UpLoadTaskListDBAdapter:setTaskData	(Lcom/kaixin001/db/UpLoadTaskListDBAdapter$TaskParameters;Landroid/database/Cursor;)V
    //   281: aload 5
    //   283: aload 16
    //   285: invokevirtual 221	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   288: pop
    //   289: aload 16
    //   291: astore 13
    //   293: goto -76 -> 217
    //   296: astore 11
    //   298: aload 6
    //   300: ifnull +10 -> 310
    //   303: aload 6
    //   305: invokeinterface 190 1 0
    //   310: aload 11
    //   312: athrow
    //   313: astore 11
    //   315: aload 13
    //   317: pop
    //   318: goto -20 -> 298
    //
    // Exception table:
    //   from	to	target	type
    //   184	205	296	finally
    //   273	289	296	finally
    //   217	226	313	finally
    //   264	273	313	finally
  }

  // ERROR //
  public java.util.ArrayList<TaskParameters> getTasks(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, int paramInt3)
    throws android.database.SQLException
  {
    // Byte code:
    //   0: getstatic 70	com/kaixin001/db/UpLoadTaskListDBAdapter:$assertionsDisabled	Z
    //   3: ifne +20 -> 23
    //   6: aload_1
    //   7: invokevirtual 128	java/lang/String:length	()I
    //   10: bipush 19
    //   12: if_icmpeq +11 -> 23
    //   15: new 130	java/lang/AssertionError
    //   18: dup
    //   19: invokespecial 132	java/lang/AssertionError:<init>	()V
    //   22: athrow
    //   23: getstatic 70	com/kaixin001/db/UpLoadTaskListDBAdapter:$assertionsDisabled	Z
    //   26: ifne +20 -> 46
    //   29: aload_2
    //   30: invokevirtual 128	java/lang/String:length	()I
    //   33: bipush 19
    //   35: if_icmpeq +11 -> 46
    //   38: new 130	java/lang/AssertionError
    //   41: dup
    //   42: invokespecial 132	java/lang/AssertionError:<init>	()V
    //   45: athrow
    //   46: new 202	java/util/ArrayList
    //   49: dup
    //   50: invokespecial 203	java/util/ArrayList:<init>	()V
    //   53: astore 7
    //   55: aconst_null
    //   56: astore 8
    //   58: iconst_0
    //   59: invokestatic 208	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   62: astore 9
    //   64: new 94	java/lang/StringBuilder
    //   67: dup
    //   68: invokespecial 154	java/lang/StringBuilder:<init>	()V
    //   71: astore 10
    //   73: aload_1
    //   74: ifnull +47 -> 121
    //   77: aload 9
    //   79: invokevirtual 213	java/lang/Boolean:booleanValue	()Z
    //   82: ifeq +11 -> 93
    //   85: aload 10
    //   87: ldc 139
    //   89: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   92: pop
    //   93: aload 10
    //   95: new 94	java/lang/StringBuilder
    //   98: dup
    //   99: ldc 224
    //   101: invokespecial 99	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   104: aload_1
    //   105: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   108: invokevirtual 107	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   111: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   114: pop
    //   115: iconst_1
    //   116: invokestatic 208	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   119: astore 9
    //   121: aload_2
    //   122: ifnull +47 -> 169
    //   125: aload 9
    //   127: invokevirtual 213	java/lang/Boolean:booleanValue	()Z
    //   130: ifeq +11 -> 141
    //   133: aload 10
    //   135: ldc 139
    //   137: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   140: pop
    //   141: aload 10
    //   143: new 94	java/lang/StringBuilder
    //   146: dup
    //   147: ldc 226
    //   149: invokespecial 99	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   152: aload_2
    //   153: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   156: invokevirtual 107	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   159: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   162: pop
    //   163: iconst_1
    //   164: invokestatic 208	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   167: astore 9
    //   169: aload_3
    //   170: ifnull +47 -> 217
    //   173: aload 9
    //   175: invokevirtual 213	java/lang/Boolean:booleanValue	()Z
    //   178: ifeq +11 -> 189
    //   181: aload 10
    //   183: ldc 139
    //   185: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   188: pop
    //   189: aload 10
    //   191: new 94	java/lang/StringBuilder
    //   194: dup
    //   195: ldc 217
    //   197: invokespecial 99	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   200: aload_3
    //   201: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   204: invokevirtual 107	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   207: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   210: pop
    //   211: iconst_1
    //   212: invokestatic 208	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   215: astore 9
    //   217: iload 4
    //   219: iconst_m1
    //   220: if_icmpeq +51 -> 271
    //   223: aload 9
    //   225: invokevirtual 213	java/lang/Boolean:booleanValue	()Z
    //   228: ifeq +11 -> 239
    //   231: aload 10
    //   233: ldc 139
    //   235: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   238: pop
    //   239: aload 10
    //   241: new 94	java/lang/StringBuilder
    //   244: dup
    //   245: ldc 210
    //   247: invokespecial 99	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   250: iload 4
    //   252: invokestatic 171	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   255: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   258: invokevirtual 107	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   261: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   264: pop
    //   265: iconst_1
    //   266: invokestatic 208	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   269: astore 9
    //   271: iload 5
    //   273: iconst_m1
    //   274: if_icmpeq +50 -> 324
    //   277: aload 9
    //   279: invokevirtual 213	java/lang/Boolean:booleanValue	()Z
    //   282: ifeq +11 -> 293
    //   285: aload 10
    //   287: ldc 139
    //   289: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   292: pop
    //   293: aload 10
    //   295: new 94	java/lang/StringBuilder
    //   298: dup
    //   299: ldc 215
    //   301: invokespecial 99	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   304: iload 5
    //   306: invokestatic 171	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   309: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   312: invokevirtual 107	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   315: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   318: pop
    //   319: iconst_1
    //   320: invokestatic 208	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   323: pop
    //   324: aload 10
    //   326: invokevirtual 107	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   329: invokevirtual 128	java/lang/String:length	()I
    //   332: ifle +83 -> 415
    //   335: aload 10
    //   337: invokevirtual 107	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   340: astore 11
    //   342: iload 6
    //   344: ifgt +77 -> 421
    //   347: aconst_null
    //   348: astore 12
    //   350: aload_0
    //   351: getfield 92	com/kaixin001/db/UpLoadTaskListDBAdapter:mDb	Landroid/database/sqlite/SQLiteDatabase;
    //   354: iconst_1
    //   355: ldc 58
    //   357: aconst_null
    //   358: aload 11
    //   360: aconst_null
    //   361: aconst_null
    //   362: aconst_null
    //   363: aconst_null
    //   364: aload 12
    //   366: invokevirtual 175	android/database/sqlite/SQLiteDatabase:query	(ZLjava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   369: astore 14
    //   371: aload 14
    //   373: astore 8
    //   375: aload 8
    //   377: ifnull +23 -> 400
    //   380: aconst_null
    //   381: astore 15
    //   383: aload 8
    //   385: invokeinterface 180 1 0
    //   390: istore 17
    //   392: iload 17
    //   394: ifne +37 -> 431
    //   397: aload 15
    //   399: pop
    //   400: aload 8
    //   402: ifnull +10 -> 412
    //   405: aload 8
    //   407: invokeinterface 190 1 0
    //   412: aload 7
    //   414: areturn
    //   415: aconst_null
    //   416: astore 11
    //   418: goto -76 -> 342
    //   421: iload 6
    //   423: invokestatic 171	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   426: astore 12
    //   428: goto -78 -> 350
    //   431: new 182	com/kaixin001/db/UpLoadTaskListDBAdapter$TaskParameters
    //   434: dup
    //   435: invokespecial 183	com/kaixin001/db/UpLoadTaskListDBAdapter$TaskParameters:<init>	()V
    //   438: astore 18
    //   440: aload_0
    //   441: aload 18
    //   443: aload 8
    //   445: invokevirtual 187	com/kaixin001/db/UpLoadTaskListDBAdapter:setTaskData	(Lcom/kaixin001/db/UpLoadTaskListDBAdapter$TaskParameters;Landroid/database/Cursor;)V
    //   448: aload 7
    //   450: aload 18
    //   452: invokevirtual 221	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   455: pop
    //   456: aload 18
    //   458: astore 15
    //   460: goto -77 -> 383
    //   463: astore 13
    //   465: aload 8
    //   467: ifnull +10 -> 477
    //   470: aload 8
    //   472: invokeinterface 190 1 0
    //   477: aload 13
    //   479: athrow
    //   480: astore 13
    //   482: aload 15
    //   484: pop
    //   485: goto -20 -> 465
    //
    // Exception table:
    //   from	to	target	type
    //   350	371	463	finally
    //   440	456	463	finally
    //   383	392	480	finally
    //   431	440	480	finally
  }

  public long insertTask(ContentValues paramContentValues)
  {
    if (paramContentValues == null)
    {
      if (!$assertionsDisabled)
        throw new AssertionError();
      return -1L;
    }
    this.mLock.lock();
    try
    {
      long l = this.mDb.insertOrThrow("uploadTaskListDBTable", null, paramContentValues);
      return l;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return 0L;
    }
    finally
    {
      this.mLock.unlock();
    }
    throw localObject;
  }

  public void setTaskData(TaskParameters paramTaskParameters, Cursor paramCursor)
  {
    assert ((paramTaskParameters != null) && (paramCursor != null));
    paramTaskParameters.mId = paramCursor.getInt(0);
    paramTaskParameters.mData1 = paramCursor.getString(1);
    paramTaskParameters.mData2 = paramCursor.getString(2);
    paramTaskParameters.mData3 = paramCursor.getString(3);
    paramTaskParameters.mData4 = paramCursor.getString(4);
    paramTaskParameters.mData5 = paramCursor.getString(5);
    paramTaskParameters.mData6 = paramCursor.getString(6);
    paramTaskParameters.mData7 = paramCursor.getString(7);
    paramTaskParameters.status = paramCursor.getInt(8);
    paramTaskParameters.mData9 = paramCursor.getString(9);
    paramTaskParameters.mData10 = paramCursor.getInt(10);
    paramTaskParameters.mData11 = paramCursor.getString(11);
    paramTaskParameters.mData12 = paramCursor.getString(12);
    paramTaskParameters.mUid = paramCursor.getString(13);
    paramTaskParameters.mData16 = paramCursor.getInt(14);
    paramTaskParameters.mData17 = paramCursor.getString(15);
    paramTaskParameters.mData18 = paramCursor.getString(16);
    paramTaskParameters.mData19 = paramCursor.getString(17);
    paramTaskParameters.mData20 = paramCursor.getString(18);
    paramTaskParameters.mData21 = paramCursor.getString(19);
  }

  public int updateTask(int paramInt, ContentValues paramContentValues)
  {
    this.mLock.lock();
    try
    {
      int i = this.mDb.update("uploadTaskListDBTable", paramContentValues, "nID=" + paramInt, null);
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return 0;
    }
    finally
    {
      this.mLock.unlock();
    }
    throw localObject;
  }

  public static class TaskParameters
  {
    public String mData1;
    public int mData10;
    public String mData11;
    public String mData12;
    public int mData16;
    public String mData17;
    public String mData18;
    public String mData19;
    public String mData2;
    public String mData20;
    public String mData21;
    public String mData3;
    public String mData4;
    public String mData5;
    public String mData6;
    public String mData7;
    public String mData9;
    public int mId;
    public String mUid;
    public int status;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.db.UpLoadTaskListDBAdapter
 * JD-Core Version:    0.6.0
 */