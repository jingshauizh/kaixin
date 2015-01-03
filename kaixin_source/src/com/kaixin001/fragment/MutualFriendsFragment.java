package com.kaixin001.fragment;

import android.content.res.Resources;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
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
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.FriendsEngine;
import com.kaixin001.engine.NewsEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.model.HomePeopleInfoModel;
import com.kaixin001.model.HomePeopleInfoModel.PeopleInfo;
import com.kaixin001.model.User;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;

public class MutualFriendsFragment extends BaseFragment
  implements View.OnClickListener, AdapterView.OnItemClickListener
{
  private ImageView btnLeft;
  private LinearLayout llytWait;
  private MutualFriendsAdapter mFriendsAdapter;
  private ArrayList<HomePeopleInfoModel.PeopleInfo> mFriendsList = new ArrayList();
  private ListView mFriendsListView;
  private String mFuid = null;
  private GetPeopleInfoTask mGetMutualFriendsTask;
  private ImageView mIvAvatar;
  private int mTotalNum = -1;
  private TextView mTvName;
  private TextView tvWait;

  private void initTitle()
  {
    ((ImageView)findViewById(2131362928)).setVisibility(8);
    this.btnLeft = ((ImageView)findViewById(2131362914));
    this.btnLeft.setOnClickListener(this);
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setVisibility(0);
    localTextView.setText(getResources().getText(2131428077));
  }

  private void refreshDone(int paramInt)
  {
    ArrayList localArrayList = HomePeopleInfoModel.getInstance().getPeopleInfoList();
    int i = HomePeopleInfoModel.getInstance().getTotalNum();
    if (paramInt == 0)
      this.mFriendsList.clear();
    this.mTotalNum = i;
    this.mFriendsList.addAll(localArrayList);
  }

  protected boolean handleMessage(Message paramMessage)
  {
    if (paramMessage == null)
      return true;
    return super.handleMessage(paramMessage);
  }

  public void onClick(View paramView)
  {
    if (paramView.getId() == 2131362914)
      finish();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      this.mFuid = localBundle.getString("UID");
      if (this.mFuid == null)
      {
        Toast.makeText(getActivity().getApplicationContext(), 2131427800, 0).show();
        finish();
      }
    }
    while (true)
    {
      getActivity().getWindow().setFlags(3, 3);
      return;
      Toast.makeText(getActivity().getApplicationContext(), 2131427800, 0).show();
      finish();
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903235, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if ((this.mGetMutualFriendsTask != null) && (this.mGetMutualFriendsTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetMutualFriendsTask.cancel(true);
      if (FriendsEngine.getInstance() != null)
        FriendsEngine.getInstance().cancel();
    }
    super.onDestroy();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    TextUtils.isEmpty(User.getInstance().getOauthToken());
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mFriendsListView = ((ListView)findViewById(2131363107));
    this.mFriendsAdapter = new MutualFriendsAdapter(this, 2130903236, this.mFriendsList);
    this.mFriendsListView.setAdapter(this.mFriendsAdapter);
    this.mFriendsListView.setOnItemClickListener(this);
    this.llytWait = ((LinearLayout)findViewById(2131361817));
    this.tvWait = ((TextView)findViewById(2131361819));
    initTitle();
    String[] arrayOfString = new String[2];
    arrayOfString[0] = Integer.toString(0);
    arrayOfString[1] = this.mFuid;
    this.mGetMutualFriendsTask = new GetPeopleInfoTask();
    this.mGetMutualFriendsTask.execute(arrayOfString);
  }

  class GetPeopleInfoTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private int mStart = -1;

    GetPeopleInfoTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      try
      {
        this.mStart = Integer.parseInt(paramArrayOfString[0]);
        paramArrayOfString[1];
        if (NewsEngine.getInstance().getMutualFriendsData(MutualFriendsFragment.this.getActivity().getApplicationContext(), HomePeopleInfoModel.getInstance(), paramArrayOfString[0], paramArrayOfString[1]))
          return Integer.valueOf(this.mStart);
        Integer localInteger = Integer.valueOf(-1);
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        return null;
      }
      catch (Exception localException)
      {
        KXLog.e("MutualFriendsFragment", "GetPeopleInfoTask", localException);
      }
      return Integer.valueOf(-1);
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if (paramInteger == null)
        MutualFriendsFragment.this.finish();
      while (true)
      {
        return;
        if (paramInteger == null)
          continue;
        try
        {
          if (MutualFriendsFragment.this.llytWait != null)
            MutualFriendsFragment.this.llytWait.setVisibility(8);
          HomePeopleInfoModel.getInstance().getTotalNum();
          MutualFriendsFragment.this.refreshDone(paramInteger.intValue());
          if ((this.mStart != 0) || (paramInteger.intValue() >= 0))
            continue;
          Toast.makeText(MutualFriendsFragment.this.getActivity(), 2131427374, 0).show();
          return;
        }
        catch (Exception localException)
        {
          KXLog.e("HomeActivity", "onPostExecute", localException);
        }
      }
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
 * Qualified Name:     com.kaixin001.fragment.MutualFriendsFragment
 * JD-Core Version:    0.6.0
 */