package com.kaixin001.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.kaixin001.model.EditPictureModel;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.KXExifInterface;
import com.kaixin001.util.KXLog;

public class LoadPhotoTask extends AsyncTask<Void, Void, Boolean>
{
  private Context mContext;
  private DoPostExecute mDoPostExecute;
  private String mUploadFilePathName = "";

  public LoadPhotoTask(Context paramContext, DoPostExecute paramDoPostExecute)
  {
    this.mContext = paramContext;
    this.mDoPostExecute = paramDoPostExecute;
  }

  protected Boolean doInBackground(Void[] paramArrayOfVoid)
  {
    try
    {
      KXExifInterface localKXExifInterface = ImageCache.getExifInfo(EditPictureModel.getBitmapPath());
      Bitmap localBitmap = EditPictureModel.getBimap();
      this.mUploadFilePathName = ImageCache.saveBitmapToFile(this.mContext, localBitmap, localKXExifInterface, "kx_tmp_upload.jpg");
      if (TextUtils.isEmpty(this.mUploadFilePathName))
        return Boolean.valueOf(false);
    }
    catch (Exception localException)
    {
      while (true)
        KXLog.e("WriteWeiboActivity", "LoadPhotoTask.doInBackground", localException);
    }
    return Boolean.valueOf(true);
  }

  protected void onPostExecute(Boolean paramBoolean)
  {
    if (this.mDoPostExecute != null)
      this.mDoPostExecute.doPostExecute(paramBoolean, this.mUploadFilePathName);
  }

  public static abstract interface DoPostExecute
  {
    public abstract void doPostExecute(Boolean paramBoolean, String paramString);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.task.LoadPhotoTask
 * JD-Core Version:    0.6.0
 */