package com.kaixin001.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.kaixin001.engine.AsyncLocalImageLoader;
import com.kaixin001.model.EditPictureModel;
import com.kaixin001.util.AnimationUtil;
import java.util.ArrayList;

public class UploadPhotoBulkListFragment extends BaseFragment
  implements AdapterView.OnItemClickListener
{
  public static final int BULK_UPLOAD_BACK = 4;
  private static final int BULK_UPLOAD_PHOTO = 1;
  private static final int BULK_UPLOAD_PHOTO_EFFECTS = 2;
  private static final int BULK_UPLOAD_PHOTO_INTO = 3;
  private static final String CALLS_COUNT = "calls_count";
  private Cursor cursor;
  private ListView listView;
  private AsyncLocalImageLoader mImageLoader;

  private void initListView()
  {
    String[] arrayOfString = { "_id", "bucket_display_name", "bucket_id", "COUNT(*) AS calls_count" };
    this.cursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOfString, "1=1) GROUP BY (bucket_id", null, null);
    if (this.cursor == null)
      ((TextView)findViewById(2131363850)).setVisibility(0);
    BulkCursorAdapter localBulkCursorAdapter = new BulkCursorAdapter(getActivity(), 2130903388, this.cursor, new String[0], new int[0]);
    this.listView = ((ListView)findViewById(2131363849));
    this.listView.setAdapter(localBulkCursorAdapter);
    this.listView.setOnItemClickListener(this);
  }

  private void initTitleBar()
  {
    ImageView localImageView1 = (ImageView)findViewById(2131362914);
    localImageView1.setVisibility(0);
    localImageView1.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        UploadPhotoBulkListFragment.this.finish();
      }
    });
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setText(2131427809);
    localTextView.setVisibility(0);
    ImageView localImageView2 = (ImageView)findViewById(2131362928);
    localImageView2.setImageResource(2130839016);
    localImageView2.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        UploadPhotoBulkListFragment.this.finish();
      }
    });
    localImageView2.setVisibility(8);
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 2);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    ArrayList localArrayList;
    if (paramInt1 == 1)
      if (paramIntent != null)
      {
        localArrayList = paramIntent.getStringArrayListExtra("selection");
        if ((localArrayList != null) && (localArrayList.size() > 0))
        {
          if (localArrayList.size() != 1)
            break label102;
          paramIntent.setClass(getActivity(), IFEditPhotoFragment.class);
          Bundle localBundle = new Bundle();
          localBundle.putString("filePathName", (String)localArrayList.get(0));
          localBundle.putString("fileFrom", paramIntent.getStringExtra("display_name"));
          paramIntent.putExtras(localBundle);
          startActivityForResult(paramIntent, 2);
        }
      }
    label102: 
    do
    {
      return;
      EditPictureModel.setPicFrom("display_name");
      paramIntent.setClass(getActivity(), UploadPhotoFragment.class);
      paramIntent.putStringArrayListExtra("selection", localArrayList);
      startActivityForResult(paramIntent, 3);
      return;
      if (paramInt1 != 2)
        continue;
      if (paramIntent != null)
      {
        EditPictureModel.setPicFrom("display_name");
        paramIntent.setClass(getActivity(), UploadPhotoFragment.class);
        startActivityForResult(paramIntent, 3);
        return;
      }
      finish();
      return;
    }
    while (paramInt2 != 4);
    finish();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903387, paramViewGroup, false);
  }

  public void onDestroy()
  {
    super.onDestroy();
    if ((this.cursor != null) && (this.cursor.isClosed()))
    {
      this.cursor.close();
      this.cursor = null;
    }
    this.mImageLoader.stop();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    ViewHolder localViewHolder = (ViewHolder)paramView.getTag();
    Intent localIntent = new Intent();
    localIntent.setClass(getActivity(), MultiPicSelectorFragment.class);
    localIntent.putExtra("bucket_id", localViewHolder.bulketId);
    startActivityForResult(localIntent, 1);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mImageLoader = new AsyncLocalImageLoader(getActivity());
    initTitleBar();
    initListView();
  }

  private class BulkCursorAdapter extends SimpleCursorAdapter
  {
    private int mIndexBucketId;
    private int mIndexCount;
    private int mIndexDisplayName;
    private int mIndexId;

    public BulkCursorAdapter(Context paramInt, int paramCursor, Cursor paramArrayOfString, String[] paramArrayOfInt, int[] arg6)
    {
      super(paramCursor, paramArrayOfString, paramArrayOfInt, arrayOfInt);
      if (paramArrayOfString != null)
      {
        this.mIndexDisplayName = paramArrayOfString.getColumnIndex("bucket_display_name");
        this.mIndexCount = paramArrayOfString.getColumnIndex("calls_count");
        this.mIndexBucketId = paramArrayOfString.getColumnIndex("bucket_id");
        this.mIndexId = paramArrayOfString.getColumnIndex("_id");
      }
    }

    public void bindView(View paramView, Context paramContext, Cursor paramCursor)
    {
      UploadPhotoBulkListFragment.ViewHolder localViewHolder = (UploadPhotoBulkListFragment.ViewHolder)paramView.getTag();
      UploadPhotoBulkListFragment.this.mImageLoader.showImage(localViewHolder.iv, paramCursor.getString(this.mIndexId), 0);
      String str = "<b>" + paramCursor.getString(this.mIndexDisplayName) + "</b> <font color=\"#888888\">(" + paramCursor.getString(this.mIndexCount) + ")</font>";
      localViewHolder.tv.setText(Html.fromHtml(str));
      localViewHolder.bulketId = paramCursor.getString(this.mIndexBucketId);
      super.bindView(paramView, paramContext, paramCursor);
    }

    public View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
    {
      View localView = super.newView(paramContext, paramCursor, paramViewGroup);
      UploadPhotoBulkListFragment.ViewHolder localViewHolder = new UploadPhotoBulkListFragment.ViewHolder(UploadPhotoBulkListFragment.this, null);
      localViewHolder.iv = ((ImageView)localView.findViewById(2131363851));
      localViewHolder.tv = ((TextView)localView.findViewById(2131363852));
      localView.setTag(localViewHolder);
      return localView;
    }
  }

  private class ViewHolder
  {
    String bulketId;
    ImageView iv;
    TextView tv;

    private ViewHolder()
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.UploadPhotoBulkListFragment
 * JD-Core Version:    0.6.0
 */