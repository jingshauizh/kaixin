package com.kaixin001.view;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout.LayoutParams;
import com.kaixin001.adapter.NewsBarViewPagerAdapter;
import com.kaixin001.engine.KXPKEngine;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.fragment.CoverListFragment;
import com.kaixin001.fragment.FilmDetailFragment;
import com.kaixin001.fragment.FilmListFragment;
import com.kaixin001.fragment.FindFriendFragment;
import com.kaixin001.fragment.FriendsFragment;
import com.kaixin001.fragment.GiftNewsFragment;
import com.kaixin001.fragment.PositionMainFragment;
import com.kaixin001.fragment.RecommendAppsFragment;
import com.kaixin001.fragment.SharedPostFragment;
import com.kaixin001.fragment.WriteWeiboFragment;
import com.kaixin001.item.AdvertItem;
import com.kaixin001.model.AdvertModel;
import com.kaixin001.model.PKModel;
import com.kaixin001.task.KaiXinPKTask;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.UserHabitUtils;
import java.util.ArrayList;
import java.util.List;

public class KXNewsBarView extends LinearLayout
  implements Runnable
{
  private static final boolean FROM_PICTURTACTIVITY = true;
  private static final String FROM_WEBPAGE = "from_webpage";
  private ImageView clearImage = null;
  private Handler handler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      super.handleMessage(paramMessage);
      KXNewsBarView.this.rightScroll();
    }
  };
  private boolean isCanScroll = true;
  private boolean isRun = true;
  private boolean isShowDot = true;
  private AdvertItem[] items;
  private View mAdvView = null;
  public KXViewPager mAdvViewPager = null;
  private Activity mContext = null;
  private BaseFragment mFragment = null;
  private List<View> mNewBarlist = new ArrayList();
  private int maxPage = 2147483647;
  private LinearLayout mdotLineLayout = null;
  private NewsBarViewPagerAdapter newsBarAdapater = null;
  private int sleepTime = 3;
  private Thread thread = null;

  public KXNewsBarView(BaseFragment paramBaseFragment)
  {
    super(paramBaseFragment.getActivity());
    this.mContext = paramBaseFragment.getActivity();
    this.mFragment = paramBaseFragment;
    LayoutInflater.from(paramBaseFragment.getActivity()).inflate(2130903245, this, true);
    this.mdotLineLayout = ((LinearLayout)findViewById(2131362946));
    this.mAdvViewPager = ((KXViewPager)findViewById(2131362945));
    this.clearImage = ((ImageView)findViewById(2131362947));
    this.clearImage.setVisibility(0);
    fillNewsBarList();
    this.newsBarAdapater = new NewsBarViewPagerAdapter(this.mNewBarlist);
    this.mAdvViewPager.setAdapter(this.newsBarAdapater);
    this.mAdvViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
    {
      public void onPageScrollStateChanged(int paramInt)
      {
        KXNewsBarView.this.isCanScroll = true;
      }

      public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
      {
        KXNewsBarView.this.isCanScroll = false;
      }

      public void onPageSelected(int paramInt)
      {
        if (KXNewsBarView.this.isShowDot)
          KXNewsBarView.this.constructViews();
      }
    });
    if (this.isShowDot)
      constructViews();
    setCurrentPage(0);
    this.thread = new Thread(this);
    this.thread.start();
  }

  private void constructViews()
  {
    this.mdotLineLayout.removeAllViews();
    LinearLayout localLinearLayout = new LinearLayout(this.mContext);
    TableLayout.LayoutParams localLayoutParams1 = new TableLayout.LayoutParams();
    localLayoutParams1.width = -2;
    localLayoutParams1.height = -2;
    localLinearLayout.setLayoutParams(localLayoutParams1);
    if (AdvertModel.getInstance().item == null)
      return;
    int i = AdvertModel.getInstance().item.length;
    if (i == 1)
    {
      this.mdotLineLayout.setVisibility(8);
      return;
    }
    int j = 0;
    if (j >= i)
    {
      this.mdotLineLayout.addView(localLinearLayout);
      return;
    }
    ImageView localImageView = new ImageView(this.mContext);
    if (this.mAdvViewPager.getCurrentItem() % i == j)
      localImageView.setBackgroundResource(2130838175);
    while (true)
    {
      if (j != 0)
      {
        TableLayout.LayoutParams localLayoutParams2 = new TableLayout.LayoutParams();
        localLayoutParams2.setMargins(10, 0, 0, 10);
        localImageView.setLayoutParams(localLayoutParams2);
      }
      localLinearLayout.addView(localImageView);
      j++;
      break;
      localImageView.setBackgroundResource(2130838174);
    }
  }

  public void fillNewsBarList()
  {
    this.mNewBarlist.clear();
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -1);
    this.items = AdvertModel.getInstance().item;
    int i = AdvertModel.getInstance().uid;
    if (this.items == null);
    while (true)
    {
      return;
      for (int j = 0; j < this.items.length; j++)
      {
        ImageView localImageView = new ImageView(this.mContext);
        localImageView.setLayoutParams(localLayoutParams);
        localImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        this.mFragment.displayPicture(localImageView, this.items[j].advertImageUrl, 2130838105);
        String str1 = this.items[j].advertClickUrl;
        String str2 = this.items[j].title;
        int k = this.items[j].id;
        localImageView.setTag(this.items[j]);
        localImageView.setOnClickListener(new View.OnClickListener(str1, k, str2, i)
        {
          public void onClick(View paramView)
          {
            AdvertItem localAdvertItem = (AdvertItem)paramView.getTag();
            if (localAdvertItem.type.equals("link"))
            {
              Intent localIntent13 = IntentUtil.setWebPageIntent(KXNewsBarView.this.mContext, localAdvertItem.advertClickUrl, KXNewsBarView.this.mContext.getApplication().getString(2131427974), null, -1);
              localIntent13.putExtra("scale", 49);
              AnimationUtil.startFragment(KXNewsBarView.this.mFragment, localIntent13, 1);
            }
            while (true)
            {
              UserHabitUtils.getInstance(KXNewsBarView.this.mContext).addUserHabit("Home_Banner_Click");
              return;
              if (localAdvertItem.type.equals("voice"))
              {
                Bundle localBundle1 = new Bundle();
                localBundle1.putString("content", this.val$advertClickUrl);
                IntentUtil.showVoiceRecordFragment(KXNewsBarView.this.mFragment, localBundle1);
                continue;
              }
              if (localAdvertItem.type.equals("record"))
              {
                Intent localIntent1 = new Intent(KXNewsBarView.this.mContext, WriteWeiboFragment.class);
                Bundle localBundle2 = new Bundle();
                localBundle2.putString("content", this.val$advertClickUrl);
                localIntent1.putExtras(localBundle2);
                AnimationUtil.startFragment(KXNewsBarView.this.mFragment, localIntent1, 1);
                continue;
              }
              if (localAdvertItem.type.equals("checkin"))
              {
                IntentUtil.showCheckInFragment(KXNewsBarView.this.mContext, KXNewsBarView.this.mFragment, null, null, null, null, this.val$advertClickUrl, null, false);
                continue;
              }
              if (localAdvertItem.type.equals("nearpeople"))
              {
                Intent localIntent2 = new Intent(KXNewsBarView.this.mContext, PositionMainFragment.class);
                Bundle localBundle3 = new Bundle();
                localBundle3.putInt("CurrentTab", 1);
                localIntent2.putExtras(localBundle3);
                AnimationUtil.startFragment(KXNewsBarView.this.mFragment, localIntent2, 1);
                continue;
              }
              if (localAdvertItem.type.equals("nearpic"))
              {
                Intent localIntent3 = new Intent(KXNewsBarView.this.mContext, PositionMainFragment.class);
                Bundle localBundle4 = new Bundle();
                localBundle4.putInt("CurrentTab", 1);
                localIntent3.putExtras(localBundle4);
                AnimationUtil.startFragment(KXNewsBarView.this.mFragment, localIntent3, 1);
                continue;
              }
              if (localAdvertItem.type.equals("friendlist"))
              {
                Intent localIntent4 = new Intent(KXNewsBarView.this.mContext, FriendsFragment.class);
                AnimationUtil.startFragment(KXNewsBarView.this.mFragment, localIntent4, 1);
                continue;
              }
              if (localAdvertItem.type.equals("friendadd"))
              {
                Intent localIntent5 = new Intent(KXNewsBarView.this.mContext, FindFriendFragment.class);
                AnimationUtil.startFragment(KXNewsBarView.this.mFragment, localIntent5, 1);
                continue;
              }
              if (localAdvertItem.type.equals("repastelist"))
              {
                Intent localIntent6 = new Intent(KXNewsBarView.this.mContext, SharedPostFragment.class);
                AnimationUtil.startFragment(KXNewsBarView.this.mFragment, localIntent6, 1);
                continue;
              }
              if (localAdvertItem.type.equals("shot"))
              {
                KXNewsBarView.this.mFragment.takePictureWithCamera();
                continue;
              }
              if (localAdvertItem.type.equals("profile"))
              {
                IntentUtil.showHomeFragment(KXNewsBarView.this.mFragment, "152655379", "独月影枫");
                continue;
              }
              if (localAdvertItem.type.equals("gift"))
              {
                Intent localIntent7 = new Intent(KXNewsBarView.this.mContext, GiftNewsFragment.class);
                new Bundle().putString("giftId", this.val$advertClickUrl);
                AnimationUtil.startFragment(KXNewsBarView.this.mFragment, localIntent7, 1);
                continue;
              }
              if (localAdvertItem.type.equals("chgbg"))
              {
                Intent localIntent8 = new Intent(KXNewsBarView.this.mContext, CoverListFragment.class);
                localIntent8.putExtra("from", "KXNewsBarView");
                AnimationUtil.startFragment(KXNewsBarView.this.mFragment, localIntent8, 1);
                continue;
              }
              if (localAdvertItem.type.equals("game"))
              {
                Intent localIntent9 = new Intent(KXNewsBarView.this.mContext, RecommendAppsFragment.class);
                AnimationUtil.startFragment(KXNewsBarView.this.mFragment, localIntent9, 1);
                continue;
              }
              if (localAdvertItem.type.equals("topic"))
              {
                new KaiXinPKTask(KXNewsBarView.this.getContext(), KXNewsBarView.this.mFragment, localAdvertItem.advertClickUrl).execute(new Void[0]);
                continue;
              }
              if (localAdvertItem.type.equals("filmlist"))
              {
                Intent localIntent10 = new Intent(KXNewsBarView.this.mContext, FilmListFragment.class);
                localIntent10.putExtra("from", "KXNewsBarView");
                KXNewsBarView.this.mFragment.startFragment(localIntent10, true, 1);
                continue;
              }
              if (localAdvertItem.type.equals("filminfo"))
              {
                Intent localIntent11 = new Intent(KXNewsBarView.this.mContext, FilmDetailFragment.class);
                localIntent11.putExtra("fid", localAdvertItem.filmId);
                localIntent11.putExtra("name", localAdvertItem.filmName);
                KXNewsBarView.this.mFragment.startFragment(localIntent11, true, 1);
                continue;
              }
              if (!localAdvertItem.type.equals("photoactivity"))
                continue;
              KXNewsBarView.this.mContext.getApplicationContext().getSharedPreferences("from_webpage", 0).edit().putString("mid", this.val$id).commit();
              Intent localIntent12 = IntentUtil.setWebPageIntent(KXNewsBarView.this.mContext, this.val$advertClickUrl, this.val$title, null, -1);
              localIntent12.putExtra("from", true);
              localIntent12.putExtra("id", this.val$id);
              localIntent12.putExtra("uid", this.val$uid);
              AnimationUtil.startFragment(KXNewsBarView.this.mFragment, localIntent12, 1);
            }
          }
        });
        this.mNewBarlist.add(localImageView);
      }
    }
  }

  public void leftScroll()
  {
    KXViewPager localKXViewPager;
    if (this.mAdvViewPager != null)
    {
      localKXViewPager = this.mAdvViewPager;
      if (-1 + this.mAdvViewPager.getCurrentItem() > 0)
        break label42;
    }
    label42: for (int i = -1 + this.mNewBarlist.size(); ; i = -1 + this.mAdvViewPager.getCurrentItem())
    {
      localKXViewPager.setCurrentItem(i);
      return;
    }
  }

  public void notifyDataSetChanged()
  {
    fillNewsBarList();
    this.newsBarAdapater.notifyDataSetChanged();
  }

  public void rightScroll()
  {
    if (this.mAdvViewPager != null)
      this.mAdvViewPager.setCurrentItem(1 + this.mAdvViewPager.getCurrentItem());
  }

  public void run()
  {
    Thread localThread = Thread.currentThread();
    while (true)
    {
      if ((!this.isRun) || (Thread.interrupted()) || (localThread != this.thread))
        return;
      try
      {
        boolean bool;
        do
        {
          Thread.sleep(1000 * this.sleepTime);
          bool = this.isCanScroll;
        }
        while (!bool);
        if (this.handler == null)
          continue;
        this.handler.sendMessage(Message.obtain(this.handler));
      }
      catch (InterruptedException localInterruptedException)
      {
        while (true)
        {
          if (this.thread == null)
            continue;
          this.thread.interrupt();
        }
      }
    }
  }

  public void setClearButtonOnClickListener(View.OnClickListener paramOnClickListener)
  {
    this.clearImage.setOnClickListener(paramOnClickListener);
  }

  public void setClearListener(View.OnClickListener paramOnClickListener)
  {
    this.clearImage.setOnClickListener(paramOnClickListener);
  }

  public void setCurrentPage(int paramInt)
  {
    if (this.mAdvViewPager != null)
      this.mAdvViewPager.setCurrentItem(paramInt);
  }

  public void setIsShowDot(boolean paramBoolean)
  {
    this.isShowDot = paramBoolean;
  }

  class PKTask extends AsyncTask<Void, Void, Integer>
  {
    private String title;

    public PKTask(String arg2)
    {
      Object localObject;
      this.title = localObject;
    }

    protected Integer doInBackground(Void[] paramArrayOfVoid)
    {
      return Integer.valueOf(KXPKEngine.getInstance().pkInfoByTitle(KXNewsBarView.this.getContext(), this.title));
    }

    protected void onPostExecute(Integer paramInteger)
    {
      super.onPostExecute(paramInteger);
      if (paramInteger.intValue() == 1)
      {
        String str = PKModel.getInstance().getPkid();
        if (!TextUtils.isEmpty(str))
        {
          IntentUtil.showPkFragment(KXNewsBarView.this.mFragment, str);
          return;
        }
        IntentUtil.showTopicGroupActivity(KXNewsBarView.this.mFragment, this.title);
        return;
      }
      IntentUtil.showTopicGroupActivity(KXNewsBarView.this.mFragment, this.title);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXNewsBarView
 * JD-Core Version:    0.6.0
 */