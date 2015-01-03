package com.amap.mapapi.offlinemap;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import com.amap.mapapi.core.b;
import com.amap.mapapi.core.u;
import java.io.File;
import java.util.ArrayList;

public class c
{
  private static String g = null;
  public ArrayList<g> a = new ArrayList();
  public ArrayList<g> b = new ArrayList();
  public ArrayList<i> c = new ArrayList();
  public ArrayList<City> d = new ArrayList();
  Handler e;
  e f;
  private Context h = null;

  public c(Context paramContext, Handler paramHandler)
  {
    this.h = paramContext;
    this.e = paramHandler;
    g = c();
    b.b(this.h);
  }

  public static String a()
  {
    return Environment.getExternalStorageDirectory() + "/amap/VMAP/";
  }

  private String c()
  {
    if (!Environment.getExternalStorageState().equals("mounted"))
      return this.h.getCacheDir().toString() + "/";
    File localFile1 = new File(Environment.getExternalStorageDirectory(), "amap");
    if (!localFile1.exists())
      localFile1.mkdir();
    File localFile2 = new File(localFile1, "mini_mapv2");
    if (!localFile2.exists())
      localFile2.mkdir();
    File localFile3 = new File(localFile2, "vmap");
    if (!localFile3.exists())
      localFile3.mkdir();
    return localFile3.toString() + "/";
  }

