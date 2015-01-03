package com.kaixin001.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.KXApplication;
import com.kaixin001.engine.CoverListEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.item.CoverItem;
import com.kaixin001.model.CoverListModel;
import com.kaixin001.model.User;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.UserHabitUtils;
import java.io.File;
import java.util.ArrayList;

public class CoverListFragment extends BaseFragment
  implements View.OnClickListener, AdapterView.OnItemClickListener
{
  public static final int CAMERA_WITH_DATA = 35454;
  public static final String COVER_ID_CUSTOM = "COVER_ID_CUSTOM";
  public static final int COVER_LIST = 25521;
  public static final int PHOTO_CLIP_CUSTOM = 42431;
  public static final int PHOTO_PICKED_WITH_DATA = 22586;
  public static final String TAG = "CoverListFragment";
  private CoverListAdapter adapter = null;
  private CoverItem currentItem = null;
  private int currentSelect = -1;
  private GetCoverListTask getCoverListTask = null;
  private GridView gridview = null;
  private String id = "";
  private LinearLayout loadingLayout = null;
  private SetCoverTask setCoverTask = null;
  private String url = "";

  private void cancelTask()
  {
    if ((this.getCoverListTask != null) && (!this.getCoverListTask.isCancelled()) && (this.getCoverListTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.getCoverListTask.cancel(true);
      CoverListEngine.getInstance().cancel();
      this.getCoverListTask = null;
    }
    if ((this.setCoverTask != null) && (!this.setCoverTask.isCancelled()) && (this.setCoverTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.setCoverTask.cancel(true);
      CoverListEngine.getInstance().cancel();
      this.setCoverTask = null;
    }
  }

  private void gotoClipActivity(String paramString)
  {
    Intent localIntent = new Intent("com.kaixin001.PHOTO_CLIP");
    localIntent.putExtra("filePath", paramString);
    startActivityForResult(localIntent, 42431);
  }

  private void gotoClipPhoto(String paramString)
  {
    File localFile = new File(paramString);
    if (!localFile.exists())
    {
      Toast.makeText(getActivity(), 2131427841, 0).show();
      return;
    }
    Intent localIntent = new Intent("com.android.camera.action.CROP");
    localIntent.setDataAndType(Uri.fromFile(localFile), "image/*");
    localIntent.putExtra("crop", "true");
    localIntent.putExtra("aspectX", 1);
    localIntent.putExtra("aspectY", 1);
    localIntent.putExtra("outputX", 320);
    localIntent.putExtra("outputY", 320);
    localIntent.putExtra("scale", true);
    localIntent.putExtra("noFaceDetection", true);
    localIntent.putExtra("return-data", true);
    startActivityForResult(localIntent, 22586);
  }

  public static boolean isNativeBackground()
  {
    return KXApplication.getInstance().getSharedPreferences("isHomeBgNativeSetted", 0).getBoolean("isSetted", false);
  }

  public static void setNativeBackground(boolean paramBoolean)
  {
    KXApplication.getInstance().getSharedPreferences("isHomeBgNativeSetted", 0).edit().putBoolean("isSetted", paramBoolean).commit();
  }

  private void setTitleBar()
  {
    ImageView localImageView = (ImageView)findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(this);
    ((ImageView)findViewById(2131362928)).setVisibility(8);
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setText(2131428467);
    localTextView.setVisibility(0);
  }

  protected void dealPhotoResult(String paramString1, String paramString2)
  {
    if ((getActivity() == null) || (TextUtils.isEmpty(paramString1)))
      return;
    gotoClipPhoto(paramString1);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramIntent == null);
    while (true)
    {
      return;
      Bundle localBundle = paramIntent.getExtras();
      if (localBundle == null)
        continue;
      if (paramInt1 == 22586)
      {
        String str2 = User.getInstance().getCoverPath();
        try
        {
          Bitmap localBitmap2 = (Bitmap)localBundle.getParcelable("data");
          ImageCache.saveBitmapToFileWithAbsolutePath(getActivity(), localBitmap2, null, str2);
          if (localBitmap2 == null)
            continue;
          Intent localIntent2 = new Intent();
          localIntent2.putExtra("from", 22586);
          localIntent2.putExtra("filepath", str2);
          setResult(-1, localIntent2);
          finish();
          return;
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
          return;
        }
      }
      if (paramInt1 != 42431)
        continue;
      String str1 = User.getInstance().getCoverPath();
      try
      {
        byte[] arrayOfByte = paramIntent.getByteArrayExtra("bitmap");
        Bitmap localBitmap1 = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length);
        if (localBitmap1 == null)
          continue;
        ImageCache.saveBitmapToFileWithAbsolutePath(getActivity(), localBitmap1, null, str1);
        Intent localIntent1 = new Intent();
        localIntent1.putExtra("from", 22586);
        localIntent1.putExtra("filepath", str1);
        setResult(-1, localIntent1);
        finish();
        return;
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace();
      }
    }
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131362914:
    }
    Intent localIntent = new Intent();
    localIntent.putExtra("url", this.url);
    setResult(0, localIntent);
    super.finish();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903091, paramViewGroup, false);
  }

  public void onDestroy()
  {
    cancelTask();
    super.onDestroy();
  }

  protected void onFragmentResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onFragmentResult(paramInt1, paramInt2, paramIntent);
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    this.currentItem = ((CoverItem)CoverListModel.getInstance().getCoverList().get(paramInt));
    if ((this.setCoverTask != null) && (!this.setCoverTask.isCancelled()) && (this.setCoverTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.setCoverTask.cancel(true);
      CoverListEngine.getInstance().cancel();
      this.setCoverTask = null;
    }
    if (this.currentItem.thumb.equals("ITEM_ADD"))
    {
      Intent localIntent = new Intent();
      localIntent.setClass(getActivity(), PhotoSelectFragment.class);
      localIntent.putExtra("single", true);
      AnimationUtil.startFragmentForResult(this, localIntent, 103, 1);
      UserHabitUtils.getInstance(getActivity()).addUserHabit("native_bg_click");
      return;
    }
    this.setCoverTask = new SetCoverTask(null);
    String[] arrayOfString = new String[2];
    arrayOfString[0] = this.currentItem.id;
    arrayOfString[1] = String.valueOf(paramInt);
    this.setCoverTask.execute(arrayOfString);
    Bundle localBundle = getArguments();
    if ((localBundle != null) && (localBundle.getString("from") != null) && (localBundle.getString("from").equals("KXNewsBarView")))
    {
      IntentUtil.showMyHomeFragment(this);
      return;
    }
    this.adapter.notifyDataSetChanged();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      String str = localBundle.getString("id");
      if (!TextUtils.isEmpty(str))
        this.id = str;
    }
    setTitleBar();
    this.loadingLayout = ((LinearLayout)findViewById(2131362156));
    this.gridview = ((GridView)findViewById(2131362155));
    this.adapter = new CoverListAdapter(this);
    this.gridview.setAdapter(this.adapter);
    this.gridview.setOnItemClickListener(this);
    this.getCoverListTask = new GetCoverListTask(null);
    this.getCoverListTask.execute(new Void[0]);
  }

  class CoverListAdapter extends BaseAdapter
  {
    private LayoutInflater inflater;
    ArrayList<CoverItem> mCoverList = null;

    CoverListAdapter(BaseFragment arg2)
    {
      Object localObject;
      this.inflater = LayoutInflater.from(localObject.getActivity());
    }

    public int getCount()
    {
      return this.mCoverList.size();
    }

    public Object getItem(int paramInt)
    {
      return this.mCoverList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      CoverItem localCoverItem = (CoverItem)getItem(paramInt);
      CoverListFragment.CoverViewHolder localCoverViewHolder;
      if (paramView == null)
      {
        paramView = this.inflater.inflate(2130903092, null);
        localCoverViewHolder = new CoverListFragment.CoverViewHolder(CoverListFragment.this);
        localCoverViewHolder.image = ((ImageView)paramView.findViewById(2131362157));
        localCoverViewHolder.selected = ((ImageView)paramView.findViewById(2131362159));
        localCoverViewHolder.frame = ((ImageView)paramView.findViewById(2131362158));
        paramView.setTag(localCoverViewHolder);
        if (!localCoverItem.thumb.equals("ITEM_ADD"))
          break label164;
        localCoverViewHolder.frame.setVisibility(8);
        localCoverViewHolder.image.setScaleType(ImageView.ScaleType.FIT_XY);
        localCoverViewHolder.image.setImageResource(2130837525);
      }
      while (true)
      {
        if (CoverListFragment.this.currentSelect != paramInt)
          break label206;
        localCoverViewHolder.selected.setVisibility(0);
        return paramView;
        localCoverViewHolder = (CoverListFragment.CoverViewHolder)paramView.getTag();
        break;
        label164: localCoverViewHolder.frame.setVisibility(0);
        localCoverViewHolder.image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        CoverListFragment.this.displayPicture(localCoverViewHolder.image, localCoverItem.thumb, 2130837742);
      }
      label206: localCoverViewHolder.selected.setVisibility(8);
      return paramView;
    }
  }

  class CoverViewHolder
  {
    ImageView frame;
    ImageView image;
    ImageView selected;

    CoverViewHolder()
    {
    }
  }

  private class GetCoverListTask extends KXFragment.KXAsyncTask<Void, Void, Boolean>
  {
    private GetCoverListTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(Void[] paramArrayOfVoid)
    {
      try
      {
        Boolean.valueOf(false);
        Boolean localBoolean = Boolean.valueOf(CoverListEngine.getInstance().doGetCoverListRequest(CoverListFragment.this.getActivity().getApplicationContext()));
        return localBoolean;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return Boolean.valueOf(true);
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      while (true)
      {
        int i;
        try
        {
          CoverListFragment.this.loadingLayout.setVisibility(8);
          if (!paramBoolean.booleanValue())
            continue;
          CoverListFragment.this.gridview.setVisibility(0);
          ArrayList localArrayList = CoverListModel.getInstance().getCoverList();
          i = 0;
          if (i < localArrayList.size())
            continue;
          CoverListFragment.this.adapter.notifyDataSetChanged();
          CoverListFragment.this.getCoverListTask = null;
          return;
          if (((CoverItem)localArrayList.get(i)).id.equals(CoverListFragment.this.id))
          {
            CoverListFragment.this.currentSelect = i;
            break label134;
            Toast.makeText(CoverListFragment.this.getActivity(), 2131427547, 0).show();
            continue;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("CoverListFragment", "onPostExecute", localException);
          return;
        }
        label134: i++;
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class SetCoverTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    int position = -1;

    private SetCoverTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      Boolean localBoolean1;
      try
      {
        localBoolean1 = Boolean.valueOf(false);
        if (paramArrayOfString != null)
        {
          if (paramArrayOfString.length != 2)
            return localBoolean1;
          String str = paramArrayOfString[0];
          this.position = Integer.valueOf(paramArrayOfString[1]).intValue();
          Boolean localBoolean2 = Boolean.valueOf(CoverListEngine.getInstance().doSetCoverListRequest(CoverListFragment.this.getActivity().getApplicationContext(), str));
          return localBoolean2;
        }
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        localBoolean1 = Boolean.valueOf(true);
      }
      return localBoolean1;
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      while (true)
      {
        try
        {
          if ((CoverListFragment.this.getArguments() != null) && (CoverListFragment.this.getArguments().getString("from") != null) && (CoverListFragment.this.getArguments().getString("from").equals("KXNewsBarView")))
            return;
          CoverListFragment.this.loadingLayout.setVisibility(8);
          if (!paramBoolean.booleanValue())
          {
            Toast.makeText(CoverListFragment.this.getActivity(), 2131428096, 0).show();
            CoverListFragment.this.setCoverTask = null;
            Intent localIntent = new Intent();
            localIntent.putExtra("url", CoverListFragment.this.url);
            localIntent.putExtra("from", 25521);
            CoverListFragment.this.setResult(-1, localIntent);
            CoverListFragment.this.finish();
            return;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("CoverListFragment", "onPostExecute", localException);
          return;
        }
        CoverListFragment.this.currentSelect = this.position;
        User localUser = User.getInstance();
        if (CoverListFragment.this.currentItem != null)
        {
          localUser.setCoverUrl(CoverListFragment.this.currentItem.url);
          localUser.setCoverId(CoverListFragment.this.currentItem.id);
        }
        CoverListFragment.this.adapter.notifyDataSetChanged();
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.CoverListFragment
 * JD-Core Version:    0.6.0
 */