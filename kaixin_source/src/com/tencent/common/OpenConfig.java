package com.tencent.common;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class OpenConfig
{
  private static HashMap<String, OpenConfig> a = null;
  private static String b = null;
  private Context c = null;
  private String d = null;
  private JSONObject e = null;
  private long f = 0L;
  private boolean g = true;

  private OpenConfig(Context paramContext, String paramString)
  {
    this.c = paramContext;
    this.d = paramString;
    a();
  }

  public static OpenConfig a(Context paramContext, String paramString)
  {
    if (a == null)
      a = new HashMap();
    if (paramString != null)
      b = paramString;
    if (paramString == null)
      if (b == null)
        break label74;
    label74: for (paramString = b; ; paramString = "0")
    {
      OpenConfig localOpenConfig = (OpenConfig)a.get(paramString);
      if (localOpenConfig == null)
      {
        localOpenConfig = new OpenConfig(paramContext, paramString);
        a.put(paramString, localOpenConfig);
      }
      return localOpenConfig;
    }
  }

  private void a()
  {
    String str = e("com.tencent.open.config.json");
    try
    {
      this.e = new JSONObject(str);
      return;
    }
    catch (JSONException localJSONException)
    {
      this.e = new JSONObject();
    }
  }

  private void b()
  {
    int i = this.e.optInt("Common_frequency");
    if (i == 0)
      i = 1;
    long l = i * 3600000;
    if (SystemClock.elapsedRealtime() - this.f >= l);
  }

  // ERROR //
  private String e(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 32	com/tencent/common/OpenConfig:d	Ljava/lang/String;
    //   4: ifnull +116 -> 120
    //   7: new 88	java/lang/StringBuilder
    //   10: dup
    //   11: invokespecial 89	java/lang/StringBuilder:<init>	()V
    //   14: aload_1
    //   15: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   18: ldc 95
    //   20: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   23: aload_0
    //   24: getfield 32	com/tencent/common/OpenConfig:d	Ljava/lang/String;
    //   27: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   30: invokevirtual 99	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   33: astore 16
    //   35: aload_0
    //   36: getfield 30	com/tencent/common/OpenConfig:c	Landroid/content/Context;
    //   39: aload 16
    //   41: invokevirtual 105	android/content/Context:openFileInput	(Ljava/lang/String;)Ljava/io/FileInputStream;
    //   44: astore 17
    //   46: aload 17
    //   48: astore 5
    //   50: new 107	java/io/BufferedReader
    //   53: dup
    //   54: new 109	java/io/InputStreamReader
    //   57: dup
    //   58: aload 5
    //   60: invokespecial 112	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   63: invokespecial 115	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   66: astore 6
    //   68: new 117	java/lang/StringBuffer
    //   71: dup
    //   72: invokespecial 118	java/lang/StringBuffer:<init>	()V
    //   75: astore 7
    //   77: aload 6
    //   79: invokevirtual 121	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   82: astore 12
    //   84: aload 12
    //   86: ifnull +69 -> 155
    //   89: aload 7
    //   91: aload 12
    //   93: invokevirtual 124	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   96: pop
    //   97: goto -20 -> 77
    //   100: astore 10
    //   102: aload 10
    //   104: invokevirtual 127	java/io/IOException:printStackTrace	()V
    //   107: aload 5
    //   109: invokevirtual 132	java/io/InputStream:close	()V
    //   112: aload 6
    //   114: invokevirtual 133	java/io/BufferedReader:close	()V
    //   117: ldc 135
    //   119: areturn
    //   120: aload_1
    //   121: astore 16
    //   123: goto -88 -> 35
    //   126: astore_2
    //   127: aload_0
    //   128: getfield 30	com/tencent/common/OpenConfig:c	Landroid/content/Context;
    //   131: invokevirtual 139	android/content/Context:getAssets	()Landroid/content/res/AssetManager;
    //   134: aload_1
    //   135: invokevirtual 145	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
    //   138: astore 4
    //   140: aload 4
    //   142: astore 5
    //   144: goto -94 -> 50
    //   147: astore_3
    //   148: aload_3
    //   149: invokevirtual 127	java/io/IOException:printStackTrace	()V
    //   152: ldc 135
    //   154: areturn
    //   155: aload 7
    //   157: invokevirtual 146	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   160: astore 14
    //   162: aload 5
    //   164: invokevirtual 132	java/io/InputStream:close	()V
    //   167: aload 6
    //   169: invokevirtual 133	java/io/BufferedReader:close	()V
    //   172: aload 14
    //   174: areturn
    //   175: astore 15
    //   177: aload 15
    //   179: invokevirtual 127	java/io/IOException:printStackTrace	()V
    //   182: aload 14
    //   184: areturn
    //   185: astore 11
    //   187: aload 11
    //   189: invokevirtual 127	java/io/IOException:printStackTrace	()V
    //   192: ldc 135
    //   194: areturn
    //   195: astore 8
    //   197: aload 5
    //   199: invokevirtual 132	java/io/InputStream:close	()V
    //   202: aload 6
    //   204: invokevirtual 133	java/io/BufferedReader:close	()V
    //   207: aload 8
    //   209: athrow
    //   210: astore 9
    //   212: aload 9
    //   214: invokevirtual 127	java/io/IOException:printStackTrace	()V
    //   217: goto -10 -> 207
    //
    // Exception table:
    //   from	to	target	type
    //   77	84	100	java/io/IOException
    //   89	97	100	java/io/IOException
    //   155	162	100	java/io/IOException
    //   0	35	126	java/io/FileNotFoundException
    //   35	46	126	java/io/FileNotFoundException
    //   127	140	147	java/io/IOException
    //   162	172	175	java/io/IOException
    //   107	117	185	java/io/IOException
    //   77	84	195	finally
    //   89	97	195	finally
    //   102	107	195	finally
    //   155	162	195	finally
    //   197	207	210	java/io/IOException
  }

  private void f(String paramString)
  {
    if (this.g)
      Log.i("OpenConfig", paramString + "; appid: " + this.d);
  }

  public String a(String paramString)
  {
    f("get " + paramString);
    b();
    return this.e.optString(paramString);
  }

  public int b(String paramString)
  {
    f("get " + paramString);
    b();
    return this.e.optInt(paramString);
  }

  public long c(String paramString)
  {
    f("get " + paramString);
    b();
    return this.e.optLong(paramString);
  }

  public boolean d(String paramString)
  {
    f("get " + paramString);
    b();
    Object localObject = this.e.opt(paramString);
    if (localObject == null);
    do
    {
      return false;
      if (!(localObject instanceof Integer))
        continue;
      if (!localObject.equals(Integer.valueOf(0)));
      for (int i = 1; ; i = 0)
        return i;
    }
    while (!(localObject instanceof Boolean));
    return ((Boolean)localObject).booleanValue();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.common.OpenConfig
 * JD-Core Version:    0.6.0
 */