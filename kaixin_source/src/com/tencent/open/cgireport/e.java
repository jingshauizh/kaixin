package com.tencent.open.cgireport;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;

public class e
{
  private Context a;
  private SQLiteDatabase b;
  private b c;
  private int d;

  public e(Context paramContext)
  {
    this.a = paramContext;
    this.c = new b(this, paramContext, "sdk_cgi_report.db", null, 2);
    this.d = c().size();
  }

  public int a(ArrayList<a> paramArrayList)
  {
    monitorenter;
    int i;
    try
    {
      Log.i("cgi_report_debug", "ReportDataModal backupOldItems count = " + paramArrayList.size());
      Iterator localIterator = paramArrayList.iterator();
      i = 0;
      while (true)
        if (localIterator.hasNext())
        {
          a locala = (a)localIterator.next();
          ContentValues localContentValues = new ContentValues();
          localContentValues.put("apn", locala.a());
          localContentValues.put("frequency", locala.b());
          localContentValues.put("commandid", locala.c());
          localContentValues.put("resultcode", locala.d());
          localContentValues.put("tmcost", locala.e());
          localContentValues.put("reqsize", locala.f());
          localContentValues.put("rspsize", locala.g());
          localContentValues.put("deviceinfo", locala.i());
          localContentValues.put("detail", locala.h());
          try
          {
            this.b = this.c.getWritableDatabase();
            this.b.insertOrThrow("olddata", null, localContentValues);
            this.b.close();
            i++;
          }
          catch (Exception localException)
          {
            while (true)
              localException.printStackTrace();
          }
        }
    }
    finally
    {
      monitorexit;
    }
    Log.i("cgi_report_debug", "ReportDataModal backupOldItems succ_count = " + i);
    monitorexit;
    return i;
  }

