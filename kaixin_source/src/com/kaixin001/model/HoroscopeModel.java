package com.kaixin001.model;

import com.kaixin001.item.HoroscopeData;
import com.kaixin001.item.UserInfoItem;

public class HoroscopeModel extends KXModel
{
  private HoroscopeData horoscopeData = null;
  private UserInfoItem userInfoItem = null;

  public HoroscopeModel()
  {
    if (this.userInfoItem == null)
      this.userInfoItem = new UserInfoItem();
    if (this.horoscopeData == null)
      this.horoscopeData = new HoroscopeData();
  }

  public void clear()
  {
    this.userInfoItem = null;
    this.horoscopeData = null;
  }

  public HoroscopeData getHoroscopeData()
  {
    return this.horoscopeData;
  }

  public UserInfoItem getUserInfoItem()
  {
    return this.userInfoItem;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.HoroscopeModel
 * JD-Core Version:    0.6.0
 */