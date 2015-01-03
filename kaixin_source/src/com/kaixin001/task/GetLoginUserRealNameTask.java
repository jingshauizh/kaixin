package com.kaixin001.task;

import android.os.AsyncTask;
import android.text.TextUtils;
import com.kaixin001.activity.KXApplication;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.model.User;
import com.kaixin001.model.UserLevel;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetLoginUserRealNameTask extends AsyncTask<Void, Void, Integer>
{
  private static final String TAG = "GetLoginUserRealNameTask";

  public static Integer getMyNameAndLogo(String paramString1, String paramString2)
  {
    while (true)
    {
      try
      {
        User localUser = User.getInstance();
        String str1 = localUser.getUID();
        String str2 = Protocol.getInstance().makeUserInfoRequest(true, str1, str1, null);
        HttpProxy localHttpProxy = new HttpProxy(KXApplication.getInstance());
        String str3;
        try
        {
          str3 = localHttpProxy.httpGet(str2, null, null);
          if (TextUtils.isEmpty(str3))
            return Integer.valueOf(0);
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
          return null;
        }
        JSONObject localJSONObject1 = new JSONObject(str3);
        if (localJSONObject1.optInt("ret", 0) != 1)
          continue;
        JSONObject localJSONObject2 = localJSONObject1.getJSONArray("friends").getJSONObject(0);
        String str4 = localJSONObject2.getString("fname");
        String str5 = localJSONObject2.getString("fuid");
        String str6 = localJSONObject2.getString("flogo");
        String str7 = localJSONObject2.getString("flogo120");
        String str8 = localJSONObject2.getString("state");
        String str9 = localJSONObject2.getString("online");
        JSONObject localJSONObject3 = localJSONObject2.getJSONObject("cover");
        String str10 = localJSONObject3.getString("id");
        String str11 = localJSONObject3.getString("url");
        JSONObject localJSONObject4 = localJSONObject2.optJSONObject("upgrade_info");
        if (localJSONObject4 == null)
          continue;
        UserLevel localUserLevel = new UserLevel();
        localUser.setmUserLevel(localUserLevel);
        localUserLevel.setExp(localJSONObject4.optString("exp"));
        localUserLevel.setExp_award(localJSONObject4.optString("exp_award"));
        localUserLevel.setExp_to_upgrade(localJSONObject4.optString("exp_to_upgrade"));
        localUserLevel.setLevel(localJSONObject4.optString("level"));
        localUserLevel.setLogin_days(localJSONObject4.optString("login_days"));
        localUserLevel.setTitle(localJSONObject4.optString("title"));
        localUserLevel.setImage(localJSONObject4.getJSONObject("image").optString("large"));
        JSONObject localJSONObject5 = localJSONObject2.optJSONObject("dailyinfo");
        if (localJSONObject5 != null)
        {
          BaseFragment.getBaseFragment().setUpgradeInfoData(localJSONObject5);
          break label449;
          if ((!str5.equals(str1)) || (!bool1) || (!bool2))
            continue;
          localUser.setName(str4);
          localUser.setLogo(str6);
          localUser.setLogo120(str7);
          localUser.setState(str8);
          localUser.setCoverUrl(str11);
          localUser.setCoverId(str10);
          localUser.setOnline(str9);
          localUser.saveUserInfo(KXApplication.getInstance());
          return Integer.valueOf(1);
          bool1 = paramString1.equals(str6);
          break label456;
          bool2 = paramString2.equals(str7);
          continue;
          KXLog.d("GetLoginUserRealNameTask", str3);
          return Integer.valueOf(0);
        }
      }
      catch (Exception localException1)
      {
        KXLog.e("GetLoginUserRealNameTask", "makeUserInfoRequest", localException1);
        return null;
      }
      label449: if (paramString1 != null)
        continue;
      boolean bool1 = true;
      label456: if (paramString2 != null)
        continue;
      boolean bool2 = true;
    }
  }

  protected Integer doInBackground(Void[] paramArrayOfVoid)
  {
    return getMyNameAndLogo(null, null);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.task.GetLoginUserRealNameTask
 * JD-Core Version:    0.6.0
 */