package com.kaixin001.item;

public class FriendPhotoInfo
{
  public String albumId;
  public boolean bNeedPwd;
  public String ctime;
  public String imageName;
  public String img;
  public String ownerId;
  public String ownerName;
  public int picTotal;
  public String pid;
  public int pos;
  public String time;
  public String vnum;

  public FriendPhotoInfo clone(FriendPhotoInfo paramFriendPhotoInfo)
  {
    this.ownerId = paramFriendPhotoInfo.ownerId;
    this.ownerName = paramFriendPhotoInfo.ownerName;
    this.imageName = paramFriendPhotoInfo.imageName;
    this.img = paramFriendPhotoInfo.img;
    this.albumId = paramFriendPhotoInfo.albumId;
    this.pid = paramFriendPhotoInfo.pid;
    this.vnum = paramFriendPhotoInfo.vnum;
    this.ctime = paramFriendPhotoInfo.ctime;
    this.time = paramFriendPhotoInfo.time;
    this.pos = paramFriendPhotoInfo.pos;
    this.picTotal = paramFriendPhotoInfo.picTotal;
    this.bNeedPwd = paramFriendPhotoInfo.bNeedPwd;
    return this;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[ownerId]=").append(this.ownerId).append(" ");
    localStringBuffer.append("[ownerName]=").append(this.ownerName).append(" ");
    localStringBuffer.append("[imageName]=").append(this.imageName).append(" ");
    localStringBuffer.append("[albumId]=").append(this.albumId).append(" ");
    localStringBuffer.append("[pos]=").append(this.pos).append(" ");
    return localStringBuffer.toString();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.FriendPhotoInfo
 * JD-Core Version:    0.6.0
 */