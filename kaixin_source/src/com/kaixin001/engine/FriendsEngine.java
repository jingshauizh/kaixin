package com.kaixin001.engine;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Message;
import android.text.TextUtils;
import com.kaixin001.activity.KXApplication;
import com.kaixin001.activity.MessageHandlerHolder;
import com.kaixin001.model.FriendsModel;
import com.kaixin001.model.FriendsModel.Friend;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.PinYinUtils;
import java.io.File;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class FriendsEngine extends KXEngine
{
  public static final int BIRTH_TYPE_FRIENDS = 6;
  public static final int FOCUS_TYPE_FRIENDS = 7;
  public static final String FRIENDS_FILE = "friends.kx";
  public static final String FRIENDS_S_FILE = "friendss.kx";
  public static final String FRIENDS_V_FILE = "friendsv.kx";
  private static final String LOGTAG = "FriendsEngine";
  public static final int TYPE_FRIENDS = 8;
  public static final String UNI_MACHINE_ID = "uni_machine_id";
  public static final int UPDATE_TYPE_ALL = 0;
  public static final int UPDATE_TYPE_FRIENDS = 1;
  public static final int UPDATE_TYPE_ONLINE = 4;
  private static final int UPDATE_TYPE_OTHERS = 5;
  public static final int UPDATE_TYPE_STAR = 3;
  public static final int UPDATE_TYPE_VISITORS = 2;
  public static final int VNUM = 40;
  private static FriendsEngine instance;

  public static FriendsEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new FriendsEngine();
      FriendsEngine localFriendsEngine = instance;
      return localFriendsEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private long getUniMachineId()
  {
    return KXApplication.getInstance().getSharedPreferences("uni_machine_id", 0).getLong("uni_machine_id", 0L);
  }

  private ArrayList<FriendsModel.Friend> parseBirthJson(JSONArray paramJSONArray)
  {
    ArrayList localArrayList;
    if ((paramJSONArray == null) || (paramJSONArray.length() == 0))
      localArrayList = null;
    while (true)
    {
      return localArrayList;
      localArrayList = new ArrayList();
      int i = 0;
      int j = 0;
      try
      {
        while ((j < paramJSONArray.length()) && (j != 5000))
        {
          i++;
          JSONObject localJSONObject = paramJSONArray.optJSONObject(j);
          if (localJSONObject != null)
          {
            FriendsModel localFriendsModel = FriendsModel.getInstance();
            localFriendsModel.getClass();
            FriendsModel.Friend localFriend = new FriendsModel.Friend(localFriendsModel);
            localFriend.setFuid(localJSONObject.optString("fuid"));
            localFriend.setFname(localJSONObject.optString("fname"));
            localFriend.setGender(localJSONObject.optString("gender"));
            localFriend.setOnline(localJSONObject.optString("online"));
            localFriend.setFlogo(localJSONObject.optString("flogo"));
            localFriend.setIsFriend(localJSONObject.optString("isfriend"));
            localFriend.setVtime(localJSONObject.optString("vtime"));
            localFriend.setStrvtime(localJSONObject.optString("strvtime"));
            localFriend.setBirthDisplay(localJSONObject.optString("birthdisplay"));
            localFriend.setIsSendBirthGift(localJSONObject.optString("send"));
            String str = PinYinUtils.getInstance().getPinYinString(localFriend.getFname());
            if (str != null)
            {
              String[] arrayOfString = str.split("\\|");
              localFriend.setFpy(arrayOfString);
              localFriend.setPy(arrayOfString);
            }
            localArrayList.add(localFriend);
          }
          j++;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("FriendsEngine", "add friends error:" + localException);
        return localArrayList;
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        KXLog.e("FriendsEngine", "add friends memory error, count num :" + i);
      }
    }
    return localArrayList;
  }

  private boolean parseFriendsJSON(Context paramContext, boolean paramBoolean, String paramString, int paramInt)
    throws SecurityErrorException
  {
    int i = 1;
    FriendsModel localFriendsModel = FriendsModel.getInstance();
    JSONObject localJSONObject = super.parseJSON(paramContext, paramBoolean, paramString);
    if ((localJSONObject == null) || (this.ret != i))
      i = 0;
    while (true)
    {
      return i;
      try
      {
        String str = localJSONObject.optString("onlinetotal");
        if ((str != null) && (!str.equals(localFriendsModel.getOnlinetotal())))
          localFriendsModel.setOnlinetotal(str);
        if (paramInt != 3)
          break;
        FriendsModel.getInstance().setStotal(localJSONObject.getString("stotal"));
        break;
        ArrayList localArrayList7 = parseJson(localJSONObject.optJSONArray("friends"));
        FriendsModel.getInstance().setFriends(localArrayList7);
        FriendsModel.getInstance().sortBasePy(FriendsModel.getInstance().getFriends());
        if (paramBoolean)
        {
          KXLog.d("LoadFriendCache", "setCacheData");
          FileUtil.setCacheData(FileUtil.getKXCacheDir(paramContext), User.getInstance().getUID(), "friends.kx", paramString);
        }
        ArrayList localArrayList8 = parseJson(localJSONObject.optJSONArray("visitors"));
        FriendsModel.getInstance().setVisitors(localArrayList8);
        FileUtil.setCacheData(FileUtil.getKXCacheDir(paramContext), User.getInstance().getUID(), "friendsv.kx", paramString);
        Message localMessage = Message.obtain();
        localMessage.what = 90007;
        MessageHandlerHolder.getInstance().fireMessage(localMessage);
        ArrayList localArrayList9 = parseJson(localJSONObject.optJSONArray("starfriends"));
        FriendsModel.getInstance().setStarFriends(localArrayList9);
        FileUtil.setCacheData(FileUtil.getKXCacheDir(paramContext), User.getInstance().getUID(), "friendss.kx", paramString);
        FriendsModel.getInstance().setStotal(localJSONObject.optString("stotal"));
        ArrayList localArrayList10 = parseJson(localJSONObject.optJSONArray("onlines"));
        FriendsModel.getInstance().setOnlines(localArrayList10);
        ArrayList localArrayList11 = parseBirthJson(localJSONObject.optJSONArray("birthlist"));
        FriendsModel.getInstance().setBirthFriends(localArrayList11);
        ArrayList localArrayList12 = parseJson(localJSONObject.optJSONArray("focuslist"));
        FriendsModel.getInstance().setFocusFriends(localArrayList12);
        return i;
      }
      catch (Exception localException)
      {
        KXLog.e("FriendsEngine", "parseFriendsJSON", localException);
        return false;
      }
      ArrayList localArrayList6 = parseJson(localJSONObject.optJSONArray("friends"));
      FriendsModel.getInstance().setFriends(localArrayList6);
      FriendsModel.getInstance().sortBasePy(FriendsModel.getInstance().getFriends());
      if (!paramBoolean)
        continue;
      KXLog.d("LoadFriendCache", "setCacheData");
      FileUtil.setCacheData(FileUtil.getKXCacheDir(paramContext), User.getInstance().getUID(), "friends.kx", paramString);
      return i;
      ArrayList localArrayList5 = parseJson(localJSONObject.optJSONArray("visitors"));
      FriendsModel.getInstance().setVisitors(localArrayList5);
      FileUtil.setCacheData(FileUtil.getKXCacheDir(paramContext), User.getInstance().getUID(), "friendsv.kx", paramString);
      return i;
      ArrayList localArrayList4 = parseJson(localJSONObject.optJSONArray("starfriends"));
      FriendsModel.getInstance().setStarFriends(localArrayList4);
      FriendsModel.getInstance().sortBasePy(FriendsModel.getInstance().getStarFriends());
      FileUtil.setCacheData(FileUtil.getKXCacheDir(paramContext), User.getInstance().getUID(), "friendss.kx", paramString);
      FriendsModel.getInstance().setStotal(localJSONObject.optString("stotal"));
      return i;
      ArrayList localArrayList3 = parseJson(localJSONObject.optJSONArray("onlines"));
      FriendsModel.getInstance().setOnlines(localArrayList3);
      return i;
      ArrayList localArrayList2 = parseBirthJson(localJSONObject.optJSONArray("birthlist"));
      FriendsModel.getInstance().setBirthFriends(localArrayList2);
      return i;
      ArrayList localArrayList1 = parseJson(localJSONObject.optJSONArray("focuslist"));
      FriendsModel.getInstance().setFocusFriends(localArrayList1);
      return i;
    }
    switch (paramInt)
    {
    case 0:
    case 1:
    case 2:
    case 3:
    case 4:
    case 6:
    case 7:
    case 5:
    }
    return i;
  }

  private ArrayList<FriendsModel.Friend> parseJson(JSONArray paramJSONArray)
  {
    ArrayList localArrayList;
    if ((paramJSONArray == null) || (paramJSONArray.length() == 0))
      localArrayList = null;
    while (true)
    {
      return localArrayList;
      localArrayList = new ArrayList();
      int i = 0;
      int j = 0;
      try
      {
        while ((j < paramJSONArray.length()) && (j != 5000))
        {
          i++;
          JSONObject localJSONObject = paramJSONArray.optJSONObject(j);
          if (localJSONObject != null)
          {
            FriendsModel localFriendsModel = FriendsModel.getInstance();
            localFriendsModel.getClass();
            FriendsModel.Friend localFriend = new FriendsModel.Friend(localFriendsModel);
            localFriend.setFuid(localJSONObject.optString("fuid"));
            localFriend.setFname(localJSONObject.optString("fname"));
            localFriend.setGender(localJSONObject.optString("gender"));
            localFriend.setOnline(localJSONObject.optString("online"));
            localFriend.setFlogo(localJSONObject.optString("flogo"));
            localFriend.setIsFriend(localJSONObject.optString("isfriend"));
            localFriend.setVtime(localJSONObject.optString("vtime"));
            localFriend.setStrvtime(localJSONObject.optString("strvtime"));
            String str = PinYinUtils.getInstance().getPinYinString(localFriend.getFname());
            if (str != null)
            {
              String[] arrayOfString = str.split("\\|");
              localFriend.setFpy(arrayOfString);
              localFriend.setPy(arrayOfString);
            }
            localArrayList.add(localFriend);
          }
          j++;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("FriendsEngine", "add friends error:" + localException);
        return localArrayList;
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        KXLog.e("FriendsEngine", "add friends memory error, count num :" + i);
      }
    }
    return localArrayList;
  }

  private void setUniMachineId(long paramLong)
  {
    SharedPreferences.Editor localEditor = KXApplication.getInstance().getSharedPreferences("uni_machine_id", 0).edit();
    localEditor.putLong("uni_machine_id", paramLong);
    localEditor.commit();
  }

  public boolean getFriendsList(Context paramContext, int paramInt)
    throws SecurityErrorException
  {
    boolean bool1 = new File(FileUtil.getKXCacheDir(paramContext) + "/data/" + User.getInstance().getUID() + "/" + "friends.kx").exists();
    boolean bool2 = false;
    if (!bool1)
      bool2 = true;
    String str1 = Protocol.getInstance().makeFriendsRequest(User.getInstance().getUID(), 5000, 40, paramInt, getUniMachineId(), bool2);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    try
    {
      String str4 = localHttpProxy.httpGet(str1, null, null);
      str2 = str4;
      if (TextUtils.isEmpty(str2))
        return false;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("FriendsEngine", "getFriendsList error", localException);
        str2 = null;
      }
      if (str2.contains("\"cached\":1"))
        return true;
      if (getUniMachineId() == 0L)
      {
        int i = str2.indexOf("ctime\":");
        if (i != -1)
        {
          String str3 = str2.substring(i);
          setUniMachineId(Long.parseLong(str3.substring(1 + str3.indexOf(":"), str3.indexOf(","))));
        }
      }
    }
    return parseFriendsJSON(paramContext, true, str2, paramInt);
  }

  public boolean loadFriendsCache(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    if ((paramContext == null) || (TextUtils.isEmpty(paramString)));
    String str1;
    String str2;
    do
    {
      return false;
      str1 = FileUtil.getKXCacheDir(paramContext);
      str2 = FileUtil.getCacheData(str1, paramString, "friends.kx");
    }
    while (TextUtils.isEmpty(str2));
    parseFriendsJSON(paramContext, false, FileUtil.getCacheData(str1, paramString, "friendsv.kx"), 2);
    parseFriendsJSON(paramContext, false, FileUtil.getCacheData(str1, paramString, "friendss.kx"), 3);
    return parseFriendsJSON(paramContext, false, str2, 1);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.FriendsEngine
 * JD-Core Version:    0.6.0
 */