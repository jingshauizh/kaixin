package com.tencent.tauth;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.tencent.sdkutil.BrowserAuth;
import com.tencent.sdkutil.BrowserAuth.Auth;
import com.tencent.sdkutil.TDialog;
import com.tencent.sdkutil.TemporaryStorage;
import com.tencent.sdkutil.Util;
import org.json.JSONException;
import org.json.JSONObject;

public class AuthActivity extends Activity
{
  public static final String ACTION_KEY = "action";
  public static final String ACTION_SHARE_TO_QQ = "shareToQQ";
  public static final String ACTION_SHARE_TO_QZONE = "shareToQzone";
  private static final String TAG = "AuthActivity";

  private void execAuthCallback(Bundle paramBundle, String paramString)
  {
    BrowserAuth localBrowserAuth = BrowserAuth.getInstance();
    String str1 = paramBundle.getString("serial");
    BrowserAuth.Auth localAuth = localBrowserAuth.get(str1);
    if (localAuth != null)
    {
      if (paramString.indexOf("://cancel") == -1)
        break label64;
      localAuth.listener.onCancel();
      localAuth.dialog.dismiss();
    }
    while (true)
    {
      localBrowserAuth.remove(str1);
      finish();
      return;
      label64: String str2 = paramBundle.getString("access_token");
      if (str2 != null)
        paramBundle.putString("access_token", localBrowserAuth.decode(str2, localAuth.key));
      String str3 = Util.encodeUrl(paramBundle);
      JSONObject localJSONObject = Util.decodeUrlToJson(new JSONObject(), str3);
      String str4 = localJSONObject.optString("cb");
      if (!"".equals(str4))
      {
        localAuth.dialog.callJs(str4, localJSONObject.toString());
        continue;
      }
      localAuth.listener.onComplete(localJSONObject);
      localAuth.dialog.dismiss();
    }
  }

  private void execShareToQQCallback(Bundle paramBundle, IUiListener paramIUiListener)
  {
    if (paramIUiListener == null)
    {
      finish();
      return;
    }
    String str1 = paramBundle.getString("result");
    String str2 = paramBundle.getString("response");
    if (str1.equals("cancel"))
      paramIUiListener.onCancel();
    do
      while (true)
      {
        finish();
        return;
        if (!str1.equals("error"))
          break;
        paramIUiListener.onError(new UiError(-6, "unknown error", str2 + ""));
      }
    while (!str1.equals("complete"));
    if (str2 == null);
    for (String str3 = "{\"ret\": 0}"; ; str3 = str2)
    {
      try
      {
        paramIUiListener.onComplete(new JSONObject(str3));
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        paramIUiListener.onError(new UiError(-4, "json error", str3 + ""));
      }
      break;
    }
  }

  private void handleActionUri(Uri paramUri)
  {
    if ((paramUri == null) || (paramUri.toString().equals("")))
    {
      finish();
      return;
    }
    String str1 = paramUri.toString();
    Bundle localBundle = Util.decodeUrl(str1.substring(1 + str1.indexOf("#")));
    String str2 = localBundle.getString("action");
    if (str2 == null)
    {
      execAuthCallback(localBundle, str1);
      return;
    }
    if ((str2.equals("shareToQQ")) || (str2.equals("shareToQzone")))
    {
      execShareToQQCallback(localBundle, TemporaryStorage.getListener(TemporaryStorage.nextRequestCode()));
      return;
    }
    execAuthCallback(localBundle, str1);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    handleActionUri(getIntent().getData());
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.AuthActivity
 * JD-Core Version:    0.6.0
 */