package com.tencent.open;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout.LayoutParams;
import com.tencent.connect.common.b;
import com.tencent.mta.TencentStat;
import com.tencent.sdkutil.HttpUtils;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.QQToken;
import java.io.IOException;
import java.io.InputStream;

public class TaskGuide extends b
{
  private static int BACKGROUND_HEIGHT = 0;
  private static int BACKGROUND_WIDTH = 0;
  private static int BUTTON_HEIGHT = 0;
  private static final int BUTTON_ID = 2;
  private static int BUTTON_MARGIN_RIGHT = 0;
  private static int BUTTON_MARGIN_TOP = 0;
  private static int BUTTON_WIDTH = 0;
  private static final String CGI_APP_GRADE_URI = "http://appact.qzone.qq.com/appstore_activity_task_pcpush_sdk";
  private static int REWARD_MARGIN_TOP = 0;
  private static int REWARD_TEXT_SIZE = 0;
  private static int REWARD_TEXT_WIDTH = 0;
  private static final int REWARD_TX_ID = 3;
  private static int SUBTEXT_MARGIN_TOP = 0;
  private static final int SUB_TIP_TX_ID = 4;
  private static int TIPTEXT_MARGIN_LEFT = 0;
  private static int TIPTEXT_TEXT_SIZE = 0;
  private static int TIPTEXT_WIDTH = 0;
  private static final int TIP_TX_ID = 1;
  private static int sAutoCollapseInteval;
  private static Drawable sBackground;
  private static Drawable sButtonGreen;
  private static Drawable sButtonRed;
  static long sDuration;
  private Runnable clRunnalbe = null;
  private Runnable cllDelayRunnable = null;
  private boolean mAddedWindow = false;
  private int mAnimationLength;
  private boolean mCollapseAnimationRunning = false;
  private ViewGroup mContentView = null;
  private boolean mExpandAnimationRunning = false;
  private Handler mHandler = new Handler(Looper.getMainLooper());
  private Interpolator mInterpolator = new AccelerateInterpolator();
  boolean mIsCollapse = false;
  IUiListener mListener;
  private long mStartTime;
  private int mStartY;
  private TaskGuide.TaskState mState1 = TaskGuide.TaskState.INIT;
  private TaskGuide.TaskState mState2 = TaskGuide.TaskState.INIT;
  private TaskGuide.TaskInfo mTaskInfo;
  private WindowManager.LayoutParams mWinParams = null;
  private float sDensity = 0.0F;
  private int sScreenHeight = 0;
  private int sScreenWidth = 0;
  private WindowManager wm;

  static
  {
    BUTTON_HEIGHT = 30;
    BUTTON_MARGIN_RIGHT = 29;
    BUTTON_MARGIN_TOP = 5;
    REWARD_TEXT_WIDTH = 74;
    REWARD_MARGIN_TOP = 0;
    REWARD_TEXT_SIZE = 6;
    TIPTEXT_WIDTH = 153;
    TIPTEXT_MARGIN_LEFT = 30;
    TIPTEXT_TEXT_SIZE = 6;
    SUBTEXT_MARGIN_TOP = 3;
    sDuration = 5000L;
    sAutoCollapseInteval = 3000;
  }

  public TaskGuide(Context paramContext, QQToken paramQQToken)
  {
    super(paramContext, paramQQToken);
    this.wm = ((WindowManager)paramContext.getSystemService("window"));
    initDisplay();
  }

  private void autoCollapseWindow(int paramInt)
  {
    cancelAutoAnimation();
    this.cllDelayRunnable = new TaskGuide.CollapseDelayRunnable(this, null);
    this.mHandler.postDelayed(this.cllDelayRunnable, paramInt);
  }

  private void cancelAutoAnimation()
  {
    this.mHandler.removeCallbacks(this.cllDelayRunnable);
    if (!isAnimation())
      this.mHandler.removeCallbacks(this.clRunnalbe);
  }

  private void collapseWindow()
  {
    if (!isAnimation())
    {
      this.mHandler.removeCallbacks(this.cllDelayRunnable);
      this.mHandler.removeCallbacks(this.clRunnalbe);
      this.clRunnalbe = new TaskGuide.CollapseExpandRunnable(this, false);
      setAnimationParam(false);
      this.mHandler.post(this.clRunnalbe);
    }
  }

