package com.tencent.jsutil;

import android.os.Handler;
import android.os.Message;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import org.json.JSONObject;

class PackIUiListener$1 extends Handler
{
  public void handleMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default:
      return;
    case 0:
      JSONObject localJSONObject = (JSONObject)paramMessage.obj;
      PackIUiListener.access$000(this.this$0).onComplete(localJSONObject);
      return;
    case 1:
      PackIUiListener.access$000(this.this$0).onCancel();
      return;
    case 2:
    }
    UiError localUiError = (UiError)paramMessage.obj;
    PackIUiListener.access$000(this.this$0).onError(localUiError);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.jsutil.PackIUiListener.1
 * JD-Core Version:    0.6.0
 */