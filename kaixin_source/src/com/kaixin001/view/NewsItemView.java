package com.kaixin001.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.kaixin001.activity.KXEnvironment;
import com.kaixin001.adapter.NewsItemAdapter;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.fragment.HomeFragment;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.model.User;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.ImageDownloader.RoundCornerType;

public abstract class NewsItemView extends LinearLayout
{
  public NewsItemAdapter adapter = null;
  public LayoutInflater inflater = null;
  public View logoView = null;
  public View mCommentInfoLayout;
  public Activity mContext = null;
  public View mDevideLine;
  public View mForwardInfoLayout;
  public BaseFragment mFragment = null;
  public ImageView mImgFlogo = null;
  public ImageView mImgForwardDividingIcon = null;
  public ImageView mImgForwardIcon = null;
  public ImageView mImgLocationIcon = null;
  public RelativeLayout mImgUpLayout = null;
  public RelativeLayout mLayoutComment = null;
  public View mLayoutLocation = null;
  public TextView mTextClientName = null;
  public TextView mTextComment = null;
  public TextView mTextForward = null;
  public TextView mTextLocation = null;
  public TextView mTextUp = null;
  public View rootView = null;
  public View toolsView = null;
  private String type = "";

  public NewsItemView(BaseFragment paramBaseFragment, NewsInfo paramNewsInfo, NewsItemAdapter paramNewsItemAdapter)
  {
    super(paramBaseFragment.getActivity());
    this.mFragment = paramBaseFragment;
    this.mContext = paramBaseFragment.getActivity();
    this.inflater = paramBaseFragment.getActivity().getLayoutInflater();
    this.type = paramNewsInfo.mNtype;
    this.adapter = paramNewsItemAdapter;
  }

