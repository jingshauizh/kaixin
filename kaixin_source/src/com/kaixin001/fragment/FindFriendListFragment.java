package com.kaixin001.fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
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
import com.kaixin001.engine.FindFriendEngine;
import com.kaixin001.engine.FindFriendEngine.Findby;
import com.kaixin001.engine.NewFriend2Engine;
import com.kaixin001.lbs.cell.NetworkRequest;
import com.kaixin001.model.FindFriendModel;
import com.kaixin001.model.StrangerModel.Stranger;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.view.KXFrameImageView;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

public class FindFriendListFragment extends BaseFragment
  implements View.OnClickListener, AdapterView.OnItemClickListener
{
  private static final int FETCH_NUM = 20;
  public static int MODE_COMMON = 0;
  public static int MODE_MAYBE = 0;
  public static int MODE_NEARBY = 0;
  public static int MODE_SEARCH_CLASSMATE_COLLEAGUE = 0;
  public static int MODE_SEARCH_NAME = 0;
  public static int MODE_STAR = 0;
  private static final int REQUEST_CAPTCHA = 99;
  HashMap<String, AddFriendResult> addFriendApplyChanges = this.mModel.addFriendApplyChanges;
  private AddFriend addFriendUtil;
  private FindFriendListAdapter mAdapter;
  private ImageView mBtnDelete;
  private ImageView mBtnLeft;
  private ImageView mBtnRight;
  private String mCollege = "";
  private int mCollegeIndex = 0;
  private ArrayList<String> mCollegeList = new ArrayList();
  private String mCompany = "";
  private int mCurSelect = -1;
  private View mEmptyView;
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  protected View mFooterView;
  private boolean mHasmore = false;
  private String mLable;
  private String mLat;
  private ArrayList<StrangerModel.Stranger> mListData = new ArrayList();
  private ListView mListView;
  private String mLon;
  private int mMode;
  private FindFriendModel mModel = FindFriendModel.getInstance();
  private NameListAdapter mNameListAdapter;
  private ListView mNameListView;
  private View mNameListViewLayout;
  private String mSchool = "";
  private EditText mSearchEdittext;
  private String mSearchText = "";
  private TextView mSelectCollege;
  private View mSelectCollegeLayout;
  private ImageView mSelectCollegeTag;
  private TextView mSelectYear;
  private ImageView mSelectYearTag;
  private String mStarType;
  private GetListTask mTask;
  private TextView mTextRight;
  private TextView mTopTitle;
  private TextView mTvEmpty = null;
  private String mUniversityId = "";
  protected LinearLayout mWaitLayout;
  private ProgressBar mWaitingProBar;
  private String mX;
  private String mY;
  private String mYear = "";
  private int mYearIndex = 0;
  private ArrayList<String> mYearList = new ArrayList();

  static
  {
    MODE_NEARBY = 2;
    MODE_MAYBE = 3;
    MODE_SEARCH_CLASSMATE_COLLEAGUE = 4;
    MODE_COMMON = 5;
    MODE_SEARCH_NAME = 6;
  }

  private void actionAddFriend(View paramView, boolean paramBoolean1, StrangerModel.Stranger paramStranger, boolean paramBoolean2)
  {
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
          FindFriendListFragment.this.getAddFriendUtil().addNewFriend("3", this.val$fuid);
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

  private void cancelSearch()
  {
    if (this.mTask != null)
      this.mTask.cancelRequest();
    showWait(false);
    cancelTask(this.mTask);
    this.mSearchText = "";
    this.mSearchEdittext.setText(this.mSearchText);
  }

  private void getMoreData()
  {
    cancelTask(this.mTask);
    showLoadingFooter(true);
    this.mTask = new GetListTask();
    GetListTask localGetListTask = this.mTask;
    Integer[] arrayOfInteger = new Integer[1];
    if (this.mHasmore);
    for (int i = -1 + this.mListData.size(); ; i = this.mListData.size())
    {
      arrayOfInteger[0] = Integer.valueOf(i);
      localGetListTask.execute(arrayOfInteger);
      return;
    }
  }

  private void hideNameList()
  {
    this.mNameListViewLayout.setVisibility(8);
  }

  private void initData()
  {
    if (this.mMode == MODE_SEARCH_CLASSMATE_COLLEAGUE)
      startSearch(null);
    do
      return;
    while ((this.mMode != MODE_STAR) || (TextUtils.isEmpty(this.mStarType)));
    showWait(true);
    getMoreData();
  }

  private void initTitle(View paramView)
  {
    if (this.mMode == MODE_SEARCH_NAME)
    {
      paramView.findViewById(2131362121).setVisibility(8);
      paramView.findViewById(2131362120).setVisibility(0);
      this.mBtnLeft = ((ImageView)paramView.findViewById(2131362933));
      this.mBtnLeft.setOnClickListener(this);
      this.mSearchEdittext = ((EditText)paramView.findViewById(2131362937));
      this.mBtnDelete = ((ImageView)paramView.findViewById(2131362938));
      this.mBtnDelete.setOnClickListener(this);
      this.mSearchEdittext.setText(this.mSearchText);
      this.mSearchEdittext.addTextChangedListener(new TextWatcher()
      {
        public void afterTextChanged(Editable paramEditable)
        {
        }

        public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
        {
        }

        public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
        {
          if (FindFriendListFragment.this.isNeedReturn());
          do
          {
            String str;
            do
            {
              do
                return;
              while (FindFriendListFragment.this.mSearchEdittext == null);
              str = FindFriendListFragment.this.mSearchEdittext.getText().toString();
              if (str == null)
                str = "";
              if (FindFriendListFragment.this.mSearchText != null)
                continue;
              FindFriendListFragment.this.mSearchText = "";
            }
            while (FindFriendListFragment.this.mSearchText.equals(str));
            FindFriendListFragment.this.mSearchText = str;
          }
          while (FindFriendListFragment.this.mSearchText.length() <= 1);
          FindFriendListFragment.this.startSearch(FindFriendListFragment.this.mSearchText);
        }
      });
      this.mSearchEdittext.requestFocus();
      showKeyboard();
      return;
    }
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    this.mBtnRight = ((ImageView)findViewById(2131362928));
    this.mBtnRight.setVisibility(8);
    this.mTextRight = ((TextView)paramView.findViewById(2131362930));
    this.mTextRight.setVisibility(8);
    this.mBtnLeft = ((ImageView)paramView.findViewById(2131362914));
    this.mBtnLeft.setOnClickListener(this);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    localTextView.setVisibility(0);
    localTextView.setText(this.mLable);
  }

  private void initView()
  {
    this.mListView = ((ListView)getView().findViewById(2131362128));
    if (this.mAdapter == null)
      this.mAdapter = new FindFriendListAdapter();
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
    if (this.mMode == MODE_SEARCH_NAME)
      this.mWaitLayout.setPadding(0, (int)(10.0F * KXEnvironment.DENSITY), 0, (int)(200.0F * KXEnvironment.DENSITY));
    this.mEmptyView = getActivity().getLayoutInflater().inflate(2130903122, null);
    this.mNameListViewLayout = findViewById(2131362132);
    this.mNameListView = ((ListView)findViewById(2131362133));
    this.mSelectCollegeLayout = findViewById(2131362123);
    this.mSelectCollege = ((TextView)findViewById(2131362124));
    this.mSelectCollegeTag = ((ImageView)findViewById(2131362125));
    this.mSelectYear = ((TextView)findViewById(2131362126));
    this.mSelectYearTag = ((ImageView)findViewById(2131362127));
    this.mSelectCollege.setOnClickListener(this);
    this.mSelectYear.setOnClickListener(this);
    updateSelectCollegueView();
    if (!TextUtils.isEmpty(this.mUniversityId))
    {
      this.mSelectCollegeLayout.setVisibility(0);
      this.mNameListAdapter = new NameListAdapter();
      this.mNameListView.setAdapter(this.mNameListAdapter);
      this.mNameListView.setOnItemClickListener(this);
    }
  }

  private void showInputVerify(String paramString)
  {
    getAddFriendUtil().addVerify(true, paramString);
  }

  private void showLoadingFooter(boolean paramBoolean)
  {
    ProgressBar localProgressBar;
    if (this.mFooterProBar != null)
    {
      localProgressBar = this.mFooterProBar;
      if (!paramBoolean)
        break label67;
    }
    label67: for (int j = 0; ; j = 4)
    {
      localProgressBar.setVisibility(j);
      if (this.mFooterTV != null)
      {
        int i = getResources().getColor(2130839419);
        if (paramBoolean)
          i = getResources().getColor(2130839395);
        this.mFooterTV.setTextColor(i);
      }
      return;
    }
  }

  private void showNameList()
  {
    this.mNameListViewLayout.setVisibility(0);
    this.mNameListAdapter.notifyDataSetChanged();
    if (this.mCurSelect == 0)
    {
      ListView localListView2 = this.mNameListView;
      int k = this.mCollegeIndex;
      int m = 0;
      if (k >= 0)
        m = this.mCollegeIndex;
      localListView2.setSelection(m);
      return;
    }
    ListView localListView1 = this.mNameListView;
    int i = this.mYearIndex;
    int j = 0;
    if (i >= 0)
      j = this.mYearIndex;
    localListView1.setSelection(j);
  }

  private void startSearch(String paramString)
  {
    if (this.mTask != null)
      this.mTask.cancelRequest();
    cancelTask(this.mTask);
    showWait(true);
    this.mSearchText = paramString;
    this.mTask = new GetListTask();
    GetListTask localGetListTask = this.mTask;
    Integer[] arrayOfInteger = new Integer[1];
    arrayOfInteger[0] = Integer.valueOf(0);
    localGetListTask.execute(arrayOfInteger);
  }

  private void updateItemView(View paramView, int paramInt, StrangerModel.Stranger paramStranger)
  {
    KXFrameImageView localKXFrameImageView = (KXFrameImageView)paramView.findViewById(2131362333);
    localKXFrameImageView.setFrameNinePatchResId(2130838101);
    displayRoundPicture(localKXFrameImageView, paramStranger.flogo, ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
    2 local2 = new View.OnClickListener(paramStranger)
    {
      public void onClick(View paramView)
      {
        IntentUtil.showHomeFragment(FindFriendListFragment.this, this.val$item.fuid, this.val$item.fname);
      }
    };
    localKXFrameImageView.setOnClickListener(local2);
    TextView localTextView1 = (TextView)paramView.findViewById(2131362337);
    ImageView localImageView1 = (ImageView)paramView.findViewById(2131362338);
    TextView localTextView2 = (TextView)paramView.findViewById(2131362339);
    localTextView1.setText(paramStranger.fname);
    int i;
    TextView localTextView3;
    ImageView localImageView2;
    TextView localTextView4;
    label213: ImageView localImageView3;
    AddFriendResult localAddFriendResult;
    int j;
    if (paramInt == MODE_STAR)
    {
      i = 0;
      localImageView1.setVisibility(i);
      localTextView2.setVisibility(8);
      3 local3 = new View.OnClickListener(paramStranger)
      {
        public void onClick(View paramView)
        {
          IntentUtil.showHomeFragment(FindFriendListFragment.this, this.val$item.fuid, this.val$item.fname);
        }
      };
      localTextView1.setOnClickListener(local3);
      localTextView3 = (TextView)paramView.findViewById(2131362341);
      localImageView2 = (ImageView)paramView.findViewById(2131362340);
      localTextView4 = (TextView)paramView.findViewById(2131362342);
      if (paramInt != MODE_STAR)
        break label343;
      localTextView3.setVisibility(0);
      localTextView3.setText(paramStranger.fans);
      localImageView2.setVisibility(8);
      localTextView4.setVisibility(8);
      localImageView3 = (ImageView)paramView.findViewById(2131362334);
      localImageView3.setPadding(0, 0, (int)(8.0F * KXEnvironment.DENSITY), 0);
      localAddFriendResult = (AddFriendResult)this.addFriendApplyChanges.get(paramStranger.fuid);
      if (localAddFriendResult != null)
        break label574;
      localImageView3.setImageResource(2130837519);
      j = 1;
    }
    while (true)
    {
      if (j != 0)
      {
        4 local4 = new View.OnClickListener(paramInt, paramStranger)
        {
          public void onClick(View paramView)
          {
            boolean bool1 = true;
            FindFriendListFragment localFindFriendListFragment = FindFriendListFragment.this;
            boolean bool2;
            StrangerModel.Stranger localStranger;
            if (this.val$type == FindFriendListFragment.MODE_STAR)
            {
              bool2 = bool1;
              localStranger = this.val$item;
              if (this.val$type == FindFriendListFragment.MODE_STAR)
                break label53;
            }
            while (true)
            {
              localFindFriendListFragment.actionAddFriend(paramView, bool2, localStranger, bool1);
              return;
              bool2 = false;
              break;
              label53: bool1 = false;
            }
          }
        };
        localImageView3.setOnClickListener(local4);
      }
      if (paramStranger.isMyFriend == 1)
        localImageView3.setVisibility(8);
      paramView.findViewById(2131362331).setVisibility(8);
      paramView.findViewById(2131362343).setVisibility(0);
      return;
      i = 8;
      break;
      label343: if (paramInt == MODE_NEARBY)
      {
        localTextView3.setVisibility(0);
        localTextView3.setText(paramStranger.location);
        localImageView2.setVisibility(0);
        localTextView4.setVisibility(0);
        localTextView4.setText(paramStranger.distance);
        break label213;
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
          break label564;
        localTextView4.setVisibility(0);
        localTextView4.setText(paramStranger.sameFriendsNum + "位共同好友");
        break;
        localTextView3.setVisibility(8);
      }
      label564: localTextView4.setVisibility(8);
      break label213;
      label574: if (localAddFriendResult.code == 1)
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
    if (this.mMode == MODE_STAR)
    {
      this.mListData.clear();
      this.mListData.addAll(this.mModel.getRecommendCategoryStars());
      this.mHasmore = this.mModel.mHasmoreCategoryStar;
    }
    while (true)
    {
      if (this.mHasmore)
      {
        StrangerModel.Stranger localStranger = new StrangerModel.Stranger();
        localStranger.fuid = "";
        this.mListData.add(localStranger);
      }
      return;
      if (this.mMode == MODE_NEARBY)
      {
        this.mListData.clear();
        this.mListData.addAll(this.mModel.getNearbyPerson());
        this.mHasmore = this.mModel.mHasmoreNearbyPerson;
        continue;
      }
      if (this.mMode == MODE_MAYBE)
      {
        this.mListData.clear();
        this.mListData.addAll(this.mModel.getMaybeKnow());
        this.mHasmore = this.mModel.mHasmoreMaybeKnow;
        continue;
      }
      if ((this.mMode != MODE_SEARCH_NAME) && (this.mMode != MODE_SEARCH_CLASSMATE_COLLEAGUE))
        continue;
      this.mListData.clear();
      this.mListData.addAll(this.mModel.getSearchList());
      this.mHasmore = this.mModel.mHasmoreSearch;
    }
  }

  public AddFriend getAddFriendUtil()
  {
    if (this.addFriendUtil == null)
      this.addFriendUtil = new AddFriend(this, this.mHandler);
    return this.addFriendUtil;
  }

  public void getCollegeList()
  {
    if (TextUtils.isEmpty(this.mUniversityId));
    while (true)
    {
      return;
      this.mCollegeList.clear();
      this.mYearList.clear();
      this.mCollegeList.add(getResources().getString(2131428626));
      String str1 = "http://www.kaixin001.com/interface/suggestlocation.php?type=8&id=" + this.mUniversityId;
      try
      {
        JSONArray localJSONArray = new JSONArray(NetworkRequest.doGet(str1));
        int j = localJSONArray.length();
        for (int k = 0; ; k++)
        {
          if (k >= j)
          {
            this.mYearList.add(getResources().getString(2131428627));
            for (int i = 2013; i >= 1960; i--)
              this.mYearList.add(i);
          }
          String str2 = localJSONArray.getJSONObject(k).optString("name");
          this.mCollegeList.add(str2);
        }
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }
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
    do
    {
      return;
      if (paramView == this.mFooterView)
      {
        getMoreData();
        return;
      }
      if (paramView == this.mBtnDelete)
      {
        cancelSearch();
        this.mModel.getSearchList().clear();
        updateListData();
        return;
      }
      if (paramView != this.mSelectCollege)
        continue;
      if (this.mCurSelect != 0)
      {
        this.mCurSelect = 0;
        updateSelectCollegueView();
        hideNameList();
        showNameList();
        return;
      }
      this.mCurSelect = -1;
      updateSelectCollegueView();
      hideNameList();
      return;
    }
    while (paramView != this.mSelectYear);
    if (this.mCurSelect != 1)
    {
      this.mCurSelect = 1;
      updateSelectCollegueView();
      hideNameList();
      showNameList();
      return;
    }
    this.mCurSelect = -1;
    updateSelectCollegueView();
    hideNameList();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mLable = "";
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      this.mMode = localBundle.getInt("mode");
      this.mStarType = localBundle.getString("startype");
      this.mLon = localBundle.getString("lon");
      this.mLat = localBundle.getString("lat");
      this.mX = localBundle.getString("x");
      this.mY = localBundle.getString("y");
      this.mLable = localBundle.getString("lable");
      this.mCompany = localBundle.getString("company");
      this.mSchool = localBundle.getString("name");
      this.mYear = localBundle.getString("year");
      this.mUniversityId = localBundle.getString("universityId");
      this.mSearchText = localBundle.getString("person_name");
    }
    this.mModel.getRecommendCategoryStars().clear();
    this.mModel.mHasmoreCategoryStar = false;
    updateListData();
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903087, paramViewGroup, false);
  }

  public void onDestroy()
  {
    this.mModel.getSearchList().clear();
    this.mModel.mHasmoreSearch = false;
    super.onDestroy();
  }

  public void onDestroyView()
  {
    cancelTask(this.mTask);
    super.onDestroyView();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (this.mCurSelect == 0)
    {
      this.mCollege = ((String)this.mCollegeList.get(paramInt));
      String str2 = this.mCollege;
      if (str2.length() > 4)
        str2 = str2.substring(0, 4) + "...";
      if (paramInt == 0)
        this.mCollege = "";
      this.mSelectCollege.setText(str2);
      this.mCollegeIndex = paramInt;
      startSearch(null);
      hideNameList();
    }
    while (true)
    {
      this.mCurSelect = -1;
      updateSelectCollegueView();
      return;
      if (this.mCurSelect != 1)
        continue;
      this.mYear = ((String)this.mYearList.get(paramInt));
      String str1 = this.mYear;
      if ((this.mCurSelect == 1) && (paramInt > 0))
        str1 = str1 + "入学";
      this.mSelectYear.setText(str1);
      if (paramInt == 0)
        this.mYear = "";
      this.mYearIndex = paramInt;
      startSearch(null);
      hideNameList();
    }
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    initTitle(getView());
    initView();
    if (!dataInited())
      initData();
    if (this.mSearchText != null)
      startSearch(this.mSearchText);
  }

  protected void showWait(boolean paramBoolean)
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

  public void updateSelectCollegueView()
  {
    if (this.mCurSelect == -1)
    {
      this.mSelectCollege.setTextColor(-6710887);
      this.mSelectCollegeTag.setImageResource(2130838110);
      this.mSelectYear.setTextColor(-6710887);
      this.mSelectYearTag.setImageResource(2130838110);
      return;
    }
    if (this.mCurSelect == 0)
    {
      this.mSelectCollege.setTextColor(-13327425);
      this.mSelectCollegeTag.setImageResource(2130838109);
      this.mSelectYear.setTextColor(-6710887);
      this.mSelectYearTag.setImageResource(2130838110);
      return;
    }
    this.mSelectCollege.setTextColor(-6710887);
    this.mSelectCollegeTag.setImageResource(2130838110);
    this.mSelectYear.setTextColor(-13327425);
    this.mSelectYearTag.setImageResource(2130838109);
  }

  public class FindFriendListAdapter extends BaseAdapter
  {
    public FindFriendListAdapter()
    {
    }

    public int getCount()
    {
      return FindFriendListFragment.this.mListData.size();
    }

    public Object getItem(int paramInt)
    {
      return FindFriendListFragment.this.mListData.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      StrangerModel.Stranger localStranger = (StrangerModel.Stranger)FindFriendListFragment.this.mListData.get(paramInt);
      if (TextUtils.isEmpty(localStranger.fuid))
        return FindFriendListFragment.this.mFooterView;
      if (localStranger.fuid.equals("empty"))
        return FindFriendListFragment.this.mEmptyView;
      if ((paramView == null) || (paramView == FindFriendListFragment.this.mFooterView) || (paramView == FindFriendListFragment.this.mEmptyView))
        paramView = FindFriendListFragment.this.getActivity().getLayoutInflater().inflate(2130903121, null);
      FindFriendListFragment.this.updateItemView(paramView, FindFriendListFragment.this.mMode, localStranger);
      return paramView;
    }
  }

  public class GetListTask extends KXFragment.KXAsyncTask<Integer, Void, Integer>
  {
    FindFriendEngine engine = new FindFriendEngine();

    public GetListTask()
    {
      super();
    }

    public void cancelRequest()
    {
      this.engine.cancelRequest();
    }

    protected Integer doInBackgroundA(Integer[] paramArrayOfInteger)
    {
      int i = paramArrayOfInteger[0].intValue();
      try
      {
        if (FindFriendListFragment.this.mMode == FindFriendListFragment.MODE_STAR)
        {
          int i1 = this.engine.getRecommandStarList(FindFriendListFragment.this.getActivity(), FindFriendListFragment.this.mModel, i, 20, FindFriendListFragment.this.mStarType);
          j = i1;
        }
        while (true)
        {
          return Integer.valueOf(j);
          if (FindFriendListFragment.this.mMode == FindFriendListFragment.MODE_NEARBY)
          {
            j = this.engine.getNearbyPersonList(FindFriendListFragment.this.getActivity(), FindFriendListFragment.this.mModel, FindFriendListFragment.this.mLon, FindFriendListFragment.this.mLat, FindFriendListFragment.this.mX, FindFriendListFragment.this.mY, i, 20);
            continue;
          }
          if (FindFriendListFragment.this.mMode == FindFriendListFragment.MODE_MAYBE)
          {
            j = this.engine.getMaybeKnowList(FindFriendListFragment.this.getActivity(), FindFriendListFragment.this.mModel, i, 20);
            continue;
          }
          if (FindFriendListFragment.this.mMode == FindFriendListFragment.MODE_SEARCH_NAME)
          {
            j = this.engine.doSearchFriend(FindFriendListFragment.this.getActivity(), FindFriendListFragment.this.mModel, FindFriendListFragment.this.mSearchText, FindFriendEngine.Findby.name, i, 20);
            continue;
          }
          int k = FindFriendListFragment.this.mMode;
          int m = FindFriendListFragment.MODE_SEARCH_CLASSMATE_COLLEAGUE;
          j = 0;
          if (k != m)
            continue;
          if (TextUtils.isEmpty(FindFriendListFragment.this.mCompany))
          {
            FindFriendListFragment.this.getCollegeList();
            j = this.engine.getClassmateColleagueList(true, FindFriendListFragment.this.getActivity(), FindFriendListFragment.this.mModel, FindFriendListFragment.this.mSchool, FindFriendListFragment.this.mCollege, FindFriendListFragment.this.mYear, i, 20);
            continue;
          }
          int n = this.engine.getClassmateColleagueList(false, FindFriendListFragment.this.getActivity(), FindFriendListFragment.this.mModel, FindFriendListFragment.this.mCompany, FindFriendListFragment.this.mCollege, FindFriendListFragment.this.mYear, i, 20);
          j = n;
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          localException.printStackTrace();
          int j = 0;
        }
      }
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if ((FindFriendListFragment.this.getActivity() == null) || (FindFriendListFragment.this.getView() == null))
        return;
      FindFriendListFragment.this.showWait(false);
      FindFriendListFragment.this.showLoadingFooter(false);
      if (paramInteger.intValue() == 1)
      {
        if (FindFriendListFragment.this.mModel.getSearchList().size() == 0)
        {
          StrangerModel.Stranger localStranger = new StrangerModel.Stranger();
          localStranger.fuid = "empty";
          FindFriendListFragment.this.mModel.getSearchList().add(localStranger);
        }
        FindFriendListFragment.this.updateListData();
        FindFriendListFragment.this.mAdapter.notifyDataSetChanged();
        return;
      }
      FindFriendListFragment.this.showToast(2131427374);
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  public class NameListAdapter extends BaseAdapter
  {
    public NameListAdapter()
    {
    }

    private ArrayList<String> getItems()
    {
      if (FindFriendListFragment.this.mCurSelect == 0)
        return FindFriendListFragment.this.mCollegeList;
      return FindFriendListFragment.this.mYearList;
    }

    private int getSelectIndex()
    {
      if (FindFriendListFragment.this.mCurSelect == 0)
        return FindFriendListFragment.this.mCollegeIndex;
      return FindFriendListFragment.this.mYearIndex;
    }

    public int getCount()
    {
      return getItems().size();
    }

    public Object getItem(int paramInt)
    {
      return getItems().get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null)
        paramView = ((LayoutInflater)FindFriendListFragment.this.getActivity().getSystemService("layout_inflater")).inflate(2130903297, null);
      String str = (String)getItems().get(paramInt);
      if ((FindFriendListFragment.this.mCurSelect == 1) && (paramInt > 0))
        str = str + " 入学";
      TextView localTextView = (TextView)paramView.findViewById(2131362400);
      ImageView localImageView = (ImageView)paramView.findViewById(2131362401);
      if (!TextUtils.isEmpty(str))
      {
        localTextView.setText(str);
        localTextView.setVisibility(0);
      }
      while (paramInt == getSelectIndex())
      {
        localImageView.setVisibility(0);
        localImageView.setImageResource(2130838198);
        return paramView;
        localTextView.setVisibility(8);
      }
      localImageView.setVisibility(8);
      return paramView;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.FindFriendListFragment
 * JD-Core Version:    0.6.0
 */