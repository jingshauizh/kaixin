package com.kaixin001.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
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
import com.kaixin001.activity.LoginActivity;
import com.kaixin001.activity.OnViewMoreClickListener;
import com.kaixin001.businesslogic.AddFriend;
import com.kaixin001.businesslogic.AddFriendResult;
import com.kaixin001.businesslogic.IUseAddFriendBussinessLogic;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.fragment.FindFriendFragment;
import com.kaixin001.model.StrangerModel.Stranger;
import com.kaixin001.model.User;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.view.KXFrameImageView;
import com.kaixin001.view.KaiXinProgressBar;
import java.util.ArrayList;
import java.util.HashMap;

public class AddFriendsAndFansAdapter extends ArrayAdapter<StrangerModel.Stranger>
{
  public static int ADDFANS_MODE;
  public static int FROM_ADDFRIEND;
  public static int STRANGER_MODE = 0;
  public static int VISITOR_MODE = 1;
  private boolean changeMode = true;
  ArrayList<StrangerModel.Stranger> friendList;
  private int from;
  private View mBlankView = null;
  private Activity mContext;
  private View mFooter;
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  private BaseFragment mFragment;
  private ImageView mRemindImage;
  private int mode = STRANGER_MODE;
  HashMap<String, AddFriendResult> otherChanges;
  private int total;

  static
  {
    ADDFANS_MODE = 2;
    FROM_ADDFRIEND = 4;
  }

