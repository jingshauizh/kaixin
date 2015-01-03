package com.kaixin001.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.kaixin001.engine.GetUrlEngine;
import com.kaixin001.model.UserInfo;
import java.util.List;

public class RankingFragment extends BaseFragment
{
  private GetUrlEngine engine = new GetUrlEngine();
  private ListView mListView;
  private TextView mTextView;
  private String shareUrl;

  private void initData()
  {
    this.shareUrl = getArguments().getString("shareUrl");
    if (!TextUtils.isEmpty(this.shareUrl))
      new WapGameRankingTask(this.shareUrl).execute(new Void[0]);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903048, paramViewGroup, false);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    View localView = LayoutInflater.from(getActivity()).inflate(2130903305, null);
    this.mTextView = ((TextView)localView.findViewById(2131363487));
    this.mListView = ((ListView)findViewById(2131361827));
    this.mListView.addHeaderView(localView);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setText("排行榜");
    localTextView.setVisibility(0);
    ((ImageView)findViewById(2131362928)).setVisibility(8);
    ((ImageView)findViewById(2131362914)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        RankingFragment.this.finish();
      }
    });
    initData();
  }

  private class RankingAdapter extends BaseAdapter
  {
    private Context context;
    private List<String> names;

    public RankingAdapter(List<String> arg2)
    {
      Object localObject1;
      this.context = localObject1;
      Object localObject2;
      this.names = localObject2;
    }

    public int getCount()
    {
      return this.names.size();
    }

    public Object getItem(int paramInt)
    {
      return this.names.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      ViewHolder localViewHolder;
      if (paramView == null)
      {
        paramView = LayoutInflater.from(this.context).inflate(2130903306, null);
        localViewHolder = new ViewHolder();
        localViewHolder.nameTextView = ((TextView)paramView.findViewById(2131363489));
        localViewHolder.topNumTextView = ((TextView)paramView.findViewById(2131363488));
        paramView.setTag(localViewHolder);
      }
      while (true)
      {
        localViewHolder.nameTextView.setText((CharSequence)this.names.get(paramInt));
        String str = Integer.toString(paramInt + 1);
        localViewHolder.topNumTextView.setText(str);
        return paramView;
        localViewHolder = (ViewHolder)paramView.getTag();
      }
    }
  }

  private class WapGameRankingTask extends AsyncTask<Void, Void, Integer>
  {
    private String url;

    public WapGameRankingTask(String arg2)
    {
      Object localObject;
      this.url = localObject;
    }

    protected Integer doInBackground(Void[] paramArrayOfVoid)
    {
      return RankingFragment.this.engine.processWapRanking(RankingFragment.this.getActivity(), this.url);
    }

    protected void onPostExecute(Integer paramInteger)
    {
      super.onPostExecute(paramInteger);
      if ((paramInteger == null) || (paramInteger.intValue() != 1))
        return;
      List localList = UserInfo.getInstance().getNames();
      String str = UserInfo.getInstance().getCurUserTopNum();
      RankingFragment.this.mTextView.setText(str);
      RankingFragment.RankingAdapter localRankingAdapter = new RankingFragment.RankingAdapter(RankingFragment.this, RankingFragment.this.getActivity(), localList);
      RankingFragment.this.mListView.setAdapter(localRankingAdapter);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.RankingFragment
 * JD-Core Version:    0.6.0
 */