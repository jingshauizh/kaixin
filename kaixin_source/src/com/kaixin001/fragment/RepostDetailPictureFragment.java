package com.kaixin001.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.ImageDownloader;
import com.kaixin001.view.KXDragImageView;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepostDetailPictureFragment extends BaseFragment
  implements View.OnClickListener, ViewPager.OnPageChangeListener
{
  private RepostDetailAdapter adapter;
  private Button downButton;
  private ViewPager pager;
  private int position;
  private TextView textView;
  private List<String> urls;

  private void downloadImage(String paramString)
  {
    new LoadPhotoTask(paramString, null).execute(new Void[0]);
  }

  private void initData()
  {
    Bundle localBundle = getArguments();
    this.position = localBundle.getInt("position");
    this.urls = ((List)localBundle.getSerializable("urls"));
  }

  private void initView()
  {
    this.pager = ((ViewPager)findViewById(2131363576));
    if (this.adapter == null)
      this.adapter = new RepostDetailAdapter();
    this.pager.setAdapter(this.adapter);
    this.pager.setCurrentItem(this.position);
    this.pager.setOnPageChangeListener(this);
    this.downButton = ((Button)findViewById(2131363577));
    this.downButton.setOnClickListener(this);
    this.textView = ((TextView)findViewById(2131363578));
    this.textView.setText(1 + this.position + "/" + this.urls.size());
  }

  private void savePicture()
  {
    String str1 = (String)this.urls.get(this.position);
    if (str1 != null)
      try
      {
        String str2 = FileUtil.savePicture(str1);
        if (str2 != null)
        {
          getActivity().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(str2))));
          showToast(2130838515, 2131427493);
          return;
        }
        showToast(-1, 2131427494);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        showToast(-1, 2131427494);
      }
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    return false;
  }

  public void loadImage(KXDragImageView paramKXDragImageView, String paramString)
  {
    Bitmap localBitmap1 = ImageCache.getInstance().createSafeImage(paramString);
    Bitmap localBitmap2 = null;
    if (localBitmap1 == null)
    {
      localBitmap2 = ImageCache.getInstance().createSafeImage(paramString);
      downloadImage(paramString);
    }
    if (localBitmap1 != null)
    {
      paramKXDragImageView.setImageBitmap(localBitmap1);
      paramKXDragImageView.setScaleType(ImageView.ScaleType.MATRIX);
      return;
    }
    if (localBitmap2 != null)
    {
      paramKXDragImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
      paramKXDragImageView.setImageBitmap(localBitmap2);
      return;
    }
    paramKXDragImageView.setImageResource(2130838766);
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131363577:
    }
    savePicture();
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903319, paramViewGroup, false);
  }

  public void onPageScrollStateChanged(int paramInt)
  {
  }

  public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
  {
  }

  public void onPageSelected(int paramInt)
  {
    this.position = paramInt;
    this.textView.setText(paramInt + 1 + "/" + this.urls.size());
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    initData();
    initView();
  }

  class LoadPhotoTask extends AsyncTask<Void, Void, Void>
  {
    private String url;

    private LoadPhotoTask(String arg2)
    {
      Object localObject;
      this.url = localObject;
    }

    protected Void doInBackground(Void[] paramArrayOfVoid)
    {
      ImageDownloader.getInstance().downloadImageSync(this.url);
      return null;
    }

    protected void onPostExecute(Void paramVoid)
    {
      super.onPostExecute(paramVoid);
      RepostDetailPictureFragment.this.adapter.notifyDataSetChanged();
    }
  }

  class RepostDetailAdapter extends PagerAdapter
  {
    private Map<Integer, View> map = new HashMap();

    RepostDetailAdapter()
    {
    }

    public void destroyItem(ViewGroup paramViewGroup, int paramInt, Object paramObject)
    {
      ((ViewPager)paramViewGroup).removeView((View)paramObject);
    }

    public int getCount()
    {
      return RepostDetailPictureFragment.this.urls.size();
    }

    public int getItemPosition(Object paramObject)
    {
      return -2;
    }

    public Object instantiateItem(ViewGroup paramViewGroup, int paramInt)
    {
      View localView;
      if (this.map.containsKey(Integer.valueOf(paramInt)))
        localView = (View)this.map.get(Integer.valueOf(paramInt));
      while (true)
      {
        KXDragImageView localKXDragImageView = (KXDragImageView)localView.findViewById(2131363969);
        RepostDetailPictureFragment.this.loadImage(localKXDragImageView, (String)RepostDetailPictureFragment.this.urls.get(paramInt));
        localKXDragImageView.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            RepostDetailPictureFragment.this.finish();
          }
        });
        ((ViewPager)paramViewGroup).addView(localView);
        return localView;
        localView = LayoutInflater.from(RepostDetailPictureFragment.this.getActivity()).inflate(2130903406, null);
        this.map.put(Integer.valueOf(paramInt), localView);
      }
    }

    public boolean isViewFromObject(View paramView, Object paramObject)
    {
      return paramView == paramObject;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.RepostDetailPictureFragment
 * JD-Core Version:    0.6.0
 */