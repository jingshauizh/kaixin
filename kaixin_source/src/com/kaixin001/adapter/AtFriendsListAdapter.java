package com.kaixin001.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.kaixin001.model.FriendsModel.Friend;
import java.util.ArrayList;

public class AtFriendsListAdapter extends BaseAdapter
{
  private LayoutInflater inflater;
  private ArrayList<FriendsModel.Friend> mFriendsList;

  public AtFriendsListAdapter(Context paramContext, ArrayList<FriendsModel.Friend> paramArrayList)
  {
    this.inflater = LayoutInflater.from(paramContext);
    this.mFriendsList = paramArrayList;
  }

  public int getCount()
  {
    return this.mFriendsList.size();
  }

  public Object getItem(int paramInt)
  {
    return this.mFriendsList.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null)
    {
      paramView = this.inflater.inflate(2130903202, null);
      ViewHolder localViewHolder2 = new ViewHolder(null);
      localViewHolder2.realNameText = ((TextView)paramView.findViewById(2131362966));
      paramView.setTag(localViewHolder2);
    }
    ViewHolder localViewHolder1 = (ViewHolder)paramView.getTag();
    FriendsModel.Friend localFriend = (FriendsModel.Friend)this.mFriendsList.get(paramInt);
    localViewHolder1.realNameText.setText(localFriend.getFname());
    return paramView;
  }

  private class ViewHolder
  {
    public TextView realNameText;

    private ViewHolder()
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.adapter.AtFriendsListAdapter
 * JD-Core Version:    0.6.0
 */