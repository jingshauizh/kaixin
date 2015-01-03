package com.tencent.sdkutil;

import android.content.Context;
import android.os.SystemClock;
import com.tencent.open.cgireport.ReportManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

class TDialog$OnTimeListener
  implements IUiListener
{
  private String mAction;
  String mAppid;
  String mUrl;
  private WeakReference<Context> mWeakCtx;
  private IUiListener mWeakL;

  public TDialog$OnTimeListener(Context paramContext, String paramString1, String paramString2, String paramString3, IUiListener paramIUiListener)
  {
    this.mWeakCtx = new WeakReference(paramContext);
    this.mAction = paramString1;
    this.mUrl = paramString2;
    this.mAppid = paramString3;
    this.mWeakL = paramIUiListener;
  }

  private void onComplete(String paramString)
  {
    try
    {
      onComplete(JsonUtil.parseJson(paramString));
      return;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      onError(new UiError(-4, "服务器返回数据格式有误!", paramString));
    }
  }

  public void onCancel()
  {
    if (this.mWeakL != null)
    {
      this.mWeakL.onCancel();
      this.mWeakL = null;
    }
  }

  public void onComplete(JSONObject paramJSONObject)
  {
    ReportManager.getInstance().report((Context)this.mWeakCtx.get(), this.mAction + "_H5", SystemClock.elapsedRealtime(), 0L, 0L, paramJSONObject.optInt("ret", -6), this.mAppid, this.mUrl, "1000067");
    if (this.mWeakL != null)
    {
      this.mWeakL.onComplete(paramJSONObject);
      this.mWeakL = null;
    }
  }

  public void onError(UiError paramUiError)
  {
    if (paramUiError.errorMessage != null);
    for (String str = paramUiError.errorMessage + this.mUrl; ; str = this.mUrl)
    {
      ReportManager.getInstance().report((Context)this.mWeakCtx.get(), this.mAction + "_H5", SystemClock.elapsedRealtime(), 0L, 0L, paramUiError.errorCode, this.mAppid, str, "1000067");
      if (this.mWeakL != null)
      {
        this.mWeakL.onError(paramUiError);
        this.mWeakL = null;
      }
      return;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.TDialog.OnTimeListener
 * JD-Core Version:    0.6.0
 */