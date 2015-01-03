package com.kaixin001.adapter;

import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.item.RepItem;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import java.util.ArrayList;

public class RepostListAdapter extends BaseAdapter
{
  private BaseFragment mBaseFragment;
  private View mFooterView;
  private ArrayList<RepItem> mListRepost;

  public RepostListAdapter(BaseFragment paramBaseFragment, View paramView, ArrayList<RepItem> paramArrayList)
  {
    this.mBaseFragment = paramBaseFragment;
    this.mFooterView = paramView;
    this.mListRepost = paramArrayList;
  }

  protected void fillView(View paramView, RepItem paramRepItem)
  {
    ((ImageView)paramView.findViewById(2131363626)).setImageResource(getTypeImg(paramRepItem.category));
    TextView localTextView1 = (TextView)paramView.findViewById(2131363628);
    localTextView1.setMaxLines(2);
    localTextView1.setText(paramRepItem.title);
    ImageView localImageView1 = (ImageView)paramView.findViewById(2131363630);
    TextView localTextView2 = (TextView)paramView.findViewById(2131363633);
    TextView localTextView3 = (TextView)paramView.findViewById(2131363634);
    if (paramRepItem.mHideUserInfo)
    {
      paramView.findViewById(2131363629).setVisibility(8);
      localTextView2.setVisibility(8);
      localTextView3.setText(this.mBaseFragment.getResources().getString(2131428478).replace("*", String.valueOf(paramRepItem.mRepostNum)));
    }
    while (true)
    {
      ((TextView)paramView.findViewById(2131363632)).setText(paramRepItem.stime);
      if ((!TextUtils.isEmpty(paramRepItem.mContent)) || (!TextUtils.isEmpty(paramRepItem.mThumbImg)))
        break;
      paramView.findViewById(2131363635).setVisibility(8);
      paramView.findViewById(2131363637).setVisibility(8);
      return;
      paramView.findViewById(2131363629).setVisibility(8);
      localTextView2.setVisibility(0);
      this.mBaseFragment.displayRoundPicture(localImageView1, paramRepItem.flogo, ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
      String str = paramRepItem.fname;
      if ((str != null) && (str.length() > 6))
        str = paramRepItem.fname.substring(0, 5) + "...";
      localTextView2.setText(str);
      localTextView3.setText(2131427766);
    }
    paramView.findViewById(2131363635).setVisibility(0);
    ImageView localImageView2 = (ImageView)paramView.findViewById(2131363638);
    if (TextUtils.isEmpty(paramRepItem.mThumbImg))
      paramView.findViewById(2131363637).setVisibility(8);
    while (true)
    {
      TextView localTextView4 = (TextView)paramView.findViewById(2131363636);
      localTextView4.setMaxLines(2);
      localTextView4.setText(paramRepItem.mContent);
      return;
      paramView.findViewById(2131363637).setVisibility(0);
      this.mBaseFragment.displayPicture(localImageView2, paramRepItem.mThumbImg, 2130838784);
    }
  }

  public int getCount()
  {
    if (this.mListRepost == null)
      return 0;
    return this.mListRepost.size();
  }

  public Object getItem(int paramInt)
  {
    if (this.mListRepost == null);
    do
      return null;
    while ((paramInt < 0) || (paramInt >= this.mListRepost.size()));
    return this.mListRepost.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  protected int getTypeImg(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return 2130838882;
    case 10:
      return 2130838882;
    case 30:
      return 2130838777;
    case 40:
      return 2130838708;
    case 20:
    }
    return 2130837758;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    RepItem localRepItem = (RepItem)getItem(paramInt);
    if (localRepItem == null)
      return paramView;
    if (TextUtils.isEmpty(localRepItem.id))
      paramView = this.mFooterView;
    while (true)
    {
      return paramView;
      if ((paramView == null) || (paramView == this.mFooterView))
        paramView = this.mBaseFragment.getActivity().getLayoutInflater().inflate(2130903335, null);
      fillView(paramView, localRepItem);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.adapter.RepostListAdapter
 * JD-Core Version:    0.6.0
 */