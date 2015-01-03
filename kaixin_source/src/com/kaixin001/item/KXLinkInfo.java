package com.kaixin001.item;

import android.graphics.RectF;
import android.text.TextUtils;
import java.util.ArrayList;

public class KXLinkInfo
{
  public static final String TYPE_COLOR_TEXT = "-101";
  public static final String TYPE_COMMON_USER = "0";
  private static final int TYPE_INT_COLOR_TEXT = -101;
  private static final int TYPE_INT_COMMON_USER = 0;
  private static final int TYPE_INT_LBS_POI = -110;
  private static final int TYPE_INT_LOCATION = -102;
  private static final int TYPE_INT_STAR = -1;
  private static final int TYPE_INT_TOPIC = -301;
  private static final int TYPE_INT_UNKNOWN = -100;
  private static final int TYPE_INT_URL_LINK = -103;
  private static final int TYPE_INT_VIDEO_END = -250;
  private static final int TYPE_INT_VIDEO_FLASH = -202;
  private static final int TYPE_INT_VIDEO_HTML = -201;
  private static final int TYPE_INT_VIDEO_IPHONE = -203;
  private static final int TYPE_INT_VIDEO_MP4 = -204;
  public static final String TYPE_LOCATION = "-102";
  public static final String TYPE_STAR = "-1";
  public static final String TYPE_URL_LINK = "-103";
  public static final String TYPE_URL_TOPIC = "-301";
  public static final String TYPE_URL_VIDEO_FLASH = "-202";
  public static final String TYPE_URL_VIDEO_HTML = "-201";
  public static final String TYPE_URL_VIDEO_IPHONE = "-203";
  public static final String TYPE_URL_VIDEO_MP4 = "-204";
  private boolean bIsFace = false;
  private boolean bSelected = false;
  private String content;
  private String id;
  private boolean mClickEnable = false;
  private Integer mColor = null;
  private int mPosition = -1;
  private int mType = -100;
  private String msFuid;
  private ArrayList<KXLinkInfoRect> rectFList = new ArrayList();
  private String type;

  public KXLinkInfo()
  {
  }

  public KXLinkInfo(String paramString1, String paramString2, String paramString3)
  {
    this.content = paramString1;
    this.id = paramString3;
    setType(paramString2);
    if ((isVideo()) && (paramString1 != null) && (paramString1.length() == 0));
  }

  public void addRectF(KXLinkInfoRect paramKXLinkInfoRect)
  {
    this.rectFList.add(paramKXLinkInfoRect);
  }

  public boolean contains(float paramFloat1, float paramFloat2)
  {
    int i = this.rectFList.size();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return false;
      if (((RectF)this.rectFList.get(j)).contains(paramFloat1, paramFloat2))
        return true;
    }
  }

  public Integer getColor()
  {
    return this.mColor;
  }

  public String getContent()
  {
    return this.content;
  }

  public String getId()
  {
    return this.id;
  }

  public int getPosition()
  {
    return this.mPosition;
  }

  public ArrayList<KXLinkInfoRect> getRectFList()
  {
    return this.rectFList;
  }

  public String getType()
  {
    return this.type;
  }

  public String getfuid()
  {
    return this.msFuid;
  }

  public int getnType()
  {
    return this.mType;
  }

  public boolean isClickEnable()
  {
    return this.mClickEnable;
  }

  public boolean isColorText()
  {
    return -101 == this.mType;
  }

  public boolean isCommonString()
  {
    return TextUtils.isEmpty(this.type);
  }

  public boolean isFace()
  {
    return this.bIsFace;
  }

  public boolean isLbsPoi()
  {
    return (-110 == this.mType) && (!TextUtils.isEmpty(this.id));
  }

  public boolean isLocation()
  {
    return -102 == this.mType;
  }

  public boolean isMp4()
  {
    return this.mType == -204;
  }

  public boolean isPraiseOriTitle()
  {
    return this.mType > 0;
  }

  public boolean isRepostAlbumOrVote()
  {
    return "1242".equals(this.type);
  }

  public boolean isSelected()
  {
    return this.bSelected;
  }

  public boolean isStar()
  {
    return -1 == this.mType;
  }

  public boolean isTitle()
  {
    return this.mType > 0;
  }

  public boolean isTopic()
  {
    return -301 == this.mType;
  }

  public boolean isUrlLink()
  {
    return -103 == this.mType;
  }

  public boolean isUserName()
  {
    return this.mType == 0;
  }

  public boolean isVideo()
  {
    return (this.mType <= -201) && (this.mType >= -250);
  }

  public void setClickEnable(boolean paramBoolean)
  {
    this.mClickEnable = paramBoolean;
  }

  public void setColor(int paramInt)
  {
    this.mColor = Integer.valueOf(paramInt);
  }

  public void setContent(String paramString)
  {
    this.content = paramString;
  }

  public void setFace(boolean paramBoolean)
  {
    this.bIsFace = paramBoolean;
  }

  public void setFuid(String paramString)
  {
    this.msFuid = paramString;
  }

  public void setId(String paramString)
  {
    this.id = paramString;
  }

  public void setPosition(int paramInt)
  {
    this.mPosition = paramInt;
  }

  public void setSelected(boolean paramBoolean)
  {
    this.bSelected = paramBoolean;
  }

  public void setType(String paramString)
  {
    this.type = paramString;
    try
    {
      this.mType = Integer.parseInt(paramString);
      if ((isVideo()) && ((this.content == null) || (this.content.length() == 0)))
        this.content = "分享视频";
      return;
    }
    catch (Exception localException)
    {
      while (true)
        this.mType = -100;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.KXLinkInfo
 * JD-Core Version:    0.6.0
 */