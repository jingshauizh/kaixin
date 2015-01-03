package com.kaixin001.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.OnViewMoreClickListener;
import com.kaixin001.adapter.ActivityAwardsAdapter;
import com.kaixin001.businesslogic.ShowMenuDialog;
import com.kaixin001.engine.ActivityAwardsEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.item.ActivityAwardItem;
import com.kaixin001.model.ActivityAwardModel;
import com.kaixin001.model.KaixinModelTemplate;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.view.KXListView;
import com.kaixin001.view.PullToRefreshView;
import com.kaixin001.view.PullToRefreshView.PullToRefreshListener;
import java.util.ArrayList;

public class ActivityAwardsFragment extends BaseFragment
  implements View.OnClickListener, OnViewMoreClickListener, PullToRefreshView.PullToRefreshListener
{
  private static final int FETCH_NUM = 50;
  public static final int PHOTO_NUM = 20;
  private static final int STATE_DOWNLOADING = 1;
  private static final int STATE_FINSHED = 2;
  private static final int STATE_INITIAL = 0;
  private static final String TAG = "ActivityAwardsActivity";
  private static final int TASK_FOR_LOAD = 1;
  private static final int TASK_FOR_PRELOAD;
  private ActivityAwardsAdapter adapterAward;
  private final ArrayList<ActivityAwardItem> awardList = new ArrayList();
  private ImageView btnLeft;
  private ImageView btnRefresh;
  private TextView centerText;
  int currentState;
  private GetDataTask getDataTask;
  int listIndex;
  private LinearLayout llytWait;
  private KXListView lstvAwards;
  Dialog mAlertDialog;
  private PullToRefreshView mPullView = null;
  private ShowMenuDialog menuUtil;
  ActivityAwardModel model = new ActivityAwardModel();
  int privState;
  ProgressDialog progressDialog;
  private ProgressBar rightProBar;
  private TextView tvNodata;
  private TextView tvWaiting;

  private void finishActivity()
  {
    finish();
  }

  private int getActivityState()
  {
    return this.currentState;
  }

  private void getFirstPageData()
  {
    onDownloading(true);
    this.getDataTask = new GetDataTask(null);
    GetDataTask localGetDataTask = this.getDataTask;
    Integer[] arrayOfInteger = new Integer[3];
    arrayOfInteger[0] = Integer.valueOf(1);
    arrayOfInteger[1] = Integer.valueOf(0);
    arrayOfInteger[2] = Integer.valueOf(50);
    localGetDataTask.execute(arrayOfInteger);
  }

  private void getNextPageData(int paramInt)
  {
    int i = -1 + this.awardList.size();
    if ((this.getDataTask != null) && (this.getDataTask.getStatus() != AsyncTask.Status.FINISHED) && (this.getDataTask.start == i) && (this.getDataTask.taskPurpose == 0))
      this.getDataTask.taskPurpose = 1;
    while (true)
    {
      if (this.getDataTask.taskPurpose == 1)
        onDownloading(true);
      return;
      this.getDataTask = new GetDataTask(null);
      GetDataTask localGetDataTask = this.getDataTask;
      Integer[] arrayOfInteger = new Integer[3];
      arrayOfInteger[0] = Integer.valueOf(paramInt);
      arrayOfInteger[1] = Integer.valueOf(i);
      arrayOfInteger[2] = Integer.valueOf(50);
      localGetDataTask.execute(arrayOfInteger);
    }
  }

  private void initTitle()
  {
    this.btnLeft = ((ImageView)findViewById(2131362914));
    this.btnLeft.setOnClickListener(this);
    ImageView localImageView = (ImageView)findViewById(2131362919);
    this.centerText = ((TextView)findViewById(2131362920));
    this.centerText.setVisibility(0);
    this.centerText.setText(2131428260);
    localImageView.setVisibility(8);
    this.btnRefresh = ((ImageView)findViewById(2131362928));
    this.btnRefresh.setImageResource(2130838834);
    this.btnRefresh.setOnClickListener(this);
    this.rightProBar = ((ProgressBar)findViewById(2131362929));
    this.btnRefresh.setVisibility(0);
  }

  private void onDownloading(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.btnRefresh.setVisibility(8);
      this.rightProBar.setVisibility(0);
      return;
    }
    this.btnRefresh.setVisibility(0);
    this.rightProBar.setVisibility(8);
  }

  private void refresh()
  {
    setActivityState(1);
    stateInitOnCreate();
    getFirstPageData();
  }

  private void resumeActivity()
  {
    this.lstvAwards.setSelection(this.listIndex);
    if (getActivityState() == 0)
    {
      setActivityState(1);
      stateInitOnCreate();
      getFirstPageData();
    }
    do
      return;
    while (getActivityState() != 2);
    setStateOnFetchFinished(true);
  }

  private void setActivityState(int paramInt)
  {
    this.privState = this.currentState;
    this.currentState = paramInt;
  }

  private void setStateOnFetchFinished(boolean paramBoolean)
  {
    showLoadingFooter(false);
    onDownloading(false);
    this.llytWait.setVisibility(8);
    int i = this.awardList.size();
    if (paramBoolean)
    {
      setActivityState(2);
      this.currentState = 2;
      if (i == 0)
      {
        this.tvNodata.setVisibility(0);
        this.tvNodata.setText(2131428261);
        this.lstvAwards.setVisibility(8);
        return;
      }
      this.tvNodata.setVisibility(8);
      this.adapterAward.notifyDataSetChanged();
      this.lstvAwards.setVisibility(0);
      return;
    }
    this.currentState = this.privState;
    if (i == 0)
    {
      this.tvNodata.setVisibility(8);
      this.lstvAwards.setVisibility(8);
      return;
    }
    this.tvNodata.setVisibility(8);
    this.lstvAwards.setVisibility(0);
  }

  private void stateInitOnCreate()
  {
    if (this.awardList.size() > 0);
    for (int i = 1; i != 0; i = 0)
    {
      this.tvNodata.setVisibility(8);
      this.lstvAwards.setVisibility(0);
      this.llytWait.setVisibility(8);
      return;
    }
    this.tvNodata.setVisibility(8);
    this.lstvAwards.setVisibility(8);
    this.tvWaiting.setText(2131427599);
    this.llytWait.setVisibility(0);
  }

  private void stopActivity()
  {
    this.listIndex = this.lstvAwards.getFirstVisiblePosition();
    if (getActivityState() == 1)
    {
      setActivityState(this.privState);
      this.getDataTask.cancel(true);
      return;
    }
    getActivityState();
  }

  private void updateDataList()
  {
    ArrayList localArrayList = this.model.awards.getItemList();
    this.awardList.clear();
    int i;
    if (localArrayList == null)
    {
      i = 0;
      if (i > 0)
      {
        this.awardList.addAll(localArrayList);
        this.lstvAwards.setVisibility(0);
      }
      if (this.awardList.size() >= this.model.awards.total)
        break label118;
    }
    label118: for (int j = 1; ; j = 0)
    {
      if (j != 0)
      {
        ActivityAwardItem localActivityAwardItem = new ActivityAwardItem();
        localActivityAwardItem.type = -2;
        this.awardList.add(localActivityAwardItem);
        getNextPageData(0);
      }
      this.adapterAward.notifyDataSetChanged();
      return;
      i = localArrayList.size();
      break;
    }
  }

  public void deleteAward(String paramString)
  {
    if (!checkNetworkAndHint(true))
    {
      KXLog.d("ActivityAwardsActivity", "delete award failed for no network: " + paramString);
      return;
    }
    if ((this.mAlertDialog != null) && (this.mAlertDialog.isShowing()))
    {
      this.mAlertDialog.dismiss();
      this.mAlertDialog = null;
    }
    this.mAlertDialog = DialogUtil.showMsgDlgLite(getActivity(), 2131428262, new DialogInterface.OnClickListener(paramString)
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        ActivityAwardsFragment.DeleteAwardTask localDeleteAwardTask = new ActivityAwardsFragment.DeleteAwardTask(ActivityAwardsFragment.this, null);
        String[] arrayOfString = new String[1];
        arrayOfString[0] = this.val$awardId;
        localDeleteAwardTask.execute(arrayOfString);
        DialogUtil.dismissDialog(ActivityAwardsFragment.this.progressDialog);
        ActivityAwardsFragment.this.progressDialog = ProgressDialog.show(ActivityAwardsFragment.this.getActivity(), "", ActivityAwardsFragment.this.getResources().getText(2131427618), true, true, new DialogInterface.OnCancelListener(localDeleteAwardTask)
        {
          public void onCancel(DialogInterface paramDialogInterface)
          {
            ActivityAwardsFragment.this.progressDialog = null;
            this.val$deleteAwardTask.cancel(true);
          }
        });
      }
    });
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    return super.dispatchTouchEvent(paramMotionEvent);
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 2);
  }

  public ShowMenuDialog getShowMenuDialog()
  {
    if (this.menuUtil == null)
      this.menuUtil = new ShowMenuDialog(this);
    return this.menuUtil;
  }

  public void onClick(View paramView)
  {
    if (paramView.getId() == 2131362914)
      finishActivity();
    do
      return;
    while (paramView != this.btnRefresh);
    refresh();
  }

  public void onCreate(Bundle paramBundle)
  {
    this.currentState = 0;
    this.privState = 0;
    super.onCreate(paramBundle);
    getActivity().getWindow().setFlags(3, 3);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903044, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if ((this.getDataTask != null) && (this.getDataTask.getStatus() != AsyncTask.Status.FINISHED))
      this.getDataTask.cancel(true);
    DialogUtil.dismissDialog(this.progressDialog);
    this.model = null;
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
    resumeActivity();
  }

  public void onStop()
  {
    super.onStop();
    stopActivity();
  }

  public void onUpdate()
  {
    refresh();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.lstvAwards = ((KXListView)findViewById(2131361815));
    this.adapterAward = new ActivityAwardsAdapter(this, this.awardList);
    this.lstvAwards.setAdapter(this.adapterAward);
    this.tvNodata = ((TextView)findViewById(2131361816));
    this.llytWait = ((LinearLayout)findViewById(2131361817));
    this.tvWaiting = ((TextView)findViewById(2131361819));
    initTitle();
    this.mPullView = ((PullToRefreshView)findViewById(2131361814));
    this.mPullView.setPullToRefreshListener(this);
  }

  public void onViewMoreClick()
  {
    if (this.model.awards.getItemList().size() > -1 + this.awardList.size());
    for (int i = 1; i != 0; i = 0)
    {
      updateDataList();
      return;
    }
    showLoadingFooter(true);
    onDownloading(true);
    getNextPageData(1);
  }

  public void showLoadingFooter(boolean paramBoolean)
  {
    this.adapterAward.showLoadingFooter(paramBoolean);
  }

  public void useAward(String paramString)
  {
    if (!checkNetworkAndHint(true))
    {
      KXLog.d("ActivityAwardsActivity", "use award failed for no network: " + paramString);
      return;
    }
    if ((this.mAlertDialog != null) && (this.mAlertDialog.isShowing()))
    {
      this.mAlertDialog.dismiss();
      this.mAlertDialog = null;
    }
    this.mAlertDialog = DialogUtil.showMsgDlgLite(getActivity(), 2131428263, new DialogInterface.OnClickListener(paramString)
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        ActivityAwardsFragment.UseAwardTask localUseAwardTask = new ActivityAwardsFragment.UseAwardTask(ActivityAwardsFragment.this, null);
        String[] arrayOfString = new String[1];
        arrayOfString[0] = this.val$awardId;
        localUseAwardTask.execute(arrayOfString);
        DialogUtil.dismissDialog(ActivityAwardsFragment.this.progressDialog);
        ActivityAwardsFragment.this.progressDialog = ProgressDialog.show(ActivityAwardsFragment.this.getActivity(), "", ActivityAwardsFragment.this.getResources().getText(2131427618), true, true, new DialogInterface.OnCancelListener(localUseAwardTask)
        {
          public void onCancel(DialogInterface paramDialogInterface)
          {
            ActivityAwardsFragment.this.progressDialog = null;
            this.val$useAwardTask.cancel(true);
          }
        });
      }
    });
  }

  private class DeleteAwardTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    String awardId;

    private DeleteAwardTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      try
      {
        this.awardId = paramArrayOfString[0];
        Integer localInteger = Integer.valueOf(ActivityAwardsEngine.getInstance().doDeleteAwardRequest(ActivityAwardsFragment.this.getActivity().getApplicationContext(), ActivityAwardsFragment.this.model, this.awardId));
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      DialogUtil.dismissDialog(ActivityAwardsFragment.this.progressDialog);
      if ((paramInteger == null) || (paramInteger.intValue() != 1))
      {
        KXLog.d("ActivityAwardsActivity", "delete award failed: " + this.awardId);
        Toast.makeText(ActivityAwardsFragment.this.getActivity().getApplicationContext(), 2131428265, 0).show();
      }
      do
      {
        return;
        KXLog.d("ActivityAwardsActivity", "delete award success: " + this.awardId);
      }
      while (paramInteger.intValue() != 1);
      Toast.makeText(ActivityAwardsFragment.this.getActivity(), 2131428267, 0).show();
      ActivityAwardModel.deleteAwardById(ActivityAwardsFragment.this.model.awards.getItemList(), this.awardId);
      ActivityAwardsFragment.this.model.awards.total = (-1 + ActivityAwardsFragment.this.model.awards.total);
      ActivityAwardModel.deleteAwardById(ActivityAwardsFragment.this.awardList, this.awardId);
      int i = ActivityAwardsFragment.this.awardList.size();
      int j = ActivityAwardsFragment.this.model.awards.total;
      int k = 0;
      if (i < j)
        k = 1;
      if ((ActivityAwardsFragment.this.awardList.size() == 1) && (k != 0))
      {
        ActivityAwardsFragment.this.updateDataList();
        return;
      }
      if (ActivityAwardsFragment.this.awardList.size() == 0)
      {
        ActivityAwardsFragment.this.refresh();
        return;
      }
      ActivityAwardsFragment.this.adapterAward.notifyDataSetChanged();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class GetDataTask extends KXFragment.KXAsyncTask<Integer, Void, Integer>
  {
    private int number;
    private int start;
    public int taskPurpose;

    private GetDataTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Integer[] paramArrayOfInteger)
    {
      if ((paramArrayOfInteger == null) || (paramArrayOfInteger.length == 0) || (paramArrayOfInteger.length != 3))
        return null;
      this.taskPurpose = paramArrayOfInteger[0].intValue();
      this.start = paramArrayOfInteger[1].intValue();
      this.number = paramArrayOfInteger[2].intValue();
      try
      {
        Integer localInteger = Integer.valueOf(ActivityAwardsEngine.getInstance().doGetAwardsRequest(ActivityAwardsFragment.this.getActivity().getApplicationContext(), ActivityAwardsFragment.this.model, this.start, this.number));
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
      if (ActivityAwardsFragment.this.mPullView.isFrefrshing())
        ActivityAwardsFragment.this.mPullView.onRefreshComplete();
      if (this.taskPurpose == 0);
      do
      {
        return;
        if ((paramInteger == null) || (paramInteger.intValue() != 1))
        {
          Toast.makeText(ActivityAwardsFragment.this.getActivity().getApplicationContext(), 2131427374, 0).show();
          ActivityAwardsFragment.this.setStateOnFetchFinished(false);
          return;
        }
        ActivityAwardsFragment.this.updateDataList();
        ActivityAwardsFragment.this.setStateOnFetchFinished(true);
      }
      while (this.start != 0);
      ActivityAwardsFragment.this.lstvAwards.setSelection(0);
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class UseAwardTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    String awardId;

    private UseAwardTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      try
      {
        this.awardId = paramArrayOfString[0];
        Integer localInteger = Integer.valueOf(ActivityAwardsEngine.getInstance().doUseAwardRequest(ActivityAwardsFragment.this.getActivity().getApplicationContext(), ActivityAwardsFragment.this.model, this.awardId));
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      DialogUtil.dismissDialog(ActivityAwardsFragment.this.progressDialog);
      if ((paramInteger == null) || (paramInteger.intValue() != 1))
      {
        KXLog.d("ActivityAwardsActivity", "use award failed: " + this.awardId);
        Toast.makeText(ActivityAwardsFragment.this.getActivity().getApplicationContext(), 2131428264, 0).show();
      }
      do
        return;
      while (paramInteger.intValue() != 1);
      KXLog.d("ActivityAwardsActivity", "use award success: " + this.awardId);
      Toast.makeText(ActivityAwardsFragment.this.getActivity().getApplicationContext(), 2131428266, 0).show();
      ActivityAwardsFragment.this.model.useAwardById(this.awardId);
      ActivityAwardsFragment.this.adapterAward.notifyDataSetChanged();
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
 * Qualified Name:     com.kaixin001.fragment.ActivityAwardsFragment
 * JD-Core Version:    0.6.0
 */