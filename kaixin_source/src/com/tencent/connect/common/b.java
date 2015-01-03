package com.tencent.connect.common;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.common.OpenConfig;
import com.tencent.record.debug.WnsClientLog;
import com.tencent.sdkutil.JsonUtil;
import com.tencent.sdkutil.Util;
import com.tencent.tauth.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.QQToken;
import com.tencent.tauth.UiError;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class b
{
  protected static final String ACTION_CHECK_TOKEN = "action_check_token";
  protected static final String ACTIVITY_AGENT = "com.tencent.open.agent.AgentActivity";
  protected static final String ACTIVITY_ENCRY_TOKEN = "com.tencent.open.agent.EncryTokenActivity";
  protected static final String DEFAULT_PF = "openmobile_android";
  private static final String KEY_REQUEST_CODE = "key_request_code";
  private static final int MSG_COMPLETE = 0;
  protected static final String PARAM_ENCRY_EOKEN = "encry_token";
  protected static final String PREFERENCE_PF = "pfStore";
  protected static int sRequestCode = 1000;
  protected Intent mActivityIntent = null;
  protected Context mContext;
  protected List<a> mTaskList = null;
  protected QQToken mToken;
  protected IUiListener mUiListener = null;

  public b(Context paramContext, QQToken paramQQToken)
  {
    this.mContext = paramContext;
    this.mToken = paramQQToken;
    this.mTaskList = new ArrayList();
  }

  protected Bundle composeActivityParams()
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("appid", this.mToken.getAppId());
    if (this.mToken.isSessionValid())
    {
      localBundle.putString("keystr", this.mToken.getAccessToken());
      localBundle.putString("keytype", "0x80");
    }
    String str = this.mToken.getOpenId();
    if (str != null)
      localBundle.putString("hopenid", str);
    localBundle.putString("platform", "androidqz");
    localBundle.putString("pf", this.mContext.getSharedPreferences("pfStore", 0).getString("pf", "openmobile_android"));
    localBundle.putString("pf", "openmobile_android");
    localBundle.putString("sdkv", "2.2");
    localBundle.putString("sdkp", "a");
    return localBundle;
  }

  protected Bundle composeCGIParams()
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("format", "json");
    localBundle.putString("status_os", Build.VERSION.RELEASE);
    localBundle.putString("status_machine", Build.MODEL);
    localBundle.putString("status_version", Build.VERSION.SDK);
    localBundle.putString("sdkv", "2.2");
    localBundle.putString("sdkp", "a");
    if ((this.mToken != null) && (this.mToken.isSessionValid()))
    {
      localBundle.putString("access_token", this.mToken.getAccessToken());
      localBundle.putString("oauth_consumer_key", this.mToken.getAppId());
      localBundle.putString("openid", this.mToken.getOpenId());
    }
    localBundle.putString("appid_for_getting_config", this.mToken.getAppId());
    localBundle.putString("pf", this.mContext.getSharedPreferences("pfStore", 0).getString("pf", "openmobile_android"));
    return localBundle;
  }

  Intent getActivityIntent()
  {
    return this.mActivityIntent;
  }

  protected Intent getAgentIntent()
  {
    return getTargetActivityIntent("com.tencent.open.agent.AgentActivity");
  }

  protected Intent getAgentIntentWithTarget(String paramString)
  {
    Intent localIntent1 = new Intent();
    Intent localIntent2 = getTargetActivityIntent(paramString);
    if (localIntent2 == null);
    do
      return null;
    while (localIntent2.getComponent() == null);
    localIntent1.setClassName(localIntent2.getComponent().getPackageName(), "com.tencent.open.agent.AgentActivity");
    return localIntent1;
  }

  protected Intent getAssitIntent()
  {
    return new Intent(this.mContext, AssistActivity.class);
  }

  protected Intent getTargetActivityIntent(String paramString)
  {
    Intent localIntent1 = new Intent();
    localIntent1.setClassName(Constants.PACKAGE_QZONE, paramString);
    Intent localIntent2 = new Intent();
    localIntent2.setClassName(Constants.PACKAGE_QQ, paramString);
    String str1 = Util.getAppVersionName(this.mContext, Constants.PACKAGE_QZONE);
    if (str1 == null)
      if (!Util.isActivityExist(this.mContext, localIntent2));
    do
    {
      return localIntent2;
      return null;
      String str2 = OpenConfig.a(this.mContext, this.mToken.getAppId()).a("Common_SSO_QzoneVersion");
      if (TextUtils.isEmpty(str2))
        str2 = "4.0";
      if ((Util.compareVersion(str1, "3.4") < 0) || (Util.compareVersion(str1, str2) >= 0))
        continue;
      boolean bool1 = Util.isActivityExist(this.mContext, localIntent1);
      Intent localIntent3 = null;
      if (bool1)
      {
        boolean bool2 = Util.isAppSignatureValid(this.mContext, localIntent1.getComponent().getPackageName(), Constants.SIGNATRUE_QZONE);
        localIntent3 = null;
        if (bool2)
          localIntent3 = localIntent1;
      }
      return localIntent3;
    }
    while (Util.isActivityExist(this.mContext, localIntent2));
    if ((Util.isActivityExist(this.mContext, localIntent1)) && (Util.isAppSignatureValid(this.mContext, localIntent1.getComponent().getPackageName(), Constants.SIGNATRUE_QZONE)));
    while (true)
    {
      return localIntent1;
      localIntent1 = null;
    }
  }

  protected boolean hasActivityForIntent()
  {
    if (this.mActivityIntent != null)
      return Util.isActivityExist(this.mContext, this.mActivityIntent);
    return false;
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    Iterator localIterator = this.mTaskList.iterator();
    IUiListener localIUiListener;
    while (true)
    {
      boolean bool = localIterator.hasNext();
      localIUiListener = null;
      if (!bool)
        break;
      a locala = (a)localIterator.next();
      if (locala.a != paramInt1)
        continue;
      localIUiListener = locala.b;
      this.mTaskList.remove(locala);
    }
    if (localIUiListener == null)
      return;
    int i;
    String str;
    if (paramInt2 == -1)
    {
      i = paramIntent.getIntExtra("key_error_code", 0);
      if (i == 0)
      {
        str = paramIntent.getStringExtra("key_response");
        if (str == null);
      }
    }
    while (true)
    {
      try
      {
        localIUiListener.onComplete(JsonUtil.parseJson(str));
        WnsClientLog.getInstance().stop();
        return;
      }
      catch (JSONException localJSONException)
      {
        localIUiListener.onError(new UiError(-4, "服务器返回数据格式有误!", str));
        WnsClientLog.getInstance().e("openSDK_LOG", "OpenUi, onActivityResult, json error", localJSONException);
        continue;
      }
      WnsClientLog.getInstance().d("openSDK_LOG", "OpenUi, onActivityResult, onComplete");
      localIUiListener.onComplete(new JSONObject());
      continue;
      WnsClientLog.getInstance().e("openSDK_LOG", "OpenUi, onActivityResult, onError = " + i + "");
      localIUiListener.onError(new UiError(i, paramIntent.getStringExtra("key_error_msg"), paramIntent.getStringExtra("key_error_detail")));
      continue;
      WnsClientLog.getInstance().d("openSDK_LOG", "OpenUi, onActivityResult, Constants.ACTIVITY_CANCEL");
      localIUiListener.onCancel();
    }
  }

  protected void startAssitActivity(Activity paramActivity, IUiListener paramIUiListener)
  {
    AssistActivity.a(this);
    int i = sRequestCode;
    sRequestCode = i + 1;
    this.mActivityIntent.putExtra("key_request_code", i);
    this.mTaskList.add(new a(this, i, paramIUiListener));
    paramActivity.startActivity(getAssitIntent());
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.connect.common.b
 * JD-Core Version:    0.6.0
 */