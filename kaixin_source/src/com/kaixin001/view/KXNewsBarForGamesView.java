package com.kaixin001.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout.LayoutParams;
import com.kaixin001.adapter.NewsBarViewPagerAdapter;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.item.AdvGameItem;
import com.kaixin001.model.AdvGameModel;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.UserHabitUtils;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class KXNewsBarForGamesView extends LinearLayout
  implements Runnable
{
  private ImageView clearImage = null;
  private int curPosition = 0;
  private Handler handler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      super.handleMessage(paramMessage);
      KXNewsBarForGamesView.this.rightScroll();
    }
  };
  private boolean isRun = true;
  private boolean isShowDot = true;
  private ArrayList<AdvGameItem> items;
  public KXViewPager mAdvViewPager = null;
  private Activity mContext;
  private BaseFragment mFragment;
  private List<View> mGameBarList = new ArrayList();
  private LinearLayout mdotLineLayout = null;
  private NewsBarViewPagerAdapter newsBarAdapater = null;
  private int sleepTime = 3;
  private Thread thread = null;

  public KXNewsBarForGamesView(BaseFragment paramBaseFragment)
  {
    super(paramBaseFragment.getActivity());
    this.mFragment = paramBaseFragment;
    this.mContext = paramBaseFragment.getActivity();
    LayoutInflater.from(paramBaseFragment.getActivity()).inflate(2130903193, this, true);
    this.mdotLineLayout = ((LinearLayout)findViewById(2131362946));
    this.mAdvViewPager = ((KXViewPager)findViewById(2131362945));
    this.clearImage = ((ImageView)findViewById(2131362947));
    setBarDataList();
    this.newsBarAdapater = new NewsBarViewPagerAdapter(this.mGameBarList);
    this.mAdvViewPager.setAdapter(this.newsBarAdapater);
    this.mAdvViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
    {
      public void onPageScrollStateChanged(int paramInt)
      {
      }

      public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
      {
      }

      public void onPageSelected(int paramInt)
      {
        if (KXNewsBarForGamesView.this.isShowDot)
          KXNewsBarForGamesView.this.constructDotViews();
      }
    });
    if (this.isShowDot)
      constructDotViews();
    setCurrentPage(0);
    this.thread = new Thread(this);
    this.thread.start();
  }

  private void constructDotViews()
  {
    this.mdotLineLayout.removeAllViews();
    LinearLayout localLinearLayout = new LinearLayout(this.mContext);
    TableLayout.LayoutParams localLayoutParams1 = new TableLayout.LayoutParams();
    localLayoutParams1.width = -2;
    localLayoutParams1.height = -2;
    localLinearLayout.setLayoutParams(localLayoutParams1);
    if (this.items.size() == 1)
    {
      this.mdotLineLayout.setVisibility(8);
      return;
    }
    this.mdotLineLayout.setVisibility(0);
    int i = 0;
    if (i >= this.items.size())
    {
      this.mdotLineLayout.addView(localLinearLayout);
      return;
    }
    ImageView localImageView = new ImageView(this.mContext);
    int j = ((NewsBarViewPagerAdapter)this.mAdvViewPager.getAdapter()).getRealCount();
    if ((j != 0) && (this.mAdvViewPager.getCurrentItem() % j == i))
      localImageView.setBackgroundResource(2130838175);
    while (true)
    {
      if (i != 0)
      {
        TableLayout.LayoutParams localLayoutParams2 = new TableLayout.LayoutParams();
        localLayoutParams2.setMargins(10, 0, 0, 10);
        localImageView.setLayoutParams(localLayoutParams2);
      }
      localLinearLayout.addView(localImageView);
      i++;
      break;
      localImageView.setBackgroundResource(2130838174);
    }
  }

  public void leftScroll()
  {
    KXViewPager localKXViewPager;
    if (this.mAdvViewPager != null)
    {
      localKXViewPager = this.mAdvViewPager;
      int i = -1 + this.curPosition;
      this.curPosition = i;
      if (i > 0)
        break label46;
    }
    label46: for (int j = -1 + this.mGameBarList.size(); ; j = this.curPosition)
    {
      localKXViewPager.setCurrentItem(j);
      return;
    }
  }

  public void notifyDataSetChanged()
  {
    setBarDataList();
    this.newsBarAdapater.notifyDataSetChanged();
    this.mAdvViewPager.setCurrentItem(0);
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
        if ((this.items != null) && (this.items.size() > 0))
          Thread.sleep(1000 * ((AdvGameItem)this.items.get(this.curPosition)).getDisplayTime());
        while (this.handler != null)
        {
          this.handler.sendMessage(Message.obtain(this.handler));
          break;
          Thread.sleep(1000 * this.sleepTime);
        }
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

  public void setBarDataList()
  {
    this.mGameBarList.clear();
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -1);
    this.items = AdvGameModel.getInstance().getGameBannerItems();
    if ((this.items != null) && (this.items.size() > 0));
    for (int i = 0; ; i++)
    {
      if (i >= this.items.size())
        return;
      AdvGameItem localAdvGameItem = (AdvGameItem)this.items.get(i);
      ImageView localImageView = new ImageView(this.mContext);
      localImageView.setLayoutParams(localLayoutParams);
      localImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
      this.mFragment.displayPicture(localImageView, localAdvGameItem.getAdvertImageUrl(), 2130838105);
      String str1 = localAdvGameItem.getAdvertClickUrl();
      String str2 = localAdvGameItem.getTitle();
      String str3 = localAdvGameItem.getId();
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append(str1).append("&id=").append(str3);
      localImageView.setTag(this.items.get(i));
      localImageView.setOnClickListener(new View.OnClickListener(localStringBuffer, str2, str3)
      {
        public void onClick(View paramView)
        {
          Intent localIntent = IntentUtil.setWebPageIntent(KXNewsBarForGamesView.this.mContext, this.val$realUrl.toString(), this.val$title, null, -1);
          System.out.println(this.val$realUrl.toString());
          localIntent.putExtra("from", false);
          localIntent.putExtra("id", this.val$id);
          localIntent.putExtra("uid", AdvGameModel.getInstance().getUid());
          AnimationUtil.startFragment(KXNewsBarForGamesView.this.mFragment, localIntent, 1);
          UserHabitUtils.getInstance(KXNewsBarForGamesView.this.mContext).addUserHabit("Game_Banner_Click");
        }
      });
      this.mGameBarList.add(localImageView);
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
    {
      this.mAdvViewPager.setCurrentItem(paramInt);
      this.curPosition = paramInt;
    }
  }

  public void setIsShowDot(boolean paramBoolean)
  {
    this.isShowDot = paramBoolean;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXNewsBarForGamesView
 * JD-Core Version:    0.6.0
 */