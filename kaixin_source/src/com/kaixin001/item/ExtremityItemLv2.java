package com.kaixin001.item;

public class ExtremityItemLv2
{
  public String content;
  public String id;
  public ItemTypeLv2 itemType = ItemTypeLv2.content_comment;
  public String mytype;
  public String time;
  public String uid;
  public String userIconUrl;
  public String userName;

  public static enum ItemTypeLv2
  {
    static
    {
      ItemTypeLv2[] arrayOfItemTypeLv2 = new ItemTypeLv2[4];
      arrayOfItemTypeLv2[0] = content_comment;
      arrayOfItemTypeLv2[1] = content_repost;
      arrayOfItemTypeLv2[2] = footer_viewmore_comment;
      arrayOfItemTypeLv2[3] = footer_viewmore_repost;
      ENUM$VALUES = arrayOfItemTypeLv2;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.ExtremityItemLv2
 * JD-Core Version:    0.6.0
 */