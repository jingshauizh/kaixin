package com.kaixin001.view.media;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.kaixin001.activity.KXDownloadPicActivity;
import java.io.FileDescriptor;
import java.util.Timer;
import java.util.TimerTask;

public class KXMediaView extends FrameLayout
  implements View.OnClickListener
{
  private static final int PERIOD = 200;
  private TextView mAction;
  private KXDownloadPicActivity mActivity;
  private KXMediaInfo mCurMediaInfo;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      KXMediaView.this.updateView();
    }
  };
  private TextView mLength;
  private int mMediaDuration;
  private View mProgressIndication;
  private ImageView mState;
  private CheckMediaTask mTask;
  private Timer mTime;

  public KXMediaView(Context paramContext)
  {
    super(paramContext);
    initView(paramContext);
  }

  public KXMediaView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initView(paramContext);
  }

  public KXMediaView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initView(paramContext);
  }

  private void createTimer()
  {
    if (this.mTime == null)
    {
      this.mTime = new Timer();
      this.mTask = new CheckMediaTask(null);
      this.mTime.schedule(this.mTask, 0L, 200L);
    }
  }

  private void initView(Context paramContext)
  {
    if ((paramContext instanceof KXDownloadPicActivity));
    for (this.mActivity = ((KXDownloadPicActivity)paramContext); ; this.mActivity = null)
    {
      View localView = ((LayoutInflater)paramContext.getSystemService("layout_inflater")).inflate(2130903401, this);
      localView.setOnClickListener(this);
      this.mLength = ((TextView)localView.findViewById(2131363938));
      this.mState = ((ImageView)localView.findViewById(2131363939));
      this.mAction = ((TextView)localView.findViewById(2131363940));
      this.mProgressIndication = localView.findViewById(2131363941);
      return;
    }
  }

  private void playMedia()
  {
    this.mCurMediaInfo.setState(0);
    KXMediaManager.getInstance().requestPlayMedia(getContext(), this.mCurMediaInfo);
    updateView();
  }

  private void stopMedia()
  {
    this.mCurMediaInfo.setState(1);
    KXMediaManager.getInstance().requestStopMedia();
    updateView();
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

  private void updateView()
  {
    int i = this.mCurMediaInfo.getState();
    if ((i == 2) && (this.mActivity != null) && (!this.mActivity.getIsCanLoad()))
      return;
    float f1;
    int j;
    String str;
    float f2;
    ViewGroup.LayoutParams localLayoutParams;
    if ((i == 0) || (i == 2))
    {
      createTimer();
      this.mAction.setText(2131428523);
      this.mState.setImageResource(2130838269);
      f1 = KXMediaManager.getInstance().getCurMediaDuration() / 1000.0F;
      j = (int)f1;
      if (j > 9)
      {
        str = String.valueOf(j) + "s";
        this.mLength.setText(str);
        f2 = getWidth();
        localLayoutParams = this.mProgressIndication.getLayoutParams();
        if (f1 >= this.mMediaDuration)
          break label275;
      }
    }
    label275: for (localLayoutParams.width = (int)(f2 * f1 / this.mMediaDuration); ; localLayoutParams.width = (int)f2)
    {
      this.mProgressIndication.setLayoutParams(localLayoutParams);
      return;
      str = "0" + j + "s";
      break;
      str = this.mCurMediaInfo.getDuration() + "s";
      if (str.length() == 2)
        str = "0" + str;
      stopTimer();
      this.mAction.setText(2131428522);
      this.mState.setImageResource(2130838264);
      f1 = 0.0F;
      break;
    }
  }

  public void onClick(View paramView)
  {
    int i = this.mCurMediaInfo.getState();
    if ((i == 0) || (i == 2))
    {
      stopMedia();
      return;
    }
    playMedia();
  }

  public void setMediaData(KXMediaInfo paramKXMediaInfo)
  {
    this.mCurMediaInfo = paramKXMediaInfo;
    try
    {
      int j = Integer.parseInt(this.mCurMediaInfo.getDuration());
      i = j;
      this.mMediaDuration = i;
      updateView();
      return;
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        int i = 1;
      }
    }
  }

  public void setMediaData(FileDescriptor paramFileDescriptor, String paramString)
  {
    if (this.mCurMediaInfo == null)
      this.mCurMediaInfo = new KXMediaInfo();
    this.mCurMediaInfo.setDuration(paramString);
    this.mCurMediaInfo.setFileDescriptor(paramFileDescriptor);
    this.mCurMediaInfo.setPath(null);
    this.mCurMediaInfo.setUrl(null);
    this.mMediaDuration = Integer.parseInt(this.mCurMediaInfo.getDuration());
    this.mLength.setText(this.mCurMediaInfo.getDuration() + "s");
    updateView();
  }

  public void setMediaData(String paramString1, String paramString2)
  {
    if (this.mCurMediaInfo == null)
      this.mCurMediaInfo = new KXMediaInfo();
    this.mCurMediaInfo.setDuration(paramString2);
    this.mCurMediaInfo.setFileDescriptor(null);
    this.mCurMediaInfo.setPath(paramString1);
    this.mCurMediaInfo.setUrl(null);
    this.mMediaDuration = Integer.parseInt(this.mCurMediaInfo.getDuration());
    this.mLength.setText(this.mCurMediaInfo.getDuration() + "s");
    updateView();
  }

  private class CheckMediaTask extends TimerTask
  {
    private CheckMediaTask()
    {
    }

    public void run()
    {
      KXMediaView.this.mHandler.sendEmptyMessage(0);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.media.KXMediaView
 * JD-Core Version:    0.6.0
 */