  public void a(int paramInt)
  {
    g localg;
    String str3;
    if ((this.b != null) && (this.b.size() > 0))
    {
      localg = (g)this.b.get(paramInt);
      String str1 = localg.b();
      String str2 = str1 + ".zip";
      str3 = str2 + ".tmp";
    }
    try
    {
      this.f = new e(new f(localg.c(), a(), str3, 5), this, localg, this.h);
      this.f.start();
      a(localg, 0, 0);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public void a(g paramg)
  {
    paramg.a = 3;
    String str1;
    String str2;
    synchronized (this.b)
    {
      this.b.remove(paramg);
      synchronized (this.a)
      {
        this.a.remove(paramg);
        b(paramg.d());
        str1 = paramg.a();
        str2 = str1 + ".dt";
        if ((str1 == null) || (str1.length() <= 0));
      }
    }
    try
    {
      new File(str1).delete();
      if ((str2 == null) || (str2.length() <= 0));
    }
    catch (Exception localException2)
    {
      try
      {
        new File(str2).delete();
        str3 = str1 + ".info";
        if ((str3 == null) || (str3.length() <= 0));
      }
      catch (Exception localException2)
      {
        try
        {
          while (true)
          {
            String str3;
            new File(str3).delete();
            return;
            localObject1 = finally;
            monitorexit;
            throw localObject1;
            localObject2 = finally;
            monitorexit;
            throw localObject2;
            localException3 = localException3;
            localException3.printStackTrace();
          }
          localException2 = localException2;
          localException2.printStackTrace();
        }
        catch (Exception localException1)
        {
          localException1.printStackTrace();
        }
      }
    }
  }

  public void a(g paramg, int paramInt1, int paramInt2)
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("status", paramInt1);
    localBundle.putInt("completepercent", paramInt2);
    Message localMessage = this.e.obtainMessage();
    localMessage.setData(localBundle);
    this.e.sendMessage(localMessage);
    paramg.a = paramInt1;
    paramg.e();
  }

  // ERROR //
  public void a(org.json.JSONObject paramJSONObject)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 37	com/amap/mapapi/offlinemap/c:c	Ljava/util/ArrayList;
    //   4: invokevirtual 210	java/util/ArrayList:clear	()V
    //   7: aload_1
    //   8: ifnull +289 -> 297
    //   11: aload_1
    //   12: ldc 212
    //   14: invokevirtual 218	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   17: astore_3
    //   18: aload_3
    //   19: ifnonnull +4 -> 23
    //   22: return
    //   23: aload_3
    //   24: invokevirtual 221	org/json/JSONArray:length	()I
    //   27: istore 4
    //   29: iconst_0
    //   30: istore 5
    //   32: iload 5
    //   34: iload 4
    //   36: if_icmpge +231 -> 267
    //   39: aload_3
    //   40: iload 5
    //   42: invokevirtual 225	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   45: astore 6
    //   47: ldc 227
    //   49: astore 7
    //   51: ldc 227
    //   53: astore 8
    //   55: ldc 227
    //   57: astore 9
    //   59: aload 6
    //   61: ldc 229
    //   63: invokevirtual 233	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   66: astore 32
    //   68: aload 32
    //   70: astore 7
    //   72: aload 6
    //   74: ldc 235
    //   76: invokevirtual 233	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   79: astore 31
    //   81: aload 31
    //   83: astore 8
    //   85: aload 6
    //   87: ldc 237
    //   89: invokevirtual 233	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   92: astore 30
    //   94: aload 30
    //   96: astore 13
    //   98: aload 6
    //   100: ldc 238
    //   102: invokevirtual 242	org/json/JSONObject:getLong	(Ljava/lang/String;)J
    //   105: lstore 28
    //   107: lload 28
    //   109: lstore 15
    //   111: aload 6
    //   113: ldc 244
    //   115: invokevirtual 233	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   118: astore 27
    //   120: aload 27
    //   122: astore 9
    //   124: aload 6
    //   126: ldc 246
    //   128: invokevirtual 233	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   131: astore 26
    //   133: aload 26
    //   135: astore 19
    //   137: aload 6
    //   139: ldc 248
    //   141: invokevirtual 233	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   144: astore 25
    //   146: aload 25
    //   148: astore 21
    //   150: aload 8
    //   152: invokevirtual 169	java/lang/String:length	()I
    //   155: ifle +143 -> 298
    //   158: aload 7
    //   160: invokevirtual 169	java/lang/String:length	()I
    //   163: ifle +135 -> 298
    //   166: aload 13
    //   168: invokevirtual 169	java/lang/String:length	()I
    //   171: ifle +127 -> 298
    //   174: aload 9
    //   176: invokevirtual 169	java/lang/String:length	()I
    //   179: ifle +119 -> 298
    //   182: lload 15
    //   184: lconst_0
    //   185: lcmp
    //   186: ifgt +6 -> 192
    //   189: goto +109 -> 298
    //   192: new 250	com/amap/mapapi/offlinemap/i
    //   195: dup
    //   196: ldc 227
    //   198: aload 7
    //   200: aload 8
    //   202: aload 9
    //   204: iconst_0
    //   205: iconst_1
    //   206: invokevirtual 254	java/lang/String:substring	(II)Ljava/lang/String;
    //   209: aload 9
    //   211: invokespecial 257	com/amap/mapapi/offlinemap/i:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   214: astore 22
    //   216: aload 22
    //   218: aload 13
    //   220: putfield 259	com/amap/mapapi/offlinemap/i:a	Ljava/lang/String;
    //   223: aload 22
    //   225: lload 15
    //   227: putfield 262	com/amap/mapapi/offlinemap/i:b	J
    //   230: aload 22
    //   232: aload 19
    //   234: putfield 264	com/amap/mapapi/offlinemap/i:d	Ljava/lang/String;
    //   237: aload 22
    //   239: aload 21
    //   241: putfield 266	com/amap/mapapi/offlinemap/i:e	Ljava/lang/String;
    //   244: aload_0
    //   245: getfield 39	com/amap/mapapi/offlinemap/c:d	Ljava/util/ArrayList;
    //   248: aload 22
    //   250: invokevirtual 269	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   253: pop
    //   254: aload_0
    //   255: getfield 37	com/amap/mapapi/offlinemap/c:c	Ljava/util/ArrayList;
    //   258: aload 22
    //   260: invokevirtual 269	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   263: pop
    //   264: goto +34 -> 298
    //   267: aload_0
    //   268: getfield 37	com/amap/mapapi/offlinemap/c:c	Ljava/util/ArrayList;
    //   271: invokestatic 275	java/util/Collections:sort	(Ljava/util/List;)V
    //   274: aload_0
    //   275: getfield 39	com/amap/mapapi/offlinemap/c:d	Ljava/util/ArrayList;
    //   278: invokestatic 275	java/util/Collections:sort	(Ljava/util/List;)V
    //   281: return
    //   282: astore 17
    //   284: goto -160 -> 124
    //   287: astore 11
    //   289: goto -204 -> 85
    //   292: astore 10
    //   294: goto -222 -> 72
    //   297: return
    //   298: iinc 5 1
    //   301: goto -269 -> 32
    //   304: astore 12
    //   306: ldc 227
    //   308: astore 13
    //   310: goto -212 -> 98
    //   313: astore 14
    //   315: lconst_0
    //   316: lstore 15
    //   318: goto -207 -> 111
    //   321: astore 18
    //   323: ldc 227
    //   325: astore 19
    //   327: goto -190 -> 137
    //   330: astore 20
    //   332: ldc 227
    //   334: astore 21
    //   336: goto -186 -> 150
    //   339: astore_2
    //   340: return
    //
    // Exception table:
    //   from	to	target	type
    //   111	120	282	java/lang/Exception
    //   72	81	287	java/lang/Exception
    //   59	68	292	java/lang/Exception
    //   85	94	304	java/lang/Exception
    //   98	107	313	java/lang/Exception
    //   124	133	321	java/lang/Exception
    //   137	146	330	java/lang/Exception
    //   11	18	339	java/lang/Exception
    //   23	29	339	java/lang/Exception
    //   39	47	339	java/lang/Exception
    //   150	182	339	java/lang/Exception
    //   192	264	339	java/lang/Exception
    //   267	281	339	java/lang/Exception
  }

