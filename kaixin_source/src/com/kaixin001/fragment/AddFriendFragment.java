package com.kaixin001.fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.VerifyActivity;
import com.kaixin001.adapter.AddFriendsAndFansAdapter;
import com.kaixin001.businesslogic.AddFriend;
import com.kaixin001.businesslogic.AddFriendResult;
import com.kaixin001.businesslogic.IUseAddFriendBussinessLogic;
import com.kaixin001.engine.AddFriendsEngine;
import com.kaixin001.engine.NewFriend2Engine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.item.FriendInfo;
import com.kaixin001.model.StrangerModel;
import com.kaixin001.model.StrangerModel.Stranger;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.DialogUtil;
import java.util.ArrayList;
import java.util.HashMap;

public class AddFriendFragment extends BaseFragment
  implements View.OnClickListener, IUseAddFriendBussinessLogic
{
  public static int FROM_ADDFRIEND = 0;
  private static final int REQUEST_CAPTCHA = 99;
  public static final int SEARCH_BY_ACOUNT_READY = 34;
  HashMap<String, AddFriendResult> addFriendApplyChanges = new HashMap();
  private AddFriend addFriendUtil;
  private AddFriendsAndFansAdapter addfriendsAdapter;
  private String addfriendtype;
  private ImageView btnLeft;
  private ImageView btnRight;
  private TextView centerText;
  private ArrayList<FriendInfo> checkedFriendsList;
  private FriendInfo friendInfo;
  private ListView friendListView;
  private ArrayList<StrangerModel.Stranger> friendslist = new ArrayList();
  private String frienduid;
  private GetFriendsDataTask getFriendsDataTask;
  private LinearLayout llytWait;
  private StringBuffer stringBuffer;
  private String stringFrienduid;
  private String stringFrienduids;

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
        this.addFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
        this.addfriendsAdapter.notifyDataSetChanged();
        return;
      }
      Toast.makeText(getActivity(), localNewFriend2Engine.strTipMsg, 0).show();
      this.addFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
      this.addfriendsAdapter.notifyDataSetChanged();
      return;
    case 1:
      this.addFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
      this.addfriendsAdapter.notifyDataSetChanged();
      return;
    case 2:
      this.addFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
      this.addfriendsAdapter.notifyDataSetChanged();
      return;
    case 3:
      showInputVerify(paramString);
      return;
    case 4:
      this.addFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
      this.addfriendsAdapter.notifyDataSetChanged();
      return;
    case 5:
      this.addFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
      this.addfriendsAdapter.notifyDataSetChanged();
      return;
    case 6:
      DialogUtil.showMsgDlg(getActivity(), 2131427329, localNewFriend2Engine.strTipMsg, 2131427517, 2131427587, new DialogInterface.OnClickListener(paramString)
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          AddFriendFragment.this.getAddFriendUtil().addNewFriend("3", this.val$fuid);
        }
      }
      , null);
      return;
    case 7:
    case 8:
      this.addFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
      this.addfriendsAdapter.notifyDataSetChanged();
      return;
    case 9:
    case 10:
      getAddFriendUtil().addVerify(false, paramString);
      return;
    case 11:
    case 12:
    }
    this.addFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
    this.addfriendsAdapter.notifyDataSetChanged();
  }

  private ArrayList<StrangerModel.Stranger> getModelData()
  {
    return StrangerModel.getInstance().getFriends();
  }

  private void initData()
  {
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      this.checkedFriendsList = ((ArrayList)localBundle.getSerializable("newsinfo"));
      this.addfriendtype = localBundle.getString("addfriendtype");
      new ArrayList();
      this.stringBuffer = new StringBuffer();
    }
    for (int i = 0; ; i++)
    {
      if (i >= this.checkedFriendsList.size())
      {
        if (this.stringBuffer.length() >= 1)
        {
          this.stringBuffer.delete(-1 + this.stringBuffer.length(), this.stringBuffer.length());
          this.stringFrienduid = this.stringBuffer.toString();
        }
        return;
      }
      this.friendInfo = ((FriendInfo)this.checkedFriendsList.get(i));
      this.frienduid = this.friendInfo.getFuid();
      this.stringBuffer.append(this.frienduid);
      this.stringBuffer.append(",");
    }
  }

  private void initOnOnClick()
  {
    this.btnRight.setOnClickListener(this);
    this.btnLeft.setOnClickListener(this);
  }

  private void initView(View paramView)
  {
    this.friendListView = ((ListView)paramView.findViewById(2131361841));
    this.btnLeft = ((ImageView)paramView.findViewById(2131362914));
    this.btnRight = ((ImageView)paramView.findViewById(2131362928));
    this.btnRight.setVisibility(8);
    this.llytWait = ((LinearLayout)paramView.findViewById(2131361817));
    this.llytWait.setVisibility(0);
    this.centerText = ((TextView)paramView.findViewById(2131362920));
    this.centerText.setVisibility(0);
    if (this.addfriendtype.equals("addfriend"))
    {
      this.centerText.setText(2131427348);
      return;
    }
    if (this.addfriendtype.equals("addfans"))
    {
      this.centerText.setText(2131427701);
      return;
    }
    if (this.addfriendtype.equals("maybefriend"))
    {
      this.centerText.setText(2131427967);
      return;
    }
    this.centerText.setText(2131428531);
  }

  private void showInputVerify(String paramString)
  {
    getAddFriendUtil().addVerify(true, paramString);
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 2);
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
      if (paramMessage.what == 114)
      {
        String str = (String)paramMessage.obj;
        this.addFriendApplyChanges.put(str, new AddFriendResult(str, 0, null));
        this.addfriendsAdapter.notifyDataSetChanged();
        continue;
      }
      if (paramMessage.what != 34)
        continue;
      this.llytWait.setVisibility(8);
    }
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
    case 2131361842:
    }
    startFragment(new Intent(getActivity(), FindFriendFragment.class), true, 1);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    getActivity().getWindow().setFlags(3, 3);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903050, paramViewGroup, false);
  }

  public void onDestroy()
  {
    cancelTask(this.getFriendsDataTask);
    super.onDestroy();
  }

  public void onStop()
  {
    if (this.checkedFriendsList != null)
    {
      this.checkedFriendsList.clear();
      this.checkedFriendsList = null;
      this.addfriendsAdapter.notifyDataSetChanged();
    }
    super.onStop();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    initData();
    initView(paramView);
    initOnOnClick();
    this.getFriendsDataTask = new GetFriendsDataTask(null);
    GetFriendsDataTask localGetFriendsDataTask = this.getFriendsDataTask;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = this.stringFrienduid;
    localGetFriendsDataTask.execute(arrayOfString);
    if (this.addfriendtype.equals("addfriend"));
    for (this.addfriendsAdapter = new AddFriendsAndFansAdapter(this, 2130903131, this.friendslist, this.addFriendApplyChanges, AddFriendsAndFansAdapter.STRANGER_MODE, FROM_ADDFRIEND); ; this.addfriendsAdapter = new AddFriendsAndFansAdapter(this, 2130903131, this.friendslist, this.addFriendApplyChanges, AddFriendsAndFansAdapter.ADDFANS_MODE, FROM_ADDFRIEND))
    {
      this.friendListView.setAdapter(this.addfriendsAdapter);
      return;
    }
  }

  private class GetFriendsDataTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private GetFriendsDataTask()
    {
      super();
    }

    private int getModelTotal()
    {
      return StrangerModel.getInstance().totalFriends;
    }

    private void updateDataList()
    {
      ArrayList localArrayList = AddFriendFragment.this.getModelData();
      AddFriendFragment.this.friendslist.clear();
      int i = getModelTotal();
      if (localArrayList != null)
        AddFriendFragment.this.friendslist.addAll(localArrayList);
      if (AddFriendFragment.this.addfriendsAdapter != null)
      {
        AddFriendFragment.this.addfriendsAdapter.setTotalItem(i);
        AddFriendFragment.this.addfriendsAdapter.notifyDataSetChanged();
      }
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
        return null;
      AddFriendFragment.this.stringFrienduids = paramArrayOfString[0];
      try
      {
        int i = AddFriendsEngine.getInstance().doGetFriendList(AddFriendFragment.this.getActivity().getApplicationContext(), 1, AddFriendFragment.this.stringFrienduids);
        if (i == 1)
          AddFriendFragment.this.mHandler.sendEmptyMessage(34);
        Integer localInteger = Integer.valueOf(i);
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        localSecurityErrorException.printStackTrace();
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      AddFriendFragment.this.llytWait.setVisibility(8);
      updateDataList();
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
 * Qualified Name:     com.kaixin001.fragment.AddFriendFragment
 * JD-Core Version:    0.6.0
 */