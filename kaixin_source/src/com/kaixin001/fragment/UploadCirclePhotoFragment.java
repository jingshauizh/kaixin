package com.kaixin001.fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.item.CircleRecordUploadTask;
import com.kaixin001.model.EditPictureModel;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXExifInterface;
import com.kaixin001.util.KXLog;

public class UploadCirclePhotoFragment extends BaseFragment
{
  protected static final int UPLOAD_PHOTO_CAMERA = 101;
  protected static final int UPLOAD_PHOTO_GALLERY = 102;
  Button cancelButton = null;
  private String mFilePathName = "";
  private String mFileTitle = "";
  private String mGid = "";
  private boolean mIsUploading = false;
  private LoadPhotoTask mLoadPhotoTask = null;
  Button mUploadButton = null;
  private String mUploadFilePathName = "";

  private void doCancelUploadPhoto()
  {
    DialogUtil.showKXAlertDialog(getActivity(), 2131427451, 2131427382, 2131427383, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        UploadCirclePhotoFragment.this.finish();
      }
    }
    , null);
  }

  private void doUploadPhoto()
  {
    if ((this.mIsUploading) || (this.mFilePathName.length() <= 0) || (!super.checkNetworkAndHint(true)))
      return;
    this.mFileTitle = ((EditText)findViewById(2131363390)).getText().toString();
    if (TextUtils.isEmpty(this.mFileTitle))
      this.mFileTitle = getResources().getString(2131427396);
    hideInputMethod();
    if ((this.mFileTitle.length() == 0) || (this.mFileTitle.equals(getResources().getString(2131427642))))
      this.mFileTitle = getResources().getString(2131427648);
    if (this.mUploadFilePathName != null)
    {
      String str = FileUtil.getDynamicFilePath(this.mUploadFilePathName);
      FileUtil.renameCachePath(this.mUploadFilePathName, str);
      this.mUploadFilePathName = str;
    }
    CircleRecordUploadTask localCircleRecordUploadTask = new CircleRecordUploadTask(getActivity().getApplicationContext());
    localCircleRecordUploadTask.initCircleRecordUploadTask(this.mFileTitle, this.mUploadFilePathName, "2", this.mGid, 10);
    UploadTaskListEngine.getInstance().addUploadTask(localCircleRecordUploadTask);
    Toast.makeText(getActivity().getApplication(), 2131428307, 0).show();
    this.mUploadFilePathName = null;
    finish();
  }

  private void enableUploadButton(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mUploadButton.setEnabled(true);
      this.mUploadButton.setTextColor(getResources().getColor(2130839390));
      return;
    }
    this.mUploadButton.setTextColor(getResources().getColor(2130839395));
    this.mUploadButton.setEnabled(false);
  }

  private void hideInputMethod()
  {
    View localView = getActivity().getCurrentFocus();
    if (localView != null)
      ((InputMethodManager)getActivity().getSystemService("input_method")).hideSoftInputFromWindow(localView.getWindowToken(), 2);
  }

  private void setBottomLayoutState(int paramInt)
  {
    View localView = findViewById(2131363390);
    switch (paramInt)
    {
    default:
      return;
    case 0:
      localView.setVisibility(0);
      this.mUploadButton.setTextColor(getResources().getColor(2130839385));
      this.mUploadButton.setEnabled(true);
      return;
    case 1:
    }
    localView.setVisibility(8);
    this.mUploadButton.setTextColor(getResources().getColor(2130839395));
    this.mUploadButton.setEnabled(false);
  }

  private void showLoadPreviewProgressBar(boolean paramBoolean)
  {
    View localView = findViewById(2131363402);
    if (paramBoolean)
    {
      localView.setVisibility(0);
      return;
    }
    localView.setVisibility(8);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (CrashRecoverUtil.isCrashBefore())
    {
      CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
      IntentUtil.returnToDesktop(getActivity());
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903282, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if (this.mLoadPhotoTask != null)
      this.mLoadPhotoTask.cancel(true);
    super.onDestroy();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    ((EditText)findViewById(2131363390)).setText(2131427396);
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      String str = localBundle.getString("gid");
      if (str != null)
        this.mGid = str;
    }
    this.cancelButton = ((Button)findViewById(2131363405));
    this.cancelButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        UploadCirclePhotoFragment.this.doCancelUploadPhoto();
      }
    });
    this.mUploadButton = ((Button)findViewById(2131363404));
    this.mUploadButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        UploadCirclePhotoFragment.this.doUploadPhoto();
      }
    });
    this.mFilePathName = EditPictureModel.getBitmapPath();
    if (this.mFilePathName.length() > 0)
    {
      showLoadPreviewProgressBar(true);
      enableUploadButton(false);
      new LoadPhotoTask(null).execute(new Void[0]);
    }
  }

  public void requestFinish()
  {
    doCancelUploadPhoto();
  }

  private class LoadPhotoTask extends KXFragment.KXAsyncTask<Void, Void, Boolean>
  {
    private Bitmap mScaledBitmap = null;

    private LoadPhotoTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(Void[] paramArrayOfVoid)
    {
      try
      {
        this.mScaledBitmap = EditPictureModel.getBimap();
        KXExifInterface localKXExifInterface = ImageCache.getExifInfo(UploadCirclePhotoFragment.this.mFilePathName);
        UploadCirclePhotoFragment.this.mUploadFilePathName = ImageCache.saveBitmapToFile(UploadCirclePhotoFragment.this.getActivity(), this.mScaledBitmap, localKXExifInterface, "kx_tmp_upload.jpg");
        if (TextUtils.isEmpty(UploadCirclePhotoFragment.this.mUploadFilePathName))
          return Boolean.valueOf(false);
      }
      catch (Exception localException)
      {
        while (true)
          KXLog.e("UploadPhotoActivity", "LoadPhotoTask.doInBackground", localException);
      }
      return Boolean.valueOf(true);
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      UploadCirclePhotoFragment.this.showLoadPreviewProgressBar(false);
      UploadCirclePhotoFragment.this.setBottomLayoutState(0);
      if ((this.mScaledBitmap != null) && (paramBoolean.booleanValue()))
      {
        ((ImageView)UploadCirclePhotoFragment.this.findViewById(2131363401)).setImageBitmap(this.mScaledBitmap);
        UploadCirclePhotoFragment.this.enableUploadButton(true);
      }
      while (true)
      {
        this.mScaledBitmap = null;
        return;
        UploadCirclePhotoFragment.this.enableUploadButton(false);
        Toast.makeText(UploadCirclePhotoFragment.this.getActivity(), 2131427333, 0).show();
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.UploadCirclePhotoFragment
 * JD-Core Version:    0.6.0
 */