  // ERROR //
  public boolean a()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: ldc 44
    //   4: ldc 153
    //   6: invokestatic 66	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   9: pop
    //   10: aload_0
    //   11: aload_0
    //   12: getfield 28	com/tencent/open/cgireport/e:c	Lcom/tencent/open/cgireport/b;
    //   15: invokevirtual 133	com/tencent/open/cgireport/b:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   18: putfield 135	com/tencent/open/cgireport/e:b	Landroid/database/sqlite/SQLiteDatabase;
    //   21: aload_0
    //   22: getfield 135	com/tencent/open/cgireport/e:b	Landroid/database/sqlite/SQLiteDatabase;
    //   25: ldc 155
    //   27: invokevirtual 159	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   30: aload_0
    //   31: getfield 135	com/tencent/open/cgireport/e:b	Landroid/database/sqlite/SQLiteDatabase;
    //   34: invokevirtual 146	android/database/sqlite/SQLiteDatabase:close	()V
    //   37: ldc 44
    //   39: ldc 161
    //   41: invokestatic 66	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   44: pop
    //   45: iconst_1
    //   46: istore 4
    //   48: aload_0
    //   49: monitorexit
    //   50: iload 4
    //   52: ireturn
    //   53: astore_3
    //   54: aload_3
    //   55: invokevirtual 149	java/lang/Exception:printStackTrace	()V
    //   58: iconst_0
    //   59: istore 4
    //   61: goto -13 -> 48
    //   64: astore_1
    //   65: aload_0
    //   66: monitorexit
    //   67: aload_1
    //   68: athrow
    //   69: astore 5
    //   71: aload 5
    //   73: invokevirtual 149	java/lang/Exception:printStackTrace	()V
    //   76: aload_0
    //   77: getfield 135	com/tencent/open/cgireport/e:b	Landroid/database/sqlite/SQLiteDatabase;
    //   80: invokevirtual 146	android/database/sqlite/SQLiteDatabase:close	()V
    //   83: iconst_0
    //   84: istore 4
    //   86: goto -38 -> 48
    //
    // Exception table:
    //   from	to	target	type
    //   10	21	53	java/lang/Exception
    //   2	10	64	finally
    //   10	21	64	finally
    //   21	30	64	finally
    //   30	45	64	finally
    //   54	58	64	finally
    //   71	83	64	finally
    //   21	30	69	java/lang/Exception
  }

  public boolean a(String paramString1, String paramString2, String paramString3, int paramInt, long paramLong1, long paramLong2, long paramLong3, String paramString4)
  {
    monitorenter;
    try
    {
      if (paramString3.contains("?"))
        paramString3 = paramString3.split("\\?")[0];
      Log.i("cgi_report_debug", "ReportDataModal addNewItem apn=" + paramString1 + ",frequency=" + paramString2 + ",commandid=" + paramString3 + ",resultCode=" + paramInt + ",costTime=" + paramLong1 + ",reqSzie=" + paramLong2 + ",rspSize=" + paramLong3);
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("apn", paramString1 + "");
      localContentValues.put("frequency", paramString2 + "");
      localContentValues.put("commandid", paramString3 + "");
      localContentValues.put("resultcode", paramInt + "");
      localContentValues.put("tmcost", paramLong1 + "");
      localContentValues.put("reqsize", paramLong2 + "");
      localContentValues.put("rspsize", paramLong3 + "");
      StringBuilder localStringBuilder1 = new StringBuilder();
      localStringBuilder1.append("network=").append(paramString1).append('&');
      StringBuilder localStringBuilder2 = localStringBuilder1.append("sdcard=");
      int i;
      if (Environment.getExternalStorageState().equals("mounted"))
        i = 1;
      while (true)
      {
        localStringBuilder2.append(i).append('&');
        localStringBuilder1.append("wifi=").append(c.c(this.a));
        localContentValues.put("deviceinfo", localStringBuilder1.toString());
        localContentValues.put("detail", paramString4);
        try
        {
          this.b = this.c.getWritableDatabase();
          this.b.insertOrThrow("newdata", null, localContentValues);
          this.b.close();
          Log.i("cgi_report_debug", "ReportDataModal addNewItem success");
          this.d = (1 + this.d);
          j = 1;
          monitorexit;
          return j;
          i = 0;
        }
        catch (Exception localException)
        {
          while (true)
          {
            Log.e("cgi_report_debug", "ReportDataModal addNewItem failed");
            localException.printStackTrace();
            int j = 0;
          }
        }
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  // ERROR //
  public boolean b()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: ldc 44
    //   4: ldc 230
    //   6: invokestatic 66	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   9: pop
    //   10: aload_0
    //   11: aload_0
    //   12: getfield 28	com/tencent/open/cgireport/e:c	Lcom/tencent/open/cgireport/b;
    //   15: invokevirtual 133	com/tencent/open/cgireport/b:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   18: putfield 135	com/tencent/open/cgireport/e:b	Landroid/database/sqlite/SQLiteDatabase;
    //   21: aload_0
    //   22: getfield 135	com/tencent/open/cgireport/e:b	Landroid/database/sqlite/SQLiteDatabase;
    //   25: ldc 232
    //   27: invokevirtual 159	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   30: aload_0
    //   31: iconst_0
    //   32: putfield 39	com/tencent/open/cgireport/e:d	I
    //   35: aload_0
    //   36: getfield 135	com/tencent/open/cgireport/e:b	Landroid/database/sqlite/SQLiteDatabase;
    //   39: invokevirtual 146	android/database/sqlite/SQLiteDatabase:close	()V
    //   42: ldc 44
    //   44: ldc 230
    //   46: invokestatic 66	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   49: pop
    //   50: iconst_1
    //   51: istore 4
    //   53: aload_0
    //   54: monitorexit
    //   55: iload 4
    //   57: ireturn
    //   58: astore_3
    //   59: aload_3
    //   60: invokevirtual 149	java/lang/Exception:printStackTrace	()V
    //   63: iconst_0
    //   64: istore 4
    //   66: goto -13 -> 53
    //   69: astore_1
    //   70: aload_0
    //   71: monitorexit
    //   72: aload_1
    //   73: athrow
    //   74: astore 5
    //   76: aload 5
    //   78: invokevirtual 149	java/lang/Exception:printStackTrace	()V
    //   81: aload_0
    //   82: getfield 135	com/tencent/open/cgireport/e:b	Landroid/database/sqlite/SQLiteDatabase;
    //   85: invokevirtual 146	android/database/sqlite/SQLiteDatabase:close	()V
    //   88: iconst_0
    //   89: istore 4
    //   91: goto -38 -> 53
    //
    // Exception table:
    //   from	to	target	type
    //   10	21	58	java/lang/Exception
    //   2	10	69	finally
    //   10	21	69	finally
    //   21	30	69	finally
    //   30	50	69	finally
    //   59	63	69	finally
    //   76	88	69	finally
    //   21	30	74	java/lang/Exception
  }

  // ERROR //
  public ArrayList<a> c()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: ldc 44
    //   4: ldc 234
    //   6: invokestatic 66	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   9: pop
    //   10: new 33	java/util/ArrayList
    //   13: dup
    //   14: invokespecial 235	java/util/ArrayList:<init>	()V
    //   17: astore_3
    //   18: aload_0
    //   19: aload_0
    //   20: getfield 28	com/tencent/open/cgireport/e:c	Lcom/tencent/open/cgireport/b;
    //   23: invokevirtual 238	com/tencent/open/cgireport/b:getReadableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   26: putfield 135	com/tencent/open/cgireport/e:b	Landroid/database/sqlite/SQLiteDatabase;
    //   29: aload_0
    //   30: getfield 135	com/tencent/open/cgireport/e:b	Landroid/database/sqlite/SQLiteDatabase;
    //   33: ldc 240
    //   35: iconst_0
    //   36: anewarray 166	java/lang/String
    //   39: invokevirtual 244	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   42: astore 6
    //   44: aload 6
    //   46: invokeinterface 249 1 0
    //   51: pop
    //   52: aload 6
    //   54: invokeinterface 252 1 0
    //   59: ifne +222 -> 281
    //   62: aload 6
    //   64: aload 6
    //   66: ldc 87
    //   68: invokeinterface 256 2 0
    //   73: invokeinterface 260 2 0
    //   78: astore 9
    //   80: aload 6
    //   82: aload 6
    //   84: ldc 95
    //   86: invokeinterface 256 2 0
    //   91: invokeinterface 260 2 0
    //   96: astore 10
    //   98: aload 6
    //   100: aload 6
    //   102: ldc 99
    //   104: invokeinterface 256 2 0
    //   109: invokeinterface 260 2 0
    //   114: astore 11
    //   116: aload 6
    //   118: aload 6
    //   120: ldc 103
    //   122: invokeinterface 256 2 0
    //   127: invokeinterface 260 2 0
    //   132: astore 12
    //   134: aload 6
    //   136: aload 6
    //   138: ldc 107
    //   140: invokeinterface 256 2 0
    //   145: invokeinterface 260 2 0
    //   150: astore 13
    //   152: aload 6
    //   154: aload 6
    //   156: ldc 112
    //   158: invokeinterface 256 2 0
    //   163: invokeinterface 260 2 0
    //   168: astore 14
    //   170: aload 6
    //   172: aload 6
    //   174: ldc 117
    //   176: invokeinterface 256 2 0
    //   181: invokeinterface 260 2 0
    //   186: astore 15
    //   188: aload 6
    //   190: aload 6
    //   192: ldc 126
    //   194: invokeinterface 256 2 0
    //   199: invokeinterface 260 2 0
    //   204: astore 16
    //   206: aload_3
    //   207: new 82	com/tencent/open/cgireport/a
    //   210: dup
    //   211: aload 9
    //   213: aload 10
    //   215: aload 11
    //   217: aload 12
    //   219: aload 13
    //   221: aload 14
    //   223: aload 15
    //   225: aload 6
    //   227: aload 6
    //   229: ldc 122
    //   231: invokeinterface 256 2 0
    //   236: invokeinterface 260 2 0
    //   241: aload 16
    //   243: invokespecial 263	com/tencent/open/cgireport/a:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   246: invokevirtual 266	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   249: pop
    //   250: aload 6
    //   252: invokeinterface 269 1 0
    //   257: pop
    //   258: goto -206 -> 52
    //   261: astore_1
    //   262: aload_0
    //   263: monitorexit
    //   264: aload_1
    //   265: athrow
    //   266: astore 4
    //   268: aload 4
    //   270: invokevirtual 149	java/lang/Exception:printStackTrace	()V
    //   273: aload_3
    //   274: astore 5
    //   276: aload_0
    //   277: monitorexit
    //   278: aload 5
    //   280: areturn
    //   281: aload 6
    //   283: invokeinterface 270 1 0
    //   288: aload_0
    //   289: getfield 135	com/tencent/open/cgireport/e:b	Landroid/database/sqlite/SQLiteDatabase;
    //   292: invokevirtual 146	android/database/sqlite/SQLiteDatabase:close	()V
    //   295: ldc 44
    //   297: new 46	java/lang/StringBuilder
    //   300: dup
    //   301: invokespecial 47	java/lang/StringBuilder:<init>	()V
    //   304: ldc_w 272
    //   307: invokevirtual 53	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   310: aload_3
    //   311: invokevirtual 37	java/util/ArrayList:size	()I
    //   314: invokevirtual 56	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   317: invokevirtual 60	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   320: invokestatic 66	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   323: pop
    //   324: aload_3
    //   325: astore 5
    //   327: goto -51 -> 276
    //
    // Exception table:
    //   from	to	target	type
    //   2	18	261	finally
    //   18	29	261	finally
    //   29	52	261	finally
    //   52	258	261	finally
    //   268	273	261	finally
    //   281	324	261	finally
    //   18	29	266	java/lang/Exception
  }

  // ERROR //
  public ArrayList<a> d()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: ldc 44
    //   4: ldc_w 274
    //   7: invokestatic 66	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   10: pop
    //   11: new 33	java/util/ArrayList
    //   14: dup
    //   15: invokespecial 235	java/util/ArrayList:<init>	()V
    //   18: astore_3
    //   19: aload_0
    //   20: aload_0
    //   21: getfield 28	com/tencent/open/cgireport/e:c	Lcom/tencent/open/cgireport/b;
    //   24: invokevirtual 238	com/tencent/open/cgireport/b:getReadableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   27: putfield 135	com/tencent/open/cgireport/e:b	Landroid/database/sqlite/SQLiteDatabase;
    //   30: aload_0
    //   31: getfield 135	com/tencent/open/cgireport/e:b	Landroid/database/sqlite/SQLiteDatabase;
    //   34: ldc_w 276
    //   37: iconst_0
    //   38: anewarray 166	java/lang/String
    //   41: invokevirtual 244	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   44: astore 6
    //   46: aload 6
    //   48: invokeinterface 249 1 0
    //   53: pop
    //   54: aload 6
    //   56: invokeinterface 252 1 0
    //   61: ifne +222 -> 283
    //   64: aload 6
    //   66: aload 6
    //   68: ldc 87
    //   70: invokeinterface 256 2 0
    //   75: invokeinterface 260 2 0
    //   80: astore 9
    //   82: aload 6
    //   84: aload 6
    //   86: ldc 95
    //   88: invokeinterface 256 2 0
    //   93: invokeinterface 260 2 0
    //   98: astore 10
    //   100: aload 6
    //   102: aload 6
    //   104: ldc 99
    //   106: invokeinterface 256 2 0
    //   111: invokeinterface 260 2 0
    //   116: astore 11
    //   118: aload 6
    //   120: aload 6
    //   122: ldc 103
    //   124: invokeinterface 256 2 0
    //   129: invokeinterface 260 2 0
    //   134: astore 12
    //   136: aload 6
    //   138: aload 6
    //   140: ldc 107
    //   142: invokeinterface 256 2 0
    //   147: invokeinterface 260 2 0
    //   152: astore 13
    //   154: aload 6
    //   156: aload 6
    //   158: ldc 112
    //   160: invokeinterface 256 2 0
    //   165: invokeinterface 260 2 0
    //   170: astore 14
    //   172: aload 6
    //   174: aload 6
    //   176: ldc 117
    //   178: invokeinterface 256 2 0
    //   183: invokeinterface 260 2 0
    //   188: astore 15
    //   190: aload 6
    //   192: aload 6
    //   194: ldc 126
    //   196: invokeinterface 256 2 0
    //   201: invokeinterface 260 2 0
    //   206: astore 16
    //   208: aload_3
    //   209: new 82	com/tencent/open/cgireport/a
    //   212: dup
    //   213: aload 9
    //   215: aload 10
    //   217: aload 11
    //   219: aload 12
    //   221: aload 13
    //   223: aload 14
    //   225: aload 15
    //   227: aload 6
    //   229: aload 6
    //   231: ldc 122
    //   233: invokeinterface 256 2 0
    //   238: invokeinterface 260 2 0
    //   243: aload 16
    //   245: invokespecial 263	com/tencent/open/cgireport/a:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   248: invokevirtual 266	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   251: pop
    //   252: aload 6
    //   254: invokeinterface 269 1 0
    //   259: pop
    //   260: goto -206 -> 54
    //   263: astore_1
    //   264: aload_0
    //   265: monitorexit
    //   266: aload_1
    //   267: athrow
    //   268: astore 4
    //   270: aload 4
    //   272: invokevirtual 149	java/lang/Exception:printStackTrace	()V
    //   275: aload_3
    //   276: astore 5
    //   278: aload_0
    //   279: monitorexit
    //   280: aload 5
    //   282: areturn
    //   283: aload 6
    //   285: invokeinterface 270 1 0
    //   290: aload_0
    //   291: getfield 135	com/tencent/open/cgireport/e:b	Landroid/database/sqlite/SQLiteDatabase;
    //   294: invokevirtual 146	android/database/sqlite/SQLiteDatabase:close	()V
    //   297: ldc 44
    //   299: new 46	java/lang/StringBuilder
    //   302: dup
    //   303: invokespecial 47	java/lang/StringBuilder:<init>	()V
    //   306: ldc_w 278
    //   309: invokevirtual 53	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   312: aload_3
    //   313: invokevirtual 37	java/util/ArrayList:size	()I
    //   316: invokevirtual 56	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   319: invokevirtual 60	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   322: invokestatic 66	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   325: pop
    //   326: aload_3
    //   327: astore 5
    //   329: goto -51 -> 278
    //
    // Exception table:
    //   from	to	target	type
    //   2	19	263	finally
    //   19	30	263	finally
    //   30	54	263	finally
    //   54	260	263	finally
    //   270	275	263	finally
    //   283	326	263	finally
    //   19	30	268	java/lang/Exception
  }

  public int e()
  {
    Log.i("cgi_report_debug", "ReportDataModal getTotalCount count = " + this.d);
    return this.d;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.cgireport.e
 * JD-Core Version:    0.6.0
 */