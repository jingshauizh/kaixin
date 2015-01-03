package com.kaixin001.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.item.GiftItem;
import com.kaixin001.model.GiftListModel;
import com.kaixin001.task.GetGiftListTask;
import com.kaixin001.util.AnimationUtil;
import java.util.ArrayList;

public class SelectGiftFragment extends BaseFragment
{
  private static final int DIALOG_ID_GET_GIFTS = 1;
  public static final int GIFT_NUM_TOTAL = 80;
  public static final int PAGE_GIFT_NUM = 16;
  private static String giftId = "-1";
  private static ArrayList<View> mCacheItems = new ArrayList();
  private GetGiftListTask getGiftListTask;
  private ViewPager mGiftListViewPager;
  private ImageView pagerIndex;

  private void constructViews()
  {
    LinearLayout localLinearLayout1 = (LinearLayout)findViewById(2131363604);
    localLinearLayout1.removeAllViews();
    LinearLayout localLinearLayout2 = new LinearLayout(getActivity());
    TableLayout.LayoutParams localLayoutParams1 = new TableLayout.LayoutParams();
    localLayoutParams1.width = -2;
    localLayoutParams1.height = -2;
    localLinearLayout2.setLayoutParams(localLayoutParams1);
    int i = GiftListModel.getInstance().gifts.length / 16;
    if (GiftListModel.getInstance().gifts.length % 16 != 0)
      i++;
    int j = 0;
    if (j >= i)
    {
      localLinearLayout1.addView(localLinearLayout2);
      return;
    }
    ImageView localImageView = new ImageView(getActivity());
    if (this.mGiftListViewPager.getCurrentItem() == j)
      localImageView.setBackgroundResource(2130838088);
    while (true)
    {
      if (j != 0)
      {
        TableLayout.LayoutParams localLayoutParams2 = new TableLayout.LayoutParams();
        localLayoutParams2.setMargins(10, 0, 0, 0);
        localImageView.setLayoutParams(localLayoutParams2);
      }
      localLinearLayout2.addView(localImageView);
      j++;
      break;
      localImageView.setBackgroundResource(2130838087);
    }
  }

