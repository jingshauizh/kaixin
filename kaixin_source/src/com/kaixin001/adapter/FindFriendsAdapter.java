package com.kaixin001.adapter;

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
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import java.util.ArrayList;
import java.util.HashMap;

public class FindFriendsAdapter extends ArrayAdapter<StrangerModel.Stranger>
{
  public static int ADDFANS_MODE = 0;
  public static final int ADD_FANS_TYPE = 1;
  public static final int ADD_FRIEND_TYPE = 0;
  public static final int ALL_TYPE = 4;
  public static final int FIND_FRIEND_TYPE = 2;
  public static int FROM_ADDFRIEND = 0;
  public static int STRANGER_MODE = 0;
  public static int VISITOR_MODE = 0;
  public static final int VISIT_TYPE = 3;
  private boolean changeMode = true;
  ArrayList<StrangerModel.Stranger> friendList;
  private int from;
  private View mBlankView = null;
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

  public FindFriendsAdapter(BaseFragment paramBaseFragment, int paramInt1, ArrayList<StrangerModel.Stranger> paramArrayList, HashMap<String, AddFriendResult> paramHashMap, int paramInt2, int paramInt3)
  {
    super(paramBaseFragment.getActivity(), paramInt1, paramArrayList);
    this.friendList = paramArrayList;
    this.mFragment = paramBaseFragment;
    this.otherChanges = paramHashMap;
    this.mode = paramInt2;
    this.from = paramInt3;
    if ((this.mode == STRANGER_MODE) || (this.mode == ADDFANS_MODE));
    for (this.mFooter = this.mFragment.getLayoutInflater(null).inflate(2130903123, null); ; this.mFooter = this.mFragment.getLayoutInflater(null).inflate(2130903411, null))
    {
      this.mRemindImage = ((ImageView)this.mFooter.findViewById(2131362345));
      this.mFooterProBar = ((ProgressBar)this.mFooter.findViewById(2131362072));
      this.mFooter.setOnClickListener(new View.OnClickListener(paramBaseFragment)
      {
        public void onClick(View paramView)
        {
          if (FindFriendsAdapter.this.mode == FindFriendsAdapter.STRANGER_MODE)
            FindFriendsAdapter.this.mRemindImage.setVisibility(8);
          ((OnViewMoreClickListener)this.val$fragment).onViewMoreClick();
        }
      });
      this.mFooter.findViewById(2131362071).setDuplicateParentStateEnabled(true);
      this.mFooterTV = ((TextView)this.mFooter.findViewById(2131362073));
      if ((this.mode != STRANGER_MODE) || (!this.changeMode))
        break;
      this.mFooterTV.setText(2131427749);
      this.mRemindImage.setVisibility(0);
      return;
    }
    this.mFooterTV.setText(2131427748);
  }

