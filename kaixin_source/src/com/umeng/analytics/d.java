package com.umeng.analytics;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.umeng.common.b.g;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

public class d
{
  private ArrayList<JSONObject> a = new ArrayList();
  private ArrayList<JSONObject> b = new ArrayList();
  private HashMap<String, i> c = new HashMap();
  private HashMap<String, Map<String, String>> d = new HashMap();
  private int e = 10;

  // ERROR //
  private JSONObject a(JSONObject paramJSONObject, String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ifnonnull +11 -> 14
    //   6: new 40	org/json/JSONObject
    //   9: dup
    //   10: invokespecial 41	org/json/JSONObject:<init>	()V
    //   13: astore_1
    //   14: aload_0
    //   15: getfield 24	com/umeng/analytics/d:a	Ljava/util/ArrayList;
    //   18: invokevirtual 45	java/util/ArrayList:size	()I
    //   21: istore 4
    //   23: iload 4
    //   25: ifle +92 -> 117
    //   28: aload_1
    //   29: ldc 47
    //   31: invokevirtual 51	org/json/JSONObject:isNull	(Ljava/lang/String;)Z
    //   34: ifeq +153 -> 187
    //   37: new 53	org/json/JSONArray
    //   40: dup
    //   41: invokespecial 54	org/json/JSONArray:<init>	()V
    //   44: astore 19
    //   46: aload_1
    //   47: ldc 47
    //   49: aload 19
    //   51: invokevirtual 58	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   54: pop
    //   55: aload 19
    //   57: astore 21
    //   59: aload_0
    //   60: getfield 24	com/umeng/analytics/d:a	Ljava/util/ArrayList;
    //   63: invokevirtual 62	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   66: astore 22
    //   68: aload 22
    //   70: invokeinterface 68 1 0
    //   75: ifeq +123 -> 198
    //   78: aload 22
    //   80: invokeinterface 72 1 0
    //   85: checkcast 40	org/json/JSONObject
    //   88: astore 23
    //   90: aload 23
    //   92: ldc 74
    //   94: aload_2
    //   95: invokevirtual 58	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   98: pop
    //   99: aload 21
    //   101: aload 23
    //   103: invokevirtual 77	org/json/JSONArray:put	(Ljava/lang/Object;)Lorg/json/JSONArray;
    //   106: pop
    //   107: goto -39 -> 68
    //   110: astore 18
    //   112: aload 18
    //   114: invokevirtual 80	java/lang/Exception:printStackTrace	()V
    //   117: aload_0
    //   118: getfield 26	com/umeng/analytics/d:b	Ljava/util/ArrayList;
    //   121: invokevirtual 45	java/util/ArrayList:size	()I
    //   124: istore 5
    //   126: iload 5
    //   128: ifle +55 -> 183
    //   131: aload_1
    //   132: ldc 82
    //   134: invokevirtual 51	org/json/JSONObject:isNull	(Ljava/lang/String;)Z
    //   137: ifeq +76 -> 213
    //   140: new 53	org/json/JSONArray
    //   143: dup
    //   144: invokespecial 54	org/json/JSONArray:<init>	()V
    //   147: astore 7
    //   149: new 40	org/json/JSONObject
    //   152: dup
    //   153: invokespecial 41	org/json/JSONObject:<init>	()V
    //   156: astore 8
    //   158: aload_0
    //   159: aload 8
    //   161: aload_2
    //   162: invokespecial 85	com/umeng/analytics/d:b	(Lorg/json/JSONObject;Ljava/lang/String;)Z
    //   165: pop
    //   166: aload 7
    //   168: aload 8
    //   170: invokevirtual 77	org/json/JSONArray:put	(Ljava/lang/Object;)Lorg/json/JSONArray;
    //   173: pop
    //   174: aload_1
    //   175: ldc 82
    //   177: aload 7
    //   179: invokevirtual 58	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   182: pop
    //   183: aload_0
    //   184: monitorexit
    //   185: aload_1
    //   186: areturn
    //   187: aload_1
    //   188: ldc 47
    //   190: invokevirtual 89	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   193: astore 21
    //   195: goto -136 -> 59
    //   198: aload_0
    //   199: getfield 24	com/umeng/analytics/d:a	Ljava/util/ArrayList;
    //   202: invokevirtual 92	java/util/ArrayList:clear	()V
    //   205: goto -88 -> 117
    //   208: astore_3
    //   209: aload_0
    //   210: monitorexit
    //   211: aload_3
    //   212: athrow
    //   213: aload_1
    //   214: ldc 82
    //   216: invokevirtual 89	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   219: astore 12
    //   221: iconst_m1
    //   222: aload 12
    //   224: invokevirtual 95	org/json/JSONArray:length	()I
    //   227: iadd
    //   228: istore 13
    //   230: iload 13
    //   232: iflt +73 -> 305
    //   235: aload_0
    //   236: aload 12
    //   238: iload 13
    //   240: invokevirtual 99	org/json/JSONArray:get	(I)Ljava/lang/Object;
    //   243: checkcast 40	org/json/JSONObject
    //   246: aload_2
    //   247: invokespecial 85	com/umeng/analytics/d:b	(Lorg/json/JSONObject;Ljava/lang/String;)Z
    //   250: ifeq +49 -> 299
    //   253: iconst_1
    //   254: istore 14
    //   256: iload 14
    //   258: ifne -75 -> 183
    //   261: new 40	org/json/JSONObject
    //   264: dup
    //   265: invokespecial 41	org/json/JSONObject:<init>	()V
    //   268: astore 15
    //   270: aload_0
    //   271: aload 15
    //   273: aload_2
    //   274: invokespecial 85	com/umeng/analytics/d:b	(Lorg/json/JSONObject;Ljava/lang/String;)Z
    //   277: pop
    //   278: aload 12
    //   280: aload 15
    //   282: invokevirtual 77	org/json/JSONArray:put	(Ljava/lang/Object;)Lorg/json/JSONArray;
    //   285: pop
    //   286: goto -103 -> 183
    //   289: astore 6
    //   291: aload 6
    //   293: invokevirtual 80	java/lang/Exception:printStackTrace	()V
    //   296: goto -113 -> 183
    //   299: iinc 13 255
    //   302: goto -72 -> 230
    //   305: iconst_0
    //   306: istore 14
    //   308: goto -52 -> 256
    //
    // Exception table:
    //   from	to	target	type
    //   28	55	110	java/lang/Exception
    //   59	68	110	java/lang/Exception
    //   68	107	110	java/lang/Exception
    //   187	195	110	java/lang/Exception
    //   198	205	110	java/lang/Exception
    //   6	14	208	finally
    //   14	23	208	finally
    //   28	55	208	finally
    //   59	68	208	finally
    //   68	107	208	finally
    //   112	117	208	finally
    //   117	126	208	finally
    //   131	183	208	finally
    //   187	195	208	finally
    //   198	205	208	finally
    //   213	230	208	finally
    //   235	253	208	finally
    //   261	286	208	finally
    //   291	296	208	finally
    //   131	183	289	java/lang/Exception
    //   213	230	289	java/lang/Exception
    //   235	253	289	java/lang/Exception
    //   261	286	289	java/lang/Exception
  }

