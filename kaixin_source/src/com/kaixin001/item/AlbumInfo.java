package com.kaixin001.item;

public class AlbumInfo
{
  public String albumsFeedAlbumid = null;
  public String albumsFeedAlbumtitle = null;
  public String albumsFeedCoverpic = null;
  public String albumsFeedCoverpic130 = null;
  public String albumsFeedCoverpid = null;
  public String albumsFeedCtime = null;
  public boolean albumsFeedIsFriend = false;
  public String albumsFeedMtime = null;
  public String albumsFeedPicnum = null;
  public String albumsFeedPrivacy = null;

  public AlbumInfo clone(AlbumInfo paramAlbumInfo)
  {
    this.albumsFeedPrivacy = paramAlbumInfo.albumsFeedPrivacy;
    this.albumsFeedCtime = paramAlbumInfo.albumsFeedCtime;
    this.albumsFeedMtime = paramAlbumInfo.albumsFeedMtime;
    this.albumsFeedCoverpid = paramAlbumInfo.albumsFeedCoverpid;
    this.albumsFeedCoverpic = paramAlbumInfo.albumsFeedCoverpic;
    this.albumsFeedCoverpic130 = paramAlbumInfo.albumsFeedCoverpic130;
    this.albumsFeedAlbumid = paramAlbumInfo.albumsFeedAlbumid;
    this.albumsFeedPicnum = paramAlbumInfo.albumsFeedPicnum;
    this.albumsFeedAlbumtitle = paramAlbumInfo.albumsFeedAlbumtitle;
    this.albumsFeedIsFriend = paramAlbumInfo.albumsFeedIsFriend;
    return this;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.AlbumInfo
 * JD-Core Version:    0.6.0
 */