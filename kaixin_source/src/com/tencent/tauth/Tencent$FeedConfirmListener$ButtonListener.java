package com.tencent.tauth;

import android.app.Dialog;
import android.view.View.OnClickListener;

abstract class Tencent$FeedConfirmListener$ButtonListener
  implements View.OnClickListener
{
  Dialog dialog;

  Tencent$FeedConfirmListener$ButtonListener(Tencent.FeedConfirmListener paramFeedConfirmListener, Dialog paramDialog)
  {
    this.dialog = paramDialog;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.Tencent.FeedConfirmListener.ButtonListener
 * JD-Core Version:    0.6.0
 */