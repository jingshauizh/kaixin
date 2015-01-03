package com.tencent.open;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.record.debug.WnsClientLog;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import org.json.JSONException;
import org.json.JSONObject;

class SocialApiIml$EncryptTokenListener
  implements IUiListener
{
  private String mAction;
  private Bundle mBundle;
  private IUiListener mListener;
  private String mUrl;

  SocialApiIml$EncryptTokenListener(SocialApiIml paramSocialApiIml, IUiListener paramIUiListener, String paramString1, String paramString2, Bundle paramBundle)
  {
    this.mListener = paramIUiListener;
    this.mAction = paramString1;
    this.mUrl = paramString2;
    this.mBundle = paramBundle;
  }

  public void onCancel()
  {
    this.mListener.onCancel();
  }

  public void onComplete(JSONObject paramJSONObject)
  {
    try
    {
      String str2 = paramJSONObject.getString("encry_token");
      str1 = str2;
      this.mBundle.putString("encrytoken", str1);
      SocialApiIml.access$400(this.this$0, SocialApiIml.access$100(this.this$0), this.mAction, this.mBundle, this.mUrl, this.mListener);
      if (TextUtils.isEmpty(str1))
      {
        Log.d("miles", "The token get from qq or qzone is empty. Write temp token to localstorage.");
        this.this$0.writeEncryToken(SocialApiIml.access$500(this.this$0));
      }
      return;
    }
    catch (JSONException localJSONException)
    {
      while (true)
      {
        localJSONException.printStackTrace();
        WnsClientLog.getInstance().e("openSDK_LOG", "OpenApi, EncrytokenListener() onComplete error", localJSONException);
        String str1 = null;
      }
    }
  }

  public void onError(UiError paramUiError)
  {
    WnsClientLog.getInstance().d("openSDK_LOG", "OpenApi, EncryptTokenListener() onError" + paramUiError.errorMessage);
    this.mListener.onError(paramUiError);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.SocialApiIml.EncryptTokenListener
 * JD-Core Version:    0.6.0
 */