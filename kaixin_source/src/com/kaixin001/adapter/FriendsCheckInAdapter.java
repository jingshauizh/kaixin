package com.kaixin001.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.item.CheckInItem;
import com.kaixin001.item.CheckInUser;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.model.CheckInInfoModel;
import com.kaixin001.model.KaixinUser;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.KXTextUtil;
import com.kaixin001.util.ParseNewsInfoUtil;
import com.kaixin001.view.KXIntroView;
import com.kaixin001.view.KXIntroView.OnClickLinkListener;
import java.util.ArrayList;

public class FriendsCheckInAdapter extends BaseAdapter
{
  private static final String TAG = "FriendsCheckInAdapter";
  private Activity mContext;
  private View mFooter;
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  private BaseFragment mFragment;
  ArrayList<CheckInItem> nearbyFriendCheckinList;
  ArrayList<CheckInItem> otherFriendCheckinList;

  public FriendsCheckInAdapter(BaseFragment paramBaseFragment, ArrayList<CheckInItem> paramArrayList1, ArrayList<CheckInItem> paramArrayList2)
  {
    this.mFragment = paramBaseFragment;
    this.mContext = paramBaseFragment.getActivity();
    this.nearbyFriendCheckinList = paramArrayList1;
    this.otherFriendCheckinList = paramArrayList2;
    this.mFooter = paramBaseFragment.getActivity().getLayoutInflater().inflate(2130903153, null);
    this.mFooterTV = ((TextView)this.mFooter.findViewById(2131362073));
    this.mFooterTV.setText(2131427748);
    this.mFooterProBar = ((ProgressBar)this.mFooter.findViewById(2131362072));
  }

  private int getNearbyFriendCheckInCount()
  {
    if (this.nearbyFriendCheckinList == null)
      return 0;
    return this.nearbyFriendCheckinList.size();
  }

  private int getOtherFriendCheckInCount()
  {
    if (this.otherFriendCheckinList == null)
      return 0;
    return this.otherFriendCheckinList.size();
  }

  public int getCount()
  {
    return getNearbyFriendCheckInCount() + getOtherFriendCheckInCount();
  }

