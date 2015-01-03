package com.kaixin001.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.kaixin001.db.PicMd5DBAdapter;
import com.kaixin001.engine.CloudAlbumEngine;
import com.kaixin001.item.ChatInfoItem.ChatMsg;
import com.kaixin001.item.CloudAlbumPicItem;
import com.kaixin001.model.CloudAlbumModel;
import com.kaixin001.model.KaixinConst;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProgressListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class CloudAlbumManager
  implements Runnable
{
  private static final long DEFAULT_SCANN_INTERVAL = 36000L;
  private static final String LOG = "CloudAlbumManager";
  private static final long NOTICE_SYNC_PIC_NUM = 10L;
  public static final int SYNC_CONTENT_ALL = 2;
  public static final int SYNC_CONTENT_RECENT = 1;
  public static final int SYNC_CONTENT_RECENT_NUM = 10;
  public static final int SYNC_PER_NUM = 90;
  public static final boolean TEST_MODE;
  private static final CloudAlbumManager instance;
  public static boolean mWifiEnable = false;
  private Context ctx;
  private volatile File currentUploadingImage = null;
  private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
  private ScheduledFuture<?> future = null;
  private ArrayList<Handler> handlers = new ArrayList();
  private CloudAlbumDataListener mCloudAlbumDataListener;
  private HashMap<String, String> mPicMd5Map = new HashMap();
  private int mSyncContentType = 1;
  private boolean mSyncOpen = false;
  private WifiStateChangeListener mWifiStateChangeListener;

  static
  {
    instance = new CloudAlbumManager();
  }

  private boolean canBackgroundUpload()
  {
    KXLog.d("CloudAlbumManager", "canUpload() mSyncContentType：" + this.mSyncContentType + ", mSyncNetworkType：" + this.mSyncOpen);
    boolean bool = registerState(this.ctx);
    if (!bool)
    {
      bool = getInstance().registerState(this.ctx);
      if (bool)
        setRegisterState(this.ctx, bool);
    }
    return (bool) && (this.mSyncOpen) && (ConnectivityUtil.isWifiConnected(this.ctx));
  }

  public static CloudAlbumManager getInstance()
  {
    return instance;
  }

  private void loadMd5sFromDB()
  {
    KXLog.d("CloudAlbumManager", "loadMd5sFromDB start()...");
    Cursor localCursor = null;
    PicMd5DBAdapter localPicMd5DBAdapter = new PicMd5DBAdapter(this.ctx);
    try
    {
      localCursor = localPicMd5DBAdapter.getMd5s();
      if (localCursor != null)
      {
        boolean bool1 = localCursor.moveToFirst();
        if (bool1);
      }
      else
      {
        return;
      }
      KXLog.d("CloudAlbumManager", "db md5s size:" + localCursor.getCount());
      while (true)
      {
        boolean bool2 = localCursor.moveToNext();
        if (!bool2)
          return;
        String str1 = localCursor.getString(0);
        String str2 = localCursor.getString(1);
        this.mPicMd5Map.put(str1, str2);
      }
    }
    catch (Exception localException)
    {
      KXLog.d("CloudAlbumManager", "loadMd5sFromDB exception:" + localException);
      localException.printStackTrace();
      return;
    }
    finally
    {
      KXLog.d("CloudAlbumManager", "loadMd5sFromDB end!");
      if (localCursor != null)
        localCursor.close();
      if (localPicMd5DBAdapter != null)
        localPicMd5DBAdapter.close();
    }
    throw localObject;
  }

  public static void sendSyncPicNumNotification(Context paramContext, int paramInt)
  {
    if (paramContext != null)
    {
      KXLog.d("CloudAlbumManager", "sendSyncPicNumNotification:" + paramInt);
      SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
      boolean bool1 = localSharedPreferences.getBoolean("notification_vibrate_preference", true);
      boolean bool2 = localSharedPreferences.getBoolean("notification_led_preference", true);
      boolean bool3 = localSharedPreferences.getBoolean("notification_ringtone_enabled_preference", true);
      NotificationManager localNotificationManager = (NotificationManager)paramContext.getSystemService("notification");
      Notification localNotification = new Notification();
      Intent localIntent = new Intent("com.kaixin001.VIEW_CLOUD_ALBUM");
      localIntent.addFlags(67108864);
      PendingIntent localPendingIntent = PendingIntent.getActivity(paramContext, 0, localIntent, 0);
      localNotification.icon = 2130838989;
      String str1 = paramContext.getResources().getString(2131427329);
      String str2 = StringUtil.replaceTokenWith(paramContext.getResources().getString(2131428521), "*", String.valueOf(paramInt));
      localNotification.tickerText = (str1 + ":" + str2);
      localNotification.setLatestEventInfo(paramContext, str1, str2, localPendingIntent);
      if (bool1)
        localNotification.defaults = (0x2 | localNotification.defaults);
      if (bool2)
        localNotification.defaults = (0x4 | localNotification.defaults);
      if (bool3)
        localNotification.sound = Uri.parse("android.resource://com.kaixin001.activity/2131099652");
      localNotificationManager.notify(KaixinConst.ID_VIEW_CLOUDALBUM, localNotification);
    }
  }

  private boolean uploadImage(File paramFile, String paramString)
  {
    KXLog.d("CloudAlbumManager", "uploadImage() start..." + paramFile.getAbsolutePath());
    CloudAlbumEngine localCloudAlbumEngine = CloudAlbumEngine.getInstance();
    String str1 = null;
    if (paramString != null)
      str1 = (String)this.mPicMd5Map.get(paramString);
    if (TextUtils.isEmpty(str1))
      str1 = StringUtil.byteArrayToHexString(FileUtil.md5(paramFile));
    int i = CloudAlbumModel.getInstance().getStatus(str1);
    if (i == 4);
    while (true)
    {
      return true;
      if ((i != 1) && (i != 0) && (localCloudAlbumEngine.checkFileExists(this.ctx, str1) == 1))
        i = CloudAlbumModel.getInstance().getStatus(str1);
      if (i != 1)
        break;
      CloudAlbumPicItem localCloudAlbumPicItem = CloudAlbumModel.getInstance().getItem(str1);
      if (localCloudAlbumPicItem == null)
        continue;
      localCloudAlbumPicItem.mState = 1;
      return true;
    }
    try
    {
      this.currentUploadingImage = paramFile;
      String str2 = paramFile.getName();
      if ((str2 == null) || (str2.lastIndexOf('.') == -1))
      {
        if (localCloudAlbumEngine.postFile(this.ctx, paramFile, str2, new ProgressListener(str1)) != 1)
          break label277;
        UserHabitUtils.getInstance(this.ctx).addUserHabit("cloudalbum_upload_pic");
        showSyncPicNumNotice(this.ctx);
        CloudAlbumModel.getInstance().addPicStatus(str1, 1);
      }
      for (bool = true; ; bool = false)
      {
        KXLog.d("CloudAlbumManager", "uploadImage() end! result:" + bool);
        return bool;
        str2 = str2.substring(0, str2.lastIndexOf('.'));
        break;
        label277: sendFailedMsg(str1);
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        sendFailedMsg(str1);
        boolean bool = false;
      }
    }
  }

  public void addHandler(Handler paramHandler)
  {
    monitorenter;
    try
    {
      this.handlers.add(paramHandler);
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public void addIgnoreFile(Context paramContext, String paramString)
  {
    CloudAlbumModel.getInstance().addPicStatus(paramString, 4);
    CloudAlbumModel.getInstance().getIgnoreFileList().put(paramString, "");
    saveIgnoreFileList(paramContext, CloudAlbumModel.getInstance().getIgnoreFileList());
    if (this.mCloudAlbumDataListener != null)
      this.mCloudAlbumDataListener.onPicDelete(paramString);
  }

  public void calculateMD5(ArrayList<CloudAlbumPicItem> paramArrayList)
  {
    PicMd5DBAdapter localPicMd5DBAdapter = new PicMd5DBAdapter(this.ctx);
    long l = System.currentTimeMillis();
    int i = 0;
    KXLog.d("CloudAlbumManager", "calculateMD5() start ...");
    try
    {
      Iterator localIterator = paramArrayList.iterator();
      while (true)
      {
        boolean bool = localIterator.hasNext();
        if (!bool)
        {
          if (localPicMd5DBAdapter != null)
            localPicMd5DBAdapter.close();
          KXLog.d("CloudAlbumManager", "calculateMD5() end! use time:" + (System.currentTimeMillis() - l) + ", total file num:" + paramArrayList.size() + ", calculateTime:" + i);
          return;
        }
        CloudAlbumPicItem localCloudAlbumPicItem = (CloudAlbumPicItem)localIterator.next();
        String str = localCloudAlbumPicItem.mUrl + localCloudAlbumPicItem.mLastModfiedTime;
        localCloudAlbumPicItem.mMD5 = ((String)this.mPicMd5Map.get(str));
        if (TextUtils.isEmpty(localCloudAlbumPicItem.mMD5))
        {
          localCloudAlbumPicItem.mMD5 = StringUtil.byteArrayToHexString(FileUtil.md5(new File(localCloudAlbumPicItem.mUrl)));
          this.mPicMd5Map.put(str, localCloudAlbumPicItem.mMD5);
          localPicMd5DBAdapter.insertPicMd5(str, localCloudAlbumPicItem.mMD5);
          i++;
        }
        localCloudAlbumPicItem.mState = CloudAlbumModel.getInstance().getStatus(localCloudAlbumPicItem.mMD5);
        if (localCloudAlbumPicItem.mState != -1)
          continue;
        localCloudAlbumPicItem.mState = 0;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        if (localPicMd5DBAdapter == null)
          continue;
        localPicMd5DBAdapter.close();
      }
    }
    finally
    {
      if (localPicMd5DBAdapter != null)
        localPicMd5DBAdapter.close();
    }
    throw localObject;
  }

  public void clearLocalPictures()
  {
    KXLog.d("CloudAlbumManager", "clearLocalPictures()...");
    CloudAlbumModel.getInstance().geLocalPicList().clear();
  }

  public void closeIntroduceView(Context paramContext)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(paramContext).edit();
    localEditor.putBoolean("cloud_album_show_introduce_" + User.getInstance().getUID(), false);
    localEditor.commit();
    KXLog.d("CloudAlbumManager", "closeIntroduceView...");
  }

  public File getCurrentUploadingImage()
  {
    return this.currentUploadingImage;
  }

  public long getLastModifiedTime(Context paramContext)
  {
    return PreferenceManager.getDefaultSharedPreferences(paramContext).getLong("cloud_album_last_modifiedtime_" + User.getInstance().getUID(), -1L);
  }

  public int getSyncContentType(Context paramContext)
  {
    String str = PreferenceManager.getDefaultSharedPreferences(paramContext).getString("cloud_album_setting_content_pref", "1");
    KXLog.d("CloudAlbumManager", "getSyncContentType:" + str);
    this.mSyncContentType = Integer.parseInt(str);
    return this.mSyncContentType;
  }

  public boolean getSyncState(Context paramContext)
  {
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    try
    {
      boolean bool2 = localSharedPreferences.getBoolean("cloud_album_setting_network_pref", true);
      bool1 = bool2;
      this.mSyncOpen = bool1;
      return bool1;
    }
    catch (Exception localException)
    {
      while (true)
        boolean bool1 = false;
    }
  }

  public int getWhiteUser(Context paramContext, String paramString)
  {
    int i = PreferenceManager.getDefaultSharedPreferences(paramContext).getInt("cloud_album_whiteUser_value_" + User.getInstance().getUID(), -1);
    KXLog.d("CloudAlbumManager", "getWhiteUser:" + i);
    return i;
  }

  public void init(Context paramContext)
  {
    this.ctx = paramContext;
    this.mSyncContentType = getSyncContentType(paramContext);
    this.mSyncOpen = getSyncState(paramContext);
    loadIgnoreFileList(paramContext);
    loadMd5sFromDB();
  }

  public void initData(ArrayList<CloudAlbumPicItem> paramArrayList, int paramInt, boolean paramBoolean)
  {
    KXLog.d("CloudAlbumManager", "initData start...");
    if (paramArrayList == null)
      return;
    int i = paramArrayList.size();
    CloudAlbumPicItem localCloudAlbumPicItem1 = null;
    if (i > 0)
      localCloudAlbumPicItem1 = (CloudAlbumPicItem)paramArrayList.get(-1 + paramArrayList.size());
    ArrayList localArrayList = loadLocalPictures(localCloudAlbumPicItem1, paramInt);
    paramArrayList.addAll(localArrayList);
    calculateMD5(localArrayList);
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = localArrayList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        if (paramBoolean)
        {
          String str = localStringBuffer.toString();
          KXLog.d("CloudAlbumManager", "forceCheck file exist:" + str);
          if ((!TextUtils.isEmpty(str)) && (CloudAlbumEngine.getInstance().checkFileExists(this.ctx, str) == 1))
            CloudAlbumModel.getInstance().updateState();
        }
        KXLog.d("CloudAlbumManager", "initData end!");
        return;
      }
      CloudAlbumPicItem localCloudAlbumPicItem2 = (CloudAlbumPicItem)localIterator.next();
      if (localCloudAlbumPicItem2.mState == 1)
        continue;
      if (localStringBuffer.length() > 0)
        localStringBuffer.append(";");
      localStringBuffer.append(localCloudAlbumPicItem2.mMD5);
    }
  }

  public boolean isSyncClosed()
  {
    return !this.mSyncOpen;
  }

  public void loadIgnoreFileList(Context paramContext)
  {
    CloudAlbumModel.getInstance().getIgnoreFileList().clear();
    String str1 = PreferenceManager.getDefaultSharedPreferences(paramContext).getString("cloud_album_ignore_" + User.getInstance().getUID(), "");
    String[] arrayOfString;
    int i;
    if (!TextUtils.isEmpty(str1))
    {
      arrayOfString = str1.split(";");
      i = arrayOfString.length;
    }
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return;
      String str2 = arrayOfString[j];
      CloudAlbumModel.getInstance().getIgnoreFileList().put(str2, "");
    }
  }

  public ArrayList<CloudAlbumPicItem> loadLocalPictures(CloudAlbumPicItem paramCloudAlbumPicItem, int paramInt)
  {
    return loadLocalPictures(paramCloudAlbumPicItem, paramInt, true);
  }

  public ArrayList<CloudAlbumPicItem> loadLocalPictures(CloudAlbumPicItem paramCloudAlbumPicItem, int paramInt, boolean paramBoolean)
  {
    monitorenter;
    String str = "";
    if (paramCloudAlbumPicItem != null);
    try
    {
      str = paramCloudAlbumPicItem.mLastModfiedTime;
      ArrayList localArrayList = FileUtil.loadLocalPictures(this.ctx, str, paramInt, paramBoolean);
      return localArrayList;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void postImages(File[] paramArrayOfFile)
  {
    this.executor.execute(new Runnable(paramArrayOfFile)
    {
      public void run()
      {
        Process.setThreadPriority(19);
        File[] arrayOfFile = this.val$imgs;
        int i = arrayOfFile.length;
        for (int j = 0; ; j++)
        {
          if (j >= i)
            return;
          File localFile = arrayOfFile[j];
          CloudAlbumManager.this.uploadImage(localFile, null);
        }
      }
    });
  }

  public boolean registerState(Context paramContext)
  {
    boolean bool = PreferenceManager.getDefaultSharedPreferences(paramContext).getBoolean("cloud_album_register_" + User.getInstance().getUID(), false);
    KXLog.d("CloudAlbumManager", "registerState:" + bool);
    return bool;
  }

  public void removeAllHandler()
  {
    monitorenter;
    try
    {
      this.handlers.clear();
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public boolean removeHandler(Handler paramHandler)
  {
    monitorenter;
    try
    {
      boolean bool = this.handlers.remove(paramHandler);
      monitorexit;
      return bool;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public void run()
  {
    Process.setThreadPriority(19);
    if (isSyncClosed())
      return;
    KXLog.d("CloudAlbumManager", "run() ...");
    int i = 90;
    int j = this.mSyncContentType;
    int k = this.mSyncContentType;
    CloudAlbumPicItem localCloudAlbumPicItem1 = null;
    long l;
    label62: ArrayList localArrayList;
    int m;
    if (k == 1)
    {
      l = getLastModifiedTime(this.ctx);
      if (l < 0L)
        i = 10;
    }
    else
    {
      localArrayList = loadLocalPictures(localCloudAlbumPicItem1, i, false);
      m = 0;
    }
    while (true)
    {
      label75: if (localArrayList.size() <= 0)
      {
        KXLog.d("CloudAlbumManager", "run end! upload img num:" + m);
        return;
        i = 2147483647;
        localCloudAlbumPicItem1 = new CloudAlbumPicItem();
        localCloudAlbumPicItem1.mLastModfiedTime = l;
        break label62;
      }
      m = localArrayList.size();
      for (int n = 0; ; n++)
      {
        if (n >= m)
        {
          KXLog.d("CloudAlbumManager", "fenbu shangchuan upload img num:" + m);
          if (this.mSyncContentType == 1)
            setLastModifiedTime(this.ctx, ((CloudAlbumPicItem)localArrayList.get(0)).mLastModfiedTime);
          if (localArrayList.size() != 90)
            break label407;
          CloudAlbumPicItem localCloudAlbumPicItem3 = (CloudAlbumPicItem)localArrayList.get(-1 + localArrayList.size());
          localArrayList.clear();
          localArrayList = loadLocalPictures(localCloudAlbumPicItem3, m);
          break label75;
        }
        if (j != this.mSyncContentType)
          break;
        CloudAlbumPicItem localCloudAlbumPicItem2 = (CloudAlbumPicItem)localArrayList.get(n);
        KXLog.d("CloudAlbumManager", "No." + n + ", " + ChatInfoItem.ChatMsg.formatTimestamp(1000L * localCloudAlbumPicItem2.mLastModfiedTime) + ", " + localCloudAlbumPicItem2.mThumbUrl);
        if (!canBackgroundUpload())
        {
          KXLog.d("CloudAlbumManager", "network not available!");
          return;
        }
        String str = localCloudAlbumPicItem2.mUrl + localCloudAlbumPicItem2.mLastModfiedTime;
        if ((localCloudAlbumPicItem2.mState != -1) && (localCloudAlbumPicItem2.mState != 0))
          continue;
        uploadImage(new File(localCloudAlbumPicItem2.mUrl), str);
      }
      label407: localArrayList.clear();
    }
  }

  public void saveIgnoreFileList(Context paramContext, HashMap<String, String> paramHashMap)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(paramContext).edit();
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = paramHashMap.keySet().iterator();
    try
    {
      while (true)
      {
        boolean bool = localIterator.hasNext();
        if (!bool)
        {
          label44: localEditor.putString("cloud_album_ignore_" + User.getInstance().getUID(), localStringBuffer.toString());
          localEditor.commit();
          return;
        }
        localStringBuffer.append((String)localIterator.next()).append(";");
      }
    }
    catch (Exception localException)
    {
      break label44;
    }
  }

  public void sendFailedMsg(String paramString)
  {
    CloudAlbumPicItem localCloudAlbumPicItem = CloudAlbumModel.getInstance().getItem(paramString);
    if (localCloudAlbumPicItem != null)
    {
      localCloudAlbumPicItem.mState = 3;
      CloudAlbumModel.getInstance().addPicStatus(paramString, localCloudAlbumPicItem.mState);
      sendMsg(911, paramString, localCloudAlbumPicItem.mState, 0);
    }
  }

  public void sendMsg(int paramInt1, Object paramObject, int paramInt2, int paramInt3)
  {
    monitorenter;
    try
    {
      Iterator localIterator = this.handlers.iterator();
      while (true)
      {
        boolean bool = localIterator.hasNext();
        if (!bool)
          return;
        Handler localHandler = (Handler)localIterator.next();
        Message localMessage = Message.obtain();
        localMessage.what = paramInt1;
        localMessage.obj = paramObject;
        localMessage.arg1 = paramInt2;
        localMessage.arg2 = paramInt3;
        localHandler.sendMessage(localMessage);
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void setCloudAlbumDataListener(CloudAlbumDataListener paramCloudAlbumDataListener)
  {
    this.mCloudAlbumDataListener = paramCloudAlbumDataListener;
  }

  public void setLastModifiedTime(Context paramContext, long paramLong)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(paramContext).edit();
    localEditor.putLong("cloud_album_last_modifiedtime_" + User.getInstance().getUID(), paramLong);
    localEditor.commit();
  }

  public void setRegisterState(Context paramContext, boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(paramContext).edit();
    localEditor.putBoolean("cloud_album_register_" + User.getInstance().getUID(), paramBoolean);
    localEditor.commit();
    KXLog.d("CloudAlbumManager", "setRegisterState...");
  }

  public void setSyncContentType(Context paramContext, int paramInt)
  {
    KXLog.d("CloudAlbumManager", "setSyncContentType:" + paramInt);
    int i = this.mSyncContentType;
    this.mSyncContentType = paramInt;
    if ((this.mSyncOpen) && (i != paramInt))
    {
      stopUploadDeamon();
      startUploadDeamon(paramContext.getApplicationContext());
    }
  }

  public void setSyncState(Context paramContext, boolean paramBoolean)
  {
    boolean bool = this.mSyncOpen;
    this.mSyncOpen = paramBoolean;
    if ((bool != this.mSyncOpen) && (this.mSyncOpen))
    {
      stopUploadDeamon();
      startUploadDeamon(paramContext.getApplicationContext());
    }
    while (true)
    {
      if (this.mWifiStateChangeListener != null)
        this.mWifiStateChangeListener.syncStateChanged();
      return;
      if ((bool == this.mSyncOpen) || (this.mSyncOpen))
        continue;
      stopUploadDeamon();
    }
  }

  public void setWhiteUser(Context paramContext, String paramString, int paramInt)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(paramContext).edit();
    localEditor.putInt("cloud_album_whiteUser_value_" + User.getInstance().getUID(), paramInt);
    localEditor.commit();
    KXLog.d("CloudAlbumManager", "setWhiteUser:" + paramInt);
  }

  public void setWifiStateChangeListener(WifiStateChangeListener paramWifiStateChangeListener)
  {
    this.mWifiStateChangeListener = paramWifiStateChangeListener;
  }

  public boolean showIntroduceView(Context paramContext)
  {
    boolean bool = PreferenceManager.getDefaultSharedPreferences(paramContext).getBoolean("cloud_album_show_introduce_" + User.getInstance().getUID(), true);
    KXLog.d("CloudAlbumManager", "showIntroduceView:" + bool);
    return bool;
  }

  public void showSyncPicNumNotice(Context paramContext)
  {
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    int i = localSharedPreferences.getInt("cloud_album_sync_pic_num" + User.getInstance().getUID(), 0);
    long l1 = localSharedPreferences.getLong("cloud_album_sync_pic_time" + User.getInstance().getUID(), 0L);
    int j = i + 1;
    SharedPreferences.Editor localEditor = localSharedPreferences.edit();
    localEditor.putInt("cloud_album_sync_pic_num" + User.getInstance().getUID(), j);
    long l2 = System.currentTimeMillis();
    Date localDate1 = new Date(l1);
    Date localDate2 = new Date(l2);
    if ((localDate1.getYear() == localDate2.getYear()) && (localDate1.getMonth() == localDate2.getMonth()) && (localDate1.getDay() == localDate2.getDay()))
      return;
    if (j % 10L == 0L)
    {
      localEditor.putLong("cloud_album_sync_pic_time" + User.getInstance().getUID(), l2);
      sendSyncPicNumNotification(this.ctx, j);
    }
    localEditor.commit();
  }

  public void startUploadDeamon(Context paramContext)
  {
    monitorenter;
    try
    {
      KXLog.d("CloudAlbumManager", "startUploadDeamon() start...");
      startUploadDeamon(paramContext, 36000L, TimeUnit.SECONDS, 0L);
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public void startUploadDeamon(Context paramContext, long paramLong)
  {
    monitorenter;
    try
    {
      KXLog.d("CloudAlbumManager", "startUploadDeamon() start...");
      startUploadDeamon(paramContext, 36000L, TimeUnit.SECONDS, paramLong);
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public void startUploadDeamon(Context paramContext, long paramLong1, TimeUnit paramTimeUnit, long paramLong2)
  {
    monitorenter;
    try
    {
      this.ctx = paramContext;
      if (this.future != null)
        this.future.cancel(true);
      this.future = this.executor.scheduleAtFixedRate(this, paramLong2, paramLong1, paramTimeUnit);
      return;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void stopUploadDeamon()
  {
    monitorenter;
    try
    {
      KXLog.d("CloudAlbumManager", "stopUploadDeamon() start...");
      if (this.future != null)
        this.future.cancel(true);
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public void wifiStateChanged(Context paramContext)
  {
    boolean bool = ConnectivityUtil.isWifiConnected(paramContext);
    if (bool != mWifiEnable)
    {
      mWifiEnable = bool;
      if (!bool)
        break label42;
      startUploadDeamon(paramContext);
    }
    while (true)
    {
      if (this.mWifiStateChangeListener != null)
        this.mWifiStateChangeListener.wifiChanged();
      return;
      label42: stopUploadDeamon();
    }
  }

  public static abstract interface CloudAlbumDataListener
  {
    public abstract void onPicDelete(String paramString);
  }

  private class ProgressListener
    implements HttpProgressListener
  {
    private long lastTime = 0L;
    private CloudAlbumPicItem mItem;
    private final String mMD5;

    ProgressListener(String arg2)
    {
      Object localObject;
      this.mMD5 = localObject;
    }

    public void transferred(long paramLong1, long paramLong2)
    {
      if (CloudAlbumManager.this.handlers.size() == 0);
      do
      {
        long l;
        do
        {
          return;
          l = System.currentTimeMillis();
        }
        while (((paramLong1 < paramLong2) && (l - this.lastTime < 100L)) || (paramLong2 <= 0L));
        this.lastTime = l;
        if (this.mItem != null)
          continue;
        this.mItem = CloudAlbumModel.getInstance().getItem(this.mMD5);
      }
      while (this.mItem == null);
      if (paramLong1 < paramLong2)
      {
        this.mItem.mState = 2;
        this.mItem.mPercent = (int)(paramLong1 * 100L / paramLong2);
      }
      while (true)
      {
        CloudAlbumManager.this.sendMsg(911, this.mItem.mMD5, this.mItem.mState, this.mItem.mPercent);
        return;
        if (paramLong1 < paramLong2)
          continue;
        this.mItem.mState = 1;
        this.mItem.mPercent = 100;
      }
    }
  }

  public static abstract interface WifiStateChangeListener
  {
    public abstract void syncStateChanged();

    public abstract void wifiChanged();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.CloudAlbumManager
 * JD-Core Version:    0.6.0
 */