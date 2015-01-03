package com.kaixin001.businesslogic;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.kaixin001.activity.VerifyActivity;
import com.kaixin001.engine.AddVerifyEngine;
import com.kaixin001.engine.NewFriend2Engine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.util.KXLog;
import java.util.HashMap;

public class AddFriend
{
  public static final int CANCLE_ADD_FRIEND = 114;
  public static final int REQUEST_CAPTCHA = 99;
  public static final int RUSULT_ADD_FRIEND = 113;
  HashMap<String, AddFriendResult> addFriendApplyChanges;
  private AddVerifyTask addVerifyTask;
  private DialogInterface.OnCancelListener cancelAddFriendListener;
  Activity context;
  private String fuid;
  BaseFragment mFragment;
  Handler mHandler;
  ProgressDialog mProgressDialog;
  private NewFriendTask newFriendTask;

  public AddFriend(BaseFragment paramBaseFragment, Handler paramHandler)
  {
    this.mFragment = paramBaseFragment;
    this.context = paramBaseFragment.getActivity();
    this.mHandler = paramHandler;
  }

  private void dismissDialog(Dialog paramDialog)
  {
    if (paramDialog != null)
      paramDialog.dismiss();
  }

  private void init()
  {
    this.cancelAddFriendListener = new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramDialogInterface)
      {
        NewFriend2Engine.getInstance().cancel();
        AddFriend.this.newFriendTask.cancel(true);
        Message localMessage = Message.obtain();
        localMessage.what = 114;
        localMessage.obj = AddFriend.this.fuid;
        AddFriend.this.mHandler.sendMessage(localMessage);
      }
    };
    this.mProgressDialog = new ProgressDialog(this.context);
    this.mProgressDialog.setTitle("");
    this.mProgressDialog.setMessage(this.context.getResources().getText(2131427512));
    this.mProgressDialog.setIndeterminate(true);
    this.mProgressDialog.setCancelable(true);
    this.mProgressDialog.setOnCancelListener(this.cancelAddFriendListener);
  }

  public void addNewFriend(String paramString1, String paramString2)
  {
    init();
    this.fuid = paramString2;
    View localView = this.context.getCurrentFocus();
    if (localView != null)
      ((InputMethodManager)this.context.getSystemService("input_method")).hideSoftInputFromWindow(localView.getWindowToken(), 2);
    String[] arrayOfString = { paramString1, paramString2 };
    this.newFriendTask = new NewFriendTask(null);
    this.newFriendTask.execute(arrayOfString);
  }

  public void addNewFriendResult(String paramString)
  {
    NewFriend2Engine localNewFriend2Engine = NewFriend2Engine.getInstance();
    int i = localNewFriend2Engine.getRet();
    switch (i)
    {
    default:
      return;
    case 0:
      if ((localNewFriend2Engine.captcha == NewFriend2Engine.CAPTCHA) || (localNewFriend2Engine.captcha == NewFriend2Engine.CAPTCHA2))
      {
        Intent localIntent = new Intent(this.context, VerifyActivity.class);
        localIntent.putExtra("fuid", paramString);
        localIntent.putExtra("rcode", localNewFriend2Engine.rcode);
        localIntent.putExtra("imageURL", localNewFriend2Engine.captcha_url);
        this.mFragment.getActivity().startActivityForResult(localIntent, 99);
        this.addFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
        return;
      }
      Toast.makeText(this.context, localNewFriend2Engine.strTipMsg, 0).show();
      this.addFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
      return;
    case 1:
    }
    this.addFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
  }

  public void addVerify(boolean paramBoolean, String paramString)
  {
    init();
    this.addVerifyTask = new AddVerifyTask(null);
    if (paramBoolean)
    {
      String str = this.context.getString(2131427987);
      this.addVerifyTask.execute(new String[] { str, paramString });
      return;
    }
    AddVerifyTask localAddVerifyTask = this.addVerifyTask;
    String[] arrayOfString = new String[2];
    arrayOfString[1] = paramString;
    localAddVerifyTask.execute(arrayOfString);
  }

  public HashMap<String, AddFriendResult> getAddFriendApplyChanges()
  {
    return this.addFriendApplyChanges;
  }

  public void setAddFriendApplyChanges(HashMap<String, AddFriendResult> paramHashMap)
  {
    this.addFriendApplyChanges = paramHashMap;
  }

  private class AddVerifyTask extends AsyncTask<String, Void, Integer>
  {
    private String fuid;

    private AddVerifyTask()
    {
    }

    protected Integer doInBackground(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length != 2))
        return null;
      String str = paramArrayOfString[0];
      this.fuid = paramArrayOfString[1];
      try
      {
        if (AddVerifyEngine.getInstance().addVerify(AddFriend.this.context, this.fuid, str))
        {
          Integer localInteger = Integer.valueOf(1);
          return localInteger;
        }
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        return null;
      }
      return Integer.valueOf(0);
    }

    protected void onPostExecute(Integer paramInteger)
    {
      AddFriend.this.dismissDialog(AddFriend.this.mProgressDialog);
      if (paramInteger == null)
      {
        Toast.makeText(AddFriend.this.context, 2131427374, 0).show();
        return;
      }
      try
      {
        if (paramInteger.intValue() == 1)
        {
          Message localMessage = Message.obtain();
          localMessage.what = 113;
          localMessage.obj = this.fuid;
          AddFriend.this.mHandler.sendMessage(localMessage);
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("HomeActivity", "onPostExecute", localException);
        return;
      }
      Toast.makeText(AddFriend.this.context, 2131427513, 0).show();
    }

    protected void onPreExecute()
    {
      AddFriend.this.mProgressDialog.show();
    }
  }

  private class NewFriendTask extends AsyncTask<String, Void, Integer>
  {
    private String fuid;

    private NewFriendTask()
    {
    }

    protected Integer doInBackground(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length != 2))
        return null;
      String str = paramArrayOfString[0];
      this.fuid = paramArrayOfString[1];
      try
      {
        int i = NewFriend2Engine.getInstance().addNewFriend(AddFriend.this.context, this.fuid, str);
        if ((i == 0) && (NewFriend2Engine.getInstance().strTipMsg == null))
        {
          NewFriend2Engine.getInstance().strTipMsg = AddFriend.this.context.getString(2131427378);
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
      AddFriend.this.dismissDialog(AddFriend.this.mProgressDialog);
      if (paramInteger == null)
      {
        Toast.makeText(AddFriend.this.context, 2131427374, 0).show();
        return;
      }
      Message localMessage = Message.obtain();
      localMessage.what = 113;
      localMessage.obj = this.fuid;
      AddFriend.this.mHandler.sendMessage(localMessage);
    }

    protected void onPreExecute()
    {
      AddFriend.this.mProgressDialog.show();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.businesslogic.AddFriend
 * JD-Core Version:    0.6.0
 */