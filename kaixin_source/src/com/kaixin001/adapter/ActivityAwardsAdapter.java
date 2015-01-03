package com.kaixin001.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.kaixin001.fragment.ActivityAwardsFragment;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.item.ActivityAwardItem;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;

public class ActivityAwardsAdapter extends BaseAdapter
{
  private static final String TAG = "ActivityAwardsAdapter";
  ArrayList<ActivityAwardItem> awardList;
  private Activity mContext;
  private LinearLayout mFooter;
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  private BaseFragment mFragment;

  public ActivityAwardsAdapter(BaseFragment paramBaseFragment, ArrayList<ActivityAwardItem> paramArrayList)
  {
    this.awardList = paramArrayList;
    this.mFragment = paramBaseFragment;
    this.mContext = paramBaseFragment.getActivity();
    this.mFooter = ((LinearLayout)paramBaseFragment.getActivity().getLayoutInflater().inflate(2130903200, null));
    this.mFooter.setOnClickListener(new View.OnClickListener(paramBaseFragment)
    {
      public void onClick(View paramView)
      {
        ((ActivityAwardsFragment)this.val$fragment).onViewMoreClick();
      }
    });
    this.mFooterTV = ((TextView)this.mFooter.findViewById(2131362073));
    this.mFooterTV.setText(2131427748);
    this.mFooterProBar = ((ProgressBar)this.mFooter.findViewById(2131362072));
  }

  public int getCount()
  {
    if (this.awardList == null)
      return 0;
    return this.awardList.size();
  }

  public Object getItem(int paramInt)
  {
    if (this.awardList == null);
    do
      return null;
    while ((paramInt < 0) || (paramInt > this.awardList.size()));
    return this.awardList.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    try
    {
      ActivityAwardItem localActivityAwardItem = (ActivityAwardItem)getItem(paramInt);
      if (localActivityAwardItem == null)
        return paramView;
      if (-2 == localActivityAwardItem.type)
        return this.mFooter;
      AwardViewHolder localAwardViewHolder;
      if ((paramView == null) || (paramView == this.mFooter))
      {
        paramView = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903043, null);
        localAwardViewHolder = new AwardViewHolder(paramView, null);
        paramView.setTag(localAwardViewHolder);
      }
      while (true)
      {
        localAwardViewHolder.updateItem(localActivityAwardItem);
        int i = getCount();
        if (paramInt == i - 2)
        {
          if (-1 != ((ActivityAwardItem)getItem(i - 1)).type)
            break;
          localAwardViewHolder.setPaddingTop(10);
          localAwardViewHolder.setPaddingBottom(0);
          break;
          localAwardViewHolder = (AwardViewHolder)paramView.getTag();
          continue;
        }
        else
        {
          localAwardViewHolder.setPaddingTop(10);
          localAwardViewHolder.setPaddingBottom(10);
        }
      }
    }
    catch (Exception localException)
    {
      KXLog.e("ActivityAwardsAdapter", "getView", localException);
    }
    return paramView;
  }

  public boolean isFooterShowLoading()
  {
    if (this.mFooterProBar == null);
    do
      return false;
    while (this.mFooterProBar.getVisibility() != 0);
    return true;
  }

  public void showLoadingFooter(boolean paramBoolean)
  {
    ProgressBar localProgressBar;
    if (this.mFooterProBar != null)
    {
      localProgressBar = this.mFooterProBar;
      if (!paramBoolean)
        break label71;
    }
    label71: for (int j = 0; ; j = 8)
    {
      localProgressBar.setVisibility(j);
      if (this.mFooterTV != null)
      {
        int i = this.mContext.getResources().getColor(2130839419);
        if (paramBoolean)
          i = this.mContext.getResources().getColor(2130839395);
        this.mFooterTV.setTextColor(i);
      }
      return;
    }
  }

  private class AwardViewHolder
    implements View.OnClickListener
  {
    private ActivityAwardItem award;
    private ImageView btnDelete = null;
    private Button btnUseIt = null;
    private View mView = null;
    private TextView tvContent = null;
    private TextView tvTitle = null;

    private AwardViewHolder(View arg2)
    {
      Object localObject;
      this.mView = localObject;
      this.tvTitle = ((TextView)localObject.findViewById(2131361808));
      this.btnDelete = ((ImageView)localObject.findViewById(2131361809));
      this.btnDelete.setOnClickListener(this);
      this.tvContent = ((TextView)localObject.findViewById(2131361811));
      this.btnUseIt = ((Button)localObject.findViewById(2131361812));
    }

    private void setPaddingBottom(int paramInt)
    {
      int i = this.mView.getPaddingTop();
      int j = this.mView.getPaddingLeft();
      int k = this.mView.getPaddingRight();
      float f = paramInt * ActivityAwardsAdapter.this.mContext.getResources().getDisplayMetrics().density;
      this.mView.setPadding(j, i, k, (int)f);
    }

    private void setPaddingTop(int paramInt)
    {
      float f = paramInt * ActivityAwardsAdapter.this.mContext.getResources().getDisplayMetrics().density;
      int i = this.mView.getPaddingLeft();
      int j = this.mView.getPaddingRight();
      int k = this.mView.getPaddingBottom();
      this.mView.setPadding(i, (int)f, j, k);
    }

    public void onClick(View paramView)
    {
      if (paramView == this.btnUseIt)
        ((ActivityAwardsFragment)ActivityAwardsAdapter.this.mFragment).useAward(this.award.id);
      do
        return;
      while (paramView != this.btnDelete);
      ((ActivityAwardsFragment)ActivityAwardsAdapter.this.mFragment).deleteAward(this.award.id);
    }

    public void updateItem(ActivityAwardItem paramActivityAwardItem)
      throws Exception
    {
      if (paramActivityAwardItem == null)
        return;
      this.award = paramActivityAwardItem;
      this.tvTitle.setText(paramActivityAwardItem.title);
      this.tvContent.setText(paramActivityAwardItem.content);
      if (paramActivityAwardItem.type == 0)
      {
        this.btnUseIt.setVisibility(0);
        if (paramActivityAwardItem.used == 1)
        {
          this.btnUseIt.setText(2131428269);
          this.btnUseIt.setBackgroundDrawable(ActivityAwardsAdapter.this.mContext.getResources().getDrawable(2130838493));
          this.btnUseIt.setOnClickListener(null);
          return;
        }
        this.btnUseIt.setText(2131428268);
        this.btnUseIt.setBackgroundDrawable(ActivityAwardsAdapter.this.mContext.getResources().getDrawable(2130838793));
        this.btnUseIt.setOnClickListener(this);
        return;
      }
      this.btnUseIt.setVisibility(8);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.adapter.ActivityAwardsAdapter
 * JD-Core Version:    0.6.0
 */