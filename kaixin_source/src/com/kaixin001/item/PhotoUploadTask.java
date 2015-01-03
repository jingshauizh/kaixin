package com.kaixin001.item;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.ExifInterface;
import com.kaixin001.db.UpLoadTaskListDBAdapter.TaskParameters;
import com.kaixin001.engine.UploadPhotoEngine;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.model.NewsModel;
import com.kaixin001.util.FileUtil;

public class PhotoUploadTask extends KXUploadTask
{
  private static final String FROM_WEBPAGE = "from_webpage";
  private String mAlbumId = null;
  private String mAlbumName = null;
  private int mAngleDegree = 0;
  private String mFilePath = null;
  private String mImgIndex = null;

  static
  {
    if (!PhotoUploadTask.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public PhotoUploadTask(Context paramContext)
  {
    super(paramContext);
  }

  public void deleteDraft()
  {
    if (this.mFilePath != null)
      FileUtil.deleteCacheFile(this.mFilePath);
  }

  public void doCancel()
  {
    UploadPhotoEngine.getInstance().cancel();
  }

  public boolean doUpload()
  {
    boolean bool2;
    try
    {
      ExifInterface localExifInterface = new ExifInterface(this.mFilePath);
      int i = localExifInterface.getAttributeInt("Orientation", 1);
      if ((i == 6) || (i == 3) || (i == 8) || (i == 0))
      {
        localExifInterface.setAttribute("Orientation", "1");
        localExifInterface.saveAttributes();
      }
      boolean bool1 = this.mContext.getApplicationContext().getSharedPreferences("from_webpage", 0).getBoolean("fromwebpage", false);
      bool2 = UploadPhotoEngine.getInstance().doUploadPhoto(getContext(), getTitle(), this.mFilePath, null, this.mAlbumId, getLocationName(), getLatitude(), getLongitude(), this.mAlbumName, this, bool1);
      if (bool2)
      {
        setAlbumId(UploadPhotoEngine.getInstance().getThumbnailUri());
        setImageIndex(UploadPhotoEngine.getInstance().getLargeUri());
        NewsModel.getMyHomeModel().setFirstRefresh(true);
        return bool2;
      }
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
      UploadTaskListEngine.getInstance().broadcastMessage(10003, 0, this);
    }
    return bool2;
  }

  public String getAlbumId()
  {
    return this.mAlbumId;
  }

  public String getImageIndex()
  {
    return this.mImgIndex;
  }

  public void initPhotoUploadTask(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    setTaskType(2);
    setLocationName(paramString6);
    setLatitude(paramString4);
    setLongitude(paramString5);
    setTitle(paramString1);
    this.mFilePath = paramString2;
    this.mAlbumId = paramString3;
    this.mAlbumName = paramString7;
  }

  public void initUploadTask(UpLoadTaskListDBAdapter.TaskParameters paramTaskParameters)
  {
    assert (paramTaskParameters != null);
    super.initUploadTask(paramTaskParameters);
    setTitle(paramTaskParameters.mData1);
    this.mFilePath = paramTaskParameters.mData2;
    this.mAlbumId = paramTaskParameters.mData18;
    this.mImgIndex = paramTaskParameters.mData17;
    this.mAlbumName = paramTaskParameters.mData19;
  }

  public void setAlbumId(String paramString)
  {
    this.mAlbumId = paramString;
  }

  public void setAngleDegree(int paramInt)
  {
    this.mAngleDegree = paramInt;
  }

  public void setImageIndex(String paramString)
  {
    this.mImgIndex = paramString;
  }

  public ContentValues toContentValues()
  {
    ContentValues localContentValues = super.toContentValues();
    localContentValues.put("DATA1", getTitle());
    localContentValues.put("DATA2", this.mFilePath);
    localContentValues.put("DATA17", getImageIndex());
    localContentValues.put("DATA18", getAlbumId());
    return localContentValues;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.PhotoUploadTask
 * JD-Core Version:    0.6.0
 */