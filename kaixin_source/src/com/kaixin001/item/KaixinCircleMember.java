package com.kaixin001.item;

import com.kaixin001.model.KaixinUser;

public class KaixinCircleMember extends KaixinUser
{
  public static final int RELATION_2ME_FANS = 2;
  public static final int RELATION_2ME_FRIEND = 3;
  public static final int RELATION_2ME_IDOL = 1;
  public static final int RELATION_2ME_STRANGER = 0;
  public static final int RELATION_ADMIN = 2;
  public static final int RELATION_CREATOR = 1;
  public static final int RELATION_MEMBER = 3;
  public String city;
  public String company;
  public int grelation = -1;
  public String school;

  public void clear()
  {
    super.clear();
    this.city = null;
    this.company = null;
    this.school = null;
    this.grelation = -1;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.KaixinCircleMember
 * JD-Core Version:    0.6.0
 */