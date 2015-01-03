package com.kaixin001.menu;

import com.kaixin001.fragment.ChatFragment;
import com.kaixin001.fragment.CirclesFragment;
import com.kaixin001.fragment.CloudAlbumFragment;
import com.kaixin001.fragment.ContactsPrepareFragment;
import com.kaixin001.fragment.DiaryListFragment;
import com.kaixin001.fragment.FilmListFragment;
import com.kaixin001.fragment.FindFriendFragment;
import com.kaixin001.fragment.FriendsFragment;
import com.kaixin001.fragment.GamesFragment;
import com.kaixin001.fragment.GiftNewsFragment;
import com.kaixin001.fragment.HomeFragment;
import com.kaixin001.fragment.HoroscopeFragment;
import com.kaixin001.fragment.MessageCenterFragment;
import com.kaixin001.fragment.MultiPicSelectorFragment;
import com.kaixin001.fragment.NewsFragment;
import com.kaixin001.fragment.OthersFragment;
import com.kaixin001.fragment.PicturesFragment;
import com.kaixin001.fragment.PositionMainFragment;
import com.kaixin001.fragment.SharedPostFragment;

public enum MenuItemId
{
  public Class<?> activityClass;
  public final int defIconId;
  public final String id;
  public final int pressIconId;
  public int textId;

