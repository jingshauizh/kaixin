package com.kaixin001.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import com.kaixin001.engine.CircleAlbumEngine;
import com.kaixin001.item.CircleAlbumItem;
import com.kaixin001.item.KaixinPhotoItem;
import com.kaixin001.model.CircleAlbumModel;
import com.kaixin001.model.KaixinModelTemplate;
import com.kaixin001.model.User;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.ScreenUtil;
import java.util.ArrayList;

public class CircleAlbumViewFragment extends BaseFragment
  implements View.OnClickListener
{
  private static final String TAG = "CircleAlbumViewActivity";
  private GetPhotoTask getPhotoTask = null;
  private CircleAlbumListAdapter mAdapter = null;
  private final ArrayList<KaixinPhotoItem[]> mAlbumList = new ArrayList();
  private ListView mAlbumListView = null;
  private String mCircleAlbumTitle = null;
  private String mGid = null;
  private int mOrientation = 0;
  private int mScreenWidth;
  private View mWaitLayout;
  private AlbumItemListener photoTouchListener;

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

  private void initPhotoList()
  {
    this.mWaitLayout.setVisibility(0);
    this.mAlbumListView.setVisibility(8);
    String[] arrayOfString = new String[2];
    arrayOfString[0] = this.mGid;
    arrayOfString[1] = String.valueOf(0);
    try
    {
      if ((this.getPhotoTask != null) && (!this.getPhotoTask.isCancelled()) && (this.getPhotoTask.getStatus() != AsyncTask.Status.FINISHED))
      {
        this.getPhotoTask.cancel(true);
        CircleAlbumModel.getInstance().clear();
      }
      this.getPhotoTask = new GetPhotoTask(null);
      this.getPhotoTask.execute(arrayOfString);
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("CircleAlbumViewActivity", "InitPhotoList");
      localException.printStackTrace();
    }
  }

  private void refreshMoreData()
  {
    if ((this.getPhotoTask != null) && (!this.getPhotoTask.isCancelled()) && (this.getPhotoTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    String[] arrayOfString = new String[2];
    arrayOfString[0] = this.mGid;
    arrayOfString[1] = String.valueOf(0);
    ArrayList localArrayList = CircleAlbumModel.getInstance().mCirclePhotoList.getItemList();
    if (localArrayList != null)
      arrayOfString[1] = String.valueOf(localArrayList.size());
    this.getPhotoTask = new GetPhotoTask(null);
    this.getPhotoTask.execute(arrayOfString);
  }

  private boolean setPhotoList()
  {
    ArrayList localArrayList = CircleAlbumModel.getInstance().mCirclePhotoList.getItemList();
    if (localArrayList == null)
      return false;
    int i = getColumnCount();
    int j = (localArrayList.size() + (i - 1)) / i;
    int k = localArrayList.size() % i;
    this.mAlbumList.clear();
    try
    {
      int n;
      KaixinPhotoItem[] arrayOfKaixinPhotoItem2;
      if (localArrayList.size() > 0)
      {
        m = 0;
        n = 0;
        break label201;
        arrayOfKaixinPhotoItem2 = new KaixinPhotoItem[k];
      }
      else
      {
        for (int i2 = 0; ; i2++)
        {
          if (i2 >= k)
          {
            this.mAlbumList.add(arrayOfKaixinPhotoItem2);
            if (this.mAdapter != null)
              this.mAdapter.notifyDataSetChanged();
            return true;
            KaixinPhotoItem[] arrayOfKaixinPhotoItem1 = new KaixinPhotoItem[i];
            for (int i1 = 0; ; i1++)
            {
              if (i1 >= i)
              {
                n += i;
                this.mAlbumList.add(arrayOfKaixinPhotoItem1);
                m++;
                break;
              }
              arrayOfKaixinPhotoItem1[i1] = ((KaixinPhotoItem)localArrayList.get(n + i1));
            }
          }
          arrayOfKaixinPhotoItem2[i2] = ((KaixinPhotoItem)localArrayList.get(n + i2));
        }
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        int m;
        KXLog.e("CircleAlbumViewActivity", "setPhotoList");
        continue;
        label201: if (m < j - 1)
          continue;
        if (k != 0)
          continue;
        k = i;
      }
    }
  }

  private void setTitle(View paramView)
  {
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    String str;
    if (this.mCircleAlbumTitle == null)
      str = getResources().getString(2131428070);
    while (true)
    {
      localTextView.setText(str);
      localTextView.setVisibility(0);
      ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
      ((ImageView)paramView.findViewById(2131362914)).setOnClickListener(this);
      ((ImageView)paramView.findViewById(2131362928)).setVisibility(8);
      return;
      if (this.mCircleAlbumTitle.length() > 6)
      {
        str = this.mCircleAlbumTitle.substring(0, 6) + "...";
        continue;
      }
      str = this.mCircleAlbumTitle;
    }
  }

  public void onClick(View paramView)
  {
    finish();
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    this.mOrientation = paramConfiguration.orientation;
    this.mScreenWidth = ScreenUtil.getScreenWidth(getActivity());
    try
    {
      setPhotoList();
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
    if ((str != null) && (!str.equals("com.kaixin001.VIEW_CIRCLE_ALBUM_DETAIL")) && (CrashRecoverUtil.isCrashBefore()))
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
    if ((this.getPhotoTask != null) && (!this.getPhotoTask.isCancelled()) && (this.getPhotoTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.getPhotoTask.cancel(true);
      CircleAlbumEngine.getInstance().cancel();
    }
    if (CircleAlbumModel.getInstance() != null)
      CircleAlbumModel.getInstance().clear();
    super.onDestroy();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    User.getInstance().loadDataIfEmpty(getActivity().getApplicationContext());
    this.photoTouchListener = new AlbumItemListener(null);
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      this.mGid = localBundle.getString("gid");
      this.mCircleAlbumTitle = localBundle.getString("albumTitle");
    }
    setTitle(paramView);
    this.mWaitLayout = paramView.findViewById(2131361854);
    this.mAlbumListView = ((ListView)paramView.findViewById(2131361857));
    this.mAlbumListView.setOnScrollListener(new AbsListView.OnScrollListener()
    {
      public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
      {
        ArrayList localArrayList = CircleAlbumModel.getInstance().mCirclePhotoList.getItemList();
        if (localArrayList == null);
        int i;
        do
        {
          return;
          i = CircleAlbumViewFragment.this.getColumnCount() * (paramInt1 + paramInt2);
        }
        while ((CircleAlbumModel.getInstance().mCirclePhotoList.total <= localArrayList.size()) || (localArrayList.size() - i > 20));
        CircleAlbumViewFragment.this.refreshMoreData();
      }

      public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
      {
      }
    });
    if (this.mAdapter == null)
      this.mAdapter = new CircleAlbumListAdapter(getActivity(), 2130903056);
    this.mAlbumListView.setAdapter(this.mAdapter);
    this.mOrientation = ScreenUtil.getOrientation(getActivity());
    this.mScreenWidth = ScreenUtil.getScreenWidth(getActivity());
    initPhotoList();
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
      int j = CircleAlbumViewFragment.this.getColumnCount();
      int k = i / j;
      int m = i % j;
      CircleAlbumItem localCircleAlbumItem = (CircleAlbumItem)((KaixinPhotoItem[])CircleAlbumViewFragment.this.mAlbumList.get(k))[m];
      IntentUtil.showViewPhotoActivity4Circle(CircleAlbumViewFragment.this.getActivity(), CircleAlbumViewFragment.this, String.valueOf(i + 1), localCircleAlbumItem.title, localCircleAlbumItem.albumId, localCircleAlbumItem.gid, CircleAlbumViewFragment.this.mCircleAlbumTitle, true);
    }
  }

  private class AlbumItemViewTag
  {
    private static final int MAX_PICS = 6;
    private CircleAlbumViewFragment.PhotoLayoutItem[] mPhotos = new CircleAlbumViewFragment.PhotoLayoutItem[6];
    private TextView mTxtButtom = null;

    private AlbumItemViewTag(View paramInt, int arg3)
    {
      for (int i = 0; ; i++)
      {
        if (i >= this.mPhotos.length)
        {
          this.mPhotos[0] = new CircleAlbumViewFragment.PhotoLayoutItem(paramInt.findViewById(2131361859));
          this.mPhotos[1] = new CircleAlbumViewFragment.PhotoLayoutItem(paramInt.findViewById(2131361860));
          this.mPhotos[2] = new CircleAlbumViewFragment.PhotoLayoutItem(paramInt.findViewById(2131361861));
          this.mPhotos[3] = new CircleAlbumViewFragment.PhotoLayoutItem(paramInt.findViewById(2131361862));
          this.mPhotos[4] = new CircleAlbumViewFragment.PhotoLayoutItem(paramInt.findViewById(2131361863));
          this.mPhotos[5] = new CircleAlbumViewFragment.PhotoLayoutItem(paramInt.findViewById(2131361864));
          this.mTxtButtom = ((TextView)paramInt.findViewById(2131361865));
          return;
        }
        this.mPhotos[i] = null;
      }
    }

    public void setPhotoItem(KaixinPhotoItem[] paramArrayOfKaixinPhotoItem, int paramInt)
    {
      this.mTxtButtom.setVisibility(8);
      int i = CircleAlbumViewFragment.this.getColumnCount();
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
            CircleAlbumViewFragment.this.displayPicture(this.mPhotos[j].mImageView, paramArrayOfKaixinPhotoItem[j].large, 2130838779);
            this.mPhotos[j].setTag(j + paramInt * i);
            this.mPhotos[j].setOnClickListener(CircleAlbumViewFragment.this.photoTouchListener);
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
  }

  private class CircleAlbumListAdapter extends BaseAdapter
  {
    public CircleAlbumListAdapter(Context paramInt, int arg3)
    {
    }

    public int getCount()
    {
      if (CircleAlbumViewFragment.this.mAlbumList == null)
        return 0;
      return CircleAlbumViewFragment.this.mAlbumList.size();
    }

    public Object getItem(int paramInt)
    {
      if (CircleAlbumViewFragment.this.mAlbumList == null);
      do
        return null;
      while ((paramInt < 0) || (paramInt >= CircleAlbumViewFragment.this.mAlbumList.size()));
      return CircleAlbumViewFragment.this.mAlbumList.get(paramInt);
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
      //   2: invokevirtual 39	com/kaixin001/fragment/CircleAlbumViewFragment$CircleAlbumListAdapter:getItem	(I)Ljava/lang/Object;
      //   5: checkcast 41	[Lcom/kaixin001/item/KaixinPhotoItem;
      //   8: astore 5
      //   10: aload_2
      //   11: ifnonnull +58 -> 69
      //   14: aload_0
      //   15: getfield 10	com/kaixin001/fragment/CircleAlbumViewFragment$CircleAlbumListAdapter:this$0	Lcom/kaixin001/fragment/CircleAlbumViewFragment;
      //   18: invokevirtual 45	com/kaixin001/fragment/CircleAlbumViewFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
      //   21: ldc 47
      //   23: invokevirtual 53	android/support/v4/app/FragmentActivity:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
      //   26: checkcast 55	android/view/LayoutInflater
      //   29: ldc 56
      //   31: aconst_null
      //   32: invokevirtual 60	android/view/LayoutInflater:inflate	(ILandroid/view/ViewGroup;)Landroid/view/View;
      //   35: astore_2
      //   36: new 62	com/kaixin001/fragment/CircleAlbumViewFragment$AlbumItemViewTag
      //   39: dup
      //   40: aload_0
      //   41: getfield 10	com/kaixin001/fragment/CircleAlbumViewFragment$CircleAlbumListAdapter:this$0	Lcom/kaixin001/fragment/CircleAlbumViewFragment;
      //   44: aload_2
      //   45: iload_1
      //   46: aconst_null
      //   47: invokespecial 65	com/kaixin001/fragment/CircleAlbumViewFragment$AlbumItemViewTag:<init>	(Lcom/kaixin001/fragment/CircleAlbumViewFragment;Landroid/view/View;ILcom/kaixin001/fragment/CircleAlbumViewFragment$AlbumItemViewTag;)V
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
      //   73: checkcast 62	com/kaixin001/fragment/CircleAlbumViewFragment$AlbumItemViewTag
      //   76: astore 7
      //   78: goto -16 -> 62
      //   81: aload 7
      //   83: aload 5
      //   85: iload_1
      //   86: invokevirtual 79	com/kaixin001/fragment/CircleAlbumViewFragment$AlbumItemViewTag:setPhotoItem	([Lcom/kaixin001/item/KaixinPhotoItem;I)V
      //   89: aload_2
      //   90: areturn
      //   91: astore 4
      //   93: ldc 81
      //   95: ldc 82
      //   97: aload 4
      //   99: invokestatic 88	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
      //   102: goto -13 -> 89
      //   105: astore 4
      //   107: goto -14 -> 93
      //
      // Exception table:
      //   from	to	target	type
      //   0	10	91	java/lang/Exception
      //   14	52	91	java/lang/Exception
      //   69	78	91	java/lang/Exception
      //   81	89	91	java/lang/Exception
      //   52	58	105	java/lang/Exception
    }
  }

  private class GetPhotoTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    String gid = "";
    int mStart = 0;

    private GetPhotoTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      if (paramArrayOfString != null);
      try
      {
        if (paramArrayOfString.length == 2)
        {
          this.gid = paramArrayOfString[0];
          this.mStart = Integer.parseInt(paramArrayOfString[1]);
          return Integer.valueOf(CircleAlbumEngine.getInstance().getCirclePhotoList(CircleAlbumViewFragment.this.getActivity().getApplicationContext(), this.mStart, this.gid));
        }
        Integer localInteger = Integer.valueOf(0);
        return localInteger;
      }
      catch (Exception localException)
      {
        KXLog.e("CircleAlbumViewActivity", "doInBackground");
        localException.printStackTrace();
      }
      return Integer.valueOf(0);
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if (paramInteger.intValue() == 0)
        Toast.makeText(CircleAlbumViewFragment.this.getActivity(), 2131427485, 0).show();
      while (true)
      {
        return;
        try
        {
          CircleAlbumViewFragment.this.mWaitLayout.setVisibility(8);
          CircleAlbumViewFragment.this.mAlbumListView.setVisibility(0);
          CircleAlbumViewFragment.this.setPhotoList();
          if (this.mStart != 0)
            continue;
          CircleAlbumViewFragment.this.refreshMoreData();
          return;
        }
        catch (Exception localException)
        {
          KXLog.e("CircleAlbumViewActivity", "onPostExecute");
          localException.printStackTrace();
        }
      }
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

    public void setOnClickListener(CircleAlbumViewFragment.AlbumItemListener paramAlbumItemListener)
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
 * Qualified Name:     com.kaixin001.fragment.CircleAlbumViewFragment
 * JD-Core Version:    0.6.0
 */