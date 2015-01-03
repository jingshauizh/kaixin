package com.kaixin001.menu;

import java.util.ArrayList;

public class MenuCategory
{
  public MenuCategoryId categoryId;
  public ArrayList<MenuItem> menuItemList;

  public MenuCategory(String paramString)
  {
    this.categoryId = MenuCategoryId.getInstanceById(paramString);
    this.menuItemList = new ArrayList();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.menu.MenuCategory
 * JD-Core Version:    0.6.0
 */