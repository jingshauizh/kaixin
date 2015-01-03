package com.kaixin001.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.kaixin001.engine.UserInfoEngine;
import com.kaixin001.item.UserRankItem;
import com.kaixin001.item.UserRankItem.UserRankModel;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import com.kaixin001.view.KXFrameImageView;
import java.util.ArrayList;

public class UserRankingFragment extends Fragment
{
  private BaseAdapter adapter;
  private ListView lvRanking;
  private String mFname;
  private String mFuid;
  private BaseFragment mParentFragment;
  private ArrayList<UserRankItem.UserRankModel> mRankingList;

  public UserRankingFragment()
  {
  }

  public UserRankingFragment(BaseFragment paramBaseFragment)
  {
    this.mParentFragment = paramBaseFragment;
  }

  private void loadData(ArrayList<UserRankItem.UserRankModel> paramArrayList)
  {
    if ((paramArrayList != null) && (paramArrayList.size() > 0))
    {
      this.mRankingList.clear();
      this.mRankingList.addAll(paramArrayList);
      this.adapter.notifyDataSetChanged();
    }
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
    return paramLayoutInflater.inflate(2130903397, paramViewGroup, false);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mRankingList = new ArrayList();
    this.lvRanking = ((ListView)paramView.findViewById(2131363919));
    this.adapter = new RankingAdapter();
    this.lvRanking.setAdapter(this.adapter);
    ArrayList localArrayList = UserRankItem.getInstance().getRankList(this.mFuid);
    if ((localArrayList == null) || (localArrayList.size() == 0))
    {
      getView().findViewById(2131363887).setVisibility(0);
      new GetRankingTask().execute(new Void[0]);
      return;
    }
    loadData(localArrayList);
  }

  class GetRankingTask extends AsyncTask<Void, Void, Integer>
  {
    GetRankingTask()
    {
    }

    protected Integer doInBackground(Void[] paramArrayOfVoid)
    {
      if ((UserRankingFragment.this.getActivity() == null) || (UserRankingFragment.this.getView() == null))
        return null;
      try
      {
        UserInfoEngine.getInstance().getRankingList(UserRankingFragment.this.getActivity(), UserRankingFragment.this.mFuid);
        Integer localInteger = Integer.valueOf(1);
        return localInteger;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return null;
    }

    protected void onPostExecute(Integer paramInteger)
    {
      if ((UserRankingFragment.this.getActivity() == null) || (UserRankingFragment.this.getView() == null) || (paramInteger == null))
        return;
      try
      {
        UserRankingFragment.this.getView().findViewById(2131363887).setVisibility(8);
        ArrayList localArrayList = UserRankItem.getInstance().getRankList(UserRankingFragment.this.mFuid);
        UserRankingFragment.this.loadData(localArrayList);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  class RankingAdapter extends BaseAdapter
  {
    RankingAdapter()
    {
    }

    public int getCount()
    {
      return UserRankingFragment.this.mRankingList.size();
    }

    public Object getItem(int paramInt)
    {
      return UserRankingFragment.this.mRankingList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      UserRankingFragment.RankingHolder localRankingHolder;
      if (paramView == null)
      {
        paramView = UserRankingFragment.this.getActivity().getLayoutInflater().inflate(2130903396, null);
        localRankingHolder = new UserRankingFragment.RankingHolder(UserRankingFragment.this);
        localRankingHolder.initView(paramView);
        paramView.setTag(localRankingHolder);
      }
      while (true)
      {
        localRankingHolder.updateView((UserRankItem.UserRankModel)UserRankingFragment.this.mRankingList.get(paramInt));
        return paramView;
        localRankingHolder = (UserRankingFragment.RankingHolder)paramView.getTag();
      }
    }
  }

  class RankingHolder
  {
    private View itemLayout;
    private ImageView ivDivideLine;
    private ImageView ivRank;
    private View logolayout;
    private KXFrameImageView lvLevelImg;
    private KXFrameImageView lvLogo;
    private View moreLayout;
    private TextView tvExpValue;
    private TextView tvLevel;
    private TextView tvName;
    private TextView tvRankValue;

    RankingHolder()
    {
    }

    public void initView(View paramView)
    {
      this.itemLayout = paramView.findViewById(2131363909);
      this.moreLayout = paramView.findViewById(2131363908);
      this.tvRankValue = ((TextView)paramView.findViewById(2131363911));
      this.ivRank = ((ImageView)paramView.findViewById(2131363912));
      this.logolayout = paramView.findViewById(2131363913);
      this.lvLogo = ((KXFrameImageView)paramView.findViewById(2131361939));
      this.tvName = ((TextView)paramView.findViewById(2131363914));
      this.lvLevelImg = ((KXFrameImageView)paramView.findViewById(2131363915));
      this.tvLevel = ((TextView)paramView.findViewById(2131363916));
      this.tvExpValue = ((TextView)paramView.findViewById(2131363917));
      this.ivDivideLine = ((ImageView)paramView.findViewById(2131362425));
    }

    public void updateView(UserRankItem.UserRankModel paramUserRankModel)
    {
      this.ivDivideLine.setVisibility(8);
      this.moreLayout.setVisibility(8);
      switch ($SWITCH_TABLE$com$kaixin001$item$UserRankItem$BackType()[paramUserRankModel.backType.ordinal()])
      {
      default:
        this.tvRankValue.setVisibility(8);
        this.ivRank.setVisibility(0);
        switch (paramUserRankModel.rank)
        {
        default:
          this.ivRank.setVisibility(8);
          this.tvRankValue.setVisibility(0);
          this.tvRankValue.setText(paramUserRankModel.rank);
        case 1:
        case 2:
        case 3:
        }
      case 3:
      case 1:
      case 2:
      }
      while (true)
      {
        this.lvLogo.setFrameNinePatchResId(2130838099);
        UserRankingFragment.this.mParentFragment.displayRoundPicture(this.lvLogo, paramUserRankModel.logo90, ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
        this.tvName.setText(paramUserRankModel.name);
        this.lvLevelImg.setFrameNinePatchResId(2130838078);
        UserRankingFragment.this.mParentFragment.displayRoundPicture(this.lvLevelImg, paramUserRankModel.large, ImageDownloader.RoundCornerType.hdpi_big, 0);
        this.tvLevel.setText("LV." + paramUserRankModel.level + " " + paramUserRankModel.title);
        this.tvExpValue.setText("经验值：" + paramUserRankModel.exp);
        this.logolayout.setOnClickListener(new View.OnClickListener(paramUserRankModel)
        {
          public void onClick(View paramView)
          {
            Intent localIntent = new Intent(UserRankingFragment.this.getActivity(), HomeFragment.class);
            Bundle localBundle = new Bundle();
            localBundle.putString("fuid", this.val$item.uid);
            localBundle.putString("fname", this.val$item.name);
            localBundle.putString("flogo", this.val$item.logo90);
            localIntent.putExtras(localBundle);
            UserRankingFragment.this.mParentFragment.startFragment(localIntent, true, 1);
          }
        });
        return;
        this.moreLayout.setVisibility(0);
        this.itemLayout.setBackgroundResource(2130838594);
        break;
        this.itemLayout.setBackgroundResource(2130838574);
        break;
        this.itemLayout.setBackgroundResource(2130838575);
        this.ivDivideLine.setVisibility(0);
        break;
        this.ivRank.setBackgroundResource(2130838578);
        continue;
        this.ivRank.setBackgroundResource(2130838588);
        continue;
        this.ivRank.setBackgroundResource(2130838590);
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.UserRankingFragment
 * JD-Core Version:    0.6.0
 */