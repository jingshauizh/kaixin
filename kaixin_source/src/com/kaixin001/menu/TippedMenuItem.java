package com.kaixin001.menu;

import com.kaixin001.model.User;

public class TippedMenuItem extends MenuItem
{
  public int defTipIconId = 0;
  public String textTip = null;
  public int textTipBGId = 0;

  public TippedMenuItem(MenuItemId paramMenuItemId, int paramInt1, int paramInt2)
  {
    super(paramMenuItemId);
    if ((User.getInstance().GetLookAround()) && (!paramMenuItemId.equals(MenuItemId.ID_HOMEPAGE)) && (!paramMenuItemId.equals(MenuItemId.ID_NEWS)) && (!paramMenuItemId.equals(MenuItemId.ID_FRIENDS)))
      this.onMenuItemClickListener = null;
    this.defTipIconId = paramInt1;
    this.textTipBGId = paramInt2;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.menu.TippedMenuItem
 * JD-Core Version:    0.6.0
 */