  private ViewGroup createNewContentView(Context paramContext)
  {
    TaskGuide.QQRelativeLayout localQQRelativeLayout = new TaskGuide.QQRelativeLayout(this, paramContext);
    TaskGuide.StepInfo[] arrayOfStepInfo = this.mTaskInfo.stepInfoArray;
    if (arrayOfStepInfo.length == 1)
    {
      TaskGuide.TaskLinearLayout localTaskLinearLayout1 = new TaskGuide.TaskLinearLayout(this, paramContext, arrayOfStepInfo[0]);
      localTaskLinearLayout1.setId(1);
      RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(-1, -2);
      localLayoutParams1.addRule(15);
      localQQRelativeLayout.addView(localTaskLinearLayout1, localLayoutParams1);
    }
    while (true)
    {
      localQQRelativeLayout.setBackgroundDrawable(getBackgroundDrawable());
      return localQQRelativeLayout;
      TaskGuide.TaskLinearLayout localTaskLinearLayout2 = new TaskGuide.TaskLinearLayout(this, paramContext, arrayOfStepInfo[0]);
      localTaskLinearLayout2.setId(1);
      TaskGuide.TaskLinearLayout localTaskLinearLayout3 = new TaskGuide.TaskLinearLayout(this, paramContext, arrayOfStepInfo[1]);
      localTaskLinearLayout3.setId(2);
      RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(-1, -2);
      localLayoutParams2.addRule(14);
      localLayoutParams2.setMargins(0, getDimenDp2Px(6), 0, 0);
      RelativeLayout.LayoutParams localLayoutParams3 = new RelativeLayout.LayoutParams(-1, -2);
      localLayoutParams3.addRule(14);
      localLayoutParams3.setMargins(0, getDimenDp2Px(4), 0, 0);
      localLayoutParams3.addRule(3, 1);
      localLayoutParams3.addRule(5, 1);
      localQQRelativeLayout.addView(localTaskLinearLayout2, localLayoutParams2);
      localQQRelativeLayout.addView(localTaskLinearLayout3, localLayoutParams3);
    }
  }

  private void endAnimation()
  {
    if (this.mExpandAnimationRunning)
      autoCollapseWindow(3000);
    while (true)
    {
      if (this.mExpandAnimationRunning)
      {
        WindowManager.LayoutParams localLayoutParams = this.mWinParams;
        localLayoutParams.flags = (0xFFFFFFEF & localLayoutParams.flags);
        this.wm.updateViewLayout(this.mContentView, this.mWinParams);
      }
      this.mExpandAnimationRunning = false;
      this.mCollapseAnimationRunning = false;
      return;
      removeWindow();
    }
  }

  private void expandWindow()
  {
    if (!isAnimation())
    {
      this.mHandler.removeCallbacks(this.cllDelayRunnable);
      this.mHandler.removeCallbacks(this.clRunnalbe);
      this.clRunnalbe = new TaskGuide.CollapseExpandRunnable(this, true);
      setAnimationParam(true);
      this.mHandler.post(this.clRunnalbe);
    }
  }

  private WindowManager.LayoutParams genearteWinParams(Context paramContext)
  {
    WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
    localLayoutParams.gravity = 49;
    this.wm.getDefaultDisplay().getWidth();
    this.wm.getDefaultDisplay().getHeight();
    localLayoutParams.width = getDimenDp2Px(BACKGROUND_WIDTH);
    localLayoutParams.height = getDimenDp2Px(BACKGROUND_HEIGHT);
    localLayoutParams.windowAnimations = 16973826;
    localLayoutParams.format = 1;
    localLayoutParams.flags = (0x208 | localLayoutParams.flags);
    localLayoutParams.type = 2;
    this.mWinParams = localLayoutParams;
    return localLayoutParams;
  }

  private Drawable getBackgroundDrawable()
  {
    if (sBackground == null)
      sBackground = getDrawable("background.9.png", this.mContext);
    return sBackground;
  }

  private Drawable getButtonGreen()
  {
    if (sButtonGreen == null)
      sButtonGreen = getDrawable("button_green.9.png", this.mContext);
    return sButtonGreen;
  }

  private Drawable getButtonRed()
  {
    if (sButtonRed == null)
      sButtonRed = getDrawable("button_red.9.png", this.mContext);
    return sButtonRed;
  }

  private int getDimenDp2Px(int paramInt)
  {
    return (int)(paramInt * this.sDensity);
  }

  private Drawable getDrawable(String paramString, Context paramContext)
  {
    AssetManager localAssetManager = paramContext.getApplicationContext().getAssets();
    InputStream localInputStream;
    Object localObject;
    IOException localIOException2;
    try
    {
      localInputStream = localAssetManager.open(paramString);
      if (localInputStream == null)
        return null;
      if (paramString.endsWith(".9.png"))
      {
        Bitmap localBitmap = BitmapFactory.decodeStream(localInputStream);
        if (localBitmap != null)
        {
          byte[] arrayOfByte = localBitmap.getNinePatchChunk();
          NinePatch.isNinePatchChunk(arrayOfByte);
          NinePatchDrawable localNinePatchDrawable = new NinePatchDrawable(localBitmap, arrayOfByte, new Rect(), null);
          return localNinePatchDrawable;
        }
      }
    }
    catch (IOException localIOException1)
    {
      localObject = null;
      localIOException2 = localIOException1;
    }
    while (true)
    {
      localIOException2.printStackTrace();
      return localObject;
      return null;
      Drawable localDrawable = Drawable.createFromStream(localInputStream, paramString);
      localObject = localDrawable;
      try
      {
        localInputStream.close();
        return localObject;
      }
      catch (IOException localIOException3)
      {
      }
    }
  }

