package com.kaixin001.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.OnViewMoreClickListener;
import com.kaixin001.businesslogic.AddFriend;
import com.kaixin001.businesslogic.AddFriendResult;
import com.kaixin001.businesslogic.IUseAddFriendBussinessLogic;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.item.KaixinCircleMember;
import com.kaixin001.model.User;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import java.util.HashMap;

public class CircleMembersAdapter extends ArrayAdapter<KaixinCircleMember>
{
  private static final String TAG = "CircleMembersAdapter";
  private Activity mContext;
  private View mFooter;
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  private BaseFragment mFragment;
  private View mSumMemberView = null;
  ArrayList<KaixinCircleMember> memberList;
  HashMap<String, AddFriendResult> otherChanges;
  private int total;
  private TextView tvMemberNum;

  public CircleMembersAdapter(BaseFragment paramBaseFragment, int paramInt, ArrayList<KaixinCircleMember> paramArrayList, HashMap<String, AddFriendResult> paramHashMap)
  {
    super(paramBaseFragment.getActivity(), paramInt, paramArrayList);
    this.memberList = paramArrayList;
    this.mFragment = paramBaseFragment;
    this.mContext = paramBaseFragment.getActivity();
    this.otherChanges = paramHashMap;
    this.mFooter = paramBaseFragment.getActivity().getLayoutInflater().inflate(2130903259, null);
    this.mFooter.setOnClickListener(new View.OnClickListener(paramBaseFragment)
    {
      public void onClick(View paramView)
      {
        ((OnViewMoreClickListener)this.val$fragment).onViewMoreClick();
      }
    });
    this.mFooterTV = ((TextView)this.mFooter.findViewById(2131362073));
    this.mFooterTV.setText(2131427748);
    this.mFooterProBar = ((ProgressBar)this.mFooter.findViewById(2131362072));
  }

