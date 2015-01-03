package com.kaixin001.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public abstract class CheckBoxListAdapter<T> extends BaseAdapter
{
  private Context mContext = null;
  private int mSelectIndex = 0;

  public CheckBoxListAdapter(Context paramContext)
  {
    this.mContext = paramContext;
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    View localView = paramView;
    if (localView == null)
    {
      localView = ((Activity)this.mContext).getLayoutInflater().inflate(2130903053, paramViewGroup, false);
      ViewHolder localViewHolder2 = new ViewHolder(null);
      localViewHolder2.nameView = ((TextView)localView.findViewById(2131361847));
      localViewHolder2.albumChoseMark = localView.findViewById(2131361846);
      localView.setTag(localViewHolder2);
    }
    try
    {
      ViewHolder localViewHolder1 = (ViewHolder)localView.getTag();
      String str = (String)getItem(paramInt);
      localViewHolder1.nameView.setText(str);
      if (this.mSelectIndex == paramInt)
      {
        localViewHolder1.albumChoseMark.setVisibility(0);
        return localView;
      }
      localViewHolder1.albumChoseMark.setVisibility(4);
      return localView;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localView;
  }

  public void setSelectIndex(int paramInt)
  {
    this.mSelectIndex = paramInt;
  }

  private class ViewHolder
  {
    public View albumChoseMark;
    public TextView nameView;

    private ViewHolder()
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.adapter.CheckBoxListAdapter
 * JD-Core Version:    0.6.0
 */