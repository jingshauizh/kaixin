package com.kaixin001.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.item.ExtremityItemLv1;
import com.kaixin001.item.ExtremityItemLv1.ItemType;
import com.kaixin001.item.ExtremityItemLv2;
import com.kaixin001.item.ExtremityItemLv2.ItemTypeLv2;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import java.util.ArrayList;
import java.util.List;

public class ExtremityAdapter extends BaseExpandableListAdapter
  implements View.OnClickListener
{
  private Activity activity;
  public Integer commentCount;
  private List<ExtremityItemLv1> comments;
  private List<ExtremityItemLv1> commentsData;
  private OnDataChangeListern dataChangeListern;
  private View emptyView;
  private int endComments = -1;
  private ExtremityItemLv1 header_comment;
  private ExtremityItemLv1 header_repost;
  private LayoutInflater inflater;
  public boolean isIniting;
  public boolean isloadingRepost;
  private BaseFragment mFragment;
  private OnRelyListerner relyListerner;
  public Integer repostCount;
  private List<ExtremityItemLv1> reposts;
  private int step = 1;
  private OnViewMoreListerner viewMoreListerner;

  public ExtremityAdapter(BaseFragment paramBaseFragment, List<ExtremityItemLv1> paramList1, List<ExtremityItemLv1> paramList2, OnViewMoreListerner paramOnViewMoreListerner, OnRelyListerner paramOnRelyListerner, int paramInt1, int paramInt2)
  {
    this.mFragment = paramBaseFragment;
    this.activity = paramBaseFragment.getActivity();
    this.inflater = LayoutInflater.from(paramBaseFragment.getActivity());
    this.commentsData = paramList1;
    this.header_comment = new ExtremityItemLv1();
    this.header_comment.itemType = ExtremityItemLv1.ItemType.header_comment;
    this.header_repost = new ExtremityItemLv1();
    this.header_repost.itemType = ExtremityItemLv1.ItemType.header_repost;
    this.viewMoreListerner = paramOnViewMoreListerner;
    this.relyListerner = paramOnRelyListerner;
    this.commentCount = Integer.valueOf(paramInt1);
    this.repostCount = Integer.valueOf(paramInt2);
    this.comments = getMoreComments();
    this.reposts = paramList2;
  }

  private List<ExtremityItemLv1> getShowComments()
  {
    ArrayList localArrayList = new ArrayList();
    if (this.endComments == -1)
      return localArrayList;
    for (int i = 0; ; i++)
    {
      if (i > this.endComments)
      {
        if (this.endComments >= -1 + this.commentsData.size())
          break;
        ExtremityItemLv1 localExtremityItemLv1 = new ExtremityItemLv1();
        localExtremityItemLv1.itemType = ExtremityItemLv1.ItemType.footer_viewmore_comment;
        localArrayList.add(localExtremityItemLv1);
        return localArrayList;
      }
      localArrayList.add((ExtremityItemLv1)this.commentsData.get(i));
    }
  }

  private void nextComments()
  {
    if (this.commentsData.size() <= 0)
    {
      this.endComments = -1;
      return;
    }
    this.endComments += this.step;
    this.endComments = Math.min(this.endComments, -1 + this.commentsData.size());
  }

  public Object getChild(int paramInt1, int paramInt2)
  {
    ExtremityItemLv1 localExtremityItemLv1 = (ExtremityItemLv1)getGroup(paramInt1);
    ExtremityItemLv2 localExtremityItemLv2;
    if ((localExtremityItemLv1 == null) || (localExtremityItemLv1.itemLv2s == null) || (localExtremityItemLv1.itemLv2s.size() <= 0))
      localExtremityItemLv2 = new ExtremityItemLv2();
    do
    {
      return localExtremityItemLv2;
      if (localExtremityItemLv1.end >= -1 + localExtremityItemLv1.itemLv2s.size())
        return localExtremityItemLv1.itemLv2s.get(paramInt2);
      if (paramInt2 <= localExtremityItemLv1.end)
        return localExtremityItemLv1.itemLv2s.get(paramInt2);
      localExtremityItemLv2 = new ExtremityItemLv2();
      if (localExtremityItemLv1.itemType != ExtremityItemLv1.ItemType.content_comment)
        continue;
      localExtremityItemLv2.itemType = ExtremityItemLv2.ItemTypeLv2.footer_viewmore_comment;
      return localExtremityItemLv2;
    }
    while (localExtremityItemLv1.itemType != ExtremityItemLv1.ItemType.cotent_repost);
    localExtremityItemLv2.itemType = ExtremityItemLv2.ItemTypeLv2.footer_viewmore_repost;
    return localExtremityItemLv2;
  }

  public long getChildId(int paramInt1, int paramInt2)
  {
    return paramInt2;
  }

  public View getChildView(int paramInt1, int paramInt2, boolean paramBoolean, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null)
      paramView = this.inflater.inflate(2130903100, null);
    ExtremityItemLv2 localExtremityItemLv2 = (ExtremityItemLv2)getChild(paramInt1, paramInt2);
    View localView;
    if (localExtremityItemLv2.itemType == ExtremityItemLv2.ItemTypeLv2.footer_viewmore_comment)
    {
      TextView localTextView4 = (TextView)paramView.findViewById(2131362202);
      localTextView4.setTag(getGroup(paramInt1));
      localTextView4.setOnClickListener(this);
      localTextView4.setVisibility(0);
      localTextView4.setTextAppearance(this.activity, 2131230731);
      ((RelativeLayout)paramView.findViewById(2131362203)).setVisibility(8);
      localView = paramView.findViewById(2131362187);
      if (paramInt2 != -1 + getChildrenCount(paramInt1))
        break label495;
      localView.setVisibility(0);
    }
    while (true)
    {
      paramView.setTag(localExtremityItemLv2);
      return paramView;
      if (localExtremityItemLv2.itemType == ExtremityItemLv2.ItemTypeLv2.footer_viewmore_repost)
      {
        TextView localTextView3 = (TextView)paramView.findViewById(2131362202);
        localTextView3.setTag(getGroup(paramInt1));
        localTextView3.setOnClickListener(this);
        localTextView3.setVisibility(0);
        localTextView3.setTextAppearance(this.activity, 2131230732);
        ((RelativeLayout)paramView.findViewById(2131362203)).setVisibility(8);
        break;
      }
      if (localExtremityItemLv2.itemType == ExtremityItemLv2.ItemTypeLv2.content_comment)
      {
        ((TextView)paramView.findViewById(2131362202)).setVisibility(8);
        ((RelativeLayout)paramView.findViewById(2131362203)).setVisibility(0);
        ImageView localImageView2 = (ImageView)paramView.findViewById(2131362204);
        this.mFragment.displayRoundPicture(localImageView2, localExtremityItemLv2.userIconUrl, ImageDownloader.RoundCornerType.hdpi_small, 2130838355);
        ((TextView)paramView.findViewById(2131362205)).setText(localExtremityItemLv2.userName);
        ((TextView)paramView.findViewById(2131362206)).setText(localExtremityItemLv2.time);
        TextView localTextView2 = (TextView)paramView.findViewById(2131362196);
        localTextView2.setText(localExtremityItemLv2.content);
        localTextView2.setTextAppearance(this.activity, 2131230731);
        break;
      }
      if (localExtremityItemLv2.itemType != ExtremityItemLv2.ItemTypeLv2.content_repost)
        break;
      ((TextView)paramView.findViewById(2131362202)).setVisibility(8);
      ((RelativeLayout)paramView.findViewById(2131362203)).setVisibility(0);
      ImageView localImageView1 = (ImageView)paramView.findViewById(2131362204);
      this.mFragment.displayRoundPicture(localImageView1, localExtremityItemLv2.userIconUrl, ImageDownloader.RoundCornerType.hdpi_small, 2130838355);
      ((TextView)paramView.findViewById(2131362205)).setText(localExtremityItemLv2.userName);
      ((TextView)paramView.findViewById(2131362206)).setText(localExtremityItemLv2.time);
      TextView localTextView1 = (TextView)paramView.findViewById(2131362196);
      localTextView1.setText(localExtremityItemLv2.content);
      localTextView1.setTextAppearance(this.activity, 2131230732);
      break;
      label495: localView.setVisibility(8);
    }
  }

  public int getChildrenCount(int paramInt)
  {
    if (this.isIniting);
    ExtremityItemLv1 localExtremityItemLv1;
    do
    {
      return 0;
      localExtremityItemLv1 = (ExtremityItemLv1)getGroup(paramInt);
    }
    while ((localExtremityItemLv1 == null) || (localExtremityItemLv1.itemLv2s == null) || (localExtremityItemLv1.itemLv2s.size() <= 0));
    if (localExtremityItemLv1.end < -1 + localExtremityItemLv1.itemLv2s.size())
      return 2 + localExtremityItemLv1.end;
    return localExtremityItemLv1.itemLv2s.size();
  }

  public Object getGroup(int paramInt)
  {
    if (paramInt == 0);
    try
    {
      return this.header_comment;
      if ((paramInt > 0) && (paramInt <= this.comments.size()))
        return this.comments.get(paramInt - 1);
      if (paramInt == 1 + this.comments.size())
        return this.header_repost;
      Object localObject = this.reposts.get(-2 + (paramInt - this.comments.size()));
      return localObject;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return new ExtremityItemLv1();
  }

  public int getGroupCount()
  {
    if (this.isIniting)
      return 1;
    return 2 + (this.comments.size() + this.reposts.size());
  }

  public long getGroupId(int paramInt)
  {
    return paramInt;
  }

  public View getGroupView(int paramInt, boolean paramBoolean, View paramView, ViewGroup paramViewGroup)
  {
    if (this.isIniting)
    {
      if (this.emptyView == null)
        this.emptyView = this.inflater.inflate(2130903418, null);
      return this.emptyView;
    }
    if (paramView != null)
    {
      View localView = this.emptyView;
      if (paramView != localView);
    }
    else
    {
      paramView = this.inflater.inflate(2130903099, null);
    }
    TextView localTextView1 = (TextView)paramView.findViewById(2131362188);
    RelativeLayout localRelativeLayout = (RelativeLayout)paramView.findViewById(2131362191);
    TextView localTextView2 = (TextView)paramView.findViewById(2131362198);
    ExtremityItemLv1 localExtremityItemLv1 = (ExtremityItemLv1)getGroup(paramInt);
    if (localExtremityItemLv1.itemType == null)
      return paramView;
    if (localExtremityItemLv1.itemType == ExtremityItemLv1.ItemType.header_comment)
    {
      localTextView1.setVisibility(0);
      localTextView1.setText(this.commentCount + this.activity.getString(2131427757));
      localTextView1.setCompoundDrawablesWithIntrinsicBounds(2130838352, 0, 0, 0);
      localTextView1.setTextAppearance(this.activity, 2131230731);
      localRelativeLayout.setVisibility(8);
      localTextView2.setVisibility(8);
    }
    while (true)
    {
      return paramView;
      if (localExtremityItemLv1.itemType == ExtremityItemLv1.ItemType.header_repost)
      {
        localTextView1.setVisibility(0);
        localTextView1.setText(this.repostCount + this.activity.getString(2131427373));
        localTextView1.setCompoundDrawablesWithIntrinsicBounds(2130838360, 0, 0, 0);
        localTextView1.setTextAppearance(this.activity, 2131230732);
        localRelativeLayout.setVisibility(8);
        localTextView2.setVisibility(8);
        continue;
      }
      if (localExtremityItemLv1.itemType == ExtremityItemLv1.ItemType.content_comment)
      {
        localRelativeLayout.setVisibility(0);
        ImageView localImageView2 = (ImageView)paramView.findViewById(2131362193);
        this.mFragment.displayRoundPicture(localImageView2, localExtremityItemLv1.userIconUrl, ImageDownloader.RoundCornerType.hdpi_small, 2130838355);
        ((TextView)paramView.findViewById(2131362194)).setText(localExtremityItemLv1.userName);
        ((TextView)paramView.findViewById(2131362195)).setText(localExtremityItemLv1.time);
        TextView localTextView4 = (TextView)paramView.findViewById(2131362196);
        localTextView4.setText(localExtremityItemLv1.content);
        localTextView4.setTextAppearance(this.activity, 2131230731);
        ImageView localImageView3 = (ImageView)paramView.findViewById(2131362197);
        localImageView3.setTag(localExtremityItemLv1);
        localImageView3.setOnClickListener(this);
        localTextView1.setVisibility(8);
        localTextView2.setVisibility(8);
        continue;
      }
      if (localExtremityItemLv1.itemType == ExtremityItemLv1.ItemType.cotent_repost)
      {
        localRelativeLayout.setVisibility(0);
        ImageView localImageView1 = (ImageView)paramView.findViewById(2131362193);
        this.mFragment.displayRoundPicture(localImageView1, localExtremityItemLv1.userIconUrl, ImageDownloader.RoundCornerType.hdpi_small, 2130838355);
        ((TextView)paramView.findViewById(2131362194)).setText(localExtremityItemLv1.userName);
        ((TextView)paramView.findViewById(2131362195)).setText(localExtremityItemLv1.time);
        TextView localTextView3 = (TextView)paramView.findViewById(2131362196);
        localTextView3.setText(localExtremityItemLv1.content);
        localTextView3.setTextAppearance(this.activity, 2131230731);
        ((ImageView)paramView.findViewById(2131362197)).setVisibility(8);
        localTextView1.setVisibility(8);
        localTextView2.setVisibility(8);
        continue;
      }
      if (localExtremityItemLv1.itemType == ExtremityItemLv1.ItemType.footer_viewmore_comment)
      {
        localTextView2.setVisibility(0);
        localTextView2.setTag(localExtremityItemLv1);
        localTextView2.setOnClickListener(this);
        localTextView2.setTextAppearance(this.activity, 2131230731);
        localTextView1.setVisibility(8);
        localRelativeLayout.setVisibility(8);
        continue;
      }
      if (localExtremityItemLv1.itemType != ExtremityItemLv1.ItemType.footer_viewmore_repost)
        continue;
      if (this.isloadingRepost)
      {
        if (this.emptyView == null)
          this.emptyView = this.inflater.inflate(2130903418, null);
        return this.emptyView;
      }
      localTextView2.setVisibility(0);
      localTextView2.setTag(localExtremityItemLv1);
      localTextView2.setOnClickListener(this);
      localTextView2.setTextAppearance(this.activity, 2131230732);
      localTextView1.setVisibility(8);
      localRelativeLayout.setVisibility(8);
    }
  }

  public List<ExtremityItemLv1> getMoreComments()
  {
    nextComments();
    return getShowComments();
  }

  public boolean hasStableIds()
  {
    return false;
  }

  public boolean isChildSelectable(int paramInt1, int paramInt2)
  {
    return false;
  }

  public void notifyDataSetChanged()
  {
    super.notifyDataSetChanged();
    if (this.dataChangeListern != null)
      this.dataChangeListern.onDataChange();
  }

  public void onClick(View paramView)
  {
    if (paramView.getId() == 2131362197);
    while (true)
    {
      try
      {
        ExtremityItemLv1 localExtremityItemLv13 = (ExtremityItemLv1)paramView.getTag();
        if (localExtremityItemLv13.itemType != ExtremityItemLv1.ItemType.content_comment)
          continue;
        this.relyListerner.onReply(localExtremityItemLv13);
        return;
      }
      catch (Exception localException3)
      {
        localException3.printStackTrace();
        return;
      }
      if (paramView.getId() == 2131362198)
      {
        ExtremityItemLv1 localExtremityItemLv12;
        try
        {
          localExtremityItemLv12 = (ExtremityItemLv1)paramView.getTag();
          if (localExtremityItemLv12.itemType == ExtremityItemLv1.ItemType.footer_viewmore_comment)
          {
            this.comments = getMoreComments();
            notifyDataSetChanged();
            return;
          }
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
          return;
        }
        if (localExtremityItemLv12.itemType != ExtremityItemLv1.ItemType.footer_viewmore_repost)
          continue;
        this.isloadingRepost = true;
        notifyDataSetChanged();
        this.viewMoreListerner.onViewMoreReposts();
        return;
      }
      if (paramView.getId() != 2131362202)
        continue;
      try
      {
        ExtremityItemLv1 localExtremityItemLv11 = (ExtremityItemLv1)paramView.getTag();
        if ((localExtremityItemLv11.itemType != ExtremityItemLv1.ItemType.content_comment) && (localExtremityItemLv11.itemType != ExtremityItemLv1.ItemType.cotent_repost))
          continue;
        localExtremityItemLv11.next();
        notifyDataSetChanged();
        return;
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace();
      }
    }
  }

  public void setCommentsData(List<ExtremityItemLv1> paramList)
  {
    this.commentsData = paramList;
    if (this.endComments == -1);
    for (this.comments = getMoreComments(); ; this.comments = getShowComments())
    {
      notifyDataSetChanged();
      return;
    }
  }

  public void setDataChangeListern(OnDataChangeListern paramOnDataChangeListern)
  {
    this.dataChangeListern = paramOnDataChangeListern;
  }

  public void setReposts(List<ExtremityItemLv1> paramList)
  {
    this.reposts = paramList;
    notifyDataSetChanged();
  }

  public static abstract interface OnDataChangeListern
  {
    public abstract void onDataChange();
  }

  public static abstract interface OnRelyListerner
  {
    public abstract void onReply(ExtremityItemLv1 paramExtremityItemLv1);
  }

  public static abstract interface OnViewMoreListerner
  {
    public abstract void onViewMoreReposts();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.adapter.ExtremityAdapter
 * JD-Core Version:    0.6.0
 */