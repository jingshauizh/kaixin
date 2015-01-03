package com.kaixin001.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.OnViewMoreClickListener;
import com.kaixin001.activity.VerifyActivity;
import com.kaixin001.adapter.CircleMembersAdapter;
import com.kaixin001.adapter.CircleNewsAdapter;
import com.kaixin001.adapter.CirclesAdapter;
import com.kaixin001.businesslogic.AddFriend;
import com.kaixin001.businesslogic.AddFriendResult;
import com.kaixin001.businesslogic.IUseAddFriendBussinessLogic;
import com.kaixin001.engine.CircleMemberEngine;
import com.kaixin001.engine.CircleNewsEngine;
import com.kaixin001.engine.GetStrangerInfoEngine;
import com.kaixin001.engine.NewFriend2Engine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.item.CircleRecordUploadTask;
import com.kaixin001.item.KaixinCircleMember;
import com.kaixin001.model.CircleMemberModel;
import com.kaixin001.model.CircleNewsModel;
import com.kaixin001.model.CircleNewsModel.CircleNewsItem;
import com.kaixin001.model.KaixinUser;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.view.KXTopTab;
import com.kaixin001.view.KXTopTabHost;
import com.kaixin001.view.KXTopTabHost.OnTabChangeListener;
import java.util.ArrayList;
import java.util.HashMap;

