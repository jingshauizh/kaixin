package com.kaixin001.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.AsyncLocalImageLoader;
import com.kaixin001.item.LocalPhotoItem;
import com.kaixin001.model.EditPictureModel;
import com.kaixin001.model.PhotoSelectModel;
import com.kaixin001.model.PhotoSelectModel.PhotoSelectChangedListener;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.view.KXFrameImageView;
import com.kaixin001.view.KXHorizontalScrollView;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class MultiPicSelectorFragment extends BaseFragment
  implements AdapterView.OnItemClickListener, View.OnClickListener, PhotoSelectModel.PhotoSelectChangedListener
{
  public static final String EXTRA_BUCKET_ID = "bucket_id";
  public static final String EXTRA_DISPLAY_NAME = "display_name";
  public static final String EXTRA_SELECTION = "selection";
  public static final String EXTRA_SELECTION_NEW = "selection";
  private static final String[] IMAGE_PROJECT = { "_id", "_data", "_display_name", "mini_thumb_magic" };
  private static final String ORDER_BY = "date_modified DESC";
  private static final String SELECTION = "bucket_id = ?";
  private ImageAdapter mAdapter;
  private AsyncLocalImageLoader mAsyncLoader;
  private View mBtnConfirm;
  private TextView mBtnConfirmPicNum;
  private String mBucketId;
  private Cursor mCursor;
  private GridView mGridView;
  private HashMap<View, String> mItemViews = new HashMap();
  private View mLayoutBottom;
  private View mLayoutLoading;
  private final ArrayList<Integer> mListSelection = new ArrayList();
  private SelectImageAsyncTask mLoadTask = null;
  private KXHorizontalScrollView mScrollView;
  private boolean mSingle = false;
  private long picSize;

  private void AddSelectedImg(int paramInt)
  {
    this.mCursor.moveToPosition(paramInt);
    int i = this.mCursor.getColumnIndex("_data");
    String str1 = this.mCursor.getString(i);
    int j = this.mCursor.getColumnIndex("_id");
    String str2 = this.mCursor.getString(j);
    if (!TextUtils.isEmpty(str2))
    {
      PhotoSelectModel.getInstance().removePhotoById(str2);
      PhotoSelectModel.getInstance().addPhoto(str2, str1);
    }
  }

  private void Inverse()
  {
    int i;
    if ((this.mCursor == null) || (this.mCursor.isClosed()))
      i = 0;
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        this.picSize = 0L;
        onSelectionChanged(true);
        return;
        i = this.mCursor.getCount();
        break;
      }
      Integer localInteger = Integer.valueOf(j);
      if (!this.mListSelection.contains(localInteger))
        continue;
      this.mListSelection.remove(localInteger);
    }
  }

  private void cancelTask()
  {
    if ((this.mLoadTask != null) && (this.mLoadTask.getStatus() != AsyncTask.Status.FINISHED) && (this.mLoadTask.isCancelled()))
    {
      this.mLoadTask.cancel(true);
      this.mLoadTask = null;
    }
  }

  private void clearSelection()
  {
    this.mListSelection.clear();
    onSelectionChanged(false);
  }

  private void closeCursor()
  {
    if (this.mCursor != null)
    {
      this.mCursor.close();
      this.mCursor = null;
    }
  }

  private void confirmPhotos()
  {
    ArrayList localArrayList = PhotoSelectModel.getInstance().getSelectPhotoList();
    if ((localArrayList == null) || (localArrayList.size() == 0))
      Toast.makeText(getActivity(), "请选择照片", 1).show();
    if (this.mSingle)
    {
      Intent localIntent = new Intent();
      localIntent.putExtra("filename", ((LocalPhotoItem)localArrayList.get(0)).mPath);
      setResult(-1, localIntent);
      finish();
      return;
    }
    setResult(-1);
    finish();
  }

  private void constructBottomViews()
  {
    ArrayList localArrayList = PhotoSelectModel.getInstance().getSelectPhotoList();
    String str1 = getResources().getString(2131428567);
    String str2;
    LinearLayout localLinearLayout1;
    int i;
    int j;
    int k;
    if (localArrayList.size() < 10)
    {
      str2 = "0" + String.valueOf(localArrayList.size());
      String str3 = str1.replace("*", str2);
      this.mBtnConfirmPicNum.setText(str3);
      localLinearLayout1 = (LinearLayout)findViewById(2131363370);
      i = localLinearLayout1.getChildCount();
      j = localArrayList.size();
      k = 0;
      if (k < j)
        break label124;
    }
    while (true)
    {
      if (i <= j)
      {
        return;
        str2 = String.valueOf(localArrayList.size());
        break;
        label124: LocalPhotoItem localLocalPhotoItem = (LocalPhotoItem)localArrayList.get(k);
        label135: int m = 0;
        if (k >= i)
          label145: if (m == 0)
            break label252;
        while (true)
        {
          k++;
          break;
          View localView = localLinearLayout1.getChildAt(k);
          if ((localView != null) && (localView.getTag() != null) && (localLocalPhotoItem.mId.equals((String)localView.getTag())))
          {
            m = 1;
            if (k < j - 1)
            {
              localView.setPadding(0, 0, dipToPx(8.0F), 0);
              break label145;
            }
            localView.setPadding(0, 0, 0, 0);
            break label145;
          }
          localLinearLayout1.removeViewAt(k);
          i--;
          break label135;
          label252: LinearLayout localLinearLayout2 = new LinearLayout(getActivity());
          TableLayout.LayoutParams localLayoutParams1 = new TableLayout.LayoutParams();
          localLayoutParams1.width = -2;
          localLayoutParams1.height = -2;
          localLinearLayout2.setLayoutParams(localLayoutParams1);
          if (k < j - 1)
            localLinearLayout2.setPadding(0, 0, dipToPx(8.0F), 0);
          while (true)
          {
            KXFrameImageView localKXFrameImageView = new KXFrameImageView(getActivity());
            localKXFrameImageView.setFrameResId(2130838245);
            TableLayout.LayoutParams localLayoutParams2 = new TableLayout.LayoutParams();
            localLayoutParams2.width = dipToPx(50.0F);
            localLayoutParams2.height = dipToPx(50.0F);
            localKXFrameImageView.setLayoutParams(localLayoutParams2);
            this.mAsyncLoader.showImage(localKXFrameImageView, localLocalPhotoItem.mId, 0);
            3 local3 = new View.OnClickListener(localLocalPhotoItem)
            {
              public void onClick(View paramView)
              {
                View localView = MultiPicSelectorFragment.this.getItemView(this.val$item.mId);
                if ((localView != null) && (localView.getTag() != null))
                {
                  MultiPicSelectorFragment.ImageItemTag localImageItemTag = (MultiPicSelectorFragment.ImageItemTag)localView.getTag();
                  if (localImageItemTag != null)
                  {
                    MultiPicSelectorFragment.this.removeListSelection(this.val$item.mId);
                    MultiPicSelectorFragment.this.removeSelectedImg(this.val$item.mId);
                    localImageItemTag.imageView.setCenterIndicateBackground(null);
                    localImageItemTag.imageView.setCenterIndicateIcon(0);
                    MultiPicSelectorFragment.this.onSelectionChanged(false);
                  }
                }
              }
            };
            localKXFrameImageView.setOnClickListener(local3);
            localLinearLayout2.addView(localKXFrameImageView);
            localLinearLayout2.setTag(localLocalPhotoItem.mId);
            if (i - 1 <= k)
              break label471;
            localLinearLayout1.removeViewAt(k);
            localLinearLayout1.addView(localLinearLayout2, k);
            break;
            localLinearLayout2.setPadding(0, 0, 0, 0);
          }
          label471: localLinearLayout1.addView(localLinearLayout2);
        }
      }
      localLinearLayout1.removeViewAt(i - 1);
      i--;
    }
  }

  private View getItemView(String paramString)
  {
    try
    {
      synchronized (this.mItemViews)
      {
        Iterator localIterator = this.mItemViews.entrySet().iterator();
        Map.Entry localEntry;
        do
        {
          boolean bool = localIterator.hasNext();
          if (!bool)
            return null;
          localEntry = (Map.Entry)localIterator.next();
        }
        while (!((String)localEntry.getValue()).equals(paramString));
        View localView = (View)localEntry.getKey();
        return localView;
      }
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  private void itemViewsUpdate(View paramView, String paramString)
  {
    while (true)
    {
      Iterator localIterator2;
      synchronized (this.mItemViews)
      {
        try
        {
          Iterator localIterator1 = this.mItemViews.entrySet().iterator();
          ArrayList localArrayList = new ArrayList();
          if (localIterator1.hasNext())
            continue;
          localIterator2 = localArrayList.iterator();
          if (!localIterator2.hasNext())
          {
            this.mItemViews.put(paramView, paramString);
            return;
            Map.Entry localEntry = (Map.Entry)localIterator1.next();
            if (!((String)localEntry.getValue()).equals(paramString))
              continue;
            localArrayList.add((View)localEntry.getKey());
            continue;
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          continue;
        }
      }
      View localView = (View)localIterator2.next();
      this.mItemViews.remove(localView);
    }
  }

  private void onSelectionChanged(boolean paramBoolean)
  {
    int i = this.mListSelection.size();
    if ((paramBoolean) && (this.mAdapter != null))
      this.mAdapter.notifyDataSetChanged();
    setNumText(i);
    if (PhotoSelectModel.getInstance().getSelectPhotoList().size() > 0)
    {
      this.mBtnConfirm.setEnabled(true);
      findViewById(2131363370).setVisibility(0);
    }
    while (true)
    {
      if (this.mScrollView == null)
        this.mScrollView = ((KXHorizontalScrollView)findViewById(2131363369));
      scrollToEnd();
      return;
      this.mBtnConfirm.setEnabled(false);
      findViewById(2131363370).setVisibility(8);
    }
  }

  private void removeListSelection(String paramString)
  {
    Iterator localIterator = this.mListSelection.iterator();
    Integer localInteger;
    String str;
    do
    {
      if (!localIterator.hasNext())
        return;
      localInteger = (Integer)localIterator.next();
      this.mCursor.moveToPosition(localInteger.intValue());
      int i = this.mCursor.getColumnIndex("_id");
      str = this.mCursor.getString(i);
    }
    while ((str == null) || (!str.equals(paramString)));
    this.mListSelection.remove(localInteger);
  }

  private void removeSelectedImg(int paramInt)
  {
    this.mCursor.moveToPosition(paramInt);
    int i = this.mCursor.getColumnIndex("_id");
    String str = this.mCursor.getString(i);
    if (!TextUtils.isEmpty(str))
      PhotoSelectModel.getInstance().removePhotoById(str);
  }

  private void removeSelectedImg(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
      PhotoSelectModel.getInstance().removePhotoById(paramString);
  }

  private void scrollToEnd()
  {
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        try
        {
          Thread.sleep(10L);
          int i = ((LinearLayout)MultiPicSelectorFragment.this.mScrollView.getChildAt(0)).getMeasuredWidth() - MultiPicSelectorFragment.this.mScrollView.getWidth();
          if (i < 0)
            i = 0;
          MultiPicSelectorFragment.this.mScrollView.scrollTo(i, 0);
          return;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    });
  }

  private void selectAll()
  {
    if ((this.mCursor == null) || (this.mCursor.isClosed()))
      return;
    int i = Math.min(this.mCursor.getCount(), 20);
    Iterator localIterator = this.mListSelection.iterator();
    if (!localIterator.hasNext())
    {
      Math.min(i, 20 - PhotoSelectModel.getInstance().getSelectPhotoList().size());
      this.mListSelection.clear();
    }
    for (int j = 0; ; j++)
    {
      if ((PhotoSelectModel.getInstance().getSelectPhotoList().size() >= 20) || (j >= this.mCursor.getCount()))
      {
        if (this.mCursor.getCount() > 20)
          Toast.makeText(getActivity(), 2131428377, 0).show();
        this.picSize = 0L;
        onSelectionChanged(true);
        return;
        removeSelectedImg(((Integer)localIterator.next()).intValue());
        break;
      }
      this.mListSelection.add(Integer.valueOf(j));
      AddSelectedImg(j);
    }
  }

  private void setNumText(int paramInt)
  {
    constructBottomViews();
  }

  private void setTitleBar()
  {
    ImageView localImageView = (ImageView)findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        MultiPicSelectorFragment.this.setResult(0);
        MultiPicSelectorFragment.this.finish();
      }
    });
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setText(2131428566);
    localTextView.setVisibility(0);
    ((ImageView)findViewById(2131362928)).setVisibility(8);
    ((TextView)findViewById(2131362930)).setVisibility(8);
  }

  private void showEmpty()
  {
    if (this.mLayoutLoading != null)
    {
      ((ProgressBar)findViewById(2131363103)).setVisibility(8);
      ((TextView)findViewById(2131363104)).setText(2131428372);
    }
    if (this.mGridView != null)
      this.mGridView.setVisibility(8);
    if (this.mLayoutBottom != null)
      this.mLayoutBottom.setVisibility(8);
  }

  private void showImages()
  {
    if (this.mLayoutLoading != null)
      this.mLayoutLoading.setVisibility(8);
    if (this.mGridView != null)
    {
      this.mAdapter = new ImageAdapter(null);
      this.mGridView.setAdapter(this.mAdapter);
      clearSelection();
      this.mGridView.setVisibility(0);
    }
    if (this.mLayoutBottom != null)
      this.mLayoutBottom.setVisibility(0);
  }

  private void showLoading()
  {
    if (this.mLayoutLoading != null)
    {
      ((ProgressBar)findViewById(2131363103)).setVisibility(0);
      ((TextView)findViewById(2131363104)).setText(2131427380);
    }
    if (this.mGridView != null)
      this.mGridView.setVisibility(8);
    if (this.mLayoutBottom != null)
      this.mLayoutBottom.setVisibility(8);
  }

  protected void dealPhotoResult(String paramString1, String paramString2)
  {
    try
    {
      if (!new File(paramString1).exists())
      {
        Toast.makeText(getActivity(), 2131427841, 0).show();
        return;
      }
      Bundle localBundle = new Bundle();
      localBundle.putString("filePathName", paramString1);
      localBundle.putString("fileFrom", paramString2);
      IntentUtil.launchEditPhotoForResult(getActivity(), this, 104, localBundle);
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("KaixinDesktop", "onActivityResult", localException);
    }
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 2);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 == 2)
      if (paramIntent != null)
      {
        EditPictureModel.setPicFrom("display_name");
        paramIntent.setClass(getActivity(), UploadPhotoFragment.class);
        startActivityForResult(paramIntent, 3);
      }
    while (true)
    {
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
      return;
      finish();
      continue;
      if (paramInt2 != 4)
        continue;
      finish();
    }
  }

  public void onClick(View paramView)
  {
    if (paramView == this.mBtnConfirm)
      confirmPhotos();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903233, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if (this.mGridView != null)
      this.mGridView.setAdapter(null);
    cancelTask();
    closeCursor();
    this.mAsyncLoader.stop();
    PhotoSelectModel.getInstance().removePhotoSelectChangedListener(this);
    super.onDestroy();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    ImageItemTag localImageItemTag = (ImageItemTag)paramView.getTag();
    Integer localInteger = Integer.valueOf(paramInt);
    if (!FileUtil.isPathExist(localImageItemTag.imageSrc))
    {
      Toast.makeText(getActivity(), "文件已删除", 0).show();
      return;
    }
    if (this.mListSelection.contains(localInteger))
    {
      this.mListSelection.remove(localInteger);
      removeSelectedImg(paramInt);
      localImageItemTag.imageView.setCenterIndicateBackground(null);
      localImageItemTag.imageView.setCenterIndicateIcon(0);
      File localFile2 = new File(localImageItemTag.imageSrc);
      if (localFile2.exists())
        this.picSize -= localFile2.length();
    }
    while (true)
    {
      onSelectionChanged(false);
      return;
      if (PhotoSelectModel.getInstance().getSelectPhotoList().size() < 20)
      {
        this.mListSelection.add(localInteger);
        AddSelectedImg(paramInt);
        localImageItemTag.imageView.setCenterIndicateBackground(getResources().getDrawable(2130839391));
        localImageItemTag.imageView.setCenterIndicateIcon(2130838935);
        File localFile1 = new File(localImageItemTag.imageSrc);
        if (!localFile1.exists())
          continue;
        this.picSize += localFile1.length();
        continue;
      }
      Toast.makeText(getActivity(), 2131428377, 0).show();
    }
  }

  public void onPhotoSelectChanged(int paramInt)
  {
    if ((this.mSingle) && (paramInt == 1))
      confirmPhotos();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mAsyncLoader = new AsyncLocalImageLoader(getActivity());
    Bundle localBundle = getArguments();
    if (localBundle == null)
    {
      finish();
      return;
    }
    this.mSingle = localBundle.getBoolean("single");
    this.mBucketId = localBundle.getString("bucket_id");
    if (TextUtils.isEmpty(this.mBucketId))
    {
      finish();
      return;
    }
    setTitleBar();
    this.mGridView = ((GridView)findViewById(2131363101));
    this.mGridView.setOnItemClickListener(this);
    ViewGroup.LayoutParams localLayoutParams = this.mGridView.getLayoutParams();
    if (localLayoutParams != null)
    {
      DisplayMetrics localDisplayMetrics = getResources().getDisplayMetrics();
      int i = (localDisplayMetrics.widthPixels - dipToPx(40.0F)) / 4;
      this.mGridView.setColumnWidth(i);
      this.mGridView.setHorizontalSpacing(dipToPx(8.0F));
      localLayoutParams.width = (localDisplayMetrics.widthPixels - dipToPx(16.0F));
      this.mGridView.setLayoutParams(localLayoutParams);
    }
    this.mLayoutLoading = findViewById(2131363102);
    this.mLayoutBottom = findViewById(2131362013);
    this.mBtnConfirm = findViewById(2131363371);
    this.mBtnConfirm.setOnClickListener(this);
    this.mBtnConfirmPicNum = ((TextView)findViewById(2131363372));
    PhotoSelectModel.getInstance().addPhotoSelectChangedListener(this);
    this.mLoadTask = new SelectImageAsyncTask(null);
    SelectImageAsyncTask localSelectImageAsyncTask = this.mLoadTask;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = this.mBucketId;
    localSelectImageAsyncTask.execute(arrayOfString);
  }

  private class ImageAdapter extends BaseAdapter
  {
    private ImageAdapter()
    {
    }

    public int getCount()
    {
      if ((MultiPicSelectorFragment.this.mCursor == null) || (MultiPicSelectorFragment.this.mCursor.isClosed()))
        return 0;
      return MultiPicSelectorFragment.this.mCursor.getCount();
    }

    public Object getItem(int paramInt)
    {
      return null;
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      MultiPicSelectorFragment.ImageItemTag localImageItemTag;
      if (paramView == null)
      {
        paramView = MultiPicSelectorFragment.this.getActivity().getLayoutInflater().inflate(2130903234, null);
        localImageItemTag = new MultiPicSelectorFragment.ImageItemTag(paramView);
        ViewGroup.LayoutParams localLayoutParams = localImageItemTag.imageView.getLayoutParams();
        if (localLayoutParams != null)
        {
          int i = (MultiPicSelectorFragment.this.getResources().getDisplayMetrics().widthPixels - MultiPicSelectorFragment.this.dipToPx(40.0F)) / 4;
          localLayoutParams.width = i;
          localLayoutParams.height = i;
          localImageItemTag.imageView.setLayoutParams(localLayoutParams);
        }
        paramView.setTag(localImageItemTag);
        if (paramInt >= 4)
          break label333;
        paramView.setPadding(0, MultiPicSelectorFragment.this.dipToPx(10.0F), 0, 0);
        label124: localImageItemTag.imageView.setImageDrawable(null);
        if (MultiPicSelectorFragment.this.mCursor.moveToPosition(paramInt))
        {
          localImageItemTag.imageSrc = MultiPicSelectorFragment.this.mCursor.getString(1);
          if (!FileUtil.isPathExist(localImageItemTag.imageSrc))
            break label344;
          MultiPicSelectorFragment.this.mAsyncLoader.showImage(localImageItemTag.imageView, MultiPicSelectorFragment.this.mCursor.getString(0), 0);
        }
      }
      while (true)
      {
        MultiPicSelectorFragment.this.itemViewsUpdate(paramView, MultiPicSelectorFragment.this.mCursor.getString(0));
        Integer.valueOf(paramInt);
        if (!PhotoSelectModel.getInstance().isSelect(MultiPicSelectorFragment.this.mCursor.getString(0)))
          break label356;
        Integer localInteger = Integer.valueOf(paramInt);
        if (!MultiPicSelectorFragment.this.mListSelection.contains(localInteger))
          MultiPicSelectorFragment.this.mListSelection.add(localInteger);
        localImageItemTag.imageView.setCenterIndicateBackground(MultiPicSelectorFragment.this.getResources().getDrawable(2130839391));
        localImageItemTag.imageView.setCenterIndicateIcon(2130838935);
        return paramView;
        localImageItemTag = (MultiPicSelectorFragment.ImageItemTag)paramView.getTag();
        break;
        label333: paramView.setPadding(0, 0, 0, 0);
        break label124;
        label344: localImageItemTag.imageView.setImageBitmap(null);
      }
      label356: localImageItemTag.imageView.setCenterIndicateBackground(null);
      localImageItemTag.imageView.setCenterIndicateIcon(0);
      return paramView;
    }
  }

  private static class ImageItemTag
  {
    public String imageSrc;
    public KXFrameImageView imageView;
    public View layout;

    public ImageItemTag(View paramView)
    {
      this.layout = paramView;
      this.imageView = ((KXFrameImageView)paramView.findViewById(2131363105));
      this.imageView.setFrameResId(2130838245);
    }
  }

  private class SelectImageAsyncTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private SelectImageAsyncTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
        return null;
      MultiPicSelectorFragment.this.closeCursor();
      ContentResolver localContentResolver = MultiPicSelectorFragment.this.getActivity().getContentResolver();
      MultiPicSelectorFragment.this.mCursor = localContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MultiPicSelectorFragment.IMAGE_PROJECT, "bucket_id = ?", paramArrayOfString, "date_modified DESC");
      if (MultiPicSelectorFragment.this.mCursor == null)
        return null;
      return Integer.valueOf(MultiPicSelectorFragment.this.mCursor.getCount());
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if (paramInteger == null)
      {
        MultiPicSelectorFragment.this.showEmpty();
        return;
      }
      if (paramInteger.intValue() > 0)
      {
        MultiPicSelectorFragment.this.showImages();
        return;
      }
      MultiPicSelectorFragment.this.showEmpty();
    }

    protected void onPreExecuteA()
    {
      MultiPicSelectorFragment.this.showLoading();
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.MultiPicSelectorFragment
 * JD-Core Version:    0.6.0
 */