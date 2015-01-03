package com.kaixin001.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.SendEmailEngine;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.StringUtil;

public class PwdByEmailActivity extends Activity
  implements View.OnClickListener, TextWatcher
{
  private static final int DIALOG_ID_SENDING = 1;
  boolean bIsCanceled = false;
  private EditText btnCmEnroll;
  private ImageView btnLeft;
  private ImageView btnRight;
  private SendEmailEngine sendEmailEngine = SendEmailEngine.getInstance();
  SendEmailTask sendEmailTask;
  private TextView tvTitle;

  private void cancelSend()
  {
    if (this.sendEmailTask != null)
    {
      this.bIsCanceled = true;
      this.sendEmailEngine.cancel();
      this.sendEmailTask.cancel(true);
      this.sendEmailTask = null;
    }
  }

  private void sendEmail()
  {
    if (!StringUtil.isEmail(this.btnCmEnroll.getText().toString()))
    {
      Toast.makeText(this, "邮箱格式不正确！", 0).show();
      return;
    }
    this.sendEmailTask = new SendEmailTask(null);
    this.sendEmailTask.execute(new Void[0]);
    showDialog(1);
  }

  public void afterTextChanged(Editable paramEditable)
  {
  }

  public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131362914:
      finish();
      return;
    case 2131362928:
    }
    sendEmail();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903208);
    setTitleBar();
    this.btnCmEnroll = ((EditText)findViewById(2131362997));
    this.btnCmEnroll.addTextChangedListener(this);
    this.btnCmEnroll.setOnEditorActionListener(new TextView.OnEditorActionListener()
    {
      public boolean onEditorAction(TextView paramTextView, int paramInt, KeyEvent paramKeyEvent)
      {
        if ((PwdByEmailActivity.this.btnCmEnroll.getText().toString() != null) && (PwdByEmailActivity.this.btnCmEnroll.getText().toString() != "") && (paramKeyEvent.getAction() == 0))
        {
          PwdByEmailActivity.this.sendEmail();
          return true;
        }
        return false;
      }
    });
  }

  protected Dialog onCreateDialog(int paramInt)
  {
    if (paramInt == 1)
      return ProgressDialog.show(this, "", getResources().getText(2131428387), true, true, new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramDialogInterface)
        {
          PwdByEmailActivity.this.cancelSend();
        }
      });
    return super.onCreateDialog(paramInt);
  }

  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt3 > 0)
    {
      this.btnRight.setClickable(true);
      this.btnRight.setAlpha(220);
      return;
    }
    this.btnRight.setClickable(false);
    this.btnRight.setAlpha(100);
  }

  protected void setTitleBar()
  {
    this.btnLeft = ((ImageView)findViewById(2131362914));
    this.btnLeft.setOnClickListener(this);
    this.btnLeft.setOnClickListener(this);
    this.tvTitle = ((TextView)findViewById(2131362920));
    this.tvTitle.setText(2131428386);
    this.tvTitle.setVisibility(0);
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    this.btnRight = ((ImageView)findViewById(2131362928));
    this.btnRight.setImageResource(2130839022);
    this.btnRight.setAlpha(100);
    this.btnRight.setOnClickListener(this);
    this.btnRight.setClickable(false);
  }

  private class SendEmailTask extends AsyncTask<Void, Void, Integer>
  {
    private SendEmailTask()
    {
    }

    protected Integer doInBackground(Void[] paramArrayOfVoid)
    {
      try
      {
        String str = PwdByEmailActivity.this.btnCmEnroll.getText().toString();
        Integer localInteger = Integer.valueOf(PwdByEmailActivity.this.sendEmailEngine.sendEmail(PwdByEmailActivity.this.getApplicationContext(), str, "100015822"));
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecute(Integer paramInteger)
    {
      Context localContext = PwdByEmailActivity.this.getApplicationContext();
      if (paramInteger == null)
        PwdByEmailActivity.this.finish();
      while (true)
      {
        return;
        try
        {
          PwdByEmailActivity.this.dismissDialog(1);
          if (PwdByEmailActivity.this.bIsCanceled)
            continue;
          if (paramInteger.intValue() == 1)
            break;
          Toast.makeText(localContext, 2131428418, 0).show();
          return;
        }
        catch (Exception localException)
        {
          KXLog.e("SendEmailEngine", "onPostExecute", localException);
          return;
        }
      }
      Toast.makeText(localContext, 2131428417, 0).show();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.PwdByEmailActivity
 * JD-Core Version:    0.6.0
 */