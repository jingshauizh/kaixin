package com.kaixin001.util;

import android.view.View.OnClickListener;

public class SettingListDate
{
  public View.OnClickListener listener;
  public String settingString;

  public SettingListDate(String paramString, View.OnClickListener paramOnClickListener)
  {
    this.settingString = paramString;
    this.listener = paramOnClickListener;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.SettingListDate
 * JD-Core Version:    0.6.0
 */