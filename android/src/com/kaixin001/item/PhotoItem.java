package com.kaixin001.item;

import java.io.Serializable;

public class PhotoItem extends KaixinPhotoItem
  implements Serializable
{
  public String albumCover = null;
  public String albumTitle = null;
  public int albumTotal = 0;
  public int cnum = 0;
  public String fuid = null;
  public int isFriend = 0;
  public String privacy = null;
  public int selfUp = -1;
  public String visible = null;
  public int visitorNum = -1;

  public PhotoItem clone(PhotoItem paramPhotoItem)
  {
    this.privacy = paramPhotoItem.privacy;
    this.fuid = paramPhotoItem.fuid;
    this.index = paramPhotoItem.index;
    this.albumTitle = paramPhotoItem.albumTitle;
    this.albumId = paramPhotoItem.albumId;
    this.albumType = paramPhotoItem.albumType;
    this.thumbnail = paramPhotoItem.thumbnail;
    this.large = paramPhotoItem.large;
    this.pid = paramPhotoItem.pid;
    this.title = paramPhotoItem.title;
    this.picnum = paramPhotoItem.picnum;
    this.visitorNum = paramPhotoItem.visitorNum;
    this.selfUp = paramPhotoItem.selfUp;
    this.cnum = paramPhotoItem.cnum;
    this.isFriend = paramPhotoItem.isFriend;
    this.albumTotal = paramPhotoItem.albumTotal;
    this.albumCover = paramPhotoItem.albumCover;
    this.ctime = paramPhotoItem.ctime;
    this.visible = paramPhotoItem.visible;
    return this;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.PhotoItem
 * JD-Core Version:    0.6.0
 */