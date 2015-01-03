package com.kaixin001.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.kaixin001.engine.AsyncLocalImageLoader;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.item.CloudAlbumPicItem;
import com.kaixin001.util.CloudAlbumManager;
import com.kaixin001.util.ImageCache;
import java.io.File;

public class KXCloudAlbumPicView extends FrameLayout
  implements View.OnClickListener
{
  private AsyncLocalImageLoader mAsyncLocalImageLoader;
  private View mFailedLayout;
  private BaseFragment mFragment;
  private View mGrayView;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      if ((paramMessage != null) && (KXCloudAlbumPicView.this.mFragment != null) && (KXCloudAlbumPicView.this.mItemData != null))
      {
        String str = (String)paramMessage.obj;
        if (KXCloudAlbumPicView.this.mItemData.mMD5.equals(str))
        {
          KXCloudAlbumPicView.this.mItemData.mState = paramMessage.arg1;
          KXCloudAlbumPicView.this.mItemData.mPercent = paramMessage.arg2;
          KXCloudAlbumPicView.this.update(KXCloudAlbumPicView.this.mFragment, KXCloudAlbumPicView.this.mAsyncLocalImageLoader, KXCloudAlbumPicView.this.mItemData);
        }
      }
    }
  };
  private ImageView mImage;
  private CloudAlbumPicItem mItemData;
  private View mProgressBarLayout;
  private ImageView mProgressIndication;

  public KXCloudAlbumPicView(Context paramContext)
  {
    super(paramContext);
    initView(paramContext);
  }

  public KXCloudAlbumPicView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initView(paramContext);
  }

  public KXCloudAlbumPicView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initView(paramContext);
  }

  private void initView(Context paramContext)
  {
    View localView = ((LayoutInflater)paramContext.getSystemService("layout_inflater")).inflate(2130903400, this);
    this.mImage = ((ImageView)localView.findViewById(2131363931));
    this.mGrayView = localView.findViewById(2131363933);
    this.mProgressBarLayout = localView.findViewById(2131363935);
    this.mProgressIndication = ((ImageView)localView.findViewById(2131363937));
    this.mFailedLayout = localView.findViewById(2131363934);
    this.mFailedLayout.setOnClickListener(this);
  }

  public Handler getHandler()
  {
    return this.mHandler;
  }

  public void onClick(View paramView)
  {
    try
    {
      this.mItemData.mState = 0;
      File[] arrayOfFile = new File[1];
      arrayOfFile[0] = new File(this.mItemData.mUrl);
      CloudAlbumManager.getInstance().postImages(arrayOfFile);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void setAsyncLocalImageLoader(AsyncLocalImageLoader paramAsyncLocalImageLoader)
  {
    this.mAsyncLocalImageLoader = paramAsyncLocalImageLoader;
  }

  public void update(BaseFragment paramBaseFragment, AsyncLocalImageLoader paramAsyncLocalImageLoader, CloudAlbumPicItem paramCloudAlbumPicItem)
  {
    this.mFragment = paramBaseFragment;
    CloudAlbumPicItem localCloudAlbumPicItem = this.mItemData;
    this.mItemData = paramCloudAlbumPicItem;
    label111: int i;
    label128: View localView2;
    if (paramCloudAlbumPicItem.mIsLocalAlbumPic)
    {
      paramBaseFragment.displayPictureCancel(this.mImage);
      if ((this.mItemData != null) && (localCloudAlbumPicItem != null) && (localCloudAlbumPicItem.mThumbUrl != null) && (localCloudAlbumPicItem.mThumbUrl.equals(this.mItemData.mThumbUrl)))
      {
        if (this.mAsyncLocalImageLoader != null)
          this.mAsyncLocalImageLoader.showImage(this.mImage, paramCloudAlbumPicItem.mThumbUrl, 0, null, Integer.valueOf(2130837713));
        View localView1 = this.mFailedLayout;
        if (paramCloudAlbumPicItem.mState != 3)
          break label277;
        i = 0;
        localView1.setVisibility(i);
        localView2 = this.mGrayView;
        if (paramCloudAlbumPicItem.mState != 1)
          break label284;
      }
    }
    label277: label284: for (int j = 8; ; j = 0)
    {
      localView2.setVisibility(j);
      if (paramCloudAlbumPicItem.mState != 2)
        break label290;
      this.mProgressBarLayout.setVisibility(0);
      float f = this.mProgressBarLayout.getWidth();
      ViewGroup.LayoutParams localLayoutParams = this.mProgressIndication.getLayoutParams();
      localLayoutParams.width = (int)(f * paramCloudAlbumPicItem.mPercent / 100.0F);
      this.mProgressIndication.setLayoutParams(localLayoutParams);
      return;
      this.mImage.setImageBitmap(ImageCache.getInstance().getLoadingBitmap(2130837713, 0, 0));
      break;
      if (this.mAsyncLocalImageLoader != null)
        this.mAsyncLocalImageLoader.showImageCancel(this.mImage);
      paramBaseFragment.displayPicture(this.mImage, paramCloudAlbumPicItem.mThumbUrl, 2130837713);
      break label111;
      i = 8;
      break label128;
    }
    label290: this.mProgressBarLayout.setVisibility(8);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXCloudAlbumPicView
 * JD-Core Version:    0.6.0
 */