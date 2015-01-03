package com.kaixin001.item;

import java.io.Serializable;
import java.util.ArrayList;

public class RepItem
  implements Serializable
{
  private static final long serialVersionUID = -12345678L;
  public int category = 20;
  public long ctime;
  public String flogo = null;
  public String fname = null;
  public String ftitle = null;
  public String fuid = null;
  public String id = null;
  public boolean isStar = false;
  public String mContent = null;
  public boolean mHideUserInfo = false;
  public ArrayList<String> mRepostImgList = null;
  public int mRepostNum;
  public String mThumbImg = null;
  public String myview = null;
  public String stime;
  public String title = null;
  public int type = 0;
  public String vthumb = null;
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.RepItem
 * JD-Core Version:    0.6.0
 */