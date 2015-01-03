package com.kaixin001.item;

public class CloudAlbumPicItem
{
  public static final int STATE_IGNORE = 4;
  public static final int STATE_NONE = -1;
  public static final int STATE_SYNC = 1;
  public static final int STATE_UN_SYNC = 0;
  public static final int STATE_UPLOADING = 2;
  public static final int STATE_UPLOAD_FAILED = 3;
  public String mCpid;
  public boolean mIsLocalAlbumPic = true;
  public String mLargeUrl;
  public long mLastModfiedTime;
  public String mMD5;
  public int mPercent = 0;
  public int mState = -1;
  public String mThumbUrl;
  public String mUrl;
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.CloudAlbumPicItem
 * JD-Core Version:    0.6.0
 */