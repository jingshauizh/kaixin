package com.kaixin001.menu;

import android.content.Intent;
import android.os.Bundle;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.fragment.DiaryListFragment;

public class DiaryPageMenuItem extends MenuItem
{
  private String fname;
  private String fuid;
  private String gender;
  private String isMyFriend;

  public DiaryPageMenuItem(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    super(MenuItemId.ID_DIARY);
    this.fuid = paramString1;
    this.fname = paramString2;
    this.gender = paramString3;
    this.isMyFriend = paramString4;
    this.onMenuItemClickListener = new MenuItem.OnClickListener()
    {
      public void onClick(BaseFragment paramBaseFragment)
      {
        Intent localIntent = new Intent(paramBaseFragment.getActivity(), DiaryListFragment.class);
        Bundle localBundle = new Bundle();
        localBundle.putString("fuid", DiaryPageMenuItem.this.fuid);
        localBundle.putString("fname", DiaryPageMenuItem.this.fname);
        localBundle.putString("ismyfriend", DiaryPageMenuItem.this.isMyFriend);
        localBundle.putString("gender", DiaryPageMenuItem.this.gender);
        localIntent.putExtras(localBundle);
        DiaryPageMenuItem.startRightFragment(paramBaseFragment, localIntent);
      }
    };
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.menu.DiaryPageMenuItem
 * JD-Core Version:    0.6.0
 */