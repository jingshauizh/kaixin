package com.kaixin001.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class CheckInAdapter extends BaseAdapter
{
  private static final String TAG = "NearbyFriendAdapter";
  ArrayList<CheckInItem> mCheckInList;
  private Activity mContext;
  private String mCurrentPoiId = null;
  private LinearLayout mFooter;
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  private BaseFragment mFragment;

  public CheckInAdapter(BaseFragment paramBaseFragment, int paramInt, ArrayList<CheckInItem> paramArrayList)
  {
    this.mCheckInList = paramArrayList;
    this.mFragment = paramBaseFragment;
    this.mContext = paramBaseFragment.getActivity();
    this.mFooter = ((LinearLayout)paramBaseFragment.getActivity().getLayoutInflater().inflate(2130903153, null));
    this.mFooterTV = ((TextView)this.mFooter.findViewById(2131362073));
    this.mFooterTV.setText(2131427748);
    this.mFooterProBar = ((ProgressBar)this.mFooter.findViewById(2131362072));
  }

  public int getCount()
  {
    if (this.mCheckInList == null)
      return 0;
    return this.mCheckInList.size();
  }

  public Object getItem(int paramInt)
  {
    if (this.mCheckInList == null);
    do
      return null;
    while ((paramInt < 0) || (paramInt > this.mCheckInList.size()));
    return this.mCheckInList.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    NearbyFriendItemViewTag localNearbyFriendItemViewTag;
    int i;
    try
    {
      CheckInItem localCheckInItem = (CheckInItem)getItem(paramInt);
      if (localCheckInItem == null)
        return paramView;
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
        localNearbyFriendItemViewTag.updateMemberItem(localCheckInItem);
        i = getCount();
        if (i != 1)
          break;
        localNearbyFriendItemViewTag.showSingleBkg();
        break label168;
        localNearbyFriendItemViewTag = (NearbyFriendItemViewTag)paramView.getTag();
      }
      if (paramInt == 0)
        localNearbyFriendItemViewTag.showTopBkg();
    }
    catch (Exception localException)
    {
      KXLog.e("NearbyFriendAdapter", "getView", localException);
    }
    if (paramInt == i - 1)
      localNearbyFriendItemViewTag.showBottomBkg();
    else
      localNearbyFriendItemViewTag.showMiddleBkg();
    label168: return paramView;
  }

  public boolean isFooterShowLoading()
  {
    if (this.mFooterProBar == null);
    do
      return false;
    while (this.mFooterProBar.getVisibility() != 0);
    return true;
  }

  public void setCurrentPoiId(String paramString)
  {
    this.mCurrentPoiId = paramString;
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
    private ImageView ivLogo = null;
    private ImageView ivPhoto = null;
    private View mLayoutInfo = null;
    private View mView = null;
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
            IntentUtil.showHomeFragment(CheckInAdapter.this.mFragment, str1, str2);
          }
          do
            return;
          while ((!paramKXLinkInfo.isLbsPoi()) || (CheckInAdapter.this.mContext.toString().indexOf("PoiActivity") != -1));
          IntentUtil.showLbsPositionDetailFragment(CheckInAdapter.this.mFragment, CheckInAdapter.NearbyFriendItemViewTag.this.checkinItem.poiId, CheckInAdapter.NearbyFriendItemViewTag.this.checkinItem.poiName, "", CheckInAdapter.NearbyFriendItemViewTag.this.checkinItem.distance);
        }
      };
      Object localObject;
      this.mView = localObject;
      this.mLayoutInfo = localObject.findViewById(2131363461);
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
      float f = paramInt * CheckInAdapter.this.mContext.getResources().getDisplayMetrics().density;
      this.mView.setPadding(j, i, k, (int)f);
    }

    private void setPaddingTop(int paramInt)
    {
      float f = paramInt * CheckInAdapter.this.mContext.getResources().getDisplayMetrics().density;
      int i = this.mView.getPaddingLeft();
      int j = this.mView.getPaddingRight();
      int k = this.mView.getPaddingBottom();
      this.mView.setPadding(i, (int)f, j, k);
    }

    public void onClick(View paramView)
    {
      if ((paramView.getId() == 2131361834) || (paramView.getId() == 2131362058))
        IntentUtil.showHomeFragment(CheckInAdapter.this.mFragment, this.checkinItem.checkInUser.user.uid, this.checkinItem.checkInUser.user.realname);
      while (true)
      {
        return;
        if (paramView.getId() != 2131363465)
          break;
        if ((this.checkinItem == null) || (this.checkinItem.poiId == null) || (this.checkinItem.poiId.equals(CheckInAdapter.this.mCurrentPoiId)))
          continue;
        IntentUtil.showLbsPositionDetailFragment(CheckInAdapter.this.mFragment, this.checkinItem.poiId, this.checkinItem.poiName, "", this.checkinItem.distance);
        return;
      }
      String str = KXTextUtil.getLbsPoiText(this.checkinItem.poiName, this.checkinItem.poiId);
      IntentUtil.showLbsCheckInCommentFragment(CheckInAdapter.this.mFragment, this.checkinItem.wid, this.checkinItem.checkInUser.user.uid, this.checkinItem.checkInUser.user.realname, this.checkinItem.checkInUser.user.logo, "1", this.checkinItem.content, String.valueOf(this.checkinItem.ctime), this.checkinItem.thumbnail, this.checkinItem.large, str, "", this.checkinItem.mMapUrl);
    }

    public void showBottomBkg()
    {
      this.mLayoutInfo.setBackgroundResource(2130837699);
      setPaddingTop(0);
      setPaddingBottom(10);
    }

    public void showMiddleBkg()
    {
      this.mLayoutInfo.setBackgroundResource(2130837700);
      setPaddingTop(0);
      setPaddingBottom(0);
    }

    public void showSingleBkg()
    {
      this.mLayoutInfo.setBackgroundResource(2130837701);
      setPaddingTop(10);
      setPaddingBottom(10);
    }

    public void showTopBkg()
    {
      this.mLayoutInfo.setBackgroundResource(2130837702);
      setPaddingTop(10);
      setPaddingBottom(0);
    }

    public void updateMemberItem(CheckInItem paramCheckInItem)
      throws Exception
    {
      if (paramCheckInItem == null)
        return;
      this.checkinItem = paramCheckInItem;
      KaixinUser localKaixinUser = paramCheckInItem.checkInUser.user;
      float f = 0.0F;
      if (localKaixinUser == null)
      {
        this.ivLogo.setImageBitmap(null);
        this.tvName.setText("");
      }
      while (true)
      {
        DisplayMetrics localDisplayMetrics = CheckInAdapter.this.mContext.getResources().getDisplayMetrics();
        int i = -1;
        if ((paramCheckInItem.checkInUser.mutualFriend == null) || (TextUtils.isEmpty(paramCheckInItem.checkInUser.mutualFriend.realname)))
        {
          this.tvFriendDesc.setVisibility(4);
          label93: if (i != -1)
            break label370;
          this.tvName.setWidth(3 + (int)f);
          label110: ArrayList localArrayList = ParseNewsInfoUtil.parseNewsLinkString(KXTextUtil.getLbsPoiText(paramCheckInItem.poiName, paramCheckInItem.poiId));
          this.tvPosition.setTitleList(localArrayList);
          if (!TextUtils.isEmpty(paramCheckInItem.thumbnail))
            break label404;
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
              break label429;
            this.tvDistance.setText("");
            return;
            CheckInAdapter.this.mFragment.displayPicture(this.ivLogo, localKaixinUser.logo, 2130838676);
            this.tvName.setText(localKaixinUser.realname);
            this.tvName.setTextColor(CheckInAdapter.this.mContext.getResources().getColor(2130839419));
            this.tvName.setPadding(0, 2, 0, 2);
            f = this.tvName.getPaint().measureText(localKaixinUser.realname);
            break;
            String str1 = CheckInAdapter.this.mContext.getString(2131428143);
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = paramCheckInItem.checkInUser.mutualFriend.realname;
            String str2 = String.format(str1, arrayOfObject);
            this.tvFriendDesc.setText(str2);
            this.tvFriendDesc.setVisibility(0);
            i = -16 + localDisplayMetrics.widthPixels / 3;
            break label93;
            label370: if (f < i)
            {
              this.tvName.setWidth(3 + (int)f);
              break label110;
            }
            this.tvName.setWidth(i);
            break label110;
            label404: this.ivPhoto.setVisibility(0);
          }
        }
        catch (Exception localException1)
        {
          while (true)
            this.tvCheckinTime.setText("");
          try
          {
            label429: double d = Double.parseDouble(paramCheckInItem.distance);
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
 * Qualified Name:     com.kaixin001.adapter.CheckInAdapter
 * JD-Core Version:    0.6.0
 */