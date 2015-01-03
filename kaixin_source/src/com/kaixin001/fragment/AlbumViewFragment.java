package com.kaixin001.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.LoginActivity;
import com.kaixin001.engine.AlbumPhotoEngine;
import com.kaixin001.engine.CheckinListEngine;
import com.kaixin001.item.KaixinPhotoItem;
import com.kaixin001.item.PoiPhotoesItem;
import com.kaixin001.model.AlbumPhotoModel;
import com.kaixin001.model.CheckInInfoModel;
import com.kaixin001.model.KaixinModelTemplate;
import com.kaixin001.model.KaixinUser;
import com.kaixin001.model.User;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.ScreenUtil;
import java.util.ArrayList;

public class AlbumViewFragment extends BaseFragment
  implements View.OnClickListener
{
  public static final String IS_FROM_VIEW_PHOTO = "isFromViewPhoto";
  public static final int REQUEST_VIEW_PHOTO = 10;
  private AlbumItemListener albumListener;
  private int albumType;
  private String fname;
  private String fuid;
  private GetDataTask getDataTask;
  private boolean isFromViewPhotoActiviy;
  private AlbumListAdapter mAdapter;
  private String mAlbumId;
  private final ArrayList<KaixinPhotoItem[]> mAlbumList = new ArrayList();
  private String mAlbumTitle;
  private ListView mListView;
  private int mOrientation = 0;
  private int mPicCount = -1;
  private String mReplyContent = "";
  private int mScreenWidth;
  private View mWaitLayout;
  private String poiid;
  private String pwd;
  private TextView tvNodata;
  private TextView tvTitle;

  private int getColumnCount()
  {
    int i = 4;
    if (this.mOrientation == 1)
      if ((this.mScreenWidth >= 240) && (this.mScreenWidth < 320))
        i = 3;
    do
    {
      return i;
      return 4;
    }
    while (this.mOrientation != 2);
    if ((this.mScreenWidth >= 240) && (this.mScreenWidth < 320))
      return 3;
    if ((this.mScreenWidth >= 320) && (this.mScreenWidth < 480))
      return 4;
    return 6;
  }

  private ArrayList<KaixinPhotoItem> getModelList()
  {
    AlbumPhotoModel localAlbumPhotoModel = AlbumPhotoModel.getInstance();
    ArrayList localArrayList;
    if (this.albumType == 1)
    {
      KaixinUser localKaixinUser = localAlbumPhotoModel.logoAlbumOwner;
      localArrayList = null;
      if (localKaixinUser != null)
      {
        boolean bool2 = this.fuid.equals(localAlbumPhotoModel.logoAlbumOwner.uid);
        localArrayList = null;
        if (bool2)
        {
          this.mPicCount = localAlbumPhotoModel.mListLogos.total;
          localArrayList = localAlbumPhotoModel.mListLogos.getItemList();
        }
      }
    }
    PoiPhotoesItem localPoiPhotoesItem;
    do
    {
      int i;
      do
      {
        while (true)
        {
          return localArrayList;
          if (this.albumType != 2)
            break;
          boolean bool1 = this.mAlbumId.equals(localAlbumPhotoModel.getAlbumId());
          localArrayList = null;
          if (!bool1)
            continue;
          this.mPicCount = localAlbumPhotoModel.mListPhotos.total;
          return localAlbumPhotoModel.mListPhotos.getItemList();
        }
        i = this.albumType;
        localArrayList = null;
      }
      while (i != 4);
      localPoiPhotoesItem = CheckInInfoModel.getInstance().getPoiPhotoesItemByPoiid(this.poiid);
      localArrayList = null;
    }
    while (localPoiPhotoesItem == null);
    this.mPicCount = localPoiPhotoesItem.photoList.total;
    return localPoiPhotoesItem.photoList.getItemList();
  }

  private void init()
  {
    updateDataList();
    updateView();
    if ((!this.mAlbumList.isEmpty()) || (!super.checkNetworkAndHint(true)))
      return;
    this.mWaitLayout.setVisibility(0);
    this.mListView.setVisibility(8);
    this.tvNodata.setVisibility(8);
    Integer[] arrayOfInteger = new Integer[2];
    arrayOfInteger[0] = Integer.valueOf(0);
    arrayOfInteger[1] = Integer.valueOf(24);
    this.getDataTask = new GetDataTask(null);
    this.getDataTask.execute(arrayOfInteger);
  }

  private void onDownloading(boolean paramBoolean)
  {
  }

  private void refreshMore()
  {
    if ((this.getDataTask != null) && (!this.getDataTask.isCancelled()) && (this.getDataTask.getStatus() != AsyncTask.Status.FINISHED));
    Integer[] arrayOfInteger;
    ArrayList localArrayList;
    do
    {
      do
      {
        return;
        arrayOfInteger = new Integer[2];
        arrayOfInteger[0] = Integer.valueOf(0);
        arrayOfInteger[1] = Integer.valueOf(24);
        localArrayList = getModelList();
      }
      while (localArrayList == null);
      arrayOfInteger[0] = Integer.valueOf(localArrayList.size());
    }
    while ((this.mPicCount >= 0) && (localArrayList.size() == this.mPicCount));
    this.getDataTask = new GetDataTask(null);
    this.getDataTask.execute(arrayOfInteger);
  }

  private void setStateOnFetchFinished(boolean paramBoolean)
  {
    onDownloading(false);
    this.mWaitLayout.setVisibility(8);
    int i = this.mAlbumList.size();
    if (paramBoolean)
    {
      if (i == 0)
      {
        this.tvNodata.setVisibility(0);
        this.mListView.setVisibility(8);
        return;
      }
      this.tvNodata.setVisibility(8);
      this.mAdapter.notifyDataSetChanged();
      this.mListView.setVisibility(0);
      return;
    }
    if (i == 0)
    {
      this.tvNodata.setVisibility(8);
      this.mListView.setVisibility(8);
      return;
    }
    this.tvNodata.setVisibility(8);
    this.mListView.setVisibility(0);
  }

  private void setTitle(View paramView)
  {
    this.tvTitle = ((TextView)paramView.findViewById(2131362920));
    this.tvTitle.setVisibility(0);
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    ImageView localImageView1 = (ImageView)paramView.findViewById(2131362914);
    ImageView localImageView2 = (ImageView)paramView.findViewById(2131362928);
    if (this.isFromViewPhotoActiviy)
    {
      localImageView1.setVisibility(8);
      localImageView2.setImageResource(2130839022);
      localImageView2.setOnClickListener(this);
      return;
    }
    localImageView2.setVisibility(8);
    localImageView1.setOnClickListener(this);
  }

  private void updateDataList()
  {
    ArrayList localArrayList = getModelList();
    this.mAlbumList.clear();
    if ((localArrayList == null) || (localArrayList.isEmpty()))
      return;
    int i = getColumnCount();
    int j = (localArrayList.size() + (i - 1)) / i;
    int k = localArrayList.size() % i;
    int m;
    int n;
    KaixinPhotoItem[] arrayOfKaixinPhotoItem2;
    if (localArrayList.size() > 0)
    {
      m = 0;
      n = 0;
      if (n < j - 1)
        break label128;
      if (k == 0)
        k = i;
      arrayOfKaixinPhotoItem2 = new KaixinPhotoItem[k];
    }
    for (int i2 = 0; ; i2++)
    {
      if (i2 >= k)
      {
        this.mAlbumList.add(arrayOfKaixinPhotoItem2);
        this.mAlbumList.add(null);
        if (this.mAdapter == null)
          break;
        this.mAdapter.notifyDataSetChanged();
        return;
        label128: KaixinPhotoItem[] arrayOfKaixinPhotoItem1 = new KaixinPhotoItem[i];
        for (int i1 = 0; ; i1++)
        {
          if (i1 >= i)
          {
            m += i;
            this.mAlbumList.add(arrayOfKaixinPhotoItem1);
            n++;
            break;
          }
          arrayOfKaixinPhotoItem1[i1] = ((KaixinPhotoItem)localArrayList.get(m + i1));
        }
      }
      arrayOfKaixinPhotoItem2[i2] = ((KaixinPhotoItem)localArrayList.get(m + i2));
    }
  }

  private void updateView()
  {
    if (this.mAlbumTitle.length() > 6);
    for (String str = this.mAlbumTitle.substring(0, 6) + "..."; ; str = this.mAlbumTitle)
    {
      this.tvTitle.setText(str);
      return;
    }
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 2);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 == -1)
    {
      if (paramInt1 != 3)
        break label29;
      this.mReplyContent = paramIntent.getStringExtra("content");
    }
    label29: 
    do
      return;
    while (paramInt1 != 10);
    this.mAlbumId = paramIntent.getStringExtra("albumId");
    this.albumType = paramIntent.getIntExtra("albumType", -1);
    if ("0".equals(this.mAlbumId))
      this.albumType = 1;
    String str;
    if ((this.albumType == 1) || (this.albumType == 2))
    {
      this.fuid = paramIntent.getStringExtra("fuid");
      this.pwd = paramIntent.getStringExtra("pwd");
      this.fname = paramIntent.getStringExtra("fname");
      str = paramIntent.getStringExtra("pic_count");
    }
    while (true)
    {
      try
      {
        this.mPicCount = Integer.parseInt(str);
        this.mAlbumTitle = paramIntent.getStringExtra("title");
        init();
        return;
      }
      catch (Exception localException)
      {
        this.mPicCount = -1;
        localException.printStackTrace();
        continue;
      }
      if (this.albumType != 4)
        continue;
      this.mAlbumTitle = paramIntent.getStringExtra("title");
      this.poiid = this.mAlbumId;
    }
  }

  public void onClick(View paramView)
  {
    if (!TextUtils.isEmpty(this.mReplyContent))
    {
      Intent localIntent = new Intent();
      localIntent.putExtra("content", this.mReplyContent);
      setResult(-1, localIntent);
      finishFragment(3);
    }
    finish();
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    this.mOrientation = paramConfiguration.orientation;
    this.mScreenWidth = ScreenUtil.getScreenWidth(getActivity());
    try
    {
      updateDataList();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    String str = null;
    if (localBundle != null)
      str = localBundle.getString("action");
    if ((str != null) && (!str.equals("com.kaixin001.VIEW_ALBUM_DETAIL")) && (CrashRecoverUtil.isCrashBefore()))
    {
      CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
      IntentUtil.returnToDesktop(getActivity());
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903055, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if ((this.getDataTask != null) && (!this.getDataTask.isCancelled()) && (this.getDataTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.getDataTask.cancel(true);
      AlbumPhotoEngine.getInstance().cancel();
    }
    cancelTask(this.getDataTask);
    if (!this.isFromViewPhotoActiviy)
      AlbumPhotoModel.getInstance().clear();
    super.onDestroy();
  }

  public void onResume()
  {
    super.onResume();
    if (this.isFromViewPhotoActiviy)
      updateDataList();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    User.getInstance().loadDataIfEmpty(getActivity().getApplicationContext());
    this.albumListener = new AlbumItemListener(null);
    Bundle localBundle = getArguments();
    String str;
    if (localBundle != null)
    {
      this.isFromViewPhotoActiviy = localBundle.getBoolean("isFromViewPhoto");
      this.mAlbumId = localBundle.getString("albumId");
      this.albumType = localBundle.getInt("albumType", 2);
      if ("0".equals(this.mAlbumId))
        this.albumType = 1;
      if ((this.albumType != 1) && (this.albumType != 2))
        break label391;
      this.fuid = localBundle.getString("fuid");
      this.pwd = localBundle.getString("pwd");
      this.fname = localBundle.getString("fname");
      str = localBundle.getString("pic_count");
    }
    while (true)
    {
      try
      {
        this.mPicCount = Integer.parseInt(str);
        this.mAlbumTitle = localBundle.getString("title");
        if (!TextUtils.isEmpty(User.getInstance().getOauthToken()))
          continue;
        Intent localIntent = new Intent(getActivity(), LoginActivity.class);
        localIntent.putExtra("fuid", this.fuid);
        localIntent.putExtra("fname", this.fname);
        localIntent.putExtra("title", this.mAlbumTitle);
        localIntent.putExtra("albumId", this.mAlbumId);
        startActivity(localIntent);
        finish();
        setTitle(paramView);
        this.tvNodata = ((TextView)paramView.findViewById(2131361816));
        this.mWaitLayout = paramView.findViewById(2131361854);
        this.mListView = ((ListView)paramView.findViewById(2131361857));
        this.mListView.setOnScrollListener(new AbsListView.OnScrollListener()
        {
          public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
          {
            ArrayList localArrayList = AlbumViewFragment.this.getModelList();
            if (localArrayList == null);
            int i;
            do
            {
              return;
              i = AlbumViewFragment.this.getColumnCount() * (paramInt1 + paramInt2);
            }
            while ((AlbumViewFragment.this.mPicCount <= localArrayList.size()) || (localArrayList.size() - i >= 24));
            AlbumViewFragment.this.refreshMore();
          }

          public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
          {
          }
        });
        this.mOrientation = ScreenUtil.getOrientation(getActivity());
        this.mScreenWidth = ScreenUtil.getScreenWidth(getActivity());
        this.mAdapter = new AlbumListAdapter(getActivity(), 2130903056);
        this.mListView.setAdapter(this.mAdapter);
        init();
        return;
      }
      catch (Exception localException)
      {
        this.mPicCount = -1;
        localException.printStackTrace();
        continue;
      }
      label391: if (this.albumType != 4)
        continue;
      this.mAlbumTitle = localBundle.getString("title");
      this.poiid = this.mAlbumId;
    }
  }

  private class AlbumItemListener
    implements View.OnClickListener
  {
    private AlbumItemListener()
    {
    }

    public void onClick(View paramView)
    {
      int i = ((Integer)paramView.getTag()).intValue();
      if ((AlbumViewFragment.this.isFromViewPhotoActiviy) && (AlbumViewFragment.this.albumType == 2))
      {
        Intent localIntent2 = new Intent();
        localIntent2.putExtra("albumId", AlbumViewFragment.this.mAlbumId);
        localIntent2.putExtra("albumType", AlbumViewFragment.this.albumType);
        localIntent2.putExtra("title", AlbumViewFragment.this.mAlbumTitle);
        localIntent2.putExtra("imgIndex", i);
        localIntent2.putExtra("fuid", AlbumViewFragment.this.fuid);
        localIntent2.putExtra("password", AlbumViewFragment.this.pwd);
        AlbumViewFragment.this.setResult(-1, localIntent2);
        AlbumViewFragment.this.finish();
        return;
      }
      if ((AlbumViewFragment.this.isFromViewPhotoActiviy) && (AlbumViewFragment.this.albumType != 2))
      {
        Intent localIntent1 = new Intent(AlbumViewFragment.this.getActivity(), ViewPhotoFragment.class);
        Bundle localBundle = new Bundle();
        localBundle.putInt("imgIndex", i);
        localBundle.putString("fuid", AlbumViewFragment.this.fuid);
        localBundle.putString("title", AlbumViewFragment.this.mAlbumTitle);
        localBundle.putString("albumId", AlbumViewFragment.this.mAlbumId);
        localBundle.putInt("albumType", AlbumViewFragment.this.albumType);
        if (!TextUtils.isEmpty(AlbumViewFragment.this.pwd))
          localBundle.putString("password", AlbumViewFragment.this.pwd);
        localBundle.putBoolean("isFromViewAlbum", true);
        localIntent1.putExtras(localBundle);
        AlbumViewFragment.this.startActivityForResult(localIntent1, 3);
        return;
      }
      IntentUtil.showViewPhotoActivityFromAlbum(AlbumViewFragment.this.getActivity(), AlbumViewFragment.this, i + 1, AlbumViewFragment.this.fuid, AlbumViewFragment.this.mAlbumTitle, AlbumViewFragment.this.mAlbumId, AlbumViewFragment.this.albumType, AlbumViewFragment.this.pwd);
    }
  }

  private class AlbumItemViewTag
  {
    private static final int MAX_PICS = 6;
    private AlbumViewFragment.PhotoLayoutItem[] mPhotos = new AlbumViewFragment.PhotoLayoutItem[6];
    private TextView mTxtButtom = null;

    private AlbumItemViewTag(View paramInt, int arg3)
    {
      for (int i = 0; ; i++)
      {
        if (i >= this.mPhotos.length)
        {
          this.mPhotos[0] = new AlbumViewFragment.PhotoLayoutItem(paramInt.findViewById(2131361859));
          this.mPhotos[1] = new AlbumViewFragment.PhotoLayoutItem(paramInt.findViewById(2131361860));
          this.mPhotos[2] = new AlbumViewFragment.PhotoLayoutItem(paramInt.findViewById(2131361861));
          this.mPhotos[3] = new AlbumViewFragment.PhotoLayoutItem(paramInt.findViewById(2131361862));
          this.mPhotos[4] = new AlbumViewFragment.PhotoLayoutItem(paramInt.findViewById(2131361863));
          this.mPhotos[5] = new AlbumViewFragment.PhotoLayoutItem(paramInt.findViewById(2131361864));
          this.mTxtButtom = ((TextView)paramInt.findViewById(2131361865));
          return;
        }
        this.mPhotos[i] = null;
      }
    }

    public void setPhotoItem(KaixinPhotoItem[] paramArrayOfKaixinPhotoItem, int paramInt)
    {
      this.mTxtButtom.setVisibility(8);
      int i = AlbumViewFragment.this.getColumnCount();
      int j = 0;
      if (j >= i);
      label26: for (int k = i; ; k++)
      {
        if (k >= 6)
        {
          return;
          if (this.mPhotos[j] == null)
            break label26;
          if ((j < paramArrayOfKaixinPhotoItem.length) && (paramArrayOfKaixinPhotoItem[j] != null))
          {
            this.mPhotos[j].setVisibility(0);
            AlbumViewFragment.this.displayPicture(this.mPhotos[j].mImageView, paramArrayOfKaixinPhotoItem[j].thumbnail, 2130838784);
            this.mPhotos[j].setTag(j + paramInt * i);
            this.mPhotos[j].setOnClickListener(AlbumViewFragment.this.albumListener);
          }
          while (true)
          {
            j++;
            break;
            this.mPhotos[j].setVisibility(4);
          }
        }
        if (this.mPhotos[k] == null)
          continue;
        this.mPhotos[k].setVisibility(8);
      }
    }

    public void setTextViewButtom(int paramInt)
    {
      for (int i = 0; ; i++)
      {
        if (i >= 6)
        {
          String str = AlbumViewFragment.this.getResources().getString(2131427497);
          this.mTxtButtom.setText(paramInt + str);
          this.mTxtButtom.setVisibility(0);
          return;
        }
        if (this.mPhotos[i] == null)
          continue;
        this.mPhotos[i].setVisibility(8);
      }
    }
  }

  private class AlbumListAdapter extends BaseAdapter
  {
    public AlbumListAdapter(Context paramInt, int arg3)
    {
    }

    public int getCount()
    {
      if (AlbumViewFragment.this.mAlbumList == null)
        return 0;
      return AlbumViewFragment.this.mAlbumList.size();
    }

    public Object getItem(int paramInt)
    {
      if (AlbumViewFragment.this.mAlbumList == null);
      do
        return null;
      while ((paramInt < 0) || (paramInt >= AlbumViewFragment.this.mAlbumList.size()));
      return AlbumViewFragment.this.mAlbumList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    // ERROR //
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      // Byte code:
      //   0: aload_0
      //   1: iload_1
      //   2: invokevirtual 39	com/kaixin001/fragment/AlbumViewFragment$AlbumListAdapter:getItem	(I)Ljava/lang/Object;
      //   5: checkcast 41	[Lcom/kaixin001/item/KaixinPhotoItem;
      //   8: astore 5
      //   10: aload_2
      //   11: ifnonnull +58 -> 69
      //   14: aload_0
      //   15: getfield 10	com/kaixin001/fragment/AlbumViewFragment$AlbumListAdapter:this$0	Lcom/kaixin001/fragment/AlbumViewFragment;
      //   18: invokevirtual 45	com/kaixin001/fragment/AlbumViewFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
      //   21: ldc 47
      //   23: invokevirtual 53	android/support/v4/app/FragmentActivity:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
      //   26: checkcast 55	android/view/LayoutInflater
      //   29: ldc 56
      //   31: aconst_null
      //   32: invokevirtual 60	android/view/LayoutInflater:inflate	(ILandroid/view/ViewGroup;)Landroid/view/View;
      //   35: astore_2
      //   36: new 62	com/kaixin001/fragment/AlbumViewFragment$AlbumItemViewTag
      //   39: dup
      //   40: aload_0
      //   41: getfield 10	com/kaixin001/fragment/AlbumViewFragment$AlbumListAdapter:this$0	Lcom/kaixin001/fragment/AlbumViewFragment;
      //   44: aload_2
      //   45: iload_1
      //   46: aconst_null
      //   47: invokespecial 65	com/kaixin001/fragment/AlbumViewFragment$AlbumItemViewTag:<init>	(Lcom/kaixin001/fragment/AlbumViewFragment;Landroid/view/View;ILcom/kaixin001/fragment/AlbumViewFragment$AlbumItemViewTag;)V
      //   50: astore 6
      //   52: aload_2
      //   53: aload 6
      //   55: invokevirtual 71	android/view/View:setTag	(Ljava/lang/Object;)V
      //   58: aload 6
      //   60: astore 7
      //   62: aload 7
      //   64: ifnonnull +17 -> 81
      //   67: aload_2
      //   68: areturn
      //   69: aload_2
      //   70: invokevirtual 75	android/view/View:getTag	()Ljava/lang/Object;
      //   73: checkcast 62	com/kaixin001/fragment/AlbumViewFragment$AlbumItemViewTag
      //   76: astore 7
      //   78: goto -16 -> 62
      //   81: iload_1
      //   82: iconst_m1
      //   83: aload_0
      //   84: getfield 10	com/kaixin001/fragment/AlbumViewFragment$AlbumListAdapter:this$0	Lcom/kaixin001/fragment/AlbumViewFragment;
      //   87: invokestatic 21	com/kaixin001/fragment/AlbumViewFragment:access$0	(Lcom/kaixin001/fragment/AlbumViewFragment;)Ljava/util/ArrayList;
      //   90: invokevirtual 26	java/util/ArrayList:size	()I
      //   93: iadd
      //   94: if_icmpne +68 -> 162
      //   97: aload_0
      //   98: getfield 10	com/kaixin001/fragment/AlbumViewFragment$AlbumListAdapter:this$0	Lcom/kaixin001/fragment/AlbumViewFragment;
      //   101: invokestatic 79	com/kaixin001/fragment/AlbumViewFragment:access$1	(Lcom/kaixin001/fragment/AlbumViewFragment;)I
      //   104: istore 8
      //   106: iconst_m1
      //   107: iload 8
      //   109: if_icmpne +33 -> 142
      //   112: aload_0
      //   113: getfield 10	com/kaixin001/fragment/AlbumViewFragment$AlbumListAdapter:this$0	Lcom/kaixin001/fragment/AlbumViewFragment;
      //   116: invokestatic 82	com/kaixin001/fragment/AlbumViewFragment:access$2	(Lcom/kaixin001/fragment/AlbumViewFragment;)Ljava/util/ArrayList;
      //   119: astore 9
      //   121: aload 9
      //   123: ifnonnull +29 -> 152
      //   126: iconst_m1
      //   127: aload_0
      //   128: invokevirtual 84	com/kaixin001/fragment/AlbumViewFragment$AlbumListAdapter:getCount	()I
      //   131: iadd
      //   132: aload_0
      //   133: getfield 10	com/kaixin001/fragment/AlbumViewFragment$AlbumListAdapter:this$0	Lcom/kaixin001/fragment/AlbumViewFragment;
      //   136: invokestatic 87	com/kaixin001/fragment/AlbumViewFragment:access$3	(Lcom/kaixin001/fragment/AlbumViewFragment;)I
      //   139: imul
      //   140: istore 8
      //   142: aload 7
      //   144: iload 8
      //   146: invokevirtual 91	com/kaixin001/fragment/AlbumViewFragment$AlbumItemViewTag:setTextViewButtom	(I)V
      //   149: goto +43 -> 192
      //   152: aload 9
      //   154: invokevirtual 26	java/util/ArrayList:size	()I
      //   157: istore 8
      //   159: goto -17 -> 142
      //   162: aload 7
      //   164: aload 5
      //   166: iload_1
      //   167: invokevirtual 95	com/kaixin001/fragment/AlbumViewFragment$AlbumItemViewTag:setPhotoItem	([Lcom/kaixin001/item/KaixinPhotoItem;I)V
      //   170: goto +22 -> 192
      //   173: astore 4
      //   175: ldc 97
      //   177: ldc 98
      //   179: aload 4
      //   181: invokestatic 104	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
      //   184: goto +8 -> 192
      //   187: astore 4
      //   189: goto -14 -> 175
      //   192: aload_2
      //   193: areturn
      //
      // Exception table:
      //   from	to	target	type
      //   0	10	173	java/lang/Exception
      //   14	52	173	java/lang/Exception
      //   69	78	173	java/lang/Exception
      //   81	106	173	java/lang/Exception
      //   112	121	173	java/lang/Exception
      //   126	142	173	java/lang/Exception
      //   142	149	173	java/lang/Exception
      //   152	159	173	java/lang/Exception
      //   162	170	173	java/lang/Exception
      //   52	58	187	java/lang/Exception
    }
  }

  private class GetDataTask extends KXFragment.KXAsyncTask<Integer, Void, Integer>
  {
    private int mStart = 0;

    private GetDataTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Integer[] paramArrayOfInteger)
    {
      int i = 24;
      if (paramArrayOfInteger != null);
      try
      {
        if (paramArrayOfInteger.length == 2)
        {
          this.mStart = paramArrayOfInteger[0].intValue();
          i = paramArrayOfInteger[1].intValue();
        }
        if (AlbumViewFragment.this.albumType == 1)
          return Integer.valueOf(AlbumPhotoEngine.getInstance().getLogoPhotoList(AlbumViewFragment.this.getActivity().getApplicationContext(), AlbumViewFragment.this.fuid, this.mStart, i));
        if (AlbumViewFragment.this.albumType == 2)
          return Integer.valueOf(AlbumPhotoEngine.getInstance().getAlbumPhotoList(AlbumViewFragment.this.getActivity().getApplicationContext(), AlbumViewFragment.this.mAlbumId, AlbumViewFragment.this.fuid, AlbumViewFragment.this.pwd, this.mStart, i));
        if (AlbumViewFragment.this.albumType == 4)
          return CheckinListEngine.getInstance().getPoiPhotoes(AlbumViewFragment.this.getActivity().getApplicationContext(), AlbumViewFragment.this.mAlbumId, this.mStart, i);
        Integer localInteger = Integer.valueOf(0);
        return localInteger;
      }
      catch (Exception localException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if (paramInteger == null)
      {
        Toast.makeText(AlbumViewFragment.this.getActivity(), 2131427485, 0).show();
        AlbumViewFragment.this.finish();
      }
      do
        while (true)
        {
          return;
          try
          {
            AlbumViewFragment.this.mWaitLayout.setVisibility(8);
            AlbumViewFragment.this.updateDataList();
            AlbumViewFragment.this.mListView.setVisibility(0);
            if (paramInteger.intValue() != 1)
              break;
            AlbumViewFragment.this.setStateOnFetchFinished(true);
            if ((this.mStart != 0) || (AlbumViewFragment.this.mPicCount <= 24))
              continue;
            AlbumViewFragment.this.refreshMore();
            return;
          }
          catch (Exception localException)
          {
            KXLog.e("AlbumViewActivity", "onPostExecute", localException);
            return;
          }
        }
      while (paramInteger.intValue() != 0);
      AlbumViewFragment.this.setStateOnFetchFinished(false);
      Toast.makeText(AlbumViewFragment.this.getActivity(), 2131427485, 0).show();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private static class PhotoLayoutItem
  {
    public ImageView mImageView = null;
    public LinearLayout mLayout = null;

    public PhotoLayoutItem(View paramView)
    {
      if (paramView == null)
        return;
      this.mLayout = ((LinearLayout)paramView);
      this.mImageView = ((ImageView)paramView.findViewById(2131363105));
    }

    public void setOnClickListener(AlbumViewFragment.AlbumItemListener paramAlbumItemListener)
    {
      if (this.mImageView != null)
        this.mImageView.setOnClickListener(paramAlbumItemListener);
    }

    public void setTag(int paramInt)
    {
      if (this.mImageView != null)
        this.mImageView.setTag(Integer.valueOf(paramInt));
    }

    public void setVisibility(int paramInt)
    {
      if (this.mLayout != null)
        this.mLayout.setVisibility(paramInt);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.AlbumViewFragment
 * JD-Core Version:    0.6.0
 */