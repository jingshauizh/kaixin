package com.kaixin001.view;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.adapter.NewsItemAdapter;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.fragment.HomeFragment;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.model.User;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import java.util.ArrayList;

public abstract class NewsItemBaseView extends NewsItemView
{
  public static final int BUTTON_LEFT = 1;
  public static final int BUTTON_MID = 2;
  public static final int BUTTON_RIGHT = 3;
  public static final int CLICK_BOTTOM_BTN_LEFT = 1;
  public static final int CLICK_BOTTOM_BTN_MID = 2;
  public static final int CLICK_BOTTOM_BTN_RIGHT = 3;
  public static final int CLICK_TOP_VIEW_RIGHT;
  protected NewsItemAdapter adapter = null;
  public LayoutInflater inflater = null;
  private View mBottomBtnsLayout = null;
  private View mBottomLayout = null;
  private TextView mBtnLeft = null;
  private View mBtnLeftLayout = null;
  private TextView mBtnMid = null;
  private View mBtnMidLayout = null;
  private TextView mBtnRight = null;
  private View mBtnRightLayout = null;
  protected Activity mContext = null;
  protected BaseFragment mFragment = null;
  private ImageView mIconRight = null;
  protected NewsInfo mInfo;
  private View mLine1 = null;
  private View mLine2 = null;
  private KXFrameImageView mLogo = null;
  private boolean mShowSource = true;
  private TextView mSource = null;
  private TextView mTime = null;
  private KXIntroView mTitle = null;
  private View mTopLayout = null;
  private ImageView mTopRightBtn = null;
  public View rootView = null;
  private String type = "";

  public NewsItemBaseView(BaseFragment paramBaseFragment, NewsInfo paramNewsInfo, NewsItemAdapter paramNewsItemAdapter)
  {
    super(paramBaseFragment, paramNewsInfo, paramNewsItemAdapter);
    this.mFragment = paramBaseFragment;
    this.mContext = paramBaseFragment.getActivity();
    this.inflater = paramBaseFragment.getActivity().getLayoutInflater();
    this.type = paramNewsInfo.mNtype;
    this.mInfo = paramNewsInfo;
    this.adapter = paramNewsItemAdapter;
  }

  private void doClick(int paramInt, View paramView)
  {
    if (User.getInstance().GetLookAround())
    {
      this.adapter.showLoginDialog();
      return;
    }
    onClickView(paramInt, paramView);
  }

  private static String getLimitNum(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      paramString = "0";
    do
      return paramString;
    while (Integer.valueOf(paramString).intValue() <= 999);
    return "999+";
  }

