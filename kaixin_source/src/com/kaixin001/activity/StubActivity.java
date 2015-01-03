package com.kaixin001.activity;

import android.app.Activity;
import android.os.Bundle;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.IntentUtil;

public class StubActivity extends Activity
{
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (CrashRecoverUtil.isCrashBefore())
    {
      CrashRecoverUtil.crashRecover(getApplicationContext());
      IntentUtil.returnToDesktop(this);
      return;
    }
    finish();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.StubActivity
 * JD-Core Version:    0.6.0
 */