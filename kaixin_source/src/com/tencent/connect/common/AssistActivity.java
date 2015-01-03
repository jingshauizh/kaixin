package com.tencent.connect.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class AssistActivity extends Activity
{
  private static b b;

  static
  {
    if (!AssistActivity.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      a = bool;
      return;
    }
  }

  public static void a(b paramb)
  {
    b = paramb;
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (b != null)
      b.onActivityResult(paramInt1, paramInt2, paramIntent);
    finish();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    if ((!a) && (b == null))
      throw new AssertionError();
    if (b == null)
    {
      finish();
      return;
    }
    int i = b.getActivityIntent().getIntExtra("key_request_code", 0);
    startActivityForResult(b.getActivityIntent(), i);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.connect.common.AssistActivity
 * JD-Core Version:    0.6.0
 */