  private void showLogo(NewsInfo paramNewsInfo)
  {
    if (this.logoView == null)
      this.logoView = this.rootView.findViewById(2131362586);
    if (this.logoView != null)
    {
      if (this.mImgFlogo == null)
        this.mImgFlogo = ((ImageView)this.logoView.findViewById(2131363221));
      this.mFragment.displayRoundPicture(this.mImgFlogo, paramNewsInfo.mFlogo, ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
      this.mImgFlogo.setTag(paramNewsInfo);
      this.mImgFlogo.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          if ((NewsItemView.this.mFragment != null) && ((NewsItemView.this.mFragment instanceof HomeFragment)))
            return;
          if (User.getInstance().GetLookAround())
          {
            NewsItemView.this.adapter.showLoginDialog();
            return;
          }
          NewsInfo localNewsInfo = (NewsInfo)paramView.getTag();
          Intent localIntent = new Intent(NewsItemView.this.mContext, HomeFragment.class);
          Bundle localBundle = new Bundle();
          localBundle.putString("fuid", localNewsInfo.mFuid);
          localBundle.putString("fname", localNewsInfo.mFname);
          localBundle.putString("flogo", localNewsInfo.mFlogo);
          localIntent.putExtras(localBundle);
          AnimationUtil.startFragment(NewsItemView.this.mFragment, localIntent, 1);
        }
      });
    }
  }

  public String getType()
  {
    return this.type;
  }

  public boolean isSaveFlowState()
  {
    return (!KXEnvironment.wifiEnabled()) && (KXEnvironment.saveFlowOpen());
  }

  public boolean isSaveFlowState(KXSaveFlowImageView paramKXSaveFlowImageView)
  {
    return (paramKXSaveFlowImageView != null) && (paramKXSaveFlowImageView.saveFlowState()) && (!KXEnvironment.wifiEnabled()) && (KXEnvironment.saveFlowOpen());
  }

  public boolean show(NewsInfo paramNewsInfo)
  {
    try
    {
      showLogo(paramNewsInfo);
      showTools(paramNewsInfo);
      return true;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public void showLocation(NewsInfo paramNewsInfo)
  {
    if (this.mLayoutLocation == null)
      this.mLayoutLocation = this.toolsView.findViewById(2131362662);
    if (this.mImgLocationIcon == null)
      this.mImgLocationIcon = ((ImageView)this.toolsView.findViewById(2131362663));
    if (this.mTextLocation == null)
      this.mTextLocation = ((TextView)this.toolsView.findViewById(2131362664));
    if ("1192".equals(paramNewsInfo.mNtype))
    {
      this.mLayoutLocation.setVisibility(8);
      return;
    }
    if (TextUtils.isEmpty(paramNewsInfo.mLocation))
    {
      this.mLayoutLocation.setVisibility(8);
      return;
    }
    this.mLayoutLocation.setVisibility(0);
    String str = paramNewsInfo.mLocation;
    if (str.startsWith("[|s|]"))
    {
      int i = str.indexOf("[|m|]");
      if (i > 0)
        str = str.substring("[|s|]".length(), i);
    }
    this.mTextLocation.setText(str);
  }

  protected void showTools(NewsInfo paramNewsInfo)
  {
    if (this.toolsView == null)
      this.toolsView = this.rootView.findViewById(2131362665);
    if (this.toolsView != null)
    {
      if (this.mTextClientName == null)
        this.mTextClientName = ((TextView)this.toolsView.findViewById(2131362667));
      if (this.mImgForwardIcon == null)
        this.mImgForwardIcon = ((ImageView)this.toolsView.findViewById(2131362675));
      if (this.mLayoutComment == null)
        this.mLayoutComment = ((RelativeLayout)this.toolsView.findViewById(2131363185));
      if (this.mTextComment == null)
        this.mTextComment = ((TextView)this.toolsView.findViewById(2131362671));
      if (this.mTextUp == null)
        this.mTextUp = ((TextView)this.toolsView.findViewById(2131362668));
      if (this.mImgUpLayout == null)
        this.mImgUpLayout = ((RelativeLayout)this.toolsView.findViewById(2131363186));
      if (this.mTextForward == null)
        this.mTextForward = ((TextView)this.toolsView.findViewById(2131362674));
      if (this.mImgForwardDividingIcon == null)
        this.mImgForwardDividingIcon = ((ImageView)this.toolsView.findViewById(2131362673));
      if (this.mCommentInfoLayout == null)
        this.mCommentInfoLayout = this.toolsView.findViewById(2131363189);
      if (this.mForwardInfoLayout == null)
        this.mForwardInfoLayout = this.toolsView.findViewById(2131363187);
      if (this.mDevideLine == null)
        this.mDevideLine = this.toolsView.findViewById(2131363190);
      showLocation(paramNewsInfo);
      if (TextUtils.isEmpty(paramNewsInfo.mSource))
        this.mTextClientName.setVisibility(4);
    }
    else
    {
      i = 8;
      if (!this.adapter.isShowComment(paramNewsInfo))
        break label704;
      if ((!"1018".equals(paramNewsInfo.mNtype)) || (TextUtils.isEmpty(paramNewsInfo.mTnum)) || (TextUtils.isEmpty(paramNewsInfo.mCnum)) || (TextUtils.isEmpty(paramNewsInfo.mUpnum)))
        break label596;
      this.mImgForwardIcon.setVisibility(0);
      this.mImgForwardDividingIcon.setVisibility(0);
      this.mTextForward.setVisibility(0);
      String str3 = paramNewsInfo.mTnum;
      if (Integer.parseInt(str3) > 99)
        str3 = "99+";
      this.mTextForward.setText(str3);
      String str4 = paramNewsInfo.mCnum;
      if (Integer.parseInt(str4) > 99)
        str4 = "99+";
      this.mTextComment.setText(str4);
      String str5 = paramNewsInfo.mUpnum;
      if (Integer.parseInt(str5) > 99)
        str5 = "99+";
      this.mTextUp.setText(str5);
    }
    for (int i = 0; ; i = 0)
    {
      label596: 
      do
      {
        this.mImgUpLayout.setTag(paramNewsInfo);
        this.mImgUpLayout.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            NewsInfo localNewsInfo = (NewsInfo)paramView.getTag();
            if (User.getInstance().GetLookAround())
            {
              NewsItemView.this.adapter.showLoginDialog();
              return;
            }
            NewsItemView.this.adapter.praiseIt(localNewsInfo);
          }
        });
        this.mLayoutComment.setVisibility(i);
        this.mDevideLine.setVisibility(i);
        this.mLayoutComment.setTag(paramNewsInfo);
        this.mLayoutComment.setOnClickListener(this.adapter.mCommentOnClickListener);
        this.mCommentInfoLayout.setTag(paramNewsInfo);
        this.mCommentInfoLayout.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            NewsItemView.this.adapter.onCommentLayoutClick(paramView, 0);
          }
        });
        this.mForwardInfoLayout.setTag(paramNewsInfo);
        this.mForwardInfoLayout.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            NewsItemView.this.adapter.onCommentLayoutClick(paramView, 1);
          }
        });
        return;
        this.mTextClientName.setVisibility(0);
        this.mTextClientName.setText(paramNewsInfo.mSource);
        break;
      }
      while ((TextUtils.isEmpty(paramNewsInfo.mUpnum)) || (TextUtils.isEmpty(paramNewsInfo.mCnum)));
      this.mImgForwardIcon.setVisibility(8);
      this.mImgForwardDividingIcon.setVisibility(8);
      this.mTextForward.setVisibility(8);
      String str1 = paramNewsInfo.mCnum;
      if (Integer.parseInt(str1) > 99)
        str1 = "99+";
      this.mTextComment.setText(str1);
      String str2 = paramNewsInfo.mUpnum;
      if (Integer.parseInt(str2) > 99)
        str2 = "99+";
      this.mTextUp.setText(str2);
    }
    label704: this.mLayoutComment.setVisibility(i);
    this.mLayoutComment.setOnClickListener(null);
    this.mLayoutComment.setTag(null);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.NewsItemView
 * JD-Core Version:    0.6.0
 */