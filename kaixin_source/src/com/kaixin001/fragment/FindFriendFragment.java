package com.kaixin001.fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.VerifyActivity;
import com.kaixin001.businesslogic.AddFriend;
import com.kaixin001.businesslogic.AddFriendResult;
import com.kaixin001.engine.FindFriendEngine;
import com.kaixin001.engine.NewFriend2Engine;
import com.kaixin001.lbs.LocationService;
import com.kaixin001.lbs.RefreshLocationProxy;
import com.kaixin001.lbs.RefreshLocationProxy.IRefreshLocationCallback;
import com.kaixin001.model.FindFriendModel;
import com.kaixin001.model.StrangerModel.Stranger;
import com.kaixin001.model.User;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.UserHabitUtils;
import com.kaixin001.view.KXFrameImageView;
import java.util.ArrayList;
import java.util.HashMap;

public class FindFriendFragment extends BaseFragment
  implements View.OnClickListener
{
  private static final int FETCH_NUM = 10;
  private static final int REQUEST_CAPTCHA = 99;
  public static final int REQUEST_CODE_VIEW_ALL = 101;
  public static final int TYPE_HEAD = 1;
  public static final int TYPE_MAYBE_KNOW = 4;
  public static final int TYPE_NEARBY_PERSON = 3;
  public static final int TYPE_RECOMMEND_STAR = 2;
  HashMap<String, AddFriendResult> addFriendApplyChanges = this.mModel.addFriendApplyChanges;
  private AddFriend addFriendUtil;
  Location currentLocation;
  private boolean isRefreshLocation;
  private FindFriendAdapter mAdapter;
  protected ImageView mBtnLeft;
  protected ImageView mBtnRight;
  private int mCompleted = 1;
  private GetNearbyFriendTask mGetNearbyFriendTask;
  private GetRecommendFriendTask mGetRecommendFriendTask;
  private String mLat = "";
  private ArrayList<FindFriendItem> mListData = new ArrayList();
  private ListView mListView;
  private View mLoginLayout;
  private String mLon = "";
  private FindFriendModel mModel = FindFriendModel.getInstance();
  protected TextView mTextRight;
  protected TextView mTopTitle;
  private String mX = "";
  private String mY = "";
  private RefreshLocationProxy refreshLocationProxy;

  private void actionAddFriend(View paramView, boolean paramBoolean1, StrangerModel.Stranger paramStranger, boolean paramBoolean2)
  {
    if (User.getInstance().GetLookAround())
    {
      showLoginDialog();
      return;
    }
    AddFriendResult localAddFriendResult = new AddFriendResult(paramStranger.fuid, 0, "");
    localAddFriendResult.inProgress = true;
    this.addFriendApplyChanges.put(paramStranger.fuid, localAddFriendResult);
    ((ImageView)paramView).setImageResource(2130838214);
    if (paramBoolean1)
    {
      getAddFriendUtil().addNewFriend(null, paramStranger.fuid);
      return;
    }
    getAddFriendUtil().addNewFriend("3", paramStranger.fuid);
  }

  private void actionFindByContacts()
  {
    startFragmentForResultNew(new Intent(getActivity(), FindFriendByContactsFragment.class), 101, 1, true);
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
          FindFriendFragment.this.getAddFriendUtil().addNewFriend("3", this.val$fuid);
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

  private void getData()
  {
    cancelTask(this.mGetRecommendFriendTask);
    this.mGetRecommendFriendTask = new GetRecommendFriendTask();
    this.mGetRecommendFriendTask.execute(new Void[0]);
  }

  private RefreshLocationProxy getRefreshLocationProxy()
  {
    if (this.refreshLocationProxy == null)
      this.refreshLocationProxy = new RefreshLocationProxy(getActivity(), new RefreshLocationProxy.IRefreshLocationCallback()
      {
        public void onBeginRefreshLocation()
        {
          FindFriendFragment.this.isRefreshLocation = true;
        }

        public void onCancelRefreshLocation()
        {
          FindFriendFragment.this.isRefreshLocation = false;
        }

        public void onLocationAvailable(Location paramLocation)
        {
          FindFriendFragment.this.isRefreshLocation = false;
          FindFriendFragment.this.currentLocation = paramLocation;
          Location localLocation = LocationService.getLocationService().lastBestMapABCLocation;
          if (LocationService.isMapABCLocation(FindFriendFragment.this.currentLocation))
          {
            FindFriendFragment.this.mLon = "0";
            FindFriendFragment.this.mLat = "0";
            FindFriendFragment.this.mX = String.valueOf(FindFriendFragment.this.currentLocation.getLongitude());
            FindFriendFragment.this.mY = String.valueOf(FindFriendFragment.this.currentLocation.getLatitude());
            FindFriendFragment.this.cancelTask(FindFriendFragment.this.mGetNearbyFriendTask);
            FindFriendFragment.this.mGetNearbyFriendTask = new FindFriendFragment.GetNearbyFriendTask(FindFriendFragment.this);
            FindFriendFragment.GetNearbyFriendTask localGetNearbyFriendTask = FindFriendFragment.this.mGetNearbyFriendTask;
            String[] arrayOfString = new String[4];
            arrayOfString[0] = FindFriendFragment.access$16(FindFriendFragment.this);
            arrayOfString[1] = FindFriendFragment.access$17(FindFriendFragment.this);
            arrayOfString[2] = FindFriendFragment.access$18(FindFriendFragment.this);
            arrayOfString[3] = FindFriendFragment.access$19(FindFriendFragment.this);
            localGetNearbyFriendTask.execute(arrayOfString);
            return;
          }
          FindFriendFragment.this.mLon = String.valueOf(FindFriendFragment.this.currentLocation.getLongitude());
          FindFriendFragment.this.mLat = String.valueOf(FindFriendFragment.this.currentLocation.getLatitude());
          FindFriendFragment localFindFriendFragment1 = FindFriendFragment.this;
          String str1;
          label247: FindFriendFragment localFindFriendFragment2;
          String str2;
          if (localLocation == null)
          {
            str1 = null;
            localFindFriendFragment1.mX = str1;
            localFindFriendFragment2 = FindFriendFragment.this;
            str2 = null;
            if (localLocation != null)
              break label288;
          }
          while (true)
          {
            localFindFriendFragment2.mY = str2;
            break;
            str1 = String.valueOf(localLocation.getLongitude());
            break label247;
            label288: str2 = String.valueOf(localLocation.getLatitude());
          }
        }

        public void onLocationFailed()
        {
          FindFriendFragment.this.isRefreshLocation = false;
          FindFriendFragment.this.currentLocation = null;
          Toast.makeText(FindFriendFragment.this.getActivity(), 2131427722, 0).show();
        }
      });
    return this.refreshLocationProxy;
  }

  private void hideInputMethod()
  {
    View localView = getActivity().getCurrentFocus();
    if (localView != null)
      ((InputMethodManager)getActivity().getSystemService("input_method")).hideSoftInputFromWindow(localView.getWindowToken(), 2);
  }

  private void initView()
  {
    this.mListView = ((ListView)getView().findViewById(2131362311));
    this.mAdapter = new FindFriendAdapter();
    this.mListView.setAdapter(this.mAdapter);
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
          FindFriendFragment.this.showHideLoginLayoutAnimation(0);
        }
      });
      ((Button)localView1.findViewById(2131362131)).setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          FindFriendFragment.this.showHideLoginLayoutAnimation(1);
        }
      });
      return;
    }
  }

  private void refreshLocation(boolean paramBoolean)
  {
    this.currentLocation = null;
    getRefreshLocationProxy().refreshLocation(paramBoolean);
  }

  private void showAll(int paramInt)
  {
    if (paramInt == 2)
    {
      startFragmentForResultNew(new Intent(getActivity(), FindStarsFragment.class), 101, 1, true);
      return;
    }
    Intent localIntent = new Intent(getActivity(), FindFriendListFragment.class);
    int i;
    String str;
    if (paramInt == 2)
    {
      i = FindFriendListFragment.MODE_STAR;
      str = getActivity().getResources().getString(2131428610);
      UserHabitUtils.getInstance(getActivity()).addUserHabit("Show_All_Stars");
    }
    while (true)
    {
      localIntent.putExtra("mode", i);
      localIntent.putExtra("lable", str);
      localIntent.putExtra("lon", this.mLon);
      localIntent.putExtra("lat", this.mLat);
      localIntent.putExtra("x", this.mX);
      localIntent.putExtra("y", this.mY);
      startFragmentForResultNew(localIntent, 101, 1, true);
      return;
      if (paramInt == 3)
      {
        i = FindFriendListFragment.MODE_NEARBY;
        str = getActivity().getResources().getString(2131428611);
        UserHabitUtils.getInstance(getActivity()).addUserHabit("Show_All_Nearby");
        continue;
      }
      i = FindFriendListFragment.MODE_MAYBE;
      str = getActivity().getResources().getString(2131428612);
      UserHabitUtils.getInstance(getActivity()).addUserHabit("Show_All_Maybe_Know");
    }
  }

  private void showInputVerify(String paramString)
  {
    getAddFriendUtil().addVerify(true, paramString);
  }

  private void showPersons(ViewHolder paramViewHolder, int paramInt)
  {
    int i;
    ArrayList localArrayList;
    LinearLayout localLinearLayout;
    LayoutInflater localLayoutInflater;
    int j;
    if (paramInt == 2)
    {
      i = 2131428610;
      localArrayList = this.mModel.getRecommendStars();
      localLinearLayout = paramViewHolder.mLayoutItemsList;
      paramViewHolder.mTitle.setText(i);
      localLinearLayout.removeAllViews();
      localLayoutInflater = getActivity().getLayoutInflater();
      j = localArrayList.size();
      if (j > 3)
        j = 3;
    }
    for (int k = 0; ; k++)
    {
      if (k >= j)
      {
        paramViewHolder.mMore.setOnClickListener(new View.OnClickListener(paramInt)
        {
          public void onClick(View paramView)
          {
            FindFriendFragment.this.showAll(this.val$type);
          }
        });
        return;
        if (paramInt == 3)
        {
          i = 2131428611;
          localArrayList = this.mModel.getNearbyPerson();
          break;
        }
        i = 2131428612;
        localArrayList = this.mModel.getMaybeKnow();
        break;
      }
      StrangerModel.Stranger localStranger = (StrangerModel.Stranger)localArrayList.get(k);
      View localView = localLayoutInflater.inflate(2130903120, null);
      updateItemView(localView, paramInt, localStranger);
      localLinearLayout.addView(localView);
    }
  }

  private void showTopMethods(LinearLayout paramLinearLayout)
  {
    paramLinearLayout.removeAllViews();
    LayoutInflater localLayoutInflater = getActivity().getLayoutInflater();
    int[] arrayOfInt = { 2130837522, 2131428606, 2130837520, 2131428607, 2130837521, 2131428608 };
    int i = arrayOfInt.length / 2;
    int j = 0;
    if (j >= i)
      return;
    View localView = localLayoutInflater.inflate(2130903114, null);
    ImageView localImageView = (ImageView)localView.findViewById(2131362308);
    TextView localTextView = (TextView)localView.findViewById(2131362309);
    localImageView.setImageResource(arrayOfInt[(j * 2)]);
    localTextView.setText(arrayOfInt[(1 + j * 2)]);
    if (j == 0)
      localView.setBackgroundResource(2130838225);
    while (true)
    {
      localView.setTag(new Integer(j));
      localView.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          int i = ((Integer)paramView.getTag()).intValue();
          if (i == 0)
          {
            FindFriendFragment.this.actionFindByContacts();
            UserHabitUtils.getInstance(FindFriendFragment.this.getActivity()).addUserHabit("Find_Friend_By_Contacts");
          }
          do
          {
            return;
            if (i != 1)
              continue;
            if (User.getInstance().GetLookAround())
              FindFriendFragment.this.showLoginDialog();
            while (true)
            {
              UserHabitUtils.getInstance(FindFriendFragment.this.getActivity()).addUserHabit("Find_Classmate");
              return;
              Intent localIntent2 = new Intent(FindFriendFragment.this.getActivity(), FindClassmateColleagueFragment.class);
              localIntent2.putExtra("mode", 1);
              FindFriendFragment.this.startFragmentForResultNew(localIntent2, 101, 1, true);
            }
          }
          while (i != 2);
          if (User.getInstance().GetLookAround())
            FindFriendFragment.this.showLoginDialog();
          while (true)
          {
            UserHabitUtils.getInstance(FindFriendFragment.this.getActivity()).addUserHabit("Find_Colleague");
            return;
            Intent localIntent1 = new Intent(FindFriendFragment.this.getActivity(), FindClassmateColleagueFragment.class);
            localIntent1.putExtra("mode", 2);
            FindFriendFragment.this.startFragmentForResultNew(localIntent1, 101, 1, true);
          }
        }
      });
      paramLinearLayout.addView(localView);
      j++;
      break;
      if (j == i - 1)
      {
        localView.setBackgroundResource(2130838224);
        continue;
      }
      localView.setBackgroundResource(2130838223);
    }
  }

  private void updateItemView(View paramView, int paramInt, StrangerModel.Stranger paramStranger)
  {
    KXFrameImageView localKXFrameImageView = (KXFrameImageView)paramView.findViewById(2131362333);
    localKXFrameImageView.setFrameNinePatchResId(2130838101);
    displayRoundPicture(localKXFrameImageView, paramStranger.flogo, ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
    6 local6 = new View.OnClickListener(paramStranger)
    {
      public void onClick(View paramView)
      {
        IntentUtil.showHomeFragment(FindFriendFragment.this, this.val$item.fuid, this.val$item.fname);
      }
    };
    localKXFrameImageView.setOnClickListener(local6);
    TextView localTextView1 = (TextView)paramView.findViewById(2131362337);
    ImageView localImageView1 = (ImageView)paramView.findViewById(2131362338);
    TextView localTextView2 = (TextView)paramView.findViewById(2131362339);
    localTextView1.setText(paramStranger.fname);
    int i;
    TextView localTextView3;
    ImageView localImageView2;
    TextView localTextView4;
    label209: ImageView localImageView3;
    AddFriendResult localAddFriendResult;
    int j;
    if (paramInt == 2)
    {
      i = 0;
      localImageView1.setVisibility(i);
      localTextView2.setVisibility(8);
      7 local7 = new View.OnClickListener(paramStranger)
      {
        public void onClick(View paramView)
        {
          IntentUtil.showHomeFragment(FindFriendFragment.this, this.val$item.fuid, this.val$item.fname);
        }
      };
      localTextView1.setOnClickListener(local7);
      localTextView3 = (TextView)paramView.findViewById(2131362341);
      localImageView2 = (ImageView)paramView.findViewById(2131362340);
      localTextView4 = (TextView)paramView.findViewById(2131362342);
      if (paramInt != 2)
        break label285;
      localTextView3.setVisibility(0);
      localTextView3.setText(paramStranger.fans);
      localImageView2.setVisibility(8);
      localTextView4.setVisibility(8);
      localImageView3 = (ImageView)paramView.findViewById(2131362334);
      localAddFriendResult = (AddFriendResult)this.addFriendApplyChanges.get(paramStranger.fuid);
      if (localAddFriendResult != null)
        break label514;
      localImageView3.setImageResource(2130837519);
      j = 1;
    }
    while (true)
    {
      if (j != 0)
      {
        8 local8 = new View.OnClickListener(paramInt, paramStranger)
        {
          public void onClick(View paramView)
          {
            boolean bool1 = true;
            FindFriendFragment localFindFriendFragment = FindFriendFragment.this;
            boolean bool2;
            StrangerModel.Stranger localStranger;
            if (this.val$type == 2)
            {
              bool2 = bool1;
              localStranger = this.val$item;
              if (this.val$type == 2)
                break label49;
            }
            while (true)
            {
              localFindFriendFragment.actionAddFriend(paramView, bool2, localStranger, bool1);
              return;
              bool2 = false;
              break;
              label49: bool1 = false;
            }
          }
        };
        localImageView3.setOnClickListener(local8);
      }
      return;
      i = 8;
      break;
      label285: if (paramInt == 3)
      {
        localTextView3.setVisibility(0);
        localTextView3.setText(paramStranger.location);
        localImageView2.setVisibility(0);
        localTextView4.setVisibility(0);
        localTextView4.setText(paramStranger.distance);
        break label209;
      }
      StringBuffer localStringBuffer = new StringBuffer();
      if (!TextUtils.isEmpty(paramStranger.city))
        localStringBuffer.append(paramStranger.city).append(" ");
      if (!TextUtils.isEmpty(paramStranger.company))
        localStringBuffer.append(paramStranger.company).append(" ");
      if (!TextUtils.isEmpty(paramStranger.school))
        localStringBuffer.append(paramStranger.school).append(" ");
      if (!TextUtils.isEmpty(localStringBuffer.toString()))
      {
        localTextView3.setText(localStringBuffer.toString());
        localTextView3.setVisibility(0);
      }
      while (true)
      {
        localImageView2.setVisibility(8);
        if (paramStranger.sameFriendsNum <= 0)
          break label504;
        localTextView4.setVisibility(0);
        localTextView4.setText(paramStranger.sameFriendsNum + "位共同好友");
        break;
        localTextView3.setVisibility(8);
      }
      label504: localTextView4.setVisibility(8);
      break label209;
      label514: if (localAddFriendResult.code == 1)
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
  }

  private void updateListData()
  {
    this.mListData.clear();
    FindFriendItem localFindFriendItem1 = new FindFriendItem();
    localFindFriendItem1.mType = 1;
    this.mListData.add(localFindFriendItem1);
    if (this.mModel.getMaybeKnow().size() > 0)
    {
      FindFriendItem localFindFriendItem2 = new FindFriendItem();
      localFindFriendItem2.mType = 4;
      this.mListData.add(localFindFriendItem2);
    }
    if (this.mModel.getRecommendStars().size() > 0)
    {
      FindFriendItem localFindFriendItem3 = new FindFriendItem();
      localFindFriendItem3.mType = 2;
      this.mListData.add(localFindFriendItem3);
    }
    if (this.mModel.getNearbyPerson().size() > 0)
    {
      FindFriendItem localFindFriendItem4 = new FindFriendItem();
      localFindFriendItem4.mType = 3;
      this.mListData.add(localFindFriendItem4);
    }
    this.mAdapter.notifyDataSetChanged();
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
      this.addFriendApplyChanges.put(str, new AddFriendResult(str, 0, null));
      this.mAdapter.notifyDataSetChanged();
    }
  }

  protected void initTitle(View paramView)
  {
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    this.mBtnRight = ((ImageView)findViewById(2131362928));
    this.mBtnRight.setVisibility(8);
    this.mTextRight = ((TextView)paramView.findViewById(2131362930));
    this.mTextRight.setVisibility(0);
    this.mTextRight.setText(2131428605);
    Drawable localDrawable = getResources().getDrawable(2130837523);
    this.mTextRight.setCompoundDrawablesWithIntrinsicBounds(localDrawable, null, null, null);
    this.mTextRight.setCompoundDrawablePadding(0);
    this.mTextRight.setOnClickListener(this);
    if (this.mCompleted == 1)
      this.mTextRight.setPadding(dipToPx(7.4F), 0, dipToPx(12.0F), 0);
    TextView localTextView1;
    while (true)
    {
      findViewById(2131362926).setVisibility(0);
      this.mBtnLeft = ((ImageView)paramView.findViewById(2131362914));
      this.mBtnLeft.setOnClickListener(this);
      localTextView1 = (TextView)paramView.findViewById(2131362920);
      TextView localTextView2 = (TextView)findViewById(2131362922);
      View localView1 = findViewById(2131362914);
      View localView2 = findViewById(2131362916);
      View localView3 = findViewById(2131362917);
      if (this.mCompleted == 1)
        break;
      localView1.setVisibility(8);
      localView2.setVisibility(8);
      localView3.setVisibility(8);
      localTextView1.setVisibility(8);
      localTextView2.setVisibility(0);
      localTextView2.setText(2131428604);
      this.mTextRight.setText(2131428601);
      this.mTextRight.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
      this.mTextRight.setCompoundDrawablePadding(0);
      return;
      this.mTextRight.setPadding(dipToPx(7.4F), 0, dipToPx(10.0F), 0);
    }
    localTextView1.setVisibility(0);
    localTextView1.setText(2131428604);
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
    {
      hideInputMethod();
      Bundle localBundle = getArguments();
      if ((localBundle != null) && (localBundle.getString("from") != null) && (localBundle.getString("from").equals("KaixinMenuListFragment")))
        if (super.isMenuListVisibleInMain())
          super.showSubActivityInMain();
    }
    do
    {
      return;
      super.showMenuListInMain();
      return;
      finish();
      return;
    }
    while (paramView != this.mTextRight);
    if (this.mCompleted != 1)
    {
      IntentUtil.returnToDesktop(getActivity(), false);
      return;
    }
    if (User.getInstance().GetLookAround())
    {
      showLoginDialog();
      return;
    }
    Intent localIntent = new Intent(getActivity(), FindFriendListFragment.class);
    localIntent.putExtra("mode", FindFriendListFragment.MODE_SEARCH_NAME);
    startFragmentForResultNew(localIntent, 101, 1, true);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle != null)
      this.mCompleted = localBundle.getInt("complete", 1);
    if (this.mCompleted != 1)
      this.mBackKeyNotify = true;
    UserHabitUtils.getInstance(getActivity()).addUserHabit("Find_Friend_Page");
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903115, paramViewGroup, false);
  }

  public void onDestroy()
  {
    FindFriendModel.clearData();
    super.onDestroy();
  }

  public void onDestroyView()
  {
    cancelTask(this.mGetRecommendFriendTask);
    cancelTask(this.mGetNearbyFriendTask);
    super.onDestroyView();
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
    updateListData();
    if (!dataInited())
    {
      refreshLocation(false);
      getData();
    }
  }

  public void requestFinish()
  {
    if (this.mCompleted == 1)
    {
      super.requestFinish();
      return;
    }
    DialogUtil.showMsgDlg(getActivity(), 2131428628, 2131428629, 2131428631, 2131428630, null, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        FindFriendFragment.this.getActivity().finish();
      }
    });
  }

  public void showHideLoginLayoutAnimation(int paramInt)
  {
    TranslateAnimation localTranslateAnimation = new TranslateAnimation(0.0F, 0.0F, 0.0F, this.mLoginLayout.getHeight());
    localTranslateAnimation.setDuration(200L);
    localTranslateAnimation.setAnimationListener(new Animation.AnimationListener(paramInt)
    {
      public void onAnimationEnd(Animation paramAnimation)
      {
        FindFriendFragment.this.mLoginLayout.setVisibility(4);
        FindFriendFragment.this.showLoginPage(this.val$type);
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

  public class FindFriendAdapter extends BaseAdapter
  {
    public FindFriendAdapter()
    {
    }

    public int getCount()
    {
      return FindFriendFragment.this.mListData.size();
    }

    public Object getItem(int paramInt)
    {
      return FindFriendFragment.this.mListData.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      FindFriendFragment.FindFriendItem localFindFriendItem = (FindFriendFragment.FindFriendItem)FindFriendFragment.this.mListData.get(paramInt);
      FindFriendFragment.ViewHolder localViewHolder;
      if (paramView == null)
      {
        paramView = FindFriendFragment.this.getActivity().getLayoutInflater().inflate(2130903116, null);
        localViewHolder = new FindFriendFragment.ViewHolder(FindFriendFragment.this);
        localViewHolder.mLayoutHeadMethods = ((LinearLayout)paramView.findViewById(2131362312));
        localViewHolder.mLayoutTypes = ((LinearLayout)paramView.findViewById(2131362313));
        localViewHolder.mLayoutItemsList = ((LinearLayout)paramView.findViewById(2131362315));
        localViewHolder.mTitle = ((TextView)paramView.findViewById(2131362314));
        localViewHolder.mMore = ((TextView)paramView.findViewById(2131362316));
        paramView.setTag(localViewHolder);
        if (localFindFriendItem.mType != 1)
          break label212;
        localViewHolder.mLayoutHeadMethods.setVisibility(0);
        localViewHolder.mLayoutTypes.setVisibility(8);
        FindFriendFragment.this.showTopMethods(localViewHolder.mLayoutHeadMethods);
      }
      while (true)
      {
        if (paramInt != -1 + FindFriendFragment.this.mListData.size())
          break label248;
        paramView.setPadding(0, 0, 0, FindFriendFragment.this.dipToPx(13.4F));
        return paramView;
        localViewHolder = (FindFriendFragment.ViewHolder)paramView.getTag();
        break;
        label212: localViewHolder.mLayoutHeadMethods.setVisibility(8);
        localViewHolder.mLayoutTypes.setVisibility(0);
        FindFriendFragment.this.showPersons(localViewHolder, localFindFriendItem.mType);
      }
      label248: paramView.setPadding(0, 0, 0, 0);
      return paramView;
    }
  }

  public class FindFriendItem
  {
    Object mData;
    int mType;

    public FindFriendItem()
    {
    }
  }

  public class GetNearbyFriendTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    public GetNearbyFriendTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      try
      {
        new FindFriendEngine().getNearbyPersonList(FindFriendFragment.this.getActivity(), FindFriendFragment.this.mModel, paramArrayOfString[0], paramArrayOfString[1], paramArrayOfString[2], paramArrayOfString[3], 0, 10);
        Integer localInteger = Integer.valueOf(1);
        return localInteger;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return Integer.valueOf(0);
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if ((FindFriendFragment.this.getActivity() == null) || (FindFriendFragment.this.getView() == null))
        return;
      FindFriendFragment.this.updateListData();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  public class GetRecommendFriendTask extends AsyncTask<Void, Void, Integer>
  {
    public GetRecommendFriendTask()
    {
    }

    protected Integer doInBackground(Void[] paramArrayOfVoid)
    {
      try
      {
        FindFriendEngine localFindFriendEngine = new FindFriendEngine();
        localFindFriendEngine.getMaybeKnowList(FindFriendFragment.this.getActivity(), FindFriendFragment.this.mModel, 0, 10);
        publishProgress(new Void[0]);
        localFindFriendEngine.getRecommandStarList(FindFriendFragment.this.getActivity(), FindFriendFragment.this.mModel, 0, 10, null);
        Integer localInteger = Integer.valueOf(1);
        return localInteger;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return Integer.valueOf(0);
    }

    protected void onPostExecute(Integer paramInteger)
    {
      if ((FindFriendFragment.this.getActivity() == null) || (FindFriendFragment.this.getView() == null))
        return;
      FindFriendFragment.this.updateListData();
    }

    protected void onProgressUpdate(Void[] paramArrayOfVoid)
    {
      if ((FindFriendFragment.this.getActivity() == null) || (FindFriendFragment.this.getView() == null))
        return;
      FindFriendFragment.this.updateListData();
    }
  }

  public class ViewHolder
  {
    LinearLayout mLayoutHeadMethods;
    LinearLayout mLayoutItemsList;
    LinearLayout mLayoutTypes;
    TextView mMore;
    TextView mTitle;

    public ViewHolder()
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.FindFriendFragment
 * JD-Core Version:    0.6.0
 */