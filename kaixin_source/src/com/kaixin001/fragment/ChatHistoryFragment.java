package com.kaixin001.fragment;

import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.adapter.ChatListItemAdapter;
import com.kaixin001.engine.ChatEngine;
import com.kaixin001.item.ChatInfoItem;
import com.kaixin001.item.ChatInfoItem.ChatMsg;
import com.kaixin001.model.ChatModel.ChatHistoryList;
import com.kaixin001.model.User;
import java.util.ArrayList;

public class ChatHistoryFragment extends BaseFragment
  implements View.OnClickListener
{
  private ChatListItemAdapter mAdapter = null;
  private final ChatModel.ChatHistoryList mChatHistoryList = new ChatModel.ChatHistoryList();
  private String mFname = null;
  private String mFuid = null;
  private GetChatHistoryTask mGetHistoryTask = null;
  private boolean mIsCircleMessage = false;
  private View mLayoutHeader = null;
  private final ArrayList<ChatInfoItem> mListMessages = new ArrayList();
  private ListView mListView = null;
  private ProgressBar mProBarHeader = null;
  private TextView mTxtHeader = null;
  private TextView mTxtLoading = null;

  private void cancelTask()
  {
    if ((this.mGetHistoryTask != null) && (!this.mGetHistoryTask.isCancelled()) && (this.mGetHistoryTask.getStatus() != AsyncTask.Status.FINISHED))
      this.mGetHistoryTask.cancel(true);
    this.mGetHistoryTask = null;
  }

  private void initListView(View paramView)
  {
    View localView = getActivity().getLayoutInflater().inflate(2130903222, null);
    this.mLayoutHeader = localView.findViewById(2131363039);
    this.mLayoutHeader.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (ChatHistoryFragment.this.mChatHistoryList.size() > ChatHistoryFragment.this.mListMessages.size())
        {
          int j = ChatHistoryFragment.this.mListMessages.size() - ChatHistoryFragment.this.mListView.getFirstVisiblePosition();
          ChatHistoryFragment.this.updateChatList();
          ChatHistoryFragment.this.mListView.setSelection(ChatHistoryFragment.this.mListMessages.size() - j);
        }
        while (true)
        {
          if (ChatHistoryFragment.this.mChatHistoryList.getTotal() > ChatHistoryFragment.this.mChatHistoryList.size())
          {
            int i = ChatHistoryFragment.this.mChatHistoryList.size();
            String str = null;
            if (i > 0)
            {
              ChatInfoItem localChatInfoItem = (ChatInfoItem)ChatHistoryFragment.this.mChatHistoryList.get(0);
              str = null;
              if (localChatInfoItem != null)
                str = ((ChatInfoItem.ChatMsg)localChatInfoItem.mSubObject).mMsgID;
            }
            ChatHistoryFragment.this.loadChatHistory(str, 20, true);
          }
          return;
          ChatHistoryFragment.this.mProBarHeader.setVisibility(0);
        }
      }
    });
    this.mTxtHeader = ((TextView)localView.findViewById(2131363041));
    this.mTxtHeader.setText(2131428068);
    this.mProBarHeader = ((ProgressBar)localView.findViewById(2131363040));
    this.mListView = ((ListView)paramView.findViewById(2131361970));
    this.mListView.addHeaderView(localView);
    this.mAdapter = new ChatListItemAdapter(this, this.mListMessages, null);
    this.mListView.setAdapter(this.mAdapter);
  }

  private void initTitle(View paramView)
  {
    ImageView localImageView = (ImageView)paramView.findViewById(2131362914);
    if ((!TextUtils.isEmpty(this.mFname)) && (this.mFname.length() > 4))
      new StringBuilder(String.valueOf(this.mFname.substring(0, 4))).append("...").toString();
    localImageView.setOnClickListener(this);
    ((ImageView)paramView.findViewById(2131362928)).setVisibility(8);
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    localTextView.setText(2131428076);
    localTextView.setVisibility(0);
  }

  private void loadChatHistory(String paramString, int paramInt, boolean paramBoolean)
  {
    if (!super.checkNetworkAndHint(paramBoolean));
    do
      return;
    while ((this.mGetHistoryTask != null) && (!this.mGetHistoryTask.isCancelled()) && (this.mGetHistoryTask.getStatus() != AsyncTask.Status.FINISHED));
    String[] arrayOfString = new String[2];
    arrayOfString[0] = paramString;
    arrayOfString[1] = String.valueOf(paramInt);
    this.mGetHistoryTask = new GetChatHistoryTask(null);
    this.mGetHistoryTask.execute(arrayOfString);
  }

  private void updateChatList()
  {
    this.mListMessages.clear();
    if (this.mChatHistoryList.size() > 0)
      this.mListMessages.addAll(this.mChatHistoryList);
    if (this.mChatHistoryList.getTotal() > this.mChatHistoryList.size())
      this.mLayoutHeader.setVisibility(0);
    while (true)
    {
      if (this.mAdapter != null)
        this.mAdapter.notifyDataSetChanged();
      return;
      this.mLayoutHeader.setVisibility(8);
    }
  }

  public void onClick(View paramView)
  {
    if (paramView.getId() == 2131362914)
      finish();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903063, paramViewGroup, false);
  }

  public void onDestroy()
  {
    cancelTask();
    this.mListMessages.clear();
    this.mChatHistoryList.clear();
    super.onDestroy();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      this.mFname = localBundle.getString("fname");
      this.mFuid = localBundle.getString("fuid");
      this.mIsCircleMessage = localBundle.getBoolean("is_circle", false);
    }
    initTitle(paramView);
    paramView.findViewById(2131361971).setVisibility(8);
    this.mTxtLoading = ((TextView)paramView.findViewById(2131361972));
    this.mTxtLoading.setVisibility(0);
    initListView(paramView);
    this.mListView.setVisibility(8);
    loadChatHistory(null, 20, true);
  }

  private class GetChatHistoryTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private String mBefore = null;

    private GetChatHistoryTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length != 2))
        return Integer.valueOf(2);
      this.mBefore = paramArrayOfString[0];
      try
      {
        int j = Integer.parseInt(paramArrayOfString[1]);
        i = j;
        return Integer.valueOf(ChatEngine.getInstance().getChatHistory(ChatHistoryFragment.this.getActivity().getApplicationContext(), User.getInstance().getUID(), this.mBefore, i, ChatHistoryFragment.this.mFuid, ChatHistoryFragment.this.mChatHistoryList));
      }
      catch (Exception localException)
      {
        while (true)
          int i = 20;
      }
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      ChatHistoryFragment.this.mTxtLoading.setVisibility(8);
      ChatHistoryFragment.this.mListView.setVisibility(0);
      int i;
      if (paramInteger.intValue() == 1)
        if ((TextUtils.isEmpty(this.mBefore)) || (ChatHistoryFragment.this.mProBarHeader.getVisibility() == 0))
        {
          i = ChatHistoryFragment.this.mListMessages.size() - ChatHistoryFragment.this.mListView.getFirstVisiblePosition();
          ChatHistoryFragment.this.updateChatList();
          if (!TextUtils.isEmpty(this.mBefore))
            break label253;
          ChatHistoryFragment.this.mListView.setSelection(ChatHistoryFragment.this.mListMessages.size());
          if (ChatHistoryFragment.this.mListMessages.size() == 0)
            Toast.makeText(ChatHistoryFragment.this.getActivity(), 2131428069, 0).show();
          if (ChatHistoryFragment.this.mChatHistoryList.size() < ChatHistoryFragment.this.mChatHistoryList.getTotal())
          {
            int j = ChatHistoryFragment.this.mChatHistoryList.size();
            String str = null;
            if (j > 0)
            {
              ChatInfoItem localChatInfoItem = (ChatInfoItem)ChatHistoryFragment.this.mChatHistoryList.get(0);
              str = null;
              if (localChatInfoItem != null)
                str = ((ChatInfoItem.ChatMsg)localChatInfoItem.mSubObject).mMsgID;
            }
            ChatHistoryFragment.this.mGetHistoryTask = null;
            ChatHistoryFragment.this.loadChatHistory(str, 20, false);
          }
        }
      while (true)
      {
        ChatHistoryFragment.this.mProBarHeader.setVisibility(8);
        return;
        label253: ChatHistoryFragment.this.mListView.setSelection(ChatHistoryFragment.this.mListMessages.size() - i);
        break;
        if (ChatHistoryFragment.this.mProBarHeader.getVisibility() != 0)
          continue;
        Toast.makeText(ChatHistoryFragment.this.getActivity(), 2131427332, 0).show();
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
 * Qualified Name:     com.kaixin001.fragment.ChatHistoryFragment
 * JD-Core Version:    0.6.0
 */