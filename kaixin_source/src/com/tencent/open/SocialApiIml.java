package com.tencent.open;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.tencent.connect.common.b;
import com.tencent.record.debug.WnsClientLog;
import com.tencent.sdkutil.PKDialog;
import com.tencent.sdkutil.ServerSetting;
import com.tencent.sdkutil.TDialog;
import com.tencent.sdkutil.Util;
import com.tencent.tauth.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.QQToken;
import com.tencent.tauth.UiError;
import org.json.JSONException;
import org.json.JSONObject;

public class SocialApiIml extends b
{
  private Activity mActivity;
  ProgressDialog mProgressDialog;

  public SocialApiIml(Context paramContext, QQToken paramQQToken)
  {
    super(paramContext, paramQQToken);
  }

  private void askgift(Activity paramActivity, String paramString, Bundle paramBundle, IUiListener paramIUiListener)
  {
    this.mActivity = paramActivity;
    Intent localIntent = getAgentIntentWithTarget("com.tencent.open.agent.RequestFreegiftActivity");
    paramBundle.putAll(composeActivityParams());
    if ("action_ask".equals(paramString))
      paramBundle.putString("type", "request");
    while (true)
    {
      handleIntent(paramActivity, localIntent, paramString, paramBundle, ServerSetting.getInstance().getEnvUrl(this.mContext, "http://qzs.qq.com/open/mobile/request/sdk_request.html?"), paramIUiListener);
      return;
      if (!"action_gift".equals(paramString))
        continue;
      paramBundle.putString("type", "freegift");
    }
  }

  private void dismissProgressDl()
  {
    if ((!this.mActivity.isFinishing()) && (this.mProgressDialog != null) && (this.mProgressDialog.isShowing()))
    {
      this.mProgressDialog.dismiss();
      this.mProgressDialog = null;
    }
  }

  private SocialApiIml.DelayStartParam generateDelayStParam(Bundle paramBundle, String paramString1, String paramString2, IUiListener paramIUiListener)
  {
    Intent localIntent = new Intent();
    localIntent.setClassName(Constants.PACKAGE_QZONE, "com.tencent.open.agent.AgentActivity");
    SocialApiIml.DelayStartParam localDelayStartParam = new SocialApiIml.DelayStartParam();
    localDelayStartParam.agentIntent = localIntent;
    localDelayStartParam.params = paramBundle;
    localDelayStartParam.h5Url = paramString2;
    localDelayStartParam.listener = paramIUiListener;
    localDelayStartParam.action = paramString1;
    return localDelayStartParam;
  }

  private void handleIntent(Activity paramActivity, Intent paramIntent, String paramString1, Bundle paramBundle, String paramString2, IUiListener paramIUiListener)
  {
    Log.i("SocialApiIml", "SocialApiIml handleIntent " + paramString1 + " params=" + paramBundle + " activityIntent=" + paramIntent);
    if (paramIntent != null)
    {
      paramIntent.putExtra("key_action", paramString1);
      paramIntent.putExtra("key_params", paramBundle);
      this.mActivityIntent = paramIntent;
      startAssitActivity(paramActivity, paramIUiListener);
    }
    SocialApiIml.EncryptTokenListener localEncryptTokenListener;
    while (true)
    {
      return;
      Intent localIntent1 = getTargetActivityIntent("com.tencent.open.agent.AgentActivity");
      localEncryptTokenListener = new SocialApiIml.EncryptTokenListener(this, paramIUiListener, paramString1, paramString2, paramBundle);
      Intent localIntent2 = getTargetActivityIntent("com.tencent.open.agent.EncryTokenActivity");
      if ((localIntent2 == null) || (localIntent1 == null) || (localIntent1.getComponent() == null) || (localIntent2.getComponent() == null) || (!localIntent1.getComponent().getPackageName().equals(localIntent2.getComponent().getPackageName())))
        break;
      localIntent2.putExtra("oauth_consumer_key", this.mToken.getAppId());
      localIntent2.putExtra("openid", this.mToken.getOpenId());
      localIntent2.putExtra("access_token", this.mToken.getAccessToken());
      localIntent2.putExtra("key_action", "action_check_token");
      this.mActivityIntent = localIntent2;
      if (!hasActivityForIntent())
        continue;
      startAssitActivity(paramActivity, localEncryptTokenListener);
      return;
    }
    Log.e("source", "tencent&sdk&qazxc***14969%%" + this.mToken.getAccessToken() + this.mToken.getAppId() + this.mToken.getOpenId() + "qzone3.4");
    String str = Util.encrypt("tencent&sdk&qazxc***14969%%" + this.mToken.getAccessToken() + this.mToken.getAppId() + this.mToken.getOpenId() + "qzone3.4");
    Log.e("result", str);
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("encry_token", str);
      localEncryptTokenListener.onComplete(localJSONObject);
      return;
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }

