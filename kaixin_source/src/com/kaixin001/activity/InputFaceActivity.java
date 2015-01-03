package com.kaixin001.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.kaixin001.model.FaceModel;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.IntentUtil;
import java.util.ArrayList;

public class InputFaceActivity extends KXActivity
{
  public static final int SELECT_MESSAGE_FACE_ICON = 109;
  public static final int SELECT_STATE_FACE_ICON = 209;
  private ArrayList<Bitmap> mFaceIcons;
  private FaceModel mFaceModel;

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (CrashRecoverUtil.isCrashBefore())
    {
      CrashRecoverUtil.crashRecover(getApplicationContext());
      IntentUtil.returnToDesktop(this);
      return;
    }
    requestWindowFeature(1);
    getWindow().setFlags(131072, 131072);
    setContentView(2130903177);
    this.mFaceModel = FaceModel.getInstance();
    this.mFaceIcons = new ArrayList(this.mFaceModel.getStateFaceIcons());
    GridView localGridView = (GridView)findViewById(2131362862);
    localGridView.setAdapter(new FaceListAdapter(null));
    localGridView.setOnItemClickListener(new FaceListOnItemClickListener(null));
    ((Button)findViewById(2131362863)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        InputFaceActivity.this.finish();
      }
    });
  }

  protected void onDestroy()
  {
    if (this.mFaceIcons != null)
    {
      this.mFaceIcons.clear();
      this.mFaceIcons = null;
    }
    super.onDestroy();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (84 == paramInt)
      return true;
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  private class FaceListAdapter extends BaseAdapter
  {
    private FaceListAdapter()
    {
    }

    public int getCount()
    {
      if (InputFaceActivity.this.mFaceIcons != null)
        return InputFaceActivity.this.mFaceIcons.size();
      return 0;
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
      ImageView localImageView;
      if (paramView == null)
      {
        localImageView = new ImageView(InputFaceActivity.this);
        localImageView.setLayoutParams(new AbsListView.LayoutParams(50, 50));
        localImageView.setScaleType(ImageView.ScaleType.CENTER);
      }
      while (true)
      {
        localImageView.setImageBitmap((Bitmap)InputFaceActivity.this.mFaceIcons.get(paramInt));
        return localImageView;
        localImageView = (ImageView)paramView;
      }
    }
  }

  private class FaceListOnItemClickListener
    implements AdapterView.OnItemClickListener
  {
    private FaceListOnItemClickListener()
    {
    }

    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
    {
      Intent localIntent = new Intent();
      localIntent.putExtra("face", paramInt);
      InputFaceActivity.this.setResult(-1, localIntent);
      InputFaceActivity.this.finishActivity(209);
      InputFaceActivity.this.finish();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.InputFaceActivity
 * JD-Core Version:    0.6.0
 */