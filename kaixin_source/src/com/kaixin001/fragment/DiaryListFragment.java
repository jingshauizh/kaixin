package com.kaixin001.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.DiaryListEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.item.DiaryInfo;
import com.kaixin001.model.DiaryListModel;
import com.kaixin001.model.FaceModel;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpConnection;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import java.util.List;

public class DiaryListFragment extends BaseFragment
  implements AdapterView.OnItemClickListener
{
  private static final int REQUEST_CODE_WRITE_DIARY = 2001;
  private boolean isCurrentCreated = false;
  private boolean isNeedRefresh = false;
  private String ismyfriend = "0";
  private ListView lvDiaryList;
  private DiaryListAdapter mAdapter;
  private final ArrayList<DiaryInfo> mDiaryListData = new ArrayList();
  private String mFname;
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  private View mFooterView;
  private String mFuid;
  private String mGender;
  private GetDiaryListTask mGetDiaryListTask = null;
  private ImageView mMiddleButton;
  private ProgressBar mMiddleProBar;
  private View mProgressBar;
  private ImageView mRightButton;
  private String mViewFname;
  private TextView tvEmptyList;

  private void cancelTask()
  {
    if ((this.mGetDiaryListTask != null) && (!this.mGetDiaryListTask.isCancelled()) && (this.mGetDiaryListTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetDiaryListTask.cancel(true);
      this.mGetDiaryListTask = null;
      DiaryListEngine.getInstance().cancel();
    }
  }

  private void downloadDiaryListData(int paramInt)
  {
    if ((this.mGetDiaryListTask != null) && (!this.mGetDiaryListTask.isCancelled()) && (this.mGetDiaryListTask.getStatus() != AsyncTask.Status.FINISHED));
    do
      return;
    while (HttpConnection.checkNetworkAvailable(getActivity().getApplicationContext()) < 0);
    showLoading(1);
    this.mFooterTV.setTextColor(getResources().getColor(2130839395));
    this.mFooterProBar.setVisibility(0);
    this.mGetDiaryListTask = new GetDiaryListTask(null);
    GetDiaryListTask localGetDiaryListTask = this.mGetDiaryListTask;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(paramInt);
    localGetDiaryListTask.execute(arrayOfString);
  }

  private String getEmptyTextView(String paramString)
  {
    String str;
    if (User.getInstance().getUID().compareTo(this.mFuid) == 0)
      str = getResources().getString(2131427565);
    while (true)
    {
      return paramString.replace("*", str);
      if (this.mGender.compareTo("0") == 0)
      {
        str = getResources().getString(2131427384);
        continue;
      }
      str = getResources().getString(2131427385);
    }
  }

  private void initDiaryListData(int paramInt)
  {
    this.mDiaryListData.clear();
    if (!super.checkNetworkAndHint(true))
    {
      this.tvEmptyList.setVisibility(8);
      this.mProgressBar.setVisibility(8);
      return;
    }
    this.lvDiaryList.setVisibility(8);
    this.tvEmptyList.setVisibility(8);
    this.mProgressBar.setVisibility(0);
    showLoading(1);
    this.mGetDiaryListTask = new GetDiaryListTask(null);
    GetDiaryListTask localGetDiaryListTask = this.mGetDiaryListTask;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(paramInt);
    localGetDiaryListTask.execute(arrayOfString);
  }

  private void initViews(View paramView)
  {
    this.mFooterView = getActivity().getLayoutInflater().inflate(2130903259, null);
    this.mFooterView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        DiaryListModel localDiaryListModel = DiaryListModel.getInstance();
        ArrayList localArrayList = localDiaryListModel.getDiaryList();
        if ((localArrayList == null) || (localArrayList.size() < DiaryListFragment.this.mDiaryListData.size()))
          DiaryListFragment.this.showLoadingFooter(true);
        for (boolean bool = true; ; bool = false)
        {
          if ((localArrayList != null) && (localArrayList.size() < localDiaryListModel.getTotal()))
            DiaryListFragment.this.refreshMore(bool);
          return;
          DiaryListFragment.this.updateDiaryList();
        }
      }
    });
    this.mFooterTV = ((TextView)this.mFooterView.findViewById(2131362073));
    this.mFooterTV.setText(2131427748);
    this.mFooterProBar = ((ProgressBar)this.mFooterView.findViewById(2131362072));
    this.tvEmptyList = ((TextView)paramView.findViewById(2131362171));
    this.lvDiaryList = ((ListView)paramView.findViewById(2131362172));
    this.lvDiaryList.setOnItemClickListener(this);
    this.mAdapter = new DiaryListAdapter(getActivity(), 2130903096, this.mDiaryListData);
    this.lvDiaryList.setAdapter(this.mAdapter);
    this.mProgressBar = paramView.findViewById(2131362173);
  }

  private void refreshMore(boolean paramBoolean)
  {
    if (!super.checkNetworkAndHint(paramBoolean))
    {
      showLoadingFooter(false);
      return;
    }
    int i = this.mDiaryListData.size();
    if ((this.mDiaryListData.size() > 0) && (TextUtils.isEmpty(((DiaryInfo)this.mDiaryListData.get(i - 1)).diaryFeedDid)))
      i--;
    downloadDiaryListData(i);
  }

  private void showLoading(int paramInt)
  {
    if (paramInt > 0)
    {
      this.mMiddleProBar.setVisibility(0);
      this.mMiddleButton.setVisibility(8);
      this.mMiddleButton.setEnabled(false);
      return;
    }
    this.mMiddleProBar.setVisibility(8);
    this.mMiddleButton.setVisibility(0);
    this.mMiddleButton.setEnabled(true);
  }

  private void showLoadingFooter(boolean paramBoolean)
  {
    ProgressBar localProgressBar;
    if (this.mFooterProBar != null)
    {
      localProgressBar = this.mFooterProBar;
      if (!paramBoolean)
        break label66;
    }
    label66: for (int j = 0; ; j = 4)
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

  private void updateDiaryList()
  {
    this.mDiaryListData.clear();
    DiaryListModel localDiaryListModel = DiaryListModel.getInstance();
    ArrayList localArrayList = localDiaryListModel.getDiaryList();
    int i = localDiaryListModel.getTotal();
    if (localArrayList != null)
    {
      this.mDiaryListData.addAll(localArrayList);
      if (localArrayList.size() < i)
      {
        DiaryInfo localDiaryInfo = new DiaryInfo();
        localDiaryInfo.diaryFeedDid = "";
        this.mDiaryListData.add(localDiaryInfo);
      }
    }
    if (this.mAdapter != null)
      this.mAdapter.notifyDataSetChanged();
  }

  private void writeNewDiary()
  {
    if (UploadTaskListEngine.getInstance().getWaitingTaskCount() < 20)
    {
      startFragmentForResult(new Intent(getActivity(), WriteDiaryFragment.class), 2001, 3, true);
      return;
    }
    showCantUploadOptions();
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt1 == 2001)
      this.isNeedRefresh = true;
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (CrashRecoverUtil.isCrashBefore())
    {
      CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
      IntentUtil.returnToDesktop(getActivity());
      return;
    }
    this.isCurrentCreated = true;
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903095, paramViewGroup, false);
  }

  public void onDestroy()
  {
    cancelTask();
    DiaryListModel.getInstance().clear();
    super.onDestroy();
  }

  public void onDestroyView()
  {
    super.onDestroyView();
    this.isCurrentCreated = false;
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (this.mDiaryListData == null);
    String str1;
    do
    {
      while (true)
      {
        return;
        DiaryInfo localDiaryInfo = (DiaryInfo)this.mDiaryListData.get(paramInt);
        try
        {
          str1 = localDiaryInfo.diaryFeedPrivacy;
          String str2 = localDiaryInfo.diaryFeedDid;
          if ((str1.compareTo("0") != 0) && ((str1.compareTo("1") != 0) || (this.ismyfriend.compareTo("1") != 0)) && ((str1.compareTo("2") != 0) || (User.getInstance().getUID().compareTo(this.mFuid) != 0)) && ((str1.compareTo("3") != 0) || (User.getInstance().getUID().compareTo(this.mFuid) != 0)))
            break;
          if (TextUtils.isEmpty(str2))
            continue;
          Intent localIntent = new Intent(getActivity(), DiaryDetailFragment.class);
          Bundle localBundle = new Bundle();
          localBundle.putString("fuid", this.mFuid);
          localBundle.putString("fname", this.mFname);
          localBundle.putString("did", str2);
          localBundle.putString("title", localDiaryInfo.diaryFeedTitle);
          localBundle.putString("intro", localDiaryInfo.diaryFeedContent);
          localIntent.putExtras(localBundle);
          startFragment(localIntent, true, 1);
          return;
        }
        catch (Exception localException)
        {
          KXLog.e("DiaryListFragment", "onItemClick", localException);
          return;
        }
      }
      if ((str1.compareTo("2") != 0) || (User.getInstance().getUID().compareTo(this.mFuid) == 0))
        continue;
      DialogUtil.showKXAlertDialog(getActivity(), 2131427774, null);
      return;
    }
    while ((str1.compareTo("1") != 0) || (this.ismyfriend.compareTo("0") != 0));
    DialogUtil.showKXAlertDialog(getActivity(), 2131427775, null);
  }

  public void onResume()
  {
    super.onResume();
    if (this.isNeedRefresh)
    {
      if (!checkNetworkAndHint(true))
        return;
      DiaryListModel.getInstance().clear();
      downloadDiaryListData(0);
      this.mAdapter.setNotifyOnChange(true);
    }
    this.isNeedRefresh = false;
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      String str1 = localBundle.getString("fuid");
      if (str1 != null)
        this.mFuid = str1;
      String str2 = localBundle.getString("fname");
      if (str2 != null)
        this.mFname = str2;
      String str3 = localBundle.getString("ismyfriend");
      if (str3 != null)
        this.ismyfriend = str3;
      String str4 = localBundle.getString("gender");
      if (str4 != null)
        this.mGender = str4;
    }
    if (this.mFname.length() > 3);
    for (this.mViewFname = (this.mFname.substring(0, 3) + "..."); ; this.mViewFname = this.mFname)
    {
      initViews(paramView);
      setTitleBar(paramView);
      if (!this.isCurrentCreated)
        break;
      initDiaryListData(0);
      return;
    }
    updateDiaryList();
  }

  protected void setTitleBar(View paramView)
  {
    ImageView localImageView = (ImageView)paramView.findViewById(2131362914);
    localImageView.setVisibility(0);
    label179: TextView localTextView;
    String str;
    if (User.getInstance().getUID().compareTo(this.mFuid) == 0)
    {
      getResources().getString(2131427734);
      localImageView.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          DiaryListFragment.this.finish();
        }
      });
      this.mMiddleProBar = ((ProgressBar)paramView.findViewById(2131362925));
      this.mMiddleButton = ((ImageView)paramView.findViewById(2131362924));
      this.mMiddleButton.setImageResource(2130838838);
      this.mMiddleButton.setVisibility(0);
      this.mMiddleButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          if (!DiaryListFragment.this.checkNetworkAndHint(true))
            return;
          DiaryListModel.getInstance().clear();
          DiaryListFragment.this.downloadDiaryListData(0);
        }
      });
      this.mRightButton = ((ImageView)paramView.findViewById(2131362928));
      if (!this.mFuid.equals(User.getInstance().getUID()))
        break label264;
      this.mRightButton.setVisibility(0);
      this.mRightButton.setImageResource(2130839066);
      this.mRightButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          DiaryListFragment.this.writeNewDiary();
        }
      });
      ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
      localTextView = (TextView)paramView.findViewById(2131362920);
      str = getResources().getString(2131427746);
      if (User.getInstance().getUID().compareTo(this.mFuid) != 0)
        break label279;
      localTextView.setText(getResources().getString(2131427736));
    }
    while (true)
    {
      localTextView.setVisibility(0);
      return;
      break;
      label264: paramView.findViewById(2131362927).setVisibility(8);
      break label179;
      label279: localTextView.setText(this.mViewFname + str);
    }
  }

  private class DiaryListAdapter extends ArrayAdapter<DiaryInfo>
  {
    private ArrayList<DiaryInfo> items;

    public DiaryListAdapter(int paramArrayList, ArrayList<DiaryInfo> arg3)
    {
      super(i, localList);
      this.items = localList;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      DiaryInfo localDiaryInfo = (DiaryInfo)this.items.get(paramInt);
      try
      {
        if (TextUtils.isEmpty(localDiaryInfo.diaryFeedDid))
          return DiaryListFragment.this.mFooterView;
        if ((paramView == null) || (paramView == DiaryListFragment.this.mFooterView))
        {
          paramView = ((LayoutInflater)DiaryListFragment.this.getActivity().getSystemService("layout_inflater")).inflate(2130903096, null);
          localDiaryListItemViewTag1 = new DiaryListFragment.DiaryListItemViewTag(DiaryListFragment.this, paramView);
        }
      }
      catch (Exception localException2)
      {
        try
        {
          DiaryListFragment.DiaryListItemViewTag localDiaryListItemViewTag1;
          paramView.setTag(localDiaryListItemViewTag1);
          DiaryListFragment.DiaryListItemViewTag localDiaryListItemViewTag2 = localDiaryListItemViewTag1;
          while (true)
          {
            localDiaryListItemViewTag2.setDiaryItem(paramView, localDiaryInfo);
            break;
            localDiaryListItemViewTag2 = (DiaryListFragment.DiaryListItemViewTag)paramView.getTag();
            continue;
            localException1 = localException1;
            label118: KXLog.e("DiaryListAdapter", "getView", localException1);
          }
        }
        catch (Exception localException2)
        {
          break label118;
        }
      }
      return paramView;
    }
  }

  private class DiaryListItemViewTag
  {
    private TextView contView;
    private LinearLayout privacyView;
    private TextView tvTime;
    private TextView tvTitle;

    public DiaryListItemViewTag(View arg2)
    {
      Object localObject;
      this.tvTitle = ((TextView)localObject.findViewById(2131362177));
      this.contView = ((TextView)localObject.findViewById(2131362181));
      this.tvTime = ((TextView)localObject.findViewById(2131362182));
      this.privacyView = ((LinearLayout)localObject.findViewById(2131362178));
    }

    public void setDiaryItem(View paramView, DiaryInfo paramDiaryInfo)
    {
      while (true)
      {
        String str1;
        try
        {
          str1 = paramDiaryInfo.diaryFeedPrivacy;
          if ((str1.compareTo("0") != 0) && ((str1.compareTo("1") != 0) || (DiaryListFragment.this.ismyfriend.compareTo("1") != 0)))
            continue;
          this.privacyView.setVisibility(8);
          this.contView.setVisibility(0);
          String str2 = paramDiaryInfo.diaryFeedTitle;
          this.tvTitle.setText(str2);
          String str3 = paramDiaryInfo.diaryFeedContent;
          SpannableString localSpannableString = FaceModel.getInstance().processTextForFace(str3);
          this.contView.setText(localSpannableString);
          String str4 = paramDiaryInfo.diaryFeedStrctime;
          this.tvTime.setText(str4);
          return;
          if (str1.compareTo("2") == 0)
          {
            this.privacyView.setVisibility(0);
            this.contView.setVisibility(8);
            continue;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("DiaryListItemViewTag", "setDiaryItem", localException);
          return;
        }
        if (str1.compareTo("1") != 0)
          continue;
        this.privacyView.setVisibility(8);
        this.contView.setVisibility(0);
      }
    }
  }

  private class GetDiaryListTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    private int mStart = 0;

    private GetDiaryListTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      this.mStart = Integer.parseInt(paramArrayOfString[0]);
      try
      {
        Boolean localBoolean = Boolean.valueOf(DiaryListEngine.getInstance().getDiaryList(DiaryListFragment.this.getActivity().getApplicationContext(), DiaryListFragment.this.mFuid, this.mStart));
        return localBoolean;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      DiaryListFragment.this.showLoading(0);
      DiaryListFragment.this.showLoadingFooter(false);
      if (paramBoolean == null)
      {
        DiaryListFragment.this.finish();
        return;
      }
      try
      {
        if (!paramBoolean.booleanValue())
          break label286;
        DiaryListModel localDiaryListModel = DiaryListModel.getInstance();
        ArrayList localArrayList = localDiaryListModel.getDiaryList();
        int i = localDiaryListModel.getTotal();
        if ((this.mStart == 0) || (DiaryListFragment.this.mDiaryListData.size() == 0) || (DiaryListFragment.this.mFooterProBar.getVisibility() == 0))
        {
          DiaryListFragment.this.updateDiaryList();
          if (this.mStart == 0)
            DiaryListFragment.this.lvDiaryList.setSelection(0);
          if ((localArrayList != null) && (localArrayList.size() > 0) && (localArrayList.size() < i))
          {
            DiaryListFragment.this.mGetDiaryListTask = null;
            DiaryListFragment.this.refreshMore(false);
          }
        }
        if ((DiaryListFragment.this.mDiaryListData == null) || (DiaryListFragment.this.mDiaryListData.size() == 0))
        {
          String str = DiaryListFragment.this.getResources().getString(2131427747);
          DiaryListFragment.this.tvEmptyList.setText(DiaryListFragment.this.getEmptyTextView(str));
          DiaryListFragment.this.mProgressBar.setVisibility(8);
          DiaryListFragment.this.lvDiaryList.setVisibility(8);
          DiaryListFragment.this.tvEmptyList.setVisibility(0);
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("DiaryListFragment", "onPostExecute", localException);
        return;
      }
      DiaryListFragment.this.mProgressBar.setVisibility(8);
      DiaryListFragment.this.tvEmptyList.setVisibility(8);
      DiaryListFragment.this.lvDiaryList.setVisibility(0);
      return;
      label286: Toast.makeText(DiaryListFragment.this.getActivity(), 2131427751, 0).show();
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
 * Qualified Name:     com.kaixin001.fragment.DiaryListFragment
 * JD-Core Version:    0.6.0
 */