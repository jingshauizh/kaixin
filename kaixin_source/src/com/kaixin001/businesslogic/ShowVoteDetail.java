package com.kaixin001.businesslogic;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import com.kaixin001.activity.LoginActivity;
import com.kaixin001.engine.AlbumPhotoEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.VoteEngine;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.fragment.VoteDetailFragment;
import com.kaixin001.model.User;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;

public class ShowVoteDetail
{
  private DialogInterface.OnCancelListener cancelGetAlbumListener;
  GetVoteDetailTask getVoteDetailTask;
  Context mContext;
  BaseFragment mFragment;
  ProgressDialog mProgressDialog;

  public ShowVoteDetail(BaseFragment paramBaseFragment)
  {
    this.mFragment = paramBaseFragment;
    this.mContext = paramBaseFragment.getActivity();
  }

  private void dismissDialog(Dialog paramDialog)
  {
    if (paramDialog != null)
      paramDialog.dismiss();
  }

  private void init()
  {
    this.cancelGetAlbumListener = new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramDialogInterface)
      {
        AlbumPhotoEngine.getInstance().cancel();
        ShowVoteDetail.this.getVoteDetailTask.cancel(true);
      }
    };
    this.mProgressDialog = new ProgressDialog(this.mContext);
    this.mProgressDialog.setTitle("");
    this.mProgressDialog.setMessage(this.mContext.getResources().getText(2131427546));
    this.mProgressDialog.setIndeterminate(true);
    this.mProgressDialog.setCancelable(true);
    this.mProgressDialog.setOnCancelListener(this.cancelGetAlbumListener);
  }

  protected void doFinishedHandle()
  {
  }

  protected void refreshGUI()
  {
  }

  public void showVoteDetail(String paramString)
  {
    if (User.getInstance().GetLookAround())
    {
      Context localContext = this.mContext;
      String[] arrayOfString = new String[2];
      arrayOfString[0] = this.mContext.getString(2131427338);
      arrayOfString[1] = this.mContext.getString(2131427339);
      DialogUtil.showSelectListDlg(localContext, 2131427729, arrayOfString, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          switch (paramInt)
          {
          default:
            return;
          case 0:
            Intent localIntent2 = new Intent(ShowVoteDetail.this.mContext, LoginActivity.class);
            ShowVoteDetail.this.mContext.startActivity(localIntent2);
            return;
          case 1:
          }
          Intent localIntent1 = new Intent(ShowVoteDetail.this.mContext, LoginActivity.class);
          Bundle localBundle = new Bundle();
          localBundle.putInt("regist", 1);
          localIntent1.putExtras(localBundle);
          ShowVoteDetail.this.mContext.startActivity(localIntent1);
        }
      }
      , 1);
      return;
    }
    init();
    this.getVoteDetailTask = new GetVoteDetailTask();
    this.getVoteDetailTask.execute(new String[] { paramString });
  }

  public class GetVoteDetailTask extends AsyncTask<String, Void, Integer>
  {
    private static final String TAG = "GetVoteDetailTask";
    private String votoId;

    public GetVoteDetailTask()
    {
    }

    protected Integer doInBackground(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
        return Integer.valueOf(-1);
      this.votoId = paramArrayOfString[0];
      try
      {
        Integer localInteger = Integer.valueOf(VoteEngine.getInstance().doGetVoteDetail(ShowVoteDetail.this.mContext, this.votoId));
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecute(Integer paramInteger)
    {
      try
      {
        ShowVoteDetail.this.dismissDialog(ShowVoteDetail.this.mProgressDialog);
        ShowVoteDetail.this.doFinishedHandle();
        switch (paramInteger.intValue())
        {
        default:
          Toast.makeText(ShowVoteDetail.this.mContext, 2131427547, 0).show();
          return;
        case 1:
          if ((ShowVoteDetail.this.mFragment instanceof VoteDetailFragment))
          {
            ShowVoteDetail.this.refreshGUI();
            return;
          }
        case -5:
        case -3:
        case -4:
        case -7:
        case -6:
        case -1002:
        case -1001:
        }
      }
      catch (Exception localException)
      {
        KXLog.e("GetVoteDetailTask", "onPostExecute", localException);
        return;
      }
      IntentUtil.showVoteDetailActivity(ShowVoteDetail.this.mContext, this.votoId);
      return;
      Toast.makeText(ShowVoteDetail.this.mContext, 2131427848, 0).show();
      return;
      Toast.makeText(ShowVoteDetail.this.mContext, 2131427849, 0).show();
      return;
      Toast.makeText(ShowVoteDetail.this.mContext, 2131427850, 0).show();
      return;
      Toast.makeText(ShowVoteDetail.this.mContext, 2131427851, 0).show();
      return;
      Toast.makeText(ShowVoteDetail.this.mContext, 2131427852, 0).show();
      return;
      Toast.makeText(ShowVoteDetail.this.mContext, 2131427378, 0).show();
      return;
      Toast.makeText(ShowVoteDetail.this.mContext, 2131427379, 0).show();
    }

    protected void onPreExecute()
    {
      ShowVoteDetail.this.mProgressDialog.show();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.businesslogic.ShowVoteDetail
 * JD-Core Version:    0.6.0
 */