package com.kaixin001.fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.RecordEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.item.DetailItem;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.item.RecordInfo;
import com.kaixin001.model.RecordModel;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpConnection;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.view.KXIntroView;
import com.kaixin001.view.KXIntroView.OnClickLinkListener;
import java.util.ArrayList;

public class WeiboDetailFragment extends BaseFragment
  implements View.OnClickListener, KXIntroView.OnClickLinkListener
{
  private static final int RECORD_ERRORNUM_DELETED = 4001;
  public static final int WEIBO_DETAIL_ACTIVITY = 110;
  private LinearLayout flInfo;
  private LinearLayout flSubInfo;
  private ImageView ivInfo;
  private ImageView ivLogo;
  private ImageView ivSource;
  private ImageView ivSubInfo;
  private KXIntroView kvLocation;
  private String mCthreadCid = "";
  private FrameLayout mFLWeiboUi = null;
  private String mFlogo;
  private String mFname = "";
  private String mFuid;
  private LinearLayout mLayoutProgressBar = null;
  private LinearLayout mLlytRecordInfo;
  private LinearLayout mLlytSubRecordInfo;
  private int mMode = -1;
  private int mPosition = -1;
  private String mReplyContent = "";
  private boolean mVideoPressed = false;
  private String mViewFname;
  private boolean mbHavePhoto = false;
  private ImageView mivVideoThumbnailInfo;
  private ImageView mivVideoThumbnailSubInfo;
  private String msRecordId = null;
  private LinearLayout news_item_layout_location = null;
  private LinearLayout news_item_layout_subinfo = null;
  private RecordInfo recordInfo;
  private KXIntroView subinfo_title;
  private KXIntroView subinfo_title1;
  private GetRecordDetailTask tTask = null;
  private TextView tvComment;
  private TextView tvRepost;
  private TextView tvSource;
  private TextView tvTime;
  private LinearLayout weibo_comment_btn = null;
  private LinearLayout weibo_fw_btn = null;

  static
  {
    if (!WeiboDetailFragment.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  private void InitAttribute(RecordInfo paramRecordInfo)
  {
    if (paramRecordInfo != null)
    {
      if (paramRecordInfo.getRecordFeedFuid() != null)
        this.mFuid = paramRecordInfo.getRecordFeedFuid();
      if (paramRecordInfo.getRecordFeedFname() != null)
        this.mFname = paramRecordInfo.getRecordFeedFname();
      if (paramRecordInfo.getRecordFeedFlogo() != null)
        this.mFlogo = paramRecordInfo.getRecordFeedFlogo();
    }
  }

  private void commentAction()
  {
    if (this.recordInfo == null);
    while (true)
    {
      return;
      RecordInfo localRecordInfo = this.recordInfo;
      try
      {
        String str1 = localRecordInfo.getRecordFeedRid();
        if (TextUtils.isEmpty(str1))
          continue;
        Intent localIntent = new Intent(getActivity(), NewsDetailFragment.class);
        int i = this.mMode;
        String str2 = null;
        if (i == 3)
          str2 = this.mCthreadCid;
        DetailItem localDetailItem = DetailItem.makeWeiboDetailItem("1018", this.recordInfo.fpri, this.mFuid, this.mFname, this.mFlogo, "0", str1, this.recordInfo.getRecordFeedTitle(), this.recordInfo.getRecordFeedTime(), this.recordInfo.getRecordThumbnail(), this.recordInfo.getRecordLarge(), this.recordInfo.getRecordFeedSubRid(), "", this.recordInfo.getRecordFeedSubTitle(), this.recordInfo.getRecordSubThumbnail(), this.recordInfo.getRecordSubLarge(), this.recordInfo.getRecordFeedSubLocation(), str2, Integer.parseInt(this.recordInfo.getRecordFeedCnum()), Integer.parseInt(this.recordInfo.getRecordFeedTnum()));
        localDetailItem.setTagRecordInfo(this.recordInfo);
        Bundle localBundle = new Bundle();
        localBundle.putSerializable("data", localDetailItem);
        localIntent.putExtras(localBundle);
        startFragment(localIntent, true, 1);
        return;
      }
      catch (Exception localException)
      {
        KXLog.e("RecordListActivity", "onItemClick", localException);
      }
    }
  }

  private void fillAttributes()
  {
    Bundle localBundle = getArguments();
    if (localBundle == null)
      return;
    this.recordInfo = ((RecordInfo)localBundle.getSerializable("recordInfo"));
    if (this.recordInfo != null)
      InitAttribute(this.recordInfo);
    if (this.mFname.length() > 3);
    for (this.mViewFname = (this.mFname.substring(0, 3) + "..."); ; this.mViewFname = this.mFname)
    {
      String str = localBundle.getString("cthread_cid");
      if (str != null)
        this.mCthreadCid = str;
      int i = localBundle.getInt("mode");
      if (i != 0)
        this.mMode = i;
      int j = localBundle.getInt("position");
      if (j == -1)
        break;
      this.mPosition = j;
      return;
    }
  }

  private void forwardWeiboAction()
  {
    if (this.recordInfo == null)
      return;
    if (this.recordInfo.fpri == 0)
    {
      DialogUtil.showMsgDlgStdConfirm(getActivity(), 2131427329, 2131427744, null);
      return;
    }
    Intent localIntent = new Intent();
    localIntent.setClass(getActivity(), ForwardWeiboFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putSerializable("recordInfo", this.recordInfo);
    localIntent.putExtras(localBundle);
    startActivityForResult(localIntent, 110);
  }

  private void initLogo()
  {
    if (!TextUtils.isEmpty(this.mFlogo))
      displayPicture(this.ivLogo, this.mFlogo, 2130838676);
    while (true)
    {
      this.ivLogo.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          if ((WeiboDetailFragment.this.mFuid == null) || (WeiboDetailFragment.this.mFname == null))
            return;
          KXLog.d("DEBUG", "RecordListActivity onClick(KXLinkInfo linkInfo)");
          IntentUtil.showHomeFragment(WeiboDetailFragment.this, WeiboDetailFragment.this.mFuid, WeiboDetailFragment.this.mFname, WeiboDetailFragment.this.mFlogo);
        }
      });
      return;
      this.ivLogo.setImageResource(2130838676);
    }
  }

  private void initViews()
  {
    this.mFLWeiboUi = ((FrameLayout)findViewById(2131364039));
    this.ivLogo = ((ImageView)findViewById(2131364062));
    this.ivInfo = ((ImageView)findViewById(2131364044));
    this.mivVideoThumbnailInfo = ((ImageView)findViewById(2131364045));
    this.mivVideoThumbnailSubInfo = ((ImageView)findViewById(2131364048));
    this.flInfo = ((LinearLayout)findViewById(2131364043));
    this.mLlytRecordInfo = ((LinearLayout)findViewById(2131363543));
    this.mLlytSubRecordInfo = ((LinearLayout)findViewById(2131363548));
    this.flSubInfo = ((LinearLayout)findViewById(2131363313));
    this.ivSubInfo = ((ImageView)findViewById(2131363549));
    this.weibo_fw_btn = ((LinearLayout)findViewById(2131364055));
    this.weibo_comment_btn = ((LinearLayout)findViewById(2131364058));
    this.weibo_fw_btn.setOnClickListener(this);
    this.weibo_comment_btn.setOnClickListener(this);
    this.news_item_layout_subinfo = ((LinearLayout)findViewById(2131362644));
    this.news_item_layout_location = ((LinearLayout)findViewById(2131362662));
    this.subinfo_title = ((KXIntroView)findViewById(2131363525));
    this.subinfo_title1 = ((KXIntroView)findViewById(2131362646));
    this.tvRepost = ((TextView)findViewById(2131364057));
    this.tvComment = ((TextView)findViewById(2131364060));
    this.kvLocation = ((KXIntroView)findViewById(2131362664));
    this.tvTime = ((TextView)findViewById(2131364051));
    this.ivSource = ((ImageView)findViewById(2131364052));
    this.tvSource = ((TextView)findViewById(2131364053));
    this.mLayoutProgressBar = ((LinearLayout)findViewById(2131364041));
  }

  private void openByUc(String paramString1, String paramString2)
  {
    Intent localIntent = new Intent();
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.setData(Uri.parse(paramString2));
    if (paramString1.equals("com.uc.browser"))
    {
      localIntent.setClassName("com.uc.browser", "com.uc.browser.ActivityUpdate");
      startActivity(localIntent);
      return;
    }
    if (paramString1.equals("com.UCMobile"))
    {
      localIntent.setClassName("com.UCMobile", "com.UCMobile.main.UCMobile");
      startActivity(localIntent);
      return;
    }
    startActivity(localIntent);
  }

  private void reInitUiControls(RecordInfo paramRecordInfo)
  {
    this.mFLWeiboUi.setVisibility(0);
    InitAttribute(paramRecordInfo);
    setTitleBar();
    initLogo();
    setViewVisibility();
    initViewData();
  }

  private void refreshRecordDetailData()
  {
    if (HttpConnection.checkNetworkAvailable(getActivity()) < 0)
      return;
    assert (this.msRecordId != null);
    this.tTask = new GetRecordDetailTask(null);
    GetRecordDetailTask localGetRecordDetailTask = this.tTask;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(this.msRecordId);
    localGetRecordDetailTask.execute(arrayOfString);
  }

  private void setThumbnail(String paramString1, String paramString2, ImageView paramImageView, LinearLayout paramLinearLayout)
  {
    if (!TextUtils.isEmpty(paramString1))
    {
      displayPicture(paramImageView, paramString1, 2130838779);
      paramImageView.setOnClickListener(new View.OnClickListener(new String[] { paramString1, paramString2 })
      {
        public void onClick(View paramView)
        {
          Intent localIntent = new Intent(WeiboDetailFragment.this.getActivity().getApplicationContext(), PreviewUploadPhotoFragment.class);
          localIntent.putExtra("small", this.val$params[0]);
          localIntent.putExtra("large", this.val$params[1]);
          localIntent.putExtra("action", "com.kaixin001.VIEW_SINGLE_PHOTO");
          WeiboDetailFragment.this.startFragment(localIntent, true, 1);
        }
      });
      this.mbHavePhoto = true;
    }
  }

  private void setVideoThumbnail(RecordInfo paramRecordInfo, String paramString, ImageView paramImageView)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      showVideoThumbnail(paramImageView, paramString);
      this.mbHavePhoto = true;
    }
    paramImageView.setOnClickListener(new View.OnClickListener(paramRecordInfo)
    {
      public void onClick(View paramView)
      {
        label10: ArrayList localArrayList;
        if (WeiboDetailFragment.this.mVideoPressed)
        {
          return;
        }
        else
        {
          if (this.val$info.getRecordFeedSubTitle() == null)
            break label105;
          localArrayList = NewsInfo.makeIntroList(this.val$info.getRecordFeedSubTitle());
        }
        label32: for (int i = 0; ; i++)
        {
          if (i >= localArrayList.size())
          {
            if (i >= localArrayList.size())
              break label10;
            WeiboDetailFragment.this.mVideoPressed = true;
            IntentUtil.showVideoPage(WeiboDetailFragment.this.getActivity().getApplicationContext(), ((KXLinkInfo)localArrayList.get(i)).getId(), ((KXLinkInfo)localArrayList.get(i)).getType(), ((KXLinkInfo)localArrayList.get(i)).getContent());
            return;
            label105: localArrayList = NewsInfo.makeIntroList(this.val$info.getRecordFeedTitle());
            break label32;
          }
          if (((KXLinkInfo)localArrayList.get(i)).isVideo())
            break;
        }
      }
    });
  }

  private void setViewVisibility()
  {
    this.flInfo.setVisibility(0);
    this.flSubInfo.setVisibility(0);
    this.news_item_layout_subinfo.setVisibility(0);
    this.news_item_layout_location.setVisibility(0);
    this.ivSource.setVisibility(0);
    this.tvSource.setVisibility(0);
    if (this.recordInfo != null)
    {
      if (!TextUtils.isEmpty(this.recordInfo.getRecordFeedSource()))
        break label218;
      this.ivSource.setVisibility(8);
      this.tvSource.setVisibility(8);
      if (this.recordInfo.isRepost())
        break label262;
      if (this.recordInfo.getRecordFeedLocation() == null)
        this.news_item_layout_location.setVisibility(8);
      this.news_item_layout_subinfo.setVisibility(8);
      this.flSubInfo.setVisibility(8);
      setThumbnail(this.recordInfo.getRecordThumbnail(), this.recordInfo.getRecordLarge(), this.ivInfo, this.mLlytRecordInfo);
      if (!TextUtils.isEmpty(this.recordInfo.getRecordThumbnail()))
        break label251;
      this.mLlytRecordInfo.setVisibility(8);
    }
    while (true)
    {
      setVideoThumbnail(this.recordInfo, this.recordInfo.getRecordVideoLogo(0), this.mivVideoThumbnailInfo);
      if (!this.mbHavePhoto)
        this.mivVideoThumbnailInfo.setVisibility(8);
      return;
      label218: this.ivSource.setVisibility(0);
      this.tvSource.setText(this.recordInfo.getRecordFeedSource());
      this.tvSource.setVisibility(0);
      break;
      label251: this.mLlytRecordInfo.setVisibility(0);
    }
    label262: this.news_item_layout_location.setVisibility(8);
    this.flInfo.setVisibility(8);
    if ((this.recordInfo.getRecordSubThumbnail() == null) || (this.recordInfo.getRecordSubThumbnail().equals("")))
      this.mLlytRecordInfo.setVisibility(8);
    setThumbnail(this.recordInfo.getRecordSubThumbnail(), this.recordInfo.getRecordSubLarge(), this.ivSubInfo, this.mLlytSubRecordInfo);
    if (TextUtils.isEmpty(this.recordInfo.getRecordSubThumbnail()))
      this.mLlytSubRecordInfo.setVisibility(8);
    while (true)
    {
      setVideoThumbnail(this.recordInfo, this.recordInfo.getRecordVideoLogo(0), this.mivVideoThumbnailSubInfo);
      if (this.mbHavePhoto)
        break;
      this.mivVideoThumbnailSubInfo.setVisibility(8);
      return;
      this.mLlytSubRecordInfo.setVisibility(0);
    }
    this.mivVideoThumbnailSubInfo.setVisibility(0);
  }

  public void initViewData()
  {
    StringBuilder localStringBuilder;
    if (this.recordInfo != null)
    {
      localStringBuilder = new StringBuilder("[|s|]").append(this.recordInfo.getRecordFeedFname()).append("[|m|]").append(this.recordInfo.getRecordFeedFuid()).append("[|m|]");
      if (!TextUtils.isEmpty(this.recordInfo.getRecordFeedStar()))
        break label352;
    }
    label352: for (String str1 = "0"; ; str1 = this.recordInfo.getRecordFeedStar())
    {
      String str2 = str1 + "[|e|]";
      String str3 = this.recordInfo.getRecordFeedTitle();
      if (!str3.startsWith(str2))
        str3 = str2 + "：" + str3;
      this.subinfo_title.setTitleList(RecordInfo.parseRecordTitleUtil(str3));
      this.subinfo_title.setOnClickLinkListener(this);
      StringBuffer localStringBuffer = new StringBuffer("");
      localStringBuffer.append(this.recordInfo.getRecordFeedSubTitle());
      if (this.recordInfo.getRecordFeedSubLocation() != null)
        localStringBuffer.append(this.recordInfo.getRecordFeedSubLocation());
      this.subinfo_title1.setTitleList(RecordInfo.parseRecordTitleUtil(localStringBuffer.toString()));
      this.subinfo_title1.setOnClickLinkListener(this);
      this.kvLocation.setTitleList(RecordInfo.parseRecordTitleUtil(this.recordInfo.getRecordFeedLocation()));
      this.tvRepost.setText(getResources().getString(2131427371) + "(" + this.recordInfo.getRecordFeedTnum() + ")");
      this.tvComment.setText(getResources().getString(2131427372) + "(" + this.recordInfo.getRecordFeedCnum() + ")");
      this.tvTime.setText(this.recordInfo.getRecordFeedTime());
      return;
    }
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt2 == -1)
    {
      if ((paramInt1 != 10) && (paramInt1 != 1201))
        break label199;
      String str1 = paramIntent.getStringExtra("cnum");
      String str2 = paramIntent.getStringExtra("tnum");
      if ((str1 == null) || (str1.equals("null")))
        str1 = "0";
      if ((str2 == null) || (str1.equals("null")))
        str2 = "0";
      this.recordInfo.setRecordFeedCnum(str1);
      this.recordInfo.setRecordFeedTnum(str2);
      this.tvRepost.setText(getResources().getString(2131427371) + "(" + this.recordInfo.getRecordFeedTnum() + ")");
      this.tvComment.setText(getResources().getString(2131427372) + "(" + this.recordInfo.getRecordFeedCnum() + ")");
    }
    label199: 
    do
      return;
    while (paramInt1 != 3);
    this.mReplyContent = paramIntent.getStringExtra("content");
  }

  public void onClick(View paramView)
  {
    paramView.requestFocus();
    if (this.weibo_fw_btn.equals(paramView))
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
              IntentUtil.returnToLogin(WeiboDetailFragment.this.getActivity(), false);
              return;
            case 1:
            }
            Bundle localBundle = new Bundle();
            localBundle.putInt("regist", 1);
            IntentUtil.returnToLogin(WeiboDetailFragment.this.getActivity(), localBundle, false);
          }
        }
        , 1);
      }
    do
    {
      return;
      forwardWeiboAction();
      return;
    }
    while (!this.weibo_comment_btn.equals(paramView));
    commentAction();
  }

  public void onClick(KXLinkInfo paramKXLinkInfo)
  {
    if (this.mVideoPressed);
    do
    {
      return;
      if ((paramKXLinkInfo.isStar()) || (paramKXLinkInfo.isUserName()))
      {
        IntentUtil.showHomeFragment(this, paramKXLinkInfo.getId(), paramKXLinkInfo.getContent());
        return;
      }
      if (paramKXLinkInfo.isUrlLink())
      {
        IntentUtil.showWebPage(getActivity(), this, paramKXLinkInfo.getId(), null);
        return;
      }
      if (!paramKXLinkInfo.isVideo())
        continue;
      this.mVideoPressed = true;
      IntentUtil.showVideoPage(getActivity().getApplicationContext(), paramKXLinkInfo.getId(), paramKXLinkInfo.getType(), paramKXLinkInfo.getContent());
      return;
    }
    while (!paramKXLinkInfo.isTopic());
    IntentUtil.showTopicGroupActivity(this, paramKXLinkInfo.getId());
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903420, paramViewGroup, false);
  }

  public void onDestroy()
  {
    cancelTask(this.tTask);
    super.onDestroy();
  }

  public void onResume()
  {
    super.onResume();
    this.mVideoPressed = false;
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    initViews();
    Bundle localBundle = getArguments();
    if (localBundle != null)
      this.recordInfo = ((RecordInfo)localBundle.getSerializable("recordInfo"));
    if (this.recordInfo != null)
    {
      this.mFLWeiboUi.setVisibility(0);
      fillAttributes();
      setTitleBar();
      initLogo();
      setViewVisibility();
      initViewData();
      return;
    }
    this.msRecordId = localBundle.getString("recordid");
    this.mLayoutProgressBar.setVisibility(0);
    refreshRecordDetailData();
    setTitleBar();
  }

  protected void setTitleBar()
  {
    ImageView localImageView = (ImageView)findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (WeiboDetailFragment.this.mMode == 3)
          if (!TextUtils.isEmpty(WeiboDetailFragment.this.mReplyContent))
          {
            Intent localIntent2 = new Intent();
            localIntent2.putExtra("content", WeiboDetailFragment.this.mReplyContent);
            WeiboDetailFragment.this.setResult(-1, localIntent2);
            WeiboDetailFragment.this.finishFragment(3);
          }
        while (true)
        {
          WeiboDetailFragment.this.finish();
          return;
          Intent localIntent1 = new Intent();
          if (WeiboDetailFragment.this.recordInfo == null)
            continue;
          String str1 = WeiboDetailFragment.this.recordInfo.getRecordFeedCnum();
          String str2 = WeiboDetailFragment.this.recordInfo.getRecordFeedTnum();
          localIntent1.putExtra("cnum", str1);
          localIntent1.putExtra("tnum", str2);
          localIntent1.putExtra("rid", WeiboDetailFragment.this.recordInfo.getRecordFeedRid());
          WeiboDetailFragment.this.setResult(-1, localIntent1);
          WeiboDetailFragment.this.finishFragment(110);
        }
      }
    });
    ((ImageView)findViewById(2131362928)).setVisibility(8);
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)findViewById(2131362920);
    String str = getResources().getString(2131427753);
    if ((this.mFuid != null) && (User.getInstance().getUID().compareTo(this.mFuid) == 0))
      localTextView.setText(getResources().getString(2131427737));
    while (true)
    {
      localTextView.setVisibility(0);
      getResources().getString(2131427597);
      return;
      if (this.mViewFname != null)
      {
        localTextView.setText(this.mViewFname + str);
        continue;
      }
      localTextView.setText(getString(2131427565) + str);
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
      String str = paramArrayOfString[0];
      try
      {
        boolean bool2 = RecordEngine.getInstance().doGetRecordDetailMoreRequest(WeiboDetailFragment.this.getActivity().getApplicationContext(), str, User.getInstance().getUID());
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
      WeiboDetailFragment.this.mLayoutProgressBar.setVisibility(8);
      if (paramBoolean == null)
      {
        WeiboDetailFragment.this.finish();
        return;
      }
      if (paramBoolean.booleanValue())
      {
        WeiboDetailFragment.this.recordInfo = RecordModel.getInstance().getRecordInfo();
        WeiboDetailFragment.this.reInitUiControls(WeiboDetailFragment.this.recordInfo);
        return;
      }
      if (RecordModel.getInstance().getErrorNum() == 4001)
      {
        Toast.makeText(WeiboDetailFragment.this.getActivity().getApplicationContext(), 2131427923, 0).show();
        return;
      }
      Toast.makeText(WeiboDetailFragment.this.getActivity(), 2131427752, 0).show();
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
 * Qualified Name:     com.kaixin001.fragment.WeiboDetailFragment
 * JD-Core Version:    0.6.0
 */