package com.kaixin001.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.kaixin001.activity.OnViewMoreClickListener;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.model.CircleModel.CircleItem;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;

public class CirclesAdapter extends ArrayAdapter<CircleModel.CircleItem>
{
  public static final int[] CIRCLE_ICONS = { 2130837689, 2130837690, 2130837691, 2130837692, 2130837693, 2130837694, 2130837695, 2130837696 };
  private static final String TAG = "CirclesAdapter";
  ArrayList<CircleModel.CircleItem> circleList;
  private View lytFooter;
  private View mFooter;
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  private BaseFragment mFragment;
  private int total;

  public CirclesAdapter(BaseFragment paramBaseFragment, int paramInt, ArrayList<CircleModel.CircleItem> paramArrayList)
  {
    super(paramBaseFragment.getActivity(), paramInt, paramArrayList);
    this.circleList = paramArrayList;
    this.mFragment = paramBaseFragment;
    this.mFooter = paramBaseFragment.getActivity().getLayoutInflater().inflate(2130903079, null);
    this.mFooter.setOnClickListener(new View.OnClickListener(paramBaseFragment)
    {
      public void onClick(View paramView)
      {
        ((OnViewMoreClickListener)this.val$fragment).onViewMoreClick();
      }
    });
    this.lytFooter = this.mFooter.findViewById(2131362071);
    this.lytFooter.setBackgroundResource(2130837699);
    this.lytFooter.setDuplicateParentStateEnabled(true);
    this.mFooterTV = ((TextView)this.mFooter.findViewById(2131362073));
    this.mFooterTV.setText(2131427748);
    this.mFooterProBar = ((ProgressBar)this.mFooter.findViewById(2131362072));
  }

  public static Drawable getCircleTypeImage(Context paramContext, int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= CIRCLE_ICONS.length))
      paramInt = 0;
    return paramContext.getResources().getDrawable(CIRCLE_ICONS[paramInt]);
  }

  public int getCount()
  {
    if (this.circleList == null)
      return 0;
    return this.circleList.size();
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramInt >= this.circleList.size())
      return paramView;
    try
    {
      CircleModel.CircleItem localCircleItem = (CircleModel.CircleItem)this.circleList.get(paramInt);
      if (TextUtils.isEmpty(localCircleItem.gid))
      {
        this.lytFooter.setBackgroundResource(2130837699);
        return this.mFooter;
      }
      if ((paramView == null) || (paramView == this.mFooter))
      {
        paramView = ((LayoutInflater)this.mFragment.getActivity().getSystemService("layout_inflater")).inflate(2130903074, null);
        localCircleItemViewTag = new CircleItemViewTag(paramView, null);
        paramView.setTag(localCircleItemViewTag);
        localCircleItemViewTag.updateCircleItem(localCircleItem);
        if ((paramInt != 0) || (this.total != 1))
          break label203;
        localCircleItemViewTag.rlytCircle.setBackgroundResource(2130837701);
      }
      while (true)
      {
        ViewGroup.LayoutParams localLayoutParams = localCircleItemViewTag.rlytCircle.getLayoutParams();
        if (!(localLayoutParams instanceof ViewGroup.MarginLayoutParams))
          break label278;
        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)localLayoutParams;
        int i = -1 + this.total;
        int j = 0;
        if (paramInt == i)
          j = 10;
        localMarginLayoutParams.setMargins(0, 0, 0, j);
        break label278;
        localCircleItemViewTag = (CircleItemViewTag)paramView.getTag();
        break;
        label203: if ((paramInt != 0) || (this.total == 1))
          break label242;
        localCircleItemViewTag.rlytCircle.setBackgroundResource(2130837702);
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        CircleItemViewTag localCircleItemViewTag;
        KXLog.e("CirclesAdapter", "getView", localException);
        break;
        label242: if (paramInt == -1 + this.total)
        {
          localCircleItemViewTag.rlytCircle.setBackgroundResource(2130837699);
          continue;
        }
        localCircleItemViewTag.rlytCircle.setBackgroundResource(2130837700);
      }
    }
    label278: return paramView;
  }

  public boolean isFooterShowLoading()
  {
    if (this.mFooterProBar == null);
    do
      return false;
    while (this.mFooterProBar.getVisibility() != 0);
    return true;
  }

  public void setTotalItem(int paramInt)
  {
    this.total = paramInt;
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
    label71: for (int j = 0; ; j = 4)
    {
      localProgressBar.setVisibility(j);
      if (this.mFooterTV != null)
      {
        int i = this.mFragment.getResources().getColor(2130839419);
        if (paramBoolean)
          i = this.mFragment.getResources().getColor(2130839395);
        this.mFooterTV.setTextColor(i);
      }
      return;
    }
  }

  private class CircleItemViewTag
  {
    private String gid;
    private String gname;
    private int gtype;
    private boolean isHasNews;
    private ImageView ivHasNews;
    private ImageView ivLogo;
    RelativeLayout rlytCircle;
    private TextView tvName = null;

    private CircleItemViewTag(View arg2)
    {
      Object localObject;
      this.rlytCircle = ((RelativeLayout)localObject.findViewById(2131362047));
      this.ivHasNews = ((ImageView)localObject.findViewById(2131362048));
      this.ivLogo = ((ImageView)localObject.findViewById(2131362049));
      this.tvName = ((TextView)localObject.findViewById(2131362051));
    }

    public void updateCircleItem(CircleModel.CircleItem paramCircleItem)
      throws Exception
    {
      this.ivLogo.setImageDrawable(CirclesAdapter.getCircleTypeImage(CirclesAdapter.this.mFragment.getActivity(), paramCircleItem.type));
      this.gid = paramCircleItem.gid;
      this.gname = paramCircleItem.gname;
      this.gtype = paramCircleItem.type;
      this.tvName.setText(this.gname);
      this.tvName.setTextColor(CirclesAdapter.this.mFragment.getResources().getColor(2130839419));
      this.tvName.setPadding(0, 2, 0, 2);
      boolean bool1;
      ImageView localImageView;
      int i;
      if (paramCircleItem.hasnews > 0)
      {
        bool1 = true;
        this.isHasNews = bool1;
        localImageView = this.ivHasNews;
        boolean bool2 = this.isHasNews;
        i = 0;
        if (!bool2)
          break label137;
      }
      while (true)
      {
        localImageView.setVisibility(i);
        return;
        bool1 = false;
        break;
        label137: i = 4;
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.adapter.CirclesAdapter
 * JD-Core Version:    0.6.0
 */