  private void getGift(int paramInt)
  {
    Bundle localBundle = composeCGIParams();
    localBundle.putString("action", "get_gift");
    localBundle.putString("task_id", this.mTaskInfo.taskId);
    localBundle.putString("step_no", new Integer(paramInt).toString());
    localBundle.putString("appid", this.mToken.getAppId());
    TaskGuide.GiftResultListener localGiftResultListener = new TaskGuide.GiftResultListener(this, paramInt);
    HttpUtils.requestAsync(this.mToken, this.mContext, "http://appact.qzone.qq.com/appstore_activity_task_pcpush_sdk", localBundle, "GET", localGiftResultListener);
    moveToState(paramInt, TaskGuide.TaskState.WAITTING_BACK_REWARD);
    TencentStat.a(this.mContext, this.mToken, "TaskApi", new String[] { "getGift" });
  }

  private TaskGuide.TaskState getState(int paramInt)
  {
    if (paramInt == 0)
      return this.mState1;
    if (paramInt == 1)
      return this.mState2;
    return TaskGuide.TaskState.INIT;
  }

  private void initDisplay()
  {
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    this.wm.getDefaultDisplay().getMetrics(localDisplayMetrics);
    this.sScreenWidth = localDisplayMetrics.widthPixels;
    this.sScreenHeight = localDisplayMetrics.heightPixels;
    this.sDensity = localDisplayMetrics.density;
  }

  private boolean isAnimation()
  {
    return (this.mExpandAnimationRunning) || (this.mCollapseAnimationRunning);
  }

  private void moveToState(int paramInt, TaskGuide.TaskState paramTaskState)
  {
    if (paramInt == 0)
    {
      this.mState1 = paramTaskState;
      return;
    }
    if (paramInt == 1)
    {
      this.mState2 = paramTaskState;
      return;
    }
    this.mState1 = paramTaskState;
    this.mState2 = paramTaskState;
  }

  private void retWinParams()
  {
    if (this.mWinParams != null)
      this.mWinParams.y = (-this.mWinParams.height);
  }

  private void setAnimationParam(boolean paramBoolean)
  {
    this.mStartTime = SystemClock.currentThreadTimeMillis();
    if (paramBoolean)
      this.mExpandAnimationRunning = true;
    while (true)
    {
      this.mAnimationLength = this.mWinParams.height;
      this.mStartY = this.mWinParams.y;
      WindowManager.LayoutParams localLayoutParams = this.mWinParams;
      localLayoutParams.flags = (0x10 | localLayoutParams.flags);
      this.wm.updateViewLayout(this.mContentView, this.mWinParams);
      return;
      this.mCollapseAnimationRunning = true;
    }
  }

  private void showToast(String paramString)
  {
    this.mHandler.post(new TaskGuide.3(this, paramString));
  }

  private void updateContentView(int paramInt)
  {
    if (this.mHandler != null)
      this.mHandler.post(new TaskGuide.1(this, paramInt));
  }

  public void removeWindow()
  {
    if (this.mAddedWindow)
    {
      this.wm.removeView(this.mContentView);
      this.mAddedWindow = false;
    }
  }

  public void showTaskGuideWindow(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    this.mContext = paramActivity;
    this.mListener = paramIUiListener;
    if ((this.mState1 == TaskGuide.TaskState.WAITTING_BACK_TASKINFO) || (this.mState2 == TaskGuide.TaskState.WAITTING_BACK_TASKINFO) || (this.mAddedWindow))
      return;
    this.mTaskInfo = null;
    Bundle localBundle;
    if (paramBundle != null)
    {
      localBundle = new Bundle(paramBundle);
      localBundle.putAll(composeCGIParams());
    }
    while (true)
    {
      TaskGuide.TaskRequestListener localTaskRequestListener = new TaskGuide.TaskRequestListener(this, null);
      localBundle.putString("action", "task_list");
      localBundle.putString("auth", "mobile");
      localBundle.putString("appid", this.mToken.getAppId());
      HttpUtils.requestAsync(this.mToken, this.mContext, "http://appact.qzone.qq.com/appstore_activity_task_pcpush_sdk", localBundle, "GET", localTaskRequestListener);
      moveToState(2, TaskGuide.TaskState.WAITTING_BACK_TASKINFO);
      return;
      localBundle = composeCGIParams();
    }
  }

  @SuppressLint({"ResourceAsColor"})
  public void showWindow()
  {
    new Handler(Looper.getMainLooper()).post(new TaskGuide.2(this));
    TencentStat.a(this.mContext, this.mToken, "TaskApi", new String[] { "showTaskWindow" });
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.TaskGuide
 * JD-Core Version:    0.6.0
 */