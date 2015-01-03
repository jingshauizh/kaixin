package com.kaixin001.menu;

import android.content.Intent;
import com.kaixin001.engine.NavigatorApplistEngine.KXAppItem;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.fragment.MoreItemDetailFragment;
import com.kaixin001.model.User;

public class KXAppMenuItem extends MenuItem
{
  public final NavigatorApplistEngine.KXAppItem kxAppItem;

  public KXAppMenuItem(NavigatorApplistEngine.KXAppItem paramKXAppItem)
  {
    super(MenuItemId.ID_KX_WAP_APP);
    this.kxAppItem = paramKXAppItem;
    this.iconURL = paramKXAppItem.getLogo();
    this.text = paramKXAppItem.getName();
    this.onMenuItemClickListener = new MenuItem.OnClickListener(paramKXAppItem)
    {
      public void onClick(BaseFragment paramBaseFragment)
      {
        String str1 = User.getInstance().getUID();
        String str2 = this.val$kxAppItem.getName();
        StringBuffer localStringBuffer = new StringBuffer(this.val$kxAppItem.getUrl());
        Intent localIntent = new Intent(paramBaseFragment.getActivity(), MoreItemDetailFragment.class);
        localStringBuffer.append("?uid=").append(str1);
        localIntent.putExtra("label", str2);
        localIntent.putExtra("link", localStringBuffer.toString());
        MenuItem.startRightFragment(paramBaseFragment, localIntent);
      }
    };
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.menu.KXAppMenuItem
 * JD-Core Version:    0.6.0
 */