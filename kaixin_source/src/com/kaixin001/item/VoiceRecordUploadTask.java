package com.kaixin001.item;

import android.content.Context;
import com.kaixin001.engine.RecordEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.engine.WeiboEngine;
import com.kaixin001.model.NewsModel;
import com.kaixin001.model.User;

public class VoiceRecordUploadTask extends RecordUploadTask
{
  private String audioFormat;
  private String audioLength;
  private String uploadAudio;

  public VoiceRecordUploadTask(Context paramContext)
  {
    super(paramContext);
  }

  public boolean doUpload()
  {
    int i = 0;
    try
    {
      if (getTaskType() == 0)
      {
        int k = WeiboEngine.getInstance().postWeibo(getContext(), getSourceId(), getTitle(), getLocationName(), this);
        if (k != 1)
        {
          UploadTaskListEngine.getInstance().broadcastMessage(10003, k, this);
          return false;
        }
        setRecordId(WeiboEngine.getInstance().getRetRecordId());
        NewsModel.getMyHomeModel().setFirstRefresh(true);
        return true;
      }
      int j = RecordEngine.getInstance().postRecordRequest(getContext(), this, this);
      if (j == 1)
      {
        setRecordId(RecordEngine.getInstance().getRetRecordId());
        i = 1;
        NewsModel.getMyHomeModel().setFirstRefresh(true);
        if ("1".equals(getStatus()))
        {
          User.getInstance().setState(getTitle());
          return i;
        }
      }
      else
      {
        UploadTaskListEngine.getInstance().broadcastMessage(10003, j, this);
      }
      return i;
    }
    catch (SecurityErrorException localSecurityErrorException)
    {
    }
    return false;
  }

  public String getAudioFormat()
  {
    return this.audioFormat;
  }

  public String getAudioLength()
  {
    return this.audioLength;
  }

  public String getUploadAudio()
  {
    return this.uploadAudio;
  }

  public void setAudioFormat(String paramString)
  {
    this.audioFormat = paramString;
  }

  public void setAudioLength(String paramString)
  {
    this.audioLength = paramString;
  }

  public void setUploadAudio(String paramString)
  {
    this.uploadAudio = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.VoiceRecordUploadTask
 * JD-Core Version:    0.6.0
 */