  public void b()
  {
    if (this.f != null)
    {
      this.f.b();
      Bundle localBundle = new Bundle();
      localBundle.putInt("status", 5);
      localBundle.putInt("completepercent", 100);
      Message localMessage = this.e.obtainMessage();
      localMessage.setData(localBundle);
      this.e.sendMessage(localMessage);
      this.b.clear();
      if (this.f != null)
        this.f.b();
    }
  }

  public void b(int paramInt)
  {
    if (this.f != null)
    {
      this.f.b();
      Bundle localBundle = new Bundle();
      localBundle.putInt("status", 3);
      localBundle.putInt("completepercent", 100);
      Message localMessage = this.e.obtainMessage();
      localMessage.setData(localBundle);
      this.e.sendMessage(localMessage);
    }
  }

  public void b(g paramg)
  {
    String str1 = paramg.b();
    String str2 = a() + str1 + ".zip";
    String str3 = str2 + ".tmp";
    paramg.e();
    File localFile = new File(str2);
    new File(str3).renameTo(localFile);
    Bundle localBundle1 = new Bundle();
    localBundle1.putInt("status", 1);
    localBundle1.putInt("completepercent", 100);
    Message localMessage1 = this.e.obtainMessage();
    localMessage1.setData(localBundle1);
    this.e.sendMessage(localMessage1);
    paramg.a = 1;
    paramg.e();
    if (localFile.exists())
    {
      u.a(g, str2);
      new File(str2).delete();
      Bundle localBundle2 = new Bundle();
      localBundle2.putInt("status", 4);
      localBundle2.putInt("completepercent", 100);
      Message localMessage2 = this.e.obtainMessage();
      localMessage1.setData(localBundle2);
      this.e.sendMessage(localMessage2);
    }
    paramg.a = 4;
    synchronized (this.b)
    {
      this.b.remove(paramg);
      paramg.e();
      return;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.offlinemap.c
 * JD-Core Version:    0.6.0
 */