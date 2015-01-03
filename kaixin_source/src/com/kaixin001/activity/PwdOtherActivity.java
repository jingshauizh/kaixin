package com.kaixin001.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.kaixin001.util.UserHabitUtils;

public class PwdOtherActivity extends Activity
  implements View.OnClickListener
{
  private Button btnCmEnroll;
  private ImageView btnLeft;
  private ImageView btnRight;
  private TextView tvTitle;

  private void doCall(String paramString)
  {
    Intent localIntent = new Intent("android.intent.action.DIAL", Uri.parse("tel:" + paramString));
    localIntent.setFlags(268435456);
    startActivity(localIntent);
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131362914:
      finish();
      return;
    case 2131363002:
    }
    doCall(this.btnCmEnroll.getText().toString());
    UserHabitUtils.getInstance(getApplicationContext()).addUserHabit("FindPwd_Other_CustomServicTel");
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903209);
    setTitleBar();
    this.btnCmEnroll = ((Button)findViewById(2131363002));
    this.btnCmEnroll.setOnClickListener(this);
    Time localTime = new Time();
    localTime.setToNow();
    if ((localTime.hour >= 9) && (localTime.hour < 18))
    {
      this.btnCmEnroll.setText(2131428384);
      this.btnCmEnroll.setBackgroundResource(2130838564);
      return;
    }
    this.btnCmEnroll.setText(2131428382);
    this.btnCmEnroll.setBackgroundResource(2130838530);
    this.btnCmEnroll.setClickable(false);
  }

  protected void setTitleBar()
  {
    this.btnLeft = ((ImageView)findViewById(2131362914));
    this.btnLeft.setOnClickListener(this);
    this.btnLeft.setOnClickListener(this);
    this.tvTitle = ((TextView)findViewById(2131362920));
    this.tvTitle.setText(2131428379);
    this.tvTitle.setVisibility(0);
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    this.btnRight = ((ImageView)findViewById(2131362928));
    this.btnRight.setVisibility(8);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.PwdOtherActivity
 * JD-Core Version:    0.6.0
 */