package com.umeng.common.net;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.umeng.common.Log;
import com.umeng.common.a.a;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class DownloadingService extends Service
{
  static final int a = 3;
  static final int b = 4;
  static final int c = 5;
  static final int d = 0;
  static final int e = 1;
  static final int f = 100;
  static final String g = "filename";
  public static boolean h = false;
  private static final String j = DownloadingService.class.getName();
  private static final int l = 3;
  private static final long p = 8000L;
  private static Map<a.a, Messenger> q;
  private static Map<Integer, d> r;
  private static Boolean t;
  final Messenger i = new Messenger(new c());
  private NotificationManager k;
  private Context m;
  private Handler n;
  private a o;
  private BroadcastReceiver s;

  static
  {
    h = false;
    q = new HashMap();
    r = new HashMap();
    t = Boolean.valueOf(false);
  }

  public static int a(a.a parama)
  {
    return Math.abs((int)((parama.b.hashCode() >> 2) + (parama.c.hashCode() >> 3) + System.currentTimeMillis()));
  }

  private Notification a(a.a parama, int paramInt1, int paramInt2)
  {
    Context localContext = getApplicationContext();
    Notification localNotification = new Notification(17301633, "正在下载应用", 1L);
    RemoteViews localRemoteViews = new RemoteViews(localContext.getPackageName(), com.umeng.common.a.b.a(localContext));
    localRemoteViews.setProgressBar(a.c(localContext), 100, paramInt2, false);
    localRemoteViews.setTextViewText(a.b(localContext), paramInt2 + "%");
    localRemoteViews.setTextViewText(a.d(localContext), localContext.getResources().getString(com.umeng.common.a.c.g(localContext.getApplicationContext())) + parama.b);
    localRemoteViews.setTextViewText(a.a(localContext), "");
    localRemoteViews.setImageViewResource(a.e(localContext), 17301633);
    localNotification.contentView = localRemoteViews;
    localNotification.contentIntent = PendingIntent.getActivity(localContext, 0, new Intent(), 134217728);
    if (parama.e)
    {
      localNotification.flags = 2;
      PendingIntent localPendingIntent1 = l.b(getApplicationContext(), l.a(paramInt1, "continue"));
      localRemoteViews.setOnClickPendingIntent(a.f(localContext), localPendingIntent1);
      localRemoteViews.setViewVisibility(a.f(localContext), 0);
      b(localNotification, paramInt1);
      PendingIntent localPendingIntent2 = l.b(getApplicationContext(), l.a(paramInt1, "cancel"));
      localRemoteViews.setViewVisibility(a.h(localContext), 0);
      localRemoteViews.setOnClickPendingIntent(a.h(localContext), localPendingIntent2);
      return localNotification;
    }
    localNotification.flags = 16;
    localRemoteViews.setViewVisibility(a.f(localContext), 8);
    localRemoteViews.setViewVisibility(a.h(localContext), 8);
    return localNotification;
  }

  private void a(int paramInt)
  {
    d locald = (d)r.get(Integer.valueOf(paramInt));
    Log.c(j, "download service clear cache " + locald.e.b);
    if (locald.a != null)
      locald.a.a(2);
    this.k.cancel(locald.c);
    if (q.containsKey(locald.e))
      q.remove(locald.e);
    locald.b();
    e();
  }

  private void a(Notification paramNotification, int paramInt)
  {
    int i1 = a.f(this.m);
    paramNotification.contentView.setTextViewText(i1, this.m.getResources().getString(com.umeng.common.a.c.e(this.m.getApplicationContext())));
    paramNotification.contentView.setInt(i1, "setBackgroundResource", com.umeng.common.c.a(this.m).c("umeng_common_gradient_green"));
    this.k.notify(paramInt, paramNotification);
  }

  private void a(a.a parama, long paramLong1, long paramLong2, long paramLong3)
  {
    if (parama.d != null)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("dsize", String.valueOf(paramLong1));
      localHashMap.put("dtime", com.umeng.common.b.g.a().split(" ")[1]);
      boolean bool = paramLong2 < 0L;
      float f1 = 0.0F;
      if (bool)
        f1 = (float)paramLong1 / (float)paramLong2;
      localHashMap.put("dpcent", String.valueOf((int)(f1 * 100.0F)));
      localHashMap.put("ptimes", String.valueOf(paramLong3));
      b(localHashMap, false, parama.d);
    }
  }

  // ERROR //
  private boolean a(Context paramContext, Intent paramIntent)
  {
    // Byte code:
    //   0: aload_2
    //   1: invokevirtual 380	android/content/Intent:getExtras	()Landroid/os/Bundle;
    //   4: ldc_w 382
    //   7: invokevirtual 387	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   10: ldc_w 389
    //   13: invokevirtual 360	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   16: astore 4
    //   18: aload 4
    //   20: iconst_0
    //   21: aaload
    //   22: invokestatic 392	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   25: istore 5
    //   27: aload 4
    //   29: iconst_1
    //   30: aaload
    //   31: invokevirtual 395	java/lang/String:trim	()Ljava/lang/String;
    //   34: astore 6
    //   36: iload 5
    //   38: ifeq +302 -> 340
    //   41: aload 6
    //   43: invokestatic 401	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   46: ifne +294 -> 340
    //   49: getstatic 69	com/umeng/common/net/DownloadingService:r	Ljava/util/Map;
    //   52: iload 5
    //   54: invokestatic 263	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   57: invokeinterface 298 2 0
    //   62: ifeq +278 -> 340
    //   65: getstatic 69	com/umeng/common/net/DownloadingService:r	Ljava/util/Map;
    //   68: iload 5
    //   70: invokestatic 263	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   73: invokeinterface 269 2 0
    //   78: checkcast 271	com/umeng/common/net/DownloadingService$d
    //   81: astore 7
    //   83: aload 7
    //   85: getfield 284	com/umeng/common/net/DownloadingService$d:a	Lcom/umeng/common/net/DownloadingService$b;
    //   88: astore 8
    //   90: ldc 226
    //   92: aload 6
    //   94: invokevirtual 404	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   97: ifeq +144 -> 241
    //   100: aload 8
    //   102: ifnonnull +105 -> 207
    //   105: getstatic 58	com/umeng/common/net/DownloadingService:j	Ljava/lang/String;
    //   108: ldc_w 406
    //   111: invokestatic 281	com/umeng/common/Log:c	(Ljava/lang/String;Ljava/lang/String;)V
    //   114: aload_1
    //   115: ldc_w 408
    //   118: invokestatic 413	com/umeng/common/b:a	(Landroid/content/Context;Ljava/lang/String;)Z
    //   121: ifeq +34 -> 155
    //   124: aload_1
    //   125: invokestatic 415	com/umeng/common/b:m	(Landroid/content/Context;)Z
    //   128: ifne +27 -> 155
    //   131: aload_1
    //   132: aload_1
    //   133: invokevirtual 181	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   136: aload_1
    //   137: invokevirtual 182	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   140: invokestatic 416	com/umeng/common/a/c:a	(Landroid/content/Context;)I
    //   143: invokevirtual 192	android/content/res/Resources:getString	(I)Ljava/lang/String;
    //   146: iconst_1
    //   147: invokestatic 422	android/widget/Toast:makeText	(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;
    //   150: invokevirtual 425	android/widget/Toast:show	()V
    //   153: iconst_0
    //   154: ireturn
    //   155: new 286	com/umeng/common/net/DownloadingService$b
    //   158: dup
    //   159: aload_0
    //   160: aload_1
    //   161: aload 7
    //   163: getfield 276	com/umeng/common/net/DownloadingService$d:e	Lcom/umeng/common/net/a$a;
    //   166: iload 5
    //   168: aload 7
    //   170: getfield 427	com/umeng/common/net/DownloadingService$d:d	I
    //   173: aload_0
    //   174: getfield 429	com/umeng/common/net/DownloadingService:o	Lcom/umeng/common/net/DownloadingService$a;
    //   177: invokespecial 432	com/umeng/common/net/DownloadingService$b:<init>	(Lcom/umeng/common/net/DownloadingService;Landroid/content/Context;Lcom/umeng/common/net/a$a;IILcom/umeng/common/net/DownloadingService$a;)V
    //   180: astore 9
    //   182: aload 7
    //   184: aload 9
    //   186: putfield 284	com/umeng/common/net/DownloadingService$d:a	Lcom/umeng/common/net/DownloadingService$b;
    //   189: aload 9
    //   191: invokevirtual 435	com/umeng/common/net/DownloadingService$b:start	()V
    //   194: aload_0
    //   195: aload 7
    //   197: getfield 438	com/umeng/common/net/DownloadingService$d:b	Landroid/app/Notification;
    //   200: iload 5
    //   202: invokespecial 246	com/umeng/common/net/DownloadingService:b	(Landroid/app/Notification;I)V
    //   205: iconst_1
    //   206: ireturn
    //   207: getstatic 58	com/umeng/common/net/DownloadingService:j	Ljava/lang/String;
    //   210: ldc_w 406
    //   213: invokestatic 281	com/umeng/common/Log:c	(Ljava/lang/String;Ljava/lang/String;)V
    //   216: aload 8
    //   218: iconst_1
    //   219: invokevirtual 288	com/umeng/common/net/DownloadingService$b:a	(I)V
    //   222: aload 7
    //   224: aconst_null
    //   225: putfield 284	com/umeng/common/net/DownloadingService$d:a	Lcom/umeng/common/net/DownloadingService$b;
    //   228: aload_0
    //   229: aload 7
    //   231: getfield 438	com/umeng/common/net/DownloadingService$d:b	Landroid/app/Notification;
    //   234: iload 5
    //   236: invokespecial 440	com/umeng/common/net/DownloadingService:a	(Landroid/app/Notification;I)V
    //   239: iconst_1
    //   240: ireturn
    //   241: ldc 248
    //   243: aload 6
    //   245: invokevirtual 404	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   248: ifeq +92 -> 340
    //   251: getstatic 58	com/umeng/common/net/DownloadingService:j	Ljava/lang/String;
    //   254: ldc_w 442
    //   257: invokestatic 281	com/umeng/common/Log:c	(Ljava/lang/String;Ljava/lang/String;)V
    //   260: aload 8
    //   262: ifnull +17 -> 279
    //   265: aload 8
    //   267: iconst_2
    //   268: invokevirtual 288	com/umeng/common/net/DownloadingService$b:a	(I)V
    //   271: aload_0
    //   272: iload 5
    //   274: invokespecial 328	com/umeng/common/net/DownloadingService:a	(I)V
    //   277: iconst_1
    //   278: ireturn
    //   279: aload 7
    //   281: getfield 445	com/umeng/common/net/DownloadingService$d:f	[J
    //   284: iconst_0
    //   285: laload
    //   286: lstore 12
    //   288: aload 7
    //   290: getfield 445	com/umeng/common/net/DownloadingService$d:f	[J
    //   293: iconst_1
    //   294: laload
    //   295: lstore 14
    //   297: aload 7
    //   299: getfield 445	com/umeng/common/net/DownloadingService$d:f	[J
    //   302: iconst_2
    //   303: laload
    //   304: lstore 16
    //   306: aload_0
    //   307: aload 7
    //   309: getfield 276	com/umeng/common/net/DownloadingService$d:e	Lcom/umeng/common/net/a$a;
    //   312: lload 12
    //   314: lload 14
    //   316: lload 16
    //   318: invokespecial 336	com/umeng/common/net/DownloadingService:a	(Lcom/umeng/common/net/a$a;JJJ)V
    //   321: goto -50 -> 271
    //   324: astore 11
    //   326: aload_0
    //   327: iload 5
    //   329: invokespecial 328	com/umeng/common/net/DownloadingService:a	(I)V
    //   332: goto -55 -> 277
    //   335: astore_3
    //   336: aload_3
    //   337: invokevirtual 448	java/lang/Exception:printStackTrace	()V
    //   340: iconst_0
    //   341: ireturn
    //   342: astore 10
    //   344: aload_0
    //   345: iload 5
    //   347: invokespecial 328	com/umeng/common/net/DownloadingService:a	(I)V
    //   350: aload 10
    //   352: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   265	271	324	java/lang/Exception
    //   279	321	324	java/lang/Exception
    //   0	36	335	java/lang/Exception
    //   41	100	335	java/lang/Exception
    //   105	153	335	java/lang/Exception
    //   155	205	335	java/lang/Exception
    //   207	239	335	java/lang/Exception
    //   241	260	335	java/lang/Exception
    //   271	277	335	java/lang/Exception
    //   326	332	335	java/lang/Exception
    //   344	353	335	java/lang/Exception
    //   265	271	342	finally
    //   279	321	342	finally
  }

  private void b(Notification paramNotification, int paramInt)
  {
    int i1 = a.f(this.m);
    paramNotification.contentView.setTextViewText(i1, this.m.getResources().getString(com.umeng.common.a.c.d(this.m.getApplicationContext())));
    paramNotification.contentView.setInt(i1, "setBackgroundResource", com.umeng.common.c.a(this.m).c("umeng_common_gradient_orange"));
    this.k.notify(paramInt, paramNotification);
  }

  private static final void b(Map<String, String> paramMap, boolean paramBoolean, String[] paramArrayOfString)
  {
    new Thread(new i(paramArrayOfString, paramBoolean, paramMap)).start();
  }

  private static boolean b(Context paramContext)
  {
    List localList = ((ActivityManager)paramContext.getSystemService("activity")).getRunningAppProcesses();
    if (localList == null)
      return false;
    String str = paramContext.getPackageName();
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)localIterator.next();
      if ((localRunningAppProcessInfo.importance == 100) && (localRunningAppProcessInfo.processName.equals(str)))
        return true;
    }
    return false;
  }

  private void c(a.a parama)
  {
    Log.c(j, "startDownload([mComponentName:" + parama.a + " mTitle:" + parama.b + " mUrl:" + parama.c + "])");
    int i1 = a(parama);
    b localb = new b(getApplicationContext(), parama, i1, 0, this.o);
    d locald1 = new d(parama, i1);
    locald1.a();
    locald1.a = localb;
    localb.start();
    e();
    if (h)
    {
      Iterator localIterator = r.keySet().iterator();
      while (localIterator.hasNext())
      {
        Integer localInteger = (Integer)localIterator.next();
        d locald2 = (d)r.get(localInteger);
        Log.c(j, "Running task " + locald2.e.b);
      }
    }
  }

  private void d()
  {
    IntentFilter localIntentFilter = new IntentFilter("com.umeng.intent.DOWNLOAD");
    this.s = new f(this);
    registerReceiver(this.s, localIntentFilter);
  }

  private static boolean d(a.a parama)
  {
    if (h)
    {
      int i1 = new Random().nextInt(1000);
      if (q != null)
      {
        Iterator localIterator2 = q.keySet().iterator();
        while (localIterator2.hasNext())
        {
          a.a locala = (a.a)localIterator2.next();
          Log.c(j, "_" + i1 + " downling  " + locala.b + "   " + locala.c);
        }
      }
      Log.c(j, "_" + i1 + "downling  null");
    }
    if (q == null)
      return false;
    Iterator localIterator1 = q.keySet().iterator();
    while (localIterator1.hasNext())
      if (((a.a)localIterator1.next()).c.equals(parama.c))
        return true;
    return false;
  }

  private void e()
  {
    if (h)
    {
      int i1 = q.size();
      int i2 = r.size();
      Log.a(j, "Client size =" + i1 + "   cacheSize = " + i2);
      if (i1 != i2)
        throw new RuntimeException("Client size =" + i1 + "   cacheSize = " + i2);
    }
  }

  public void a(String paramString)
  {
    synchronized (t)
    {
      if (!t.booleanValue())
      {
        Log.c(j, "show single toast.[" + paramString + "]");
        t = Boolean.valueOf(true);
        this.n.post(new g(this, paramString));
        this.n.postDelayed(new h(this), 1200L);
      }
      return;
    }
  }

  public IBinder onBind(Intent paramIntent)
  {
    Log.c(j, "onBind ");
    return this.i.getBinder();
  }

  public void onCreate()
  {
    super.onCreate();
    if (h)
    {
      Log.LOG = true;
      Debug.waitForDebugger();
    }
    Log.c(j, "onCreate ");
    this.k = ((NotificationManager)getSystemService("notification"));
    this.m = this;
    this.n = new d(this);
    this.o = new e(this);
  }

  public void onDestroy()
  {
    try
    {
      c.a(getApplicationContext()).a(259200);
      c.a(getApplicationContext()).finalize();
      if (this.s != null)
        unregisterReceiver(this.s);
      super.onDestroy();
      return;
    }
    catch (Exception localException)
    {
      while (true)
        Log.b(j, localException.getMessage());
    }
  }

  public void onStart(Intent paramIntent, int paramInt)
  {
    Log.c(j, "onStart ");
    a(getApplicationContext(), paramIntent);
    super.onStart(paramIntent, paramInt);
  }

  private static abstract interface a
  {
    public abstract void a(int paramInt);

    public abstract void a(int paramInt1, int paramInt2);

    public abstract void a(int paramInt, Exception paramException);

    public abstract void a(int paramInt, String paramString);
  }

  class b extends Thread
  {
    private Context b;
    private String c;
    private int d = 0;
    private long e = -1L;
    private long f = -1L;
    private int g = -1;
    private int h;
    private DownloadingService.a i;
    private a.a j;

    public b(Context parama, a.a paramInt1, int paramInt2, int parama1, DownloadingService.a arg6)
    {
      try
      {
        this.b = parama;
        this.j = paramInt1;
        this.d = parama1;
        if (DownloadingService.c().containsKey(Integer.valueOf(paramInt2)))
        {
          long[] arrayOfLong = ((DownloadingService.d)DownloadingService.c().get(Integer.valueOf(paramInt2))).f;
          if ((arrayOfLong != null) && (arrayOfLong.length > 1))
          {
            this.e = arrayOfLong[0];
            this.f = arrayOfLong[1];
          }
        }
        Object localObject;
        this.i = localObject;
        this.h = paramInt2;
        if (com.umeng.common.b.b())
        {
          this.c = Environment.getExternalStorageDirectory().getCanonicalPath();
          new File(this.c).mkdirs();
        }
        while (true)
        {
          this.c += "/download/.um/apk";
          new File(this.c).mkdirs();
          return;
          this.c = this.b.getFilesDir().getAbsolutePath();
        }
      }
      catch (Exception localException)
      {
        Log.c(DownloadingService.a(), localException.getMessage(), localException);
        this.i.a(this.h, localException);
      }
    }

    private void a(Exception paramException)
    {
      Log.b(DownloadingService.a(), "can not install. " + paramException.getMessage());
      if (this.i != null)
        this.i.a(this.h, paramException);
      DownloadingService.a(DownloadingService.this, this.j, this.e, this.f, this.d);
    }

    // ERROR //
    private void a(boolean paramBoolean)
    {
      // Byte code:
      //   0: new 101	java/lang/StringBuilder
      //   3: dup
      //   4: invokespecial 102	java/lang/StringBuilder:<init>	()V
      //   7: aload_0
      //   8: getfield 44	com/umeng/common/net/DownloadingService$b:j	Lcom/umeng/common/net/a$a;
      //   11: getfield 156	com/umeng/common/net/a$a:c	Ljava/lang/String;
      //   14: invokestatic 161	com/umeng/common/b/g:a	(Ljava/lang/String;)Ljava/lang/String;
      //   17: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   20: ldc 163
      //   22: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   25: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   28: astore 38
      //   30: invokestatic 79	com/umeng/common/b:b	()Z
      //   33: ifeq +501 -> 534
      //   36: new 87	java/io/File
      //   39: dup
      //   40: aload_0
      //   41: getfield 93	com/umeng/common/net/DownloadingService$b:c	Ljava/lang/String;
      //   44: aload 38
      //   46: invokespecial 165	java/io/File:<init>	(Ljava/lang/String;Ljava/lang/String;)V
      //   49: astore 39
      //   51: new 167	java/io/FileOutputStream
      //   54: dup
      //   55: aload 39
      //   57: iconst_1
      //   58: invokespecial 170	java/io/FileOutputStream:<init>	(Ljava/io/File;Z)V
      //   61: astore 7
      //   63: aload 39
      //   65: astore 40
      //   67: invokestatic 121	com/umeng/common/net/DownloadingService:a	()Ljava/lang/String;
      //   70: astore 42
      //   72: iconst_2
      //   73: anewarray 172	java/lang/Object
      //   76: astore 43
      //   78: aload 43
      //   80: iconst_0
      //   81: aload_0
      //   82: getfield 44	com/umeng/common/net/DownloadingService$b:j	Lcom/umeng/common/net/a$a;
      //   85: getfield 156	com/umeng/common/net/a$a:c	Ljava/lang/String;
      //   88: aastore
      //   89: aload 43
      //   91: iconst_1
      //   92: aload 40
      //   94: invokevirtual 119	java/io/File:getAbsolutePath	()Ljava/lang/String;
      //   97: aastore
      //   98: aload 42
      //   100: ldc 174
      //   102: aload 43
      //   104: invokestatic 180	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      //   107: invokestatic 182	com/umeng/common/Log:c	(Ljava/lang/String;Ljava/lang/String;)V
      //   110: new 184	java/net/URL
      //   113: dup
      //   114: aload_0
      //   115: getfield 44	com/umeng/common/net/DownloadingService$b:j	Lcom/umeng/common/net/a$a;
      //   118: getfield 156	com/umeng/common/net/a$a:c	Ljava/lang/String;
      //   121: invokespecial 185	java/net/URL:<init>	(Ljava/lang/String;)V
      //   124: invokevirtual 189	java/net/URL:openConnection	()Ljava/net/URLConnection;
      //   127: checkcast 191	java/net/HttpURLConnection
      //   130: astore 44
      //   132: aload 44
      //   134: ldc 193
      //   136: invokevirtual 196	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
      //   139: aload 44
      //   141: ldc 198
      //   143: ldc 200
      //   145: invokevirtual 203	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
      //   148: aload 44
      //   150: ldc 205
      //   152: ldc 207
      //   154: invokevirtual 210	java/net/HttpURLConnection:addRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
      //   157: aload 44
      //   159: sipush 5000
      //   162: invokevirtual 214	java/net/HttpURLConnection:setConnectTimeout	(I)V
      //   165: aload 44
      //   167: sipush 10000
      //   170: invokevirtual 217	java/net/HttpURLConnection:setReadTimeout	(I)V
      //   173: aload 40
      //   175: invokevirtual 220	java/io/File:exists	()Z
      //   178: ifeq +48 -> 226
      //   181: aload 40
      //   183: invokevirtual 224	java/io/File:length	()J
      //   186: lconst_0
      //   187: lcmp
      //   188: ifle +38 -> 226
      //   191: aload 44
      //   193: ldc 226
      //   195: new 101	java/lang/StringBuilder
      //   198: dup
      //   199: invokespecial 102	java/lang/StringBuilder:<init>	()V
      //   202: ldc 228
      //   204: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   207: aload 40
      //   209: invokevirtual 224	java/io/File:length	()J
      //   212: invokevirtual 231	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
      //   215: ldc 233
      //   217: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   220: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   223: invokevirtual 203	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
      //   226: aload 44
      //   228: invokevirtual 236	java/net/HttpURLConnection:connect	()V
      //   231: aload 44
      //   233: invokevirtual 240	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
      //   236: astore 45
      //   238: aload 45
      //   240: astore 8
      //   242: iload_1
      //   243: ifne +52 -> 295
      //   246: aload_0
      //   247: lconst_0
      //   248: putfield 36	com/umeng/common/net/DownloadingService$b:e	J
      //   251: aload_0
      //   252: aload 44
      //   254: invokevirtual 244	java/net/HttpURLConnection:getContentLength	()I
      //   257: i2l
      //   258: putfield 38	com/umeng/common/net/DownloadingService$b:f	J
      //   261: invokestatic 121	com/umeng/common/net/DownloadingService:a	()Ljava/lang/String;
      //   264: astore 87
      //   266: iconst_1
      //   267: anewarray 172	java/lang/Object
      //   270: astore 88
      //   272: aload 88
      //   274: iconst_0
      //   275: aload_0
      //   276: getfield 38	com/umeng/common/net/DownloadingService$b:f	J
      //   279: invokestatic 249	java/lang/Long:valueOf	(J)Ljava/lang/Long;
      //   282: aastore
      //   283: aload 87
      //   285: ldc 251
      //   287: aload 88
      //   289: invokestatic 180	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      //   292: invokestatic 182	com/umeng/common/Log:c	(Ljava/lang/String;Ljava/lang/String;)V
      //   295: sipush 4096
      //   298: newarray byte
      //   300: astore 47
      //   302: iconst_0
      //   303: istore 48
      //   305: invokestatic 121	com/umeng/common/net/DownloadingService:a	()Ljava/lang/String;
      //   308: new 101	java/lang/StringBuilder
      //   311: dup
      //   312: invokespecial 102	java/lang/StringBuilder:<init>	()V
      //   315: aload_0
      //   316: getfield 44	com/umeng/common/net/DownloadingService$b:j	Lcom/umeng/common/net/a$a;
      //   319: getfield 253	com/umeng/common/net/a$a:b	Ljava/lang/String;
      //   322: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   325: ldc 255
      //   327: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   330: aload_0
      //   331: getfield 38	com/umeng/common/net/DownloadingService$b:f	J
      //   334: invokestatic 258	java/lang/String:valueOf	(J)Ljava/lang/String;
      //   337: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   340: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   343: invokestatic 182	com/umeng/common/Log:c	(Ljava/lang/String;Ljava/lang/String;)V
      //   346: aload_0
      //   347: getfield 42	com/umeng/common/net/DownloadingService$b:b	Landroid/content/Context;
      //   350: invokestatic 263	com/umeng/common/net/c:a	(Landroid/content/Context;)Lcom/umeng/common/net/c;
      //   353: aload_0
      //   354: getfield 44	com/umeng/common/net/DownloadingService$b:j	Lcom/umeng/common/net/a$a;
      //   357: getfield 265	com/umeng/common/net/a$a:a	Ljava/lang/String;
      //   360: aload_0
      //   361: getfield 44	com/umeng/common/net/DownloadingService$b:j	Lcom/umeng/common/net/a$a;
      //   364: getfield 156	com/umeng/common/net/a$a:c	Ljava/lang/String;
      //   367: invokevirtual 268	com/umeng/common/net/c:a	(Ljava/lang/String;Ljava/lang/String;)Z
      //   370: pop
      //   371: aload_0
      //   372: getfield 40	com/umeng/common/net/DownloadingService$b:g	I
      //   375: ifge +1827 -> 2202
      //   378: aload 8
      //   380: aload 47
      //   382: invokevirtual 274	java/io/InputStream:read	([B)I
      //   385: istore 79
      //   387: iload 79
      //   389: ifle +1813 -> 2202
      //   392: aload 7
      //   394: aload 47
      //   396: iconst_0
      //   397: iload 79
      //   399: invokevirtual 278	java/io/FileOutputStream:write	([BII)V
      //   402: aload_0
      //   403: aload_0
      //   404: getfield 36	com/umeng/common/net/DownloadingService$b:e	J
      //   407: iload 79
      //   409: i2l
      //   410: ladd
      //   411: putfield 36	com/umeng/common/net/DownloadingService$b:e	J
      //   414: iload 48
      //   416: iconst_1
      //   417: iadd
      //   418: istore 80
      //   420: iload 48
      //   422: bipush 50
      //   424: irem
      //   425: ifne +1770 -> 2195
      //   428: aload_0
      //   429: getfield 42	com/umeng/common/net/DownloadingService$b:b	Landroid/content/Context;
      //   432: invokestatic 282	com/umeng/common/b:m	(Landroid/content/Context;)Z
      //   435: ifne +149 -> 584
      //   438: iconst_0
      //   439: istore 50
      //   441: aload 8
      //   443: invokevirtual 285	java/io/InputStream:close	()V
      //   446: aload 7
      //   448: invokevirtual 286	java/io/FileOutputStream:close	()V
      //   451: aload_0
      //   452: getfield 40	com/umeng/common/net/DownloadingService$b:g	I
      //   455: iconst_1
      //   456: if_icmpne +522 -> 978
      //   459: invokestatic 49	com/umeng/common/net/DownloadingService:c	()Ljava/util/Map;
      //   462: aload_0
      //   463: getfield 74	com/umeng/common/net/DownloadingService$b:h	I
      //   466: invokestatic 55	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   469: invokeinterface 65 2 0
      //   474: checkcast 67	com/umeng/common/net/DownloadingService$d
      //   477: astore 73
      //   479: aload 73
      //   481: getfield 70	com/umeng/common/net/DownloadingService$d:f	[J
      //   484: iconst_0
      //   485: aload_0
      //   486: getfield 36	com/umeng/common/net/DownloadingService$b:e	J
      //   489: lastore
      //   490: aload 73
      //   492: getfield 70	com/umeng/common/net/DownloadingService$d:f	[J
      //   495: iconst_1
      //   496: aload_0
      //   497: getfield 38	com/umeng/common/net/DownloadingService$b:f	J
      //   500: lastore
      //   501: aload 73
      //   503: getfield 70	com/umeng/common/net/DownloadingService$d:f	[J
      //   506: iconst_2
      //   507: aload_0
      //   508: getfield 32	com/umeng/common/net/DownloadingService$b:d	I
      //   511: i2l
      //   512: lastore
      //   513: aload 8
      //   515: ifnull +8 -> 523
      //   518: aload 8
      //   520: invokevirtual 285	java/io/InputStream:close	()V
      //   523: aload 7
      //   525: ifnull +8 -> 533
      //   528: aload 7
      //   530: invokevirtual 286	java/io/FileOutputStream:close	()V
      //   533: return
      //   534: aload_0
      //   535: aload_0
      //   536: getfield 42	com/umeng/common/net/DownloadingService$b:b	Landroid/content/Context;
      //   539: invokevirtual 116	android/content/Context:getFilesDir	()Ljava/io/File;
      //   542: invokevirtual 119	java/io/File:getAbsolutePath	()Ljava/lang/String;
      //   545: putfield 93	com/umeng/common/net/DownloadingService$b:c	Ljava/lang/String;
      //   548: aload_0
      //   549: getfield 42	com/umeng/common/net/DownloadingService$b:b	Landroid/content/Context;
      //   552: aload 38
      //   554: ldc_w 287
      //   557: invokevirtual 291	android/content/Context:openFileOutput	(Ljava/lang/String;I)Ljava/io/FileOutputStream;
      //   560: astore 89
      //   562: aload_0
      //   563: getfield 42	com/umeng/common/net/DownloadingService$b:b	Landroid/content/Context;
      //   566: aload 38
      //   568: invokevirtual 295	android/content/Context:getFileStreamPath	(Ljava/lang/String;)Ljava/io/File;
      //   571: astore 91
      //   573: aload 91
      //   575: astore 40
      //   577: aload 89
      //   579: astore 7
      //   581: goto -514 -> 67
      //   584: ldc_w 296
      //   587: aload_0
      //   588: getfield 36	com/umeng/common/net/DownloadingService$b:e	J
      //   591: l2f
      //   592: fmul
      //   593: aload_0
      //   594: getfield 38	com/umeng/common/net/DownloadingService$b:f	J
      //   597: l2f
      //   598: fdiv
      //   599: f2i
      //   600: istore 81
      //   602: iload 81
      //   604: bipush 100
      //   606: if_icmple +1582 -> 2188
      //   609: bipush 99
      //   611: istore 82
      //   613: aload_0
      //   614: getfield 72	com/umeng/common/net/DownloadingService$b:i	Lcom/umeng/common/net/DownloadingService$a;
      //   617: ifnull +18 -> 635
      //   620: aload_0
      //   621: getfield 72	com/umeng/common/net/DownloadingService$b:i	Lcom/umeng/common/net/DownloadingService$a;
      //   624: aload_0
      //   625: getfield 74	com/umeng/common/net/DownloadingService$b:h	I
      //   628: iload 82
      //   630: invokeinterface 299 3 0
      //   635: invokestatic 301	com/umeng/common/net/DownloadingService:b	()Ljava/util/Map;
      //   638: aload_0
      //   639: getfield 44	com/umeng/common/net/DownloadingService$b:j	Lcom/umeng/common/net/a$a;
      //   642: invokeinterface 65 2 0
      //   647: ifnull +29 -> 676
      //   650: invokestatic 301	com/umeng/common/net/DownloadingService:b	()Ljava/util/Map;
      //   653: aload_0
      //   654: getfield 44	com/umeng/common/net/DownloadingService$b:j	Lcom/umeng/common/net/a$a;
      //   657: invokeinterface 65 2 0
      //   662: checkcast 303	android/os/Messenger
      //   665: aconst_null
      //   666: iconst_3
      //   667: iload 82
      //   669: iconst_0
      //   670: invokestatic 309	android/os/Message:obtain	(Landroid/os/Handler;III)Landroid/os/Message;
      //   673: invokevirtual 313	android/os/Messenger:send	(Landroid/os/Message;)V
      //   676: aload_0
      //   677: getfield 42	com/umeng/common/net/DownloadingService$b:b	Landroid/content/Context;
      //   680: invokestatic 263	com/umeng/common/net/c:a	(Landroid/content/Context;)Lcom/umeng/common/net/c;
      //   683: aload_0
      //   684: getfield 44	com/umeng/common/net/DownloadingService$b:j	Lcom/umeng/common/net/a$a;
      //   687: getfield 265	com/umeng/common/net/a$a:a	Ljava/lang/String;
      //   690: aload_0
      //   691: getfield 44	com/umeng/common/net/DownloadingService$b:j	Lcom/umeng/common/net/a$a;
      //   694: getfield 156	com/umeng/common/net/a$a:c	Ljava/lang/String;
      //   697: iload 82
      //   699: invokevirtual 316	com/umeng/common/net/c:a	(Ljava/lang/String;Ljava/lang/String;I)V
      //   702: iload 80
      //   704: istore 48
      //   706: goto -335 -> 371
      //   709: astore 83
      //   711: invokestatic 121	com/umeng/common/net/DownloadingService:a	()Ljava/lang/String;
      //   714: astore 84
      //   716: iconst_1
      //   717: anewarray 172	java/lang/Object
      //   720: astore 85
      //   722: aload 85
      //   724: iconst_0
      //   725: aload_0
      //   726: getfield 44	com/umeng/common/net/DownloadingService$b:j	Lcom/umeng/common/net/a$a;
      //   729: getfield 253	com/umeng/common/net/a$a:b	Ljava/lang/String;
      //   732: aastore
      //   733: aload 84
      //   735: ldc_w 318
      //   738: aload 85
      //   740: invokestatic 180	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      //   743: invokestatic 141	com/umeng/common/Log:b	(Ljava/lang/String;Ljava/lang/String;)V
      //   746: invokestatic 301	com/umeng/common/net/DownloadingService:b	()Ljava/util/Map;
      //   749: aload_0
      //   750: getfield 44	com/umeng/common/net/DownloadingService$b:j	Lcom/umeng/common/net/a$a;
      //   753: aconst_null
      //   754: invokeinterface 322 3 0
      //   759: pop
      //   760: goto -84 -> 676
      //   763: astore 46
      //   765: aload 46
      //   767: astore_3
      //   768: aload 7
      //   770: astore 4
      //   772: aload 8
      //   774: astore 5
      //   776: invokestatic 121	com/umeng/common/net/DownloadingService:a	()Ljava/lang/String;
      //   779: aload_3
      //   780: invokevirtual 323	java/io/IOException:getMessage	()Ljava/lang/String;
      //   783: aload_3
      //   784: invokestatic 129	com/umeng/common/Log:c	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Exception;)V
      //   787: iconst_1
      //   788: aload_0
      //   789: getfield 32	com/umeng/common/net/DownloadingService$b:d	I
      //   792: iadd
      //   793: istore 14
      //   795: aload_0
      //   796: iload 14
      //   798: putfield 32	com/umeng/common/net/DownloadingService$b:d	I
      //   801: iload 14
      //   803: iconst_3
      //   804: if_icmple +824 -> 1628
      //   807: aload_0
      //   808: getfield 44	com/umeng/common/net/DownloadingService$b:j	Lcom/umeng/common/net/a$a;
      //   811: getfield 326	com/umeng/common/net/a$a:e	Z
      //   814: istore 15
      //   816: iload 15
      //   818: ifne +810 -> 1628
      //   821: invokestatic 121	com/umeng/common/net/DownloadingService:a	()Ljava/lang/String;
      //   824: ldc_w 328
      //   827: invokestatic 141	com/umeng/common/Log:b	(Ljava/lang/String;Ljava/lang/String;)V
      //   830: invokestatic 301	com/umeng/common/net/DownloadingService:b	()Ljava/util/Map;
      //   833: aload_0
      //   834: getfield 44	com/umeng/common/net/DownloadingService$b:j	Lcom/umeng/common/net/a$a;
      //   837: invokeinterface 65 2 0
      //   842: checkcast 303	android/os/Messenger
      //   845: aconst_null
      //   846: iconst_5
      //   847: iconst_0
      //   848: iconst_0
      //   849: invokestatic 309	android/os/Message:obtain	(Landroid/os/Handler;III)Landroid/os/Message;
      //   852: invokevirtual 313	android/os/Messenger:send	(Landroid/os/Message;)V
      //   855: aload_0
      //   856: getfield 27	com/umeng/common/net/DownloadingService$b:a	Lcom/umeng/common/net/DownloadingService;
      //   859: aload_0
      //   860: getfield 74	com/umeng/common/net/DownloadingService$b:h	I
      //   863: invokestatic 331	com/umeng/common/net/DownloadingService:a	(Lcom/umeng/common/net/DownloadingService;I)V
      //   866: aload_0
      //   867: aload_3
      //   868: invokespecial 333	com/umeng/common/net/DownloadingService$b:a	(Ljava/lang/Exception;)V
      //   871: aload_0
      //   872: getfield 27	com/umeng/common/net/DownloadingService$b:a	Lcom/umeng/common/net/DownloadingService;
      //   875: invokestatic 336	com/umeng/common/net/DownloadingService:c	(Lcom/umeng/common/net/DownloadingService;)Landroid/os/Handler;
      //   878: new 338	com/umeng/common/net/j
      //   881: dup
      //   882: aload_0
      //   883: invokespecial 341	com/umeng/common/net/j:<init>	(Lcom/umeng/common/net/DownloadingService$b;)V
      //   886: invokevirtual 347	android/os/Handler:post	(Ljava/lang/Runnable;)Z
      //   889: pop
      //   890: aload 5
      //   892: ifnull +8 -> 900
      //   895: aload 5
      //   897: invokevirtual 285	java/io/InputStream:close	()V
      //   900: aload 4
      //   902: ifnull -369 -> 533
      //   905: aload 4
      //   907: invokevirtual 286	java/io/FileOutputStream:close	()V
      //   910: return
      //   911: astore 17
      //   913: aload 17
      //   915: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   918: return
      //   919: astore 74
      //   921: aload 74
      //   923: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   926: return
      //   927: astore 77
      //   929: aload 77
      //   931: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   934: aload 7
      //   936: ifnull -403 -> 533
      //   939: aload 7
      //   941: invokevirtual 286	java/io/FileOutputStream:close	()V
      //   944: return
      //   945: astore 78
      //   947: aload 78
      //   949: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   952: return
      //   953: astore 75
      //   955: aload 7
      //   957: ifnull +8 -> 965
      //   960: aload 7
      //   962: invokevirtual 286	java/io/FileOutputStream:close	()V
      //   965: aload 75
      //   967: athrow
      //   968: astore 76
      //   970: aload 76
      //   972: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   975: goto -10 -> 965
      //   978: aload_0
      //   979: getfield 40	com/umeng/common/net/DownloadingService$b:g	I
      //   982: iconst_2
      //   983: if_icmpne +121 -> 1104
      //   986: aload_0
      //   987: getfield 27	com/umeng/common/net/DownloadingService$b:a	Lcom/umeng/common/net/DownloadingService;
      //   990: aload_0
      //   991: getfield 44	com/umeng/common/net/DownloadingService$b:j	Lcom/umeng/common/net/a$a;
      //   994: aload_0
      //   995: getfield 36	com/umeng/common/net/DownloadingService$b:e	J
      //   998: aload_0
      //   999: getfield 38	com/umeng/common/net/DownloadingService$b:f	J
      //   1002: aload_0
      //   1003: getfield 32	com/umeng/common/net/DownloadingService$b:d	I
      //   1006: i2l
      //   1007: invokestatic 144	com/umeng/common/net/DownloadingService:a	(Lcom/umeng/common/net/DownloadingService;Lcom/umeng/common/net/a$a;JJJ)V
      //   1010: aload_0
      //   1011: getfield 27	com/umeng/common/net/DownloadingService$b:a	Lcom/umeng/common/net/DownloadingService;
      //   1014: invokestatic 353	com/umeng/common/net/DownloadingService:b	(Lcom/umeng/common/net/DownloadingService;)Landroid/app/NotificationManager;
      //   1017: aload_0
      //   1018: getfield 74	com/umeng/common/net/DownloadingService$b:h	I
      //   1021: invokevirtual 358	android/app/NotificationManager:cancel	(I)V
      //   1024: aload 8
      //   1026: ifnull +8 -> 1034
      //   1029: aload 8
      //   1031: invokevirtual 285	java/io/InputStream:close	()V
      //   1034: aload 7
      //   1036: ifnull -503 -> 533
      //   1039: aload 7
      //   1041: invokevirtual 286	java/io/FileOutputStream:close	()V
      //   1044: return
      //   1045: astore 68
      //   1047: aload 68
      //   1049: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   1052: return
      //   1053: astore 71
      //   1055: aload 71
      //   1057: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   1060: aload 7
      //   1062: ifnull -529 -> 533
      //   1065: aload 7
      //   1067: invokevirtual 286	java/io/FileOutputStream:close	()V
      //   1070: return
      //   1071: astore 72
      //   1073: aload 72
      //   1075: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   1078: return
      //   1079: astore 69
      //   1081: aload 7
      //   1083: ifnull +8 -> 1091
      //   1086: aload 7
      //   1088: invokevirtual 286	java/io/FileOutputStream:close	()V
      //   1091: aload 69
      //   1093: athrow
      //   1094: astore 70
      //   1096: aload 70
      //   1098: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   1101: goto -10 -> 1091
      //   1104: iload 50
      //   1106: ifne +169 -> 1275
      //   1109: invokestatic 121	com/umeng/common/net/DownloadingService:a	()Ljava/lang/String;
      //   1112: new 101	java/lang/StringBuilder
      //   1115: dup
      //   1116: invokespecial 102	java/lang/StringBuilder:<init>	()V
      //   1119: ldc_w 360
      //   1122: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1125: aload_0
      //   1126: getfield 32	com/umeng/common/net/DownloadingService$b:d	I
      //   1129: invokevirtual 363	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   1132: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   1135: invokestatic 141	com/umeng/common/Log:b	(Ljava/lang/String;Ljava/lang/String;)V
      //   1138: invokestatic 301	com/umeng/common/net/DownloadingService:b	()Ljava/util/Map;
      //   1141: aload_0
      //   1142: getfield 44	com/umeng/common/net/DownloadingService$b:j	Lcom/umeng/common/net/a$a;
      //   1145: invokeinterface 65 2 0
      //   1150: checkcast 303	android/os/Messenger
      //   1153: aconst_null
      //   1154: iconst_5
      //   1155: iconst_0
      //   1156: iconst_0
      //   1157: invokestatic 309	android/os/Message:obtain	(Landroid/os/Handler;III)Landroid/os/Message;
      //   1160: invokevirtual 313	android/os/Messenger:send	(Landroid/os/Message;)V
      //   1163: aload_0
      //   1164: getfield 27	com/umeng/common/net/DownloadingService$b:a	Lcom/umeng/common/net/DownloadingService;
      //   1167: aload_0
      //   1168: getfield 74	com/umeng/common/net/DownloadingService$b:h	I
      //   1171: invokestatic 331	com/umeng/common/net/DownloadingService:a	(Lcom/umeng/common/net/DownloadingService;I)V
      //   1174: aload_0
      //   1175: getfield 72	com/umeng/common/net/DownloadingService$b:i	Lcom/umeng/common/net/DownloadingService$a;
      //   1178: ifnull +17 -> 1195
      //   1181: aload_0
      //   1182: getfield 72	com/umeng/common/net/DownloadingService$b:i	Lcom/umeng/common/net/DownloadingService$a;
      //   1185: aload_0
      //   1186: getfield 74	com/umeng/common/net/DownloadingService$b:h	I
      //   1189: aconst_null
      //   1190: invokeinterface 134 3 0
      //   1195: aload 8
      //   1197: ifnull +8 -> 1205
      //   1200: aload 8
      //   1202: invokevirtual 285	java/io/InputStream:close	()V
      //   1205: aload 7
      //   1207: ifnull -674 -> 533
      //   1210: aload 7
      //   1212: invokevirtual 286	java/io/FileOutputStream:close	()V
      //   1215: return
      //   1216: astore 63
      //   1218: aload 63
      //   1220: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   1223: return
      //   1224: astore 66
      //   1226: aload 66
      //   1228: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   1231: aload 7
      //   1233: ifnull -700 -> 533
      //   1236: aload 7
      //   1238: invokevirtual 286	java/io/FileOutputStream:close	()V
      //   1241: return
      //   1242: astore 67
      //   1244: aload 67
      //   1246: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   1249: return
      //   1250: astore 64
      //   1252: aload 7
      //   1254: ifnull +8 -> 1262
      //   1257: aload 7
      //   1259: invokevirtual 286	java/io/FileOutputStream:close	()V
      //   1262: aload 64
      //   1264: athrow
      //   1265: astore 65
      //   1267: aload 65
      //   1269: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   1272: goto -10 -> 1262
      //   1275: aload_0
      //   1276: getfield 44	com/umeng/common/net/DownloadingService$b:j	Lcom/umeng/common/net/a$a;
      //   1279: getfield 366	com/umeng/common/net/a$a:d	[Ljava/lang/String;
      //   1282: ifnull +83 -> 1365
      //   1285: new 368	java/util/HashMap
      //   1288: dup
      //   1289: invokespecial 369	java/util/HashMap:<init>	()V
      //   1292: astore 51
      //   1294: aload 51
      //   1296: ldc_w 371
      //   1299: aload_0
      //   1300: getfield 38	com/umeng/common/net/DownloadingService$b:f	J
      //   1303: invokestatic 258	java/lang/String:valueOf	(J)Ljava/lang/String;
      //   1306: invokeinterface 322 3 0
      //   1311: pop
      //   1312: aload 51
      //   1314: ldc_w 373
      //   1317: invokestatic 374	com/umeng/common/b/g:a	()Ljava/lang/String;
      //   1320: ldc_w 376
      //   1323: invokevirtual 380	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
      //   1326: iconst_1
      //   1327: aaload
      //   1328: invokeinterface 322 3 0
      //   1333: pop
      //   1334: aload 51
      //   1336: ldc_w 382
      //   1339: aload_0
      //   1340: getfield 32	com/umeng/common/net/DownloadingService$b:d	I
      //   1343: invokestatic 385	java/lang/String:valueOf	(I)Ljava/lang/String;
      //   1346: invokeinterface 322 3 0
      //   1351: pop
      //   1352: aload 51
      //   1354: iconst_1
      //   1355: aload_0
      //   1356: getfield 44	com/umeng/common/net/DownloadingService$b:j	Lcom/umeng/common/net/a$a;
      //   1359: getfield 366	com/umeng/common/net/a$a:d	[Ljava/lang/String;
      //   1362: invokestatic 388	com/umeng/common/net/DownloadingService:a	(Ljava/util/Map;Z[Ljava/lang/String;)V
      //   1365: new 87	java/io/File
      //   1368: dup
      //   1369: aload 40
      //   1371: invokevirtual 391	java/io/File:getParent	()Ljava/lang/String;
      //   1374: aload 40
      //   1376: invokevirtual 394	java/io/File:getName	()Ljava/lang/String;
      //   1379: ldc_w 396
      //   1382: ldc_w 398
      //   1385: invokevirtual 402	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
      //   1388: invokespecial 165	java/io/File:<init>	(Ljava/lang/String;Ljava/lang/String;)V
      //   1391: astore 55
      //   1393: aload 40
      //   1395: aload 55
      //   1397: invokevirtual 406	java/io/File:renameTo	(Ljava/io/File;)Z
      //   1400: pop
      //   1401: aload 55
      //   1403: invokevirtual 119	java/io/File:getAbsolutePath	()Ljava/lang/String;
      //   1406: astore 57
      //   1408: aload_0
      //   1409: getfield 72	com/umeng/common/net/DownloadingService$b:i	Lcom/umeng/common/net/DownloadingService$a;
      //   1412: ifnull +18 -> 1430
      //   1415: aload_0
      //   1416: getfield 72	com/umeng/common/net/DownloadingService$b:i	Lcom/umeng/common/net/DownloadingService$a;
      //   1419: aload_0
      //   1420: getfield 74	com/umeng/common/net/DownloadingService$b:h	I
      //   1423: aload 57
      //   1425: invokeinterface 409 3 0
      //   1430: aload 8
      //   1432: ifnull +8 -> 1440
      //   1435: aload 8
      //   1437: invokevirtual 285	java/io/InputStream:close	()V
      //   1440: aload 7
      //   1442: ifnull -909 -> 533
      //   1445: aload 7
      //   1447: invokevirtual 286	java/io/FileOutputStream:close	()V
      //   1450: return
      //   1451: astore 58
      //   1453: aload 58
      //   1455: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   1458: return
      //   1459: astore 61
      //   1461: aload 61
      //   1463: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   1466: aload 7
      //   1468: ifnull -935 -> 533
      //   1471: aload 7
      //   1473: invokevirtual 286	java/io/FileOutputStream:close	()V
      //   1476: return
      //   1477: astore 62
      //   1479: aload 62
      //   1481: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   1484: return
      //   1485: astore 59
      //   1487: aload 7
      //   1489: ifnull +8 -> 1497
      //   1492: aload 7
      //   1494: invokevirtual 286	java/io/FileOutputStream:close	()V
      //   1497: aload 59
      //   1499: athrow
      //   1500: astore 60
      //   1502: aload 60
      //   1504: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   1507: goto -10 -> 1497
      //   1510: astore 29
      //   1512: aload 29
      //   1514: invokevirtual 410	android/os/RemoteException:printStackTrace	()V
      //   1517: aload_0
      //   1518: getfield 27	com/umeng/common/net/DownloadingService$b:a	Lcom/umeng/common/net/DownloadingService;
      //   1521: aload_0
      //   1522: getfield 74	com/umeng/common/net/DownloadingService$b:h	I
      //   1525: invokestatic 331	com/umeng/common/net/DownloadingService:a	(Lcom/umeng/common/net/DownloadingService;I)V
      //   1528: aload_0
      //   1529: aload_3
      //   1530: invokespecial 333	com/umeng/common/net/DownloadingService$b:a	(Ljava/lang/Exception;)V
      //   1533: aload_0
      //   1534: getfield 27	com/umeng/common/net/DownloadingService$b:a	Lcom/umeng/common/net/DownloadingService;
      //   1537: invokestatic 336	com/umeng/common/net/DownloadingService:c	(Lcom/umeng/common/net/DownloadingService;)Landroid/os/Handler;
      //   1540: new 338	com/umeng/common/net/j
      //   1543: dup
      //   1544: aload_0
      //   1545: invokespecial 341	com/umeng/common/net/j:<init>	(Lcom/umeng/common/net/DownloadingService$b;)V
      //   1548: invokevirtual 347	android/os/Handler:post	(Ljava/lang/Runnable;)Z
      //   1551: pop
      //   1552: goto -662 -> 890
      //   1555: astore 6
      //   1557: aload 4
      //   1559: astore 7
      //   1561: aload 5
      //   1563: astore 8
      //   1565: aload 8
      //   1567: ifnull +8 -> 1575
      //   1570: aload 8
      //   1572: invokevirtual 285	java/io/InputStream:close	()V
      //   1575: aload 7
      //   1577: ifnull +8 -> 1585
      //   1580: aload 7
      //   1582: invokevirtual 286	java/io/FileOutputStream:close	()V
      //   1585: aload 6
      //   1587: athrow
      //   1588: astore 27
      //   1590: aload_0
      //   1591: getfield 27	com/umeng/common/net/DownloadingService$b:a	Lcom/umeng/common/net/DownloadingService;
      //   1594: aload_0
      //   1595: getfield 74	com/umeng/common/net/DownloadingService$b:h	I
      //   1598: invokestatic 331	com/umeng/common/net/DownloadingService:a	(Lcom/umeng/common/net/DownloadingService;I)V
      //   1601: aload_0
      //   1602: aload_3
      //   1603: invokespecial 333	com/umeng/common/net/DownloadingService$b:a	(Ljava/lang/Exception;)V
      //   1606: aload_0
      //   1607: getfield 27	com/umeng/common/net/DownloadingService$b:a	Lcom/umeng/common/net/DownloadingService;
      //   1610: invokestatic 336	com/umeng/common/net/DownloadingService:c	(Lcom/umeng/common/net/DownloadingService;)Landroid/os/Handler;
      //   1613: new 338	com/umeng/common/net/j
      //   1616: dup
      //   1617: aload_0
      //   1618: invokespecial 341	com/umeng/common/net/j:<init>	(Lcom/umeng/common/net/DownloadingService$b;)V
      //   1621: invokevirtual 347	android/os/Handler:post	(Ljava/lang/Runnable;)Z
      //   1624: pop
      //   1625: aload 27
      //   1627: athrow
      //   1628: invokestatic 121	com/umeng/common/net/DownloadingService:a	()Ljava/lang/String;
      //   1631: new 101	java/lang/StringBuilder
      //   1634: dup
      //   1635: invokespecial 102	java/lang/StringBuilder:<init>	()V
      //   1638: ldc_w 412
      //   1641: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1644: aload_0
      //   1645: getfield 32	com/umeng/common/net/DownloadingService$b:d	I
      //   1648: invokevirtual 363	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   1651: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   1654: invokestatic 182	com/umeng/common/Log:c	(Ljava/lang/String;Ljava/lang/String;)V
      //   1657: aload_0
      //   1658: getfield 44	com/umeng/common/net/DownloadingService$b:j	Lcom/umeng/common/net/a$a;
      //   1661: getfield 326	com/umeng/common/net/a$a:e	Z
      //   1664: ifne +56 -> 1720
      //   1667: ldc2_w 413
      //   1670: invokestatic 418	java/lang/Thread:sleep	(J)V
      //   1673: aload_0
      //   1674: getfield 38	com/umeng/common/net/DownloadingService$b:f	J
      //   1677: lconst_1
      //   1678: lcmp
      //   1679: ifge +33 -> 1712
      //   1682: aload_0
      //   1683: iconst_0
      //   1684: invokespecial 420	com/umeng/common/net/DownloadingService$b:a	(Z)V
      //   1687: goto -797 -> 890
      //   1690: astore 16
      //   1692: aload_0
      //   1693: aload 16
      //   1695: invokespecial 333	com/umeng/common/net/DownloadingService$b:a	(Ljava/lang/Exception;)V
      //   1698: aload_0
      //   1699: getfield 27	com/umeng/common/net/DownloadingService$b:a	Lcom/umeng/common/net/DownloadingService;
      //   1702: aload_0
      //   1703: getfield 74	com/umeng/common/net/DownloadingService$b:h	I
      //   1706: invokestatic 331	com/umeng/common/net/DownloadingService:a	(Lcom/umeng/common/net/DownloadingService;I)V
      //   1709: goto -819 -> 890
      //   1712: aload_0
      //   1713: iconst_1
      //   1714: invokespecial 420	com/umeng/common/net/DownloadingService$b:a	(Z)V
      //   1717: goto -827 -> 890
      //   1720: invokestatic 49	com/umeng/common/net/DownloadingService:c	()Ljava/util/Map;
      //   1723: aload_0
      //   1724: getfield 74	com/umeng/common/net/DownloadingService$b:h	I
      //   1727: invokestatic 55	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   1730: invokeinterface 65 2 0
      //   1735: checkcast 67	com/umeng/common/net/DownloadingService$d
      //   1738: astore 22
      //   1740: aload 22
      //   1742: getfield 70	com/umeng/common/net/DownloadingService$d:f	[J
      //   1745: iconst_0
      //   1746: aload_0
      //   1747: getfield 36	com/umeng/common/net/DownloadingService$b:e	J
      //   1750: lastore
      //   1751: aload 22
      //   1753: getfield 70	com/umeng/common/net/DownloadingService$d:f	[J
      //   1756: iconst_1
      //   1757: aload_0
      //   1758: getfield 38	com/umeng/common/net/DownloadingService$b:f	J
      //   1761: lastore
      //   1762: aload 22
      //   1764: getfield 70	com/umeng/common/net/DownloadingService$d:f	[J
      //   1767: iconst_2
      //   1768: aload_0
      //   1769: getfield 32	com/umeng/common/net/DownloadingService$b:d	I
      //   1772: i2l
      //   1773: lastore
      //   1774: aload_0
      //   1775: getfield 74	com/umeng/common/net/DownloadingService$b:h	I
      //   1778: ldc_w 422
      //   1781: invokestatic 427	com/umeng/common/net/l:a	(ILjava/lang/String;)Ljava/lang/String;
      //   1784: astore 23
      //   1786: new 429	android/content/Intent
      //   1789: dup
      //   1790: aload_0
      //   1791: getfield 42	com/umeng/common/net/DownloadingService$b:b	Landroid/content/Context;
      //   1794: ldc 46
      //   1796: invokespecial 432	android/content/Intent:<init>	(Landroid/content/Context;Ljava/lang/Class;)V
      //   1799: astore 24
      //   1801: aload 24
      //   1803: ldc_w 434
      //   1806: aload 23
      //   1808: invokevirtual 438	android/content/Intent:putExtra	(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;
      //   1811: pop
      //   1812: aload_0
      //   1813: getfield 27	com/umeng/common/net/DownloadingService$b:a	Lcom/umeng/common/net/DownloadingService;
      //   1816: aload_0
      //   1817: getfield 42	com/umeng/common/net/DownloadingService$b:b	Landroid/content/Context;
      //   1820: aload 24
      //   1822: invokestatic 441	com/umeng/common/net/DownloadingService:a	(Lcom/umeng/common/net/DownloadingService;Landroid/content/Context;Landroid/content/Intent;)Z
      //   1825: pop
      //   1826: aload_0
      //   1827: getfield 27	com/umeng/common/net/DownloadingService$b:a	Lcom/umeng/common/net/DownloadingService;
      //   1830: aload_0
      //   1831: getfield 42	com/umeng/common/net/DownloadingService$b:b	Landroid/content/Context;
      //   1834: aload_0
      //   1835: getfield 42	com/umeng/common/net/DownloadingService$b:b	Landroid/content/Context;
      //   1838: invokestatic 446	com/umeng/common/c:a	(Landroid/content/Context;)Lcom/umeng/common/c;
      //   1841: ldc_w 448
      //   1844: invokevirtual 451	com/umeng/common/c:f	(Ljava/lang/String;)I
      //   1847: invokevirtual 454	android/content/Context:getString	(I)Ljava/lang/String;
      //   1850: invokevirtual 456	com/umeng/common/net/DownloadingService:a	(Ljava/lang/String;)V
      //   1853: invokestatic 121	com/umeng/common/net/DownloadingService:a	()Ljava/lang/String;
      //   1856: ldc_w 458
      //   1859: invokestatic 182	com/umeng/common/Log:c	(Ljava/lang/String;Ljava/lang/String;)V
      //   1862: goto -972 -> 890
      //   1865: astore 20
      //   1867: aload 20
      //   1869: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   1872: aload 4
      //   1874: ifnull -1341 -> 533
      //   1877: aload 4
      //   1879: invokevirtual 286	java/io/FileOutputStream:close	()V
      //   1882: return
      //   1883: astore 21
      //   1885: aload 21
      //   1887: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   1890: return
      //   1891: astore 18
      //   1893: aload 4
      //   1895: ifnull +8 -> 1903
      //   1898: aload 4
      //   1900: invokevirtual 286	java/io/FileOutputStream:close	()V
      //   1903: aload 18
      //   1905: athrow
      //   1906: astore 19
      //   1908: aload 19
      //   1910: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   1913: goto -10 -> 1903
      //   1916: astore 32
      //   1918: aconst_null
      //   1919: astore 7
      //   1921: aconst_null
      //   1922: astore 8
      //   1924: aload_0
      //   1925: getfield 27	com/umeng/common/net/DownloadingService$b:a	Lcom/umeng/common/net/DownloadingService;
      //   1928: aload_0
      //   1929: getfield 74	com/umeng/common/net/DownloadingService$b:h	I
      //   1932: invokestatic 331	com/umeng/common/net/DownloadingService:a	(Lcom/umeng/common/net/DownloadingService;I)V
      //   1935: aload 32
      //   1937: invokevirtual 410	android/os/RemoteException:printStackTrace	()V
      //   1940: aload 8
      //   1942: ifnull +8 -> 1950
      //   1945: aload 8
      //   1947: invokevirtual 285	java/io/InputStream:close	()V
      //   1950: aload 7
      //   1952: ifnull -1419 -> 533
      //   1955: aload 7
      //   1957: invokevirtual 286	java/io/FileOutputStream:close	()V
      //   1960: return
      //   1961: astore 33
      //   1963: aload 33
      //   1965: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   1968: return
      //   1969: astore 36
      //   1971: aload 36
      //   1973: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   1976: aload 7
      //   1978: ifnull -1445 -> 533
      //   1981: aload 7
      //   1983: invokevirtual 286	java/io/FileOutputStream:close	()V
      //   1986: return
      //   1987: astore 37
      //   1989: aload 37
      //   1991: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   1994: return
      //   1995: astore 34
      //   1997: aload 7
      //   1999: ifnull +8 -> 2007
      //   2002: aload 7
      //   2004: invokevirtual 286	java/io/FileOutputStream:close	()V
      //   2007: aload 34
      //   2009: athrow
      //   2010: astore 35
      //   2012: aload 35
      //   2014: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   2017: goto -10 -> 2007
      //   2020: astore 9
      //   2022: aload 9
      //   2024: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   2027: goto -442 -> 1585
      //   2030: astore 12
      //   2032: aload 12
      //   2034: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   2037: aload 7
      //   2039: ifnull -454 -> 1585
      //   2042: aload 7
      //   2044: invokevirtual 286	java/io/FileOutputStream:close	()V
      //   2047: goto -462 -> 1585
      //   2050: astore 13
      //   2052: aload 13
      //   2054: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   2057: goto -472 -> 1585
      //   2060: astore 10
      //   2062: aload 7
      //   2064: ifnull +8 -> 2072
      //   2067: aload 7
      //   2069: invokevirtual 286	java/io/FileOutputStream:close	()V
      //   2072: aload 10
      //   2074: athrow
      //   2075: astore 11
      //   2077: aload 11
      //   2079: invokevirtual 350	java/io/IOException:printStackTrace	()V
      //   2082: goto -10 -> 2072
      //   2085: astore 6
      //   2087: aconst_null
      //   2088: astore 7
      //   2090: aconst_null
      //   2091: astore 8
      //   2093: goto -528 -> 1565
      //   2096: astore 6
      //   2098: aload 89
      //   2100: astore 7
      //   2102: aconst_null
      //   2103: astore 8
      //   2105: goto -540 -> 1565
      //   2108: astore 6
      //   2110: aconst_null
      //   2111: astore 8
      //   2113: goto -548 -> 1565
      //   2116: astore 6
      //   2118: goto -553 -> 1565
      //   2121: astore 32
      //   2123: aload 89
      //   2125: astore 7
      //   2127: aconst_null
      //   2128: astore 8
      //   2130: goto -206 -> 1924
      //   2133: astore 32
      //   2135: aconst_null
      //   2136: astore 8
      //   2138: goto -214 -> 1924
      //   2141: astore 32
      //   2143: goto -219 -> 1924
      //   2146: astore_2
      //   2147: aload_2
      //   2148: astore_3
      //   2149: aconst_null
      //   2150: astore 4
      //   2152: aconst_null
      //   2153: astore 5
      //   2155: goto -1379 -> 776
      //   2158: astore 90
      //   2160: aload 89
      //   2162: astore 4
      //   2164: aload 90
      //   2166: astore_3
      //   2167: aconst_null
      //   2168: astore 5
      //   2170: goto -1394 -> 776
      //   2173: astore 41
      //   2175: aload 41
      //   2177: astore_3
      //   2178: aload 7
      //   2180: astore 4
      //   2182: aconst_null
      //   2183: astore 5
      //   2185: goto -1409 -> 776
      //   2188: iload 81
      //   2190: istore 82
      //   2192: goto -1579 -> 613
      //   2195: iload 80
      //   2197: istore 48
      //   2199: goto -1828 -> 371
      //   2202: iconst_1
      //   2203: istore 50
      //   2205: goto -1764 -> 441
      //
      // Exception table:
      //   from	to	target	type
      //   635	676	709	android/os/DeadObjectException
      //   246	295	763	java/io/IOException
      //   295	302	763	java/io/IOException
      //   305	371	763	java/io/IOException
      //   371	387	763	java/io/IOException
      //   392	414	763	java/io/IOException
      //   428	438	763	java/io/IOException
      //   441	513	763	java/io/IOException
      //   584	602	763	java/io/IOException
      //   613	635	763	java/io/IOException
      //   635	676	763	java/io/IOException
      //   676	702	763	java/io/IOException
      //   711	760	763	java/io/IOException
      //   978	1024	763	java/io/IOException
      //   1109	1195	763	java/io/IOException
      //   1275	1365	763	java/io/IOException
      //   1365	1430	763	java/io/IOException
      //   905	910	911	java/io/IOException
      //   528	533	919	java/io/IOException
      //   518	523	927	java/io/IOException
      //   939	944	945	java/io/IOException
      //   518	523	953	finally
      //   929	934	953	finally
      //   960	965	968	java/io/IOException
      //   1039	1044	1045	java/io/IOException
      //   1029	1034	1053	java/io/IOException
      //   1065	1070	1071	java/io/IOException
      //   1029	1034	1079	finally
      //   1055	1060	1079	finally
      //   1086	1091	1094	java/io/IOException
      //   1210	1215	1216	java/io/IOException
      //   1200	1205	1224	java/io/IOException
      //   1236	1241	1242	java/io/IOException
      //   1200	1205	1250	finally
      //   1226	1231	1250	finally
      //   1257	1262	1265	java/io/IOException
      //   1445	1450	1451	java/io/IOException
      //   1435	1440	1459	java/io/IOException
      //   1471	1476	1477	java/io/IOException
      //   1435	1440	1485	finally
      //   1461	1466	1485	finally
      //   1492	1497	1500	java/io/IOException
      //   821	855	1510	android/os/RemoteException
      //   776	801	1555	finally
      //   807	816	1555	finally
      //   855	890	1555	finally
      //   1517	1552	1555	finally
      //   1590	1628	1555	finally
      //   1628	1657	1555	finally
      //   1657	1687	1555	finally
      //   1692	1709	1555	finally
      //   1712	1717	1555	finally
      //   1720	1862	1555	finally
      //   821	855	1588	finally
      //   1512	1517	1588	finally
      //   1657	1687	1690	java/lang/InterruptedException
      //   1712	1717	1690	java/lang/InterruptedException
      //   1720	1862	1690	java/lang/InterruptedException
      //   895	900	1865	java/io/IOException
      //   1877	1882	1883	java/io/IOException
      //   895	900	1891	finally
      //   1867	1872	1891	finally
      //   1898	1903	1906	java/io/IOException
      //   0	63	1916	android/os/RemoteException
      //   534	562	1916	android/os/RemoteException
      //   1955	1960	1961	java/io/IOException
      //   1945	1950	1969	java/io/IOException
      //   1981	1986	1987	java/io/IOException
      //   1945	1950	1995	finally
      //   1971	1976	1995	finally
      //   2002	2007	2010	java/io/IOException
      //   1580	1585	2020	java/io/IOException
      //   1570	1575	2030	java/io/IOException
      //   2042	2047	2050	java/io/IOException
      //   1570	1575	2060	finally
      //   2032	2037	2060	finally
      //   2067	2072	2075	java/io/IOException
      //   0	63	2085	finally
      //   534	562	2085	finally
      //   562	573	2096	finally
      //   67	226	2108	finally
      //   226	238	2108	finally
      //   246	295	2116	finally
      //   295	302	2116	finally
      //   305	371	2116	finally
      //   371	387	2116	finally
      //   392	414	2116	finally
      //   428	438	2116	finally
      //   441	513	2116	finally
      //   584	602	2116	finally
      //   613	635	2116	finally
      //   635	676	2116	finally
      //   676	702	2116	finally
      //   711	760	2116	finally
      //   978	1024	2116	finally
      //   1109	1195	2116	finally
      //   1275	1365	2116	finally
      //   1365	1430	2116	finally
      //   1924	1940	2116	finally
      //   562	573	2121	android/os/RemoteException
      //   67	226	2133	android/os/RemoteException
      //   226	238	2133	android/os/RemoteException
      //   246	295	2141	android/os/RemoteException
      //   295	302	2141	android/os/RemoteException
      //   305	371	2141	android/os/RemoteException
      //   371	387	2141	android/os/RemoteException
      //   392	414	2141	android/os/RemoteException
      //   428	438	2141	android/os/RemoteException
      //   441	513	2141	android/os/RemoteException
      //   584	602	2141	android/os/RemoteException
      //   613	635	2141	android/os/RemoteException
      //   635	676	2141	android/os/RemoteException
      //   676	702	2141	android/os/RemoteException
      //   711	760	2141	android/os/RemoteException
      //   978	1024	2141	android/os/RemoteException
      //   1109	1195	2141	android/os/RemoteException
      //   1275	1365	2141	android/os/RemoteException
      //   1365	1430	2141	android/os/RemoteException
      //   0	63	2146	java/io/IOException
      //   534	562	2146	java/io/IOException
      //   562	573	2158	java/io/IOException
      //   67	226	2173	java/io/IOException
      //   226	238	2173	java/io/IOException
    }

    public void a(int paramInt)
    {
      this.g = paramInt;
    }

    public void run()
    {
      this.d = 0;
      try
      {
        if (this.i != null)
          this.i.a(this.h);
        boolean bool1 = this.e < 0L;
        boolean bool2 = false;
        if (bool1)
          bool2 = true;
        a(bool2);
        if (DownloadingService.b().size() <= 0)
          DownloadingService.this.stopSelf();
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  class c extends Handler
  {
    c()
    {
    }

    public void handleMessage(Message paramMessage)
    {
      Log.c(DownloadingService.a(), "IncomingHandler(msg.what:" + paramMessage.what + " msg.arg1:" + paramMessage.arg1 + " msg.arg2:" + paramMessage.arg2 + " msg.replyTo:" + paramMessage.replyTo);
      switch (paramMessage.what)
      {
      default:
        super.handleMessage(paramMessage);
        return;
      case 4:
      }
      Bundle localBundle = paramMessage.getData();
      Log.c(DownloadingService.a(), "IncomingHandler(msg.getData():" + localBundle);
      a.a locala = a.a.a(localBundle);
      if (DownloadingService.b(locala))
      {
        Log.a(DownloadingService.a(), locala.b + " is already in downloading list. ");
        Toast.makeText(DownloadingService.a(DownloadingService.this), com.umeng.common.c.a(DownloadingService.a(DownloadingService.this)).f("umeng_common_action_info_exist"), 0).show();
        return;
      }
      DownloadingService.b().put(locala, paramMessage.replyTo);
      DownloadingService.a(DownloadingService.this, locala);
    }
  }

  private static class d
  {
    DownloadingService.b a;
    Notification b;
    int c;
    int d;
    a.a e;
    long[] f = new long[3];

    public d(a.a parama, int paramInt)
    {
      this.c = paramInt;
      this.e = parama;
    }

    public void a()
    {
      DownloadingService.c().put(Integer.valueOf(this.c), this);
    }

    public void b()
    {
      if (DownloadingService.c().containsKey(Integer.valueOf(this.c)))
        DownloadingService.c().remove(Integer.valueOf(this.c));
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.umeng.common.net.DownloadingService
 * JD-Core Version:    0.6.0
 */