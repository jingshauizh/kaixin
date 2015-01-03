package com.kaixin001.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.adapter.AlbumlistAdapter;
import com.kaixin001.engine.AlbumListEngine;
import com.kaixin001.engine.AlbumPhotoEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.item.AlbumInfo;
import com.kaixin001.model.AlbumListModel;
import com.kaixin001.model.User;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;

public class AlbumListFragment extends BaseFragment
  implements AdapterView.OnItemClickListener
{
  public static final String FLAG_PRIVACY_0 = "0";
  public static final String FLAG_PRIVACY_1 = "1";
  public static final String FLAG_PRIVACY_2 = "2";
  public static final String FLAG_PRIVACY_3 = "3";
  private GetAlbumTask getAlbumTask;
  private boolean isCurrentCreated = false;
  private String ismyfriend = "0";
  private GridView lvAlbumList;
  private AlbumlistAdapter mAdapter;
  private ArrayList<AlbumInfo> mAlbumlist = new ArrayList();
  private AlbumInfo mCurrentAlbum = null;
  private Dialog mDialog = null;
  private EditText mEditPassword = null;
  private String mFname;
  private String mFuid;
  private String mGender;
  private GetAlbumlistTask mGetAlbumlistTask;
  private View mProgressBar;
  private ProgressDialog mProgressDialog;
  private TextView tvEmptyList;

  private void getAlbumList()
  {
    if (this.mCurrentAlbum == null)
      return;
    String str = this.mEditPassword.getText().toString().trim();
    if (TextUtils.isEmpty(str))
    {
      Toast.makeText(getActivity(), 2131427482, 0).show();
      return;
    }
    this.getAlbumTask = new GetAlbumTask(null);
    this.getAlbumTask.execute(new String[] { str });
    this.mProgressDialog = ProgressDialog.show(getActivity(), "", getResources().getText(2131427483), true, true, new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramDialogInterface)
      {
        AlbumPhotoEngine.getInstance().cancel();
        AlbumListFragment.this.getAlbumTask.cancel(true);
      }
    });
  }

  private void getAlbumlistData()
  {
    if (!super.checkNetworkAndHint(true))
    {
      this.lvAlbumList.setVisibility(8);
      this.tvEmptyList.setVisibility(8);
      this.mProgressBar.setVisibility(8);
      return;
    }
    this.lvAlbumList.setVisibility(8);
    this.tvEmptyList.setVisibility(8);
    this.mProgressBar.setVisibility(0);
    this.mGetAlbumlistTask = new GetAlbumlistTask(null);
    this.mGetAlbumlistTask.execute(new Void[0]);
  }

  private String getEmptyTextView(String paramString)
  {
    if (this.mGender.compareTo("0") == 0);
    for (String str = getResources().getString(2131427384); ; str = getResources().getString(2131427385))
      return paramString.replace("*", str);
  }

  private void initEditTextPassword()
  {
    if (this.mEditPassword != null)
    {
      ViewGroup localViewGroup = (ViewGroup)this.mEditPassword.getParent();
      if (localViewGroup != null)
        localViewGroup.removeView(this.mEditPassword);
      this.mEditPassword.setText("");
      return;
    }
    this.mEditPassword = new EditText(getActivity());
    this.mEditPassword.setLayoutParams(new TableLayout.LayoutParams(-2, -2));
    this.mEditPassword.setRawInputType(129);
    this.mEditPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
    EditText localEditText = this.mEditPassword;
    InputFilter[] arrayOfInputFilter = new InputFilter[1];
    arrayOfInputFilter[0] = new InputFilter.LengthFilter(20);
    localEditText.setFilters(arrayOfInputFilter);
  }

  private void initViews(View paramView)
  {
    this.tvEmptyList = ((TextView)paramView.findViewById(2131361849));
    this.lvAlbumList = ((GridView)paramView.findViewById(2131361850));
    this.lvAlbumList.setOnItemClickListener(this);
    this.mProgressBar = paramView.findViewById(2131361851);
    initEditTextPassword();
  }

  private void showAlbumView(String paramString)
  {
    if (this.mCurrentAlbum == null)
      return;
    IntentUtil.showViewAlbumNotFromViewPhoto(getActivity(), this, this.mCurrentAlbum.albumsFeedAlbumid, this.mCurrentAlbum.albumsFeedAlbumtitle, this.mFuid, this.mFname, this.mCurrentAlbum.albumsFeedPicnum, paramString);
  }

  private void showInputPwd()
  {
    initEditTextPassword();
    if (this.mDialog != null)
    {
      if (this.mDialog.isShowing())
        this.mDialog.dismiss();
      this.mDialog = null;
    }
    this.mDialog = DialogUtil.showMsgDlgStd(getActivity(), 2131427329, 2131427482, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        AlbumListFragment.this.getAlbumList();
      }
    });
  }

  private void updateData()
  {
    if (User.getInstance().getUID().compareTo(this.mFuid) == 0);
    for (ArrayList localArrayList = AlbumListModel.getMyAlbumList().getAlbumslist(); (localArrayList == null) || (localArrayList.size() == 0); localArrayList = AlbumListModel.getInstance().getAlbumslist())
    {
      String str = getResources().getString(2131427501);
      this.tvEmptyList.setText(getEmptyTextView(str));
      this.mProgressBar.setVisibility(8);
      this.lvAlbumList.setVisibility(8);
      this.tvEmptyList.setVisibility(0);
      return;
    }
    int i = localArrayList.size();
    if (this.isCurrentCreated)
      this.mAlbumlist.clear();
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        this.mAdapter = new AlbumlistAdapter(this, 2130903148, this.mAlbumlist, this.ismyfriend);
        this.lvAlbumList.setAdapter(this.mAdapter);
        this.mProgressBar.setVisibility(8);
        this.tvEmptyList.setVisibility(8);
        this.lvAlbumList.setVisibility(0);
        return;
      }
      AlbumInfo localAlbumInfo = (AlbumInfo)localArrayList.get(j);
      this.mAlbumlist.add(localAlbumInfo);
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    String str = null;
    if (localBundle != null)
      str = localBundle.getString("action");
    if ((str != null) && (!str.equals("com.kaixin001.VIEW_ALBUMLIST")) && (CrashRecoverUtil.isCrashBefore()))
    {
      CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
      IntentUtil.returnToDesktop(getActivity());
      return;
    }
    this.isCurrentCreated = true;
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903054, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if (this.mDialog != null)
    {
      if (this.mDialog.isShowing())
        this.mDialog.dismiss();
      this.mDialog = null;
    }
    this.mEditPassword = null;
    if ((this.mGetAlbumlistTask != null) && (this.mGetAlbumlistTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetAlbumlistTask.cancel(true);
      this.mGetAlbumlistTask = null;
      AlbumListEngine.getInstance().cancel();
    }
    cancelTask(this.getAlbumTask);
    if (AlbumListModel.getInstance() != null)
      AlbumListModel.getInstance().clear();
    if (AlbumListModel.getMyAlbumList() != null)
      AlbumListModel.getMyAlbumList().clear();
    super.onDestroy();
  }

  public void onDestroyView()
  {
    super.onDestroyView();
    this.isCurrentCreated = false;
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (this.mAlbumlist == null);
    String str;
    do
    {
      do
      {
        return;
        this.mCurrentAlbum = ((AlbumInfo)this.mAlbumlist.get(paramInt));
      }
      while (this.mCurrentAlbum == null);
      try
      {
        str = this.mCurrentAlbum.albumsFeedPrivacy;
        if ((!"0".equals(str)) && ((!"1".equals(str)) || (!this.ismyfriend.equals("1"))) && ((!"2".equals(str)) || (!User.getInstance().getUID().equals(this.mFuid))) && ((!"3".equals(str)) || (!User.getInstance().getUID().equals(this.mFuid))))
          continue;
        showAlbumView("");
        return;
      }
      catch (Exception localException)
      {
        KXLog.e("AlbumListActivity", "onItemClick", localException);
        return;
      }
    }
    while ((!"2".equals(str)) || (User.getInstance().getUID().equals(this.mFuid)));
    showInputPwd();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    User.getInstance().loadDataIfEmpty(getActivity().getApplicationContext());
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
    if (this.mFname.length() > 3)
      this.mFname = (this.mFname.substring(0, 3) + "...");
    initViews(paramView);
    setTitleBar(paramView);
    if (this.isCurrentCreated)
    {
      getAlbumlistData();
      return;
    }
    updateData();
  }

  protected void setTitleBar(View paramView)
  {
    ImageView localImageView = (ImageView)paramView.findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        AlbumListFragment.this.finish();
      }
    });
    ((ImageView)paramView.findViewById(2131362928)).setVisibility(8);
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    String str = getResources().getString(2131427740);
    if (User.getInstance().getUID().compareTo(this.mFuid) == 0)
      localTextView.setText(getResources().getString(2131427735));
    while (true)
    {
      localTextView.setVisibility(0);
      if (User.getInstance().getUID().compareTo(this.mFuid) != 0)
        break;
      getResources().getString(2131427734);
      return;
      localTextView.setText(this.mFname + str);
    }
  }

  private class GetAlbumTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private String m_pwd;

    private GetAlbumTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
        return Integer.valueOf(-1);
      if ((AlbumListFragment.this.mCurrentAlbum == null) || (AlbumListFragment.this.mCurrentAlbum.albumsFeedAlbumid == null))
        return Integer.valueOf(-1);
      try
      {
        this.m_pwd = paramArrayOfString[0];
        if (AlbumListFragment.this.mCurrentAlbum.albumsFeedAlbumid.compareTo("0") == 0)
          return Integer.valueOf(AlbumPhotoEngine.getInstance().getLogoPhotoList(AlbumListFragment.this.getActivity().getApplicationContext(), AlbumListFragment.this.mFuid, 0, 24));
        Integer localInteger = Integer.valueOf(AlbumPhotoEngine.getInstance().getAlbumPhotoList(AlbumListFragment.this.getActivity().getApplicationContext(), AlbumListFragment.this.mCurrentAlbum.albumsFeedAlbumid, AlbumListFragment.this.mFuid, this.m_pwd, 0, 24));
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if (paramInteger == null)
      {
        AlbumListFragment.this.finish();
        return;
      }
      try
      {
        AlbumListFragment.this.mProgressDialog.dismiss();
        if (paramInteger.intValue() == 1)
        {
          AlbumListFragment.this.showAlbumView(this.m_pwd);
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("AlbumListActivity", "onPostExecute", localException);
        return;
      }
      Toast.makeText(AlbumListFragment.this.getActivity(), 2131427484, 0).show();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class GetAlbumlistTask extends KXFragment.KXAsyncTask<Void, Void, Boolean>
  {
    private GetAlbumlistTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(Void[] paramArrayOfVoid)
    {
      try
      {
        Boolean localBoolean = Boolean.valueOf(AlbumListEngine.getInstance().getAlbumPhotoList(AlbumListFragment.this.getActivity().getApplicationContext(), AlbumListFragment.this.mFuid));
        return localBoolean;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      if (paramBoolean == null)
      {
        AlbumListFragment.this.finish();
        return;
      }
      try
      {
        if (paramBoolean.booleanValue())
        {
          AlbumListFragment.this.updateData();
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("GetAlbumlistTask", "onPostExecute", localException);
        return;
      }
      Toast.makeText(AlbumListFragment.this.getActivity(), 2131427741, 0).show();
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
 * Qualified Name:     com.kaixin001.fragment.AlbumListFragment
 * JD-Core Version:    0.6.0
 */