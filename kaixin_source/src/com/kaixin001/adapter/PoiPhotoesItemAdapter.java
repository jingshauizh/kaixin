package com.kaixin001.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.kaixin001.businesslogic.ShowMenuDialog;
import com.kaixin001.engine.CheckinListEngine;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.item.KaixinPhotoItem;
import com.kaixin001.item.PoiItem;
import com.kaixin001.item.PoiPhotoesItem;
import com.kaixin001.model.KaixinModelTemplate;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.KXTextUtil;
import com.kaixin001.util.ParseNewsInfoUtil;
import com.kaixin001.util.ScreenUtil;
import com.kaixin001.view.KXIntroView;
import com.kaixin001.view.KXIntroView.OnClickLinkListener;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class PoiPhotoesItemAdapter extends BaseAdapter
{
  private static final String TAG = "PoiPhotoesItemAdapter";
  public PoiPhotoesItem currentCheckInPoiItem;
  private Activity mContext;
  private View mFooter;
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  private BaseFragment mFragment;
  ArrayList<PoiPhotoesItem> poiPhotoesItemList;

  public PoiPhotoesItemAdapter(BaseFragment paramBaseFragment, int paramInt, ArrayList<PoiPhotoesItem> paramArrayList)
  {
    this.poiPhotoesItemList = paramArrayList;
    this.mFragment = paramBaseFragment;
    this.mContext = paramBaseFragment.getActivity();
    this.mFooter = paramBaseFragment.getActivity().getLayoutInflater().inflate(2130903153, null);
    View localView = this.mFooter.findViewById(2131362071);
    if (localView != null)
      localView.setBackgroundResource(2130838795);
    this.mFooterTV = ((TextView)this.mFooter.findViewById(2131362073));
    this.mFooterTV.setText(2131427748);
    this.mFooterProBar = ((ProgressBar)this.mFooter.findViewById(2131362072));
  }

  public int getCount()
  {
    if (this.poiPhotoesItemList == null)
      return 0;
    return this.poiPhotoesItemList.size();
  }

  public Object getItem(int paramInt)
  {
    if (this.poiPhotoesItemList == null);
    do
      return null;
    while ((paramInt < 0) || (paramInt > this.poiPhotoesItemList.size()));
    return this.poiPhotoesItemList.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    PoiPhotoesItemViewTag localPoiPhotoesItemViewTag;
    int i;
    try
    {
      PoiPhotoesItem localPoiPhotoesItem = (PoiPhotoesItem)getItem(paramInt);
      if (localPoiPhotoesItem == null)
        return paramView;
      if (localPoiPhotoesItem.poi == null)
        return this.mFooter;
      if ((paramView == null) || (paramView == this.mFooter))
      {
        paramView = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903294, null);
        localPoiPhotoesItemViewTag = new PoiPhotoesItemViewTag(paramView, null);
        paramView.setTag(localPoiPhotoesItemViewTag);
      }
      while (true)
      {
        localPoiPhotoesItemViewTag.updateItem(localPoiPhotoesItem);
        i = getCount();
        if (i != 1)
          break;
        localPoiPhotoesItemViewTag.showSingleBkg();
        break label168;
        localPoiPhotoesItemViewTag = (PoiPhotoesItemViewTag)paramView.getTag();
      }
      if (paramInt == 0)
        localPoiPhotoesItemViewTag.showTopBkg();
    }
    catch (Exception localException)
    {
      KXLog.e("PoiPhotoesItemAdapter", "getView", localException);
    }
    if (paramInt == i - 1)
      localPoiPhotoesItemViewTag.showBottomBkg();
    else
      localPoiPhotoesItemViewTag.showMiddleBkg();
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

  public void updatePoiPhotoes(PoiPhotoesItem paramPoiPhotoesItem)
  {
    paramPoiPhotoesItem.currentSelection = -1;
    if (paramPoiPhotoesItem.photoList != null)
      paramPoiPhotoesItem.photoList.clearItemList();
    GetPhotoesTask localGetPhotoesTask = new GetPhotoesTask(null);
    String[] arrayOfString = new String[1];
    arrayOfString[0] = paramPoiPhotoesItem.poi.poiId;
    localGetPhotoesTask.execute(arrayOfString);
  }

  private class GetPhotoesTask extends AsyncTask<String, Void, Integer>
  {
    private GetPhotoesTask()
    {
    }

    protected Integer doInBackground(String[] paramArrayOfString)
    {
      String str = paramArrayOfString[0];
      return CheckinListEngine.getInstance().getPoiPhotoes(PoiPhotoesItemAdapter.this.mContext.getApplicationContext(), str, 0, 20);
    }

    protected void onPostExecute(Integer paramInteger)
    {
      PoiPhotoesItemAdapter.this.notifyDataSetChanged();
    }
  }

  private class PoiPhotoesItemViewTag
    implements View.OnClickListener
  {
    ImageAdapter adapter;
    Gallery galleryPhotoes;
    boolean isOnClickFirstItem = false;
    public PoiPhotoesItem item;
    private ImageView ivPoiName;
    private View mLayoutInfo = null;
    private View mView = null;
    private ShowMenuDialog menuUtil;
    ArrayList<KaixinPhotoItem> photoList = new ArrayList();
    private TextView tvDistance = null;
    private KXIntroView tvPoiName = null;

    private PoiPhotoesItemViewTag(View arg2)
    {
      Object localObject;
      this.mView = localObject;
      this.mLayoutInfo = localObject.findViewById(2131363454);
      this.ivPoiName = ((ImageView)localObject.findViewById(2131363456));
      this.tvPoiName = ((KXIntroView)localObject.findViewById(2131363457));
      this.tvPoiName.setOnClickLinkListener(new KXIntroView.OnClickLinkListener()
      {
        public void onClick(KXLinkInfo paramKXLinkInfo)
        {
          if (paramKXLinkInfo.isLbsPoi())
          {
            PoiItem localPoiItem = PoiPhotoesItemAdapter.PoiPhotoesItemViewTag.this.item.poi;
            IntentUtil.showLbsPositionDetailFragment(PoiPhotoesItemAdapter.this.mFragment, localPoiItem.poiId, localPoiItem.name, localPoiItem.location, localPoiItem.distance);
          }
        }
      });
      this.tvDistance = ((TextView)localObject.findViewById(2131363458));
      this.galleryPhotoes = ((Gallery)localObject.findViewById(2131363459));
      this.adapter = new ImageAdapter();
      this.galleryPhotoes.setAdapter(this.adapter);
      this.galleryPhotoes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
      {
        public void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
        {
          if ((paramInt == 0) && (PoiPhotoesItemAdapter.PoiPhotoesItemViewTag.this.isOnClickFirstItem))
          {
            PoiPhotoesItemAdapter.PoiPhotoesItemViewTag.this.galleryPhotoes.setSelection(1);
            PoiPhotoesItemAdapter.PoiPhotoesItemViewTag.this.isOnClickFirstItem = false;
            PoiPhotoesItemAdapter.PoiPhotoesItemViewTag.this.item.currentSelection = 1;
            return;
          }
          PoiPhotoesItemAdapter.PoiPhotoesItemViewTag.this.item.currentSelection = paramInt;
        }

        public void onNothingSelected(AdapterView<?> paramAdapterView)
        {
        }
      });
      this.galleryPhotoes.setOnItemClickListener(new AdapterView.OnItemClickListener()
      {
        public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
        {
          if ("-1".equals(((KaixinPhotoItem)PoiPhotoesItemAdapter.PoiPhotoesItemViewTag.this.adapter.getItem(paramInt)).pid))
          {
            PoiPhotoesItemAdapter.this.currentCheckInPoiItem = PoiPhotoesItemAdapter.PoiPhotoesItemViewTag.this.item;
            PoiPhotoesItemAdapter.PoiPhotoesItemViewTag.this.getShowMenuDialog().showPhotoMenuOfPositionMainActivity();
          }
          do
          {
            return;
            IntentUtil.showViewPhotoActivity4Poi(PoiPhotoesItemAdapter.this.mContext, PoiPhotoesItemAdapter.this.mFragment, paramInt, PoiPhotoesItemAdapter.PoiPhotoesItemViewTag.this.item.poi.poiId, PoiPhotoesItemAdapter.PoiPhotoesItemViewTag.this.item.poi.name, false);
          }
          while (paramInt != 0);
          PoiPhotoesItemAdapter.PoiPhotoesItemViewTag.this.isOnClickFirstItem = true;
        }
      });
    }

    private void setPaddingBottom(int paramInt)
    {
      int i = this.mView.getPaddingTop();
      int j = this.mView.getPaddingLeft();
      int k = this.mView.getPaddingRight();
      float f = paramInt * PoiPhotoesItemAdapter.this.mContext.getResources().getDisplayMetrics().density;
      this.mView.setPadding(j, i, k, (int)f);
    }

    private void setPaddingTop(int paramInt)
    {
      float f = paramInt * PoiPhotoesItemAdapter.this.mContext.getResources().getDisplayMetrics().density;
      int i = this.mView.getPaddingLeft();
      int j = this.mView.getPaddingRight();
      int k = this.mView.getPaddingBottom();
      this.mView.setPadding(i, (int)f, j, k);
    }

    public ShowMenuDialog getShowMenuDialog()
    {
      if (this.menuUtil == null)
        this.menuUtil = new ShowMenuDialog(PoiPhotoesItemAdapter.this.mFragment);
      return this.menuUtil;
    }

    public void onClick(View paramView)
    {
      if ((paramView.getId() == 2131363456) || (paramView.getId() == 2131363457))
      {
        PoiItem localPoiItem = this.item.poi;
        IntentUtil.showLbsPositionDetailFragment(PoiPhotoesItemAdapter.this.mFragment, localPoiItem.poiId, localPoiItem.name, localPoiItem.location, localPoiItem.distance);
      }
    }

    public void showBottomBkg()
    {
      this.mLayoutInfo.setBackgroundResource(2130837699);
      setPaddingTop(0);
      setPaddingBottom(10);
    }

    public void showMiddleBkg()
    {
      this.mLayoutInfo.setBackgroundResource(2130838495);
      setPaddingBottom(0);
      setPaddingTop(0);
    }

    public void showSingleBkg()
    {
      this.mLayoutInfo.setBackgroundResource(2130837701);
      setPaddingTop(10);
      setPaddingBottom(10);
    }

    public void showTopBkg()
    {
      this.mLayoutInfo.setBackgroundResource(2130838494);
      setPaddingTop(10);
      setPaddingBottom(0);
    }

    public void updateItem(PoiPhotoesItem paramPoiPhotoesItem)
      throws Exception
    {
      int i = 2;
      if (paramPoiPhotoesItem == null);
      while (true)
      {
        return;
        ArrayList localArrayList1 = ParseNewsInfoUtil.parseNewsLinkString(KXTextUtil.getLbsPoiText(paramPoiPhotoesItem.poi.name, paramPoiPhotoesItem.poi.poiId));
        this.tvPoiName.setTitleList(localArrayList1);
        this.ivPoiName.setOnClickListener(this);
        try
        {
          double d = Double.parseDouble(paramPoiPhotoesItem.poi.distance);
          this.tvDistance.setText(KXTextUtil.getLbsFormatDistance(d));
          this.item = paramPoiPhotoesItem;
          this.photoList.clear();
          localArrayList2 = paramPoiPhotoesItem.photoList.getItemList();
          if (localArrayList2 == null)
          {
            j = 0;
            if (j <= 0);
          }
        }
        catch (Exception localException)
        {
          while (true)
          {
            int j;
            int m;
            int k;
            try
            {
              ArrayList localArrayList2;
              paramPoiPhotoesItem.photoList.itemListLock.lock();
              m = 0;
              break label402;
              KaixinPhotoItem localKaixinPhotoItem = new KaixinPhotoItem();
              localKaixinPhotoItem.pid = "-1";
              this.photoList.add(localKaixinPhotoItem);
              if (this.photoList.size() != i)
                continue;
              this.photoList.add(new KaixinPhotoItem());
              paramPoiPhotoesItem.photoList.itemListLock.unlock();
              this.adapter.notifyDataSetChanged();
              k = this.adapter.getCount();
              if (k <= 0)
                break;
              if (paramPoiPhotoesItem.currentSelection != -1)
                break label334;
              if (ScreenUtil.getOrientation(PoiPhotoesItemAdapter.this.mContext) == 1)
              {
                this.galleryPhotoes.setSelection(1);
                paramPoiPhotoesItem.currentSelection = 1;
                return;
                localException = localException;
                this.tvDistance.setText("");
                continue;
                j = localArrayList2.size();
                continue;
                this.photoList.add((KaixinPhotoItem)localArrayList2.get(m));
                if (19 == m)
                  continue;
                m++;
              }
            }
            finally
            {
              paramPoiPhotoesItem.photoList.itemListLock.unlock();
            }
            if (k > i);
            while (true)
            {
              this.galleryPhotoes.setSelection(i);
              paramPoiPhotoesItem.currentSelection = i;
              return;
              i = 1;
            }
            label334: if (ScreenUtil.getOrientation(PoiPhotoesItemAdapter.this.mContext) == 1)
            {
              this.galleryPhotoes.setSelection(paramPoiPhotoesItem.currentSelection);
              return;
            }
            if (k > i);
            while (paramPoiPhotoesItem.currentSelection < i)
            {
              this.galleryPhotoes.setSelection(i);
              paramPoiPhotoesItem.currentSelection = i;
              return;
              i = 1;
            }
            this.galleryPhotoes.setSelection(i);
            return;
            label402: if (m < j)
              continue;
          }
        }
      }
    }

    public class ImageAdapter extends BaseAdapter
    {
      public ImageAdapter()
      {
      }

      public int getCount()
      {
        return PoiPhotoesItemAdapter.PoiPhotoesItemViewTag.this.photoList.size();
      }

      public Object getItem(int paramInt)
      {
        return PoiPhotoesItemAdapter.PoiPhotoesItemViewTag.this.photoList.get(paramInt);
      }

      public long getItemId(int paramInt)
      {
        return paramInt;
      }

      public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
      {
        KaixinPhotoItem localKaixinPhotoItem = (KaixinPhotoItem)PoiPhotoesItemAdapter.PoiPhotoesItemViewTag.this.photoList.get(paramInt);
        if (localKaixinPhotoItem == null)
          return null;
        if (paramView == null)
          paramView = ((LayoutInflater)PoiPhotoesItemAdapter.this.mContext.getSystemService("layout_inflater")).inflate(2130903070, null);
        ImageView localImageView = (ImageView)paramView.findViewById(2131362024);
        if (localKaixinPhotoItem.pid == null)
          paramView.setVisibility(4);
        while (true)
        {
          return paramView;
          if ("-1".equals(localKaixinPhotoItem.pid))
          {
            localImageView.setBackgroundResource(2130838499);
            continue;
          }
          PoiPhotoesItemAdapter.this.mFragment.displayPicture(localImageView, localKaixinPhotoItem.thumbnail, 2130838498);
        }
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.adapter.PoiPhotoesItemAdapter
 * JD-Core Version:    0.6.0
 */