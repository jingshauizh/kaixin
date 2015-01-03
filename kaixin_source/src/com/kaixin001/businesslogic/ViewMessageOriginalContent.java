package com.kaixin001.businesslogic;

import android.app.Activity;
import android.app.AlertDialog.Builder;
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
import android.widget.EditText;
import android.widget.TableLayout.LayoutParams;
import android.widget.Toast;
import com.kaixin001.engine.AlbumPhotoEngine;
import com.kaixin001.engine.DiaryEngine;
import com.kaixin001.engine.RecordEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.fragment.DiaryDetailFragment;
import com.kaixin001.fragment.NewsDetailFragment;
import com.kaixin001.fragment.PreviewUploadPhotoFragment;
import com.kaixin001.fragment.RepostDetailFragment;
import com.kaixin001.fragment.ViewAlbumPhotoFragment;
import com.kaixin001.item.DetailItem;
import com.kaixin001.item.MessageDetailInfo;
import com.kaixin001.item.RecordInfo;
import com.kaixin001.item.RepItem;
import com.kaixin001.model.DiaryModel;
import com.kaixin001.model.RecordModel;
import com.kaixin001.model.User;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewMessageOriginalContent
{
  public static final String TAG = "ViewMessageOriginalContent";
  private Activity context;
  private GetAlbumTask getAlbumTask = null;
  private String mDetailFuid;
  private MessageDetailInfo mDetailInfo = null;
  private int mDetailType;
  private EditText mEditVerify;
  private String mFname;
  private BaseFragment mFragment;
  private String mFuid;
  private String[] mImgParams;
  private ProgressDialog mProgressDialog = null;
  private ProgressDialog mRecordDetailProgressDialog;

  public ViewMessageOriginalContent(Activity paramActivity, BaseFragment paramBaseFragment)
  {
    this.context = paramActivity;
    this.mFragment = paramBaseFragment;
  }

  private void getAlbumList()
  {
    String str = this.mEditVerify.getText().toString().trim();
    if (TextUtils.isEmpty(str))
    {
      DialogUtil.showKXAlertDialog(this.context, 2131427482, null);
      return;
    }
    this.getAlbumTask = new GetAlbumTask(null);
    this.getAlbumTask.execute(new String[] { str });
    this.mProgressDialog = ProgressDialog.show(this.context, "", this.context.getResources().getText(2131427483), true, true, new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramDialogInterface)
      {
        AlbumPhotoEngine.getInstance().cancel();
        ViewMessageOriginalContent.this.getAlbumTask.cancel(true);
      }
    });
  }

  private void goToAlbum()
  {
    try
    {
      JSONObject localJSONObject = this.mDetailInfo.getmJSONAlbumInfo();
      this.mImgParams = new String[4];
      this.mImgParams[0] = localJSONObject.getString("picnum");
      this.mImgParams[1] = localJSONObject.getString("title");
      this.mImgParams[2] = localJSONObject.getString("albumid");
      this.mImgParams[3] = localJSONObject.getString("privacy");
      if ((this.mImgParams[3].equals("2")) && (User.getInstance().getUID().compareTo(this.mFuid) != 0))
      {
        showInputPwd();
        return;
      }
      showAlbumView(this.mImgParams[2], this.mImgParams[1], "");
      return;
    }
    catch (JSONException localJSONException)
    {
      KXLog.e("MessageDetailActivity", localJSONException.getMessage());
    }
  }

  private void goToDiary()
  {
    if (("2".equals(this.mDetailInfo.getmPrivacy())) && (User.getInstance().getUID().compareTo(this.mFuid) != 0))
    {
      Toast.makeText(this.context, 2131427774, 0).show();
      return;
    }
    String[] arrayOfString = new String[2];
    arrayOfString[0] = this.mDetailInfo.getmOriginalId();
    if (this.mDetailType == 203)
      arrayOfString[1] = User.getInstance().getUID();
    while (true)
    {
      this.mRecordDetailProgressDialog = ProgressDialog.show(this.context, "", this.context.getResources().getText(2131427546), true, false, null);
      new GetDiaryTask(null).execute(arrayOfString);
      return;
      if (this.mDetailType != 204)
        continue;
      arrayOfString[1] = this.mDetailFuid;
    }
  }

  private void goToPhoto()
  {
    try
    {
      JSONObject localJSONObject1 = this.mDetailInfo.getmJSONPhotoInfo();
      int i = 0;
      if (localJSONObject1 != null)
      {
        JSONObject localJSONObject2 = localJSONObject1.optJSONObject("pics");
        i = 0;
        if (localJSONObject2 != null)
        {
          String str1 = localJSONObject2.optString("small");
          String str2 = localJSONObject2.optString("default");
          if (TextUtils.isEmpty(str1))
          {
            boolean bool = TextUtils.isEmpty(str2);
            i = 0;
            if (bool);
          }
          else
          {
            i = 1;
            showPreviewPhotoActivity(str1, str2);
          }
        }
      }
      if (i == 0)
        Toast.makeText(this.context, 2131427902, 0).show();
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("MessageDetailActivity", localException.getMessage());
    }
  }

  private void goToRecord()
  {
    String[] arrayOfString = new String[2];
    arrayOfString[0] = this.mDetailInfo.getmOriginalId();
    if (this.mDetailType == 203)
      arrayOfString[1] = User.getInstance().getUID();
    while (true)
    {
      this.mRecordDetailProgressDialog = ProgressDialog.show(this.context, "", this.context.getResources().getText(2131427546), true, false, null);
      new RecordDetailTask(null).execute(arrayOfString);
      return;
      if (this.mDetailType != 204)
        continue;
      arrayOfString[1] = this.mDetailFuid;
    }
  }

  private void goToRepost()
  {
    Intent localIntent = new Intent(this.context, RepostDetailFragment.class);
    Bundle localBundle = new Bundle();
    ArrayList localArrayList = new ArrayList();
    RepItem localRepItem = new RepItem();
    localRepItem.fname = this.mFname;
    localRepItem.fuid = this.mFuid;
    localRepItem.id = this.mDetailInfo.getmOriginalId();
    localArrayList.add(localRepItem);
    localBundle.putSerializable("repostList", (Serializable)localArrayList);
    localBundle.putInt("position", 0);
    localBundle.putString("cthread_cid", this.mDetailInfo.getId());
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragmentForResult(this.mFragment, localIntent, 3, 1);
  }

  private void showAlbumView(String paramString1, String paramString2, String paramString3)
  {
    IntentUtil.showViewAlbumNotFromViewPhoto(this.context, this.mFragment, paramString1, paramString2, this.mFuid, this.mFname, null, paramString3);
  }

  private void showInputPwd()
  {
    this.mEditVerify = new EditText(this.context);
    this.mEditVerify.setLayoutParams(new TableLayout.LayoutParams(-2, -2));
    new AlertDialog.Builder(this.context).setMessage(2131427482).setTitle(2131427329).setView(this.mEditVerify).setPositiveButton(2131427381, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        ViewMessageOriginalContent.this.getAlbumList();
      }
    }).setNegativeButton(2131427587, null).show();
  }

  private void showPreviewPhotoActivity(String paramString1, String paramString2)
  {
    Intent localIntent = new Intent(this.context, PreviewUploadPhotoFragment.class);
    localIntent.putExtra("small", paramString1);
    localIntent.putExtra("large", paramString2);
    localIntent.putExtra("action", "com.kaixin001.VIEW_SINGLE_PHOTO");
    AnimationUtil.startFragmentForResult(this.mFragment, localIntent, 3);
  }

  private void showViewPhotoActivity(String paramString1, String paramString2, String paramString3)
  {
    Intent localIntent = new Intent(this.context, ViewAlbumPhotoFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putInt("imgIndex", Integer.parseInt(paramString1));
    localBundle.putString("fuid", this.mFuid);
    localBundle.putString("title", paramString2);
    localBundle.putString("albumId", paramString3);
    localBundle.putString("cthread_cid", this.mDetailInfo.getId());
    localBundle.putBoolean("isFromViewAlbum", false);
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragmentForResult(this.mFragment, localIntent, 3);
  }

  public void turnToOriginalContent(MessageDetailInfo paramMessageDetailInfo, int paramInt, String paramString)
  {
    this.mDetailInfo = paramMessageDetailInfo;
    this.mDetailType = paramInt;
    this.mDetailFuid = paramString;
    String str1 = paramMessageDetailInfo.getmAppid();
    if ((!str1.equals("1018")) && (!str1.equals("2")) && (!str1.equals("1")) && (!str1.equals("1088")));
    String str2;
    do
    {
      return;
      str2 = paramMessageDetailInfo.getmAppid();
      if (paramInt == 3);
      for (String str3 = this.context.getString(2131427565); ; str3 = paramMessageDetailInfo.getmCRealName())
      {
        this.mFname = str3;
        if (paramInt == 3)
          paramString = User.getInstance().getUID();
        this.mFuid = paramString;
        if (!str2.equals("1088"))
          break;
        goToRepost();
        return;
      }
      if (str2.equals("1"))
      {
        goToPhoto();
        return;
      }
      if (str2.equals("6"))
      {
        goToAlbum();
        return;
      }
      if (!str2.equals("1018"))
        continue;
      goToRecord();
      return;
    }
    while (!str2.equals("2"));
    goToDiary();
  }

  private class GetAlbumTask extends AsyncTask<String, Void, Integer>
  {
    private String pwd;

    private GetAlbumTask()
    {
    }

    protected Integer doInBackground(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
        return Integer.valueOf(-1);
      try
      {
        this.pwd = paramArrayOfString[0];
        Integer localInteger = Integer.valueOf(AlbumPhotoEngine.getInstance().getAlbumPhotoList(ViewMessageOriginalContent.this.context, ViewMessageOriginalContent.this.mImgParams[2], ViewMessageOriginalContent.this.mFuid, this.pwd, 0, 24));
        return localInteger;
      }
      catch (Exception localException)
      {
        KXLog.e("NewsActivity", "doInBackground", localException);
      }
      return Integer.valueOf(0);
    }

    protected void onPostExecute(Integer paramInteger)
    {
      try
      {
        if (ViewMessageOriginalContent.this.mProgressDialog != null)
          ViewMessageOriginalContent.this.mProgressDialog.dismiss();
        if (paramInteger.intValue() == 1)
        {
          String str1 = ViewMessageOriginalContent.this.mDetailInfo.getmAppid();
          if (str1.equals("1"))
          {
            JSONObject localJSONObject = ViewMessageOriginalContent.this.mDetailInfo.getmJSONPhotoInfo();
            String str2 = -1 + localJSONObject.optInt("pos");
            ViewMessageOriginalContent.this.showViewPhotoActivity(str2, ViewMessageOriginalContent.this.mImgParams[1], ViewMessageOriginalContent.this.mImgParams[2]);
            return;
          }
          if (!str1.equals("6"))
            return;
          ViewMessageOriginalContent.this.showAlbumView(ViewMessageOriginalContent.this.mImgParams[0], ViewMessageOriginalContent.this.mImgParams[1], this.pwd);
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("MessageDetailActivity", "onPostExecute", localException);
        return;
      }
      Toast.makeText(ViewMessageOriginalContent.this.context, 2131427852, 0).show();
    }
  }

  private class GetDiaryTask extends AsyncTask<String, Void, Boolean>
  {
    private GetDiaryTask()
    {
    }

    protected Boolean doInBackground(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length < 2))
        return Boolean.valueOf(false);
      try
      {
        Boolean localBoolean = Boolean.valueOf(DiaryEngine.getInstance().doGetDiaryDetail(ViewMessageOriginalContent.this.context, paramArrayOfString[0], paramArrayOfString[1]));
        return localBoolean;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecute(Boolean paramBoolean)
    {
      super.onPostExecute(paramBoolean);
      ViewMessageOriginalContent.this.mRecordDetailProgressDialog.dismiss();
      if (paramBoolean.booleanValue())
      {
        Intent localIntent = new Intent(ViewMessageOriginalContent.this.context, DiaryDetailFragment.class);
        Bundle localBundle = new Bundle();
        localBundle.putString("fuid", ViewMessageOriginalContent.this.mFuid);
        localBundle.putString("fname", ViewMessageOriginalContent.this.mFname);
        localBundle.putString("did", ViewMessageOriginalContent.this.mDetailInfo.getmOriginalId());
        localBundle.putString("cthread_cid", ViewMessageOriginalContent.this.mDetailInfo.getId());
        localBundle.putString("title", ViewMessageOriginalContent.this.mDetailInfo.getAppHtml());
        localIntent.putExtras(localBundle);
        AnimationUtil.startFragmentForResult(ViewMessageOriginalContent.this.mFragment, localIntent, 3);
        return;
      }
      if (DiaryModel.getInstance().getErrorNum() == 4001)
      {
        Toast.makeText(ViewMessageOriginalContent.this.context, 2131427921, 0).show();
        return;
      }
      if (DiaryModel.getInstance().getErrorNum() == 100)
      {
        Toast.makeText(ViewMessageOriginalContent.this.context, 2131427921, 0).show();
        return;
      }
      Toast.makeText(ViewMessageOriginalContent.this.context, 2131427547, 0).show();
    }
  }

  private class RecordDetailTask extends AsyncTask<String, Void, Boolean>
  {
    private RecordDetailTask()
    {
    }

    protected Boolean doInBackground(String[] paramArrayOfString)
    {
      boolean bool1 = false;
      if (paramArrayOfString != null)
      {
        int i = paramArrayOfString.length;
        bool1 = false;
        if (i < 2);
      }
      try
      {
        boolean bool2 = RecordEngine.getInstance().doGetRecordDetailRequest(ViewMessageOriginalContent.this.context, paramArrayOfString[0], paramArrayOfString[1]);
        bool1 = bool2;
        return Boolean.valueOf(bool1);
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        while (true)
        {
          KXLog.e("ViewMessageOriginalContent", localSecurityErrorException.getMessage());
          bool1 = false;
        }
      }
    }

    protected void onPostExecute(Boolean paramBoolean)
    {
      super.onPostExecute(paramBoolean);
      ViewMessageOriginalContent.this.mRecordDetailProgressDialog.dismiss();
      if (paramBoolean.booleanValue())
      {
        RecordInfo localRecordInfo = RecordModel.getInstance().getRecordInfo();
        Intent localIntent = new Intent(ViewMessageOriginalContent.this.context, NewsDetailFragment.class);
        DetailItem localDetailItem = DetailItem.makeWeiboDetailItem("1018", localRecordInfo.getRecordFeedFuid(), localRecordInfo.getRecordFeedRid());
        localDetailItem.setTagRecordInfo(localRecordInfo);
        localDetailItem.setCthreadCid(ViewMessageOriginalContent.this.mDetailInfo.getId());
        Bundle localBundle = new Bundle();
        localBundle.putSerializable("data", localDetailItem);
        localIntent.putExtras(localBundle);
        AnimationUtil.startFragmentForResult(ViewMessageOriginalContent.this.mFragment, localIntent, 1300);
        return;
      }
      if (RecordModel.getInstance().getErrorNum() == 4001)
      {
        Toast.makeText(ViewMessageOriginalContent.this.context, 2131427923, 0).show();
        return;
      }
      Toast.makeText(ViewMessageOriginalContent.this.context, 2131427547, 0).show();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.businesslogic.ViewMessageOriginalContent
 * JD-Core Version:    0.6.0
 */