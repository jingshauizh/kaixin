package com.kaixin001.activity;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.TextUtils;
import android.widget.RemoteViews;
import com.kaixin001.engine.NewsEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.UpdateEngine;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.model.NewsModel;
import com.kaixin001.model.User;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.ImageDownloader;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

public class KaixinAppWidgetProvider extends AppWidgetProvider
{
  public static final String ACTION_WIDGET_CHANGE_USER = "com.kaixin001.WIDGET_CHANGE_USER";
  public static final String ACTION_WIDGET_UPDATE = "com.kaixin001.WIDGET_UPDATE";
  public static final String ACTION_WIDGET_UPDATE_INTERVAL = "com.kaixin001.WIDGET_UPDATE_INTERVAL";
  public static final String ACTION_WIDGET_VIEW_NEWS_NEXT = "com.kaixin001.WIDGET_VIEW_NEWS_NEXT";
  public static final String ACTION_WIDGET_VIEW_NEWS_PRE = "com.kaixin001.WIDGET_VIEW_NEWS_PRE";
  public static final String MODE_KEY = "mode";
  static final ComponentName THIS_APPWIDGET = new ComponentName("com.kaixin001.activity", "com.kaixin001.activity.KaixinAppWidgetProvider");
  private static final int UPDATE_NEWS_FAIL = 221;
  private static final int UPDATE_NEWS_SUCCEED = 211;
  private static int mActiveNewsIndex = 0;
  private static volatile Timer mBrowseTimer;
  private static volatile Context mContext;
  private static KaixinAppWidgetProvider mInstance;
  private static volatile Timer mRefreshTimer;
  private static long mStopBrowseTime;
  private static volatile UpdateNewsThread mThread = null;
  private static long mWidgetUpdateInterval;
  private String E_SYMBOL = "[|e|]";
  private String M_SYMBOL = "[|m|]";
  private String S_SYMBOL = "[|s|]";
  ReentrantLock lock = new ReentrantLock();
  private TimerTask mBrowseTask = null;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      case 221:
      default:
      case 211:
      }
      do
        return;
      while (KaixinAppWidgetProvider.mContext == null);
      String str1 = User.getInstance().getUID();
      String str2 = User.getInstance().getOauthToken();
      if ((!TextUtils.isEmpty(str1)) && (!TextUtils.isEmpty(str2)))
      {
        KaixinAppWidgetProvider.this.updateAllViews(KaixinAppWidgetProvider.mContext, null, KaixinAppWidgetProvider.mActiveNewsIndex);
        return;
      }
      int[] arrayOfInt = AppWidgetManager.getInstance(KaixinAppWidgetProvider.mContext).getAppWidgetIds(KaixinAppWidgetProvider.THIS_APPWIDGET);
      for (int i = 0; ; i++)
      {
        if (i >= arrayOfInt.length)
        {
          KaixinAppWidgetProvider.mActiveNewsIndex = 0;
          return;
        }
        KaixinAppWidgetProvider.this.showDefaultViews(0, KaixinAppWidgetProvider.mContext, arrayOfInt[i]);
      }
    }
  };
  private ImageDownloader mImgDownloader = null;
  private TimerTask mRefreshTask = null;

  static
  {
    mContext = null;
    mBrowseTimer = null;
    mRefreshTimer = null;
    mStopBrowseTime = 0L;
    mWidgetUpdateInterval = 1800000L;
  }

  public static KaixinAppWidgetProvider getInstance()
  {
    monitorenter;
    try
    {
      if (mInstance == null)
        mInstance = new KaixinAppWidgetProvider();
      KaixinAppWidgetProvider localKaixinAppWidgetProvider = mInstance;
      return localKaixinAppWidgetProvider;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void linkButtons(Context paramContext, RemoteViews paramRemoteViews, int paramInt1, int paramInt2)
  {
    paramRemoteViews.setOnClickPendingIntent(2131362891, PendingIntent.getBroadcast(paramContext, 0, new Intent("com.kaixin001.WIDGET_VIEW_NEWS_NEXT"), 0));
    paramRemoteViews.setOnClickPendingIntent(2131362889, PendingIntent.getBroadcast(paramContext, 0, new Intent("com.kaixin001.WIDGET_VIEW_NEWS_PRE"), 0));
    Intent localIntent = new Intent(paramContext, MainActivity.class);
    Bundle localBundle = new Bundle();
    localBundle.putBoolean("updatestate", true);
    localBundle.putInt("mode", 16);
    localBundle.putBoolean("from_widget", true);
    localIntent.putExtras(localBundle);
    PendingIntent localPendingIntent = PendingIntent.getActivity(paramContext, 0, localIntent, 0);
    paramRemoteViews.setOnClickPendingIntent(2131362880, localPendingIntent);
    paramRemoteViews.setOnClickPendingIntent(2131362881, localPendingIntent);
  }

  private void pushUpdate(Context paramContext, int paramInt, RemoteViews paramRemoteViews)
  {
    AppWidgetManager.getInstance(paramContext).updateAppWidget(paramInt, paramRemoteViews);
  }

  private void setBrowseIntervalTimer()
  {
    monitorenter;
    try
    {
      if (mBrowseTimer != null)
      {
        mBrowseTimer.cancel();
        mBrowseTimer = null;
      }
      if (this.mBrowseTask != null)
      {
        this.mBrowseTask.cancel();
        this.mBrowseTask = null;
      }
      mBrowseTimer = new Timer();
      this.mBrowseTask = new TimerTask()
      {
        public void run()
        {
          try
          {
            String str1 = User.getInstance().getUID();
            String str2 = User.getInstance().getOauthToken();
            if ((TextUtils.isEmpty(str1)) || (TextUtils.isEmpty(str2)))
            {
              if (!User.getInstance().loadUserData(KaixinAppWidgetProvider.mContext))
                return;
              if (TextUtils.isEmpty(User.getInstance().getUID()))
                return;
              NewsEngine.getInstance().loadNewsCache(KaixinAppWidgetProvider.mContext, User.getInstance().getUID());
            }
            if ((TextUtils.isEmpty(User.getInstance().getOauthToken())) || (NewsModel.getInstance().newsListAll == null) || (NewsModel.getInstance().newsListAll.size() == 0) || (System.currentTimeMillis() - KaixinAppWidgetProvider.mStopBrowseTime <= 90000L))
              return;
            if (KaixinAppWidgetProvider.mActiveNewsIndex < -1 + NewsModel.getInstance().newsListAll.size())
            {
              KaixinAppWidgetProvider.mActiveNewsIndex = 1 + KaixinAppWidgetProvider.mActiveNewsIndex;
              KaixinAppWidgetProvider.this.updateAllViews(KaixinAppWidgetProvider.mContext, null, KaixinAppWidgetProvider.mActiveNewsIndex);
              return;
            }
          }
          catch (Exception localException)
          {
            KXLog.e("KaixinAppWidgetProvider", "run", localException);
            return;
          }
          if (KaixinAppWidgetProvider.mActiveNewsIndex == -1 + NewsModel.getInstance().newsListAll.size())
          {
            KaixinAppWidgetProvider.mActiveNewsIndex = 0;
            KaixinAppWidgetProvider.this.updateAllViews(KaixinAppWidgetProvider.mContext, null, KaixinAppWidgetProvider.mActiveNewsIndex);
          }
        }
      };
      mBrowseTimer.schedule(this.mBrowseTask, 5000L, 10000L);
      return;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void setClickImageAction(Context paramContext, RemoteViews paramRemoteViews, int paramInt)
  {
    try
    {
      ArrayList localArrayList = NewsModel.getInstance().newsListAll;
      if ((localArrayList != null) && (paramInt < localArrayList.size()))
      {
        NewsInfo localNewsInfo = (NewsInfo)localArrayList.get(paramInt);
        String str1 = localNewsInfo.mFuid;
        String str2 = localNewsInfo.mFname;
        String str3 = localNewsInfo.mFlogo;
        if ((str1 != null) && (str1.equalsIgnoreCase(User.getInstance().getUID())));
        for (Intent localIntent = new Intent("com.kaixin001.VIEW_HOME_DETAIL"); ; localIntent = new Intent(paramContext, MainActivity.class))
        {
          localIntent.setFlags(67108864);
          Bundle localBundle = new Bundle();
          localBundle.putString("fuid", str1);
          localBundle.putString("fname", str2);
          localBundle.putString("flogo", str3);
          localBundle.putBoolean("from_widget", true);
          localIntent.putExtras(localBundle);
          paramRemoteViews.setOnClickPendingIntent(2131362883, PendingIntent.getActivity(paramContext, 0, localIntent, 134217728));
          return;
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private static void setContext(Context paramContext)
  {
    mContext = paramContext;
  }

  private void setRemoteViewsContent(RemoteViews paramRemoteViews, String paramString)
  {
    if (TextUtils.isEmpty(paramString))
    {
      paramRemoteViews.setTextViewText(2131362886, Html.fromHtml("<font color=\"#576b95\">[图片]</font>"));
      return;
    }
    int i = paramString.indexOf(this.S_SYMBOL, 0);
    if (i != -1)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      int j = 0;
      if (i == -1);
      while (true)
        while (true)
        {
          paramRemoteViews.setTextViewText(2131362886, Html.fromHtml(localStringBuffer.toString()));
          return;
          if (i > j);
          try
          {
            localStringBuffer.append(paramString.substring(j, i));
            int k = paramString.indexOf(this.M_SYMBOL, i);
            if (k == -1)
              continue;
            localStringBuffer.append("<font color=\"#576b95\">");
            localStringBuffer.append(paramString.substring(i + this.S_SYMBOL.length(), k));
            localStringBuffer.append("</font>");
            int m = paramString.indexOf(this.E_SYMBOL, k);
            if (m == -1)
              continue;
            j = m + this.E_SYMBOL.length();
            i = paramString.indexOf(this.S_SYMBOL, j);
            if (-1 != i)
              break;
            localStringBuffer.append(paramString.substring(j));
          }
          catch (Exception localException)
          {
          }
        }
    }
    paramRemoteViews.setTextViewText(2131362886, paramString);
  }

  private void setRemoteViewsIndex(RemoteViews paramRemoteViews, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(paramInt1).append("/").append(paramInt2);
    paramRemoteViews.setTextViewText(2131362890, localStringBuffer);
  }

  private void setRemoteViewsTitle(RemoteViews paramRemoteViews, String paramString, int paramInt)
  {
    int i = 0;
    int j = paramString.indexOf(this.S_SYMBOL, 0);
    if (j != -1)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      if (j == -1);
      while (true)
        while (true)
        {
          paramRemoteViews.setTextViewText(2131362885, Html.fromHtml(localStringBuffer.toString()));
          return;
          if (j > i);
          try
          {
            localStringBuffer.append(paramString.substring(i, j));
            int k = paramString.indexOf(this.M_SYMBOL, j);
            if (k == -1)
              continue;
            localStringBuffer.append("<font color=\"#576b95\">");
            localStringBuffer.append(paramString.substring(j + this.S_SYMBOL.length(), k));
            localStringBuffer.append("</font>");
            int m = paramString.indexOf(this.E_SYMBOL, k);
            if (m == -1)
              continue;
            i = m + this.E_SYMBOL.length();
            j = paramString.indexOf(this.S_SYMBOL, i);
            if (-1 != j)
              break;
            localStringBuffer.append(paramString.substring(i));
          }
          catch (Exception localException)
          {
          }
        }
    }
    paramRemoteViews.setTextViewText(2131362885, paramString);
  }

  private void setUpdateIntervalTimer()
  {
    monitorenter;
    try
    {
      if (mRefreshTimer != null)
      {
        mRefreshTimer.cancel();
        mRefreshTimer = null;
      }
      if (this.mRefreshTask != null)
      {
        this.mRefreshTask.cancel();
        this.mRefreshTask = null;
      }
      mRefreshTimer = new Timer();
      this.mRefreshTask = new TimerTask()
      {
        public void run()
        {
          AppWidgetManager localAppWidgetManager = AppWidgetManager.getInstance(KaixinAppWidgetProvider.mContext);
          if (localAppWidgetManager != null)
          {
            int[] arrayOfInt = localAppWidgetManager.getAppWidgetIds(KaixinAppWidgetProvider.THIS_APPWIDGET);
            if (arrayOfInt != null)
              KaixinAppWidgetProvider.this.onUpdate(KaixinAppWidgetProvider.mContext, localAppWidgetManager, arrayOfInt);
          }
        }
      };
      mRefreshTimer.schedule(this.mRefreshTask, 0L, mWidgetUpdateInterval);
      return;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void showDefaultViews(int paramInt1, Context paramContext, int paramInt2)
  {
    RemoteViews localRemoteViews = new RemoteViews(THIS_APPWIDGET.getPackageName(), 2130903180);
    if (paramInt1 == 1)
    {
      localRemoteViews.setViewVisibility(2131362876, 8);
      localRemoteViews.setViewVisibility(2131362884, 0);
      localRemoteViews.setViewVisibility(2131362888, 0);
      localRemoteViews.setViewVisibility(2131362877, 0);
      localRemoteViews.setViewVisibility(2131362882, 0);
      localRemoteViews.setViewVisibility(2131362880, 0);
      localRemoteViews.setViewVisibility(2131362881, 0);
      PendingIntent localPendingIntent2 = PendingIntent.getActivity(paramContext, 0, new Intent(paramContext, StartActivity.class), 0);
      localRemoteViews.setOnClickPendingIntent(2131362884, localPendingIntent2);
      localRemoteViews.setOnClickPendingIntent(2131362878, localPendingIntent2);
      setClickImageAction(paramContext, localRemoteViews, mActiveNewsIndex);
    }
    while (true)
    {
      pushUpdate(paramContext, paramInt2, localRemoteViews);
      return;
      localRemoteViews.setViewVisibility(2131362876, 0);
      localRemoteViews.setViewVisibility(2131362884, 8);
      localRemoteViews.setViewVisibility(2131362888, 8);
      localRemoteViews.setViewVisibility(2131362877, 0);
      localRemoteViews.setViewVisibility(2131362882, 8);
      localRemoteViews.setViewVisibility(2131362880, 8);
      localRemoteViews.setViewVisibility(2131362881, 8);
      Intent localIntent = new Intent(paramContext, StartActivity.class);
      localIntent.setAction("android.intent.action.MAIN");
      localIntent.addCategory("android.intent.category.LAUNCHER");
      PendingIntent localPendingIntent1 = PendingIntent.getActivity(paramContext, 0, localIntent, 0);
      localRemoteViews.setOnClickPendingIntent(2131362876, localPendingIntent1);
      localRemoteViews.setOnClickPendingIntent(2131362878, localPendingIntent1);
    }
  }

  private void showNewsView(Context paramContext, int paramInt1, int paramInt2)
  {
    RemoteViews localRemoteViews = new RemoteViews(THIS_APPWIDGET.getPackageName(), 2130903180);
    localRemoteViews.setViewVisibility(2131362876, 8);
    localRemoteViews.setViewVisibility(2131362884, 0);
    localRemoteViews.setViewVisibility(2131362888, 0);
    localRemoteViews.setViewVisibility(2131362877, 0);
    localRemoteViews.setViewVisibility(2131362882, 0);
    localRemoteViews.setViewVisibility(2131362880, 0);
    localRemoteViews.setViewVisibility(2131362881, 0);
    Intent localIntent = new Intent("com.kaixin001.VIEW_NEWS");
    localIntent.putExtra("from_widget", true);
    localIntent.setFlags(67108864);
    PendingIntent localPendingIntent = PendingIntent.getActivity(paramContext, 0, localIntent, 0);
    localRemoteViews.setOnClickPendingIntent(2131362884, localPendingIntent);
    localRemoteViews.setOnClickPendingIntent(2131362878, localPendingIntent);
    setClickImageAction(paramContext, localRemoteViews, paramInt2);
    try
    {
      ArrayList localArrayList = NewsModel.getInstance().newsListAll;
      if (localArrayList != null)
      {
        int i = localArrayList.size();
        if (paramInt2 >= i)
        {
          paramInt2 = 0;
          mActiveNewsIndex = 0;
        }
      }
      NewsInfo localNewsInfo;
      String str2;
      String str3;
      String str4;
      String str5;
      if (localArrayList != null)
      {
        int j = localArrayList.size();
        if (paramInt2 < j)
        {
          localNewsInfo = (NewsInfo)localArrayList.get(paramInt2);
          String str1 = localNewsInfo.mTitle;
          str2 = localNewsInfo.mIntro;
          str3 = localNewsInfo.mStime;
          str4 = this.S_SYMBOL + localNewsInfo.mFname + this.M_SYMBOL + localNewsInfo.mFuid + this.M_SYMBOL;
          if ((TextUtils.isEmpty(str1)) || (str1.startsWith(str4)))
            break label437;
          str5 = str4 + "0" + this.E_SYMBOL + ": " + str1;
          if (TextUtils.isEmpty(str5))
            break label470;
          setRemoteViewsTitle(localRemoteViews, str5, 1);
          setRemoteViewsContent(localRemoteViews, str2);
          setRemoteViewsIndex(localRemoteViews, paramInt2 + 1, localArrayList.size());
        }
      }
      while (true)
      {
        localRemoteViews.setTextViewText(2131362887, str3);
        str6 = localNewsInfo.mFlogo;
        Bitmap localBitmap = ImageCache.getInstance().createSafeImage(str6);
        if (localBitmap == null)
          break label515;
        localRemoteViews.setImageViewBitmap(2131362883, localBitmap);
        linkButtons(paramContext, localRemoteViews, paramInt2, paramInt1);
        pushUpdate(paramContext, paramInt1, localRemoteViews);
        return;
        label437: str5 = str4 + "0" + this.E_SYMBOL;
        break;
        label470: setRemoteViewsTitle(localRemoteViews, str2, 3);
        setRemoteViewsContent(localRemoteViews, "");
        setRemoteViewsIndex(localRemoteViews, paramInt2 + 1, localArrayList.size());
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        String str6;
        localException.printStackTrace();
        continue;
        label515: localRemoteViews.setImageViewResource(2131362883, 2130837561);
        new GetLogoImageTask().execute(new String[] { str6 });
      }
    }
  }

  private void updateAllViews(Context paramContext, int[] paramArrayOfInt, int paramInt)
  {
    if (paramArrayOfInt == null)
      paramArrayOfInt = AppWidgetManager.getInstance(paramContext).getAppWidgetIds(THIS_APPWIDGET);
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfInt.length)
        return;
      showNewsView(paramContext, paramArrayOfInt[i], paramInt);
    }
  }

  private void updateNews(Context paramContext, int paramInt, String paramString)
  {
    try
    {
      this.lock.lock();
      if (mThread != null)
        mThread.setStop();
      mThread = new UpdateNewsThread(paramContext, paramInt, paramString);
      mThread.start();
      return;
    }
    finally
    {
      this.lock.unlock();
    }
    throw localObject;
  }

  public void onDeleted(Context paramContext, int[] paramArrayOfInt)
  {
    super.onDeleted(paramContext, paramArrayOfInt);
    int[] arrayOfInt = AppWidgetManager.getInstance(paramContext).getAppWidgetIds(THIS_APPWIDGET);
    if ((arrayOfInt == null) || (arrayOfInt.length == 0))
      if (this.mImgDownloader != null)
      {
        this.mImgDownloader.clear();
        this.mImgDownloader = null;
      }
    try
    {
      this.lock.lock();
      if (mThread != null)
        mThread.setStop();
      mThread = null;
      this.lock.unlock();
      mActiveNewsIndex = 0;
      UpdateEngine.getInstance().cancel();
      UpdateEngine.getInstance().clearContext();
      return;
    }
    finally
    {
      this.lock.unlock();
    }
    throw localObject;
  }

  public void onDisabled(Context paramContext)
  {
    super.onDisabled(paramContext);
    int[] arrayOfInt = AppWidgetManager.getInstance(paramContext).getAppWidgetIds(THIS_APPWIDGET);
    if ((arrayOfInt == null) || (arrayOfInt.length == 0))
      if (this.mImgDownloader != null)
      {
        this.mImgDownloader.clear();
        this.mImgDownloader = null;
      }
    try
    {
      this.lock.lock();
      if (mThread != null)
        mThread.setStop();
      mThread = null;
      this.lock.unlock();
      mActiveNewsIndex = 0;
      return;
    }
    finally
    {
      this.lock.unlock();
    }
    throw localObject;
  }

  public void onEnabled(Context paramContext)
  {
    super.onEnabled(paramContext);
    if (this.mImgDownloader != null)
      this.mImgDownloader.setContext(paramContext.getApplicationContext());
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    String str1 = paramIntent.getAction();
    setContext(paramContext);
    if (str1.equals("com.kaixin001.WIDGET_VIEW_NEWS_NEXT"))
      if (mActiveNewsIndex < -1 + NewsModel.getInstance().newsListAll.size())
        mActiveNewsIndex = 1 + mActiveNewsIndex;
    while (true)
    {
      break label58;
      while (true)
      {
        updateAllViews(paramContext, null, mActiveNewsIndex);
        mStopBrowseTime = System.currentTimeMillis();
        label58: return;
        if (mActiveNewsIndex != -1 + NewsModel.getInstance().newsListAll.size())
          continue;
        mActiveNewsIndex = 0;
      }
      if (str1.equals("com.kaixin001.WIDGET_VIEW_NEWS_PRE"))
      {
        if (mActiveNewsIndex > 0)
          mActiveNewsIndex = -1 + mActiveNewsIndex;
        while (true)
        {
          updateAllViews(paramContext, null, mActiveNewsIndex);
          mStopBrowseTime = System.currentTimeMillis();
          return;
          if (mActiveNewsIndex != 0)
            continue;
          mActiveNewsIndex = -1 + NewsModel.getInstance().newsListAll.size();
        }
      }
      if (str1.equals("com.kaixin001.WIDGET_UPDATE"))
      {
        String str4 = User.getInstance().getUID();
        String str5 = User.getInstance().getOauthToken();
        if ((!TextUtils.isEmpty(str4)) && (!TextUtils.isEmpty(str5)))
        {
          updateAllViews(paramContext, null, mActiveNewsIndex);
          return;
        }
        int[] arrayOfInt2 = AppWidgetManager.getInstance(paramContext).getAppWidgetIds(THIS_APPWIDGET);
        for (int j = 0; ; j++)
        {
          if (j >= arrayOfInt2.length)
          {
            mActiveNewsIndex = 0;
            return;
          }
          showDefaultViews(0, paramContext, arrayOfInt2[j]);
        }
      }
      if (str1.equals("com.kaixin001.WIDGET_CHANGE_USER"))
      {
        int[] arrayOfInt1 = AppWidgetManager.getInstance(paramContext).getAppWidgetIds(THIS_APPWIDGET);
        if (arrayOfInt1.length == 0)
          continue;
        String str2 = User.getInstance().getUID();
        String str3 = User.getInstance().getOauthToken();
        if ((!TextUtils.isEmpty(str2)) && (!TextUtils.isEmpty(str3)))
        {
          mActiveNewsIndex = 0;
          updateNews(paramContext, 1, str2);
          return;
        }
        if (mBrowseTimer != null)
        {
          mBrowseTimer.cancel();
          mBrowseTimer = null;
        }
        if (this.mBrowseTask != null)
        {
          this.mBrowseTask.cancel();
          this.mBrowseTask = null;
        }
        for (int i = 0; ; i++)
        {
          if (i >= arrayOfInt1.length)
          {
            mActiveNewsIndex = 0;
            return;
          }
          showDefaultViews(0, paramContext, arrayOfInt1[i]);
        }
      }
      if (!str1.equals("com.kaixin001.WIDGET_UPDATE_INTERVAL"))
        break;
      SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
      if (localSharedPreferences.getBoolean("check_widget_refresh_interval", true))
      {
        long l = Long.parseLong(localSharedPreferences.getString("widget_refresh_interval", "1800000"));
        if ((l == mWidgetUpdateInterval) && (mRefreshTimer != null))
          continue;
        mWidgetUpdateInterval = l;
        setUpdateIntervalTimer();
        return;
      }
      if (mRefreshTimer != null)
      {
        mRefreshTimer.cancel();
        mRefreshTimer = null;
      }
      if (this.mRefreshTask == null)
        continue;
      this.mRefreshTask.cancel();
      return;
    }
    if (this.mImgDownloader == null)
      this.mImgDownloader = ImageDownloader.getInstance();
    this.mImgDownloader.setContext(mContext.getApplicationContext());
    setBrowseIntervalTimer();
    super.onReceive(paramContext, paramIntent);
  }

  public void onUpdate(Context paramContext, AppWidgetManager paramAppWidgetManager, int[] paramArrayOfInt)
  {
    super.onUpdate(paramContext, paramAppWidgetManager, paramArrayOfInt);
    setContext(paramContext);
    if (this.mImgDownloader == null)
      this.mImgDownloader = ImageDownloader.getInstance();
    this.mImgDownloader.setContext(mContext.getApplicationContext());
    String str1 = User.getInstance().getUID();
    User.getInstance().getOauthToken();
    if (str1 == null)
      User.getInstance().loadUserData(paramContext);
    String str2 = User.getInstance().getUID();
    String str3 = User.getInstance().getOauthToken();
    if ((!TextUtils.isEmpty(str2)) && (!TextUtils.isEmpty(str3)))
    {
      if (NewsModel.getInstance().getTotalNum("") > 0)
        updateAllViews(paramContext, paramArrayOfInt, mActiveNewsIndex);
      while (true)
      {
        updateNews(paramContext, 1, str2);
        return;
        for (int j = 0; j < paramArrayOfInt.length; j++)
          showDefaultViews(1, paramContext, paramArrayOfInt[j]);
      }
    }
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfInt.length)
      {
        mActiveNewsIndex = 0;
        return;
      }
      showDefaultViews(0, paramContext, paramArrayOfInt[i]);
    }
  }

  public class GetLogoImageTask extends AsyncTask<String, Void, Boolean>
  {
    public GetLogoImageTask()
    {
    }

    protected Boolean doInBackground(String[] paramArrayOfString)
    {
      if (KaixinAppWidgetProvider.this.mImgDownloader == null)
        KaixinAppWidgetProvider.this.mImgDownloader = ImageDownloader.getInstance();
      return Boolean.valueOf(KaixinAppWidgetProvider.this.mImgDownloader.downloadImageSync(paramArrayOfString[0]));
    }

    protected void onPostExecute(Boolean paramBoolean)
    {
      if ((paramBoolean.booleanValue()) && (KaixinAppWidgetProvider.mContext != null))
        KaixinAppWidgetProvider.this.updateAllViews(KaixinAppWidgetProvider.mContext, null, KaixinAppWidgetProvider.mActiveNewsIndex);
      super.onPostExecute(paramBoolean);
    }
  }

  private class UpdateNewsThread extends Thread
  {
    private int mMode = 0;
    private boolean mStop = false;
    Context mThreadContext = null;
    private String mUid = null;

    public UpdateNewsThread(Context paramInt, int paramString, String arg4)
    {
      this.mMode = paramString;
      this.mThreadContext = paramInt;
      Object localObject;
      this.mUid = localObject;
    }

    public void run()
    {
      if (this.mStop)
        return;
      while (true)
      {
        Message localMessage2;
        try
        {
          if (this.mMode != 0)
            continue;
          boolean bool3 = NewsEngine.getInstance().loadNewsCache(this.mThreadContext, this.mUid);
          boolean bool2 = bool3;
          this.mThreadContext = null;
          if (this.mStop)
            break;
          localMessage2 = Message.obtain();
          if (!bool2)
            break label156;
          localMessage2.what = 211;
          KaixinAppWidgetProvider.this.mHandler.sendMessage(localMessage2);
          super.run();
          return;
          boolean bool1 = NewsEngine.getInstance().getNewsData(this.mThreadContext, NewsModel.getInstance(), "0", String.valueOf(20), "", "", "", "");
          bool2 = bool1;
          continue;
        }
        catch (SecurityErrorException localSecurityErrorException)
        {
        }
        if ((localSecurityErrorException.errorNumber != -22) || (localSecurityErrorException.ret != -22))
          break;
        Message localMessage1 = Message.obtain();
        localMessage1.what = 10000;
        MessageHandlerHolder.getInstance().fireMessage(localMessage1);
        return;
        label156: localMessage2.what = 221;
      }
    }

    public void setStop()
    {
      this.mStop = true;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.KaixinAppWidgetProvider
 * JD-Core Version:    0.6.0
 */