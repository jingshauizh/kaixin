package com.tencent.mm.sdk.platformtools;

import android.widget.ListView;

class SmoothScrollToPosition21below
  implements BackwardSupportUtil.SmoothScrollFactory.IScroll
{
  public void doScroll(ListView paramListView)
  {
    paramListView.setSelection(0);
  }

  public void doScroll(ListView paramListView, int paramInt)
  {
    paramListView.setSelection(paramInt);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.SmoothScrollToPosition21below
 * JD-Core Version:    0.6.0
 */