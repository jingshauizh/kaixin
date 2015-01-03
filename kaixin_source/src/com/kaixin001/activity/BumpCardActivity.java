package com.kaixin001.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.kaixin001.item.UserCard;
import com.kaixin001.network.HttpConnection;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.ActivityUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.StringUtil;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class BumpCardActivity extends KXDownloadPicActivity
  implements View.OnClickListener
{
  private static final int CANCEL_CONFIRM_DIALOG = 401;
  private static final int COMMIT_EDIT_FAILED_DIALOG = 402;
  public static final int EDIT_CARD_REQUEST_CODE = 502;
  public static final String EXTRA_EDIT_CARD = "card";
  private static final String LOGTAG = "BumpCardActivity";
  private UserCard mCard;
  private EditText mEdtCity;
  private EditText mEdtEmail;
  private EditText mEdtInc;
  private EditText mEdtMoblie;
  private EditText mEdtPhone;
  private EditText mEdtPost;
  private ImageView mIvLogo;
  private TextView mTvName;
  private UpdateCardTask mUpdateTask;
  private Dialog mUpdatingDialog;

  private boolean checkNetworkAndHint()
  {
    int i = 1;
    if (HttpConnection.checkNetworkAvailable(getApplicationContext()) == i);
    while (true)
    {
      if (i == 0)
        showToast(2131427387);
      return i;
      i = 0;
    }
  }

  private void fillViewWithCard(UserCard paramUserCard)
  {
    if (paramUserCard == null);
    do
    {
      return;
      this.mTvName.setText(paramUserCard.name);
      displayPicture(this.mIvLogo, paramUserCard.logo, 2130837561);
      if (!TextUtils.isEmpty(paramUserCard.city))
        this.mEdtCity.setText(paramUserCard.city);
      if (!TextUtils.isEmpty(paramUserCard.company))
        this.mEdtInc.setText(paramUserCard.company);
      if (!TextUtils.isEmpty(paramUserCard.post))
        this.mEdtPost.setText(paramUserCard.post);
      if (!TextUtils.isEmpty(paramUserCard.phone))
        this.mEdtPhone.setText(paramUserCard.phone);
      if (TextUtils.isEmpty(paramUserCard.mobile))
        continue;
      this.mEdtMoblie.setText(paramUserCard.mobile);
    }
    while (TextUtils.isEmpty(paramUserCard.email));
    this.mEdtEmail.setText(paramUserCard.email);
  }

  private void initTitle()
  {
    ImageView localImageView = (ImageView)findViewById(2131362928);
    localImageView.setImageResource(2130839272);
    localImageView.setOnClickListener(this);
    localImageView.setVisibility(0);
    ((ImageView)findViewById(2131362914)).setOnClickListener(this);
    findViewById(2131362919).setVisibility(8);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setVisibility(0);
    localTextView.setText(2131428099);
  }

  private void initViews()
  {
    initTitle();
    this.mIvLogo = ((ImageView)findViewById(2131361866));
    this.mTvName = ((TextView)findViewById(2131361867));
    this.mEdtCity = ((EditText)findViewById(2131361868));
    this.mEdtInc = ((EditText)findViewById(2131361869));
    this.mEdtPost = ((EditText)findViewById(2131361870));
    this.mEdtPhone = ((EditText)findViewById(2131361871));
    this.mEdtMoblie = ((EditText)findViewById(2131361872));
    this.mEdtEmail = ((EditText)findViewById(2131361873));
  }

  public static int launchForEditResult(Activity paramActivity, UserCard paramUserCard)
  {
    Intent localIntent = new Intent(paramActivity, BumpCardActivity.class);
    localIntent.putExtra("card", paramUserCard);
    paramActivity.startActivityForResult(localIntent, 502);
    return 502;
  }

  private void succResult()
  {
    Intent localIntent = new Intent();
    localIntent.putExtra("card", this.mCard);
    setResult(-1, localIntent);
    finish();
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
    case 2131362928:
      do
      {
        return;
        ActivityUtil.hideInputMethod(this);
      }
      while (!checkNetworkAndHint());
      if (this.mCard == null)
        this.mCard = new UserCard();
      String str = this.mEdtEmail.getEditableText().toString().trim();
      if ((!TextUtils.isEmpty(str)) && (!StringUtil.isEmail(str)))
      {
        this.mEdtEmail.setSelection(0, this.mEdtEmail.length());
        showToast(2131428101);
        return;
      }
      this.mCard.city = this.mEdtCity.getEditableText().toString().trim();
      this.mCard.company = this.mEdtInc.getEditableText().toString().trim();
      this.mCard.post = this.mEdtPost.getEditableText().toString().trim();
      this.mCard.phone = this.mEdtPhone.getEditableText().toString().trim();
      this.mCard.mobile = this.mEdtMoblie.getEditableText().toString().trim();
      this.mCard.email = this.mEdtEmail.getEditableText().toString().trim();
      this.mUpdateTask = new UpdateCardTask(null);
      UpdateCardTask localUpdateCardTask = this.mUpdateTask;
      UserCard[] arrayOfUserCard = new UserCard[1];
      arrayOfUserCard[0] = this.mCard;
      localUpdateCardTask.execute(arrayOfUserCard);
      return;
    case 2131362914:
    }
    showDialog(401);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903057);
    initViews();
    this.mCard = ((UserCard)getIntent().getParcelableExtra("card"));
    fillViewWithCard(this.mCard);
  }

  protected Dialog onCreateDialog(int paramInt)
  {
    Dialog localDialog;
    if (paramInt == 401)
      localDialog = DialogUtil.showMsgDlgStd(this, 2131427329, 2131427451, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          BumpCardActivity.this.succResult();
        }
      });
    do
    {
      return localDialog;
      localDialog = null;
    }
    while (paramInt != 402);
    return DialogUtil.createAlertDialog(this, 2131428091, null);
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      showDialog(401);
      return true;
    }
    return false;
  }

  private class UpdateCardTask extends AsyncTask<UserCard, Void, Integer>
  {
    private UpdateCardTask()
    {
    }

    protected Integer doInBackground(UserCard[] paramArrayOfUserCard)
    {
      if ((paramArrayOfUserCard == null) || (paramArrayOfUserCard.length == 0))
        return Integer.valueOf(0);
      UserCard localUserCard = paramArrayOfUserCard[0];
      HashMap localHashMap = new HashMap(6);
      String str1;
      if (localUserCard.city == null)
        str1 = "";
      while (true)
      {
        localHashMap.put("city", str1);
        String str2;
        label59: String str3;
        label79: String str4;
        label99: String str5;
        label119: String str6;
        label139: String str7;
        HttpProxy localHttpProxy;
        if (localUserCard.company == null)
        {
          str2 = "";
          localHashMap.put("com", str2);
          if (localUserCard.post != null)
            break label238;
          str3 = "";
          localHashMap.put("title", str3);
          if (localUserCard.phone != null)
            break label247;
          str4 = "";
          localHashMap.put("phone", str4);
          if (localUserCard.mobile != null)
            break label256;
          str5 = "";
          localHashMap.put("mobile", str5);
          if (localUserCard.email != null)
            break label265;
          str6 = "";
          localHashMap.put("email", str6);
          str7 = Protocol.getInstance().makeUpdateUserCardRequest(localHashMap);
          localHttpProxy = new HttpProxy(BumpCardActivity.this);
        }
        try
        {
          String str9 = localHttpProxy.httpGet(str7, null, null);
          str8 = str9;
          if (TextUtils.isEmpty(str8))
            break;
        }
        catch (Exception localException)
        {
          try
          {
            if (new JSONObject(str8).getInt("ret") != 1)
              break;
            Integer localInteger = Integer.valueOf(1);
            return localInteger;
            str1 = localUserCard.city;
            continue;
            str2 = localUserCard.company;
            break label59;
            label238: str3 = localUserCard.post;
            break label79;
            label247: str4 = localUserCard.phone;
            break label99;
            label256: str5 = localUserCard.mobile;
            break label119;
            label265: str6 = localUserCard.email;
            break label139;
            localException = localException;
            KXLog.e("BumpCardActivity", "UpdateCardTask error", localException);
            String str8 = null;
          }
          catch (JSONException localJSONException)
          {
            KXLog.e("BumpCardActivity", "UpdateCardTask error", localJSONException);
            return Integer.valueOf(-2);
          }
        }
      }
      return Integer.valueOf(-1);
    }

    protected void onPostExecute(Integer paramInteger)
    {
      super.onPostExecute(paramInteger);
      if ((BumpCardActivity.this.mUpdatingDialog != null) && (BumpCardActivity.this.mUpdatingDialog.isShowing()))
        BumpCardActivity.this.mUpdatingDialog.dismiss();
      if (paramInteger.intValue() == 1)
      {
        BumpCardActivity.this.succResult();
        return;
      }
      BumpCardActivity.this.showDialog(402);
    }

    protected void onPreExecute()
    {
      ActivityUtil.hideInputMethod(BumpCardActivity.this);
      BumpCardActivity.this.mUpdatingDialog = ProgressDialog.show(BumpCardActivity.this, "", BumpCardActivity.this.getResources().getText(2131428100), true, true, new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramDialogInterface)
        {
          if ((BumpCardActivity.this.mUpdateTask != null) && (BumpCardActivity.this.mUpdateTask.isCancelled()))
            BumpCardActivity.this.mUpdateTask.cancel(true);
        }
      });
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.BumpCardActivity
 * JD-Core Version:    0.6.0
 */