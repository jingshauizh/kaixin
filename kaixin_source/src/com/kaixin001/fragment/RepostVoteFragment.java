package com.kaixin001.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.RepostEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.model.RepostModel;
import com.kaixin001.model.User;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.StringUtil;
import com.kaixin001.view.KXIntroView;
import com.kaixin001.view.KXIntroView.OnClickLinkListener;
import com.kaixin001.view.KXTagWidget;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RepostVoteFragment extends BaseFragment
  implements KXIntroView.OnClickLinkListener
{
  private static final int MODE_TAG = 21;
  private static final int MODE_VOTE = 20;
  private static final int VOTE_BAR_IMAGE_NUM = 9;
  private int TAG_NUM = 15;
  private JSONArray mAnswerList = null;
  private int mMode = 21;
  private RepostModel mModel = RepostModel.getInstance();
  RepostTask mRepostTask = null;
  RepostVoteTask mRepostVoteTask = null;
  private JSONArray mResultList = null;
  private int mRpNum = 0;
  SubmitTagTask mSubmitTagTask = null;
  private int[] mTagBgColor = new int[14];
  private JSONArray mTagList = null;
  private TagOnClickListener mTagOnClickListener = null;
  private TagOnTouchListener mTagOnTouchListener = null;
  private Timer mTimer = new Timer();
  private int mTimerCycles = 0;
  private Handler mTimerHandler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      if (RepostVoteFragment.this.updateAnswerListProgressively())
        RepostVoteFragment.this.mTimer.cancel();
    }
  };
  private TimerTask mTimerTask = new TimerTask()
  {
    public void run()
    {
      if (RepostVoteFragment.this.isNeedReturn())
        return;
      RepostVoteFragment.this.mTimerHandler.sendEmptyMessage(1);
    }
  };
  private int[] mVoteBarImage = new int[9];
  private HashMap<String, View> mVoteControlMap = new HashMap();
  private ProgressDialog m_ProgressDialog = null;

  private void constructAnswerListView()
  {
    LinearLayout localLinearLayout = (LinearLayout)findViewById(2131363565);
    if (this.mAnswerList == null)
      localLinearLayout.setVisibility(8);
    while (true)
    {
      return;
      if (this.mAnswerList.length() == 0)
      {
        localLinearLayout.setVisibility(8);
        return;
      }
      localLinearLayout.removeAllViews();
      for (int i = 0; i < this.mAnswerList.length(); i++)
      {
        View localView = createOneAnswerView(i);
        if (localView == null)
          continue;
        localLinearLayout.addView(localView);
        this.mVoteControlMap.put(String.valueOf(i), localView);
        if (i == -1 + this.mAnswerList.length())
          continue;
        localLinearLayout.addView(createOnedivider());
      }
    }
  }

  private void constructResultListView()
  {
    LinearLayout localLinearLayout = (LinearLayout)findViewById(2131363568);
    if (this.mResultList == null)
    {
      localLinearLayout.setVisibility(8);
      return;
    }
    if (this.mResultList.length() == 0)
    {
      localLinearLayout.setVisibility(8);
      return;
    }
    localLinearLayout.removeAllViews();
    for (int i = 0; ; i++)
    {
      if (i >= this.mResultList.length())
      {
        localLinearLayout.setVisibility(0);
        return;
      }
      View localView = createOneResultView(i);
      if (localView == null)
        continue;
      localLinearLayout.addView(localView);
      if (i == -1 + this.mResultList.length())
        continue;
      localLinearLayout.addView(createOnedivider());
    }
  }

  private void constructTagListView()
  {
    LinearLayout localLinearLayout1 = (LinearLayout)findViewById(2131363566);
    if (this.mTagList == null)
    {
      localLinearLayout1.setVisibility(8);
      return;
    }
    if (this.mTagList.length() == 0)
    {
      localLinearLayout1.setVisibility(8);
      return;
    }
    localLinearLayout1.removeAllViews();
    float f1 = getActivity().getWindowManager().getDefaultDisplay().getWidth() - 25.0F;
    int i = Math.min(this.mTagList.length(), this.TAG_NUM);
    float f2 = f1;
    LinearLayout localLinearLayout2 = new LinearLayout(getActivity());
    TableLayout.LayoutParams localLayoutParams = new TableLayout.LayoutParams();
    localLayoutParams.width = -1;
    localLayoutParams.height = -2;
    localLayoutParams.bottomMargin = 1;
    localLinearLayout2.setLayoutParams(localLayoutParams);
    Object localObject = "";
    int j = 0;
    while (true)
    {
      if (j >= i)
      {
        if (localLinearLayout2 == null)
          break;
        localLinearLayout1.addView(localLinearLayout2);
        return;
      }
      LinearLayout localLinearLayout3 = (LinearLayout)getActivity().getLayoutInflater().inflate(2130903192, null);
      KXTagWidget localKXTagWidget = (KXTagWidget)localLinearLayout3.findViewById(2131362944);
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
          localLinearLayout3.removeView(localKXTagWidget);
          localLinearLayout2.addView(localKXTagWidget);
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
          localLinearLayout1.addView(localLinearLayout2);
          localLinearLayout2 = new LinearLayout(getActivity());
          localLinearLayout2.setLayoutParams(localLayoutParams);
          localLinearLayout3.removeView(localKXTagWidget);
          localLinearLayout2.addView(localKXTagWidget);
          f2 = f1 - f3;
        }
      }
    }
  }

  private void constructViews()
  {
    String str = StringUtil.replaceTokenWith(getResources().getString(2131427612), "*", String.valueOf(this.mRpNum));
    ((TextView)findViewById(2131363564)).setText(str);
    TextView localTextView = (TextView)findViewById(2131363567);
    View localView1;
    View localView2;
    View localView3;
    if (this.mResultList == null)
    {
      localTextView.setVisibility(8);
      localView1 = findViewById(2131363565);
      localView2 = findViewById(2131363566);
      localView3 = findViewById(2131363599);
      if (this.mMode != 20)
        break label150;
      localView1.setVisibility(0);
      localView2.setVisibility(8);
      localView3.setVisibility(8);
      constructAnswerListView();
    }
    while (true)
    {
      constructResultListView();
      return;
      if (this.mResultList.length() == 0)
      {
        localTextView.setVisibility(8);
        break;
      }
      localTextView.setVisibility(0);
      break;
      label150: localView1.setVisibility(8);
      localView2.setVisibility(0);
      localView3.setVisibility(0);
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
    this.mRepostTask = new RepostTask(null);
    this.mRepostTask.execute(new Void[0]);
    this.m_ProgressDialog = ProgressDialog.show(getActivity(), "", getResources().getText(2131427618), true, true, new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramDialogInterface)
      {
        if (RepostVoteFragment.this.mRepostTask != null)
        {
          RepostEngine.getInstance().cancel();
          RepostVoteFragment.this.mRepostTask.cancel(true);
          RepostVoteFragment.this.mRepostTask = null;
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
        if (RepostVoteFragment.this.mRepostVoteTask != null)
        {
          RepostEngine.getInstance().cancel();
          RepostVoteFragment.this.mRepostVoteTask.cancel(true);
          RepostVoteFragment.this.mRepostVoteTask = null;
        }
      }
    });
  }

  private void doSubmitTag(int paramInt)
  {
    if (!super.checkNetworkAndHint(true));
    while (true)
    {
      return;
      try
      {
        if (this.mSubmitTagTask != null)
        {
          if ((this.mSubmitTagTask.getStatus() != AsyncTask.Status.FINISHED) && (!this.mSubmitTagTask.isCancelled()))
            continue;
          this.mSubmitTagTask = null;
        }
        this.mSubmitTagTask = new SubmitTagTask(null);
        String[] arrayOfString = new String[2];
        arrayOfString[0] = this.mTagList.getJSONObject(paramInt).getString("name");
        arrayOfString[1] = this.mTagList.getJSONObject(paramInt).getString("id");
        this.mSubmitTagTask.execute(arrayOfString);
        this.m_ProgressDialog = ProgressDialog.show(getActivity(), "", getResources().getText(2131427618), true, true, new DialogInterface.OnCancelListener()
        {
          public void onCancel(DialogInterface paramDialogInterface)
          {
            if (RepostVoteFragment.this.mSubmitTagTask != null)
            {
              RepostEngine.getInstance().cancel();
              RepostVoteFragment.this.mSubmitTagTask.cancel(true);
              RepostVoteFragment.this.mSubmitTagTask = null;
            }
          }
        });
        return;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
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

  private void initVoteBarImage()
  {
    this.mVoteBarImage[0] = 2130839289;
    this.mVoteBarImage[1] = 2130839290;
    this.mVoteBarImage[2] = 2130839291;
    this.mVoteBarImage[3] = 2130839292;
    this.mVoteBarImage[4] = 2130839293;
    this.mVoteBarImage[5] = 2130839294;
    this.mVoteBarImage[6] = 2130839295;
    this.mVoteBarImage[7] = 2130839296;
    this.mVoteBarImage[8] = 2130839297;
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

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt2 == -1) && (paramInt1 == 6))
    {
      modifyDataModelAfterSubmitTagSucceed(paramIntent.getStringExtra("content"), true);
      constructViews();
      doRepost();
    }
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

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    if ((paramConfiguration.orientation == 2) || (paramConfiguration.orientation == 1))
      constructTagListView();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (CrashRecoverUtil.isCrashBefore())
    {
      CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
      IntentUtil.returnToDesktop(getActivity());
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903326, paramViewGroup, false);
  }

  public void onDestroy()
  {
    this.mTimer.cancel();
    if (this.m_ProgressDialog != null)
    {
      this.m_ProgressDialog.dismiss();
      this.m_ProgressDialog = null;
    }
    cancelTask(this.mRepostTask);
    cancelTask(this.mRepostVoteTask);
    cancelTask(this.mSubmitTagTask);
    super.onDestroy();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mTagOnClickListener = new TagOnClickListener(null);
    this.mTagOnTouchListener = new TagOnTouchListener(null);
    updateData();
    setTitleBar();
    if (this.mMode == 20)
      initVoteBarImage();
    if (this.mMode == 21)
      initTagBgColor();
    constructViews();
    if (this.mMode == 20)
      this.mTimer.scheduleAtFixedRate(this.mTimerTask, 500L, 100L);
    findViewById(2131363599).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent(RepostVoteFragment.this.getActivity(), InputFragment.class);
        Bundle localBundle = new Bundle();
        String str1 = RepostModel.getInstance().getRepostSurpid();
        String str2 = RepostModel.getInstance().getRepostSuid();
        localBundle.putInt("mode", 6);
        localBundle.putString("surpid", str1);
        localBundle.putString("suid", str2);
        localIntent.putExtras(localBundle);
        RepostVoteFragment.this.startActivityForResult(localIntent, 6);
      }
    });
    updateAnswerListProgressively();
  }

  protected void setTitleBar()
  {
    ImageView localImageView = (ImageView)findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        RepostVoteFragment.this.finish();
      }
    });
    ((ImageView)findViewById(2131362928)).setVisibility(8);
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setText(getResources().getString(2131427614));
    localTextView.setVisibility(0);
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
        Boolean localBoolean = Boolean.valueOf(RepostEngine.getInstance().doRepost(RepostVoteFragment.this, RepostModel.getInstance().getRepostId(), RepostModel.getInstance().getRepostFuid()));
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
        RepostVoteFragment.this.finish();
        return;
      }
      try
      {
        if (RepostVoteFragment.this.m_ProgressDialog != null)
        {
          RepostVoteFragment.this.m_ProgressDialog.dismiss();
          RepostVoteFragment.this.m_ProgressDialog = null;
        }
        if (paramBoolean.booleanValue())
        {
          RepostModel.getInstance().setNewflag(1);
          Toast.makeText(RepostVoteFragment.this.getActivity(), RepostVoteFragment.this.getResources().getString(2131427617), 0).show();
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("RepostVoteActivity", "onPostExecute", localException);
        return;
      }
      Toast.makeText(RepostVoteFragment.this.getActivity(), 2131427604, 0).show();
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
        if (RepostEngine.getInstance().doRepostVote(RepostVoteFragment.this.getActivity().getApplicationContext(), RepostModel.getInstance().getRepostUrpid(), RepostModel.getInstance().getRepostVoteuid(), RepostModel.getInstance().getRepostSuid(), RepostModel.getInstance().getRepostSurpid(), str))
          return Boolean.valueOf(RepostEngine.getInstance().doRepost(RepostVoteFragment.this, RepostModel.getInstance().getRepostId(), RepostModel.getInstance().getRepostFuid()));
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
        if (RepostVoteFragment.this.m_ProgressDialog != null)
        {
          RepostVoteFragment.this.m_ProgressDialog.dismiss();
          RepostVoteFragment.this.m_ProgressDialog = null;
        }
        if (paramBoolean.booleanValue())
        {
          RepostVoteFragment.this.modifyDataModelAfterVoteSucceed(this.mAnswernum);
          RepostVoteFragment.this.constructViews();
          RepostVoteFragment.this.updateAnswerListProgressively();
          RepostModel.getInstance().setNewflag(1);
          Toast.makeText(RepostVoteFragment.this.getActivity(), RepostVoteFragment.this.getResources().getString(2131427617), 0).show();
          return;
        }
        if (!TextUtils.isEmpty(RepostEngine.getInstance().getLastError()))
        {
          Toast.makeText(RepostVoteFragment.this.getActivity(), RepostEngine.getInstance().getLastError(), 0).show();
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("RepostVoteActivity", "onPostExecute", localException);
        return;
      }
      Toast.makeText(RepostVoteFragment.this.getActivity(), 2131427609, 0).show();
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
      try
      {
        if (RepostEngine.getInstance().doSubmitRepostTag(RepostVoteFragment.this.getActivity().getApplicationContext(), this.tagId, "", RepostModel.getInstance().getRepostSurpid(), RepostModel.getInstance().getRepostSuid()))
          return Boolean.valueOf(RepostEngine.getInstance().doRepost(RepostVoteFragment.this, RepostModel.getInstance().getRepostId(), RepostModel.getInstance().getRepostFuid()));
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
      if (RepostVoteFragment.this.m_ProgressDialog != null)
      {
        RepostVoteFragment.this.m_ProgressDialog.dismiss();
        RepostVoteFragment.this.m_ProgressDialog = null;
      }
      try
      {
        if (paramBoolean.booleanValue())
        {
          RepostVoteFragment.this.modifyDataModelAfterSubmitTagSucceed(this.tagName, false);
          RepostVoteFragment.this.constructViews();
          RepostModel.getInstance().setNewflag(1);
          Toast.makeText(RepostVoteFragment.this.getActivity(), RepostVoteFragment.this.getResources().getString(2131427617), 0).show();
          return;
        }
        if (!TextUtils.isEmpty(RepostEngine.getInstance().getLastError()))
        {
          Toast.makeText(RepostVoteFragment.this.getActivity(), RepostEngine.getInstance().getLastError(), 0).show();
          return;
        }
      }
      catch (Resources.NotFoundException localNotFoundException)
      {
        KXLog.e("RepostVoteActivity", "onPostExecute", localNotFoundException);
        return;
      }
      Toast.makeText(RepostVoteFragment.this.getActivity(), 2131427609, 0).show();
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
      RepostVoteFragment.this.doSubmitTag(localInteger.intValue());
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
        TextView localTextView2 = (TextView)RepostVoteFragment.this.findViewById(2131363564);
        localTextView2.setTextColor(localKXTagWidget.getmBgColor());
        localTextView2.setText(localCharSequence);
        localKXTagWidget.setTextColor(localKXTagWidget.getmPressedTextColor());
        localKXTagWidget.setBackgroundColor(localKXTagWidget.getmPressedBgColor());
        continue;
        String str = StringUtil.replaceTokenWith(RepostVoteFragment.this.getResources().getString(2131427612), "*", String.valueOf(RepostVoteFragment.this.mRpNum));
        TextView localTextView1 = (TextView)RepostVoteFragment.this.findViewById(2131363564);
        localTextView1.setTextColor(-7829368);
        localTextView1.setText(str);
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
      RepostVoteFragment.this.doRepostVote(this.mAnswernum);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.RepostVoteFragment
 * JD-Core Version:    0.6.0
 */