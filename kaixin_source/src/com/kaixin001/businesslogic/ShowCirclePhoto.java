package com.kaixin001.businesslogic;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.widget.Toast;
import com.kaixin001.engine.AlbumPhotoEngine;
import com.kaixin001.engine.CirclePhotoDetailEngine;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.item.PhotoItem;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;

public class ShowCirclePhoto
{
  DialogInterface.OnCancelListener cancelGetCirclePhotoDetailListener;
  ArrayList<PhotoItem> container;
  GetCirclePhotoDetailTask getCirclePhotoDetailTask;
  String gid;
  String gname;
  Activity mContext;
  BaseFragment mFragment;
  ProgressDialog mProgressDialog;
  String pid;
  String tid;

  public ShowCirclePhoto(Activity paramActivity, BaseFragment paramBaseFragment)
  {
    this.mContext = paramActivity;
    this.mFragment = paramBaseFragment;
  }

  private void dismissDialog(Dialog paramDialog)
  {
    if (paramDialog != null)
      paramDialog.dismiss();
  }

  private void init()
  {
    this.cancelGetCirclePhotoDetailListener = new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramDialogInterface)
      {
        AlbumPhotoEngine.getInstance().cancel();
        ShowCirclePhoto.this.getCirclePhotoDetailTask.cancel(true);
      }
    };
    this.mProgressDialog = new ProgressDialog(this.mContext);
    this.mProgressDialog.setTitle("");
    this.mProgressDialog.setMessage(this.mContext.getResources().getText(2131427546));
    this.mProgressDialog.setIndeterminate(true);
    this.mProgressDialog.setCancelable(true);
    this.mProgressDialog.setOnCancelListener(this.cancelGetCirclePhotoDetailListener);
    this.container = new ArrayList();
  }

  public void showPhoto(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.tid = paramString1;
    this.gid = paramString2;
    this.pid = paramString3;
    this.gname = paramString4;
    init();
    this.getCirclePhotoDetailTask = new GetCirclePhotoDetailTask(null);
    this.getCirclePhotoDetailTask.execute(null);
  }

  private class GetCirclePhotoDetailTask extends AsyncTask<String, Void, Integer>
  {
    private static final String TAG = "GetCirclePhotoDetailTask";

    private GetCirclePhotoDetailTask()
    {
    }

    protected Integer doInBackground(String[] paramArrayOfString)
    {
      try
      {
        Integer localInteger = Integer.valueOf(CirclePhotoDetailEngine.getInstance().doGetCirclePhotoDetail(ShowCirclePhoto.this.mContext, ShowCirclePhoto.this.tid, ShowCirclePhoto.this.gid, ShowCirclePhoto.this.pid, ShowCirclePhoto.this.container));
        return localInteger;
      }
      catch (Exception localException)
      {
        KXLog.e("GetCirclePhotoDetailTask", "doInBackground", localException);
      }
      return Integer.valueOf(0);
    }

    protected void onPostExecute(Integer paramInteger)
    {
      try
      {
        ShowCirclePhoto.this.dismissDialog(ShowCirclePhoto.this.mProgressDialog);
        if (paramInteger.intValue() == 1)
        {
          PhotoItem localPhotoItem = (PhotoItem)ShowCirclePhoto.this.container.get(0);
          IntentUtil.showViewPhotoActivity4Circle(ShowCirclePhoto.this.mContext, ShowCirclePhoto.this.mFragment, localPhotoItem.index, localPhotoItem.title, localPhotoItem.albumId, ShowCirclePhoto.this.gid, ShowCirclePhoto.this.gname, false);
          return;
        }
        Toast.makeText(ShowCirclePhoto.this.mContext, 2131427484, 0).show();
        return;
      }
      catch (Exception localException)
      {
        KXLog.e("GetCirclePhotoDetailTask", "onPostExecute", localException);
      }
    }

    protected void onPreExecute()
    {
      ShowCirclePhoto.this.mProgressDialog.show();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.businesslogic.ShowCirclePhoto
 * JD-Core Version:    0.6.0
 */