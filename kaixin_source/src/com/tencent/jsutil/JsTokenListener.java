package com.tencent.jsutil;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import com.tencent.mta.TencentStat;
import com.tencent.record.debug.WnsClientLog;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.QQToken;
import com.tencent.tauth.UiError;
import org.json.JSONException;
import org.json.JSONObject;

public class JsTokenListener
  implements IUiListener
{
  private static final int ONCANCEL_MSG = 2;
  private static final int ONCOMPLETE_MSG = 0;
  private static final int ONERROR_MSG = 1;
  private Context ctx;
  private Handler mHandler = new JsTokenListener.1(this);
  private IUiListener mIUiListener = null;
  private QQToken mQqToken;

  public JsTokenListener(IUiListener paramIUiListener, QQToken paramQQToken, Context paramContext)
  {
    this.mIUiListener = paramIUiListener;
    this.mQqToken = paramQQToken;
    this.ctx = paramContext;
  }

  private void doCancel()
  {
    this.mIUiListener.onCancel();
  }

  private void doComplete(JSONObject paramJSONObject)
  {
    try
    {
      String str1 = paramJSONObject.getString("access_token");
      String str2 = paramJSONObject.getString("expires_in");
      String str3 = paramJSONObject.getString("openid");
      if ((str1 != null) && (this.mQqToken != null) && (str3 != null))
      {
        this.mQqToken.setAccessToken(str1, str2);
        this.mQqToken.setOpenId(str3);
        TencentStat.c(this.ctx, this.mQqToken);
      }
      String str4 = paramJSONObject.getString("pf");
      if (str4 != null);
      try
      {
        this.mQqToken.getAppContext().getSharedPreferences("pfStore", 0).edit().putString("pf", str4).commit();
        this.mIUiListener.onComplete(paramJSONObject);
        return;
      }
      catch (Exception localException)
      {
        while (true)
        {
          localException.printStackTrace();
          WnsClientLog.getInstance().e("openSDK_LOG", "JsTokenListener, JsTokenListener() onComplete error", localException);
        }
      }
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
  }

  private void doError(UiError paramUiError)
  {
    this.mIUiListener.onError(paramUiError);
  }

  public void onCancel()
  {
    this.mHandler.sendEmptyMessage(2);
  }

  public void onComplete(JSONObject paramJSONObject)
  {
    Message localMessage = new Message();
    localMessage.what = 0;
    localMessage.obj = paramJSONObject;
    this.mHandler.sendMessage(localMessage);
  }

  public void onError(UiError paramUiError)
  {
    Message localMessage = new Message();
    localMessage.what = 1;
    localMessage.obj = paramUiError;
    this.mHandler.sendMessage(localMessage);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.jsutil.JsTokenListener
 * JD-Core Version:    0.6.0
 */