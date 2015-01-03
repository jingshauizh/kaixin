package com.kaixin001.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.adapter.NewsItemAdapter;
import com.kaixin001.engine.AddVerifyEngine;
import com.kaixin001.engine.NewFriendEngine;
import com.kaixin001.engine.RepostEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.fragment.NewsDetailPictureFragment;
import com.kaixin001.fragment.RepostDetailFragment;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.item.RepItem;
import com.kaixin001.model.User;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.UserHabitUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewsItemPlazaView extends NewsItemBaseView
  implements View.OnClickListener
{
  private AddVerifyTask addVerifyTask;
  private boolean bStop = false;
  private TextView mContent1 = (TextView)this.rootView.findViewById(2131362599);
  private TextView mContent2 = (TextView)this.rootView.findViewById(2131363223);
  private String mFname;
  private String mFuid;
  private KXFrameImageView mPic = (KXFrameImageView)this.rootView.findViewById(2131363224);
  private ProgressDialog mProgressDialog;
  private RepostTask mRepostTask = null;
  private String mRid;
  private NewFriendTask newFriendTask;

  public NewsItemPlazaView(BaseFragment paramBaseFragment, NewsInfo paramNewsInfo, NewsItemAdapter paramNewsItemAdapter)
  {
    super(paramBaseFragment, paramNewsInfo, paramNewsItemAdapter);
    this.rootView = this.inflater.inflate(2130903252, this);
    this.mPic.setStartCrop(true);
    setOnClickListener(this);
    showSource(false);
  }

  private void actionAddFans()
  {
    this.newFriendTask = new NewFriendTask(null);
    this.newFriendTask.execute(new String[0]);
    this.mProgressDialog = ProgressDialog.show(this.mContext, "", getResources().getText(2131427512), true, true, new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramDialogInterface)
      {
        if (NewFriendEngine.getInstance() != null)
          NewFriendEngine.getInstance().cancel();
        NewsItemPlazaView.this.newFriendTask.cancel(true);
      }
    });
  }

  private void actionRepost()
  {
    this.mRepostTask = new RepostTask(null);
    this.mRepostTask.execute(new Void[0]);
    this.mProgressDialog = ProgressDialog.show(this.mContext, "", getResources().getText(2131427618), true, true, new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramDialogInterface)
      {
        if (NewsItemPlazaView.this.mRepostTask != null)
        {
          RepostEngine.getInstance().cancel();
          NewsItemPlazaView.this.mRepostTask.cancel(true);
          NewsItemPlazaView.this.mRepostTask = null;
        }
      }
    });
  }

  private void actionViewBigPic(String paramString1, String paramString2)
  {
    Intent localIntent = new Intent(this.mContext, NewsDetailPictureFragment.class);
    localIntent.putExtra("small", paramString1);
    localIntent.putExtra("large", paramString2);
    localIntent.putExtra("action", "com.kaixin001.VIEW_SINGLE_PHOTO");
    AnimationUtil.startFragment(this.mFragment, localIntent, 1);
  }

  private void actionViewRepost(boolean paramBoolean)
  {
    Intent localIntent = new Intent(this.mContext, RepostDetailFragment.class);
    ArrayList localArrayList = new ArrayList();
    RepItem localRepItem = new RepItem();
    localRepItem.fname = this.mFname;
    localRepItem.fuid = this.mFuid;
    localRepItem.id = this.mRid;
    localArrayList.add(localRepItem);
    Bundle localBundle = new Bundle();
    localBundle.putSerializable("repostList", (Serializable)localArrayList);
    localBundle.putInt("position", 0);
    localBundle.putString("commentflag", "1");
    localBundle.putString("commentflag", "1");
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragment(this.mFragment, localIntent, 1);
  }

  private void addNewFriendResult()
  {
    switch (NewFriendEngine.getInstance().getRet())
    {
    default:
      Toast.makeText(this.mContext, NewFriendEngine.getInstance().getTipMsg(), 0).show();
      return;
    case 9:
    case 10:
      DialogUtil.showKXAlertDialog(this.mContext, NewFriendEngine.getInstance().getTipMsg(), 2131427517, 2131427587, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          NewsItemPlazaView.this.addVerify(false);
        }
      }
      , null);
      return;
    case 11:
    case 12:
    }
    Toast.makeText(this.mContext, NewFriendEngine.getInstance().getTipMsg(), 0).show();
    this.adapter.addFansResult(this.mFuid);
    this.adapter.notifyDataSetChanged();
  }

  private void addVerify(boolean paramBoolean)
  {
    this.addVerifyTask = new AddVerifyTask(null);
    this.addVerifyTask.execute(null);
    this.mProgressDialog = ProgressDialog.show(this.mContext, "", getResources().getText(2131427512), true, true, new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramDialogInterface)
      {
        NewsItemPlazaView.this.bStop = true;
        if (AddVerifyEngine.getInstance() != null)
          AddVerifyEngine.getInstance().cancel();
        NewsItemPlazaView.this.addVerifyTask.cancel(true);
      }
    });
  }

  public void onClick(View paramView)
  {
    if (User.getInstance().GetLookAround())
      this.adapter.showLoginDialog();
    do
      return;
    while (paramView != this);
    UserHabitUtils.getInstance(this.mContext).addUserHabit("kxplaza_view_repost");
    actionViewRepost(false);
  }

  protected void onClickView(int paramInt, View paramView)
  {
    if (paramInt == 0)
    {
      UserHabitUtils.getInstance(this.mContext).addUserHabit("kxplaza_add_fans");
      actionAddFans();
      return;
    }
    if (paramInt == 1)
    {
      UserHabitUtils.getInstance(this.mContext).addUserHabit("kxplaza_write_tag");
      actionViewRepost(true);
      return;
    }
    if (paramInt == 3)
    {
      UserHabitUtils.getInstance(this.mContext).addUserHabit("kxplaza_repost");
      actionRepost();
      return;
    }
    super.onClickView(paramInt, paramView);
  }

  public boolean show(NewsInfo paramNewsInfo)
  {
    super.show(paramNewsInfo);
    this.mFname = paramNewsInfo.mFname;
    this.mFuid = paramNewsInfo.mFuid;
    this.mRid = paramNewsInfo.mNewsId;
    int i;
    String str1;
    if (paramNewsInfo.mIsFriend)
    {
      i = 0;
      setTopRightButton(i, 2130838982);
      if (paramNewsInfo.mVNum <= 999)
        break label108;
      str1 = "999+";
      label62: if (paramNewsInfo.mRpNum <= 999)
        break label130;
    }
    label130: for (String str2 = "999+"; ; str2 = paramNewsInfo.mRpNum)
    {
      showTwoButtons(2130838984, str1, 17, 2130838983, str2, 17);
      showContent(paramNewsInfo);
      return true;
      i = 8;
      break;
      label108: str1 = paramNewsInfo.mVNum;
      break label62;
    }
  }

  protected void showContent(NewsInfo paramNewsInfo)
  {
    String str = paramNewsInfo.mImageUrl;
    if (TextUtils.isEmpty(paramNewsInfo.mTitle))
    {
      this.mContent1.setVisibility(8);
      if (!TextUtils.isEmpty(paramNewsInfo.mAbstractContent))
        break label82;
      this.mContent2.setVisibility(8);
    }
    while (true)
    {
      if (!TextUtils.isEmpty(str))
        break label104;
      this.mPic.setVisibility(8);
      return;
      this.mContent1.setVisibility(0);
      this.mContent1.setText(paramNewsInfo.mTitle);
      break;
      label82: this.mContent2.setVisibility(0);
      this.mContent2.setText(paramNewsInfo.mAbstractContent);
    }
    label104: this.mPic.setVisibility(0);
    this.mPic.resetState(isSaveFlowState());
    this.mFragment.displayPicture(this.mPic, str, 2130838784);
    if (!isSaveFlowState(this.mPic))
    {
      this.mPic.setOnClickListener(null);
      this.mPic.setClickable(false);
      return;
    }
    this.mPic.setOnClickListener(new View.OnClickListener(str)
    {
      public void onClick(View paramView)
      {
        if (NewsItemPlazaView.this.mPic.saveFlowState())
        {
          NewsItemPlazaView.this.mPic.startDownloading();
          NewsItemPlazaView.this.mFragment.displayPicture(NewsItemPlazaView.this.mPic, this.val$picUrl, 0);
          NewsItemPlazaView.this.mPic.setOnClickListener(null);
          NewsItemPlazaView.this.mPic.setClickable(false);
        }
      }
    });
  }

  private class AddVerifyTask extends AsyncTask<String, Void, Integer>
  {
    private AddVerifyTask()
    {
    }

    protected Integer doInBackground(String[] paramArrayOfString)
    {
      String str = null;
      if (paramArrayOfString != null)
      {
        int i = paramArrayOfString.length;
        str = null;
        if (i > 0)
          str = paramArrayOfString[0];
      }
      try
      {
        if (AddVerifyEngine.getInstance().addVerify(NewsItemPlazaView.this.mContext, NewsItemPlazaView.this.mFuid, str))
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
      if (paramInteger == null)
        return;
      while (true)
      {
        try
        {
          if (NewsItemPlazaView.this.bStop)
            break;
          if (NewsItemPlazaView.this.mProgressDialog == null)
            continue;
          NewsItemPlazaView.this.mProgressDialog.dismiss();
          if (paramInteger.intValue() == 1)
          {
            Toast.makeText(NewsItemPlazaView.this.mContext, AddVerifyEngine.getInstance().getTipMsg(), 0).show();
            NewsItemPlazaView.this.adapter.addFansResult(NewsItemPlazaView.this.mFuid);
            NewsItemPlazaView.this.adapter.notifyDataSetChanged();
            NewsItemPlazaView.this.addVerifyTask = null;
            return;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("HomeActivity", "onPostExecute", localException);
          return;
        }
        Toast.makeText(NewsItemPlazaView.this.mContext, 2131427513, 0).show();
      }
    }
  }

  private class NewFriendTask extends AsyncTask<String, Void, Integer>
  {
    private NewFriendTask()
    {
    }

    protected Integer doInBackground(String[] paramArrayOfString)
    {
      try
      {
        if (NewFriendEngine.getInstance().addNewFriend(NewsItemPlazaView.this.mContext, NewsItemPlazaView.this.mFuid, null))
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
      if (paramInteger == null)
        return;
      while (true)
      {
        try
        {
          if (NewsItemPlazaView.this.mProgressDialog == null)
            continue;
          NewsItemPlazaView.this.mProgressDialog.dismiss();
          if (paramInteger.intValue() == 1)
          {
            NewsItemPlazaView.this.addNewFriendResult();
            NewsItemPlazaView.this.newFriendTask = null;
            return;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("HomeActivity", "onPostExecute", localException);
          return;
        }
        Toast.makeText(NewsItemPlazaView.this.mContext, 2131427513, 0).show();
      }
    }
  }

  private class RepostTask extends AsyncTask<Void, Void, Boolean>
  {
    private RepostTask()
    {
    }

    protected Boolean doInBackground(Void[] paramArrayOfVoid)
    {
      try
      {
        Boolean localBoolean = Boolean.valueOf(RepostEngine.getInstance().doRepost(NewsItemPlazaView.this.mFragment, NewsItemPlazaView.this.mRid, NewsItemPlazaView.this.mFuid));
        return localBoolean;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecute(Boolean paramBoolean)
    {
      try
      {
        if (NewsItemPlazaView.this.mProgressDialog != null)
          NewsItemPlazaView.this.mProgressDialog.dismiss();
        if (paramBoolean.booleanValue())
        {
          Toast.makeText(NewsItemPlazaView.this.mContext, NewsItemPlazaView.this.getResources().getString(2131427603), 0).show();
          NewsItemPlazaView.this.adapter.repostResult(NewsItemPlazaView.this.mRid);
          NewsItemPlazaView.this.adapter.notifyDataSetChanged();
          return;
        }
        Toast.makeText(NewsItemPlazaView.this.mContext, 2131427604, 0).show();
        return;
      }
      catch (Exception localException)
      {
        KXLog.e("RepostDetailActivity", "onPostExecute", localException);
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.NewsItemPlazaView
 * JD-Core Version:    0.6.0
 */