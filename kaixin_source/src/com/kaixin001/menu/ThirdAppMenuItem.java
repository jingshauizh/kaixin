package com.kaixin001.menu;

import android.support.v4.app.FragmentActivity;
import com.kaixin001.activity.KXApplication;
import com.kaixin001.businesslogic.ThirdPartyAppUtil;
import com.kaixin001.engine.NavigatorApplistEngine.ThirdAppItem;
import com.kaixin001.fragment.BaseFragment;

public class ThirdAppMenuItem extends TippedMenuItem
{
  public final NavigatorApplistEngine.ThirdAppItem thirdAppItem;

  public ThirdAppMenuItem(NavigatorApplistEngine.ThirdAppItem paramThirdAppItem)
  {
    super(MenuItemId.ID_3RD_APP, 2130838182, 0);
    if (paramThirdAppItem.getPakageName().equals("html5"))
      this.defTipIconId = 0;
    this.thirdAppItem = paramThirdAppItem;
    this.iconURL = paramThirdAppItem.getLogo();
    this.text = paramThirdAppItem.getName();
    this.onMenuItemClickListener = new MenuItem.OnClickListener(paramThirdAppItem)
    {
      public void onClick(BaseFragment paramBaseFragment)
      {
        ((KXApplication)paramBaseFragment.getActivity().getApplicationContext()).thirdPartyAppUtil.onClickAppItem(paramBaseFragment, this.val$thirdAppItem);
      }
    };
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.menu.ThirdAppMenuItem
 * JD-Core Version:    0.6.0
 */