  private boolean b(JSONObject paramJSONObject, String paramString)
    throws Exception
  {
    JSONArray localJSONArray;
    if (paramJSONObject.has(paramString))
      localJSONArray = paramJSONObject.getJSONArray(paramString);
    for (int i = 1; ; i = 0)
    {
      Iterator localIterator = this.b.iterator();
      while (localIterator.hasNext())
        localJSONArray.put((JSONObject)localIterator.next());
      localJSONArray = new JSONArray();
    }
    this.b.clear();
    paramJSONObject.put(paramString, localJSONArray);
    return i;
  }

  public int a()
  {
    return this.a.size() + this.b.size();
  }

  public void a(int paramInt)
  {
    this.e = paramInt;
  }

  public void a(Context paramContext)
  {
    if (a() <= 0);
    String str;
    do
    {
      return;
      str = h.e(paramContext).getString("session_id", null);
    }
    while (str == null);
    h.a(paramContext, a(h.i(paramContext), str));
  }

  public void a(String paramString)
  {
    if (this.c.containsKey(paramString))
    {
      ((i)this.c.get(paramString)).a(Long.valueOf(System.currentTimeMillis()));
      return;
    }
    i locali = new i(paramString);
    locali.a(Long.valueOf(System.currentTimeMillis()));
    this.c.put(paramString, locali);
  }

  public void a(String paramString, Map<String, String> paramMap)
  {
    if (!this.d.containsKey(paramString))
      this.d.put(paramString, paramMap);
  }

  public boolean a(Context paramContext, String paramString1, String paramString2, long paramLong, int paramInt)
  {
    monitorenter;
    try
    {
      JSONObject localJSONObject = new JSONObject();
      String str1 = g.a();
      String str2 = str1.split(" ")[0];
      String str3 = str1.split(" ")[1];
      try
      {
        localJSONObject.put("tag", paramString1);
        localJSONObject.put("date", str2);
        localJSONObject.put("time", str3);
        localJSONObject.put("acc", paramInt);
        if (!TextUtils.isEmpty(paramString2))
          localJSONObject.put("label", paramString2);
        if (paramLong > 0L)
          localJSONObject.put("du", paramLong);
        this.a.add(localJSONObject);
        boolean bool = b();
        monitorexit;
        return bool;
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public boolean a(Context paramContext, String paramString, Map<String, String> paramMap, long paramLong)
  {
    monitorenter;
    try
    {
      JSONObject localJSONObject = new JSONObject();
      try
      {
        localJSONObject.put("id", paramString);
        localJSONObject.put("ts", System.currentTimeMillis());
        Iterator localIterator = paramMap.entrySet().iterator();
        int j;
        for (int i = 0; (localIterator.hasNext()) && (i < 10); i = j)
        {
          j = i + 1;
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          localJSONObject.put((String)localEntry.getKey(), (String)localEntry.getValue());
        }
        if (paramLong > 0L)
          localJSONObject.put("du", paramLong);
        this.b.add(localJSONObject);
        boolean bool = b();
        monitorexit;
        return bool;
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public long b(String paramString)
  {
    if (this.c.containsKey(paramString))
      return ((i)this.c.get(paramString)).a().longValue();
    return -1L;
  }

  public boolean b()
  {
    return this.a.size() + this.b.size() > this.e;
  }

  public Map<String, String> c(String paramString)
  {
    if ((this.c.containsKey(paramString)) && (((i)this.c.get(paramString)).b() > 0))
      return (Map)this.d.get(paramString);
    return (Map)this.d.remove(paramString);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.umeng.analytics.d
 * JD-Core Version:    0.6.0
 */