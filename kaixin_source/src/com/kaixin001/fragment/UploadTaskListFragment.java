package com.kaixin001.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.item.CircleRecordUploadTask;
import com.kaixin001.item.DetailItem;
import com.kaixin001.item.DiaryUploadTask;
import com.kaixin001.item.KXUploadTask;
import com.kaixin001.item.PhotoUploadTask;
import com.kaixin001.item.RecordUploadTask;
import com.kaixin001.model.User;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.ParseNewsInfoUtil;
import com.kaixin001.util.UploadNotificationHandler;
import com.kaixin001.view.KXIntroView;
import java.util.ArrayList;

public class UploadTaskListFragment extends BaseFragment
  implements View.OnClickListener, AdapterView.OnItemClickListener
{
  private static final String TAG = "UploadTaskListFragment";
  private UploadTaskAdapter mAdapter = null;
  private Dialog mAlertDialog = null;
  private KXUploadTask mCurrentTask = null;
  private ListView mListView = null;

  static
  {
    if (!UploadTaskListFragment.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  private void goToCheckDiaryPage(DiaryUploadTask paramDiaryUploadTask)
  {
    Intent localIntent = new Intent(getActivity(), DiaryDetailFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("fuid", User.getInstance().getUID());
    localBundle.putString("did", paramDiaryUploadTask.getDiaryId());
    localBundle.putString("title", paramDiaryUploadTask.getTitle());
    localBundle.putString("intro", paramDiaryUploadTask.getContent());
    localIntent.putExtras(localBundle);
    startFragment(localIntent, true, 1);
  }

  private void goToCheckPhotoPage(PhotoUploadTask paramPhotoUploadTask)
  {
    if ((paramPhotoUploadTask.getAlbumId() == null) || (paramPhotoUploadTask.getAlbumId().length() == 0) || (paramPhotoUploadTask.getImageIndex() == null) || (paramPhotoUploadTask.getImageIndex().length() == 0))
      return;
    Intent localIntent = new Intent(getActivity(), PreviewUploadPhotoFragment.class);
    localIntent.putExtra("small", paramPhotoUploadTask.getAlbumId());
    localIntent.putExtra("large", paramPhotoUploadTask.getImageIndex());
    localIntent.putExtra("action", "com.kaixin001.VIEW_SINGLE_PHOTO");
    startFragment(localIntent, true, 1);
  }

  private void goToCheckRecordPage(RecordUploadTask paramRecordUploadTask)
  {
    Intent localIntent = new Intent(getActivity(), NewsDetailFragment.class);
    DetailItem localDetailItem = DetailItem.makeWeiboDetailItem("1018", User.getInstance().getUID(), paramRecordUploadTask.getRecordId());
    Bundle localBundle = new Bundle();
    localBundle.putSerializable("data", localDetailItem);
    localIntent.putExtras(localBundle);
    startActivityForResult(localIntent, 1300);
  }

  private void goToCircleRecordPage(CircleRecordUploadTask paramCircleRecordUploadTask)
  {
    Intent localIntent = new Intent();
    localIntent.setClass(getActivity(), CircleDetailsFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putSerializable("tid", paramCircleRecordUploadTask.getRecordId());
    localBundle.putSerializable("gid", paramCircleRecordUploadTask.getGid());
    localIntent.putExtras(localBundle);
    startActivityForResult(localIntent, 111);
  }

  private void handleErrorMessage(int paramInt, Object paramObject)
  {
    KXUploadTask localKXUploadTask = (KXUploadTask)paramObject;
    if (localKXUploadTask == null);
    label164: label326: 
    while (true)
    {
      return;
      StringBuilder localStringBuilder = new StringBuilder();
      String str;
      if (localKXUploadTask.getTitle() != null)
        if (localKXUploadTask.getTitle().length() > 10)
        {
          str = localKXUploadTask.getTitle().substring(0, 5) + "...";
          if (localKXUploadTask.getTaskType() != 3)
            break label164;
          localStringBuilder.append(getString(2131427979));
          localStringBuilder.append("\"");
          localStringBuilder.append(str);
          localStringBuilder.append("\"");
          localStringBuilder.append(getString(2131427984));
        }
      while (true)
      {
        if (localStringBuilder.length() <= 0)
          break label326;
        Toast.makeText(getActivity(), localStringBuilder.toString(), 0).show();
        return;
        str = localKXUploadTask.getTitle();
        break;
        str = "";
        break;
        if (localKXUploadTask.getTaskType() == 1)
        {
          localStringBuilder.append(getString(2131427978));
          localStringBuilder.append("\"");
          localStringBuilder.append(str);
          localStringBuilder.append("\"");
          localStringBuilder.append(getString(2131427984));
          continue;
        }
        if (localKXUploadTask.getTaskType() == 0)
        {
          localStringBuilder.append("\"");
          localStringBuilder.append(str);
          localStringBuilder.append("\"");
          localStringBuilder.append(getString(2131427982));
          continue;
        }
        if (localKXUploadTask.getTaskType() != 2)
          continue;
        localStringBuilder.append(getString(2131427977));
        localStringBuilder.append("\"");
        localStringBuilder.append(str);
        localStringBuilder.append("\"");
        localStringBuilder.append(getString(2131427983));
      }
    }
  }

  private void handleSuccessMessage(int paramInt, Object paramObject)
  {
    KXUploadTask localKXUploadTask = (KXUploadTask)paramObject;
    if (localKXUploadTask == null)
      return;
    StringBuilder localStringBuilder = new StringBuilder();
    String str;
    if (localKXUploadTask.getTitle() != null)
      if (localKXUploadTask.getTitle().length() > 10)
      {
        str = localKXUploadTask.getTitle().substring(0, 5) + "...";
        if (localKXUploadTask.getTaskType() != 3)
          break label156;
        localStringBuilder.append(getString(2131427979));
        localStringBuilder.append("\"");
        localStringBuilder.append(str);
        localStringBuilder.append("\"");
        localStringBuilder.append(getString(2131427981));
      }
    while (true)
    {
      Toast.makeText(getActivity(), localStringBuilder.toString(), 0).show();
      return;
      str = localKXUploadTask.getTitle();
      break;
      str = "";
      break;
      label156: if (localKXUploadTask.getTaskType() == 1)
      {
        localStringBuilder.append(getString(2131427978));
        localStringBuilder.append("\"");
        localStringBuilder.append(str);
        localStringBuilder.append("\"");
        localStringBuilder.append(getString(2131427981));
        continue;
      }
      if (localKXUploadTask.getTaskType() == 0)
      {
        localStringBuilder.append("\"");
        localStringBuilder.append(str);
        localStringBuilder.append("\"");
        localStringBuilder.append(getString(2131427982));
        continue;
      }
      if (localKXUploadTask.getTaskType() != 2)
        continue;
      localStringBuilder.append(getString(2131427977));
      localStringBuilder.append("\"");
      localStringBuilder.append(str);
      localStringBuilder.append("\"");
      localStringBuilder.append(getString(2131427980));
    }
  }

  private void initTitle()
  {
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setVisibility(0);
    localTextView.setText(2131427926);
    ((ImageView)findViewById(2131362928)).setVisibility(8);
    ((ImageView)findViewById(2131362914)).setOnClickListener(this);
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 2);
  }

  protected boolean handleMessage(Message paramMessage)
  {
    if (paramMessage == null)
      return true;
    switch (paramMessage.what)
    {
    case 10003:
    case 10004:
    default:
    case 10001:
    case 10002:
    }
    while (true)
    {
      return super.handleMessage(paramMessage);
      this.mAdapter.notifyDataSetChanged();
      continue;
      this.mAdapter.notifyDataSetChanged();
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
    return paramLayoutInflater.inflate(2130903389, paramViewGroup, false);
  }

  public void onDestroy()
  {
    UploadTaskListEngine.getInstance().unRegister(this.mHandler);
    super.onDestroy();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    this.mCurrentTask = ((KXUploadTask)this.mAdapter.getItem(paramInt));
    if (this.mCurrentTask == null);
    do
    {
      return;
      if (this.mAlertDialog == null)
        continue;
      this.mAlertDialog.dismiss();
      this.mAlertDialog = null;
    }
    while ((this.mCurrentTask.getTaskStatus() != 3) && (this.mCurrentTask.getTaskStatus() != 0) && (this.mCurrentTask.getTaskStatus() != 1) && (this.mCurrentTask.getTaskStatus() != 2));
    String[] arrayOfString;
    if (this.mCurrentTask.getTaskStatus() == 3)
    {
      arrayOfString = new String[2];
      arrayOfString[0] = getString(2131427937);
      arrayOfString[1] = getString(2131427938);
    }
    while (true)
    {
      this.mAlertDialog = DialogUtil.showSelectListDlg(getActivity(), 2131427509, arrayOfString, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          UploadTaskListEngine localUploadTaskListEngine = UploadTaskListEngine.getInstance();
          if ((UploadTaskListFragment.this.mCurrentTask.getTaskStatus() == 3) && (paramInt == 0))
            localUploadTaskListEngine.restartTask(UploadTaskListFragment.this.mCurrentTask);
          while (true)
          {
            return;
            if (UploadTaskListFragment.this.mCurrentTask.getTaskStatus() == 1)
            {
              if (!localUploadTaskListEngine.cancelCurrentUploadingTask(UploadTaskListFragment.this.mCurrentTask))
              {
                Toast.makeText(UploadTaskListFragment.this.getActivity(), 2131427939, 0).show();
                return;
              }
              UploadNotificationHandler.getInstance(UploadTaskListFragment.this.getActivity()).deleteNotification(UploadTaskListFragment.this.mCurrentTask);
              return;
            }
            if (UploadTaskListFragment.this.mCurrentTask.getTaskStatus() != 2)
              break;
            if (paramInt == 0)
            {
              if (localUploadTaskListEngine.deleteTask(UploadTaskListFragment.this.mCurrentTask))
                continue;
              Toast.makeText(UploadTaskListFragment.this.getActivity(), 2131427939, 0).show();
              return;
            }
            if (paramInt == 1)
            {
              if (UploadTaskListFragment.this.mCurrentTask.getTaskType() == 3)
              {
                DiaryUploadTask localDiaryUploadTask = (DiaryUploadTask)UploadTaskListFragment.this.mCurrentTask;
                UploadTaskListFragment.this.goToCheckDiaryPage(localDiaryUploadTask);
                return;
              }
              if (UploadTaskListFragment.this.mCurrentTask.getTaskType() == 2)
              {
                PhotoUploadTask localPhotoUploadTask = (PhotoUploadTask)UploadTaskListFragment.this.mCurrentTask;
                UploadTaskListFragment.this.goToCheckPhotoPage(localPhotoUploadTask);
                return;
              }
              if (UploadTaskListFragment.this.mCurrentTask.getTaskType() == 1)
              {
                RecordUploadTask localRecordUploadTask2 = (RecordUploadTask)UploadTaskListFragment.this.mCurrentTask;
                UploadTaskListFragment.this.goToCheckRecordPage(localRecordUploadTask2);
                return;
              }
              if (UploadTaskListFragment.this.mCurrentTask.getTaskType() == 0)
              {
                RecordUploadTask localRecordUploadTask1 = (RecordUploadTask)UploadTaskListFragment.this.mCurrentTask;
                UploadTaskListFragment.this.goToCheckRecordPage(localRecordUploadTask1);
                return;
              }
              if (UploadTaskListFragment.this.mCurrentTask.getTaskType() == 10)
              {
                CircleRecordUploadTask localCircleRecordUploadTask = (CircleRecordUploadTask)UploadTaskListFragment.this.mCurrentTask;
                UploadTaskListFragment.this.goToCircleRecordPage(localCircleRecordUploadTask);
                return;
              }
              if (UploadTaskListFragment.$assertionsDisabled)
                continue;
              throw new AssertionError();
            }
            if (UploadTaskListFragment.$assertionsDisabled)
              continue;
            throw new AssertionError();
          }
          if (!localUploadTaskListEngine.deleteTask(UploadTaskListFragment.this.mCurrentTask))
          {
            Toast.makeText(UploadTaskListFragment.this.getActivity(), 2131427939, 0).show();
            return;
          }
          UploadNotificationHandler.getInstance(UploadTaskListFragment.this.getActivity()).deleteNotification(UploadTaskListFragment.this.mCurrentTask);
        }
      }
      , 1);
      return;
      if (this.mCurrentTask.getTaskStatus() == 2)
      {
        arrayOfString = new String[2];
        arrayOfString[0] = getString(2131427938);
        arrayOfString[1] = getString(2131427940);
        continue;
      }
      arrayOfString = new String[1];
      arrayOfString[0] = getString(2131427938);
    }
  }

  public void onPause()
  {
    super.onPause();
    UploadNotificationHandler.getInstance(getActivity().getApplicationContext()).setIsNotifyEndTask(true);
  }

  public void onResume()
  {
    super.onResume();
    UploadNotificationHandler.getInstance(getActivity().getApplicationContext()).setIsNotifyEndTask(false);
    UploadNotificationHandler.getInstance(getActivity().getApplicationContext()).deleteAllEndNotifications();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    initTitle();
    this.mListView = ((ListView)findViewById(2131363853));
    this.mAdapter = new UploadTaskAdapter();
    this.mListView.setAdapter(this.mAdapter);
    this.mListView.setOnItemClickListener(this);
    TextView localTextView = (TextView)findViewById(2131363854);
    if (this.mAdapter.getCount() > 0)
    {
      localTextView.setVisibility(8);
      this.mListView.setVisibility(0);
    }
    while (true)
    {
      UploadTaskListEngine.getInstance().register(this.mHandler);
      return;
      localTextView.setVisibility(0);
      this.mListView.setVisibility(8);
    }
  }

  protected class UploadTaskAdapter extends BaseAdapter
  {
    private int mFinishedTaskCount = 0;
    private final ArrayList<KXUploadTask> mListTasks = new ArrayList();
    private int mUploadingTaskCount = 0;
    private int mWaitingTaskCount = 0;

    public UploadTaskAdapter()
    {
      syncTaskList();
    }

    private int addElementFromList(ArrayList<KXUploadTask> paramArrayList)
    {
      int i = 0;
      if (paramArrayList != null);
      for (int j = 0; ; j++)
      {
        if (j >= paramArrayList.size())
          return i;
        if ((UploadTaskListEngine.getInstance().isChatTask((KXUploadTask)paramArrayList.get(j))) || (((KXUploadTask)paramArrayList.get(j)).getTaskType() == 5) || (((KXUploadTask)paramArrayList.get(j)).getTaskType() == 4))
          continue;
        this.mListTasks.add((KXUploadTask)paramArrayList.get(j));
        i++;
      }
    }

    private void syncTaskList()
    {
      this.mListTasks.clear();
      UploadTaskListEngine localUploadTaskListEngine = UploadTaskListEngine.getInstance();
      this.mUploadingTaskCount = addElementFromList(localUploadTaskListEngine.getUploadingTaskList());
      this.mWaitingTaskCount = addElementFromList(localUploadTaskListEngine.getWaitTaskList());
      this.mFinishedTaskCount = addElementFromList(localUploadTaskListEngine.getFinishedTaskList());
    }

    public int getCount()
    {
      return this.mListTasks.size();
    }

    public Object getItem(int paramInt)
    {
      KXUploadTask localKXUploadTask = null;
      if (paramInt >= 0)
      {
        int i = this.mListTasks.size();
        localKXUploadTask = null;
        if (paramInt < i)
          localKXUploadTask = (KXUploadTask)this.mListTasks.get(paramInt);
      }
      return localKXUploadTask;
    }

    public long getItemId(int paramInt)
    {
      KXUploadTask localKXUploadTask = (KXUploadTask)getItem(paramInt);
      if (localKXUploadTask == null)
        return -1L;
      return localKXUploadTask.getTaskId();
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      KXUploadTask localKXUploadTask = (KXUploadTask)getItem(paramInt);
      if (localKXUploadTask == null);
      UploadTaskListFragment.UploadTaskCacheView localUploadTaskCacheView;
      ArrayList localArrayList;
      label85: int i;
      label136: label204: label214: label232: int j;
      int m;
      while (true)
      {
        return null;
        if (paramView != null)
          break;
        paramView = UploadTaskListFragment.this.getActivity().getLayoutInflater().inflate(2130903390, null);
        localUploadTaskCacheView = new UploadTaskListFragment.UploadTaskCacheView(paramView);
        paramView.setTag(localUploadTaskCacheView);
        if (localUploadTaskCacheView == null)
          continue;
        if (!"2".equals(String.valueOf(localKXUploadTask.getNType())))
          break label285;
        localArrayList = ParseNewsInfoUtil.parseCommentAndReplyLinkString(localKXUploadTask.getTitle());
        localUploadTaskCacheView.mIntroView.setTitleList(localArrayList);
        if (localKXUploadTask.getTaskStatus() == 2)
          break label298;
        localUploadTaskCacheView.mProgressBar.setProgress(localKXUploadTask.getProgress());
        localUploadTaskCacheView.mProgressBar.setVisibility(0);
        localUploadTaskCacheView.mTxtTime.setVisibility(8);
        if ((paramInt != 0) && (paramInt != this.mUploadingTaskCount) && (paramInt != this.mUploadingTaskCount + this.mWaitingTaskCount))
          break label354;
        localUploadTaskCacheView.mTxtSection.setVisibility(0);
        switch (localKXUploadTask.getTaskStatus())
        {
        default:
          i = 2131427928;
          localUploadTaskCacheView.mTxtSection.setText(i);
          if (localKXUploadTask.getTaskStatus() != 3)
            break label367;
          localUploadTaskCacheView.mTxtFailed.setVisibility(0);
          j = 8;
          if (localKXUploadTask.getTaskType() != 0)
            break label380;
          m = 2130839255;
          j = 0;
        case 1:
        case 0:
        case 2:
        }
      }
      while (true)
      {
        localUploadTaskCacheView.mImgTaskIcon.setImageResource(m);
        localUploadTaskCacheView.mTxtRepost.setVisibility(j);
        return paramView;
        localUploadTaskCacheView = (UploadTaskListFragment.UploadTaskCacheView)paramView.getTag();
        break;
        label285: localArrayList = ParseNewsInfoUtil.parseCommentAndReplyLinkString(localKXUploadTask.getTitle());
        break label85;
        label298: localUploadTaskCacheView.mProgressBar.setVisibility(8);
        localUploadTaskCacheView.mTxtTime.setText(localKXUploadTask.getFinishTime());
        localUploadTaskCacheView.mTxtTime.setVisibility(0);
        break label136;
        i = 2131427927;
        break label204;
        i = 2131427928;
        break label204;
        i = 2131427929;
        break label204;
        label354: localUploadTaskCacheView.mTxtSection.setVisibility(8);
        break label214;
        label367: localUploadTaskCacheView.mTxtFailed.setVisibility(8);
        break label232;
        label380: if (localKXUploadTask.getTaskType() == 1)
        {
          m = 2130839257;
          continue;
        }
        if (localKXUploadTask.getTaskType() == 2)
        {
          m = 2130839256;
          continue;
        }
        if (localKXUploadTask.getTaskType() == 3)
        {
          m = 2130839254;
          continue;
        }
        int k = localKXUploadTask.getTaskType();
        m = 0;
        if (k != 10)
          continue;
        m = 2130839253;
      }
    }

    public void notifyDataSetChanged()
    {
      syncTaskList();
      super.notifyDataSetChanged();
    }
  }

  protected static class UploadTaskCacheView
  {
    public ImageView mImgTaskIcon = null;
    public KXIntroView mIntroView = null;
    public ProgressBar mProgressBar = null;
    public TextView mTxtFailed = null;
    public TextView mTxtRepost = null;
    public TextView mTxtSection = null;
    public TextView mTxtTime = null;

    public UploadTaskCacheView(View paramView)
    {
      if (paramView != null)
      {
        this.mTxtSection = ((TextView)paramView.findViewById(2131363855));
        this.mImgTaskIcon = ((ImageView)paramView.findViewById(2131363857));
        this.mIntroView = ((KXIntroView)paramView.findViewById(2131363861));
        this.mProgressBar = ((ProgressBar)paramView.findViewById(2131363863));
        this.mTxtTime = ((TextView)paramView.findViewById(2131362195));
        this.mTxtRepost = ((TextView)paramView.findViewById(2131363860));
        this.mTxtFailed = ((TextView)paramView.findViewById(2131363862));
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.UploadTaskListFragment
 * JD-Core Version:    0.6.0
 */