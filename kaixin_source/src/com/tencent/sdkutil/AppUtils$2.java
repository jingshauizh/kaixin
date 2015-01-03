package com.tencent.sdkutil;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import com.tencent.jsutil.JsBridge;
import com.tencent.jsutil.JsConfig;

class AppUtils$2
  implements DialogInterface.OnClickListener
{
  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    JsBridge.getInstance(AppUtils.access$000(this.this$0), JsConfig.mTencentFileProtocolPath).executeMethod("appUtils.onJsAlertCallBack", new String[0]);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.AppUtils.2
 * JD-Core Version:    0.6.0
 */