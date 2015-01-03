package com.kaixin001.item;

public class PKRecordListItem
{
  private String content;
  private String imgURL;
  private int pktype;

  public PKRecordListItem()
  {
  }

  public PKRecordListItem(int paramInt)
  {
    this.pktype = paramInt;
  }

  public String getContent()
  {
    return this.content;
  }

  public String getImgURL()
  {
    return this.imgURL;
  }

  public int getPktype()
  {
    return this.pktype;
  }

  public void setContent(String paramString)
  {
    this.content = paramString;
  }

  public void setImgURL(String paramString)
  {
    this.imgURL = paramString;
  }

  public void setPktype(int paramInt)
  {
    this.pktype = paramInt;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.PKRecordListItem
 * JD-Core Version:    0.6.0
 */