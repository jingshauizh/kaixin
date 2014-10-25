package com.kaixin001.item;

public class AdvertItem
{
  public static final String CHANGE_BACKGROUND = "chgbg";
  public static final String CHECK_IN = "checkin";
  public static final String FILM_INFO = "filminfo";
  public static final String FILM_LIST = "filmlist";
  public static final String FRIEND_ADD = "friendadd";
  public static final String FRIEND_LIST = "friendlist";
  public static final String GAME = "game";
  public static final String GIFT = "gift";
  public static final String LINK = "link";
  public static final String NEAR_PEOPLE = "nearpeople";
  public static final String NEAR_PIC = "nearpic";
  public static final String PHOTOACTIVITY = "photoactivity";
  public static final String PROFILE = "profile";
  public static final String RECORD = "record";
  public static final String REPASTE_LIST = "repastelist";
  public static final String SHOT = "shot";
  public static final String TOPIC = "topic";
  public static final String VOICE = "voice";
  public String advertClickUrl = null;
  public String advertImageUrl = null;
  public String filmId = null;
  public String filmName = null;
  public int id = 0;
  public String title = null;
  public String type = null;
  public String uploadphoto = null;

  public AdvertItem(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    this.id = paramInt;
    this.type = paramString1;
    this.advertImageUrl = paramString2;
    this.advertClickUrl = paramString3;
    this.uploadphoto = paramString5;
    this.title = paramString4;
    this.filmId = paramString6;
    this.filmName = paramString7;
  }

  public String getFilmId()
  {
    return this.filmId;
  }

  public void setFilmId(String paramString)
  {
    this.filmId = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.AdvertItem
 * JD-Core Version:    0.6.0
 */