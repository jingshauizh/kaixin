package com.kaixin001.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.model.EditPictureModel;
import com.kaixin001.model.User;
import com.kaixin001.util.BitmapFrameUtils;
import com.kaixin001.view.KXFrameImageView;
import com.kaixin001.view.KXMergeFrameImageView;
import com.kaixin001.view.KXTrimImageView;
import java.util.ArrayList;

public class IFWaterMarkFragment extends BaseFragment
  implements View.OnClickListener, DialogInterface.OnClickListener, ViewPager.OnPageChangeListener
{
  private WaterMarkViewPagerAdaper mAdapter;
  private Bitmap mBitmap;
  private TextView mBottomView;
  private ImageView mBtnRight;
  private boolean mHorizontalPic = false;
  private KXMergeFrameImageView mImageBack;
  private KXTrimImageView mImageView;
  private View mMark;
  private boolean mSaving = false;
  private int mSelectId = 0;
  private boolean mShowGuide = true;
  private boolean mShowTrim = true;
  private ImageView mTrimBtn;
  private ViewPager mViewPager;
  private ArrayList<Integer> mWatermarkList = new ArrayList();

  private float getHei(float paramFloat)
  {
    if (this.mHorizontalPic)
      return paramFloat * 3.0F / 4.0F;
    return paramFloat * 4.0F / 3.0F;
  }

  private void initData()
  {
    if (this.mHorizontalPic)
    {
      this.mWatermarkList.add(Integer.valueOf(2130839316));
      this.mWatermarkList.add(Integer.valueOf(2130839304));
      this.mWatermarkList.add(Integer.valueOf(2130839308));
      this.mWatermarkList.add(Integer.valueOf(2130839330));
      this.mWatermarkList.add(Integer.valueOf(2130839300));
      this.mWatermarkList.add(Integer.valueOf(2130839314));
      this.mWatermarkList.add(Integer.valueOf(2130839320));
      this.mWatermarkList.add(Integer.valueOf(2130839332));
      this.mWatermarkList.add(Integer.valueOf(2130839318));
      this.mWatermarkList.add(Integer.valueOf(2130839302));
      this.mWatermarkList.add(Integer.valueOf(2130839324));
      this.mWatermarkList.add(Integer.valueOf(2130839322));
      this.mWatermarkList.add(Integer.valueOf(2130839306));
      this.mWatermarkList.add(Integer.valueOf(2130839310));
      this.mWatermarkList.add(Integer.valueOf(2130839312));
      return;
    }
    this.mWatermarkList.add(Integer.valueOf(2130839315));
    this.mWatermarkList.add(Integer.valueOf(2130839303));
    this.mWatermarkList.add(Integer.valueOf(2130839307));
    this.mWatermarkList.add(Integer.valueOf(2130839329));
    this.mWatermarkList.add(Integer.valueOf(2130839299));
    this.mWatermarkList.add(Integer.valueOf(2130839313));
    this.mWatermarkList.add(Integer.valueOf(2130839319));
    this.mWatermarkList.add(Integer.valueOf(2130839331));
    this.mWatermarkList.add(Integer.valueOf(2130839317));
    this.mWatermarkList.add(Integer.valueOf(2130839301));
    this.mWatermarkList.add(Integer.valueOf(2130839323));
    this.mWatermarkList.add(Integer.valueOf(2130839321));
    this.mWatermarkList.add(Integer.valueOf(2130839305));
    this.mWatermarkList.add(Integer.valueOf(2130839309));
    this.mWatermarkList.add(Integer.valueOf(2130839311));
  }

  private void initTitle()
  {
    View localView = getView();
    localView.findViewById(2131362282).setBackgroundColor(-13750738);
    ((ImageView)localView.findViewById(2131362919)).setVisibility(8);
    this.mBtnRight = ((ImageView)localView.findViewById(2131362928));
    this.mBtnRight.setImageResource(2130838138);
    this.mBtnRight.setOnClickListener(this);
    ImageView localImageView = (ImageView)localView.findViewById(2131362914);
    localView.findViewById(2131362916).setVisibility(8);
    localView.findViewById(2131362917).setVisibility(0);
    localImageView.setOnClickListener(this);
    TextView localTextView = (TextView)localView.findViewById(2131362920);
    localTextView.setVisibility(0);
    localTextView.setText(2131428192);
  }

  private void initViews()
  {
    View localView = getView();
    initTitle();
    float f;
    if (this.mHorizontalPic)
      f = 10.7F;
    while (true)
    {
      int i = getResources().getDisplayMetrics().widthPixels - dipToPx(21.4F);
      int j = (int)getHei(i);
      this.mImageView = ((KXTrimImageView)localView.findViewById(2131362817));
      this.mImageView.setVisibility(8);
      this.mMark = localView.findViewById(2131362819);
      this.mMark.setVisibility(4);
      this.mHandler.postDelayed(new Runnable(localView, f, j, i)
      {
        public void run()
        {
          if (IFWaterMarkFragment.this.isNeedReturn());
          int i;
          View localView3;
          ViewGroup.LayoutParams localLayoutParams;
          do
          {
            return;
            View localView1 = this.val$view.findViewById(2131362816);
            View localView2 = this.val$view.findViewById(2131362820);
            i = IFWaterMarkFragment.this.dipToPx(this.val$offsetY) + (localView1.getHeight() - this.val$hei - localView2.getHeight() - IFWaterMarkFragment.this.dipToPx(this.val$offsetY)) / 2;
            IFWaterMarkFragment.this.mImageView.setFrame(IFWaterMarkFragment.this.dipToPx(10.7F), i, this.val$wid, this.val$hei, this.val$wid, this.val$hei);
            IFWaterMarkFragment.this.mImageView.initDefaultParams();
            IFWaterMarkFragment.this.mImageView.setOnClickListener(IFWaterMarkFragment.this);
            IFWaterMarkFragment.this.mImageView.setImageBitmap(IFWaterMarkFragment.this.mBitmap);
            IFWaterMarkFragment.this.mImageView.setVisibility(0);
            IFWaterMarkFragment.this.mMark.setVisibility(0);
            localView3 = this.val$view.findViewById(2131362818);
            localLayoutParams = localView3.getLayoutParams();
          }
          while (localLayoutParams == null);
          localLayoutParams.height = (i - IFWaterMarkFragment.this.dipToPx(this.val$offsetY));
          localView3.setLayoutParams(localLayoutParams);
        }
      }
      , 100L);
      this.mTrimBtn = ((ImageView)localView.findViewById(2131362821));
      this.mTrimBtn.setOnClickListener(this);
      ViewGroup.LayoutParams localLayoutParams1 = this.mMark.getLayoutParams();
      if (localLayoutParams1 != null)
      {
        localLayoutParams1.height = (j + dipToPx(f));
        this.mMark.setLayoutParams(localLayoutParams1);
      }
      this.mImageBack = ((KXMergeFrameImageView)localView.findViewById(2131362823));
      ViewGroup.LayoutParams localLayoutParams2 = this.mImageBack.getLayoutParams();
      if (localLayoutParams2 != null)
      {
        localLayoutParams2.height = (int)getHei(getResources().getDisplayMetrics().widthPixels);
        this.mImageBack.setLayoutParams(localLayoutParams2);
      }
      this.mViewPager = ((ViewPager)localView.findViewById(2131362824));
      this.mViewPager.setPageMargin(0);
      ViewGroup.LayoutParams localLayoutParams3 = this.mViewPager.getLayoutParams();
      if (localLayoutParams3 != null)
      {
        localLayoutParams3.height = (int)getHei(getResources().getDisplayMetrics().widthPixels);
        this.mViewPager.setLayoutParams(localLayoutParams3);
      }
      this.mBottomView = ((TextView)localView.findViewById(2131362744));
      this.mAdapter = new WaterMarkViewPagerAdaper();
      this.mViewPager.setAdapter(this.mAdapter);
      this.mViewPager.setOnPageChangeListener(this);
      return;
      f = 10.7F;
    }
  }

  private void onSaveClick()
  {
    if (this.mSaving)
      return;
    this.mSaving = true;
    BitmapDrawable localBitmapDrawable = (BitmapDrawable)getResources().getDrawable(((Integer)this.mWatermarkList.get(this.mSelectId)).intValue());
    if (localBitmapDrawable != null)
    {
      Bitmap localBitmap = BitmapFrameUtils.drawFrame(this.mBitmap, localBitmapDrawable);
      if (localBitmap != null)
      {
        EditPictureModel.setBimapCanvas(localBitmap);
        setResult(-1);
      }
    }
    finish();
  }

  private void trimPic()
  {
    int i = (int)this.mImageView.getFramePaddingX();
    int j = (int)this.mImageView.getFramePaddingY();
    int k = i + (int)this.mImageView.getFrameWidth();
    int m = j + (int)this.mImageView.getFrameHeight();
    this.mBitmap = BitmapFrameUtils.trimBitmap(this.mBitmap, this.mImageView.getImageMatrix(), i, j, k, m);
    EditPictureModel.setBimap(this.mBitmap);
    this.mShowTrim = false;
    updateVisiable();
    updateBottomText();
    this.mImageBack.setImageBitmap(this.mBitmap);
    this.mHandler.postDelayed(new Runnable()
    {
      public void run()
      {
        if (IFWaterMarkFragment.this.isNeedReturn())
          return;
        IFWaterMarkFragment.this.mImageBack.setframeChips(EditPictureModel.getFrameClip());
      }
    }
    , 10L);
    this.mShowGuide = getShowGuide(getActivity());
    setShowGuide(getActivity(), false);
    View localView = getView().findViewById(2131362825);
    if (this.mShowGuide);
    for (int n = 0; ; n = 8)
    {
      localView.setVisibility(n);
      localView.setOnClickListener(new View.OnClickListener(localView)
      {
        public void onClick(View paramView)
        {
          this.val$guideView.setVisibility(8);
        }
      });
      return;
    }
  }

  private void updateBottomText()
  {
    if (!this.mShowTrim)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append(1 + this.mSelectId);
      localStringBuffer.append("/");
      localStringBuffer.append(this.mWatermarkList.size());
      String str = localStringBuffer.toString();
      if ((this.mWatermarkList.size() > 9) && (this.mSelectId < 9))
        str = "0" + str;
      this.mBottomView.setText(str);
    }
  }

  private void updateVisiable()
  {
    int i = 8;
    View localView1 = getView();
    View localView2 = localView1.findViewById(2131362816);
    int j;
    label48: boolean bool2;
    label74: ImageView localImageView2;
    if (this.mShowTrim)
    {
      j = 0;
      localView2.setVisibility(j);
      View localView3 = localView1.findViewById(2131362822);
      if (!this.mShowTrim)
        break label112;
      localView3.setVisibility(i);
      ImageView localImageView1 = this.mBtnRight;
      boolean bool1 = this.mShowTrim;
      bool2 = false;
      if (!bool1)
        break label117;
      localImageView1.setEnabled(bool2);
      localImageView2 = this.mBtnRight;
      if (!this.mShowTrim)
        break label123;
    }
    label112: label117: label123: for (int k = 92; ; k = 255)
    {
      localImageView2.setAlpha(k);
      return;
      j = i;
      break;
      i = 0;
      break label48;
      bool2 = true;
      break label74;
    }
  }

  public boolean getShowGuide(Context paramContext)
  {
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    try
    {
      boolean bool = localSharedPreferences.getBoolean("water_mark_guide_" + User.getInstance().getUID(), true);
      return bool;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    if (paramInt == -1)
      finish();
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131362914:
      finish();
      return;
    case 2131362928:
      onSaveClick();
      return;
    case 2131362821:
    }
    trimPic();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mBitmap = EditPictureModel.getBimap();
    if (this.mBitmap == null)
      Toast.makeText(getActivity(), 2131427333, 0).show();
    do
      return;
    while (this.mBitmap.getWidth() <= this.mBitmap.getHeight());
    this.mHorizontalPic = true;
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903174, paramViewGroup, false);
  }

  public void onPageScrollStateChanged(int paramInt)
  {
  }

  public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
  {
  }

  public void onPageSelected(int paramInt)
  {
    this.mSelectId = paramInt;
    updateBottomText();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    initData();
    initViews();
    updateVisiable();
  }

  public void requestFinish()
  {
  }

  public void setShowGuide(Context paramContext, boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(paramContext).edit();
    localEditor.putBoolean("water_mark_guide_" + User.getInstance().getUID(), paramBoolean);
    localEditor.commit();
  }

  class WaterMarkViewPagerAdaper extends PagerAdapter
  {
    private ArrayList<View> mCacheViews = new ArrayList();

    WaterMarkViewPagerAdaper()
    {
    }

    private void cacheView(View paramView)
    {
      if (paramView != null)
        this.mCacheViews.add(paramView);
    }

    private View getCacheView()
    {
      if (this.mCacheViews.size() > 0)
        return (View)this.mCacheViews.remove(0);
      return null;
    }

    public void destroyItem(ViewGroup paramViewGroup, int paramInt, Object paramObject)
    {
      KXFrameImageView localKXFrameImageView = (KXFrameImageView)paramObject;
      if (localKXFrameImageView != null)
        cacheView(localKXFrameImageView);
      try
      {
        ((ViewPager)paramViewGroup).removeView(localKXFrameImageView);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }

    public int getCount()
    {
      return IFWaterMarkFragment.this.mWatermarkList.size();
    }

    public Object instantiateItem(ViewGroup paramViewGroup, int paramInt)
    {
      View localView = getCacheView();
      KXFrameImageView localKXFrameImageView;
      if (localView != null)
        localKXFrameImageView = (KXFrameImageView)localView;
      while (true)
      {
        localKXFrameImageView.setFrameResId(((Integer)IFWaterMarkFragment.this.mWatermarkList.get(paramInt)).intValue());
        localKXFrameImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        try
        {
          ((ViewPager)paramViewGroup).addView(localKXFrameImageView, 0);
          return localKXFrameImageView;
          localKXFrameImageView = new KXFrameImageView(IFWaterMarkFragment.this.getActivity());
          localKXFrameImageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
      return localKXFrameImageView;
    }

    public boolean isViewFromObject(View paramView, Object paramObject)
    {
      return paramView == paramObject;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.IFWaterMarkFragment
 * JD-Core Version:    0.6.0
 */