package com.kaixin001.ugc;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.kaixin001.db.ConfigDBAdapter;
import com.kaixin001.model.User;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;

public class UGCMenuLayout extends KXComponentBase
  implements View.OnClickListener
{
  private static int BUTTON_SIZE = 0;
  private static int CENTER_X = 0;
  private static int CENTER_Y = 0;
  private static int RADIUS_A = 0;
  private static int RADIUS_B = 0;
  private static int START_MARGIN_X = 0;
  private static final String TAG = "MenuLayout";
  private static boolean isAnimating = false;
  private static final int mTextAnimationTime = 200;
  private float currentFactor = 1.0F;
  private Handler mHandler;
  private MenuClickListener mListener;
  FitwidthImageView menuBackground;
  RelativeLayout menuHolder;
  ImageButton menuTrigger;

  static
  {
    CENTER_X = 370;
    CENTER_Y = -132;
    START_MARGIN_X = 20;
    BUTTON_SIZE = 44;
    isAnimating = false;
  }

  public UGCMenuLayout(Context paramContext, int paramInt, Handler paramHandler)
  {
    super(paramContext, paramInt);
    this.mHandler = paramHandler;
  }

  public UGCMenuLayout(Context paramContext, View paramView, Handler paramHandler)
  {
    super(paramContext, paramView);
    this.mHandler = paramHandler;
  }

  private boolean getVoiceIconClicked()
  {
    return "1".equals(ConfigDBAdapter.getConfig(this.mContext, User.getInstance().getUID(), "voice:android-3.9.9"));
  }

  public static void resetAnimationState()
  {
    isAnimating = false;
  }

  private void setVoiceIconClicked()
  {
    ConfigDBAdapter.setConfig(this.mContext, User.getInstance().getUID(), "voice:android-3.9.9", "1", "");
  }

  private void toggyMenues()
  {
    if (this.menuBackground.getVisibility() == 0)
    {
      if (this.mHandler != null)
        this.mHandler.postDelayed(new Runnable()
        {
          public void run()
          {
            if (UGCMenuLayout.this.mContext == null)
              return;
            UGCMenuLayout.MenuButtonAnimation.startAnimations(UGCMenuLayout.this.menuHolder, InOutAnimation.Direction.OUT);
          }
        }
        , 200L);
      return;
    }
    MenuButtonAnimation.setMenuButtonListener(new Animation.AnimationListener()
    {
      public void onAnimationEnd(Animation paramAnimation)
      {
      }

      public void onAnimationRepeat(Animation paramAnimation)
      {
      }

      public void onAnimationStart(Animation paramAnimation)
      {
      }
    });
    MenuButtonAnimation.startAnimations(this.menuHolder, InOutAnimation.Direction.IN);
  }

  public boolean ensureHideMenu()
  {
    KXLog.d("menu", "ensureHideMenu");
    if (this.menuBackground.getVisibility() == 0)
    {
      if (this.mHandler != null)
        this.mHandler.postDelayed(new Runnable()
        {
          public void run()
          {
            if (UGCMenuLayout.this.mContext == null)
              return;
            UGCMenuLayout.MenuButtonAnimation.startAnimations(UGCMenuLayout.this.menuHolder, InOutAnimation.Direction.OUT);
          }
        }
        , 200L);
      return true;
    }
    return false;
  }

  public boolean ensureShowMenu()
  {
    if (this.menuBackground.getVisibility() != 0)
    {
      triggerMenu();
      return true;
    }
    return false;
  }

  public void init(Context paramContext, View paramView)
  {
    RADIUS_A = paramContext.getResources().getDimensionPixelSize(2131296263);
    RADIUS_B = paramContext.getResources().getDimensionPixelSize(2131296264);
    CENTER_X = paramContext.getResources().getDimensionPixelSize(2131296265);
    CENTER_Y = paramContext.getResources().getDimensionPixelSize(2131296266);
    START_MARGIN_X = paramContext.getResources().getDimensionPixelSize(2131296267);
    BUTTON_SIZE = paramContext.getResources().getDimensionPixelSize(2131296268);
    this.menuHolder = ((RelativeLayout)paramView.findViewById(2131363748));
    this.menuBackground = ((FitwidthImageView)this.menuHolder.findViewById(2131363749));
    updateIcons(paramView);
    this.menuTrigger.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
      {
        if (paramMotionEvent.getAction() == 0)
          UGCMenuLayout.this.triggerMenu();
        return false;
      }
    });
    MenuClickListenerInternal localMenuClickListenerInternal = null;
    int i = this.menuHolder.getChildCount();
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        paramView.setOnTouchListener(new View.OnTouchListener()
        {
          public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
          {
            return (paramMotionEvent.getAction() == 0) && (UGCMenuLayout.this.ensureHideMenu());
          }
        });
        return;
      }
      View localView = this.menuHolder.getChildAt(j);
      if (!(localView instanceof InOutImageButton))
        continue;
      if (localMenuClickListenerInternal == null)
        localMenuClickListenerInternal = new MenuClickListenerInternal(null);
      localView.setOnClickListener(localMenuClickListenerInternal);
    }
  }

  public void onClick(View paramView)
  {
    triggerMenu();
  }

  public void setOnMenuClickListener(MenuClickListener paramMenuClickListener)
  {
    this.mListener = paramMenuClickListener;
  }

  public void triggerMenu()
  {
    if (isAnimating)
      return;
    float f = this.menuBackground.getScaleFactor();
    int i;
    int j;
    if (this.currentFactor != f)
    {
      this.currentFactor = f;
      RADIUS_A = (int)(RADIUS_A * this.currentFactor);
      RADIUS_B = (int)(RADIUS_B * this.currentFactor);
      CENTER_X = (int)(CENTER_X * this.currentFactor);
      CENTER_Y = (int)(CENTER_Y * this.currentFactor);
      START_MARGIN_X = (int)(START_MARGIN_X * this.currentFactor);
      i = this.menuHolder.getChildCount();
      j = 0;
      if (j < i);
    }
    else
    {
      toggyMenues();
      return;
    }
    View localView = this.menuHolder.getChildAt(j);
    ViewGroup.MarginLayoutParams localMarginLayoutParams;
    Point localPoint;
    if ((localView instanceof InOutImageButton))
    {
      localMarginLayoutParams = (ViewGroup.MarginLayoutParams)localView.getLayoutParams();
      localPoint = new Point(localMarginLayoutParams.leftMargin, localMarginLayoutParams.bottomMargin);
      if (localView.getTag() == null)
        break label258;
      localPoint = (Point)localView.getTag();
      label179: localPoint.x = (int)(f * localPoint.x);
      localPoint.y = (int)(f * localPoint.y);
      if (this.currentFactor <= 1.0F)
        break label268;
      localPoint.y = (i + localPoint.y);
    }
    while (true)
    {
      localMarginLayoutParams.setMargins(localPoint.x, 0, 0, localPoint.y);
      localView.setLayoutParams(localMarginLayoutParams);
      j++;
      break;
      label258: localView.setTag(localPoint);
      break label179;
      label268: if (this.currentFactor >= 1.0F)
        continue;
      localPoint.y -= i - 2 - j;
    }
  }

  public void updateIcons(View paramView)
  {
    if (!getVoiceIconClicked())
    {
      ((ImageButton)paramView.findViewById(2131363756)).setBackgroundResource(2130839138);
      this.menuTrigger.setBackgroundResource(2130839144);
      return;
    }
    ((ImageButton)paramView.findViewById(2131363756)).setBackgroundResource(2130839137);
    this.menuTrigger.setBackgroundResource(2130839143);
  }

  public static class MenuButtonAnimation extends InOutAnimation
  {
    private static final int DURATION_IN = 300;
    private static final int DURATION_OUT = 250;
    private static Animation.AnimationListener mListener;

    public MenuButtonAnimation(InOutAnimation.Direction paramDirection, long paramLong, View paramView)
    {
      super(paramLong, paramView);
    }

    private static AnimationSet scaleFadeInOut(boolean paramBoolean)
    {
      float f;
      if (paramBoolean)
        f = 0.3F;
      while (true)
      {
        AnimationSet localAnimationSet = new AnimationSet(true);
        AlphaAnimation localAlphaAnimation = new AlphaAnimation(1.0F, 0.0F);
        localAlphaAnimation.setDuration(250L);
        localAnimationSet.addAnimation(localAlphaAnimation);
        ScaleAnimation localScaleAnimation = new ScaleAnimation(1.0F, f, 1.0F, f, 1, 0.5F, 1, 0.5F);
        localScaleAnimation.setDuration(250L);
        localAnimationSet.addAnimation(localScaleAnimation);
        return localAnimationSet;
        f = 2.0F;
      }
    }

    public static void setMenuButtonListener(Animation.AnimationListener paramAnimationListener)
    {
      mListener = paramAnimationListener;
    }

    public static void startAnimations(ViewGroup paramViewGroup, InOutAnimation.Direction paramDirection)
    {
      startAnimations(paramViewGroup, paramDirection, null);
    }

    public static void startAnimations(ViewGroup paramViewGroup, InOutAnimation.Direction paramDirection, View paramView)
    {
      if (paramDirection == InOutAnimation.Direction.IN)
        startAnimationsIn(paramViewGroup);
      do
        return;
      while (paramDirection != InOutAnimation.Direction.OUT);
      if (paramView == null)
      {
        startAnimationsOut(paramViewGroup);
        return;
      }
      startAnimationsOutWhenClicked(paramViewGroup, paramView);
    }

    private static void startAnimationsIn(ViewGroup paramViewGroup)
    {
      int i = paramViewGroup.getChildCount();
      int j = 0;
      int k = 0;
      if (k >= i)
        return;
      View localView = paramViewGroup.getChildAt(k);
      if ((localView instanceof InOutImageButton))
      {
        InOutImageButton localInOutImageButton = (InOutImageButton)localView;
        MenuButtonAnimation localMenuButtonAnimation = new MenuButtonAnimation(InOutAnimation.Direction.IN, 150L, localInOutImageButton);
        localMenuButtonAnimation.setInterpolator(new OvershootInterpolator(2.0F - 0.2F * j));
        localInOutImageButton.startAnimation(localMenuButtonAnimation);
        localMenuButtonAnimation.setStartOffset(150L);
        j++;
      }
      while (true)
      {
        k++;
        break;
        if ((localView instanceof ImageButton))
        {
          RotateAnimation localRotateAnimation = new RotateAnimation(0.0F, 360.0F, 1, 0.5F, 1, 0.5F);
          localRotateAnimation.setDuration(300L);
          localView.startAnimation(localRotateAnimation);
          continue;
        }
        if (!(localView instanceof ImageView))
          continue;
        AlphaAnimation localAlphaAnimation = new AlphaAnimation(0.0F, 1.0F);
        localAlphaAnimation.setDuration(300L);
        localView.startAnimation(localAlphaAnimation);
        localView.setVisibility(0);
        localAlphaAnimation.setAnimationListener(new Animation.AnimationListener()
        {
          public void onAnimationEnd(Animation paramAnimation)
          {
            UGCMenuLayout.isAnimating = false;
          }

          public void onAnimationRepeat(Animation paramAnimation)
          {
          }

          public void onAnimationStart(Animation paramAnimation)
          {
            UGCMenuLayout.isAnimating = true;
          }
        });
      }
    }

    private static void startAnimationsOut(ViewGroup paramViewGroup)
    {
      int i = paramViewGroup.getChildCount();
      int j = 0;
      int k = i - 1;
      if (k < 0)
        return;
      View localView = paramViewGroup.getChildAt(k);
      if ((localView instanceof InOutImageButton))
      {
        InOutImageButton localInOutImageButton = (InOutImageButton)localView;
        localInOutImageButton.startAnimation(new MenuButtonAnimation(InOutAnimation.Direction.OUT, 250L, localInOutImageButton));
        j++;
      }
      while (true)
      {
        k--;
        break;
        if ((localView instanceof ImageButton))
        {
          RotateAnimation localRotateAnimation = new RotateAnimation(360.0F, 0.0F, 1, 0.5F, 1, 0.5F);
          localRotateAnimation.setDuration(250L);
          localView.startAnimation(localRotateAnimation);
          continue;
        }
        if (!(localView instanceof ImageView))
          continue;
        AlphaAnimation localAlphaAnimation = new AlphaAnimation(1.0F, 0.0F);
        localAlphaAnimation.setDuration(250L);
        localAlphaAnimation.setStartOffset(125L);
        localView.startAnimation(localAlphaAnimation);
        localView.setVisibility(8);
        localAlphaAnimation.setAnimationListener(new Animation.AnimationListener()
        {
          public void onAnimationEnd(Animation paramAnimation)
          {
            UGCMenuLayout.isAnimating = false;
          }

          public void onAnimationRepeat(Animation paramAnimation)
          {
          }

          public void onAnimationStart(Animation paramAnimation)
          {
            UGCMenuLayout.isAnimating = true;
          }
        });
      }
    }

    private static void startAnimationsOutWhenClicked(ViewGroup paramViewGroup, View paramView)
    {
      int i = -1 + paramViewGroup.getChildCount();
      if (i < 0)
        return;
      View localView = paramViewGroup.getChildAt(i);
      if ((localView instanceof InOutImageButton))
        if (localView.equals(paramView))
        {
          localView.startAnimation(scaleFadeInOut(false));
          label41: localView.setVisibility(8);
        }
      while (true)
      {
        i--;
        break;
        localView.startAnimation(scaleFadeInOut(true));
        break label41;
        if (((localView instanceof ImageButton)) || (!(localView instanceof ImageView)))
          continue;
        AlphaAnimation localAlphaAnimation = new AlphaAnimation(1.0F, 0.0F);
        localAlphaAnimation.setDuration(250L);
        localAlphaAnimation.setStartOffset(125L);
        localView.startAnimation(localAlphaAnimation);
        localView.setVisibility(8);
        localAlphaAnimation.setAnimationListener(new Animation.AnimationListener()
        {
          public void onAnimationEnd(Animation paramAnimation)
          {
            UGCMenuLayout.isAnimating = false;
          }

          public void onAnimationRepeat(Animation paramAnimation)
          {
          }

          public void onAnimationStart(Animation paramAnimation)
          {
            UGCMenuLayout.isAnimating = true;
          }
        });
      }
    }

    protected void addInAnimation(View paramView)
    {
      float f1 = (float)Math.acos((((ViewGroup.MarginLayoutParams)paramView.getLayoutParams()).leftMargin - UGCMenuLayout.CENTER_X) / UGCMenuLayout.RADIUS_A);
      float f2 = (float)Math.acos((UGCMenuLayout.START_MARGIN_X - UGCMenuLayout.CENTER_X) / UGCMenuLayout.RADIUS_A);
      EllipseAnimation localEllipseAnimation = new EllipseAnimation(UGCMenuLayout.RADIUS_A, UGCMenuLayout.RADIUS_B, UGCMenuLayout.CENTER_X, UGCMenuLayout.CENTER_Y, f2, f1, f1, 0.0F, 360.0F);
      localEllipseAnimation.setDuration(225L);
      localEllipseAnimation.setStartOffset(75L);
      localEllipseAnimation.setAnimationListener(mListener);
      AlphaAnimation localAlphaAnimation = new AlphaAnimation(0.0F, 1.0F);
      localAlphaAnimation.setDuration(150L);
      addAnimation(localAlphaAnimation);
      addAnimation(localEllipseAnimation);
    }

    protected void addOutAnimation(View paramView)
    {
      RotateAnimation localRotateAnimation = new RotateAnimation(360.0F, 0.0F, 1, 0.5F, 1, 0.5F);
      localRotateAnimation.setDuration(187L);
      float f1 = (float)Math.acos((((ViewGroup.MarginLayoutParams)paramView.getLayoutParams()).leftMargin - UGCMenuLayout.CENTER_X) / UGCMenuLayout.RADIUS_A);
      float f2 = (float)Math.acos((UGCMenuLayout.START_MARGIN_X - UGCMenuLayout.CENTER_X) / UGCMenuLayout.RADIUS_A);
      EllipseAnimation localEllipseAnimation = new EllipseAnimation(UGCMenuLayout.RADIUS_A, UGCMenuLayout.RADIUS_B, UGCMenuLayout.CENTER_X, UGCMenuLayout.CENTER_Y, f1, f2, f1, 360.0F, 0.0F);
      localEllipseAnimation.setDuration(62L);
      localEllipseAnimation.setStartOffset(187L);
      AlphaAnimation localAlphaAnimation = new AlphaAnimation(1.0F, 0.0F);
      localAlphaAnimation.setDuration(62L);
      localAlphaAnimation.setStartOffset(187L);
      addAnimation(localRotateAnimation);
      addAnimation(localEllipseAnimation);
      addAnimation(localAlphaAnimation);
      setAnimationListener(new Animation.AnimationListener()
      {
        public void onAnimationEnd(Animation paramAnimation)
        {
          UGCMenuLayout.isAnimating = false;
        }

        public void onAnimationRepeat(Animation paramAnimation)
        {
        }

        public void onAnimationStart(Animation paramAnimation)
        {
          UGCMenuLayout.isAnimating = true;
        }
      });
    }

    public static class EllipseAnimation extends Animation
    {
      private float mCenterX;
      private float mCenterY;
      private float mFromDegrees;
      private float mRotateFrom;
      private float mRotateTo;
      private float mToDegrees;
      private PointF ptTarget;
      private float xRadius;
      private float yRadius;

      public EllipseAnimation(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9)
      {
        this.xRadius = paramFloat1;
        this.yRadius = paramFloat2;
        this.mCenterX = paramFloat3;
        this.mCenterY = paramFloat4;
        this.mFromDegrees = paramFloat5;
        this.mToDegrees = paramFloat6;
        this.ptTarget = UGCMenuLayout.MenuButtonAnimation.EllipseHelper.getPosition(this.xRadius, this.yRadius, paramFloat3, paramFloat4, paramFloat7);
        this.mRotateFrom = paramFloat8;
        this.mRotateTo = paramFloat9;
      }

      protected void applyTransformation(float paramFloat, Transformation paramTransformation)
      {
        float f1 = this.mFromDegrees;
        float f2 = f1 + paramFloat * (this.mToDegrees - f1);
        float f3 = this.mRotateFrom + paramFloat * (this.mRotateTo - this.mRotateFrom);
        float f4 = this.mCenterX;
        float f5 = this.mCenterY;
        paramTransformation.clear();
        float f6;
        if (this.mRotateTo > this.mRotateFrom)
          f6 = Math.min(f3, this.mRotateTo);
        while (true)
        {
          KXLog.d("MenuLayout", "menu_rotation:" + f6);
          PointF localPointF = UGCMenuLayout.MenuButtonAnimation.EllipseHelper.getPosition(this.xRadius, this.yRadius, f4, f5, f2);
          Matrix localMatrix = paramTransformation.getMatrix();
          localMatrix.setRotate(f6, UGCMenuLayout.BUTTON_SIZE / 2.0F, UGCMenuLayout.BUTTON_SIZE / 2.0F);
          localMatrix.postTranslate(localPointF.x - this.ptTarget.x, this.ptTarget.y - localPointF.y);
          return;
          f6 = Math.max(f3, this.mRotateTo);
        }
      }

      public void initialize(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
      {
        super.initialize(paramInt1, paramInt2, paramInt3, paramInt4);
      }
    }

    public static class EllipseHelper
    {
      public static PointF getPosition(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
      {
        PointF localPointF = new PointF();
        localPointF.x = (paramFloat3 + (float)(paramFloat1 * Math.cos(paramFloat5)));
        localPointF.y = (paramFloat4 + (float)(paramFloat2 * Math.sin(paramFloat5)));
        return localPointF;
      }

      public static ArrayList<EllipsePointInfo> getPositions(float paramFloat1, float paramFloat2, double paramDouble, int paramInt)
      {
        ArrayList localArrayList = null;
        double d;
        if (paramInt > 0)
        {
          localArrayList = new ArrayList();
          d = 6.283185307179586D / paramInt;
        }
        for (int i = 0; ; i++)
        {
          if (i >= paramInt)
            return localArrayList;
          float f = (float)(paramDouble + d * i);
          localArrayList.add(new EllipsePointInfo(getPosition(paramFloat1, paramFloat2, 0.0F, 0.0F, f), f));
        }
      }

      static class EllipsePointInfo
      {
        float angle;
        PointF point;

        public EllipsePointInfo(PointF paramPointF, float paramFloat)
        {
          this.point = paramPointF;
          this.angle = paramFloat;
        }
      }
    }
  }

  public static abstract interface MenuClickListener
  {
    public abstract void onMenuItemClicked(UGCMenuLayout.MenuItem paramMenuItem);
  }

  private class MenuClickListenerInternal
    implements View.OnClickListener
  {
    private MenuClickListenerInternal()
    {
    }

    public void onClick(View paramView)
    {
      if (paramView.getId() == 2131363756)
        UGCMenuLayout.this.setVoiceIconClicked();
      if (!UGCMenuLayout.isAnimating)
      {
        if (UGCMenuLayout.this.mListener != null)
          paramView.postDelayed(new Runnable(paramView)
          {
            public void run()
            {
              if (UGCMenuLayout.this.mContext == null)
                return;
              UGCMenuLayout.this.mListener.onMenuItemClicked(UGCMenuLayout.MenuItem.valueOf(this.val$v.getId()));
            }
          }
          , 450L);
        if (UGCMenuLayout.this.mHandler != null)
          UGCMenuLayout.this.mHandler.postDelayed(new Runnable(paramView)
          {
            public void run()
            {
              if (UGCMenuLayout.this.mContext == null)
                return;
              UGCMenuLayout.MenuButtonAnimation.startAnimations(UGCMenuLayout.this.menuHolder, InOutAnimation.Direction.OUT, this.val$v);
            }
          }
          , 200L);
      }
    }
  }

  public static enum MenuItem
  {
    static
    {
      LBS = new MenuItem("LBS", 1);
      VOICE = new MenuItem("VOICE", 2);
      RECORD = new MenuItem("RECORD", 3);
      DIARY = new MenuItem("DIARY", 4);
      MenuItem[] arrayOfMenuItem = new MenuItem[5];
      arrayOfMenuItem[0] = PHOTO;
      arrayOfMenuItem[1] = LBS;
      arrayOfMenuItem[2] = VOICE;
      arrayOfMenuItem[3] = RECORD;
      arrayOfMenuItem[4] = DIARY;
      ENUM$VALUES = arrayOfMenuItem;
    }

    static MenuItem valueOf(int paramInt)
    {
      switch (paramInt)
      {
      case 2131363751:
      case 2131363753:
      case 2131363755:
      default:
        return null;
      case 2131363754:
        return PHOTO;
      case 2131363750:
        return LBS;
      case 2131363756:
        return VOICE;
      case 2131363752:
      }
      return RECORD;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.ugc.UGCMenuLayout
 * JD-Core Version:    0.6.0
 */