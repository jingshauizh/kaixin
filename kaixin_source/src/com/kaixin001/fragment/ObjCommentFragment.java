package com.kaixin001.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.LoginActivity;
import com.kaixin001.businesslogic.ShowPhoto;
import com.kaixin001.engine.ObjCommentEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.UpEngine;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.item.ObjCommentItem;
import com.kaixin001.item.RecordInfo;
import com.kaixin001.model.NewsModel;
import com.kaixin001.model.ObjCommentModel;
import com.kaixin001.model.ObjCommentModel.LbsCommentTitle;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.KXTextUtil;
import com.kaixin001.util.ParseNewsInfoUtil;
import com.kaixin001.view.KXIntroView;
import com.kaixin001.view.KXIntroView.OnClickLinkListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ObjCommentFragment extends BaseFragment
  implements View.OnClickListener, KXIntroView.OnClickLinkListener
{
  public static final int COMMENT_INVALID_INT = -1;
  public static final int COMMENT_OR_REPLY_MODE = 3;
  public static final int COMMON_MODE = 5;
  public static final String MODE_KEY = "mode";
  public static final int NEWS_MODE = 1;
  public static final int OBJ_COMMENT_CODE = 10;
  public static final int RESQUET_NEWS_CHANGED_CODE = 1230;
  private String AT = "[|s|]@[|m|]10066329[|m|]-101[|e|]";
  private int Cnum = -1;
  private int Tnum = -1;
  ShowPhoto albumShowUtil;
  private boolean bHasNew = false;
  private boolean bHasUp = false;
  private ProgressBar barLikeState;
  private ImageView btnLeft;
  private ImageView btnRight;
  private final ArrayList<ObjCommentItem> commentList = new ArrayList();
  private GetDataTask getDataTask;
  private GetLBSTitleTask getLbsTitleTask;
  private ImageView ivSubInfo1;
  private ImageView ivSubInfo2;
  private ImageView ivSubInfo3;
  private LinearLayout llPhotoes;
  private LinearLayout llSubInfo;
  private ListView lvComment;
  private final CommentAdapter mAdapter = new CommentAdapter(getActivity(), 2130903266, this.commentList);
  private String mClientName = null;
  private String mCommentFlag = "1";
  private final ObjCommentModel mCommentModel = new ObjCommentModel();
  private String mCthreadCid = "";
  private String mCtime;
  private String mFname;
  private String mFuid;
  private View mHeaderView;
  private String mId;
  private String mIntro;
  private boolean mIsSameThreadCid = false;
  private String mLarge;
  private String[] mLarges;
  private String mLocation = null;
  private int mNeedRefresh = -1;
  private String mNtype;
  private String[] mPostions;
  private String mReplyContent = "";
  private String mRepostAlbumId;
  private String mRepostAlbumTitle;
  private String mRepostIndex;
  private String mRepostName;
  private String mRepostPicNum;
  private String mRepostPrivacy;
  private String mRepostTitle;
  private String mRepostUid;
  private String mRepostVisible;
  private String mStime;
  private String mSubLarge;
  private String mSubThumbnail;
  private String mThumbnail;
  private String[] mThumbnails;
  private String mTitle;
  private String[] mTitles;
  private String mTypeName;
  private int mUpNum = -1;
  private View mViewEmpty = null;
  private int mode = 0;
  private int newCommentNum = 0;
  private ProgressBar proBar;
  private String strLikeState;
  private String subLocation;
  private TextView tvLikeState;
  private TextView txtComment;
  private TextView txtWait;
  private PostUpTask upTask;

  public static String consistNameStartText(String paramString1, String paramString2, String paramString3)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[|s|]").append(paramString2).append("[|m|]");
    localStringBuilder.append(paramString3).append("[|m|]");
    if (!paramString1.startsWith(localStringBuilder.toString()))
      paramString1 = localStringBuilder.toString() + "0" + "[|e|]" + "：" + paramString1;
    return paramString1;
  }

  private void finishActivity()
  {
    Intent localIntent = new Intent();
    if ((this.bHasNew) || (this.bHasUp))
    {
      if (this.Cnum != -1)
        localIntent.putExtra("cnum", String.valueOf(this.Cnum));
      if (this.Tnum != -1)
        localIntent.putExtra("tnum", String.valueOf(this.Tnum));
      if (this.mode == 1)
        localIntent.putExtra("cnum", String.valueOf(this.mCommentModel.getTotalComment()));
      localIntent.putExtra("upnum", String.valueOf(this.mUpNum));
      setResult(-1, localIntent);
    }
    while (true)
    {
      finish();
      return;
      if ((this.mode != 3) || (TextUtils.isEmpty(this.mReplyContent)))
        continue;
      localIntent.putExtra("content", this.mReplyContent);
      setResult(-1, localIntent);
      finishFragment(3);
    }
  }

  private ShowPhoto getPhotoShowUtil()
  {
    if (this.albumShowUtil == null)
      this.albumShowUtil = new ShowPhoto(getActivity(), this, false);
    return this.albumShowUtil;
  }

  private View getTitleView()
  {
    View localView = ((LayoutInflater)getActivity().getSystemService("layout_inflater")).inflate(2130903267, null);
    updateTitileView(localView);
    return localView;
  }

  private void initEmptyView()
  {
    this.mViewEmpty = getActivity().getLayoutInflater().inflate(2130903097, null);
    ((TextView)this.mViewEmpty.findViewById(2131361995)).setText(2131427477);
  }

  private void setLikeState()
  {
    this.mUpNum = this.mCommentModel.getUpCount();
    boolean bool;
    int i;
    String str1;
    String str2;
    String str3;
    String str4;
    String str5;
    if (this.mUpNum > 0)
    {
      bool = this.mCommentModel.isSelfUp();
      i = this.mCommentModel.getUpListCount();
      if (bool)
      {
        str1 = getResources().getString(2131427565);
        i--;
        str2 = getResources().getString(2131427473);
        str3 = getResources().getString(2131427474);
        str4 = getResources().getString(2131427475);
        str5 = getResources().getString(2131427476);
        if ((!bool) || (this.mUpNum != 1))
          break label169;
        this.strLikeState = (str1 + str5);
      }
    }
    while (true)
    {
      this.barLikeState.setVisibility(8);
      this.tvLikeState.setText(this.strLikeState);
      return;
      this.btnRight.setEnabled(true);
      str1 = null;
      break;
      label169: if ((bool) && (this.mUpNum > 1) && (i == 0))
      {
        StringBuilder localStringBuilder = new StringBuilder(String.valueOf(str1)).append(str2);
        int j = -1 + this.mUpNum;
        this.mUpNum = j;
        this.strLikeState = (String.valueOf(j) + str4 + str5);
        continue;
      }
      if ((bool) && (this.mUpNum == 2) && (i == 1))
      {
        String str7 = this.mCommentModel.getFirstUpName();
        this.strLikeState = (str1 + str2 + str7 + str5);
        continue;
      }
      if ((bool) && (this.mUpNum > 2) && (i >= 1))
      {
        String str6 = this.mCommentModel.getFirstUpName();
        this.strLikeState = (str1 + str2 + str6 + str3 + String.valueOf(this.mUpNum) + str4 + str5);
        continue;
      }
      if ((!bool) && (this.mUpNum >= 1) && (i == 0))
      {
        this.strLikeState = (String.valueOf(this.mUpNum) + str4 + str5);
        continue;
      }
      if ((!bool) && (this.mUpNum == 1) && (i == 1))
      {
        this.strLikeState = (this.mCommentModel.getFirstUpName() + str5);
        continue;
      }
      if ((!bool) && (this.mUpNum > 1) && (i == 1))
      {
        this.strLikeState = (this.mCommentModel.getFirstUpName() + str3 + String.valueOf(this.mUpNum) + str4 + str5);
        continue;
      }
      if ((!bool) && (this.mUpNum == 2) && (i == 2))
      {
        String[] arrayOfString2 = this.mCommentModel.getTwoUpNames();
        this.strLikeState = (arrayOfString2[0] + str2 + arrayOfString2[1] + str5);
        continue;
      }
      if ((bool) || (this.mUpNum <= 2) || (i < 2))
        continue;
      String[] arrayOfString1 = this.mCommentModel.getTwoUpNames();
      this.strLikeState = (arrayOfString1[0] + str2 + arrayOfString1[1] + str3 + String.valueOf(this.mUpNum) + str4 + str5);
      continue;
      this.btnRight.setEnabled(true);
      this.strLikeState = getResources().getString(2131427480);
    }
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
          Intent localIntent = new Intent(ObjCommentFragment.this.getActivity(), PreviewUploadPhotoFragment.class);
          localIntent.putExtra("small", this.val$params[0]);
          localIntent.putExtra("large", this.val$params[1]);
          localIntent.putExtra("action", "com.kaixin001.VIEW_SINGLE_PHOTO");
          ObjCommentFragment.this.startFragment(localIntent, true, 1);
        }
      });
    }
    do
      return;
    while (paramLinearLayout == null);
    paramLinearLayout.setVisibility(8);
  }

  private void showPhoto(String paramString1, String paramString2)
  {
    if ((this.mThumbnails == null) || (this.mThumbnails.length == 0))
    {
      Toast.makeText(getActivity(), 2131427902, 0).show();
      return;
    }
    if ((!User.getInstance().getUID().equals(this.mRepostUid)) && ("1".equals(this.mRepostPrivacy)) && ("0".equals(this.mRepostVisible)))
    {
      Toast.makeText(getActivity(), 2131427903, 0).show();
      return;
    }
    getPhotoShowUtil().showPhoto(this.mRepostAlbumId, 2, Integer.parseInt(paramString1), this.mRepostUid, paramString2, this.mRepostPrivacy);
  }

  private void updateListView()
  {
    this.commentList.clear();
    ArrayList localArrayList = this.mCommentModel.getCommentList();
    if ((localArrayList != null) && (localArrayList.size() > 0))
      this.commentList.addAll(localArrayList);
    if (this.mAdapter != null)
      this.mAdapter.notifyDataSetChanged();
  }

  private void updateTitileView(View paramView)
  {
    ImageView localImageView1 = (ImageView)paramView.findViewById(2131363307);
    KXIntroView localKXIntroView1 = (KXIntroView)paramView.findViewById(2131363309);
    TextView localTextView1 = (TextView)paramView.findViewById(2131363319);
    ImageView localImageView2 = (ImageView)paramView.findViewById(2131363321);
    KXIntroView localKXIntroView2 = (KXIntroView)paramView.findViewById(2131363318);
    ImageView localImageView3 = (ImageView)paramView.findViewById(2131363317);
    TextView localTextView2 = (TextView)paramView.findViewById(2131363320);
    if ((!TextUtils.isEmpty(this.mNtype)) && (this.mNtype.equals("1192")))
    {
      if (!TextUtils.isEmpty(this.mLocation))
      {
        ArrayList localArrayList = ParseNewsInfoUtil.parseNewsLinkString(this.mLocation);
        if (!TextUtils.isEmpty(((KXLinkInfo)localArrayList.get(0)).getContent()))
        {
          String str4 = ((KXLinkInfo)localArrayList.get(0)).getContent().trim();
          ((KXLinkInfo)localArrayList.get(0)).setContent(str4);
        }
        localKXIntroView2.setTitleList(localArrayList);
        localKXIntroView2.setOnClickLinkListener(this);
        localKXIntroView2.setVisibility(0);
        localImageView3.setVisibility(0);
      }
      if (!TextUtils.isEmpty(this.mClientName))
      {
        String str3 = getResources().getString(2131427847);
        if (!this.mClientName.contains(str3))
          this.mClientName = (str3 + this.mClientName);
        localTextView2.setText(this.mClientName);
        localTextView2.setVisibility(0);
      }
    }
    this.tvLikeState = ((TextView)paramView.findViewById(2131363324));
    this.barLikeState = ((ProgressBar)paramView.findViewById(2131363323));
    this.ivSubInfo1 = ((ImageView)paramView.findViewById(2131363314));
    this.ivSubInfo2 = ((ImageView)paramView.findViewById(2131363315));
    this.ivSubInfo3 = ((ImageView)paramView.findViewById(2131363316));
    this.llSubInfo = ((LinearLayout)paramView.findViewById(2131363311));
    this.llPhotoes = ((LinearLayout)paramView.findViewById(2131363313));
    KXLog.d("DEBUG", "flSubInfo is " + this.llPhotoes);
    this.ivSubInfo1.setVisibility(8);
    this.ivSubInfo2.setVisibility(8);
    this.ivSubInfo3.setVisibility(8);
    if ((TextUtils.isEmpty(this.mFname)) || ((TextUtils.isEmpty(this.mIntro)) && (TextUtils.isEmpty(this.mTitle))))
    {
      localImageView1.setVisibility(8);
      localKXIntroView1.setVisibility(8);
      localTextView1.setVisibility(8);
      localImageView2.setVisibility(8);
    }
    label659: label729: int i;
    label794: ImageView localImageView5;
    while (true)
    {
      return;
      localImageView1.setVisibility(0);
      localKXIntroView1.setVisibility(0);
      localTextView1.setVisibility(0);
      localImageView2.setVisibility(0);
      localImageView1.setImageResource(Setting.getInstance().getAppIcon(this.mNtype, this.mTypeName));
      ImageView localImageView4 = (ImageView)paramView.findViewById(2131363310);
      KXIntroView localKXIntroView3 = (KXIntroView)paramView.findViewById(2131363312);
      if (TextUtils.isEmpty(this.mTitle))
        break label923;
      localKXIntroView1.setTitleList(ParseNewsInfoUtil.parseNewsLinkString(this.mTitle));
      localKXIntroView1.setOnClickLinkListener(this);
      if (TextUtils.isEmpty(this.mIntro))
        break label911;
      localKXIntroView3.setTitleList(ParseNewsInfoUtil.parseNewsLinkString(this.mIntro));
      localKXIntroView3.setOnClickLinkListener(this);
      localKXIntroView3.setVisibility(0);
      this.llSubInfo.setVisibility(0);
      if (!TextUtils.isEmpty(this.mSubThumbnail))
      {
        setThumbnail(this.mSubThumbnail, this.mSubLarge, this.ivSubInfo1, this.llPhotoes);
        this.llPhotoes.setVisibility(0);
        this.ivSubInfo1.setVisibility(0);
        if (!TextUtils.isEmpty(this.mThumbnail))
        {
          localImageView4.setVisibility(0);
          displayPicture(localImageView4, this.mThumbnail, 2130838779);
          String[] arrayOfString = new String[2];
          arrayOfString[0] = this.mThumbnail;
          arrayOfString[1] = this.mLarge;
          3 local3 = new View.OnClickListener(arrayOfString)
          {
            public void onClick(View paramView)
            {
              Intent localIntent = new Intent(ObjCommentFragment.this.getActivity(), PreviewUploadPhotoFragment.class);
              localIntent.putExtra("small", this.val$params[0]);
              localIntent.putExtra("large", this.val$params[1]);
              localIntent.putExtra("action", "com.kaixin001.VIEW_SINGLE_PHOTO");
              ObjCommentFragment.this.startFragment(localIntent, true, 1);
            }
          };
          localImageView4.setOnClickListener(local3);
        }
        localTextView1.setText(this.mStime);
        if ((this.mThumbnails == null) || (this.mThumbnails.length <= 0))
          continue;
        this.llPhotoes.setVisibility(0);
        ImageView[] arrayOfImageView = new ImageView[3];
        arrayOfImageView[0] = this.ivSubInfo1;
        arrayOfImageView[1] = this.ivSubInfo2;
        arrayOfImageView[2] = this.ivSubInfo3;
        i = 0;
        if (i >= this.mThumbnails.length)
          break;
        localImageView5 = arrayOfImageView[i];
        localImageView5.setVisibility(0);
        if ((User.getInstance().getUID().equals(this.mRepostUid)) || (!"2".equals(this.mRepostPrivacy)))
          break label942;
        localImageView5.setImageResource(2130838692);
      }
    }
    while (true)
    {
      String str1 = this.mPostions[i];
      String str2 = this.mTitles[i];
      4 local4 = new View.OnClickListener(str1, str2)
      {
        public void onClick(View paramView)
        {
          ObjCommentFragment.this.showPhoto(this.val$pos, this.val$title);
        }
      };
      localImageView5.setOnClickListener(local4);
      i++;
      break label794;
      break;
      this.llPhotoes.setVisibility(8);
      break label659;
      label911: this.llSubInfo.setVisibility(8);
      break label659;
      label923: localKXIntroView1.setTitleList(ParseNewsInfoUtil.parseNewsLinkString(this.mIntro));
      localKXIntroView1.setOnClickLinkListener(this);
      break label729;
      label942: displayPicture(localImageView5, this.mThumbnails[i], 2130838779);
    }
  }

  public void finish()
  {
    Intent localIntent = new Intent();
    localIntent.putExtra("comment", this.newCommentNum);
    setResult(-1, localIntent);
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 2);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt2 == -1)
    {
      if (paramInt1 != 2)
        break label159;
      this.bHasNew = true;
      this.newCommentNum = (1 + this.newCommentNum);
      String str4 = paramIntent.getStringExtra("content");
      String str5 = getResources().getString(2131427479);
      if (this.mCommentModel.isEmpty())
        this.mCommentModel.getCommentList().clear();
      String str6 = paramIntent.getStringExtra("cid");
      String str7 = paramIntent.getStringExtra("mtype");
      if (this.Cnum != -1)
        this.Cnum = (1 + this.Cnum);
      this.mCommentModel.addMainComment(0, str4, str6, str5, User.getInstance().getUID(), User.getInstance().getLogo(), User.getInstance().getRealName(), str7);
      this.commentList.clear();
      this.mAdapter.notifyDataSetChanged();
      this.lvComment.setSelection(0);
    }
    label159: 
    do
      return;
    while (paramInt1 != 8);
    this.bHasNew = true;
    String str1 = paramIntent.getStringExtra("content");
    if (this.mIsSameThreadCid)
      this.mReplyContent = str1;
    this.mIsSameThreadCid = false;
    String str2 = getResources().getString(2131427479);
    String str3 = paramIntent.getStringExtra("thread_cid");
    int i = Integer.parseInt(paramIntent.getStringExtra("index"));
    this.mCommentModel.addReply(i, str3, str1, str2, User.getInstance().getUID(), NewsModel.getInstance().getLogo(), User.getInstance().getRealName());
    this.commentList.clear();
    this.mAdapter.notifyDataSetChanged();
  }

  public void onClick(View paramView)
  {
    if (paramView.equals(this.btnLeft))
      finishActivity();
    while (true)
    {
      return;
      if (!paramView.equals(this.btnRight))
        break label167;
      if (this.bHasUp)
        continue;
      if (User.getInstance().GetLookAround())
      {
        FragmentActivity localFragmentActivity2 = getActivity();
        String[] arrayOfString3 = new String[2];
        arrayOfString3[0] = getString(2131427338);
        arrayOfString3[1] = getString(2131427339);
        DialogUtil.showSelectListDlg(localFragmentActivity2, 2131427729, arrayOfString3, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramDialogInterface, int paramInt)
          {
            switch (paramInt)
            {
            default:
              return;
            case 0:
              Intent localIntent2 = new Intent(ObjCommentFragment.this.getActivity(), LoginActivity.class);
              ObjCommentFragment.this.startActivity(localIntent2);
              ObjCommentFragment.this.getActivity().finish();
              return;
            case 1:
            }
            Intent localIntent1 = new Intent(ObjCommentFragment.this.getActivity(), LoginActivity.class);
            Bundle localBundle = new Bundle();
            localBundle.putInt("regist", 1);
            localIntent1.putExtras(localBundle);
            ObjCommentFragment.this.startActivity(localIntent1);
            ObjCommentFragment.this.getActivity().finish();
          }
        }
        , 1);
        return;
      }
      if (this.upTask == null)
        break;
      if ((this.upTask.getStatus() != AsyncTask.Status.FINISHED) && (!this.upTask.isCancelled()))
        continue;
      this.upTask = null;
    }
    this.barLikeState.setVisibility(0);
    this.upTask = new PostUpTask(null);
    this.upTask.execute(new Void[0]);
    return;
    label167: if ((!TextUtils.isEmpty(this.mCommentFlag)) && (this.mCommentFlag.compareTo("1") != 0))
    {
      DialogUtil.showKXAlertDialog(getActivity(), 2131427471, null);
      return;
    }
    if (User.getInstance().GetLookAround())
    {
      FragmentActivity localFragmentActivity1 = getActivity();
      String[] arrayOfString2 = new String[2];
      arrayOfString2[0] = getString(2131427338);
      arrayOfString2[1] = getString(2131427339);
      DialogUtil.showSelectListDlg(localFragmentActivity1, 2131427729, arrayOfString2, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          switch (paramInt)
          {
          default:
            return;
          case 0:
            Intent localIntent2 = new Intent(ObjCommentFragment.this.getActivity(), LoginActivity.class);
            ObjCommentFragment.this.startActivity(localIntent2);
            ObjCommentFragment.this.getActivity().finish();
            return;
          case 1:
          }
          Intent localIntent1 = new Intent(ObjCommentFragment.this.getActivity(), LoginActivity.class);
          Bundle localBundle = new Bundle();
          localBundle.putInt("regist", 1);
          localIntent1.putExtras(localBundle);
          ObjCommentFragment.this.startActivity(localIntent1);
          ObjCommentFragment.this.getActivity().finish();
        }
      }
      , 1);
      return;
    }
    Intent localIntent = new Intent(getActivity(), InputFragment.class);
    Bundle localBundle = new Bundle();
    if (paramView.equals(this.txtComment))
    {
      localBundle.putString("type", this.mNtype);
      localBundle.putString("ntypename", this.mTypeName);
      localBundle.putString("id", this.mId);
      localBundle.putString("ouid", this.mFuid);
      localBundle.putInt("mode", 2);
    }
    for (int i = 2; ; i = 8)
    {
      localIntent.putExtras(localBundle);
      startActivityForResult(localIntent, i);
      return;
      String[] arrayOfString1 = (String[])paramView.getTag();
      localBundle.putInt("mode", 8);
      localBundle.putString("thread_cid", arrayOfString1[0]);
      localBundle.putString("fuid", arrayOfString1[1]);
      localBundle.putString("index", arrayOfString1[2]);
      localBundle.putString("mtype", arrayOfString1[3]);
      if ((TextUtils.isEmpty(this.mCthreadCid)) || (!this.mCthreadCid.equals(arrayOfString1[0])))
        continue;
      this.mIsSameThreadCid = true;
    }
  }

  public void onClick(KXLinkInfo paramKXLinkInfo)
  {
    if ("6".equals(paramKXLinkInfo.getType()))
      if ("转发照片专辑".equals(this.mTypeName))
      {
        if ((this.mThumbnails != null) && (this.mThumbnails.length != 0))
          break label56;
        Toast.makeText(getActivity(), 2131427902, 0).show();
      }
    label56: 
    do
    {
      while (true)
      {
        return;
        if ((!User.getInstance().getUID().equals(this.mRepostUid)) && ("1".equals(this.mRepostPrivacy)) && ("0".equals(this.mRepostVisible)))
        {
          Toast.makeText(getActivity(), 2131427903, 0).show();
          return;
        }
        getPhotoShowUtil().showAlbum(this.mRepostAlbumId, this.mRepostAlbumTitle, this.mRepostUid, this.mRepostName, this.mRepostPicNum, this.mRepostPrivacy);
        return;
        if (!"1".equals(paramKXLinkInfo.getType()))
          break;
        if (!"转发照片".equals(this.mTypeName))
          continue;
        showPhoto(this.mRepostIndex, this.mRepostTitle);
        return;
      }
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
      if (!paramKXLinkInfo.isLbsPoi())
        continue;
      IntentUtil.showLbsPositionDetailFragment(this, paramKXLinkInfo.getId(), paramKXLinkInfo.getContent(), "", "");
      return;
    }
    while (!paramKXLinkInfo.isTopic());
    IntentUtil.showTopicGroupActivity(this, paramKXLinkInfo.getId());
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    String str = null;
    if (localBundle != null)
      str = localBundle.getString("action");
    if ((str != null) && (!str.equals("com.kaixin001.VIEW_OBJCOMMNET_DETAIL")) && (CrashRecoverUtil.isCrashBefore()))
    {
      CrashRecoverUtil.crashRecover(getActivity());
      IntentUtil.returnToDesktop(getActivity());
      return;
    }
    User.getInstance().loadDataIfEmpty(getActivity().getApplicationContext());
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903265, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if ((this.getDataTask != null) && (this.getDataTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.getDataTask.cancel(true);
      ObjCommentEngine.getInstance().cancel();
    }
    if ((this.getLbsTitleTask != null) && (this.getLbsTitleTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.getLbsTitleTask.cancel(true);
      ObjCommentEngine.getInstance().cancel();
    }
    if ((this.upTask != null) && (this.upTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.upTask.cancel(true);
      UpEngine.getInstance().cancel();
    }
    this.mCommentModel.clear();
    super.onDestroy();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      finishActivity();
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  public void onResume()
  {
    super.onResume();
    updateListView();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    localTextView.setText(2131427427);
    localTextView.setVisibility(0);
    this.btnLeft = ((ImageView)paramView.findViewById(2131362914));
    this.btnLeft.setOnClickListener(this);
    this.btnRight = ((ImageView)paramView.findViewById(2131362928));
    this.btnRight.setOnClickListener(this);
    this.btnRight.setImageResource(2130838141);
    this.btnRight.setEnabled(false);
    this.proBar = ((ProgressBar)paramView.findViewById(2131363290));
    this.txtWait = ((TextView)paramView.findViewById(2131363291));
    this.lvComment = ((ListView)paramView.findViewById(16908298));
    this.txtComment = ((TextView)paramView.findViewById(2131363293));
    this.txtComment.setOnClickListener(this);
    Bundle localBundle = getArguments();
    this.mode = localBundle.getInt("mode", 5);
    this.mId = localBundle.getString("id");
    this.mNtype = localBundle.getString("type");
    this.mTypeName = localBundle.getString("ntypename");
    this.mFuid = localBundle.getString("fuid");
    this.mCommentFlag = localBundle.getString("commentflag");
    this.mFname = localBundle.getString("fname");
    this.mTitle = localBundle.getString("title");
    this.mIntro = localBundle.getString("intro");
    this.mStime = localBundle.getString("stime");
    this.mCtime = localBundle.getString("ctime");
    this.mThumbnail = localBundle.getString("thumbnail");
    this.mLarge = localBundle.getString("large");
    this.mSubThumbnail = localBundle.getString("subthumbnail");
    this.mSubLarge = localBundle.getString("sublarge");
    this.subLocation = localBundle.getString("subLocation");
    this.Tnum = localBundle.getInt("Tnum", -1);
    this.Cnum = localBundle.getInt("Cnum", -1);
    if (this.mIntro == null)
      this.mIntro = "";
    if (this.subLocation == null)
      this.subLocation = "";
    this.mIntro += this.subLocation;
    this.mCthreadCid = localBundle.getString("cthread_cid");
    this.mLocation = localBundle.getString("location");
    this.mClientName = localBundle.getString("client_name");
    this.mNeedRefresh = localBundle.getInt("needRefresh", -1);
    int i;
    if ((!TextUtils.isEmpty(this.mTitle)) && (!TextUtils.isEmpty(this.mFname)) && (!TextUtils.isEmpty(this.mFuid)) && (!this.mTitle.equals("null")))
    {
      this.mTitle = consistNameStartText(this.mTitle, this.mFname, this.mFuid);
      if (!TextUtils.isEmpty(this.mCtime))
        this.mStime = KXTextUtil.formatTimestamp(1000L * Long.parseLong(this.mCtime));
      if (this.mode == 5)
        this.btnLeft.setImageResource(2130839022);
      if ("1242".equals(this.mNtype))
      {
        this.mRepostUid = localBundle.getString("uid");
        this.mRepostName = localBundle.getString("name");
        this.mRepostAlbumId = localBundle.getString("albumId");
        this.mRepostAlbumTitle = localBundle.getString("albumTitle");
        this.mRepostPicNum = localBundle.getString("picnum");
        this.mRepostIndex = localBundle.getString("photo_index");
        this.mRepostTitle = localBundle.getString("photo_title");
        this.mRepostPrivacy = localBundle.getString("photo_privacy");
        this.mRepostVisible = localBundle.getString("photo_visible");
        i = localBundle.getInt("photosize", 0);
        this.mThumbnails = new String[i];
        this.mLarges = new String[i];
        this.mPostions = new String[i];
        this.mTitles = new String[i];
      }
    }
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        initEmptyView();
        this.mHeaderView = getTitleView();
        this.lvComment.addHeaderView(this.mHeaderView);
        this.lvComment.setAdapter(this.mAdapter);
        updateListView();
        this.lvComment.setVisibility(8);
        refreshData();
        return;
        this.mTitle = "";
        break;
      }
      this.mThumbnails[j] = localBundle.getString("thumbnail" + j);
      this.mLarges[j] = localBundle.getString("large" + j);
      this.mPostions[j] = localBundle.getString("photo_pos" + j);
      this.mTitles[j] = localBundle.getString("photo_title" + j);
    }
  }

  public String processTextForAt(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap = new HashMap();
    if (paramString.length() == 0)
      return null;
    int i = 0;
    int j = paramString.indexOf("@", i);
    label44: Iterator localIterator;
    if (j < 0)
      localIterator = localArrayList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        return paramString;
        int k = paramString.indexOf("#)", i);
        if ((k < 0) || (j >= k))
          break label44;
        String str1 = paramString.substring(j, k + 2);
        String str2 = str1.substring(1 + str1.indexOf("@"), str1.indexOf("(#")).trim();
        String str3 = str1.substring(1 + str1.indexOf("#"), str1.lastIndexOf("#")).trim();
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append(this.AT).append("[|s|]").append(str3).append("[|m|]").append(str2).append("[|m|]").append("0").append("[|e|]");
        localArrayList.add(str1);
        localHashMap.put(str1, localStringBuffer.toString());
        i = k + 1;
        break;
      }
      String str4 = (String)localIterator.next();
      paramString = paramString.replace(str4, (String)localHashMap.get(str4));
    }
  }

  public void refreshData()
  {
    if (!super.checkNetworkAndHint(true))
      return;
    this.proBar.setVisibility(0);
    this.txtWait.setVisibility(0);
    if ((this.mNeedRefresh != -1) && (TextUtils.isEmpty(this.mTitle)))
    {
      this.getLbsTitleTask = new GetLBSTitleTask(null);
      GetLBSTitleTask localGetLBSTitleTask = this.getLbsTitleTask;
      String[] arrayOfString = new String[1];
      arrayOfString[0] = this.mId;
      localGetLBSTitleTask.execute(arrayOfString);
      return;
    }
    this.getDataTask = new GetDataTask(null);
    this.getDataTask.execute(new Void[0]);
  }

  private class CommentAdapter extends BaseAdapter
  {
    public CommentAdapter(int paramArrayList, ArrayList<ObjCommentItem> arg3)
    {
    }

    public int getCount()
    {
      return ObjCommentFragment.this.commentList.size();
    }

    public Object getItem(int paramInt)
    {
      if ((paramInt < 0) || (paramInt > ObjCommentFragment.this.commentList.size()))
        return null;
      return ObjCommentFragment.this.commentList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      ObjCommentItem localObjCommentItem = (ObjCommentItem)getItem(paramInt);
      if (localObjCommentItem == null)
        return paramView;
      if ((TextUtils.isEmpty(localObjCommentItem.getFuid())) && (TextUtils.isEmpty(localObjCommentItem.getThread_cid())) && (TextUtils.isEmpty(localObjCommentItem.getStime())))
        return ObjCommentFragment.this.mViewEmpty;
      LayoutInflater localLayoutInflater = (LayoutInflater)ObjCommentFragment.this.getActivity().getSystemService("layout_inflater");
      ObjCommentFragment.ObjCommentItemTag localObjCommentItemTag;
      if ((paramView == null) || (paramView.getId() != 2131363295))
      {
        paramView = localLayoutInflater.inflate(2130903266, null);
        localObjCommentItemTag = new ObjCommentFragment.ObjCommentItemTag(ObjCommentFragment.this, paramView, null);
        paramView.setTag(localObjCommentItemTag);
      }
      try
      {
        int i;
        while (true)
        {
          KXLog.d("CommentList>>", "Item Count=" + getCount() + "\n");
          i = localObjCommentItem.getMainThread();
          if (i != -1)
            break;
          localObjCommentItemTag.setButton(paramView, localObjCommentItem, String.valueOf(paramInt));
          return paramView;
          localObjCommentItemTag = (ObjCommentFragment.ObjCommentItemTag)paramView.getTag();
        }
        if (i == 1)
          localObjCommentItemTag.setMainItem(paramView, localObjCommentItem);
        else
          localObjCommentItemTag.setSubItem(paramView, localObjCommentItem);
      }
      catch (Exception localException)
      {
        KXLog.e("ObjCommentActivity", "CommentAdapter getView", localException);
      }
      return paramView;
    }
  }

  private class FNameOnClickListener
    implements View.OnClickListener
  {
    private String fname;
    private String fuid;

    public FNameOnClickListener(String paramString1, String arg3)
    {
      this.fuid = paramString1;
      Object localObject;
      this.fname = localObject;
    }

    public void onClick(View paramView)
    {
      if ((this.fuid != null) && (this.fname != null))
        IntentUtil.showHomeFragment(ObjCommentFragment.this, this.fuid, this.fname);
    }
  }

  private class GetDataTask extends KXFragment.KXAsyncTask<Void, Void, Integer>
  {
    private GetDataTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Void[] paramArrayOfVoid)
    {
      try
      {
        if (ObjCommentEngine.getInstance().getObjCommentList(ObjCommentFragment.this.getActivity().getApplicationContext(), ObjCommentFragment.this.mId, ObjCommentFragment.this.mNtype, ObjCommentFragment.this.mFuid, ObjCommentFragment.this.mCommentModel))
          return Integer.valueOf(1);
        Integer localInteger = Integer.valueOf(0);
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if (paramInteger == null)
      {
        ObjCommentFragment.this.finish();
        return;
      }
      while (true)
      {
        try
        {
          ObjCommentFragment.this.proBar.setVisibility(8);
          ObjCommentFragment.this.txtWait.setVisibility(8);
          if (paramInteger.intValue() == 0)
          {
            String str = ObjCommentEngine.getInstance().getLastError();
            if (str != null)
              continue;
            str = ObjCommentFragment.this.getString(2131427478);
            Toast.makeText(ObjCommentFragment.this.getActivity(), str, 0).show();
            ObjCommentFragment.this.mCommentModel.addMainComment(0, "", "", "", "", "", "", "");
            if (ObjCommentFragment.this.lvComment == null)
              continue;
            ObjCommentFragment.this.lvComment.setDividerHeight(0);
            ObjCommentFragment.this.updateListView();
            return;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("ObjCommentActivity", "onPostExecute", localException);
          return;
        }
        ObjCommentFragment.this.lvComment.setVisibility(0);
        ObjCommentFragment.this.setLikeState();
        ArrayList localArrayList = ObjCommentFragment.this.mCommentModel.getCommentList();
        if ((localArrayList == null) || (localArrayList.size() == 0))
        {
          ObjCommentFragment.this.mCommentModel.addMainComment(0, "", "", "", "", "", "", "");
          ObjCommentFragment.this.lvComment.setDividerHeight(0);
          continue;
        }
        ObjCommentFragment.this.lvComment.setDividerHeight(1);
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class GetLBSTitleTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private GetLBSTitleTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      try
      {
        if (ObjCommentEngine.getInstance().getLBSCommentTitle(ObjCommentFragment.this.getActivity().getApplicationContext(), paramArrayOfString[0], ObjCommentFragment.this.mCommentModel))
        {
          ObjCommentModel.LbsCommentTitle localLbsCommentTitle = ObjCommentFragment.this.mCommentModel.getmLbsCommentTitle();
          ObjCommentFragment.this.mFname = localLbsCommentTitle.mFname;
          ObjCommentFragment.this.mTitle = localLbsCommentTitle.mContent;
          ObjCommentFragment.this.mFuid = localLbsCommentTitle.mFuid;
          label205: String str3;
          if ((!TextUtils.isEmpty(ObjCommentFragment.this.mFname)) && (!TextUtils.isEmpty(ObjCommentFragment.this.mFuid)))
          {
            if (TextUtils.isEmpty(ObjCommentFragment.this.mTitle))
              ObjCommentFragment.this.mTitle = "";
            ObjCommentFragment.this.mTitle = ObjCommentFragment.consistNameStartText(ObjCommentFragment.this.mTitle, ObjCommentFragment.this.mFname, ObjCommentFragment.this.mFuid);
            ObjCommentFragment.this.mStime = localLbsCommentTitle.mCtime;
            if (TextUtils.isEmpty(ObjCommentFragment.this.mStime))
              break label306;
            ObjCommentFragment.this.mStime = KXTextUtil.formatTimestamp(1000L * Long.parseLong(ObjCommentFragment.this.mStime));
            ObjCommentFragment.this.mThumbnail = localLbsCommentTitle.mThumbnail;
            ObjCommentFragment.this.mLarge = localLbsCommentTitle.mLargePhoto;
            ObjCommentFragment.this.mClientName = localLbsCommentTitle.mSource;
            String str1 = localLbsCommentTitle.mPoiId;
            String str2 = localLbsCommentTitle.mPoiName;
            if ((TextUtils.isEmpty(str1)) || (TextUtils.isEmpty(str2)))
              break label318;
            str3 = KXTextUtil.getLbsPoiText(str2, str1);
          }
          label306: label318: for (ObjCommentFragment.this.mLocation = str3; ; ObjCommentFragment.this.mLocation = "")
          {
            return Integer.valueOf(1);
            ObjCommentFragment.this.mTitle = "";
            break;
            ObjCommentFragment.this.mStime = "";
            break label205;
          }
        }
        Integer localInteger = Integer.valueOf(0);
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if (paramInteger == null)
      {
        ObjCommentFragment.this.finish();
        return;
      }
      if (paramInteger.intValue() == 1)
        ObjCommentFragment.this.updateTitileView(ObjCommentFragment.this.mHeaderView);
      while (true)
      {
        ObjCommentFragment.this.getDataTask = new ObjCommentFragment.GetDataTask(ObjCommentFragment.this, null);
        ObjCommentFragment.this.getDataTask.execute(new Void[0]);
        return;
        Toast.makeText(ObjCommentFragment.this.getActivity(), 2131427913, 0).show();
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class ObjCommentItemTag
  {
    private Button btnReply;
    private TextView divider;
    private KXIntroView exttext;
    private ImageView ivLogo;
    private TextView tvContent;
    private TextView tvText;
    private TextView tvTime;

    private ObjCommentItemTag(View arg2)
    {
      Object localObject;
      this.divider = ((TextView)localObject.findViewById(2131363297));
      this.tvText = ((TextView)localObject.findViewById(2131363298));
      this.tvContent = ((TextView)localObject.findViewById(2131363301));
      this.tvTime = ((TextView)localObject.findViewById(2131363302));
      this.exttext = ((KXIntroView)localObject.findViewById(2131363303));
      this.btnReply = ((Button)localObject.findViewById(2131363304));
      this.ivLogo = ((ImageView)localObject.findViewById(2131363299));
    }

    private void setSubTag(ObjCommentItem paramObjCommentItem)
    {
      String str1 = paramObjCommentItem.getFname();
      String str2 = paramObjCommentItem.getFuid();
      if (User.getInstance().getUID().compareTo(str2) == 0);
      for (String str3 = ObjCommentFragment.this.getResources().getString(2131427565); ; str3 = paramObjCommentItem.getFname())
      {
        if (str3.length() > 10)
          str3 = str3.substring(0, 5) + "...";
        this.tvContent.setText(Html.fromHtml("<font color=\"#576b95\">" + str3 + "</font>"));
        this.tvContent.setOnClickListener(new ObjCommentFragment.FNameOnClickListener(ObjCommentFragment.this, str2, str1));
        this.tvTime.setText(paramObjCommentItem.getStime());
        String str4 = paramObjCommentItem.getContent();
        String str5 = ObjCommentFragment.this.processTextForAt(str4);
        this.exttext.setOnClickLinkListener(ObjCommentFragment.this);
        this.exttext.setTitleList(RecordInfo.parseObjCommentUtil(str5));
        ObjCommentFragment.this.displayPicture(this.ivLogo, paramObjCommentItem.getFlogo(), 2130838676);
        this.ivLogo.setOnClickListener(new ObjCommentFragment.FNameOnClickListener(ObjCommentFragment.this, str2, str1));
        return;
      }
    }

    public void setButton(View paramView, ObjCommentItem paramObjCommentItem, String paramString)
    {
      this.divider.setVisibility(8);
      this.tvText.setVisibility(8);
      this.tvContent.setVisibility(8);
      this.tvTime.setVisibility(8);
      this.exttext.setVisibility(8);
      this.ivLogo.setVisibility(8);
      this.btnReply.setVisibility(0);
      paramView.setPadding(0, 4, 4, 4);
      Button localButton = this.btnReply;
      String[] arrayOfString = new String[4];
      arrayOfString[0] = paramObjCommentItem.getThread_cid();
      arrayOfString[1] = paramObjCommentItem.getFuid();
      arrayOfString[2] = paramString;
      arrayOfString[3] = paramObjCommentItem.getType();
      localButton.setTag(arrayOfString);
      this.btnReply.setOnClickListener(ObjCommentFragment.this);
    }

    public void setMainItem(View paramView, ObjCommentItem paramObjCommentItem)
    {
      String str = paramObjCommentItem.getType();
      if (!TextUtils.isEmpty(str))
      {
        if (str.compareTo("1") != 0)
          break label92;
        this.tvText.setVisibility(0);
      }
      while (true)
      {
        this.exttext.setVisibility(0);
        this.divider.setVisibility(0);
        this.tvContent.setVisibility(0);
        this.tvTime.setVisibility(0);
        this.ivLogo.setVisibility(0);
        this.btnReply.setVisibility(8);
        setSubTag(paramObjCommentItem);
        paramView.setPadding(4, 4, 4, 4);
        return;
        label92: this.tvText.setVisibility(8);
      }
    }

    public void setSubItem(View paramView, ObjCommentItem paramObjCommentItem)
    {
      this.tvText.setVisibility(8);
      this.divider.setVisibility(8);
      this.tvContent.setVisibility(0);
      this.tvTime.setVisibility(0);
      this.exttext.setVisibility(0);
      this.ivLogo.setVisibility(0);
      this.btnReply.setVisibility(8);
      setSubTag(paramObjCommentItem);
      paramView.setPadding(54, 4, 4, 4);
    }
  }

  private class PostUpTask extends KXFragment.KXAsyncTask<Void, Void, Integer>
  {
    private PostUpTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Void[] paramArrayOfVoid)
    {
      try
      {
        Integer localInteger = Integer.valueOf(UpEngine.getInstance().postUp(ObjCommentFragment.this.getActivity(), ObjCommentFragment.this.mNtype, ObjCommentFragment.this.mId, ObjCommentFragment.this.mFuid));
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onCancelledA()
    {
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if (paramInteger == null)
      {
        ObjCommentFragment.this.finish();
        return;
      }
      try
      {
        if (paramInteger.intValue() == 1)
        {
          ObjCommentFragment.this.bHasUp = true;
          ObjCommentFragment.this.mCommentModel.addMyUp();
          ObjCommentFragment.this.setLikeState();
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("ObjCommentActivity", "onPostExecute", localException);
        return;
      }
      ObjCommentFragment.this.setLikeState();
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
 * Qualified Name:     com.kaixin001.fragment.ObjCommentFragment
 * JD-Core Version:    0.6.0
 */