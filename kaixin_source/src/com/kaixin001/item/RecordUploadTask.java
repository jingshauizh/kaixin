package com.kaixin001.item;

import android.content.ContentValues;
import android.content.Context;
import android.os.Handler;
import com.kaixin001.db.UpLoadTaskListDBAdapter.TaskParameters;
import com.kaixin001.engine.RecordEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.engine.WeiboEngine;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.model.NewsModel;
import com.kaixin001.model.User;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.KXLog;

public class RecordUploadTask extends KXUploadTask
{
  private String mImageFileName = null;
  private String mLargeImage = null;
  private String mPrivacy = null;
  private String mRecordID = null;
  private String mSource = null;
  private String mSourceID = null;
  private String mStatus = null;
  private String mThumbnail = null;

  static
  {
    if (!RecordUploadTask.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public RecordUploadTask(Context paramContext)
  {
    super(paramContext);
  }

  public void deleteDraft()
  {
    if (this.mImageFileName != null)
      FileUtil.deleteCacheFile(this.mImageFileName);
  }

  public void doCancel()
  {
    RecordEngine.getInstance().cancel();
  }

  public boolean doUpload()
  {
    int i = 0;
    try
    {
      if (getTaskType() == 0)
      {
        int m = WeiboEngine.getInstance().postWeibo(getContext(), getSourceId(), getTitle(), getLocationName(), this);
        if (m != 1)
          UploadTaskListEngine.getInstance().broadcastMessage(10003, m, this);
        while (true)
        {
          BaseFragment.getBaseFragment().getHandler().sendEmptyMessageDelayed(1, 200L);
          return i;
          this.mRecordID = WeiboEngine.getInstance().getRetRecordId();
          i = 1;
          NewsModel.getMyHomeModel().setFirstRefresh(true);
        }
      }
      int j = RecordEngine.getInstance().postRecordRequest(getContext(), this.mPrivacy, getTitle(), getLocationName(), getLatitude(), getLongitude(), this.mImageFileName, this.mStatus, this);
      int k;
      if (j == 1)
      {
        this.mRecordID = RecordEngine.getInstance().getRetRecordId();
        k = 1;
        deleteDraft();
        NewsModel.getMyHomeModel().setFirstRefresh(true);
        if (this.mStatus.equals("1"))
          User.getInstance().setState(getTitle());
      }
      while (true)
      {
        KXLog.d("levelUpgradeTag", "________________handler invoked");
        BaseFragment.getBaseFragment().getHandler().sendEmptyMessageDelayed(1, 200L);
        return k;
        UploadTaskListEngine.getInstance().broadcastMessage(10003, j, this);
        k = 0;
      }
    }
    catch (SecurityErrorException localSecurityErrorException)
    {
    }
    return false;
  }

  public String getImageFileName()
  {
    return this.mImageFileName;
  }

  public String getLargeImage()
  {
    return this.mLargeImage;
  }

  public String getPrivacy()
  {
    return this.mPrivacy;
  }

  public String getRecordId()
  {
    return this.mRecordID;
  }

  public String getSource()
  {
    return this.mSource;
  }

  public String getSourceId()
  {
    return this.mSourceID;
  }

  public String getStatus()
  {
    return this.mStatus;
  }

  public String getThumbnail()
  {
    return this.mThumbnail;
  }

  public void initRecordUploadTask(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, int paramInt)
  {
    if (paramInt == 0)
      setTaskType(0);
    while (true)
    {
      setLocationName(paramString7);
      setLatitude(paramString5);
      setLongitude(paramString6);
      this.mPrivacy = paramString1;
      this.mImageFileName = paramString3;
      this.mStatus = paramString4;
      this.mSourceID = paramString8;
      setTitle(paramString2);
      return;
      setTaskType(1);
    }
  }

  public void initUploadTask(UpLoadTaskListDBAdapter.TaskParameters paramTaskParameters)
  {
    assert (paramTaskParameters != null);
    super.initUploadTask(paramTaskParameters);
    this.mPrivacy = paramTaskParameters.mData1;
    setTitle(paramTaskParameters.mData2);
    this.mImageFileName = paramTaskParameters.mData3;
    this.mStatus = paramTaskParameters.mData4;
    this.mSource = paramTaskParameters.mData17;
    this.mSourceID = paramTaskParameters.mData18;
    this.mRecordID = paramTaskParameters.mData19;
    this.mThumbnail = paramTaskParameters.mData20;
    this.mLargeImage = paramTaskParameters.mData21;
  }

  public void setPrivacy(String paramString)
  {
    this.mPrivacy = paramString;
  }

  public void setRecordId(String paramString)
  {
    this.mRecordID = paramString;
  }

  public void setSource(String paramString)
  {
    this.mSource = paramString;
  }

  public void setSourceId(String paramString)
  {
    this.mSourceID = paramString;
  }

  public void setThumbnail(String paramString)
  {
    this.mThumbnail = paramString;
  }

  public void setmImageFileName(String paramString)
  {
    this.mImageFileName = paramString;
  }

  public void setmLargeImage(String paramString)
  {
    this.mLargeImage = paramString;
  }

  public void setmStatus(String paramString)
  {
    this.mStatus = paramString;
  }

  public ContentValues toContentValues()
  {
    ContentValues localContentValues = super.toContentValues();
    if (this.mPrivacy != null)
      localContentValues.put("DATA1", this.mPrivacy);
    if (getTitle() != null)
      localContentValues.put("DATA2", getTitle());
    if (this.mImageFileName != null)
      localContentValues.put("DATA3", this.mImageFileName);
    if (this.mStatus != null)
      localContentValues.put("DATA4", this.mStatus);
    if (this.mSourceID != null)
      localContentValues.put("DATA18", this.mSourceID);
    if (this.mRecordID != null)
      localContentValues.put("DATA19", this.mRecordID);
    return localContentValues;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.RecordUploadTask
 * JD-Core Version:    0.6.0
 */