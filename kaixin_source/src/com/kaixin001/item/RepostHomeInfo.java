package com.kaixin001.item;

public class RepostHomeInfo
{
  public String id = null;
  public String strctime = null;
  public String title = null;
  public int type = 1;

  public RepostHomeInfo clone(RepostHomeInfo paramRepostHomeInfo)
  {
    this.id = paramRepostHomeInfo.id;
    this.title = paramRepostHomeInfo.title;
    this.strctime = paramRepostHomeInfo.strctime;
    this.type = paramRepostHomeInfo.type;
    return this;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.RepostHomeInfo
 * JD-Core Version:    0.6.0
 */