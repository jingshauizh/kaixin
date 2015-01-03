package com.kaixin001.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.kaixin001.engine.UserInfoEngine;
import com.kaixin001.model.UserMissionModel;
import com.kaixin001.model.UserMissionModel.LevelActivityModel;
import com.kaixin001.model.UserMissionModel.MissionItem;
import java.util.ArrayList;

public class UserMissionFragment extends Fragment
{
  private UserMissionModel.LevelActivityModel activityData;
  private MissionAdapter adapter;
  private ArrayList<UserMissionModel.MissionItem> dataList = new ArrayList();
  private String mFname;
  private String mFuid;
  private TextView tvActivityContent;
  private TextView tvActivityTime;
  private TextView tvActivityTitle;
  private View userActivityLayout;
  private View waitLayout;

  private void initView()
  {
    this.waitLayout = getView().findViewById(2131363887);
    this.userActivityLayout = getView().findViewById(2131363882);
    this.tvActivityTitle = ((TextView)getView().findViewById(2131363883));
    this.tvActivityContent = ((TextView)getView().findViewById(2131363884));
    this.tvActivityTime = ((TextView)getView().findViewById(2131363885));
    ListView localListView = (ListView)getView().findViewById(2131363886);
    this.adapter = new MissionAdapter(getActivity(), this.dataList);
    localListView.setAdapter(this.adapter);
  }

  private void loadDataInBackground()
  {
    this.waitLayout.setVisibility(0);
    new GetMissionTask().execute(new Void[0]);
  }

  private void updateActivityData()
  {
    ArrayList localArrayList = UserMissionModel.getInstance().getActivityList(this.mFuid);
    if ((localArrayList != null) && (localArrayList.size() == 0))
      this.userActivityLayout.setVisibility(8);
    do
    {
      return;
      if ((localArrayList == null) || (localArrayList.size() <= 0))
        continue;
      this.userActivityLayout.setVisibility(0);
      this.activityData = ((UserMissionModel.LevelActivityModel)localArrayList.get(0));
    }
    while (this.activityData == null);
    this.tvActivityTitle.setText(this.activityData.title);
    this.tvActivityContent.setText(this.activityData.content);
    this.tvActivityTime.setText("活动时间：" + this.activityData.startTime + " ~ " + this.activityData.endTime);
  }

