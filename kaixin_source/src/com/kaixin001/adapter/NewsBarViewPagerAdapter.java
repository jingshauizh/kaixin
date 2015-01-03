package com.kaixin001.adapter;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import java.util.List;

public class NewsBarViewPagerAdapter extends PagerAdapter
{
  List<View> views;

  public NewsBarViewPagerAdapter(List<View> paramList)
  {
    this.views = paramList;
  }

  public void destroyItem(View paramView, int paramInt, Object paramObject)
  {
    try
    {
      if ((paramInt >= this.views.size()) && (this.views.size() != 0))
      {
        int i = paramInt % this.views.size();
        paramInt = i;
      }
      if (paramInt < 0)
        (-paramInt);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void finishUpdate(View paramView)
  {
  }

  public int getCount()
  {
    if (getRealCount() == 1)
      return 1;
    return 2147483647;
  }

  public int getItemPosition(Object paramObject)
  {
    return -2;
  }

  public int getRealCount()
  {
    return this.views.size();
  }

  public Object instantiateItem(View paramView, int paramInt)
  {
    try
    {
      if (((ViewPager)paramView).getChildCount() == getRealCount())
        ((ViewPager)paramView).removeView((View)this.views.get(paramInt % getRealCount()));
      ((ViewPager)paramView).addView((View)this.views.get(paramInt % this.views.size()), 0);
      label70: if (this.views.size() == 0)
        return null;
      return this.views.get(paramInt % this.views.size());
    }
    catch (Exception localException)
    {
      break label70;
    }
  }

  public boolean isViewFromObject(View paramView, Object paramObject)
  {
    return paramView == paramObject;
  }

  public void restoreState(Parcelable paramParcelable, ClassLoader paramClassLoader)
  {
  }

  public Parcelable saveState()
  {
    return null;
  }

  public void startUpdate(View paramView)
  {
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.adapter.NewsBarViewPagerAdapter
 * JD-Core Version:    0.6.0
 */