package com.kaixin001.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExtremityItemLv1
{
  public String appId;
  public String content;
  public int end = -1;
  public String id;
  public boolean isMainComment;
  public List<ExtremityItemLv2> itemLv2s = Collections.synchronizedList(new ArrayList());
  public ItemType itemType;
  public String mytype;
  private int step = 1;
  public String time;
  public int total = -1;
  public String uid;
  public String userIconUrl;
  public String userName;

  public void next()
  {
    if (this.itemLv2s.size() <= 0)
    {
      this.end = -1;
      return;
    }
    this.end += this.step;
    this.end = Math.min(this.end, -1 + this.itemLv2s.size());
  }

  public static enum ItemType
  {
    static
    {
      content_comment = new ItemType("content_comment", 4);
      cotent_repost = new ItemType("cotent_repost", 5);
      footer_viewmore_comment = new ItemType("footer_viewmore_comment", 6);
      footer_viewmore_repost = new ItemType("footer_viewmore_repost", 7);
      ItemType[] arrayOfItemType = new ItemType[8];
      arrayOfItemType[0] = header;
      arrayOfItemType[1] = header_comment;
      arrayOfItemType[2] = header_repost;
      arrayOfItemType[3] = no_comment;
      arrayOfItemType[4] = content_comment;
      arrayOfItemType[5] = cotent_repost;
      arrayOfItemType[6] = footer_viewmore_comment;
      arrayOfItemType[7] = footer_viewmore_repost;
      ENUM$VALUES = arrayOfItemType;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.ExtremityItemLv1
 * JD-Core Version:    0.6.0
 */