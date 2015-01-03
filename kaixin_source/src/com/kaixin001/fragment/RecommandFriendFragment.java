package com.kaixin001.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.VerifyActivity;
import com.kaixin001.engine.AddVerifyEngine;
import com.kaixin001.engine.FriendsEngine;
import com.kaixin001.engine.NewFriend2Engine;
import com.kaixin001.engine.RecommandFriendEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.model.StrangerModel;
import com.kaixin001.model.StrangerModel.Stranger;
import com.kaixin001.model.User;
import com.kaixin001.util.ContactUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.view.KXTopTab;
import com.kaixin001.view.KXTopTabHost;
import com.kaixin001.view.KXTopTabHost.OnTabChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecommandFriendFragment extends BaseFragment
  implements KXTopTabHost.OnTabChangeListener, View.OnClickListener, AdapterView.OnItemClickListener
{
  private static final int FETCH_NUM = 50;
  private static final int REFRESH_LIST = 100;
  static final int REQUEST_CAPTCHA = 99;
  private static final int STATE_DOWNLOADING = 1;
  private static final int STATE_FINSHED = 2;
  private static final int STATE_GETMORE = 3;
  private static final int STATE_INITIAL;
  protected int ADD_FRIEND_PROGRESS_DIALOG = 11;
  protected int GET_FRIENDS_PROGRESS_DIALOG = 10;
  private AsyncTask<String, Void, Integer> addVerifyTask;
  private ImageView btnLeft;
  HashMap<String, String> faceChanges = new HashMap();
  private ProgressBar footerProBar;
  private TextView footerTV;
  private View footerView;
  private ListView friendListView;
  private int friendStartIndex = 0;
  private Integer friendTabState = Integer.valueOf(0);
  private FriendsAdapter friendsAdapter;
  private ArrayList<StrangerModel.Stranger> friendslist = new ArrayList();
  private GetFaceDataTask getDataTask;
  private NewFriendTask newFriendTask;
  private int starStartIndex = 0;
  private Integer starTabState = Integer.valueOf(0);
  private KXTopTabHost tabHost;
  private int[] tabIndexArray = new int[2];
  private TextView tvNofriends;

  private void addNewFriend(String paramString1, String paramString2)
  {
    View localView = getActivity().getCurrentFocus();
    if (localView != null)
      ((InputMethodManager)getActivity().getSystemService("input_method")).hideSoftInputFromWindow(localView.getWindowToken(), 2);
    String[] arrayOfString = { paramString1, paramString2 };
    this.newFriendTask = new NewFriendTask(null);
    this.newFriendTask.execute(arrayOfString);
    showDialog(this.ADD_FRIEND_PROGRESS_DIALOG);
  }

  private void addNewFriendResult(String paramString)
  {
    NewFriend2Engine localNewFriend2Engine = NewFriend2Engine.getInstance();
    switch (localNewFriend2Engine.getRet())
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
        return;
      }
      this.faceChanges.put(paramString, localNewFriend2Engine.strTipMsg);
      this.friendsAdapter.notifyDataSetChanged();
      return;
    case 1:
      this.faceChanges.put(paramString, localNewFriend2Engine.strTipMsg);
      this.friendsAdapter.notifyDataSetChanged();
      return;
    case 2:
      this.faceChanges.put(paramString, localNewFriend2Engine.strTipMsg);
      this.friendsAdapter.notifyDataSetChanged();
      return;
    case 3:
      showInputVerify(paramString);
      return;
    case 4:
      this.faceChanges.put(paramString, getResources().getString(2131427986));
      this.friendsAdapter.notifyDataSetChanged();
      return;
    case 5:
      this.faceChanges.put(paramString, localNewFriend2Engine.strTipMsg);
      this.friendsAdapter.notifyDataSetChanged();
      return;
    case 6:
      DialogUtil.showKXAlertDialog(getActivity(), localNewFriend2Engine.strTipMsg, 2131427517, 2131427587, new DialogInterface.OnClickListener(paramString)
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          RecommandFriendFragment.this.addNewFriend("3", this.val$fuid);
        }
      }
      , null);
      return;
    case 7:
    case 8:
      this.faceChanges.put(paramString, localNewFriend2Engine.strTipMsg);
      this.friendsAdapter.notifyDataSetChanged();
      return;
    case 9:
    case 10:
      addVerify(false, paramString);
      return;
    case 11:
    case 12:
    }
    this.faceChanges.put(paramString, localNewFriend2Engine.strTipMsg);
    this.friendsAdapter.notifyDataSetChanged();
  }

  private void addVerify(boolean paramBoolean, String paramString)
  {
    this.addVerifyTask = new AddVerifyTask(null);
    if (paramBoolean)
    {
      String str = getString(2131427987);
      this.addVerifyTask.execute(new String[] { str, paramString });
    }
    while (true)
    {
      showDialog(this.ADD_FRIEND_PROGRESS_DIALOG);
      return;
      AsyncTask localAsyncTask = this.addVerifyTask;
      String[] arrayOfString = new String[2];
      arrayOfString[1] = paramString;
      localAsyncTask.execute(arrayOfString);
    }
  }

  private int getActivityState(int paramInt)
  {
    if (paramInt == 0)
      return this.friendTabState.intValue();
    return this.starTabState.intValue();
  }

  private void initTabHost()
  {
    this.tabHost = ((KXTopTabHost)findViewById(2131362370));
    this.tabHost.setOnTabChangeListener(this);
    KXTopTab localKXTopTab1 = new KXTopTab(getActivity());
    this.tabHost.addTab(localKXTopTab1);
    KXTopTab localKXTopTab2 = new KXTopTab(getActivity());
    this.tabHost.addTab(localKXTopTab2);
    if (TextUtils.isEmpty(User.getInstance().getOauthToken()))
    {
      localKXTopTab1.setText(2131427357);
      localKXTopTab2.setText(2131427358);
    }
    while (true)
    {
      this.tabHost.setCurrentTab(0);
      return;
      localKXTopTab1.setText(2131427967);
      localKXTopTab2.setText(2131427968);
    }
  }

  private void initTitle()
  {
    ((ImageView)findViewById(2131362928)).setVisibility(8);
    this.btnLeft = ((ImageView)findViewById(2131362914));
    this.btnLeft.setOnClickListener(this);
  }

  private void pauseActivity(int paramInt)
  {
    Integer localInteger = Integer.valueOf(getActivityState(paramInt));
    if (localInteger.intValue() == 1)
      this.getDataTask.cancel(true);
    do
      return;
    while ((localInteger.intValue() == 2) || (localInteger.intValue() != 3));
    this.getDataTask.cancel(true);
  }

  private void resumeActivity(int paramInt)
  {
    Integer localInteger = Integer.valueOf(getActivityState(paramInt));
    if (localInteger.intValue() == 0)
    {
      updateActivityState(paramInt, 1);
      stateInitOnCreate();
      this.getDataTask = new GetFaceDataTask(null);
      GetFaceDataTask localGetFaceDataTask2 = this.getDataTask;
      Integer[] arrayOfInteger2 = new Integer[3];
      arrayOfInteger2[0] = Integer.valueOf(paramInt);
      arrayOfInteger2[1] = Integer.valueOf(0);
      arrayOfInteger2[2] = Integer.valueOf(50);
      localGetFaceDataTask2.execute(arrayOfInteger2);
    }
    do
    {
      return;
      if (localInteger.intValue() == 1)
      {
        this.getDataTask = new GetFaceDataTask(null);
        GetFaceDataTask localGetFaceDataTask1 = this.getDataTask;
        Integer[] arrayOfInteger1 = new Integer[3];
        arrayOfInteger1[0] = Integer.valueOf(paramInt);
        arrayOfInteger1[1] = Integer.valueOf(0);
        arrayOfInteger1[2] = Integer.valueOf(50);
        localGetFaceDataTask1.execute(arrayOfInteger1);
        return;
      }
      if (localInteger.intValue() != 2)
        continue;
      updateFriendList(this.tabHost.getCurrentTab());
      setStateOnFetchFinished();
      return;
    }
    while (localInteger.intValue() != 3);
    Message localMessage = Message.obtain();
    localMessage.what = 100;
    localMessage.arg1 = paramInt;
    this.mHandler.sendMessage(localMessage);
  }

  private void setStateOnFetchFinished()
  {
    dismissDialog(this.GET_FRIENDS_PROGRESS_DIALOG);
    this.friendsAdapter.notifyDataSetChanged();
    if (this.friendslist == null);
    for (int i = 0; i == 0; i = this.friendslist.size())
    {
      this.tvNofriends.setVisibility(0);
      this.friendListView.setVisibility(8);
      return;
    }
    this.friendListView.setVisibility(0);
    this.tvNofriends.setVisibility(8);
  }

  private boolean shouldGetMore(int paramInt)
  {
    if (paramInt == 0)
    {
      int j = StrangerModel.getInstance().totalFriends;
      if (this.friendStartIndex >= j - 1);
    }
    else
    {
      int i;
      do
      {
        return true;
        if (paramInt != 1)
          break;
        i = Integer.parseInt(StrangerModel.getInstance().totalStars);
      }
      while (this.starStartIndex < i - 1);
    }
    return false;
  }

  private void showInputVerify(String paramString)
  {
    addVerify(true, paramString);
  }

  private void stateInitOnCreate()
  {
    this.friendListView.setVisibility(8);
    showDialog(this.GET_FRIENDS_PROGRESS_DIALOG);
  }

  private void stateOnGetMore()
  {
    this.footerView.setEnabled(false);
    this.footerView.setVisibility(0);
    this.footerProBar.setVisibility(4);
    this.footerTV.setVisibility(0);
    this.footerTV.setTextColor(getResources().getColor(2130839395));
  }

  private void updateActivityState(int paramInt1, int paramInt2)
  {
    if (paramInt1 == 0)
    {
      this.friendTabState = Integer.valueOf(paramInt2);
      return;
    }
    this.starTabState = Integer.valueOf(paramInt2);
  }

  private void updateFriendList(int paramInt)
  {
    ArrayList localArrayList1;
    if (paramInt == 0)
    {
      localArrayList1 = StrangerModel.getInstance().getFriends();
      if (localArrayList1 != null)
        break label33;
    }
    label33: for (ArrayList localArrayList2 = null; ; localArrayList2 = (ArrayList)localArrayList1.clone())
    {
      this.friendslist = localArrayList2;
      return;
      localArrayList1 = StrangerModel.getInstance().getStarFriends();
      break;
    }
  }

  public void beforeTabChanged(int paramInt)
  {
    this.tabIndexArray[paramInt] = this.friendListView.getFirstVisiblePosition();
    pauseActivity(paramInt);
  }

  protected void getMoreFace()
  {
    int i = this.tabHost.getCurrentTab();
    if ((Integer.valueOf(getActivityState(i)).intValue() == 2) && (shouldGetMore(i)))
    {
      updateActivityState(i, 3);
      stateOnGetMore();
      this.getDataTask = new GetFaceDataTask(null);
      GetFaceDataTask localGetFaceDataTask = this.getDataTask;
      Integer[] arrayOfInteger = new Integer[3];
      arrayOfInteger[0] = Integer.valueOf(i);
      arrayOfInteger[1] = Integer.valueOf(0);
      arrayOfInteger[2] = Integer.valueOf(50);
      localGetFaceDataTask.execute(arrayOfInteger);
    }
  }

  protected boolean handleMessage(Message paramMessage)
  {
    if (paramMessage == null)
      return true;
    int i;
    Integer localInteger;
    if (paramMessage.what == 100)
    {
      i = paramMessage.arg1;
      updateFriendList(i);
      localInteger = Integer.valueOf(getActivityState(i));
      if (localInteger.intValue() != 1)
        break label69;
      updateActivityState(i, 2);
      if (i == this.tabHost.getCurrentTab())
        setStateOnFetchFinished();
    }
    while (true)
    {
      return super.handleMessage(paramMessage);
      label69: if (localInteger.intValue() != 3)
        continue;
      updateActivityState(i, 2);
      if (i != this.tabHost.getCurrentTab())
        continue;
      setStateOnFetchFinished();
    }
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt2 == -1) && (paramInt1 == 99))
      addNewFriendResult(paramIntent.getExtras().getString("fuid"));
  }

  public void onClick(View paramView)
  {
    if (paramView.getId() == 2131362914)
      finish();
    do
      return;
    while (paramView.getId() != 2131362376);
    Toast.makeText(getActivity(), 2131427972, 0).show();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    getActivity().getWindow().setFlags(3, 3);
  }

  protected Dialog onCreateDialog(int paramInt)
  {
    if (paramInt == this.GET_FRIENDS_PROGRESS_DIALOG)
      return ProgressDialog.show(getActivity(), "", getResources().getText(2131427599), true, true, new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramDialogInterface)
        {
          if (RecommandFriendFragment.this.getDataTask != null)
          {
            RecommandFriendFragment.this.getDataTask.cancel(true);
            RecommandFriendFragment.this.getDataTask = null;
            RecommandFriendFragment.this.mHandler.sendEmptyMessage(100);
          }
        }
      });
    if (paramInt == this.ADD_FRIEND_PROGRESS_DIALOG)
      return ProgressDialog.show(getActivity(), "", getResources().getText(2131427512), true, true, new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramDialogInterface)
        {
        }
      });
    return super.onCreateDialog(paramInt);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903309, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if ((this.getDataTask != null) && (this.getDataTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.getDataTask.cancel(true);
      if (FriendsEngine.getInstance() != null)
        FriendsEngine.getInstance().cancel();
    }
    cancelTask(this.newFriendTask);
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
    pauseActivity(this.tabHost.getCurrentTab());
  }

  public void onResume()
  {
    super.onResume();
    resumeActivity(this.tabHost.getCurrentTab());
  }

  public void onTabChanged(int paramInt)
  {
    updateFriendList(paramInt);
    this.friendsAdapter.notifyDataSetChanged();
    int i = this.tabIndexArray[paramInt];
    this.friendListView.setSelection(i);
    resumeActivity(this.tabHost.getCurrentTab());
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.friendListView = ((ListView)findViewById(2131362376));
    this.friendsAdapter = new FriendsAdapter(getActivity(), 2130903131, this.friendslist);
    this.friendListView.setAdapter(this.friendsAdapter);
    this.friendListView.setOnItemClickListener(this);
    this.tvNofriends = ((TextView)findViewById(2131363504));
    this.footerView = getActivity().getLayoutInflater().inflate(2130903259, null);
    this.footerView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        RecommandFriendFragment.this.getMoreFace();
      }
    });
    this.footerTV = ((TextView)this.footerView.findViewById(2131362073));
    this.footerTV.setText(2131427748);
    this.footerProBar = ((ProgressBar)this.footerView.findViewById(2131362072));
    initTitle();
    initTabHost();
  }

  private class AddVerifyTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private String fuid;

    private AddVerifyTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length != 2))
        return null;
      String str = paramArrayOfString[0];
      this.fuid = paramArrayOfString[1];
      try
      {
        if (AddVerifyEngine.getInstance().addVerify(RecommandFriendFragment.this.getActivity().getApplicationContext(), this.fuid, str))
        {
          Integer localInteger = Integer.valueOf(1);
          return localInteger;
        }
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        return null;
      }
      return Integer.valueOf(0);
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      RecommandFriendFragment.this.dismissDialog(RecommandFriendFragment.this.ADD_FRIEND_PROGRESS_DIALOG);
      if (paramInteger == null)
      {
        Toast.makeText(RecommandFriendFragment.this.getActivity(), 2131427374, 0).show();
        return;
      }
      try
      {
        if (paramInteger.intValue() == 1)
        {
          RecommandFriendFragment.this.faceChanges.put(this.fuid, AddVerifyEngine.getInstance().getTipMsg());
          RecommandFriendFragment.this.friendsAdapter.notifyDataSetChanged();
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("HomeActivity", "onPostExecute", localException);
        return;
      }
      Toast.makeText(RecommandFriendFragment.this.getActivity(), 2131427513, 0).show();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class FriendItemViewTag
    implements View.OnClickListener
  {
    private Button btnAddFriend;
    private String fuid;
    private boolean isStar;
    private ImageView ivLogo;
    private ImageView ivStar;
    private LinearLayout layout_add_friend;
    private TextView tvAddFriendNotice;
    private TextView tvCity = null;
    private TextView tvConstellation = null;
    private TextView tvFans;
    private TextView tvGender = null;
    private TextView tvName = null;

    private FriendItemViewTag(View arg2)
    {
      Object localObject;
      this.tvName = ((TextView)localObject.findViewById(2131363508));
      this.ivLogo = ((ImageView)localObject.findViewById(2131361829));
      this.ivStar = ((ImageView)localObject.findViewById(2131363509));
      this.tvFans = ((TextView)localObject.findViewById(2131363510));
      this.tvGender = ((TextView)localObject.findViewById(2131363511));
      this.tvConstellation = ((TextView)localObject.findViewById(2131363512));
      this.tvCity = ((TextView)localObject.findViewById(2131363513));
      this.btnAddFriend = ((Button)localObject.findViewById(2131361830));
      this.btnAddFriend.setOnClickListener(this);
      this.layout_add_friend = ((LinearLayout)localObject.findViewById(2131363514));
      this.tvAddFriendNotice = ((TextView)localObject.findViewById(2131363516));
    }

    public void onClick(View paramView)
    {
      if (TextUtils.isEmpty(User.getInstance().getOauthToken()))
        Toast.makeText(RecommandFriendFragment.this.getActivity(), 2131427972, 0).show();
      do
        return;
      while (paramView.getId() != 2131361830);
      if (this.isStar)
      {
        RecommandFriendFragment.this.addNewFriend(null, this.fuid);
        return;
      }
      RecommandFriendFragment.this.addNewFriend("3", this.fuid);
    }

    public void updateFriend(StrangerModel.Stranger paramStranger)
      throws Exception
    {
      RecommandFriendFragment.this.displayPicture(this.ivLogo, paramStranger.flogo, 2130838676);
      this.fuid = paramStranger.fuid;
      this.tvName.setText(paramStranger.fname);
      this.tvName.setTextColor(RecommandFriendFragment.this.getResources().getColor(2130839419));
      this.tvName.setPadding(0, 2, 0, 2);
      boolean bool;
      if (paramStranger.fans == null)
        bool = false;
      String str2;
      while (true)
      {
        this.isStar = bool;
        if (this.isStar)
        {
          this.ivStar.setVisibility(0);
          this.ivStar.setImageResource(2130838985);
          this.tvFans.setVisibility(0);
          this.tvGender.setVisibility(8);
          this.tvConstellation.setVisibility(8);
          this.tvCity.setVisibility(8);
          this.tvFans.setText(RecommandFriendFragment.this.getResources().getString(2131427969) + ": " + paramStranger.fans);
          if (paramStranger.isfan == 1)
            RecommandFriendFragment.this.faceChanges.put(paramStranger.fuid, RecommandFriendFragment.this.getString(2131427991));
          this.btnAddFriend.setText(2131427990);
          str2 = (String)RecommandFriendFragment.this.faceChanges.get(this.fuid);
          if (str2 != null)
            break;
          this.btnAddFriend.setVisibility(0);
          this.layout_add_friend.setVisibility(8);
          this.tvAddFriendNotice.setVisibility(8);
          return;
          bool = true;
          continue;
        }
        else
        {
          this.ivStar.setVisibility(8);
          this.tvFans.setVisibility(8);
          this.tvGender.setVisibility(0);
          this.tvConstellation.setVisibility(0);
          this.tvCity.setVisibility(0);
          if ("0".equals(String.valueOf(paramStranger.gender)));
          for (String str1 = RecommandFriendFragment.this.getString(2131427532); ; str1 = RecommandFriendFragment.this.getString(2131427533))
          {
            this.tvGender.setText(RecommandFriendFragment.this.getResources().getString(2131427518) + ": " + str1);
            this.tvConstellation.setText(RecommandFriendFragment.this.getResources().getString(2131427520) + ": " + paramStranger.constellation);
            this.tvCity.setText(RecommandFriendFragment.this.getResources().getString(2131427522) + ": " + paramStranger.city);
            this.btnAddFriend.setText(2131427989);
            break;
          }
        }
      }
      this.btnAddFriend.setVisibility(8);
      this.layout_add_friend.setVisibility(0);
      this.tvAddFriendNotice.setVisibility(0);
      this.tvAddFriendNotice.setText(str2);
    }
  }

  private class FriendsAdapter extends ArrayAdapter<StrangerModel.Stranger>
  {
    public FriendsAdapter(int paramArrayList, ArrayList<StrangerModel.Stranger> arg3)
    {
      super(i, localList);
    }

    public int getCount()
    {
      if (RecommandFriendFragment.this.friendslist == null)
        return 0;
      return RecommandFriendFragment.this.friendslist.size();
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      try
      {
        StrangerModel.Stranger localStranger = (StrangerModel.Stranger)RecommandFriendFragment.this.friendslist.get(paramInt);
        RecommandFriendFragment.FriendItemViewTag localFriendItemViewTag;
        if (paramView == null)
        {
          paramView = ((LayoutInflater)RecommandFriendFragment.this.getActivity().getSystemService("layout_inflater")).inflate(2130903310, null);
          localFriendItemViewTag = new RecommandFriendFragment.FriendItemViewTag(RecommandFriendFragment.this, paramView, null);
          paramView.setTag(localFriendItemViewTag);
        }
        while (true)
        {
          localFriendItemViewTag.updateFriend(localStranger);
          return paramView;
          localFriendItemViewTag = (RecommandFriendFragment.FriendItemViewTag)paramView.getTag();
        }
      }
      catch (Exception localException)
      {
        KXLog.e("AddMaybeFriendsActivity", "getView", localException);
      }
      return paramView;
    }
  }

  private class GetFaceDataTask extends KXFragment.KXAsyncTask<Integer, Void, Integer>
  {
    private static final int GET_MAYBE_FRIEND = 0;
    private static final int GET_STAR = 1;
    private int number;
    private String phonenums;
    private int start;
    private int type;

    private GetFaceDataTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Integer[] paramArrayOfInteger)
    {
      if ((paramArrayOfInteger == null) || (paramArrayOfInteger.length == 0) || (paramArrayOfInteger.length != 3));
      while (true)
      {
        return null;
        this.type = paramArrayOfInteger[0].intValue();
        this.start = paramArrayOfInteger[1].intValue();
        this.number = paramArrayOfInteger[2].intValue();
        try
        {
          RecommandFriendEngine localRecommandFriendEngine = RecommandFriendEngine.getInstance();
          if (this.type == 0)
          {
            if (this.phonenums == null)
              this.phonenums = ContactUtil.getAllPhoneNumbers(RecommandFriendFragment.this.getActivity().getContentResolver());
            return Integer.valueOf(localRecommandFriendEngine.doGetRecommandFriendList(RecommandFriendFragment.this.getActivity().getApplicationContext(), this.start, this.number, this.phonenums));
          }
          if (1 != this.type)
            continue;
          Integer localInteger = Integer.valueOf(localRecommandFriendEngine.doGetRecommandStarList(RecommandFriendFragment.this.getActivity().getApplicationContext(), this.start, this.number));
          return localInteger;
        }
        catch (SecurityErrorException localSecurityErrorException)
        {
        }
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if ((paramInteger == null) || (paramInteger.intValue() != 1))
        Toast.makeText(RecommandFriendFragment.this.getActivity(), 2131427374, 0).show();
      Message localMessage = Message.obtain();
      localMessage.what = 100;
      localMessage.arg1 = this.type;
      RecommandFriendFragment.this.mHandler.sendMessage(localMessage);
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class NewFriendTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private String fuid;

    private NewFriendTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length != 2))
        return null;
      String str = paramArrayOfString[0];
      this.fuid = paramArrayOfString[1];
      try
      {
        int i = NewFriend2Engine.getInstance().addNewFriend(RecommandFriendFragment.this.getActivity().getApplicationContext(), this.fuid, str);
        if ((i == 0) && (NewFriend2Engine.getInstance().strTipMsg == null))
        {
          NewFriend2Engine.getInstance().strTipMsg = RecommandFriendFragment.this.getString(2131427378);
          return Integer.valueOf(0);
        }
        Integer localInteger = Integer.valueOf(i);
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      RecommandFriendFragment.this.dismissDialog(RecommandFriendFragment.this.ADD_FRIEND_PROGRESS_DIALOG);
      if (paramInteger == null)
      {
        Toast.makeText(RecommandFriendFragment.this.getActivity(), 2131427374, 0).show();
        return;
      }
      try
      {
        RecommandFriendFragment.this.addNewFriendResult(this.fuid);
        return;
      }
      catch (Exception localException)
      {
        KXLog.e("HomeActivity", "onPostExecute", localException);
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
 * Qualified Name:     com.kaixin001.fragment.RecommandFriendFragment
 * JD-Core Version:    0.6.0
 */