  private void intViewPager()
  {
    ArrayList localArrayList1 = new ArrayList();
    int i = 0;
    GiftItem[] arrayOfGiftItem = GiftListModel.getInstance().gifts;
    int j = arrayOfGiftItem.length;
    if (i >= j)
    {
      GiftListViewPagerAdapter localGiftListViewPagerAdapter = new GiftListViewPagerAdapter(getActivity(), localArrayList1);
      this.mGiftListViewPager.setAdapter(localGiftListViewPagerAdapter);
      return;
    }
    int k;
    label64: ArrayList localArrayList2;
    if (i + 16 < j)
    {
      k = 16;
      localArrayList2 = new ArrayList();
    }
    for (int m = 0; ; m++)
    {
      if (m >= k)
      {
        GiftListPager localGiftListPager = new GiftListPager(this, localArrayList2);
        localGiftListPager.setIndex(localArrayList1.size());
        localArrayList1.add(localGiftListPager);
        i += 16;
        break;
        k = j - i;
        break label64;
      }
      localArrayList2.add(arrayOfGiftItem[(i + m)]);
    }
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 2);
  }

  protected boolean handleMessage(Message paramMessage)
  {
    if (getActivity().isFinishing())
      return true;
    if (paramMessage == null)
      return false;
    if (paramMessage.what == 222)
    {
      dismissDialog(1);
      if (paramMessage.arg1 == 1)
      {
        intViewPager();
        constructViews();
        return true;
      }
      Toast.makeText(getActivity(), 2131427874, 0).show();
      finish();
      return true;
    }
    return super.handleMessage(paramMessage);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  protected Dialog onCreateDialog(int paramInt)
  {
    if (paramInt == 1)
      return ProgressDialog.show(getActivity(), "", getResources().getText(2131427869), true, true, new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramDialogInterface)
        {
          SelectGiftFragment.this.dismissDialog(1);
          SelectGiftFragment.this.getGiftListTask.cancel(true);
          SelectGiftFragment.this.finish();
        }
      });
    return super.onCreateDialog(paramInt);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903329, paramViewGroup, false);
  }

  public void onDestroy()
  {
    super.onDestroy();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mGiftListViewPager = ((ViewPager)findViewById(2131363603));
    setTitleBar();
    enableSlideBack(false);
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      String str = localBundle.getString("giftId");
      if (str != null)
        giftId = str;
    }
    while (true)
    {
      this.mGiftListViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
      {
        public void onPageScrollStateChanged(int paramInt)
        {
        }

        public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
        {
        }

        public void onPageSelected(int paramInt)
        {
          SelectGiftFragment.this.constructViews();
          SelectGiftFragment localSelectGiftFragment = SelectGiftFragment.this;
          if (paramInt == 0);
          for (boolean bool = true; ; bool = false)
          {
            localSelectGiftFragment.enableSlideBack(bool);
            return;
          }
        }
      });
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = this.mHandler;
      arrayOfObject[1] = String.valueOf(80);
      this.getGiftListTask = new GetGiftListTask(getActivity());
      this.getGiftListTask.execute(arrayOfObject);
      showDialog(1);
      return;
      giftId = "-1";
      continue;
      giftId = "-1";
    }
  }

  protected void setTitleBar()
  {
    ((RelativeLayout)findViewById(2131361802)).setVisibility(0);
    ((ImageView)findViewById(2131362914)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        SelectGiftFragment.this.finish();
      }
    });
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setText(2131428448);
    localTextView.setVisibility(0);
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    ImageView localImageView = (ImageView)findViewById(2131362928);
    localImageView.setImageResource(2130839022);
    localImageView.setVisibility(8);
  }

  public static class GiftListPager extends RelativeLayout
    implements AdapterView.OnItemClickListener
  {
    public static final int FACE_DELETE = -1;
    public static final int FACE_NONE = -2;
    private Activity activity;
    private LayoutInflater inflater;
    private GiftListPagerAdapter mAdapter;
    private BaseFragment mFragment;
    private ArrayList<GiftItem> mGiftDataList;
    private GridView mGridView;
    private int nIndex;

    public GiftListPager(BaseFragment paramBaseFragment, ArrayList<GiftItem> paramArrayList)
    {
      super();
      this.mFragment = paramBaseFragment;
      this.activity = paramBaseFragment.getActivity();
      this.inflater = LayoutInflater.from(this.activity);
      this.mGiftDataList = paramArrayList;
      initView(this.activity);
    }

    private void initView(Context paramContext)
    {
      View localView = ((LayoutInflater)paramContext.getSystemService("layout_inflater")).inflate(2130903140, null);
      addView(localView);
      this.mGridView = ((GridView)localView.findViewById(2131362484));
      this.mGridView.setOnItemClickListener(this);
      this.mAdapter = new GiftListPagerAdapter();
      this.mGridView.setAdapter(this.mAdapter);
    }

    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
    {
      Intent localIntent = new Intent();
      Bundle localBundle = new Bundle();
      localBundle.putString("giftId", ((GiftItem)this.mGiftDataList.get(paramInt)).gid);
      localBundle.putString("defaultps", ((GiftItem)this.mGiftDataList.get(paramInt)).defaultPs);
      localIntent.putExtras(localBundle);
      if (((this.mFragment instanceof GiftNewsFragment)) || (SelectGiftFragment.giftId.equals("-1")))
      {
        localIntent.setClass(this.activity, SendGiftFragment.class);
        AnimationUtil.startFragment(this.mFragment, localIntent, 1);
        return;
      }
      this.mFragment.setResult(-1, localIntent);
      this.mFragment.finish();
    }

    public void setIndex(int paramInt)
    {
      this.nIndex = paramInt;
    }

    class GiftListPagerAdapter extends BaseAdapter
    {
      GiftListPagerAdapter()
      {
      }

      public int getCount()
      {
        if (SelectGiftFragment.GiftListPager.this.mGiftDataList == null)
          return 0;
        return SelectGiftFragment.GiftListPager.this.mGiftDataList.size();
      }

      public Object getItem(int paramInt)
      {
        return Integer.valueOf(paramInt);
      }

      public long getItemId(int paramInt)
      {
        return paramInt;
      }

      public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
      {
        SelectGiftFragment.GiftListPager.ViewHolder localViewHolder;
        if (paramView == null)
        {
          paramView = SelectGiftFragment.GiftListPager.this.inflater.inflate(2130903141, null);
          localViewHolder = new SelectGiftFragment.GiftListPager.ViewHolder(SelectGiftFragment.GiftListPager.this);
          localViewHolder.title = ((TextView)paramView.findViewById(2131362487));
          localViewHolder.image = ((ImageView)paramView.findViewById(2131362486));
          paramView.setTag(localViewHolder);
        }
        while (true)
        {
          if (paramInt < SelectGiftFragment.GiftListPager.this.mGiftDataList.size())
          {
            localViewHolder.title.setText(((GiftItem)SelectGiftFragment.GiftListPager.this.mGiftDataList.get(paramInt)).gname);
            SelectGiftFragment.GiftListPager.this.mFragment.displayPicture(localViewHolder.image, ((GiftItem)SelectGiftFragment.GiftListPager.this.mGiftDataList.get(paramInt)).pic, 0);
          }
          return paramView;
          localViewHolder = (SelectGiftFragment.GiftListPager.ViewHolder)paramView.getTag();
        }
      }
    }

    class ViewHolder
    {
      public ImageView image;
      public TextView title;

      ViewHolder()
      {
      }
    }
  }

  public static class GiftListViewPagerAdapter extends PagerAdapter
  {
    private Context mContext;
    private ArrayList<View> mViews;

    GiftListViewPagerAdapter(Context paramContext, ArrayList<View> paramArrayList)
    {
      this.mContext = paramContext;
      this.mViews = paramArrayList;
    }

    private View popCacheItem()
    {
      synchronized (SelectGiftFragment.mCacheItems)
      {
        int i = SelectGiftFragment.mCacheItems.size();
        View localView = null;
        if (i > 0)
        {
          localView = (View)SelectGiftFragment.mCacheItems.get(0);
          SelectGiftFragment.mCacheItems.remove(0);
        }
        return localView;
      }
    }

    private void pushCacheItem(View paramView)
    {
      if (paramView == null)
        return;
      synchronized (SelectGiftFragment.mCacheItems)
      {
        SelectGiftFragment.mCacheItems.add(paramView);
        return;
      }
    }

    public void destroyItem(View paramView, int paramInt, Object paramObject)
    {
      try
      {
        ((ViewPager)paramView).removeView((View)this.mViews.get(paramInt));
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }

    public void finishUpdate(View paramView)
    {
    }

    public int getCount()
    {
      return this.mViews.size();
    }

    public int getItemPosition(Object paramObject)
    {
      return -2;
    }

    public void initWithData(Context paramContext, ArrayList<View> paramArrayList)
    {
      this.mContext = null;
      this.mContext = paramContext;
      if (this.mViews != null)
      {
        this.mViews.clear();
        this.mViews = null;
      }
      this.mViews = paramArrayList;
    }

    public Object instantiateItem(View paramView, int paramInt)
    {
      try
      {
        ((ViewPager)paramView).addView((View)this.mViews.get(paramInt), 0);
        return this.mViews.get(paramInt);
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }

    public boolean isViewFromObject(View paramView, Object paramObject)
    {
      return paramView == paramObject;
    }

    public void restoreState(Parcelable paramParcelable, ClassLoader paramClassLoader)
    {
    }

    public Parcelable saveState()
    {
      return null;
    }

    public void startUpdate(View paramView)
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.SelectGiftFragment
 * JD-Core Version:    0.6.0
 */