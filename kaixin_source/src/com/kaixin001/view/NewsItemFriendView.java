package com.kaixin001.view;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.kaixin001.adapter.NewsItemAdapter;
import com.kaixin001.fragment.AddFriendFragment;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.fragment.HomeFragment;
import com.kaixin001.fragment.SendGiftFragment;
import com.kaixin001.item.FriendInfo;
import com.kaixin001.item.GiftItem;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.item.LogoItem;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NewsItemFriendView extends NewsItemBaseView
{
  static final String S_AddFriend = "addfriend";
  static final String S_Becomefan = "becomefan";
  static final String S_BirthdayNotice = "birthdaynotice";
  static final String S_GiftNews = "usual";
  static final String S_GiftNews1 = "giftsend";
  static final String S_MaybeFriend = "maybefriend";
  static final String TAG = "NewsItemFriendView";
  public static final String TYPE_COMMON_USER = "0";
  public static final String TYPE_STAR = "-1";
  private RelativeLayout bottomLayout;
  private TextView bottomTextView = null;
  private ImageView ellipsisImage;
  private List<ViewGroup> friendLogoItemList = new ArrayList(5);
  private List<ImageView> friendLogoList = new ArrayList(5);
  private LinearLayout friendLogoListLayout = null;
  private List<ImageView> giftLogoList = new ArrayList(5);
  private LinearLayout giftLogoListLayout = null;
  ArrayList<KXLinkInfo> intro2KXLinkList;
  private KXIntroView mNewsItemContent = null;
  private boolean needFindmoreFriends = false;
  private RelativeLayout newbottomView = (RelativeLayout)this.rootView.findViewById(2131363209);
  private ImageView sendImgView = null;

  public NewsItemFriendView(BaseFragment paramBaseFragment, NewsInfo paramNewsInfo, NewsItemAdapter paramNewsItemAdapter)
  {
    super(paramBaseFragment, paramNewsInfo, paramNewsItemAdapter);
    this.rootView = this.inflater.inflate(2130903249, this);
    this.giftLogoList.add((ImageView)this.rootView.findViewById(2131362626));
    this.giftLogoList.add((ImageView)this.rootView.findViewById(2131362627));
    this.giftLogoList.add((ImageView)this.rootView.findViewById(2131362628));
    this.giftLogoList.add((ImageView)this.rootView.findViewById(2131363219));
    this.giftLogoList.add((ImageView)this.rootView.findViewById(2131363220));
    LogoClickListener localLogoClickListener = new LogoClickListener(null);
    ViewGroup localViewGroup1 = (ViewGroup)this.rootView.findViewById(2131363174);
    this.friendLogoItemList.add(localViewGroup1);
    ImageView localImageView1 = (ImageView)localViewGroup1.findViewById(2131363221);
    localImageView1.setClickable(true);
    localImageView1.setOnClickListener(localLogoClickListener);
    this.friendLogoList.add(localImageView1);
    ViewGroup localViewGroup2 = (ViewGroup)this.rootView.findViewById(2131363175);
    this.friendLogoItemList.add(localViewGroup2);
    ImageView localImageView2 = (ImageView)localViewGroup2.findViewById(2131363221);
    localImageView2.setClickable(true);
    localImageView2.setOnClickListener(localLogoClickListener);
    this.friendLogoList.add(localImageView2);
    ViewGroup localViewGroup3 = (ViewGroup)this.rootView.findViewById(2131363176);
    this.friendLogoItemList.add(localViewGroup3);
    ImageView localImageView3 = (ImageView)localViewGroup3.findViewById(2131363221);
    localImageView3.setClickable(true);
    localImageView3.setOnClickListener(localLogoClickListener);
    this.friendLogoList.add(localImageView3);
    ViewGroup localViewGroup4 = (ViewGroup)this.rootView.findViewById(2131363177);
    this.friendLogoItemList.add(localViewGroup4);
    ImageView localImageView4 = (ImageView)localViewGroup4.findViewById(2131363221);
    localImageView4.setClickable(true);
    localImageView4.setOnClickListener(localLogoClickListener);
    this.friendLogoList.add(localImageView4);
    ViewGroup localViewGroup5 = (ViewGroup)this.rootView.findViewById(2131363217);
    this.friendLogoItemList.add(localViewGroup5);
    ImageView localImageView5 = (ImageView)localViewGroup5.findViewById(2131363221);
    localImageView5.setClickable(true);
    localImageView5.setOnClickListener(localLogoClickListener);
    this.friendLogoList.add(localImageView5);
    this.ellipsisImage = ((ImageView)this.rootView.findViewById(2131363218));
    this.ellipsisImage.setClickable(false);
    this.bottomLayout = ((RelativeLayout)this.rootView.findViewById(2131363200));
  }

  private void bottomViewClick(NewsInfo paramNewsInfo, ArrayList<FriendInfo> paramArrayList)
  {
    ViewType localViewType = getNewtype(paramNewsInfo);
    switch ($SWITCH_TABLE$com$kaixin001$view$NewsItemFriendView$ViewType()[localViewType.ordinal()])
    {
    default:
      return;
    case 1:
      this.needFindmoreFriends = true;
      Intent localIntent5 = new Intent(this.mContext, AddFriendFragment.class);
      Bundle localBundle5 = new Bundle();
      localBundle5.putString("addfriendtype", "maybefriend");
      localBundle5.putBoolean("needFindmoreFriends", this.needFindmoreFriends);
      localBundle5.putSerializable("newsinfo", paramArrayList);
      localIntent5.putExtras(localBundle5);
      AnimationUtil.startFragment(this.mFragment, localIntent5, 1);
      return;
    case 2:
      Intent localIntent4 = new Intent(this.mContext, SendGiftFragment.class);
      Bundle localBundle4 = new Bundle();
      localBundle4.putSerializable("checkedFriendsList", paramArrayList);
      localIntent4.putExtras(localBundle4);
      AnimationUtil.startFragment(this.mFragment, localIntent4, 1);
      return;
    case 3:
      this.needFindmoreFriends = false;
      Intent localIntent3 = new Intent(this.mContext, AddFriendFragment.class);
      Bundle localBundle3 = new Bundle();
      localBundle3.putString("addfriendtype", "addfriend");
      localBundle3.putBoolean("needFindmoreFriends", this.needFindmoreFriends);
      localBundle3.putSerializable("newsinfo", paramArrayList);
      localIntent3.putExtras(localBundle3);
      AnimationUtil.startFragment(this.mFragment, localIntent3, 1);
      return;
    case 4:
      Intent localIntent2 = new Intent(this.mContext, AddFriendFragment.class);
      Bundle localBundle2 = new Bundle();
      localBundle2.putString("addfriendtype", "addfans");
      localBundle2.putSerializable("newsinfo", paramArrayList);
      localIntent2.putExtras(localBundle2);
      AnimationUtil.startFragment(this.mFragment, localIntent2, 1);
      return;
    case 5:
    }
    Intent localIntent1 = new Intent(this.mContext, SendGiftFragment.class);
    Bundle localBundle1 = new Bundle();
    localBundle1.putSerializable("checkedFriendsList", paramArrayList);
    localIntent1.putExtras(localBundle1);
    AnimationUtil.startFragment(this.mFragment, localIntent1, 1);
  }

  private void displayFriendList(NewsInfo paramNewsInfo)
  {
    int j;
    while (true)
    {
      try
      {
        hideFriendList();
        ArrayList localArrayList = new ArrayList();
        this.friendLogoListLayout.setVisibility(0);
        Iterator localIterator = paramNewsInfo.mlogoList.iterator();
        if (localIterator.hasNext())
          continue;
        if ((!"birthdaynotice".equals(paramNewsInfo.mNtypename)) || (this.sendImgView == null))
          continue;
        this.sendImgView.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            ArrayList localArrayList = (ArrayList)paramView.getTag();
            Intent localIntent = new Intent(NewsItemFriendView.this.mContext, SendGiftFragment.class);
            Bundle localBundle = new Bundle();
            localBundle.putString("giftId", "2");
            localBundle.putString("defaultps", "祝你生日快乐！(#生日蛋糕)");
            localBundle.putSerializable("checkedFriendsList", localArrayList);
            localIntent.putExtras(localBundle);
            AnimationUtil.startFragment(NewsItemFriendView.this.mFragment, localIntent, 1);
          }
        });
        if (getNewtype(paramNewsInfo).equals(ViewType.MaybeFriend))
        {
          i = 1;
          break;
          if (j < i)
            break label232;
          if ((getNewtype(paramNewsInfo).equals(ViewType.MaybeFriend)) || (paramNewsInfo.mlogoList.size() < 5))
            break label332;
          this.ellipsisImage.setVisibility(0);
          return;
          LogoItem localLogoItem1 = (LogoItem)localIterator.next();
          localArrayList.add(new FriendInfo(localLogoItem1.sname, localLogoItem1.suid, localLogoItem1.slogo));
          continue;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("NewsItemFriendView", "displayFriendList", localException);
        return;
      }
      if (paramNewsInfo.mlogoList.size() < this.friendLogoList.size())
      {
        i = paramNewsInfo.mlogoList.size();
        break label333;
      }
      int i = this.friendLogoList.size();
      break label333;
      label232: ImageView localImageView = (ImageView)this.friendLogoList.get(j);
      LogoItem localLogoItem2 = (LogoItem)paramNewsInfo.mlogoList.get(j);
      this.mFragment.displayRoundPicture(localImageView, localLogoItem2.slogo, ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
      localImageView.setTag(localLogoItem2);
      localImageView.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          LogoItem localLogoItem = (LogoItem)paramView.getTag();
          Intent localIntent = new Intent(NewsItemFriendView.this.mContext, HomeFragment.class);
          Bundle localBundle = new Bundle();
          localBundle.putString("fuid", localLogoItem.suid);
          localBundle.putString("fname", localLogoItem.sname);
          localBundle.putString("flogo", localLogoItem.slogo);
          localIntent.putExtras(localBundle);
          AnimationUtil.startFragment(NewsItemFriendView.this.mFragment, localIntent, 1);
        }
      });
      ((ViewGroup)this.friendLogoItemList.get(j)).setVisibility(0);
      j++;
    }
    label332: label333: 
    while (true)
    {
      j = 0;
      break;
      return;
    }
  }

  private void displayGiftList(NewsInfo paramNewsInfo)
  {
    if ("1002".equals(paramNewsInfo.mNtype))
      try
      {
        hideGiftList();
        for (int i = 0; ; i++)
        {
          if ((i >= paramNewsInfo.mgiftList.size()) || (i >= this.giftLogoList.size()))
          {
            new ArrayList().add(new FriendInfo(paramNewsInfo.mFname, paramNewsInfo.mFuid, paramNewsInfo.mFlogo));
            this.giftLogoListLayout.setVisibility(0);
            return;
          }
          this.mFragment.displayPicture((ImageView)this.giftLogoList.get(i), ((GiftItem)paramNewsInfo.mgiftList.get(i)).pic, 2130838085);
          ((ImageView)this.giftLogoList.get(i)).setVisibility(0);
        }
      }
      catch (Exception localException)
      {
        KXLog.e("NewsItemFriendView", "displayGiftList", localException);
      }
  }

  private void fillBottomText(NewsInfo paramNewsInfo)
  {
    ViewType localViewType = getNewtype(paramNewsInfo);
    switch ($SWITCH_TABLE$com$kaixin001$view$NewsItemFriendView$ViewType()[localViewType.ordinal()])
    {
    default:
      return;
    case 1:
      showOneButton(-1, -1, this.mContext.getResources().getString(2131428528), 17);
      return;
    case 2:
      showOneButton(-1, -1, "送" + paramNewsInfo.mFname + "礼物", 17);
      return;
    case 3:
    case 4:
      showOneButton(-1, -1, this.mContext.getResources().getString(2131428529), 17);
      return;
    case 5:
    }
    showOneButton(-1, -1, this.mContext.getResources().getString(2131428530), 17);
  }

  private ViewType getNewtype(NewsInfo paramNewsInfo)
  {
    if (TextUtils.isEmpty(paramNewsInfo.mNtypename))
      return ViewType.GiftNews;
    if (paramNewsInfo.mNtypename.equals("maybefriend"))
      return ViewType.MaybeFriend;
    if ((paramNewsInfo.mNtypename.equals("usual")) || (paramNewsInfo.mNtypename.equals("giftsend")))
      return ViewType.GiftNews;
    if (paramNewsInfo.mNtypename.equals("addfriend"))
      return ViewType.AddFriend;
    if (paramNewsInfo.mNtypename.equals("becomefan"))
      return ViewType.Becomefan;
    if (paramNewsInfo.mNtypename.equals("birthdaynotice"))
      return ViewType.BirthdayNotice;
    return ViewType.GiftNews;
  }

  private void hideFriendList()
  {
    this.friendLogoListLayout.setVisibility(8);
    for (int i = 0; ; i++)
    {
      if (i >= this.friendLogoList.size())
      {
        this.ellipsisImage.setVisibility(8);
        return;
      }
      ((ViewGroup)this.friendLogoItemList.get(i)).setVisibility(8);
    }
  }

  private void hideGiftList()
  {
    this.giftLogoListLayout.setVisibility(8);
    for (int i = 0; ; i++)
    {
      if (i >= this.giftLogoList.size())
        return;
      ((ImageView)this.giftLogoList.get(i)).setVisibility(8);
    }
  }

  private boolean isfans(NewsInfo paramNewsInfo)
  {
    return paramNewsInfo.mIntro.substring(0, 1).equals("成");
  }

  private boolean issendgift(NewsInfo paramNewsInfo)
  {
    return paramNewsInfo.mIntro.substring(0, 1).equals("收");
  }

  private void showText(NewsInfo paramNewsInfo)
  {
    KXIntroView.OnClickLinkListener localOnClickLinkListener = this.adapter.makeTitleClickListener(paramNewsInfo);
    this.intro2KXLinkList = this.adapter.getIntro2KXLinkList(paramNewsInfo);
    this.mNewsItemContent.setTitleList(this.adapter.getIntro2KXLinkList(paramNewsInfo));
    this.mNewsItemContent.setOnClickLinkListener(localOnClickLinkListener);
  }

  protected void onClickView(int paramInt, View paramView)
  {
    if (paramInt == 2)
    {
      NewsInfo localNewsInfo = (NewsInfo)paramView.getTag();
      ArrayList localArrayList1 = new ArrayList();
      ArrayList localArrayList2 = new ArrayList();
      localArrayList2.add(new FriendInfo(localNewsInfo.mFname, localNewsInfo.mFuid, localNewsInfo.mFlogo));
      Iterator localIterator;
      if (localNewsInfo.mlogoList != null)
        localIterator = localNewsInfo.mlogoList.iterator();
      while (true)
      {
        if (!localIterator.hasNext())
        {
          if (!issendgift(localNewsInfo))
            break;
          bottomViewClick(localNewsInfo, localArrayList2);
          return;
        }
        LogoItem localLogoItem = (LogoItem)localIterator.next();
        localArrayList1.add(new FriendInfo(localLogoItem.sname, localLogoItem.suid, localLogoItem.slogo));
      }
      bottomViewClick(localNewsInfo, localArrayList1);
      return;
    }
    super.onClickView(paramInt, paramView);
  }

  public boolean show(NewsInfo paramNewsInfo)
  {
    showSource(false);
    super.show(paramNewsInfo);
    showText(paramNewsInfo);
    fillBottomText(paramNewsInfo);
    if (("0".equals(paramNewsInfo.mNtype)) || (getNewtype(paramNewsInfo).equals(ViewType.BirthdayNotice)))
    {
      displayFriendList(paramNewsInfo);
      if ((!"1002".equals(paramNewsInfo.mNtype)) || (getNewtype(paramNewsInfo).equals(ViewType.BirthdayNotice)))
        break label93;
      displayGiftList(paramNewsInfo);
    }
    while (true)
    {
      return true;
      hideFriendList();
      break;
      label93: hideGiftList();
    }
  }

  private class LogoClickListener
    implements View.OnClickListener
  {
    private LogoClickListener()
    {
    }

    public void onClick(View paramView)
    {
      LogoItem localLogoItem = (LogoItem)paramView.getTag();
      Intent localIntent = new Intent(NewsItemFriendView.this.mContext, HomeFragment.class);
      Bundle localBundle = new Bundle();
      localBundle.putString("fuid", localLogoItem.suid);
      localBundle.putString("fname", localLogoItem.sname);
      localBundle.putString("flogo", localLogoItem.slogo);
      localIntent.putExtras(localBundle);
      AnimationUtil.startFragment(NewsItemFriendView.this.mFragment, localIntent, 1);
    }
  }

  private static enum ViewType
  {
    static
    {
      GiftNews = new ViewType("GiftNews", 1);
      AddFriend = new ViewType("AddFriend", 2);
      Becomefan = new ViewType("Becomefan", 3);
      BirthdayNotice = new ViewType("BirthdayNotice", 4);
      ViewType[] arrayOfViewType = new ViewType[5];
      arrayOfViewType[0] = MaybeFriend;
      arrayOfViewType[1] = GiftNews;
      arrayOfViewType[2] = AddFriend;
      arrayOfViewType[3] = Becomefan;
      arrayOfViewType[4] = BirthdayNotice;
      ENUM$VALUES = arrayOfViewType;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.NewsItemFriendView
 * JD-Core Version:    0.6.0
 */