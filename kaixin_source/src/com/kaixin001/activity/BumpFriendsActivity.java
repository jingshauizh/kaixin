package com.kaixin001.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.kaixin001.item.UserCard;
import com.kaixin001.lbs.LocationService;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpConnection;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.pengpeng.IShakeUIAdapter;
import com.kaixin001.pengpeng.ShakeApiHelper;
import com.kaixin001.pengpeng.data.BumpFriend;
import com.kaixin001.util.ContactUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.view.ContentListWindow;
import com.kaixin001.view.ContentListWindow.DoSelect;
import com.kaixin001.view.KXSliderLayout;
import com.kaixin001.view.KXSliderLayout.OnScreenSlideListener;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class BumpFriendsActivity extends KXDownloadPicActivity
  implements View.OnClickListener, IShakeUIAdapter, ContentListWindow.DoSelect, PopupWindow.OnDismissListener
{
  private static final int DIALOG_BUMP_ID = 902;
  private static final int DIALOG_LOCATION_ENABLE_ID = 903;
  private static final int DIALOG_MODIFY_PRIVACY_ID = 901;
  private static final int MSG_BUMP_HTTP_ERROR = 709;
  private static final int MSG_CONFIRM_STATE = 703;
  private static final int MSG_EXCHANGE_STATE = 705;
  private static final int MSG_IDLE_STATE = 701;
  private static final int MSG_INTIALIZING_STATE = 700;
  private static final int MSG_MATCHING_STATE = 702;
  private static final int MSG_SHOW_BUMP_TIMEOUT = 708;
  private static final int MSG_SHOW_NOT_MATCHED = 706;
  private static final int MSG_SHOW_OTHER_CANCEL = 707;
  private static final int MSG_WAITING_STATE = 704;
  private static final String TAG = "BumpFriendsActivity";
  private ImageView btnRight;
  private BumpDeviceAnimLooper mAnimationLooper;
  private boolean mBePaused = false;
  private BumpDialog mBumpDialog;
  private View mBumpTipsLayout;
  private DialogInterface.OnDismissListener mCommonDialogOnDismissListener = new DialogInterface.OnDismissListener()
  {
    public void onDismiss(DialogInterface paramDialogInterface)
    {
      if (!BumpFriendsActivity.this.mBePaused)
        BumpFriendsActivity.this.mShakeApiHelper.onResume();
    }
  };
  private DialogInterface.OnShowListener mCommonDialogOnShowListener = new DialogInterface.OnShowListener()
  {
    public void onShow(DialogInterface paramDialogInterface)
    {
      BumpFriendsActivity.this.mShakeApiHelper.onPause();
    }
  };
  private int mCurrentSelectType = 201;
  private Animation mFadeOutAnimation;
  private GetUserCardTask mGetCardTask;
  private boolean mHadGetRealUserCard = false;
  private ImageView mIvLogo;
  private ImageView mIvTutorialSliderIndex;
  private View mLeftDevice;
  private TextView mLoadCardRetry;
  private TextView mLoadCardText;
  private View mLoadingCardProBar;
  private ContentListWindow mPopupWindow;
  private View mRightDevice;
  private ShakeApiHelper mShakeApiHelper;
  private ImageView mSignal;
  private View mSinalAnimView;
  private View mTutorialEditCard;
  private View mTutorialLayout;
  private View mTutorialLoadingCard;
  private KXSliderLayout mTutorialSliderLayout;
  private TextView mTvBumpRetry;
  private TextView mTvBumpTips;
  private TextView mTvCity;
  private TextView mTvEmail;
  private TextView mTvInc;
  private TextView mTvMobile;
  private TextView mTvName;
  private TextView mTvPhone;
  private TextView mTvPost;
  private UserCard mUserCard;

  private void afterHasGetCard(UserCard paramUserCard)
  {
    this.mTutorialLayout.setVisibility(8);
    findViewById(2131362928).setEnabled(true);
    findViewById(2131362914).setEnabled(true);
    this.mHadGetRealUserCard = true;
    if (this.mShakeApiHelper == null)
      this.mShakeApiHelper = new ShakeApiHelper(this, this, this.mHandler);
    this.mShakeApiHelper.init();
    this.mUserCard = paramUserCard;
    updateUserCard(this.mUserCard);
  }

  private ArrayList<ListData> buildItemDataFromCard(UserCard paramUserCard)
  {
    ArrayList localArrayList = new ArrayList();
    ListData localListData1 = new ListData(null);
    localListData1.showType = -1;
    localArrayList.add(localListData1);
    if (!TextUtils.isEmpty(paramUserCard.email))
    {
      ListData localListData2 = new ListData(null);
      localListData2.showType = 3;
      localListData2.isCurrentHide = paramUserCard.isEmailHide();
      localArrayList.add(localListData2);
    }
    if (!TextUtils.isEmpty(paramUserCard.mobile))
    {
      ListData localListData3 = new ListData(null);
      localListData3.showType = 2;
      localListData3.isCurrentHide = paramUserCard.isMobileHide();
      localArrayList.add(localListData3);
    }
    if (!TextUtils.isEmpty(paramUserCard.phone))
    {
      ListData localListData4 = new ListData(null);
      localListData4.showType = 1;
      localListData4.isCurrentHide = paramUserCard.isPhoneHide();
      localArrayList.add(localListData4);
    }
    return localArrayList;
  }

  private void hasUpdatedCardResult(UserCard paramUserCard)
  {
    this.mUserCard = paramUserCard;
    this.mFadeOutAnimation.setAnimationListener(new Animation.AnimationListener(paramUserCard)
    {
      public void onAnimationEnd(Animation paramAnimation)
      {
        BumpFriendsActivity.this.afterHasGetCard(this.val$card);
      }

      public void onAnimationRepeat(Animation paramAnimation)
      {
      }

      public void onAnimationStart(Animation paramAnimation)
      {
      }
    });
    this.mTutorialLayout.startAnimation(this.mFadeOutAnimation);
  }

  private void httpExceptionResult()
  {
    this.mLoadingCardProBar.setVisibility(8);
    this.mLoadCardText.setText(2131428088);
    this.mLoadCardRetry.setVisibility(0);
    this.mLoadCardRetry.setOnClickListener(this);
  }

  private void initAnimations()
  {
    AnimationSet localAnimationSet1 = (AnimationSet)AnimationUtils.loadAnimation(this, 2130968585);
    AnimationSet localAnimationSet2 = (AnimationSet)AnimationUtils.loadAnimation(this, 2130968586);
    if (this.mLeftDevice == null)
      this.mLeftDevice = findViewById(2131361926);
    if (this.mRightDevice == null)
      this.mRightDevice = findViewById(2131361927);
    this.mAnimationLooper = new BumpDeviceAnimLooper(localAnimationSet1, this.mLeftDevice, localAnimationSet2, this.mRightDevice);
    this.mFadeOutAnimation = AnimationUtils.loadAnimation(this, 2130968589);
  }

  private void initTitle()
  {
    this.btnRight = ((ImageView)findViewById(2131362928));
    this.btnRight.setImageResource(2130838622);
    this.btnRight.setOnClickListener(this);
    this.btnRight.setEnabled(false);
    this.btnRight.setVisibility(0);
    ImageView localImageView = (ImageView)findViewById(2131362914);
    localImageView.setOnClickListener(this);
    localImageView.setEnabled(false);
    findViewById(2131362919).setVisibility(8);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setVisibility(0);
    localTextView.setText(2131428108);
  }

  private void initViews()
  {
    this.mTutorialLayout = findViewById(2131361933);
    this.mTutorialSliderLayout = ((KXSliderLayout)this.mTutorialLayout.findViewById(2131361934));
    this.mIvTutorialSliderIndex = ((ImageView)this.mTutorialLayout.findViewById(2131361941));
    this.mTutorialSliderLayout.setOnScreenSliderListener(new KXSliderLayout.OnScreenSlideListener()
    {
      public void onSlideOverTheEnd()
      {
      }

      public void onSlideTo(int paramInt)
      {
        if (BumpFriendsActivity.this.mIvTutorialSliderIndex != null)
          BumpFriendsActivity.this.mIvTutorialSliderIndex.setImageLevel(paramInt);
      }
    });
    this.mTutorialLoadingCard = this.mTutorialLayout.findViewById(2131361942);
    this.mLoadingCardProBar = this.mTutorialLoadingCard.findViewById(2131361943);
    this.mLoadCardText = ((TextView)this.mTutorialLoadingCard.findViewById(2131361944));
    this.mLoadCardRetry = ((TextView)this.mTutorialLoadingCard.findViewById(2131361945));
    this.mTutorialEditCard = this.mTutorialLayout.findViewById(2131361940);
    displayPicture((ImageView)this.mTutorialLayout.findViewById(2131361939), User.getInstance().getLogo(), 2130837561);
    this.mSinalAnimView = findViewById(2131361928);
    this.mSignal = ((ImageView)findViewById(2131361929));
    this.mLeftDevice = findViewById(2131361926);
    this.mRightDevice = findViewById(2131361927);
    this.mBumpTipsLayout = findViewById(2131361930);
    this.mTvBumpTips = ((TextView)findViewById(2131361931));
    this.mTvBumpRetry = ((TextView)findViewById(2131361932));
    this.mSignal.setImageResource(2130837633);
    this.mBumpTipsLayout.setVisibility(8);
    this.mIvLogo = ((ImageView)findViewById(2131361916));
    this.mTvName = ((TextView)findViewById(2131361867));
    this.mTvPost = ((TextView)findViewById(2131361917));
    this.mTvCity = ((TextView)findViewById(2131361918));
    this.mTvInc = ((TextView)findViewById(2131361919));
    this.mTvEmail = ((TextView)findViewById(2131361920));
    this.mTvPhone = ((TextView)findViewById(2131361921));
    this.mTvMobile = ((TextView)findViewById(2131361922));
  }

  private void notUpdatedCardResult(UserCard paramUserCard)
  {
    this.mTutorialEditCard.setTag(paramUserCard);
    this.mTutorialEditCard.setOnClickListener(this);
    this.mTutorialLoadingCard.setVisibility(8);
  }

  private void showPopUpWindow(View paramView)
  {
    View localView = ((LayoutInflater)getSystemService("layout_inflater")).inflate(2130903239, null, false);
    if ((this.mPopupWindow != null) && (this.mPopupWindow.isShowing()))
      try
      {
        this.mPopupWindow.dismiss();
        this.mPopupWindow = null;
        return;
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    float f = getResources().getDisplayMetrics().density;
    int i = getResources().getConfiguration().orientation;
    ArrayList localArrayList = buildItemDataFromCard(this.mUserCard);
    Resources localResources = getResources();
    ContentListWindow.mEditContent = new String[localArrayList.size()];
    ContentListWindow.mEditContent[0] = localResources.getString(2131428099);
    int j = 1;
    if (j >= localArrayList.size())
      if (i != 1)
        break label448;
    label284: label448: for (this.mPopupWindow = new ContentListWindow(localView, (int)(180.0F * f), -2, true, this, this.mCurrentSelectType); ; this.mPopupWindow = new ContentListWindow(localView, (int)(180.0F * f), -2, true, this, this.mCurrentSelectType))
    {
      this.mPopupWindow.setDoSelectListener(this);
      this.mPopupWindow.setOnDismissListener(this);
      this.mPopupWindow.setOutsideTouchable(true);
      this.mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
      this.mPopupWindow.setClippingEnabled(false);
      int[] arrayOfInt = new int[2];
      paramView.getLocationInWindow(arrayOfInt);
      int k = 6 * (int)f;
      this.mPopupWindow.showAtLocation(paramView, 53, 0, 13 + (arrayOfInt[1] + paramView.getHeight() - k));
      return;
      String str;
      if (((ListData)localArrayList.get(j)).isCurrentHide)
      {
        str = localResources.getString(2131428095);
        switch (((ListData)localArrayList.get(j)).showType)
        {
        default:
        case 3:
        case 1:
        case 2:
        }
      }
      while (true)
      {
        j++;
        break;
        str = localResources.getString(2131428094);
        break label284;
        ContentListWindow.mEditContent[j] = (str + localResources.getString(2131428124));
        continue;
        ContentListWindow.mEditContent[j] = (str + localResources.getString(2131428097));
        continue;
        ContentListWindow.mEditContent[j] = (str + localResources.getString(2131428098));
      }
    }
  }

  private void updateUserCard(UserCard paramUserCard)
  {
    displayPicture(this.mIvLogo, paramUserCard.logo, 2130837561);
    this.mTvName.setText(paramUserCard.name);
    Resources localResources;
    StringBuffer localStringBuffer;
    if ((!paramUserCard.isPostHide()) && (!TextUtils.isEmpty(paramUserCard.post)))
    {
      this.mTvPost.setText(paramUserCard.post);
      this.mTvPost.setVisibility(0);
      localResources = getResources();
      localStringBuffer = new StringBuffer();
      if ((paramUserCard.isCityHide()) || (TextUtils.isEmpty(paramUserCard.city)))
        break label442;
      localStringBuffer.append(localResources.getString(2131428120)).append(":  ").append(paramUserCard.city);
      this.mTvCity.setText(localStringBuffer.toString());
      this.mTvCity.setVisibility(0);
      localStringBuffer.delete(0, localStringBuffer.length());
      label146: if ((paramUserCard.isCompanyHide()) || (TextUtils.isEmpty(paramUserCard.company)))
        break label454;
      localStringBuffer.append(localResources.getString(2131428121)).append(":  ").append(paramUserCard.company);
      this.mTvInc.setText(localStringBuffer.toString());
      this.mTvInc.setVisibility(0);
      localStringBuffer.delete(0, localStringBuffer.length());
      label217: if ((paramUserCard.isEmailHide()) || (TextUtils.isEmpty(paramUserCard.email)))
        break label466;
      localStringBuffer.append(localResources.getString(2131428124)).append(":  ").append(paramUserCard.email);
      this.mTvEmail.setText(localStringBuffer.toString());
      this.mTvEmail.setVisibility(0);
      localStringBuffer.delete(0, localStringBuffer.length());
      label288: if ((paramUserCard.isPhoneHide()) || (TextUtils.isEmpty(paramUserCard.phone)))
        break label478;
      localStringBuffer.append(localResources.getString(2131428125)).append(":  ").append(paramUserCard.phone);
      this.mTvPhone.setText(localStringBuffer.toString());
      this.mTvPhone.setVisibility(0);
      localStringBuffer.delete(0, localStringBuffer.length());
    }
    while (true)
    {
      if ((paramUserCard.isMobileHide()) || (TextUtils.isEmpty(paramUserCard.mobile)))
        break label490;
      localStringBuffer.append(localResources.getString(2131428098)).append(":  ").append(paramUserCard.mobile);
      this.mTvMobile.setText(localStringBuffer.toString());
      this.mTvMobile.setVisibility(0);
      localStringBuffer.delete(0, localStringBuffer.length());
      return;
      this.mTvPost.setVisibility(4);
      break;
      label442: this.mTvCity.setVisibility(8);
      break label146;
      label454: this.mTvInc.setVisibility(8);
      break label217;
      label466: this.mTvEmail.setVisibility(8);
      break label288;
      label478: this.mTvPhone.setVisibility(8);
    }
    label490: this.mTvMobile.setVisibility(8);
  }

  public void doSelect(int paramInt)
  {
    if (paramInt == 201)
    {
      BumpCardActivity.launchForEditResult(this, this.mUserCard);
      return;
    }
    int i;
    int j;
    int k;
    if (paramInt == 202)
    {
      i = 3;
      ArrayList localArrayList = buildItemDataFromCard(this.mUserCard);
      j = 0;
      k = 0;
      label41: if (k < ContentListWindow.mEditTypeList.length)
        break label114;
      if (!((ListData)localArrayList.get(j)).isCurrentHide)
        break label134;
    }
    label134: for (boolean bool = false; ; bool = true)
    {
      new UpdateCardPrivacyTask(i, bool).execute(new Void[0]);
      return;
      if (paramInt == 204)
      {
        i = 1;
        break;
      }
      i = 0;
      if (paramInt != 203)
        break;
      i = 2;
      break;
      label114: if (paramInt == ContentListWindow.mEditTypeList[k])
        j = k;
      k++;
      break label41;
    }
  }

  protected boolean handleMessage(Message paramMessage)
  {
    if (super.handleMessage(paramMessage))
      return true;
    switch (paramMessage.what)
    {
    default:
      return false;
    case 700:
      onStatusIntializing();
      return true;
    case 701:
      onStatusIdle(paramMessage.arg1);
      return true;
    case 702:
      onStatusMatching();
      return true;
    case 703:
      BumpFriend localBumpFriend2 = (BumpFriend)paramMessage.obj;
      onStatusConfirm(localBumpFriend2.name, localBumpFriend2.logo, localBumpFriend2.isAlreadyFrid);
      return true;
    case 704:
      onStatusWaiting();
      return true;
    case 705:
      onStatusExchange((BumpFriend)paramMessage.obj);
      return true;
    case 706:
      showNotMatched();
      return true;
    case 707:
      BumpFriend localBumpFriend1 = (BumpFriend)paramMessage.obj;
      showOtherUserCanceled(localBumpFriend1.name, localBumpFriend1.logo);
      return true;
    case 708:
      showBumpTimeout();
      return true;
    case 709:
    }
    showHttpError();
    return true;
  }

  protected void modifyCardPrivacy(int paramInt, boolean paramBoolean)
  {
    this.mUserCard.changePrivacy(paramInt, paramBoolean);
    updateUserCard(this.mUserCard);
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt1 == 502) && (paramInt2 == -1))
    {
      UserCard localUserCard = (UserCard)paramIntent.getParcelableExtra("card");
      this.mUserCard = localUserCard;
      afterHasGetCard(localUserCard);
    }
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
    case 2131362914:
    case 2131361940:
    case 2131362928:
    case 2131361915:
    case 2131361945:
      do
      {
        return;
        finish();
        return;
        BumpCardActivity.launchForEditResult(this, (UserCard)paramView.getTag());
        return;
        showPopUpWindow(this.btnRight);
        return;
        showDialog(901);
        return;
      }
      while ((this.mGetCardTask != null) && (this.mGetCardTask.getStatus() != AsyncTask.Status.FINISHED));
      this.mGetCardTask = new GetUserCardTask(null);
      this.mGetCardTask.execute(new Void[0]);
      return;
    case 2131361932:
    }
    if (HttpConnection.checkNetworkAvailable(getApplicationContext()) != 1)
      showToast(2131427387);
    this.mShakeApiHelper.init();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903059);
    initTitle();
    initViews();
    initAnimations();
    this.mShakeApiHelper = new ShakeApiHelper(this, this, this.mHandler);
    if (!LocationService.getLocationService().isLocationProviderEnabled())
    {
      this.mTutorialLayout.setVisibility(4);
      showDialog(903);
      return;
    }
    this.mGetCardTask = new GetUserCardTask(null);
    this.mGetCardTask.execute(new Void[0]);
  }

  protected Dialog onCreateDialog(int paramInt)
  {
    AlertDialog localAlertDialog;
    if (paramInt == 901)
    {
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
      localBuilder.setTitle(2131428093);
      localBuilder.setNegativeButton(2131427587, null);
      localBuilder.setAdapter(new BumpCardPrivacyDialogAdapter(this, this.mUserCard), new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          if (paramInt == 0)
          {
            BumpCardActivity.launchForEditResult(BumpFriendsActivity.this, BumpFriendsActivity.this.mUserCard);
            return;
          }
          BumpCardPrivacyDialogAdapter localBumpCardPrivacyDialogAdapter = (BumpCardPrivacyDialogAdapter)((AlertDialog)paramDialogInterface).getListView().getAdapter();
          String[] arrayOfString = new String[2];
          localBumpCardPrivacyDialogAdapter.getItemAction(arrayOfString, paramInt);
          int i;
          if (arrayOfString[0].equalsIgnoreCase("email"))
            i = 3;
          while (true)
          {
            boolean bool2 = arrayOfString[1].equalsIgnoreCase("hide");
            new BumpFriendsActivity.UpdateCardPrivacyTask(BumpFriendsActivity.this, i, bool2).execute(new Void[0]);
            return;
            if (arrayOfString[0].equalsIgnoreCase("phone"))
            {
              i = 1;
              continue;
            }
            boolean bool1 = arrayOfString[0].equalsIgnoreCase("mobile");
            i = 0;
            if (!bool1)
              continue;
            i = 2;
          }
        }
      });
      localAlertDialog = localBuilder.create();
      localAlertDialog.setOnShowListener(this.mCommonDialogOnShowListener);
      localAlertDialog.setOnDismissListener(this.mCommonDialogOnDismissListener);
    }
    do
    {
      return localAlertDialog;
      if (paramInt == 902)
      {
        this.mBumpDialog = new BumpDialog(this);
        return this.mBumpDialog;
      }
      localAlertDialog = null;
    }
    while (paramInt != 903);
    Dialog localDialog = DialogUtil.createAlertDialog(this, 2131427669, 2131427382, 2131427383, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        Intent localIntent1 = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
        try
        {
          BumpFriendsActivity.this.startActivity(localIntent1);
          return;
        }
        catch (ActivityNotFoundException localActivityNotFoundException)
        {
          Intent localIntent2 = new Intent("android.settings.SECURITY_SETTINGS");
          BumpFriendsActivity.this.startActivity(localIntent2);
        }
      }
    }
    , null);
    localDialog.setOnDismissListener(new DialogInterface.OnDismissListener()
    {
      public void onDismiss(DialogInterface paramDialogInterface)
      {
        BumpFriendsActivity.this.mGetCardTask = new BumpFriendsActivity.GetUserCardTask(BumpFriendsActivity.this, null);
        BumpFriendsActivity.this.mGetCardTask.execute(new Void[0]);
      }
    });
    return localDialog;
  }

  protected void onDestroy()
  {
    super.onDestroy();
    if (this.mShakeApiHelper != null)
      this.mShakeApiHelper.release();
  }

  public void onDismiss()
  {
  }

  protected void onPause()
  {
    super.onPause();
    this.mBePaused = true;
    if (this.mShakeApiHelper != null)
    {
      this.mShakeApiHelper.onPause();
      this.mShakeApiHelper.stopRequest();
    }
  }

  protected void onPrepareDialog(int paramInt, Dialog paramDialog)
  {
    super.onPrepareDialog(paramInt, paramDialog);
    if (paramInt == 901)
      ((BumpCardPrivacyDialogAdapter)((AlertDialog)paramDialog).getListView().getAdapter()).updateCardPrivacy(this.mUserCard);
    do
      return;
    while (paramInt != 902);
    ((BumpDialog)paramDialog).showMatching();
  }

  protected void onResume()
  {
    super.onResume();
    this.mBePaused = false;
    if ((this.mHadGetRealUserCard) && (this.mShakeApiHelper != null) && ((this.mBumpDialog == null) || (!this.mBumpDialog.isShowing())))
      this.mShakeApiHelper.onResume();
    if ((this.mHadGetRealUserCard) && (this.mShakeApiHelper != null) && (this.mTvBumpRetry.getVisibility() != 0))
      this.mShakeApiHelper.restartRequest();
  }

  public void onStatusConfirm(String paramString1, String paramString2, boolean paramBoolean)
  {
    this.mBumpDialog.showConfirm(paramString1, paramString2, paramBoolean);
  }

  public void onStatusExchange(BumpFriend paramBumpFriend)
  {
    this.mHandler.post(new Runnable(paramBumpFriend)
    {
      public void run()
      {
        ContactUtil.storeCardToLocalContacts(BumpFriendsActivity.this.getContentResolver(), this.val$otherCard.mobile, this.val$otherCard.phone, this.val$otherCard.email, this.val$otherCard.name, this.val$otherCard.company, this.val$otherCard.post, this.val$otherCard.city, BumpFriendsActivity.this.mBumpDialog.getOtherLogoBitmap());
      }
    });
    this.mBumpDialog.showOtherCard(paramBumpFriend);
  }

  public void onStatusIdle(int paramInt)
  {
    if ((this.mBePaused) || ((this.mBumpDialog != null) && (this.mBumpDialog.isShowing())))
      return;
    this.mSinalAnimView.setBackgroundDrawable(null);
    this.mSignal.setImageResource(2130837636);
    this.mSignal.setVisibility(0);
    this.mTvBumpRetry.setVisibility(8);
    this.mTvBumpTips.setText(2131428107);
    this.mAnimationLooper.start();
    this.mShakeApiHelper.startBumpDetection();
  }

  public void onStatusIntializing()
  {
    this.mBumpTipsLayout.setVisibility(0);
    this.mTvBumpRetry.setVisibility(8);
    this.mTvBumpTips.setText(2131428112);
    this.mSignal.setVisibility(4);
    this.mSinalAnimView.setBackgroundResource(2130968587);
    AnimationDrawable localAnimationDrawable = (AnimationDrawable)this.mSinalAnimView.getBackground();
    this.mAnimationLooper.stop();
    if (localAnimationDrawable != null)
      localAnimationDrawable.start();
  }

  public void onStatusMatching()
  {
    this.mAnimationLooper.stop();
    showDialog(902);
  }

  public void onStatusWaiting()
  {
    this.mBumpDialog.showWaiting();
  }

  public void setBumpTimeout()
  {
    this.mHandler.sendEmptyMessage(708);
  }

  public void setHttpError()
  {
    this.mHandler.sendEmptyMessage(709);
  }

  public void setNotMatched()
  {
    this.mHandler.sendEmptyMessage(706);
  }

  public void setOtherUserCanceled(BumpFriend paramBumpFriend)
  {
    Message localMessage = Message.obtain();
    localMessage.what = 707;
    localMessage.obj = paramBumpFriend;
    this.mHandler.sendMessage(localMessage);
  }

  public void setStatusConfirm(BumpFriend paramBumpFriend)
  {
    Message localMessage = Message.obtain();
    localMessage.what = 703;
    localMessage.obj = paramBumpFriend;
    this.mHandler.sendMessage(localMessage);
  }

  public void setStatusExchange(BumpFriend paramBumpFriend)
  {
    Message localMessage = Message.obtain();
    localMessage.what = 705;
    localMessage.obj = paramBumpFriend;
    this.mHandler.sendMessage(localMessage);
  }

  public void setStatusIdle(int paramInt)
  {
    Message localMessage = Message.obtain();
    localMessage.what = 701;
    localMessage.arg1 = paramInt;
    this.mHandler.sendMessage(localMessage);
  }

  public void setStatusIntializeing()
  {
    this.mHandler.sendEmptyMessage(700);
  }

  public void setStatusMatching()
  {
    this.mHandler.sendEmptyMessage(702);
  }

  public void setStatusWaiting(BumpFriend paramBumpFriend)
  {
    this.mHandler.sendEmptyMessage(704);
  }

  public void showBumpTimeout()
  {
    if (this.mBumpDialog.isShowing())
      this.mBumpDialog.showTimeout();
  }

  public void showBumpToast(int paramInt, String paramString)
  {
  }

  public void showBumpToast(String paramString)
  {
  }

  void showHttpError()
  {
    if ((this.mBumpDialog != null) && (this.mBumpDialog.isShowing()))
      this.mBumpDialog.showHttpError();
    this.mTvBumpTips.setText(2131428090);
    this.mTvBumpRetry.setVisibility(0);
    this.mTvBumpRetry.setOnClickListener(this);
    this.mSinalAnimView.setBackgroundDrawable(null);
    this.mSignal.setImageResource(2130837633);
    this.mSignal.setVisibility(0);
    this.mAnimationLooper.stop();
  }

  public void showNotMatched()
  {
    if (this.mBumpDialog.isShowing())
      this.mBumpDialog.showNotMatched();
  }

  public void showOtherUserCanceled(String paramString1, String paramString2)
  {
    if (this.mBumpDialog.isShowing())
      this.mBumpDialog.showOtherCancel(paramString1, paramString2);
  }

  private static class BumpDeviceAnimLooper
    implements Animation.AnimationListener
  {
    private AnimationSet mAnim1;
    private AnimationSet mAnim2;
    private View mView1;
    private View mView2;

    public BumpDeviceAnimLooper(AnimationSet paramAnimationSet1, View paramView1, AnimationSet paramAnimationSet2, View paramView2)
    {
      this.mAnim1 = paramAnimationSet1;
      this.mAnim2 = paramAnimationSet2;
      this.mView1 = paramView1;
      this.mView2 = paramView2;
      this.mAnim2.setAnimationListener(this);
    }

    public void onAnimationEnd(Animation paramAnimation)
    {
      if (this.mView2.getAnimation() == paramAnimation)
      {
        this.mView2.postDelayed(new Runnable()
        {
          public void run()
          {
            BumpFriendsActivity.BumpDeviceAnimLooper.this.start();
          }
        }
        , 800L);
        return;
      }
      paramAnimation.setAnimationListener(null);
    }

    public void onAnimationRepeat(Animation paramAnimation)
    {
    }

    public void onAnimationStart(Animation paramAnimation)
    {
    }

    public void start()
    {
      this.mView1.startAnimation(this.mAnim1);
      this.mView2.startAnimation(this.mAnim2);
    }

    public void stop()
    {
      this.mView1.setAnimation(null);
      this.mView2.setAnimation(null);
    }
  }

  private class BumpDialog extends Dialog
    implements View.OnClickListener
  {
    private Button mBtnWaitingAction;
    private View mCancel;
    private View mConfirmLayout;
    private View mConfirmNo;
    private View mConfirmYes;
    private boolean mIsHttpError = false;
    private ImageView mIvConfirmLogo;
    private ImageView mIvOtherCancel;
    private ImageView mIvSuccLogo;
    private ImageView mIvWaitingLogo;
    private View mMatchingLayout;
    private String mOhterLogo;
    private View mSuccOK;
    private View mSuccessLayout;
    private TextView mTvConfirmName;
    private TextView mTvConfirmRelation;
    private TextView mTvSuccCity;
    private TextView mTvSuccEmail;
    private TextView mTvSuccInc;
    private TextView mTvSuccMobile;
    private TextView mTvSuccName;
    private TextView mTvSuccNameTips;
    private TextView mTvSuccPhone;
    private TextView mTvSuccPost;
    private TextView mTvSuccRelationTips;
    private ViewFlipper mViewFlipper;
    private View mWaitingLayout;

    public BumpDialog(Context arg2)
    {
      super(2131230725);
    }

    private void showWaiting()
    {
      this.mViewFlipper.setDisplayedChild(2);
      BumpFriendsActivity.this.displayPicture(this.mIvWaitingLogo, this.mOhterLogo, 2130837561);
    }

    public Bitmap getOtherLogoBitmap()
    {
      if (this.mIvWaitingLogo != null)
        return this.mIvWaitingLogo.getDrawingCache();
      return null;
    }

    public void onClick(View paramView)
    {
      switch (paramView.getId())
      {
      default:
        return;
      case 2131361876:
        dismiss();
        return;
      case 2131361884:
        BumpFriendsActivity.this.mShakeApiHelper.confirm();
        return;
      case 2131361885:
        BumpFriendsActivity.this.mShakeApiHelper.reject();
        dismiss();
        return;
      case 2131361887:
      case 2131361908:
      case 2131361911:
        dismiss();
        return;
      case 2131361906:
        dismiss();
        return;
      case 2131361913:
      }
      dismiss();
    }

    protected void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      setContentView(2130903058);
      this.mViewFlipper = ((ViewFlipper)findViewById(2131361874));
      this.mMatchingLayout = findViewById(2131361875);
      this.mConfirmLayout = findViewById(2131361879);
      this.mWaitingLayout = findViewById(2131361886);
      this.mSuccessLayout = findViewById(2131361892);
      this.mCancel = this.mMatchingLayout.findViewById(2131361876);
      this.mCancel.setOnClickListener(this);
      this.mIvConfirmLogo = ((ImageView)this.mConfirmLayout.findViewById(2131361881));
      this.mTvConfirmName = ((TextView)this.mConfirmLayout.findViewById(2131361882));
      this.mTvConfirmRelation = ((TextView)this.mConfirmLayout.findViewById(2131361883));
      this.mConfirmYes = this.mConfirmLayout.findViewById(2131361884);
      this.mConfirmNo = this.mConfirmLayout.findViewById(2131361885);
      this.mConfirmYes.setOnClickListener(this);
      this.mConfirmNo.setOnClickListener(this);
      this.mIvWaitingLogo = ((ImageView)this.mWaitingLayout.findViewById(2131361889));
      this.mIvWaitingLogo.setDrawingCacheEnabled(true);
      this.mIvWaitingLogo.setDrawingCacheQuality(1048576);
      this.mBtnWaitingAction = ((Button)this.mWaitingLayout.findViewById(2131361887));
      this.mBtnWaitingAction.setOnClickListener(this);
      this.mIvSuccLogo = ((ImageView)this.mSuccessLayout.findViewById(2131361895));
      this.mTvSuccName = ((TextView)this.mSuccessLayout.findViewById(2131361896));
      this.mTvSuccPost = ((TextView)this.mSuccessLayout.findViewById(2131361897));
      this.mTvSuccCity = ((TextView)this.mSuccessLayout.findViewById(2131361898));
      this.mTvSuccInc = ((TextView)this.mSuccessLayout.findViewById(2131361899));
      this.mTvSuccEmail = ((TextView)this.mSuccessLayout.findViewById(2131361900));
      this.mTvSuccPhone = ((TextView)this.mSuccessLayout.findViewById(2131361901));
      this.mTvSuccMobile = ((TextView)this.mSuccessLayout.findViewById(2131361902));
      this.mTvSuccNameTips = ((TextView)this.mSuccessLayout.findViewById(2131361904));
      this.mTvSuccRelationTips = ((TextView)this.mSuccessLayout.findViewById(2131361905));
      this.mSuccOK = this.mSuccessLayout.findViewById(2131361906);
      this.mSuccOK.setOnClickListener(this);
      this.mIvOtherCancel = ((ImageView)findViewById(2131361910));
      findViewById(2131361911).setOnClickListener(this);
      findViewById(2131361908).setOnClickListener(this);
      findViewById(2131361913).setOnClickListener(this);
    }

    protected void onStop()
    {
      super.onStop();
      if (!this.mIsHttpError)
        BumpFriendsActivity.this.mShakeApiHelper.resetIdleForCancel();
      this.mIsHttpError = false;
    }

    public void showConfirm(String paramString1, String paramString2, boolean paramBoolean)
    {
      this.mOhterLogo = paramString2;
      this.mViewFlipper.setDisplayedChild(1);
      String str = getContext().getResources().getString(2131428113, new Object[] { paramString1 });
      this.mTvConfirmName.setText(Html.fromHtml(str));
      BumpFriendsActivity.this.displayPicture(this.mIvConfirmLogo, paramString2, 2130837561);
      TextView localTextView = this.mTvConfirmRelation;
      int i = 0;
      if (paramBoolean)
        i = 4;
      localTextView.setVisibility(i);
    }

    public void showHttpError()
    {
      this.mIsHttpError = true;
      this.mViewFlipper.setDisplayedChild(6);
    }

    public void showMatching()
    {
      this.mViewFlipper.setDisplayedChild(0);
    }

    public void showNotMatched()
    {
      this.mViewFlipper.setDisplayedChild(4);
    }

    public void showOtherCancel(String paramString1, String paramString2)
    {
      this.mViewFlipper.setDisplayedChild(5);
      BumpFriendsActivity.this.displayPicture(this.mIvOtherCancel, paramString2, 2130837561);
    }

    public void showOtherCard(BumpFriend paramBumpFriend)
    {
      this.mViewFlipper.setDisplayedChild(3);
      BumpFriendsActivity.this.displayPicture(this.mIvSuccLogo, paramBumpFriend.logo, 2130837561);
      this.mTvSuccName.setText(paramBumpFriend.name);
      this.mTvSuccPost.setText(paramBumpFriend.post);
      StringBuffer localStringBuffer = new StringBuffer();
      Resources localResources = getContext().getResources();
      label191: label255: Object[] arrayOfObject2;
      if (!TextUtils.isEmpty(paramBumpFriend.city))
      {
        localStringBuffer.append(localResources.getString(2131428120)).append(":  ").append(paramBumpFriend.city);
        this.mTvSuccCity.setText(localStringBuffer.toString());
        this.mTvSuccCity.setVisibility(0);
        localStringBuffer.delete(0, localStringBuffer.length());
        if (TextUtils.isEmpty(paramBumpFriend.company))
          break label448;
        localStringBuffer.append(localResources.getString(2131428121)).append(":  ").append(paramBumpFriend.company);
        this.mTvSuccInc.setText(localStringBuffer.toString());
        this.mTvSuccInc.setVisibility(0);
        localStringBuffer.delete(0, localStringBuffer.length());
        if (TextUtils.isEmpty(paramBumpFriend.email))
          break label460;
        localStringBuffer.append(localResources.getString(2131428124)).append(":  ").append(paramBumpFriend.email);
        this.mTvSuccEmail.setText(localStringBuffer.toString());
        this.mTvSuccEmail.setVisibility(0);
        localStringBuffer.delete(0, localStringBuffer.length());
        if (TextUtils.isEmpty(paramBumpFriend.phone))
          break label472;
        localStringBuffer.append(localResources.getString(2131428125)).append(":  ").append(paramBumpFriend.phone);
        this.mTvSuccPhone.setText(localStringBuffer.toString());
        this.mTvSuccPhone.setVisibility(0);
        localStringBuffer.delete(0, localStringBuffer.length());
        label319: if (TextUtils.isEmpty(paramBumpFriend.mobile))
          break label484;
        localStringBuffer.append(localResources.getString(2131428126)).append(":  ").append(paramBumpFriend.mobile);
        this.mTvSuccMobile.setText(localStringBuffer.toString());
        this.mTvSuccMobile.setVisibility(0);
        localStringBuffer.delete(0, localStringBuffer.length());
        label383: this.mTvSuccRelationTips.setVisibility(0);
        if (!paramBumpFriend.isAlreadyFrid)
          break label496;
        arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = paramBumpFriend.name;
      }
      label448: label460: label472: label484: label496: Object[] arrayOfObject1;
      for (String str = localResources.getString(2131428114, arrayOfObject2); ; str = localResources.getString(2131428115, arrayOfObject1))
      {
        this.mTvSuccNameTips.setText(Html.fromHtml(str));
        return;
        this.mTvSuccCity.setVisibility(8);
        break;
        this.mTvSuccInc.setVisibility(8);
        break label191;
        this.mTvSuccEmail.setVisibility(8);
        break label255;
        this.mTvSuccPhone.setVisibility(8);
        break label319;
        this.mTvSuccMobile.setVisibility(8);
        break label383;
        arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = paramBumpFriend.name;
      }
    }

    public void showTimeout()
    {
      this.mViewFlipper.setDisplayedChild(6);
    }
  }

  private class GetUserCardTask extends AsyncTask<Void, Void, UserCard>
  {
    private GetUserCardTask()
    {
    }

    protected UserCard doInBackground(Void[] paramArrayOfVoid)
    {
      long l1 = System.currentTimeMillis();
      String str1 = Protocol.getInstance().makeGetUserCardRequest();
      HttpProxy localHttpProxy = new HttpProxy(BumpFriendsActivity.this);
      try
      {
        String str3 = localHttpProxy.httpGet(str1, null, null);
        str2 = str3;
        if (TextUtils.isEmpty(str2))
        {
          localUserCard = null;
          return localUserCard;
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          UserCard localUserCard;
          KXLog.e("BumpFriendsActivity", "doInBackground error", localException);
          String str2 = null;
          continue;
          try
          {
            JSONObject localJSONObject1 = new JSONObject(str2);
            if (localJSONObject1.getInt("ret") == 1)
            {
              localUserCard = new UserCard();
              localUserCard.privacy = localJSONObject1.getInt("privacy");
              if (localJSONObject1.has("profile"))
              {
                JSONObject localJSONObject2 = localJSONObject1.getJSONObject("profile");
                localUserCard.name = localJSONObject2.getString("name");
                localUserCard.logo = localJSONObject2.getString("logo");
                localUserCard.city = localJSONObject2.getString("city");
                localUserCard.company = localJSONObject2.getString("com");
                localUserCard.post = localJSONObject2.getString("title");
                localUserCard.phone = localJSONObject2.getString("phone");
                localUserCard.mobile = localJSONObject2.getString("mobile");
                localUserCard.email = localJSONObject2.getString("email");
                localUserCard.from = localJSONObject2.getString("from");
                long l2 = System.currentTimeMillis();
                long l3 = l2 - l1;
                if (l3 >= 1500L)
                  continue;
                long l4 = 1500L - l3;
                try
                {
                  Thread.sleep(l4);
                  return localUserCard;
                }
                catch (InterruptedException localInterruptedException)
                {
                  localInterruptedException.printStackTrace();
                  return localUserCard;
                }
              }
            }
          }
          catch (JSONException localJSONException)
          {
            localJSONException.printStackTrace();
          }
        }
      }
      return null;
    }

    protected void onPostExecute(UserCard paramUserCard)
    {
      super.onPostExecute(paramUserCard);
      if (paramUserCard == null)
      {
        BumpFriendsActivity.this.httpExceptionResult();
        return;
      }
      if (paramUserCard.hasBeenUpdatedCard())
      {
        BumpFriendsActivity.this.hasUpdatedCardResult(paramUserCard);
        return;
      }
      BumpFriendsActivity.this.notUpdatedCardResult(paramUserCard);
    }

    protected void onPreExecute()
    {
      BumpFriendsActivity.this.mTutorialLayout.setVisibility(0);
      BumpFriendsActivity.this.mLoadCardRetry.setVisibility(8);
      BumpFriendsActivity.this.mLoadCardText.setText(2131428087);
      BumpFriendsActivity.this.mLoadingCardProBar.setVisibility(0);
    }
  }

  private class ListData
  {
    public boolean isCurrentHide;
    public int showType;

    private ListData()
    {
    }
  }

  private class UpdateCardPrivacyTask extends AsyncTask<Void, Void, Integer>
  {
    private boolean actionHide;
    private int mUpdatePropertyTyep;

    public UpdateCardPrivacyTask(int paramBoolean, boolean arg3)
    {
      this.mUpdatePropertyTyep = paramBoolean;
      boolean bool;
      this.actionHide = bool;
    }

    protected Integer doInBackground(Void[] paramArrayOfVoid)
    {
      String str1 = Protocol.getInstance().makeUpdateUserCardPrivacy(this.mUpdatePropertyTyep, this.actionHide);
      HttpProxy localHttpProxy = new HttpProxy(BumpFriendsActivity.this);
      try
      {
        String str3 = localHttpProxy.httpGet(str1, null, null);
        str2 = str3;
        if (TextUtils.isEmpty(str2))
          return Integer.valueOf(-1);
      }
      catch (Exception localException)
      {
        String str2;
        while (true)
        {
          KXLog.e("BumpFriendsActivity", "doInBackground error", localException);
          str2 = null;
        }
        try
        {
          if (new JSONObject(str2).getInt("ret") == 1)
          {
            Integer localInteger = Integer.valueOf(1);
            return localInteger;
          }
        }
        catch (JSONException localJSONException)
        {
          localJSONException.printStackTrace();
          return Integer.valueOf(-1);
        }
      }
      return Integer.valueOf(-1);
    }

    protected void onPostExecute(Integer paramInteger)
    {
      super.onPostExecute(paramInteger);
      if (paramInteger.intValue() == 1)
      {
        BumpFriendsActivity.this.modifyCardPrivacy(this.mUpdatePropertyTyep, this.actionHide);
        return;
      }
      BumpFriendsActivity.this.showToast(2131428096);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.BumpFriendsActivity
 * JD-Core Version:    0.6.0
 */