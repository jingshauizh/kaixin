package com.kaixin001.fragment;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

class HomePeopleInfoAdapter extends ArrayAdapter<HomePeopleInfo>
{
  public int mLayoutContent = -1;

  public HomePeopleInfoAdapter(Context paramContext, int paramInt, ArrayList<HomePeopleInfo> paramArrayList)
  {
    super(paramContext, paramInt, paramArrayList);
    this.mLayoutContent = paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    HomePeopleInfo localHomePeopleInfo = (HomePeopleInfo)getItem(paramInt);
    if (paramView == null)
      paramView = newView();
    CacheView localCacheView = (CacheView)paramView.getTag();
    localCacheView.mTvCaption.setText(localHomePeopleInfo.mCaption);
    localCacheView.mTvContent.setText(localHomePeopleInfo.mContent);
    if (localHomePeopleInfo.mShowArrow == 1)
    {
      localCacheView.mIvArrow.setImageResource(localHomePeopleInfo.mArrowResouce);
      if (localCacheView.mTvCaption.getText().equals(getContext().getString(2131428077)))
        break label141;
      localCacheView.mIvArrow.setVisibility(8);
    }
    while (true)
    {
      if (getCount() != 1)
        break label153;
      localCacheView.mLytHomePeopleInfo.setBackgroundResource(2130838335);
      return paramView;
      localCacheView.mIvArrow.setImageResource(0);
      break;
      label141: localCacheView.mIvArrow.setVisibility(0);
    }
    label153: if (paramInt == 0)
    {
      localCacheView.mLytHomePeopleInfo.setBackgroundResource(2130838338);
      return paramView;
    }
    if (paramInt == -1 + getCount())
    {
      localCacheView.mLytHomePeopleInfo.setBackgroundResource(2130837699);
      return paramView;
    }
    localCacheView.mLytHomePeopleInfo.setBackgroundResource(2130837700);
    return paramView;
  }

  public View newView()
  {
    CacheView localCacheView = new CacheView();
    View localView = ((Activity)getContext()).getLayoutInflater().inflate(this.mLayoutContent, null);
    localView.setTag(localCacheView);
    localCacheView.mTvCaption = ((TextView)localView.findViewById(2131362677));
    localCacheView.mTvContent = ((TextView)localView.findViewById(2131362678));
    localCacheView.mIvArrow = ((ImageView)localView.findViewById(2131362679));
    localCacheView.mLytHomePeopleInfo = localView.findViewById(2131362676);
    return localView;
  }

  static class CacheView
  {
    ImageView mIvArrow = null;
    View mLytHomePeopleInfo = null;
    TextView mTvCaption = null;
    TextView mTvContent = null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.HomePeopleInfoAdapter
 * JD-Core Version:    0.6.0
 */