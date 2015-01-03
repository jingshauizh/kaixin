package com.kaixin001.item;

public class HomeVisitorItem
{
  public String gender;
  public String icon;
  public String isOnline;
  public String name;
  public String time;
  public String type;
  public String uid;

  public HomeVisitorItem(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    this.uid = paramString1;
    this.name = paramString2;
    this.icon = paramString3;
    this.gender = paramString4;
    this.type = paramString5;
    this.time = paramString6;
    this.isOnline = paramString7;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.HomeVisitorItem
 * JD-Core Version:    0.6.0
 */