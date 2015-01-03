package com.kaixin001.fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.OnViewMoreClickListener;
import com.kaixin001.activity.VerifyActivity;
import com.kaixin001.adapter.FindFriendsAdapter;
import com.kaixin001.businesslogic.AddFriend;
import com.kaixin001.businesslogic.AddFriendResult;
import com.kaixin001.businesslogic.IUseAddFriendBussinessLogic;
import com.kaixin001.engine.FriendsEngine;
import com.kaixin001.engine.NewFriend2Engine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.VisitorEngine;
import com.kaixin001.item.VisitorItem;
import com.kaixin001.model.StrangerModel.Stranger;
import com.kaixin001.model.User;
import com.kaixin001.model.VisitorModel;
import com.kaixin001.util.DialogUtil;
import java.util.ArrayList;
import java.util.HashMap;

public class VisitorHistroyFragment extends BaseFragment
  implements View.OnClickListener, AdapterView.OnItemClickListener, OnViewMoreClickListener, IUseAddFriendBussinessLogic
{
  private static final int FETCH_NUM = 20;
  private static final int REQUEST_CAPTCHA = 99;
  private static final int STATE_DOWNLOADING = 1;
  private static final int STATE_FINSHED = 2;
  private static final int STATE_INITIAL;
  private AddFriend addFriendUtil;
  private int mActivityState = 0;
  HashMap<String, AddFriendResult> mAddFriendApplyChanges = new HashMap();
  private ImageView mBtnLeft;
  private TextView mCenterText;
  private GetVisitorsTask mGetDataTask;
  private RelativeLayout mLlytWait;
  private String mOuid = "";
  private String mPid = "";
  private int mVisitCount = 0;
  private ListView mVisitorListView;
  private FindFriendsAdapter mVisitorsAdapter;
  private ArrayList<StrangerModel.Stranger> mVisitorsList = new ArrayList();

  private void addNewFriendResult(String paramString)
  {
    NewFriend2Engine localNewFriend2Engine = NewFriend2Engine.getInstance();
    int i = localNewFriend2Engine.getRet();
    switch (i)
    {
    default:
      return;
    case 0:
      if ((localNewFriend2Engine.captcha == NewFriend2Engine.CAPTCHA) || (localNewFriend2Engine.captcha == NewFriend2Engine.CAPTCHA2))
      {
        Intent localIntent = new Intent(getActivity(), VerifyActivity.class);
        localIntent.putExtra("fuid", paramString);
        localIntent.putExtra("rcode", localNewFriend2Engine.rcode);
        localIntent.putExtra("imageURL", localNewFriend2Engine.captcha_url);
        getActivity().startActivityForResult(localIntent, 99);
        this.mAddFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
        this.mVisitorsAdapter.notifyDataSetChanged();
        return;
      }
      Toast.makeText(getActivity(), localNewFriend2Engine.strTipMsg, 0).show();
      this.mAddFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
      this.mVisitorsAdapter.notifyDataSetChanged();
      return;
    case 1:
      this.mAddFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
      this.mVisitorsAdapter.notifyDataSetChanged();
      return;
    case 2:
      this.mAddFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
      this.mVisitorsAdapter.notifyDataSetChanged();
      return;
    case 3:
      showInputVerify(paramString);
      return;
    case 4:
      this.mAddFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
      this.mVisitorsAdapter.notifyDataSetChanged();
      return;
    case 5:
      this.mAddFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
      this.mVisitorsAdapter.notifyDataSetChanged();
      return;
    case 6:
      DialogUtil.showKXAlertDialog(getActivity(), localNewFriend2Engine.strTipMsg, 2131427517, 2131427587, new DialogInterface.OnClickListener(paramString)
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          VisitorHistroyFragment.this.getAddFriendUtil().addNewFriend("3", this.val$fuid);
        }
      }
      , null);
      return;
    case 7:
    case 8:
      this.mAddFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
      this.mVisitorsAdapter.notifyDataSetChanged();
      return;
    case 9:
    case 10:
      getAddFriendUtil().addVerify(false, paramString);
      return;
    case 11:
    case 12:
    }
    this.mAddFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
    this.mVisitorsAdapter.notifyDataSetChanged();
  }

  private void getFirstPageFriends()
  {
    this.mVisitorsList.clear();
    this.mVisitorsAdapter.notifyDataSetChanged();
    this.mGetDataTask = new GetVisitorsTask(null);
    GetVisitorsTask localGetVisitorsTask = this.mGetDataTask;
    String[] arrayOfString = new String[2];
    arrayOfString[0] = "0";
    arrayOfString[1] = String.valueOf(20);
    localGetVisitorsTask.execute(arrayOfString);
  }

  private ArrayList<VisitorItem> getModelData()
  {
    return VisitorModel.getInstance().getVisitors();
  }

  private int getModelTotal()
  {
    return VisitorModel.getInstance().totalViewCount;
  }

  private void getNextPageFriends()
  {
    String str = ((StrangerModel.Stranger)this.mVisitorsList.get(-2 + this.mVisitorsList.size())).fuid;
    this.mGetDataTask = new GetVisitorsTask(null);
    GetVisitorsTask localGetVisitorsTask = this.mGetDataTask;
    String[] arrayOfString = new String[2];
    arrayOfString[0] = str;
    arrayOfString[1] = String.valueOf(20);
    localGetVisitorsTask.execute(arrayOfString);
  }

  private void initTitle()
  {
    this.mBtnLeft = ((ImageView)findViewById(2131362914));
    this.mBtnLeft.setOnClickListener(this);
    findViewById(2131362928).setVisibility(8);
    findViewById(2131362919).setVisibility(8);
    this.mCenterText = ((TextView)findViewById(2131362920));
    this.mCenterText.setVisibility(0);
    TextView localTextView = this.mCenterText;
    Resources localResources = getResources();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(this.mVisitCount);
    localTextView.setText(localResources.getString(2131428248, arrayOfObject));
  }

  private void pauseActivity()
  {
    if (this.mActivityState == 1)
    {
      this.mGetDataTask.cancel(true);
      updateActivityState(0);
    }
  }

  private void resumeActivity()
  {
    if (this.mActivityState == 0)
    {
      updateActivityState(1);
      stateInitOnCreate();
      getFirstPageFriends();
    }
    do
    {
      return;
      if (this.mActivityState != 1)
        continue;
      updateActivityState(2);
      setStateOnFetchFinished();
      return;
    }
    while (this.mActivityState != 2);
    setStateOnFetchFinished();
  }

  private void setStateOnFetchFinished()
  {
    this.mLlytWait.setVisibility(8);
    if (this.mVisitorsList.size() == 0)
    {
      StrangerModel.Stranger localStranger = new StrangerModel.Stranger();
      localStranger.fuid = "-1";
      this.mVisitorsList.add(localStranger);
      this.mVisitorListView.setVisibility(0);
      return;
    }
    this.mVisitorListView.setVisibility(0);
  }

  private void showInputVerify(String paramString)
  {
    getAddFriendUtil().addVerify(true, paramString);
  }

  private void stateInitOnCreate()
  {
    this.mVisitorListView.setVisibility(0);
  }

  private void updateActivityState(int paramInt)
  {
    this.mActivityState = paramInt;
  }

  private void updateDataList()
  {
    ArrayList localArrayList = getModelData();
    int i = getModelTotal();
    this.mVisitorsList.clear();
    if (localArrayList != null)
      this.mVisitorsList.addAll(localArrayList);
    if (this.mVisitorsList.size() < i);
    for (int j = 1; ; j = 0)
    {
      if (j != 0)
      {
        VisitorItem localVisitorItem = new VisitorItem();
        localVisitorItem.fuid = null;
        this.mVisitorsList.add(localVisitorItem);
      }
      this.mVisitorsAdapter.setTotalItem(i);
      this.mVisitorsAdapter.notifyDataSetChanged();
      return;
    }
  }

  private void updateTitleCenterText()
  {
    int i = VisitorModel.getInstance().totalVisitors;
    if (i != this.mVisitCount)
    {
      TextView localTextView = this.mCenterText;
      Resources localResources = getResources();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = String.valueOf(i);
      localTextView.setText(localResources.getString(2131428248, arrayOfObject));
    }
  }

  public AddFriend getAddFriendUtil()
  {
    if (this.addFriendUtil == null)
      this.addFriendUtil = new AddFriend(this, this.mHandler);
    return this.addFriendUtil;
  }

  protected boolean handleMessage(Message paramMessage)
  {
    if (paramMessage == null)
      return true;
    if (paramMessage.what == 113)
      addNewFriendResult((String)paramMessage.obj);
    while (true)
    {
      return super.handleMessage(paramMessage);
      if (paramMessage.what != 114)
        continue;
      String str = (String)paramMessage.obj;
      this.mAddFriendApplyChanges.put(str, new AddFriendResult(str, 0, null));
      this.mVisitorsAdapter.notifyDataSetChanged();
    }
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt2 == -1) && (paramInt1 == 99))
      addNewFriendResult(paramIntent.getExtras().getString("fuid"));
    this.mVisitorsAdapter.notifyDataSetChanged();
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131362914:
      finish();
      return;
    case 2131362376:
    }
    Toast.makeText(getActivity(), 2131427972, 0).show();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903408, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if ((this.mGetDataTask != null) && (this.mGetDataTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetDataTask.cancel(true);
      if (FriendsEngine.getInstance() != null)
        FriendsEngine.getInstance().cancel();
    }
    super.onDestroy();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (TextUtils.isEmpty(User.getInstance().getOauthToken()))
      Toast.makeText(getActivity(), 2131427972, 0).show();
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
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      String str1 = localBundle.getString("pid");
      if (!TextUtils.isEmpty(str1))
        this.mPid = str1;
      String str2 = localBundle.getString("ouid");
      if (!TextUtils.isEmpty(str2))
        this.mOuid = str2;
      int i = localBundle.getInt("visitor_count");
      if (i != 0)
        this.mVisitCount = i;
    }
    this.mVisitorListView = ((ListView)findViewById(2131361841));
    this.mVisitorsAdapter = new FindFriendsAdapter(this, 2130903131, this.mVisitorsList, this.mAddFriendApplyChanges, FindFriendsAdapter.VISITOR_MODE, 0);
    this.mVisitorListView.setAdapter(this.mVisitorsAdapter);
    this.mVisitorListView.setOnItemClickListener(this);
    this.mLlytWait = ((RelativeLayout)findViewById(2131361817));
    initTitle();
  }

  public void onViewMoreClick()
  {
    ArrayList localArrayList = getModelData();
    if ((localArrayList == null) || (localArrayList.size() < this.mVisitorsList.size()))
      this.mVisitorsAdapter.showLoadingFooter(true);
    while (true)
    {
      if ((localArrayList != null) && (localArrayList.size() < getModelTotal()))
        getNextPageFriends();
      return;
      updateDataList();
    }
  }

  private class GetVisitorsTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private String before;
    private int number;

    private GetVisitorsTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length == 0) || (paramArrayOfString.length != 2))
        return null;
      this.before = paramArrayOfString[0];
      this.number = Integer.valueOf(paramArrayOfString[1]).intValue();
      try
      {
        Integer localInteger = Integer.valueOf(VisitorEngine.getInstance().doGetVisitorList(VisitorHistroyFragment.this.getActivity().getApplicationContext(), this.before, this.number, VisitorHistroyFragment.this.mOuid, VisitorHistroyFragment.this.mPid));
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      int i = 1;
      if ((paramInteger == null) || (paramInteger.intValue() != i))
      {
        VisitorHistroyFragment.this.mVisitorsAdapter.showLoadingFooter(false);
        VisitorHistroyFragment.this.mLlytWait.setVisibility(0);
        VisitorHistroyFragment.this.findViewById(2131363981).setVisibility(8);
        ((TextView)VisitorHistroyFragment.this.findViewById(2131361819)).setText(VisitorHistroyFragment.this.getResources().getString(2131427852));
        return;
      }
      VisitorHistroyFragment.this.updateActivityState(2);
      VisitorHistroyFragment.this.updateTitleCenterText();
      if ((this.before.equals("0")) || (VisitorHistroyFragment.this.mVisitorsAdapter.isFooterShowLoading()))
      {
        VisitorHistroyFragment.this.updateDataList();
        if (VisitorHistroyFragment.this.mVisitorsList.size() >= VisitorHistroyFragment.this.getModelTotal())
          break label220;
      }
      while (true)
      {
        if ((this.before.equals("0")) && (i != 0))
          VisitorHistroyFragment.this.getNextPageFriends();
        if ((this.before.equals("0")) && (VisitorHistroyFragment.this.mVisitorListView != null))
          VisitorHistroyFragment.this.mVisitorListView.setSelection(0);
        VisitorHistroyFragment.this.mVisitorsAdapter.showLoadingFooter(false);
        VisitorHistroyFragment.this.setStateOnFetchFinished();
        return;
        label220: i = 0;
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
 * Qualified Name:     com.kaixin001.fragment.VisitorHistroyFragment
 * JD-Core Version:    0.6.0
 */