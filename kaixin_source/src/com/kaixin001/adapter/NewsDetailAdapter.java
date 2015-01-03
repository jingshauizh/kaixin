package com.kaixin001.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.kaixin001.businesslogic.ShowPhoto;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.item.ExtremityItemLv1;
import com.kaixin001.item.ExtremityItemLv1.ItemType;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.item.RecordInfo;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.view.KXIntroView;
import com.kaixin001.view.KXIntroView.OnClickLinkListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class NewsDetailAdapter extends BaseAdapter
  implements View.OnClickListener, KXIntroView.OnClickLinkListener
{
  private String AT = "[|s|]@[|m|]10066329[|m|]-101[|e|]";
  private Activity activity;
  private ShowPhoto albumShowUtil;
  public int commentCount;
  private List<ExtremityItemLv1> comments = new ArrayList();
  private View emptyView;
  private View headView;
  private ExtremityItemLv1 header;
  private ExtremityItemLv1 header_comment;
  private ExtremityItemLv1 header_repost;
  private LayoutInflater inflater;
  public boolean isIniting;
  public boolean isloadingComment;
  public boolean isloadingRepost;
  private List<ExtremityItemLv1> mDataList = new ArrayList();
  private BaseFragment mFragment;
  private ArrayList<Integer> mNodevideLineItemIds = new ArrayList();
  private String mType;
  private NewsInfo newsInfo;
  private OnRelyDetailListerner relyListerner;
  public int repostCount;
  private List<ExtremityItemLv1> reposts = new ArrayList();
  private OnViewMoreDetailListerner viewMoreListerner;

  public NewsDetailAdapter(BaseFragment paramBaseFragment, String paramString, List<ExtremityItemLv1> paramList1, List<ExtremityItemLv1> paramList2, OnViewMoreDetailListerner paramOnViewMoreDetailListerner, OnRelyDetailListerner paramOnRelyDetailListerner, int paramInt1, int paramInt2, NewsInfo paramNewsInfo, View paramView)
  {
    this.activity = paramBaseFragment.getActivity();
    this.mFragment = paramBaseFragment;
    this.inflater = LayoutInflater.from(paramBaseFragment.getActivity());
    this.mType = paramString;
    this.comments.addAll(paramList1);
    this.reposts.addAll(paramList2);
    this.headView = paramView;
    this.header = new ExtremityItemLv1();
    this.header.itemType = ExtremityItemLv1.ItemType.header;
    this.header_comment = new ExtremityItemLv1();
    this.header_comment.itemType = ExtremityItemLv1.ItemType.header_comment;
    this.header_repost = new ExtremityItemLv1();
    this.header_repost.itemType = ExtremityItemLv1.ItemType.header_repost;
    updateDataList();
    this.viewMoreListerner = paramOnViewMoreDetailListerner;
    this.relyListerner = paramOnRelyDetailListerner;
    this.commentCount = paramInt1;
    this.repostCount = paramInt2;
    this.newsInfo = paramNewsInfo;
  }

  private void updateDataList()
  {
    this.mDataList.clear();
    this.mNodevideLineItemIds.clear();
    this.mNodevideLineItemIds.add(Integer.valueOf(this.mDataList.size()));
    this.mDataList.add(this.header_comment);
    this.mNodevideLineItemIds.add(Integer.valueOf(this.mDataList.size()));
    this.mDataList.addAll(this.comments);
    if ((this.commentCount > this.comments.size()) && (this.comments.size() > 0) && (this.comments.size() % 10 == 0))
    {
      ExtremityItemLv1 localExtremityItemLv13 = new ExtremityItemLv1();
      localExtremityItemLv13.itemType = ExtremityItemLv1.ItemType.footer_viewmore_comment;
      this.mDataList.add(localExtremityItemLv13);
    }
    while (true)
    {
      if ("1018".equals(this.mType))
      {
        this.mNodevideLineItemIds.add(Integer.valueOf(this.mDataList.size()));
        this.mDataList.add(this.header_repost);
        this.mNodevideLineItemIds.add(Integer.valueOf(this.mDataList.size()));
        this.mDataList.addAll(this.reposts);
        if ((this.repostCount > this.reposts.size()) && (this.reposts.size() > 0))
        {
          ExtremityItemLv1 localExtremityItemLv12 = new ExtremityItemLv1();
          localExtremityItemLv12.itemType = ExtremityItemLv1.ItemType.footer_viewmore_repost;
          this.mDataList.add(localExtremityItemLv12);
        }
      }
      return;
      if (this.comments.size() != 0)
        continue;
      ExtremityItemLv1 localExtremityItemLv11 = new ExtremityItemLv1();
      localExtremityItemLv11.itemType = ExtremityItemLv1.ItemType.no_comment;
      this.mDataList.add(localExtremityItemLv11);
    }
  }

  public void clearData()
  {
    this.comments.clear();
    this.reposts.clear();
    updateDataList();
  }

  public int getCount()
  {
    if (this.isIniting)
      return 2;
    return this.mDataList.size();
  }

  public Object getItem(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < this.mDataList.size()))
      return this.mDataList.get(paramInt);
    return null;
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    Object localObject = getItem(paramInt);
    ExtremityItemLv1 localExtremityItemLv1 = null;
    if (localObject != null)
      localExtremityItemLv1 = (ExtremityItemLv1)localObject;
    if (localExtremityItemLv1 == null)
      return null;
    if (this.isIniting)
    {
      if (this.emptyView == null)
        this.emptyView = this.inflater.inflate(2130903418, null);
      return this.emptyView;
    }
    ViewHolder localViewHolder;
    if ((paramView == null) || (paramView == this.emptyView) || (paramView == this.headView))
    {
      paramView = this.inflater.inflate(2130903099, null);
      localViewHolder = new ViewHolder();
      localViewHolder.init(paramView);
      paramView.setTag(localViewHolder);
    }
    while (true)
    {
      localViewHolder.update((ExtremityItemLv1)getItem(paramInt), paramInt);
      return paramView;
      localViewHolder = (ViewHolder)paramView.getTag();
    }
  }

  public void onClick(View paramView)
  {
    if ((paramView.getId() == 2131362197) || (paramView.getId() == 2131362191));
    ExtremityItemLv1 localExtremityItemLv12;
    do
    {
      do
        try
        {
          ExtremityItemLv1 localExtremityItemLv11 = (ExtremityItemLv1)paramView.getTag();
          if (localExtremityItemLv11.itemType == ExtremityItemLv1.ItemType.content_comment)
            this.relyListerner.onReply(localExtremityItemLv11);
          return;
        }
        catch (Exception localException1)
        {
          localException1.printStackTrace();
          return;
        }
      while (paramView.getId() != 2131362198);
      try
      {
        localExtremityItemLv12 = (ExtremityItemLv1)paramView.getTag();
        if (localExtremityItemLv12.itemType != ExtremityItemLv1.ItemType.footer_viewmore_comment)
          continue;
        this.isloadingComment = true;
        notifyDataSetChanged();
        this.viewMoreListerner.onViewMoreComments();
        return;
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
        return;
      }
    }
    while (localExtremityItemLv12.itemType != ExtremityItemLv1.ItemType.footer_viewmore_repost);
    this.isloadingRepost = true;
    notifyDataSetChanged();
    this.viewMoreListerner.onViewMoreReposts();
  }

  public void onClick(KXLinkInfo paramKXLinkInfo)
  {
    if ((paramKXLinkInfo.isStar()) || (paramKXLinkInfo.isUserName()))
    {
      String str1 = paramKXLinkInfo.getId();
      String str2 = paramKXLinkInfo.getContent();
      IntentUtil.showHomeFragment(this.mFragment, str1, str2);
    }
    do
    {
      return;
      if (paramKXLinkInfo.isUrlLink())
      {
        IntentUtil.showWebPage(this.activity, this.mFragment, paramKXLinkInfo.getId(), null);
        return;
      }
      if (!paramKXLinkInfo.isLbsPoi())
        continue;
      IntentUtil.showLbsPositionDetailFragment(this.mFragment, paramKXLinkInfo.getId(), paramKXLinkInfo.getContent(), "", "");
      return;
    }
    while (!paramKXLinkInfo.isTopic());
    IntentUtil.showTopicGroupActivity(this.mFragment, paramKXLinkInfo.getId());
  }

  public String processTextForAt(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap = new HashMap();
    if (paramString.length() == 0)
      return null;
    int i = 0;
    int j = paramString.indexOf("@", i);
    label44: Iterator localIterator;
    if (j < 0)
      localIterator = localArrayList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        return paramString;
        int k = paramString.indexOf("#)", i);
        if ((k < 0) || (j >= k))
          break label44;
        String str1 = paramString.substring(j, k + 2);
        String str2 = str1.substring(1 + str1.indexOf("@"), str1.indexOf("(#")).trim();
        String str3 = str1.substring(1 + str1.indexOf("#"), str1.lastIndexOf("#")).trim();
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append(this.AT).append("[|s|]").append(str3).append("[|m|]").append(str2).append("[|m|]").append("0").append("[|e|]");
        localArrayList.add(str1);
        localHashMap.put(str1, localStringBuffer.toString());
        i = k + 1;
        break;
      }
      String str4 = (String)localIterator.next();
      paramString = paramString.replace(str4, (String)localHashMap.get(str4));
    }
  }

  public void setComments(List<ExtremityItemLv1> paramList)
  {
    this.comments.clear();
    this.comments.addAll(paramList);
    updateDataList();
  }

  public void setReposts(List<ExtremityItemLv1> paramList)
  {
    this.reposts.clear();
    this.reposts.addAll(paramList);
    updateDataList();
  }

  public static abstract interface OnRelyDetailListerner
  {
    public abstract void onReply(ExtremityItemLv1 paramExtremityItemLv1);
  }

  public static abstract interface OnViewMoreDetailListerner
  {
    public abstract void onViewMoreComments();

    public abstract void onViewMoreReposts();
  }

  class ViewHolder
  {
    View leftGap;
    TextView mNoComment;
    View moreItemLoading;
    RelativeLayout relaDetail;
    TextView txtHead;
    TextView txtViewMore;
    View verticalGap;
    View viewDivider;

    ViewHolder()
    {
    }

    private void showContentItem(ExtremityItemLv1 paramExtremityItemLv1)
    {
      this.viewDivider.setVisibility(0);
      this.relaDetail.setVisibility(0);
      this.relaDetail.setTag(paramExtremityItemLv1);
      this.relaDetail.setOnClickListener(NewsDetailAdapter.this);
      ImageView localImageView1 = (ImageView)this.relaDetail.findViewById(2131362193);
      localImageView1.setOnClickListener(new View.OnClickListener(paramExtremityItemLv1)
      {
        public void onClick(View paramView)
        {
          IntentUtil.showHomeFragment(NewsDetailAdapter.this.mFragment, this.val$itemLv1.uid, this.val$itemLv1.userName);
        }
      });
      NewsDetailAdapter.this.mFragment.displayRoundPicture(localImageView1, paramExtremityItemLv1.userIconUrl, ImageDownloader.RoundCornerType.hdpi_small, 2130838355);
      TextView localTextView = (TextView)this.relaDetail.findViewById(2131362194);
      localTextView.setText(paramExtremityItemLv1.userName);
      localTextView.setOnClickListener(new View.OnClickListener(paramExtremityItemLv1)
      {
        public void onClick(View paramView)
        {
          IntentUtil.showHomeFragment(NewsDetailAdapter.this.mFragment, this.val$itemLv1.uid, this.val$itemLv1.userName);
        }
      });
      ((TextView)this.relaDetail.findViewById(2131362195)).setText(paramExtremityItemLv1.time);
      KXIntroView localKXIntroView = (KXIntroView)this.relaDetail.findViewById(2131362196);
      localKXIntroView.setTextAppearance(NewsDetailAdapter.this.activity, 2131230731);
      String str1 = paramExtremityItemLv1.content;
      String str2 = NewsDetailAdapter.this.processTextForAt(str1);
      localKXIntroView.setOnClickLinkListener(NewsDetailAdapter.this);
      localKXIntroView.setTitleList(RecordInfo.parseObjCommentUtil(str2));
      ImageView localImageView2 = (ImageView)this.relaDetail.findViewById(2131362197);
      localImageView2.setTag(paramExtremityItemLv1);
      localImageView2.setOnClickListener(NewsDetailAdapter.this);
      if (paramExtremityItemLv1.itemType == ExtremityItemLv1.ItemType.cotent_repost)
        localImageView2.setVisibility(8);
      while (true)
      {
        this.txtHead.setVisibility(8);
        this.mNoComment.setVisibility(8);
        this.txtViewMore.setVisibility(8);
        this.moreItemLoading.setVisibility(8);
        return;
        if (paramExtremityItemLv1.isMainComment)
        {
          localImageView2.setVisibility(0);
          continue;
        }
        this.leftGap.setVisibility(0);
        localImageView2.setVisibility(8);
        this.viewDivider.setVisibility(8);
      }
    }

    private void showMoreItem(ExtremityItemLv1 paramExtremityItemLv1)
    {
      this.viewDivider.setVisibility(0);
      int i;
      if (((paramExtremityItemLv1.itemType != ExtremityItemLv1.ItemType.footer_viewmore_comment) || (!NewsDetailAdapter.this.isloadingComment)) && ((paramExtremityItemLv1.itemType != ExtremityItemLv1.ItemType.footer_viewmore_repost) || (!NewsDetailAdapter.this.isloadingRepost)))
      {
        i = 0;
        if (i == 0)
          break label104;
        this.txtViewMore.setVisibility(8);
        this.moreItemLoading.setVisibility(0);
      }
      while (true)
      {
        this.txtHead.setVisibility(8);
        this.mNoComment.setVisibility(8);
        this.relaDetail.setVisibility(8);
        return;
        i = 1;
        break;
        label104: this.txtViewMore.setVisibility(0);
        this.moreItemLoading.setVisibility(8);
        this.txtViewMore.setTag(paramExtremityItemLv1);
        this.txtViewMore.setOnClickListener(NewsDetailAdapter.this);
        this.txtViewMore.setTextAppearance(NewsDetailAdapter.this.activity, 2131230733);
      }
    }

    private void showNoCommentItem(ExtremityItemLv1 paramExtremityItemLv1)
    {
      this.viewDivider.setVisibility(0);
      this.mNoComment.setVisibility(0);
      this.relaDetail.setVisibility(8);
      this.txtHead.setVisibility(8);
      this.txtViewMore.setVisibility(8);
      this.moreItemLoading.setVisibility(8);
    }

    private void showTopItem(ExtremityItemLv1 paramExtremityItemLv1)
    {
      this.viewDivider.setVisibility(8);
      this.txtHead.setVisibility(0);
      if (paramExtremityItemLv1.itemType == ExtremityItemLv1.ItemType.header_comment);
      for (String str = NewsDetailAdapter.this.commentCount + NewsDetailAdapter.this.activity.getString(2131427757); ; str = NewsDetailAdapter.this.repostCount + NewsDetailAdapter.this.activity.getString(2131427373))
      {
        this.txtHead.setText(str);
        this.txtHead.setCompoundDrawablesWithIntrinsicBounds(2130838352, 0, 0, 0);
        this.txtHead.setTextAppearance(NewsDetailAdapter.this.activity, 2131230731);
        this.relaDetail.setVisibility(8);
        this.mNoComment.setVisibility(8);
        this.txtViewMore.setVisibility(8);
        this.moreItemLoading.setVisibility(8);
        return;
      }
    }

    public void init(View paramView)
    {
      this.txtHead = ((TextView)paramView.findViewById(2131362188));
      this.mNoComment = ((TextView)paramView.findViewById(2131362190));
      this.relaDetail = ((RelativeLayout)paramView.findViewById(2131362191));
      this.txtViewMore = ((TextView)paramView.findViewById(2131362198));
      this.moreItemLoading = paramView.findViewById(2131362199);
      this.viewDivider = paramView.findViewById(2131362187);
      this.verticalGap = paramView.findViewById(2131362189);
      this.leftGap = paramView.findViewById(2131362192);
    }

    public void update(ExtremityItemLv1 paramExtremityItemLv1, int paramInt)
    {
      this.leftGap.setVisibility(8);
      this.verticalGap.setVisibility(0);
      Iterator localIterator;
      if ((paramExtremityItemLv1.itemType == ExtremityItemLv1.ItemType.header_comment) || (paramExtremityItemLv1.itemType == ExtremityItemLv1.ItemType.header_repost))
      {
        showTopItem(paramExtremityItemLv1);
        localIterator = NewsDetailAdapter.this.mNodevideLineItemIds.iterator();
      }
      do
        if (!localIterator.hasNext())
        {
          return;
          if ((paramExtremityItemLv1.itemType == ExtremityItemLv1.ItemType.content_comment) || (paramExtremityItemLv1.itemType == ExtremityItemLv1.ItemType.cotent_repost))
          {
            showContentItem(paramExtremityItemLv1);
            break;
          }
          if ((paramExtremityItemLv1.itemType == ExtremityItemLv1.ItemType.footer_viewmore_comment) || (paramExtremityItemLv1.itemType == ExtremityItemLv1.ItemType.footer_viewmore_repost))
          {
            showMoreItem(paramExtremityItemLv1);
            break;
          }
          if (paramExtremityItemLv1.itemType != ExtremityItemLv1.ItemType.no_comment)
            break;
          showNoCommentItem(paramExtremityItemLv1);
          break;
        }
      while (paramInt != ((Integer)localIterator.next()).intValue());
      this.viewDivider.setVisibility(8);
      this.verticalGap.setVisibility(8);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.adapter.NewsDetailAdapter
 * JD-Core Version:    0.6.0
 */