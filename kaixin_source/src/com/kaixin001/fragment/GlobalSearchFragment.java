package com.kaixin001.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.kaixin001.adapter.MenuAdapter.MenuListAdapter;
import com.kaixin001.engine.FriendsEngine;
import com.kaixin001.engine.GlobalSearchEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.menu.HomePageMenuItem;
import com.kaixin001.menu.MenuItem;
import com.kaixin001.model.FriendsModel;
import com.kaixin001.model.FriendsModel.Friend;
import com.kaixin001.view.KXListView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GlobalSearchFragment extends BaseFragment
{
  private ArrayList<MenuItem> list = new ArrayList();
  private Button mButtonCancel;
  private Button mButtonClear;
  private GetAppDataTask mGetAppDataTask;
  private GetFriendsDataTask mGetFriendsDataTask;
  private GetStarDataTask mGetStarDataTask;
  private MenuAdapter.MenuListAdapter mListAdapter;
  private KXListView mListView;
  private TextView mTextView;

  private void cancelTask()
  {
    if ((this.mGetStarDataTask != null) && (!this.mGetStarDataTask.isCancelled()))
      this.mGetStarDataTask.cancel(true);
    if ((this.mGetAppDataTask != null) && (!this.mGetAppDataTask.isCancelled()))
      this.mGetAppDataTask.cancel(true);
  }

  private void findData(String paramString, ArrayList<FriendsModel.Friend> paramArrayList)
  {
    Iterator localIterator1 = paramArrayList.iterator();
    label275: 
    while (true)
    {
      if (!localIterator1.hasNext())
      {
        this.mListAdapter.notifyDataSetChanged();
        return;
      }
      FriendsModel.Friend localFriend = (FriendsModel.Friend)localIterator1.next();
      String[] arrayOfString1 = localFriend.getPy();
      ArrayList localArrayList = new ArrayList();
      int i = arrayOfString1.length;
      int j = 0;
      Iterator localIterator2;
      label73: int n;
      if (j >= i)
      {
        localIterator2 = localArrayList.iterator();
        boolean bool = localIterator2.hasNext();
        n = 0;
        if (bool)
          break label200;
      }
      while (true)
      {
        if ((n != 0) || (!localFriend.getFname().startsWith(paramString)))
          break label275;
        HomePageMenuItem localHomePageMenuItem2 = new HomePageMenuItem(localFriend.getFname(), localFriend.getFuid(), localFriend.getFlogo(), false);
        this.mListAdapter.list.add(localHomePageMenuItem2);
        break;
        String[] arrayOfString2 = arrayOfString1[j].split(",");
        int k = arrayOfString2.length;
        for (int m = 0; ; m++)
        {
          if (m >= k)
          {
            j++;
            break;
          }
          localArrayList.add(arrayOfString2[m]);
        }
        label200: String str = (String)localIterator2.next();
        if ((!str.startsWith(paramString)) || (str.length() < paramString.length()))
          break label73;
        HomePageMenuItem localHomePageMenuItem1 = new HomePageMenuItem(localFriend.getFname(), localFriend.getFuid(), localFriend.getFlogo(), false);
        this.mListAdapter.list.add(localHomePageMenuItem1);
        n = 1;
      }
    }
  }

  private void searchListData(String paramString)
  {
    ArrayList localArrayList = FriendsModel.getInstance().getFriends();
    if (localArrayList.size() == 0)
    {
      if ((this.mGetFriendsDataTask != null) && (!this.mGetFriendsDataTask.isCancelled()))
        this.mGetFriendsDataTask.cancel(true);
      this.mGetFriendsDataTask = new GetFriendsDataTask(null);
      this.mGetFriendsDataTask.execute(new String[] { paramString });
      return;
    }
    findData(paramString, localArrayList);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903144, paramViewGroup, false);
  }

  public void onDestroy()
  {
    cancelTask(this.mGetAppDataTask);
    cancelTask(this.mGetFriendsDataTask);
    cancelTask(this.mGetStarDataTask);
    super.onDestroy();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mButtonClear = ((Button)paramView.findViewById(2131362163));
    this.mButtonClear.setOnClickListener(new ClearBtnOnClickListener(null));
    this.mButtonCancel = ((Button)paramView.findViewById(2131362162));
    this.mButtonCancel.setVisibility(0);
    this.mButtonCancel.setOnClickListener(new CancleBtnOnClickListener(null));
    this.mTextView = ((TextView)paramView.findViewById(2131362161));
    this.mTextView.addTextChangedListener(new SearchTextWatcher(null));
    this.mListView = ((KXListView)paramView.findViewById(2131362498));
    this.mListAdapter = new MenuAdapter.MenuListAdapter(this, this.list);
    this.mListView.setAdapter(this.mListAdapter);
    this.mListView.setOnItemClickListener(this.mListAdapter);
  }

  private class CancleBtnOnClickListener
    implements View.OnClickListener
  {
    private CancleBtnOnClickListener()
    {
    }

    public void onClick(View paramView)
    {
      GlobalSearchFragment.this.finish();
    }
  }

  private class ClearBtnOnClickListener
    implements View.OnClickListener
  {
    private ClearBtnOnClickListener()
    {
    }

    public void onClick(View paramView)
    {
      if (GlobalSearchFragment.this.mTextView != null)
        GlobalSearchFragment.this.mTextView.setText("");
    }
  }

  private class GetAppDataTask extends KXFragment.KXAsyncTask<String, Void, ArrayList<MenuItem>>
  {
    Context context;

    public GetAppDataTask(Context arg2)
    {
      super();
      Object localObject;
      this.context = localObject;
    }

    protected ArrayList<MenuItem> doInBackgroundA(String[] paramArrayOfString)
    {
      try
      {
        ArrayList localArrayList = GlobalSearchEngine.getInstance().getGlobalSearchAppList(this.context, paramArrayOfString[0], 0, 5);
        return localArrayList;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(ArrayList<MenuItem> paramArrayList)
    {
      if (paramArrayList != null)
      {
        GlobalSearchFragment.this.mListAdapter.list.addAll(paramArrayList);
        GlobalSearchFragment.this.mListAdapter.notifyDataSetChanged();
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class GetFriendsDataTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    private String keyword;

    private GetFriendsDataTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      this.keyword = paramArrayOfString[0];
      try
      {
        Boolean localBoolean = Boolean.valueOf(FriendsEngine.getInstance().getFriendsList(GlobalSearchFragment.this.getActivity().getApplicationContext(), 1));
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
      if (paramBoolean.booleanValue())
        GlobalSearchFragment.this.findData(this.keyword, FriendsModel.getInstance().getFriends());
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class GetStarDataTask extends KXFragment.KXAsyncTask<String, Void, ArrayList<MenuItem>>
  {
    Context context;

    public GetStarDataTask(Context arg2)
    {
      super();
      Object localObject;
      this.context = localObject;
    }

    protected ArrayList<MenuItem> doInBackgroundA(String[] paramArrayOfString)
    {
      try
      {
        ArrayList localArrayList = GlobalSearchEngine.getInstance().getGlobalSearchStarList(this.context, paramArrayOfString[0], 0, 5);
        return localArrayList;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(ArrayList<MenuItem> paramArrayList)
    {
      if (paramArrayList != null)
      {
        GlobalSearchFragment.this.mListAdapter.list.addAll(paramArrayList);
        GlobalSearchFragment.this.mListAdapter.notifyDataSetChanged();
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class SearchTextWatcher
    implements TextWatcher
  {
    private String keywordOld = "";

    private SearchTextWatcher()
    {
    }

    public void afterTextChanged(Editable paramEditable)
    {
      if (GlobalSearchFragment.this.mTextView != null)
      {
        if (GlobalSearchFragment.this.mTextView.getText().length() > 0)
          GlobalSearchFragment.this.mButtonClear.setVisibility(0);
      }
      else
        return;
      GlobalSearchFragment.this.mButtonClear.setVisibility(8);
      GlobalSearchFragment.this.cancelTask();
    }

    public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
    }

    public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
      if (GlobalSearchFragment.this.isNeedReturn());
      do
      {
        String str;
        do
        {
          return;
          str = paramCharSequence.toString().trim().replace(" ", "");
        }
        while (this.keywordOld.equals(str));
        this.keywordOld = str;
        if (TextUtils.isEmpty(str))
          continue;
        GlobalSearchFragment.this.mListAdapter.list.clear();
        GlobalSearchFragment.this.searchListData(str);
        GlobalSearchFragment.this.cancelTask();
        GlobalSearchFragment.this.mGetStarDataTask = new GlobalSearchFragment.GetStarDataTask(GlobalSearchFragment.this, GlobalSearchFragment.this.getActivity());
        GlobalSearchFragment.this.mGetStarDataTask.execute(new String[] { str });
        GlobalSearchFragment.this.mGetAppDataTask = new GlobalSearchFragment.GetAppDataTask(GlobalSearchFragment.this, GlobalSearchFragment.this.getActivity());
        GlobalSearchFragment.this.mGetAppDataTask.execute(new String[] { str });
        return;
      }
      while ((GlobalSearchFragment.this.mListAdapter == null) || (GlobalSearchFragment.this.mListAdapter.list == null));
      GlobalSearchFragment.this.mListAdapter.list.clear();
      GlobalSearchFragment.this.mListAdapter.notifyDataSetChanged();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.GlobalSearchFragment
 * JD-Core Version:    0.6.0
 */