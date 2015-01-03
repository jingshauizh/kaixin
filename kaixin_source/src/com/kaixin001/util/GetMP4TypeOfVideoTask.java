package com.kaixin001.util;

import android.content.Context;
import android.os.AsyncTask;
import com.kaixin001.engine.GetMP4VideoEngine;
import org.json.JSONException;

public class GetMP4TypeOfVideoTask extends AsyncTask<String, Void, Boolean>
{
  Context mCtx = null;
  String msName = null;
  String msType = null;
  String msUrl = null;

  protected Boolean doInBackground(String[] paramArrayOfString)
  {
    try
    {
      if (paramArrayOfString.length >= 3)
      {
        this.msUrl = paramArrayOfString[0];
        this.msType = paramArrayOfString[1];
        this.msName = paramArrayOfString[2];
        GetMP4VideoEngine.getInstance().postVideoMP4Request(this.mCtx, this.msUrl);
        return Boolean.valueOf(true);
      }
      Boolean localBoolean = Boolean.valueOf(false);
      return localBoolean;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return null;
  }

  protected void onPostExecute(Boolean paramBoolean)
  {
    if (paramBoolean == null);
    while (true)
    {
      return;
      try
      {
        if (!paramBoolean.booleanValue())
          continue;
        String str = GetMP4VideoEngine.getInstance().getVideoUrl();
        if ((str == null) || (!str.endsWith(".mp4")))
          break;
        IntentUtil.showVideo(this.mCtx, str, "-204", this.msName);
        return;
      }
      catch (Exception localException)
      {
        KXLog.e("IntentUtil", "onPostExecute", localException);
        return;
      }
    }
    IntentUtil.showVideo(this.mCtx, this.msUrl, this.msType, this.msName);
  }

  public void setContext(Context paramContext)
  {
    this.mCtx = paramContext;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.GetMP4TypeOfVideoTask
 * JD-Core Version:    0.6.0
 */