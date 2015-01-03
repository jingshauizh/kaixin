package com.tencent.tauth;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import org.json.JSONObject;

class Tencent$FeedConfirmListener$3
  implements DialogInterface.OnCancelListener
{
  public void onCancel(DialogInterface paramDialogInterface)
  {
    if (this.val$userListener != null)
      this.val$userListener.onComplete(this.val$response);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.Tencent.FeedConfirmListener.3
 * JD-Core Version:    0.6.0
 */