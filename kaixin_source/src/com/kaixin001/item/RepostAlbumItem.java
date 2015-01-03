package com.kaixin001.item;

import org.json.JSONObject;

public class RepostAlbumItem
{
  public String albumid;
  public String albumtitle;
  public String large;
  public String picnum;
  public String pos;
  public String privacy;
  public String rid;
  public String thumbnail;
  public String title;
  public String type;
  public String visible;

  public static RepostAlbumItem valueOf(JSONObject paramJSONObject)
  {
    if (paramJSONObject != null)
    {
      RepostAlbumItem localRepostAlbumItem = new RepostAlbumItem();
      localRepostAlbumItem.title = paramJSONObject.optString("title");
      localRepostAlbumItem.thumbnail = paramJSONObject.optString("thumbnail");
      localRepostAlbumItem.large = paramJSONObject.optString("large");
      localRepostAlbumItem.privacy = paramJSONObject.optString("privacy");
      localRepostAlbumItem.albumid = paramJSONObject.optString("albumid");
      localRepostAlbumItem.albumtitle = paramJSONObject.optString("albumtitle");
      localRepostAlbumItem.rid = paramJSONObject.optString("rid");
      localRepostAlbumItem.pos = paramJSONObject.optString("pos");
      localRepostAlbumItem.picnum = paramJSONObject.optString("picnum");
      localRepostAlbumItem.type = paramJSONObject.optString("type");
      localRepostAlbumItem.visible = paramJSONObject.optString("visible");
      return localRepostAlbumItem;
    }
    return null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.RepostAlbumItem
 * JD-Core Version:    0.6.0
 */