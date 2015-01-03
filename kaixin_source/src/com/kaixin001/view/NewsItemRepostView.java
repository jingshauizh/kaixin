package com.kaixin001.view;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.kaixin001.adapter.NewsItemAdapter;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.fragment.FilmDetailFragment;
import com.kaixin001.fragment.FilmListFragment;
import com.kaixin001.fragment.RepostDetailFragment;
import com.kaixin001.item.FilmItem;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.item.RepItem;
import com.kaixin001.item.VoteItem;
import com.kaixin001.model.NewsModel;
import com.kaixin001.model.User;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.KXLog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewsItemRepostView extends NewsItemView
{
  private static final String TAG = "NewsItemRepostView";
  private NewsInfo info = null;
  public ImageView mItemTypeIcon1 = null;
  public ImageView mItemTypeIcon2 = null;
  public ImageView mItemTypeIcon3 = null;
  public ImageView mItemTypeIcon4 = null;
  public ImageView mItemTypeIcon5 = null;
  public ImageView mItemTypeIcon6 = null;
  public LinearLayout mLayoutRepost1 = null;
  public LinearLayout mLayoutRepost2 = null;
  public LinearLayout mLayoutRepost3 = null;
  public LinearLayout mLayoutRepost4 = null;
  public LinearLayout mLayoutRepost5 = null;
  public LinearLayout mLayoutRepost6 = null;
  public KXIntroView mNewsItemText = null;
  public TextView mTextReleaseTime = null;
  public TextView mTextRepost = null;
  public TextView mTextRepost1 = null;
  public TextView mTextRepost2 = null;
  public TextView mTextRepost3 = null;
  public TextView mTextRepost4 = null;
  public TextView mTextRepost5 = null;
  public TextView mTextRepost6 = null;
  public TextView mTextRepostExt = null;
  public TextView mTextRepostExt1 = null;
  public TextView mTextRepostExt2 = null;
  public TextView mTextRepostExt3 = null;
  public TextView mTextRepostExt4 = null;
  public TextView mTextRepostExt5 = null;
  public TextView mTextRepostExt6 = null;
  public ImageView mTextRepostImg = null;
  public ImageView mTextRepostImg1 = null;
  public ImageView mTextRepostImg2 = null;
  public ImageView mTextRepostImg3 = null;
  public ImageView mTextRepostImg4 = null;
  public ImageView mTextRepostImg5 = null;
  public KXFrameImageView mThumbImg1 = null;
  public KXFrameImageView mThumbImg2 = null;
  public KXFrameImageView mThumbImg3 = null;
  public KXFrameImageView mThumbImg4 = null;
  public KXFrameImageView mThumbImg5 = null;
  public KXFrameImageView mThumbImg6 = null;
  public View mThumbLayout1 = null;
  public View mThumbLayout2 = null;
  public View mThumbLayout3 = null;
  public View mThumbLayout4 = null;
  public View mThumbLayout5 = null;
  public View mThumbLayout6 = null;
  private TextView news_item_comments;
  private TextView news_item_score;
  public LinearLayout repostListLayout = null;
  public LinearLayout repostLogolayout = null;

  public NewsItemRepostView(BaseFragment paramBaseFragment, NewsInfo paramNewsInfo, NewsItemAdapter paramNewsItemAdapter)
  {
    super(paramBaseFragment, paramNewsInfo, paramNewsItemAdapter);
    this.info = paramNewsInfo;
    this.rootView = this.inflater.inflate(2130903254, this);
    this.repostLogolayout = ((LinearLayout)this.rootView.findViewById(2131363215));
    this.repostListLayout = ((LinearLayout)this.rootView.findViewById(2131363236));
    this.mNewsItemText = ((KXIntroView)this.rootView.findViewById(2131362592));
    this.mTextReleaseTime = ((TextView)this.rootView.findViewById(2131362590));
    this.mLayoutRepost1 = ((LinearLayout)this.rootView.findViewById(2131362601));
    this.mLayoutRepost2 = ((LinearLayout)this.rootView.findViewById(2131362605));
    this.mLayoutRepost3 = ((LinearLayout)this.rootView.findViewById(2131362609));
    this.mLayoutRepost4 = ((LinearLayout)this.rootView.findViewById(2131362613));
    this.mLayoutRepost5 = ((LinearLayout)this.rootView.findViewById(2131363165));
    this.mLayoutRepost6 = ((LinearLayout)this.rootView.findViewById(2131363169));
    this.mTextRepost1 = ((TextView)this.rootView.findViewById(2131362602));
    this.mTextRepost2 = ((TextView)this.rootView.findViewById(2131362606));
    this.mTextRepost3 = ((TextView)this.rootView.findViewById(2131362610));
    this.mTextRepost4 = ((TextView)this.rootView.findViewById(2131362614));
    this.mTextRepost5 = ((TextView)this.rootView.findViewById(2131363166));
    this.mTextRepost6 = ((TextView)this.rootView.findViewById(2131363170));
    this.mTextRepostExt1 = ((TextView)this.rootView.findViewById(2131362603));
    this.mTextRepostExt2 = ((TextView)this.rootView.findViewById(2131362607));
    this.mTextRepostExt3 = ((TextView)this.rootView.findViewById(2131362611));
    this.mTextRepostExt4 = ((TextView)this.rootView.findViewById(2131362615));
    this.mTextRepostExt5 = ((TextView)this.rootView.findViewById(2131363167));
    this.mTextRepostExt6 = ((TextView)this.rootView.findViewById(2131363171));
    this.mTextRepostImg1 = ((ImageView)this.rootView.findViewById(2131362604));
    this.mTextRepostImg2 = ((ImageView)this.rootView.findViewById(2131362608));
    this.mTextRepostImg3 = ((ImageView)this.rootView.findViewById(2131362612));
    this.mTextRepostImg4 = ((ImageView)this.rootView.findViewById(2131363164));
    this.mTextRepostImg5 = ((ImageView)this.rootView.findViewById(2131363168));
    this.mThumbImg1 = ((KXFrameImageView)this.rootView.findViewById(2131363238));
    this.mThumbImg2 = ((KXFrameImageView)this.rootView.findViewById(2131363243));
    this.mThumbImg3 = ((KXFrameImageView)this.rootView.findViewById(2131363248));
    this.mThumbImg4 = ((KXFrameImageView)this.rootView.findViewById(2131363253));
    this.mThumbImg5 = ((KXFrameImageView)this.rootView.findViewById(2131363258));
    this.mThumbImg6 = ((KXFrameImageView)this.rootView.findViewById(2131363263));
    this.mThumbLayout1 = this.rootView.findViewById(2131363237);
    this.mThumbLayout2 = this.rootView.findViewById(2131363242);
    this.mThumbLayout3 = this.rootView.findViewById(2131363247);
    this.mThumbLayout4 = this.rootView.findViewById(2131363252);
    this.mThumbLayout5 = this.rootView.findViewById(2131363257);
    this.mThumbLayout6 = this.rootView.findViewById(2131363262);
    this.mItemTypeIcon1 = ((ImageView)this.rootView.findViewById(2131363239));
    this.mItemTypeIcon2 = ((ImageView)this.rootView.findViewById(2131363244));
    this.mItemTypeIcon3 = ((ImageView)this.rootView.findViewById(2131363249));
    this.mItemTypeIcon4 = ((ImageView)this.rootView.findViewById(2131363254));
    this.mItemTypeIcon5 = ((ImageView)this.rootView.findViewById(2131363259));
    this.mItemTypeIcon6 = ((ImageView)this.rootView.findViewById(2131363264));
    this.news_item_comments = ((TextView)this.rootView.findViewById(2131363235));
    this.news_item_score = ((TextView)this.rootView.findViewById(2131363266));
  }

  public void constructRepostView(NewsInfo paramNewsInfo)
  {
    this.mLayoutRepost1.setVisibility(8);
    this.mLayoutRepost2.setVisibility(8);
    this.mLayoutRepost3.setVisibility(8);
    this.mLayoutRepost4.setVisibility(8);
    this.mLayoutRepost5.setVisibility(8);
    this.mLayoutRepost6.setVisibility(8);
    this.news_item_score.setVisibility(8);
    this.news_item_comments.setVisibility(8);
    if (paramNewsInfo.mNtype.equals("1088"))
    {
      ArrayList localArrayList2 = paramNewsInfo.mRepostList;
      if ((localArrayList2 == null) || (localArrayList2.size() == 0))
        return;
      int k = localArrayList2.size();
      if (k > 3)
        k = 3;
      int m = 0;
      label124: LinearLayout localLinearLayout2;
      TextView localTextView3;
      TextView localTextView4;
      KXFrameImageView localKXFrameImageView;
      View localView;
      ImageView localImageView2;
      label172: RepItem localRepItem;
      int n;
      if (m < k)
      {
        if (m != 0)
          break label408;
        localLinearLayout2 = this.mLayoutRepost1;
        localTextView3 = this.mTextRepost1;
        localTextView4 = this.mTextRepostExt1;
        localKXFrameImageView = this.mThumbImg1;
        localView = this.mThumbLayout1;
        localImageView2 = this.mItemTypeIcon1;
        localLinearLayout2.setVisibility(0);
        localView.setVisibility(0);
        localTextView4.setVisibility(8);
        localKXFrameImageView.setImageResource(2130838261);
        ((KXFrameImageView)localKXFrameImageView).setCenterIndicateIcon(0);
        localRepItem = (RepItem)localArrayList2.get(m);
        String str3 = localRepItem.myview;
        String str4 = localRepItem.title;
        n = localRepItem.category;
        localTextView3.setText(str4);
        if (!TextUtils.isEmpty(str3))
        {
          String str7 = "\"" + str3 + "\"";
          localTextView4.setText(str7);
        }
        if ((str3 != null) && (str3.length() > 0))
          localTextView4.setVisibility(0);
        if (TextUtils.isEmpty(localRepItem.mThumbImg))
          break label627;
        BaseFragment localBaseFragment2 = this.mFragment;
        String str6 = localRepItem.mThumbImg;
        localBaseFragment2.displayPicture(localKXFrameImageView, str6, 2130838784);
        label344: if (n == 40)
          ((KXFrameImageView)localKXFrameImageView).setCenterIndicateIcon(2130838154);
        if (n != 10)
          break label662;
        localImageView2.setImageResource(2130838882);
      }
      while (true)
      {
        localLinearLayout2.setTag(localRepItem);
        3 local3 = new View.OnClickListener(paramNewsInfo)
        {
          public void onClick(View paramView)
          {
            if (User.getInstance().GetLookAround())
            {
              NewsItemRepostView.this.adapter.showLoginDialog();
              return;
            }
            RepItem localRepItem = (RepItem)paramView.getTag();
            NewsModel.getInstance().setActiveItem(this.val$newsInfo);
            Intent localIntent = new Intent(NewsItemRepostView.this.mContext, RepostDetailFragment.class);
            ArrayList localArrayList = new ArrayList();
            localRepItem.fname = this.val$newsInfo.mFname;
            localRepItem.fuid = this.val$newsInfo.mFuid;
            localArrayList.add(localRepItem);
            Bundle localBundle = new Bundle();
            localBundle.putSerializable("repostList", (Serializable)localArrayList);
            localBundle.putInt("position", 0);
            localBundle.putString("commentflag", this.val$newsInfo.mCommentFlag);
            localBundle.putString("isShowMoreRep", "1");
            localIntent.putExtras(localBundle);
            AnimationUtil.startFragment(NewsItemRepostView.this.mFragment, localIntent, 1);
          }
        };
        localLinearLayout2.setOnClickListener(local3);
        m++;
        break label124;
        break;
        label408: if (m == 1)
        {
          localLinearLayout2 = this.mLayoutRepost2;
          localTextView3 = this.mTextRepost2;
          localTextView4 = this.mTextRepostExt2;
          localKXFrameImageView = this.mThumbImg2;
          localView = this.mThumbLayout2;
          localImageView2 = this.mItemTypeIcon2;
          break label172;
        }
        if (m == 2)
        {
          localLinearLayout2 = this.mLayoutRepost3;
          localTextView3 = this.mTextRepost3;
          localTextView4 = this.mTextRepostExt3;
          localKXFrameImageView = this.mThumbImg3;
          localView = this.mThumbLayout3;
          localImageView2 = this.mItemTypeIcon3;
          break label172;
        }
        if (m == 3)
        {
          localLinearLayout2 = this.mLayoutRepost4;
          localTextView3 = this.mTextRepost4;
          localTextView4 = this.mTextRepostExt4;
          localKXFrameImageView = this.mThumbImg4;
          localView = this.mThumbLayout4;
          localImageView2 = this.mItemTypeIcon4;
          break label172;
        }
        if (m == 4)
        {
          localLinearLayout2 = this.mLayoutRepost5;
          localTextView3 = this.mTextRepost5;
          localTextView4 = this.mTextRepostExt5;
          localKXFrameImageView = this.mThumbImg5;
          localView = this.mThumbLayout5;
          localImageView2 = this.mItemTypeIcon5;
          break label172;
        }
        localLinearLayout2 = this.mLayoutRepost6;
        localTextView3 = this.mTextRepost6;
        localTextView4 = this.mTextRepostExt6;
        localKXFrameImageView = this.mThumbImg6;
        localView = this.mThumbLayout6;
        localImageView2 = this.mItemTypeIcon6;
        break label172;
        label627: if (n != 40)
          break label344;
        BaseFragment localBaseFragment1 = this.mFragment;
        String str5 = localRepItem.vthumb;
        localBaseFragment1.displayPicture(localKXFrameImageView, str5, 2130838784);
        break label344;
        label662: if (n == 30)
        {
          localImageView2.setImageResource(2130838777);
          continue;
        }
        if (n == 40)
        {
          localImageView2.setImageResource(2130838708);
          continue;
        }
        if (n != 20)
          continue;
        localImageView2.setImageResource(2130837758);
      }
    }
    if (paramNewsInfo.mNtype.equals("1016"));
    while (true)
    {
      try
      {
        ArrayList localArrayList1 = paramNewsInfo.mVoteList;
        if ((localArrayList1 == null) || (localArrayList1.size() == 0))
          break;
        int i = localArrayList1.size();
        if (i <= 4)
          break label1314;
        i = 4;
        break label1314;
        if (j >= i)
          break;
        String str2 = ((VoteItem)localArrayList1.get(j)).mTitle;
        if (j != 0)
          continue;
        TextView localTextView1 = this.mTextRepost1;
        TextView localTextView2 = this.mTextRepostExt1;
        ImageView localImageView1 = this.mTextRepostImg1;
        localTextView1.setText(str2);
        localTextView1.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        localTextView1.setVisibility(0);
        localTextView1.setClickable(false);
        localTextView2.setVisibility(8);
        if (localImageView1 == null)
          break label1320;
        if (j >= i - 1)
          continue;
        localImageView1.setVisibility(0);
        break label1320;
        if (j != 1)
          continue;
        localTextView1 = this.mTextRepost2;
        localTextView2 = this.mTextRepostExt2;
        localImageView1 = this.mTextRepostImg2;
        continue;
        if (j != 2)
          continue;
        localTextView1 = this.mTextRepost3;
        localTextView2 = this.mTextRepostExt3;
        localImageView1 = this.mTextRepostImg3;
        continue;
        if (j != 3)
          continue;
        localTextView1 = this.mTextRepost4;
        localTextView2 = this.mTextRepostExt4;
        localImageView1 = this.mTextRepostImg4;
        continue;
        if (j != 4)
          continue;
        localTextView1 = this.mTextRepost5;
        localTextView2 = this.mTextRepostExt5;
        localImageView1 = this.mTextRepostImg5;
        continue;
        localTextView1 = this.mTextRepost6;
        localTextView2 = this.mTextRepostExt6;
        localImageView1 = null;
        continue;
        localImageView1.setVisibility(8);
      }
      catch (Exception localException)
      {
        KXLog.e("NewsItemRepostView", "displayVoteTopic", localException);
        return;
      }
      if (!paramNewsInfo.mNtype.equals("1020"))
        break;
      this.mLayoutRepost6.setVisibility(0);
      this.mItemTypeIcon6.setVisibility(8);
      this.news_item_score.setVisibility(0);
      this.mFragment.displayPicture(this.mThumbImg6, paramNewsInfo.filmItem.getmCover(), 2130838784);
      this.mTextRepostExt6.setText("主演：" + paramNewsInfo.filmItem.getmActor());
      this.mTextRepost6.setText(paramNewsInfo.filmItem.getmName());
      this.news_item_score.setText(paramNewsInfo.filmItem.getmScore() + "分");
      String str1 = paramNewsInfo.filmItem.getmComments();
      if ((str1 != null) && (!"".equals(str1)))
      {
        this.news_item_comments.setText("\"" + str1 + "\"");
        this.news_item_comments.setVisibility(0);
        this.mTextRepost6.setTextColor(getResources().getColor(2130839397));
      }
      while (true)
      {
        LinearLayout localLinearLayout1 = this.mLayoutRepost6;
        4 local4 = new View.OnClickListener(paramNewsInfo)
        {
          public void onClick(View paramView)
          {
            Intent localIntent = new Intent();
            localIntent.setClass(NewsItemRepostView.this.mContext, FilmDetailFragment.class);
            localIntent.putExtra("fid", this.val$newsInfo.filmItem.getmMid());
            localIntent.putExtra("name", this.val$newsInfo.filmItem.getmName());
            AnimationUtil.startFragment(NewsItemRepostView.this.mFragment, localIntent, 1);
          }
        };
        localLinearLayout1.setOnClickListener(local4);
        return;
        this.news_item_comments.setVisibility(8);
        this.mTextRepost6.setTextColor(getResources().getColor(2130839398));
      }
      label1314: int j = 0;
      continue;
      label1320: j++;
    }
  }

  public boolean show(NewsInfo paramNewsInfo)
  {
    constructRepostView(paramNewsInfo);
    showTitle(paramNewsInfo);
    return super.show(paramNewsInfo);
  }

  public void showTitle(NewsInfo paramNewsInfo)
  {
    this.mTextReleaseTime.setText(paramNewsInfo.mStime);
    KXIntroView.OnClickLinkListener localOnClickLinkListener = this.adapter.makeTitleClickListener(paramNewsInfo);
    ArrayList localArrayList;
    if (paramNewsInfo.mNtype.equals("1088"))
      localArrayList = this.adapter.getTitleKXLinkListAddNameLimit(paramNewsInfo, " 转帖给大家", 5);
    boolean bool;
    do
    {
      this.mNewsItemText.setTitleList(localArrayList);
      this.mNewsItemText.setOnClickLinkListener(localOnClickLinkListener);
      return;
      bool = paramNewsInfo.mNtype.equals("1020");
      localArrayList = null;
    }
    while (!bool);
    String str = "";
    if (paramNewsInfo.filmItem.getmType().equals("merge_film_new_want"))
      str = " 想看电影";
    while (true)
    {
      localArrayList = this.adapter.getTitleKXLinkListAddNameLimit(paramNewsInfo, str, 5);
      break;
      if (!paramNewsInfo.filmItem.getmType().equals("merge_film_view_new_0"))
        continue;
      str = " 看了电影";
    }
  }

  protected void showTools(NewsInfo paramNewsInfo)
  {
    View localView = this.rootView.findViewById(2131363268);
    if (paramNewsInfo.mNtype.equals("1088"))
    {
      ArrayList localArrayList = paramNewsInfo.mRepostList;
      if ((localArrayList != null) && (localArrayList.size() != 0));
    }
    do
    {
      return;
      localView.setOnClickListener(new View.OnClickListener(paramNewsInfo)
      {
        public void onClick(View paramView)
        {
          NewsItemRepostView.this.adapter.showRepost(this.val$newsInfo);
        }
      });
      return;
    }
    while (!paramNewsInfo.mNtype.equals("1020"));
    localView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent(NewsItemRepostView.this.mContext, FilmListFragment.class);
        NewsItemRepostView.this.mFragment.startFragment(localIntent, true, 1);
      }
    });
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.NewsItemRepostView
 * JD-Core Version:    0.6.0
 */