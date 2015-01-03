package com.kaixin001.menu;

public enum MenuCategoryId
{
  public final String id;
  public final Integer textId;

  static
  {
    ID_APPLICATION = new MenuCategoryId("ID_APPLICATION", 1, "id_function", Integer.valueOf(2131428330));
    ID_COMPONENT = new MenuCategoryId("ID_COMPONENT", 2, "id_application", Integer.valueOf(2131428331));
    ID_SUGGEST_APPLICATION = new MenuCategoryId("ID_SUGGEST_APPLICATION", 3, "id_suggest_application", Integer.valueOf(2131428332));
    ID_EVENT = new MenuCategoryId("ID_EVENT", 4, "id_event", Integer.valueOf(2131428333));
    ID_ACCOUNT = new MenuCategoryId("ID_ACCOUNT", 5, "id_account", Integer.valueOf(2131428334));
    ID_TEST = new MenuCategoryId("ID_TEST", 6, "id_test", Integer.valueOf(2131428335));
    ID_UNKNOWN = new MenuCategoryId("ID_UNKNOWN", 7, "id_unknown", Integer.valueOf(0));
    MenuCategoryId[] arrayOfMenuCategoryId = new MenuCategoryId[8];
    arrayOfMenuCategoryId[0] = ID_HOME;
    arrayOfMenuCategoryId[1] = ID_APPLICATION;
    arrayOfMenuCategoryId[2] = ID_COMPONENT;
    arrayOfMenuCategoryId[3] = ID_SUGGEST_APPLICATION;
    arrayOfMenuCategoryId[4] = ID_EVENT;
    arrayOfMenuCategoryId[5] = ID_ACCOUNT;
    arrayOfMenuCategoryId[6] = ID_TEST;
    arrayOfMenuCategoryId[7] = ID_UNKNOWN;
    ENUM$VALUES = arrayOfMenuCategoryId;
  }

  private MenuCategoryId(String arg3, Integer arg4)
  {
    Object localObject1;
    this.id = localObject1;
    Object localObject2;
    this.textId = localObject2;
  }

  public static MenuCategoryId getInstanceById(String paramString)
  {
    MenuCategoryId[] arrayOfMenuCategoryId = values();
    int i = arrayOfMenuCategoryId.length;
    for (int j = 0; ; j++)
    {
      MenuCategoryId localMenuCategoryId;
      if (j >= i)
        localMenuCategoryId = ID_UNKNOWN;
      do
      {
        return localMenuCategoryId;
        localMenuCategoryId = arrayOfMenuCategoryId[j];
      }
      while (localMenuCategoryId.id.equals(paramString));
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.menu.MenuCategoryId
 * JD-Core Version:    0.6.0
 */