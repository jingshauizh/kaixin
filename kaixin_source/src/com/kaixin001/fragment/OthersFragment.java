package com.kaixin001.fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.kaixin001.activity.MainActivity;
import com.kaixin001.activity.SettingActivity;
import com.kaixin001.model.Setting;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.IntentUtil;
import java.util.ArrayList;

public class OthersFragment extends BaseFragment
  implements AdapterView.OnItemClickListener
{
  private static final int FLAG_ACCOUNT_SWITCH = 5;
  private static final int FLAG_FEEDBACK = 3;
  private static final int FLAG_IMPORT_CONTACTS = 1;
  private static final int FLAG_SETTINGS = 4;
  private static final int FLAG_UPLOAD_TASK_LIST = 2;
  private Button mLogout;
  private final ArrayList<MoreItem> mOthersList = new ArrayList();

  private void initItems()
  {
    this.mOthersList.clear();
    Resources localResources = getResources();
    MoreItem localMoreItem1 = new MoreItem(localResources.getString(2131427792), "", 1, MoreItem.TYPE_GROUP_TOP);
    this.mOthersList.add(localMoreItem1);
    MoreItem localMoreItem2 = new MoreItem(localResources.getString(2131427783), "", 2, MoreItem.TYPE_GROUP_MIDDLE);
    this.mOthersList.add(localMoreItem2);
    MoreItem localMoreItem3 = new MoreItem(localResources.getString(2131427781), "", 3, MoreItem.TYPE_GROUP_MIDDLE);
    this.mOthersList.add(localMoreItem3);
    MoreItem localMoreItem4 = new MoreItem(localResources.getString(2131428549), "", 5, MoreItem.TYPE_GROUP_MIDDLE);
    this.mOthersList.add(localMoreItem4);
    MoreItem localMoreItem5 = new MoreItem(localResources.getString(2131427782), "", 4, MoreItem.TYPE_GROUP_BOTTOM);
    this.mOthersList.add(localMoreItem5);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (CrashRecoverUtil.isCrashBefore())
    {
      CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
      IntentUtil.returnToDesktop(getActivity());
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903268, paramViewGroup, false);
  }

  public void onDestroy()
  {
    this.mOthersList.clear();
    super.onDestroy();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    MoreItem localMoreItem;
    try
    {
      localMoreItem = (MoreItem)this.mOthersList.get((int)paramLong);
      switch (localMoreItem.getFlag())
      {
      case 1:
        startFragment(new Intent(getActivity(), ContactsPrepareFragment.class), true, 1);
        return;
      case 4:
      case 3:
      case 2:
      case 5:
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    startActivity(new Intent(getActivity(), SettingActivity.class));
    return;
    Intent localIntent = new Intent(getActivity(), FeedbackFragment.class);
    localIntent.putExtra("label", localMoreItem.getLabel());
    localIntent.putExtra("link", localMoreItem.getLink());
    startFragment(localIntent, true, 1);
    return;
    startFragment(new Intent(getActivity(), UploadTaskListFragment.class), true, 1);
    return;
    startFragment(new Intent(getActivity(), AccountSwitchFragment.class), true, 1);
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 82)
      return true;
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mLogout = ((Button)paramView.findViewById(2131363327));
    this.mLogout.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        OthersFragment.this.dismissDialog();
        OthersFragment.this.dialog = DialogUtil.showMsgDlgStd(OthersFragment.this.getActivity(), 2131427697, 2131427369, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramDialogInterface, int paramInt)
          {
            if (!Setting.getInstance().isTestVersion())
            {
              Intent localIntent = new Intent(OthersFragment.this.getActivity(), MainActivity.class);
              localIntent.setFlags(67108864);
              Bundle localBundle = new Bundle();
              localBundle.putString("logout", "1");
              localIntent.putExtras(localBundle);
              OthersFragment.this.startActivity(localIntent);
            }
          }
        });
      }
    });
    setTitleBar(paramView);
    initItems();
    MoreItemAdapter localMoreItemAdapter = new MoreItemAdapter(null);
    ListView localListView = (ListView)paramView.findViewById(2131363326);
    localListView.setAdapter(localMoreItemAdapter);
    localListView.setOnItemClickListener(this);
  }

  protected void setTitleBar(View paramView)
  {
    ImageView localImageView = (ImageView)paramView.findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (OthersFragment.this.isMenuListVisibleInMain())
        {
          OthersFragment.this.showSubActivityInMain();
          return;
        }
        OthersFragment.this.showMenuListInMain();
      }
    });
    ((ImageView)paramView.findViewById(2131362928)).setVisibility(8);
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    localTextView.setVisibility(0);
    localTextView.setText(getResources().getText(2131428358));
  }

  private static class MoreItem
  {
    public static int TYPE_GROUP_BOTTOM;
    public static int TYPE_GROUP_MIDDLE;
    public static int TYPE_GROUP_TOP;
    public static int TYPE_SINGLELINE = 0;
    private int mFlag;
    private String mLabel = "";
    private String mLink = "";
    private int mType = TYPE_SINGLELINE;

    static
    {
      TYPE_GROUP_TOP = 1;
      TYPE_GROUP_MIDDLE = 2;
      TYPE_GROUP_BOTTOM = 3;
    }

    public MoreItem(String paramString1, String paramString2, int paramInt1, int paramInt2)
    {
      if (paramString1 != null)
        this.mLabel = paramString1;
      if (paramString2 != null)
        this.mLink = paramString2;
      this.mFlag = paramInt1;
      this.mType = paramInt2;
    }

    public int getFlag()
    {
      return this.mFlag;
    }

    public String getLabel()
    {
      return this.mLabel;
    }

    public String getLink()
    {
      return this.mLink;
    }

    public int getType()
    {
      return this.mType;
    }
  }

  private class MoreItemAdapter extends BaseAdapter
  {
    private MoreItemAdapter()
    {
    }

    public int getCount()
    {
      return OthersFragment.this.mOthersList.size();
    }

    public Object getItem(int paramInt)
    {
      if ((paramInt < 0) || (paramInt >= getCount()))
        return null;
      return OthersFragment.this.mOthersList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      OthersFragment.MoreItem localMoreItem = (OthersFragment.MoreItem)getItem(paramInt);
      if (localMoreItem == null)
        return paramView;
      OthersFragment.MoreItemViewHolder localMoreItemViewHolder;
      if (paramView == null)
      {
        paramView = OthersFragment.this.getActivity().getLayoutInflater().inflate(2130903230, null);
        localMoreItemViewHolder = new OthersFragment.MoreItemViewHolder(OthersFragment.this, paramView);
        paramView.setTag(localMoreItemViewHolder);
      }
      while (true)
      {
        if (localMoreItemViewHolder != null)
          localMoreItemViewHolder.showItem(localMoreItem);
        return paramView;
        localMoreItemViewHolder = (OthersFragment.MoreItemViewHolder)paramView.getTag();
      }
    }
  }

  private class MoreItemViewHolder
  {
    public View layout = null;
    public View layoutItem = null;
    public TextView txtTitle = null;

    public MoreItemViewHolder(View arg2)
    {
      Object localObject;
      this.layout = localObject;
      if (this.layout != null)
      {
        this.layoutItem = this.layout.findViewById(2131362892);
        this.txtTitle = ((TextView)this.layout.findViewById(2131363094));
      }
    }

    public void showItem(OthersFragment.MoreItem paramMoreItem)
    {
      if (paramMoreItem == null);
      do
      {
        return;
        if (this.txtTitle == null)
          continue;
        this.txtTitle.setText(paramMoreItem.getLabel());
      }
      while ((this.layoutItem == null) || (this.layout == null));
      int i = this.layout.getPaddingLeft();
      int j = this.layout.getPaddingRight();
      float f = 8.0F * OthersFragment.this.getResources().getDisplayMetrics().density;
      if (paramMoreItem.getType() == OthersFragment.MoreItem.TYPE_SINGLELINE)
      {
        this.layoutItem.setBackgroundResource(2130837701);
        this.layout.setPadding(i, (int)f, j, (int)f);
        return;
      }
      if (paramMoreItem.getType() == OthersFragment.MoreItem.TYPE_GROUP_TOP)
      {
        this.layoutItem.setBackgroundResource(2130837702);
        this.layout.setPadding(i, (int)f, j, 0);
        return;
      }
      if (paramMoreItem.getType() == OthersFragment.MoreItem.TYPE_GROUP_BOTTOM)
      {
        this.layoutItem.setBackgroundResource(2130837699);
        this.layout.setPadding(i, 0, j, (int)f);
        return;
      }
      this.layoutItem.setBackgroundResource(2130837700);
      this.layout.setPadding(i, 0, j, 0);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.OthersFragment
 * JD-Core Version:    0.6.0
 */