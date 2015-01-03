package com.kaixin001.businesslogic;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.Toast;
import com.kaixin001.engine.AlbumPhotoEngine;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.model.User;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;

public class ShowPhoto
{
  String albumId;
  String albumPicnum;
  String albumTitle;
  DialogInterface.OnCancelListener cancelGetAlbumListener;
  DialogInterface.OnClickListener checkPasswordListener;
  String fname;
  String fuid;
  GetAlbumTask getAlbumTask;
  boolean isAlbum;
  boolean isAlbumContainer;
  Activity mContext;
  AlertDialog mDialogInputPassword;
  EditText mEditVerify;
  BaseFragment mFragment;
  ProgressDialog mProgressDialog;
  String password;
  private int picIndex;
  private String picTitle;

  public ShowPhoto(Activity paramActivity, BaseFragment paramBaseFragment, boolean paramBoolean)
  {
    this.mContext = paramActivity;
    this.mFragment = paramBaseFragment;
    this.isAlbumContainer = paramBoolean;
  }

  private void dismissDialog(Dialog paramDialog)
  {
    if (paramDialog != null)
      paramDialog.dismiss();
  }

  private void init()
  {
    this.mEditVerify = new EditText(this.mContext);
    this.mEditVerify.setRawInputType(129);
    this.mEditVerify.setTransformationMethod(PasswordTransformationMethod.getInstance());
    this.mEditVerify.postInvalidate();
    this.checkPasswordListener = new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        String str = ShowPhoto.this.mEditVerify.getText().toString().trim();
        if (TextUtils.isEmpty(str))
        {
          Toast.makeText(ShowPhoto.this.mContext, 2131427482, 0).show();
          return;
        }
        ShowPhoto.this.dismissDialog(ShowPhoto.this.mDialogInputPassword);
        ShowPhoto.this.password = str;
        ShowPhoto.this.getAlbumTask = new ShowPhoto.GetAlbumTask(ShowPhoto.this, null);
        ShowPhoto.this.getAlbumTask.execute(new String[] { str });
      }
    };
    this.cancelGetAlbumListener = new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramDialogInterface)
      {
        AlbumPhotoEngine.getInstance().cancel();
        ShowPhoto.this.getAlbumTask.cancel(true);
      }
    };
    this.mDialogInputPassword = new AlertDialog.Builder(this.mContext).setMessage(2131427482).setTitle(2131427329).setView(this.mEditVerify).setPositiveButton(2131427381, this.checkPasswordListener).setNegativeButton(2131427587, null).create();
    this.mProgressDialog = new ProgressDialog(this.mContext);
    this.mProgressDialog.setTitle("");
    this.mProgressDialog.setMessage(this.mContext.getResources().getText(2131427483));
    this.mProgressDialog.setIndeterminate(true);
    this.mProgressDialog.setCancelable(true);
    this.mProgressDialog.setOnCancelListener(this.cancelGetAlbumListener);
  }

  public void showAlbum(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    this.isAlbum = true;
    this.albumId = paramString1;
    this.albumTitle = paramString2;
    this.fuid = paramString3;
    this.fname = paramString4;
    this.albumPicnum = paramString5;
    init();
    if (("2".equals(paramString6)) && (!User.getInstance().getUID().equals(paramString3)))
    {
      this.mEditVerify.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
      this.mEditVerify.setMaxLines(3);
      this.mEditVerify.setText("");
      this.mDialogInputPassword.show();
      return;
    }
    IntentUtil.showViewAlbumNotFromViewPhoto(this.mContext, this.mFragment, paramString1, paramString2, paramString3, paramString4, paramString5, "");
  }

  public void showPhoto(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3, String paramString4)
  {
    this.isAlbum = false;
    this.picIndex = paramInt2;
    this.picTitle = paramString3;
    this.albumId = paramString1;
    this.fuid = paramString2;
    init();
    if (("2".equals(paramString4)) && (!User.getInstance().getUID().equals(paramString2)))
    {
      this.mEditVerify.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
      this.mEditVerify.setMaxLines(3);
      this.mEditVerify.setText("");
      this.mDialogInputPassword.show();
      return;
    }
    IntentUtil.showViewPhotoActivity(this.mContext, this.mFragment, paramInt2, paramString2, paramString3, paramString1, paramInt1, "", this.isAlbumContainer);
  }

  private class GetAlbumTask extends AsyncTask<String, Void, Integer>
  {
    private static final String TAG = "GetAlbumTask";

    private GetAlbumTask()
    {
    }

    protected Integer doInBackground(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
        return Integer.valueOf(-1);
      String str = paramArrayOfString[0];
      try
      {
        Integer localInteger = Integer.valueOf(AlbumPhotoEngine.getInstance().getAlbumPhotoList(ShowPhoto.this.mContext, ShowPhoto.this.albumId, ShowPhoto.this.fuid, str, 0, 24));
        return localInteger;
      }
      catch (Exception localException)
      {
        KXLog.e("GetAlbumTask", "doInBackground", localException);
      }
      return Integer.valueOf(0);
    }

    protected void onPostExecute(Integer paramInteger)
    {
      try
      {
        ShowPhoto.this.dismissDialog(ShowPhoto.this.mProgressDialog);
        if (paramInteger.intValue() == 1)
        {
          if (ShowPhoto.this.isAlbum)
          {
            IntentUtil.showViewAlbumNotFromViewPhoto(ShowPhoto.this.mContext, ShowPhoto.this.mFragment, ShowPhoto.this.albumId, ShowPhoto.this.albumTitle, ShowPhoto.this.fuid, ShowPhoto.this.fname, ShowPhoto.this.albumPicnum, ShowPhoto.this.password);
            return;
          }
          IntentUtil.showViewPhotoActivity(ShowPhoto.this.mContext, ShowPhoto.this.mFragment, ShowPhoto.this.picIndex, ShowPhoto.this.fuid, ShowPhoto.this.picTitle, ShowPhoto.this.albumId, 2, ShowPhoto.this.password, ShowPhoto.this.isAlbumContainer);
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("GetAlbumTask", "onPostExecute", localException);
        return;
      }
      Toast.makeText(ShowPhoto.this.mContext, 2131427484, 0).show();
    }

    protected void onPreExecute()
    {
      ShowPhoto.this.mProgressDialog.show();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.businesslogic.ShowPhoto
 * JD-Core Version:    0.6.0
 */