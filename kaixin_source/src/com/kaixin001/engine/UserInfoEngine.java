package com.kaixin001.engine;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.kaixin001.item.UserRankItem;
import com.kaixin001.item.UserRankItem.BackType;
import com.kaixin001.item.UserRankItem.UserRankModel;
import com.kaixin001.model.User;
import com.kaixin001.model.UserInfoModel;
import com.kaixin001.model.UserLevel;
import com.kaixin001.model.UserMissionModel;
import com.kaixin001.model.UserMissionModel.LevelActivityModel;
import com.kaixin001.model.UserMissionModel.MissionItem;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class UserInfoEngine extends KXEngine
{
  public static final int EDIT_BIRTHDAY = 1;
  public static final int EDIT_CITY = 2;
  public static final int EDIT_DISABLE = 0;
  public static final int EDIT_PHONE = 3;
  public static final int EDIT_SEX = 4;
  private static final String LOGTAG = "UserInfoEngine";
  private static UserInfoEngine instance;

  public static UserInfoEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new UserInfoEngine();
      UserInfoEngine localUserInfoEngine = instance;
      return localUserInfoEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void parseJsonDailyTask(Context paramContext, String paramString1, String paramString2)
    throws Exception
  {
    JSONObject localJSONObject1 = parseJSON(paramContext, paramString1).getJSONObject("data");
    if (UserMissionModel.getInstance().getMissionList(paramString2) != null)
      UserMissionModel.getInstance().getMissionList(paramString2).clear();
    ArrayList localArrayList = new ArrayList();
    JSONArray localJSONArray1 = localJSONObject1.optJSONArray("daily_task");
    int j;
    JSONArray localJSONArray2;
    if ((localJSONArray1 != null) && (localJSONArray1.length() > 0))
    {
      j = 0;
      if (j < localJSONArray1.length());
    }
    else
    {
      localJSONArray2 = localJSONObject1.optJSONArray("upgrade_desc");
      if ((localJSONArray2 == null) || (localJSONArray2.length() <= 0));
    }
    for (int i = 0; ; i++)
    {
      if (i >= localJSONArray2.length())
      {
        UserMissionModel.getInstance().addMissionList(paramString2, localArrayList);
        return;
        JSONObject localJSONObject4 = (JSONObject)localJSONArray1.get(j);
        UserMissionModel localUserMissionModel2 = UserMissionModel.getInstance();
        localUserMissionModel2.getClass();
        UserMissionModel.MissionItem localMissionItem2 = new UserMissionModel.MissionItem(localUserMissionModel2);
        localMissionItem2.type = 1001;
        localMissionItem2.days = localJSONObject4.optString("days", "");
        localMissionItem2.exp = localJSONObject4.optString("exp", "");
        localMissionItem2.limitValue = localJSONObject4.optString("limitValue", "");
        localMissionItem2.done = localJSONObject4.optString("done", "");
        if (j == 0)
          localMissionItem2.typeTitle = "每日任务";
        if (j == -1 + localJSONArray1.length())
          localMissionItem2.isEnd = true;
        localArrayList.add(localMissionItem2);
        j++;
        break;
      }
      JSONObject localJSONObject2 = (JSONObject)localJSONArray2.get(i);
      UserMissionModel localUserMissionModel1 = UserMissionModel.getInstance();
      localUserMissionModel1.getClass();
      UserMissionModel.MissionItem localMissionItem1 = new UserMissionModel.MissionItem(localUserMissionModel1);
      localMissionItem1.type = 2001;
      localMissionItem1.title = localJSONObject2.optString("title", "");
      localMissionItem1.level = localJSONObject2.optString("level", "");
      JSONObject localJSONObject3 = localJSONObject2.optJSONObject("image");
      localMissionItem1.small = localJSONObject3.optString("small", "");
      localMissionItem1.middle = localJSONObject3.optString("middle", "");
      localMissionItem1.large = localJSONObject3.optString("large", "");
      if (i == 0)
        localMissionItem1.typeTitle = "升职说明";
      if (i == -1 + localJSONArray2.length())
        localMissionItem1.isEnd = true;
      localArrayList.add(localMissionItem1);
    }
  }

  private void parseJsonLevelActivity(Context paramContext, String paramString1, String paramString2)
    throws Exception
  {
    JSONArray localJSONArray = parseJSON(paramContext, paramString1).optJSONArray("data");
    if (UserMissionModel.getInstance().getActivityList(paramString2) != null)
      UserMissionModel.getInstance().getActivityList(paramString2).clear();
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; ; i++)
    {
      if (i >= localJSONArray.length())
      {
        UserMissionModel.getInstance().addLevelActivity(paramString2, localArrayList);
        return;
      }
      JSONObject localJSONObject = localJSONArray.getJSONObject(i);
      UserMissionModel localUserMissionModel = UserMissionModel.getInstance();
      localUserMissionModel.getClass();
      UserMissionModel.LevelActivityModel localLevelActivityModel = new UserMissionModel.LevelActivityModel(localUserMissionModel);
      localLevelActivityModel.activityId = localJSONObject.optString("id", "");
      localLevelActivityModel.title = localJSONObject.optString("name", "");
      localLevelActivityModel.content = localJSONObject.optString("content", "");
      localLevelActivityModel.startTime = localJSONObject.optString("starttime", "");
      localLevelActivityModel.endTime = localJSONObject.optString("endtime", "");
      localArrayList.add(localLevelActivityModel);
    }
  }

  private void parseJsonRanking(Context paramContext, String paramString1, String paramString2)
    throws Exception
  {
    JSONArray localJSONArray = parseJSON(paramContext, paramString1).optJSONArray("data");
    ArrayList localArrayList;
    if (localJSONArray != null)
    {
      if (UserRankItem.getInstance().getRankList(paramString2) != null)
        UserRankItem.getInstance().getRankList(paramString2).clear();
      localArrayList = new ArrayList();
    }
    int j;
    for (int i = 0; ; i++)
    {
      if (i >= localJSONArray.length())
      {
        j = 0;
        if (j < localArrayList.size())
          break;
        UserRankItem.getInstance().addRankList(paramString2, localArrayList);
        return;
      }
      JSONObject localJSONObject1 = localJSONArray.getJSONObject(i);
      UserRankItem localUserRankItem = UserRankItem.getInstance();
      localUserRankItem.getClass();
      UserRankItem.UserRankModel localUserRankModel1 = new UserRankItem.UserRankModel(localUserRankItem);
      localUserRankModel1.rank = localJSONObject1.optInt("rank", 0);
      JSONObject localJSONObject2 = localJSONObject1.getJSONObject("user");
      localUserRankModel1.uid = localJSONObject2.optString("uid", "");
      localUserRankModel1.name = localJSONObject2.optString("name", "");
      localUserRankModel1.birthday = localJSONObject2.optString("birthday", "");
      localUserRankModel1.logo = localJSONObject2.optString("logo", "");
      localUserRankModel1.logo50 = localJSONObject2.optString("logo50", "");
      localUserRankModel1.logo90 = localJSONObject2.optString("logo90", "");
      localUserRankModel1.gender = localJSONObject2.optString("gender", "");
      localUserRankModel1.online = localJSONObject2.optString("online", "");
      localUserRankModel1.state = localJSONObject2.optString("state", "");
      localUserRankModel1.city = localJSONObject2.optString("city", "");
      localUserRankModel1.star = localJSONObject2.optString("star", "");
      JSONObject localJSONObject3 = localJSONObject1.getJSONObject("upgrade_info");
      localUserRankModel1.exp = localJSONObject3.optString("exp", "");
      localUserRankModel1.level = localJSONObject3.optString("level", "");
      localUserRankModel1.title = localJSONObject3.optString("title", "");
      JSONObject localJSONObject4 = localJSONObject3.getJSONObject("image");
      localUserRankModel1.small = localJSONObject4.optString("small", "");
      localUserRankModel1.middle = localJSONObject4.optString("middle", "");
      localUserRankModel1.large = localJSONObject4.optString("large", "");
      if (localUserRankModel1.uid.equals(User.getInstance().getUID()))
        UserRankItem.getInstance().setUserRank(localUserRankModel1.rank);
      localArrayList.add(localUserRankModel1);
    }
    UserRankItem.UserRankModel localUserRankModel2 = (UserRankItem.UserRankModel)localArrayList.get(j);
    if (UserRankItem.getInstance().getUserRank() >= 32)
      if (localUserRankModel2.rank == 20)
        localUserRankModel2.backType = UserRankItem.BackType.BOTTOM_ROUND;
    while (true)
    {
      j++;
      break;
      if (localUserRankModel2.rank == -10 + UserRankItem.getInstance().getUserRank())
      {
        localUserRankModel2.backType = UserRankItem.BackType.OMIT_ROUND;
        continue;
      }
      if (j == -1 + localArrayList.size())
      {
        localUserRankModel2.backType = UserRankItem.BackType.BOTTOM_ROUND;
        continue;
      }
      localUserRankModel2.backType = UserRankItem.BackType.MIDDLE_LINE;
    }
  }

  public void getDailyTask(Context paramContext, String paramString)
    throws Exception
  {
    String str1 = Protocol.getInstance().getDailyTask(paramString);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("UserInfoEngine", "get ranking error", localException);
        str2 = null;
      }
      parseJsonDailyTask(paramContext, str2, paramString);
    }
  }

  public void getRankingList(Context paramContext, String paramString)
    throws Exception
  {
    String str1 = Protocol.getInstance().getUserRanking(paramString);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("UserInfoEngine", "get ranking error", localException);
        str2 = null;
      }
      parseJsonRanking(paramContext, str2, paramString);
    }
  }

  public void getUserActivity(Context paramContext, String paramString)
    throws Exception
  {
    String str1 = Protocol.getInstance().getUserLevelActivity(paramString);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("UserInfoEngine", "get user level activity error", localException);
        str2 = null;
      }
      parseJsonLevelActivity(paramContext, str2, paramString);
    }
  }

  public boolean getUserInfo(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeUserInfoRequest(true, User.getInstance().getUID(), paramString, null);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return false;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("UserInfoEngine", "getUserInfo error", localException);
        str2 = null;
      }
    }
    return parseUserInfoJSON(paramContext, UserInfoModel.getInstance(), true, str2);
  }

  public void getUserInfoList(Context paramContext, JSONArray paramJSONArray, UserInfoModel paramUserInfoModel)
  {
    ArrayList localArrayList = new ArrayList();
    Object localObject = null;
    if (paramJSONArray != null)
    {
      int i6 = paramJSONArray.length();
      localObject = null;
      if (i6 <= 0);
    }
    try
    {
      JSONObject localJSONObject13 = paramJSONArray.getJSONObject(0);
      localObject = localJSONObject13;
      if (localObject == null);
    }
    catch (Exception localException2)
    {
      while (true)
      {
        int i;
        int j;
        JSONArray localJSONArray1;
        StringBuffer localStringBuffer1;
        int m;
        int n;
        JSONArray localJSONArray2;
        StringBuffer localStringBuffer2;
        int i2;
        int i3;
        int i4;
        try
        {
          paramUserInfoModel.setOnline(localObject.getString("online"));
          paramUserInfoModel.setStateTime(localObject.getString("statetime"));
          paramUserInfoModel.setState(localObject.getString("state"));
          String str1 = localObject.optString("gender", "0");
          JSONObject localJSONObject1 = new JSONObject();
          localJSONObject1.put("tag", paramContext.getResources().getString(2131427518));
          if (str1.compareTo("1") != 0)
            continue;
          String str2 = paramContext.getResources().getString(2131427533);
          localJSONObject1.put("value", str2);
          localArrayList.add(localJSONObject1);
          String str3 = localObject.optString("birthday", "");
          JSONObject localJSONObject2 = new JSONObject();
          localJSONObject2.put("tag", paramContext.getResources().getString(2131427519));
          localJSONObject2.put("value", str3);
          localJSONObject2.put("edit", 1);
          localArrayList.add(localJSONObject2);
          paramUserInfoModel.setBirthday(str3);
          String str4 = localObject.optString("astro", "");
          JSONObject localJSONObject3 = new JSONObject();
          localJSONObject3.put("tag", paramContext.getResources().getString(2131427520));
          localJSONObject3.put("value", str4);
          localArrayList.add(localJSONObject3);
          String str5 = localObject.optString("hometown", " ");
          JSONObject localJSONObject4 = new JSONObject();
          localJSONObject4.put("tag", paramContext.getResources().getString(2131427521));
          localJSONObject4.put("value", str5);
          localArrayList.add(localJSONObject4);
          paramUserInfoModel.setHometown(str5);
          String str6 = localObject.optString("city", " ");
          JSONObject localJSONObject5 = new JSONObject();
          localJSONObject5.put("tag", paramContext.getResources().getString(2131427522));
          localJSONObject5.put("value", str6);
          localJSONObject5.put("edit", 2);
          localArrayList.add(localJSONObject5);
          paramUserInfoModel.setCity(str6);
          String str7 = localObject.optString("mobile", "");
          JSONObject localJSONObject6 = new JSONObject();
          localJSONObject6.put("tag", paramContext.getResources().getString(2131427523));
          localJSONObject6.put("value", str7);
          localJSONObject6.put("edit", 3);
          localArrayList.add(localJSONObject6);
          paramUserInfoModel.setPhoneNum(str7);
          i = 0;
          if (i < localArrayList.size())
            continue;
          j = localArrayList.size();
          String str8 = localObject.getString("interest");
          if (TextUtils.isEmpty(str8))
            continue;
          JSONObject localJSONObject8 = new JSONObject();
          localJSONObject8.put("tag", paramContext.getResources().getString(2131427529));
          localJSONObject8.put("value", str8);
          localArrayList.add(localJSONObject8);
          localJSONArray1 = localObject.getJSONArray("education");
          if (localJSONArray1 != null)
            continue;
          int k = localJSONArray1.length();
          if (k != 0)
            continue;
          localStringBuffer1 = new StringBuffer();
          m = 0;
          n = 0;
          if (n >= k)
          {
            String str9 = localStringBuffer1.toString();
            JSONObject localJSONObject9 = new JSONObject();
            localJSONObject9.put("tag", paramContext.getResources().getString(2131427530));
            localJSONObject9.put("value", str9);
            localArrayList.add(localJSONObject9);
            localJSONArray2 = localObject.getJSONArray("career");
            if (localJSONArray2 != null)
              continue;
            int i1 = localJSONArray2.length();
            if (k != 0)
              continue;
            localStringBuffer2 = new StringBuffer();
            i2 = 0;
            i3 = 0;
            if (i3 < i1)
              break label1115;
            String str10 = localStringBuffer2.toString();
            JSONObject localJSONObject10 = new JSONObject();
            localJSONObject10.put("tag", paramContext.getResources().getString(2131427531));
            localJSONObject10.put("value", str10);
            JSONObject localJSONObject11 = localObject.getJSONObject("upgrade_info");
            UserLevel localUserLevel = new UserLevel();
            paramUserInfoModel.setmUserLevel(localUserLevel);
            localUserLevel.setExp(localJSONObject11.optString("exp"));
            localUserLevel.setExp_award(localJSONObject11.optString("exp_award"));
            localUserLevel.setExp_to_upgrade(localJSONObject11.optString("exp_to_upgrade"));
            localUserLevel.setLevel(localJSONObject11.optString("level"));
            localUserLevel.setLogin_days(localJSONObject11.optString("login_days"));
            localUserLevel.setTitle(localJSONObject11.optString("title"));
            localUserLevel.setImage(localJSONObject11.getJSONObject("image").optString("small"));
            localArrayList.add(localJSONObject10);
            i4 = j;
            int i5 = localArrayList.size();
            if (i4 < i5)
              break label1166;
            paramUserInfoModel.setUserInfoList(localArrayList);
            return;
            localException2 = localException2;
            KXLog.e("UserInfoModel", "getFirstItem", localException2);
            localObject = null;
            continue;
            str2 = paramContext.getResources().getString(2131427532);
            continue;
            localJSONObject7 = (JSONObject)localArrayList.get(i);
            if (i != 0)
              continue;
            localJSONObject7.put("pos", 1);
            break label1236;
            if (i != -1 + localArrayList.size())
              continue;
            localJSONObject7.put("pos", 3);
          }
        }
        catch (Exception localException1)
        {
          JSONObject localJSONObject7;
          KXLog.e("UserInfoModel", "getUserInfoList", localException1);
          continue;
          localJSONObject7.put("pos", 2);
        }
        String str12 = localJSONArray1.getJSONObject(n).getString("school");
        if (!TextUtils.isEmpty(str12))
        {
          if (m > 0)
            localStringBuffer1.append("\n");
          localStringBuffer1.append(str12);
          m++;
          break label1242;
          label1115: String str11 = localJSONArray2.getJSONObject(i3).getString("company");
          if (TextUtils.isEmpty(str11))
            break label1248;
          if (i2 > 0)
            localStringBuffer2.append("\n");
          localStringBuffer2.append(str11);
          i2++;
          break label1248;
          label1166: JSONObject localJSONObject12 = (JSONObject)localArrayList.get(i4);
          if (i4 == j)
          {
            localJSONObject12.put("pos", 1);
            break label1254;
          }
          if (i4 == -1 + localArrayList.size())
          {
            localJSONObject12.put("pos", 3);
            break label1254;
          }
          localJSONObject12.put("pos", 2);
          break label1254;
          label1236: i++;
          continue;
        }
        label1242: n++;
        continue;
        label1248: i3++;
        continue;
        label1254: i4++;
      }
    }
  }

  public boolean parseUserInfoJSON(Context paramContext, UserInfoModel paramUserInfoModel, boolean paramBoolean, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramBoolean, paramString);
    if (localJSONObject == null)
      return false;
    if (this.ret == 1)
      try
      {
        JSONArray localJSONArray = localJSONObject.getJSONArray("friends");
        getUserInfoList(paramContext, localJSONArray, paramUserInfoModel);
        paramUserInfoModel.setFriendList(localJSONArray);
        return true;
      }
      catch (Exception localException)
      {
        KXLog.e("UserInfoEngine", "parseUserInfoJSON", localException);
        return false;
      }
    KXLog.d("UserInfoEngine failed", paramString);
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.UserInfoEngine
 * JD-Core Version:    0.6.0
 */