package com.kaixin001.item;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class SystemMessageSource
{
  public String albumid;
  public String albumtitle;
  public String delete;
  public String fragment = null;
  public String intro;
  public String large;
  public String objid;
  public String ouid;
  public String picnum;
  public ArrayList<RepostAlbumItem> pics;
  public String pos;
  public String privacy;
  public String rid;
  public String thumbnail;
  public String title;
  public String type;
  public String uid;
  public String visible;

  public static SystemMessageSource valueOf(JSONObject paramJSONObject)
  {
    if (paramJSONObject != null)
    {
      SystemMessageSource localSystemMessageSource = new SystemMessageSource();
      localSystemMessageSource.title = paramJSONObject.optString("title");
      localSystemMessageSource.thumbnail = paramJSONObject.optString("thumbnail");
      localSystemMessageSource.large = paramJSONObject.optString("large");
      localSystemMessageSource.privacy = paramJSONObject.optString("privacy");
      localSystemMessageSource.albumid = paramJSONObject.optString("albumid");
      localSystemMessageSource.albumtitle = paramJSONObject.optString("albumtitle");
      localSystemMessageSource.rid = paramJSONObject.optString("rid");
      localSystemMessageSource.pos = paramJSONObject.optString("pos");
      localSystemMessageSource.picnum = paramJSONObject.optString("picnum");
      localSystemMessageSource.type = paramJSONObject.optString("type");
      localSystemMessageSource.visible = paramJSONObject.optString("visible");
      localSystemMessageSource.ouid = paramJSONObject.optString("ouid");
      localSystemMessageSource.objid = paramJSONObject.optString("objid");
      localSystemMessageSource.delete = paramJSONObject.optString("delete");
      localSystemMessageSource.uid = paramJSONObject.optString("uid");
      localSystemMessageSource.intro = paramJSONObject.optString("intro");
      localSystemMessageSource.fragment = paramJSONObject.optString("fragment");
      JSONArray localJSONArray = paramJSONObject.optJSONArray("pics");
      if (localJSONArray != null);
      for (int i = 0; ; i++)
      {
        if (i >= localJSONArray.length())
          return localSystemMessageSource;
        RepostAlbumItem localRepostAlbumItem = RepostAlbumItem.valueOf(localJSONArray.optJSONObject(i));
        if (localRepostAlbumItem == null)
          continue;
        if (localSystemMessageSource.pics == null)
          localSystemMessageSource.pics = new ArrayList();
        localSystemMessageSource.pics.add(localRepostAlbumItem);
      }
    }
    return null;
  }

  public RepostAlbumItem getPicItem()
  {
    RepostAlbumItem localRepostAlbumItem = new RepostAlbumItem();
    localRepostAlbumItem.title = this.title;
    localRepostAlbumItem.thumbnail = this.thumbnail;
    localRepostAlbumItem.large = this.large;
    localRepostAlbumItem.privacy = this.privacy;
    localRepostAlbumItem.albumid = this.albumtitle;
    localRepostAlbumItem.albumtitle = this.albumtitle;
    localRepostAlbumItem.rid = this.rid;
    localRepostAlbumItem.pos = this.pos;
    localRepostAlbumItem.picnum = this.picnum;
    localRepostAlbumItem.type = this.type;
    return localRepostAlbumItem;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.SystemMessageSource
 * JD-Core Version:    0.6.0
 */