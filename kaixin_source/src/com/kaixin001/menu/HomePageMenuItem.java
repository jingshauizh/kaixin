package com.kaixin001.menu;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.fragment.HomeFragment;
import com.kaixin001.model.User;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.IntentUtil;

public class HomePageMenuItem extends MenuItem
{
  public String flogo;
  public String fname;
  public final String fuid;
  public final boolean isStar;

  public HomePageMenuItem(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    super(MenuItemId.ID_HOMEPAGE);
    this.fuid = paramString2;
    this.isStar = paramBoolean;
    setName(paramString1, paramString3);
    this.onMenuItemClickListener = new MenuItem.OnClickListener()
    {
      public void onClick(BaseFragment paramBaseFragment)
      {
        if (User.getInstance().GetLookAround())
        {
          FragmentActivity localFragmentActivity = paramBaseFragment.getActivity();
          String[] arrayOfString = new String[2];
          arrayOfString[0] = paramBaseFragment.getString(2131427338);
          arrayOfString[1] = paramBaseFragment.getString(2131427339);
          DialogUtil.showSelectListDlg(localFragmentActivity, 2131427729, arrayOfString, new DialogInterface.OnClickListener(paramBaseFragment)
          {
            public void onClick(DialogInterface paramDialogInterface, int paramInt)
            {
              switch (paramInt)
              {
              default:
                return;
              case 0:
                IntentUtil.returnToLogin(this.val$fragment.getActivity(), false);
                return;
              case 1:
              }
              Bundle localBundle = new Bundle();
              localBundle.putInt("regist", 1);
              IntentUtil.returnToLogin(this.val$fragment.getActivity(), localBundle, false);
            }
          }
          , 1);
          return;
        }
        Intent localIntent = new Intent(paramBaseFragment.getActivity(), HomeFragment.class);
        localIntent.putExtras(IntentUtil.getHomeActivityIntent(HomePageMenuItem.this.fuid, HomePageMenuItem.this.fname, HomePageMenuItem.this.flogo, null));
        HomePageMenuItem.startRightFragment(paramBaseFragment, localIntent);
      }
    };
  }

  public void setName(String paramString1, String paramString2)
  {
    this.fname = paramString1;
    this.text = paramString1;
    this.flogo = paramString2;
    this.iconURL = paramString2;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.menu.HomePageMenuItem
 * JD-Core Version:    0.6.0
 */