public class CircleMainFragment extends BaseFragment
  implements KXTopTabHost.OnTabChangeListener, View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, OnViewMoreClickListener, IUseAddFriendBussinessLogic
{
  private static final int FETCH_NUM = 20;
  private static final int MENU_DESKTOP_ID = 403;
  private static final int MENU_HOME_ME_ID = 404;
  private static final int MENU_REFRESH_ID = 401;
  private static final int MENU_TOP_ID = 402;
  public static final int MODE_NEWS_MEMBER = 0;
  public static final int MODE_VIEW_MEMBER = 1;
  private static final int REQUEST_CAPTCHA = 99;
  private static final int STATE_DOWNLOADING = 1;
  private static final int STATE_FINSHED = 2;
  private static final int STATE_INITIAL = 0;
  private static final int TAB_MEMBER = 4;
  private static final int TAB_NEWS = 3;
  private static final String TAG = "CircleMainFragment";
  public static final int VIEW_LATEST_NEWS = 410;
  private int activityStateMember = 0;
  private int activityStateNews = 0;
  private int activityTab = 3;
  private CircleMembersAdapter adapterCircleMember;
  private CircleNewsAdapter adapterCircleNews;
  HashMap<String, AddFriendResult> addFriendApplyChanges = new HashMap();
  private AddFriend addFriendUtil;
  private ImageView btnLeft;
  private ImageView btnRefresh;
  private TextView centerText;
  private final ArrayList<KaixinCircleMember> circleMemberList = new ArrayList();
  private final ArrayList<CircleNewsModel.CircleNewsItem> circleNewsList = new ArrayList();
  private GetCircleMemberTask getMembersTask;
  private GetCircleNewsTask getNewsTask;
  private String gid;
  private String gname;
  private int gtype;
  private boolean isViewLeastNews = false;
  private LinearLayout llytToolbar;
  private LinearLayout llytWait;
  private ListView lstvNewsOrMember;
  private Dialog mAlertDialog;
  private View mLayoutUploadPhoto = null;
  private View mLayoutWriteRecord = null;
  private int mode = 0;
  private ProgressBar rightProBar;
  private KXTopTabHost tabHost;
  private int[] tabIndexArray = new int[2];
  private String title;

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
        this.adapterCircleMember.notifyDataSetChanged();
        return;
      }
      Toast.makeText(getActivity(), localNewFriend2Engine.strTipMsg, 0).show();
      this.addFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
      this.adapterCircleMember.notifyDataSetChanged();
      return;
    case 1:
      this.addFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
      this.adapterCircleMember.notifyDataSetChanged();
      return;
    case 2:
      this.addFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
      this.adapterCircleMember.notifyDataSetChanged();
      return;
    case 3:
      showInputVerify(paramString);
      return;
    case 4:
      this.addFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
      this.adapterCircleMember.notifyDataSetChanged();
      return;
    case 5:
      this.addFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
      this.adapterCircleMember.notifyDataSetChanged();
      return;
    case 6:
      DialogUtil.showKXAlertDialog(getActivity(), localNewFriend2Engine.strTipMsg, 2131427517, 2131427587, new DialogInterface.OnClickListener(paramString)
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          CircleMainFragment.this.getAddFriendUtil().addNewFriend("3", this.val$fuid);
        }
      }
      , null);
      return;
    case 7:
    case 8:
      this.addFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
      this.adapterCircleMember.notifyDataSetChanged();
      return;
    case 9:
    case 10:
      getAddFriendUtil().addVerify(false, paramString);
      return;
    case 11:
    case 12:
    }
    this.addFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
    this.adapterCircleMember.notifyDataSetChanged();
  }

  private void finishActivity()
  {
    Intent localIntent = new Intent();
    localIntent.putExtra("gid", this.gid);
    localIntent.putExtra("view_latest_news", this.isViewLeastNews);
    setResult(-1, localIntent);
    finish();
  }

  private int getActivityState()
  {
    if (this.activityTab == 3)
      return this.activityStateNews;
    return this.activityStateMember;
  }

  private void getFirstPageMember()
  {
    this.llytWait.setVisibility(0);
    this.circleMemberList.clear();
    this.adapterCircleMember.notifyDataSetChanged();
    this.getMembersTask = new GetCircleMemberTask(null);
    GetCircleMemberTask localGetCircleMemberTask = this.getMembersTask;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(0);
    arrayOfObject[1] = Integer.valueOf(20);
    localGetCircleMemberTask.execute(arrayOfObject);
  }

  private void getFirstPageNews()
  {
    if (this.circleNewsList.size() == 0)
      this.llytWait.setVisibility(0);
    this.getNewsTask = new GetCircleNewsTask(null);
    GetCircleNewsTask localGetCircleNewsTask = this.getNewsTask;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(0);
    arrayOfObject[1] = Integer.valueOf(20);
    localGetCircleNewsTask.execute(arrayOfObject);
  }

  private void getNextPageMember()
  {
    int i = -1 + this.circleMemberList.size();
    this.getMembersTask = new GetCircleMemberTask(null);
    GetCircleMemberTask localGetCircleMemberTask = this.getMembersTask;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(i);
    arrayOfObject[1] = Integer.valueOf(20);
    localGetCircleMemberTask.execute(arrayOfObject);
  }

  private void getNextPageNews()
  {
    int i = -1 + this.circleNewsList.size();
    this.getNewsTask = new GetCircleNewsTask(null);
    GetCircleNewsTask localGetCircleNewsTask = this.getNewsTask;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(i);
    arrayOfObject[1] = Integer.valueOf(20);
    localGetCircleNewsTask.execute(arrayOfObject);
  }

  private void initTabHost(View paramView)
  {
    this.tabHost = ((KXTopTabHost)paramView.findViewById(2131362053));
    if (this.mode == 0)
    {
      this.tabHost.setVisibility(0);
      this.tabHost.setOnTabChangeListener(this);
      KXTopTab localKXTopTab1 = new KXTopTab(getActivity());
      localKXTopTab1.setText(2131428079);
      this.tabHost.addTab(localKXTopTab1);
      KXTopTab localKXTopTab2 = new KXTopTab(getActivity());
      localKXTopTab2.setText(2131428080);
      this.tabHost.addTab(localKXTopTab2);
      this.tabHost.setCurrentTab(1);
      return;
    }
    this.tabHost.setVisibility(8);
  }

  private void initTitle(View paramView)
  {
    this.llytToolbar = ((LinearLayout)paramView.findViewById(2131362054));
    this.mLayoutWriteRecord = paramView.findViewById(2131362056);
    this.mLayoutUploadPhoto = paramView.findViewById(2131362055);
    TextView localTextView1 = (TextView)this.mLayoutWriteRecord.findViewById(2131362894);
    ImageView localImageView1 = (ImageView)this.mLayoutWriteRecord.findViewById(2131362893);
    localTextView1.setText(2131427397);
    localImageView1.setImageResource(2130837757);
    this.mLayoutWriteRecord.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (UploadTaskListEngine.getInstance().getWaitingTaskCount() < 20)
        {
          IntentUtil.showWriteCircleRecordActivity(CircleMainFragment.this.getActivity(), CircleMainFragment.this.gid);
          return;
        }
        CircleMainFragment.this.showCantUploadOptions();
      }
    });
    TextView localTextView2 = (TextView)this.mLayoutUploadPhoto.findViewById(2131362894);
    ImageView localImageView2 = (ImageView)this.mLayoutUploadPhoto.findViewById(2131362893);
    localTextView2.setText(2131428081);
    localImageView2.setImageResource(2130837756);
    this.mLayoutUploadPhoto.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (UploadTaskListEngine.getInstance().getWaitingTaskCount() < 20)
        {
          CircleMainFragment.this.showUploadPhotoOptions();
          return;
        }
        CircleMainFragment.this.showCantUploadOptions();
      }
    });
    this.btnLeft = ((ImageView)paramView.findViewById(2131362914));
    this.btnLeft.setOnClickListener(this);
    ImageView localImageView3 = (ImageView)paramView.findViewById(2131362919);
    localImageView3.setImageDrawable(CirclesAdapter.getCircleTypeImage(getActivity(), this.gtype));
    this.centerText = ((TextView)paramView.findViewById(2131362920));
    this.centerText.setVisibility(0);
    this.centerText.setText(this.title);
    localImageView3.setVisibility(8);
    this.btnRefresh = ((ImageView)paramView.findViewById(2131362928));
    this.btnRefresh.setImageResource(2130838834);
    this.btnRefresh.setOnClickListener(this);
    this.rightProBar = ((ProgressBar)paramView.findViewById(2131362929));
    if (this.mode == 0)
    {
      this.btnRefresh.setVisibility(0);
      this.llytToolbar.setVisibility(0);
      return;
    }
    this.btnRefresh.setVisibility(8);
    this.llytToolbar.setVisibility(8);
  }

  private void onCreateCircleNewsLongClicked(CircleNewsModel.CircleNewsItem paramCircleNewsItem)
  {
    if (paramCircleNewsItem == null)
      return;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = (paramCircleNewsItem.suser.realname + "的首页");
    if (this.mAlertDialog != null)
    {
      if (this.mAlertDialog.isShowing())
        this.mAlertDialog.dismiss();
      this.mAlertDialog = null;
    }
    this.mAlertDialog = DialogUtil.showSelectListDlg(getActivity(), 2131427509, arrayOfString, new DialogInterface.OnClickListener(paramCircleNewsItem)
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        IntentUtil.showHomeFragment(CircleMainFragment.this, this.val$info.suser.uid, this.val$info.suser.realname, this.val$info.suser.logo);
      }
    }
    , 1);
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

  private void onInviteNewsLongClicked(CircleNewsModel.CircleNewsItem paramCircleNewsItem)
  {
    if (paramCircleNewsItem == null)
      return;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = (paramCircleNewsItem.suser.realname + "的首页");
    if (this.mAlertDialog != null)
    {
      if (this.mAlertDialog.isShowing())
        this.mAlertDialog.dismiss();
      this.mAlertDialog = null;
    }
    this.mAlertDialog = DialogUtil.showSelectListDlg(getActivity(), 2131427509, arrayOfString, new DialogInterface.OnClickListener(paramCircleNewsItem)
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        IntentUtil.showHomeFragment(CircleMainFragment.this, this.val$info.suser.uid, this.val$info.suser.realname, this.val$info.suser.logo);
      }
    }
    , 1);
  }

  private void onTalkNewsLongClicked(CircleNewsModel.CircleNewsItem paramCircleNewsItem)
  {
    if (paramCircleNewsItem == null)
      return;
    String[] arrayOfString = new String[3];
    arrayOfString[0] = (paramCircleNewsItem.suser.realname + "的首页");
    arrayOfString[1] = "添加回复";
    arrayOfString[2] = "查看回复";
    if (this.mAlertDialog != null)
    {
      if (this.mAlertDialog.isShowing())
        this.mAlertDialog.dismiss();
      this.mAlertDialog = null;
    }
    this.mAlertDialog = DialogUtil.showSelectListDlg(getActivity(), 2131427509, arrayOfString, new DialogInterface.OnClickListener(paramCircleNewsItem)
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        if (paramInt == 0)
        {
          IntentUtil.showHomeFragment(CircleMainFragment.this, this.val$info.suser.uid, this.val$info.suser.realname, this.val$info.suser.logo);
          return;
        }
        if (paramInt == 1)
        {
          IntentUtil.viewCircleTalkRepliesDetail(CircleMainFragment.this.getActivity(), CircleMainFragment.this, this.val$info.tid, CircleMainFragment.this.gid, CircleMainFragment.this.gname);
          return;
        }
        IntentUtil.replyCircleTalk(CircleMainFragment.this.getActivity(), CircleMainFragment.this, CircleMainFragment.this.gid, this.val$info.stid);
      }
    }
    , 1);
  }

  private void pauseActivity()
  {
    if (this.activityStateNews == 1)
      this.getNewsTask.cancel(true);
  }

  private void refresh()
  {
    onDownloading(true);
    updateActivityState(1);
    stateInitOnCreate();
    if (this.activityTab == 3)
    {
      getFirstPageNews();
      return;
    }
    getFirstPageMember();
  }

  private void resumeActivity()
  {
    int[] arrayOfInt = this.tabIndexArray;
    int i;
    if (this.activityTab == 3)
    {
      i = 0;
      int j = arrayOfInt[i];
      this.lstvNewsOrMember.setSelection(j);
      if (getActivityState() != 0)
        break label118;
      updateActivityState(1);
      stateInitOnCreate();
      if (this.activityTab != 3)
        break label87;
      this.lstvNewsOrMember.setAdapter(this.adapterCircleNews);
      this.lstvNewsOrMember.setVisibility(0);
      this.adapterCircleNews.notifyDataSetChanged();
      getFirstPageNews();
    }
    label87: label118: 
    do
    {
      return;
      i = 1;
      break;
      this.lstvNewsOrMember.setAdapter(this.adapterCircleMember);
      this.lstvNewsOrMember.setVisibility(0);
      this.adapterCircleMember.notifyDataSetChanged();
      getFirstPageMember();
      return;
      if (getActivityState() != 1)
        continue;
      updateActivityState(2);
      setStateOnFetchFinished();
      return;
    }
    while (getActivityState() != 2);
    setStateOnFetchFinished();
  }

  private void setStateOnFetchFinished()
  {
    this.llytWait.setVisibility(8);
    onDownloading(false);
    if (this.activityTab == 3)
    {
      if (this.lstvNewsOrMember.getAdapter() != this.adapterCircleNews)
        this.lstvNewsOrMember.setAdapter(this.adapterCircleNews);
      this.lstvNewsOrMember.setVisibility(0);
      this.adapterCircleNews.notifyDataSetChanged();
      return;
    }
    if (this.lstvNewsOrMember.getAdapter() != this.adapterCircleMember)
      this.lstvNewsOrMember.setAdapter(this.adapterCircleMember);
    this.lstvNewsOrMember.setVisibility(0);
    this.adapterCircleMember.notifyDataSetChanged();
  }

  private void showInputVerify(String paramString)
  {
    getAddFriendUtil().addVerify(true, paramString);
  }

  private void showUploadPhotoOptions()
  {
    selectPictureFromGallery();
  }

  private void stateInitOnCreate()
  {
    this.lstvNewsOrMember.setVisibility(0);
  }

  private void updateActivityState(int paramInt)
  {
    if (this.activityTab == 3)
    {
      this.activityStateNews = paramInt;
      KXLog.d("maindebug", "set activityStateNews to " + paramInt);
      return;
    }
    this.activityStateMember = paramInt;
    KXLog.d("maindebug", "set activityStateMember to " + paramInt);
  }

  private void updateDataList(int paramInt)
  {
    int i = 1;
    int j = this.lstvNewsOrMember.getFirstVisiblePosition();
    if (paramInt == 3)
    {
      ArrayList localArrayList2 = CircleNewsModel.getInstance().getCircleNews();
      int m = CircleNewsModel.getInstance().total;
      this.circleNewsList.clear();
      if (localArrayList2 != null)
        this.circleNewsList.addAll(localArrayList2);
      if (this.circleNewsList.size() < m)
      {
        if (i != 0)
        {
          CircleNewsModel.CircleNewsItem localCircleNewsItem = new CircleNewsModel.CircleNewsItem();
          localCircleNewsItem.suser = null;
          this.circleNewsList.add(localCircleNewsItem);
        }
        this.adapterCircleNews.notifyDataSetChanged();
        if (j >= this.circleNewsList.size())
          break label126;
      }
      while (true)
      {
        this.lstvNewsOrMember.setSelection(j);
        return;
        i = 0;
        break;
        label126: j = 0;
      }
    }
    ArrayList localArrayList1 = CircleMemberModel.getInstance().getCircleMembers();
    int k = CircleMemberModel.getInstance().total;
    this.circleMemberList.clear();
    if (localArrayList1 != null)
      this.circleMemberList.addAll(localArrayList1);
    if (this.circleMemberList.size() < k)
    {
      if (i == 0)
        break label251;
      KaixinCircleMember localKaixinCircleMember1 = new KaixinCircleMember();
      localKaixinCircleMember1.uid = null;
      this.circleMemberList.add(localKaixinCircleMember1);
      label210: this.adapterCircleMember.setTotalItem(k);
      this.adapterCircleMember.notifyDataSetChanged();
      if (j >= this.circleMemberList.size())
        break label286;
    }
    while (true)
    {
      this.lstvNewsOrMember.setSelection(j);
      return;
      i = 0;
      break;
      label251: if (k <= 0)
        break label210;
      KaixinCircleMember localKaixinCircleMember2 = new KaixinCircleMember();
      localKaixinCircleMember2.uid = "-1";
      this.circleMemberList.add(localKaixinCircleMember2);
      break label210;
      label286: j = 0;
    }
  }

  public void beforeTabChanged(int paramInt)
  {
    this.tabIndexArray[paramInt] = this.lstvNewsOrMember.getFirstVisiblePosition();
  }

  protected void dealPhotoResult(String paramString1, String paramString2)
  {
    try
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("filePathName", paramString1);
      localBundle.putString("fileFrom", paramString2);
      IntentUtil.launchEditPhotoForResult(getActivity(), this, 104, localBundle);
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("CircleMainFragment", "dealPhotoResult", localException);
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
    if (paramMessage == null);
    while (true)
    {
      return true;
      if (paramMessage.what == 113)
      {
        addNewFriendResult((String)paramMessage.obj);
        return true;
      }
      if (paramMessage.what == 114)
      {
        String str = (String)paramMessage.obj;
        this.addFriendApplyChanges.put(str, new AddFriendResult(str, 0, null));
        this.adapterCircleMember.notifyDataSetChanged();
        return true;
      }
      if (paramMessage.what == 10007)
      {
        CircleRecordUploadTask localCircleRecordUploadTask2 = (CircleRecordUploadTask)paramMessage.obj;
        this.gid.equals(localCircleRecordUploadTask2.getGid());
        return true;
      }
      if (paramMessage.what != 10006)
        break;
      CircleRecordUploadTask localCircleRecordUploadTask1 = (CircleRecordUploadTask)paramMessage.obj;
      if ((!this.gid.equals(localCircleRecordUploadTask1.getGid())) || (this.activityTab != 3))
        continue;
      refresh();
      return true;
    }
    return super.handleMessage(paramMessage);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 != 104)
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
    Bundle localBundle;
    if (paramInt2 == -1)
    {
      localBundle = null;
      if (paramIntent != null)
        localBundle = paramIntent.getExtras();
      if (paramInt1 != 99)
        break label56;
      addNewFriendResult(localBundle.getString("fuid"));
    }
    while (true)
    {
      this.adapterCircleMember.notifyDataSetChanged();
      return;
      label56: if (paramInt1 == 9)
      {
        String str = localBundle.getString("stid");
        int i = localBundle.getInt("replynum", -1);
        ArrayList localArrayList = CircleNewsModel.getInstance().getCircleNews();
        int j = localArrayList.size();
        for (int k = 0; ; k++)
        {
          if (k >= j);
          while (true)
          {
            if (this.activityTab != 3)
              break label178;
            updateDataList(3);
            break;
            CircleNewsModel.CircleNewsItem localCircleNewsItem = (CircleNewsModel.CircleNewsItem)localArrayList.get(k);
            if (!str.equals(localCircleNewsItem.stid))
              break label180;
            if (-1 == i)
            {
              localCircleNewsItem.rnum = (1 + localCircleNewsItem.rnum);
              continue;
            }
            localCircleNewsItem.rnum = i;
          }
          break;
        }
      }
      label178: label180: if (paramInt1 != 104)
        continue;
      Intent localIntent = new Intent(getActivity(), UploadCirclePhotoFragment.class);
      localIntent.putExtra("gid", this.gid);
      startFragment(localIntent, true, 1);
    }
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
    super.onCreate(paramBundle);
  }

  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    super.onCreateOptionsMenu(paramMenu, paramMenuInflater);
    paramMenu.add(0, 401, 0, 2131427690).setIcon(2130838607);
    paramMenu.add(0, 402, 0, 2131427698).setIcon(2130838609);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903075, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if (this.mode == 0)
      UploadTaskListEngine.getInstance().unRegister(this.mHandler);
    if ((this.getNewsTask != null) && (this.getNewsTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.getNewsTask.cancel(true);
      if (CircleNewsEngine.getInstance() != null)
        CircleNewsEngine.getInstance().cancel();
    }
    super.onDestroy();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (this.mode == 1);
    do
      return;
    while ((this.activityTab == 4) || (paramInt < 0) || (paramInt >= this.circleNewsList.size()));
    CircleNewsModel.CircleNewsItem localCircleNewsItem = (CircleNewsModel.CircleNewsItem)this.circleNewsList.get(paramInt);
    IntentUtil.viewCircleTalkRepliesDetail(getActivity(), this, localCircleNewsItem.stid, this.gid, this.gname);
  }

  public boolean onItemLongClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (this.activityTab == 4)
      return true;
    CircleNewsModel.CircleNewsItem localCircleNewsItem;
    try
    {
      Vibrator localVibrator = (Vibrator)getActivity().getSystemService("vibrator");
      localVibrator.vibrate(1000L);
      localVibrator.vibrate(new long[] { 100L, 300L, 150L, 400L }, -1);
      if (this.circleNewsList == null)
        return false;
      if ((paramLong < 0L) || (paramLong >= this.circleNewsList.size()))
        break label196;
      localCircleNewsItem = (CircleNewsModel.CircleNewsItem)this.circleNewsList.get((int)paramLong);
      if (localCircleNewsItem.suser == null)
        return false;
      if (2 == localCircleNewsItem.talkType)
      {
        onCreateCircleNewsLongClicked(localCircleNewsItem);
        return true;
      }
    }
    catch (Exception localException)
    {
      KXLog.e("CircleMainFragment", "onListItemClick", localException);
      return true;
    }
    if (1 == localCircleNewsItem.talkType)
    {
      onInviteNewsLongClicked(localCircleNewsItem);
      return true;
    }
    if (localCircleNewsItem.talkType == 0)
    {
      onTalkNewsLongClicked(localCircleNewsItem);
      return true;
    }
    onCreateCircleNewsLongClicked(localCircleNewsItem);
    return true;
    label196: return false;
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

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
      return super.onOptionsItemSelected(paramMenuItem);
    case 401:
      refresh();
      return true;
    case 402:
      this.lstvNewsOrMember.setSelection(0);
      return true;
    case 403:
      IntentUtil.returnToDesktop(getActivity());
      return true;
    case 404:
    }
    IntentUtil.showMyHomeFragment(this);
    return true;
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

  public void onTabChanged(int paramInt)
  {
    if (paramInt == 0)
    {
      this.activityTab = 3;
      this.btnRefresh.setVisibility(0);
      this.llytToolbar.setVisibility(0);
    }
    while (true)
    {
      resumeActivity();
      this.adapterCircleMember.showLoadingFooter(false);
      this.adapterCircleNews.showLoadingFooter(false);
      KXLog.d("maindebug", this.lstvNewsOrMember.getAdapter().toString());
      return;
      this.activityTab = 4;
      this.btnRefresh.setVisibility(4);
      this.llytToolbar.setVisibility(8);
    }
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle == null)
      throw new IllegalArgumentException("Intent extras can not be null");
    this.gid = localBundle.getString("gid");
    this.gname = localBundle.getString("gname");
    this.gtype = localBundle.getInt("type");
    this.mode = localBundle.getInt("mode");
    if ((this.gid == null) || (this.gname == null))
      throw new IllegalArgumentException("gid and gname can not be null");
    if (this.mode == 0)
    {
      this.activityTab = 3;
      this.title = this.gname;
      UploadTaskListEngine.getInstance().register(this.mHandler);
    }
    while (true)
    {
      super.onCreate(paramBundle);
      getActivity().getWindow().setFlags(3, 3);
      this.lstvNewsOrMember = ((ListView)paramView.findViewById(2131362057));
      this.adapterCircleMember = new CircleMembersAdapter(this, 2130903076, this.circleMemberList, this.addFriendApplyChanges);
      this.lstvNewsOrMember.setAdapter(this.adapterCircleMember);
      this.adapterCircleNews = new CircleNewsAdapter(this, 2130903131, this.gid, this.circleNewsList, this.gname);
      this.lstvNewsOrMember.setOnItemClickListener(this);
      this.llytWait = ((LinearLayout)paramView.findViewById(2131361817));
      initTitle(paramView);
      initTabHost(paramView);
      return;
      this.activityTab = 4;
      String str = getString(2131428082);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.gname;
      this.title = String.format(str, arrayOfObject);
    }
  }

  public void onViewMoreClick()
  {
    if (this.activityTab == 3)
    {
      ArrayList localArrayList2 = CircleNewsModel.getInstance().getCircleNews();
      if ((localArrayList2 == null) || (localArrayList2.size() < this.circleNewsList.size()))
      {
        this.adapterCircleNews.showLoadingFooter(true);
        if ((localArrayList2 != null) && (localArrayList2.size() < CircleNewsModel.getInstance().total))
          getNextPageNews();
      }
    }
    while (true)
    {
      return;
      updateDataList(3);
      break;
      ArrayList localArrayList1 = CircleMemberModel.getInstance().getCircleMembers();
      if ((localArrayList1 == null) || (localArrayList1.size() < this.circleMemberList.size()))
        this.adapterCircleMember.showLoadingFooter(true);
      while ((localArrayList1 != null) && (localArrayList1.size() < CircleMemberModel.getInstance().total))
      {
        getNextPageMember();
        return;
        updateDataList(4);
      }
    }
  }

  private class GetCircleMemberTask extends KXFragment.KXAsyncTask<Object, Void, Integer>
  {
    private int number;
    private int start;

    private GetCircleMemberTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Object[] paramArrayOfObject)
    {
      if ((paramArrayOfObject == null) || (paramArrayOfObject.length == 0) || (paramArrayOfObject.length != 2))
        return null;
      this.start = ((Integer)paramArrayOfObject[0]).intValue();
      this.number = ((Integer)paramArrayOfObject[1]).intValue();
      try
      {
        int i = CircleMemberEngine.getInstance().doGetCircleMemberList(CircleMainFragment.this.getActivity().getApplicationContext(), CircleMainFragment.this.gid, this.start, this.number);
        if (i == 1)
        {
          ArrayList localArrayList = CircleMemberModel.getInstance().getCircleMembers();
          GetStrangerInfoEngine.getInstance().doGetCircleMemberAdditionInfo(CircleMainFragment.this.getActivity().getApplicationContext(), localArrayList);
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
      int i;
      if (CircleMainFragment.this.activityTab == 4)
      {
        i = 1;
        CircleMainFragment.this.activityStateMember = 2;
        if ((paramInteger != null) && (paramInteger.intValue() == 1))
          break label110;
        if (i != 0)
        {
          CircleMainFragment.this.adapterCircleMember.showLoadingFooter(false);
          if ((paramInteger == null) || (paramInteger.intValue() != -3002))
            break label91;
          Toast.makeText(CircleMainFragment.this.getActivity(), 2131427375, 0).show();
        }
      }
      while (true)
      {
        CircleMainFragment.this.setStateOnFetchFinished();
        return;
        i = 0;
        break;
        label91: Toast.makeText(CircleMainFragment.this.getActivity(), 2131427374, 0).show();
      }
      label110: if ((this.start == 0) || (CircleMainFragment.this.adapterCircleMember.isFooterShowLoading()))
      {
        CircleMainFragment.this.updateDataList(4);
        if (CircleMainFragment.this.circleMemberList.size() >= CircleMemberModel.getInstance().total)
          break label228;
      }
      label228: for (int j = 1; ; j = 0)
      {
        if ((this.start == 0) && (j != 0))
          CircleMainFragment.this.getNextPageMember();
        if (i == 0)
          break;
        if ((this.start == 0) && (CircleMainFragment.this.lstvNewsOrMember != null))
          CircleMainFragment.this.lstvNewsOrMember.setSelection(0);
        CircleMainFragment.this.adapterCircleMember.showLoadingFooter(false);
        CircleMainFragment.this.setStateOnFetchFinished();
        return;
      }
    }

    protected void onPreExecuteA()
    {
      CircleMainFragment.this.activityStateMember = 1;
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class GetCircleNewsTask extends KXFragment.KXAsyncTask<Object, Void, Integer>
  {
    private int number;
    private int start;

    private GetCircleNewsTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Object[] paramArrayOfObject)
    {
      if ((paramArrayOfObject == null) || (paramArrayOfObject.length == 0) || (paramArrayOfObject.length != 2))
        return null;
      this.start = ((Integer)paramArrayOfObject[0]).intValue();
      this.number = ((Integer)paramArrayOfObject[1]).intValue();
      try
      {
        Integer localInteger = Integer.valueOf(CircleNewsEngine.getInstance().doGetCircleNewsItemList(CircleMainFragment.this.getActivity().getApplicationContext(), CircleMainFragment.this.gid, this.start, this.number));
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      int i;
      if (CircleMainFragment.this.activityTab == 3)
      {
        i = 1;
        CircleMainFragment.this.activityStateNews = 2;
        if ((paramInteger != null) && (paramInteger.intValue() == 1))
          break label110;
        if (i != 0)
        {
          CircleMainFragment.this.adapterCircleNews.showLoadingFooter(false);
          if ((paramInteger == null) || (paramInteger.intValue() != -3002))
            break label91;
          Toast.makeText(CircleMainFragment.this.getActivity(), 2131427376, 0).show();
        }
      }
      while (true)
      {
        CircleMainFragment.this.setStateOnFetchFinished();
        return;
        i = 0;
        break;
        label91: Toast.makeText(CircleMainFragment.this.getActivity(), 2131427374, 0).show();
      }
      label110: CircleMainFragment.this.isViewLeastNews = true;
      if ((this.start == 0) || (CircleMainFragment.this.adapterCircleNews.isFooterShowLoading()))
      {
        CircleMainFragment.this.updateDataList(3);
        if (CircleMainFragment.this.circleNewsList.size() >= CircleNewsModel.getInstance().total)
          break label236;
      }
      label236: for (int j = 1; ; j = 0)
      {
        if ((this.start == 0) && (j != 0))
          CircleMainFragment.this.getNextPageNews();
        if (i == 0)
          break;
        if ((this.start == 0) && (CircleMainFragment.this.lstvNewsOrMember != null))
          CircleMainFragment.this.lstvNewsOrMember.setSelection(0);
        CircleMainFragment.this.adapterCircleNews.showLoadingFooter(false);
        CircleMainFragment.this.setStateOnFetchFinished();
        return;
      }
    }

    protected void onPreExecuteA()
    {
      CircleMainFragment.this.activityStateNews = 1;
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.CircleMainFragment
 * JD-Core Version:    0.6.0
 */