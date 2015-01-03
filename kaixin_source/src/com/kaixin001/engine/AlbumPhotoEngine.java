package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.PhotoItem;
import com.kaixin001.model.AlbumPhotoModel;
import com.kaixin001.model.KaixinModelTemplate;
import com.kaixin001.model.KaixinUser;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class AlbumPhotoEngine extends KXEngine
{
  private static final String LOGTAG = "AlbumPhotoEngine";
  public static final int NUM = 24;
  private static AlbumPhotoEngine instance;

  public static AlbumPhotoEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new AlbumPhotoEngine();
      AlbumPhotoEngine localAlbumPhotoEngine = instance;
      return localAlbumPhotoEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private boolean isLogoAlbum(String paramString)
  {
    return (TextUtils.isEmpty(paramString)) || ("0".equals(paramString));
  }

  public int getAlbumPhotoList(Context paramContext, String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeAlbumPhotoListRequest(User.getInstance().getUID(), paramString1, paramString2, paramString3, paramInt1, paramInt2);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return 0;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("AlbumPhotoEngine", "getAlbumPhotoList error", localException);
        str2 = null;
      }
    }
    return parseAlbumPhotoJSON(paramContext, str2, paramString2, paramString1, paramInt1);
  }

  public int getLogoPhotoList(Context paramContext, String paramString, int paramInt1, int paramInt2)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeLogoPhotoListRequest(User.getInstance().getUID(), paramString, paramInt1, paramInt2);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return 0;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("AlbumPhotoEngine", "getLogoPhotoList error", localException);
        str2 = null;
      }
    }
    return parseAlbumPhotoJSON(paramContext, str2, paramString, "0", paramInt1);
  }

  public int parseAlbumPhotoJSON(Context paramContext, String paramString1, String paramString2, String paramString3, int paramInt)
    throws SecurityErrorException
  {
    JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString1);
    if (localJSONObject1 == null)
      return 0;
    if (this.ret == 1);
    while (true)
    {
      String str2;
      try
      {
        boolean bool = isLogoAlbum(paramString3);
        if (!bool)
          break label481;
        i = 1;
        JSONArray localJSONArray = localJSONObject1.getJSONArray("photos");
        ArrayList localArrayList = new ArrayList();
        int j = 0;
        if (j < localJSONArray.length())
          continue;
        AlbumPhotoModel localAlbumPhotoModel = AlbumPhotoModel.getInstance();
        if (!bool)
          continue;
        int k = localJSONObject1.optInt("total", 0);
        int m = Math.max(localArrayList.size(), k);
        String str1 = localJSONObject1.optString("fname");
        KaixinUser localKaixinUser1 = new KaixinUser();
        localKaixinUser1.uid = paramString2;
        localKaixinUser1.realname = str1;
        localAlbumPhotoModel.logoAlbumOwner = localKaixinUser1;
        if (localArrayList.size() != 0)
          continue;
        m = localAlbumPhotoModel.mListLogos.getItemList().size();
        localAlbumPhotoModel.mListLogos.setItemList(m, localArrayList, paramInt);
        return this.ret;
        PhotoItem localPhotoItem = new PhotoItem();
        JSONObject localJSONObject2 = (JSONObject)localJSONArray.get(j);
        localPhotoItem.pid = localJSONObject2.optString("pid");
        localPhotoItem.title = localJSONObject2.optString("title");
        localPhotoItem.privacy = localJSONObject2.optString("privacy");
        localPhotoItem.thumbnail = localJSONObject2.optString("thumbnail");
        localPhotoItem.large = localJSONObject2.optString("large");
        localPhotoItem.cnum = localJSONObject2.optInt("cnum", 0);
        localPhotoItem.index = String.valueOf(paramInt + 1);
        localPhotoItem.albumId = paramString3;
        localPhotoItem.albumType = i;
        localPhotoItem.fuid = paramString2;
        localArrayList.add(localPhotoItem);
        j++;
        continue;
        int n = localJSONObject1.optInt("total", 0);
        str2 = localJSONObject1.optString("fuid", null);
        String str3 = localJSONObject1.optString("fname", null);
        KaixinUser localKaixinUser2 = new KaixinUser();
        if (str2 == null)
        {
          localKaixinUser2.uid = paramString2;
          localKaixinUser2.realname = str3;
          localAlbumPhotoModel.albumOwner = localKaixinUser2;
          if (paramString3.equals(localAlbumPhotoModel.getAlbumId()))
            continue;
          localAlbumPhotoModel.mListPhotos.clearItemList();
          localAlbumPhotoModel.setAlbumId(paramString3);
          if (localArrayList.size() != 0)
            continue;
          n = localAlbumPhotoModel.mListPhotos.getItemList().size();
          localAlbumPhotoModel.mListPhotos.setItemList(n, localArrayList, paramInt);
          continue;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("AlbumPhotoEngine", "parseAlbumPhotoJSON", localException);
      }
      while (true)
      {
        return this.ret;
        paramString2 = str2;
        break;
        KXLog.e("AlbumPhotoEngine failed", paramString1);
      }
      label481: int i = 2;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.AlbumPhotoEngine
 * JD-Core Version:    0.6.0
 */