  private void initView()
  {
    if (this.mTopLayout == null)
    {
      this.mTopLayout = this.rootView.findViewById(2131363192);
      this.mLogo = ((KXFrameImageView)this.mTopLayout.findViewById(2131363221));
      this.mTitle = ((KXIntroView)this.mTopLayout.findViewById(2131362592));
      this.mTopRightBtn = ((ImageView)this.mTopLayout.findViewById(2131363277));
      this.mTime = ((TextView)this.mTopLayout.findViewById(2131363276));
    }
    if (this.mBottomLayout == null)
    {
      this.mBottomLayout = this.rootView.findViewById(2131363200);
      this.mSource = ((TextView)this.rootView.findViewById(2131363204));
      this.mBottomBtnsLayout = this.mBottomLayout.findViewById(2131363205);
      this.mBtnLeft = ((TextView)this.mBottomLayout.findViewById(2131363207));
      this.mBtnLeftLayout = this.mBottomLayout.findViewById(2131363206);
      this.mBtnMid = ((TextView)this.mBottomLayout.findViewById(2131363210));
      this.mBtnMidLayout = this.mBottomLayout.findViewById(2131363209);
      this.mBtnRight = ((TextView)this.mBottomLayout.findViewById(2131363213));
      this.mBtnRightLayout = this.mBottomLayout.findViewById(2131363212);
      this.mIconRight = ((ImageView)this.mBottomLayout.findViewById(2131363214));
      this.mLine1 = this.mBottomLayout.findViewById(2131363208);
      this.mLine2 = this.mBottomLayout.findViewById(2131363211);
      this.mBtnLeftLayout.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          NewsItemBaseView.this.doClick(1, NewsItemBaseView.this.mBtnLeftLayout);
        }
      });
      this.mBtnMidLayout.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          NewsItemBaseView.this.doClick(2, NewsItemBaseView.this.mBtnMidLayout);
        }
      });
      this.mBtnRightLayout.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          NewsItemBaseView.this.doClick(3, NewsItemBaseView.this.mBtnRightLayout);
        }
      });
    }
  }

  private void setBottomButton(int paramInt1, int paramInt2, int paramInt3, String paramString, int paramInt4)
  {
    View localView;
    TextView localTextView;
    Drawable localDrawable;
    if (paramInt1 == 1)
    {
      localView = this.mBtnLeftLayout;
      localTextView = this.mBtnLeft;
      if (paramInt2 == 0)
        this.mBottomBtnsLayout.setVisibility(0);
      localView.setVisibility(paramInt2);
      localDrawable = null;
      if (paramInt3 > 0)
        localDrawable = getResources().getDrawable(paramInt3);
      if (paramInt1 != 3)
        break label154;
      if (localDrawable != null)
        break label134;
      this.mIconRight.setVisibility(8);
    }
    while (true)
    {
      if (!TextUtils.isEmpty(paramString))
        localTextView.setText(paramString);
      if (paramInt4 > 0)
        localTextView.setGravity(paramInt4);
      return;
      if (paramInt1 == 2)
      {
        localView = this.mBtnMidLayout;
        localTextView = this.mBtnMid;
        break;
      }
      localView = this.mBtnRightLayout;
      localTextView = this.mBtnRight;
      break;
      label134: this.mIconRight.setVisibility(0);
      this.mIconRight.setImageDrawable(localDrawable);
      continue;
      label154: localTextView.setCompoundDrawablesWithIntrinsicBounds(localDrawable, null, null, null);
    }
  }

  private void setBottomLineVisible(int paramInt1, int paramInt2)
  {
    if (paramInt1 == 0)
      this.mLine1.setVisibility(paramInt2);
    do
      return;
    while (paramInt1 != 1);
    this.mLine2.setVisibility(paramInt2);
  }

  public String getType()
  {
    return this.type;
  }

  protected void onClickView(int paramInt, View paramView)
  {
    switch (paramInt)
    {
    default:
      return;
    case 1:
      this.adapter.onCommentLayoutClick(this.mBtnLeftLayout, 0);
      return;
    case 2:
      this.adapter.onCommentLayoutClick(this.mBtnMidLayout, 1);
      return;
    case 3:
    }
    NewsInfo localNewsInfo;
    try
    {
      localNewsInfo = (NewsInfo)this.mBtnRightLayout.getTag();
      if (localNewsInfo.mHasUp.booleanValue())
      {
        Toast.makeText(this.mContext, 2131427845, 0).show();
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    localNewsInfo.mHasUp = Boolean.valueOf(true);
    this.adapter.notifyDataSetChanged();
    new Handler().postDelayed(new Runnable(localNewsInfo)
    {
      public void run()
      {
        this.val$info.mHasUp = Boolean.valueOf(false);
        NewsItemBaseView.this.adapter.praiseAnimation(NewsItemBaseView.this.mIconRight);
        NewsItemBaseView.this.adapter.praiseIt(this.val$info);
      }
    }
    , 100L);
  }

  public void setTopLayoutVisivle(boolean paramBoolean)
  {
    if (paramBoolean);
    for (int i = 0; ; i = 8)
    {
      this.mTopLayout.setVisibility(i);
      return;
    }
  }

  protected void setTopRightButton(int paramInt1, int paramInt2)
  {
    int i = 8;
    this.mTopRightBtn.setVisibility(paramInt1);
    if (paramInt2 > 0)
      this.mTopRightBtn.setBackgroundResource(paramInt2);
    while (true)
    {
      TextView localTextView = this.mTime;
      if (paramInt1 == i)
        i = 0;
      localTextView.setVisibility(i);
      return;
      this.mTopRightBtn.setBackgroundDrawable(null);
    }
  }

  public boolean show(NewsInfo paramNewsInfo)
  {
    initView();
    showTopLayout(paramNewsInfo, null);
    showBottomLayout(paramNewsInfo);
    return true;
  }

  protected void showBottomLayout(NewsInfo paramNewsInfo)
  {
    int j;
    if ((!this.mShowSource) || (TextUtils.isEmpty(paramNewsInfo.mSource)))
    {
      this.mSource.setVisibility(8);
      this.mBtnLeftLayout.setTag(paramNewsInfo);
      this.mBtnMidLayout.setTag(paramNewsInfo);
      this.mBtnRightLayout.setTag(paramNewsInfo);
      i = 8;
      if (!paramNewsInfo.mHasUp.booleanValue())
        break label219;
      j = 2130838665;
      label67: if (this.adapter.isShowComment(paramNewsInfo))
      {
        if ((!"1018".equals(paramNewsInfo.mNtype)) || (TextUtils.isEmpty(paramNewsInfo.mTnum)) || (TextUtils.isEmpty(paramNewsInfo.mCnum)) || (TextUtils.isEmpty(paramNewsInfo.mUpnum)))
          break label226;
        setBottomButton(1, 0, 2130838659, getLimitNum(paramNewsInfo.mCnum), -1);
        setBottomButton(2, 0, 2130838661, getLimitNum(paramNewsInfo.mTnum), -1);
        String str2 = getLimitNum(paramNewsInfo.mUpnum);
        setBottomButton(3, 0, j, str2, -1);
        setBottomLineVisible(0, 0);
        setBottomLineVisible(1, 0);
      }
    }
    for (int i = 0; ; i = 0)
    {
      label219: label226: 
      do
      {
        this.mBottomBtnsLayout.setVisibility(i);
        return;
        this.mSource.setVisibility(0);
        this.mSource.setText(paramNewsInfo.mSource);
        break;
        j = 2130838663;
        break label67;
      }
      while ((TextUtils.isEmpty(paramNewsInfo.mUpnum)) || (TextUtils.isEmpty(paramNewsInfo.mCnum)));
      setBottomButton(1, 0, 2130838659, getLimitNum(paramNewsInfo.mCnum), -1);
      setBottomButton(2, 8, -1, null, -1);
      String str1 = getLimitNum(paramNewsInfo.mUpnum);
      setBottomButton(3, 0, j, str1, -1);
      setBottomLineVisible(0, 0);
      setBottomLineVisible(1, 8);
    }
  }

  protected void showNoneButton()
  {
    setBottomButton(1, 8, -1, null, -1);
    setBottomButton(2, 8, -1, null, -1);
    setBottomButton(3, 8, -1, null, -1);
    setBottomLineVisible(0, 8);
    setBottomLineVisible(1, 8);
  }

  protected void showOneButton(int paramInt1, int paramInt2, String paramString, int paramInt3)
  {
    if (this.mBottomBtnsLayout.getVisibility() != 0)
      this.mBottomBtnsLayout.setVisibility(0);
    setBottomButton(1, 8, -1, null, -1);
    setBottomButton(3, 8, -1, null, -1);
    setBottomLineVisible(0, 8);
    setBottomLineVisible(1, 8);
    Drawable localDrawable1;
    if (paramInt1 > 0)
    {
      localDrawable1 = getResources().getDrawable(paramInt1);
      if (paramInt2 <= 0)
        break label147;
    }
    label147: for (Drawable localDrawable2 = getResources().getDrawable(paramInt2); ; localDrawable2 = null)
    {
      if (paramString == null)
        paramString = "";
      this.mBtnMid.setText(paramString);
      this.mBtnMid.setCompoundDrawablesWithIntrinsicBounds(localDrawable1, null, localDrawable2, null);
      if (paramInt3 > 0)
        this.mBtnMid.setGravity(paramInt3);
      this.mBtnMidLayout.setVisibility(0);
      this.mBtnMidLayout.setBackgroundResource(2130838685);
      return;
      localDrawable1 = null;
      break;
    }
  }

  public void showSource(boolean paramBoolean)
  {
    this.mShowSource = paramBoolean;
  }

  protected void showTopLayout(NewsInfo paramNewsInfo, String paramString)
  {
    this.mLogo.setFrameNinePatchResId(2130838099);
    this.mFragment.displayRoundPicture(this.mLogo, paramNewsInfo.mFlogo, ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
    this.mLogo.setTag(paramNewsInfo);
    if ((TextUtils.isEmpty(paramNewsInfo.mFuid)) && (("好友推荐".equals(paramNewsInfo.mFname)) || ("生日提醒".equals(paramNewsInfo.mFname))))
      this.mLogo.setOnClickListener(null);
    while (true)
    {
      this.mTime.setText(paramNewsInfo.mStime);
      this.mTopRightBtn.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          NewsItemBaseView.this.doClick(0, NewsItemBaseView.this.mTopRightBtn);
        }
      });
      KXIntroView.OnClickLinkListener localOnClickLinkListener = this.adapter.makeTitleClickListener(paramNewsInfo);
      String str = paramString;
      if (!TextUtils.isEmpty(str))
        str = " " + str;
      ArrayList localArrayList = this.adapter.getTitleKXLinkListAddName(paramNewsInfo, str);
      this.mTitle.setTitleList(localArrayList);
      this.mTitle.setOnClickLinkListener(localOnClickLinkListener);
      return;
      this.mLogo.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          if (User.getInstance().GetLookAround())
          {
            NewsItemBaseView.this.adapter.showLoginDialog();
            return;
          }
          if (User.getInstance().GetLookAround())
          {
            NewsItemBaseView.this.adapter.showLoginDialog();
            return;
          }
          NewsInfo localNewsInfo = (NewsInfo)paramView.getTag();
          Intent localIntent = new Intent(NewsItemBaseView.this.mContext, HomeFragment.class);
          Bundle localBundle = new Bundle();
          localBundle.putString("fuid", localNewsInfo.mFuid);
          localBundle.putString("fname", localNewsInfo.mFname);
          localBundle.putString("flogo", localNewsInfo.mFlogo);
          localIntent.putExtras(localBundle);
          AnimationUtil.startFragment(NewsItemBaseView.this.mFragment, localIntent, 1);
        }
      });
    }
  }

  protected void showTwoButtons(int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2, int paramInt4)
  {
    if (this.mBottomBtnsLayout.getVisibility() != 0)
      this.mBottomBtnsLayout.setVisibility(0);
    setBottomButton(1, 0, paramInt1, paramString1, paramInt2);
    setBottomButton(2, 8, -1, null, -1);
    setBottomButton(3, 0, paramInt3, paramString2, paramInt4);
    setBottomLineVisible(0, 0);
    setBottomLineVisible(1, 8);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.NewsItemBaseView
 * JD-Core Version:    0.6.0
 */