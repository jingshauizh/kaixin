package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.CheckInItem;
import com.kaixin001.item.CheckInPhotoItem;
import com.kaixin001.item.CheckInUser;
import com.kaixin001.item.PoiItem;
import com.kaixin001.item.PoiPhotoesItem;
import com.kaixin001.model.CheckInInfoModel;
import com.kaixin001.model.KaixinModelTemplate;
import com.kaixin001.model.KaixinUser;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckinListEngine extends KXEngine
{
  private static final String ITEM_CACHE_FILE = "checkin_item_";
  private static final String LOGTAG = "CheckinListEngine";
  public static final int NUM = 10;
  private static final String PHOTO_CACHE_FILE = "checkin_photoes";
  private static final String POIS = "pois";
  private static final String POI_CACHE_FILE = "checkin_pois";
  private static final String POI_DISTANCE = "distance";
  private static final String POI_ID = "poiid";
  private static final String POI_LOCATION = "location";
  private static final String POI_NAME = "name";
  private static final String POI_X = "x";
  private static final String POI_Y = "y";
  public static final int RESULT_FAILED = 0;
  public static final int RESULT_FAILED_NETWORK = -2;
  public static final int RESULT_FAILED_PARSE = -1;
  public static final int RESULT_OK = 1;
  private static CheckinListEngine instance = null;
  private String mLastError = "";

  private void addKaixinUsers(JSONArray paramJSONArray)
  {
    int i;
    if (paramJSONArray == null)
    {
      i = 0;
      if (i != 0)
        break label19;
    }
    label19: CheckInInfoModel localCheckInInfoModel;
    JSONObject localJSONObject1;
    while (true)
    {
      return;
      i = paramJSONArray.length();
      break;
      localCheckInInfoModel = CheckInInfoModel.getInstance();
      for (int j = 0; j < i; j++)
      {
        localJSONObject1 = paramJSONArray.optJSONObject(j);
        if (localJSONObject1 != null)
          break label51;
      }
    }
    label51: String str = localJSONObject1.optString("uid");
    CheckInUser localCheckInUser = localCheckInInfoModel.getKaixinUserbyID(str);
    if (localCheckInUser == null)
      localCheckInUser = new CheckInUser();
    KaixinUser localKaixinUser1 = new KaixinUser();
    localKaixinUser1.uid = str;
    localKaixinUser1.realname = localJSONObject1.optString("name");
    localKaixinUser1.gender = localJSONObject1.optInt("gender", -1);
    localKaixinUser1.logo = localJSONObject1.optString("logo");
    localKaixinUser1.online = localJSONObject1.optInt("online", -1);
    localCheckInUser.user = localKaixinUser1;
    JSONObject localJSONObject2 = localJSONObject1.optJSONObject("mutualFriend");
    if (localJSONObject2 == null);
    KaixinUser localKaixinUser2;
    for (localCheckInUser.mutualFriend = null; ; localCheckInUser.mutualFriend = localKaixinUser2)
    {
      localCheckInInfoModel.addKaixinUser(localCheckInUser);
      break;
      localKaixinUser2 = new KaixinUser();
      localKaixinUser2.uid = localJSONObject2.optString("uid");
      localKaixinUser2.realname = localJSONObject2.optString("name");
      localKaixinUser2.gender = localJSONObject2.optInt("gender", -1);
      localKaixinUser2.logo = localJSONObject2.optString("logo");
      localKaixinUser2.online = localJSONObject2.optInt("online", -1);
    }
  }

  private ArrayList<CheckInItem> getCheckinList(JSONArray paramJSONArray)
  {
    if (paramJSONArray == null);
    for (int i = 0; i == 0; i = paramJSONArray.length())
    {
      localArrayList = null;
      return localArrayList;
    }
    ArrayList localArrayList = new ArrayList(i);
    int j = 0;
    label34: JSONObject localJSONObject;
    if (j < i)
    {
      localJSONObject = paramJSONArray.optJSONObject(j);
      if (localJSONObject != null)
        break label59;
    }
    while (true)
    {
      j++;
      break label34;
      break;
      label59: CheckInItem localCheckInItem = valueOf(localJSONObject);
      if (localCheckInItem == null)
        continue;
      localArrayList.add(localCheckInItem);
    }
  }

  public static CheckinListEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new CheckinListEngine();
      CheckinListEngine localCheckinListEngine = instance;
      return localCheckinListEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private ArrayList<PoiPhotoesItem> getPoiList(JSONObject paramJSONObject)
  {
    ArrayList localArrayList = new ArrayList();
    JSONArray localJSONArray = paramJSONObject.optJSONArray("pois");
    if (localJSONArray == null);
    int j;
    for (int i = 0; ; i = localJSONArray.length())
    {
      j = 0;
      if (j < i)
        break;
      return localArrayList;
    }
    JSONObject localJSONObject = localJSONArray.optJSONObject(j);
    if (localJSONObject == null);
    while (true)
    {
      j++;
      break;
      PoiPhotoesItem localPoiPhotoesItem = new PoiPhotoesItem();
      PoiItem localPoiItem = new PoiItem();
      localPoiItem.poiId = localJSONObject.optString("poiid");
      localPoiItem.name = localJSONObject.optString("name");
      localPoiItem.location = localJSONObject.optString("location");
      localPoiItem.x = localJSONObject.optString("x");
      localPoiItem.y = localJSONObject.optString("y");
      localPoiItem.cuid = localJSONObject.optString("cuid");
      localPoiItem.ctime = localJSONObject.optString("ctime");
      localPoiItem.distance = localJSONObject.optString("distance");
      localPoiPhotoesItem.poi = localPoiItem;
      localArrayList.add(localPoiPhotoesItem);
    }
  }

  private void saveCacheFile(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    FileUtil.setCacheData(FileUtil.getKXCacheDir(paramContext), paramString2, paramString3, paramString1);
  }

  private void updatePhotoes(JSONObject paramJSONObject, PoiPhotoesItem paramPoiPhotoesItem, int paramInt)
  {
    int i = paramJSONObject.optInt("total", 0);
    JSONArray localJSONArray = paramJSONObject.optJSONArray("photos");
    int j = 0;
    if (localJSONArray == null);
    while (j == 0)
    {
      return;
      j = localJSONArray.length();
    }
    ArrayList localArrayList = new ArrayList(i);
    int k = 0;
    if (k >= j)
    {
      paramPoiPhotoesItem.photoList.setItemList(i, localArrayList, paramInt);
      return;
    }
    JSONObject localJSONObject1 = localJSONArray.optJSONObject(k);
    CheckInPhotoItem localCheckInPhotoItem = new CheckInPhotoItem();
    localCheckInPhotoItem.poiid = localJSONObject1.optString("poiid");
    localCheckInPhotoItem.chid = localJSONObject1.optString("chid");
    localCheckInPhotoItem.wid = localJSONObject1.optString("wid");
    localCheckInPhotoItem.pid = localJSONObject1.optString("pid");
    localCheckInPhotoItem.title = localJSONObject1.optString("title");
    localCheckInPhotoItem.thumbnail = localJSONObject1.optString("thumbnail");
    localCheckInPhotoItem.large = localJSONObject1.optString("large");
    localCheckInPhotoItem.ctime = localJSONObject1.optString("ctime");
    localCheckInPhotoItem.picnum = String.valueOf(i);
    localCheckInPhotoItem.albumId = paramPoiPhotoesItem.poi.poiId;
    localCheckInPhotoItem.albumType = 4;
    JSONObject localJSONObject2 = localJSONObject1.optJSONObject("owner_info");
    if (localJSONObject2 == null);
    KaixinUser localKaixinUser;
    for (localCheckInPhotoItem.owner = null; ; localCheckInPhotoItem.owner = localKaixinUser)
    {
      localArrayList.add(localCheckInPhotoItem);
      k++;
      break;
      localKaixinUser = new KaixinUser();
      localKaixinUser.uid = localJSONObject2.optString("uid");
      localKaixinUser.realname = localJSONObject2.optString("name");
      localKaixinUser.gender = localJSONObject2.optInt("gender", -1);
      localKaixinUser.online = localJSONObject2.optInt("online", -1);
      localKaixinUser.logo = localJSONObject2.optString("logo");
    }
  }

  public int doGetNearbyFriendCheckInList(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeNearbyFriendCheckinListbyPoi(paramString1, paramString2, paramString3, paramString4, User.getInstance().getUID(), paramInt1, paramInt2);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str4 = localHttpProxy.httpGet(str1, null, null);
      str2 = str4;
      if (paramInt1 == 0)
        CheckInInfoModel.getInstance().nearbyFriendCheckin.clearItemList();
      if (str2 == null)
        return 0;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("CheckinListEngine", "doGetNearbyFriendCheckInList error", localException);
        str2 = null;
      }
      KXLog.d("doGetNearbyFriendCheckInList", "strContent=" + str2);
      JSONObject localJSONObject = super.parseJSON(paramContext, str2);
      if (localJSONObject == null)
        return -1;
      if (this.ret == 1)
      {
        if (paramInt1 == 0)
        {
          String str3 = User.getInstance().getUID();
          saveCacheFile(paramContext, str2, str3, "checkin_item_0");
        }
        int i = localJSONObject.optInt("total", 0);
        CheckInInfoModel.getInstance().currentTimestamp = localJSONObject.optLong("ctime", System.currentTimeMillis() / 1000L);
        addKaixinUsers(localJSONObject.optJSONArray("users"));
        JSONArray localJSONArray = localJSONObject.optJSONArray("checks");
        CheckInInfoModel.getInstance().nearbyFriendCheckin.setItemList(i, getCheckinList(localJSONArray), paramInt1);
        return 1;
      }
    }
    return 0;
  }

  public int doGetOtherFriendCheckInList(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeOtherFriendCheckinListbyPoi(paramString1, paramString2, paramString3, paramString4, User.getInstance().getUID(), paramInt1, paramInt2);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str4 = localHttpProxy.httpGet(str1, null, null);
      str2 = str4;
      if (paramInt1 == 0)
        CheckInInfoModel.getInstance().otherFriendCheckin.clearItemList();
      if (str2 == null)
        return 0;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("CheckinListEngine", "doGetOtherFriendCheckInList error", localException);
        str2 = null;
      }
      KXLog.d("doGetFriendCheckInList", "strContent=" + str2);
      JSONObject localJSONObject = super.parseJSON(paramContext, str2);
      if (localJSONObject == null)
        return -1;
      if (this.ret == 1)
      {
        if (paramInt1 == 0)
        {
          String str3 = User.getInstance().getUID();
          saveCacheFile(paramContext, str2, str3, "checkin_item_1");
        }
        int i = localJSONObject.optInt("total", 0);
        addKaixinUsers(localJSONObject.optJSONArray("users"));
        JSONArray localJSONArray = localJSONObject.optJSONArray("checks");
        CheckInInfoModel.getInstance().otherFriendCheckin.setItemList(i, getCheckinList(localJSONArray), paramInt1);
        return 1;
      }
    }
    return 0;
  }

  public int doGetStrangeCheckInList(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeStrangeCheckinListbyPoi(paramString1, paramString2, paramString3, paramString4, User.getInstance().getUID(), paramInt1, paramInt2);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str4 = localHttpProxy.httpGet(str1, null, null);
      str2 = str4;
      if (paramInt1 == 0)
        CheckInInfoModel.getInstance().strangerCheckin.clearItemList();
      if (str2 == null)
        return 0;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("CheckinListEngine", "doGetOtherFriendCheckInList error", localException);
        str2 = null;
      }
      KXLog.d("doGetStrangeCheckInList", "strContent=" + str2);
      JSONObject localJSONObject = super.parseJSON(paramContext, str2);
      if (localJSONObject == null)
        return -1;
      if (this.ret == 1)
      {
        if (paramInt1 == 0)
        {
          String str3 = User.getInstance().getUID();
          saveCacheFile(paramContext, str2, str3, "checkin_item_2");
        }
        int i = localJSONObject.optInt("total", 0);
        addKaixinUsers(localJSONObject.optJSONArray("users"));
        JSONArray localJSONArray = localJSONObject.optJSONArray("checks");
        CheckInInfoModel.getInstance().strangerCheckin.setItemList(i, getCheckinList(localJSONArray), paramInt1);
        return 1;
      }
    }
    return 0;
  }

  public String getLastError()
  {
    return this.mLastError;
  }

  public Integer getPoiPhotoes(Context paramContext, String paramString, int paramInt1, int paramInt2)
  {
    String str1 = Protocol.getInstance().makePoiPhotoes(paramString, User.getInstance().getUID(), paramInt1, paramInt2);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (str2 == null)
        return Integer.valueOf(0);
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("CheckinListEngine", "getPoiPhotoes error", localException);
        str2 = null;
      }
      JSONObject localJSONObject;
      try
      {
        localJSONObject = super.parseJSON(paramContext, str2);
        if (localJSONObject == null)
          return Integer.valueOf(-1);
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        return Integer.valueOf(0);
      }
      if (this.ret == 1)
      {
        PoiPhotoesItem localPoiPhotoesItem = CheckInInfoModel.getInstance().getPoiPhotoesItemByPoiid(paramString);
        if (localPoiPhotoesItem == null)
          return Integer.valueOf(0);
        updatePhotoes(localJSONObject, localPoiPhotoesItem, paramInt1);
        return Integer.valueOf(1);
      }
    }
    return Integer.valueOf(0);
  }

  public Integer getPois(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt1, int paramInt2, KaixinModelTemplate<PoiPhotoesItem> paramKaixinModelTemplate)
  {
    String str1 = Protocol.getInstance().makeLbsGetPoisRequest(User.getInstance().getUID(), paramString2, paramString1, paramString3, paramString4, paramString5, paramString6, paramInt1, paramInt2);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str4 = localHttpProxy.httpGet(str1, null, null);
      str2 = str4;
      if (str2 == null)
        return Integer.valueOf(0);
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("CheckinListEngine", "getPois error", localException);
        str2 = null;
      }
      JSONObject localJSONObject;
      try
      {
        localJSONObject = super.parseJSON(paramContext, str2);
        if (localJSONObject == null)
          return Integer.valueOf(-1);
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        return Integer.valueOf(0);
      }
      if (this.ret == 1)
      {
        if (paramInt1 == 0)
        {
          String str3 = User.getInstance().getUID();
          saveCacheFile(paramContext, str2, str3, "checkin_pois");
        }
        paramKaixinModelTemplate.setItemList(localJSONObject.optInt("total", 0), getPoiList(localJSONObject), paramInt1);
        return Integer.valueOf(1);
      }
    }
    return Integer.valueOf(0);
  }

  public Integer getPoisPhotoes(Context paramContext, KaixinModelTemplate<PoiPhotoesItem> paramKaixinModelTemplate, int paramInt1, int paramInt2)
  {
    int i = paramKaixinModelTemplate.getItemList().size();
    if (i == 0)
      return Integer.valueOf(0);
    StringBuffer localStringBuffer = new StringBuffer();
    int j = 0;
    int k = 0;
    while (true)
    {
      String str1;
      String str2;
      HttpProxy localHttpProxy;
      if (k >= i)
      {
        str1 = localStringBuffer.toString();
        str2 = Protocol.getInstance().makePoisPhotoes(str1, User.getInstance().getUID(), paramInt2);
        localHttpProxy = new HttpProxy(paramContext);
      }
      try
      {
        String str6 = localHttpProxy.httpGet(str2, null, null);
        str3 = str6;
        if (str3 == null)
        {
          return Integer.valueOf(0);
          PoiPhotoesItem localPoiPhotoesItem1 = (PoiPhotoesItem)paramKaixinModelTemplate.getItemList().get(k);
          if (j != 0)
            localStringBuffer.append(",");
          localStringBuffer.append(localPoiPhotoesItem1.poi.poiId);
          j++;
          k++;
        }
      }
      catch (Exception localException)
      {
        String str3;
        while (true)
        {
          KXLog.e("CheckinListEngine", "getPoisPhotoes error", localException);
          str3 = null;
        }
        JSONObject localJSONObject1;
        try
        {
          localJSONObject1 = super.parseJSON(paramContext, str3);
          if (localJSONObject1 == null)
            return Integer.valueOf(-1);
        }
        catch (SecurityErrorException localSecurityErrorException)
        {
          return Integer.valueOf(0);
        }
        if (this.ret == 1)
        {
          if (paramInt1 == 0)
          {
            String str5 = User.getInstance().getUID();
            saveCacheFile(paramContext, str3, str5, "checkin_photoes");
          }
          JSONObject localJSONObject2 = localJSONObject1.optJSONObject("pois");
          String[] arrayOfString = str1.split(",");
          CheckInInfoModel.getInstance().poiPhotoesList.setItemList(paramKaixinModelTemplate.total, paramKaixinModelTemplate.getItemList(), paramInt1);
          int m = arrayOfString.length;
          int n = 0;
          if (n >= m)
            return Integer.valueOf(1);
          String str4 = arrayOfString[n];
          PoiPhotoesItem localPoiPhotoesItem2 = CheckInInfoModel.getInstance().getPoiPhotoesItemByPoiid(str4);
          if (localPoiPhotoesItem2 == null);
          while (true)
          {
            n++;
            break;
            JSONObject localJSONObject3 = localJSONObject2.optJSONObject(str4);
            if (localJSONObject3 == null)
              continue;
            updatePhotoes(localJSONObject3, localPoiPhotoesItem2, 0);
          }
        }
      }
    }
    return Integer.valueOf(0);
  }

  public ArrayList<CheckInItem> loadCheckInItemCache(Context paramContext, String paramString, int paramInt)
  {
    if ((paramContext == null) || (TextUtils.isEmpty(paramString)));
    String str;
    do
    {
      return null;
      str = FileUtil.getCacheData(FileUtil.getKXCacheDir(paramContext), paramString, "checkin_item_" + paramInt);
    }
    while (TextUtils.isEmpty(str));
    try
    {
      JSONObject localJSONObject = new JSONObject(str);
      addKaixinUsers(localJSONObject.optJSONArray("users"));
      ArrayList localArrayList = getCheckinList(localJSONObject.optJSONArray("checks"));
      return localArrayList;
    }
    catch (JSONException localJSONException)
    {
    }
    return null;
  }

  public ArrayList<PoiPhotoesItem> loadCheckInPhotoCache(Context paramContext, String paramString)
  {
    ArrayList localArrayList;
    if ((paramContext == null) || (TextUtils.isEmpty(paramString)))
      localArrayList = null;
    while (true)
    {
      return localArrayList;
      String str1 = FileUtil.getKXCacheDir(paramContext);
      String str2 = FileUtil.getCacheData(str1, paramString, "checkin_pois");
      if (TextUtils.isEmpty(str2))
        return null;
      try
      {
        JSONObject localJSONObject1 = new JSONObject(str2);
        localArrayList = getPoiList(localJSONObject1);
        int i = localJSONObject1.optInt("total", 0);
        CheckInInfoModel.getInstance().poiPhotoesList.setItemList(i, localArrayList, 0);
        JSONObject localJSONObject2 = new JSONObject(FileUtil.getCacheData(str1, paramString, "checkin_photoes")).optJSONObject("pois");
        Iterator localIterator = localArrayList.iterator();
        while (localIterator.hasNext())
        {
          PoiPhotoesItem localPoiPhotoesItem = (PoiPhotoesItem)localIterator.next();
          JSONObject localJSONObject3 = localJSONObject2.optJSONObject(localPoiPhotoesItem.poi.poiId);
          if (localJSONObject3 == null)
            continue;
          updatePhotoes(localJSONObject3, localPoiPhotoesItem, 0);
        }
      }
      catch (JSONException localJSONException)
      {
      }
    }
    return null;
  }

  public CheckInItem valueOf(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null)
      return null;
    String str = paramJSONObject.optString("uid");
    if (str == null)
      return null;
    CheckInUser localCheckInUser = CheckInInfoModel.getInstance().getKaixinUserbyID(str);
    if (localCheckInUser == null)
      return null;
    CheckInItem localCheckInItem = new CheckInItem();
    localCheckInItem.checkInUser = localCheckInUser;
    localCheckInItem.ctime = paramJSONObject.optLong("ctime", 0L);
    localCheckInItem.content = paramJSONObject.optString("content", null);
    localCheckInItem.distance = paramJSONObject.optString("distance", null);
    localCheckInItem.poiId = paramJSONObject.optString("poiid", null);
    localCheckInItem.poiName = paramJSONObject.optString("poiname", null);
    localCheckInItem.wid = paramJSONObject.optString("wid", null);
    localCheckInItem.thumbnail = paramJSONObject.optString("thumbnail", null);
    localCheckInItem.large = paramJSONObject.optString("large", null);
    localCheckInItem.mSource = paramJSONObject.optString("source", null);
    localCheckInItem.mMapUrl = paramJSONObject.optString("mapurl", null);
    return localCheckInItem;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.CheckinListEngine
 * JD-Core Version:    0.6.0
 */