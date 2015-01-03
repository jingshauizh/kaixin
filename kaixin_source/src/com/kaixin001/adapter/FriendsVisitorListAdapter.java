package com.kaixin001.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.fragment.HomeFragment;
import com.kaixin001.item.HomeVisitorItem;
import com.kaixin001.model.HomeVisitorModel;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import java.util.ArrayList;

public class FriendsVisitorListAdapter extends BaseAdapter
{
  private Activity context;
  ArrayList<HomeVisitorItem> mAllList = new ArrayList();
  private BaseFragment mFragment;
  HomeVisitorModel model;
  LayoutInflater vi;

  public FriendsVisitorListAdapter(BaseFragment paramBaseFragment, HomeVisitorModel paramHomeVisitorModel)
  {
    this.mFragment = paramBaseFragment;
    this.context = paramBaseFragment.getActivity();
    this.model = paramHomeVisitorModel;
    this.mAllList.clear();
    this.mAllList.addAll(this.model.getVisitorList());
    this.vi = ((LayoutInflater)paramBaseFragment.getActivity().getSystemService("layout_inflater"));
  }

  public int getCount()
  {
    return (3 + this.mAllList.size()) / 4;
  }

  public Object getItem(int paramInt)
  {
    return null;
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    VisitorFriends4ViewTag localVisitorFriends4ViewTag;
    if ((paramView == null) || (paramView.getTag() == null))
    {
      paramView = this.vi.inflate(2130903157, null);
      localVisitorFriends4ViewTag = new VisitorFriends4ViewTag(this.mFragment, paramView, this.mAllList);
      paramView.setTag(localVisitorFriends4ViewTag);
    }
    while (true)
    {
      localVisitorFriends4ViewTag.showItem(paramInt);
      return paramView;
      localVisitorFriends4ViewTag = (VisitorFriends4ViewTag)paramView.getTag();
    }
  }

  public boolean isEnabled(int paramInt)
  {
    return false;
  }

  public void notifyDataSetChanged()
  {
    this.mAllList.clear();
    this.mAllList.addAll(this.model.getVisitorList());
    super.notifyDataSetChanged();
  }

  static class VisitorFriends4ViewTag
  {
    RelativeLayout item1;
    RelativeLayout item2;
    RelativeLayout item3;
    RelativeLayout item4;
    ArrayList<HomeVisitorItem> mList;
    FriendsVisitorListAdapter.VisitorFriendsViewTag visitor1;
    FriendsVisitorListAdapter.VisitorFriendsViewTag visitor2;
    FriendsVisitorListAdapter.VisitorFriendsViewTag visitor3;
    FriendsVisitorListAdapter.VisitorFriendsViewTag visitor4;

    public VisitorFriends4ViewTag(BaseFragment paramBaseFragment, View paramView, ArrayList<HomeVisitorItem> paramArrayList)
    {
      this.item1 = ((RelativeLayout)paramView.findViewById(2131362686));
      this.item2 = ((RelativeLayout)paramView.findViewById(2131362693));
      this.item3 = ((RelativeLayout)paramView.findViewById(2131362694));
      this.item4 = ((RelativeLayout)paramView.findViewById(2131362695));
      this.visitor1 = new FriendsVisitorListAdapter.VisitorFriendsViewTag(this.item1, paramBaseFragment);
      this.visitor2 = new FriendsVisitorListAdapter.VisitorFriendsViewTag(this.item2, paramBaseFragment);
      this.visitor3 = new FriendsVisitorListAdapter.VisitorFriendsViewTag(this.item3, paramBaseFragment);
      this.visitor4 = new FriendsVisitorListAdapter.VisitorFriendsViewTag(this.item4, paramBaseFragment);
      this.mList = paramArrayList;
    }

    public void showItem(int paramInt)
    {
      int i = this.mList.size();
      this.item1.setVisibility(4);
      this.item2.setVisibility(4);
      this.item3.setVisibility(4);
      this.item4.setVisibility(4);
      if (i > paramInt * 4)
      {
        this.visitor1.showItem((HomeVisitorItem)this.mList.get(paramInt * 4));
        this.item1.setVisibility(0);
      }
      if (i > 1 + paramInt * 4)
      {
        this.visitor2.showItem((HomeVisitorItem)this.mList.get(1 + paramInt * 4));
        this.item2.setVisibility(0);
      }
      if (i > 2 + paramInt * 4)
      {
        this.visitor3.showItem((HomeVisitorItem)this.mList.get(2 + paramInt * 4));
        this.item3.setVisibility(0);
      }
      if (i > 3 + paramInt * 4)
      {
        this.visitor4.showItem((HomeVisitorItem)this.mList.get(3 + paramInt * 4));
        this.item4.setVisibility(0);
      }
    }
  }

  static class VisitorFriendsViewTag
  {
    ImageView isOnline;
    RelativeLayout item;
    ImageView logo;
    BaseFragment mFragment;
    TextView name;
    TextView time;

    public VisitorFriendsViewTag(RelativeLayout paramRelativeLayout, BaseFragment paramBaseFragment)
    {
      this.item = paramRelativeLayout;
      this.logo = ((ImageView)this.item.findViewById(2131362688));
      this.name = ((TextView)this.item.findViewById(2131362690));
      this.isOnline = ((ImageView)this.item.findViewById(2131362691));
      this.time = ((TextView)this.item.findViewById(2131362692));
      this.mFragment = paramBaseFragment;
    }

    public void showItem(HomeVisitorItem paramHomeVisitorItem)
    {
      if (paramHomeVisitorItem == null)
      {
        this.item.setVisibility(4);
        return;
      }
      this.item.setVisibility(0);
      this.mFragment.displayRoundPicture(this.logo, paramHomeVisitorItem.icon, ImageDownloader.RoundCornerType.hdpi_small, 2130838334);
      this.logo.setTag(paramHomeVisitorItem);
      this.logo.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          HomeVisitorItem localHomeVisitorItem = (HomeVisitorItem)paramView.getTag();
          Intent localIntent = new Intent(FriendsVisitorListAdapter.VisitorFriendsViewTag.this.mFragment.getActivity(), HomeFragment.class);
          Bundle localBundle = new Bundle();
          localBundle.putString("fuid", localHomeVisitorItem.uid);
          localBundle.putString("fname", localHomeVisitorItem.name);
          localBundle.putString("flogo", localHomeVisitorItem.icon);
          localIntent.putExtras(localBundle);
          FriendsVisitorListAdapter.VisitorFriendsViewTag.this.mFragment.startFragment(localIntent, true, 1);
        }
      });
      this.name.setText(paramHomeVisitorItem.name);
      if ("1".equals(paramHomeVisitorItem.isOnline))
        this.isOnline.setImageResource(2130838704);
      while (!TextUtils.isEmpty(paramHomeVisitorItem.time))
      {
        this.time.setText(paramHomeVisitorItem.time);
        return;
        if ("2".equals(paramHomeVisitorItem.isOnline))
        {
          this.isOnline.setImageResource(2130838621);
          continue;
        }
        this.isOnline.setImageBitmap(null);
      }
      this.time.setVisibility(8);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.adapter.FriendsVisitorListAdapter
 * JD-Core Version:    0.6.0
 */