  public Object getItem(int paramInt)
  {
    if (paramInt < 0);
    int i;
    do
    {
      return null;
      i = getNearbyFriendCheckInCount();
      if (paramInt < i)
        return this.nearbyFriendCheckinList.get(paramInt);
    }
    while (paramInt >= i + getOtherFriendCheckInCount());
    return this.otherFriendCheckinList.get(paramInt - i);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public int getPositionInList(int paramInt)
  {
    int i = getNearbyFriendCheckInCount();
    if (paramInt < i)
      return paramInt;
    return paramInt - i;
  }

  public int getSizeInList(int paramInt)
  {
    int i = getNearbyFriendCheckInCount();
    if (paramInt < i)
      return i;
    return getOtherFriendCheckInCount();
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramInt >= getCount())
      return paramView;
    NearbyFriendItemViewTag localNearbyFriendItemViewTag;
    int i;
    int j;
    try
    {
      CheckInItem localCheckInItem = (CheckInItem)getItem(paramInt);
      if (localCheckInItem.checkInUser == null)
        return this.mFooter;
      if ((paramView == null) || (paramView == this.mFooter))
      {
        paramView = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903298, null);
        localNearbyFriendItemViewTag = new NearbyFriendItemViewTag(paramView, null);
        paramView.setTag(localNearbyFriendItemViewTag);
      }
      while (true)
      {
        localNearbyFriendItemViewTag.updateCheckinItem(localCheckInItem, paramInt);
        i = getSizeInList(paramInt);
        j = getPositionInList(paramInt);
        if (i != 1)
          break;
        localNearbyFriendItemViewTag.showSingleBkg();
        break label179;
        localNearbyFriendItemViewTag = (NearbyFriendItemViewTag)paramView.getTag();
      }
      if (j == 0)
        localNearbyFriendItemViewTag.showTopBkg();
    }
    catch (Exception localException)
    {
      KXLog.e("FriendsCheckInAdapter", "getView", localException);
    }
    if (j == i - 1)
      localNearbyFriendItemViewTag.showBottomBkg();
    else
      localNearbyFriendItemViewTag.showMiddleBkg();
    label179: return paramView;
  }

  public boolean isFooterShowLoading()
  {
    if (this.mFooterProBar == null);
    do
      return false;
    while (this.mFooterProBar.getVisibility() != 0);
    return true;
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

  private class NearbyFriendItemViewTag
    implements View.OnClickListener
  {
    private CheckInItem checkinItem;
    private ImageView ivLogo;
    private ImageView ivPhoto = null;
    private View mLayoutInfo;
    private View mView;
    private TextView tvCheckinDesc = null;
    private TextView tvCheckinTime = null;
    private TextView tvDistance = null;
    private TextView tvFriendDesc = null;
    private TextView tvName = null;
    private KXIntroView tvPosition = null;

    private NearbyFriendItemViewTag(View arg2)
    {
      1 local1 = new KXIntroView.OnClickLinkListener()
      {
        public void onClick(KXLinkInfo paramKXLinkInfo)
        {
          if ((paramKXLinkInfo.isUserName()) || (paramKXLinkInfo.isStar()))
          {
            String str1 = paramKXLinkInfo.getId();
            String str2 = paramKXLinkInfo.getContent();
            IntentUtil.showHomeFragment(FriendsCheckInAdapter.this.mFragment, str1, str2);
          }
          do
            return;
          while (!paramKXLinkInfo.isLbsPoi());
          IntentUtil.showLbsPositionDetailFragment(FriendsCheckInAdapter.this.mFragment, FriendsCheckInAdapter.NearbyFriendItemViewTag.this.checkinItem.poiId, FriendsCheckInAdapter.NearbyFriendItemViewTag.this.checkinItem.poiName, "", FriendsCheckInAdapter.NearbyFriendItemViewTag.this.checkinItem.distance);
        }
      };
      Object localObject;
      this.mView = localObject;
      this.mLayoutInfo = localObject.findViewById(2131363461);
      this.tvCheckinDesc = ((TextView)localObject.findViewById(2131363460));
      this.ivLogo = ((ImageView)localObject.findViewById(2131362058));
      this.tvName = ((TextView)localObject.findViewById(2131361834));
      this.ivLogo.setOnClickListener(this);
      this.tvName.setOnClickListener(this);
      this.tvFriendDesc = ((TextView)localObject.findViewById(2131363463));
      this.tvPosition = ((KXIntroView)localObject.findViewById(2131363465));
      this.ivPhoto = ((ImageView)localObject.findViewById(2131363466));
      this.tvPosition.setOnClickLinkListener(local1);
      this.tvCheckinTime = ((TextView)localObject.findViewById(2131363467));
      this.tvDistance = ((TextView)localObject.findViewById(2131363458));
    }

    private void setPaddingBottom(int paramInt)
    {
      int i = this.mView.getPaddingTop();
      int j = this.mView.getPaddingLeft();
      int k = this.mView.getPaddingRight();
      float f = paramInt * FriendsCheckInAdapter.this.mContext.getResources().getDisplayMetrics().density;
      this.mView.setPadding(j, i, k, (int)f);
    }

    public void onClick(View paramView)
    {
      if ((paramView.getId() == 2131361834) || (paramView.getId() == 2131362058))
        IntentUtil.showHomeFragment(FriendsCheckInAdapter.this.mFragment, this.checkinItem.checkInUser.user.uid, this.checkinItem.checkInUser.user.realname);
      do
        return;
      while ((paramView.getId() != 2131363465) && (paramView.getId() != 2131363466));
      IntentUtil.showLbsPositionDetailFragment(FriendsCheckInAdapter.this.mFragment, this.checkinItem.poiId, this.checkinItem.poiName, "", this.checkinItem.distance);
    }

    public void showBottomBkg()
    {
      this.mLayoutInfo.setBackgroundResource(2130837699);
      setPaddingBottom(10);
    }

    public void showMiddleBkg()
    {
      this.mLayoutInfo.setBackgroundResource(2130837700);
      setPaddingBottom(0);
    }

    public void showSingleBkg()
    {
      this.mLayoutInfo.setBackgroundResource(2130837701);
      setPaddingBottom(10);
    }

    public void showTopBkg()
    {
      this.mLayoutInfo.setBackgroundResource(2130837702);
      setPaddingBottom(0);
    }

    public void updateCheckinItem(CheckInItem paramCheckInItem, int paramInt)
      throws Exception
    {
      this.checkinItem = paramCheckInItem;
      int i = FriendsCheckInAdapter.this.getNearbyFriendCheckInCount();
      int j = FriendsCheckInAdapter.this.getOtherFriendCheckInCount();
      if ((paramInt < i) && (paramInt == 0))
      {
        this.tvCheckinDesc.setText(2131428141);
        this.tvCheckinDesc.setVisibility(0);
      }
      while (true)
      {
        this.checkinItem = paramCheckInItem;
        KaixinUser localKaixinUser = paramCheckInItem.checkInUser.user;
        if (localKaixinUser == null)
        {
          this.ivLogo.setImageBitmap(null);
          this.tvName.setText("");
          this.ivLogo.setImageResource(2130838676);
          label93: if ((paramCheckInItem.checkInUser.mutualFriend != null) && (!TextUtils.isEmpty(paramCheckInItem.checkInUser.mutualFriend.realname)))
            break label339;
          this.tvFriendDesc.setVisibility(4);
          label127: this.tvPosition.setTitleList(ParseNewsInfoUtil.parseNewsLinkString(KXTextUtil.getLbsPoiText(paramCheckInItem.poiName, paramCheckInItem.poiId)));
          if (!TextUtils.isEmpty(paramCheckInItem.thumbnail))
            break label403;
          this.ivPhoto.setVisibility(8);
        }
        try
        {
          while (true)
          {
            long l1 = 1000L * paramCheckInItem.ctime;
            long l2 = 1000L * CheckInInfoModel.getInstance().currentTimestamp;
            this.tvCheckinTime.setText(KXTextUtil.getNewsFormatTime(l1, l2));
            if (!TextUtils.isEmpty(paramCheckInItem.distance))
              break label428;
            this.tvDistance.setText("");
            return;
            if ((paramInt < i + j) && (paramInt == i))
            {
              this.tvCheckinDesc.setText(2131428142);
              this.tvCheckinDesc.setVisibility(0);
              break;
            }
            this.tvCheckinDesc.setVisibility(8);
            break;
            FriendsCheckInAdapter.this.mFragment.displayPicture(this.ivLogo, localKaixinUser.logo, 2130838676);
            this.tvName.setText(localKaixinUser.realname);
            this.tvName.setTextColor(FriendsCheckInAdapter.this.mContext.getResources().getColor(2130839419));
            this.tvName.setPadding(0, 2, 0, 2);
            break label93;
            label339: String str1 = FriendsCheckInAdapter.this.mContext.getString(2131428143);
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = paramCheckInItem.checkInUser.mutualFriend.realname;
            String str2 = String.format(str1, arrayOfObject);
            this.tvFriendDesc.setText(str2);
            this.tvFriendDesc.setVisibility(0);
            break label127;
            label403: this.ivPhoto.setVisibility(0);
          }
        }
        catch (Exception localException1)
        {
          while (true)
            this.tvCheckinTime.setText("");
          try
          {
            label428: double d = Double.parseDouble(paramCheckInItem.distance);
            this.tvDistance.setText(KXTextUtil.getLbsFormatDistance(d));
            return;
          }
          catch (Exception localException2)
          {
            this.tvDistance.setText("");
          }
        }
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.adapter.FriendsCheckInAdapter
 * JD-Core Version:    0.6.0
 */