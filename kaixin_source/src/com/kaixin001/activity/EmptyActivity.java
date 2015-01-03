package com.kaixin001.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class EmptyActivity extends Activity
{
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    ((Intent)getIntent().getExtras().getParcelable("activity"));
    finish();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.EmptyActivity
 * JD-Core Version:    0.6.0
 */