package com.kaixin001.fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.MainActivity;
import com.kaixin001.activity.VerifyActivity;
import com.kaixin001.businesslogic.AddFriend;
import com.kaixin001.businesslogic.AddFriendResult;
import com.kaixin001.engine.NewFriend2Engine;
import com.kaixin001.engine.RecommentStarsEngine;
import com.kaixin001.model.FindFriendModel;
import com.kaixin001.model.RecommentStarsModel;
import com.kaixin001.model.RecommentStarsModel.RecommentStarsTypeItem;
import com.kaixin001.model.StrangerModel.Stranger;
import com.kaixin001.model.User;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.UserHabitUtils;
import com.kaixin001.view.KXFrameImageView;
import java.util.ArrayList;
import java.util.HashMap;

public class FindStarsFragment extends BaseFragment
  implements View.OnClickListener
{
  private static final int FETCH_NUM = 10;
  private static final int REQUEST_CAPTCHA = 99;
  public static final int REQUEST_CODE_VIEW_ALL = 101;
  HashMap<String, AddFriendResult> addFriendApplyChanges = this.mModel.addFriendApplyChanges;
  private AddFriend addFriendUtil;
  private FindFriendAdapter mAdapter;
  protected ImageView mBtnLeft;
  protected ImageView mBtnRight;
  private int mCompleted = 1;
  private GetRecommendFriendTask mGetRecommendFriendTask;
  private String mLat = "";
  private ArrayList<RecommentStarsModel.RecommentStarsTypeItem> mListData = new ArrayList();
  private ListView mListView;
  private View mLoginLayout;
  private String mLon = "";
  private FindFriendModel mModel = FindFriendModel.getInstance();
  private RecommentStarsModel mRecommentStarsModel = RecommentStarsModel.getInstance();
  protected TextView mTextRight;
  protected TextView mTopTitle;
  protected View mWaitLayout;
  private String mX = "";
  private String mY = "";

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
          FindStarsFragment.this.getAddFriendUtil().addNewFriend("3", this.val$fuid);
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
    if (this.mWaitLayout != null)
      this.mWaitLayout.setVisibility(0);
    cancelTask(this.mGetRecommendFriendTask);
    this.mGetRecommendFriendTask = new GetRecommendFriendTask();
    this.mGetRecommendFriendTask.execute(new Void[0]);
  }

  private void initView()
  {
    this.mListView = ((ListView)getView().findViewById(2131362311));
    this.mAdapter = new FindFriendAdapter();
    this.mListView.setAdapter(this.mAdapter);
    this.mWaitLayout = findViewById(2131361817);
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
          FindStarsFragment.this.showHideLoginLayoutAnimation(0);
        }
      });
      ((Button)localView1.findViewById(2131362131)).setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          FindStarsFragment.this.showHideLoginLayoutAnimation(1);
        }
      });
      return;
    }
  }

  private void showAll(String paramString)
  {
    Intent localIntent = new Intent(getActivity(), FindFriendListFragment.class);
    int i = FindFriendListFragment.MODE_STAR;
    String str = getActivity().getResources().getString(2131428610);
    localIntent.putExtra("mode", i);
    localIntent.putExtra("lable", str);
    localIntent.putExtra("startype", paramString);
    startFragmentForResultNew(localIntent, 101, 1, true);
  }

  private void showInputVerify(String paramString)
  {
    getAddFriendUtil().addVerify(true, paramString);
  }

  private void showPersons(RecommentStarsModel.RecommentStarsTypeItem paramRecommentStarsTypeItem, ViewHolder paramViewHolder, String paramString)
  {
    String str1 = paramRecommentStarsTypeItem.mType;
    String str2 = paramRecommentStarsTypeItem.mName;
    ArrayList localArrayList = paramRecommentStarsTypeItem.items;
    LinearLayout localLinearLayout = paramViewHolder.mLayoutItemsList;
    paramViewHolder.mTitle.setText(str2);
    localLinearLayout.removeAllViews();
    LayoutInflater localLayoutInflater = getActivity().getLayoutInflater();
    int i = localArrayList.size();
    if (i > 3)
      i = 3;
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        paramViewHolder.mMore.setOnClickListener(new View.OnClickListener(str1)
        {
          public void onClick(View paramView)
          {
            FindStarsFragment.this.showAll(this.val$typeStr);
            UserHabitUtils.getInstance(FindStarsFragment.this.getActivity()).addUserHabit("Show_Category_Stars");
          }
        });
        return;
      }
      StrangerModel.Stranger localStranger = (StrangerModel.Stranger)localArrayList.get(j);
      View localView = localLayoutInflater.inflate(2130903120, null);
      updateItemView(localView, localStranger);
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
            FindStarsFragment.this.actionFindByContacts();
          do
          {
            return;
            if (i != 1)
              continue;
            Intent localIntent1 = new Intent(FindStarsFragment.this.getActivity(), FindClassmateColleagueFragment.class);
            localIntent1.putExtra("mode", 1);
            FindStarsFragment.this.startFragmentForResultNew(localIntent1, 101, 1, true);
            return;
          }
          while (i != 2);
          Intent localIntent2 = new Intent(FindStarsFragment.this.getActivity(), FindClassmateColleagueFragment.class);
          localIntent2.putExtra("mode", 2);
          FindStarsFragment.this.startFragmentForResultNew(localIntent2, 101, 1, true);
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

  private void updateItemView(View paramView, StrangerModel.Stranger paramStranger)
  {
    KXFrameImageView localKXFrameImageView = (KXFrameImageView)paramView.findViewById(2131362333);
    localKXFrameImageView.setFrameNinePatchResId(2130838101);
    displayRoundPicture(localKXFrameImageView, paramStranger.flogo, ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
    localKXFrameImageView.setOnClickListener(new View.OnClickListener(paramStranger)
    {
      public void onClick(View paramView)
      {
        IntentUtil.showHomeFragment(FindStarsFragment.this, this.val$item.fuid, this.val$item.fname);
      }
    });
    TextView localTextView1 = (TextView)paramView.findViewById(2131362337);
    ImageView localImageView1 = (ImageView)paramView.findViewById(2131362338);
    TextView localTextView2 = (TextView)paramView.findViewById(2131362339);
    localTextView1.setText(paramStranger.fname);
    localImageView1.setVisibility(0);
    localTextView2.setVisibility(8);
    localTextView1.setOnClickListener(new View.OnClickListener(paramStranger)
    {
      public void onClick(View paramView)
      {
        IntentUtil.showHomeFragment(FindStarsFragment.this, this.val$item.fuid, this.val$item.fname);
      }
    });
    TextView localTextView3 = (TextView)paramView.findViewById(2131362341);
    ImageView localImageView2 = (ImageView)paramView.findViewById(2131362340);
    TextView localTextView4 = (TextView)paramView.findViewById(2131362342);
    localTextView3.setVisibility(0);
    localTextView3.setText(paramStranger.fans);
    localImageView2.setVisibility(8);
    localTextView4.setVisibility(8);
    ImageView localImageView3 = (ImageView)paramView.findViewById(2131362334);
    AddFriendResult localAddFriendResult = (AddFriendResult)this.addFriendApplyChanges.get(paramStranger.fuid);
    int i;
    if (localAddFriendResult == null)
    {
      localImageView3.setImageResource(2130837519);
      i = 1;
    }
    while (true)
    {
      if (i != 0)
        localImageView3.setOnClickListener(new View.OnClickListener(paramStranger)
        {
          public void onClick(View paramView)
          {
            FindStarsFragment.this.actionAddFriend(paramView, true, this.val$item, false);
          }
        });
      return;
      if (localAddFriendResult.code == 1)
      {
        localImageView3.setImageResource(2130838118);
        localImageView3.setOnClickListener(null);
        i = 0;
        continue;
      }
      if (localAddFriendResult.inProgress)
      {
        localImageView3.setVisibility(0);
        localImageView3.setImageResource(2130838214);
        i = 0;
        continue;
      }
      localImageView3.setImageResource(2130837519);
      i = 1;
    }
  }

  private void updateListData()
  {
    this.mListData.clear();
    this.mListData.clear();
    this.mListData.addAll(this.mRecommentStarsModel.getTypseList());
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
    this.mTextRight.setVisibility(8);
    this.mTextRight.setOnClickListener(this);
    this.mBtnLeft = ((ImageView)paramView.findViewById(2131362914));
    this.mBtnLeft.setOnClickListener(this);
    TextView localTextView1 = (TextView)paramView.findViewById(2131362920);
    TextView localTextView2 = (TextView)findViewById(2131362922);
    View localView1 = findViewById(2131362914);
    View localView2 = findViewById(2131362916);
    View localView3 = findViewById(2131362917);
    if (this.mCompleted != 1)
    {
      localView1.setVisibility(8);
      localView2.setVisibility(8);
      localView3.setVisibility(8);
      localTextView1.setVisibility(8);
      localTextView2.setVisibility(0);
      localTextView2.setText(2131428604);
      findViewById(2131362926).setVisibility(0);
      this.mTextRight.setVisibility(0);
      this.mTextRight.setPadding(dipToPx(7.4F), 0, dipToPx(12.0F), 0);
      this.mTextRight.setText(2131428601);
      this.mTextRight.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
      this.mTextRight.setCompoundDrawablePadding(0);
      return;
    }
    localTextView1.setVisibility(0);
    localTextView1.setText(2131427358);
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
      return;
    while ((paramView != this.mTextRight) || (this.mCompleted == 1));
    Intent localIntent = new Intent(getActivity(), MainActivity.class);
    localIntent.setFlags(67108864);
    getActivity().startActivity(localIntent);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle != null)
      this.mCompleted = localBundle.getInt("complete", 1);
    if (this.mCompleted != 1)
      this.mBackKeyNotify = true;
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903115, paramViewGroup, false);
  }

  public void onDestroy()
  {
    super.onDestroy();
  }

  public void onDestroyView()
  {
    cancelTask(this.mGetRecommendFriendTask);
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
      getData();
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
        FindStarsFragment.this.getActivity().finish();
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
        FindStarsFragment.this.mLoginLayout.setVisibility(4);
        FindStarsFragment.this.showLoginPage(this.val$type);
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
      return FindStarsFragment.this.mListData.size();
    }

    public Object getItem(int paramInt)
    {
      return FindStarsFragment.this.mListData.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      RecommentStarsModel.RecommentStarsTypeItem localRecommentStarsTypeItem = (RecommentStarsModel.RecommentStarsTypeItem)FindStarsFragment.this.mListData.get(paramInt);
      FindStarsFragment.ViewHolder localViewHolder;
      if (paramView == null)
      {
        paramView = FindStarsFragment.this.getActivity().getLayoutInflater().inflate(2130903116, null);
        localViewHolder = new FindStarsFragment.ViewHolder(FindStarsFragment.this);
        localViewHolder.mLayoutHeadMethods = ((LinearLayout)paramView.findViewById(2131362312));
        localViewHolder.mLayoutTypes = ((LinearLayout)paramView.findViewById(2131362313));
        localViewHolder.mLayoutItemsList = ((LinearLayout)paramView.findViewById(2131362315));
        localViewHolder.mTitle = ((TextView)paramView.findViewById(2131362314));
        localViewHolder.mMore = ((TextView)paramView.findViewById(2131362316));
        paramView.setTag(localViewHolder);
      }
      while (true)
      {
        localViewHolder.mLayoutHeadMethods.setVisibility(8);
        localViewHolder.mLayoutTypes.setVisibility(0);
        FindStarsFragment.this.showPersons(localRecommentStarsTypeItem, localViewHolder, localRecommentStarsTypeItem.mType);
        if (paramInt != -1 + FindStarsFragment.this.mListData.size())
          break;
        paramView.setPadding(0, 0, 0, FindStarsFragment.this.dipToPx(13.4F));
        return paramView;
        localViewHolder = (FindStarsFragment.ViewHolder)paramView.getTag();
      }
      paramView.setPadding(0, 0, 0, 0);
      return paramView;
    }
  }

  public class GetRecommendFriendTask extends KXFragment.KXAsyncTask<Void, Void, Integer>
  {
    public GetRecommendFriendTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Void[] paramArrayOfVoid)
    {
      try
      {
        new RecommentStarsEngine().getTypes(FindStarsFragment.this.getActivity(), FindStarsFragment.this.mRecommentStarsModel);
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
      if ((FindStarsFragment.this.getActivity() == null) || (FindStarsFragment.this.getView() == null))
        return;
      if (FindStarsFragment.this.mWaitLayout != null)
        FindStarsFragment.this.mWaitLayout.setVisibility(8);
      FindStarsFragment.this.updateListData();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
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
 * Qualified Name:     com.kaixin001.fragment.FindStarsFragment
 * JD-Core Version:    0.6.0
 */