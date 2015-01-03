package com.kaixin001.model;

import com.kaixin001.item.PKInfoItem;
import com.kaixin001.item.PKRecordListItem;
import java.util.List;

public class PKModel
{
  private static PKModel instance;
  private List<PKRecordListItem> blueList;
  private PKInfoItem pkInfoItem;
  private String pkType;
  private String pkid;
  private List<PKRecordListItem> redList;
  private String word;

  public static PKModel getInstance()
  {
    if (instance == null)
      instance = new PKModel();
    return instance;
  }

  public List<PKRecordListItem> getBlueList()
  {
    return this.blueList;
  }

  public PKInfoItem getPkInfoItem()
  {
    return this.pkInfoItem;
  }

  public String getPkType()
  {
    return this.pkType;
  }

  public String getPkid()
  {
    return this.pkid;
  }

  public List<PKRecordListItem> getRedList()
  {
    return this.redList;
  }

  public String getWord()
  {
    return this.word;
  }

  public void setBlueList(List<PKRecordListItem> paramList)
  {
    this.blueList = paramList;
  }

  public void setPkInfoItem(PKInfoItem paramPKInfoItem)
  {
    this.pkInfoItem = paramPKInfoItem;
  }

  public void setPkType(String paramString)
  {
    this.pkType = paramString;
  }

  public void setPkid(String paramString)
  {
    this.pkid = paramString;
  }

  public void setRedList(List<PKRecordListItem> paramList)
  {
    this.redList = paramList;
  }

  public void setWord(String paramString)
  {
    this.word = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.PKModel
 * JD-Core Version:    0.6.0
 */