  private void updateListData()
  {
    this.dataList.clear();
    ArrayList localArrayList = UserMissionModel.getInstance().getMissionList(this.mFuid);
    if ((localArrayList != null) && (localArrayList.size() > 0))
      this.dataList.addAll(localArrayList);
    this.adapter.notifyDataSetChanged();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      localBundle.getString("from");
      String str1 = localBundle.getString("fuid");
      if (str1 != null)
        this.mFuid = str1;
      String str2 = localBundle.getString("fname");
      if (str2 != null)
        this.mFname = str2;
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903394, paramViewGroup, false);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    initView();
    updateActivityData();
    updateListData();
    loadDataInBackground();
  }

  class DataHolder
  {
    private View gradeItemLayout;
    private View gradeTitleLayout;
    private ImageView ivComment;
    private ImageView ivDivideLine;
    private View missionItemLayout;
    private View missionTitleLayout;
    private TextView tvComment;
    private TextView tvCondition;
    private TextView tvExp;
    private TextView tvGradeTitle;
    private TextView tvMissionTitle;
    private TextView tvNeedGrade;
    private TextView tvProfession;
    private TextView tvUplevel;

    DataHolder()
    {
    }

    public void initView(View paramView)
    {
      this.tvMissionTitle = ((TextView)paramView.findViewById(2131363888));
      this.missionTitleLayout = paramView.findViewById(2131363889);
      this.missionItemLayout = paramView.findViewById(2131363894);
      this.tvCondition = ((TextView)paramView.findViewById(2131363895));
      this.tvExp = ((TextView)paramView.findViewById(2131363896));
      this.tvUplevel = ((TextView)paramView.findViewById(2131363897));
      this.tvComment = ((TextView)paramView.findViewById(2131363899));
      this.ivComment = ((ImageView)paramView.findViewById(2131363900));
      this.tvGradeTitle = ((TextView)paramView.findViewById(2131363901));
      this.gradeTitleLayout = paramView.findViewById(2131363902);
      this.gradeItemLayout = paramView.findViewById(2131363905);
      this.tvProfession = ((TextView)paramView.findViewById(2131363906));
      this.tvNeedGrade = ((TextView)paramView.findViewById(2131363907));
      this.ivDivideLine = ((ImageView)paramView.findViewById(2131362425));
    }

    public void updateView(UserMissionModel.MissionItem paramMissionItem)
    {
      switch (paramMissionItem.type)
      {
      default:
        return;
      case 1001:
        this.tvGradeTitle.setVisibility(8);
        this.gradeTitleLayout.setVisibility(8);
        this.gradeItemLayout.setVisibility(8);
        this.missionItemLayout.setVisibility(0);
        if (!TextUtils.isEmpty(paramMissionItem.typeTitle))
        {
          this.tvMissionTitle.setVisibility(0);
          this.missionTitleLayout.setVisibility(0);
          if (!paramMissionItem.isEnd)
            break label234;
          this.missionItemLayout.setBackgroundResource(2130838574);
          this.ivDivideLine.setVisibility(8);
        }
        while (true)
        {
          this.tvCondition.setText(paramMissionItem.days);
          this.tvExp.setText("+" + paramMissionItem.exp);
          this.tvUplevel.setText(paramMissionItem.limitValue);
          if ((paramMissionItem.done == null) || (!paramMissionItem.done.equals("1")))
            break label254;
          this.tvComment.setVisibility(8);
          this.ivComment.setVisibility(0);
          this.ivComment.setBackgroundResource(2130838587);
          return;
          this.tvMissionTitle.setVisibility(8);
          this.missionTitleLayout.setVisibility(8);
          break;
          label234: this.missionItemLayout.setBackgroundResource(2130838575);
          this.ivDivideLine.setVisibility(0);
        }
        label254: this.ivComment.setVisibility(8);
        this.tvComment.setVisibility(0);
        this.tvComment.setText(paramMissionItem.done);
        return;
      case 2001:
      }
      this.tvMissionTitle.setVisibility(8);
      this.missionTitleLayout.setVisibility(8);
      this.missionItemLayout.setVisibility(8);
      this.gradeItemLayout.setVisibility(0);
      if (!TextUtils.isEmpty(paramMissionItem.typeTitle))
      {
        this.tvGradeTitle.setVisibility(0);
        this.gradeTitleLayout.setVisibility(0);
        if (!paramMissionItem.isEnd)
          break label413;
        this.gradeItemLayout.setBackgroundResource(2130838574);
        this.ivDivideLine.setVisibility(8);
      }
      while (true)
      {
        this.tvProfession.setText(paramMissionItem.title);
        this.tvNeedGrade.setText(paramMissionItem.level);
        return;
        this.tvGradeTitle.setVisibility(8);
        this.gradeTitleLayout.setVisibility(8);
        break;
        label413: this.gradeItemLayout.setBackgroundResource(2130838575);
        this.ivDivideLine.setVisibility(0);
      }
    }
  }

  class GetMissionTask extends AsyncTask<Void, Void, Integer>
  {
    GetMissionTask()
    {
    }

    protected Integer doInBackground(Void[] paramArrayOfVoid)
    {
      if ((UserMissionFragment.this.getActivity() == null) || (UserMissionFragment.this.getView() == null))
        return null;
      try
      {
        UserInfoEngine.getInstance().getUserActivity(UserMissionFragment.this.getActivity(), UserMissionFragment.this.mFuid);
        UserInfoEngine.getInstance().getDailyTask(UserMissionFragment.this.getActivity(), UserMissionFragment.this.mFuid);
        Integer localInteger = Integer.valueOf(1);
        return localInteger;
      }
      catch (Exception localException)
      {
      }
      return null;
    }

    protected void onPostExecute(Integer paramInteger)
    {
      if ((UserMissionFragment.this.getActivity() == null) || (UserMissionFragment.this.getView() == null) || (paramInteger == null))
        return;
      UserMissionFragment.this.waitLayout.setVisibility(8);
      UserMissionFragment.this.updateActivityData();
      UserMissionFragment.this.updateListData();
    }
  }

  class MissionAdapter extends BaseAdapter
  {
    private Context mContext;
    private ArrayList<UserMissionModel.MissionItem> mList;

    public MissionAdapter(ArrayList<UserMissionModel.MissionItem> arg2)
    {
      Object localObject1;
      this.mContext = localObject1;
      Object localObject2;
      this.mList = localObject2;
    }

    public int getCount()
    {
      return this.mList.size();
    }

    public Object getItem(int paramInt)
    {
      return this.mList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      UserMissionFragment.DataHolder localDataHolder;
      if (paramView == null)
      {
        paramView = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903395, null, false);
        localDataHolder = new UserMissionFragment.DataHolder(UserMissionFragment.this);
        localDataHolder.initView(paramView);
        paramView.setTag(localDataHolder);
      }
      while (true)
      {
        localDataHolder.updateView((UserMissionModel.MissionItem)this.mList.get(paramInt));
        return paramView;
        localDataHolder = (UserMissionFragment.DataHolder)paramView.getTag();
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.UserMissionFragment
 * JD-Core Version:    0.6.0
 */