package com.tencent.jsutil;

import android.os.Handler;
import android.os.Message;
import android.webkit.JavascriptInterface;
import com.tencent.sdkutil.TemporaryStorage;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import org.json.JSONException;
import org.json.JSONObject;

public class PackIUiListener
  implements JsIUiListener
{
  private static final int CANCEL = 1;
  private static final int COMPLETE = 0;
  private static final int ERROR = 2;
  Handler mHandler = new PackIUiListener.1(this);
  private IUiListener mIUiListener;

  @JavascriptInterface
  public void onCancel(int paramInt)
  {
    this.mIUiListener = TemporaryStorage.getListener(paramInt);
    if (this.mIUiListener != null)
    {
      Message localMessage = new Message();
      localMessage.what = 1;
      this.mHandler.sendMessage(localMessage);
    }
  }

  @JavascriptInterface
  public void onComplete(String paramString, int paramInt)
  {
    int i = 1;
    while (true)
    {
      try
      {
        this.mIUiListener = TemporaryStorage.getListener(Integer.valueOf(paramInt).intValue());
        if (this.mIUiListener == null)
          continue;
        int j = i;
        break label94;
        if ((i & j) == 0)
          continue;
        Message localMessage = new Message();
        localMessage.what = 0;
        localMessage.obj = new JSONObject(paramString);
        this.mHandler.sendMessage(localMessage);
        return;
        j = 0;
        break label94;
        i = 0;
        continue;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        return;
      }
      label94: if (paramString == null)
        continue;
    }
  }

  @JavascriptInterface
  public void onError(int paramInt1, String paramString1, String paramString2, int paramInt2)
  {
    this.mIUiListener = TemporaryStorage.getListener(paramInt2);
    if (this.mIUiListener != null)
    {
      Message localMessage = new Message();
      localMessage.what = 2;
      localMessage.obj = new UiError(paramInt1, paramString1, paramString2);
      this.mHandler.sendMessage(localMessage);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.jsutil.PackIUiListener
 * JD-Core Version:    0.6.0
 */