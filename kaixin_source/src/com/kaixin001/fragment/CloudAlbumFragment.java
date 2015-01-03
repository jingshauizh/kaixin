package com.kaixin001.fragment;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.CloudAlbumSettingActivity;
import com.kaixin001.adapter.CloudAlbumListAdapter;
import com.kaixin001.engine.CloudAlbumEngine;
import com.kaixin001.item.CloudAlbumPicItem;
import com.kaixin001.model.CloudAlbumModel;
import com.kaixin001.model.KaixinConst;
import com.kaixin001.util.CloudAlbumManager;
import com.kaixin001.util.CloudAlbumManager.CloudAlbumDataListener;
import com.kaixin001.util.CloudAlbumManager.WifiStateChangeListener;
import com.kaixin001.util.ConnectivityUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.UserHabitUtils;
import java.util.ArrayList;
import java.util.Iterator;

public class CloudAlbumFragment extends BaseFragment
  implements View.OnClickListener, AbsListView.OnScrollListener
{
  private static final String LOG = "CloudAlbumManager";
  public static final int MSG_PICTURE_RPOGRESS = 911;
  private static final int STATE_CHECK = 0;
  private static final int STATE_GET_LIST = 2;
  private static final int STATE_INTRO = 1;
  private static final int STATE_LIST = 3;
  private static final int TYPE_ALBUM_GETLIST = 2;
  private static final int TYPE_ALBUM_ISOPEN = 1;
  private CloudAlbumListAdapter mAdapter;
  private boolean mAutoRegister = false;
  ImageView mBtnRight;
  private CheckStatusTask mCheckStatusTask;
  private int mCurState = 0;
  private View mEnableAlbumBtn;
  private EnableCloudAlbumTask mEnableCloudAlbumTask;
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  private View mFooterView;
  private GetDataTask mGetDataTask;
  private ListView mGridView;
  private boolean mHaveGetDetail = false;
  private View mInfoSyncClosedView;
  private TextView mInfoText;
  private View mIntroCloudAlbumView;
  private boolean mLoadingMore;
  private View mLoadingView;
  private boolean mNeedAutoLoading = false;
  private boolean mNeedGetMoreLocalPic = false;
  private ArrayList<CloudAlbumPicItem> mPicItems = new ArrayList();
  private ProgressDialog mProgressDialog;
  private OnRefreshCloudListener mRefreshCloudListener;
  private boolean mShowLocalPic = false;
  private View mTopInfoLayout;
  private View mTopInfoLine;
  private PicturesFragment picFragment;

  private void cancelTask()
  {
    if ((this.mCheckStatusTask != null) && (!this.mCheckStatusTask.isCancelled()) && (this.mCheckStatusTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mCheckStatusTask.cancel(true);
      this.mCheckStatusTask = null;
    }
    if ((this.mEnableCloudAlbumTask != null) && (!this.mEnableCloudAlbumTask.isCancelled()) && (this.mEnableCloudAlbumTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mEnableCloudAlbumTask.cancel(true);
      this.mEnableCloudAlbumTask = null;
    }
    if ((this.mGetDataTask != null) && (!this.mGetDataTask.isCancelled()) && (this.mGetDataTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetDataTask.cancel(true);
      this.mGetDataTask = null;
    }
  }

  private boolean hasMore()
  {
    return (CloudAlbumModel.getInstance().hasMore()) || (this.mNeedGetMoreLocalPic);
  }

  private void initView()
  {
    this.mIntroCloudAlbumView = findViewById(2131362092);
    this.mEnableAlbumBtn = findViewById(2131362093);
    this.mEnableAlbumBtn.setOnClickListener(this);
    this.mTopInfoLayout = findViewById(2131362094);
    this.mTopInfoLine = findViewById(2131362095);
    this.mGridView = ((ListView)findViewById(2131362096));
    this.mLoadingView = findViewById(2131362097);
    this.mInfoSyncClosedView = findViewById(2131362100);
    this.mInfoSyncClosedView.setOnClickListener(this);
    this.mInfoText = ((TextView)findViewById(2131362101));
    this.mFooterView = getActivity().getLayoutInflater().inflate(2130903085, null);
    this.mFooterView.setOnClickListener(this);
    this.mFooterTV = ((TextView)this.mFooterView.findViewById(2131362073));
    this.mFooterTV.setText(2131427748);
    this.mFooterProBar = ((ProgressBar)this.mFooterView.findViewById(2131362072));
    this.mFooterProBar.setVisibility(4);
    if (this.mAdapter == null)
      this.mAdapter = new CloudAlbumListAdapter(this, this.mFooterView, this.mPicItems);
    this.mGridView.setAdapter(this.mAdapter);
    this.mGridView.setOnScrollListener(this);
  }

  private void setSate(int paramInt)
  {
    this.mCurState = paramInt;
    switch (paramInt)
    {
    case 2:
    default:
      return;
    case 0:
      showLoadingView(1);
      return;
    case 1:
      showIntroView();
      return;
    case 3:
    }
    showListView();
  }

  private void showIntroView()
  {
    this.mIntroCloudAlbumView.setVisibility(0);
    this.picFragment.cloudWithNoPhotos();
    this.mLoadingView.setVisibility(8);
    this.mTopInfoLayout.setVisibility(8);
    this.mTopInfoLine.setVisibility(8);
    this.mGridView.setVisibility(8);
    if (this.mBtnRight != null)
      this.mBtnRight.setVisibility(8);
  }

  private void showListView()
  {
    this.mIntroCloudAlbumView.setVisibility(8);
    this.picFragment.cloudWithPhotos();
    if (this.mBtnRight != null)
      this.mBtnRight.setVisibility(0);
    this.mLoadingView.setVisibility(8);
    this.mTopInfoLayout.setVisibility(0);
    this.mTopInfoLine.setVisibility(0);
    this.mGridView.setVisibility(0);
    CloudAlbumManager.getInstance().isSyncClosed();
    updateInfoView();
    if (CloudAlbumManager.getInstance().showIntroduceView(getActivity()))
      DialogUtil.showMsgDlg(getActivity(), 2131428517, 2131428509, 2131428510, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          if (CloudAlbumFragment.this.getActivity() != null)
            CloudAlbumManager.getInstance().closeIntroduceView(CloudAlbumFragment.this.getActivity());
        }
      });
  }

  private void showLoadingFooter(boolean paramBoolean)
  {
    this.mLoadingMore = paramBoolean;
    ProgressBar localProgressBar;
    if (this.mFooterProBar != null)
    {
      localProgressBar = this.mFooterProBar;
      if (!paramBoolean)
        break label72;
    }
    label72: for (int j = 0; ; j = 4)
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

  private void showLoadingView(int paramInt)
  {
    this.mIntroCloudAlbumView.setVisibility(8);
    this.mLoadingView.setVisibility(0);
    if (paramInt == 2)
    {
      this.mGridView.setVisibility(8);
      this.mTopInfoLayout.setVisibility(0);
      this.mTopInfoLine.setVisibility(0);
    }
    while (true)
    {
      this.mGridView.setVisibility(8);
      updateInfoView();
      return;
      this.mTopInfoLayout.setVisibility(8);
      this.mTopInfoLine.setVisibility(8);
    }
  }

  private void startCheckStatusTask()
  {
    if ((this.mCheckStatusTask != null) && (!this.mCheckStatusTask.isCancelled()) && (this.mCheckStatusTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    this.mCheckStatusTask = new CheckStatusTask();
    this.mCheckStatusTask.execute(new Void[0]);
  }

  private void startEnableCloudAlbumTask()
  {
    if ((this.mEnableCloudAlbumTask != null) && (!this.mEnableCloudAlbumTask.isCancelled()) && (this.mEnableCloudAlbumTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    this.mEnableCloudAlbumTask = new EnableCloudAlbumTask();
    this.mEnableCloudAlbumTask.execute(new Void[0]);
  }

  private boolean startGetDataTask(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    KXLog.d("CloudAlbumManager", "startGetDataTask(forceLoad:" + paramBoolean1 + ", forceCheck:" + paramBoolean2 + ", bBack:" + paramBoolean3 + ")");
    if ((this.mGetDataTask != null) && (!this.mGetDataTask.isCancelled()) && (this.mGetDataTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetDataTask.cancel(true);
      this.mGetDataTask = null;
    }
    if ((paramBoolean3) && (!hasMore()))
      return false;
    this.mGetDataTask = new GetDataTask();
    GetDataTask localGetDataTask = this.mGetDataTask;
    Boolean[] arrayOfBoolean = new Boolean[3];
    arrayOfBoolean[0] = Boolean.valueOf(paramBoolean1);
    arrayOfBoolean[1] = Boolean.valueOf(paramBoolean2);
    arrayOfBoolean[2] = Boolean.valueOf(paramBoolean3);
    localGetDataTask.execute(arrayOfBoolean);
    return true;
  }

  private void updateInfoView()
  {
    if (this.mGridView.getVisibility() == 0)
    {
      boolean bool = CloudAlbumManager.getInstance().isSyncClosed();
      if (bool)
      {
        this.mInfoSyncClosedView.setVisibility(0);
        this.mInfoText.setCompoundDrawablesWithIntrinsicBounds(2130837710, 0, 0, 0);
        this.mInfoText.setText(2131428507);
      }
      do
        return;
      while (bool);
      if (!ConnectivityUtil.isWifiConnected(getActivity()))
      {
        this.mInfoSyncClosedView.setVisibility(0);
        this.mInfoText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        this.mInfoText.setText(2131428508);
        return;
      }
      this.mInfoSyncClosedView.setVisibility(8);
      return;
    }
    this.mInfoSyncClosedView.setVisibility(8);
  }

  public PicturesFragment getPicFragment()
  {
    return this.picFragment;
  }

  public void onClick(View paramView)
  {
    if (paramView == this.mFooterView)
      onMoreItemClick();
    int i;
    do
    {
      return;
      i = paramView.getId();
      if (i != 2131362093)
        continue;
      String str = getResources().getString(2131428506);
      this.mProgressDialog = ProgressDialog.show(getActivity(), null, str);
      startEnableCloudAlbumTask();
      return;
    }
    while (i != 2131362100);
    startActivity(new Intent(getActivity(), CloudAlbumSettingActivity.class));
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    UserHabitUtils.getInstance(getActivity()).addUserHabit("visit_cloud_album");
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903082, paramViewGroup, false);
  }

  public void onDestroy()
  {
    CloudAlbumManager.getInstance().removeAllHandler();
    CloudAlbumModel.getInstance().geLocalPicList().clear();
    cancelTask();
    cancelTask(this.mCheckStatusTask);
    cancelTask(this.mEnableCloudAlbumTask);
    cancelTask(this.mGetDataTask);
    CloudAlbumManager.getInstance().setCloudAlbumDataListener(null);
    super.onDestroy();
  }

  public void onMoreItemClick()
  {
    showLoadingFooter(true);
    startGetDataTask(false, true, true);
  }

  public void onResume()
  {
    super.onResume();
  }

  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    if (hasMore())
    {
      int i = -1 + (paramInt1 + paramInt2);
      int j = -1 + this.mAdapter.getCount();
      if (j > 10)
        j -= 15;
      if ((i >= j) && (j > 0) && (!this.mNeedAutoLoading))
        this.mNeedAutoLoading = true;
    }
  }

  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
  {
    if (paramInt == 0)
      setCanLoad();
    while (true)
    {
      if (this.mNeedAutoLoading)
      {
        if (!this.mLoadingMore)
          onMoreItemClick();
        this.mNeedAutoLoading = false;
      }
      return;
      setNotCanLoad();
    }
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    initView();
    Bundle localBundle = getArguments();
    if (localBundle != null)
      this.mAutoRegister = "1".equals(localBundle.getString("autoRegister"));
    if (!dataInited())
    {
      setSate(0);
      startCheckStatusTask();
    }
    while (true)
    {
      CloudAlbumManager.getInstance().setCloudAlbumDataListener(new CloudAlbumManager.CloudAlbumDataListener()
      {
        public void onPicDelete(String paramString)
        {
          Iterator localIterator;
          if (CloudAlbumFragment.this.mAdapter != null)
            localIterator = CloudAlbumFragment.this.mPicItems.iterator();
          CloudAlbumPicItem localCloudAlbumPicItem;
          do
          {
            if (!localIterator.hasNext())
              return;
            localCloudAlbumPicItem = (CloudAlbumPicItem)localIterator.next();
          }
          while (!localCloudAlbumPicItem.mMD5.equals(paramString));
          localCloudAlbumPicItem.mState = 4;
          CloudAlbumFragment.this.mAdapter.setListData(CloudAlbumFragment.this.mPicItems, CloudAlbumFragment.this.hasMore());
          CloudAlbumFragment.this.mAdapter.notifyDataSetChanged();
        }
      });
      ((NotificationManager)getActivity().getSystemService("notification")).cancel(KaixinConst.ID_VIEW_CLOUDALBUM);
      CloudAlbumManager.getInstance().setWifiStateChangeListener(new CloudAlbumManager.WifiStateChangeListener()
      {
        public void syncStateChanged()
        {
          CloudAlbumFragment.this.updateInfoView();
        }

        public void wifiChanged()
        {
          CloudAlbumFragment.this.updateInfoView();
        }
      });
      updateInfoView();
      if ((this.mHaveGetDetail) && (!this.mShowLocalPic) && (!CloudAlbumManager.getInstance().isSyncClosed()))
        startGetDataTask(true, true, false);
      return;
      setSate(this.mCurState);
    }
  }

  public void refresh()
  {
    startGetDataTask(true, true, false);
  }

  public void setOnRefreshCloudListener(OnRefreshCloudListener paramOnRefreshCloudListener)
  {
    this.mRefreshCloudListener = paramOnRefreshCloudListener;
  }

  public void setPicFragment(PicturesFragment paramPicturesFragment)
  {
    this.picFragment = paramPicturesFragment;
  }

  class CheckStatusTask extends KXFragment.KXAsyncTask<Void, Void, Integer>
  {
    CheckStatusTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Void[] paramArrayOfVoid)
    {
      if (CloudAlbumManager.getInstance().registerState(CloudAlbumFragment.this.getActivity()))
        return Integer.valueOf(1);
      return Integer.valueOf(CloudAlbumEngine.getInstance().checkStatus(CloudAlbumFragment.this.getActivity()));
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if ((CloudAlbumFragment.this.getActivity() == null) || (CloudAlbumFragment.this.getView() == null))
        return;
      if (paramInteger.intValue() == 1)
      {
        CloudAlbumManager.getInstance().setRegisterState(CloudAlbumFragment.this.getActivity(), true);
        CloudAlbumFragment.this.showLoadingView(2);
        CloudAlbumFragment.this.startGetDataTask(false, true, false);
        return;
      }
      if (CloudAlbumFragment.this.mAutoRegister)
      {
        String str = CloudAlbumFragment.this.getResources().getString(2131428506);
        CloudAlbumFragment.this.mProgressDialog = ProgressDialog.show(CloudAlbumFragment.this.getActivity(), null, str);
        CloudAlbumFragment.this.startEnableCloudAlbumTask();
        return;
      }
      CloudAlbumFragment.this.setSate(1);
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  class EnableCloudAlbumTask extends KXFragment.KXAsyncTask<Void, Void, Integer>
  {
    EnableCloudAlbumTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Void[] paramArrayOfVoid)
    {
      return Integer.valueOf(CloudAlbumEngine.getInstance().openCloudAlbum(CloudAlbumFragment.this.getActivity()));
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if ((CloudAlbumFragment.this.getActivity() == null) || (CloudAlbumFragment.this.getView() == null))
        return;
      if (CloudAlbumFragment.this.mProgressDialog != null)
      {
        CloudAlbumFragment.this.mProgressDialog.dismiss();
        CloudAlbumFragment.this.mProgressDialog = null;
      }
      if (paramInteger.intValue() == 1)
      {
        CloudAlbumManager.getInstance().setRegisterState(CloudAlbumFragment.this.getActivity().getApplicationContext(), true);
        CloudAlbumFragment.this.showLoadingView(2);
        CloudAlbumFragment.this.startGetDataTask(false, true, false);
        return;
      }
      Toast.makeText(CloudAlbumFragment.this.getActivity(), "同步失败", 1).show();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  class GetDataTask extends AsyncTask<Boolean, Void, Integer>
  {
    private boolean mForceLoad = false;
    private boolean mGetMore = false;

    GetDataTask()
    {
    }

    protected Integer doInBackground(Boolean[] paramArrayOfBoolean)
    {
      ArrayList localArrayList1 = CloudAlbumModel.getInstance().geLocalPicList();
      this.mForceLoad = paramArrayOfBoolean[0].booleanValue();
      this.mGetMore = paramArrayOfBoolean[2].booleanValue();
      if ((!this.mGetMore) || (this.mForceLoad))
        localArrayList1.clear();
      int i = localArrayList1.size();
      CloudAlbumFragment localCloudAlbumFragment = CloudAlbumFragment.this;
      boolean bool;
      if (CloudAlbumManager.getInstance().isSyncClosed())
      {
        bool = false;
        localCloudAlbumFragment.mShowLocalPic = bool;
        if ((CloudAlbumFragment.this.mShowLocalPic) && ((!this.mGetMore) || (CloudAlbumFragment.this.mNeedGetMoreLocalPic)))
          CloudAlbumManager.getInstance().initData(localArrayList1, 90, paramArrayOfBoolean[1].booleanValue());
        CloudAlbumFragment.this.mPicItems.clear();
        CloudAlbumFragment.this.mPicItems.addAll(localArrayList1);
        if (localArrayList1.size() <= 0)
          break label217;
        CloudAlbumModel.getInstance().setFirstLocalPicModifiedTime(((CloudAlbumPicItem)CloudAlbumFragment.this.mPicItems.get(0)).mLastModfiedTime);
      }
      while (true)
      {
        CloudAlbumFragment.this.mNeedGetMoreLocalPic = false;
        if (localArrayList1.size() - i != 90)
          break label229;
        CloudAlbumFragment.this.mNeedGetMoreLocalPic = true;
        CloudAlbumManager.getInstance().startUploadDeamon(CloudAlbumFragment.this.getActivity());
        return null;
        bool = true;
        break;
        label217: CloudAlbumModel.getInstance().setFirstLocalPicModifiedTime(-1L);
      }
      label229: ArrayList localArrayList2 = CloudAlbumModel.getInstance().geSyncPicList();
      String str1 = "";
      if ((this.mGetMore) && (localArrayList2.size() > 0))
        str1 = ((CloudAlbumPicItem)localArrayList2.get(-1 + localArrayList2.size())).mCpid;
      KXLog.d("CloudAlbumManager", "GetDataTask doInBackground... lastCpid:" + str1 + ", mGetMore:" + this.mGetMore);
      int j = CloudAlbumEngine.getInstance().getSyncPicsList(CloudAlbumFragment.this.getActivity(), str1, 20);
      label329: label363: CloudAlbumPicItem localCloudAlbumPicItem1;
      if ((!this.mGetMore) || (j != 100) || (!CloudAlbumModel.getInstance().hasMore()))
      {
        Iterator localIterator = CloudAlbumModel.getInstance().geSyncPicList().iterator();
        if (localIterator.hasNext())
          localCloudAlbumPicItem1 = (CloudAlbumPicItem)localIterator.next();
      }
      label516: label518: for (int k = 0; ; k++)
      {
        int m = CloudAlbumFragment.this.mPicItems.size();
        int n = 0;
        if (k >= m);
        while (true)
        {
          if (n != 0)
            break label516;
          CloudAlbumFragment.this.mPicItems.add(localCloudAlbumPicItem1);
          break label363;
          break;
          String str2 = ((CloudAlbumPicItem)localArrayList2.get(-1 + localArrayList2.size())).mCpid;
          j = CloudAlbumEngine.getInstance().getSyncPicsList(CloudAlbumFragment.this.getActivity(), str2, 20);
          break label329;
          CloudAlbumPicItem localCloudAlbumPicItem2 = (CloudAlbumPicItem)CloudAlbumFragment.this.mPicItems.get(k);
          if (!localCloudAlbumPicItem1.mMD5.equals(localCloudAlbumPicItem2.mMD5))
            break label518;
          n = 1;
          localCloudAlbumPicItem2.mState = 1;
        }
        break label363;
      }
    }

    protected void onPostExecute(Integer paramInteger)
    {
      if ((CloudAlbumFragment.this.getActivity() == null) || (CloudAlbumFragment.this.getView() == null))
        return;
      if (CloudAlbumFragment.this.mRefreshCloudListener != null)
        CloudAlbumFragment.this.mRefreshCloudListener.onCloudRefreshed();
      KXLog.d("CloudAlbumManager", "GetDataTask onPostExecute... data size:" + CloudAlbumFragment.this.mPicItems.size() + ", mNeedGetMoreLocalPic:" + CloudAlbumFragment.this.mNeedGetMoreLocalPic + ", hasMore():" + CloudAlbumModel.getInstance().hasMore());
      if (!this.mGetMore)
      {
        CloudAlbumFragment.this.mHaveGetDetail = true;
        CloudAlbumFragment.this.setSate(3);
      }
      CloudAlbumFragment.this.showLoadingFooter(false);
      CloudAlbumFragment.this.mAdapter.setListData(CloudAlbumFragment.this.mPicItems, CloudAlbumFragment.this.hasMore());
      CloudAlbumFragment.this.mAdapter.notifyDataSetChanged();
    }
  }

  public static abstract interface OnRefreshCloudListener
  {
    public abstract void onCloudRefreshed();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.CloudAlbumFragment
 * JD-Core Version:    0.6.0
 */