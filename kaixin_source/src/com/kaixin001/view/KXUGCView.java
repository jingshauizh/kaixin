package com.kaixin001.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Matrix;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.kaixin001.util.KXLog;

public class KXUGCView extends FrameLayout
  implements View.OnClickListener
{
  public static final int ID_LBS = 0;
  public static final int ID_PHOTO = 2;
  public static final int ID_RECORD = 1;
  public static final int ID_VOICE = 3;
  private ImageView mBtnClose;
  private ImageView mBtnLbs;
  private ImageView mBtnOpen;
  private ImageView mBtnPhoto;
  private ImageView mBtnRecord;
  private Animation mBtnRotateDown;
  private Animation mBtnRotateUp;
  private ImageView mBtnVoice;
  private View mContentLayout;
  private int mDuration = 500;
  private Handler mHandler;
  private Animation mLayoutDown;
  private Animation mLayoutUp;
  private OnUGCItemClickListener mOnUGCItemClickListener;

  public KXUGCView(Context paramContext)
  {
    super(paramContext);
    initView(paramContext);
  }

  public KXUGCView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initView(paramContext);
  }

  public KXUGCView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initView(paramContext);
  }

  private void closeUgc()
  {
    this.mLayoutDown.setAnimationListener(new Animation.AnimationListener()
    {
      public void onAnimationEnd(Animation paramAnimation)
      {
        KXUGCView.this.mContentLayout.setVisibility(4);
        KXUGCView.this.mBtnClose.setVisibility(4);
        KXUGCView.this.mBtnOpen.setVisibility(0);
      }

      public void onAnimationRepeat(Animation paramAnimation)
      {
      }

      public void onAnimationStart(Animation paramAnimation)
      {
      }
    });
    this.mBtnClose.setBackgroundResource(2130839113);
    this.mBtnClose.startAnimation(this.mBtnRotateDown);
    this.mContentLayout.startAnimation(this.mLayoutDown);
  }

  private static int dipToPx(Context paramContext, float paramFloat)
  {
    return (int)(0.5F + paramFloat * paramContext.getResources().getDisplayMetrics().density);
  }

  private void initView(Context paramContext)
  {
    View localView = ((LayoutInflater)paramContext.getSystemService("layout_inflater")).inflate(2130903407, this);
    this.mBtnOpen = ((ImageView)localView.findViewById(2131363979));
    this.mBtnOpen.setOnClickListener(this);
    this.mBtnClose = ((ImageView)localView.findViewById(2131363980));
    this.mBtnClose.setOnClickListener(this);
    this.mContentLayout = localView.findViewById(2131363970);
    this.mBtnLbs = ((ImageView)localView.findViewById(2131363761));
    this.mBtnRecord = ((ImageView)localView.findViewById(2131363759));
    this.mBtnPhoto = ((ImageView)localView.findViewById(2131363976));
    this.mBtnVoice = ((ImageView)localView.findViewById(2131363758));
    localView.findViewById(2131363978).setOnClickListener(this);
    localView.findViewById(2131363973).setOnClickListener(this);
    localView.findViewById(2131363975).setOnClickListener(this);
    localView.findViewById(2131363971).setOnClickListener(this);
    int i = (getContext().getResources().getDisplayMetrics().widthPixels - dipToPx(getContext(), 212.0F)) / 12;
    this.mContentLayout.setPadding(i, 0, i, 0);
    this.mContentLayout.setVisibility(4);
    this.mBtnClose.setVisibility(4);
    float f1 = 0 - dipToPx(getContext(), 8.0F);
    this.mBtnRotateUp = new KXRotateAnimation(0.0F, f1, 0.0F, 675.0F, 1, 0.5F, 1, 0.5F);
    this.mBtnRotateUp.setDuration(this.mDuration);
    this.mBtnRotateDown = new KXRotateAnimation(0.0F, 0.0F - f1, 0.0F, 675.0F, 1, 0.5F, 1, 0.5F);
    this.mBtnRotateDown.setDuration(this.mDuration);
    float f2 = dipToPx(getContext(), 78.0F);
    this.mLayoutUp = new TranslateAnimation(0.0F, 0.0F, f2, 0.0F);
    this.mLayoutUp.setDuration(this.mDuration);
    this.mLayoutDown = new TranslateAnimation(0.0F, 0.0F, 0.0F, f2);
    this.mLayoutDown.setDuration(this.mDuration);
  }

  private void onClicked(int paramInt1, View paramView, int paramInt2, int paramInt3)
  {
    AlphaAnimation localAlphaAnimation1 = new AlphaAnimation(1.0F, 0.0F);
    localAlphaAnimation1.setDuration(this.mDuration / 2);
    localAlphaAnimation1.setAnimationListener(new Animation.AnimationListener(paramView, paramInt1)
    {
      public void onAnimationEnd(Animation paramAnimation)
      {
        KXUGCView.this.mContentLayout.setVisibility(4);
        KXUGCView.this.mBtnClose.setVisibility(4);
        this.val$view.setVisibility(0);
        if (KXUGCView.this.mOnUGCItemClickListener != null)
        {
          KXUGCView.this.mOnUGCItemClickListener.onUGCItemClick(this.val$id);
          KXUGCView.this.mHandler.postDelayed(new Runnable()
          {
            public void run()
            {
              KXUGCView.this.showOpenBtn();
            }
          }
          , 500L);
        }
      }

      public void onAnimationRepeat(Animation paramAnimation)
      {
      }

      public void onAnimationStart(Animation paramAnimation)
      {
      }
    });
    AnimationSet localAnimationSet = new AnimationSet(true);
    AlphaAnimation localAlphaAnimation2 = new AlphaAnimation(1.0F, 0.0F);
    localAlphaAnimation2.setDuration(this.mDuration / 2);
    localAnimationSet.addAnimation(localAlphaAnimation2);
    ScaleAnimation localScaleAnimation = new ScaleAnimation(1.0F, 1.5F, 1.0F, 1.5F, 1, 0.5F, 1, 0.5F);
    localScaleAnimation.setDuration(this.mDuration);
    localAnimationSet.addAnimation(localScaleAnimation);
    localAnimationSet.setAnimationListener(new Animation.AnimationListener(paramView, paramInt3, localAlphaAnimation1)
    {
      public void onAnimationEnd(Animation paramAnimation)
      {
        this.val$view.setBackgroundResource(this.val$defaultResId);
        this.val$view.setVisibility(4);
        KXUGCView.this.mContentLayout.startAnimation(this.val$contentAlpha);
        KXUGCView.this.mBtnClose.startAnimation(this.val$contentAlpha);
      }

      public void onAnimationRepeat(Animation paramAnimation)
      {
      }

      public void onAnimationStart(Animation paramAnimation)
      {
      }
    });
    paramView.setBackgroundResource(paramInt2);
    paramView.startAnimation(localAnimationSet);
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    case 2131363972:
    case 2131363974:
    case 2131363976:
    case 2131363977:
    default:
      return;
    case 2131363979:
      showUgc();
      return;
    case 2131363980:
      closeUgc();
      return;
    case 2131363978:
      onClicked(0, this.mBtnLbs, 2130839116, 2130837590);
      return;
    case 2131363973:
      onClicked(1, this.mBtnRecord, 2130839123, 2130837593);
      return;
    case 2131363975:
      onClicked(2, this.mBtnPhoto, 2130839126, 2130837592);
      return;
    case 2131363971:
    }
    onClicked(3, this.mBtnVoice, 2130839129, 2130837594);
  }

  public void setHander(Handler paramHandler)
  {
    this.mHandler = paramHandler;
  }

  public void setOnUGCItemClickListener(OnUGCItemClickListener paramOnUGCItemClickListener)
  {
    this.mOnUGCItemClickListener = paramOnUGCItemClickListener;
  }

  public void showOpenBtn()
  {
    this.mBtnOpen.setVisibility(0);
    this.mContentLayout.setVisibility(4);
    this.mBtnClose.setVisibility(4);
  }

  public void showUgc()
  {
    this.mLayoutUp.setAnimationListener(new Animation.AnimationListener()
    {
      public void onAnimationEnd(Animation paramAnimation)
      {
        KXUGCView.this.mContentLayout.setVisibility(0);
        KXUGCView.this.mBtnClose.setVisibility(0);
        KXUGCView.this.mBtnOpen.setVisibility(4);
        if (KXUGCView.this.mOnUGCItemClickListener != null)
          KXUGCView.this.mOnUGCItemClickListener.onUGCItemShow();
      }

      public void onAnimationRepeat(Animation paramAnimation)
      {
      }

      public void onAnimationStart(Animation paramAnimation)
      {
      }
    });
    this.mBtnClose.setBackgroundResource(2130837589);
    this.mBtnOpen.startAnimation(this.mBtnRotateUp);
    this.mContentLayout.startAnimation(this.mLayoutUp);
  }

  public class KXRotateAnimation extends RotateAnimation
  {
    private float mDeltaX;
    private float mDeltaY;
    private float mFromDegrees;
    private float mPivotX;
    private int mPivotXType = 0;
    private float mPivotXValue = 0.0F;
    private float mPivotY;
    private int mPivotYType = 0;
    private float mPivotYValue = 0.0F;
    private float mToDegrees;

    public KXRotateAnimation(float paramFloat1, float paramFloat2, float paramFloat3, float paramInt1, int paramFloat4, float paramInt2, int paramFloat5, float arg9)
    {
      super(paramInt1, paramFloat4, paramInt2, paramFloat5, localObject);
      this.mDeltaX = paramFloat1;
      this.mDeltaY = paramFloat2;
      this.mFromDegrees = paramFloat3;
      this.mToDegrees = paramInt1;
      this.mPivotXValue = paramInt2;
      this.mPivotXType = paramFloat4;
      this.mPivotYValue = localObject;
      this.mPivotYType = paramFloat5;
    }

    protected void applyTransformation(float paramFloat, Transformation paramTransformation)
    {
      float f1 = this.mFromDegrees + paramFloat * (this.mToDegrees - this.mFromDegrees);
      float f2 = paramFloat * this.mDeltaX;
      float f3 = paramFloat * this.mDeltaY;
      if ((this.mPivotX == 0.0F) && (this.mPivotY == 0.0F))
      {
        paramTransformation.getMatrix().setRotate(f1);
        return;
      }
      paramTransformation.getMatrix().postTranslate(f2, f3);
      KXLog.d("TESTAPP", "setTranslate(" + f2 + ", " + f3 + ")");
      paramTransformation.getMatrix().postRotate(f1, this.mPivotX, f3 + this.mPivotY);
    }

    public void initialize(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      super.initialize(paramInt1, paramInt2, paramInt3, paramInt4);
      this.mPivotX = resolveSize(this.mPivotXType, this.mPivotXValue, paramInt1, paramInt3);
      this.mPivotY = resolveSize(this.mPivotYType, this.mPivotYValue, paramInt2, paramInt4);
    }
  }

  public static abstract interface OnUGCItemClickListener
  {
    public abstract void onUGCItemClick(int paramInt);

    public abstract void onUGCItemShow();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXUGCView
 * JD-Core Version:    0.6.0
 */