  static
  {
    ID_MSG_CENTER = new MenuItemId("ID_MSG_CENTER", 1, "id_message_center", 2130838966, 2130838967, 2131428346, MessageCenterFragment.class);
    ID_GIFT = new MenuItemId("ID_GIFT", 2, "id_gift", 2130838960, 2130838961, 2131428436, GiftNewsFragment.class);
    ID_FRIENDS = new MenuItemId("ID_FRIENDS", 3, "id_friends", 2130838958, 2130838959, 2131428347, FriendsFragment.class);
    ID_HOROSCOPE = new MenuItemId("ID_HOROSCOPE", 4, "id_horoscope", 2130838956, 2130838957, 2131428348, HoroscopeFragment.class);
    ID_POSITION = new MenuItemId("ID_POSITION", 5, "id_position", 2130838962, 2130838963, 2131428349, PositionMainFragment.class);
    ID_PHOTO = new MenuItemId("ID_PHOTO", 6, "id_photo", 2130838968, 2130838969, 2131428350, PicturesFragment.class);
    ID_CHAT = new MenuItemId("ID_CHAT", 7, "id_chat", 2130838187, 0, 2131428351, ChatFragment.class);
    ID_REPOST = new MenuItemId("ID_REPOST", 8, "id_repost", 2130838974, 2130838975, 2131428352, SharedPostFragment.class);
    ID_CIRCLE = new MenuItemId("ID_CIRCLE", 9, "id_circle", 2130838188, 0, 2131428356, CirclesFragment.class);
    ID_DIARY = new MenuItemId("ID_DIARY", 10, "id_diary", 2130838189, 0, 2131427764, DiaryListFragment.class);
    ID_IMPORT_FRIENDS = new MenuItemId("ID_IMPORT_FRIENDS", 11, "id_import_friends_to_contact", 2130838201, 0, 2131428354, ContactsPrepareFragment.class);
    ID_VIEWMORE_APPLICATION = new MenuItemId("ID_VIEWMORE_APPLICATION", 12, "id_viewmore_function", 0, 0, 2131428357, null);
    ID_GAME = new MenuItemId("ID_GAME", 13, "id_suggest_application", 2130838970, 2130838971, 2131428461, GamesFragment.class);
    ID_VIEWMORE_SUGGEST_APPLICATION = new MenuItemId("ID_VIEWMORE_SUGGEST_APPLICATION", 14, "id_viewmore_suggest_application", 2130838970, 2130838971, 2131428461, null);
    ID_3RD_APP = new MenuItemId("ID_3RD_APP", 15, "id_3rd_application", 2130838162, 0, 0, null);
    ID_KX_WAP_APP = new MenuItemId("ID_KX_WAP_APP", 16, "id_wap_application", 2130838162, 0, 0, null);
    ID_APPLICATION = new MenuItemId("ID_APPLICATION", 17, "id_application", 2130838162, 0, 0, null);
    ID_VIEWMORE_COMPONENT = new MenuItemId("ID_VIEWMORE_COMPONENT", 18, "id_viewmore_application", 0, 0, 2131428357, null);
    ID_EVENT = new MenuItemId("ID_EVENT", 19, "id_event", 2130838185, 0, 0, null);
    ID_LOGOUT = new MenuItemId("ID_LOGOUT", 20, "id_logout", 2130838205, 0, 2131427697, null);
    ID_FIND_FRIEND = new MenuItemId("ID_FIND_FRIEND", 21, "id_find_friends", 2130838193, 0, 2131428353, FindFriendFragment.class);
    ID_HOMEPAGE = new MenuItemId("ID_HOMEPAGE", 22, "id_homepage", 2130838235, 0, 0, HomeFragment.class);
    ID_SETTING = new MenuItemId("ID_SETTING", 23, "id_setting", 2130838972, 2130838973, 2131428358, OthersFragment.class);
    ID_CLOUD_ALBUM = new MenuItemId("ID_CLOUD_ALBUM", 24, "id_cloud_album", 2130838952, 2130838953, 2131428500, CloudAlbumFragment.class);
    ID_FILM = new MenuItemId("ID_FILM", 25, "id_film", 2130838964, 2130838965, 2131428535, FilmListFragment.class);
    ID_GPS = new MenuItemId("ID_GPS", 26, "id_gps", 2130838210, 0, 2131428358, MultiPicSelectorFragment.class);
    MenuItemId[] arrayOfMenuItemId = new MenuItemId[27];
    arrayOfMenuItemId[0] = ID_NEWS;
    arrayOfMenuItemId[1] = ID_MSG_CENTER;
    arrayOfMenuItemId[2] = ID_GIFT;
    arrayOfMenuItemId[3] = ID_FRIENDS;
    arrayOfMenuItemId[4] = ID_HOROSCOPE;
    arrayOfMenuItemId[5] = ID_POSITION;
    arrayOfMenuItemId[6] = ID_PHOTO;
    arrayOfMenuItemId[7] = ID_CHAT;
    arrayOfMenuItemId[8] = ID_REPOST;
    arrayOfMenuItemId[9] = ID_CIRCLE;
    arrayOfMenuItemId[10] = ID_DIARY;
    arrayOfMenuItemId[11] = ID_IMPORT_FRIENDS;
    arrayOfMenuItemId[12] = ID_VIEWMORE_APPLICATION;
    arrayOfMenuItemId[13] = ID_GAME;
    arrayOfMenuItemId[14] = ID_VIEWMORE_SUGGEST_APPLICATION;
    arrayOfMenuItemId[15] = ID_3RD_APP;
    arrayOfMenuItemId[16] = ID_KX_WAP_APP;
    arrayOfMenuItemId[17] = ID_APPLICATION;
    arrayOfMenuItemId[18] = ID_VIEWMORE_COMPONENT;
    arrayOfMenuItemId[19] = ID_EVENT;
    arrayOfMenuItemId[20] = ID_LOGOUT;
    arrayOfMenuItemId[21] = ID_FIND_FRIEND;
    arrayOfMenuItemId[22] = ID_HOMEPAGE;
    arrayOfMenuItemId[23] = ID_SETTING;
    arrayOfMenuItemId[24] = ID_CLOUD_ALBUM;
    arrayOfMenuItemId[25] = ID_FILM;
    arrayOfMenuItemId[26] = ID_GPS;
    ENUM$VALUES = arrayOfMenuItemId;
  }

  private MenuItemId(String paramString, int paramInt1, int paramInt2, int paramInt3, Class<?> paramClass)
  {
    this.id = paramString;
    this.defIconId = paramInt1;
    this.pressIconId = paramInt2;
    this.textId = paramInt3;
    this.activityClass = paramClass;
  }

  public static MenuItemId getMenuItemIdById(String paramString)
  {
    MenuItemId[] arrayOfMenuItemId = values();
    int i = arrayOfMenuItemId.length;
    for (int j = 0; ; j++)
    {
      MenuItemId localMenuItemId;
      if (j >= i)
        localMenuItemId = null;
      do
      {
        return localMenuItemId;
        localMenuItemId = arrayOfMenuItemId[j];
      }
      while (localMenuItemId.id.equals(paramString));
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.menu.MenuItemId
 * JD-Core Version:    0.6.0
 */