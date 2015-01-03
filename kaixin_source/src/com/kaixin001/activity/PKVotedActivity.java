package com.kaixin001.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class PKVotedActivity extends Activity
{
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903047);
    ((TextView)findViewById(2131361826)).setText(getIntent().getStringExtra("word"));
    new Handler().postDelayed(new Runnable()
    {
      public void run()
      {
        PKVotedActivity.this.finish();
      }
    }
    , 1000L);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.PKVotedActivity
 * JD-Core Version:    0.6.0
 */