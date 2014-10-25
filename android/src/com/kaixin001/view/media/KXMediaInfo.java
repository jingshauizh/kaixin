package com.kaixin001.view.media;

import java.io.FileDescriptor;
import java.io.Serializable;

public class KXMediaInfo
  implements Serializable
{
  public static final int STATE_LOAD = 0;
  public static final int STATE_PAUSE = 3;
  public static final int STATE_PLAY = 2;
  public static final int STATE_STOP = 1;
  public String mDuration;
  private FileDescriptor mFileDescriptor;
  private String mId;
  private String mPath;
  private int mState = 1;
  private String mUrl;

  public String getDuration()
  {
    return this.mDuration;
  }

  public FileDescriptor getFileDescriptor()
  {
    return this.mFileDescriptor;
  }

  public String getId()
  {
    return this.mId;
  }

  public String getPath()
  {
    return this.mPath;
  }

  public int getState()
  {
    return this.mState;
  }

  public String getUrl()
  {
    return this.mUrl;
  }

  public void setDuration(String paramString)
  {
    this.mDuration = paramString;
  }

  public void setFileDescriptor(FileDescriptor paramFileDescriptor)
  {
    this.mFileDescriptor = paramFileDescriptor;
  }

  public void setId(String paramString)
  {
    this.mId = paramString;
  }

  public void setPath(String paramString)
  {
    this.mPath = paramString;
  }

  public void setState(int paramInt)
  {
    this.mState = paramInt;
  }

  public void setUrl(String paramString)
  {
    this.mUrl = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.media.KXMediaInfo
 * JD-Core Version:    0.6.0
 */