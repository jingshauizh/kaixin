package com.kaixin001.item;

import java.util.ArrayList;

public class CircleDetailsItem
{
  public String ctime = null;
  public String icon50 = null;
  public ArrayList<CircleDetailsContent> mContentList = new ArrayList();
  public String name = null;
  public String uid = null;

  public String getFlogo()
  {
    return this.icon50;
  }

  public String getFname()
  {
    return this.name;
  }

  public String getFuid()
  {
    return this.uid;
  }

  public String getStime()
  {
    return this.ctime;
  }

  public static class CircleDetailsContent
  {
    public String content = null;
    public int ntype = -1;
    public String uid = null;
    public String uname = null;

    public CircleDetailsContent()
    {
    }

    public CircleDetailsContent(String paramString, int paramInt)
    {
      this.content = paramString;
      this.ntype = paramInt;
    }

    public String getContent()
    {
      return this.content;
    }

    public int getType()
    {
      return this.ntype;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.CircleDetailsItem
 * JD-Core Version:    0.6.0
 */