package com.kaixin001.item;

public class ActivityAwardItem
{
  public static final int AWARD_TYPE_COUPON = 0;
  public static final int AWARD_TYPE_LOTTERY = 1;
  public String aid;
  public String content;
  public String id;
  public String title;
  public int type = -1;
  public String uid;
  public int used = -1;

  public void clear()
  {
    this.id = null;
    this.uid = null;
    this.aid = null;
    this.title = null;
    this.content = null;
    this.type = -1;
    this.used = -1;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.ActivityAwardItem
 * JD-Core Version:    0.6.0
 */