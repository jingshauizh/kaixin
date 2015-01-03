package com.kaixin001.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.kaixin001.util.SettingListDate;
import java.util.ArrayList;

public class SettingAdapter extends BaseAdapter
{
  private Context mContext;
  private ArrayList<SettingListDate> mSetting = null;

  public SettingAdapter(Context paramContext)
  {
    this.mContext = paramContext;
  }

  public void addSetting(ArrayList<SettingListDate> paramArrayList)
  {
    this.mSetting = paramArrayList;
  }

  public int getCount()
  {
    return this.mSetting.size();
  }

  public Object getItem(int paramInt)
  {
    return this.mSetting.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    SettingListDate localSettingListDate = (SettingListDate)getItem(paramInt);
    if (paramView == null)
    {
      paramView = View.inflate(this.mContext, 2130903333, null);
      paramView.setTag(new ViewHolder(paramView));
    }
    ViewHolder localViewHolder = (ViewHolder)paramView.getTag();
    localViewHolder.setting.setText(localSettingListDate.settingString);
    localViewHolder.setting.setOnClickListener(localSettingListDate.listener);
    return paramView;
  }

  public static class ViewHolder
  {
    public TextView setting;

    public ViewHolder(View paramView)
    {
      this.setting = ((TextView)paramView.findViewById(2131363619));
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.adapter.SettingAdapter
 * JD-Core Version:    0.6.0
 */