package com.kaixin001.task;

import android.os.AsyncTask;
import android.widget.Toast;
import com.kaixin001.adapter.NewsItemAdapter;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.UpEngine;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.util.KXLog;

public class PostUpTask extends AsyncTask<Object, Void, Integer>
{
  private static final String TAG = "PostUpTask";
  private NewsInfo mActiveNewsInfo;
  private NewsItemAdapter mAdapter;
  private BaseFragment mFragment;
  private boolean mShowSendingInfo = true;

  public PostUpTask(BaseFragment paramBaseFragment)
  {
    this.mFragment = paramBaseFragment;
  }

  protected Integer doInBackground(Object[] paramArrayOfObject)
  {
    this.mActiveNewsInfo = ((NewsInfo)paramArrayOfObject[0]);
    this.mAdapter = ((NewsItemAdapter)paramArrayOfObject[1]);
    try
    {
      if (this.mActiveNewsInfo.mHasUp.booleanValue())
        return Integer.valueOf(2);
      Integer localInteger = Integer.valueOf(UpEngine.getInstance().postUp(this.mFragment.getActivity(), this.mActiveNewsInfo.mNtype, this.mActiveNewsInfo.mNewsId, this.mActiveNewsInfo.mFuid));
      return localInteger;
    }
    catch (SecurityErrorException localSecurityErrorException)
    {
    }
    return null;
  }

  protected void onPostExecute(Integer paramInteger)
  {
    if (paramInteger == null)
      Toast.makeText(this.mFragment.getActivity(), 2131427387, 0).show();
    while (true)
    {
      return;
      try
      {
        if (paramInteger.intValue() != 1)
          break;
        this.mActiveNewsInfo.mHasUp = Boolean.valueOf(true);
        this.mFragment.showToast(2130838515, 2131428458);
        this.mActiveNewsInfo.mUpnum = (1 + Integer.parseInt(this.mActiveNewsInfo.mUpnum));
        if (this.mAdapter == null)
          continue;
        this.mAdapter.notifyDataSetChanged();
        return;
      }
      catch (Exception localException)
      {
        KXLog.e("PostUpTask", "onPostExecute", localException);
        return;
      }
    }
    if (paramInteger.intValue() == 2)
    {
      this.mActiveNewsInfo.mHasUp = Boolean.valueOf(true);
      this.mFragment.showToast(0, 2131428457);
      return;
    }
    this.mFragment.showToast(0, 2131427378);
  }

  protected void onPreExecute()
  {
    if (this.mShowSendingInfo)
      Toast.makeText(this.mFragment.getActivity(), 2131427618, 0).show();
  }

  public void showSendingInfo(boolean paramBoolean)
  {
    this.mShowSendingInfo = paramBoolean;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.task.PostUpTask
 * JD-Core Version:    0.6.0
 */