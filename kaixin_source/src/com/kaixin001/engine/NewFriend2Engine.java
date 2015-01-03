package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import org.json.JSONObject;

public class NewFriend2Engine extends KXEngine
{
  public static int CAPTCHA = 0;
  public static int CAPTCHA2 = 0;
  public static final int FADD_TIP_ACTIVE_MAX_FCOUNT = 305;
  public static final int FADD_TIP_AFTER_REQ = 304;
  public static final int FADD_TIP_ALREAY_FRIEND = 302;
  public static final int FADD_TIP_ATTACH_WORD = 306;
  public static final int FADD_TIP_BE_BLACKED = 319;
  public static final int FADD_TIP_BE_REPORT = 319;
  public static final int FADD_TIP_FFRIEND = 307;
  public static final int FADD_TIP_FGROUP_VERIFY_ERROR = 318;
  public static final int FADD_TIP_IDENTIFY = 314;
  public static final int FADD_TIP_MAX_ADD_SAME_LIMIT = 319;
  public static final int FADD_TIP_MAX_DAY_ADD = 315;
  public static final int FADD_TIP_NORMAL = 303;
  public static final int FADD_TIP_ORG_FANS = 310;
  public static final int FADD_TIP_ORG_PASS = 312;
  public static final int FADD_TIP_ORG_VERIFY = 313;
  public static final int FADD_TIP_PASSIVE_MAX_FCOUNT = 308;
  public static final int FADD_TIP_STAR = 309;
  public static final int FADD_TIP_STAR_PASS = 311;
  public static final int FADD_TIP_UCD_VERIFY_ERROR = 317;
  public static final int FADD_TIP_VERIFY_ERROR = 316;
  private static final String LOGTAG = "NewFriend2Engine";
  private static NewFriend2Engine instance;
  public int captcha;
  public String captcha_url;
  public int code;
  public String rcode;
  public String strTipMsg;

  public static NewFriend2Engine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new NewFriend2Engine();
      NewFriend2Engine localNewFriend2Engine = instance;
      return localNewFriend2Engine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public int addNewFriend(Context paramContext, String paramString1, String paramString2)
    throws SecurityErrorException
  {
    this.strTipMsg = null;
    String str1 = Protocol.getInstance().makeAddFriendRequest(User.getInstance().getUID(), paramString1);
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
        KXLog.e("NewFriend2Engine", "addNewFriend error", localException);
        str2 = null;
      }
    }
    return parseNewFriendJSON(paramContext, str2);
  }

  public int addNewFriend(Context paramContext, String paramString1, String paramString2, String paramString3)
    throws SecurityErrorException
  {
    this.strTipMsg = null;
    String str1 = Protocol.getInstance().makeAddFriendRequest(User.getInstance().getUID(), paramString1, paramString2, paramString3);
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
        KXLog.e("NewFriend2Engine", "addNewFriend error", localException);
        str2 = null;
      }
    }
    return parseNewFriendJSON(paramContext, str2);
  }

  public int parseNewFriendJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
    if (localJSONObject == null)
      return 0;
    if (Setting.getInstance().isDebug())
      KXLog.d("parseNewFriendJSON", "strContent=" + localJSONObject.toString());
    try
    {
      this.strTipMsg = localJSONObject.getString("msg");
      if (this.ret == 0)
      {
        this.captcha = localJSONObject.optInt("captcha", 0);
        this.rcode = localJSONObject.optString("rcode", null);
        this.captcha_url = localJSONObject.optString("captcha_url", null);
        this.code = localJSONObject.optInt("code", -1);
      }
      return this.ret;
    }
    catch (Exception localException)
    {
      while (true)
        KXLog.e("NewFriendEngine", "parseNewFriendJSON", localException);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.NewFriend2Engine
 * JD-Core Version:    0.6.0
 */