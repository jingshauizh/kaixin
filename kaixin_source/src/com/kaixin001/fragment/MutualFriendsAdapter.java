package com.kaixin001.fragment;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.kaixin001.model.HomePeopleInfoModel.PeopleInfo;
import java.util.ArrayList;

class MutualFriendsAdapter extends ArrayAdapter<HomePeopleInfoModel.PeopleInfo>
  implements View.OnClickListener
{
  private Activity mContext;
  public View mFooter;
  public ProgressBar mFooterProBar;
  public TextView mFooterTV;
  private BaseFragment mFragment;

  public MutualFriendsAdapter(BaseFragment paramBaseFragment, int paramInt, ArrayList<HomePeopleInfoModel.PeopleInfo> paramArrayList)
  {
    super(paramBaseFragment.getActivity(), paramInt, paramArrayList);
    this.mFragment = paramBaseFragment;
    this.mContext = paramBaseFragment.getActivity();
    this.mFooter = this.mContext.getLayoutInflater().inflate(2130903259, null);
    this.mFooter.setOnClickListener(this);
    this.mFooterTV = ((TextView)this.mFooter.findViewById(2131362073));
    this.mFooterProBar = ((ProgressBar)this.mFooter.findViewById(2131362072));
    this.mFooterProBar.setVisibility(4);
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    HomePeopleInfoModel.PeopleInfo localPeopleInfo = (HomePeopleInfoModel.PeopleInfo)getItem(paramInt);
    if (TextUtils.isEmpty(localPeopleInfo.mUid))
      return this.mFooter;
    if ((paramView == null) || (paramView == this.mFooter))
      paramView = newView();
    CacheView localCacheView = (CacheView)paramView.getTag();
    this.mFragment.displayPicture(localCacheView.mAvatar, localPeopleInfo.mAvatar, 2130838676);
    localCacheView.mName.setText(localPeopleInfo.mName);
    return paramView;
  }

  public View newView()
  {
    CacheView localCacheView = new CacheView();
    View localView = ((Activity)getContext()).getLayoutInflater().inflate(2130903236, null);
    localView.setTag(localCacheView);
    localCacheView.mAvatar = ((ImageView)localView.findViewById(2131361829));
    localCacheView.mName = ((TextView)localView.findViewById(2131361834));
    return localView;
  }

  public void onClick(View paramView)
  {
  }

  class CacheView
  {
    ImageView mAvatar = null;
    TextView mName = null;

    CacheView()
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.MutualFriendsAdapter
 * JD-Core Version:    0.6.0
 */