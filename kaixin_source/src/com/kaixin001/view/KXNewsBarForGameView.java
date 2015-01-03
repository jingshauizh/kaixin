package com.kaixin001.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import java.util.ArrayList;
import java.util.List;

public class KXNewsBarForGameView extends LinearLayout
  implements Runnable
{
  private static final boolean FROM_PICTURTACTIVITY = true;
  private static final String FROM_WEBPAGE = "from_webpage";
  private ImageView clearImage = null;
  private int curPosition = 0;
  public boolean fromPicAction = true;
  private Handler handler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      super.handleMessage(paramMessage);
      KXNewsBarForGameView.this.rightScroll();
    }
  };
  private boolean isRun = true;
  private boolean isShowDot = true;
  private ArrayList<AdvGameItem> items;
  private View mAdvView = null;
  public KXViewPager mAdvViewPager = null;
  private Activity mContext;
  private BaseFragment mFragment;
  private List<View> mGameBarList = new ArrayList();
  private String mLocation;
  private int maxPage = 2147483647;
  private LinearLayout mdotLineLayout = null;
  private NewsBarViewPagerAdapter newsBarAdapater = null;
  private int sleepTime = 3;
  private Thread thread = null;

  public KXNewsBarForGameView(BaseFragment paramBaseFragment, String paramString)
  {
    super(paramBaseFragment.getActivity());
    this.mFragment = paramBaseFragment;
    this.mContext = paramBaseFragment.getActivity();
    this.mLocation = paramString;
    LayoutInflater.from(paramBaseFragment.getActivity()).inflate(2130903193, this, true);
    this.mdotLineLayout = ((LinearLayout)findViewById(2131362946));
    this.mAdvViewPager = ((KXViewPager)findViewById(2131362945));
    this.clearImage = ((ImageView)findViewById(2131362947));
    setBarDataList(paramString);
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
        if (KXNewsBarForGameView.this.isShowDot)
          KXNewsBarForGameView.this.constructViews();
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
    int i = this.items.size();
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
    int k = ((NewsBarViewPagerAdapter)this.mAdvViewPager.getAdapter()).getRealCount();
    if ((k != 0) && (this.mAdvViewPager.getCurrentItem() % k == j))
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
    setBarDataList(this.mLocation);
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

  public void setBarDataList(String paramString)
  {
    this.mGameBarList.clear();
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -1);
    int i;
    if (paramString.equals("3"))
    {
      this.items = AdvGameModel.getInstance().getAppBannerItems();
      i = AdvGameModel.getInstance().getUid();
      if ((this.items == null) || (this.items.size() <= 0));
    }
    for (int j = 0; ; j++)
    {
      if (j >= this.items.size())
      {
        return;
        if (!paramString.equals("2"))
          break;
        this.items = AdvGameModel.getInstance().getGameBannerItems();
        break;
      }
      ImageView localImageView = new ImageView(this.mContext);
      localImageView.setLayoutParams(localLayoutParams);
      localImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
      this.mFragment.displayPicture(localImageView, ((AdvGameItem)this.items.get(j)).getAdvertImageUrl(), 2130838105);
      String str1 = ((AdvGameItem)this.items.get(j)).getAdvertClickUrl();
      String str2 = ((AdvGameItem)this.items.get(j)).getTitle();
      String str3 = ((AdvGameItem)this.items.get(j)).getId();
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append(str1);
      localStringBuffer.append("&id=").append(str3);
      localImageView.setTag(this.items.get(j));
      localImageView.setOnClickListener(new View.OnClickListener(str3, localStringBuffer, str2, i)
      {
        public void onClick(View paramView)
        {
          KXNewsBarForGameView.this.mContext.getApplicationContext().getSharedPreferences("from_webpage", 0).edit().putString("mid", this.val$id).commit();
          Intent localIntent = IntentUtil.setWebPageIntent(KXNewsBarForGameView.this.mContext, this.val$realUrl.toString(), this.val$title, null, -1);
          if (KXNewsBarForGameView.this.fromPicAction)
            localIntent.putExtra("from", true);
          while (true)
          {
            localIntent.putExtra("id", this.val$id);
            localIntent.putExtra("uid", this.val$uid);
            AnimationUtil.startFragment(KXNewsBarForGameView.this.mFragment, localIntent, 1);
            UserHabitUtils.getInstance(KXNewsBarForGameView.this.mContext).addUserHabit("Game_Banner_Click");
            return;
            localIntent.putExtra("from", false);
          }
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
 * Qualified Name:     com.kaixin001.view.KXNewsBarForGameView
 * JD-Core Version:    0.6.0
 */