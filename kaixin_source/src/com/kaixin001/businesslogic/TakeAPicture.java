package com.kaixin001.businesslogic;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import com.kaixin001.activity.KXActivity;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.FileUtil;
import java.io.File;

public class TakeAPicture
{
  KXActivity context;
  Handler mHandler;

  public TakeAPicture(KXActivity paramKXActivity)
  {
    this.context = paramKXActivity;
  }

  private void handleUploadPhotoOptions(int paramInt)
  {
    if (paramInt == 0)
      takePhotoFromCamera();
    do
      return;
    while (paramInt != 1);
    takePhotoFromDisk();
  }

  private void takePhotoFromCamera()
  {
    Intent localIntent = new Intent("android.media.action.IMAGE_CAPTURE");
    File localFile = new File(Environment.getExternalStorageDirectory() + "/kaixin001", "kx_upload_tmp.jpg");
    if (!FileUtil.makeEmptyFile(localFile))
      return;
    localIntent.putExtra("output", Uri.fromFile(localFile));
    this.context.startActivityForResult(localIntent, 101);
  }

  private void takePhotoFromDisk()
  {
    Intent localIntent = new Intent();
    localIntent.setType("image/*");
    localIntent.setAction("android.intent.action.GET_CONTENT");
    this.context.startActivityForResult(Intent.createChooser(localIntent, this.context.getResources().getText(2131427329)), 102);
  }

  public void showUploadPhotoOptions()
  {
    DialogUtil.showSelectListDlg(this.context, 2131427636, this.context.getResources().getStringArray(2131492894), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        TakeAPicture.this.handleUploadPhotoOptions(paramInt);
      }
    }
    , 1);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.businesslogic.TakeAPicture
 * JD-Core Version:    0.6.0
 */