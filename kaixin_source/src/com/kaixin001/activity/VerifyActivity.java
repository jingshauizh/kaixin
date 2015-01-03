package com.kaixin001.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.NewFriend2Engine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.util.ActivityUtil;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.ImageDownLoaderManager;
import com.kaixin001.util.ImageDownloadCallback;
import com.kaixin001.util.ImageDownloader;
import com.kaixin001.util.KXLog;

public class VerifyActivity extends KXActivity
  implements View.OnClickListener
{
  public static final int MODE_ADD_FRIEND = 0;
  public static final int MODE_INPUT_CAPTCHA = 1;
  int PROGRESS_DIALOG = 3;
  private NewFriendTask addFriendTask;
  private ImageView btnVerifyLeft;
  private ImageView btnVerifyRight;
  public String code;
  private String fuid;
  public String imageURL;
  private ImageCache imageUtil = ImageCache.getInstance();
  private ImageDownloader imgDownloader = ImageDownloader.getInstance();
  private ImageView ivVerifyPic;
  private View mClearBtn;
  private EditText mContent;
  private int mMode = 0;
  private View mRefreshBtn;
  private View mSubmitBtn;
  public String rcode;
  private ProgressBar verifyBar;

  private void doSubmit()
  {
    ActivityUtil.hideInputMethod(this);
    String str = this.mContent.getText().toString().trim();
    if (TextUtils.isEmpty(str))
      Toast.makeText(getApplicationContext(), 2131428005, 0).show();
    do
    {
      return;
      if (this.mMode != 1)
        continue;
      Intent localIntent = new Intent();
      localIntent.putExtra("rcode", this.rcode);
      localIntent.putExtra("code", str);
      setResult(-1, localIntent);
      finish();
      return;
    }
    while (this.mMode != 0);
    this.addFriendTask = new NewFriendTask(null);
    NewFriendTask localNewFriendTask = this.addFriendTask;
    String[] arrayOfString = new String[2];
    arrayOfString[0] = str;
    arrayOfString[1] = this.rcode;
    localNewFriendTask.execute(arrayOfString);
    showDialog(this.PROGRESS_DIALOG);
  }

  private void downloadImg(String paramString)
  {
    this.imageUtil.deleteFileAndCacheImage(paramString);
    ImageDownLoaderManager.getInstance().displayPicture(this, this.ivVerifyPic, paramString, 0, new ImageDownloadCallback(paramString)
    {
      public void onImageDownloadFailed()
      {
      }

      public void onImageDownloadSuccess()
      {
        VerifyActivity.this.showVerifyImage(this.val$url);
      }

      public void onImageDownloading()
      {
      }
    });
    this.verifyBar.setVisibility(0);
    this.mRefreshBtn.setVisibility(8);
  }

  private void showVerifyImage(String paramString)
  {
    Bitmap localBitmap = this.imageUtil.createSafeImage(paramString);
    this.verifyBar.setVisibility(8);
    this.mRefreshBtn.setVisibility(0);
    this.ivVerifyPic.setImageBitmap(localBitmap);
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131362914:
      setResult(0);
      finish();
      return;
    case 2131362928:
    }
    doSubmit();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903399);
    this.mSubmitBtn = findViewById(2131363930);
    this.mSubmitBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        VerifyActivity.this.doSubmit();
      }
    });
    this.mRefreshBtn = findViewById(2131363926);
    this.mRefreshBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        VerifyActivity.this.downloadImg(VerifyActivity.this.imageURL);
      }
    });
    this.mClearBtn = findViewById(2131363929);
    this.mClearBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        VerifyActivity.this.mContent.setText("");
      }
    });
    this.mClearBtn.setVisibility(8);
    this.verifyBar = ((ProgressBar)findViewById(2131363927));
    setTitleBar();
    this.mContent = ((EditText)findViewById(2131363928));
    this.mContent.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramEditable)
      {
      }

      public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
      }

      public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
        if ((paramInt3 > 0) && (VerifyActivity.this.mContent.getText() != null) && (!VerifyActivity.this.mContent.getText().toString().equals("")))
          if (VerifyActivity.this.mClearBtn.getVisibility() != 0)
            VerifyActivity.this.mClearBtn.setVisibility(0);
        do
          return;
        while (VerifyActivity.this.mClearBtn.getVisibility() == 8);
        VerifyActivity.this.mClearBtn.setVisibility(8);
      }
    });
    this.ivVerifyPic = ((ImageView)findViewById(2131363925));
    Bundle localBundle = getIntent().getExtras();
    this.fuid = localBundle.getString("fuid");
    this.rcode = localBundle.getString("rcode");
    this.imageURL = localBundle.getString("imageURL");
    this.mMode = localBundle.getInt("mode", 0);
    downloadImg(this.imageURL);
  }

  protected Dialog onCreateDialog(int paramInt)
  {
    if (paramInt == this.PROGRESS_DIALOG)
      return ProgressDialog.show(this, "", getResources().getText(2131427599), true, true, new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramDialogInterface)
        {
        }
      });
    return super.onCreateDialog(paramInt);
  }

  protected void onPause()
  {
    ImageDownLoaderManager.getInstance().cancel(this);
    super.onPause();
  }

  protected void setTitleBar()
  {
    this.btnVerifyLeft = ((ImageView)findViewById(2131362914));
    this.btnVerifyLeft.setVisibility(0);
    this.btnVerifyLeft.setOnClickListener(this);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setText(2131428003);
    localTextView.setVisibility(0);
    this.btnVerifyRight = ((ImageView)findViewById(2131362928));
    this.btnVerifyRight.setVisibility(8);
  }

  private class NewFriendTask extends AsyncTask<String, Void, Integer>
  {
    private NewFriendTask()
    {
    }

    private boolean shouldReturn(int paramInt1, int paramInt2)
    {
      if (paramInt1 == 1);
      int i;
      do
      {
        return true;
        i = paramInt2 + 300;
        if ((paramInt1 == 0) && (i == 310))
          return false;
        if ((paramInt1 == 0) && (i == 303))
          return false;
      }
      while ((paramInt1 != 0) || (i != 316));
      return false;
    }

    protected Integer doInBackground(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length != 2))
        return null;
      String str1 = paramArrayOfString[0];
      String str2 = paramArrayOfString[1];
      try
      {
        int i = NewFriend2Engine.getInstance().addNewFriend(VerifyActivity.this.getApplicationContext(), VerifyActivity.this.fuid, str1, str2);
        if ((i == 0) && (NewFriend2Engine.getInstance().strTipMsg == null))
        {
          NewFriend2Engine.getInstance().strTipMsg = VerifyActivity.this.getString(2131427378);
          return Integer.valueOf(0);
        }
        Integer localInteger = Integer.valueOf(i);
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecute(Integer paramInteger)
    {
      VerifyActivity.this.dismissDialog(VerifyActivity.this.PROGRESS_DIALOG);
      if (paramInteger == null)
      {
        Toast.makeText(VerifyActivity.this, 2131427374, 0).show();
        return;
      }
      NewFriend2Engine localNewFriend2Engine = NewFriend2Engine.getInstance();
      try
      {
        if (shouldReturn(paramInteger.intValue(), localNewFriend2Engine.code))
        {
          Intent localIntent = new Intent();
          localIntent.putExtra("fuid", VerifyActivity.this.fuid);
          VerifyActivity.this.setResult(-1, localIntent);
          VerifyActivity.this.finish();
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("HomeActivity", "onPostExecute", localException);
        return;
      }
      Toast.makeText(VerifyActivity.this, localNewFriend2Engine.strTipMsg, 0).show();
      VerifyActivity.this.imageURL = localNewFriend2Engine.captcha_url;
      VerifyActivity.this.downloadImg(VerifyActivity.this.imageURL);
      VerifyActivity.this.rcode = localNewFriend2Engine.rcode;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.VerifyActivity
 * JD-Core Version:    0.6.0
 */