  public AddFriendsAndFansAdapter(BaseFragment paramBaseFragment, int paramInt1, ArrayList<StrangerModel.Stranger> paramArrayList, HashMap<String, AddFriendResult> paramHashMap, int paramInt2, int paramInt3)
  {
    super(paramBaseFragment.getActivity(), paramInt1, paramArrayList);
    this.friendList = paramArrayList;
    this.mFragment = paramBaseFragment;
    this.mContext = paramBaseFragment.getActivity();
    this.otherChanges = paramHashMap;
    this.mode = paramInt2;
    this.from = paramInt3;
    this.mFooter = paramBaseFragment.getActivity().getLayoutInflater().inflate(2130903123, null);
    this.mRemindImage = ((ImageView)this.mFooter.findViewById(2131362345));
    this.mFooterProBar = ((ProgressBar)this.mFooter.findViewById(2131362072));
    this.mFooter.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (AddFriendsAndFansAdapter.this.mode == AddFriendsAndFansAdapter.STRANGER_MODE)
          AddFriendsAndFansAdapter.this.mRemindImage.setVisibility(8);
        ((OnViewMoreClickListener)AddFriendsAndFansAdapter.this.mContext).onViewMoreClick();
      }
    });
    this.mFooter.findViewById(2131362071).setDuplicateParentStateEnabled(true);
    this.mFooterTV = ((TextView)this.mFooter.findViewById(2131362073));
    this.mFooterTV.setText(2131427748);
  }

  private View getEmptyTextView()
  {
    if (this.mBlankView == null)
      this.mBlankView = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903117, null);
    return this.mBlankView;
  }

  public AddFriend getAddFriendUtil()
  {
    return ((IUseAddFriendBussinessLogic)this.mFragment).getAddFriendUtil();
  }

  public int getCount()
  {
    if (this.friendList == null)
      return 0;
    return this.friendList.size();
  }

  public boolean getFindFriendMode()
  {
    return this.changeMode;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramInt >= this.friendList.size())
      return paramView;
    try
    {
      StrangerModel.Stranger localStranger = (StrangerModel.Stranger)this.friendList.get(paramInt);
      String str = localStranger.fuid;
      if (TextUtils.isEmpty(str))
        return this.mFooter;
      if ((str != null) && (str.equals("-1")))
        return getEmptyTextView();
      AddFriendItemViewTag localAddFriendItemViewTag;
      if ((paramView == null) || (paramView == this.mFooter) || (paramView == this.mBlankView))
      {
        paramView = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903051, null);
        localAddFriendItemViewTag = new AddFriendItemViewTag(paramView, null);
        paramView.setTag(localAddFriendItemViewTag);
      }
      while (true)
      {
        localAddFriendItemViewTag.updateFriend(localStranger);
        break;
        localAddFriendItemViewTag = (AddFriendItemViewTag)paramView.getTag();
      }
    }
    catch (Exception localException)
    {
      KXLog.e("AddMaybeFriendsActivity", "getView", localException);
    }
    return paramView;
  }

  public boolean isFooterShowLoading()
  {
    if (this.mFooterProBar == null);
    do
      return false;
    while (this.mFooterProBar.getVisibility() != 0);
    return true;
  }

  public void setFindFriendMode(boolean paramBoolean)
  {
    this.changeMode = paramBoolean;
    if (this.changeMode)
    {
      this.mFooterTV.setText(2131427749);
      this.mRemindImage.setVisibility(0);
      return;
    }
    this.mFooterTV.setText(2131427748);
    this.mRemindImage.setVisibility(8);
  }

  public void setTotalItem(int paramInt)
  {
    this.total = paramInt;
  }

  public void showLoadingFooter(boolean paramBoolean)
  {
    int i = 4;
    int k;
    ImageView localImageView;
    if (this.mFooterProBar != null)
    {
      ProgressBar localProgressBar = this.mFooterProBar;
      if (paramBoolean)
      {
        k = 0;
        localProgressBar.setVisibility(k);
      }
    }
    else if ((this.mRemindImage != null) && (this.changeMode))
    {
      localImageView = this.mRemindImage;
      if (!paramBoolean)
        break label111;
    }
    while (true)
    {
      localImageView.setVisibility(i);
      if (this.mFooterTV != null)
      {
        int j = this.mContext.getResources().getColor(2130839419);
        if (paramBoolean)
          j = this.mContext.getResources().getColor(2130839395);
        this.mFooterTV.setTextColor(j);
      }
      return;
      k = i;
      break;
      label111: i = 0;
    }
  }

  public void showLoginDialog()
  {
    if ((this.mFragment != null) && ((this.mFragment instanceof FindFriendFragment)))
    {
      ((FindFriendFragment)this.mFragment).showHideLoginLayoutAnimation(0);
      return;
    }
    Activity localActivity = this.mContext;
    String[] arrayOfString = new String[2];
    arrayOfString[0] = this.mContext.getString(2131427338);
    arrayOfString[1] = this.mContext.getString(2131427339);
    DialogUtil.showSelectListDlg(localActivity, 2131427729, arrayOfString, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        switch (paramInt)
        {
        default:
          return;
        case 0:
          Intent localIntent2 = new Intent(AddFriendsAndFansAdapter.this.mContext, LoginActivity.class);
          AddFriendsAndFansAdapter.this.mFragment.getActivity().startActivity(localIntent2);
          AddFriendsAndFansAdapter.this.mFragment.getActivity().finish();
          return;
        case 1:
        }
        Intent localIntent1 = new Intent(AddFriendsAndFansAdapter.this.mContext, LoginActivity.class);
        Bundle localBundle = new Bundle();
        localBundle.putInt("regist", 1);
        localIntent1.putExtras(localBundle);
        AddFriendsAndFansAdapter.this.mFragment.getActivity().startActivity(localIntent1);
        AddFriendsAndFansAdapter.this.mFragment.getActivity().finish();
      }
    }
    , 1);
  }

  private class AddFriendItemViewTag
    implements View.OnClickListener
  {
    private ImageView btnAddFriend;
    private String fname;
    private String fuid;
    private boolean isStar;
    private KXFrameImageView ivLogo;
    private ImageView ivStar;
    private TextView lblSameFriend = null;
    private KaiXinProgressBar progress = null;
    private TextView tvCity = null;
    private TextView tvCompany = null;
    private TextView tvName = null;
    private TextView tvSameFriend = null;
    private TextView tvSchool = null;

    private AddFriendItemViewTag(View arg2)
    {
      Object localObject;
      localObject.setOnClickListener(this);
      this.ivLogo = ((KXFrameImageView)localObject.findViewById(2131361829));
      this.tvName = ((TextView)localObject.findViewById(2131361834));
      this.ivStar = ((ImageView)localObject.findViewById(2131361833));
      this.tvCity = ((TextView)localObject.findViewById(2131361836));
      this.tvCompany = ((TextView)localObject.findViewById(2131361837));
      this.tvSchool = ((TextView)localObject.findViewById(2131361845));
      this.lblSameFriend = ((TextView)localObject.findViewById(2131361838));
      this.tvSameFriend = ((TextView)localObject.findViewById(2131361839));
      this.btnAddFriend = ((ImageView)localObject.findViewById(2131361830));
      this.btnAddFriend.setOnClickListener(this);
      this.progress = ((KaiXinProgressBar)localObject.findViewById(2131361844));
    }

    public void onClick(View paramView)
    {
      if (TextUtils.isEmpty(User.getInstance().getOauthToken()))
      {
        AddFriendsAndFansAdapter.this.showLoginDialog();
        return;
      }
      if (paramView.getId() == 2131361830)
      {
        if (User.getInstance().GetLookAround())
        {
          AddFriendsAndFansAdapter.this.showLoginDialog();
          return;
        }
        AddFriendResult localAddFriendResult = new AddFriendResult(this.fuid, 0, "");
        localAddFriendResult.inProgress = true;
        AddFriendsAndFansAdapter.this.otherChanges.put(this.fuid, localAddFriendResult);
        this.btnAddFriend.setVisibility(8);
        this.progress.setVisibility(0);
        if (this.isStar)
        {
          AddFriendsAndFansAdapter.this.getAddFriendUtil().addNewFriend(null, this.fuid);
          return;
        }
        AddFriendsAndFansAdapter.this.getAddFriendUtil().addNewFriend("3", this.fuid);
        return;
      }
      if (paramView.getId() == 2131361834)
      {
        if (User.getInstance().GetLookAround())
        {
          AddFriendsAndFansAdapter.this.showLoginDialog();
          return;
        }
        IntentUtil.showHomeFragment(AddFriendsAndFansAdapter.this.mFragment, this.fuid, this.fname);
        return;
      }
      if (User.getInstance().GetLookAround())
      {
        AddFriendsAndFansAdapter.this.showLoginDialog();
        return;
      }
      IntentUtil.showHomeFragment(AddFriendsAndFansAdapter.this.mFragment, this.fuid, this.fname);
    }

    public void updateFriend(StrangerModel.Stranger paramStranger)
      throws Exception
    {
      String str1 = null;
      this.btnAddFriend.setOnClickListener(this);
      this.progress.setVisibility(4);
      this.btnAddFriend.setVisibility(0);
      this.ivLogo.setFrameNinePatchResId(2130838101);
      AddFriendsAndFansAdapter.this.mFragment.displayRoundPicture(this.ivLogo, paramStranger.flogo, ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
      this.fuid = paramStranger.fuid;
      this.fname = paramStranger.fname;
      this.tvName.setText(paramStranger.fname);
      this.tvName.setOnClickListener(this);
      boolean bool;
      int i;
      if (paramStranger.isStar == 1)
      {
        bool = true;
        this.isStar = bool;
        ImageView localImageView = this.ivStar;
        if (!this.isStar)
          break label265;
        i = 0;
        label124: localImageView.setVisibility(i);
        if (!this.isStar)
          break label272;
        this.tvCity.setVisibility(0);
        this.tvCity.setText(2131427970);
        this.tvCompany.setVisibility(0);
        this.tvCompany.setText(paramStranger.fans);
        this.tvSameFriend.setVisibility(0);
        this.lblSameFriend.setVisibility(0);
        this.lblSameFriend.setText(2131427971);
        this.tvSameFriend.setText(paramStranger.state);
      }
      AddFriendResult localAddFriendResult;
      while (true)
      {
        localAddFriendResult = (AddFriendResult)AddFriendsAndFansAdapter.this.otherChanges.get(this.fuid);
        if (localAddFriendResult != null)
          break label587;
        if (paramStranger.isMyFriend != 1)
          break label576;
        this.btnAddFriend.setImageResource(2130838151);
        this.btnAddFriend.setOnClickListener(null);
        return;
        bool = false;
        break;
        label265: i = 8;
        break label124;
        label272: label291: label310: String str2;
        if (TextUtils.isEmpty(paramStranger.city))
        {
          this.tvCity.setVisibility(8);
          if (!TextUtils.isEmpty(paramStranger.company))
            break label446;
          this.tvCompany.setVisibility(8);
          if (TextUtils.isEmpty(paramStranger.school))
            this.tvSchool.setText(paramStranger.school);
          this.lblSameFriend.setVisibility(0);
          this.tvSameFriend.setVisibility(8);
          str2 = paramStranger.sameFriends;
          if (!str2.substring(-1 + str2.length(), str2.length()).equals("人"))
            break label468;
          str1 = str2.substring(1 + str2.lastIndexOf("等"), -1 + str2.length());
        }
        while (true)
        {
          if ((str1 != null) || (str2 != null))
            break label524;
          this.lblSameFriend.setText("0个共同好友");
          break;
          this.tvCity.setVisibility(0);
          this.tvCity.setText(paramStranger.city);
          break label291;
          label446: this.tvCompany.setVisibility(0);
          this.tvCompany.setText(paramStranger.company);
          break label310;
          label468: int j = 0;
          for (int k = 0; k < str2.length(); k++)
          {
            if (str2.charAt(k) != '、')
              continue;
            j++;
            str1 = j + 1;
          }
        }
        label524: if ((str1 == null) && (str2 != null))
        {
          this.lblSameFriend.setText("1个共同好友");
          continue;
        }
        this.lblSameFriend.setText(str1 + "个共同好友");
      }
      label576: this.btnAddFriend.setImageResource(2130837519);
      return;
      label587: if (localAddFriendResult.code == 1)
      {
        this.btnAddFriend.setImageResource(2130838118);
        this.btnAddFriend.setOnClickListener(null);
        return;
      }
      if (localAddFriendResult.inProgress)
      {
        this.btnAddFriend.setVisibility(8);
        this.progress.setVisibility(0);
        return;
      }
      this.btnAddFriend.setImageResource(2130837519);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.adapter.AddFriendsAndFansAdapter
 * JD-Core Version:    0.6.0
 */