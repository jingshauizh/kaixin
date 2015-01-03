package com.kaixin001.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.kaixin001.fragment.PhoneRegisterFirstFragment;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.UserHabitUtils;
import java.util.Date;
import java.util.HashMap;

public class PhoneRegisterActivity extends FragmentActivity
{
  public static final String KEY_PHONE_NUMBER = "phone_number";
  public static final String KEY_VERIFY_CODE = "verify_code";
  public static final String REGULAR_PASSWORD = "^[a-z0-9_-]{6,20}$";
  public static final String REGULAR_PHONE_NUMBER = "^1[3|4|5|8][0-9]\\d{8}$";
  private HashMap<String, Long> sendTimeMap = new HashMap();

  public void addSendTime(String paramString)
  {
    long l = new Date().getTime();
    this.sendTimeMap.put(paramString, Long.valueOf(l));
  }

  public boolean after10Mins(String paramString)
  {
    long l1 = ((Long)this.sendTimeMap.get(paramString)).longValue();
    try
    {
      long l2 = (new Date().getTime() - l1) / 60000L;
      f = (float)l2;
      if (f >= 10.0F)
        return true;
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        float f = 0.0F;
      }
    }
    return false;
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.callFinishActivity(this, 4);
  }

  public boolean isRecodeSendTime(String paramString)
  {
    return this.sendTimeMap.containsKey(paramString);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903271);
    PhoneRegisterFirstFragment localPhoneRegisterFirstFragment = new PhoneRegisterFirstFragment();
    getSupportFragmentManager().beginTransaction().replace(2131363334, localPhoneRegisterFirstFragment).commit();
    UserHabitUtils.getInstance(this).addUserHabit("Photo_Register");
  }

  public void startNextFragment(Fragment paramFragment)
  {
    getSupportFragmentManager().beginTransaction().replace(2131363334, paramFragment).addToBackStack(null).commit();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.PhoneRegisterActivity
 * JD-Core Version:    0.6.0
 */