package com.kaixin001.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.VoteListEngine;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.item.VoteItem;
import com.kaixin001.model.NewsModel;
import com.kaixin001.model.VoteListModel;
import java.util.ArrayList;

public class VoteListFragment extends BaseFragment
  implements AdapterView.OnItemClickListener
{
  private VoteItemAdapter mAdapter = null;
  private String mFname = "";
  private String mFuid = "";
  private GetVoteListTask mGetVoteListTask = null;
  private ArrayList<VoteItem> mMoreVoteList = new ArrayList();
  private ArrayList<VoteItem> mVoteList = new ArrayList();

  private void getMoreVoteList()
  {
    if ((this.mVoteList == null) || (this.mVoteList.size() == 0));
    String str;
    do
    {
      return;
      str = ((VoteItem)this.mVoteList.get(-1 + this.mVoteList.size())).mId;
    }
    while (TextUtils.isEmpty(str));
    showProgressBar(true);
    this.mGetVoteListTask = new GetVoteListTask(null);
    this.mGetVoteListTask.execute(new String[] { str });
  }

  private void showProgressBar(boolean paramBoolean)
  {
    View localView = findViewById(2131362183);
    if (paramBoolean)
    {
      localView.setVisibility(0);
      return;
    }
    localView.setVisibility(8);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903321, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if ((this.mGetVoteListTask != null) && (this.mGetVoteListTask.getStatus() != AsyncTask.Status.FINISHED))
      this.mGetVoteListTask.cancel(true);
    super.onDestroy();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    VoteItem localVoteItem = (VoteItem)this.mAdapter.getItem(paramInt);
    if ((localVoteItem == null) || (TextUtils.isEmpty(localVoteItem.mId)))
      return;
    Intent localIntent = new Intent(getActivity(), VoteDetailFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("vid", localVoteItem.mId);
    localIntent.putExtras(localBundle);
    startFragment(localIntent, true, 1);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      this.mFname = localBundle.getString("fname");
      if (this.mFname == null)
        this.mFname = "";
      this.mFuid = localBundle.getString("fuid");
      if (this.mFuid == null)
        this.mFuid = "";
    }
    NewsInfo localNewsInfo = NewsModel.getInstance().getActiveItem();
    if ((localNewsInfo == null) || (localNewsInfo.mVoteList == null) || (localNewsInfo.mVoteList.size() == 0))
    {
      Toast.makeText(getActivity(), 2131427634, 0).show();
      finish();
      return;
    }
    this.mVoteList.addAll(localNewsInfo.mVoteList);
    this.mAdapter = new VoteItemAdapter();
    ListView localListView = (ListView)findViewById(2131363582);
    localListView.setAdapter(this.mAdapter);
    localListView.setOnItemClickListener(this);
    ((TextView)findViewById(2131362186)).setText(2131427621);
    getMoreVoteList();
    setTitleBar();
  }

  protected void setTitleBar()
  {
    ImageView localImageView = (ImageView)findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        VoteListFragment.this.finish();
      }
    });
    ((ImageView)findViewById(2131362928)).setVisibility(8);
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)findViewById(2131362920);
    if (this.mFname.length() > 3)
      this.mFname = (this.mFname.substring(0, 3) + "...");
    localTextView.setText(this.mFname + getResources().getString(2131427619));
    localTextView.setVisibility(0);
  }

  private class GetVoteListTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    private GetVoteListTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length != 1))
        return Boolean.valueOf(false);
      String str = paramArrayOfString[0];
      try
      {
        Boolean localBoolean = Boolean.valueOf(VoteListEngine.getInstance().getVoteList(VoteListFragment.this.getActivity().getApplicationContext(), VoteListFragment.this.mFuid, str, 20));
        return localBoolean;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        localSecurityErrorException.printStackTrace();
      }
      return Boolean.valueOf(false);
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      VoteListFragment.this.showProgressBar(false);
      ArrayList localArrayList;
      if (paramBoolean.booleanValue())
      {
        localArrayList = VoteListModel.getInstance().getVoteList();
        if ((localArrayList != null) && (localArrayList.size() != 0))
          break label34;
      }
      label34: 
      do
      {
        return;
        VoteListFragment.this.mMoreVoteList.addAll(localArrayList);
      }
      while (VoteListFragment.this.mAdapter == null);
      VoteListFragment.this.mAdapter.notifyDataSetChanged();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class VoteItemAdapter extends BaseAdapter
  {
    private final VoteItem LABEL_ITEM = new VoteItem();

    public VoteItemAdapter()
    {
    }

    public int getCount()
    {
      ArrayList localArrayList = VoteListFragment.this.mVoteList;
      int i = 0;
      if (localArrayList != null)
        i = 0 + VoteListFragment.this.mVoteList.size();
      if ((VoteListFragment.this.mMoreVoteList != null) && (VoteListFragment.this.mMoreVoteList.size() > 0))
      {
        i += VoteListFragment.this.mMoreVoteList.size();
        if ((VoteListFragment.this.mVoteList != null) && (VoteListFragment.this.mVoteList.size() > 0))
          i++;
      }
      return i;
    }

    public Object getItem(int paramInt)
    {
      if ((VoteListFragment.this.mVoteList == null) || (VoteListFragment.this.mMoreVoteList == null));
      int j;
      do
      {
        return null;
        int i = VoteListFragment.this.mVoteList.size();
        if ((paramInt >= 0) && (paramInt < i))
          return VoteListFragment.this.mVoteList.get(paramInt);
        if (paramInt == i)
        {
          if (i == 0)
            return VoteListFragment.this.mMoreVoteList.get(0);
          return this.LABEL_ITEM;
        }
        j = -1 + (paramInt - i);
      }
      while ((j < 0) || (j >= VoteListFragment.this.mMoreVoteList.size()));
      return VoteListFragment.this.mMoreVoteList.get(j);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      VoteListFragment.VoteItemCache localVoteItemCache;
      if (paramView == null)
      {
        paramView = VoteListFragment.this.getActivity().getLayoutInflater().inflate(2130903324, null);
        localVoteItemCache = new VoteListFragment.VoteItemCache(VoteListFragment.this, paramView);
        paramView.setTag(localVoteItemCache);
      }
      while (localVoteItemCache == null)
      {
        return paramView;
        localVoteItemCache = (VoteListFragment.VoteItemCache)paramView.getTag();
      }
      localVoteItemCache.showVoteItem((VoteItem)getItem(paramInt));
      return paramView;
    }
  }

  private class VoteItemCache
  {
    public ImageView mImgArrow = null;
    public ImageView mImgIcon = null;
    public LinearLayout mLayout = null;
    public TextView mTxtTitle = null;

    public VoteItemCache(View arg2)
    {
      Object localObject;
      if (localObject == null)
        return;
      this.mLayout = ((LinearLayout)localObject);
      this.mImgIcon = ((ImageView)localObject.findViewById(2131363595));
      this.mTxtTitle = ((TextView)localObject.findViewById(2131363596));
      this.mImgArrow = ((ImageView)localObject.findViewById(2131363597));
    }

    public void showVoteItem(VoteItem paramVoteItem)
    {
      int i = 4;
      if (paramVoteItem == null);
      int k;
      String str;
      ImageView localImageView;
      while (true)
      {
        return;
        if (this.mImgIcon != null)
          this.mImgIcon.setVisibility(i);
        if (this.mTxtTitle != null)
        {
          int j = 2130838512;
          if (!TextUtils.isEmpty(paramVoteItem.mId))
            break;
          k = VoteListFragment.this.getResources().getColor(2130839395);
          str = VoteListFragment.this.mFname + VoteListFragment.this.getString(2131427620);
          j = 2130839385;
          this.mTxtTitle.setTextColor(k);
          this.mTxtTitle.setText(str);
          if (this.mLayout != null)
            this.mLayout.setBackgroundResource(j);
        }
        if (this.mImgArrow == null)
          continue;
        localImageView = this.mImgArrow;
        if (!TextUtils.isEmpty(paramVoteItem.mId))
          break label180;
      }
      while (true)
      {
        localImageView.setVisibility(i);
        return;
        k = VoteListFragment.this.getResources().getColor(2130839418);
        str = paramVoteItem.mTitle;
        break;
        label180: i = 0;
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.VoteListFragment
 * JD-Core Version:    0.6.0
 */