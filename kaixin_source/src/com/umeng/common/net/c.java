package com.umeng.common.net;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.umeng.common.Log;
import com.umeng.common.b.g;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class c
{
  private static final String a = c.class.getName();
  private static final String b = "umeng_download_task_list";
  private static final String c = "UMENG_DATA";
  private static final String d = "cp";
  private static final String e = "url";
  private static final String f = "progress";
  private static final String g = "last_modified";
  private static final String h = "extra";
  private static Context i;
  private static final String j = "yyyy-MM-dd HH:mm:ss";
  private a k = new a(i);

  public static c a(Context paramContext)
  {
    if ((i == null) && (paramContext == null))
      throw new NullPointerException();
    if (i == null)
      i = paramContext;
    return b.a;
  }

  public List<String> a(String paramString)
  {
    String[] arrayOfString = { paramString };
    Cursor localCursor = this.k.getReadableDatabase().query("umeng_download_task_list", new String[] { "url" }, "cp=?", arrayOfString, null, null, null, "1");
    ArrayList localArrayList = new ArrayList();
    localCursor.moveToFirst();
    while (!localCursor.isAfterLast())
    {
      localArrayList.add(localCursor.getString(0));
      localCursor.moveToNext();
    }
    localCursor.close();
    return localArrayList;
  }

  public void a(int paramInt)
  {
    try
    {
      Date localDate = new Date(new Date().getTime() - paramInt * 1000);
      String str1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(localDate);
      String str2 = " DELETE FROM umeng_download_task_list WHERE strftime('yyyy-MM-dd HH:mm:ss', last_modified)<=strftime('yyyy-MM-dd HH:mm:ss', '" + str1 + "')";
      this.k.getWritableDatabase().execSQL(str2);
      Log.c(a, "clearOverdueTasks(" + paramInt + ")" + " remove all tasks before " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(localDate));
      return;
    }
    catch (Exception localException)
    {
      Log.b(a, localException.getMessage());
    }
  }

  public void a(String paramString1, String paramString2, int paramInt)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("progress", Integer.valueOf(paramInt));
    localContentValues.put("last_modified", g.a());
    String[] arrayOfString = { paramString1, paramString2 };
    this.k.getWritableDatabase().update("umeng_download_task_list", localContentValues, "cp=? and url=?", arrayOfString);
    Log.c(a, "updateProgress(" + paramString1 + ", " + paramString2 + ", " + paramInt + ")");
  }

  public void a(String paramString1, String paramString2, String paramString3)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("extra", paramString3);
    localContentValues.put("last_modified", g.a());
    String[] arrayOfString = { paramString1, paramString2 };
    this.k.getWritableDatabase().update("umeng_download_task_list", localContentValues, "cp=? and url=?", arrayOfString);
    Log.c(a, "updateExtra(" + paramString1 + ", " + paramString2 + ", " + paramString3 + ")");
  }

  // ERROR //
  public boolean a(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: new 176	android/content/ContentValues
    //   3: dup
    //   4: invokespecial 177	android/content/ContentValues:<init>	()V
    //   7: astore_3
    //   8: aload_3
    //   9: ldc 15
    //   11: aload_1
    //   12: invokevirtual 193	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   15: aload_3
    //   16: ldc 18
    //   18: aload_2
    //   19: invokevirtual 193	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   22: aload_3
    //   23: ldc 21
    //   25: iconst_0
    //   26: invokestatic 183	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   29: invokevirtual 187	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   32: aload_3
    //   33: ldc 24
    //   35: invokestatic 191	com/umeng/common/b/g:a	()Ljava/lang/String;
    //   38: invokevirtual 193	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   41: iconst_2
    //   42: anewarray 70	java/lang/String
    //   45: dup
    //   46: iconst_0
    //   47: aload_1
    //   48: aastore
    //   49: dup
    //   50: iconst_1
    //   51: aload_2
    //   52: aastore
    //   53: astore 4
    //   55: aload_0
    //   56: getfield 56	com/umeng/common/net/c:k	Lcom/umeng/common/net/c$a;
    //   59: invokevirtual 74	com/umeng/common/net/c$a:getReadableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   62: ldc 9
    //   64: iconst_1
    //   65: anewarray 70	java/lang/String
    //   68: dup
    //   69: iconst_0
    //   70: ldc 21
    //   72: aastore
    //   73: ldc 195
    //   75: aload 4
    //   77: aconst_null
    //   78: aconst_null
    //   79: aconst_null
    //   80: ldc 78
    //   82: invokevirtual 84	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   85: astore 8
    //   87: aload 8
    //   89: invokeinterface 211 1 0
    //   94: ifle +60 -> 154
    //   97: getstatic 44	com/umeng/common/net/c:a	Ljava/lang/String;
    //   100: new 136	java/lang/StringBuilder
    //   103: dup
    //   104: invokespecial 137	java/lang/StringBuilder:<init>	()V
    //   107: ldc 213
    //   109: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   112: aload_1
    //   113: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   116: ldc 203
    //   118: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   121: aload_2
    //   122: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   125: ldc 215
    //   127: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   130: ldc 217
    //   132: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   135: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   138: invokestatic 168	com/umeng/common/Log:c	(Ljava/lang/String;Ljava/lang/String;)V
    //   141: iconst_0
    //   142: istore 7
    //   144: aload 8
    //   146: invokeinterface 112 1 0
    //   151: iload 7
    //   153: ireturn
    //   154: aload_0
    //   155: getfield 56	com/umeng/common/net/c:k	Lcom/umeng/common/net/c$a;
    //   158: invokevirtual 151	com/umeng/common/net/c$a:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   161: ldc 9
    //   163: aconst_null
    //   164: aload_3
    //   165: invokevirtual 221	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   168: lstore 9
    //   170: lload 9
    //   172: ldc2_w 222
    //   175: lcmp
    //   176: ifne +62 -> 238
    //   179: iconst_0
    //   180: istore 11
    //   182: getstatic 44	com/umeng/common/net/c:a	Ljava/lang/String;
    //   185: new 136	java/lang/StringBuilder
    //   188: dup
    //   189: invokespecial 137	java/lang/StringBuilder:<init>	()V
    //   192: ldc 213
    //   194: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   197: aload_1
    //   198: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   201: ldc 203
    //   203: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   206: aload_2
    //   207: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   210: ldc 215
    //   212: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   215: ldc 225
    //   217: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   220: lload 9
    //   222: invokevirtual 228	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   225: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   228: invokestatic 168	com/umeng/common/Log:c	(Ljava/lang/String;Ljava/lang/String;)V
    //   231: iload 11
    //   233: istore 7
    //   235: goto -91 -> 144
    //   238: iconst_1
    //   239: istore 11
    //   241: goto -59 -> 182
    //   244: astore 5
    //   246: aload 5
    //   248: astore 6
    //   250: iconst_0
    //   251: istore 7
    //   253: getstatic 44	com/umeng/common/net/c:a	Ljava/lang/String;
    //   256: new 136	java/lang/StringBuilder
    //   259: dup
    //   260: invokespecial 137	java/lang/StringBuilder:<init>	()V
    //   263: ldc 213
    //   265: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   268: aload_1
    //   269: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   272: ldc 203
    //   274: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   277: aload_2
    //   278: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   281: ldc 215
    //   283: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   286: aload 6
    //   288: invokevirtual 171	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   291: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   294: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   297: aload 6
    //   299: invokestatic 231	com/umeng/common/Log:c	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Exception;)V
    //   302: iload 7
    //   304: ireturn
    //   305: astore 12
    //   307: iload 11
    //   309: istore 7
    //   311: aload 12
    //   313: astore 6
    //   315: goto -62 -> 253
    //   318: astore 6
    //   320: goto -67 -> 253
    //
    // Exception table:
    //   from	to	target	type
    //   41	141	244	java/lang/Exception
    //   154	170	244	java/lang/Exception
    //   182	231	305	java/lang/Exception
    //   144	151	318	java/lang/Exception
  }

  public int b(String paramString1, String paramString2)
  {
    String[] arrayOfString = { paramString1, paramString2 };
    Cursor localCursor = this.k.getReadableDatabase().query("umeng_download_task_list", new String[] { "progress" }, "cp=? and url=?", arrayOfString, null, null, null, "1");
    if (localCursor.getCount() > 0)
      localCursor.moveToFirst();
    for (int m = localCursor.getInt(0); ; m = -1)
    {
      localCursor.close();
      return m;
    }
  }

  public String c(String paramString1, String paramString2)
  {
    String[] arrayOfString = { paramString1, paramString2 };
    Cursor localCursor = this.k.getReadableDatabase().query("umeng_download_task_list", new String[] { "extra" }, "cp=? and url=?", arrayOfString, null, null, null, "1");
    int m = localCursor.getCount();
    String str = null;
    if (m > 0)
    {
      localCursor.moveToFirst();
      str = localCursor.getString(0);
    }
    localCursor.close();
    return str;
  }

  public Date d(String paramString1, String paramString2)
  {
    String[] arrayOfString = { paramString1, paramString2 };
    Cursor localCursor = this.k.getReadableDatabase().query("umeng_download_task_list", new String[] { "last_modified" }, "cp=? and url=?", arrayOfString, null, null, null, null);
    int m = localCursor.getCount();
    Object localObject = null;
    String str;
    if (m > 0)
    {
      localCursor.moveToFirst();
      str = localCursor.getString(0);
      Log.c(a, "getLastModified(" + paramString1 + ", " + paramString2 + "): " + str);
    }
    try
    {
      Date localDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
      localObject = localDate;
      localCursor.close();
      return localObject;
    }
    catch (Exception localException)
    {
      while (true)
      {
        Log.c(a, localException.getMessage());
        localObject = null;
      }
    }
  }

  public void e(String paramString1, String paramString2)
  {
    String[] arrayOfString = { paramString1, paramString2 };
    this.k.getWritableDatabase().delete("umeng_download_task_list", "cp=? and url=?", arrayOfString);
    Log.c(a, "delete(" + paramString1 + ", " + paramString2 + ")");
  }

  public void finalize()
  {
    this.k.close();
  }

  class a extends SQLiteOpenHelper
  {
    private static final int b = 2;
    private static final String c = "CREATE TABLE umeng_download_task_list (cp TEXT, url TEXT, progress INTEGER, extra TEXT, last_modified TEXT, UNIQUE (cp,url) ON CONFLICT ABORT);";

    a(Context arg2)
    {
      super("UMENG_DATA", null, 2);
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
      Log.c(c.a(), "CREATE TABLE umeng_download_task_list (cp TEXT, url TEXT, progress INTEGER, extra TEXT, last_modified TEXT, UNIQUE (cp,url) ON CONFLICT ABORT);");
      paramSQLiteDatabase.execSQL("CREATE TABLE umeng_download_task_list (cp TEXT, url TEXT, progress INTEGER, extra TEXT, last_modified TEXT, UNIQUE (cp,url) ON CONFLICT ABORT);");
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
    }
  }

  private static class b
  {
    public static final c a = new c(null);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.umeng.common.net.c
 * JD-Core Version:    0.6.0
 */