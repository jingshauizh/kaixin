package com.tencent.tauth;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.tencent.jsutil.JsBridge;
import com.tencent.jsutil.JsDialogListener;
import com.tencent.jsutil.JumpController;
import com.tencent.sdkutil.AppUtils;
import com.tencent.sdkutil.TemporaryStorage;
import org.json.JSONObject;

class Tencent$3
  implements Runnable
{
  public void run()
  {
    Log.i("params", this.val$params.toString());
    Tencent.mBridge.executeMethod("reportUtils.startQQ4Connect", new String[] { "false" });
    JSONObject localJSONObject = this.this$0.parseBundleToJSON4QQShare(this.val$params, 1);
    TemporaryStorage.setListener(this.val$listener);
    Tencent.mController.setActivity(this.val$activity);
    Tencent.mJsDialogListener.setActivity(this.val$activity);
    Tencent.appUtils.setActivity(this.val$activity);
    JsBridge localJsBridge = Tencent.mBridge;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = localJSONObject.toString();
    localJsBridge.executeMethod("tencent.share.shareMessageToQQ", arrayOfString);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.Tencent.3
 * JD-Core Version:    0.6.0
 */