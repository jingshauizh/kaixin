package com.kaixin001.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.LoginActivity;
import com.kaixin001.adapter.NewsDetailAdapter;
import com.kaixin001.adapter.NewsDetailAdapter.OnRelyDetailListerner;
import com.kaixin001.adapter.NewsDetailAdapter.OnViewMoreDetailListerner;
import com.kaixin001.engine.ExtremityCommentEngin;
import com.kaixin001.engine.KXEngine;
import com.kaixin001.engine.NewsDetailVisitorEngine;
import com.kaixin001.engine.PostCommentEngine;
import com.kaixin001.engine.RecordEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.item.DetailItem;
import com.kaixin001.item.ExtremityItemLv1;
import com.kaixin001.item.ExtremityItemLv1.ItemType;
import com.kaixin001.item.FriendInfo;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.item.RecordInfo;
import com.kaixin001.model.ExtremityCommentModel;
import com.kaixin001.model.FaceModel;
import com.kaixin001.model.FriendsModel.Friend;
import com.kaixin001.model.NewsDetailVisitorsModel;
import com.kaixin001.model.RecordModel;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpConnection;
import com.kaixin001.task.PostUpTask;
import com.kaixin001.util.ActivityUtil;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.KXTextUtil;
import com.kaixin001.util.ScreenUtil;
import com.kaixin001.view.FaceKeyboardView;
import com.kaixin001.view.FaceKeyboardView.OnFaceSelectedListener;
import com.kaixin001.view.KXEditTextView;
import com.kaixin001.view.KXFrameImageView;
import com.kaixin001.view.KXFrameImageView.onKCFrameImageListener;
import com.kaixin001.view.KXIntroView;
import com.kaixin001.view.KXIntroView.OnClickLinkListener;
import com.kaixin001.view.PullToRefreshView.PullToRefreshListener;
import com.kaixin001.view.media.KXMediaManager;
import com.kaixin001.view.media.KXMediaView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class NewsDetailFragment extends BaseFragment
  implements View.OnClickListener, PullToRefreshView.PullToRefreshListener, NewsDetailAdapter.OnViewMoreDetailListerner, NewsDetailAdapter.OnRelyDetailListerner
{
  public static final int COMMENT_ERROR_CLOSE = -5;
  public static final int NEWS_DETAIL_CHANGED_CODE = 1300;
  public static final String RESULT_STRING_KEY = "content";
  public static final String TAG_AT = "@";
  public static final String TAG_NAME_LEFT = "(#";
  public static final String TAG_NAME_RIGHT = "#)";
  private static int maxLength;
  private NewsDetailAdapter adapter;
  private String cid;
  private ExtremityCommentModel commentModel;
  private int commentStep = 10;
  private CommentTask commentTask;
  private KXEngine curEngine;
  private KXEditTextView evContent;
  private ArrayList<FriendsModel.Friend> friendslist = new ArrayList();
  private int from = 0;
  private int index1 = -1;
  private boolean isInitingComments;
  private boolean isInitingReposts;
  private ExtremityItemLv1 itemLv1Temp;
  private ListView lvReferList;
  private int mAddComment = 0;
  private int mAddForward = 0;
  private int mAddZan = 0;
  private ImageView mAtBtn = null;
  private View mBottomExtendLayout = null;
  private ImageView mCheckBox = null;
  private KXIntroView mContent;
  private boolean mDataChanged;
  private DetailItem mDetailItem;
  private ImageView mFaceBtn = null;
  private FaceKeyboardView mFaceKeyboardView;
  private ImageView mForwardBtn = null;
  private ArrayList<FriendInfo> mFriends = null;
  GetRecordDetailTask mGetDetailTask;
  GetVisitorsTask mGetVisitorTask;
  private View mHeaderView;
  private KXIntroView.OnClickLinkListener mIntroViewListener = new KXIntroView.OnClickLinkListener()
  {
    public void onClick(KXLinkInfo paramKXLinkInfo)
    {
      if ((paramKXLinkInfo.isStar()) || (paramKXLinkInfo.isUserName()))
      {
        String str1 = paramKXLinkInfo.getId();
        String str2 = paramKXLinkInfo.getContent();
        IntentUtil.showHomeFragment(NewsDetailFragment.this, str1, str2);
      }
      do
      {
        return;
        if (paramKXLinkInfo.isUrlLink())
        {
          IntentUtil.showWebPage(NewsDetailFragment.this.getActivity(), NewsDetailFragment.this, paramKXLinkInfo.getId(), null);
          return;
        }
        if (paramKXLinkInfo.isLbsPoi())
        {
          IntentUtil.showLbsPositionDetailFragment(NewsDetailFragment.this, paramKXLinkInfo.getId(), paramKXLinkInfo.getContent(), "", "");
          return;
        }
        if (!paramKXLinkInfo.isVideo())
          continue;
        NewsDetailFragment.this.mVideoPressed = true;
        IntentUtil.showVideoPage(NewsDetailFragment.this.getActivity(), paramKXLinkInfo.getId(), paramKXLinkInfo.getType(), paramKXLinkInfo.getContent());
        return;
      }
      while (!paramKXLinkInfo.isTopic());
      IntentUtil.showTopicGroupActivity(NewsDetailFragment.this, paramKXLinkInfo.getId());
    }
  };
  private LinearLayout mLayoutProgressBar = null;
  private ListView mListView;
  private ImageView mLogo;
  private ImageView mMapPic;
  private KXMediaView mMediaView;
  private KXIntroView mName;
  private HashMap<String, Bitmap> mNameBmpMap = new HashMap();
  private int mOrientation = 1;
  private ImageView mPic;
  private KXIntroView mPlace;
  private ImageView mPlusBtn = null;
  private ProgressDialog mProgressDialog;
  private boolean mQuietChecked = false;
  private ImageView mSendBtn = null;
  private boolean mShowRepostList = false;
  private KXIntroView mSubContent;
  private View mSubLayout;
  private KXMediaView mSubMediaView;
  private KXIntroView mSubName;
  private ImageView mSubPic;
  private ImageView mSubVideoPic;
  private TextView mTime;
  private TextView mTitle;
  private PostUpTask mUpTask;
  private ImageView mVideoPic;
  private boolean mVideoPressed;
  private NewsDetailVisitorsModel mVisitorsModel;
  private ImageView mZanBtn = null;
  private int mode = 0;
  private ReferListAdapter referAdapter;
  private int repostStep = 10;
  private RepostsTask repostsTask;
  private String strWhisper = "0";
  private SubmitTask submitTask;

  static
  {
    if (!NewsDetailFragment.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      maxLength = 280;
      return;
    }
  }

  private void actionAt()
  {
    Intent localIntent = new Intent(getActivity(), FriendsFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putInt("MODE", 2);
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragmentForResult(this, localIntent, 4, 1);
  }

  private void actionCheckQuiet()
  {
    if (this.mQuietChecked)
    {
      this.mQuietChecked = false;
      this.mCheckBox.setBackgroundResource(2130838018);
      return;
    }
    this.mQuietChecked = true;
    this.mCheckBox.setBackgroundResource(2130838019);
  }

  private void actionComment()
  {
    if (checkIsLook());
    do
    {
      return;
      if (!TextUtils.isEmpty(String.valueOf(this.evContent.getText()).trim()))
        continue;
      DialogUtil.showMsgDlgLiteConfirm(getActivity(), 2131427456, null);
      return;
    }
    while (!super.checkNetworkAndHint(true));
    this.mFaceBtn.setEnabled(false);
    this.mAtBtn.setEnabled(false);
    this.mSendBtn.setEnabled(false);
    if (this.mQuietChecked);
    for (this.strWhisper = "1"; ; this.strWhisper = "0")
    {
      String str1 = getResources().getString(2131428506);
      this.mProgressDialog = ProgressDialog.show(getActivity(), null, str1);
      hideInputMethod();
      this.submitTask = new SubmitTask(null);
      String str2 = KXTextUtil.tranformAtFriend(this.evContent.getText().toString());
      this.submitTask.execute(new String[] { str2 });
      return;
    }
  }

  private void actionFinish()
  {
    if (finishAndRedirect())
      return;
    finish();
  }

  private void actionForward()
  {
    if (checkIsLook())
      return;
    NewsInfo localNewsInfo = this.mDetailItem.getTagNewsInfo();
    if ((localNewsInfo != null) && (!TextUtils.isEmpty(localNewsInfo.mFpri)) && (localNewsInfo.mFpri.equals("0")))
    {
      DialogUtil.showMsgDlgStdConfirm(getActivity(), 2131427329, 2131427744, null);
      return;
    }
    Intent localIntent = new Intent(getActivity(), ForwardWeiboFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putSerializable("recordInfo", this.mDetailItem.getTagRecordInfo());
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragmentForResult(this, localIntent, 1201, 3);
  }

  private void actionPlus()
  {
    if (checkIsLook())
      return;
    if (this.mBottomExtendLayout.getVisibility() == 8)
    {
      this.mPlusBtn.setImageResource(2130838272);
      this.mBottomExtendLayout.setVisibility(0);
      return;
    }
    this.mPlusBtn.setImageResource(2130838271);
    this.mBottomExtendLayout.setVisibility(8);
  }

  private void actionPraise()
  {
    if (checkIsLook());
    NewsInfo localNewsInfo;
    while (true)
    {
      return;
      localNewsInfo = this.mDetailItem.getTagNewsInfo();
      if (((localNewsInfo != null) && (localNewsInfo.mHasUp.booleanValue())) || (this.mDetailItem.isHasUp()))
      {
        showToast(-1, 2131427845);
        return;
      }
      if (this.mUpTask == null)
        break;
      if ((this.mUpTask.getStatus() != AsyncTask.Status.FINISHED) && (!this.mUpTask.isCancelled()))
        continue;
      this.mUpTask = null;
    }
    if (localNewsInfo == null)
    {
      localNewsInfo = new NewsInfo();
      localNewsInfo.mNtype = this.mDetailItem.getType();
      localNewsInfo.mNewsId = this.mDetailItem.getId();
      localNewsInfo.mFuid = this.mDetailItem.getFuid();
    }
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = localNewsInfo;
    this.mUpTask = new PostUpTask(this)
    {
      protected void onPostExecute(Integer paramInteger)
      {
        if (paramInteger.intValue() == 1)
        {
          NewsDetailFragment.this.addMyLogo(1);
          NewsDetailFragment.this.mDetailItem.setHasUp(true);
        }
      }
    };
    this.mUpTask.showSendingInfo(false);
    this.mUpTask.execute(arrayOfObject);
    this.mDataChanged = true;
    this.mAddZan = 1;
  }

  private void actionSmile()
  {
    if (this.mFaceKeyboardView.getVisibility() == 0)
    {
      this.mFaceBtn.setImageResource(2130838130);
      showFaceKeyboardView(false);
      ActivityUtil.showInputMethod(getActivity());
      return;
    }
    this.mFaceBtn.setImageResource(2130838135);
    showFaceKeyboardView(true);
  }

  private void actionViewBigPic(String paramString1, String paramString2)
  {
    Intent localIntent = new Intent(getActivity(), NewsDetailPictureFragment.class);
    localIntent.putExtra("small", paramString1);
    localIntent.putExtra("large", paramString2);
    localIntent.putExtra("action", "com.kaixin001.VIEW_SINGLE_PHOTO");
    AnimationUtil.startFragment(this, localIntent, 5);
  }

  private void addMyLogo(int paramInt)
  {
    if (this.mFriends == null)
      this.mFriends = new ArrayList();
    User localUser = User.getInstance();
    if ((localUser == null) || (TextUtils.isEmpty(localUser.getUID())))
      return;
    Iterator localIterator = this.mFriends.iterator();
    boolean bool = localIterator.hasNext();
    int i = 0;
    if (!bool);
    while (true)
    {
      if (i == 0)
      {
        FriendInfo localFriendInfo = new FriendInfo(localUser.getRealName(), localUser.getUID(), localUser.getLogo());
        if (paramInt == 1)
          localFriendInfo.setType("like");
        this.mFriends.add(0, localFriendInfo);
      }
      constructViews();
      this.adapter.notifyDataSetChanged();
      return;
      if (!((FriendInfo)localIterator.next()).getFuid().equals(localUser.getUID()))
        break;
      i = 1;
    }
  }

  private boolean checkIsLook()
  {
    if (User.getInstance().GetLookAround())
    {
      FragmentActivity localFragmentActivity = getActivity();
      String[] arrayOfString = new String[2];
      arrayOfString[0] = getString(2131427338);
      arrayOfString[1] = getString(2131427339);
      DialogUtil.showSelectListDlg(localFragmentActivity, 2131427729, arrayOfString, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          switch (paramInt)
          {
          default:
            return;
          case 0:
            Intent localIntent2 = new Intent(NewsDetailFragment.this.getActivity(), LoginActivity.class);
            NewsDetailFragment.this.startActivity(localIntent2);
            NewsDetailFragment.this.getActivity().finish();
            return;
          case 1:
          }
          Intent localIntent1 = new Intent(NewsDetailFragment.this.getActivity(), LoginActivity.class);
          Bundle localBundle = new Bundle();
          localBundle.putInt("regist", 1);
          localIntent1.putExtras(localBundle);
          NewsDetailFragment.this.startActivity(localIntent1);
          NewsDetailFragment.this.getActivity().finish();
        }
      }
      , 1);
      return true;
    }
    return false;
  }

  private void clearCommentTask()
  {
    if ((this.commentTask != null) && (!this.commentTask.isCancelled()) && (!this.commentTask.getStatus().equals(AsyncTask.Status.FINISHED)))
      this.commentTask.cancel(true);
    this.commentTask = null;
  }

  private void clearRepostTask()
  {
    if ((this.repostsTask != null) && (!this.repostsTask.isCancelled()) && (!this.repostsTask.getStatus().equals(AsyncTask.Status.FINISHED)))
      this.repostsTask.cancel(true);
    this.repostsTask = null;
  }

  private void constructViews()
  {
    if (this.mFriends == null)
      return;
    LinearLayout localLinearLayout1 = (LinearLayout)this.mHeaderView.findViewById(2131363158);
    localLinearLayout1.removeAllViews();
    LinearLayout localLinearLayout2 = new LinearLayout(getActivity());
    TableLayout.LayoutParams localLayoutParams = new TableLayout.LayoutParams();
    localLayoutParams.width = -1;
    localLayoutParams.height = -2;
    localLinearLayout2.setLayoutParams(localLayoutParams);
    for (int i = -1 + this.mFriends.size(); ; i--)
    {
      if (i < 0)
      {
        localLinearLayout1.addView(localLinearLayout2);
        return;
      }
      View localView = getActivity().getLayoutInflater().inflate(2130903242, null, false);
      ImageView localImageView1 = (ImageView)localView.findViewById(2131362697);
      ImageView localImageView2 = (ImageView)localView.findViewById(2131362698);
      FriendInfo localFriendInfo = (FriendInfo)this.mFriends.get(i);
      if (localFriendInfo.getType().equals("like"))
        localView.findViewById(2131362699).setVisibility(0);
      displayRoundPicture(localImageView1, localFriendInfo.getFlogo(), ImageDownloader.RoundCornerType.hdpi_small, 2130838334);
      localImageView2.setVisibility(0);
      localLinearLayout2.addView(localView, 0);
      localView.setOnClickListener(new View.OnClickListener(localFriendInfo)
      {
        public void onClick(View paramView)
        {
          IntentUtil.showHomeFragment(NewsDetailFragment.this, this.val$friend.getFuid(), this.val$friend.getName(), this.val$friend.getFlogo());
        }
      });
    }
  }

  private void decide2ShowList()
  {
    if ((!this.isInitingComments) && (!this.isInitingReposts))
    {
      this.adapter.isIniting = false;
      this.adapter.notifyDataSetChanged();
    }
  }

  private ArrayList<KXLinkInfo> geNameLinkInfo(DetailItem paramDetailItem)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[|s|]").append(paramDetailItem.getFname()).append("[|m|]").append(paramDetailItem.getFuid()).append("[|m|]").append(getUserType(paramDetailItem.getUserType())).append("[|e|]");
    return NewsInfo.makeIntroList(localStringBuffer.toString());
  }

  private String getUserType(String paramString)
  {
    if ("1".equals(paramString))
      return "-1";
    return "0";
  }

  private void hideInputMethod()
  {
    View localView = getActivity().getCurrentFocus();
    if (localView != null)
      ((InputMethodManager)getActivity().getSystemService("input_method")).hideSoftInputFromWindow(localView.getWindowToken(), 2);
    this.mFaceKeyboardView.setVisibility(8);
  }

  private void initBottomView(View paramView)
  {
    this.evContent = ((KXEditTextView)paramView.findViewById(2131362857));
    this.mSendBtn = ((ImageView)paramView.findViewById(2131363119));
    this.mPlusBtn = ((ImageView)paramView.findViewById(2131363118));
    this.mFaceBtn = ((ImageView)paramView.findViewById(2131363121));
    this.mFaceBtn.setImageResource(2130838130);
    this.mCheckBox = ((ImageView)paramView.findViewById(2131363123));
    this.mAtBtn = ((ImageView)paramView.findViewById(2131363122));
    this.mBottomExtendLayout = paramView.findViewById(2131363120);
    this.mPlusBtn.setOnClickListener(this);
    this.mSendBtn.setOnClickListener(this);
    this.mFaceBtn.setOnClickListener(this);
    this.mAtBtn.setOnClickListener(this);
    this.mCheckBox.setOnClickListener(this);
    this.mAtBtn.requestFocus();
    this.evContent.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramEditable)
      {
      }

      public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
      }

      public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
        if (NewsDetailFragment.this.isNeedReturn())
          return;
        if (TextUtils.isEmpty(String.valueOf(NewsDetailFragment.this.evContent.getText()).trim()))
        {
          NewsDetailFragment.this.mSendBtn.setImageResource(2130838280);
          return;
        }
        NewsDetailFragment.this.mSendBtn.setImageResource(2130838281);
      }
    });
    this.evContent.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View paramView, boolean paramBoolean)
      {
        if (paramBoolean)
        {
          if (TextUtils.isEmpty(String.valueOf(NewsDetailFragment.this.evContent.getText()).trim()))
          {
            NewsDetailFragment.this.mSendBtn.setImageResource(2130838280);
            return;
          }
          NewsDetailFragment.this.mSendBtn.setImageResource(2130838281);
          return;
        }
        NewsDetailFragment.this.mSendBtn.setImageResource(2130838280);
      }
    });
    this.evContent.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        NewsDetailFragment.this.showFaceKeyboardView(false);
      }
    });
  }

  private void initHeadView()
  {
    this.mHeaderView = getActivity().getLayoutInflater().inflate(2130903243, null);
    this.mHeaderView.setOnClickListener(this);
    this.mLogo = ((ImageView)this.mHeaderView.findViewById(2131363127));
    this.mLogo.setOnClickListener(this);
    this.mName = ((KXIntroView)this.mHeaderView.findViewById(2131363129));
    this.mName.setOnClickLinkListener(this.mIntroViewListener);
    this.mTime = ((TextView)this.mHeaderView.findViewById(2131363130));
    this.mTitle = ((TextView)this.mHeaderView.findViewById(2131363132));
    this.mPlace = ((KXIntroView)this.mHeaderView.findViewById(2131363134));
    this.mPlace.setOnClickLinkListener(this.mIntroViewListener);
    this.mContent = ((KXIntroView)this.mHeaderView.findViewById(2131363135));
    this.mContent.setOnClickLinkListener(this.mIntroViewListener);
    KXIntroView localKXIntroView = this.mContent;
    boolean bool;
    int i;
    label454: label587: ImageView localImageView;
    if (this.mDetailItem.getDetailType() == 1)
    {
      bool = true;
      localKXIntroView.setIsLbs(bool);
      this.mVideoPic = ((ImageView)this.mHeaderView.findViewById(2131363136));
      this.mPic = ((ImageView)this.mHeaderView.findViewById(2131363138));
      this.mPic.setOnClickListener(this);
      this.mMapPic = ((ImageView)this.mHeaderView.findViewById(2131363144));
      this.mMapPic.setOnClickListener(this);
      this.mMediaView = ((KXMediaView)this.mHeaderView.findViewById(2131363143));
      this.mSubMediaView = ((KXMediaView)this.mHeaderView.findViewById(2131363156));
      this.mSubLayout = this.mHeaderView.findViewById(2131363145);
      this.mSubName = ((KXIntroView)this.mHeaderView.findViewById(2131363147));
      this.mSubName.setOnClickLinkListener(this.mIntroViewListener);
      this.mSubContent = ((KXIntroView)this.mHeaderView.findViewById(2131363148));
      this.mSubContent.setOnClickLinkListener(this.mIntroViewListener);
      this.mSubVideoPic = ((ImageView)this.mHeaderView.findViewById(2131363149));
      this.mSubPic = ((ImageView)this.mHeaderView.findViewById(2131363151));
      this.mSubPic.setOnClickListener(this);
      displayRoundPicture(this.mLogo, this.mDetailItem.getFLogo(), ImageDownloader.RoundCornerType.hdpi_small, 2130838334);
      if ((!TextUtils.isEmpty(this.mDetailItem.getSubFname())) || (!TextUtils.isEmpty(this.mDetailItem.getSubIntro())))
        break label935;
      i = 0;
      if (this.mDetailItem.getDetailType() != 0)
        break label982;
      this.mTitle.setVisibility(8);
      this.mPlace.setVisibility(8);
      setText(this.mContent, this.mDetailItem.getIntro());
      if ((this.mDetailItem.getTagRecordInfo() == null) || (this.mDetailItem.getTagRecordInfo().mMediaInfo == null) || (this.mDetailItem.getTagRecordInfo().mAudioRecord != 1))
        break label940;
      this.mMediaView.setVisibility(0);
      this.mHeaderView.findViewById(2131363141).setVisibility(0);
      this.mHeaderView.findViewById(2131363142).setVisibility(0);
      this.mMediaView.setMediaData(this.mDetailItem.getTagRecordInfo().mMediaInfo);
      ArrayList localArrayList2 = geNameLinkInfo(this.mDetailItem);
      this.mName.setTitleList(localArrayList2);
      this.mTime.setText(this.mDetailItem.getStime());
      if (!TextUtils.isEmpty(this.mDetailItem.getPicLarge()))
        break label1055;
      this.mPic.setVisibility(8);
      if (i != 0)
        break label1270;
      localImageView = this.mVideoPic;
      label652: if (("1018".equals(this.mDetailItem.getType())) && (this.mDetailItem.getVideoSnapshotLIst() != null) && (this.mDetailItem.getVideoSnapshotLIst().size() > 0))
      {
        localImageView.setVisibility(0);
        ViewGroup.LayoutParams localLayoutParams2 = localImageView.getLayoutParams();
        if (localLayoutParams2 != null)
        {
          float f2 = getResources().getDisplayMetrics().density;
          localLayoutParams2.height = (int)(382.0F * (localLayoutParams2.width - 20.0F * f2) / 600.0F);
        }
        localImageView.setLayoutParams(localLayoutParams2);
        String str3 = (String)this.mDetailItem.getVideoSnapshotLIst().get(0);
        showVideoThumbnail(localImageView, str3);
        6 local6 = new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            label10: ArrayList localArrayList;
            if (NewsDetailFragment.this.mVideoPressed)
              return;
            else
              do
                localArrayList = NewsInfo.makeIntroList(NewsDetailFragment.this.mDetailItem.getVideoIntro());
              while (localArrayList == null);
            for (int i = 0; ; i++)
            {
              if (i >= localArrayList.size())
              {
                if (i >= localArrayList.size())
                  break label10;
                NewsDetailFragment.this.mVideoPressed = true;
                IntentUtil.showVideoPage(NewsDetailFragment.this.getActivity(), ((KXLinkInfo)localArrayList.get(i)).getId(), ((KXLinkInfo)localArrayList.get(i)).getType(), ((KXLinkInfo)localArrayList.get(i)).getContent());
                return;
              }
              if (((KXLinkInfo)localArrayList.get(i)).isVideo())
                break;
            }
          }
        };
        localImageView.setOnClickListener(local6);
      }
      if (i != 0)
        break label1279;
      this.mSubLayout.setVisibility(8);
    }
    label935: label940: label982: label1270: label1279: 
    do
    {
      if ((this.mPic.getVisibility() != 0) && (this.mDetailItem.getDetailType() == 1) && (!TextUtils.isEmpty(this.mDetailItem.getMapUrl())))
      {
        localImageView.setVisibility(0);
        ViewGroup.LayoutParams localLayoutParams1 = this.mMapPic.getLayoutParams();
        if (localLayoutParams1 != null)
        {
          float f1 = getResources().getDisplayMetrics().density;
          localLayoutParams1.height = (int)(230.0F * (localLayoutParams1.width - 20.0F * f1) / 600.0F);
        }
        this.mMapPic.setLayoutParams(localLayoutParams1);
        displayPicture(this.mMapPic, this.mDetailItem.getMapUrl(), 2130838784);
      }
      return;
      bool = false;
      break;
      i = 1;
      break label454;
      this.mMediaView.setVisibility(8);
      this.mHeaderView.findViewById(2131363141).setVisibility(8);
      this.mHeaderView.findViewById(2131363142).setVisibility(8);
      break label587;
      if (this.mDetailItem.getDetailType() != 1)
        break label587;
      this.mTitle.setVisibility(8);
      setText(this.mContent, this.mDetailItem.getIntro());
      ArrayList localArrayList1 = NewsInfo.makeTitleList(this.mDetailItem.getLocation());
      this.mPlace.setTitleList(localArrayList1);
      this.mHeaderView.findViewById(2131363133).setVisibility(0);
      break label587;
      KXFrameImageView localKXFrameImageView1 = (KXFrameImageView)this.mPic;
      int k;
      if (localKXFrameImageView1 != null)
      {
        if ((this.mDetailItem.getTagRecordInfo() == null) || (this.mDetailItem.getTagRecordInfo().mMediaInfo == null) || (this.mDetailItem.getTagRecordInfo().mAudioRecord != 1))
          break label1198;
        k = 1;
        if (k != 0)
          break label1204;
        localKXFrameImageView1.setStartCrop(true);
        localKXFrameImageView1.setMaxShowHei(dipToPx(302.0F));
        View localView7 = this.mHeaderView.findViewById(2131363140);
        View localView8 = this.mHeaderView.findViewById(2131363139);
        4 local4 = new KXFrameImageView.onKCFrameImageListener(localView7, localView8)
        {
          public void onImageCreate(boolean paramBoolean)
          {
            int i;
            View localView2;
            int j;
            if (this.val$imgLongView != null)
            {
              View localView1 = this.val$imgLongView;
              if (!paramBoolean)
                break label44;
              i = 0;
              localView1.setVisibility(i);
              localView2 = this.val$imgLongViewSpace;
              j = 0;
              if (!paramBoolean)
                break label50;
            }
            while (true)
            {
              localView2.setVisibility(j);
              return;
              label44: i = 8;
              break;
              label50: j = 8;
            }
          }
        };
        localKXFrameImageView1.setonKCFrameImageListener(local4);
      }
      while (true)
      {
        displayPicture(this.mPic, this.mDetailItem.getPicLarge(), 2130838784);
        break;
        k = 0;
        break label1109;
        localKXFrameImageView1.setStartCrop(true);
        localKXFrameImageView1.setMaxShowHei(dipToPx(170.0F));
        View localView5 = this.mHeaderView.findViewById(2131363140);
        View localView6 = this.mHeaderView.findViewById(2131363139);
        5 local5 = new KXFrameImageView.onKCFrameImageListener(localView5, localView6)
        {
          public void onImageCreate(boolean paramBoolean)
          {
            int i = 8;
            int j;
            int k;
            label41: View localView3;
            if (this.val$imgLongView != null)
            {
              View localView1 = this.val$imgLongView;
              if (!paramBoolean)
                break label73;
              j = 0;
              localView1.setVisibility(j);
              View localView2 = this.val$imgLongViewSpace;
              if (!paramBoolean)
                break label79;
              k = 0;
              localView2.setVisibility(k);
              localView3 = NewsDetailFragment.this.mHeaderView.findViewById(2131363142);
              if (!paramBoolean)
                break label85;
            }
            while (true)
            {
              localView3.setVisibility(i);
              return;
              label73: j = i;
              break;
              label79: k = i;
              break label41;
              label85: i = 0;
            }
          }
        };
        localKXFrameImageView1.setonKCFrameImageListener(local5);
      }
      localImageView = this.mSubVideoPic;
      break label652;
    }
    while (this.mDetailItem.getDetailType() != 0);
    label1055: label1109: if ((this.mDetailItem.getTagRecordInfo() != null) && (this.mDetailItem.getTagRecordInfo().mSubMediaInfo != null) && (this.mDetailItem.getTagRecordInfo().mAudioRecord == 1))
    {
      this.mSubContent.setVisibility(0);
      String str2 = this.mDetailItem.getSubIntro();
      if (!TextUtils.isEmpty(this.mDetailItem.getSubLocation()))
        str2 = str2 + this.mDetailItem.getSubLocation();
      setText(this.mSubContent, str2);
      this.mSubMediaView.setVisibility(0);
      this.mHeaderView.findViewById(2131363154).setVisibility(0);
      this.mHeaderView.findViewById(2131363155).setVisibility(0);
      this.mSubMediaView.setMediaData(this.mDetailItem.getTagRecordInfo().mSubMediaInfo);
    }
    while (true)
    {
      label1198: label1204: setText(this.mSubName, this.mDetailItem.getSubFname());
      if (!TextUtils.isEmpty(this.mDetailItem.getSubPicLarge()))
        break label1595;
      this.mSubPic.setVisibility(8);
      break;
      this.mSubContent.setVisibility(0);
      String str1 = this.mDetailItem.getSubIntro();
      if (!TextUtils.isEmpty(this.mDetailItem.getSubLocation()))
        str1 = str1 + this.mDetailItem.getSubLocation();
      setText(this.mSubContent, str1);
      this.mSubMediaView.setVisibility(8);
      this.mHeaderView.findViewById(2131363154).setVisibility(8);
      this.mHeaderView.findViewById(2131363155).setVisibility(8);
    }
    label1595: KXFrameImageView localKXFrameImageView2 = (KXFrameImageView)this.mSubPic;
    int j;
    if (localKXFrameImageView2 != null)
    {
      if ((this.mDetailItem.getTagRecordInfo() == null) || (this.mDetailItem.getTagRecordInfo().mSubMediaInfo == null) || (this.mDetailItem.getTagRecordInfo().mAudioRecord != 1))
        break label1738;
      j = 1;
      label1649: if (j != 0)
        break label1744;
      localKXFrameImageView2.setStartCrop(true);
      localKXFrameImageView2.setMaxShowHei(dipToPx(302.0F));
      View localView3 = this.mHeaderView.findViewById(2131363153);
      View localView4 = this.mHeaderView.findViewById(2131363152);
      7 local7 = new KXFrameImageView.onKCFrameImageListener(localView3, localView4)
      {
        public void onImageCreate(boolean paramBoolean)
        {
          int i;
          View localView2;
          int j;
          if (this.val$imgLongView != null)
          {
            View localView1 = this.val$imgLongView;
            if (!paramBoolean)
              break label44;
            i = 0;
            localView1.setVisibility(i);
            localView2 = this.val$imgLongViewSpace;
            j = 0;
            if (!paramBoolean)
              break label50;
          }
          while (true)
          {
            localView2.setVisibility(j);
            return;
            label44: i = 8;
            break;
            label50: j = 8;
          }
        }
      };
      localKXFrameImageView2.setonKCFrameImageListener(local7);
    }
    while (true)
    {
      displayPicture(this.mSubPic, this.mDetailItem.getSubPicLarge(), 2130838784);
      break;
      label1738: j = 0;
      break label1649;
      label1744: localKXFrameImageView2.setStartCrop(true);
      localKXFrameImageView2.setMaxShowHei(dipToPx(170.0F));
      View localView1 = this.mHeaderView.findViewById(2131363153);
      View localView2 = this.mHeaderView.findViewById(2131363152);
      8 local8 = new KXFrameImageView.onKCFrameImageListener(localView1, localView2)
      {
        public void onImageCreate(boolean paramBoolean)
        {
          int i = 8;
          int j;
          int k;
          label41: View localView3;
          if (this.val$imgLongView != null)
          {
            View localView1 = this.val$imgLongView;
            if (!paramBoolean)
              break label73;
            j = 0;
            localView1.setVisibility(j);
            View localView2 = this.val$imgLongViewSpace;
            if (!paramBoolean)
              break label79;
            k = 0;
            localView2.setVisibility(k);
            localView3 = NewsDetailFragment.this.mHeaderView.findViewById(2131363155);
            if (!paramBoolean)
              break label85;
          }
          while (true)
          {
            localView3.setVisibility(i);
            return;
            label73: j = i;
            break;
            label79: k = i;
            break label41;
            label85: i = 0;
          }
        }
      };
      localKXFrameImageView2.setonKCFrameImageListener(local8);
    }
  }

  private void initView(boolean paramBoolean, View paramView)
  {
    this.mLayoutProgressBar = ((LinearLayout)paramView.findViewById(2131363125));
    if (this.mDetailItem.getDetailType() == 0)
    {
      if ((this.mDetailItem.getTagRecordInfo() == null) || ((this.mDetailItem.getTagRecordInfo().mAudioRecord == -1) && (paramBoolean)))
      {
        setTitleBar(paramView);
        showLoading(true);
        refreshRecordDetailData();
        return;
      }
      if (this.mDetailItem.getFname() == null)
        DetailItem.initWithRecordInfo(this.mDetailItem, this.mDetailItem.getTagRecordInfo());
    }
    setTitleBar(paramView);
    initHeadView();
    initBottomView(paramView);
    this.mFaceKeyboardView = ((FaceKeyboardView)paramView.findViewById(2131361976));
    this.mFaceKeyboardView.setOnFaceSelectedListener(new FaceKeyboardView.OnFaceSelectedListener()
    {
      public void FaceSelected(int paramInt, String paramString, Bitmap paramBitmap)
      {
        switch (paramInt)
        {
        default:
          NewsDetailFragment.this.insertFaceIcon(paramString, paramBitmap);
        case -2:
        case -1:
        }
        int i;
        do
        {
          return;
          i = Selection.getSelectionStart(NewsDetailFragment.this.evContent.getEditableText());
        }
        while (i <= 0);
        String str1 = NewsDetailFragment.this.evContent.getText().toString();
        ArrayList localArrayList = FaceModel.getInstance().getAllFaceStringSortByLength();
        int j = 1;
        Iterator localIterator = localArrayList.iterator();
        if (!localIterator.hasNext());
        while (true)
        {
          NewsDetailFragment.this.evContent.getText().delete(i - j, i);
          return;
          String str2 = (String)localIterator.next();
          if (!str1.endsWith(str2))
            break;
          j = str2.length();
        }
      }
    });
    this.mFaceKeyboardView.init(getActivity());
    showFaceKeyboardView(false);
    this.mListView = ((ListView)paramView.findViewById(2131363115));
    if (this.adapter == null)
      this.adapter = new NewsDetailAdapter(this, this.mDetailItem.getType(), new ArrayList(), new ArrayList(), this, this, this.mDetailItem.getCommentNum(), this.mDetailItem.getForwordNum(), this.mDetailItem.getTagNewsInfo(), this.mHeaderView);
    this.mListView.addHeaderView(this.mHeaderView);
    this.mListView.setAdapter(this.adapter);
    this.mListView.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
      {
        switch (paramMotionEvent.getAction())
        {
        default:
        case 0:
        case 1:
        }
        while (true)
        {
          return false;
          if (paramView.hasFocus())
            continue;
          paramView.requestFocus();
        }
      }
    });
    if (this.mFriends == null)
    {
      showLoading(true);
      refreshVisitorsData();
      return;
    }
    refreshView();
  }

  private void insertFriendIntoContent(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    if ((paramInt1 < 0) || (paramInt1 > paramInt2) || (TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2)));
    String str;
    Bitmap localBitmap;
    Editable localEditable;
    do
    {
      return;
      str = "@" + paramString1 + "(#" + paramString2 + "#)";
      localBitmap = (Bitmap)this.mNameBmpMap.get(str);
      if (localBitmap == null)
        localBitmap = ImageCache.createStringBitmap("@" + paramString2, this.evContent.getPaint());
      if (localBitmap != null)
        this.mNameBmpMap.put(str, localBitmap);
      localEditable = this.evContent.getText().replace(paramInt1, paramInt2, str + " ");
    }
    while ((1 != this.mOrientation) && (!Setting.getInstance().isShowFaceIconInLandScape()));
    if (paramInt1 + str.length() <= maxLength)
    {
      localEditable.setSpan(new ImageSpan(localBitmap), paramInt1, paramInt1 + str.length(), 33);
      return;
    }
    Toast.makeText(getActivity(), 2131427900, 0).show();
  }

  private void refreshRecordDetailData()
  {
    if (HttpConnection.checkNetworkAvailable(getActivity()) < 0)
      return;
    assert (this.mDetailItem.getId() != null);
    this.mGetDetailTask = new GetRecordDetailTask(null);
    String str1;
    if (this.mDetailItem.getSubId() != null)
    {
      str1 = this.mDetailItem.getSubId();
      if (this.mDetailItem.getSubFuid() == null)
        break label118;
    }
    label118: for (String str2 = this.mDetailItem.getSubFuid(); ; str2 = this.mDetailItem.getFuid())
    {
      String[] arrayOfString = { str1, str2 };
      this.mGetDetailTask.execute(arrayOfString);
      return;
      str1 = this.mDetailItem.getId();
      break;
    }
  }

  private void refreshView()
  {
    this.mFriends = this.mVisitorsModel.getVisitorList();
    this.adapter.setReposts(this.commentModel.getReposts());
    this.adapter.setComments(this.commentModel.getAllCommentsAct());
    showLoading(false);
    constructViews();
    this.adapter.notifyDataSetChanged();
  }

  private void refreshVisitorsData()
  {
    if (HttpConnection.checkNetworkAvailable(getActivity()) < 0)
      return;
    assert (this.mDetailItem.getId() != null);
    this.mGetVisitorTask = new GetVisitorsTask(null);
    String str1;
    String str2;
    if (this.mDetailItem.getSubId() != null)
    {
      str1 = this.mDetailItem.getSubId();
      str2 = this.mDetailItem.getType();
      if (this.mDetailItem.getSubFuid() == null)
        break label132;
    }
    label132: for (String str3 = this.mDetailItem.getSubFuid(); ; str3 = this.mDetailItem.getFuid())
    {
      String[] arrayOfString = { str1, str2, str3 };
      this.mGetVisitorTask.execute(arrayOfString);
      return;
      str1 = this.mDetailItem.getId();
      break;
    }
  }

  private void setText(KXIntroView paramKXIntroView, String paramString)
  {
    if (TextUtils.isEmpty(paramString))
    {
      paramKXIntroView.setVisibility(8);
      return;
    }
    paramKXIntroView.setTitleList(NewsInfo.makeIntroList(paramString));
  }

  private void showFaceKeyboardView(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mFaceKeyboardView.setVisibility(0);
      ActivityUtil.hideInputMethod(getActivity());
      return;
    }
    this.mFaceKeyboardView.setVisibility(8);
  }

  private void showLoading(boolean paramBoolean)
  {
    if (getView() == null);
    do
    {
      return;
      if (paramBoolean)
      {
        this.mLayoutProgressBar.setVisibility(0);
        if (this.mListView != null)
          this.mListView.setVisibility(8);
        getView().findViewById(2131363117).setVisibility(4);
        return;
      }
      this.mLayoutProgressBar.setVisibility(8);
      getView().findViewById(2131363116).setVisibility(0);
      if (this.from == 1)
      {
        this.evContent.requestFocus();
        ((InputMethodManager)getActivity().getSystemService("input_method")).showSoftInput(this.evContent, 1);
        this.from = 0;
      }
      if (this.mListView == null)
        continue;
      this.mListView.setVisibility(0);
    }
    while (this.mDetailItem.getFname() == null);
    getView().findViewById(2131363117).setVisibility(0);
  }

  private void startCommentTask()
  {
    if ((this.commentTask != null) && (!this.commentTask.isCancelled()) && (!this.commentTask.getStatus().equals(AsyncTask.Status.FINISHED)))
      return;
    this.commentTask = new CommentTask();
    this.commentTask.execute(new String[] { "" });
  }

  private void startRepostTask(int paramInt1, int paramInt2)
  {
    if ((this.repostsTask != null) && (!this.repostsTask.isCancelled()) && (!this.repostsTask.getStatus().equals(AsyncTask.Status.FINISHED)))
      return;
    this.repostsTask = new RepostsTask();
    RepostsTask localRepostsTask = this.repostsTask;
    Integer[] arrayOfInteger = new Integer[2];
    arrayOfInteger[0] = Integer.valueOf(paramInt1);
    arrayOfInteger[1] = Integer.valueOf(paramInt2);
    localRepostsTask.execute(arrayOfInteger);
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getAction() == 0);
    try
    {
      int[] arrayOfInt = new int[2];
      this.evContent.getLocationOnScreen(arrayOfInt);
      if (paramMotionEvent.getY() < arrayOfInt[1])
        ActivityUtil.hideInputMethod(getActivity());
      return super.dispatchTouchEvent(paramMotionEvent);
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public void finish()
  {
    Intent localIntent = new Intent();
    localIntent.putExtra("comment", this.mAddComment);
    localIntent.putExtra("forward", this.mAddForward);
    localIntent.putExtra("zan", this.mAddZan);
    setResult(-1, localIntent);
    finishFragment(1300);
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 2);
  }

  public void inputFinish()
  {
    hideInputMethod();
    Intent localIntent = new Intent();
    localIntent.putExtra("content", String.valueOf(this.evContent.getText()));
    this.cid = PostCommentEngine.getInstance().getCommentCid();
    localIntent.putExtra("cid", this.cid);
    if (this.mQuietChecked)
      localIntent.putExtra("mtype", "1");
    while (true)
    {
      setResult(-1, localIntent);
      finishFragment(this.mode);
      ExtremityItemLv1 localExtremityItemLv1 = new ExtremityItemLv1();
      localExtremityItemLv1.content = localIntent.getStringExtra("content");
      localExtremityItemLv1.id = localIntent.getStringExtra("cid");
      localExtremityItemLv1.userIconUrl = User.getInstance().getLogo();
      localExtremityItemLv1.userName = User.getInstance().getRealName();
      localExtremityItemLv1.uid = User.getInstance().getUID();
      localExtremityItemLv1.isMainComment = true;
      localExtremityItemLv1.time = getResources().getString(2131427479);
      localExtremityItemLv1.mytype = localIntent.getStringExtra("mtype");
      localExtremityItemLv1.itemType = ExtremityItemLv1.ItemType.content_comment;
      this.commentModel.addComment(0, localExtremityItemLv1);
      this.adapter.setComments(this.commentModel.getAllCommentsAct());
      NewsDetailAdapter localNewsDetailAdapter = this.adapter;
      localNewsDetailAdapter.commentCount = (1 + localNewsDetailAdapter.commentCount);
      this.adapter.notifyDataSetChanged();
      return;
      localIntent.putExtra("mtype", "0");
    }
  }

  protected void insertFaceIcon(int paramInt)
  {
    ArrayList localArrayList1 = FaceModel.getInstance().getStateFaceStrings();
    ArrayList localArrayList2 = FaceModel.getInstance().getStateFaceIcons();
    String str;
    Editable localEditable;
    int i;
    int j;
    if (localArrayList1 != null)
    {
      str = (String)localArrayList1.get(paramInt);
      localEditable = this.evContent.getText();
      i = Selection.getSelectionStart(localEditable);
      j = Selection.getSelectionEnd(localEditable);
      if ((i == -1) || (j == -1))
      {
        i = localEditable.length();
        j = localEditable.length();
      }
      int k = j - i;
      if (localEditable.length() <= maxLength - str.length() - k)
        break label125;
      Toast.makeText(getActivity(), 2131427899, 0).show();
    }
    label125: 
    do
    {
      return;
      localEditable.replace(i, j, str);
    }
    while ((1 != this.mOrientation) && (!Setting.getInstance().isShowFaceIconInLandScape()));
    localEditable.setSpan(new ImageSpan((Bitmap)localArrayList2.get(paramInt)), i, i + str.length(), 33);
  }

  protected void insertFaceIcon(String paramString, Bitmap paramBitmap)
  {
    if ((TextUtils.isEmpty(paramString)) || (paramBitmap == null));
    Editable localEditable;
    int i;
    do
    {
      return;
      localEditable = this.evContent.getText();
      if (!this.evContent.isFocused())
        this.evContent.requestFocus();
      i = Selection.getSelectionStart(localEditable);
      int j = Selection.getSelectionEnd(localEditable);
      if ((i == -1) || (j == -1))
      {
        i = localEditable.length();
        j = localEditable.length();
      }
      if (localEditable.length() > maxLength - paramString.length())
      {
        Toast.makeText(getActivity(), 2131427899, 0).show();
        return;
      }
      localEditable.replace(i, j, paramString.subSequence(0, paramString.length()));
    }
    while ((1 != this.mOrientation) && (!Setting.getInstance().isShowFaceIconInLandScape()));
    localEditable.setSpan(new ImageSpan(paramBitmap), i, i + paramString.length(), 33);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt2 == -1)
    {
      addMyLogo(0);
      this.mDataChanged = true;
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
      if (paramInt1 != 4)
        break label67;
      String str1 = paramIntent.getStringExtra("fuid");
      String str2 = paramIntent.getStringExtra("fname");
      int i = this.evContent.getSelectionStart();
      insertFriendIntoContent(i, i, str1, str2);
    }
    label67: 
    do
      while (true)
      {
        return;
        if (paramInt1 == 8)
        {
          clearCommentTask();
          clearRepostTask();
          this.adapter.isIniting = true;
          this.adapter.clearData();
          this.commentModel.clearComments();
          this.commentModel.clearReposts();
          this.adapter.notifyDataSetChanged();
          this.isInitingComments = true;
          startCommentTask();
          this.isInitingReposts = true;
          startRepostTask(0, this.repostStep);
          return;
        }
        if (paramInt1 != 2)
          break;
        if (this.commentModel == null)
          continue;
        ExtremityItemLv1 localExtremityItemLv12 = new ExtremityItemLv1();
        localExtremityItemLv12.content = paramIntent.getStringExtra("content");
        localExtremityItemLv12.id = paramIntent.getStringExtra("cid");
        localExtremityItemLv12.userIconUrl = User.getInstance().getLogo();
        localExtremityItemLv12.userName = User.getInstance().getRealName();
        localExtremityItemLv12.uid = User.getInstance().getUID();
        localExtremityItemLv12.isMainComment = true;
        localExtremityItemLv12.time = getResources().getString(2131427479);
        localExtremityItemLv12.mytype = paramIntent.getStringExtra("mtype");
        localExtremityItemLv12.itemType = ExtremityItemLv1.ItemType.content_comment;
        this.commentModel.addComment(0, localExtremityItemLv12);
        this.adapter.setComments(this.commentModel.getAllCommentsAct());
        NewsDetailAdapter localNewsDetailAdapter2 = this.adapter;
        localNewsDetailAdapter2.commentCount = (1 + localNewsDetailAdapter2.commentCount);
        this.adapter.notifyDataSetChanged();
        this.mAddComment = (1 + this.mAddComment);
        return;
      }
    while ((paramInt1 != 1201) || (this.commentModel == null));
    ExtremityItemLv1 localExtremityItemLv11 = new ExtremityItemLv1();
    localExtremityItemLv11.content = paramIntent.getStringExtra("content");
    localExtremityItemLv11.userIconUrl = User.getInstance().getLogo();
    localExtremityItemLv11.userName = User.getInstance().getRealName();
    localExtremityItemLv11.uid = User.getInstance().getUID();
    localExtremityItemLv11.time = getResources().getString(2131427479);
    localExtremityItemLv11.itemType = ExtremityItemLv1.ItemType.cotent_repost;
    this.commentModel.addRepost(localExtremityItemLv11);
    this.adapter.setReposts(this.commentModel.getReposts());
    NewsDetailAdapter localNewsDetailAdapter1 = this.adapter;
    localNewsDetailAdapter1.repostCount = (1 + localNewsDetailAdapter1.repostCount);
    this.adapter.notifyDataSetChanged();
    this.mAddForward = (1 + this.mAddForward);
    this.mHandler.postDelayed(new Runnable()
    {
      public void run()
      {
        if (NewsDetailFragment.this.isNeedReturn())
          return;
        ActivityUtil.hideInputMethod(NewsDetailFragment.this.getActivity());
      }
    }
    , 1000L);
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131362914:
      actionFinish();
      return;
    case 2131363127:
      ActivityUtil.hideInputMethod(getActivity());
      IntentUtil.showHomeFragment(this, this.mDetailItem.getFuid(), this.mDetailItem.getFname());
      return;
    case 2131363144:
      actionViewBigPic(this.mDetailItem.getMapUrl(), this.mDetailItem.getMapUrl());
      return;
    case 2131363138:
      actionViewBigPic(this.mDetailItem.getPicThumbnail(), this.mDetailItem.getPicLarge());
      return;
    case 2131363151:
      actionViewBigPic(this.mDetailItem.getSubPicThumbnail(), this.mDetailItem.getSubPicLarge());
      return;
    case 2131363118:
      actionPlus();
      return;
    case 2131363119:
      actionComment();
      return;
    case 2131363122:
      actionAt();
      return;
    case 2131363121:
      actionSmile();
      return;
    case 2131362928:
      actionPraise();
      return;
    case 2131362924:
      actionForward();
      return;
    case 2131363123:
    }
    actionCheckQuiet();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      this.mDetailItem = ((DetailItem)localBundle.getSerializable("data"));
      this.from = localBundle.getInt("from");
      if ((this.mDetailItem != null) && ("1018".equals(this.mDetailItem.getType())))
        this.mShowRepostList = true;
    }
    this.mVisitorsModel = new NewsDetailVisitorsModel();
    this.commentModel = new ExtremityCommentModel();
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903241, paramViewGroup, false);
  }

  public void onDestroy()
  {
    super.onDestroy();
    cancelTask(this.mGetDetailTask);
    cancelTask(this.commentTask);
    cancelTask(this.repostsTask);
    cancelTask(this.mUpTask);
    cancelTask(this.submitTask);
    cancelTask(this.mGetVisitorTask);
  }

  public void onPause()
  {
    super.onPause();
    KXMediaManager.getInstance().requestStopCurrentMedia();
  }

  public void onReply(ExtremityItemLv1 paramExtremityItemLv1)
  {
    Intent localIntent = new Intent(getActivity(), InputFragment.class);
    Bundle localBundle = new Bundle();
    this.itemLv1Temp = paramExtremityItemLv1;
    localBundle.putInt("mode", 8);
    localBundle.putString("thread_cid", paramExtremityItemLv1.id);
    localBundle.putString("fuid", paramExtremityItemLv1.uid);
    localBundle.putString("mtype", paramExtremityItemLv1.mytype);
    localIntent.putExtras(localBundle);
    startActivityForResult(localIntent, 8);
  }

  public void onResume()
  {
    super.onResume();
    this.mVideoPressed = false;
  }

  public void onUpdate()
  {
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mOrientation = ScreenUtil.getOrientation(getActivity());
    initView(true, paramView);
  }

  public void onViewMoreComments()
  {
    if (this.adapter.commentCount <= 0)
    {
      this.adapter.isloadingComment = false;
      return;
    }
    int i = this.commentModel.getAllCommentsAct().size();
    if ((i > 0) && (((ExtremityItemLv1)this.commentModel.getAllCommentsAct().get(i - 1)).itemType == ExtremityItemLv1.ItemType.footer_viewmore_repost))
      (i - 1);
    startCommentTask();
  }

  public void onViewMoreReposts()
  {
    if (this.adapter.repostCount <= 0)
    {
      this.adapter.isloadingRepost = false;
      return;
    }
    startRepostTask(this.commentModel.getReposts().size(), this.repostStep);
  }

  protected void setTitleBar(View paramView)
  {
    ImageView localImageView = (ImageView)paramView.findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(this);
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    this.mZanBtn = ((ImageView)paramView.findViewById(2131362928));
    this.mZanBtn.setVisibility(0);
    this.mZanBtn.setImageResource(2130838141);
    this.mZanBtn.setOnClickListener(this);
    this.mForwardBtn = ((ImageView)paramView.findViewById(2131362924));
    if (this.mDetailItem.getDetailType() == 1)
      this.mForwardBtn.setVisibility(8);
    while (true)
    {
      TextView localTextView = (TextView)paramView.findViewById(2131362920);
      localTextView.setVisibility(0);
      localTextView.setText(this.mDetailItem.getTypeName(getActivity()));
      return;
      this.mForwardBtn.setVisibility(0);
      this.mForwardBtn.setImageResource(2130839005);
      this.mForwardBtn.setOnClickListener(this);
    }
  }

  class CommentTask extends KXFragment.KXAsyncTask<String, Integer, Boolean>
  {
    CommentTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      try
      {
        List localList = NewsDetailFragment.this.commentModel.getAllCommentsAct();
        String str = "";
        ExtremityItemLv1 localExtremityItemLv1;
        if (localList.size() > 0)
        {
          localExtremityItemLv1 = (ExtremityItemLv1)localList.get(-1 + localList.size());
          if (localExtremityItemLv1.itemType != ExtremityItemLv1.ItemType.footer_viewmore_comment)
            break label147;
        }
        label147: for (str = ((ExtremityItemLv1)localList.get(-2 + localList.size())).id; ; str = localExtremityItemLv1.id)
          return Boolean.valueOf(ExtremityCommentEngin.getInstance().getMoreComments(NewsDetailFragment.this.getActivity(), NewsDetailFragment.this.mDetailItem.getId(), NewsDetailFragment.this.mDetailItem.getType(), NewsDetailFragment.this.mDetailItem.getFuid(), NewsDetailFragment.this.adapter, NewsDetailFragment.this.commentModel, str, NewsDetailFragment.this.commentStep));
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return Boolean.valueOf(false);
    }

    protected void onCancelledA()
    {
      if (NewsDetailFragment.this.isInitingComments)
      {
        NewsDetailFragment.this.isInitingComments = false;
        NewsDetailFragment.this.adapter.setComments(NewsDetailFragment.this.commentModel.getAllCommentsAct());
        NewsDetailFragment.this.decide2ShowList();
        return;
      }
      NewsDetailFragment.this.adapter.isloadingComment = false;
      NewsDetailFragment.this.adapter.setComments(NewsDetailFragment.this.commentModel.getAllCommentsAct());
      NewsDetailFragment.this.adapter.notifyDataSetChanged();
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      if ((paramBoolean == null) || (!paramBoolean.booleanValue()))
        Toast.makeText(NewsDetailFragment.this.getActivity(), 2131427332, 0).show();
      if (NewsDetailFragment.this.isInitingComments)
      {
        NewsDetailFragment.this.isInitingComments = false;
        NewsDetailFragment.this.adapter.setComments(NewsDetailFragment.this.commentModel.getAllCommentsAct());
        NewsDetailFragment.this.decide2ShowList();
        return;
      }
      NewsDetailFragment.this.adapter.isloadingComment = false;
      NewsDetailFragment.this.adapter.setComments(NewsDetailFragment.this.commentModel.getAllCommentsAct());
      NewsDetailFragment.this.adapter.notifyDataSetChanged();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Integer[] paramArrayOfInteger)
    {
    }
  }

  private class GetRecordDetailTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    private GetRecordDetailTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      String str1 = paramArrayOfString[0];
      String str2 = paramArrayOfString[1];
      try
      {
        boolean bool2 = RecordEngine.getInstance().doGetRecordDetailMoreRequest(NewsDetailFragment.this.getActivity(), str1, str2);
        bool1 = bool2;
        return Boolean.valueOf(bool1);
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        while (true)
          boolean bool1 = false;
      }
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      NewsDetailFragment.this.showLoading(false);
      if (paramBoolean == null)
      {
        NewsDetailFragment.this.finish();
        return;
      }
      if (paramBoolean.booleanValue())
      {
        RecordInfo localRecordInfo = RecordModel.getInstance().getRecordInfo();
        NewsDetailFragment.this.mDetailItem.setTagRecordInfo(localRecordInfo);
        NewsDetailFragment.this.initView(false, NewsDetailFragment.this.getView());
        return;
      }
      if (RecordModel.getInstance().getErrorNum() == 4001)
      {
        Toast.makeText(NewsDetailFragment.this.getActivity(), 2131427923, 0).show();
        return;
      }
      Toast.makeText(NewsDetailFragment.this.getActivity(), 2131427752, 0).show();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class GetVisitorsTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    private GetVisitorsTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      String str1 = paramArrayOfString[0];
      String str2 = paramArrayOfString[1];
      String str3 = paramArrayOfString[2];
      boolean bool = false;
      try
      {
        bool = NewsDetailVisitorEngine.getInstance().doGetVisitorsRequest(NewsDetailFragment.this.getActivity(), str1, str2, str3, NewsDetailFragment.this.mVisitorsModel);
        ExtremityCommentEngin.getInstance().getMoreComments(NewsDetailFragment.this.getActivity(), NewsDetailFragment.this.mDetailItem.getId(), NewsDetailFragment.this.mDetailItem.getType(), NewsDetailFragment.this.mDetailItem.getFuid(), NewsDetailFragment.this.adapter, NewsDetailFragment.this.commentModel, "", NewsDetailFragment.this.commentStep);
        if (NewsDetailFragment.this.mShowRepostList)
          ExtremityCommentEngin.getInstance().getMoreReposts(NewsDetailFragment.this.getActivity(), NewsDetailFragment.this.mDetailItem.getId(), NewsDetailFragment.this.adapter, NewsDetailFragment.this.commentModel, 0, NewsDetailFragment.this.repostStep);
        return Boolean.valueOf(bool);
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        while (true)
          localSecurityErrorException.printStackTrace();
      }
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      NewsDetailFragment.this.refreshView();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class ReferListAdapter extends BaseAdapter
  {
    private LayoutInflater inflater;

    ReferListAdapter(Context arg2)
    {
      Context localContext;
      this.inflater = LayoutInflater.from(localContext);
    }

    public int getCount()
    {
      return NewsDetailFragment.this.friendslist.size();
    }

    public Object getItem(int paramInt)
    {
      return NewsDetailFragment.this.friendslist.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null)
      {
        paramView = this.inflater.inflate(2130903202, null);
        ViewHolder localViewHolder = new ViewHolder(null);
        localViewHolder.realNameText = ((TextView)paramView.findViewById(2131362966));
        paramView.setTag(localViewHolder);
      }
      FriendsModel.Friend localFriend = (FriendsModel.Friend)NewsDetailFragment.this.friendslist.get(paramInt);
      ((ViewHolder)paramView.getTag()).realNameText.setText(localFriend.getFname());
      return paramView;
    }

    private class ViewHolder
    {
      public TextView realNameText;

      private ViewHolder()
      {
      }
    }
  }

  class RepostsTask extends KXFragment.KXAsyncTask<Integer, Integer, Boolean>
  {
    RepostsTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(Integer[] paramArrayOfInteger)
    {
      try
      {
        Boolean localBoolean = Boolean.valueOf(ExtremityCommentEngin.getInstance().getMoreReposts(NewsDetailFragment.this.getActivity(), NewsDetailFragment.this.mDetailItem.getId(), NewsDetailFragment.this.adapter, NewsDetailFragment.this.commentModel, paramArrayOfInteger[0].intValue(), paramArrayOfInteger[1].intValue()));
        return localBoolean;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        localSecurityErrorException.printStackTrace();
      }
      return Boolean.valueOf(false);
    }

    protected void onCancelledA()
    {
      if (NewsDetailFragment.this.isInitingReposts)
      {
        NewsDetailFragment.this.isInitingReposts = false;
        NewsDetailFragment.this.adapter.setReposts(NewsDetailFragment.this.commentModel.getReposts());
        NewsDetailFragment.this.decide2ShowList();
        return;
      }
      NewsDetailFragment.this.adapter.isloadingRepost = false;
      NewsDetailFragment.this.adapter.setReposts(NewsDetailFragment.this.commentModel.getReposts());
      NewsDetailFragment.this.adapter.notifyDataSetChanged();
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      if ((paramBoolean == null) || (!paramBoolean.booleanValue()))
        Toast.makeText(NewsDetailFragment.this.getActivity(), 2131427332, 0).show();
      if (NewsDetailFragment.this.isInitingReposts)
      {
        NewsDetailFragment.this.isInitingReposts = false;
        NewsDetailFragment.this.adapter.setReposts(NewsDetailFragment.this.commentModel.getReposts());
        NewsDetailFragment.this.decide2ShowList();
        return;
      }
      NewsDetailFragment.this.adapter.isloadingRepost = false;
      NewsDetailFragment.this.adapter.setReposts(NewsDetailFragment.this.commentModel.getReposts());
      NewsDetailFragment.this.adapter.notifyDataSetChanged();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Integer[] paramArrayOfInteger)
    {
    }
  }

  private class SubmitTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private SubmitTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      if (NewsDetailFragment.this.getActivity() == null)
        return Integer.valueOf(0);
      try
      {
        String str1 = NewsDetailFragment.this.mDetailItem.getType();
        String str2 = NewsDetailFragment.this.mDetailItem.getId();
        String str3 = NewsDetailFragment.this.mDetailItem.getFuid();
        NewsDetailFragment.this.curEngine = PostCommentEngine.getInstance();
        Integer localInteger = Integer.valueOf(PostCommentEngine.getInstance().postComment(NewsDetailFragment.this.getActivity(), str1, str2, str3, NewsDetailFragment.this.strWhisper, paramArrayOfString[0]));
        return localInteger;
      }
      catch (Exception localException)
      {
        KXLog.e("InputActivity", "doInBackground", localException);
      }
      return Integer.valueOf(0);
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if (NewsDetailFragment.this.getActivity() == null)
        return;
      if (NewsDetailFragment.this.mProgressDialog != null)
      {
        NewsDetailFragment.this.mProgressDialog.dismiss();
        NewsDetailFragment.this.mProgressDialog = null;
      }
      NewsDetailFragment.this.curEngine = null;
      int i = paramInteger.intValue();
      if (i == -5);
      for (int j = 2131427471; ; j = 2131427443)
        try
        {
          NewsDetailFragment.this.mFaceBtn.setEnabled(true);
          NewsDetailFragment.this.mAtBtn.setEnabled(true);
          NewsDetailFragment.this.mSendBtn.setEnabled(true);
          if (i != 1)
            break;
          NewsDetailFragment localNewsDetailFragment = NewsDetailFragment.this;
          localNewsDetailFragment.mAddComment = (1 + localNewsDetailFragment.mAddComment);
          Toast.makeText(NewsDetailFragment.this.getActivity(), "评论已发出", 0).show();
          NewsDetailFragment.this.inputFinish();
          NewsDetailFragment.this.evContent.setText("");
          return;
        }
        catch (Resources.NotFoundException localNotFoundException)
        {
          Toast.makeText(NewsDetailFragment.this.getActivity(), j, 0).show();
          return;
        }
      Toast.makeText(NewsDetailFragment.this.getActivity(), j, 0).show();
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
 * Qualified Name:     com.kaixin001.fragment.NewsDetailFragment
 * JD-Core Version:    0.6.0
 */