  private View getEmptyTextView()
  {
    LayoutInflater localLayoutInflater;
    if (this.mBlankView == null)
    {
      localLayoutInflater = (LayoutInflater)this.mFragment.getActivity().getSystemService("layout_inflater");
      if (this.mode != STRANGER_MODE)
        break label49;
    }
    label49: for (this.mBlankView = localLayoutInflater.inflate(2130903117, null); ; this.mBlankView = localLayoutInflater.inflate(2130903409, null))
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

  public int getItemViewType(int paramInt)
  {
    int i = 1;
    int j;
    if (((StrangerModel.Stranger)this.friendList.get(paramInt)).isStar == i)
    {
      j = i;
      if (this.mode != STRANGER_MODE)
        break label71;
      if ((this.from != FROM_ADDFRIEND) || (j != 0))
        break label55;
      i = 0;
    }
    label55: label71: 
    do
    {
      do
      {
        return i;
        j = 0;
        break;
      }
      while ((this.from == FROM_ADDFRIEND) && (j != 0));
      return 2;
    }
    while (this.mode == ADDFANS_MODE);
    return 3;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramInt >= this.friendList.size())
      return paramView;
    try
    {
      StrangerModel.Stranger localStranger = (StrangerModel.Stranger)this.friendList.get(paramInt);
      String str = localStranger.fuid;
      if (localStranger.isStar == 1)
      {
        i = 1;
        if (TextUtils.isEmpty(str))
          return this.mFooter;
        if ((str != null) && (str.equals("-1")))
          return getEmptyTextView();
        LayoutInflater localLayoutInflater;
        if ((paramView == null) || (paramView == this.mFooter) || (paramView == this.mBlankView))
        {
          localLayoutInflater = (LayoutInflater)this.mFragment.getActivity().getSystemService("layout_inflater");
          if (this.mode == STRANGER_MODE)
            if ((this.from == FROM_ADDFRIEND) && (i == 0))
            {
              paramView = localLayoutInflater.inflate(2130903052, null);
              localFriendItemViewTag = new FriendItemViewTag(paramView, null);
              paramView.setTag(localFriendItemViewTag);
              label167: localFriendItemViewTag.updateFriend(localStranger);
              if ((this.mode != STRANGER_MODE) || (this.from == FROM_ADDFRIEND))
                break label384;
              if (paramInt != -1 + this.total)
                break label351;
              localFriendItemViewTag.rlytFriend.setBackgroundResource(2130837699);
            }
        }
        while (true)
        {
          ViewGroup.LayoutParams localLayoutParams = localFriendItemViewTag.rlytFriend.getLayoutParams();
          if (!(localLayoutParams instanceof ViewGroup.MarginLayoutParams))
            break label404;
          ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)localLayoutParams;
          if (paramInt != -1 + this.total)
            break label378;
          j = 10;
          localMarginLayoutParams.setMargins(0, 0, 0, j);
          break label404;
          if ((this.from == FROM_ADDFRIEND) && (i != 0))
          {
            paramView = localLayoutInflater.inflate(2130903049, null);
            break;
          }
          paramView = localLayoutInflater.inflate(2130903119, null);
          break;
          if (this.mode == ADDFANS_MODE)
          {
            paramView = localLayoutInflater.inflate(2130903049, null);
            break;
          }
          paramView = localLayoutInflater.inflate(2130903410, null);
          break;
          localFriendItemViewTag = (FriendItemViewTag)paramView.getTag();
          break label167;
          label351: localFriendItemViewTag.rlytFriend.setBackgroundResource(2130837700);
        }
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        FriendItemViewTag localFriendItemViewTag;
        KXLog.e("AddMaybeFriendsActivity", "getView", localException);
        break;
        label378: int j = 0;
        continue;
        label384: localFriendItemViewTag.rlytFriend.setBackgroundResource(2130838426);
        break;
        int i = 0;
      }
    }
    label404: return paramView;
  }

  public int getViewTypeCount()
  {
    return 4;
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
        break label113;
    }
    while (true)
    {
      localImageView.setVisibility(i);
      if (this.mFooterTV != null)
      {
        int j = this.mFragment.getResources().getColor(2130839419);
        if (paramBoolean)
          j = this.mFragment.getResources().getColor(2130839395);
        this.mFooterTV.setTextColor(j);
      }
      return;
      k = i;
      break;
      label113: i = 0;
    }
  }

  public void showLoginDialog()
  {
    if ((this.mFragment != null) && ((this.mFragment instanceof FindFriendFragment)))
    {
      ((FindFriendFragment)this.mFragment).showHideLoginLayoutAnimation(0);
      return;
    }
    FragmentActivity localFragmentActivity = this.mFragment.getActivity();
    String[] arrayOfString = new String[2];
    arrayOfString[0] = this.mFragment.getString(2131427338);
    arrayOfString[1] = this.mFragment.getString(2131427339);
    DialogUtil.showSelectListDlg(localFragmentActivity, 2131427729, arrayOfString, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        switch (paramInt)
        {
        default:
          return;
        case 0:
          Intent localIntent2 = new Intent(FindFriendsAdapter.this.mFragment.getActivity(), LoginActivity.class);
          FindFriendsAdapter.this.mFragment.getActivity().startActivity(localIntent2);
          FindFriendsAdapter.this.mFragment.getActivity().finish();
          return;
        case 1:
        }
        Intent localIntent1 = new Intent(FindFriendsAdapter.this.mFragment.getActivity(), LoginActivity.class);
        Bundle localBundle = new Bundle();
        localBundle.putInt("regist", 1);
        localIntent1.putExtras(localBundle);
        FindFriendsAdapter.this.mFragment.getActivity().startActivity(localIntent1);
        FindFriendsAdapter.this.mFragment.getActivity().finish();
      }
    }
    , 1);
  }

  private class FriendItemViewTag
    implements View.OnClickListener
  {
    private ImageView btnAddFriend;
    private String fname;
    private String fuid;
    private boolean isStar;
    private ImageView ivLogo;
    private ImageView ivStar;
    private TextView lblSameFriend = null;
    RelativeLayout rlytFriend;
    private TextView tvCity = null;
    private TextView tvCompany = null;
    private TextView tvName = null;
    private TextView tvSameFriend = null;
    private TextView tvSchool = null;

    private FriendItemViewTag(View arg2)
    {
      Object localObject;
      localObject.setOnClickListener(this);
      this.rlytFriend = ((RelativeLayout)localObject.findViewById(2131361828));
      this.ivLogo = ((ImageView)localObject.findViewById(2131361829));
      this.tvName = ((TextView)localObject.findViewById(2131361834));
      this.ivStar = ((ImageView)localObject.findViewById(2131361833));
      this.tvCity = ((TextView)localObject.findViewById(2131361836));
      this.tvCompany = ((TextView)localObject.findViewById(2131361837));
      this.tvSchool = ((TextView)localObject.findViewById(2131361845));
      this.lblSameFriend = ((TextView)localObject.findViewById(2131361838));
      this.tvSameFriend = ((TextView)localObject.findViewById(2131361839));
      this.btnAddFriend = ((ImageView)localObject.findViewById(2131361830));
      this.btnAddFriend.setOnClickListener(this);
    }

    public void onClick(View paramView)
    {
      if (TextUtils.isEmpty(User.getInstance().getOauthToken()))
      {
        FindFriendsAdapter.this.showLoginDialog();
        return;
      }
      if (paramView.getId() == 2131361830)
      {
        if (User.getInstance().GetLookAround())
        {
          FindFriendsAdapter.this.showLoginDialog();
          return;
        }
        AddFriendResult localAddFriendResult = new AddFriendResult(this.fuid, 0, "");
        localAddFriendResult.inProgress = true;
        FindFriendsAdapter.this.otherChanges.put(this.fuid, localAddFriendResult);
        ImageView localImageView = (ImageView)paramView;
        if (FindFriendsAdapter.this.from == FindFriendsAdapter.FROM_ADDFRIEND)
          localImageView.setImageResource(2130838214);
        while (this.isStar)
        {
          FindFriendsAdapter.this.getAddFriendUtil().addNewFriend(null, this.fuid);
          return;
          localImageView.setImageDrawable(FindFriendsAdapter.this.mFragment.getResources().getDrawable(2130838837));
        }
        FindFriendsAdapter.this.getAddFriendUtil().addNewFriend("3", this.fuid);
        return;
      }
      if (paramView.getId() == 2131361834)
      {
        if (User.getInstance().GetLookAround())
        {
          FindFriendsAdapter.this.showLoginDialog();
          return;
        }
        IntentUtil.showHomeFragment(FindFriendsAdapter.this.mFragment, this.fuid, this.fname);
        return;
      }
      if (User.getInstance().GetLookAround())
      {
        FindFriendsAdapter.this.showLoginDialog();
        return;
      }
      IntentUtil.showHomeFragment(FindFriendsAdapter.this.mFragment, this.fuid, this.fname);
    }

    public void updateFriend(StrangerModel.Stranger paramStranger)
      throws Exception
    {
      String str1 = null;
      this.btnAddFriend.setOnClickListener(this);
      boolean bool;
      label100: int i;
      if ((this.ivLogo instanceof KXFrameImageView))
      {
        ((KXFrameImageView)this.ivLogo).setFrameNinePatchResId(2130838099);
        FindFriendsAdapter.this.mFragment.displayRoundPicture(this.ivLogo, paramStranger.flogo, ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
        this.fuid = paramStranger.fuid;
        this.fname = paramStranger.fname;
        this.tvName.setText(paramStranger.fname);
        this.tvName.setOnClickListener(this);
        if (paramStranger.isStar != 1)
          break label424;
        bool = true;
        this.isStar = bool;
        ImageView localImageView = this.ivStar;
        if (!this.isStar)
          break label429;
        i = 0;
        label121: localImageView.setVisibility(i);
        if ((!this.isStar) && (FindFriendsAdapter.this.mode != FindFriendsAdapter.ADDFANS_MODE))
          break label436;
        ((KXFrameImageView)this.ivLogo).setFrameNinePatchResId(2130838099);
        FindFriendsAdapter.this.mFragment.displayRoundPicture(this.ivLogo, paramStranger.flogo, ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
        this.tvCity.setVisibility(0);
        this.tvCity.setText(2131427970);
        label200: if ((!this.isStar) && (FindFriendsAdapter.this.mode != FindFriendsAdapter.ADDFANS_MODE))
          break label480;
        this.tvCompany.setVisibility(0);
        this.tvCompany.setText(paramStranger.fans);
        label239: if ((FindFriendsAdapter.this.mode == FindFriendsAdapter.ADDFANS_MODE) && (TextUtils.isEmpty(paramStranger.school)) && (!this.isStar))
          this.tvSchool.setText(paramStranger.school);
        if ((!this.isStar) && (FindFriendsAdapter.this.mode != FindFriendsAdapter.ADDFANS_MODE))
          break label524;
        this.tvSameFriend.setVisibility(0);
        this.lblSameFriend.setVisibility(0);
        this.lblSameFriend.setText(2131427971);
        this.tvSameFriend.setText(paramStranger.state);
      }
      AddFriendResult localAddFriendResult;
      while (true)
      {
        localAddFriendResult = (AddFriendResult)FindFriendsAdapter.this.otherChanges.get(this.fuid);
        if (localAddFriendResult != null)
          break label1004;
        if (paramStranger.isMyFriend != 1)
          break label940;
        if (FindFriendsAdapter.this.from != FindFriendsAdapter.FROM_ADDFRIEND)
          break label931;
        this.btnAddFriend.setImageResource(2130838151);
        this.btnAddFriend.setOnClickListener(null);
        return;
        FindFriendsAdapter.this.mFragment.displayPicture(this.ivLogo, paramStranger.flogo, 2130838676);
        break;
        label424: bool = false;
        break label100;
        label429: i = 8;
        break label121;
        label436: if (TextUtils.isEmpty(paramStranger.city))
        {
          this.tvCity.setVisibility(8);
          break label200;
        }
        this.tvCity.setVisibility(0);
        this.tvCity.setText(paramStranger.city);
        break label200;
        label480: if (TextUtils.isEmpty(paramStranger.company))
        {
          this.tvCompany.setVisibility(8);
          break label239;
        }
        this.tvCompany.setVisibility(0);
        this.tvCompany.setText(paramStranger.company);
        break label239;
        label524: if ((!this.isStar) || (FindFriendsAdapter.this.from == FindFriendsAdapter.FROM_ADDFRIEND))
        {
          this.lblSameFriend.setVisibility(0);
          this.tvSameFriend.setVisibility(4);
          String str2 = paramStranger.sameFriends;
          if (str2.substring(-1 + str2.length(), str2.length()).equals("人"))
            str1 = str2.substring(1 + str2.lastIndexOf("等"), -1 + str2.length());
          while (true)
          {
            if ((str1 != null) || (str2 != null))
              break label693;
            this.lblSameFriend.setText("0个共同好友");
            break;
            int j = 0;
            for (int k = 0; k < str2.length(); k++)
            {
              if (str2.charAt(k) != '、')
                continue;
              j++;
              str1 = j + 1;
            }
          }
          label693: if ((str1 == null) && (str2 != null))
          {
            this.lblSameFriend.setText("1个共同好友");
            continue;
          }
          this.lblSameFriend.setText(str1 + "个共同好友");
          continue;
        }
        if (paramStranger.isMobileFriend == 1)
        {
          this.lblSameFriend.setVisibility(0);
          this.tvSameFriend.setVisibility(8);
          Resources localResources = FindFriendsAdapter.this.mFragment.getResources();
          if (paramStranger.gender == 0);
          for (int m = 2131427384; ; m = 2131427385)
          {
            String str3 = localResources.getString(m);
            this.lblSameFriend.setText(String.format(FindFriendsAdapter.this.mFragment.getResources().getString(2131428046), new Object[] { str3 }));
            break;
          }
        }
        if (TextUtils.isEmpty(paramStranger.sameFriends))
        {
          this.lblSameFriend.setVisibility(4);
          this.tvSameFriend.setVisibility(4);
          continue;
        }
        this.lblSameFriend.setVisibility(0);
        this.tvSameFriend.setVisibility(0);
        this.lblSameFriend.setText(FindFriendsAdapter.this.mFragment.getResources().getString(2131428045));
        this.tvSameFriend.setText(paramStranger.sameFriends);
      }
      label931: this.btnAddFriend.setVisibility(4);
      return;
      label940: if (FindFriendsAdapter.this.from == FindFriendsAdapter.FROM_ADDFRIEND)
      {
        this.btnAddFriend.setImageResource(2130837519);
        return;
      }
      this.btnAddFriend.setVisibility(0);
      this.btnAddFriend.setImageDrawable(FindFriendsAdapter.this.mFragment.getResources().getDrawable(2130837529));
      this.btnAddFriend.setEnabled(true);
      return;
      label1004: if (localAddFriendResult.code == 1)
      {
        if (FindFriendsAdapter.this.from == FindFriendsAdapter.FROM_ADDFRIEND)
        {
          this.btnAddFriend.setImageResource(2130838118);
          this.btnAddFriend.setOnClickListener(null);
          return;
        }
        this.btnAddFriend.setVisibility(4);
        return;
      }
      if (localAddFriendResult.inProgress)
      {
        if (FindFriendsAdapter.this.from == FindFriendsAdapter.FROM_ADDFRIEND)
        {
          this.btnAddFriend.setVisibility(0);
          this.btnAddFriend.setImageResource(2130838214);
          return;
        }
        this.btnAddFriend.setVisibility(0);
        this.btnAddFriend.setImageDrawable(FindFriendsAdapter.this.mFragment.getResources().getDrawable(2130838837));
        return;
      }
      if (FindFriendsAdapter.this.from == FindFriendsAdapter.FROM_ADDFRIEND)
      {
        this.btnAddFriend.setImageResource(2130837519);
        return;
      }
      this.btnAddFriend.setVisibility(0);
      this.btnAddFriend.setImageDrawable(FindFriendsAdapter.this.mFragment.getResources().getDrawable(2130837529));
      this.btnAddFriend.setEnabled(true);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.adapter.FindFriendsAdapter
 * JD-Core Version:    0.6.0
 */