  private void showDialog(Context paramContext, String paramString1, Bundle paramBundle, String paramString2, IUiListener paramIUiListener)
  {
    WnsClientLog.getInstance().v("openSDK_LOG", "OpenUi, showDialog --start");
    CookieSyncManager.createInstance(paramContext);
    paramBundle.putString("oauth_consumer_key", this.mToken.getAppId());
    if (this.mToken.isSessionValid())
      paramBundle.putString("access_token", this.mToken.getAccessToken());
    String str1 = this.mToken.getOpenId();
    if (str1 != null)
      paramBundle.putString("openid", str1);
    try
    {
      paramBundle.putString("pf", this.mContext.getSharedPreferences("pfStore", 0).getString("pf", "openmobile_android"));
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString2);
      localStringBuilder.append(Util.encodeUrl(paramBundle));
      str2 = localStringBuilder.toString();
      WnsClientLog.getInstance().d("openSDK_LOG", "OpenUi, showDialog TDialog");
      if (("action_challenge".equals(paramString1)) || ("action_brag".equals(paramString1)))
      {
        WnsClientLog.getInstance().d("openSDK_LOG", "OpenUi, showDialog PKDialog");
        new PKDialog(this.mActivity, paramString1, str2, paramIUiListener, this.mToken).show();
        return;
      }
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        localException.printStackTrace();
        paramBundle.putString("pf", "openmobile_android");
      }
      new TDialog(this.mActivity, paramString1, str2, paramIUiListener, this.mToken).show();
    }
  }

  private void startVoiceView(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    Intent localIntent = getAgentIntentWithTarget("com.tencent.open.agent.voice");
    String str = ServerSetting.getInstance().getEnvUrl(this.mContext, "http://qzs.qq.com/open/mobile/not_support.html?");
    if ((localIntent == null) && (isCheckFunctionEnabled()))
    {
      if ((this.mProgressDialog == null) || (!this.mProgressDialog.isShowing()))
      {
        this.mProgressDialog = new ProgressDialog(paramActivity);
        this.mProgressDialog.setTitle("请稍候");
        this.mProgressDialog.show();
      }
      getTargetActivityIntentForNew(paramActivity, "action_voice", new SocialApiIml.CheckListener(this, generateDelayStParam(paramBundle, "action_voice", str, paramIUiListener)));
      return;
    }
    handleIntent(paramActivity, localIntent, "action_voice", paramBundle, str, paramIUiListener);
  }

  public void ask(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    askgift(paramActivity, "action_ask", paramBundle, paramIUiListener);
  }

  public void brag(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    this.mActivity = paramActivity;
    Intent localIntent = getAgentIntentWithTarget("com.tencent.open.agent.BragActivity");
    paramBundle.putAll(composeActivityParams());
    handleIntent(paramActivity, localIntent, "action_brag", paramBundle, ServerSetting.getInstance().getEnvUrl(this.mContext, "http://qzs.qq.com/open/mobile/brag/sdk_brag.html?"), paramIUiListener);
  }

  public void challenge(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    this.mActivity = paramActivity;
    Intent localIntent = getAgentIntentWithTarget("com.tencent.open.agent.ChallengeActivity");
    paramBundle.putAll(composeActivityParams());
    handleIntent(paramActivity, localIntent, "action_challenge", paramBundle, ServerSetting.getInstance().getEnvUrl(this.mContext, "http://qzs.qq.com/open/mobile/brag/sdk_brag.html?"), paramIUiListener);
  }

  protected void getTargetActivityIntentForNew(Activity paramActivity, String paramString, IUiListener paramIUiListener)
  {
    Intent localIntent = new Intent();
    localIntent.setClassName(Constants.PACKAGE_QZONE, "com.tencent.open.agent.AgentActivity");
    localIntent.putExtra("key_action", "action_check");
    Bundle localBundle = new Bundle();
    localBundle.putString("apiName", paramString);
    localIntent.putExtra("key_params", localBundle);
    this.mActivityIntent = localIntent;
    startAssitActivity(paramActivity, paramIUiListener);
  }

  public void gift(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    askgift(paramActivity, "action_gift", paramBundle, paramIUiListener);
  }

  public void grade(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    this.mActivity = paramActivity;
    paramBundle.putAll(composeActivityParams());
    paramBundle.putString("version", Util.getAppVersion(paramActivity));
    Intent localIntent = getAgentIntentWithTarget("com.tencent.open.agent.AppGradeActivity");
    if ((localIntent == null) && (isCheckFunctionEnabled()))
    {
      this.mProgressDialog = new ProgressDialog(paramActivity);
      this.mProgressDialog.setMessage("请稍候...");
      this.mProgressDialog.show();
      getTargetActivityIntentForNew(paramActivity, "action_grade", new SocialApiIml.CheckListener(this, generateDelayStParam(paramBundle, "action_grade", "http://qzs.qq.com/open/mobile/rate/sdk_rate.html?", paramIUiListener)));
      return;
    }
    handleIntent(paramActivity, localIntent, "action_grade", paramBundle, "http://qzs.qq.com/open/mobile/rate/sdk_rate.html?", paramIUiListener);
  }

  public void invite(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    this.mActivity = paramActivity;
    Intent localIntent = getAgentIntentWithTarget("com.tencent.open.agent.AppInvitationActivity");
    paramBundle.putAll(composeActivityParams());
    handleIntent(paramActivity, localIntent, "action_invite", paramBundle, ServerSetting.getInstance().getEnvUrl(this.mContext, "http://qzs.qq.com/open/mobile/invite/sdk_invite.html?"), paramIUiListener);
  }

  protected boolean isCheckFunctionEnabled()
  {
    Intent localIntent = new Intent();
    localIntent.setClassName(Constants.PACKAGE_QZONE, "com.tencent.open.agent.CheckFunctionActivity");
    return Util.isActivityExist(this.mContext, localIntent);
  }

  public void story(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    this.mActivity = paramActivity;
    Intent localIntent = getAgentIntentWithTarget("com.tencent.open.agent.SendStoryActivity");
    paramBundle.putAll(composeActivityParams());
    handleIntent(paramActivity, localIntent, "action_story", paramBundle, ServerSetting.getInstance().getEnvUrl(this.mContext, "http://qzs.qq.com/open/mobile/sendstory/sdk_sendstory_v1.3.html?"), paramIUiListener);
  }

  public void voice(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    Log.v("voice", "voice params=" + paramBundle);
    this.mActivity = paramActivity;
    paramBundle.putAll(composeActivityParams());
    paramBundle.putString("version", Util.getAppVersion(paramActivity));
    if (!VoiceHelper.hasSDCard())
    {
      paramIUiListener.onError(new UiError(-12, "检测不到SD卡，无法发送语音！", "检测不到SD卡，无法发送语音！"));
      return;
    }
    if (paramBundle.containsKey("image_date"))
    {
      Bitmap localBitmap = (Bitmap)paramBundle.getParcelable("image_date");
      if (localBitmap != null)
      {
        this.mProgressDialog = new ProgressDialog(paramActivity);
        this.mProgressDialog.setTitle("请稍候，正在查询…");
        this.mProgressDialog.show();
        new VoiceHelper(new SocialApiIml.1(this, paramBundle, paramActivity, paramIUiListener)).execute(new Bitmap[] { localBitmap });
        return;
      }
    }
    startVoiceView(paramActivity, paramBundle, paramIUiListener);
  }

  @SuppressLint({"SetJavaScriptEnabled"})
  public void writeEncryToken(Context paramContext)
  {
    String str1 = this.mToken.getAccessToken();
    String str2 = this.mToken.getAppId();
    String str3 = this.mToken.getOpenId();
    if ((str1 != null) && (str1.length() > 0) && (str2 != null) && (str2.length() > 0) && (str3 != null) && (str3.length() > 0));
    for (String str4 = Util.encrypt("tencent&sdk&qazxc***14969%%" + str1 + str2 + str3 + "qzone3.4"); ; str4 = null)
    {
      WebView localWebView = new WebView(paramContext);
      WebSettings localWebSettings = localWebView.getSettings();
      localWebSettings.setDomStorageEnabled(true);
      localWebSettings.setJavaScriptEnabled(true);
      localWebSettings.setDatabaseEnabled(true);
      String str5 = "<!DOCTYPE HTML><html lang=\"en-US\"><head><meta charset=\"UTF-8\"><title>localStorage Test</title><script type=\"text/javascript\">document.domain = 'qq.com';localStorage[\"" + this.mToken.getOpenId() + "_" + this.mToken.getAppId() + "\"]=\"" + str4 + "\";</script></head><body></body></html>";
      String str6 = ServerSetting.getInstance().getEnvUrl(paramContext, "http://qzs.qq.com");
      localWebView.loadDataWithBaseURL(str6, str5, "text/html", "utf-8", str6);
      return;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.SocialApiIml
 * JD-Core Version:    0.6.0
 */