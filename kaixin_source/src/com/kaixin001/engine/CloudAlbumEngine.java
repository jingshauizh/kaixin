package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.CloudAlbumPicItem;
import com.kaixin001.model.CloudAlbumModel;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProgressListener;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class CloudAlbumEngine extends KXEngine
{
  private static final String LOG = "CloudAlbumEngine";
  private static final CloudAlbumEngine instance = new CloudAlbumEngine();

  public static CloudAlbumEngine getInstance()
  {
    return instance;
  }

  private int parseCheckFileExists(Context paramContext, String paramString)
  {
    int i;
    int j;
    try
    {
      if (!TextUtils.isEmpty(paramString))
      {
        JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
        if (localJSONObject1 == null)
          return -1;
        if (this.ret != 1)
          break label156;
        JSONArray localJSONArray = localJSONObject1.optJSONArray("statuslist");
        if (localJSONArray == null)
          return -1;
        i = localJSONArray.length();
        CloudAlbumModel localCloudAlbumModel = CloudAlbumModel.getInstance();
        j = 0;
        break label149;
        JSONObject localJSONObject2 = localJSONArray.optJSONObject(j);
        if (localJSONObject2 == null)
          break label158;
        String str = localJSONObject2.optString("md5", "");
        boolean bool = localJSONObject2.optBoolean("sync", false);
        int k = CloudAlbumModel.getInstance().getStatus(str);
        if (bool)
        {
          localCloudAlbumModel.addPicStatus(str, 1);
          break label158;
        }
        if (k == 3)
          break label158;
        localCloudAlbumModel.addPicStatus(str, 0);
      }
    }
    catch (SecurityErrorException localSecurityErrorException)
    {
      localSecurityErrorException.printStackTrace();
    }
    return -1;
    label149: 
    while (j >= i)
    {
      label156: return 1;
      label158: j++;
    }
  }

  private int parseDeletePicResust(Context paramContext, String paramString)
  {
    try
    {
      if (!TextUtils.isEmpty(paramString))
      {
        if (super.parseJSON(paramContext, paramString) == null)
          return -1;
        int i = this.ret;
        if (i == 1)
          return 1;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }

  private int parseOpenCloudAlbum(Context paramContext, String paramString)
  {
    try
    {
      if (!TextUtils.isEmpty(paramString))
      {
        if (super.parseJSON(paramContext, paramString) == null)
          return -1;
        int i = this.ret;
        return i;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }

  private int parseUploadPic(Context paramContext, String paramString)
  {
    try
    {
      if (!TextUtils.isEmpty(paramString))
      {
        if (super.parseJSON(paramContext, paramString) == null)
          return -1;
        int i = this.ret;
        return i;
      }
    }
    catch (SecurityErrorException localSecurityErrorException)
    {
      localSecurityErrorException.printStackTrace();
    }
    return -1;
  }

  private int parstCheckStatus(Context paramContext, String paramString)
  {
    int i = 1;
    try
    {
      if (!TextUtils.isEmpty(paramString))
      {
        JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
        if (localJSONObject == null)
          return -1;
        if (this.ret == i)
        {
          boolean bool = localJSONObject.optBoolean("open", false);
          if (bool)
            break label58;
          return 0;
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      i = -1;
    }
    label58: return i;
  }

  private int parstCheckWhiteUser(Context paramContext, String paramString)
  {
    try
    {
      if (!TextUtils.isEmpty(paramString))
      {
        if (super.parseJSON(paramContext, paramString) == null)
          return -1;
        int i = this.ret;
        if (i == 1)
          return 1;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }

  private int parstSyncPicsList(Context paramContext, String paramString1, String paramString2)
  {
    int i;
    int j;
    int k;
    try
    {
      if (!TextUtils.isEmpty(paramString1))
      {
        JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString1);
        if (localJSONObject1 == null)
          return -1;
        if (this.ret == 1)
        {
          if (TextUtils.isEmpty(paramString2))
            CloudAlbumModel.getInstance().mTotal = localJSONObject1.optInt("total");
          JSONArray localJSONArray = localJSONObject1.optJSONArray("picList");
          if (localJSONArray == null)
            return -1;
          ArrayList localArrayList = CloudAlbumModel.getInstance().geSyncPicList();
          if (TextUtils.isEmpty(paramString2))
            localArrayList.clear();
          i = localJSONArray.length();
          j = 0;
          k = 0;
          break label246;
          JSONObject localJSONObject2 = localJSONArray.optJSONObject(k);
          if (localJSONObject2 != null)
          {
            CloudAlbumPicItem localCloudAlbumPicItem = new CloudAlbumPicItem();
            localCloudAlbumPicItem.mCpid = localJSONObject2.optString("cpid", "");
            localCloudAlbumPicItem.mMD5 = localJSONObject2.optString("md5", "");
            localCloudAlbumPicItem.mUrl = localJSONObject2.optString("url");
            localCloudAlbumPicItem.mThumbUrl = localJSONObject2.optString("thumbUrl");
            localCloudAlbumPicItem.mLargeUrl = localJSONObject2.optString("largeUrl");
            localCloudAlbumPicItem.mIsLocalAlbumPic = false;
            localCloudAlbumPicItem.mState = 1;
            if ((j == 0) && (CloudAlbumModel.getInstance().getItem(localCloudAlbumPicItem.mMD5) == null))
              j = 1;
            localArrayList.add(localCloudAlbumPicItem);
          }
          k++;
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    label246: 
    do
    {
      return -1;
      if (k < i)
        break;
    }
    while (j != 0);
    return 100;
  }

  public int checkFileExists(Context paramContext, String paramString)
  {
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str = Protocol.getInstance().makeCloudAlbumCheckFiles();
      HashMap localHashMap = new HashMap();
      localHashMap.put("multi_md5", paramString);
      localHashMap.put("uid", User.getInstance().getUID());
      localHashMap.put("api_version", "3.9.9");
      localHashMap.put("version", "android-3.9.9");
      int i = parseCheckFileExists(paramContext, localHttpProxy.httpPost(str, localHashMap, null, null));
      return i;
    }
    catch (Exception localException)
    {
      KXLog.e("CloudAlbumEngine", "checkFileExists error", localException);
    }
    return -1;
  }

  public int checkStatus(Context paramContext)
  {
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      int i = parstCheckStatus(paramContext, localHttpProxy.httpGet(Protocol.getInstance().makeCloudAlbumCheckStatus(), null, null));
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }

  public int checkWhiteUser(Context paramContext)
  {
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      int i = parstCheckWhiteUser(paramContext, localHttpProxy.httpGet(Protocol.getInstance().makeCloudAlbumCheckWhiteUser(), null, null));
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }

  public int deletePic(Context paramContext, String paramString1, String paramString2)
  {
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      int i = parseDeletePicResust(paramContext, localHttpProxy.httpGet(Protocol.getInstance().makeCloudAlbumDeletePic(paramString1, paramString2), null, null));
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }

  public int getSyncPicsList(Context paramContext, String paramString, int paramInt)
  {
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      if (TextUtils.isEmpty(paramString));
      String str;
      for (Object localObject = Protocol.getInstance().makeCloudAlbumGetList(0, paramInt); ; localObject = str)
      {
        return parstSyncPicsList(paramContext, localHttpProxy.httpGet((String)localObject, null, null), paramString);
        str = Protocol.getInstance().makeCloudAlbumGetListById(paramString, paramInt);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }

  public int openCloudAlbum(Context paramContext)
  {
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      int i = parseOpenCloudAlbum(paramContext, localHttpProxy.httpGet(Protocol.getInstance().makeCloudAlbumOpen(), null, null));
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }

  public int postFile(Context paramContext, File paramFile, String paramString, HttpProgressListener paramHttpProgressListener)
  {
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str1 = Protocol.getInstance().makeCloudAlbumUploadPic();
      HashMap localHashMap = new HashMap();
      localHashMap.put("uid", User.getInstance().getUID());
      localHashMap.put("api_version", "3.9.9");
      localHashMap.put("version", "android-3.9.9");
      if (paramFile != null)
        localHashMap.put("picstream", paramFile);
      if (!TextUtils.isEmpty(paramString))
        localHashMap.put("title", paramString);
      if (paramHttpProgressListener != null)
        paramHttpProgressListener.transferred(0L, 100L);
      String str2 = localHttpProxy.httpPost(str1, localHashMap, null, paramHttpProgressListener);
      KXLog.d("CloudAlbumEngine", "postFile() title:" + paramString + ", strContent:" + str2);
      int i = parseUploadPic(paramContext, str2);
      return i;
    }
    catch (Exception localException)
    {
      KXLog.e("CloudAlbumEngine", "postFile error", localException);
    }
    return -1;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.CloudAlbumEngine
 * JD-Core Version:    0.6.0
 */