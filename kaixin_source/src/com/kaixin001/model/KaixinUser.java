package com.kaixin001.model;

public class KaixinUser
{
  public int gender = -1;
  public String logo;
  public int online = -1;
  public String realname;
  public int relation = -1;
  public String uid;

  public void clear()
  {
    this.uid = null;
    this.realname = null;
    this.logo = null;
    this.relation = -1;
    this.gender = -1;
    this.online = -1;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.KaixinUser
 * JD-Core Version:    0.6.0
 */