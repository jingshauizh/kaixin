package com.tencent.tauth;

import android.app.Dialog;
import android.view.View;
import org.json.JSONObject;

class Tencent$FeedConfirmListener$1 extends Tencent.FeedConfirmListener.ButtonListener
{
  public void onClick(View paramView)
  {
    this.this$1.sendFeedConfirmCgi();
    if ((this.dialog != null) && (this.dialog.isShowing()))
      this.dialog.dismiss();
    if (this.val$userListener != null)
      this.val$userListener.onComplete(this.val$response);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.Tencent.FeedConfirmListener.1
 * JD-Core Version:    0.6.0
 */