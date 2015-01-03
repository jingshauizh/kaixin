package com.tencent.jsutil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import com.tencent.connect.common.AssistActivity;
import com.tencent.connect.common.b;
import com.tencent.sdkutil.AppUtils;
import com.tencent.sdkutil.JsonUtil;
import com.tencent.sdkutil.Util;
import com.tencent.tauth.QQToken;
import com.tencent.tauth.Tencent;
import org.json.JSONObject;

public class JumpController extends b
{
  private static final String KEY_REQUEST_CODE = "key_request_code";
  private static final String TAG = "JumpAction";
  private final int SETAVATAR_REQUESTCODE = 0;
  private Activity activity;
  private QQToken mQQToken;

  public JumpController(Activity paramActivity, QQToken paramQQToken)
  {
    super(paramActivity, paramQQToken);
    this.activity = paramActivity;
    this.mQQToken = paramQQToken;
  }

  @JavascriptInterface
  public void jump(String paramString)
  {
    Intent localIntent = AppUtils.parseJsonToIntent(paramString);
    JSONObject localJSONObject = JsonUtil.parseJson(paramString);
    if ((localJSONObject.has("package_name")) && (localJSONObject.has("class_name")) && (localJSONObject.has("requestcode")))
    {
      String str1 = localJSONObject.getString("package_name");
      String str2 = localJSONObject.getString("class_name");
      int i = localJSONObject.getInt("requestcode");
      localIntent.setClassName(str1, str2);
      this.mActivityIntent = localIntent;
      AssistActivity.a(this);
      this.mActivityIntent.putExtra("key_request_code", i);
      this.activity.startActivity(getAssitIntent());
    }
  }

  @JavascriptInterface
  public void jumpSchema(String paramString)
  {
    JSONObject localJSONObject = JsonUtil.parseJson(paramString);
    String str = "";
    boolean bool1 = localJSONObject.has("scheme");
    int i = 0;
    if (bool1)
    {
      boolean bool2 = localJSONObject.has("requestcode");
      i = 0;
      if (bool2)
      {
        str = localJSONObject.getString("scheme");
        i = localJSONObject.getInt("requestcode");
      }
    }
    Intent localIntent = new Intent("android.intent.action.VIEW");
    localIntent.setData(Uri.parse(str.toString()));
    this.mActivityIntent = localIntent;
    AssistActivity.a(this);
    this.mActivityIntent.putExtra("key_request_code", i);
    this.activity.startActivity(getAssitIntent());
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramIntent != null)
    {
      Uri localUri = paramIntent.getData();
      if ((localUri != null) && (!localUri.toString().equals("")))
      {
        String str1 = localUri.toString();
        String str2 = Util.decodeUrl(str1.substring(1 + str1.indexOf("#"))).getString("action");
        if ((str2 == null) || ((!str2.equals("shareToQQ")) && (!str2.equals("shareToQzone"))));
      }
    }
    do
      return;
    while (paramInt1 == 0);
    Log.i("currentUrl", Tencent.mBridge.getWebViewCurrentUrl() + "");
    Tencent.intentmap.setIntentMap(paramIntent);
    JsBridge localJsBridge = Tencent.mBridge;
    String[] arrayOfString = new String[2];
    arrayOfString[0] = ("" + paramInt1);
    arrayOfString[1] = ("" + paramInt2);
    localJsBridge.executeMethod("onActivityResult", arrayOfString);
  }

  public void setActivity(Activity paramActivity)
  {
    this.activity = paramActivity;
    this.mContext = paramActivity;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.jsutil.JumpController
 * JD-Core Version:    0.6.0
 */