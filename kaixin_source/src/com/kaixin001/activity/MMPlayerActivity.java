package com.kaixin001.activity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.KXLog;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MMPlayerActivity extends KXActivity
  implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener
{
  private static final int MEDIA_ERROR_NO_SERIOUS_INFLUENCE = -38;
  public static final String MMSOURCE = "MMSOURCE";
  private String TAG = "MMPlayerActivity";
  View.OnClickListener mBtnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramView)
    {
      if (paramView == MMPlayerActivity.this.mImgBtnClose)
        MMPlayerActivity.this.Close();
      do
      {
        return;
        if (paramView == MMPlayerActivity.this.mImgBtnPause)
        {
          if ((MMPlayerActivity.this.mMediaPlayer != null) && (MMPlayerActivity.this.mIsPrepared) && (MMPlayerActivity.this.mMediaPlayer.isPlaying()))
            MMPlayerActivity.this.mMediaPlayer.pause();
          MMPlayerActivity.this.mStartWhenPrepared = false;
          MMPlayerActivity.this.mImgBtnPause.setVisibility(8);
          MMPlayerActivity.this.mImgBtnPlay.setVisibility(0);
          return;
        }
        if (paramView == MMPlayerActivity.this.mImgBtnPlay)
        {
          if ((MMPlayerActivity.this.mMediaPlayer != null) && (MMPlayerActivity.this.mIsPrepared))
            MMPlayerActivity.this.mMediaPlayer.start();
          for (MMPlayerActivity.this.mStartWhenPrepared = false; ; MMPlayerActivity.this.mStartWhenPrepared = true)
          {
            MMPlayerActivity.this.mImgBtnPause.setVisibility(0);
            MMPlayerActivity.this.mImgBtnPlay.setVisibility(8);
            return;
          }
        }
        if (paramView != MMPlayerActivity.this.mImgBtnFullScreen)
          continue;
        FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)MMPlayerActivity.this.mSurfaceView.getLayoutParams();
        if ((localLayoutParams.leftMargin >= MMPlayerActivity.this.mOriMargin) || (localLayoutParams.rightMargin >= MMPlayerActivity.this.mOriMargin))
        {
          MMPlayerActivity.this.mOriMargin = localLayoutParams.leftMargin;
          localLayoutParams.leftMargin = 10;
          localLayoutParams.rightMargin = 10;
          return;
        }
        localLayoutParams.leftMargin = MMPlayerActivity.this.mOriMargin;
        localLayoutParams.rightMargin = MMPlayerActivity.this.mOriMargin;
        return;
      }
      while ((paramView != MMPlayerActivity.this.mSurfaceView) || (!MMPlayerActivity.this.mIsPrepared));
      if (MMPlayerActivity.this.mControlLayOut.getVisibility() == 8)
      {
        MMPlayerActivity.this.mControlLayOut.setVisibility(0);
        return;
      }
      MMPlayerActivity.this.mControlLayOut.setVisibility(8);
    }
  };
  private LinearLayout mControlLayOut;
  private int mCurrentBufferPercentage;
  private int mDuration = -1;
  private ImageButton mImgBtnClose;
  private ImageButton mImgBtnFullScreen;
  private ImageButton mImgBtnPause;
  private ImageButton mImgBtnPlay;
  private boolean mIsPrepared;
  private MediaPlayer mMediaPlayer;
  private int mOriMargin = 0;
  private int mProgressBarShowtime = 0;
  SurfaceHolder.Callback mSHCallback = new SurfaceHolder.Callback()
  {
    public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3)
    {
      MMPlayerActivity.this.mSurfaceWidth = paramInt2;
      MMPlayerActivity.this.mSurfaceHeight = paramInt3;
      if ((MMPlayerActivity.this.mMediaPlayer != null) && (MMPlayerActivity.this.mIsPrepared) && (MMPlayerActivity.this.mVideoWidth == paramInt2) && (MMPlayerActivity.this.mVideoHeight == paramInt3))
      {
        if (MMPlayerActivity.this.mSeekWhenPrepared != 0)
        {
          MMPlayerActivity.this.mMediaPlayer.seekTo(MMPlayerActivity.this.mSeekWhenPrepared);
          MMPlayerActivity.this.mSeekWhenPrepared = 0;
        }
        MMPlayerActivity.this.setUiElementVisible(3);
        MMPlayerActivity.this.mMediaPlayer.start();
      }
    }

    public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
    {
      MMPlayerActivity.this.mSurfaceHolder = paramSurfaceHolder;
      MMPlayerActivity.this.openVideo();
    }

    public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
    {
      MMPlayerActivity.this.mSurfaceHolder = null;
      if (MMPlayerActivity.this.mMediaPlayer != null)
      {
        MMPlayerActivity.this.mMediaPlayer.reset();
        MMPlayerActivity.this.mMediaPlayer.release();
        MMPlayerActivity.this.mMediaPlayer = null;
      }
    }
  };
  private SeekBar mSeekBar;
  private int mSeekWhenPrepared;
  MediaPlayer.OnVideoSizeChangedListener mSizeChangedListener = new MediaPlayer.OnVideoSizeChangedListener()
  {
    public void onVideoSizeChanged(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2)
    {
      MMPlayerActivity.this.mVideoWidth = paramMediaPlayer.getVideoWidth();
      MMPlayerActivity.this.mVideoHeight = paramMediaPlayer.getVideoHeight();
      if ((MMPlayerActivity.this.mVideoWidth != 0) && (MMPlayerActivity.this.mVideoHeight != 0))
        MMPlayerActivity.this.getHolder().setFixedSize(MMPlayerActivity.this.mVideoWidth, MMPlayerActivity.this.mVideoHeight);
    }
  };
  private String mSource = null;
  private boolean mStartWhenPrepared;
  private int mState = 0;
  private int mSurfaceHeight;
  private SurfaceHolder mSurfaceHolder;
  private SurfaceView mSurfaceView;
  private int mSurfaceWidth;
  private Timer mTimer = new Timer();
  private MyTimerTask mTimerTask = new MyTimerTask(null);
  private TextView mTxtTotleTime;
  private Uri mUri;
  private int mVideoHeight;
  private int mVideoWidth;
  private LinearLayout mWaitingLayOut;
  private TextView mtxElapseTime;

  private void Close()
  {
    this.mImgBtnPlay.setVisibility(0);
    this.mImgBtnPause.setVisibility(8);
    this.mtxElapseTime.setText("00:00:00");
    this.mTxtTotleTime.setText("00:00:00");
    if (this.mTimer != null)
    {
      this.mTimer.cancel();
      this.mTimer = null;
    }
    if (this.mSeekBar != null)
    {
      this.mSeekBar.setProgress(0);
      this.mSeekBar.setSecondaryProgress(0);
    }
    if (this.mMediaPlayer == null)
      return;
    this.mMediaPlayer.stop();
    this.mMediaPlayer.release();
    this.mMediaPlayer = null;
    finish();
  }

  private int getCurrentPosition()
  {
    if ((this.mMediaPlayer != null) && (this.mIsPrepared))
      return this.mMediaPlayer.getCurrentPosition();
    return 0;
  }

  private SurfaceHolder getHolder()
  {
    return this.mSurfaceHolder;
  }

  private void initView()
  {
    this.mSurfaceView = ((SurfaceView)findViewById(2131363084));
    this.mSurfaceView.setOnClickListener(this.mBtnClickListener);
    this.mSurfaceHolder = this.mSurfaceView.getHolder();
    getHolder().addCallback(this.mSHCallback);
    getHolder().setType(3);
    this.mImgBtnClose = ((ImageButton)findViewById(2131363089));
    this.mImgBtnClose.setOnClickListener(this.mBtnClickListener);
    this.mImgBtnPause = ((ImageButton)findViewById(2131363091));
    this.mImgBtnPause.setOnClickListener(this.mBtnClickListener);
    this.mImgBtnPlay = ((ImageButton)findViewById(2131363090));
    this.mImgBtnPlay.setOnClickListener(this.mBtnClickListener);
    this.mImgBtnFullScreen = ((ImageButton)findViewById(2131363092));
    this.mImgBtnFullScreen.setOnClickListener(this.mBtnClickListener);
    this.mtxElapseTime = ((TextView)findViewById(2131363086));
    this.mTxtTotleTime = ((TextView)findViewById(2131363088));
    this.mSeekBar = ((SeekBar)findViewById(2131363087));
    this.mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
    {
      public void onProgressChanged(SeekBar paramSeekBar, int paramInt, boolean paramBoolean)
      {
      }

      public void onStartTrackingTouch(SeekBar paramSeekBar)
      {
      }

      public void onStopTrackingTouch(SeekBar paramSeekBar)
      {
        MMPlayerActivity.this.seekTo(paramSeekBar.getProgress());
      }
    });
    this.mWaitingLayOut = ((LinearLayout)findViewById(2131363093));
    this.mControlLayOut = ((LinearLayout)findViewById(2131363085));
  }

  private boolean isPlaying()
  {
    if ((this.mMediaPlayer != null) && (this.mIsPrepared))
      return this.mMediaPlayer.isPlaying();
    return false;
  }

  private void openVideo()
  {
    if ((this.mUri == null) || (this.mSurfaceHolder == null))
      return;
    Intent localIntent = new Intent("com.android.music.musicservicecommand");
    localIntent.putExtra("command", "pause");
    sendBroadcast(localIntent);
    if (this.mMediaPlayer != null)
    {
      this.mMediaPlayer.reset();
      this.mMediaPlayer.release();
      this.mMediaPlayer = null;
    }
    try
    {
      this.mMediaPlayer = new MediaPlayer();
      this.mMediaPlayer.setOnPreparedListener(this);
      this.mMediaPlayer.setOnVideoSizeChangedListener(this.mSizeChangedListener);
      this.mIsPrepared = false;
      KXLog.d(this.TAG, "reset duration to -1 in openVideo");
      this.mDuration = -1;
      this.mMediaPlayer.setOnCompletionListener(this);
      this.mMediaPlayer.setOnErrorListener(this);
      this.mMediaPlayer.setOnBufferingUpdateListener(this);
      this.mCurrentBufferPercentage = 0;
      this.mMediaPlayer.setDataSource(this, this.mUri);
      this.mMediaPlayer.setDisplay(this.mSurfaceHolder);
      this.mMediaPlayer.setAudioStreamType(3);
      this.mMediaPlayer.setScreenOnWhilePlaying(true);
      this.mMediaPlayer.prepareAsync();
      return;
    }
    catch (IOException localIOException)
    {
      KXLog.e(this.TAG, "Unable to open content: " + this.mUri, localIOException);
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      KXLog.e(this.TAG, "Unable to open content: " + this.mUri, localIllegalArgumentException);
    }
  }

  private void setUiElementVisible(int paramInt)
  {
    this.mState = paramInt;
    if ((paramInt != 0) && (paramInt != 1) && (paramInt != 2))
    {
      if (paramInt != 3)
        break label47;
      this.mWaitingLayOut.setVisibility(8);
      this.mControlLayOut.setVisibility(0);
      this.mProgressBarShowtime = 0;
    }
    label47: 
    do
      return;
    while (paramInt == 4);
  }

  public int getDuration()
  {
    if ((this.mMediaPlayer != null) && (this.mIsPrepared))
    {
      if (this.mDuration > 0)
        return this.mDuration;
      this.mDuration = this.mMediaPlayer.getDuration();
      return this.mDuration;
    }
    this.mDuration = -1;
    return this.mDuration;
  }

  protected boolean handleMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default:
      return super.handleMessage(paramMessage);
    case 10007:
    }
    setPlayingProgress();
    return false;
  }

  public String millsecsToTime(int paramInt)
  {
    int i = paramInt / 3600;
    int j = (paramInt - i * 3600) / 60;
    int k = paramInt % 60;
    if (i > 24)
      (0 + 1);
    StringBuilder localStringBuilder = new StringBuilder();
    if (i < 10)
      localStringBuilder.append("0");
    localStringBuilder.append(i).append(":");
    if (j < 10)
      localStringBuilder.append("0");
    localStringBuilder.append(j).append(":");
    if (k < 10)
      localStringBuilder.append("0");
    localStringBuilder.append(k);
    return localStringBuilder.toString();
  }

  public void onBufferingUpdate(MediaPlayer paramMediaPlayer, int paramInt)
  {
    if (paramMediaPlayer == null);
    do
    {
      return;
      int i = paramInt * paramMediaPlayer.getDuration() / 100;
      this.mSeekBar.setSecondaryProgress(i);
      this.mProgressBarShowtime = (1 + this.mProgressBarShowtime);
    }
    while ((this.mProgressBarShowtime % 5 != 0) || (this.mControlLayOut.getVisibility() != 0));
    this.mProgressBarShowtime = 0;
    this.mControlLayOut.setVisibility(8);
  }

  public void onCompletion(MediaPlayer paramMediaPlayer)
  {
    this.mImgBtnPause.setVisibility(8);
    this.mImgBtnPlay.setVisibility(0);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    getWindow().setFlags(1024, 1024);
    setContentView(2130903229);
    this.mSource = getIntent().getExtras().getString("MMSOURCE");
    initView();
    setVideoPath(this.mSource);
  }

  protected void onDestroy()
  {
    Close();
    super.onDestroy();
  }

  public boolean onError(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2)
  {
    KXLog.e(this.TAG, "Error: " + paramInt1 + "," + paramInt2);
    if (paramInt1 != -38)
      DialogUtil.showMsgDlgStdConfirm(this, 2131427329, 2131427365, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          MMPlayerActivity.this.finish();
        }
      });
    return true;
  }

  public void onPrepared(MediaPlayer paramMediaPlayer)
  {
    this.mIsPrepared = true;
    this.mVideoWidth = paramMediaPlayer.getVideoWidth();
    this.mVideoHeight = paramMediaPlayer.getVideoHeight();
    int i = this.mMediaPlayer.getVideoWidth();
    int j = this.mMediaPlayer.getVideoHeight();
    if ((i == 0) || (j == 0))
      finish();
    do
    {
      while (true)
      {
        return;
        if ((this.mVideoWidth == 0) || (this.mVideoHeight == 0))
          break;
        getHolder().setFixedSize(this.mVideoWidth, this.mVideoHeight);
        String str = millsecsToTime(this.mMediaPlayer.getDuration() / 1000);
        this.mTxtTotleTime.setText(str);
        int k = this.mMediaPlayer.getDuration();
        this.mSeekBar.setMax(k);
        this.mTimer.schedule(this.mTimerTask, 1000L, 1000L);
        if ((this.mSurfaceWidth != this.mVideoWidth) || (this.mSurfaceHeight != this.mVideoHeight))
          continue;
        if (this.mSeekWhenPrepared != 0)
        {
          this.mMediaPlayer.seekTo(this.mSeekWhenPrepared);
          this.mSeekWhenPrepared = 0;
        }
        if (this.mStartWhenPrepared)
        {
          setUiElementVisible(3);
          this.mMediaPlayer.start();
          this.mStartWhenPrepared = false;
          return;
        }
        if ((isPlaying()) || (this.mSeekWhenPrepared != 0))
          continue;
        getCurrentPosition();
        return;
      }
      if (this.mSeekWhenPrepared == 0)
        continue;
      this.mMediaPlayer.seekTo(this.mSeekWhenPrepared);
      this.mSeekWhenPrepared = 0;
    }
    while (!this.mStartWhenPrepared);
    setUiElementVisible(3);
    this.mMediaPlayer.start();
    this.mStartWhenPrepared = false;
  }

  protected void onStop()
  {
    finish();
    super.onStop();
  }

  public void seekTo(int paramInt)
  {
    if ((this.mMediaPlayer != null) && (this.mIsPrepared))
    {
      this.mMediaPlayer.seekTo(paramInt);
      return;
    }
    this.mSeekWhenPrepared = paramInt;
  }

  public void setPlayingProgress()
  {
    if ((this.mMediaPlayer == null) || (!this.mMediaPlayer.isPlaying()))
      return;
    int i = this.mMediaPlayer.getCurrentPosition();
    this.mSeekBar.setProgress(i);
    String str = millsecsToTime(this.mMediaPlayer.getCurrentPosition() / 1000);
    this.mtxElapseTime.setText(str);
  }

  public void setVideoPath(String paramString)
  {
    setVideoURI(Uri.parse(paramString));
  }

  public void setVideoURI(Uri paramUri)
  {
    this.mUri = paramUri;
    this.mStartWhenPrepared = true;
    this.mSeekWhenPrepared = 0;
  }

  public void stopPlayback()
  {
    if (this.mMediaPlayer != null)
    {
      this.mMediaPlayer.stop();
      this.mMediaPlayer.release();
      this.mMediaPlayer = null;
    }
  }

  private class MyTimerTask extends TimerTask
  {
    private MyTimerTask()
    {
    }

    public void run()
    {
      try
      {
        Message localMessage = Message.obtain();
        localMessage.what = 10007;
        MMPlayerActivity.this.mHandler.sendMessage(localMessage);
        MMPlayerActivity.this.mTimerTask = new MyTimerTask(MMPlayerActivity.this);
        return;
      }
      catch (Exception localException)
      {
        KXLog.e("KaixinAppWidgetProvider", "run", localException);
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.MMPlayerActivity
 * JD-Core Version:    0.6.0
 */