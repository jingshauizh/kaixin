package com.kaixin001.menu;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import com.kaixin001.engine.NavigatorApplistEngine;
import com.kaixin001.engine.NavigatorApplistEngine.KXAppItem;
import com.kaixin001.fragment.FindFriendFragment;
import com.kaixin001.fragment.FriendsFragment;
import com.kaixin001.model.User;
import com.kaixin001.util.CloseUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class KaixinNavigator
{
  public static final KaixinNavigator instance = new KaixinNavigator();
  public ArrayList<MenuCategory> menuCategoryList = new ArrayList();

  public static MenuItem createMenuItemById(Context paramContext, String paramString)
  {
    MenuItemId localMenuItemId = MenuItemId.getMenuItemIdById(paramString);
    if (localMenuItemId == null)
      return null;
    User localUser = User.getInstance();
    switch ($SWITCH_TABLE$com$kaixin001$menu$MenuItemId()[localMenuItemId.ordinal()])
    {
    default:
      MenuItem localMenuItem = new MenuItem(localMenuItemId);
      if (localUser.GetLookAround())
        localMenuItem.onMenuItemClickListener = null;
      return localMenuItem;
    case 23:
      return new HomePageMenuItem(localUser.getRealName(), localUser.getUID(), localUser.getLogo(), false);
    case 1:
      localMenuItemId.textId = 2131428345;
      if (localUser.GetLookAround())
        return new TippedMenuItem(localMenuItemId, 0, 0);
      return new TippedMenuItem(localMenuItemId, 0, 2130838227);
    case 2:
    case 6:
    case 14:
      if (localUser.GetLookAround())
        return new TippedMenuItem(localMenuItemId, 0, 0);
      return new TippedMenuItem(localMenuItemId, 0, 2130838237);
    case 7:
      return new TippedMenuItem(localMenuItemId, 0, 0);
    case 4:
    }
    if (localUser.GetLookAround())
    {
      localMenuItemId.textId = 2131428420;
      localMenuItemId.activityClass = FindFriendFragment.class;
      return new TippedMenuItem(localMenuItemId, 0, 0);
    }
    localMenuItemId.textId = 2131428347;
    localMenuItemId.activityClass = FriendsFragment.class;
    return new TippedMenuItem(localMenuItemId, 0, 0);
  }

  public static ArrayList<MenuCategory> loadMenuConfiguration(Context paramContext, InputStream paramInputStream)
  {
    ArrayList localArrayList = new ArrayList();
    DocumentBuilderFactory localDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
    while (true)
    {
      int j;
      try
      {
        NodeList localNodeList1 = localDocumentBuilderFactory.newDocumentBuilder().parse(paramInputStream).getDocumentElement().getElementsByTagName("MenuCategory");
        int i = localNodeList1.getLength();
        j = 0;
        if (j >= i)
          return localArrayList;
        Element localElement = (Element)localNodeList1.item(j);
        String str = localElement.getAttribute("id");
        if ((!User.getInstance().GetLookAround()) || (!str.equals("id_account")))
        {
          localMenuCategory = new MenuCategory(str);
          localNodeList2 = localElement.getElementsByTagName("MenuItem");
          int k = localNodeList2.getLength();
          m = 0;
          if (m < k)
            continue;
          localArrayList.add(localMenuCategory);
        }
      }
      catch (Exception localException)
      {
        MenuCategory localMenuCategory;
        NodeList localNodeList2;
        int m;
        localException.printStackTrace();
        return null;
        MenuItem localMenuItem = createMenuItemById(paramContext, ((Element)localNodeList2.item(m)).getAttribute("id"));
        if (localMenuItem == null)
          continue;
        localMenuCategory.menuItemList.add(localMenuItem);
        m++;
        continue;
      }
      finally
      {
        CloseUtil.close(paramInputStream);
      }
      j++;
    }
  }

  public MenuCategory getMenuCategory(MenuCategoryId paramMenuCategoryId)
  {
    Iterator localIterator = this.menuCategoryList.iterator();
    MenuCategory localMenuCategory;
    do
    {
      if (!localIterator.hasNext())
        return null;
      localMenuCategory = (MenuCategory)localIterator.next();
    }
    while (!localMenuCategory.categoryId.equals(paramMenuCategoryId));
    return localMenuCategory;
  }

  public MenuItem getMenuItemById(MenuItemId paramMenuItemId)
  {
    MenuItem localMenuItem;
    do
    {
      Iterator localIterator1 = this.menuCategoryList.iterator();
      Iterator localIterator2;
      while (!localIterator2.hasNext())
      {
        if (!localIterator1.hasNext())
          return null;
        localIterator2 = ((MenuCategory)localIterator1.next()).menuItemList.iterator();
      }
      localMenuItem = (MenuItem)localIterator2.next();
    }
    while (!localMenuItem.menuItemId.equals(paramMenuItemId));
    return localMenuItem;
  }

  public MenuItem getMenuItemByTargetActivity(Class<?> paramClass)
  {
    MenuItem localMenuItem;
    do
    {
      Iterator localIterator1 = this.menuCategoryList.iterator();
      Iterator localIterator2;
      while (!localIterator2.hasNext())
      {
        if (!localIterator1.hasNext())
          return null;
        localIterator2 = ((MenuCategory)localIterator1.next()).menuItemList.iterator();
      }
      localMenuItem = (MenuItem)localIterator2.next();
    }
    while (!localMenuItem.menuItemId.activityClass.equals(paramClass));
    return localMenuItem;
  }

  public void init(Context paramContext)
  {
    try
    {
      this.menuCategoryList = loadMenuConfiguration(paramContext, paramContext.getResources().getAssets().open("menu.xml"));
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  public void updateAppMenuItems()
  {
    MenuCategory localMenuCategory = getMenuCategory(MenuCategoryId.ID_APPLICATION);
    ArrayList localArrayList1 = new ArrayList(localMenuCategory.menuItemList.subList(0, 7));
    localMenuCategory.menuItemList.clear();
    localMenuCategory.menuItemList.addAll(localArrayList1);
    ArrayList localArrayList2 = NavigatorApplistEngine.getInstance().resultKXAppsList;
    Iterator localIterator;
    if (localArrayList2 != null)
      localIterator = localArrayList2.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      KXAppMenuItem localKXAppMenuItem = new KXAppMenuItem((NavigatorApplistEngine.KXAppItem)localIterator.next());
      if (User.getInstance().GetLookAround())
        localKXAppMenuItem.onMenuItemClickListener = null;
      localMenuCategory.menuItemList.add(localKXAppMenuItem);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.menu.KaixinNavigator
 * JD-Core Version:    0.6.0
 */