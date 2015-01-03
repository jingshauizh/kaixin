package com.kaixin001.businesslogic;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.item.CheckInItem;
import com.kaixin001.item.CheckInUser;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.model.KaixinUser;
import com.kaixin001.task.PostUpTask;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.IntentUtil;
import java.util.ArrayList;

public class ShowMenuDialog
{
  Dialog mAlertDialog;
  private Activity mContext;
  private BaseFragment mFragment;
  private String[] mItems;
  private String mTitle;
  private DialogInterface.OnClickListener onClickListener;

  public ShowMenuDialog(BaseFragment paramBaseFragment)
  {
    this.mFragment = paramBaseFragment;
    this.mContext = paramBaseFragment.getActivity();
  }

  private void setParameters(int paramInt1, int paramInt2, DialogInterface.OnClickListener paramOnClickListener)
  {
    this.mTitle = this.mFragment.getResources().getString(paramInt1);
    this.mItems = this.mFragment.getResources().getStringArray(paramInt2);
    this.onClickListener = paramOnClickListener;
  }

  private void showDialog()
  {
    if ((this.mAlertDialog != null) && (this.mAlertDialog.isShowing()))
    {
      this.mAlertDialog.dismiss();
      this.mAlertDialog = null;
    }
    this.mAlertDialog = DialogUtil.showSelectListDlg(this.mContext, this.mTitle, this.mItems, this.onClickListener, 1);
  }

  public void showMenuOfFriendCheckInItem(CheckInItem paramCheckInItem)
  {
    KaixinUser localKaixinUser = paramCheckInItem.checkInUser.user;
    setParameters(2131427509, 2131492895, new DialogInterface.OnClickListener(localKaixinUser, paramCheckInItem)
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        if (paramInt == 0)
        {
          IntentUtil.showHomeFragment(ShowMenuDialog.this.mFragment, this.val$user.uid, this.val$user.realname);
          return;
        }
        if (paramInt == 1)
        {
          IntentUtil.showLbsCheckInCommentFragment(ShowMenuDialog.this.mFragment, this.val$checkInItem);
          return;
        }
        IntentUtil.showLbsPositionDetailFragment(ShowMenuDialog.this.mFragment, this.val$checkInItem.poiId, this.val$checkInItem.poiName, "", this.val$checkInItem.distance);
      }
    });
    String[] arrayOfString = this.mItems;
    String str = this.mItems[0];
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = localKaixinUser.realname;
    arrayOfString[0] = String.format(str, arrayOfObject);
    showDialog();
  }

  public void showMenuOfItem(CheckInItem paramCheckInItem, ArrayList<String> paramArrayList, ArrayList<Integer> paramArrayList1, AddFriend paramAddFriend)
  {
    KaixinUser localKaixinUser = paramCheckInItem.checkInUser.user;
    this.mTitle = this.mFragment.getResources().getString(2131427509);
    this.mItems = ((String[])paramArrayList.toArray(new String[0]));
    if (((String)paramArrayList.get(0)).contains("%s"))
    {
      String[] arrayOfString = this.mItems;
      String str = this.mItems[0];
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localKaixinUser.realname;
      arrayOfString[0] = String.format(str, arrayOfObject);
    }
    this.onClickListener = new DialogInterface.OnClickListener(paramArrayList1, localKaixinUser, paramAddFriend, paramCheckInItem)
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        if (this.val$indexList != null)
          paramInt = ((Integer)this.val$indexList.get(paramInt)).intValue();
        if (paramInt == 0)
          IntentUtil.showHomeFragment(ShowMenuDialog.this.mFragment, this.val$user.uid, this.val$user.realname);
        if (paramInt == 1)
          if (this.val$addFriendUtil != null);
        do
        {
          return;
          this.val$addFriendUtil.addNewFriend("3", this.val$user.uid);
          return;
          if (paramInt == 2)
          {
            IntentUtil.showLbsCheckInCommentFragment(ShowMenuDialog.this.mFragment, this.val$checkInItem);
            return;
          }
          if (paramInt != 3)
            continue;
          IntentUtil.addCommentActivity(ShowMenuDialog.this.mContext, ShowMenuDialog.this.mFragment, "1192", "", this.val$checkInItem.wid, this.val$user.uid);
          return;
        }
        while (paramInt != 4);
        NewsInfo localNewsInfo = new NewsInfo();
        localNewsInfo.mNtype = "1192";
        localNewsInfo.mNewsId = this.val$checkInItem.wid;
        localNewsInfo.mFuid = this.val$user.uid;
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = localNewsInfo;
        new PostUpTask(ShowMenuDialog.this.mFragment).execute(arrayOfObject);
      }
    };
    showDialog();
  }

  public void showMenuOfStrangerCheckInItem(CheckInItem paramCheckInItem, AddFriend paramAddFriend)
  {
    KaixinUser localKaixinUser = paramCheckInItem.checkInUser.user;
    setParameters(2131427509, 2131492896, new DialogInterface.OnClickListener(paramAddFriend, localKaixinUser, paramCheckInItem)
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        if (paramInt == 0)
        {
          this.val$addFriendUtil.addNewFriend("3", this.val$user.uid);
          return;
        }
        if (paramInt == 1)
        {
          IntentUtil.showHomeFragment(ShowMenuDialog.this.mFragment, this.val$user.uid, this.val$user.realname);
          return;
        }
        if (paramInt == 2)
        {
          IntentUtil.showLbsCheckInCommentFragment(ShowMenuDialog.this.mFragment, this.val$checkInItem);
          return;
        }
        IntentUtil.showLbsPositionDetailFragment(ShowMenuDialog.this.mFragment, this.val$checkInItem.poiId, this.val$checkInItem.poiName, "", this.val$checkInItem.distance);
      }
    });
    String[] arrayOfString = this.mItems;
    String str = this.mItems[1];
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = localKaixinUser.realname;
    arrayOfString[1] = String.format(str, arrayOfObject);
    showDialog();
  }

  public void showPhotoMenuOfCheckInActivity(boolean paramBoolean, IMenuHandle paramIMenuHandle)
  {
    setParameters(2131428150, 2131492897, new DialogInterface.OnClickListener(paramBoolean, paramIMenuHandle)
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        if (paramInt == 0)
          if (this.val$isAddPhoto)
            ShowMenuDialog.this.mFragment.takePictureWithCamera();
        do
        {
          return;
          this.val$handle.handleMenuItem();
          return;
        }
        while (paramInt != 1);
        ShowMenuDialog.this.mFragment.selectPictureFromGallery();
      }
    });
    String[] arrayOfString2;
    if (paramBoolean)
    {
      arrayOfString2 = new String[2];
      arrayOfString2[0] = this.mItems[0];
      arrayOfString2[1] = this.mItems[1];
    }
    String[] arrayOfString1;
    for (this.mItems = arrayOfString2; ; this.mItems = arrayOfString1)
    {
      showDialog();
      return;
      arrayOfString1 = new String[1];
      arrayOfString1[0] = this.mItems[2];
    }
  }

  public void showPhotoMenuOfPositionMainActivity()
  {
    this.mFragment.selectPictureFromGallery();
  }

  public static abstract interface IMenuHandle
  {
    public abstract void handleMenuItem();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.businesslogic.ShowMenuDialog
 * JD-Core Version:    0.6.0
 */