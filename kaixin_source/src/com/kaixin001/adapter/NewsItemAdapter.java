package com.kaixin001.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.KXEnvironment;
import com.kaixin001.activity.LoginActivity;
import com.kaixin001.activity.SettingActivity;
import com.kaixin001.businesslogic.ShowPhoto;
import com.kaixin001.businesslogic.ShowVoteDetail;
import com.kaixin001.engine.NewsAdvertEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.UpEngine;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.fragment.DiaryDetailFragment;
import com.kaixin001.fragment.ForwardWeiboFragment;
import com.kaixin001.fragment.InputFragment;
import com.kaixin001.fragment.NewsDetailFragment;
import com.kaixin001.fragment.NewsFragment;
import com.kaixin001.fragment.ObjCommentFragment;
import com.kaixin001.fragment.PreviewUploadPhotoFragment;
import com.kaixin001.fragment.RepostListFragment;
import com.kaixin001.fragment.StyleBoxDiaryDetailFragment;
import com.kaixin001.fragment.TruthFragment;
import com.kaixin001.fragment.VoteListFragment;
import com.kaixin001.item.DetailItem;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.item.KaixinPhotoItem;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.item.PhotoItem;
import com.kaixin001.item.RecordInfo;
import com.kaixin001.model.NewsModel;
import com.kaixin001.model.User;
import com.kaixin001.task.KaiXinPKTask;
import com.kaixin001.task.PostUpTask;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.NewsItemViewFactory;
import com.kaixin001.view.GDTAdView;
import com.kaixin001.view.KXInputView.KXInputListener;
import com.kaixin001.view.KXIntroView;
import com.kaixin001.view.KXIntroView.OnClickLinkListener;
import com.kaixin001.view.KXNewsBarView;
import com.kaixin001.view.KXViewPager;
import com.kaixin001.view.NewsItemSearchView;
import com.kaixin001.view.NewsItemView;
import com.kaixin001.view.TopicGroupHeadView;
import com.kaixin001.view.media.KXMediaInfo;
import com.kaixin001.view.media.KXMediaManager;
import com.kaixin001.view.media.KXMediaView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NewsItemAdapter extends ArrayAdapter<NewsInfo>
  implements View.OnClickListener
{
  public static final String ADV_FUID = "-2";
  public static final String GDT_AD_FUID = "-100";
  private static final String SP_TAG_SAVE_FLOW = "need_saveflow_notify";
  private static final String TAG = "NewsItemAdapter";
  public static final String TOPIC_FUID = "-3";
  public KXNewsBarView mAdvView = null;
  public KXViewPager mAdvViewPager = null;
  ShowPhoto mAlbumShowUtil = null;
  private Dialog mAlertDialog = null;
  private View mBlankView = null;
  public CommentOnClickListener mCommentOnClickListener = new CommentOnClickListener();
  private Activity mContext;
  private CacheView mCurrentCache;
  private NewsInfo mCurrentNewsItem;
  private View mFooter;
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  private BaseFragment mFragment;
  private String mFuid = null;
  private GDTAdView mGdtAdview;
  public ImageOnClickListener mImgOnClickListener = new ImageOnClickListener();
  private KXInputView.KXInputListener mInputListener;
  private ArrayList<NewsInfo> mItems;
  private OnViewMoreClickListener mOnViewMoreListener = null;
  private boolean mShowSaveFlowNotify = true;
  ShowVoteDetail mShowVoteDetailUtil = null;
  public TopicGroupHeadView mTopicHeadView = null;
  private boolean mVideoPressed = false;
  private PhotoPostUpTask photoupTask;
  private PostUpTask upTask;

  public NewsItemAdapter(BaseFragment paramBaseFragment, ArrayList<NewsInfo> paramArrayList)
    throws NullPointerException
  {
    this(paramBaseFragment, paramArrayList, 1, null);
  }

  public NewsItemAdapter(BaseFragment paramBaseFragment, ArrayList<NewsInfo> paramArrayList, int paramInt, ArrayList<Integer> paramArrayList1)
    throws NullPointerException
  {
    super(paramBaseFragment.getActivity(), paramInt, paramArrayList);
    this.mItems = paramArrayList;
    this.mFragment = paramBaseFragment;
    this.mContext = paramBaseFragment.getActivity();
    if (paramArrayList1 != null)
    {
      View localView = paramBaseFragment.getActivity().getLayoutInflater().inflate(((Integer)paramArrayList1.get(0)).intValue(), null);
      this.mFooter = localView;
      this.mFooter.setOnClickListener(this);
      this.mFooterTV = ((TextView)localView.findViewById(((Integer)paramArrayList1.get(1)).intValue()));
      this.mFooterProBar = ((ProgressBar)localView.findViewById(((Integer)paramArrayList1.get(2)).intValue()));
      this.mFooterProBar.setVisibility(4);
      this.mFooter.setTag(paramArrayList1);
    }
    while (true)
    {
      this.mShowSaveFlowNotify = KXEnvironment.loadBooleanParams(this.mContext, "need_saveflow_notify", true, true);
      return;
      this.mFooter = paramBaseFragment.getActivity().getLayoutInflater().inflate(2130903185, null);
      this.mFooter.setOnClickListener(this);
      this.mFooterTV = ((TextView)this.mFooter.findViewById(2131362073));
      this.mFooterProBar = ((ProgressBar)this.mFooter.findViewById(2131362072));
      this.mFooterProBar.setVisibility(8);
    }
  }

  private void dismissDialog(Dialog paramDialog)
  {
    if ((paramDialog != null) && (paramDialog.isShowing()))
      paramDialog.dismiss();
  }

  private View getEmptyTextView()
  {
    return ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903097, null);
  }

  private KXLinkInfo getKXLinkInfoByAppID(ArrayList<KXLinkInfo> paramArrayList, String paramString)
  {
    int i;
    if (paramArrayList == null)
      i = 0;
    for (int j = 0; ; j++)
    {
      KXLinkInfo localKXLinkInfo;
      if (j >= i)
        localKXLinkInfo = null;
      do
      {
        return localKXLinkInfo;
        i = paramArrayList.size();
        break;
        localKXLinkInfo = (KXLinkInfo)paramArrayList.get(j);
      }
      while (paramString.equals(localKXLinkInfo.getType()));
    }
  }

  private ShowPhoto getPhotoShowUtil()
  {
    if (this.mAlbumShowUtil == null)
      this.mAlbumShowUtil = new ShowPhoto(this.mContext, this.mFragment, false);
    return this.mAlbumShowUtil;
  }

  public static RecordInfo getRecordInfoFromNewsInfo(NewsInfo paramNewsInfo)
  {
    RecordInfo localRecordInfo = new RecordInfo();
    localRecordInfo.setRecordFeedCnum(paramNewsInfo.mCnum);
    localRecordInfo.setRecordFeedFlogo(paramNewsInfo.mFlogo);
    localRecordInfo.setRecordFeedFname(paramNewsInfo.mFname);
    localRecordInfo.setRecordFeedFuid(paramNewsInfo.mFuid);
    localRecordInfo.setRecordFeedLocation(paramNewsInfo.mLocation);
    localRecordInfo.setRecordFeedRid(paramNewsInfo.mRid);
    localRecordInfo.setRecordFeedSource(paramNewsInfo.mSource);
    localRecordInfo.setRecordFeedSubLocation(paramNewsInfo.mOrigRecordLocation);
    localRecordInfo.setRecordFeedSubRid(paramNewsInfo.mOrigRecordId);
    localRecordInfo.setRecordFeedSubTitle(paramNewsInfo.mOrigRecordIntro);
    boolean bool;
    if (TextUtils.isEmpty(paramNewsInfo.mOrigRecordIntro))
      bool = false;
    while (true)
    {
      localRecordInfo.setRepost(bool);
      localRecordInfo.setRecordFeedTime(paramNewsInfo.mStime);
      localRecordInfo.setRecordFeedStar(getUserType(paramNewsInfo));
      if ((paramNewsInfo.mVideoSnapshotLIst != null) && (paramNewsInfo.mVideoSnapshotLIst.size() > 0))
        localRecordInfo.appendRecordVideoLogo((String)paramNewsInfo.mVideoSnapshotLIst.get(0));
      if (!TextUtils.isEmpty(paramNewsInfo.mFpri));
      try
      {
        localRecordInfo.fpri = Integer.parseInt(paramNewsInfo.mFpri);
        label174: String str = paramNewsInfo.mIntro;
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append("[|s|]").append(paramNewsInfo.mFname).append("[|m|]").append(paramNewsInfo.mFuid).append("[|m|]").append(getUserType(paramNewsInfo)).append("[|e|]");
        if (!str.startsWith(localStringBuffer.toString()))
        {
          localStringBuffer.append("：").append(str);
          str = localStringBuffer.toString();
        }
        localRecordInfo.setRecordFeedTitle(str);
        localRecordInfo.setRecordFeedTnum(paramNewsInfo.mTnum);
        localRecordInfo.setRecordFeedUpnum(paramNewsInfo.mUpnum);
        if ((paramNewsInfo.mRecordImages != null) && (paramNewsInfo.mRecordImages.length == 2))
        {
          if (TextUtils.isEmpty(paramNewsInfo.mOrigRecordId))
          {
            localRecordInfo.setRecordLarge(paramNewsInfo.mRecordImages[1]);
            localRecordInfo.setRecordThumbnail(paramNewsInfo.mRecordImages[0]);
          }
        }
        else
        {
          return localRecordInfo;
          bool = true;
          continue;
        }
        localRecordInfo.setRecordSubLarge(paramNewsInfo.mRecordImages[1]);
        localRecordInfo.setRecordSubThumbnail(paramNewsInfo.mRecordImages[0]);
        return localRecordInfo;
      }
      catch (Exception localException)
      {
        break label174;
      }
    }
  }

  private KXLinkInfo getUserInfo(ArrayList<KXLinkInfo> paramArrayList)
  {
    int i;
    if (paramArrayList == null)
      i = 0;
    for (int j = 0; ; j++)
    {
      KXLinkInfo localKXLinkInfo;
      if (j >= i)
        localKXLinkInfo = null;
      do
      {
        return localKXLinkInfo;
        i = paramArrayList.size();
        break;
        localKXLinkInfo = (KXLinkInfo)paramArrayList.get(j);
      }
      while ((localKXLinkInfo.isStar()) || (localKXLinkInfo.isUserName()));
    }
  }

  public static String getUserType(NewsInfo paramNewsInfo)
  {
    if ("1".equals(paramNewsInfo.mStar))
      return "-1";
    return "0";
  }

  private ShowVoteDetail getVoteShowUtil()
  {
    if (this.mShowVoteDetailUtil == null)
      this.mShowVoteDetailUtil = new ShowVoteDetail(this.mFragment);
    return this.mShowVoteDetailUtil;
  }

  private void goToHome(NewsInfo paramNewsInfo)
  {
    if (User.getInstance().GetLookAround())
    {
      showLoginDialog();
      return;
    }
    IntentUtil.showHomeFragment(this.mFragment, paramNewsInfo.mFuid, paramNewsInfo.mFname, paramNewsInfo.mFlogo);
  }

  private int isDisplayHomePage(NewsInfo paramNewsInfo)
  {
    if (paramNewsInfo == null);
    do
      return 0;
    while ((paramNewsInfo.mFname.equals(User.getInstance().getRealName())) || (!TextUtils.isEmpty(this.mFuid)));
    return 1;
  }

  private void openByUc(String paramString1, String paramString2)
  {
    Intent localIntent = new Intent();
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.setData(Uri.parse(paramString2));
    if (paramString1.equals("com.uc.browser"))
    {
      localIntent.setClassName("com.uc.browser", "com.uc.browser.ActivityUpdate");
      AnimationUtil.startFragment(this.mFragment, localIntent, 1);
      return;
    }
    if (paramString1.equals("com.UCMobile"))
    {
      localIntent.setClassName("com.UCMobile", "com.UCMobile.main.UCMobile");
      AnimationUtil.startFragment(this.mFragment, localIntent, 1);
      return;
    }
    AnimationUtil.startFragment(this.mFragment, localIntent, 1);
  }

  private void photoPraiseIt(NewsInfo paramNewsInfo)
  {
    if (this.photoupTask != null)
    {
      if ((this.photoupTask.getStatus() == AsyncTask.Status.FINISHED) || (this.photoupTask.isCancelled()))
        this.photoupTask = null;
    }
    else
    {
      Object[] arrayOfObject = { paramNewsInfo, this };
      this.photoupTask = new PhotoPostUpTask(this.mFragment);
      this.photoupTask.execute(arrayOfObject);
    }
  }

  private void showNewDetailPage(String paramString, NewsInfo paramNewsInfo)
  {
    showNewDetailPage(paramString, paramNewsInfo, 0);
  }

  private void showNewDetailPage(String paramString, NewsInfo paramNewsInfo, int paramInt)
  {
    int i;
    if (paramString.equals("1018"))
    {
      RecordInfo localRecordInfo = getRecordInfoFromNewsInfo(paramNewsInfo);
      Intent localIntent = new Intent(this.mContext, NewsDetailFragment.class);
      DetailItem localDetailItem = DetailItem.makeWeiboDetailItem("1018", localRecordInfo.fpri, localRecordInfo.getRecordFeedFuid(), localRecordInfo.getRecordFeedFname(), localRecordInfo.getRecordFeedFlogo(), paramNewsInfo.mStar, localRecordInfo.getRecordFeedRid(), paramNewsInfo.mIntro, localRecordInfo.getRecordFeedTime(), localRecordInfo.getRecordThumbnail(), localRecordInfo.getRecordLarge(), localRecordInfo.getRecordFeedSubRid(), paramNewsInfo.mOrigRecordTitle, paramNewsInfo.mOrigRecordIntro, localRecordInfo.getRecordSubThumbnail(), localRecordInfo.getRecordSubLarge(), localRecordInfo.getRecordFeedSubLocation(), null, Integer.parseInt(paramNewsInfo.mCnum), Integer.parseInt(paramNewsInfo.mTnum));
      if ("audio".equals(paramNewsInfo.mNtypename))
      {
        i = 1;
        localRecordInfo.mAudioRecord = i;
        localRecordInfo.mMediaInfo = paramNewsInfo.mMediaInfo;
        localRecordInfo.mSubMediaInfo = paramNewsInfo.mSubMediaInfo;
        localDetailItem.setTagRecordInfo(localRecordInfo);
        localDetailItem.setTagNewsInfo(paramNewsInfo);
        Bundle localBundle = new Bundle();
        localBundle.putSerializable("data", localDetailItem);
        localBundle.putInt("from", paramInt);
        localIntent.putExtras(localBundle);
        this.mFragment.startFragmentForResultNew(localIntent, 1300, 1, true);
      }
    }
    do
    {
      return;
      i = 0;
      break;
    }
    while (!paramString.equals("1192"));
    String[] arrayOfString = paramNewsInfo.mRecordImages;
    String str1 = null;
    String str2 = null;
    if (arrayOfString != null)
    {
      str1 = paramNewsInfo.mRecordImages[0];
      str2 = paramNewsInfo.mRecordImages[1];
    }
    IntentUtil.showLbsCheckInCommentFragment(this.mFragment, paramNewsInfo.mNewsId, paramNewsInfo.mFuid, paramNewsInfo.mFname, paramNewsInfo.mFlogo, "0", paramNewsInfo.mCommentFlag, paramNewsInfo.mIntro, paramNewsInfo.mStime, str1, str2, paramNewsInfo.mLocation, paramNewsInfo.mSource, -1, -1, paramNewsInfo.mMapUrl, paramNewsInfo);
  }

  private void showObjCommentsActivity(NewsInfo paramNewsInfo)
  {
    setCurrentNewsInfo(paramNewsInfo);
    KaixinPhotoItem localKaixinPhotoItem = (KaixinPhotoItem)paramNewsInfo.mPhotoList.get(0);
    Intent localIntent = new Intent(this.mContext, ObjCommentFragment.class);
    localIntent.putExtra("id", localKaixinPhotoItem.pid);
    if ("0".equals(((PhotoItem)paramNewsInfo.mPhotoList.get(0)).albumId));
    for (String str = "4"; ; str = "1")
    {
      localIntent.putExtra("type", str);
      localIntent.putExtra("fuid", ((PhotoItem)paramNewsInfo.mPhotoList.get(0)).fuid);
      localIntent.putExtra("Cnum", paramNewsInfo.mCnum);
      localIntent.putExtra("mode", 3);
      AnimationUtil.startFragmentForResult(this.mFragment, localIntent, 1230, 1);
      return;
    }
  }

  private void showPhoto(String[] paramArrayOfString)
  {
    String str1;
    String str2;
    String str3;
    String str4;
    String str5;
    if ((paramArrayOfString != null) && (paramArrayOfString.length == 6))
    {
      str1 = paramArrayOfString[0];
      str2 = paramArrayOfString[1];
      str3 = paramArrayOfString[2];
      str4 = paramArrayOfString[3];
      str5 = paramArrayOfString[4];
      String str6 = paramArrayOfString[5];
      if ((!User.getInstance().getUID().equals(str2)) && ("1".equals(str5)) && ("0".equals(str6)))
        Toast.makeText(this.mContext, 2131427903, 0).show();
    }
    else
    {
      return;
    }
    getPhotoShowUtil().showPhoto(str4, 2, Integer.parseInt(str1), str2, str3, str5);
  }

  protected void addComment(NewsInfo paramNewsInfo)
  {
    try
    {
      if ((!TextUtils.isEmpty(paramNewsInfo.mCommentFlag)) && (paramNewsInfo.mCommentFlag.compareTo("1") != 0))
      {
        DialogUtil.showKXAlertDialog(this.mContext, 2131427471, null);
        return;
      }
      Intent localIntent = new Intent(this.mContext, InputFragment.class);
      Bundle localBundle = new Bundle();
      localBundle.putString("id", paramNewsInfo.mNewsId);
      localBundle.putString("type", paramNewsInfo.mNtype);
      localBundle.putString("ntypename", paramNewsInfo.mNtypename);
      localBundle.putString("ouid", paramNewsInfo.mFuid);
      localBundle.putInt("mode", 2);
      localBundle.putString("fname", paramNewsInfo.mFname);
      localBundle.putString("title", paramNewsInfo.mTitle);
      localBundle.putString("intro", paramNewsInfo.mIntro);
      localBundle.putString("stime", paramNewsInfo.mStime);
      localBundle.putString("commentflag", paramNewsInfo.mCommentFlag);
      localIntent.putExtras(localBundle);
      AnimationUtil.startFragmentForResult(this.mFragment, localIntent, 2, 3);
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("NewsItemAdapter", "CommentOnClickListener", localException);
    }
  }

  public void addFansResult(String paramString)
  {
    Iterator localIterator = this.mItems.iterator();
    NewsInfo localNewsInfo;
    do
    {
      if (!localIterator.hasNext())
        return;
      localNewsInfo = (NewsInfo)localIterator.next();
    }
    while ((TextUtils.isEmpty(localNewsInfo.mFuid)) || (!localNewsInfo.mFuid.equals(paramString)));
    localNewsInfo.mIsFriend = true;
  }

  public void clear()
  {
    dismissDialog(this.mAlertDialog);
    this.mBlankView = null;
    super.clear();
  }

  public void forwardWeibo(NewsInfo paramNewsInfo)
  {
    if ((!TextUtils.isEmpty(paramNewsInfo.mFpri)) && (paramNewsInfo.mFpri.equals("0")))
    {
      dismissDialog(this.mAlertDialog);
      this.mAlertDialog = DialogUtil.showMsgDlgStd(this.mContext, 2131427329, 2131427744, null);
      return;
    }
    Intent localIntent = new Intent(this.mContext, ForwardWeiboFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putSerializable("recordInfo", getRecordInfoFromNewsInfo(paramNewsInfo));
    localIntent.putExtras(localBundle);
    this.mFragment.startFragmentForResult(localIntent, 1201, 3, true);
  }

  public ArrayList<String> getCommendId(NewsInfo paramNewsInfo)
  {
    ArrayList localArrayList = new ArrayList();
    if ("1018".equals(paramNewsInfo.mNtype))
    {
      localArrayList.add("记录");
      localArrayList.add(paramNewsInfo.mRid);
      return localArrayList;
    }
    if ("2".equals(paramNewsInfo.mNtype))
    {
      localArrayList.add("日记");
      localArrayList.add(paramNewsInfo.mNewsId);
      return localArrayList;
    }
    if (("1".equals(paramNewsInfo.mNtype)) && (paramNewsInfo.mPhotoList != null) && (paramNewsInfo.mPhotoList.size() > 0))
    {
      PhotoItem localPhotoItem = (PhotoItem)paramNewsInfo.mPhotoList.get(0);
      localArrayList.add("照片");
      localArrayList.add(localPhotoItem.pid);
      return localArrayList;
    }
    if ("1192".equals(paramNewsInfo.mNtype))
    {
      localArrayList.add("签到");
      localArrayList.add(paramNewsInfo.mNewsId);
      return localArrayList;
    }
    if ("1242".equals(paramNewsInfo.mNtype))
    {
      localArrayList.add("转发");
      localArrayList.add(paramNewsInfo.mNewsId);
      return localArrayList;
    }
    localArrayList.add("");
    localArrayList.add(paramNewsInfo.mNewsId);
    return localArrayList;
  }

  public NewsInfo getCurrentNewsInfo()
  {
    return this.mCurrentNewsItem;
  }

  public ArrayList<KXLinkInfo> getIntro2KXLinkList(NewsInfo paramNewsInfo)
  {
    String str = paramNewsInfo.mIntro;
    if ((paramNewsInfo.mIntroShort != null) && (!TextUtils.isEmpty(paramNewsInfo.mIntroShort)))
      str = paramNewsInfo.mIntroShort;
    if ("2".equals(paramNewsInfo.mNtype))
      return NewsInfo.makeDiaryIntroList(str);
    return NewsInfo.makeIntroList(str);
  }

  public ArrayList<KXLinkInfo> getIntro2KXLinkListAddLocation(NewsInfo paramNewsInfo)
  {
    String str1 = paramNewsInfo.mIntro;
    if ((paramNewsInfo.mIntroShort != null) && (!TextUtils.isEmpty(paramNewsInfo.mIntroShort)))
      str1 = paramNewsInfo.mIntroShort;
    StringBuffer localStringBuffer = new StringBuffer(str1);
    if (paramNewsInfo.mLocation.startsWith("[|s|]"))
      localStringBuffer.append(" " + paramNewsInfo.mLocation);
    String str2;
    while (true)
    {
      str2 = localStringBuffer.toString();
      if (!"2".equals(paramNewsInfo.mNtype))
        break;
      return NewsInfo.makeDiaryIntroList(str2);
      if ("".equals(paramNewsInfo.mLocation))
        continue;
      localStringBuffer.append(" [|s|]").append(paramNewsInfo.mLocation).append("[|m|]").append("").append("[|m|]").append("-102").append("[|e|]");
    }
    return NewsInfo.makeIntroList(str2);
  }

  public ArrayList<KXLinkInfo> getIntro2KXLinkListAddName(NewsInfo paramNewsInfo)
  {
    String str = " " + paramNewsInfo.mIntro;
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[|s|]").append(paramNewsInfo.mFname).append("[|m|]").append(paramNewsInfo.mFuid).append("[|m|]").append(getUserType(paramNewsInfo)).append("[|e|]");
    if (!str.startsWith(localStringBuffer.toString()))
    {
      localStringBuffer.append(str);
      str = localStringBuffer.toString();
    }
    return NewsInfo.makeIntroList(str);
  }

  public int getItemViewType(int paramInt)
  {
    return NewsItemViewFactory.getNewsType((NewsInfo)this.mItems.get(paramInt));
  }

  public ArrayList<KXLinkInfo> getNameKXLinkList(NewsInfo paramNewsInfo)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[|s|]").append(paramNewsInfo.mFname).append("[|m|]").append(paramNewsInfo.mFuid).append("[|m|]").append(getUserType(paramNewsInfo)).append("[|e|]");
    return NewsInfo.makeIntroList(localStringBuffer.toString());
  }

  public ArrayList<KXLinkInfo> getTitleKXLinkListAddName(NewsInfo paramNewsInfo, String paramString)
  {
    if (paramString == null)
      paramString = "";
    StringBuffer localStringBuffer = new StringBuffer();
    String str = paramNewsInfo.mFuid;
    if (("好友推荐".equals(paramNewsInfo.mFname)) || ("生日提醒".equals(paramNewsInfo.mFname)))
      str = "157368110";
    localStringBuffer.append("[|s|]").append(paramNewsInfo.mFname).append("[|m|]").append(str).append("[|m|]").append(getUserType(paramNewsInfo)).append("[|e|]");
    if (!paramString.startsWith(localStringBuffer.toString()))
    {
      localStringBuffer.append(paramString);
      paramString = localStringBuffer.toString();
    }
    return NewsInfo.makeIntroList(paramString);
  }

  public ArrayList<KXLinkInfo> getTitleKXLinkListAddNameLimit(NewsInfo paramNewsInfo, String paramString, int paramInt)
  {
    if (paramString == null)
      paramString = "";
    StringBuffer localStringBuffer = new StringBuffer();
    String str = paramNewsInfo.mFname;
    if (("生日提醒".equals(str)) || ("好友推荐".equals(str)))
      User.getInstance().getUID();
    if ((paramInt > 1) && (str.length() > paramInt))
      str = str.substring(0, paramInt - 1) + "...";
    localStringBuffer.append("[|s|]").append(str).append("[|m|]").append(paramNewsInfo.mFuid).append("[|m|]").append(getUserType(paramNewsInfo)).append("[|e|]");
    if (!paramString.startsWith(localStringBuffer.toString()))
    {
      localStringBuffer.append(paramString);
      paramString = localStringBuffer.toString();
    }
    return NewsInfo.makeIntroList(paramString);
  }

  public ArrayList<KXLinkInfo> getTittleKXLinkList(NewsInfo paramNewsInfo)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[|s|]").append(paramNewsInfo.mFname).append("[|m|]").append(paramNewsInfo.mFuid).append("[|m|]").append(getUserType(paramNewsInfo)).append("[|e|]");
    return NewsInfo.makeIntroList(localStringBuffer.toString());
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramInt >= this.mItems.size())
      return paramView;
    if ((this.mShowSaveFlowNotify) && (this.mContext != null) && (!KXEnvironment.wifiEnabled()) && (KXEnvironment.saveFlowOpen()))
    {
      this.mShowSaveFlowNotify = false;
      KXEnvironment.saveBooleanParams(this.mContext, "need_saveflow_notify", true, this.mShowSaveFlowNotify);
      DialogUtil.showMsgDlg(this.mContext, 2131428579, 2131428580, 2131428581, 2131428491, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
        }
      }
      , new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          Intent localIntent = new Intent(NewsItemAdapter.this.mContext, SettingActivity.class);
          NewsItemAdapter.this.mContext.startActivity(localIntent);
        }
      });
    }
    NewsInfo localNewsInfo = (NewsInfo)this.mItems.get(paramInt);
    String str = localNewsInfo.mFuid;
    if ((TextUtils.isEmpty(str)) && (paramInt > 1))
      return this.mFooter;
    if ((localNewsInfo.mFuid != null) && (str.equals("-2")))
    {
      if (this.mAdvView == null)
        this.mAdvView = new KXNewsBarView(this.mFragment);
      this.mAdvView.setClearListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          NewsItemAdapter.this.mItems.remove(0);
          NewsItemAdapter.this.notifyDataSetChanged();
          new Thread(new Runnable()
          {
            public void run()
            {
              NewsAdvertEngine.getInstance().sendAdvertClose(NewsItemAdapter.this.mContext.getApplicationContext());
            }
          }).start();
        }
      });
      return this.mAdvView;
    }
    if ((localNewsInfo.mFuid != null) && (str.equals("-3")))
    {
      if (this.mTopicHeadView == null)
        this.mTopicHeadView = new TopicGroupHeadView(this.mContext);
      while (true)
      {
        return this.mTopicHeadView;
        this.mTopicHeadView.fillHeadView();
      }
    }
    if ((localNewsInfo.mFuid != null) && (str.equals("-100")))
    {
      if (this.mGdtAdview == null)
        this.mGdtAdview = new GDTAdView(this.mContext, this.mFragment, localNewsInfo, this);
      return this.mGdtAdview;
    }
    if ((localNewsInfo.mFuid != null) && (localNewsInfo.mFuid.equals("-1")))
    {
      if (this.mBlankView == null)
        this.mBlankView = getEmptyTextView();
      this.mBlankView.setBackgroundResource(2130839400);
      return this.mBlankView;
    }
    if ((paramView != null) && (((paramView instanceof NewsItemSearchView)) || ((!(paramView instanceof NewsItemSearchView)) && ("5001".equals(localNewsInfo.mNtype)))))
      paramView = null;
    if ((paramView == null) || (paramView == this.mFooter) || (paramView == this.mBlankView) || (paramView == this.mAdvView) || (paramView == this.mTopicHeadView) || (paramView == this.mGdtAdview))
      paramView = NewsItemViewFactory.makeNewsItemViewFactory(this.mFragment, localNewsInfo, this);
    if ((paramView instanceof NewsItemView))
      ((NewsItemView)paramView).show(localNewsInfo);
    return paramView;
  }

  public int getViewTypeCount()
  {
    return 8;
  }

  public boolean isEnabled(int paramInt)
  {
    if ((this.mItems != null) && (paramInt >= 0) && (paramInt < this.mItems.size()))
    {
      NewsInfo localNewsInfo = (NewsInfo)this.mItems.get(paramInt);
      if ((localNewsInfo != null) && (!TextUtils.isEmpty(localNewsInfo.mNtype)) && ((localNewsInfo.mNtype.equals("0")) || (localNewsInfo.mNtype.equals("1002"))))
        return false;
    }
    return super.isEnabled(paramInt);
  }

  public boolean isFooterShowLoading()
  {
    if (this.mFooterProBar == null);
    do
      return false;
    while (this.mFooterProBar.getVisibility() != 0);
    return true;
  }

  public boolean isShowComment(NewsInfo paramNewsInfo)
  {
    String str1 = paramNewsInfo.mNtype;
    if (("1018".equals(str1)) || ("3".equals(str1)));
    String str2;
    do
    {
      do
        return true;
      while ((("2".equals(str1)) && (!"2".equals(paramNewsInfo.mPrivacy))) || ("1192".equals(str1)) || (("1".equals(str1)) && (paramNewsInfo.mPhotoList.size() == 1)));
      str2 = paramNewsInfo.mNtypename;
    }
    while ((("1242".equals(str1)) && (!"转发投票".equals(str2))) || ("1210".equals(str1)) || ("4".equals(str1)) || ("1".equals(str1)));
    return false;
  }

  public KXIntroView.OnClickLinkListener makeTitleClickListener(NewsInfo paramNewsInfo)
  {
    return new KXIntroView.OnClickLinkListener(paramNewsInfo)
    {
      public void onClick(KXLinkInfo paramKXLinkInfo)
      {
        if (NewsItemAdapter.this.mVideoPressed);
        do
        {
          while (true)
          {
            return;
            if ("6".equals(paramKXLinkInfo.getType()))
            {
              if (User.getInstance().GetLookAround())
              {
                NewsItemAdapter.this.showLoginDialog();
                return;
              }
              if (!"转发照片专辑".equals(this.val$info.mNtypename))
                continue;
              NewsItemAdapter.this.showAlbum(this.val$info);
              return;
            }
            if ("1".equals(paramKXLinkInfo.getType()))
            {
              if (User.getInstance().GetLookAround())
              {
                NewsItemAdapter.this.showLoginDialog();
                return;
              }
              if (!"转发照片".equals(this.val$info.mNtypename))
                continue;
              NewsItemAdapter.this.showPhoto(this.val$info);
              return;
            }
            if ("1016".equals(paramKXLinkInfo.getType()))
            {
              NewsItemAdapter.this.getVoteShowUtil().showVoteDetail(paramKXLinkInfo.getId());
              return;
            }
            if ((!paramKXLinkInfo.isUserName()) && (!paramKXLinkInfo.isStar()))
              break;
            String str1 = paramKXLinkInfo.getId();
            String str2 = paramKXLinkInfo.getContent();
            if ((TextUtils.isEmpty(str1)) || (str1.equals(NewsItemAdapter.this.mFuid)))
              continue;
            if (User.getInstance().GetLookAround())
            {
              NewsItemAdapter.this.showLoginDialog();
              return;
            }
            IntentUtil.showHomeFragment(NewsItemAdapter.this.mFragment, str1, str2);
            return;
          }
          if ("2".equals(paramKXLinkInfo.getType()))
          {
            if (User.getInstance().GetLookAround())
            {
              NewsItemAdapter.this.showLoginDialog();
              return;
            }
            NewsItemAdapter.this.showDiaryDetail(this.val$info);
            return;
          }
          if ("1210".equals(paramKXLinkInfo.getType()))
          {
            if (User.getInstance().GetLookAround())
            {
              NewsItemAdapter.this.showLoginDialog();
              return;
            }
            NewsItemAdapter.this.showStyleBoxDiaryDetail(this.val$info);
            return;
          }
          if (paramKXLinkInfo.isUrlLink())
          {
            if (User.getInstance().GetLookAround())
            {
              NewsItemAdapter.this.showLoginDialog();
              return;
            }
            IntentUtil.showWebPage(NewsItemAdapter.this.mContext, NewsItemAdapter.this.mFragment, paramKXLinkInfo.getId(), null);
            return;
          }
          if (paramKXLinkInfo.isLbsPoi())
          {
            if (User.getInstance().GetLookAround())
            {
              NewsItemAdapter.this.showLoginDialog();
              return;
            }
            IntentUtil.showLbsPositionDetailFragment(NewsItemAdapter.this.mFragment, paramKXLinkInfo.getId(), paramKXLinkInfo.getContent(), "", "");
            return;
          }
          if (!paramKXLinkInfo.isVideo())
            continue;
          if (User.getInstance().GetLookAround())
          {
            NewsItemAdapter.this.showLoginDialog();
            return;
          }
          NewsItemAdapter.this.mVideoPressed = true;
          IntentUtil.showVideoPage(NewsItemAdapter.this.mContext, paramKXLinkInfo.getId(), paramKXLinkInfo.getType(), paramKXLinkInfo.getContent());
          return;
        }
        while (!paramKXLinkInfo.isTopic());
        new KaiXinPKTask(NewsItemAdapter.this.getContext(), NewsItemAdapter.this.mFragment, paramKXLinkInfo.getId()).execute(new Void[0]);
      }
    };
  }

  public void onClick(View paramView)
  {
    if ((paramView.equals(this.mFooter)) && (this.mOnViewMoreListener != null))
      this.mOnViewMoreListener.onViewMoreClick();
  }

  public void onCommentLayoutClick(View paramView, int paramInt)
  {
    NewsInfo localNewsInfo = (NewsInfo)paramView.getTag();
    while (true)
    {
      try
      {
        if (!User.getInstance().GetLookAround())
          continue;
        showLoginDialog();
        return;
        if ((this.mInputListener == null) || (getCommendId(localNewsInfo).size() <= 0))
          break;
        if (paramInt == 0)
        {
          bool = this.mInputListener.onInputViewShow(0, localNewsInfo);
          if (!bool)
            break;
          this.mCurrentNewsItem = localNewsInfo;
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("NewsItemAdapter", "CommentOnClickListener", localException);
        return;
      }
      boolean bool = false;
      if (paramInt != 1)
        continue;
      bool = this.mInputListener.onInputViewShow(1, localNewsInfo);
    }
    if ("1018".equals(localNewsInfo.mNtype))
    {
      this.mCurrentNewsItem = localNewsInfo;
      showWeiboDetail(localNewsInfo);
      return;
    }
    if ("1".equals(localNewsInfo.mNtype))
    {
      showObjCommentsActivity(localNewsInfo);
      return;
    }
    this.mCurrentNewsItem = localNewsInfo;
    showCommentDetail(localNewsInfo);
  }

  public void onDiaryLongClicked(NewsInfo paramNewsInfo)
  {
    if (paramNewsInfo == null)
      return;
    String str = paramNewsInfo.mPrivacy;
    int i = isDisplayHomePage(paramNewsInfo);
    String[] arrayOfString;
    if (i > 0)
      if ((!User.getInstance().getUID().equals(paramNewsInfo.mFuid)) && ("2".equals(str)))
      {
        arrayOfString = new String[1];
        arrayOfString[0] = (paramNewsInfo.mFname + "的首页");
      }
    while (true)
    {
      if (this.mAlertDialog != null)
      {
        if (this.mAlertDialog.isShowing())
          this.mAlertDialog.dismiss();
        this.mAlertDialog = null;
      }
      this.mAlertDialog = DialogUtil.showSelectListDlg(this.mContext, 2131427509, arrayOfString, new DialogInterface.OnClickListener(i, paramNewsInfo)
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          if (User.getInstance().GetLookAround())
            NewsItemAdapter.this.showLoginDialog();
          do
          {
            return;
            if (this.val$displayHomePage <= 0)
              paramInt++;
            if (paramInt == 0)
            {
              NewsItemAdapter.this.goToHome(this.val$info);
              return;
            }
            if (paramInt == 1)
            {
              String str = this.val$info.mPrivacy;
              if ((!User.getInstance().getUID().equals(this.val$info.mFuid)) && ("2".equals(str)))
              {
                Toast.makeText(NewsItemAdapter.this.mContext, 2131427774, 0).show();
                return;
              }
              NewsModel.getInstance().setActiveItem(this.val$info);
              Intent localIntent = new Intent(NewsItemAdapter.this.mContext, DiaryDetailFragment.class);
              Bundle localBundle = new Bundle();
              localBundle.putString("fuid", this.val$info.mFuid);
              localBundle.putString("fname", this.val$info.mFname);
              localBundle.putString("did", this.val$info.mNewsId);
              localBundle.putString("commentflag", this.val$info.mCommentFlag);
              localBundle.putString("title", this.val$info.mTitle);
              localBundle.putString("intro", this.val$info.mIntro);
              localIntent.putExtras(localBundle);
              AnimationUtil.startFragment(NewsItemAdapter.this.mFragment, localIntent, 1);
              return;
            }
            if (paramInt == 2)
            {
              NewsItemAdapter.this.showCommentDetail(this.val$info);
              return;
            }
            if (paramInt != 3)
              continue;
            NewsItemAdapter.this.addComment(this.val$info);
            return;
          }
          while (paramInt != 4);
          NewsItemAdapter.this.praiseIt(this.val$info);
        }
      }
      , 1);
      return;
      arrayOfString = new String[5];
      arrayOfString[0] = (paramNewsInfo.mFname + "的首页");
      arrayOfString[1] = "查看日记";
      arrayOfString[2] = "查看评论";
      arrayOfString[3] = "添加评论";
      arrayOfString[4] = "赞";
      continue;
      if ((!User.getInstance().getUID().equals(paramNewsInfo.mFuid)) && ("2".equals(str)))
      {
        new String[1];
        return;
      }
      arrayOfString = new String[] { "查看日记", "查看评论", "添加评论", "赞" };
    }
  }

  public void onImageLongClicked(NewsInfo paramNewsInfo)
  {
    if (paramNewsInfo == null)
      return;
    int i = isDisplayHomePage(paramNewsInfo);
    String[] arrayOfString;
    if (i > 0)
    {
      arrayOfString = new String[2];
      arrayOfString[0] = (paramNewsInfo.mFname + "的首页");
      arrayOfString[1] = "查看照片";
    }
    while (true)
    {
      if (this.mAlertDialog != null)
      {
        if (this.mAlertDialog.isShowing())
          this.mAlertDialog.dismiss();
        this.mAlertDialog = null;
      }
      this.mAlertDialog = DialogUtil.showSelectListDlg(this.mContext, 2131427509, arrayOfString, new DialogInterface.OnClickListener(i, paramNewsInfo)
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          if (User.getInstance().GetLookAround())
          {
            NewsItemAdapter.this.showLoginDialog();
            return;
          }
          if (this.val$displayHomePage <= 0)
            paramInt++;
          if (paramInt == 0)
          {
            NewsItemAdapter.this.goToHome(this.val$info);
            return;
          }
          PhotoItem localPhotoItem = (PhotoItem)this.val$info.mPhotoList.get(0);
          String[] arrayOfString = new String[6];
          arrayOfString[0] = localPhotoItem.index;
          arrayOfString[1] = localPhotoItem.fuid;
          arrayOfString[2] = localPhotoItem.albumTitle;
          arrayOfString[3] = localPhotoItem.albumId;
          arrayOfString[4] = localPhotoItem.privacy;
          arrayOfString[5] = localPhotoItem.visible;
          NewsItemAdapter.this.showPhoto(arrayOfString);
        }
      }
      , 1);
      return;
      arrayOfString = new String[] { "查看照片" };
    }
  }

  public void onItemClick(NewsInfo paramNewsInfo)
  {
    try
    {
      if (TextUtils.isEmpty(paramNewsInfo.mFuid))
        return;
      if (User.getInstance().GetLookAround())
        showLoginDialog();
      setCurrentNewsInfo(paramNewsInfo);
      String str = paramNewsInfo.mNtype;
      if (!"1088".equals(str))
      {
        if ("1072".equals(str))
        {
          showTruth(paramNewsInfo);
          return;
        }
        if ("2".equals(str))
        {
          showDiaryDetail(paramNewsInfo);
          return;
        }
        if ("1016".equals(str))
        {
          showVoteList(paramNewsInfo);
          return;
        }
        if ("1018".equals(str))
        {
          if (paramNewsInfo != null)
          {
            if (paramNewsInfo.mMediaInfo != null)
              paramNewsInfo.mMediaInfo.setState(1);
            if (paramNewsInfo.mSubMediaInfo != null)
              paramNewsInfo.mSubMediaInfo.setState(1);
          }
          KXMediaManager.getInstance().requestStopCurrentMedia();
          showWeiboDetail(paramNewsInfo);
          return;
        }
        if ("2".equals(str))
        {
          showCommentDetail(paramNewsInfo);
          return;
        }
        if ("1210".equals(str))
        {
          showStyleBoxDiaryDetail(paramNewsInfo);
          return;
        }
        if ("1242".equals(str))
        {
          showRepost3Item(paramNewsInfo);
          return;
        }
        if ("1192".equals(str))
          showCommentDetail(paramNewsInfo);
      }
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void onOtherLongClicked(NewsInfo paramNewsInfo)
  {
    if (paramNewsInfo == null);
    do
      return;
    while (isDisplayHomePage(paramNewsInfo) <= 0);
    String[] arrayOfString = new String[1];
    arrayOfString[0] = (paramNewsInfo.mFname + "的首页");
    if (this.mAlertDialog != null)
    {
      if (this.mAlertDialog.isShowing())
        this.mAlertDialog.dismiss();
      this.mAlertDialog = null;
    }
    this.mAlertDialog = DialogUtil.showSelectListDlg(this.mContext, 2131427509, arrayOfString, new DialogInterface.OnClickListener(paramNewsInfo)
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        if (User.getInstance().GetLookAround())
        {
          NewsItemAdapter.this.showLoginDialog();
          return;
        }
        NewsItemAdapter.this.goToHome(this.val$info);
      }
    }
    , 1);
  }

  public void onRecordLongClicked(NewsInfo paramNewsInfo)
  {
    if (paramNewsInfo == null)
      return;
    int i = isDisplayHomePage(paramNewsInfo);
    String[] arrayOfString;
    if (i > 0)
      if ((paramNewsInfo.mRecordImages != null) && (paramNewsInfo.mRecordImages.length == 2))
      {
        arrayOfString = new String[6];
        arrayOfString[0] = (paramNewsInfo.mFname + "的首页");
        arrayOfString[1] = "查看记录照片";
        arrayOfString[2] = "查看评论";
        arrayOfString[3] = "转发该记录";
        arrayOfString[4] = "添加评论";
        arrayOfString[5] = "赞";
      }
    while (true)
    {
      if (this.mAlertDialog != null)
      {
        if (this.mAlertDialog.isShowing())
          this.mAlertDialog.dismiss();
        this.mAlertDialog = null;
      }
      this.mAlertDialog = DialogUtil.showSelectListDlg(this.mContext, 2131427509, arrayOfString, new DialogInterface.OnClickListener(i, paramNewsInfo)
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          if (User.getInstance().GetLookAround())
            NewsItemAdapter.this.showLoginDialog();
          do
          {
            return;
            if (this.val$displayHomePage <= 0)
              paramInt++;
            if (paramInt == 0)
            {
              NewsItemAdapter.this.goToHome(this.val$info);
              return;
            }
            if ((this.val$info.mRecordImages == null) || (this.val$info.mRecordImages.length != 2))
              paramInt++;
            if (paramInt == 1)
            {
              String[] arrayOfString = (String[])this.val$info.mRecordImages.clone();
              Intent localIntent = new Intent(NewsItemAdapter.this.mContext, PreviewUploadPhotoFragment.class);
              localIntent.putExtra("small", arrayOfString[0]);
              localIntent.putExtra("large", arrayOfString[1]);
              localIntent.putExtra("action", "com.kaixin001.VIEW_SINGLE_PHOTO");
              AnimationUtil.startFragment(NewsItemAdapter.this.mFragment, localIntent, 1);
              return;
            }
            if (paramInt == 2)
            {
              NewsItemAdapter.this.showCommentDetail(this.val$info);
              return;
            }
            if (paramInt == 3)
            {
              NewsItemAdapter.this.forwardWeibo(this.val$info);
              return;
            }
            if (paramInt != 4)
              continue;
            NewsItemAdapter.this.addComment(this.val$info);
            return;
          }
          while (paramInt != 5);
          NewsItemAdapter.this.praiseIt(this.val$info);
        }
      }
      , 1);
      return;
      arrayOfString = new String[5];
      arrayOfString[0] = (paramNewsInfo.mFname + "的首页");
      arrayOfString[1] = "查看评论";
      arrayOfString[2] = "转发该记录";
      arrayOfString[3] = "添加评论";
      arrayOfString[4] = "赞";
      continue;
      if ((paramNewsInfo.mRecordImages != null) && (paramNewsInfo.mRecordImages.length == 2))
      {
        arrayOfString = new String[] { "查看记录照片", "查看评论", "转发该记录", "添加评论", "赞" };
        continue;
      }
      arrayOfString = new String[] { "查看评论", "转发该记录", "添加评论", "赞" };
    }
  }

  public void onRepostAlbumLongClicked(NewsInfo paramNewsInfo)
  {
    if (paramNewsInfo == null)
      return;
    int i = isDisplayHomePage(paramNewsInfo);
    String[] arrayOfString;
    if (i > 0)
    {
      arrayOfString = new String[5];
      arrayOfString[0] = (paramNewsInfo.mFname + "的首页");
      arrayOfString[1] = "查看该专辑";
      arrayOfString[2] = "查看评论";
      arrayOfString[3] = "添加评论";
      arrayOfString[4] = "赞";
    }
    while (true)
    {
      if (this.mAlertDialog != null)
      {
        if (this.mAlertDialog.isShowing())
          this.mAlertDialog.dismiss();
        this.mAlertDialog = null;
      }
      this.mAlertDialog = DialogUtil.showSelectListDlg(this.mContext, 2131427509, arrayOfString, new DialogInterface.OnClickListener(i, paramNewsInfo)
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          if (User.getInstance().GetLookAround())
            NewsItemAdapter.this.showLoginDialog();
          do
          {
            return;
            if (this.val$displayHomePage <= 0)
              paramInt++;
            if (paramInt == 0)
            {
              NewsItemAdapter.this.goToHome(this.val$info);
              return;
            }
            if (paramInt == 1)
            {
              NewsItemAdapter.this.showAlbum(this.val$info);
              return;
            }
            if (paramInt == 2)
            {
              NewsItemAdapter.this.showCommentDetail(this.val$info);
              return;
            }
            if (paramInt != 3)
              continue;
            NewsItemAdapter.this.addComment(this.val$info);
            return;
          }
          while (paramInt != 4);
          NewsItemAdapter.this.praiseIt(this.val$info);
        }
      }
      , 1);
      return;
      arrayOfString = new String[] { "查看该专辑", "查看评论", "添加评论", "赞" };
    }
  }

  public void onRepostLongClicked(NewsInfo paramNewsInfo)
  {
    if (paramNewsInfo == null)
      return;
    int i = isDisplayHomePage(paramNewsInfo);
    String[] arrayOfString;
    if (i <= 0)
      arrayOfString = new String[] { "查看转帖" };
    while (true)
    {
      if (this.mAlertDialog != null)
      {
        if (this.mAlertDialog.isShowing())
          this.mAlertDialog.dismiss();
        this.mAlertDialog = null;
      }
      this.mAlertDialog = DialogUtil.showSelectListDlg(this.mContext, 2131427509, arrayOfString, new DialogInterface.OnClickListener(i, paramNewsInfo)
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          if (this.val$displayHomePage <= 0)
            paramInt++;
          if (paramInt == 0)
          {
            NewsItemAdapter.this.goToHome(this.val$info);
            return;
          }
          if (User.getInstance().GetLookAround())
          {
            NewsItemAdapter.this.showLoginDialog();
            return;
          }
          NewsModel.getInstance().setActiveItem(this.val$info);
          Intent localIntent = new Intent(NewsItemAdapter.this.mContext, RepostListFragment.class);
          Bundle localBundle = new Bundle();
          localBundle.putString("commentflag", this.val$info.mCommentFlag);
          localIntent.putExtras(localBundle);
          AnimationUtil.startFragment(NewsItemAdapter.this.mFragment, localIntent, 1);
        }
      }
      , 1);
      return;
      arrayOfString = new String[2];
      arrayOfString[0] = (paramNewsInfo.mFname + "的首页");
      arrayOfString[1] = "查看转帖";
    }
  }

  public void onRepostPhotoLongClicked(NewsInfo paramNewsInfo)
  {
    if (paramNewsInfo == null)
      return;
    int i = isDisplayHomePage(paramNewsInfo);
    String[] arrayOfString;
    if (i > 0)
    {
      arrayOfString = new String[5];
      arrayOfString[0] = (paramNewsInfo.mFname + "的首页");
      arrayOfString[1] = "查看该照片";
      arrayOfString[2] = "查看评论";
      arrayOfString[3] = "添加评论";
      arrayOfString[4] = "赞";
    }
    while (true)
    {
      if (this.mAlertDialog != null)
      {
        if (this.mAlertDialog.isShowing())
          this.mAlertDialog.dismiss();
        this.mAlertDialog = null;
      }
      this.mAlertDialog = DialogUtil.showSelectListDlg(this.mContext, 2131427509, arrayOfString, new DialogInterface.OnClickListener(i, paramNewsInfo)
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          if (User.getInstance().GetLookAround())
            NewsItemAdapter.this.showLoginDialog();
          do
          {
            return;
            if (this.val$displayHomePage <= 0)
              paramInt++;
            if (paramInt == 0)
            {
              NewsItemAdapter.this.goToHome(this.val$info);
              return;
            }
            if (paramInt == 1)
            {
              NewsItemAdapter.this.showPhoto(this.val$info);
              return;
            }
            if (paramInt == 2)
            {
              NewsItemAdapter.this.showCommentDetail(this.val$info);
              return;
            }
            if (paramInt != 3)
              continue;
            NewsItemAdapter.this.addComment(this.val$info);
            return;
          }
          while (paramInt != 4);
          NewsItemAdapter.this.praiseIt(this.val$info);
        }
      }
      , 1);
      return;
      arrayOfString = new String[] { "查看该照片", "查看评论", "添加评论", "赞" };
    }
  }

  public void onStateLongClicked(NewsInfo paramNewsInfo)
  {
    if (paramNewsInfo == null)
      return;
    int i = isDisplayHomePage(paramNewsInfo);
    String[] arrayOfString;
    if (i <= 0)
      arrayOfString = new String[] { "查看评论", "添加评论", "赞" };
    while (true)
    {
      if (this.mAlertDialog != null)
      {
        if (this.mAlertDialog.isShowing())
          this.mAlertDialog.dismiss();
        this.mAlertDialog = null;
      }
      this.mAlertDialog = DialogUtil.showSelectListDlg(this.mContext, 2131427509, arrayOfString, new DialogInterface.OnClickListener(i, paramNewsInfo)
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          if (User.getInstance().GetLookAround())
          {
            NewsItemAdapter.this.showLoginDialog();
            return;
          }
          if (this.val$displayHomePage <= 0)
            paramInt++;
          if (paramInt == 0)
          {
            NewsItemAdapter.this.goToHome(this.val$info);
            return;
          }
          if (paramInt == 1)
          {
            NewsItemAdapter.this.showCommentDetail(this.val$info);
            return;
          }
          if (paramInt == 2)
          {
            NewsItemAdapter.this.addComment(this.val$info);
            return;
          }
          NewsItemAdapter.this.praiseIt(this.val$info);
        }
      }
      , 1);
      return;
      arrayOfString = new String[4];
      arrayOfString[0] = (paramNewsInfo.mFname + "的首页");
      arrayOfString[1] = "查看评论";
      arrayOfString[2] = "添加评论";
      arrayOfString[3] = "赞";
    }
  }

  public void onVoteLongClicked(NewsInfo paramNewsInfo)
  {
    if (paramNewsInfo == null)
      return;
    int i = isDisplayHomePage(paramNewsInfo);
    String[] arrayOfString;
    if (i > 0)
    {
      arrayOfString = new String[2];
      arrayOfString[0] = (paramNewsInfo.mFname + "的首页");
      arrayOfString[1] = "查看投票";
    }
    while (true)
    {
      if (this.mAlertDialog != null)
      {
        if (this.mAlertDialog.isShowing())
          this.mAlertDialog.dismiss();
        this.mAlertDialog = null;
      }
      this.mAlertDialog = DialogUtil.showSelectListDlg(this.mContext, 2131427509, arrayOfString, new DialogInterface.OnClickListener(i, paramNewsInfo)
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          if (User.getInstance().GetLookAround())
          {
            NewsItemAdapter.this.showLoginDialog();
            return;
          }
          if (this.val$displayHomePage <= 0)
            paramInt++;
          if (paramInt == 0)
          {
            NewsItemAdapter.this.goToHome(this.val$info);
            return;
          }
          NewsItemAdapter.this.showVoteList(this.val$info);
        }
      }
      , 1);
      return;
      arrayOfString = new String[] { "查看投票" };
    }
  }

  public void praiseAnimation(View paramView)
  {
    if (paramView == null)
      return;
    ScaleAnimation localScaleAnimation1 = new ScaleAnimation(1.0F, 2.0F, 1.0F, 2.0F, 1, 0.5F, 1, 0.5F);
    localScaleAnimation1.setDuration(300L);
    ScaleAnimation localScaleAnimation2 = new ScaleAnimation(2.0F, 0.8F, 2.0F, 0.8F, 1, 0.5F, 1, 0.5F);
    localScaleAnimation2.setDuration(300L);
    ScaleAnimation localScaleAnimation3 = new ScaleAnimation(0.8F, 1.0F, 0.8F, 1.0F, 1, 0.5F, 1, 0.5F);
    localScaleAnimation2.setDuration(300L);
    localScaleAnimation1.setAnimationListener(new Animation.AnimationListener(paramView, localScaleAnimation2)
    {
      public void onAnimationEnd(Animation paramAnimation)
      {
        this.val$view.startAnimation(this.val$zoomOut);
      }

      public void onAnimationRepeat(Animation paramAnimation)
      {
      }

      public void onAnimationStart(Animation paramAnimation)
      {
      }
    });
    localScaleAnimation2.setAnimationListener(new Animation.AnimationListener(paramView, localScaleAnimation3)
    {
      public void onAnimationEnd(Animation paramAnimation)
      {
        this.val$view.startAnimation(this.val$animation3);
      }

      public void onAnimationRepeat(Animation paramAnimation)
      {
      }

      public void onAnimationStart(Animation paramAnimation)
      {
      }
    });
    paramView.startAnimation(localScaleAnimation1);
  }

  public void praiseIt(NewsInfo paramNewsInfo)
  {
    if (("1".equals(paramNewsInfo.mNtype)) && (paramNewsInfo.mPhotoList.size() == 1))
      photoPraiseIt(paramNewsInfo);
    while (true)
    {
      return;
      if (this.upTask == null)
        break;
      if ((this.upTask.getStatus() != AsyncTask.Status.FINISHED) && (!this.upTask.isCancelled()))
        continue;
      this.upTask = null;
    }
    Object[] arrayOfObject = { paramNewsInfo, this };
    this.upTask = new PostUpTask(this.mFragment);
    this.upTask.showSendingInfo(false);
    this.upTask.execute(arrayOfObject);
  }

  public void repostResult(String paramString)
  {
    Iterator localIterator = this.mItems.iterator();
    NewsInfo localNewsInfo;
    do
    {
      if (!localIterator.hasNext())
        return;
      localNewsInfo = (NewsInfo)localIterator.next();
    }
    while ((TextUtils.isEmpty(localNewsInfo.mNewsId)) || (!localNewsInfo.mNewsId.equals(paramString)));
    localNewsInfo.mRpNum = (1 + localNewsInfo.mRpNum);
  }

  public void setCurrentNewsInfo(NewsInfo paramNewsInfo)
  {
    this.mCurrentNewsItem = paramNewsInfo;
  }

  public void setCurrentUid(String paramString)
  {
    this.mFuid = paramString;
  }

  public void setHideFooter(int paramInt)
  {
  }

  public void setInputListener(KXInputView.KXInputListener paramKXInputListener)
  {
    this.mInputListener = paramKXInputListener;
  }

  public void setItemStyle(NewsItemUI paramNewsItemUI)
  {
  }

  public void setOnViewMoreClickListener(OnViewMoreClickListener paramOnViewMoreClickListener)
  {
    this.mOnViewMoreListener = paramOnViewMoreClickListener;
  }

  public void setVideoPressed(boolean paramBoolean)
  {
    this.mVideoPressed = paramBoolean;
  }

  public void showAlbum(NewsInfo paramNewsInfo)
  {
    if ((paramNewsInfo.mPhotoList == null) || ((paramNewsInfo.mPhotoList != null) && (paramNewsInfo.mPhotoList.isEmpty())))
    {
      Toast.makeText(this.mContext, 2131427902, 0).show();
      return;
    }
    PhotoItem localPhotoItem = (PhotoItem)paramNewsInfo.mPhotoList.get(0);
    if ((!User.getInstance().getUID().equals(localPhotoItem.fuid)) && ("1".equals(localPhotoItem.privacy)) && ("0".equals(localPhotoItem.visible)))
    {
      Toast.makeText(this.mContext, 2131427903, 0).show();
      return;
    }
    KXLinkInfo localKXLinkInfo = getUserInfo(NewsInfo.makeTitleList(paramNewsInfo.mSubTitle));
    getPhotoShowUtil().showAlbum(localPhotoItem.albumId, localPhotoItem.albumTitle, localKXLinkInfo.getId(), localKXLinkInfo.getContent(), localPhotoItem.picnum, localPhotoItem.privacy);
  }

  public void showCommentDetail(NewsInfo paramNewsInfo)
  {
    if ((paramNewsInfo == null) || (this.mContext == null))
      return;
    if ("1192".equals(paramNewsInfo.mNtype))
    {
      showNewDetailPage("1192", paramNewsInfo);
      return;
    }
    if ("1018".equals(paramNewsInfo.mNtype))
    {
      showNewDetailPage("1018", paramNewsInfo);
      return;
    }
    Intent localIntent = new Intent(this.mContext, ObjCommentFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putInt("mode", 1);
    localBundle.putString("id", paramNewsInfo.mNewsId);
    localBundle.putString("type", paramNewsInfo.mNtype);
    localBundle.putString("ntypename", paramNewsInfo.mNtypename);
    localBundle.putString("fuid", paramNewsInfo.mFuid);
    localBundle.putString("fname", paramNewsInfo.mFname);
    if (paramNewsInfo.mNtype.equals("1192"))
      localBundle.putString("client_name", paramNewsInfo.mSource);
    String str1 = paramNewsInfo.mIntro;
    String str2 = "[|s|]" + paramNewsInfo.mFname + "[|m|]" + paramNewsInfo.mFuid + "[|m|]";
    if (TextUtils.isEmpty(str1))
      str1 = str2 + "0" + "[|e|]";
    while (true)
    {
      localBundle.putString("title", str1);
      localBundle.putString("stime", paramNewsInfo.mStime);
      localBundle.putString("commentflag", paramNewsInfo.mCommentFlag);
      label305: int i;
      label376: int j;
      if ("2".equals(paramNewsInfo.mNtype))
      {
        localBundle.putString("intro", paramNewsInfo.mContent);
        if ("1242".equals(paramNewsInfo.mNtype))
        {
          localBundle.putString("intro", paramNewsInfo.mSubTitle);
          KXLinkInfo localKXLinkInfo = getUserInfo(NewsInfo.makeTitleList(paramNewsInfo.mSubTitle));
          localBundle.putString("uid", localKXLinkInfo.getId());
          localBundle.putString("name", localKXLinkInfo.getContent());
          if (paramNewsInfo.mPhotoList != null)
            break label590;
          i = 0;
          if (i > 0)
          {
            localBundle.putInt("photosize", i);
            j = 0;
            if (j < i)
              break label602;
          }
        }
      }
      try
      {
        localBundle.putInt("Tnum", Integer.valueOf(paramNewsInfo.mTnum).intValue());
        localBundle.putInt("Cnum", Integer.valueOf(paramNewsInfo.mCnum).intValue());
        localBundle.putInt("Upnum", Integer.valueOf(paramNewsInfo.mUpnum).intValue());
        localIntent.putExtras(localBundle);
        AnimationUtil.startFragmentForResult(this.mFragment, localIntent, 10, 1);
        return;
        if (str1.startsWith(str2))
          continue;
        str1 = str2 + "0" + "[|e|]" + "：" + str1;
        continue;
        localBundle.putString("intro", paramNewsInfo.mOrigRecordIntro);
        if (paramNewsInfo.mRecordImages == null)
          break label305;
        if (TextUtils.isEmpty(paramNewsInfo.mOrigRecordIntro))
          localBundle.putString("sublocation", paramNewsInfo.mOrigRecordLocation);
        localBundle.putString("thumbnail", paramNewsInfo.mRecordImages[0]);
        localBundle.putString("large", paramNewsInfo.mRecordImages[1]);
        break label305;
        label590: i = paramNewsInfo.mPhotoList.size();
        break label376;
        label602: PhotoItem localPhotoItem = (PhotoItem)paramNewsInfo.mPhotoList.get(j);
        localBundle.putString("thumbnail" + j, localPhotoItem.thumbnail);
        localBundle.putString("large" + j, localPhotoItem.large);
        localBundle.putString("photo_pos" + j, localPhotoItem.index);
        localBundle.putString("photo_title" + j, localPhotoItem.title);
        localBundle.putString("albumId", localPhotoItem.albumId);
        localBundle.putString("albumTitle", localPhotoItem.albumTitle);
        localBundle.putString("picnum", localPhotoItem.picnum);
        localBundle.putString("photo_index", localPhotoItem.index);
        localBundle.putString("photo_title", localPhotoItem.title);
        localBundle.putString("photo_privacy", localPhotoItem.privacy);
        localBundle.putString("photo_visible", localPhotoItem.visible);
        j++;
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }
  }

  public void showDiaryDetail(NewsInfo paramNewsInfo)
  {
    if ((paramNewsInfo == null) || (this.mContext == null))
      return;
    if (User.getInstance().GetLookAround())
    {
      showLoginDialog();
      return;
    }
    String str = paramNewsInfo.mPrivacy;
    if ((!User.getInstance().getUID().equals(paramNewsInfo.mFuid)) && ("2".equals(str)))
    {
      Toast.makeText(this.mContext, 2131427774, 0).show();
      return;
    }
    NewsModel.getInstance().setActiveItem(paramNewsInfo);
    Intent localIntent = new Intent(this.mContext, DiaryDetailFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("fuid", paramNewsInfo.mFuid);
    localBundle.putString("fname", paramNewsInfo.mFname);
    localBundle.putString("did", paramNewsInfo.mNewsId);
    localBundle.putString("commentflag", paramNewsInfo.mCommentFlag);
    localBundle.putString("title", paramNewsInfo.mTitle);
    localBundle.putString("intro", paramNewsInfo.mIntro);
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragment(this.mFragment, localIntent, 1);
  }

  public void showLoadingFooter(boolean paramBoolean)
  {
    int j;
    int i;
    if (this.mFooterProBar != null)
    {
      ProgressBar localProgressBar = this.mFooterProBar;
      if (paramBoolean)
      {
        j = 0;
        localProgressBar.setVisibility(j);
      }
    }
    else if (this.mFooterTV != null)
    {
      i = this.mContext.getResources().getColor(2130839420);
      if (!paramBoolean)
        break label101;
      i = this.mContext.getResources().getColor(2130839395);
      this.mFooterTV.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
      this.mFooterTV.setText(2131427380);
    }
    while (true)
    {
      this.mFooterTV.setTextColor(i);
      return;
      j = 8;
      break;
      label101: this.mFooterTV.setCompoundDrawablesWithIntrinsicBounds(this.mContext.getResources().getDrawable(2130837595), null, null, null);
      this.mFooterTV.setText(2131427418);
    }
  }

  public void showLoginDialog()
  {
    if ((this.mFragment != null) && ((this.mFragment instanceof NewsFragment)))
    {
      ((NewsFragment)this.mFragment).showHideLoginLayoutAnimation(0);
      return;
    }
    Activity localActivity = this.mContext;
    String[] arrayOfString = new String[2];
    arrayOfString[0] = this.mContext.getString(2131427338);
    arrayOfString[1] = this.mContext.getString(2131427339);
    DialogUtil.showSelectListDlg(localActivity, 2131427729, arrayOfString, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        switch (paramInt)
        {
        default:
          return;
        case 0:
          Intent localIntent2 = new Intent(NewsItemAdapter.this.mContext, LoginActivity.class);
          AnimationUtil.startFragment(NewsItemAdapter.this.mFragment, localIntent2, 1);
          NewsItemAdapter.this.mContext.finish();
          return;
        case 1:
        }
        Intent localIntent1 = new Intent(NewsItemAdapter.this.mContext, LoginActivity.class);
        Bundle localBundle = new Bundle();
        localBundle.putInt("regist", 1);
        localIntent1.putExtras(localBundle);
        AnimationUtil.startFragment(NewsItemAdapter.this.mFragment, localIntent1, 1);
        NewsItemAdapter.this.mContext.finish();
      }
    }
    , 1);
  }

  public void showPhoto(NewsInfo paramNewsInfo)
  {
    if (User.getInstance().GetLookAround())
    {
      showLoginDialog();
      return;
    }
    if ((paramNewsInfo.mPhotoList == null) || ((paramNewsInfo.mPhotoList != null) && (paramNewsInfo.mPhotoList.isEmpty())))
    {
      Toast.makeText(this.mContext, 2131427902, 0).show();
      return;
    }
    PhotoItem localPhotoItem = (PhotoItem)paramNewsInfo.mPhotoList.get(0);
    if ((!User.getInstance().getUID().equals(localPhotoItem.fuid)) && ("1".equals(localPhotoItem.privacy)) && ("0".equals(localPhotoItem.visible)))
    {
      Toast.makeText(this.mContext, 2131427903, 0).show();
      return;
    }
    KXLinkInfo localKXLinkInfo = getUserInfo(NewsInfo.makeTitleList(paramNewsInfo.mSubTitle));
    getPhotoShowUtil().showPhoto(localPhotoItem.albumId, 2, Integer.parseInt(localPhotoItem.index), localKXLinkInfo.getId(), localPhotoItem.title, localPhotoItem.privacy);
  }

  public void showRepost(NewsInfo paramNewsInfo)
  {
    if ((paramNewsInfo == null) || (this.mContext == null))
      return;
    if (User.getInstance().GetLookAround())
    {
      showLoginDialog();
      return;
    }
    NewsModel.getInstance().setActiveItem(paramNewsInfo);
    Intent localIntent = new Intent(this.mContext, RepostListFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("commentflag", paramNewsInfo.mCommentFlag);
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragment(this.mFragment, localIntent, 1);
  }

  public void showRepost3Item(NewsInfo paramNewsInfo)
  {
    if ("转发照片专辑".equals(paramNewsInfo.mNtypename))
      showAlbum(paramNewsInfo);
    do
    {
      return;
      if (!"转发投票".equals(paramNewsInfo.mNtypename))
        continue;
      String str = getKXLinkInfoByAppID(NewsInfo.makeTitleList(paramNewsInfo.mSubTitle), "1016").getId();
      getVoteShowUtil().showVoteDetail(str);
      return;
    }
    while (!"转发照片".equals(paramNewsInfo.mNtypename));
    showPhoto(paramNewsInfo);
  }

  public void showStyleBoxDiaryDetail(NewsInfo paramNewsInfo)
  {
    if ((paramNewsInfo == null) || (this.mContext == null))
      return;
    Intent localIntent = new Intent(this.mContext, StyleBoxDiaryDetailFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("fuid", paramNewsInfo.mFuid);
    localBundle.putString("fname", paramNewsInfo.mFname);
    localBundle.putString("id", paramNewsInfo.mNewsId);
    localBundle.putString("commentflag", paramNewsInfo.mCommentFlag);
    localBundle.putString("title", paramNewsInfo.mTitle);
    localBundle.putString("intro", paramNewsInfo.mIntro);
    localBundle.putString("cnum", paramNewsInfo.mCnum);
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragment(this.mFragment, localIntent, 1);
  }

  public void showTruth(NewsInfo paramNewsInfo)
  {
    if ((paramNewsInfo == null) || (this.mContext == null))
      return;
    Intent localIntent = new Intent(this.mContext, TruthFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("fuid", paramNewsInfo.mFuid);
    localBundle.putString("fname", paramNewsInfo.mFname);
    localBundle.putString("cnum", paramNewsInfo.mCnum);
    localBundle.putString("tid", paramNewsInfo.mNewsId);
    localBundle.putString("commentflag", paramNewsInfo.mCommentFlag);
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragment(this.mFragment, localIntent, 1);
  }

  public void showVoteList(NewsInfo paramNewsInfo)
  {
    if ((paramNewsInfo == null) || (this.mContext == null));
    while (true)
    {
      return;
      NewsModel.getInstance().setActiveItem(paramNewsInfo);
      if ((paramNewsInfo.mVoteList != null) && (paramNewsInfo.mVoteList.size() != 0))
        break;
      if ("转发投票".equals(paramNewsInfo.mNtypename))
      {
        String str2 = getKXLinkInfoByAppID(NewsInfo.makeTitleList(paramNewsInfo.mSubTitle), "1016").getId();
        getVoteShowUtil().showVoteDetail(str2);
        return;
      }
      if (TextUtils.isEmpty(paramNewsInfo.mNewsId))
        continue;
      String str1 = paramNewsInfo.mNewsId;
      getVoteShowUtil().showVoteDetail(str1);
      return;
    }
    Intent localIntent = new Intent(this.mContext, VoteListFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("fuid", paramNewsInfo.mFuid);
    localBundle.putString("fname", paramNewsInfo.mFname);
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragment(this.mFragment, localIntent, 1);
  }

  public void showWeiboDetail(NewsInfo paramNewsInfo)
  {
    showWeiboDetail(paramNewsInfo, 0);
  }

  public void showWeiboDetail(NewsInfo paramNewsInfo, int paramInt)
  {
    if ((paramNewsInfo == null) || (this.mContext == null))
      return;
    if (User.getInstance().GetLookAround())
    {
      showLoginDialog();
      return;
    }
    showNewDetailPage("1018", paramNewsInfo, paramInt);
  }

  public void updateCurrentItem(NewsInfo paramNewsInfo)
  {
    if ((this.mCurrentCache == null) || (paramNewsInfo == null));
    do
      return;
    while (this.mCurrentCache.mLayoutComment.getVisibility() != 0);
    this.mCurrentCache.mLayoutComment.setTag(paramNewsInfo);
    String str1 = paramNewsInfo.mCnum + this.mContext.getString(2131427757);
    if (Integer.parseInt(str1) > 99)
      str1 = "99+";
    this.mCurrentCache.mTextComment.setText(str1);
    String str2 = paramNewsInfo.mUpnum + this.mContext.getString(2131427758);
    if (Integer.parseInt(str2) > 99)
      str2 = "99+";
    this.mCurrentCache.mTextUp.setText(str2);
  }

  public static class CacheView
  {
    public List<View> friendLogoItemList = new ArrayList(4);
    public List<ImageView> friendLogoList = new ArrayList(4);
    public LinearLayout friendLogoListLayout = null;
    public List<ImageView> giftLogoList = new ArrayList(3);
    public LinearLayout giftLogoListLayout = null;
    public View mCommentInfoLayout = null;
    public View mDevideLine = null;
    public ImageView mDiaryContentImage = null;
    public TextView mDiaryContentText1 = null;
    public TextView mDiaryContentText2 = null;
    public KXIntroView mDiaryContentTitle = null;
    public View mForwardInfoLayout = null;
    public ImageView mImgDiary = null;
    public ImageView mImgFlogo = null;
    public ImageView mImgForwardDividingIcon = null;
    public ImageView mImgForwardIcon = null;
    public ImageView mImgItemIcon = null;
    public ImageView mImgLocationIcon = null;
    public ImageView mImgPhoto1 = null;
    public ImageView mImgPhoto11 = null;
    public ImageView mImgPhoto12 = null;
    public ImageView mImgPhoto13 = null;
    public ImageView mImgPhoto2 = null;
    public ImageView mImgPhoto21 = null;
    public ImageView mImgPhoto22 = null;
    public ImageView mImgPhoto23 = null;
    public ImageView mImgPhoto3 = null;
    public ImageView mImgSubInfoPhoto1 = null;
    public ImageView mImgSubInfoPhoto11 = null;
    public ImageView mImgSubInfoPhoto12 = null;
    public ImageView mImgSubInfoPhoto13 = null;
    public ImageView mImgSubInfoPhoto2 = null;
    public ImageView mImgSubInfoPhoto21 = null;
    public ImageView mImgSubInfoPhoto22 = null;
    public ImageView mImgSubInfoPhoto23 = null;
    public ImageView mImgSubInfoPhoto3 = null;
    public RelativeLayout mImgUpLayout = null;
    public RelativeLayout mLayoutComment = null;
    public RelativeLayout mLayoutContent = null;
    public RelativeLayout mLayoutDiary = null;
    public LinearLayout mLayoutLocation = null;
    public LinearLayout mLayoutRepost1 = null;
    public LinearLayout mLayoutRepost2 = null;
    public LinearLayout mLayoutRepost3 = null;
    public LinearLayout mLayoutRepost4 = null;
    public LinearLayout mLayoutRepost5 = null;
    public LinearLayout mLayoutRepost6 = null;
    public LinearLayout mLayoutSubInfo = null;
    public LinearLayout mLlyttItemBg = null;
    public KXIntroView mLocationLbs = null;
    public LinearLayout mLocationLbsParent = null;
    private KXMediaView mMediaView = null;
    public KXIntroView mNewsItemContent = null;
    public KXIntroView mNewsItemText = null;
    public KXIntroView mSubInfoContent = null;
    public KXIntroView mSubInfoTitle = null;
    private KXMediaView mSubMediaView = null;
    public TextView mTextClientName = null;
    public TextView mTextComment = null;
    public TextView mTextDiary = null;
    public TextView mTextForward = null;
    public TextView mTextLocation = null;
    public TextView mTextReleaseTime = null;
    public TextView mTextRepost1 = null;
    public TextView mTextRepost2 = null;
    public TextView mTextRepost3 = null;
    public TextView mTextRepost4 = null;
    public TextView mTextRepost5 = null;
    public TextView mTextRepost6 = null;
    public TextView mTextRepostExt1 = null;
    public TextView mTextRepostExt2 = null;
    public TextView mTextRepostExt3 = null;
    public TextView mTextRepostExt4 = null;
    public TextView mTextRepostExt5 = null;
    public TextView mTextRepostExt6 = null;
    public ImageView mTextRepostImg1 = null;
    public ImageView mTextRepostImg2 = null;
    public ImageView mTextRepostImg3 = null;
    public ImageView mTextRepostImg4 = null;
    public ImageView mTextRepostImg5 = null;
    public TextView mTextUp = null;
    public RelativeLayout newsItemImgGroup1 = null;
    public RelativeLayout newsItemImgGroup11 = null;
    public RelativeLayout newsItemImgGroup12 = null;
    public RelativeLayout newsItemImgGroup13 = null;
    public RelativeLayout newsItemImgGroup2 = null;
    public RelativeLayout newsItemImgGroup21 = null;
    public RelativeLayout newsItemImgGroup22 = null;
    public RelativeLayout newsItemImgGroup23 = null;
    public RelativeLayout newsItemImgGroup3 = null;
    public LinearLayout newsItemImgSubInfolayout1 = null;
    public RelativeLayout newsItemImgSubInfolayout2 = null;
    public LinearLayout newsItemImglayout = null;
    public RelativeLayout newsItemImglayout2 = null;
    public RelativeLayout newsItemSubInfoImgGroup1 = null;
    public RelativeLayout newsItemSubInfoImgGroup11 = null;
    public RelativeLayout newsItemSubInfoImgGroup12 = null;
    public RelativeLayout newsItemSubInfoImgGroup13 = null;
    public RelativeLayout newsItemSubInfoImgGroup2 = null;
    public RelativeLayout newsItemSubInfoImgGroup21 = null;
    public RelativeLayout newsItemSubInfoImgGroup22 = null;
    public RelativeLayout newsItemSubInfoImgGroup23 = null;
    public RelativeLayout newsItemSubInfoImgGroup3 = null;
    public ImageView sendImgView = null;
    public View toolsView = null;
  }

  class CommentOnClickListener
    implements View.OnClickListener
  {
    CommentOnClickListener()
    {
    }

    public void onClick(View paramView)
    {
      NewsItemAdapter.this.onCommentLayoutClick(paramView, -1);
    }
  }

  protected class ImageOnClickListener
    implements View.OnClickListener
  {
    protected ImageOnClickListener()
    {
    }

    public void onClick(View paramView)
    {
      if (User.getInstance().GetLookAround())
      {
        NewsItemAdapter.this.showLoginDialog();
        return;
      }
      String[] arrayOfString = (String[])paramView.getTag();
      NewsItemAdapter.this.showPhoto(arrayOfString);
    }
  }

  public static class NewsItemUI
  {
    public int mFirstBackGround = -1;
    public int mItemBackGround = -1;
  }

  public static abstract interface OnViewMoreClickListener
  {
    public abstract void onViewMoreClick();
  }

  private class PhotoPostUpTask extends AsyncTask<Object, Void, Integer>
  {
    private BaseFragment aFragment;
    private NewsInfo mActiveNewsInfo;
    private NewsItemAdapter mAdapter;

    public PhotoPostUpTask(BaseFragment arg2)
    {
      Object localObject;
      this.aFragment = localObject;
    }

    protected Integer doInBackground(Object[] paramArrayOfObject)
    {
      this.mActiveNewsInfo = ((NewsInfo)paramArrayOfObject[0]);
      this.mAdapter = ((NewsItemAdapter)paramArrayOfObject[1]);
      try
      {
        if (this.mActiveNewsInfo.mHasUp.booleanValue())
          return Integer.valueOf(2);
        Integer localInteger = Integer.valueOf(UpEngine.getInstance().postUp(this.aFragment.getActivity(), "1", ((PhotoItem)this.mActiveNewsInfo.mPhotoList.get(0)).pid, this.mActiveNewsInfo.mFuid));
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecute(Integer paramInteger)
    {
      if (paramInteger == null)
        Toast.makeText(this.aFragment.getActivity(), 2131427387, 0).show();
      while (true)
      {
        return;
        try
        {
          if (paramInteger.intValue() != 1)
            break;
          this.mActiveNewsInfo.mHasUp = Boolean.valueOf(true);
          this.aFragment.showToast(2130838515, 2131428458);
          this.mActiveNewsInfo.mUpnum = (1 + Integer.parseInt(this.mActiveNewsInfo.mUpnum));
          if (this.mAdapter == null)
            continue;
          this.mAdapter.notifyDataSetChanged();
          return;
        }
        catch (Exception localException)
        {
          KXLog.e("NewsItemAdapter", "onPostExecute", localException);
          return;
        }
      }
      if (paramInteger.intValue() == 2)
      {
        this.aFragment.showToast(0, 2131428457);
        return;
      }
      this.aFragment.showToast(0, 2131427378);
    }

    protected void onPreExecute()
    {
      Toast.makeText(this.aFragment.getActivity(), 2131427618, 0).show();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.adapter.NewsItemAdapter
 * JD-Core Version:    0.6.0
 */