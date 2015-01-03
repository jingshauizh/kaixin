package com.kaixin001.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.businesslogic.ShowVoteDetail;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.VoteEngine;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.model.User;
import com.kaixin001.model.VoteModel;
import com.kaixin001.model.VoteModel.Answer;
import com.kaixin001.model.VoteModel.Result;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.StringUtil;
import com.kaixin001.view.KXIntroView;
import com.kaixin001.view.KXIntroView.OnClickLinkListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class VoteDetailFragment extends BaseFragment
  implements KXIntroView.OnClickLinkListener
{
  private static final int VOTE_BAR_IMAGE_NUM = 9;
  private boolean isVoted = false;
  private ArrayList<VoteModel.Answer> mAnswerList = null;
  private String mEndTime = "";
  private String mFName = "";
  private VoteModel mModel = VoteModel.getInstance();
  private ArrayList<VoteModel.Result> mResultList = null;
  private HashMap<Integer, Integer> mSelectedAnswers = new HashMap();
  ShowVoteDetail mShowVoteDetailUtil = null;
  private String mStartTime = "";
  private Timer mTimer = new Timer();
  private int mTimerCycles = 0;
  private Handler mTimerHandler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      if (VoteDetailFragment.this.updateAnswerListProgressively())
        VoteDetailFragment.this.mTimer.cancel();
    }
  };
  private TimerTask mTimerTask = new TimerTask()
  {
    public void run()
    {
      if (VoteDetailFragment.this.isNeedReturn())
        return;
      Message localMessage = Message.obtain();
      localMessage.what = 1;
      VoteDetailFragment.this.mTimerHandler.sendMessage(localMessage);
    }
  };
  private String mTitle = "";
  private int[] mVoteBarImage = new int[9];
  private HashMap<String, View> mVoteControlMap = new HashMap();
  private String mVoteId = "";
  private String mVoteIntro = "";
  private String[] mVoteLimitList = null;
  private int mVoteLimitNum = 0;
  private int mVoteNum = 0;
  VoteSubmitTask mVoteSubmitTask = null;
  private String mVoteSummary = "";
  private ProgressDialog m_ProgressDialog = null;

  private void constructAllViews()
  {
    constructTitleViews();
    constructIntroViews();
    constructAnswerListViews();
    constructResultListViews();
    constructMiscViews();
    this.mTimer.scheduleAtFixedRate(this.mTimerTask, 500L, 100L);
  }

  private void constructAnswerListViews()
  {
    ((TextView)findViewById(2131364024)).setText(String.valueOf(this.mVoteNum));
    LinearLayout localLinearLayout = (LinearLayout)findViewById(2131364025);
    if (this.mAnswerList == null)
      localLinearLayout.setVisibility(8);
    while (true)
    {
      return;
      if (this.mAnswerList.size() == 0)
      {
        localLinearLayout.setVisibility(8);
        return;
      }
      localLinearLayout.removeAllViews();
      for (int i = 0; i < this.mAnswerList.size(); i++)
      {
        View localView = createOneAnswerView(i);
        if (this.isVoted)
          localView.setClickable(false);
        if (localView == null)
          continue;
        localLinearLayout.addView(localView);
        this.mVoteControlMap.put(String.valueOf(i), localView);
        if (i == -1 + this.mAnswerList.size())
          continue;
        localLinearLayout.addView(createOnedivider());
      }
    }
  }

  private void constructIntroViews()
  {
    TextView localTextView1 = (TextView)findViewById(2131364022);
    TextView localTextView2 = (TextView)findViewById(2131364023);
    if (this.mVoteIntro.length() > 0)
    {
      localTextView1.setVisibility(0);
      localTextView2.setVisibility(0);
      localTextView2.setText(this.mVoteIntro);
      return;
    }
    localTextView1.setVisibility(8);
    localTextView2.setVisibility(8);
  }

  private void constructMiscViews()
  {
    Button localButton = (Button)findViewById(2131364026);
    View localView = findViewById(2131364027);
    if (this.mEndTime.equals("已截止"))
    {
      localButton.setVisibility(8);
      localView.setVisibility(0);
    }
    while (true)
    {
      localButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          if (User.getInstance().GetLookAround())
          {
            FragmentActivity localFragmentActivity = VoteDetailFragment.this.getActivity();
            String[] arrayOfString = new String[2];
            arrayOfString[0] = VoteDetailFragment.this.getString(2131427338);
            arrayOfString[1] = VoteDetailFragment.this.getString(2131427339);
            DialogUtil.showSelectListDlg(localFragmentActivity, 2131427729, arrayOfString, new DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface paramDialogInterface, int paramInt)
              {
                switch (paramInt)
                {
                default:
                  return;
                case 0:
                  IntentUtil.returnToLogin(VoteDetailFragment.this.getActivity(), false);
                  return;
                case 1:
                }
                Bundle localBundle = new Bundle();
                localBundle.putInt("regist", 1);
                IntentUtil.returnToLogin(VoteDetailFragment.this.getActivity(), localBundle, false);
              }
            }
            , 1);
            return;
          }
          VoteDetailFragment.this.doVoteSubmit();
        }
      });
      return;
      localButton.setVisibility(0);
      localView.setVisibility(8);
    }
  }

  private void constructResultListViews()
  {
    TextView localTextView = (TextView)findViewById(2131364028);
    LinearLayout localLinearLayout = (LinearLayout)findViewById(2131364029);
    int i;
    if (this.mResultList == null)
    {
      i = 0;
      if (i != 0)
        break label59;
      localTextView.setVisibility(8);
      localLinearLayout.setVisibility(8);
    }
    while (true)
    {
      return;
      i = this.mResultList.size();
      break;
      label59: localLinearLayout.removeAllViews();
      localTextView.setVisibility(0);
      localLinearLayout.setVisibility(0);
      for (int j = 0; j < i; j++)
      {
        View localView = createOneResultView(j);
        if (localView == null)
          continue;
        localLinearLayout.addView(localView);
        if (j == i - 1)
          continue;
        localLinearLayout.addView(createOnedivider());
      }
    }
  }

  private void constructTitleViews()
  {
    TextView localTextView = (TextView)findViewById(2131364018);
    StringBuffer localStringBuffer;
    int i;
    if ((this.mVoteLimitList != null) && (this.mVoteLimitList.length > 0))
    {
      localStringBuffer = new StringBuffer(this.mTitle);
      localStringBuffer.append("<font color='#999999'>");
      localStringBuffer.append("(");
      i = 0;
      if (i >= this.mVoteLimitList.length)
      {
        localStringBuffer.append(")");
        localStringBuffer.append("</font>");
      }
    }
    for (String str = localStringBuffer.toString(); ; str = this.mTitle)
    {
      localTextView.setText(Html.fromHtml(str));
      ((TextView)findViewById(2131364019)).setText(this.mFName);
      ((TextView)findViewById(2131364020)).setText(this.mStartTime);
      ((TextView)findViewById(2131364021)).setText(this.mEndTime);
      return;
      localStringBuffer.append(this.mVoteLimitList[i]);
      if (i != -1 + this.mVoteLimitList.length)
        localStringBuffer.append(",");
      i++;
      break;
    }
  }

  private View createOneAnswerView(int paramInt)
  {
    View localView = getActivity().getLayoutInflater().inflate(2130903414, null);
    VoteModel.Answer localAnswer = (VoteModel.Answer)this.mAnswerList.get(paramInt);
    String str1 = localAnswer.getAnswer();
    int i = Integer.parseInt(localAnswer.getVotenum());
    int j = Integer.parseInt(localAnswer.getVotepercent());
    ((TextView)localView.findViewById(2131364011)).setText(str1);
    localView.findViewById(2131364013).setBackgroundResource(selectVoteBarImage(paramInt));
    String str2 = String.valueOf(i) + "(" + String.valueOf(j) + "%" + ")";
    ((TextView)localView.findViewById(2131364014)).setText(str2);
    if (this.mSelectedAnswers.containsKey(Integer.valueOf(paramInt)))
      localView.findViewById(2131364015).setVisibility(0);
    localView.setOnClickListener(new VoteControlOnClickListener(paramInt));
    return localView;
  }

  private View createOneResultView(int paramInt)
  {
    View localView = getActivity().getLayoutInflater().inflate(2130903416, null);
    VoteModel.Result localResult = (VoteModel.Result)this.mResultList.get(paramInt);
    String str1 = localResult.getAnswer();
    String str2 = localResult.getFname();
    String str3 = localResult.getUid();
    String str4 = localResult.getCtime();
    String str5 = " " + str4 + " " + getResources().getString(2131427631) + " " + str1;
    KXIntroView localKXIntroView = (KXIntroView)localView.findViewById(2131364030);
    ArrayList localArrayList = new ArrayList();
    KXLinkInfo localKXLinkInfo1 = new KXLinkInfo();
    localKXLinkInfo1.setContent(str2);
    localKXLinkInfo1.setType("0");
    localKXLinkInfo1.setId(str3);
    localArrayList.add(localKXLinkInfo1);
    KXLinkInfo localKXLinkInfo2 = new KXLinkInfo();
    localKXLinkInfo2.setContent(str5);
    localArrayList.add(localKXLinkInfo2);
    localKXIntroView.setTitleList(localArrayList);
    localKXIntroView.setOnClickLinkListener(this);
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

  private void doGetVote(String paramString)
  {
    getVoteShowUtil().showVoteDetail(paramString);
  }

  private void doVoteSubmit()
  {
    if ((this.mSelectedAnswers == null) || (this.mSelectedAnswers.isEmpty()));
    do
      return;
    while (!super.checkNetworkAndHint(true));
    Object[] arrayOfObject = this.mSelectedAnswers.keySet().toArray();
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; ; i++)
    {
      if (i >= arrayOfObject.length)
      {
        this.mVoteSubmitTask = new VoteSubmitTask(null);
        String[] arrayOfString = new String[1];
        arrayOfString[0] = localStringBuffer.toString();
        this.mVoteSubmitTask.execute(arrayOfString);
        this.m_ProgressDialog = ProgressDialog.show(getActivity(), "", getResources().getText(2131427618), true, true, new DialogInterface.OnCancelListener()
        {
          public void onCancel(DialogInterface paramDialogInterface)
          {
            if (VoteDetailFragment.this.mVoteSubmitTask != null)
            {
              VoteEngine.getInstance().cancel();
              VoteDetailFragment.this.mVoteSubmitTask.cancel(true);
              VoteDetailFragment.this.mVoteSubmitTask = null;
            }
          }
        });
        return;
      }
      localStringBuffer.append(1 + ((Integer)arrayOfObject[i]).intValue());
      if (i == -1 + arrayOfObject.length)
        continue;
      localStringBuffer.append(",");
    }
  }

  private void enableVoteButton(boolean paramBoolean)
  {
    Button localButton = (Button)findViewById(2131364026);
    if (localButton != null)
    {
      if (paramBoolean)
        localButton.setBackgroundResource(2130839283);
    }
    else
      return;
    localButton.setBackgroundResource(2130839287);
  }

  private ShowVoteDetail getVoteShowUtil()
  {
    if (this.mShowVoteDetailUtil == null)
      this.mShowVoteDetailUtil = new ShowVoteDetail(this)
      {
        protected void doFinishedHandle()
        {
          VoteDetailFragment.this.findViewById(2131364016).setVisibility(8);
        }

        protected void refreshGUI()
        {
          View localView1 = VoteDetailFragment.this.findViewById(2131364016);
          View localView2 = VoteDetailFragment.this.findViewById(2131364017);
          localView1.setVisibility(8);
          localView2.setVisibility(0);
          VoteDetailFragment.this.updateData();
          VoteDetailFragment.this.constructAllViews();
          Button localButton = (Button)VoteDetailFragment.this.findViewById(2131362928);
          if (VoteDetailFragment.this.mVoteSummary.length() > 0)
          {
            localButton.setVisibility(0);
            return;
          }
          localButton.setVisibility(8);
        }
      };
    return this.mShowVoteDetailUtil;
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

  private void modifyDataModelAfterVoteSucceed()
  {
    this.mVoteNum = (1 + this.mVoteNum);
    this.mModel.setVotenum(String.valueOf(this.mVoteNum));
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; ; i++)
    {
      if (i >= this.mAnswerList.size())
      {
        VoteModel.Result localResult = new VoteModel.Result();
        localResult.setAnswer(localStringBuffer.toString());
        localResult.setFname(User.getInstance().getRealName());
        localResult.setUid(User.getInstance().getUID());
        Date localDate = new Date();
        localResult.setCtime(new SimpleDateFormat("MM月dd日 HH:mm").format(localDate));
        this.mModel.getResultlist().add(0, localResult);
        return;
      }
      VoteModel.Answer localAnswer = (VoteModel.Answer)this.mAnswerList.get(i);
      int j = Integer.parseInt(localAnswer.getVotenum());
      Integer.parseInt(localAnswer.getVotepercent());
      if (this.mSelectedAnswers.containsKey(Integer.valueOf(i)))
      {
        j++;
        localStringBuffer.append("\"").append(localAnswer.getAnswer()).append("\" ");
      }
      int k = j * 100 / this.mVoteNum;
      localAnswer.setVotenum(String.valueOf(j));
      localAnswer.setVotepercent(String.valueOf(k));
    }
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
    if (this.mAnswerList.size() == 0)
      return i;
    this.mTimerCycles = (1 + this.mTimerCycles);
    int j = 10 * this.mTimerCycles;
    int k = 0;
    if (k >= this.mAnswerList.size())
      return i;
    View localView1 = (View)this.mVoteControlMap.get(String.valueOf(k));
    if (localView1 == null);
    while (true)
    {
      k++;
      break;
      int m = 170 * Integer.parseInt(((VoteModel.Answer)this.mAnswerList.get(k)).getVotepercent()) / 100;
      int n = Math.min(m, j);
      View localView2 = localView1.findViewById(2131364013);
      if (localView2.getWidth() >= m)
        continue;
      TableLayout.LayoutParams localLayoutParams = new TableLayout.LayoutParams();
      localLayoutParams.height = -1;
      localLayoutParams.width = n;
      localView2.setLayoutParams(localLayoutParams);
      i = 0;
    }
  }

  private void updateData()
  {
    if (this.mModel == null)
      return;
    this.mTitle = this.mModel.getTitle();
    this.mFName = this.mModel.getFname();
    this.mStartTime = this.mModel.getCtime();
    this.mEndTime = this.mModel.getEndtime();
    this.mVoteIntro = this.mModel.getIntro();
    this.mVoteNum = Integer.parseInt(this.mModel.getVotenum());
    this.mVoteSummary = this.mModel.getSummary();
    this.mAnswerList = this.mModel.getAnswerlist();
    this.mResultList = this.mModel.getResultlist();
    this.mVoteLimitList = this.mModel.getLimitlist();
    try
    {
      this.mVoteLimitNum = Integer.parseInt(this.mModel.getType());
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
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

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903415, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if ((this.mVoteSubmitTask != null) && (this.mVoteSubmitTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mVoteSubmitTask.cancel(true);
      VoteEngine.getInstance().cancel();
    }
    if (this.mModel != null)
      this.mModel.clear();
    super.onDestroy();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    User.getInstance().loadDataIfEmpty(getActivity().getApplicationContext());
    Object localObject = "";
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      String str = localBundle.getString("vid");
      if (str != null)
        localObject = str;
    }
    initVoteBarImage();
    View localView1 = findViewById(2131364016);
    View localView2 = findViewById(2131364017);
    if (localBundle == null)
    {
      localView1.setVisibility(8);
      localView2.setVisibility(0);
      updateData();
      constructAllViews();
    }
    while (true)
    {
      setTitleBar();
      return;
      if ((((String)localObject).length() > 0) && (this.mModel.getVoteId() != null) && (this.mModel.getVoteId().equals(localObject)))
      {
        localView1.setVisibility(8);
        localView2.setVisibility(0);
        updateData();
        constructAllViews();
        continue;
      }
      localView1.setVisibility(0);
      localView2.setVisibility(8);
      this.mVoteId = ((String)localObject);
      doGetVote(this.mVoteId);
    }
  }

  protected void setTitleBar()
  {
    ImageView localImageView1 = (ImageView)findViewById(2131362914);
    localImageView1.setVisibility(0);
    localImageView1.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        VoteDetailFragment.this.finish();
      }
    });
    ImageView localImageView2 = (ImageView)findViewById(2131362928);
    if ((this.mVoteSummary != null) && (this.mVoteSummary.length() > 0))
      localImageView2.setVisibility(0);
    while (true)
    {
      localImageView2.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          Intent localIntent = new Intent(VoteDetailFragment.this.getActivity(), VoteSummaryFragment.class);
          VoteDetailFragment.this.startFragment(localIntent, true, 1);
        }
      });
      ((ImageView)findViewById(2131362919)).setVisibility(8);
      TextView localTextView = (TextView)findViewById(2131362920);
      localTextView.setText(getResources().getString(2131427626));
      localTextView.setVisibility(0);
      localImageView2.setImageResource(2130839056);
      return;
      localImageView2.setVisibility(8);
    }
  }

  private class VoteControlOnClickListener
    implements View.OnClickListener
  {
    private int mAnsweIndex;

    public VoteControlOnClickListener(int arg2)
    {
      int i;
      this.mAnsweIndex = i;
    }

    public void onClick(View paramView)
    {
      View localView = paramView.findViewById(2131364015);
      if (VoteDetailFragment.this.mSelectedAnswers.containsKey(Integer.valueOf(this.mAnsweIndex)))
      {
        localView.setVisibility(4);
        VoteDetailFragment.this.mSelectedAnswers.remove(Integer.valueOf(this.mAnsweIndex));
        if (VoteDetailFragment.this.mSelectedAnswers.size() == 0)
          VoteDetailFragment.this.enableVoteButton(false);
        return;
      }
      if (VoteDetailFragment.this.mSelectedAnswers.size() == 0)
        VoteDetailFragment.this.enableVoteButton(true);
      if (VoteDetailFragment.this.mSelectedAnswers.size() < VoteDetailFragment.this.mVoteLimitNum)
      {
        localView.setVisibility(0);
        VoteDetailFragment.this.mSelectedAnswers.put(Integer.valueOf(this.mAnsweIndex), Integer.valueOf(1));
        return;
      }
      String str = StringUtil.replaceTokenWith(VoteDetailFragment.this.getResources().getString(2131427655), "*", String.valueOf(VoteDetailFragment.this.mVoteLimitNum));
      Toast.makeText(VoteDetailFragment.this.getActivity(), str, 0).show();
    }
  }

  private class VoteSubmitTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    private VoteSubmitTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      if (paramArrayOfString.length < 1)
        return Boolean.valueOf(false);
      try
      {
        VoteEngine.getInstance().mLastError = null;
        Boolean localBoolean = Boolean.valueOf(VoteEngine.getInstance().doVoteSubmit(VoteDetailFragment.this.getActivity().getApplicationContext(), VoteDetailFragment.this.mModel.getVoteId(), paramArrayOfString[0]));
        return localBoolean;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return Boolean.valueOf(false);
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      if (VoteDetailFragment.this.m_ProgressDialog != null)
        VoteDetailFragment.this.m_ProgressDialog.dismiss();
      try
      {
        if (paramBoolean.booleanValue())
        {
          VoteDetailFragment.this.isVoted = true;
          VoteDetailFragment.this.modifyDataModelAfterVoteSucceed();
          VoteDetailFragment.this.updateData();
          VoteDetailFragment.this.constructAnswerListViews();
          VoteDetailFragment.this.constructResultListViews();
          VoteDetailFragment.this.updateAnswerListProgressively();
          Toast.makeText(VoteDetailFragment.this.getActivity(), VoteDetailFragment.this.getResources().getString(2131427633), 0).show();
          return;
        }
        if (!TextUtils.isEmpty(VoteEngine.getInstance().getLastError()))
        {
          Toast.makeText(VoteDetailFragment.this.getActivity(), VoteEngine.getInstance().getLastError(), 0).show();
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("VoteDetailActivity", "onPostExecute", localException);
        return;
      }
      Toast.makeText(VoteDetailFragment.this.getActivity(), 2131427854, 0).show();
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
 * Qualified Name:     com.kaixin001.fragment.VoteDetailFragment
 * JD-Core Version:    0.6.0
 */