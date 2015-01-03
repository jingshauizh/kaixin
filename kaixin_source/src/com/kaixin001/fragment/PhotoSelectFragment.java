package com.kaixin001.fragment;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.AsyncLocalImageLoader;
import com.kaixin001.item.CloudAlbumPicItem;
import com.kaixin001.item.LocalPhotoItem;
import com.kaixin001.model.EditPictureModel;
import com.kaixin001.model.PhotoSelectModel;
import com.kaixin001.model.PhotoSelectModel.PhotoSelectChangedListener;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.CloudAlbumManager;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.view.KXFrameImageView;
import com.kaixin001.view.KXHorizontalScrollView;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class PhotoSelectFragment extends BaseFragment
  implements View.OnClickListener, AdapterView.OnItemClickListener, PhotoSelectModel.PhotoSelectChangedListener, DialogInterface.OnClickListener
{
  public static final int BULK_UPLOAD_BACK = 4;
  public static final int BULK_UPLOAD_PHOTO = 1;
  public static final int BULK_UPLOAD_PHOTO_EFFECTS = 2;
  public static final int BULK_UPLOAD_PHOTO_INTO = 3;
  public static final String CALLS_COUNT = "calls_count";
  public static final int MAX_SELECTION = 20;
  private static final String mHeadViewTag = "headview";
  private AlbumAdapter mAdapter;
  private AsyncLocalImageLoader mAsyncLoader = new AsyncLocalImageLoader(getActivity());
  private View mBottomLayout;
  private View mBtnConfirm;
  private TextView mBtnConfirmPicNum;
  boolean mConfirmed = false;
  private boolean mContinue = false;
  private GetAlbumTask mGetAlbumTask;
  private View mHeadView;
  private ListView mListView;
  private ArrayList<LocalAlbumItem> mLocalAlbumList = new ArrayList();
  ArrayList<CloudAlbumPicItem> mRecentPhotos;
  private KXHorizontalScrollView mScrollView;
  private boolean mSingle = false;
  private ArrayList<LocalPhotoItem> mTempList = null;
  private TextView mTvEmpty = null;
  protected LinearLayout mWaitLayout;
  private ProgressBar mWaitingProBar;

  private void confirmPhotos()
  {
    if (this.mConfirmed)
      return;
    this.mConfirmed = true;
    ArrayList localArrayList = PhotoSelectModel.getInstance().getSelectPhotoList();
    if ((localArrayList == null) || (localArrayList.size() == 0))
      Toast.makeText(getActivity(), "请选择照片", 1).show();
    if (this.mContinue)
    {
      setResult(-1);
      finish();
      return;
    }
    Intent localIntent = new Intent();
    if (this.mSingle)
    {
      localIntent.putExtra("filename", ((LocalPhotoItem)localArrayList.get(0)).mPath);
      setResult(-1, localIntent);
      finish();
      return;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = localArrayList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        localIntent.setClass(getActivity(), UploadPhotoFragment.class);
        localIntent.putExtra("selection", localStringBuffer.toString());
        AnimationUtil.startFragmentForResult(this, localIntent, 3, 3);
        return;
      }
      LocalPhotoItem localLocalPhotoItem = (LocalPhotoItem)localIterator.next();
      localStringBuffer.append(localLocalPhotoItem.mId).append(";");
      localStringBuffer.append(localLocalPhotoItem.mPath).append(";");
      localStringBuffer.append(localLocalPhotoItem.mFrom).append(";");
    }
  }

  private void constructBottomViews()
  {
    if (isNeedReturn());
    ArrayList localArrayList;
    int i;
    label34: Iterator localIterator1;
    label47: int j;
    label91: String str1;
    while (true)
    {
      return;
      localArrayList = PhotoSelectModel.getInstance().getSelectPhotoList();
      View localView1 = getView().findViewById(2131363366);
      if (localArrayList.size() != 0)
        break;
      i = 0;
      if (this.mContinue)
      {
        localIterator1 = localArrayList.iterator();
        if (localIterator1.hasNext())
          break label239;
        int i4 = this.mTempList.size();
        i = 0;
        if (i4 == 0)
          i = 8;
      }
      View localView2 = this.mBottomLayout;
      if (i != 8)
        break label326;
      j = 0;
      localView2.setVisibility(j);
      localView1.setVisibility(i);
      if (i == 0)
        continue;
      str1 = getResources().getString(2131428567);
      if (localArrayList.size() >= 10)
        break label333;
    }
    LinearLayout localLinearLayout1;
    int k;
    int m;
    int n;
    label260: label324: label326: label333: for (String str2 = "0" + String.valueOf(localArrayList.size()); ; str2 = String.valueOf(localArrayList.size()))
    {
      String str3 = str1.replace("*", str2);
      this.mBtnConfirmPicNum.setText(str3);
      localLinearLayout1 = (LinearLayout)getView().findViewById(2131363370);
      k = localLinearLayout1.getChildCount();
      m = localArrayList.size();
      n = 0;
      if (n < m)
        break label345;
      while (k > m)
      {
        localLinearLayout1.removeViewAt(k - 1);
        k--;
      }
      break;
      i = 8;
      break label34;
      label239: LocalPhotoItem localLocalPhotoItem2 = (LocalPhotoItem)localIterator1.next();
      Iterator localIterator2 = this.mTempList.iterator();
      boolean bool = localIterator2.hasNext();
      int i3 = 0;
      if (!bool);
      while (true)
      {
        if (i3 != 0)
          break label324;
        this.mTempList.clear();
        break;
        LocalPhotoItem localLocalPhotoItem3 = (LocalPhotoItem)localIterator2.next();
        if (!localLocalPhotoItem2.mId.equals(localLocalPhotoItem3.mId))
          break label260;
        i3 = 1;
      }
      break label47;
      j = 8;
      break label91;
    }
    label345: LocalPhotoItem localLocalPhotoItem1 = (LocalPhotoItem)localArrayList.get(n);
    label356: int i1 = k;
    int i2 = 0;
    if (n >= i1)
      label370: if (i2 == 0)
        break label477;
    while (true)
    {
      n++;
      break;
      View localView3 = localLinearLayout1.getChildAt(n);
      if ((localView3 != null) && (localView3.getTag() != null) && (localLocalPhotoItem1.mId.equals((String)localView3.getTag())))
      {
        i2 = 1;
        if (n < m - 1)
        {
          localView3.setPadding(0, 0, dipToPx(8.0F), 0);
          break label370;
        }
        localView3.setPadding(0, 0, 0, 0);
        break label370;
      }
      localLinearLayout1.removeViewAt(n);
      k--;
      break label356;
      label477: LinearLayout localLinearLayout2 = new LinearLayout(getActivity());
      TableLayout.LayoutParams localLayoutParams1 = new TableLayout.LayoutParams();
      localLayoutParams1.width = -2;
      localLayoutParams1.height = -2;
      localLinearLayout2.setLayoutParams(localLayoutParams1);
      if (n < m - 1)
        localLinearLayout2.setPadding(0, 0, dipToPx(8.0F), 0);
      while (true)
      {
        KXFrameImageView localKXFrameImageView = new KXFrameImageView(getActivity());
        localKXFrameImageView.setFrameResId(2130838245);
        TableLayout.LayoutParams localLayoutParams2 = new TableLayout.LayoutParams();
        localLayoutParams2.width = dipToPx(50.0F);
        localLayoutParams2.height = dipToPx(50.0F);
        localKXFrameImageView.setLayoutParams(localLayoutParams2);
        this.mAsyncLoader.showImage(localKXFrameImageView, localLocalPhotoItem1.mId, 0);
        2 local2 = new View.OnClickListener(localLocalPhotoItem1)
        {
          public void onClick(View paramView)
          {
            PhotoSelectModel.getInstance().removePhotoById(this.val$item.mId);
            PhotoSelectFragment.this.constructBottomViews();
            PhotoSelectFragment.this.updateHeadView();
          }
        };
        localKXFrameImageView.setOnClickListener(local2);
        localLinearLayout2.addView(localKXFrameImageView);
        localLinearLayout2.setTag(localLocalPhotoItem1.mId);
        if (k - 1 <= n)
          break label696;
        localLinearLayout1.removeViewAt(n);
        localLinearLayout1.addView(localLinearLayout2, n);
        break;
        localLinearLayout2.setPadding(0, 0, 0, 0);
      }
      label696: localLinearLayout1.addView(localLinearLayout2);
    }
  }

  private void getAlbumList()
  {
    showWait(true);
    this.mGetAlbumTask = new GetAlbumTask();
    this.mGetAlbumTask.execute(new Void[0]);
  }

  private void gotoOtherUGCPage()
  {
    LocalPhotoItem localLocalPhotoItem = (LocalPhotoItem)PhotoSelectModel.getInstance().getSelectPhotoList().get(0);
    Intent localIntent = new Intent();
    Bundle localBundle = new Bundle();
    localBundle.putString("filename", localLocalPhotoItem.mPath);
    localIntent.putExtras(localBundle);
    setResult(-1, localIntent);
    finish();
  }

  private void initView(View paramView)
  {
    this.mListView = ((ListView)paramView.findViewById(2131362128));
    this.mAdapter = new AlbumAdapter();
    this.mListView.setAdapter(this.mAdapter);
    this.mAdapter.setData(this.mLocalAlbumList);
    this.mListView.setOnItemClickListener(this);
    this.mWaitLayout = ((LinearLayout)paramView.findViewById(2131362134));
    this.mTvEmpty = ((TextView)paramView.findViewById(2131362136));
    this.mWaitingProBar = ((ProgressBar)paramView.findViewById(2131362135));
    this.mHeadView = getActivity().getLayoutInflater().inflate(2130903279, null);
    paramView.findViewById(2131363367).setOnClickListener(this);
    this.mBtnConfirm = paramView.findViewById(2131363371);
    this.mBtnConfirm.setOnClickListener(this);
    this.mBtnConfirmPicNum = ((TextView)paramView.findViewById(2131363372));
    this.mBottomLayout = paramView.findViewById(2131363368);
  }

  private void recentPicClicked(int paramInt)
  {
    CloudAlbumPicItem localCloudAlbumPicItem;
    if ((this.mRecentPhotos != null) && (this.mRecentPhotos.size() > paramInt))
    {
      localCloudAlbumPicItem = (CloudAlbumPicItem)this.mRecentPhotos.get(paramInt);
      if (!PhotoSelectModel.getInstance().isSelect(localCloudAlbumPicItem.mThumbUrl))
        break label90;
      PhotoSelectModel.getInstance().removePhotoById(localCloudAlbumPicItem.mThumbUrl);
    }
    while (true)
    {
      constructBottomViews();
      updateHeadView();
      if (this.mScrollView == null)
        this.mScrollView = ((KXHorizontalScrollView)getView().findViewById(2131363369));
      scrollToEnd();
      return;
      label90: if (PhotoSelectModel.getInstance().getSelectPhotoList().size() >= 20)
      {
        Toast.makeText(getActivity(), 2131428377, 0).show();
        return;
      }
      PhotoSelectModel.getInstance().addPhoto(localCloudAlbumPicItem.mThumbUrl, localCloudAlbumPicItem.mUrl);
    }
  }

  private void refreshAllView()
  {
    constructBottomViews();
    this.mAdapter.setData(this.mLocalAlbumList);
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
          int i = ((LinearLayout)PhotoSelectFragment.this.mScrollView.getChildAt(0)).getMeasuredWidth() - PhotoSelectFragment.this.mScrollView.getWidth();
          if (i < 0)
            i = 0;
          PhotoSelectFragment.this.mScrollView.scrollTo(i, 0);
          return;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    });
  }

  private void sortList()
  {
    int i = 0;
    String[] arrayOfString = { "camera", "screenshots" };
    ArrayList localArrayList = new ArrayList();
    int j = 0;
    int k;
    if (j >= arrayOfString.length)
    {
      k = -1 + this.mLocalAlbumList.size();
      if (k < 0)
      {
        this.mLocalAlbumList.clear();
        this.mLocalAlbumList.addAll(localArrayList);
        return;
      }
    }
    else
    {
      Iterator localIterator = this.mLocalAlbumList.iterator();
      label79: if (!localIterator.hasNext());
      while (true)
      {
        j++;
        break;
        LocalAlbumItem localLocalAlbumItem1 = (LocalAlbumItem)localIterator.next();
        if (!arrayOfString[j].equals(localLocalAlbumItem1.mName.toLowerCase()))
          break label79;
        localArrayList.add(localLocalAlbumItem1);
        this.mLocalAlbumList.remove(localLocalAlbumItem1);
        i++;
      }
    }
    LocalAlbumItem localLocalAlbumItem2 = (LocalAlbumItem)this.mLocalAlbumList.get(k);
    label253: for (int m = i; ; m++)
    {
      int n = localArrayList.size();
      int i1 = 0;
      if (m >= n);
      while (true)
      {
        if (i1 == 0)
        {
          localArrayList.add(localLocalAlbumItem2);
          this.mLocalAlbumList.remove(localLocalAlbumItem2);
        }
        k--;
        break;
        if (((LocalAlbumItem)localArrayList.get(m)).mCount >= localLocalAlbumItem2.mCount)
          break label253;
        localArrayList.add(m, localLocalAlbumItem2);
        this.mLocalAlbumList.remove(localLocalAlbumItem2);
        i1 = 1;
      }
    }
  }

  private void updateHeadView()
  {
    KXFrameImageView localKXFrameImageView1 = (KXFrameImageView)this.mHeadView.findViewById(2131363375);
    KXFrameImageView localKXFrameImageView2 = (KXFrameImageView)this.mHeadView.findViewById(2131363376);
    KXFrameImageView localKXFrameImageView3 = (KXFrameImageView)this.mHeadView.findViewById(2131363377);
    KXFrameImageView localKXFrameImageView4 = (KXFrameImageView)this.mHeadView.findViewById(2131363378);
    updateTopPhotoItem(localKXFrameImageView1, 0);
    updateTopPhotoItem(localKXFrameImageView2, 1);
    updateTopPhotoItem(localKXFrameImageView3, 2);
    updateTopPhotoItem(localKXFrameImageView4, 3);
    if ((this.mRecentPhotos == null) || ((this.mRecentPhotos != null) && (this.mRecentPhotos.size() == 0)))
    {
      this.mHeadView.findViewById(2131363373).setVisibility(8);
      this.mHeadView.findViewById(2131363374).setVisibility(8);
    }
  }

  private void updateTopPhotoItem(KXFrameImageView paramKXFrameImageView, int paramInt)
  {
    ArrayList localArrayList1 = this.mRecentPhotos;
    int i = 0;
    if (localArrayList1 != null)
      i = this.mRecentPhotos.size();
    ArrayList localArrayList2 = this.mRecentPhotos;
    boolean bool = false;
    if (localArrayList2 != null)
    {
      int k = this.mRecentPhotos.size();
      bool = false;
      if (k > paramInt)
        bool = PhotoSelectModel.getInstance().isSelect(((CloudAlbumPicItem)this.mRecentPhotos.get(paramInt)).mThumbUrl);
    }
    paramKXFrameImageView.setFrameResId(2130838245);
    int j;
    if (i > 0)
    {
      j = 0;
      paramKXFrameImageView.setVisibility(j);
      paramKXFrameImageView.setOnClickListener(this);
      if (i <= paramInt)
        break label223;
      if (paramKXFrameImageView.getVisibility() != 0)
        paramKXFrameImageView.setVisibility(0);
      this.mAsyncLoader.showImageCancel(paramKXFrameImageView);
      if (!TextUtils.isEmpty(((CloudAlbumPicItem)this.mRecentPhotos.get(paramInt)).mThumbUrl))
        break label186;
      paramKXFrameImageView.setImageBitmap(null);
      label152: if (!bool)
        break label212;
      paramKXFrameImageView.setCenterIndicateBackground(getResources().getDrawable(2130839391));
      paramKXFrameImageView.setCenterIndicateIcon(2130838935);
    }
    label186: label212: label223: 
    do
    {
      return;
      j = 8;
      break;
      this.mAsyncLoader.showImage(paramKXFrameImageView, ((CloudAlbumPicItem)this.mRecentPhotos.get(paramInt)).mThumbUrl, 0);
      break label152;
      paramKXFrameImageView.setCenterIndicateBackground(null);
      paramKXFrameImageView.setCenterIndicateIcon(0);
      return;
    }
    while (paramKXFrameImageView.getVisibility() == 8);
    paramKXFrameImageView.setVisibility(8);
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
      PhotoSelectModel.getInstance().addPhoto(getActivity(), paramString1);
      confirmPhotos();
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("KaixinDesktop", "onActivityResult", localException);
    }
  }

  protected void initTitle(View paramView)
  {
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    paramView.findViewById(2131362928).setVisibility(8);
    ((ImageView)paramView.findViewById(2131362914)).setOnClickListener(this);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    localTextView.setVisibility(0);
    if (this.mContinue);
    for (int i = 2131428565; ; i = 2131428564)
    {
      localTextView.setText(i);
      return;
    }
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    PhotoSelectModel.getInstance().removePhotoSelectChangedListener(this);
    PhotoSelectModel.getInstance().addPhotoSelectChangedListener(this);
    if (paramInt1 == 2)
    {
      if (this.mSingle)
      {
        gotoOtherUGCPage();
        return;
      }
      if (this.mContinue)
      {
        new Intent();
        paramIntent.putExtra("from", "camera");
        setResult(-1, paramIntent);
        finish();
        return;
      }
      if (paramIntent != null)
      {
        EditPictureModel.setPicFrom("display_name");
        paramIntent.setClass(getActivity(), UploadPhotoFragment.class);
        startActivityForResult(paramIntent, 3);
      }
      finish();
      return;
    }
    if (paramInt2 == 4)
    {
      finish();
      return;
    }
    if ((paramInt2 == -1) && (paramInt1 == 1))
    {
      confirmPhotos();
      return;
    }
    if (paramInt1 == 3)
    {
      finish();
      return;
    }
    if ((paramInt2 == -1) && (paramInt1 == 104))
    {
      if (this.mSingle)
      {
        gotoOtherUGCPage();
        return;
      }
      if (!this.mContinue)
        IntentUtil.showUploadPhotoFragment(this, paramIntent.getExtras());
      while (true)
      {
        finish();
        return;
        Intent localIntent = new Intent();
        localIntent.putExtras(paramIntent.getExtras());
        localIntent.putExtra("from", "camera");
        setResult(-1, localIntent);
      }
    }
    if ((paramInt2 == 0) && (paramInt1 == 1))
    {
      constructBottomViews();
      return;
    }
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    if (paramInt == -1)
      finish();
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131363375:
      recentPicClicked(0);
      return;
    case 2131363376:
      recentPicClicked(1);
      return;
    case 2131363377:
      recentPicClicked(2);
      return;
    case 2131363378:
      recentPicClicked(3);
      return;
    case 2131362914:
      finish();
      return;
    case 2131363367:
      if (PhotoSelectModel.getInstance().getSelectPhotoList().size() >= 20)
      {
        Toast.makeText(getActivity(), 2131428377, 0).show();
        return;
      }
      takePictureWithCamera();
      return;
    case 2131363371:
    }
    confirmPhotos();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903277, paramViewGroup, false);
  }

  public void onDestroy()
  {
    PhotoSelectModel.getInstance().removePhotoSelectChangedListener(this);
    cancelTask(this.mGetAlbumTask);
    super.onDestroy();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    LocalAlbumItem localLocalAlbumItem = (LocalAlbumItem)this.mLocalAlbumList.get(paramInt);
    if ("headview".equals(localLocalAlbumItem.mId))
      return;
    Intent localIntent = new Intent();
    localIntent.setClass(getActivity(), MultiPicSelectorFragment.class);
    localIntent.putExtra("bucket_id", localLocalAlbumItem.mId);
    localIntent.putExtra("single", this.mSingle);
    PhotoSelectModel.getInstance().removePhotoSelectChangedListener(this);
    AnimationUtil.startFragmentForResult(this, localIntent, 1, 3);
  }

  public void onPhotoSelectChanged(int paramInt)
  {
    if ((this.mSingle) && (paramInt == 1))
      confirmPhotos();
    if (paramInt == 0)
      this.mListView.setPadding(0, 0, 0, dipToPx(48.0F));
    while (true)
    {
      refreshAllView();
      return;
      if (paramInt <= 0)
        continue;
      this.mListView.setPadding(0, 0, 0, dipToPx(72.0F));
    }
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mAsyncLoader = new AsyncLocalImageLoader(getActivity());
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      this.mSingle = localBundle.getBoolean("single");
      this.mContinue = localBundle.getBoolean("continue");
    }
    if (!this.mContinue)
      PhotoSelectModel.getInstance().clear();
    while (true)
    {
      PhotoSelectModel.getInstance().removePhotoSelectChangedListener(this);
      PhotoSelectModel.getInstance().addPhotoSelectChangedListener(this);
      initTitle(paramView);
      initView(paramView);
      getAlbumList();
      return;
      this.mTempList = new ArrayList();
      this.mTempList.addAll(PhotoSelectModel.getInstance().getSelectPhotoList());
    }
  }

  public void requestFinish()
  {
  }

  protected void showWait(boolean paramBoolean)
  {
    ListView localListView = this.mListView;
    int i;
    LinearLayout localLinearLayout;
    int j;
    if (paramBoolean)
    {
      i = 8;
      localListView.setVisibility(i);
      localLinearLayout = this.mWaitLayout;
      j = 0;
      if (!paramBoolean)
        break label43;
    }
    while (true)
    {
      localLinearLayout.setVisibility(j);
      return;
      i = 0;
      break;
      label43: j = 8;
    }
  }

  class AlbumAdapter extends BaseAdapter
  {
    private ArrayList<PhotoSelectFragment.LocalAlbumItem> mList = new ArrayList();

    AlbumAdapter()
    {
    }

    private void setData(ArrayList<PhotoSelectFragment.LocalAlbumItem> paramArrayList)
    {
      this.mList.clear();
      if (paramArrayList != null)
        this.mList.addAll(paramArrayList);
      notifyDataSetChanged();
    }

    public int getCount()
    {
      return this.mList.size();
    }

    public Object getItem(int paramInt)
    {
      return this.mList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      PhotoSelectFragment.LocalAlbumItem localLocalAlbumItem = (PhotoSelectFragment.LocalAlbumItem)this.mList.get(paramInt);
      if ("headview".equals(localLocalAlbumItem.mId))
      {
        PhotoSelectFragment.this.updateHeadView();
        return PhotoSelectFragment.this.mHeadView;
      }
      PhotoSelectFragment.ViewHolder localViewHolder;
      if ((paramView == null) || (paramView == PhotoSelectFragment.this.mHeadView))
      {
        paramView = PhotoSelectFragment.this.getActivity().getLayoutInflater().inflate(2130903280, null);
        localViewHolder = new PhotoSelectFragment.ViewHolder(PhotoSelectFragment.this);
        localViewHolder.mThumb = ((KXFrameImageView)paramView.findViewById(2131363381));
        localViewHolder.mThumb.setFrameResId(2130838245);
        localViewHolder.mName = ((TextView)paramView.findViewById(2131363382));
        localViewHolder.mLine = paramView.findViewById(2131363384);
        paramView.setTag(localViewHolder);
        PhotoSelectFragment.this.mAsyncLoader.showImageCancel(localViewHolder.mThumb);
        if (!TextUtils.isEmpty(localLocalAlbumItem.mThumb))
          break label241;
        localViewHolder.mThumb.setImageBitmap(null);
      }
      while (true)
      {
        String str = localLocalAlbumItem.mName + "<font color=\"#b4b4b4\">(" + localLocalAlbumItem.mCount + ")</font>";
        localViewHolder.mName.setText(Html.fromHtml(str));
        return paramView;
        localViewHolder = (PhotoSelectFragment.ViewHolder)paramView.getTag();
        break;
        label241: PhotoSelectFragment.this.mAsyncLoader.showImage(localViewHolder.mThumb, localLocalAlbumItem.mThumb, 0);
      }
    }
  }

  class GetAlbumTask extends KXFragment.KXAsyncTask<Void, Void, Integer>
  {
    GetAlbumTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Void[] paramArrayOfVoid)
    {
      PhotoSelectFragment.this.mLocalAlbumList.clear();
      PhotoSelectFragment.this.mRecentPhotos = CloudAlbumManager.getInstance().loadLocalPictures(null, 8);
      int i = -1 + PhotoSelectFragment.this.mRecentPhotos.size();
      PhotoSelectFragment.LocalAlbumItem localLocalAlbumItem1;
      String[] arrayOfString;
      Cursor localCursor;
      ArrayList localArrayList;
      if (i < 0)
      {
        localLocalAlbumItem1 = new PhotoSelectFragment.LocalAlbumItem(PhotoSelectFragment.this);
        localLocalAlbumItem1.mId = "headview";
        PhotoSelectFragment.this.mLocalAlbumList.add(0, localLocalAlbumItem1);
        publishProgress(new Void[0]);
        arrayOfString = new String[] { "_id", "bucket_display_name", "bucket_id", "COUNT(*) AS calls_count" };
        localCursor = null;
        localArrayList = new ArrayList();
      }
      while (true)
      {
        Iterator localIterator;
        try
        {
          localCursor = PhotoSelectFragment.this.getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOfString, "1=1) GROUP BY (bucket_id", null, null);
          if (localCursor == null)
            continue;
          boolean bool1 = localCursor.moveToFirst();
          boolean bool2 = bool1;
          if (bool2)
            continue;
          if (localCursor == null)
            continue;
          localCursor.close();
          PhotoSelectFragment.this.mLocalAlbumList.clear();
          PhotoSelectFragment.this.mLocalAlbumList.addAll(localArrayList);
          PhotoSelectFragment.this.sortList();
          PhotoSelectFragment.this.mLocalAlbumList.add(0, localLocalAlbumItem1);
          localIterator = PhotoSelectFragment.this.mLocalAlbumList.iterator();
          if (!localIterator.hasNext())
          {
            localObject2 = Integer.valueOf(1);
            return localObject2;
            CloudAlbumPicItem localCloudAlbumPicItem = (CloudAlbumPicItem)PhotoSelectFragment.this.mRecentPhotos.get(i);
            if (FileUtil.isPicExist(PhotoSelectFragment.this.getActivity().getApplicationContext(), localCloudAlbumPicItem.mThumbUrl))
              continue;
            PhotoSelectFragment.this.mRecentPhotos.remove(i);
            i--;
            break;
            PhotoSelectFragment.LocalAlbumItem localLocalAlbumItem2 = new PhotoSelectFragment.LocalAlbumItem(PhotoSelectFragment.this);
            localLocalAlbumItem2.mThumb = localCursor.getString(0);
            localLocalAlbumItem2.mName = localCursor.getString(1);
            localLocalAlbumItem2.mId = localCursor.getString(2);
            localLocalAlbumItem2.mCount = localCursor.getInt(3);
            localArrayList.add(localLocalAlbumItem2);
            boolean bool3 = localCursor.moveToNext();
            bool2 = bool3;
            continue;
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          Integer localInteger = Integer.valueOf(-1);
          Object localObject2 = localInteger;
          return localObject2;
        }
        finally
        {
          if (localCursor == null)
            continue;
          localCursor.close();
        }
        PhotoSelectFragment.LocalAlbumItem localLocalAlbumItem3 = (PhotoSelectFragment.LocalAlbumItem)localIterator.next();
        if (FileUtil.isPicExist(PhotoSelectFragment.this.getActivity().getApplicationContext(), localLocalAlbumItem3.mThumb))
          continue;
        localLocalAlbumItem3.mThumb = null;
      }
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if ((paramInteger == null) || (PhotoSelectFragment.this.getView() == null) || (PhotoSelectFragment.this.getActivity() == null))
        return;
      PhotoSelectFragment.this.showWait(false);
      PhotoSelectFragment.this.refreshAllView();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
      PhotoSelectFragment.this.showWait(false);
      PhotoSelectFragment.this.refreshAllView();
    }
  }

  public class LocalAlbumItem
  {
    public int mCount;
    public String mId;
    public String mName;
    public String mThumb;

    public LocalAlbumItem()
    {
    }
  }

  class ViewHolder
  {
    public View mLine;
    public TextView mName;
    public KXFrameImageView mThumb;

    ViewHolder()
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.PhotoSelectFragment
 * JD-Core Version:    0.6.0
 */