package com.kaixin001.fragment;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.KXEnvironment;
import com.kaixin001.activity.VerifyActivity;
import com.kaixin001.businesslogic.AddFriend;
import com.kaixin001.businesslogic.AddFriendResult;
import com.kaixin001.engine.NewFriend2Engine;
import com.kaixin001.engine.RecommandFriendEngine;
import com.kaixin001.model.FindFriendModel;
import com.kaixin001.model.StrangerModel;
import com.kaixin001.model.StrangerModel.Stranger;
import com.kaixin001.model.User;
import com.kaixin001.util.ContactUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import com.kaixin001.util.ImageUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.view.KXFrameImageView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FindFriendByContactsFragment extends BaseFragment
  implements View.OnClickListener
{
  private static final int REQUEST_CAPTCHA = 99;
  HashMap<String, AddFriendResult> addFriendApplyChanges = this.mModel.addFriendApplyChanges;
  private AddFriend addFriendUtil;
  private ListAdapter mAdapter;
  protected ImageView mBtnLeft;
  protected ImageView mBtnRight;
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  protected View mFooterView;
  private ArrayList<ContactItem> mListData = new ArrayList();
  private ListView mListView;
  private View mLoginLayout;
  private FindFriendModel mModel = FindFriendModel.getInstance();
  private GetDataTask mTask;
  protected TextView mTextRight;
  protected TextView mTopTitle;
  private TextView mTvEmpty = null;
  protected LinearLayout mWaitLayout;
  private ProgressBar mWaitingProBar;

  private void actionAddFriend(View paramView, boolean paramBoolean, StrangerModel.Stranger paramStranger)
  {
    AddFriendResult localAddFriendResult = new AddFriendResult(paramStranger.fuid, 0, "");
    localAddFriendResult.inProgress = true;
    this.addFriendApplyChanges.put(paramStranger.fuid, localAddFriendResult);
    ((ImageView)paramView).setImageResource(2130838214);
    if (paramBoolean)
    {
      getAddFriendUtil().addNewFriend(null, paramStranger.fuid);
      return;
    }
    getAddFriendUtil().addNewFriend("3", paramStranger.fuid);
  }

  private void actionInviteFriend(ContactItem paramContactItem)
  {
    Intent localIntent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + paramContactItem.mPhoneNum));
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("我是");
    localStringBuffer.append(User.getInstance().getRealName());
    localStringBuffer.append("，和我一起玩开心吧。开心客户端下载地址：http://ums.bz/JUVGdC/");
    localIntent.putExtra("sms_body", localStringBuffer.toString());
    startActivity(localIntent);
  }

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
        this.mAdapter.notifyDataSetChanged();
        return;
      }
      Toast.makeText(getActivity(), localNewFriend2Engine.strTipMsg, 0).show();
      this.addFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
      this.mAdapter.notifyDataSetChanged();
      return;
    case 1:
      this.addFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
      this.mAdapter.notifyDataSetChanged();
      return;
    case 2:
      this.addFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
      this.mAdapter.notifyDataSetChanged();
      return;
    case 3:
      showInputVerify(paramString);
      return;
    case 4:
      this.addFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
      this.mAdapter.notifyDataSetChanged();
      return;
    case 5:
      this.addFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
      this.mAdapter.notifyDataSetChanged();
      return;
    case 6:
      DialogUtil.showKXAlertDialog(getActivity(), localNewFriend2Engine.strTipMsg, 2131427517, 2131427587, new DialogInterface.OnClickListener(paramString)
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          FindFriendByContactsFragment.this.getAddFriendUtil().addNewFriend("3", this.val$fuid);
        }
      }
      , null);
      return;
    case 7:
    case 8:
      this.addFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
      this.mAdapter.notifyDataSetChanged();
      return;
    case 9:
    case 10:
      getAddFriendUtil().addVerify(false, paramString);
      return;
    case 11:
    case 12:
    }
    this.addFriendApplyChanges.put(paramString, new AddFriendResult(paramString, i, localNewFriend2Engine.strTipMsg));
    this.mAdapter.notifyDataSetChanged();
  }

  private void initData()
  {
    showWait(true);
    cancelTask(this.mTask);
    this.mTask = new GetDataTask();
    this.mTask.execute(new Void[0]);
  }

  private void initTitle(View paramView)
  {
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    this.mBtnRight = ((ImageView)findViewById(2131362928));
    this.mBtnRight.setVisibility(8);
    this.mTextRight = ((TextView)paramView.findViewById(2131362930));
    this.mTextRight.setVisibility(8);
    this.mBtnLeft = ((ImageView)paramView.findViewById(2131362914));
    this.mBtnLeft.setOnClickListener(this);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    localTextView.setVisibility(0);
    localTextView.setText(2131428606);
  }

  private void initView()
  {
    this.mListView = ((ListView)getView().findViewById(2131362128));
    if (this.mAdapter == null)
      this.mAdapter = new ListAdapter();
    this.mListView.setAdapter(this.mAdapter);
    this.mFooterView = getActivity().getLayoutInflater().inflate(2130903259, null);
    this.mFooterView.setOnClickListener(this);
    this.mFooterTV = ((TextView)this.mFooterView.findViewById(2131362073));
    this.mFooterTV.setText(2131427748);
    this.mFooterProBar = ((ProgressBar)this.mFooterView.findViewById(2131362072));
    this.mFooterProBar.setVisibility(4);
    this.mWaitLayout = ((LinearLayout)findViewById(2131362134));
    this.mTvEmpty = ((TextView)findViewById(2131362136));
    this.mWaitingProBar = ((ProgressBar)findViewById(2131362135));
    View localView1 = getView();
    this.mLoginLayout = localView1.findViewById(2131362129);
    View localView2 = this.mLoginLayout;
    if (User.getInstance().GetLookAround());
    for (int i = 0; ; i = 8)
    {
      localView2.setVisibility(i);
      ((Button)localView1.findViewById(2131362130)).setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          FindFriendByContactsFragment.this.showHideLoginLayoutAnimation(0);
        }
      });
      ((Button)localView1.findViewById(2131362131)).setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          FindFriendByContactsFragment.this.showHideLoginLayoutAnimation(1);
        }
      });
      return;
    }
  }

  private void readContacts(ArrayList<ContactItem> paramArrayList)
  {
    paramArrayList.clear();
    Cursor localCursor = null;
    try
    {
      localCursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
      while (true)
      {
        boolean bool = localCursor.moveToNext();
        if (!bool)
          return;
        ContactItem localContactItem = new ContactItem();
        localContactItem.mName = localCursor.getString(localCursor.getColumnIndex("display_name"));
        String str = localCursor.getString(localCursor.getColumnIndex("data1"));
        if (TextUtils.isEmpty(str))
          continue;
        if (str.startsWith("+86"))
          str = str.substring("+86".length());
        localContactItem.mPhoneNum = str;
        localContactItem.mIcon = localCursor.getLong(localCursor.getColumnIndex("contact_id"));
        paramArrayList.add(localContactItem);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
    throw localObject;
  }

  private void showInputVerify(String paramString)
  {
    getAddFriendUtil().addVerify(true, paramString);
  }

  private void showWait(boolean paramBoolean)
  {
    ListView localListView = this.mListView;
    int i;
    LinearLayout localLinearLayout;
    int j;
    if (paramBoolean)
    {
      i = 8;
      localListView.setVisibility(i);
      localLinearLayout = this.mWaitLayout;
      j = 0;
      if (!paramBoolean)
        break label43;
    }
    while (true)
    {
      localLinearLayout.setVisibility(j);
      return;
      i = 0;
      break;
      label43: j = 8;
    }
  }

  private void updateItemView(View paramView, ContactItem paramContactItem, int paramInt)
  {
    TextView localTextView1 = (TextView)paramView.findViewById(2131362330);
    ImageView localImageView1 = (ImageView)paramView.findViewById(2131362331);
    boolean bool;
    label72: label96: int k;
    label127: KXFrameImageView localKXFrameImageView;
    label142: TextView localTextView2;
    TextView localTextView3;
    TextView localTextView4;
    ImageView localImageView3;
    Button localButton;
    label553: AddFriendResult localAddFriendResult;
    int j;
    if (paramInt < -1 + this.mListData.size())
    {
      bool = ((ContactItem)this.mListData.get(paramInt + 1)).mFirst;
      if (!bool)
        break label638;
      paramView.findViewById(2131362343).setVisibility(8);
      if ((!paramContactItem.mFirst) || (paramInt <= 0))
        break label652;
      localTextView1.setPadding(0, dipToPx(2.0F), 0, 0);
      if (!paramContactItem.mFirst)
        break label678;
      localTextView1.setVisibility(0);
      localImageView1.setVisibility(0);
      if (paramContactItem.mStranger != null)
        break label670;
      k = 2131428614;
      localTextView1.setText(k);
      localImageView1.setBackgroundResource(2130838936);
      localKXFrameImageView = (KXFrameImageView)paramView.findViewById(2131362333);
      localKXFrameImageView.setFrameNinePatchResId(2130838101);
      localTextView2 = (TextView)paramView.findViewById(2131362337);
      ImageView localImageView2 = (ImageView)paramView.findViewById(2131362338);
      localTextView3 = (TextView)paramView.findViewById(2131362339);
      localTextView4 = (TextView)paramView.findViewById(2131362341);
      ((ImageView)paramView.findViewById(2131362340));
      ((TextView)paramView.findViewById(2131362342));
      localImageView3 = (ImageView)paramView.findViewById(2131362334);
      localImageView3.setPadding(0, 0, (int)(8.0F * KXEnvironment.DENSITY), 0);
      localButton = (Button)paramView.findViewById(2131362335);
      if (paramContactItem.mStranger == null)
        break label775;
      localImageView3.setVisibility(0);
      if (paramContactItem.mStranger.isMyFriend == 1)
        localImageView3.setVisibility(8);
      localButton.setVisibility(8);
      displayRoundPicture(localKXFrameImageView, paramContactItem.mStranger.flogo, ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
      3 local3 = new View.OnClickListener(paramContactItem)
      {
        public void onClick(View paramView)
        {
          if (User.getInstance().GetLookAround())
          {
            FindFriendByContactsFragment.this.showLoginDialog();
            return;
          }
          IntentUtil.showHomeFragment(FindFriendByContactsFragment.this, this.val$item.mStranger.fuid, this.val$item.mStranger.fname);
        }
      };
      localKXFrameImageView.setOnClickListener(local3);
      localTextView2.setText(paramContactItem.mStranger.fname);
      localImageView2.setVisibility(8);
      localTextView3.setVisibility(0);
      localTextView3.setText("(" + paramContactItem.mName + ")");
      4 local4 = new View.OnClickListener(paramContactItem)
      {
        public void onClick(View paramView)
        {
          if (User.getInstance().GetLookAround())
          {
            FindFriendByContactsFragment.this.showLoginDialog();
            return;
          }
          IntentUtil.showHomeFragment(FindFriendByContactsFragment.this, this.val$item.mStranger.fuid, this.val$item.mStranger.fname);
        }
      };
      localTextView2.setOnClickListener(local4);
      StringBuffer localStringBuffer = new StringBuffer();
      if (!TextUtils.isEmpty(paramContactItem.mStranger.city))
        localStringBuffer.append(paramContactItem.mStranger.city).append(" ");
      if (!TextUtils.isEmpty(paramContactItem.mStranger.company))
        localStringBuffer.append(paramContactItem.mStranger.company).append(" ");
      if (!TextUtils.isEmpty(paramContactItem.mStranger.school))
        localStringBuffer.append(paramContactItem.mStranger.school).append(" ");
      if (TextUtils.isEmpty(localStringBuffer.toString()))
        break label695;
      localTextView4.setText(localStringBuffer.toString());
      localTextView4.setVisibility(0);
      localAddFriendResult = (AddFriendResult)this.addFriendApplyChanges.get(paramContactItem.mStranger.fuid);
      if (localAddFriendResult != null)
        break label705;
      localImageView3.setImageResource(2130837519);
      j = 1;
    }
    while (true)
    {
      if (j != 0)
      {
        5 local5 = new View.OnClickListener(paramContactItem)
        {
          public void onClick(View paramView)
          {
            if (User.getInstance().GetLookAround())
            {
              FindFriendByContactsFragment.this.showLoginDialog();
              return;
            }
            FindFriendByContactsFragment.this.actionAddFriend(paramView, false, this.val$item.mStranger);
          }
        };
        localImageView3.setOnClickListener(local5);
      }
      return;
      int i = -1 + this.mListData.size();
      bool = false;
      if (paramInt != i)
        break;
      bool = true;
      break;
      label638: paramView.findViewById(2131362343).setVisibility(0);
      break label72;
      label652: localTextView1.setPadding(0, dipToPx(10.4F), 0, 0);
      break label96;
      label670: k = 2131428613;
      break label127;
      label678: localTextView1.setVisibility(8);
      localImageView1.setVisibility(8);
      break label142;
      label695: localTextView4.setVisibility(8);
      break label553;
      label705: if (localAddFriendResult.code == 1)
      {
        localImageView3.setImageResource(2130838118);
        localImageView3.setOnClickListener(null);
        j = 0;
        continue;
      }
      if (localAddFriendResult.inProgress)
      {
        localImageView3.setVisibility(0);
        localImageView3.setImageResource(2130838214);
        j = 0;
        continue;
      }
      localImageView3.setImageResource(2130837519);
      j = 1;
    }
    label775: localImageView3.setVisibility(8);
    localButton.setVisibility(0);
    Object localObject = null;
    if (paramContactItem.mIcon > 0L);
    while (true)
    {
      try
      {
        Uri localUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, paramContactItem.mIcon);
        localObject = BitmapFactory.decodeStream(ContactsContract.Contacts.openContactPhotoInputStream(getActivity().getContentResolver(), localUri));
        if (localObject != null)
          continue;
        localObject = ImageCache.getInstance().getLoadingBitmap(2130838228, 0, 0);
        Bitmap localBitmap2 = ImageUtil.getOtherShapeBitmap((Bitmap)localObject, "round");
        localObject = localBitmap2;
        localKXFrameImageView.setImageBitmap((Bitmap)localObject);
        localTextView2.setText(paramContactItem.mName);
        localTextView3.setVisibility(8);
        localTextView4.setVisibility(8);
        6 local6 = new View.OnClickListener(paramContactItem)
        {
          public void onClick(View paramView)
          {
            if (User.getInstance().GetLookAround())
            {
              FindFriendByContactsFragment.this.showLoginDialog();
              return;
            }
            FindFriendByContactsFragment.this.actionInviteFriend(this.val$item);
          }
        };
        localButton.setOnClickListener(local6);
        return;
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
        continue;
      }
      try
      {
        localObject = ImageCache.getInstance().getLoadingBitmap(2130838228, 0, 0);
        Bitmap localBitmap1 = ImageUtil.getOtherShapeBitmap((Bitmap)localObject, "round");
        localObject = localBitmap1;
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace();
      }
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
    int i;
    if (paramMessage == null)
      i = 1;
    boolean bool;
    do
    {
      return i;
      bool = isNeedReturn();
      i = 0;
    }
    while (bool);
    if (paramMessage.what == 113)
      addNewFriendResult((String)paramMessage.obj);
    while (true)
    {
      return super.handleMessage(paramMessage);
      if (paramMessage.what != 114)
        continue;
      String str = (String)paramMessage.obj;
      this.addFriendApplyChanges.put(str, new AddFriendResult(str, 0, null));
      this.mAdapter.notifyDataSetChanged();
    }
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt2 == -1) && (paramInt1 == 99) && (paramIntent != null) && (paramIntent.getExtras() != null))
    {
      String str = paramIntent.getExtras().getString("fuid");
      if (!TextUtils.isEmpty(str))
      {
        AddFriendResult localAddFriendResult = new AddFriendResult(str, 1, "");
        this.addFriendApplyChanges.put(str, localAddFriendResult);
        this.mAdapter.notifyDataSetChanged();
      }
    }
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }

  public void onClick(View paramView)
  {
    if (paramView == this.mBtnLeft)
      finish();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903087, paramViewGroup, false);
  }

  public void onDestroy()
  {
    super.onDestroy();
  }

  public void onResume()
  {
    super.onResume();
    View localView;
    if (this.mLoginLayout != null)
    {
      localView = this.mLoginLayout;
      if (!User.getInstance().GetLookAround())
        break label33;
    }
    label33: for (int i = 0; ; i = 8)
    {
      localView.setVisibility(i);
      return;
    }
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    initTitle(getView());
    initView();
    if (!dataInited())
      initData();
  }

  public void showHideLoginLayoutAnimation(int paramInt)
  {
    TranslateAnimation localTranslateAnimation = new TranslateAnimation(0.0F, 0.0F, 0.0F, this.mLoginLayout.getHeight());
    localTranslateAnimation.setDuration(200L);
    localTranslateAnimation.setAnimationListener(new Animation.AnimationListener(paramInt)
    {
      public void onAnimationEnd(Animation paramAnimation)
      {
        FindFriendByContactsFragment.this.mLoginLayout.setVisibility(4);
        FindFriendByContactsFragment.this.showLoginPage(this.val$type);
      }

      public void onAnimationRepeat(Animation paramAnimation)
      {
      }

      public void onAnimationStart(Animation paramAnimation)
      {
      }
    });
    this.mLoginLayout.startAnimation(localTranslateAnimation);
  }

  public void showLoginDialog()
  {
    showHideLoginLayoutAnimation(0);
  }

  public class ContactItem
  {
    boolean mFirst;
    long mIcon;
    String mName;
    String mPhoneNum;
    StrangerModel.Stranger mStranger;

    public ContactItem()
    {
    }
  }

  public class GetDataTask extends KXFragment.KXAsyncTask<Void, Void, Integer>
  {
    private ArrayList<FindFriendByContactsFragment.ContactItem> list = new ArrayList();
    private String phonenums;

    public GetDataTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Void[] paramArrayOfVoid)
    {
      try
      {
        FindFriendByContactsFragment.this.readContacts(this.list);
        if (this.list.size() > 0)
          ((FindFriendByContactsFragment.ContactItem)this.list.get(0)).mFirst = true;
        RecommandFriendEngine localRecommandFriendEngine = RecommandFriendEngine.getInstance();
        if (this.phonenums == null)
          this.phonenums = ContactUtil.getAllPhoneNumbers(FindFriendByContactsFragment.this.getActivity().getContentResolver());
        boolean bool = TextUtils.isEmpty(this.phonenums);
        i = 0;
        int j;
        Iterator localIterator1;
        if (!bool)
        {
          i = localRecommandFriendEngine.doGetRecommandFriendList(FindFriendByContactsFragment.this.getActivity().getApplicationContext(), 0, 200, this.phonenums);
          ArrayList localArrayList = StrangerModel.getInstance().getFriends();
          j = 0;
          localIterator1 = localArrayList.iterator();
          if (localIterator1.hasNext())
            break label179;
          if (this.list.size() > 0)
          {
            ((FindFriendByContactsFragment.ContactItem)this.list.get(0)).mFirst = true;
            ((FindFriendByContactsFragment.ContactItem)this.list.get(j)).mFirst = true;
          }
        }
        return Integer.valueOf(i);
        label179: StrangerModel.Stranger localStranger = (StrangerModel.Stranger)localIterator1.next();
        FindFriendByContactsFragment.ContactItem localContactItem1 = new FindFriendByContactsFragment.ContactItem(FindFriendByContactsFragment.this);
        localContactItem1.mStranger = localStranger;
        Iterator localIterator2;
        if (!TextUtils.isEmpty(localStranger.mobile))
        {
          localIterator2 = this.list.iterator();
          label231: if (localIterator2.hasNext())
            break label258;
        }
        while (true)
        {
          this.list.add(j, localContactItem1);
          j++;
          break;
          label258: FindFriendByContactsFragment.ContactItem localContactItem2 = (FindFriendByContactsFragment.ContactItem)localIterator2.next();
          if (!localStranger.mobile.equals(localContactItem2.mPhoneNum))
            break label231;
          localContactItem1.mName = localContactItem2.mName;
          this.list.remove(localContactItem2);
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          localException.printStackTrace();
          int i = 0;
        }
      }
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if ((FindFriendByContactsFragment.this.getActivity() == null) || (FindFriendByContactsFragment.this.getView() == null) || (paramInteger == null));
      do
      {
        return;
        FindFriendByContactsFragment.this.showWait(false);
        FindFriendByContactsFragment.this.mListData.addAll(this.list);
        FindFriendByContactsFragment.this.mAdapter.notifyDataSetChanged();
      }
      while (paramInteger.intValue() == 1);
      FindFriendByContactsFragment.this.showToast(2131427374);
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  public class ListAdapter extends BaseAdapter
  {
    public ListAdapter()
    {
    }

    public int getCount()
    {
      return FindFriendByContactsFragment.this.mListData.size();
    }

    public Object getItem(int paramInt)
    {
      return FindFriendByContactsFragment.this.mListData.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      FindFriendByContactsFragment.ContactItem localContactItem = (FindFriendByContactsFragment.ContactItem)FindFriendByContactsFragment.this.mListData.get(paramInt);
      FindFriendByContactsFragment.ViewHolder localViewHolder;
      if (paramView == null)
      {
        paramView = FindFriendByContactsFragment.this.getActivity().getLayoutInflater().inflate(2130903121, null);
        localViewHolder = new FindFriendByContactsFragment.ViewHolder(FindFriendByContactsFragment.this);
        localViewHolder.initView(paramView);
        paramView.setTag(localViewHolder);
      }
      while (true)
      {
        localViewHolder.update(paramView, localContactItem, paramInt);
        return paramView;
        localViewHolder = (FindFriendByContactsFragment.ViewHolder)paramView.getTag();
      }
    }
  }

  public class ViewHolder
  {
    public ViewHolder()
    {
    }

    public void initView(View paramView)
    {
    }

    public void update(View paramView, FindFriendByContactsFragment.ContactItem paramContactItem, int paramInt)
    {
      FindFriendByContactsFragment.this.updateItemView(paramView, paramContactItem, paramInt);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.FindFriendByContactsFragment
 * JD-Core Version:    0.6.0
 */