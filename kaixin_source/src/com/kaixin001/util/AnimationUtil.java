package com.kaixin001.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import com.kaixin001.activity.MainActivity;
import com.kaixin001.fragment.BaseFragment;

public class AnimationUtil
{
  public static final int ANIMATION_TYPE_PUSH_NEW_FROM_BOTTOM = 3;
  public static final int ANIMATION_TYPE_PUSH_NEW_FROM_RIGHT = 1;
  public static final int ANIMATION_TYPE_PUSH_OLD_TO_BOTTOM = 4;
  public static final int ANIMATION_TYPE_PUSH_OLD_TO_RIGHT = 2;
  public static final int ANIMATION_TYPE_ZOOM_IN_FROM_CENTER = 5;
  public static final int ANIMATION_TYPE_ZOOM_OUT_FROM_CENTER = 6;
  public static final int NO_ANIMATION;

  public static final void callFinishActivity(Activity paramActivity, int paramInt)
  {
    startActivity(paramActivity, null, paramInt);
  }

  public static final void finishActivity(Activity paramActivity, int paramInt)
  {
  }

  public static final void setFragmentAnimations(FragmentTransaction paramFragmentTransaction, int paramInt, boolean paramBoolean)
  {
    if (paramFragmentTransaction == null)
      return;
    if (paramBoolean)
    {
      switch (paramInt)
      {
      default:
        return;
      case 1:
        paramFragmentTransaction.setCustomAnimations(2130968578, 2130968582, 2130968577, 2130968583);
        return;
      case 2:
        paramFragmentTransaction.setCustomAnimations(2130968583, 2130968580, 2130968583, 2130968580);
        return;
      case 3:
        paramFragmentTransaction.setCustomAnimations(2130968576, 0, 0, 2130968581);
        return;
      case 4:
        paramFragmentTransaction.setCustomAnimations(2130968581, 2130968580, 2130968581, 2130968580);
        return;
      case 5:
      }
      paramFragmentTransaction.setCustomAnimations(2130968591, 2130968580, 2130968580, 2130968592);
      return;
    }
    switch (paramInt)
    {
    default:
      return;
    case 1:
      paramFragmentTransaction.setCustomAnimations(2130968580, 2130968578);
      return;
    case 2:
      paramFragmentTransaction.setCustomAnimations(2130968583, 2130968580);
      return;
    case 3:
      paramFragmentTransaction.setCustomAnimations(2130968580, 2130968576);
      return;
    case 4:
    }
    paramFragmentTransaction.setCustomAnimations(2130968581, 2130968580);
  }

  public static void startActivity(Activity paramActivity, Intent paramIntent, int paramInt)
  {
    if (paramActivity == null)
      return;
    if ((paramActivity.getParent() != null) && ((paramActivity.getParent() instanceof MainActivity)))
      paramActivity = paramActivity.getParent();
    if (paramIntent != null)
      paramActivity.startActivity(paramIntent);
    switch (paramInt)
    {
    default:
      return;
    case 1:
      paramActivity.overridePendingTransition(2130968578, 2130968580);
      return;
    case 2:
      paramActivity.overridePendingTransition(2130968580, 2130968583);
      return;
    case 3:
      paramActivity.overridePendingTransition(2130968576, 2130968580);
      return;
    case 4:
    }
    paramActivity.overridePendingTransition(2130968580, 2130968581);
  }

  public static void startActivityForResult(Activity paramActivity, Intent paramIntent, int paramInt1, int paramInt2)
  {
    if (paramActivity == null)
      return;
    if ((paramActivity.getParent() != null) && ((paramActivity.getParent() instanceof MainActivity)))
      paramActivity = paramActivity.getParent();
    if (paramIntent != null)
    {
      String str = paramIntent.getComponent().getClassName();
      KXLog.d("FRAGMENT", "AnimationUtil startActivityForResult callby:" + paramActivity.getClass().getName() + ", target:" + str);
      paramActivity.startActivityForResult(paramIntent, paramInt1);
    }
    switch (paramInt2)
    {
    default:
      return;
    case 1:
      paramActivity.overridePendingTransition(2130968578, 2130968580);
      return;
    case 2:
      paramActivity.overridePendingTransition(2130968580, 2130968583);
      return;
    case 3:
      paramActivity.overridePendingTransition(2130968576, 2130968580);
      return;
    case 4:
    }
    paramActivity.overridePendingTransition(2130968580, 2130968581);
  }

  public static void startFragment(BaseFragment paramBaseFragment, Intent paramIntent, int paramInt)
  {
    startFragment(paramBaseFragment, paramIntent, paramInt, true);
  }

  public static void startFragment(BaseFragment paramBaseFragment, Intent paramIntent, int paramInt, boolean paramBoolean)
  {
    if (paramBaseFragment == null);
    do
      return;
    while (paramIntent == null);
    paramBaseFragment.startFragment(paramIntent, paramBoolean, paramInt);
  }

  public static void startFragmentForResult(BaseFragment paramBaseFragment, Intent paramIntent, int paramInt)
  {
    startFragmentForResult(paramBaseFragment, paramIntent, paramInt, 1, true);
  }

  public static void startFragmentForResult(BaseFragment paramBaseFragment, Intent paramIntent, int paramInt1, int paramInt2)
  {
    startFragmentForResult(paramBaseFragment, paramIntent, paramInt1, paramInt2, true);
  }

  public static void startFragmentForResult(BaseFragment paramBaseFragment, Intent paramIntent, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (paramBaseFragment == null);
    do
      return;
    while (paramIntent == null);
    paramBaseFragment.startFragmentForResult(paramIntent, paramInt1, paramInt2, paramBoolean);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.AnimationUtil
 * JD-Core Version:    0.6.0
 */