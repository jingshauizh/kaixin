package com.tencent.jsutil;

import android.os.Handler;
import android.os.Message;
import com.tencent.tauth.UiError;
import org.json.JSONObject;

class JsTokenListener$1 extends Handler
{
  public void handleMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default:
      return;
    case 0:
      JSONObject localJSONObject = (JSONObject)paramMessage.obj;
      JsTokenListener.access$000(this.this$0, localJSONObject);
      return;
    case 1:
      UiError localUiError = (UiError)paramMessage.obj;
      JsTokenListener.access$100(this.this$0, localUiError);
      return;
    case 2:
    }
    JsTokenListener.access$200(this.this$0);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.jsutil.JsTokenListener.1
 * JD-Core Version:    0.6.0
 */