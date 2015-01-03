package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.PhoneRegisterModel;
import com.kaixin001.model.PhoneRegisterModel.RegisterInfo;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.net.URI;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONObject;

public class PhoneRegisterEngine extends KXEngine
{
  private static final String TAG = "PhoneRegisterEngine";
  private static PhoneRegisterEngine instance;
  private PhoneRegisterModel model = PhoneRegisterModel.getInstance();

  public static PhoneRegisterEngine getInstance()
  {
    if (instance == null)
      instance = new PhoneRegisterEngine();
    return instance;
  }

  private String getStrContent(Context paramContext, String paramString)
  {
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      HttpGet localHttpGet = new HttpGet();
      localHttpGet.setHeader("Connection", "Keep-Alive");
      localHttpGet.setURI(new URI(Setting.getInstance().getNewHost() + paramString));
      String str = localHttpProxy.httpGet(localHttpGet);
      return str;
    }
    catch (Exception localException)
    {
      KXLog.e("PhoneRegisterEngine", "phone register get data error", localException);
    }
    return null;
  }

  private void parsePhoneRegisterJson(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
    this.model.setRet(localJSONObject.optString("ret", ""));
    this.model.setCode(localJSONObject.optString("code", ""));
    this.model.setError(localJSONObject.optString("error", ""));
    this.model.setUid(localJSONObject.optString("uid"));
    User.getInstance().setUID(localJSONObject.optString("uid"));
    if (localJSONObject.optJSONObject("userinfo") != null)
      parseRegisterInfoJson(paramContext, localJSONObject.optJSONObject("userinfo"));
  }

  private void parseRegisterInfoJson(Context paramContext, JSONObject paramJSONObject)
  {
    this.model.getInfo().setAccount(paramJSONObject.optString("account", ""));
    this.model.getInfo().setNick(paramJSONObject.optString("nick", ""));
    this.model.getInfo().setGender(paramJSONObject.optString("gender", ""));
    this.model.getInfo().setPassword(paramJSONObject.optString("password", ""));
    this.model.getInfo().setRegfrom(paramJSONObject.optString("regfrom", ""));
    this.model.getInfo().setSource(paramJSONObject.optString("source", ""));
    this.model.getInfo().setIp(paramJSONObject.optString("ip", ""));
    this.model.getInfo().setSrc(paramJSONObject.optString("src", ""));
    this.model.getInfo().setRefer(paramJSONObject.optString("refer", ""));
    this.model.getInfo().setUseragent(paramJSONObject.optString("useragent", ""));
    this.model.getInfo().setRegflag(paramJSONObject.optString("regflag", ""));
    this.model.getInfo().setRegposition(paramJSONObject.optString("regposition", ""));
    this.model.getInfo().setCode(paramJSONObject.optString("code", ""));
    this.model.getInfo().setRegpage(paramJSONObject.optString("regpage", ""));
    this.model.getInfo().setFromuid(paramJSONObject.optString("fromuid", ""));
    this.model.getInfo().setOutside(paramJSONObject.optString("outside", ""));
    this.model.getInfo().setLandpage(paramJSONObject.optString("landpage", ""));
    this.model.getInfo().setUid(paramJSONObject.optString("uid", ""));
  }

  public void clearModelData()
  {
    this.model.clear();
  }

  public void commitRegister(Context paramContext, String paramString1, String paramString2, String paramString3)
    throws SecurityErrorException
  {
    this.model.clear();
    String str = getStrContent(paramContext, Protocol.getInstance().getPhoneRegisterUrl("2", paramString3, paramString1, paramString2));
    if (TextUtils.isEmpty(str))
      return;
    parsePhoneRegisterJson(paramContext, str);
  }

  public void getCodeVerifyResult(Context paramContext, String paramString1, String paramString2)
    throws SecurityErrorException
  {
    this.model.clear();
    String str = getStrContent(paramContext, Protocol.getInstance().getPhoneRegisterUrl("1", paramString2, paramString1, null));
    if (TextUtils.isEmpty(str))
      return;
    parsePhoneRegisterJson(paramContext, str);
  }

  public void getRegisterCodeResult(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    this.model.clear();
    String str = getStrContent(paramContext, Protocol.getInstance().getPhoneRegisterUrl("0", null, paramString, null));
    if (TextUtils.isEmpty(str))
      return;
    parsePhoneRegisterJson(paramContext, str);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.PhoneRegisterEngine
 * JD-Core Version:    0.6.0
 */