  private View getEmptyTextView()
  {
    if (this.mSumMemberView == null)
    {
      this.mSumMemberView = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903077, null);
      this.tvMemberNum = ((TextView)this.mSumMemberView.findViewById(2131362060));
    }
    return this.mSumMemberView;
  }

  public AddFriend getAddFriendUtil()
  {
    return ((IUseAddFriendBussinessLogic)this.mFragment).getAddFriendUtil();
  }

  public int getCount()
  {
    if (this.memberList == null)
      return 0;
    return this.memberList.size();
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramInt >= this.memberList.size())
      return paramView;
    KaixinCircleMember localKaixinCircleMember;
    try
    {
      localKaixinCircleMember = (KaixinCircleMember)this.memberList.get(paramInt);
      if (TextUtils.isEmpty(localKaixinCircleMember.uid))
        return this.mFooter;
      if ((localKaixinCircleMember.uid == null) || (!localKaixinCircleMember.uid.equals("-1")))
        break label131;
      View localView = getEmptyTextView();
      TextView localTextView = this.tvMemberNum;
      String str = this.mContext.getString(2131428083);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(this.total);
      localTextView.setText(String.format(str, arrayOfObject));
      return localView;
    }
    catch (Exception localException)
    {
      KXLog.e("CircleMembersAdapter", "getView", localException);
    }
    return paramView;
    label131: CircleMemberItemViewTag localCircleMemberItemViewTag;
    if ((paramView == null) || (paramView == this.mFooter) || (paramView == this.mSumMemberView))
    {
      paramView = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903076, null);
      localCircleMemberItemViewTag = new CircleMemberItemViewTag(paramView, null);
      paramView.setTag(localCircleMemberItemViewTag);
    }
    while (true)
    {
      localCircleMemberItemViewTag.updateMemberItem(localKaixinCircleMember);
      break;
      localCircleMemberItemViewTag = (CircleMemberItemViewTag)paramView.getTag();
    }
  }

  public boolean isFooterShowLoading()
  {
    if (this.mFooterProBar == null);
    do
      return false;
    while (this.mFooterProBar.getVisibility() != 0);
    return true;
  }

  public void setTotalItem(int paramInt)
  {
    this.total = paramInt;
  }

  public void showLoadingFooter(boolean paramBoolean)
  {
    ProgressBar localProgressBar;
    if (this.mFooterProBar != null)
    {
      localProgressBar = this.mFooterProBar;
      if (!paramBoolean)
        break label71;
    }
    label71: for (int j = 0; ; j = 4)
    {
      localProgressBar.setVisibility(j);
      if (this.mFooterTV != null)
      {
        int i = this.mContext.getResources().getColor(2130839419);
        if (paramBoolean)
          i = this.mContext.getResources().getColor(2130839395);
        this.mFooterTV.setTextColor(i);
      }
      return;
    }
  }

  private class CircleMemberItemViewTag
    implements View.OnClickListener
  {
    private ImageView btnAddFriend;
    private String fname;
    private String fuid;
    private boolean isStar;
    private ImageView ivLogo;
    private TextView tvCity = null;
    private TextView tvCompany = null;
    private TextView tvName = null;
    private TextView tvSchool = null;

    private CircleMemberItemViewTag(View arg2)
    {
      Object localObject;
      localObject.setOnClickListener(this);
      this.ivLogo = ((ImageView)localObject.findViewById(2131362058));
      this.tvName = ((TextView)localObject.findViewById(2131361834));
      this.tvCity = ((TextView)localObject.findViewById(2131361836));
      this.tvCompany = ((TextView)localObject.findViewById(2131361837));
      this.tvSchool = ((TextView)localObject.findViewById(2131361845));
      this.btnAddFriend = ((ImageView)localObject.findViewById(2131361830));
      this.btnAddFriend.setOnClickListener(this);
    }

    public void onClick(View paramView)
    {
      if (TextUtils.isEmpty(User.getInstance().getOauthToken()))
      {
        Toast.makeText(CircleMembersAdapter.this.mContext, 2131427972, 0).show();
        return;
      }
      if (paramView.getId() == 2131361830)
      {
        AddFriendResult localAddFriendResult = new AddFriendResult(this.fuid, 0, "");
        localAddFriendResult.inProgress = true;
        CircleMembersAdapter.this.otherChanges.put(this.fuid, localAddFriendResult);
        ((ImageView)paramView).setImageDrawable(CircleMembersAdapter.this.mContext.getResources().getDrawable(2130838837));
        if (this.isStar)
        {
          CircleMembersAdapter.this.getAddFriendUtil().addNewFriend(null, this.fuid);
          return;
        }
        CircleMembersAdapter.this.getAddFriendUtil().addNewFriend("3", this.fuid);
        return;
      }
      if (paramView.getId() == 2131361834)
      {
        IntentUtil.showHomeFragment(CircleMembersAdapter.this.mFragment, this.fuid, this.fname);
        return;
      }
      IntentUtil.showHomeFragment(CircleMembersAdapter.this.mFragment, this.fuid, this.fname);
    }

    public void updateMemberItem(KaixinCircleMember paramKaixinCircleMember)
      throws Exception
    {
      CircleMembersAdapter.this.mFragment.displayPicture(this.ivLogo, paramKaixinCircleMember.logo, 2130838676);
      this.fuid = paramKaixinCircleMember.uid;
      this.fname = paramKaixinCircleMember.realname;
      this.tvName.setText(paramKaixinCircleMember.realname);
      this.tvName.setTextColor(CircleMembersAdapter.this.mContext.getResources().getColor(2130839419));
      this.tvName.setPadding(0, 2, 0, 2);
      this.tvName.setOnClickListener(this);
      if ((TextUtils.isEmpty(paramKaixinCircleMember.city)) && (TextUtils.isEmpty(paramKaixinCircleMember.company)) && (TextUtils.isEmpty(paramKaixinCircleMember.school)))
      {
        this.tvCity.setVisibility(8);
        this.tvCompany.setVisibility(8);
        this.tvSchool.setVisibility(8);
      }
      AddFriendResult localAddFriendResult;
      while (true)
      {
        localAddFriendResult = (AddFriendResult)CircleMembersAdapter.this.otherChanges.get(this.fuid);
        if (localAddFriendResult != null)
          break;
        if (paramKaixinCircleMember.relation == 0)
        {
          this.btnAddFriend.setVisibility(0);
          this.btnAddFriend.setImageDrawable(CircleMembersAdapter.this.mContext.getResources().getDrawable(2130837529));
          this.btnAddFriend.setEnabled(true);
          return;
          this.tvCity.setVisibility(0);
          this.tvCompany.setVisibility(0);
          this.tvSchool.setVisibility(0);
          this.tvCity.setText(paramKaixinCircleMember.city);
          this.tvCompany.setText(paramKaixinCircleMember.company);
          this.tvSchool.setText(paramKaixinCircleMember.school);
          continue;
        }
        this.btnAddFriend.setVisibility(4);
        return;
      }
      if (localAddFriendResult.code == 1)
      {
        this.btnAddFriend.setVisibility(4);
        return;
      }
      if (localAddFriendResult.inProgress)
      {
        this.btnAddFriend.setVisibility(0);
        this.btnAddFriend.setImageDrawable(CircleMembersAdapter.this.mContext.getResources().getDrawable(2130838837));
        return;
      }
      this.btnAddFriend.setVisibility(0);
      this.btnAddFriend.setImageDrawable(CircleMembersAdapter.this.mContext.getResources().getDrawable(2130837529));
      this.btnAddFriend.setEnabled(true);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.adapter.CircleMembersAdapter
 * JD-Core Version:    0.6.0
 */