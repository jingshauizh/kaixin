package com.kaixin001.model;

import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.item.CircleAlbumItem;
import com.kaixin001.item.KaixinPhotoItem;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class CircleAlbumModel extends KXModel
{
  private static final String TAG = "CirclePhotoModel";
  private static CircleAlbumModel instance = null;
  public String gid;
  public KaixinModelTemplate<KaixinPhotoItem> mCirclePhotoList = new KaixinModelTemplate();

  public static CircleAlbumModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new CircleAlbumModel();
      CircleAlbumModel localCircleAlbumModel = instance;
      return localCircleAlbumModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void clear()
  {
    this.gid = null;
    this.mCirclePhotoList.clearItemList();
  }

  public int parseCircleAlbumJSON(JSONObject paramJSONObject, int paramInt, String paramString)
    throws SecurityErrorException
  {
    int i;
    if (paramJSONObject == null)
    {
      i = 0;
      return i;
    }
    while (true)
    {
      JSONArray localJSONArray;
      ArrayList localArrayList;
      int m;
      try
      {
        i = paramJSONObject.getInt("ret");
        if (i != 1)
          break;
        JSONObject localJSONObject1 = paramJSONObject.optJSONObject("result");
        int j = localJSONObject1.getInt("total");
        localJSONArray = localJSONObject1.getJSONArray("list");
        int k = localJSONArray.length();
        localArrayList = new ArrayList();
        m = 0;
        if (m >= k)
        {
          this.mCirclePhotoList.setItemList(j, localArrayList, paramInt);
          this.gid = paramString;
          return i;
        }
      }
      catch (Exception localException)
      {
        KXLog.d("CirclePhotoModel", "parseCircleAlbumJSON");
        return 0;
      }
      CircleAlbumItem localCircleAlbumItem = new CircleAlbumItem();
      JSONObject localJSONObject2 = localJSONArray.getJSONObject(m);
      localCircleAlbumItem.title = localJSONObject2.getString("title");
      localCircleAlbumItem.gid = localJSONObject2.getString("gid");
      localCircleAlbumItem.pid = localJSONObject2.getString("pid");
      localCircleAlbumItem.tid = localJSONObject2.getString("tid");
      localCircleAlbumItem.albumId = localJSONObject2.getString("aid");
      localCircleAlbumItem.albumType = 3;
      localCircleAlbumItem.ctime = localJSONObject2.getString("ctime");
      localCircleAlbumItem.thumbnail = localJSONObject2.getString("simgurl");
      localCircleAlbumItem.large = localJSONObject2.getString("fimgurl");
      JSONObject localJSONObject3 = localJSONObject2.optJSONObject("user_info");
      localCircleAlbumItem.belongedUserId = localJSONObject3.getString("uid");
      localCircleAlbumItem.belongedUserName = localJSONObject3.getString("name");
      localCircleAlbumItem.belongedUserGender = localJSONObject3.getInt("gender");
      localCircleAlbumItem.belongedUserIcon120 = localJSONObject3.getString("icon120");
      localCircleAlbumItem.belongedUserIcon50 = localJSONObject3.getString("icon50");
      localArrayList.add(localCircleAlbumItem);
      m++;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.CircleAlbumModel
 * JD-Core Version:    0.6.0
 */