package com.kaixin001.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebView.PictureListener;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.KXEnvironment;
import com.kaixin001.activity.LoginActivity;
import com.kaixin001.activity.wxapi.WXManager;
import com.kaixin001.engine.RepostEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.item.RepItem;
import com.kaixin001.model.KaixinConst;
import com.kaixin001.model.NewsModel;
import com.kaixin001.model.RepostModel;
import com.kaixin001.model.User;
import com.kaixin001.util.ActivityUtil;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.CheckSupportFlashUtil;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.HttpUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.SocialShareManager;
import com.kaixin001.util.StringUtil;
import com.kaixin001.util.UserHabitUtils;
import com.kaixin001.view.KXIntroView;
import com.kaixin001.view.KXIntroView.OnClickLinkListener;
import com.kaixin001.view.KXPopupListWindow;
import com.kaixin001.view.KXPopupListWindow.DoSelect;
import com.kaixin001.view.KXScrollView;
import com.kaixin001.view.KXScrollView.SizeChangeListener;
import com.kaixin001.view.KXTagWidget;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RepostDetailFragment extends BaseFragment
  implements KXIntroView.OnClickLinkListener, View.OnClickListener, PopupWindow.OnDismissListener, KXPopupListWindow.DoSelect, ViewPager.OnPageChangeListener
{
  private static final int MODE_TAG = 21;
  private static final int MODE_VOTE = 20;
  private static final String TAG = "RepostDetailActivity";
  private static final int VOTE_BAR_IMAGE_NUM = 9;
  private int TAG_NUM = 15;
  private LinearLayout answerListParent;
  private View bottomBar;
  private TextView desTextView;
  private LinearLayout detailLayout;
  private float downX;
  private float downY;
  private View guideLayout;
  private boolean isShowGuide = false;
  private String isShowMoreRep = "0";
  private boolean is_all_repost = false;
  private TextView labelTextView;
  private JSONArray mAnswerList = null;
  private String mCommentFlag = "";
  private WebView mContentView = null;
  private String mCthreadCid = "";
  private EditText mEditText = null;
  private String mFriendName = "";
  private String mFuid = "";
  GetRepostTask mGetRepostTask = null;
  private GetShareWxInfoTask mGetShareWxInfoTask;
  private LinearLayout mLinearLayout = null;
  private int mMode = 21;
  private RepostModel mModel = RepostModel.getInstance();
  private KXPopupListWindow mPopupWindow;
  private int mPosition;
  private ProgressDialog mProgressDialog;
  private GetRelevantLinkTask mRelevantLinkTask;
  private List<RepItem> mRepList = new ArrayList();
  private String mReplyContent = "";
  private View mRepostBtn = null;
  private ViewPager mRepostPager;
  RepostTask mRepostTask = null;
  RepostVoteTask mRepostVoteTask = null;
  private JSONArray mResultList = null;
  private int mRpNum = 0;
  private String mRpid = "";
  private int mScreenSizeType = 1;
  private KXScrollView mScrollView;
  String mShareDes = "";
  Bitmap mShareIcon = null;
  String mShareTitle = "";
  String mShareUrl = "";
  private ImageView mSubmitTagBtn = null;
  SubmitTagTask mSubmitTagTask = null;
  private int[] mTagBgColor = new int[14];
  private JSONArray mTagList = null;
  private TagOnClickListener mTagOnClickListener = null;
  private TagOnTouchListener mTagOnTouchListener = null;
  private Tencent mTencent;
  private int mTimerCycles = 0;
  private int[] mVoteBarImage = new int[9];
  private HashMap<String, View> mVoteControlMap = new HashMap();
  private ProgressDialog m_ProgressDialog = null;
  private View progressItem;
  private JSONArray relevantData;
  private LinearLayout relevantPostParent;
  private TextView relevantPostText;
  private RepostPagerAdapter repostDetailAdapter = new RepostPagerAdapter();
  private LinearLayout resultParent;
  private int shareIndex = 0;
  private boolean showKeyboard = false;
  private LinearLayout tagParent;
  private int touchPosition;

  private String buildContentForDiffrentDevice(String paramString)
  {
    if (paramString.contains("table"))
    {
      this.mContentView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
      if (paramString != null)
        break label47;
      paramString = null;
    }
    label47: Matcher localMatcher1;
    StringBuffer localStringBuffer;
    int i;
    int j;
    int k;
    do
    {
      do
      {
        return paramString;
        this.mContentView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        break;
      }
      while (!paramString.contains("embed"));
      localMatcher1 = Pattern.compile("<\\s*embed\\s*([^>]*)\\s*(/>|>\\s*<\\s*/embed\\s*>|>)").matcher(paramString);
      localStringBuffer = new StringBuffer(paramString.length());
      i = 0;
      if (!localMatcher1.find())
      {
        int i3 = paramString.length();
        localStringBuffer.append(paramString.substring(i, i3));
        return localStringBuffer.toString();
      }
      if (CheckSupportFlashUtil.getInstance(getActivity().getApplicationContext()).isSupportFlash() != 1)
      {
        if (CheckSupportFlashUtil.getInstance(getActivity().getApplicationContext()).getFlashStatus() == 0)
          Toast.makeText(getActivity(), 2131427364, 0).show();
        return localMatcher1.replaceAll("<span ><font color=\"#D42B1E\">【无法显示视频】</font><br/></span >");
      }
      DisplayMetrics localDisplayMetrics = getResources().getDisplayMetrics();
      j = localDisplayMetrics.widthPixels;
      k = localDisplayMetrics.heightPixels;
    }
    while ((j >= KaixinConst.LESS_SCREEN_WIDTH) && (k >= KaixinConst.LESS_SCREEN_HEIGHT));
    if (j < k);
    for (int m = j; ; m = k)
    {
      int n = m * KaixinConst.VIEDO_WIDTH / KaixinConst.DEVICE_SCREEN;
      int i1 = m * KaixinConst.VIEDO_HEIGHT / KaixinConst.DEVICE_SCREEN;
      String str1 = localMatcher1.group();
      int i2 = localMatcher1.start();
      localStringBuffer.append(paramString.substring(i, i2));
      i = localMatcher1.end();
      String str2 = "width=\"" + String.valueOf(n) + "\" ";
      String str3 = "height=\"" + String.valueOf(i1) + "\" ";
      Matcher localMatcher2 = Pattern.compile("width=(\\s*|\")([(^\"|\\s)]*)(\\s*|\")").matcher(str1);
      String str4 = str1;
      if (localMatcher2.find())
        str4 = localMatcher2.replaceAll(str2);
      Matcher localMatcher3 = Pattern.compile("height=(\\s*|\")([(^\"|\\s)]*)(\\s*|\")").matcher(str4);
      if (localMatcher3.find())
        str4 = localMatcher3.replaceAll(str3);
      localStringBuffer.append(str4);
      break;
    }
  }

  private int clearCacheFolder(File paramFile, long paramLong)
  {
    int i = 0;
    if (paramFile != null)
    {
      boolean bool1 = paramFile.isDirectory();
      i = 0;
      if (bool1)
      {
        int j;
        int k;
        do
          try
          {
            File[] arrayOfFile = paramFile.listFiles();
            j = arrayOfFile.length;
            i = 0;
            k = 0;
            continue;
            File localFile = arrayOfFile[k];
            if (localFile.isDirectory())
              i += clearCacheFolder(localFile, paramLong);
            if (localFile.lastModified() < paramLong)
            {
              boolean bool2 = localFile.delete();
              if (bool2)
                i++;
            }
            k++;
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
            return i;
          }
        while (k < j);
      }
    }
    return i;
  }

  private void constructAnswerListView()
  {
    if (this.mAnswerList == null)
      this.answerListParent.setVisibility(8);
    while (true)
    {
      return;
      if (this.mAnswerList.length() == 0)
      {
        this.answerListParent.setVisibility(8);
        return;
      }
      this.answerListParent.removeAllViews();
      for (int i = 0; i < this.mAnswerList.length(); i++)
      {
        View localView = createOneAnswerView(i);
        if (localView == null)
          continue;
        this.answerListParent.addView(localView);
        this.mVoteControlMap.put(String.valueOf(i), localView);
        if (i == -1 + this.mAnswerList.length())
          continue;
        this.answerListParent.addView(createOnedivider());
      }
    }
  }

  private void constructLinkViews()
  {
    int i = 5;
    this.relevantPostParent.removeAllViews();
    int j;
    if (this.relevantData.length() > i)
      j = 0;
    while (true)
    {
      if (j >= i)
      {
        return;
        i = this.relevantData.length();
        break;
      }
      try
      {
        View localView = LayoutInflater.from(getActivity()).inflate(2130903325, null);
        TextView localTextView = (TextView)localView.findViewById(2131363598);
        JSONObject localJSONObject = this.relevantData.getJSONObject(j);
        localView.setOnClickListener(new View.OnClickListener(localJSONObject)
        {
          public void onClick(View paramView)
          {
            Intent localIntent = new Intent(RepostDetailFragment.this.getActivity(), RepostDetailFragment.class);
            ArrayList localArrayList = new ArrayList();
            RepItem localRepItem = new RepItem();
            localRepItem.fname = this.val$obj.optString("friend_name");
            localRepItem.fuid = this.val$obj.optString("uid");
            localRepItem.id = this.val$obj.optString("urpid");
            localRepItem.title = this.val$obj.optString("title");
            localArrayList.add(localRepItem);
            Bundle localBundle = new Bundle();
            localBundle.putSerializable("repostList", localArrayList);
            localBundle.putInt("position", 0);
            localBundle.putString("commentflag", "");
            localBundle.putString("isShowMoreRep", "1");
            localIntent.putExtras(localBundle);
            RepostDetailFragment.this.startFragment(localIntent, true, 1);
          }
        });
        localTextView.setText(localJSONObject.optString("title"));
        this.relevantPostParent.addView(localView);
        j++;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
  }

  private void constructResultListView()
  {
    if (this.mResultList == null)
    {
      this.resultParent.setVisibility(8);
      return;
    }
    if (this.mResultList.length() == 0)
    {
      this.resultParent.setVisibility(8);
      return;
    }
    this.resultParent.removeAllViews();
    for (int i = 0; ; i++)
    {
      if (i >= this.mResultList.length())
      {
        this.resultParent.setVisibility(0);
        return;
      }
      View localView = createOneResultView(i);
      if (localView == null)
        continue;
      this.resultParent.addView(localView);
      if (i == -1 + this.mResultList.length())
        continue;
      this.resultParent.addView(createOnedivider());
    }
  }

  private void constructTagListView()
  {
    if (this.mTagList == null)
    {
      this.tagParent.setVisibility(8);
      return;
    }
    if (this.mTagList.length() == 0)
    {
      this.tagParent.setVisibility(8);
      return;
    }
    this.tagParent.removeAllViews();
    float f1 = getActivity().getWindowManager().getDefaultDisplay().getWidth() - 25.0F;
    int i = this.mTagList.length();
    float f2 = f1;
    LinearLayout localLinearLayout1 = new LinearLayout(getActivity());
    TableLayout.LayoutParams localLayoutParams = new TableLayout.LayoutParams();
    localLayoutParams.width = -1;
    localLayoutParams.height = -2;
    localLayoutParams.bottomMargin = 1;
    localLinearLayout1.setLayoutParams(localLayoutParams);
    Object localObject = "";
    int j = 0;
    while (true)
    {
      if (j >= i)
      {
        if (localLinearLayout1 == null)
          break;
        this.tagParent.addView(localLinearLayout1);
        return;
      }
      LinearLayout localLinearLayout2 = (LinearLayout)getActivity().getLayoutInflater().inflate(2130903192, null);
      KXTagWidget localKXTagWidget = (KXTagWidget)localLinearLayout2.findViewById(2131362944);
      try
      {
        String str = this.mTagList.getJSONObject(j).getString("name");
        localObject = str;
        localKXTagWidget.setName((String)localObject);
        localKXTagWidget.setTag(Integer.valueOf(j));
        localKXTagWidget.setBgColor(selectTagBgColor(j), -1);
        localKXTagWidget.setOnClickListener(this.mTagOnClickListener);
        localKXTagWidget.setOnTouchListener(this.mTagOnTouchListener);
        f3 = localKXTagWidget.getMesuredWidth();
        if (f2 > f3)
        {
          localLinearLayout2.removeView(localKXTagWidget);
          localLinearLayout1.addView(localKXTagWidget);
          f2 -= f3;
          j++;
        }
      }
      catch (JSONException localJSONException)
      {
        while (true)
        {
          float f3;
          localJSONException.printStackTrace();
          continue;
          this.tagParent.addView(localLinearLayout1);
          localLinearLayout1 = new LinearLayout(getActivity());
          localLinearLayout1.setLayoutParams(localLayoutParams);
          localLinearLayout2.removeView(localKXTagWidget);
          localLinearLayout1.addView(localKXTagWidget);
          f2 = f1 - f3;
        }
      }
    }
  }

  private void constructViews()
  {
    this.detailLayout.setVisibility(0);
    findViewById(2131363571).setVisibility(0);
    String str = StringUtil.replaceTokenWith(getResources().getString(2131427612), "*", String.valueOf(this.mRpNum));
    this.desTextView.setText(str);
    if (this.mResultList == null)
    {
      this.labelTextView.setVisibility(8);
      if (this.mMode != 20)
        break label162;
      this.answerListParent.setVisibility(0);
      this.tagParent.setVisibility(8);
      constructAnswerListView();
    }
    while (true)
    {
      constructResultListView();
      if (this.showKeyboard)
      {
        this.showKeyboard = false;
        this.mEditText.requestFocus();
        ActivityUtil.showInputMethod(getActivity());
      }
      return;
      if (this.mResultList.length() == 0)
      {
        this.labelTextView.setVisibility(8);
        break;
      }
      this.labelTextView.setVisibility(0);
      break;
      label162: this.answerListParent.setVisibility(8);
      this.tagParent.setVisibility(0);
      constructTagListView();
    }
  }

  private View createOneAnswerView(int paramInt)
  {
    View localView = null;
    try
    {
      localView = getActivity().getLayoutInflater().inflate(2130903413, null);
      JSONObject localJSONObject = this.mAnswerList.getJSONObject(paramInt);
      String str1 = localJSONObject.getString("answer");
      int i = localJSONObject.getInt("votenum");
      int j = localJSONObject.getInt("votepercent");
      ((TextView)localView.findViewById(2131364011)).setText(str1);
      localView.findViewById(2131364013).setBackgroundResource(selectVoteBarImage(paramInt));
      String str2 = String.valueOf(i) + "(" + String.valueOf(j) + "%" + ")";
      ((TextView)localView.findViewById(2131364014)).setText(str2);
      localView.setOnClickListener(new VoteControlOnClickListener(paramInt));
      return localView;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return localView;
  }

  private View createOneResultView(int paramInt)
  {
    View localView = null;
    try
    {
      localView = getActivity().getLayoutInflater().inflate(2130903327, null);
      JSONObject localJSONObject = this.mResultList.getJSONObject(paramInt);
      String str1 = localJSONObject.getString("answer");
      String str2 = localJSONObject.getString("fname");
      String str3 = localJSONObject.getString("uid");
      KXIntroView localKXIntroView = (KXIntroView)localView.findViewById(2131363600);
      String str4 = getResources().getString(2131427616) + " " + str1;
      ArrayList localArrayList = new ArrayList();
      KXLinkInfo localKXLinkInfo1 = new KXLinkInfo();
      localKXLinkInfo1.setContent(str2);
      localKXLinkInfo1.setType("0");
      localKXLinkInfo1.setId(str3);
      localArrayList.add(localKXLinkInfo1);
      KXLinkInfo localKXLinkInfo2 = new KXLinkInfo();
      localKXLinkInfo2.setContent(str4);
      localArrayList.add(localKXLinkInfo2);
      localKXIntroView.setTitleList(localArrayList);
      localKXIntroView.setOnClickLinkListener(this);
      return localView;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return localView;
  }

  private View createOnedivider()
  {
    TextView localTextView = new TextView(getActivity());
    localTextView.setBackgroundColor(getResources().getColor(2130839396));
    TableLayout.LayoutParams localLayoutParams = new TableLayout.LayoutParams();
    localLayoutParams.height = 1;
    localLayoutParams.width = -1;
    localTextView.setLayoutParams(localLayoutParams);
    return localTextView;
  }

  private void doRepost()
  {
    if (!super.checkNetworkAndHint(true))
      return;
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
            Intent localIntent2 = new Intent(RepostDetailFragment.this.getActivity(), LoginActivity.class);
            RepostDetailFragment.this.startActivity(localIntent2);
            RepostDetailFragment.this.getActivity().finish();
            return;
          case 1:
          }
          Intent localIntent1 = new Intent(RepostDetailFragment.this.getActivity(), LoginActivity.class);
          Bundle localBundle = new Bundle();
          localBundle.putInt("regist", 1);
          localIntent1.putExtras(localBundle);
          RepostDetailFragment.this.startActivity(localIntent1);
          RepostDetailFragment.this.getActivity().finish();
        }
      }
      , 1);
      return;
    }
    this.mRepostTask = new RepostTask(null);
    this.mRepostTask.execute(new Void[0]);
    this.m_ProgressDialog = ProgressDialog.show(getActivity(), "", getResources().getText(2131427618), true, true, new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramDialogInterface)
      {
        if (RepostDetailFragment.this.mRepostTask != null)
        {
          RepostEngine.getInstance().cancel();
          RepostDetailFragment.this.mRepostTask.cancel(true);
          RepostDetailFragment.this.mRepostTask = null;
        }
      }
    });
  }

  private void doRepostVote(int paramInt)
  {
    if (!super.checkNetworkAndHint(true))
      return;
    this.mRepostVoteTask = new RepostVoteTask(null);
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(paramInt + 1);
    this.mRepostVoteTask.execute(arrayOfString);
    this.m_ProgressDialog = ProgressDialog.show(getActivity(), "", getResources().getText(2131427618), true, true, new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramDialogInterface)
      {
        if (RepostDetailFragment.this.mRepostVoteTask != null)
        {
          RepostEngine.getInstance().cancel();
          RepostDetailFragment.this.mRepostVoteTask.cancel(true);
          RepostDetailFragment.this.mRepostVoteTask = null;
        }
      }
    });
  }

  private void doSubmitTag(int paramInt, String paramString)
  {
    if (!super.checkNetworkAndHint(true))
      return;
    while (true)
    {
      String[] arrayOfString;
      try
      {
        if (this.mSubmitTagTask == null)
          continue;
        if ((this.mSubmitTagTask.getStatus() != AsyncTask.Status.FINISHED) && (!this.mSubmitTagTask.isCancelled()))
          break;
        this.mSubmitTagTask = null;
        this.mSubmitTagTask = new SubmitTagTask(null);
        arrayOfString = new String[2];
        if (!TextUtils.isEmpty(paramString))
        {
          arrayOfString[0] = paramString;
          arrayOfString[1] = "";
          this.mSubmitTagTask.execute(arrayOfString);
          this.m_ProgressDialog = ProgressDialog.show(getActivity(), "", getResources().getText(2131427618), true, true, new DialogInterface.OnCancelListener()
          {
            public void onCancel(DialogInterface paramDialogInterface)
            {
              if (RepostDetailFragment.this.mSubmitTagTask != null)
              {
                RepostEngine.getInstance().cancel();
                RepostDetailFragment.this.mSubmitTagTask.cancel(true);
                RepostDetailFragment.this.mSubmitTagTask = null;
              }
            }
          });
          return;
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        return;
      }
      arrayOfString[0] = this.mTagList.getJSONObject(paramInt).getString("name");
      arrayOfString[1] = this.mTagList.getJSONObject(paramInt).getString("id");
    }
  }

  private void findViewByIds()
  {
    this.progressItem = findViewById(2131363560);
    this.mContentView = ((WebView)findViewById(2131363562));
    this.detailLayout = ((LinearLayout)findViewById(2131363563));
    this.desTextView = ((TextView)findViewById(2131363564));
    this.labelTextView = ((TextView)findViewById(2131363567));
    this.answerListParent = ((LinearLayout)findViewById(2131363565));
    this.tagParent = ((LinearLayout)findViewById(2131363566));
    this.resultParent = ((LinearLayout)findViewById(2131363568));
    this.relevantPostText = ((TextView)findViewById(2131363569));
    this.relevantPostParent = ((LinearLayout)findViewById(2131363570));
  }

  private void getShareInfo()
  {
    if (this.mProgressDialog != null)
    {
      this.mProgressDialog.dismiss();
      this.mProgressDialog = null;
    }
    String str = getResources().getString(2131428506);
    this.mProgressDialog = ProgressDialog.show(getActivity(), null, str);
    this.mGetShareWxInfoTask = new GetShareWxInfoTask(null);
    this.mGetShareWxInfoTask.execute(new Void[0]);
  }

  private Bundle getShareQZoneParams()
  {
    return new SocialShareManager(getActivity()).getparams(RepostModel.getInstance().mTitle, RepostModel.getInstance().mWapUrl, "", RepostModel.getInstance().mDes, RepostModel.getInstance().mImageUrl);
  }

  private void init()
  {
    FragmentUtil.getInstance().add(this);
    setData();
    setBottom();
    setTitleBar();
    setPager();
  }

  private void initTagBgColor()
  {
    int i = 0;
    if (i >= 7);
    for (int n = 7; ; n++)
    {
      if (n >= 14)
      {
        return;
        int j = 231 - i * 18;
        int k = 85 - i * 3;
        int m = 85 - i * 3;
        this.mTagBgColor[i] = Color.rgb(j, k, m);
        i++;
        break;
      }
      int i1 = 118 + 14 * (n - 7);
      this.mTagBgColor[n] = Color.rgb(i1, i1, i1);
    }
  }

  private boolean isShowGuide()
  {
    SharedPreferences localSharedPreferences = getActivity().getSharedPreferences("show_slide_guide", 0);
    int i = 1 + localSharedPreferences.getInt("set_count", 0);
    localSharedPreferences.edit().putInt("set_count", i).commit();
    return i == 1;
  }

  private void modifyDataModelAfterSubmitTagSucceed(String paramString, boolean paramBoolean)
  {
    this.mRpNum = (1 + this.mRpNum);
    RepostModel.getInstance().setRpNum(this.mRpNum);
    if (paramBoolean);
    try
    {
      int k = Math.min(this.mTagList.length(), this.TAG_NUM);
      JSONObject localJSONObject2 = new JSONObject();
      localJSONObject2.put("id", "");
      localJSONObject2.put("name", paramString);
      this.mTagList.put(k, localJSONObject2);
      this.TAG_NUM = (1 + this.TAG_NUM);
      JSONObject localJSONObject1 = new JSONObject();
      localJSONObject1.put("answer", '"' + paramString + '"');
      localJSONObject1.put("fname", User.getInstance().getRealName());
      localJSONObject1.put("uid", User.getInstance().getUID());
      JSONArray localJSONArray = new JSONArray();
      localJSONArray.put(0, localJSONObject1);
      for (int i = 0; ; i++)
      {
        if (i >= this.mResultList.length())
        {
          RepostModel.getInstance().setResultList(localJSONArray);
          updateData();
          return;
        }
        int j = i + 1;
        localJSONArray.put(j, this.mResultList.getJSONObject(i));
      }
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }

  private void modifyDataModelAfterVoteSucceed(int paramInt)
  {
    this.mRpNum = (1 + this.mRpNum);
    RepostModel.getInstance().setRpNum(this.mRpNum);
    int i = 0;
    try
    {
      JSONArray localJSONArray;
      if (i >= this.mAnswerList.length())
      {
        String str = '"' + this.mAnswerList.getJSONObject(paramInt - 1).getString("answer") + '"';
        JSONObject localJSONObject2 = new JSONObject();
        localJSONObject2.put("answer", str);
        localJSONObject2.put("fname", User.getInstance().getRealName());
        localJSONObject2.put("uid", User.getInstance().getUID());
        localJSONArray = new JSONArray();
        localJSONArray.put(0, localJSONObject2);
      }
      for (int m = 0; ; m++)
      {
        if (m >= this.mResultList.length())
        {
          RepostModel.getInstance().setResultList(localJSONArray);
          updateData();
          return;
          JSONObject localJSONObject1 = this.mAnswerList.getJSONObject(i);
          int j = localJSONObject1.getInt("votenum");
          localJSONObject1.getInt("votepercent");
          if (i == paramInt - 1)
            j++;
          int k = j * 100 / this.mRpNum;
          localJSONObject1.put("votenum", j);
          localJSONObject1.put("votepercent", k);
          i++;
          break;
        }
        localJSONArray.put(m + 1, this.mResultList.getJSONObject(m));
      }
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }

  private void onClickNewTag()
  {
    String str;
    if (this.mEditText != null)
    {
      str = this.mEditText.getText().toString();
      if (TextUtils.isEmpty(str))
        DialogUtil.showMsgDlgLiteConfirm(getActivity(), 2131427457, null);
    }
    else
    {
      return;
    }
    doSubmitTag(-1, str);
  }

  private void onClickRepost()
  {
    if (this.is_all_repost)
      Toast.makeText(getActivity(), 2131427535, 0).show();
    String str1;
    String str2;
    do
    {
      return;
      str1 = this.mModel.getRepostId();
      str2 = this.mModel.getRepostFuid();
    }
    while ((str1.length() <= 0) || (str2.length() <= 0));
    if (this.mModel.getNewflag() != 0)
    {
      DialogUtil.showMsgDlgStd(getActivity(), 2131427657, 2131427656, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          RepostDetailFragment.this.doRepost();
          UserHabitUtils.getInstance(RepostDetailFragment.this.getActivity()).addUserHabit("<Repaste_CommonAction>");
        }
      });
      return;
    }
    doRepost();
    UserHabitUtils.getInstance(getActivity()).addUserHabit("Repaste_CommonAction");
  }

  private int selectTagBgColor(int paramInt)
  {
    if (paramInt < this.mTagBgColor.length)
      return this.mTagBgColor[paramInt];
    return this.mTagBgColor[(-1 + this.mTagBgColor.length)];
  }

  private int selectVoteBarImage(int paramInt)
  {
    int i = paramInt % 9;
    return this.mVoteBarImage[i];
  }

  private void setBottom()
  {
    this.bottomBar = findViewById(2131363571);
    this.mEditText = ((EditText)findViewById(2131363572));
    this.mSubmitTagBtn = ((ImageView)findViewById(2131363573));
    this.mSubmitTagBtn.setOnClickListener(this);
    this.mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View paramView, boolean paramBoolean)
      {
        if (paramBoolean)
        {
          if (TextUtils.isEmpty(String.valueOf(RepostDetailFragment.this.mEditText.getText()).trim()))
          {
            RepostDetailFragment.this.mSubmitTagBtn.setImageResource(2130838280);
            return;
          }
          RepostDetailFragment.this.mSubmitTagBtn.setImageResource(2130838281);
          return;
        }
        RepostDetailFragment.this.mSubmitTagBtn.setImageResource(2130838280);
      }
    });
    this.mEditText.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramEditable)
      {
      }

      public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
      }

      public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
        if (RepostDetailFragment.this.isNeedReturn())
          return;
        if (TextUtils.isEmpty(String.valueOf(RepostDetailFragment.this.mEditText.getText()).trim()))
        {
          RepostDetailFragment.this.mSubmitTagBtn.setImageResource(2130838280);
          return;
        }
        RepostDetailFragment.this.mSubmitTagBtn.setImageResource(2130838281);
      }
    });
  }

  private void setData()
  {
    Bundle localBundle = getArguments();
    String str1 = null;
    if (localBundle != null)
    {
      str1 = localBundle.getString("action");
      this.mRepList = ((List)localBundle.getSerializable("repostList"));
      this.touchPosition = localBundle.getInt("position");
      this.mCommentFlag = localBundle.getString("");
      this.isShowGuide = localBundle.getBoolean("isShowGuide", false);
    }
    if ((str1 != null) && (!str1.equalsIgnoreCase("com.kaixin001.VIEW_REPOST_DETAIL")) && (CrashRecoverUtil.isCrashBefore()))
    {
      CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
      IntentUtil.returnToDesktop(getActivity());
      return;
    }
    CheckSupportFlashUtil.getInstance(getActivity().getApplicationContext()).isSupportFlash();
    String str2 = localBundle.getString("isShowMoreRep");
    if (str2 != null)
    {
      this.isShowMoreRep = str2;
      label138: if (!localBundle.getBoolean("is_all_repost"))
        break label246;
    }
    label246: for (this.is_all_repost = true; ; this.is_all_repost = false)
    {
      this.showKeyboard = localBundle.getBoolean("keyboard");
      if ((str1 == null) || (!str1.equalsIgnoreCase("com.kaixin001.VIEW_REPOST_DETAIL")))
        break;
      String str3 = localBundle.getString("statKey");
      String str4 = localBundle.getString("usertype");
      if (!TextUtils.isEmpty(str3))
        UserHabitUtils.getInstance(getActivity()).addUserHabitAndSendAtOnce(str3);
      if (TextUtils.isEmpty(str4))
        break;
      UserHabitUtils.getInstance(getActivity()).addUserHabitAndSendAtOnce(str4);
      return;
      this.isShowMoreRep = "0";
      break label138;
    }
  }

  private void setPager()
  {
    if (this.mRepostPager != null)
    {
      this.mRepostPager.removeAllViews();
      this.mRepostPager = null;
    }
    this.mRepostPager = ((ViewPager)findViewById(2131363574));
    this.mRepostPager.setAdapter(this.repostDetailAdapter);
    this.mRepostPager.setOnPageChangeListener(this);
  }

  private void showPopUpWindow(View paramView)
  {
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
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(Integer.valueOf(2130838213));
    localArrayList1.add(Integer.valueOf(2130838212));
    localArrayList1.add(Integer.valueOf(2130838211));
    ArrayList localArrayList2 = new ArrayList();
    localArrayList2.add(Integer.valueOf(2131428582));
    localArrayList2.add(Integer.valueOf(2131428583));
    localArrayList2.add(Integer.valueOf(2131428584));
    View localView = getActivity().getLayoutInflater().inflate(2130903295, null, false);
    float f = getResources().getDisplayMetrics().density;
    int i = getResources().getConfiguration().orientation;
    int j = (int)(159.0F * f);
    if (i == 1);
    for (this.mPopupWindow = new KXPopupListWindow(localView, j, -2, true, getActivity(), localArrayList1, localArrayList2, null); ; this.mPopupWindow = new KXPopupListWindow(localView, j, -2, true, getActivity(), localArrayList1, localArrayList2, null))
    {
      this.mPopupWindow.setDoSelectListener(this);
      this.mPopupWindow.setOnDismissListener(this);
      this.mPopupWindow.setOutsideTouchable(true);
      this.mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
      this.mPopupWindow.setClippingEnabled(false);
      int[] arrayOfInt = new int[2];
      paramView.getLocationOnScreen(arrayOfInt);
      int k = arrayOfInt[1] + (int)(48.0F * f);
      int m = (int)(getActivity().getResources().getDisplayMetrics().widthPixels - j - (int)(2.0F * KXEnvironment.DENSITY));
      this.mPopupWindow.showAtLocation(paramView, 51, m, k);
      return;
    }
  }

  private void startQzoneShareService()
  {
    Bundle localBundle = getShareQZoneParams();
    new Thread(new Runnable(getActivity(), localBundle)
    {
      public void run()
      {
        RepostDetailFragment.this.mTencent.shareToQzone(this.val$activity, this.val$params, new IUiListener(this.val$activity)
        {
          public void onCancel()
          {
            Toast.makeText(this.val$activity, "on cancel", 0).show();
          }

          public void onComplete(JSONObject paramJSONObject)
          {
            Toast.makeText(this.val$activity, "on complete", 0).show();
          }

          public void onError(UiError paramUiError)
          {
            Toast.makeText(this.val$activity, "on error" + paramUiError.errorMessage, 0).show();
          }
        });
      }
    }).start();
  }

  private boolean updateAnswerListProgressively()
  {
    int i = 1;
    if (this.mAnswerList == null)
      return i;
    if (this.mAnswerList.length() == 0)
      return i;
    this.mTimerCycles = (1 + this.mTimerCycles);
    int j = 10 * this.mTimerCycles;
    for (int k = 0; ; k++)
    {
      try
      {
        if (k < this.mAnswerList.length())
        {
          View localView1 = (View)this.mVoteControlMap.get(String.valueOf(k));
          if (localView1 == null)
            continue;
          JSONObject localJSONObject = this.mAnswerList.getJSONObject(k);
          LinearLayout localLinearLayout = (LinearLayout)localView1.findViewById(2131364012);
          View localView2 = localView1.findViewById(2131364013);
          int m = localLinearLayout.getWidth() * localJSONObject.getInt("votepercent") / 100;
          int n = Math.min(m, j);
          if (localView2.getWidth() >= m)
            continue;
          TableLayout.LayoutParams localLayoutParams = new TableLayout.LayoutParams();
          localLayoutParams.height = -1;
          localLayoutParams.width = n;
          localView2.setLayoutParams(localLayoutParams);
          i = 0;
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        i = 1;
      }
      return i;
    }
  }

  private void updateData()
  {
    this.mRpNum = RepostModel.getInstance().getRpNum();
    this.mAnswerList = RepostModel.getInstance().getAnswerList();
    this.mResultList = RepostModel.getInstance().getResultList();
    this.mTagList = RepostModel.getInstance().getTagList();
    if ((this.mTagList != null) && (this.mTagList.length() > 0))
    {
      this.mMode = 21;
      return;
    }
    this.mMode = 20;
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.guideLayout.getVisibility() == 0)
    {
      new Handler().postDelayed(new Runnable()
      {
        public void run()
        {
          RepostDetailFragment.this.guideLayout.setVisibility(8);
        }
      }
      , 500L);
      return true;
    }
    return false;
  }

  protected void doGetRepost()
  {
    if (!super.checkNetworkAndHint(true))
    {
      this.progressItem.setVisibility(8);
      findViewById(2131363571).setVisibility(8);
      this.mContentView.setVisibility(8);
      return;
    }
    this.mGetRepostTask = new GetRepostTask(null);
    this.mGetRepostTask.execute(new Void[0]);
    this.mRelevantLinkTask = new GetRelevantLinkTask();
    this.mRelevantLinkTask.execute(new Void[0]);
  }

  public void doRelevantLink()
  {
    this.relevantData = this.mModel.getDataList();
    if ((this.relevantData == null) || (this.relevantData.length() == 0))
    {
      this.relevantPostText.setVisibility(8);
      this.relevantPostParent.setVisibility(8);
      return;
    }
    this.relevantPostText.setVisibility(0);
    this.relevantPostParent.setVisibility(0);
    constructLinkViews();
  }

  public void doSelect(int paramInt)
  {
    this.shareIndex = paramInt;
    switch (paramInt)
    {
    default:
      return;
    case 0:
      UserHabitUtils.getInstance(getActivity()).addUserHabit("kxapp_share_wx");
      getShareInfo();
      return;
    case 1:
      UserHabitUtils.getInstance(getActivity()).addUserHabit("kxapp_share_weixin");
      getShareInfo();
      return;
    case 2:
    }
    UserHabitUtils.getInstance(getActivity()).addUserHabit("kxapp_share_qzone");
    startQzoneShareService();
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 2);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt2 == -1) && (paramInt1 == 3))
      this.mReplyContent = paramIntent.getStringExtra("content");
  }

  public void onClick(View paramView)
  {
    if (paramView == null)
      return;
    switch (paramView.getId())
    {
    default:
      return;
    case 2131362928:
      onClickRepost();
      return;
    case 2131363573:
    }
    onClickNewTag();
  }

  public void onClick(KXLinkInfo paramKXLinkInfo)
  {
    if ((paramKXLinkInfo.isStar()) || (paramKXLinkInfo.isUserName()))
      IntentUtil.showHomeFragment(this, paramKXLinkInfo.getId(), paramKXLinkInfo.getContent());
    do
    {
      return;
      if (!paramKXLinkInfo.isUrlLink())
        continue;
      IntentUtil.showWebPage(getActivity(), this, paramKXLinkInfo.getId(), null);
      return;
    }
    while (!paramKXLinkInfo.isTopic());
    IntentUtil.showTopicGroupActivity(this, paramKXLinkInfo.getId());
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mTencent = Tencent.createInstance("100228505", getActivity().getApplicationContext());
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903318, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if ((this.mGetRepostTask != null) && (this.mGetRepostTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetRepostTask.cancel(true);
      RepostEngine.getInstance().cancel();
    }
    if ((this.mRepostTask != null) && (this.mRepostTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      RepostEngine.getInstance().cancel();
      this.mRepostTask.cancel(true);
    }
    if ((this.mSubmitTagTask != null) && (this.mSubmitTagTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      RepostEngine.getInstance().cancel();
      this.mSubmitTagTask.cancel(true);
    }
    if ((this.mRepostVoteTask != null) && (this.mRepostVoteTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      RepostEngine.getInstance().cancel();
      this.mRepostVoteTask.cancel(true);
    }
    try
    {
      if (this.mContentView != null)
      {
        this.mContentView.setVisibility(8);
        this.mContentView.clearCache(true);
        this.mContentView.destroy();
        clearCacheFolder(getActivity().getCacheDir(), System.currentTimeMillis());
      }
      super.onDestroy();
      System.gc();
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public void onDismiss()
  {
  }

  public void onPageScrollStateChanged(int paramInt)
  {
  }

  public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
  {
  }

  public void onPageSelected(int paramInt)
  {
    if (this.mPosition != paramInt)
    {
      this.mPosition = paramInt;
      setCenter();
    }
  }

  public void onPause()
  {
    super.onPause();
  }

  public void onResume()
  {
    super.onResume();
    if (this.mContentView != null)
    {
      View localView = this.mContentView.getFocusedChild();
      this.mContentView.clearChildFocus(localView);
      this.mContentView.clearFocus();
    }
    try
    {
      Class.forName("android.webkit.WebView").getMethod("onResume", null).invoke(this.mContentView, null);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    init();
    User.getInstance().loadDataIfEmpty(getActivity().getApplicationContext());
    if (this.mMode == 21)
      initTagBgColor();
  }

  public void setCenter()
  {
    String str1 = ((RepItem)this.mRepList.get(this.mPosition + this.touchPosition)).fuid;
    String str2 = ((RepItem)this.mRepList.get(this.mPosition + this.touchPosition)).id;
    this.mFriendName = ((RepItem)this.mRepList.get(this.mPosition + this.touchPosition)).fname;
    findViewByIds();
    if (Build.VERSION.SDK_INT >= 11);
    try
    {
      this.mContentView.getClass().getMethod("removeJavascriptInterface", new Class[] { String.class }).invoke(this.mContentView, new Object[] { "searchBoxJavaBridge_" });
      this.mContentView.getClass().getMethod("removeJavascriptInterface", new Class[] { String.class }).invoke(this.mContentView, new Object[] { "accessibility" });
      label172: this.mContentView.setWebViewClient(new WebViewListener(null));
      this.mContentView.setPictureListener(new WebViewPictureListener(null));
      this.mContentView.setHorizontalScrollBarEnabled(false);
      this.mContentView.setHorizontalScrollbarOverlay(false);
      this.mContentView.setWebChromeClient(new WebVideoChromeClient(null));
      this.mContentView.addJavascriptInterface(new JavascriptInterface(), "imagelistner");
      WebSettings localWebSettings = this.mContentView.getSettings();
      localWebSettings.setSupportZoom(false);
      localWebSettings.setBuiltInZoomControls(false);
      localWebSettings.setJavaScriptEnabled(true);
      localWebSettings.setLoadWithOverviewMode(true);
      localWebSettings.setUseWideViewPort(false);
      localWebSettings.setCacheMode(2);
      localWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
      localWebSettings.setPluginState(WebSettings.PluginState.ON);
      this.mScrollView = ((KXScrollView)findViewById(2131363561));
      this.mScrollView.setSizeChangeListener(new KXScrollView.SizeChangeListener()
      {
        public void onSizeChanged(int paramInt)
        {
          RepostDetailFragment.this.mScreenSizeType = paramInt;
        }
      });
      this.mTagOnClickListener = new TagOnClickListener(null);
      this.mTagOnTouchListener = new TagOnTouchListener(null);
      this.detailLayout.setVisibility(8);
      this.bottomBar.setVisibility(8);
      if (getArguments() == null)
      {
        this.mFriendName = this.mModel.getRepostFname();
        this.progressItem.setVisibility(8);
        this.bottomBar.setVisibility(0);
        this.mContentView.setVisibility(0);
        String str3 = buildContentForDiffrentDevice(this.mModel.getRepostContent());
        String str4 = FileUtil.getKXCacheDir(getActivity());
        if (!FileUtil.setCacheData(str4, User.getInstance().getUID(), "default.html", str3))
          KXLog.d("RepostDetailActivity", "failed to set cache data");
        new StringBuilder("file://").append(str4).append("/data/").append(User.getInstance().getUID()).append("/default.html").toString();
        this.mContentView.getSettings().setDefaultTextEncodingName("UTF-8");
        return;
      }
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      while (true)
        localIllegalAccessException.printStackTrace();
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      while (true)
        localInvocationTargetException.printStackTrace();
      this.progressItem.setVisibility(8);
      this.mContentView.setVisibility(8);
      this.bottomBar.setVisibility(8);
      this.mRpid = str2;
      this.mFuid = str1;
      doGetRepost();
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      break label172;
    }
  }

  protected void setTitleBar()
  {
    this.guideLayout = findViewById(2131363575);
    if ((this.isShowGuide) && (isShowGuide()))
      this.guideLayout.setVisibility(0);
    ImageView localImageView1 = (ImageView)findViewById(2131362914);
    localImageView1.setVisibility(0);
    localImageView1.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (!TextUtils.isEmpty(RepostDetailFragment.this.mReplyContent))
        {
          Intent localIntent = new Intent();
          localIntent.putExtra("content", RepostDetailFragment.this.mReplyContent);
          RepostDetailFragment.this.setResult(-1, localIntent);
          RepostDetailFragment.this.finishFragment(3);
        }
        if (RepostDetailFragment.this.finishAndRedirect())
          return;
        FragmentUtil.getInstance().finish();
        FragmentUtil.getInstance().clear();
      }
    });
    ImageView localImageView2 = (ImageView)findViewById(2131362924);
    ImageView localImageView3 = (ImageView)findViewById(2131362928);
    ImageView localImageView4;
    if (this.isShowMoreRep.equals("1"))
    {
      localImageView2.setImageResource(2130839023);
      localImageView3.setImageResource(2130838148);
      localImageView2.setVisibility(0);
      localImageView2.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          Intent localIntent = new Intent(RepostDetailFragment.this.getActivity(), RepostListFragment.class);
          localIntent.putExtra("hot", true);
          AnimationUtil.startFragment(RepostDetailFragment.this, localIntent, 1);
        }
      });
      localImageView3.setVisibility(0);
      localImageView3.setOnClickListener(this);
      localImageView4 = (ImageView)findViewById(2131362932);
      if (WXManager.getInstance().getWxApi().isWXAppInstalled())
        break label345;
      localImageView4.setVisibility(8);
    }
    while (true)
    {
      localImageView4.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          if (RepostDetailFragment.this.getActivity() != null)
          {
            UserHabitUtils.getInstance(RepostDetailFragment.this.getActivity()).addUserHabit("kxrepast_share");
            RepostDetailFragment.this.showPopUpWindow(paramView);
          }
        }
      });
      ((ImageView)findViewById(2131362919)).setVisibility(8);
      TextView localTextView = (TextView)findViewById(2131362920);
      String str = this.mFriendName;
      if (User.getInstance().getUID().compareTo(this.mFuid) == 0)
        str = getResources().getString(2131427565);
      if (str.length() > 5)
        str = str.substring(0, 4) + "...";
      localTextView.setText(str + getResources().getString(2131427601));
      localTextView.setText("转帖详情");
      localTextView.setVisibility(0);
      return;
      localImageView3.setImageResource(2130838148);
      localImageView2.setVisibility(8);
      break;
      label345: localImageView4.setVisibility(0);
    }
  }

  public void shareWX(int paramInt)
  {
    this.mShareTitle = RepostModel.getInstance().mTitle;
    this.mShareDes = RepostModel.getInstance().mTitle;
    this.mShareUrl = RepostModel.getInstance().mWapUrl;
    StringBuilder localStringBuilder = new StringBuilder("title:").append(this.mShareTitle).append(", des:").append(this.mShareDes).append(", url:").append(this.mShareUrl).append(", icon:");
    if (this.mShareIcon != null);
    for (boolean bool = true; ; bool = false)
    {
      KXLog.d("SHAREWX", bool);
      if (paramInt != 0)
        break;
      WXManager.getInstance().sendMsgToFriendCircle(getActivity(), this.mShareTitle, this.mShareDes, this.mShareIcon, this.mShareUrl);
      return;
    }
    WXManager.getInstance().sendMsgToWxFriend(getActivity(), this.mShareTitle, this.mShareDes, this.mShareIcon, this.mShareUrl);
  }

  class GetRelevantLinkTask extends KXFragment.KXAsyncTask<Void, Void, Boolean>
  {
    GetRelevantLinkTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(Void[] paramArrayOfVoid)
    {
      try
      {
        Boolean localBoolean = Boolean.valueOf(RepostEngine.getInstance().doGetRepostRelevantLink(RepostDetailFragment.this.getActivity(), RepostDetailFragment.this.mRpid, RepostDetailFragment.this.mFuid));
        return localBoolean;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        localSecurityErrorException.printStackTrace();
      }
      return null;
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      if (paramBoolean.booleanValue())
        RepostDetailFragment.this.doRelevantLink();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class GetRepostTask extends KXFragment.KXAsyncTask<Void, Void, Boolean>
  {
    private GetRepostTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(Void[] paramArrayOfVoid)
    {
      try
      {
        Boolean localBoolean = Boolean.valueOf(RepostEngine.getInstance().doGetRepostDetail(RepostDetailFragment.this.getActivity(), RepostDetailFragment.this.mRpid, RepostDetailFragment.this.mFuid));
        return localBoolean;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    // ERROR //
    protected void onPostExecuteA(Boolean paramBoolean)
    {
      // Byte code:
      //   0: aload_1
      //   1: ifnonnull +11 -> 12
      //   4: aload_0
      //   5: getfield 11	com/kaixin001/fragment/RepostDetailFragment$GetRepostTask:this$0	Lcom/kaixin001/fragment/RepostDetailFragment;
      //   8: invokevirtual 63	com/kaixin001/fragment/RepostDetailFragment:finish	()V
      //   11: return
      //   12: aload_0
      //   13: getfield 11	com/kaixin001/fragment/RepostDetailFragment$GetRepostTask:this$0	Lcom/kaixin001/fragment/RepostDetailFragment;
      //   16: invokestatic 67	com/kaixin001/fragment/RepostDetailFragment:access$5	(Lcom/kaixin001/fragment/RepostDetailFragment;)Landroid/view/View;
      //   19: bipush 8
      //   21: invokevirtual 73	android/view/View:setVisibility	(I)V
      //   24: aload_1
      //   25: invokevirtual 77	java/lang/Boolean:booleanValue	()Z
      //   28: ifeq +184 -> 212
      //   31: aload_0
      //   32: getfield 11	com/kaixin001/fragment/RepostDetailFragment$GetRepostTask:this$0	Lcom/kaixin001/fragment/RepostDetailFragment;
      //   35: invokestatic 81	com/kaixin001/fragment/RepostDetailFragment:access$6	(Lcom/kaixin001/fragment/RepostDetailFragment;)Landroid/webkit/WebView;
      //   38: iconst_0
      //   39: invokevirtual 84	android/webkit/WebView:setVisibility	(I)V
      //   42: aload_0
      //   43: getfield 11	com/kaixin001/fragment/RepostDetailFragment$GetRepostTask:this$0	Lcom/kaixin001/fragment/RepostDetailFragment;
      //   46: invokestatic 87	com/kaixin001/fragment/RepostDetailFragment:access$7	(Lcom/kaixin001/fragment/RepostDetailFragment;)Landroid/view/View;
      //   49: iconst_0
      //   50: invokevirtual 73	android/view/View:setVisibility	(I)V
      //   53: aload_0
      //   54: getfield 11	com/kaixin001/fragment/RepostDetailFragment$GetRepostTask:this$0	Lcom/kaixin001/fragment/RepostDetailFragment;
      //   57: aload_0
      //   58: getfield 11	com/kaixin001/fragment/RepostDetailFragment$GetRepostTask:this$0	Lcom/kaixin001/fragment/RepostDetailFragment;
      //   61: invokestatic 91	com/kaixin001/fragment/RepostDetailFragment:access$8	(Lcom/kaixin001/fragment/RepostDetailFragment;)Lcom/kaixin001/model/RepostModel;
      //   64: invokevirtual 97	com/kaixin001/model/RepostModel:getRepostContent	()Ljava/lang/String;
      //   67: invokestatic 101	com/kaixin001/fragment/RepostDetailFragment:access$9	(Lcom/kaixin001/fragment/RepostDetailFragment;Ljava/lang/String;)Ljava/lang/String;
      //   70: astore_3
      //   71: aload_0
      //   72: getfield 11	com/kaixin001/fragment/RepostDetailFragment$GetRepostTask:this$0	Lcom/kaixin001/fragment/RepostDetailFragment;
      //   75: invokevirtual 33	com/kaixin001/fragment/RepostDetailFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
      //   78: invokestatic 107	com/kaixin001/util/FileUtil:getKXCacheDir	(Landroid/content/Context;)Ljava/lang/String;
      //   81: astore 4
      //   83: aload 4
      //   85: invokestatic 112	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
      //   88: invokevirtual 115	com/kaixin001/model/User:getUID	()Ljava/lang/String;
      //   91: ldc 117
      //   93: aload_3
      //   94: invokestatic 121	com/kaixin001/util/FileUtil:setCacheData	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
      //   97: ifne +10 -> 107
      //   100: ldc 123
      //   102: ldc 125
      //   104: invokestatic 131	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
      //   107: new 133	java/lang/StringBuilder
      //   110: dup
      //   111: ldc 135
      //   113: invokespecial 138	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   116: aload 4
      //   118: invokevirtual 142	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   121: ldc 144
      //   123: invokevirtual 142	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   126: invokestatic 112	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
      //   129: invokevirtual 115	com/kaixin001/model/User:getUID	()Ljava/lang/String;
      //   132: invokevirtual 142	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   135: ldc 146
      //   137: invokevirtual 142	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   140: invokevirtual 149	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   143: astore 5
      //   145: aload_0
      //   146: getfield 11	com/kaixin001/fragment/RepostDetailFragment$GetRepostTask:this$0	Lcom/kaixin001/fragment/RepostDetailFragment;
      //   149: invokestatic 81	com/kaixin001/fragment/RepostDetailFragment:access$6	(Lcom/kaixin001/fragment/RepostDetailFragment;)Landroid/webkit/WebView;
      //   152: invokevirtual 153	android/webkit/WebView:getSettings	()Landroid/webkit/WebSettings;
      //   155: ldc 155
      //   157: invokevirtual 160	android/webkit/WebSettings:setDefaultTextEncodingName	(Ljava/lang/String;)V
      //   160: aload_0
      //   161: getfield 11	com/kaixin001/fragment/RepostDetailFragment$GetRepostTask:this$0	Lcom/kaixin001/fragment/RepostDetailFragment;
      //   164: invokestatic 81	com/kaixin001/fragment/RepostDetailFragment:access$6	(Lcom/kaixin001/fragment/RepostDetailFragment;)Landroid/webkit/WebView;
      //   167: aload 5
      //   169: invokevirtual 163	android/webkit/WebView:loadUrl	(Ljava/lang/String;)V
      //   172: aload_0
      //   173: getfield 11	com/kaixin001/fragment/RepostDetailFragment$GetRepostTask:this$0	Lcom/kaixin001/fragment/RepostDetailFragment;
      //   176: invokestatic 166	com/kaixin001/fragment/RepostDetailFragment:access$10	(Lcom/kaixin001/fragment/RepostDetailFragment;)V
      //   179: aload_0
      //   180: getfield 11	com/kaixin001/fragment/RepostDetailFragment$GetRepostTask:this$0	Lcom/kaixin001/fragment/RepostDetailFragment;
      //   183: invokestatic 169	com/kaixin001/fragment/RepostDetailFragment:access$11	(Lcom/kaixin001/fragment/RepostDetailFragment;)V
      //   186: return
      //   187: astore_2
      //   188: ldc 123
      //   190: ldc 171
      //   192: aload_2
      //   193: invokestatic 175	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
      //   196: return
      //   197: astore 6
      //   199: ldc 177
      //   201: aload 6
      //   203: invokevirtual 178	java/lang/Exception:toString	()Ljava/lang/String;
      //   206: invokestatic 180	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;)V
      //   209: goto -37 -> 172
      //   212: invokestatic 183	com/kaixin001/model/RepostModel:getInstance	()Lcom/kaixin001/model/RepostModel;
      //   215: invokevirtual 187	com/kaixin001/model/RepostModel:getErrorNum	()I
      //   218: sipush 4001
      //   221: if_icmpne +20 -> 241
      //   224: aload_0
      //   225: getfield 11	com/kaixin001/fragment/RepostDetailFragment$GetRepostTask:this$0	Lcom/kaixin001/fragment/RepostDetailFragment;
      //   228: invokevirtual 33	com/kaixin001/fragment/RepostDetailFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
      //   231: ldc 188
      //   233: iconst_0
      //   234: invokestatic 194	android/widget/Toast:makeText	(Landroid/content/Context;II)Landroid/widget/Toast;
      //   237: invokevirtual 197	android/widget/Toast:show	()V
      //   240: return
      //   241: invokestatic 183	com/kaixin001/model/RepostModel:getInstance	()Lcom/kaixin001/model/RepostModel;
      //   244: invokevirtual 187	com/kaixin001/model/RepostModel:getErrorNum	()I
      //   247: bipush 251
      //   249: if_icmpne +20 -> 269
      //   252: aload_0
      //   253: getfield 11	com/kaixin001/fragment/RepostDetailFragment$GetRepostTask:this$0	Lcom/kaixin001/fragment/RepostDetailFragment;
      //   256: invokevirtual 33	com/kaixin001/fragment/RepostDetailFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
      //   259: ldc 188
      //   261: iconst_0
      //   262: invokestatic 194	android/widget/Toast:makeText	(Landroid/content/Context;II)Landroid/widget/Toast;
      //   265: invokevirtual 197	android/widget/Toast:show	()V
      //   268: return
      //   269: aload_0
      //   270: getfield 11	com/kaixin001/fragment/RepostDetailFragment$GetRepostTask:this$0	Lcom/kaixin001/fragment/RepostDetailFragment;
      //   273: invokevirtual 33	com/kaixin001/fragment/RepostDetailFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
      //   276: ldc 198
      //   278: iconst_0
      //   279: invokestatic 194	android/widget/Toast:makeText	(Landroid/content/Context;II)Landroid/widget/Toast;
      //   282: invokevirtual 197	android/widget/Toast:show	()V
      //   285: return
      //
      // Exception table:
      //   from	to	target	type
      //   12	107	187	java/lang/Exception
      //   107	145	187	java/lang/Exception
      //   172	186	187	java/lang/Exception
      //   199	209	187	java/lang/Exception
      //   212	240	187	java/lang/Exception
      //   241	268	187	java/lang/Exception
      //   269	285	187	java/lang/Exception
      //   145	172	197	java/lang/Exception
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class GetShareWxInfoTask extends AsyncTask<Void, Void, Integer>
  {
    private GetShareWxInfoTask()
    {
    }

    protected Integer doInBackground(Void[] paramArrayOfVoid)
    {
      try
      {
        boolean bool = TextUtils.isEmpty(RepostModel.getInstance().mImageUrl);
        if (!bool)
          try
          {
            byte[] arrayOfByte = HttpUtil.doGetData(RepostModel.getInstance().mImageUrl);
            Bitmap localBitmap = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length);
            int i = Math.min(localBitmap.getWidth(), localBitmap.getHeight());
            float f = 150 / i;
            Matrix localMatrix = new Matrix();
            localMatrix.postScale(f, f);
            RepostDetailFragment.this.mShareIcon = Bitmap.createBitmap(localBitmap, 0, 0, i, i, localMatrix, true);
            Integer localInteger = Integer.valueOf(1);
            return localInteger;
          }
          catch (Exception localException2)
          {
            localException2.printStackTrace();
          }
        return Integer.valueOf(-1);
      }
      catch (Exception localException1)
      {
        while (true)
          localException1.printStackTrace();
      }
    }

    protected void onPostExecute(Integer paramInteger)
    {
      if (RepostDetailFragment.this.mProgressDialog != null)
      {
        RepostDetailFragment.this.mProgressDialog.dismiss();
        RepostDetailFragment.this.mProgressDialog = null;
      }
      RepostDetailFragment.this.shareWX(RepostDetailFragment.this.shareIndex);
    }
  }

  class JavascriptInterface
  {
    JavascriptInterface()
    {
    }

    public void openImage(String[] paramArrayOfString, String paramString)
    {
      for (int i = 0; ; i++)
      {
        int j = paramArrayOfString.length;
        int k = 0;
        if (i >= j);
        while (true)
        {
          List localList = Arrays.asList(paramArrayOfString);
          Intent localIntent = new Intent(RepostDetailFragment.this.getActivity(), RepostDetailPictureFragment.class);
          Bundle localBundle = new Bundle();
          localBundle.putInt("position", k);
          localBundle.putSerializable("urls", (Serializable)localList);
          localIntent.putExtras(localBundle);
          AnimationUtil.startFragment(RepostDetailFragment.this, localIntent, 1);
          return;
          if (!paramArrayOfString[i].equals(paramString))
            break;
          k = i;
        }
      }
    }
  }

  class RepostPagerAdapter extends PagerAdapter
  {
    private Map<Integer, View> viewHolder = new HashMap();

    public RepostPagerAdapter()
    {
    }

    public void destroyItem(View paramView, int paramInt, Object paramObject)
    {
      ((ViewPager)paramView).removeView((View)paramObject);
    }

    public int getCount()
    {
      return RepostDetailFragment.this.mRepList.size() - RepostDetailFragment.this.touchPosition;
    }

    public int getItemPosition(Object paramObject)
    {
      return -2;
    }

    public Object instantiateItem(View paramView, int paramInt)
    {
      View localView;
      if (this.viewHolder.containsKey(Integer.valueOf(paramInt)))
        localView = (View)this.viewHolder.get(Integer.valueOf(paramInt));
      while (true)
      {
        ((ViewPager)paramView).addView(localView, 0);
        localView.setTag(Integer.valueOf(paramInt));
        if ((localView != null) && (paramInt == RepostDetailFragment.this.mPosition))
          RepostDetailFragment.this.setCenter();
        return localView;
        localView = LayoutInflater.from(RepostDetailFragment.this.getActivity()).inflate(2130903317, null);
        this.viewHolder.put(Integer.valueOf(paramInt), localView);
      }
    }

    public boolean isViewFromObject(View paramView, Object paramObject)
    {
      return paramView == paramObject;
    }
  }

  private class RepostTask extends KXFragment.KXAsyncTask<Void, Void, Boolean>
  {
    private RepostTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(Void[] paramArrayOfVoid)
    {
      try
      {
        Boolean localBoolean = Boolean.valueOf(RepostEngine.getInstance().doRepost(RepostDetailFragment.this, RepostDetailFragment.this.mModel.getRepostId(), RepostDetailFragment.this.mModel.getRepostFuid()));
        return localBoolean;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      if (paramBoolean == null)
      {
        RepostDetailFragment.this.finish();
        return;
      }
      try
      {
        if (RepostDetailFragment.this.m_ProgressDialog != null)
          RepostDetailFragment.this.m_ProgressDialog.dismiss();
        if (paramBoolean.booleanValue())
        {
          RepostDetailFragment.this.mModel.setNewflag(1);
          Toast.makeText(RepostDetailFragment.this.getActivity(), RepostDetailFragment.this.getResources().getString(2131427603), 0).show();
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("RepostDetailActivity", "onPostExecute", localException);
        return;
      }
      Toast.makeText(RepostDetailFragment.this.getActivity(), 2131427604, 0).show();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class RepostVoteTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    private int mAnswernum;

    private RepostVoteTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      if (paramArrayOfString.length < 1)
        return Boolean.valueOf(false);
      String str = paramArrayOfString[0];
      this.mAnswernum = Integer.parseInt(str);
      try
      {
        if (RepostEngine.getInstance().doRepostVote(RepostDetailFragment.this.getActivity().getApplicationContext(), RepostModel.getInstance().getRepostUrpid(), RepostModel.getInstance().getRepostVoteuid(), RepostModel.getInstance().getRepostSuid(), RepostModel.getInstance().getRepostSurpid(), str))
          return Boolean.valueOf(RepostEngine.getInstance().doRepost(RepostDetailFragment.this, RepostModel.getInstance().getRepostId(), RepostModel.getInstance().getRepostFuid()));
        Boolean localBoolean = Boolean.valueOf(false);
        return localBoolean;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return Boolean.valueOf(false);
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      try
      {
        if (RepostDetailFragment.this.m_ProgressDialog != null)
        {
          RepostDetailFragment.this.m_ProgressDialog.dismiss();
          RepostDetailFragment.this.m_ProgressDialog = null;
        }
        if (paramBoolean.booleanValue())
        {
          RepostDetailFragment.this.modifyDataModelAfterVoteSucceed(this.mAnswernum);
          RepostDetailFragment.this.constructViews();
          RepostDetailFragment.this.updateAnswerListProgressively();
          RepostModel.getInstance().setNewflag(1);
          Toast.makeText(RepostDetailFragment.this.getActivity(), RepostDetailFragment.this.getResources().getString(2131427617), 0).show();
          return;
        }
        if (!TextUtils.isEmpty(RepostEngine.getInstance().getLastError()))
        {
          Toast.makeText(RepostDetailFragment.this.getActivity(), RepostEngine.getInstance().getLastError(), 0).show();
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("RepostDetailActivity", "onPostExecute", localException);
        return;
      }
      Toast.makeText(RepostDetailFragment.this.getActivity(), 2131427609, 0).show();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class SubmitTagTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    String tagId = "";
    String tagName = "";

    private SubmitTagTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      if (paramArrayOfString.length < 2)
        return Boolean.valueOf(false);
      this.tagName = paramArrayOfString[0];
      this.tagId = paramArrayOfString[1];
      String str1 = this.tagId;
      String str2 = this.tagName;
      if (TextUtils.isEmpty(str1))
        str1 = "";
      try
      {
        while (RepostEngine.getInstance().doSubmitRepostTag(RepostDetailFragment.this.getActivity().getApplicationContext(), str1, str2, RepostModel.getInstance().getRepostSurpid(), RepostModel.getInstance().getRepostSuid()))
        {
          Boolean localBoolean2 = Boolean.valueOf(RepostEngine.getInstance().doRepost(RepostDetailFragment.this, RepostModel.getInstance().getRepostId(), RepostModel.getInstance().getRepostFuid()));
          return localBoolean2;
          str2 = "";
        }
        Boolean localBoolean1 = Boolean.valueOf(false);
        return localBoolean1;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return Boolean.valueOf(false);
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      if (RepostDetailFragment.this.m_ProgressDialog != null)
      {
        RepostDetailFragment.this.m_ProgressDialog.dismiss();
        RepostDetailFragment.this.m_ProgressDialog = null;
        ActivityUtil.hideInputMethod(RepostDetailFragment.this.getActivity());
      }
      try
      {
        if (paramBoolean.booleanValue())
        {
          RepostDetailFragment.this.modifyDataModelAfterSubmitTagSucceed(this.tagName, false);
          RepostDetailFragment.this.constructViews();
          Iterator localIterator = NewsModel.getInstance().getNewsList().iterator();
          if (!localIterator.hasNext());
          while (true)
          {
            if ((!TextUtils.isEmpty(this.tagName)) && (RepostDetailFragment.this.mEditText != null))
              RepostDetailFragment.this.mEditText.setText("");
            RepostModel.getInstance().setNewflag(1);
            Toast.makeText(RepostDetailFragment.this.getActivity(), RepostDetailFragment.this.getResources().getString(2131427617), 0).show();
            return;
            NewsInfo localNewsInfo = (NewsInfo)localIterator.next();
            if ((TextUtils.isEmpty(localNewsInfo.mNewsId)) || (!localNewsInfo.mNewsId.equals(RepostDetailFragment.this.mRpid)))
              break;
            localNewsInfo.mVNum = (1 + localNewsInfo.mVNum);
          }
        }
      }
      catch (Resources.NotFoundException localNotFoundException)
      {
        KXLog.e("RepostDetailActivity", "onPostExecute", localNotFoundException);
        return;
      }
      if (!TextUtils.isEmpty(RepostEngine.getInstance().getLastError()))
      {
        Toast.makeText(RepostDetailFragment.this.getActivity(), RepostEngine.getInstance().getLastError(), 0).show();
        return;
      }
      Toast.makeText(RepostDetailFragment.this.getActivity(), 2131427609, 0).show();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class TagOnClickListener
    implements View.OnClickListener
  {
    private TagOnClickListener()
    {
    }

    public void onClick(View paramView)
    {
      Integer localInteger = (Integer)paramView.getTag();
      RepostDetailFragment.this.doSubmitTag(localInteger.intValue(), null);
    }
  }

  private class TagOnTouchListener
    implements View.OnTouchListener
  {
    private TagOnTouchListener()
    {
    }

    public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
    {
      KXTagWidget localKXTagWidget = (KXTagWidget)paramView;
      switch (paramMotionEvent.getAction())
      {
      case 2:
      default:
      case 0:
      case 1:
      case 3:
      case 4:
      }
      while (true)
      {
        return false;
        CharSequence localCharSequence = localKXTagWidget.getText();
        RepostDetailFragment.this.desTextView.setTextColor(localKXTagWidget.getmBgColor());
        RepostDetailFragment.this.desTextView.setText(localCharSequence);
        localKXTagWidget.setTextColor(localKXTagWidget.getmPressedTextColor());
        localKXTagWidget.setBackgroundColor(localKXTagWidget.getmPressedBgColor());
        continue;
        String str = StringUtil.replaceTokenWith(RepostDetailFragment.this.getResources().getString(2131427612), "*", String.valueOf(RepostDetailFragment.this.mRpNum));
        RepostDetailFragment.this.desTextView.setTextColor(-7829368);
        RepostDetailFragment.this.desTextView.setText(str);
        localKXTagWidget.setTextColor(localKXTagWidget.getmTextColor());
        localKXTagWidget.setBackgroundColor(localKXTagWidget.getmBgColor());
      }
    }
  }

  private class VoteControlOnClickListener
    implements View.OnClickListener
  {
    private int mAnswernum;

    public VoteControlOnClickListener(int arg2)
    {
      int i;
      this.mAnswernum = i;
    }

    public void onClick(View paramView)
    {
      RepostDetailFragment.this.doRepostVote(this.mAnswernum);
    }
  }

  private static class WebVideoChromeClient extends WebChromeClient
  {
    public void onProgressChanged(WebView paramWebView, int paramInt)
    {
    }
  }

  private class WebViewListener extends WebViewClient
  {
    private WebViewListener()
    {
    }

    public void onLoadResource(WebView paramWebView, String paramString)
    {
      super.onLoadResource(paramWebView, paramString);
    }

    public void onPageFinished(WebView paramWebView, String paramString)
    {
      super.onPageFinished(paramWebView, paramString);
      paramWebView.loadUrl("javascript:(function(){var objs = document.getElementsByTagName(\"img\"); var array = new Array();for(var i=0;i<objs.length;i++){array[i]=objs[i].src;}for(var i=0;i<objs.length;i++)  {    objs[i].onclick=function()      {          window.imagelistner.openImage(array,this.src);      }  }})()");
    }

    public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
    {
      super.onPageStarted(paramWebView, paramString, paramBitmap);
    }

    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      return true;
    }
  }

  private class WebViewPictureListener
    implements WebView.PictureListener
  {
    private WebViewPictureListener()
    {
    }

    public void onNewPicture(WebView paramWebView, Picture paramPicture)
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.RepostDetailFragment
 * JD-Core Version:    0.6.0
 */