package com.kaixin001.menu;

import android.content.Intent;
import com.kaixin001.activity.MainActivity;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.fragment.KaixinMenuListFragment;
import com.kaixin001.util.UserHabitUtils;

public class MenuItem
{
  public String iconURL = null;
  public MenuItemId menuItemId;
  public boolean movable = false;
  public OnClickListener onMenuItemClickListener = new OnClickListener()
  {
    public void onClick(BaseFragment paramBaseFragment)
    {
      Intent localIntent;
      String str;
      if (MenuItem.this.menuItemId.activityClass != null)
      {
        localIntent = new Intent(paramBaseFragment.getActivity(), MenuItem.this.menuItemId.activityClass);
        switch ($SWITCH_TABLE$com$kaixin001$menu$MenuItemId()[MenuItem.this.menuItemId.ordinal()])
        {
        case 8:
        case 10:
        case 11:
        case 12:
        case 13:
        case 15:
        case 16:
        case 17:
        case 18:
        case 19:
        case 20:
        case 21:
        case 22:
        case 23:
        default:
          str = "click_list_unknown";
        case 1:
        case 9:
        case 7:
        case 2:
        case 4:
        case 3:
        case 14:
        case 6:
        case 24:
        case 5:
        }
      }
      while (true)
      {
        localIntent.putExtra("from", "KaixinMenuListFragment");
        UserHabitUtils.getInstance(paramBaseFragment.getActivity()).addUserHabit(str);
        ((MainActivity)paramBaseFragment.getActivity()).clearBackStack();
        MenuItem.startRightFragment(paramBaseFragment, localIntent);
        return;
        str = "click_list_index";
        continue;
        str = "click_list_repaste";
        continue;
        str = "click_list_photo";
        continue;
        str = "click_list_msg";
        continue;
        str = "click_list_friend";
        continue;
        str = "click_list_gift";
        continue;
        str = "click_list_game";
        continue;
        str = "click_list_location";
        continue;
        str = "click_list_setting";
        continue;
        str = "click_list_horoscope";
      }
    }
  };
  public String text = null;

  public MenuItem(MenuItemId paramMenuItemId)
  {
    this.menuItemId = paramMenuItemId;
  }

  public static void startRightFragment(BaseFragment paramBaseFragment, Intent paramIntent)
  {
    if ((paramBaseFragment instanceof KaixinMenuListFragment))
    {
      MainActivity localMainActivity = (MainActivity)((KaixinMenuListFragment)paramBaseFragment).getActivity();
      localMainActivity.startSubFragment(paramIntent);
      localMainActivity.showSubFragment();
      return;
    }
    paramBaseFragment.startActivity(paramIntent);
  }

  public static abstract interface OnClickListener
  {
    public abstract void onClick(BaseFragment paramBaseFragment);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.menu.MenuItem
 * JD-Core Version:    0.6.0
 */