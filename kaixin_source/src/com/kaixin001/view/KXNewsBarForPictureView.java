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
import com.kaixin001.item.PhotoGraphItem;
import com.kaixin001.model.PhotographModel;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.UserHabitUtils;
import java.util.ArrayList;
import java.util.List;

public class KXNewsBarForPictureView extends LinearLayout
  implements Runnable
{
  private static final boolean FROM_PICTURTACTIVITY = true;
  private static final String FROM_WEBPAGE = "from_webpage";
  private ImageView clearImage = null;
  private int curPosition = 0;
  private Handler handler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      super.handleMessage(paramMessage);
      KXNewsBarForPictureView.this.rightScroll();
    }
  };
  private boolean isRun = true;
  private boolean isShowDot = true;
  private PhotoGraphItem[] items;
  private View mAdvView = null;
  public KXViewPager mAdvViewPager = null;
  private Activity mContext;
  private BaseFragment mFragment;
  private List<View> mNewBarlist = new ArrayList();
  private int maxPage = 2147483647;
  private LinearLayout mdotLineLayout = null;
  private NewsBarViewPagerAdapter newsBarAdapater = null;
  private int sleepTime = 5;
  private Thread thread = null;

  public KXNewsBarForPictureView(BaseFragment paramBaseFragment)
  {
    super(paramBaseFragment.getActivity());
    this.mFragment = paramBaseFragment;
    this.mContext = paramBaseFragment.getActivity();
    LayoutInflater.from(paramBaseFragment.getActivity()).inflate(2130903245, this, true);
    this.mdotLineLayout = ((LinearLayout)findViewById(2131362946));
    this.mAdvViewPager = ((KXViewPager)findViewById(2131362945));
    this.clearImage = ((ImageView)findViewById(2131362947));
    fillNewsBarList();
    this.newsBarAdapater = new NewsBarViewPagerAdapter(this.mNewBarlist);
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
        if (KXNewsBarForPictureView.this.isShowDot)
          KXNewsBarForPictureView.this.constructViews();
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
    if (PhotographModel.getInstance().item == null)
      return;
    int i = PhotographModel.getInstance().item.length;
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
    if (this.mAdvViewPager.getCurrentItem() % ((NewsBarViewPagerAdapter)this.mAdvViewPager.getAdapter()).getRealCount() == j)
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
    this.items = PhotographModel.getInstance().item;
    int i = PhotographModel.getInstance().uid;
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
        String str3 = this.items[j].id;
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append(str1);
        localStringBuffer.append("&id=");
        localStringBuffer.append(str3);
        localImageView.setTag(this.items[j]);
        localImageView.setOnClickListener(new View.OnClickListener(str3, localStringBuffer, str2, i)
        {
          public void onClick(View paramView)
          {
            SharedPreferences.Editor localEditor = KXNewsBarForPictureView.this.mContext.getApplicationContext().getSharedPreferences("from_webpage", 0).edit();
            localEditor.putString("mid", this.val$id);
            localEditor.commit();
            Intent localIntent = IntentUtil.setWebPageIntent(KXNewsBarForPictureView.this.mContext, this.val$realUrl.toString(), this.val$title, null, -1);
            localIntent.putExtra("from", true);
            localIntent.putExtra("id", this.val$id);
            localIntent.putExtra("uid", this.val$uid);
            AnimationUtil.startFragment(KXNewsBarForPictureView.this.mFragment, localIntent, 1);
            UserHabitUtils.getInstance(KXNewsBarForPictureView.this.mContext).addUserHabit("Photo_Activity_Banner_Click");
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
      int i = -1 + this.curPosition;
      this.curPosition = i;
      if (i > 0)
        break label46;
    }
    label46: for (int j = -1 + this.mNewBarlist.size(); ; j = this.curPosition)
    {
      localKXViewPager.setCurrentItem(j);
      return;
    }
  }

  public void notifyDataSetChanged()
  {
    fillNewsBarList();
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
        Thread.sleep(1000 * this.sleepTime);
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
 * Qualified Name:     com.kaixin001.view.KXNewsBarForPictureView
 * JD-Core Version:    0.6.0
 */