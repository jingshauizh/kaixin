package com.kaixin001.view.media;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import com.kaixin001.util.KXLog;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class KXMediaManager
  implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener
{
  private static final int PERIOD = 20000;
  private static final String TAG = "KXMediaManager";
  private static KXMediaManager instance = null;
  private boolean mCanGetDuration = false;
  private KXMediaInfo mCurMediaInfo = null;
  private Object mLock = new Object();
  private MediaPlayer mMediaPlayer = null;
  private ArrayList<MediaTask> mMediaTaskList = new ArrayList();
  private MediaTaskThead mMediaTaskThead = null;
  private ForceStopTask mTask;
  private Timer mTime;

  private KXMediaManager()
  {
    this.mMediaTaskThead.start();
  }

  private void addMediaTask(MediaTask paramMediaTask)
  {
    monitorenter;
    try
    {
      this.mMediaTaskList.add(paramMediaTask);
      try
      {
        synchronized (this.mLock)
        {
          this.mLock.notify();
          KXLog.d("KXMediaManager", "MediaTaskThead notify...");
          monitorexit;
          return;
        }
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
    throw localObject1;
  }

  private void createTimer()
  {
    if (this.mTime == null)
    {
      this.mTime = new Timer();
      this.mTask = new ForceStopTask();
      this.mTime.schedule(this.mTask, 20000L, 20000L);
    }
  }

  private void dealMediaTask(MediaTask paramMediaTask)
  {
    if (paramMediaTask != null)
      KXLog.d("KXMediaManager", "dealMediaTask:" + paramMediaTask.mType);
    switch (paramMediaTask.mType)
    {
    default:
      return;
    case 2:
      playMedia(paramMediaTask.mContext, paramMediaTask.mMediaInfo);
      return;
    case 3:
      pauseMedia();
      return;
    case 1:
    }
    stopMedia();
  }

  public static KXMediaManager getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new KXMediaManager();
      KXMediaManager localKXMediaManager = instance;
      return localKXMediaManager;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private MediaTask getMediaTask()
  {
    monitorenter;
    try
    {
      int i = this.mMediaTaskList.size();
      MediaTask localMediaTask = null;
      if (i > 0)
      {
        localMediaTask = (MediaTask)this.mMediaTaskList.get(0);
        this.mMediaTaskList.remove(0);
      }
      return localMediaTask;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void pauseMedia()
  {
    KXLog.d("KXMediaManager", "pauseMedia()");
    this.mCanGetDuration = false;
    if (this.mCurMediaInfo != null)
      this.mCurMediaInfo.setState(3);
    if (this.mMediaPlayer != null);
    try
    {
      this.mMediaPlayer.pause();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void playMedia(Context paramContext, KXMediaInfo paramKXMediaInfo)
  {
    KXLog.d("KXMediaManager", "playMedia()");
    this.mCanGetDuration = false;
    if (this.mCurMediaInfo != paramKXMediaInfo)
      stopMedia();
    this.mCurMediaInfo = paramKXMediaInfo;
    try
    {
      if (this.mMediaPlayer == null)
      {
        this.mMediaPlayer = new MediaPlayer();
        this.mMediaPlayer.setOnCompletionListener(this);
        this.mMediaPlayer.setOnErrorListener(this);
        if (this.mCurMediaInfo.getFileDescriptor() == null)
          break label123;
        this.mMediaPlayer.setDataSource(this.mCurMediaInfo.getFileDescriptor());
      }
      while (true)
      {
        createTimer();
        this.mMediaPlayer.prepare();
        this.mCurMediaInfo.setState(2);
        this.mMediaPlayer.start();
        this.mCanGetDuration = true;
        return;
        label123: if (this.mCurMediaInfo.getUrl() == null)
          break;
        this.mMediaPlayer.setDataSource(paramContext, Uri.parse(this.mCurMediaInfo.getUrl()));
      }
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      while (true)
      {
        localIllegalArgumentException.printStackTrace();
        KXLog.d("KXMediaManager", "playMedia() error:" + localIllegalArgumentException.toString());
        stopMedia();
        return;
        File localFile = new File(this.mCurMediaInfo.getPath());
        if (localFile == null)
          continue;
        FileInputStream localFileInputStream = new FileInputStream(localFile);
        this.mMediaPlayer.setDataSource(localFileInputStream.getFD());
      }
    }
    catch (IllegalStateException localIllegalStateException)
    {
      localIllegalStateException.printStackTrace();
      KXLog.d("KXMediaManager", "playMedia() error:" + localIllegalStateException.toString());
      stopMedia();
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      KXLog.d("KXMediaManager", "playMedia() error:" + localIOException.toString());
      stopMedia();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      KXLog.d("KXMediaManager", "playMedia() error:" + localException.toString());
      stopMedia();
      return;
    }
    finally
    {
      stopTimer();
    }
    throw localObject;
  }

  private void removeAllMediaTask()
  {
    monitorenter;
    try
    {
      this.mMediaTaskList.clear();
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

  private void stopMedia()
  {
    KXLog.d("KXMediaManager", "stopMedia()");
    this.mCanGetDuration = false;
    if (this.mCurMediaInfo != null)
      this.mCurMediaInfo.setState(1);
    if (this.mMediaPlayer != null)
    {
      this.mMediaPlayer.stop();
      this.mMediaPlayer.release();
      this.mMediaPlayer = null;
    }
  }

  private void stopTimer()
  {
    if (this.mTime != null)
    {
      this.mTime.cancel();
      this.mTime = null;
    }
    if (this.mTask != null)
    {
      this.mTask.cancel();
      this.mTask = null;
    }
  }

  protected int getCurMediaDuration()
  {
    if ((this.mMediaPlayer != null) && (this.mCanGetDuration))
      return this.mMediaPlayer.getCurrentPosition();
    return 0;
  }

  public void onCompletion(MediaPlayer paramMediaPlayer)
  {
    stopMedia();
  }

  public boolean onError(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2)
  {
    stopMedia();
    return false;
  }

  public void requestPauseMedia()
  {
    MediaTask localMediaTask = new MediaTask();
    localMediaTask.mType = 3;
    addMediaTask(localMediaTask);
  }

  public void requestPlayMedia(Context paramContext, KXMediaInfo paramKXMediaInfo)
  {
    if ((paramContext != null) && (paramKXMediaInfo != null))
    {
      MediaTask localMediaTask = new MediaTask();
      localMediaTask.mType = 2;
      localMediaTask.mContext = paramContext;
      localMediaTask.mMediaInfo = paramKXMediaInfo;
      addMediaTask(localMediaTask);
    }
  }

  public void requestStopCurrentMedia()
  {
    removeAllMediaTask();
    requestStopMedia();
  }

  public void requestStopMedia()
  {
    MediaTask localMediaTask = new MediaTask();
    localMediaTask.mType = 1;
    addMediaTask(localMediaTask);
  }

  public class ForceStopTask extends TimerTask
  {
    public ForceStopTask()
    {
    }

    public void run()
    {
      KXLog.d("KXMediaManager", "ForceStopTask excute...");
      KXMediaManager.this.stopMedia();
    }
  }

  public class MediaTask
  {
    public Context mContext;
    public KXMediaInfo mMediaInfo;
    public int mType;

    public MediaTask()
    {
    }
  }

  public class MediaTaskThead extends Thread
  {
    public boolean bRuning = true;

    public MediaTaskThead()
    {
    }

    public void run()
    {
      while (true)
      {
        if (!this.bRuning)
          return;
        KXLog.d("KXMediaManager", "MediaTaskThead running...");
        KXMediaManager.MediaTask localMediaTask = KXMediaManager.this.getMediaTask();
        if (localMediaTask != null)
        {
          KXMediaManager.this.dealMediaTask(localMediaTask);
          continue;
        }
        try
        {
          synchronized (KXMediaManager.this.mLock)
          {
            KXLog.d("KXMediaManager", "MediaTaskThead wait...");
            KXMediaManager.this.mLock.wait();
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.media.KXMediaManager
 * JD-Core Version:    0.6.0
 */