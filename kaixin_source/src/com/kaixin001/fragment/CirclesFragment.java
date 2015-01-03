package com.kaixin001.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.OnViewMoreClickListener;
import com.kaixin001.adapter.CirclesAdapter;
import com.kaixin001.engine.CirclesEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.model.CircleModel;
import com.kaixin001.model.CircleModel.CircleItem;
import com.kaixin001.model.User;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXUBLog;
import java.util.ArrayList;

public class CirclesFragment extends BaseFragment
  implements View.OnClickListener, AdapterView.OnItemClickListener, OnViewMoreClickListener
{
  private static final int FETCH_NUM = 100;
  public static final int SEARCH_BY_ACOUNT_READY = 34;
  private static final int STATE_DOWNLOADING = 1;
  private static final int STATE_FINSHED = 2;
  private static final int STATE_INITIAL = 0;
  private static final int TASK_FOR_LOAD = 1;
  private static final int TASK_FOR_PRELOAD;
  private int activityState = 0;
  private ImageView btnLeft;
  private ImageView btnRight;
  private ArrayList<CircleModel.CircleItem> circleList = new ArrayList();
  private ListView circleListView;
  private CirclesAdapter circlesAdapter;
  private GetCirclesTask getDataTask;
  private LinearLayout llytWait;
  private ProgressBar rightProBar;
  private TextView tvNoCircle;

  private void getFirstPageCircles()
  {
    this.getDataTask = new GetCirclesTask(null);
    GetCirclesTask localGetCirclesTask = this.getDataTask;
    Integer[] arrayOfInteger = new Integer[3];
    arrayOfInteger[0] = Integer.valueOf(1);
    arrayOfInteger[1] = Integer.valueOf(0);
    arrayOfInteger[2] = Integer.valueOf(100);
    localGetCirclesTask.execute(arrayOfInteger);
  }

  private ArrayList<CircleModel.CircleItem> getModelData()
  {
    return CircleModel.getInstance().getCircles();
  }

  private int getModelTotal()
  {
    return CircleModel.getInstance().total;
  }

  private void getNextPageCircles(int paramInt)
  {
    int i = -1 + this.circleList.size();
    if ((this.getDataTask != null) && (this.getDataTask.getStatus() != AsyncTask.Status.FINISHED) && (this.getDataTask.start == i) && (this.getDataTask.taskPurpose == 0))
    {
      this.getDataTask.taskPurpose = 1;
      return;
    }
    this.getDataTask = new GetCirclesTask(null);
    GetCirclesTask localGetCirclesTask = this.getDataTask;
    Integer[] arrayOfInteger = new Integer[3];
    arrayOfInteger[0] = Integer.valueOf(paramInt);
    arrayOfInteger[1] = Integer.valueOf(i);
    arrayOfInteger[2] = Integer.valueOf(100);
    localGetCirclesTask.execute(arrayOfInteger);
  }

  private void initTitle(View paramView)
  {
    this.btnLeft = ((ImageView)paramView.findViewById(2131362914));
    this.btnLeft.setOnClickListener(this);
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    localTextView.setVisibility(0);
    localTextView.setText(getResources().getText(2131427402));
    this.btnRight = ((ImageView)paramView.findViewById(2131362928));
    this.btnRight.setImageResource(2130838834);
    this.btnRight.setOnClickListener(this);
    this.rightProBar = ((ProgressBar)paramView.findViewById(2131362929));
  }

  private void onDownloading(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.btnRight.setVisibility(8);
      this.rightProBar.setVisibility(0);
      return;
    }
    this.btnRight.setVisibility(0);
    this.rightProBar.setVisibility(8);
  }

  private void pauseActivity()
  {
    if (this.activityState == 1)
      this.getDataTask.cancel(true);
  }

  private void resumeActivity()
  {
    if (this.activityState == 0)
    {
      updateActivityState(1);
      stateInitOnCreate();
      getFirstPageCircles();
    }
    do
    {
      return;
      if (this.activityState != 1)
        continue;
      updateActivityState(2);
      setStateOnFetchFinished(false);
      return;
    }
    while (this.activityState != 2);
    setStateOnFetchFinished(true);
  }

  private void setStateOnFetchFinished(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      if (this.circleList.size() == 0)
      {
        this.tvNoCircle.setText(2131428074);
        this.tvNoCircle.setVisibility(0);
        this.circleListView.setVisibility(8);
        return;
      }
      this.tvNoCircle.setVisibility(8);
      this.circleListView.setVisibility(0);
      return;
    }
    if (this.circleList.size() == 0)
    {
      this.tvNoCircle.setText(null);
      this.tvNoCircle.setVisibility(0);
      this.circleListView.setVisibility(8);
      return;
    }
    this.tvNoCircle.setVisibility(8);
    this.circleListView.setVisibility(0);
  }

  private void stateInitOnCreate()
  {
    ArrayList localArrayList = CirclesEngine.getInstance().loadCache(getActivity(), User.getInstance().getUID());
    int i;
    if ((localArrayList != null) && (localArrayList.size() > 0))
    {
      i = 1;
      if (i == 0)
        break label108;
      this.circleList.clear();
      this.circleList.addAll(localArrayList);
      this.circlesAdapter.setTotalItem(this.circleList.size());
      this.circlesAdapter.notifyDataSetChanged();
      this.tvNoCircle.setVisibility(8);
      this.circleListView.setVisibility(0);
      this.llytWait.setVisibility(8);
    }
    while (true)
    {
      onDownloading(true);
      return;
      i = 0;
      break;
      label108: this.tvNoCircle.setVisibility(8);
      this.circleListView.setVisibility(8);
      this.llytWait.setVisibility(0);
    }
  }

  private void updateActivityState(int paramInt)
  {
    this.activityState = paramInt;
  }

  private void updateDataList()
  {
    ArrayList localArrayList = getModelData();
    int i = getModelTotal();
    this.circleList.clear();
    if (localArrayList != null)
      this.circleList.addAll(localArrayList);
    if (this.circleList.size() < i);
    for (int j = 1; ; j = 0)
    {
      if (j != 0)
      {
        CircleModel.CircleItem localCircleItem = new CircleModel.CircleItem();
        localCircleItem.gid = null;
        this.circleList.add(localCircleItem);
        getNextPageCircles(0);
      }
      this.circlesAdapter.setTotalItem(i);
      this.circlesAdapter.notifyDataSetChanged();
      return;
    }
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    boolean bool;
    String str;
    int i;
    if ((paramInt2 == -1) && (paramInt1 == 410))
    {
      Bundle localBundle = paramIntent.getExtras();
      if (localBundle != null)
      {
        bool = localBundle.getBoolean("view_latest_news");
        str = localBundle.getString("gid");
        i = this.circleList.size();
      }
    }
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return;
      CircleModel.CircleItem localCircleItem = (CircleModel.CircleItem)this.circleList.get(j);
      if (!str.equals(localCircleItem.gid))
        continue;
      if (bool);
      for (int k = 0; ; k = localCircleItem.hasnews)
      {
        localCircleItem.hasnews = k;
        this.circlesAdapter.notifyDataSetChanged();
        return;
      }
    }
  }

  public void onClick(View paramView)
  {
    if (paramView.getId() == 2131362914)
      if (super.isMenuListVisibleInMain())
        super.showSubActivityInMain();
    do
    {
      return;
      super.showMenuListInMain();
      return;
    }
    while (!paramView.equals(this.btnRight));
    onDownloading(true);
    getFirstPageCircles();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    getActivity().getWindow().setFlags(3, 3);
    KXUBLog.log("homepage_circle");
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903081, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if ((this.getDataTask != null) && (this.getDataTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.getDataTask.cancel(true);
      if (CirclesEngine.getInstance() != null)
        CirclesEngine.getInstance().cancel();
    }
    super.onDestroy();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    CircleModel.CircleItem localCircleItem = (CircleModel.CircleItem)this.circleList.get(paramInt);
    IntentUtil.showCircleMainFragment(getActivity(), this, localCircleItem.gid, localCircleItem.gname, localCircleItem.type);
  }

  public void onPause()
  {
    super.onPause();
    pauseActivity();
  }

  public void onResume()
  {
    super.onResume();
    resumeActivity();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.circleListView = ((ListView)paramView.findViewById(2131361841));
    this.circlesAdapter = new CirclesAdapter(this, 2130903074, this.circleList);
    this.circleListView.setAdapter(this.circlesAdapter);
    this.circleListView.setOnItemClickListener(this);
    this.tvNoCircle = ((TextView)paramView.findViewById(2131362090));
    this.llytWait = ((LinearLayout)paramView.findViewById(2131361817));
    initTitle(paramView);
  }

  public void onViewMoreClick()
  {
    if (getModelData().size() > this.circleList.size());
    for (int i = 1; i != 0; i = 0)
    {
      updateDataList();
      return;
    }
    this.circlesAdapter.showLoadingFooter(true);
    onDownloading(true);
    getNextPageCircles(1);
  }

  private class GetCirclesTask extends KXFragment.KXAsyncTask<Integer, Void, Integer>
  {
    private int number;
    private int start;
    public int taskPurpose;

    private GetCirclesTask()
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
        Integer localInteger = Integer.valueOf(CirclesEngine.getInstance().doGetCircleList(CirclesFragment.this.getActivity().getApplicationContext(), this.start, this.number));
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if (this.taskPurpose == 0);
      do
      {
        return;
        CirclesFragment.this.circlesAdapter.showLoadingFooter(false);
        CirclesFragment.this.onDownloading(false);
        CirclesFragment.this.llytWait.setVisibility(8);
        if ((paramInteger == null) || (paramInteger.intValue() != 1))
        {
          Toast.makeText(CirclesFragment.this.getActivity().getApplicationContext(), 2131427374, 0).show();
          CirclesFragment.this.setStateOnFetchFinished(false);
          return;
        }
        CirclesFragment.this.updateDataList();
        CirclesFragment.this.setStateOnFetchFinished(true);
      }
      while (this.start != 0);
      CirclesFragment.this.circleListView.setSelection(0);
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
 * Qualified Name:     com.kaixin001.fragment.CirclesFragment
 * JD-Core Version:    0.6.0
 */