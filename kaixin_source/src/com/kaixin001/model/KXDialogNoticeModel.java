package com.kaixin001.model;

import android.text.TextUtils;
import java.util.HashMap;

public class KXDialogNoticeModel extends KXModel
{
  public static final int NOTICE_DIALOG = 1;
  public static final int NOTICE_NONE;
  private static KXDialogNoticeModel instance;
  private ButtonData mButtonData1;
  private ButtonData mButtonData2;
  private String mContent;
  private int mDialogNoticeType;
  private String mTitle;

  public static KXDialogNoticeModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new KXDialogNoticeModel();
      KXDialogNoticeModel localKXDialogNoticeModel = instance;
      return localKXDialogNoticeModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void clear()
  {
    this.mTitle = null;
    this.mContent = null;
    this.mButtonData1 = null;
    this.mButtonData2 = null;
  }

  public ButtonData getButtonData1()
  {
    return this.mButtonData1;
  }

  public ButtonData getButtonData2()
  {
    return this.mButtonData2;
  }

  public String getContent()
  {
    return this.mContent;
  }

  public int getDialogNoticeType()
  {
    return this.mDialogNoticeType;
  }

  public String getTitle()
  {
    return this.mTitle;
  }

  public boolean haveData()
  {
    return (!TextUtils.isEmpty(this.mTitle)) && (!TextUtils.isEmpty(this.mContent));
  }

  public void setButtonData1(ButtonData paramButtonData)
  {
    this.mButtonData1 = paramButtonData;
  }

  public void setButtonData2(ButtonData paramButtonData)
  {
    this.mButtonData2 = paramButtonData;
  }

  public void setContent(String paramString)
  {
    this.mContent = paramString;
  }

  public void setDialogNoticeType(int paramInt)
  {
    this.mDialogNoticeType = paramInt;
  }

  public void setTitle(String paramString)
  {
    this.mTitle = paramString;
  }

  public static class ButtonData
  {
    public String mName;
    public String mPageId;
    public HashMap<String, String> mParams;
    public String mUrl;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.KXDialogNoticeModel
 * JD-Core Version:    0.6.0
 */