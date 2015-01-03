package com.tencent.stat;

import android.app.ListActivity;

public class EasyListActivity extends ListActivity
{
  protected void onPause()
  {
    super.onPause();
    StatService.onPause(this);
  }

  protected void onResume()
  {
    super.onResume();
    StatService.onResume(this);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.stat.EasyListActivity
 * JD-Core Version:    0.6.0
 */