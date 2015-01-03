package com.kaixin001.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.kaixin001.model.User;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.view.KXTopTab;
import com.kaixin001.view.KXTopTabHost;
import com.kaixin001.view.KXTopTabHost.OnTabChangeListener;

public class UserAboutFragment extends BaseFragment
  implements KXTopTabHost.OnTabChangeListener, View.OnClickListener
{
  public static final String KEY_TAB = "target_tab";
  public static final int TAB_MISSION = 0;
  public static final int TAB_RANKING = 2;
  public static final int TAB_USER_INFO = 1;
  private Fragment childFragment;
  private int current_tab = -1;
  private String from;
  private ImageView leftButton;
  private String mFname;
  private String mFuid;

  private void initTabHost()
  {
    KXTopTabHost localKXTopTabHost = (KXTopTabHost)findViewById(2131363865);
    KXTopTab localKXTopTab1 = new KXTopTab(getActivity());
    localKXTopTab1.setText(2131428657);
    localKXTopTabHost.addTab(localKXTopTab1);
    KXTopTab localKXTopTab2 = new KXTopTab(getActivity());
    localKXTopTab2.setText(2131428658);
    localKXTopTabHost.addTab(localKXTopTab2);
    KXTopTab localKXTopTab3 = new KXTopTab(getActivity());
    localKXTopTab3.setText(2131428659);
    localKXTopTabHost.addTab(localKXTopTab3);
    localKXTopTabHost.setOnTabChangeListener(this);
    localKXTopTabHost.setCurrentTab(this.current_tab);
    showContent(this.current_tab);
  }

  private void initTitleBar()
  {
    this.leftButton = ((ImageView)findViewById(2131362914));
    this.leftButton.setOnClickListener(this);
    findViewById(2131362919).setVisibility(8);
    findViewById(2131362928).setVisibility(8);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setVisibility(0);
    if (User.getInstance().getUID().equals(this.mFuid))
    {
      localTextView.setText(getResources().getString(2131428660) + getResources().getString(2131427565));
      return;
    }
    localTextView.setText(getResources().getString(2131428660) + this.mFname);
  }

  public void beforeTabChanged(int paramInt)
  {
  }

  public void onClick(View paramView)
  {
    if (paramView == this.leftButton)
    {
      if ((TextUtils.isEmpty(this.from)) || (!this.from.equals("KaixinMenuListFragment")))
        break label47;
      if (super.isMenuListVisibleInMain())
        super.showSubActivityInMain();
    }
    else
    {
      return;
    }
    super.showMenuListInMain();
    return;
    label47: finish();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (CrashRecoverUtil.isCrashBefore())
    {
      CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
      IntentUtil.returnToDesktop(getActivity());
    }
    do
    {
      return;
      Bundle localBundle = getArguments();
      if (localBundle == null)
        continue;
      this.from = localBundle.getString("from");
      String str1 = localBundle.getString("fuid");
      if (str1 != null)
        this.mFuid = str1;
      String str2 = localBundle.getString("fname");
      if (str2 != null)
        this.mFname = str2;
      int i = localBundle.getInt("target_tab", -1);
      if (i == -1)
        continue;
      this.current_tab = i;
    }
    while (this.current_tab != -1);
    this.current_tab = 1;
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903391, null, false);
  }

  public void onDestroy()
  {
    super.onDestroy();
  }

  public void onDestroyView()
  {
    super.onDestroyView();
  }

  public void onTabChanged(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return;
    case 0:
      showContent(0);
      return;
    case 1:
      showContent(1);
      return;
    case 2:
    }
    showContent(2);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    initTitleBar();
    initTabHost();
  }

  public void showContent(int paramInt)
  {
    switch (paramInt)
    {
    default:
      this.childFragment = new UserInfoFragment();
    case 0:
    case 1:
    case 2:
    }
    while (true)
    {
      this.childFragment.setArguments(getArguments());
      getActivity().getSupportFragmentManager().beginTransaction().replace(2131363866, this.childFragment).commit();
      return;
      this.childFragment = new UserMissionFragment();
      continue;
      UserInfoFragment localUserInfoFragment = new UserInfoFragment();
      localUserInfoFragment.setFatherFragment(this);
      this.childFragment = localUserInfoFragment;
      continue;
      this.childFragment = new UserRankingFragment(this);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.UserAboutFragment
 * JD-Core Version:    0.6.0
 */