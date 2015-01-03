package com.kaixin001.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.model.EditPictureModel;
import com.kaixin001.model.EventModel.EventData;
import com.kaixin001.model.User;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.BitmapFilterUtils;
import com.kaixin001.util.BitmapFrameUtils;
import com.kaixin001.util.CloseUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.KXLog;
import com.kaixin001.view.KXAccessoryView;
import com.kaixin001.view.KXMergeFrameImageView;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class IFEditPhotoFragment extends BaseFragment
  implements View.OnClickListener, DialogInterface.OnClickListener
{
  public static int BITMAP_MAX_LENGTH = 0;
  public static final int CHIP_FILE_DOWN = 6;
  public static final int CHIP_FILE_LEFT = 0;
  public static final int CHIP_FILE_LEFTDOWN = 7;
  public static final int CHIP_FILE_LEFTUP = 1;
  private static final String[] CHIP_FILE_PATHS;
  public static final int CHIP_FILE_RIGHT = 4;
  public static final int CHIP_FILE_RIGHTDOWN = 5;
  public static final int CHIP_FILE_RIGHT_UP = 3;
  public static final int CHIP_FILE_UP = 2;
  private static final int EFFECT_TYPE1 = 1;
  private static final int EFFECT_TYPE10 = 10;
  private static final int EFFECT_TYPE11 = 11;
  private static final int EFFECT_TYPE12 = 12;
  private static final int EFFECT_TYPE2 = 2;
  private static final int EFFECT_TYPE3 = 3;
  private static final int EFFECT_TYPE5 = 5;
  private static final int EFFECT_TYPE6 = 6;
  private static final int EFFECT_TYPE7 = 7;
  private static final int EFFECT_TYPE8 = 8;
  public static final int REQUEST_ID_WATERMARK = 305;
  private static final String[] SINGLE_FILE_PATHS;
  private static final String TAG = "IFEditPhotoActivity";
  private static final int TYPE_EFFECT = 0;
  private static final int TYPE_FACE = 2;
  private static final int TYPE_FRAME = 1;
  private static final int TYPE_NONE = -1;
  private Bitmap[] frameChips;
  private boolean hasEdit = false;
  private boolean hasOutOfMemory = false;
  private LinearLayout mChoicesLayout;
  private int mCurFrameIndex = 0;
  private int mCurIndex = 0;
  private TextView mCurIndexText = null;
  private ImageView mCurIndexView = null;
  private int mCurType = -1;
  private ImageView mEffectBtn;
  private ArrayList<Integer> mEffectList = new ArrayList();
  private EffectTask mEffectTask;
  private ImageView mFaceBtn;
  private ArrayList<Integer> mFaceList = new ArrayList();
  private String mFileFrom = "";
  private String mFilePath = "";
  private ImageView mFrameBtn;
  private ArrayList<Integer> mFrameList = new ArrayList();
  private KXMergeFrameImageView mImageView;
  private Bitmap mPreviewBitmap = null;
  private String mPreviewBitmapPath = "";
  private ProgressDialog mProgressDialog;
  private View mRotateBtn;
  private Bitmap mTempBitmap = null;
  private View mWatermarkBtn;

  static
  {
    long l1 = System.currentTimeMillis();
    System.loadLibrary("kximager-jni");
    long l2 = System.currentTimeMillis();
    KXLog.w("IFEditPhotoActivity", "-----------  loadLibrary using >>" + (l2 - l1));
    BITMAP_MAX_LENGTH = 780;
    SINGLE_FILE_PATHS = new String[] { "mist.png", "love.png", "transparent.png", "black.png" };
    CHIP_FILE_PATHS = new String[] { "left.png", "leftup.png", "up.png", "rightup.png", "right.png", "rightdown.png", "down.png", "leftdown.png" };
  }

  private void actionEffect(int paramInt)
  {
    this.hasEdit = true;
    switch (paramInt)
    {
    default:
      return;
    case 0:
      if ((this.mTempBitmap != null) && (this.mTempBitmap != this.mPreviewBitmap))
      {
        this.mTempBitmap.recycle();
        this.mTempBitmap = null;
      }
      this.mImageView.setImageBitmap(this.mPreviewBitmap);
      return;
    case 1:
      startEffectTask(2);
      return;
    case 2:
      startEffectTask(3);
      return;
    case 3:
      startEffectTask(5);
      return;
    case 4:
      startEffectTask(6);
      return;
    case 5:
      startEffectTask(7);
      return;
    case 6:
      startEffectTask(8);
      return;
    case 7:
      startEffectTask(10);
      return;
    case 8:
      startEffectTask(11);
      return;
    case 9:
    }
    startEffectTask(12);
  }

  private void actionFace(int paramInt)
  {
    this.hasEdit = true;
    switch (paramInt)
    {
    default:
      return;
    case 0:
      addFace("accessories/image_face3.png");
      return;
    case 1:
      addFace("accessories/image_face9.png");
      return;
    case 2:
      addFace("accessories/new_year_2.png");
      return;
    case 3:
      addFace("accessories/new_year_3.png");
      return;
    case 4:
      addFace("accessories/new_year_4.png");
      return;
    case 5:
      addFace("accessories/image_face_forbite.png");
      return;
    case 6:
      addFace("accessories/image_face_rabbit.png");
      return;
    case 7:
      addFace("accessories/image_face1.png");
      return;
    case 8:
      addFace("accessories/image_face2.png");
      return;
    case 9:
      addFace("accessories/image_face4.png");
      return;
    case 10:
      addFace("accessories/image_face10.png");
      return;
    case 11:
    }
    addFace("accessories/image_face11.png");
  }

  private void actionFrame(int paramInt)
  {
    this.hasEdit = true;
    this.hasOutOfMemory = false;
    switch (paramInt)
    {
    default:
      return;
    case 0:
      this.frameChips = null;
      this.mImageView.setframeChips(null);
      this.mImageView.setImageBitmap(this.mPreviewBitmap);
      EditPictureModel.setFrameClip(null);
      return;
    case 1:
      this.frameChips = new Bitmap[8];
      loadFrameChips(this.frameChips, "frames/frame1/");
      this.mImageView.setframeChips(this.frameChips);
      EditPictureModel.setFrameClip(this.frameChips);
      return;
    case 2:
      this.frameChips = new Bitmap[8];
      loadFrameChips(this.frameChips, "frames/frame2/");
      this.mImageView.setframeChips(this.frameChips);
      EditPictureModel.setFrameClip(this.frameChips);
      return;
    case 3:
      this.frameChips = new Bitmap[8];
      loadFrameChips(this.frameChips, "frames/frame3/");
      this.mImageView.setframeChips(this.frameChips);
      EditPictureModel.setFrameClip(this.frameChips);
      return;
    case 4:
      this.frameChips = new Bitmap[8];
      loadFrameChips(this.frameChips, "frames/frame4/");
      this.mImageView.setframeChips(this.frameChips);
      EditPictureModel.setFrameClip(this.frameChips);
      return;
    case 5:
      this.frameChips = new Bitmap[1];
      loadSingleFrameChip(this.frameChips, "frames/frame5/", 5);
      this.mImageView.setframeChips(this.frameChips);
      EditPictureModel.setFrameClip(this.frameChips);
      return;
    case 6:
      this.frameChips = new Bitmap[1];
      loadSingleFrameChip(this.frameChips, "frames/frame6/", 6);
      this.mImageView.setframeChips(this.frameChips);
      EditPictureModel.setFrameClip(this.frameChips);
      return;
    case 7:
      this.frameChips = new Bitmap[8];
      loadFrameChips(this.frameChips, "frames/frame7/");
      this.mImageView.setframeChips(this.frameChips);
      EditPictureModel.setFrameClip(this.frameChips);
      return;
    case 8:
      this.frameChips = new Bitmap[1];
      loadSingleFrameChip(this.frameChips, "frames/frame8/", 8);
      this.mImageView.setframeChips(this.frameChips);
      EditPictureModel.setFrameClip(this.frameChips);
      return;
    case 9:
      this.frameChips = new Bitmap[1];
      loadSingleFrameChip(this.frameChips, "frames/frame9/", 9);
      this.mImageView.setframeChips(this.frameChips);
      EditPictureModel.setFrameClip(this.frameChips);
      return;
    case 10:
    }
    this.frameChips = new Bitmap[8];
    loadFrameChips(this.frameChips, "frames/frame10/");
    this.mImageView.setframeChips(this.frameChips);
    EditPictureModel.setFrameClip(this.frameChips);
  }

  private static Bitmap actionRotate(Bitmap paramBitmap, int paramInt)
  {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    if (paramInt / 90 % 2 != 0);
    for (int k = 1; ; k = 0)
    {
      int m = i;
      int n = j;
      if (k != 0)
      {
        m = j;
        n = i;
      }
      Bitmap localBitmap = Bitmap.createBitmap(m, n, Bitmap.Config.RGB_565);
      Matrix localMatrix = new Matrix();
      int i1 = i / 2;
      int i2 = j / 2;
      localMatrix.preTranslate(-i1, -i2);
      localMatrix.postRotate(paramInt);
      localMatrix.postTranslate(m / 2.0F, n / 2.0F);
      new Canvas(localBitmap).drawBitmap(paramBitmap, localMatrix, null);
      return localBitmap;
    }
  }

  private void addAccessory(Bitmap paramBitmap)
  {
    DisplayMetrics localDisplayMetrics = getResources().getDisplayMetrics();
    KXAccessoryView localKXAccessoryView = new KXAccessoryView(this.mImageView, paramBitmap, localDisplayMetrics.density);
    int i = this.mPreviewBitmap.getWidth();
    int j = this.mPreviewBitmap.getHeight();
    Matrix localMatrix = this.mImageView.getImageMatrix();
    float[] arrayOfFloat = new float[9];
    localMatrix.getValues(arrayOfFloat);
    float f1 = arrayOfFloat[0];
    int k = Math.max(paramBitmap.getWidth(), paramBitmap.getHeight());
    float f2 = 50.0F * localDisplayMetrics.density / (f1 * k);
    float f3 = f2 * paramBitmap.getWidth();
    float f4 = f2 * paramBitmap.getHeight();
    RectF localRectF = new RectF(0.0F, 0.0F, i, j);
    float f5 = 0.5F * (i - f3);
    float f6 = 0.5F * (j - f4);
    localKXAccessoryView.setup(localMatrix, localRectF, new RectF(f5, f6, f5 + f3, f6 + f4));
    localKXAccessoryView.setFocus(false);
    if (!this.mImageView.addAccessory(localKXAccessoryView))
      Toast.makeText(getActivity(), 2131428211, 0).show();
  }

  private void addFace(String paramString)
  {
    Bitmap localBitmap = getAccessoryBm(getResources(), paramString);
    if (localBitmap == null)
    {
      Toast.makeText(getActivity(), 2131428311, 0).show();
      finish();
      System.gc();
    }
    addAccessory(localBitmap);
  }

  private void changeType(int paramInt)
  {
    if ((this.mCurType != paramInt) && (this.mCurType == 2))
      mergeFaces();
    if ((this.mCurType != paramInt) && (paramInt != -1))
      this.mImageView.setImageBitmap(this.mPreviewBitmap);
    this.mCurType = paramInt;
  }

  private static Bitmap getAccessoryBm(Resources paramResources, String paramString)
  {
    InputStream localInputStream = null;
    try
    {
      localInputStream = paramResources.getAssets().open(paramString);
      Bitmap localBitmap2 = BitmapFactory.decodeStream(localInputStream);
      localBitmap1 = localBitmap2;
      return localBitmap1;
    }
    catch (IOException localIOException)
    {
      while (true)
      {
        localIOException.printStackTrace();
        CloseUtil.close(localInputStream);
        Bitmap localBitmap1 = null;
      }
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      return null;
    }
    catch (Exception localException)
    {
      return null;
    }
    finally
    {
      CloseUtil.close(localInputStream);
    }
    throw localObject;
  }

  private void initBitmap()
  {
    this.mPreviewBitmapPath = this.mFilePath;
    new LoadBitmapTask(null).execute(new Void[0]);
  }

  private void initData()
  {
    this.mEffectList.add(Integer.valueOf(2130838441));
    this.mEffectList.add(Integer.valueOf(2130838442));
    this.mEffectList.add(Integer.valueOf(2130838443));
    this.mEffectList.add(Integer.valueOf(2130838444));
    this.mEffectList.add(Integer.valueOf(2130838445));
    this.mEffectList.add(Integer.valueOf(2130838446));
    this.mEffectList.add(Integer.valueOf(2130838447));
    this.mEffectList.add(Integer.valueOf(2130838448));
    this.mEffectList.add(Integer.valueOf(2130838449));
    this.mEffectList.add(Integer.valueOf(2130838450));
    this.mEffectList.add(Integer.valueOf(2130838451));
    this.mEffectList.add(Integer.valueOf(2130838452));
    this.mEffectList.add(Integer.valueOf(2130838453));
    this.mEffectList.add(Integer.valueOf(2130838454));
    this.mEffectList.add(Integer.valueOf(2130838455));
    this.mEffectList.add(Integer.valueOf(2130838456));
    this.mEffectList.add(Integer.valueOf(2130838457));
    this.mEffectList.add(Integer.valueOf(2130838458));
    this.mEffectList.add(Integer.valueOf(2130838459));
    this.mEffectList.add(Integer.valueOf(2130838460));
    this.mFrameList.add(Integer.valueOf(2130838751));
    this.mFrameList.add(Integer.valueOf(2131428232));
    this.mFrameList.add(Integer.valueOf(2130838742));
    this.mFrameList.add(Integer.valueOf(2131428233));
    this.mFrameList.add(Integer.valueOf(2130838743));
    this.mFrameList.add(Integer.valueOf(2131428234));
    this.mFrameList.add(Integer.valueOf(2130838744));
    this.mFrameList.add(Integer.valueOf(2131428235));
    this.mFrameList.add(Integer.valueOf(2130838745));
    this.mFrameList.add(Integer.valueOf(2131428236));
    this.mFrameList.add(Integer.valueOf(2130838746));
    this.mFrameList.add(Integer.valueOf(2131428237));
    this.mFrameList.add(Integer.valueOf(2130838747));
    this.mFrameList.add(Integer.valueOf(2131428238));
    this.mFrameList.add(Integer.valueOf(2130838748));
    this.mFrameList.add(Integer.valueOf(2131428239));
    this.mFrameList.add(Integer.valueOf(2130838749));
    this.mFrameList.add(Integer.valueOf(2131428240));
    this.mFrameList.add(Integer.valueOf(2130838750));
    this.mFrameList.add(Integer.valueOf(2131428241));
    this.mFrameList.add(Integer.valueOf(2130838741));
    this.mFrameList.add(Integer.valueOf(2131428242));
    this.mFaceList.add(Integer.valueOf(2130838736));
    this.mFaceList.add(Integer.valueOf(2131428217));
    this.mFaceList.add(Integer.valueOf(2130838738));
    this.mFaceList.add(Integer.valueOf(2131428221));
    this.mFaceList.add(Integer.valueOf(2130838646));
    this.mFaceList.add(Integer.valueOf(2131428224));
    this.mFaceList.add(Integer.valueOf(2130838647));
    this.mFaceList.add(Integer.valueOf(2131428225));
    this.mFaceList.add(Integer.valueOf(2130838648));
    this.mFaceList.add(Integer.valueOf(2131428226));
    this.mFaceList.add(Integer.valueOf(2130838739));
    this.mFaceList.add(Integer.valueOf(2131428212));
    this.mFaceList.add(Integer.valueOf(2130838740));
    this.mFaceList.add(Integer.valueOf(2131428214));
    this.mFaceList.add(Integer.valueOf(2130838734));
    this.mFaceList.add(Integer.valueOf(2131428215));
    this.mFaceList.add(Integer.valueOf(2130838735));
    this.mFaceList.add(Integer.valueOf(2131428216));
    this.mFaceList.add(Integer.valueOf(2130838737));
    this.mFaceList.add(Integer.valueOf(2131428218));
    this.mFaceList.add(Integer.valueOf(2130838732));
    this.mFaceList.add(Integer.valueOf(2131428222));
    this.mFaceList.add(Integer.valueOf(2130838733));
    this.mFaceList.add(Integer.valueOf(2131428223));
  }

  private void initTitle(View paramView)
  {
    paramView.findViewById(2131362282).setBackgroundColor(-13750738);
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    ImageView localImageView1 = (ImageView)paramView.findViewById(2131362928);
    localImageView1.setImageResource(2130838138);
    localImageView1.setOnClickListener(this);
    ImageView localImageView2 = (ImageView)paramView.findViewById(2131362914);
    paramView.findViewById(2131362916).setVisibility(8);
    paramView.findViewById(2131362917).setVisibility(0);
    localImageView2.setOnClickListener(this);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    localTextView.setVisibility(0);
    localTextView.setText(2131428572);
  }

  private void initViews(View paramView)
  {
    initTitle(paramView);
    this.mEffectBtn = ((ImageView)paramView.findViewById(2131362736));
    this.mFrameBtn = ((ImageView)paramView.findViewById(2131362737));
    this.mFaceBtn = ((ImageView)paramView.findViewById(2131362738));
    this.mWatermarkBtn = paramView.findViewById(2131362739);
    this.mChoicesLayout = ((LinearLayout)paramView.findViewById(2131362734));
    this.mEffectBtn.setOnClickListener(this);
    this.mFrameBtn.setOnClickListener(this);
    this.mFaceBtn.setOnClickListener(this);
    this.mWatermarkBtn.setOnClickListener(this);
    this.mImageView = ((KXMergeFrameImageView)paramView.findViewById(2131362730));
    this.mRotateBtn = paramView.findViewById(2131362732);
    this.mRotateBtn.setOnClickListener(this);
  }

  private boolean isNormalSize()
  {
    int i = this.mPreviewBitmap.getWidth();
    int j = this.mPreviewBitmap.getHeight();
    float f = i / j;
    return (f >= 0.4F) && (f <= 2.0F);
  }

  private void loadFrameChips(Bitmap[] paramArrayOfBitmap, String paramString)
  {
    if ((paramArrayOfBitmap == null) || (paramArrayOfBitmap.length < 8))
      throw new RuntimeException("please input an Bitmap array which size is 8...");
    AssetManager localAssetManager = getResources().getAssets();
    StringBuffer localStringBuffer = new StringBuffer();
    Object localObject1 = null;
    int i = 0;
    while (true)
    {
      if (i >= 8)
      {
        CloseUtil.close((Closeable)localObject1);
        return;
      }
      try
      {
        localStringBuffer.delete(0, localStringBuffer.length());
        localStringBuffer.append(paramString).append(CHIP_FILE_PATHS[i]);
        localObject1 = localAssetManager.open(localStringBuffer.toString());
        paramArrayOfBitmap[i] = BitmapFactory.decodeStream((InputStream)localObject1);
        CloseUtil.close((Closeable)localObject1);
        i++;
        continue;
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        Toast.makeText(getActivity(), 2131428311, 0).show();
        System.gc();
        finish();
        return;
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
        return;
      }
      finally
      {
        CloseUtil.close((Closeable)localObject1);
      }
    }
    throw localObject2;
  }

  private void loadSingleFrameChip(Bitmap[] paramArrayOfBitmap, String paramString, int paramInt)
  {
    AssetManager localAssetManager = getResources().getAssets();
    StringBuffer localStringBuffer = new StringBuffer();
    InputStream localInputStream = null;
    while (true)
    {
      try
      {
        localStringBuffer.delete(0, localStringBuffer.length());
        switch (paramInt)
        {
        case 7:
        default:
          localInputStream = localAssetManager.open(localStringBuffer.toString());
          paramArrayOfBitmap[0] = BitmapFactory.decodeStream(localInputStream);
          CloseUtil.close(localInputStream);
          return;
        case 5:
          localStringBuffer.append(paramString).append(SINGLE_FILE_PATHS[0]);
          continue;
        case 6:
        case 8:
        case 9:
        }
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
        return;
        localStringBuffer.append(paramString).append(SINGLE_FILE_PATHS[1]);
        continue;
      }
      finally
      {
        CloseUtil.close(localInputStream);
      }
      localStringBuffer.append(paramString).append(SINGLE_FILE_PATHS[2]);
      continue;
      localStringBuffer.append(paramString).append(SINGLE_FILE_PATHS[3]);
    }
  }

  private void mergeFaces()
  {
    try
    {
      LinkedList localLinkedList = this.mImageView.getAccessories();
      if (localLinkedList != null)
      {
        if (localLinkedList.size() == 0)
          return;
        int i = this.mPreviewBitmap.getWidth();
        int j = this.mPreviewBitmap.getHeight();
        Bitmap localBitmap1 = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565);
        localCanvas = new Canvas(localBitmap1);
        localPaint = new Paint(1);
        localPaint.setDither(true);
        localCanvas.drawBitmap(this.mPreviewBitmap, null, new Rect(0, 0, i, j), localPaint);
        localIterator = localLinkedList.iterator();
        if (!localIterator.hasNext())
        {
          localLinkedList.clear();
          this.mPreviewBitmap = localBitmap1;
          return;
        }
      }
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      while (true)
      {
        Canvas localCanvas;
        Paint localPaint;
        Iterator localIterator;
        Toast.makeText(getActivity(), 2131428311, 0).show();
        System.gc();
        return;
        KXAccessoryView localKXAccessoryView = (KXAccessoryView)localIterator.next();
        Bitmap localBitmap2 = localKXAccessoryView.getBitmap();
        if ((localBitmap2 == null) || (localBitmap2.isRecycled()))
          continue;
        localCanvas.drawBitmap(localBitmap2, null, localKXAccessoryView.getBounds(), localPaint);
      }
    }
    catch (Exception localException)
    {
      Toast.makeText(getActivity(), 2131428311, 0).show();
    }
  }

  private void mergeFrame()
  {
    try
    {
      if ((this.frameChips != null) && (this.frameChips.length == 8))
      {
        updateTempBitmap(BitmapFrameUtils.mergeFrame(this.mPreviewBitmap, this.frameChips));
        return;
      }
      updateTempBitmap(BitmapFrameUtils.mergeSingleFrame(this.mPreviewBitmap, this.frameChips));
      return;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      this.hasOutOfMemory = true;
      Toast.makeText(getActivity(), 2131428311, 0).show();
      System.gc();
      finish();
      return;
    }
    catch (Exception localException)
    {
      this.hasOutOfMemory = true;
      Toast.makeText(getActivity(), 2131428311, 0).show();
      System.gc();
      finish();
    }
  }

  // ERROR //
  public static Bitmap readBitmapFromFile(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 731	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   4: ifeq +14 -> 18
    //   7: new 641	java/lang/RuntimeException
    //   10: dup
    //   11: ldc_w 733
    //   14: invokespecial 644	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   17: athrow
    //   18: aconst_null
    //   19: astore_1
    //   20: new 735	java/io/FileInputStream
    //   23: dup
    //   24: aload_0
    //   25: invokespecial 736	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   28: astore_2
    //   29: aload_2
    //   30: invokestatic 475	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;)Landroid/graphics/Bitmap;
    //   33: astore 5
    //   35: aload_2
    //   36: invokestatic 481	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   39: aload 5
    //   41: areturn
    //   42: astore_3
    //   43: aload_3
    //   44: invokevirtual 737	java/io/FileNotFoundException:printStackTrace	()V
    //   47: aload_1
    //   48: invokestatic 481	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   51: aconst_null
    //   52: areturn
    //   53: astore 4
    //   55: aload_1
    //   56: invokestatic 481	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   59: aload 4
    //   61: athrow
    //   62: astore 4
    //   64: aload_2
    //   65: astore_1
    //   66: goto -11 -> 55
    //   69: astore_3
    //   70: aload_2
    //   71: astore_1
    //   72: goto -29 -> 43
    //
    // Exception table:
    //   from	to	target	type
    //   20	29	42	java/io/FileNotFoundException
    //   20	29	53	finally
    //   43	47	53	finally
    //   29	35	62	finally
    //   29	35	69	java/io/FileNotFoundException
  }

  private void saveAndBack()
  {
    if (this.mCurType == 2)
      mergeFaces();
    while (true)
    {
      if (this.frameChips != null)
      {
        mergeFrame();
        saveThisEditStep();
      }
      EditPictureModel.setBimap(this.mPreviewBitmap);
      EventModel.EventData localEventData = (EventModel.EventData)getArguments().getSerializable("event_activity");
      Bundle localBundle = new Bundle();
      Intent localIntent = new Intent();
      localBundle.putSerializable("event_activity", localEventData);
      localIntent.putExtras(localBundle);
      setResult(-1, localIntent);
      finish();
      return;
      saveThisEditStep();
    }
  }

  private void saveThisEditStep()
  {
    if (this.mTempBitmap != null)
    {
      if ((this.mPreviewBitmap != null) && (this.mPreviewBitmap != this.mTempBitmap))
        this.mPreviewBitmap.recycle();
      this.mPreviewBitmap = this.mTempBitmap;
      this.mTempBitmap = null;
      if (this.mImageView != null)
        this.mImageView.setImageBitmap(this.mPreviewBitmap);
    }
  }

  private void showEffectsList(boolean paramBoolean, ArrayList<Integer> paramArrayList)
  {
    this.mCurIndex = 0;
    this.mChoicesLayout.removeAllViews();
    View localView1 = getView().findViewById(2131362733);
    int i;
    int j;
    label45: int k;
    if (paramBoolean)
    {
      i = 2130837772;
      localView1.setBackgroundResource(i);
      if (!paramBoolean)
        break label87;
      j = 0;
      localView1.setPadding(0, 0, 0, j);
      LinearLayout localLinearLayout = this.mChoicesLayout;
      if (!paramBoolean)
        break label99;
      k = 0;
      label67: localLinearLayout.setVisibility(k);
      if (paramBoolean)
        break label106;
    }
    while (true)
    {
      return;
      i = 2130839445;
      break;
      label87: j = dipToPx(6.7F);
      break label45;
      label99: k = 8;
      break label67;
      label106: ViewGroup.LayoutParams localLayoutParams = this.mChoicesLayout.getLayoutParams();
      if (localLayoutParams != null)
      {
        localLayoutParams.height = dipToPx(80.0F);
        this.mChoicesLayout.setLayoutParams(localLayoutParams);
      }
      for (int m = 0; m < -1 + paramArrayList.size(); m += 2)
      {
        View localView2 = getActivity().getLayoutInflater().inflate(2130903167, null);
        ImageView localImageView = (ImageView)localView2.findViewById(2131362742);
        localImageView.setVisibility(0);
        localView2.findViewById(2131362741).setVisibility(8);
        localView2.findViewById(2131362743).setVisibility(8);
        int[] arrayOfInt = new int[3];
        arrayOfInt[0] = ((Integer)paramArrayList.get(m)).intValue();
        arrayOfInt[1] = ((Integer)paramArrayList.get(m + 1)).intValue();
        arrayOfInt[2] = (m / 2);
        localImageView.setImageResource(((Integer)paramArrayList.get(m)).intValue());
        if (m == this.mCurIndex)
        {
          localImageView.setImageResource(((Integer)paramArrayList.get(m + 1)).intValue());
          this.mCurIndexView = localImageView;
        }
        localImageView.setTag(arrayOfInt);
        localImageView.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            ImageView localImageView = (ImageView)paramView;
            if (localImageView != null)
            {
              int[] arrayOfInt1 = (int[])localImageView.getTag();
              int i = arrayOfInt1[2];
              if (IFEditPhotoFragment.this.mCurIndex != i)
              {
                int[] arrayOfInt2 = (int[])IFEditPhotoFragment.this.mCurIndexView.getTag();
                IFEditPhotoFragment.this.mCurIndexView.setImageResource(arrayOfInt2[0]);
                IFEditPhotoFragment.this.mCurIndexView = localImageView;
                IFEditPhotoFragment.this.mCurIndex = i;
                localImageView.setImageResource(arrayOfInt1[1]);
                IFEditPhotoFragment.this.actionEffect(i);
              }
            }
          }
        });
        this.mChoicesLayout.addView(localView2);
      }
    }
  }

  private void showFrameOrFaceList(boolean paramBoolean, ArrayList<Integer> paramArrayList)
  {
    this.mChoicesLayout.removeAllViews();
    View localView1 = getView().findViewById(2131362733);
    int i;
    int j;
    label40: int k;
    if (paramBoolean)
    {
      i = 2130837772;
      localView1.setBackgroundResource(i);
      if (!paramBoolean)
        break label82;
      j = 0;
      localView1.setPadding(0, 0, 0, j);
      LinearLayout localLinearLayout = this.mChoicesLayout;
      if (!paramBoolean)
        break label94;
      k = 0;
      label62: localLinearLayout.setVisibility(k);
      if (paramBoolean)
        break label101;
    }
    while (true)
    {
      return;
      i = 2130839445;
      break;
      label82: j = dipToPx(6.7F);
      break label40;
      label94: k = 8;
      break label62;
      label101: ViewGroup.LayoutParams localLayoutParams1 = this.mChoicesLayout.getLayoutParams();
      if (localLayoutParams1 != null)
      {
        localLayoutParams1.height = dipToPx(85.400002F);
        this.mChoicesLayout.setLayoutParams(localLayoutParams1);
      }
      for (int m = 0; m < -1 + paramArrayList.size(); m += 2)
      {
        View localView2 = getActivity().getLayoutInflater().inflate(2130903167, null);
        ImageView localImageView1 = (ImageView)localView2.findViewById(2131362741);
        ImageView localImageView2 = (ImageView)localView2.findViewById(2131362742);
        TextView localTextView = (TextView)localView2.findViewById(2131362743);
        localImageView1.setImageResource(((Integer)paramArrayList.get(m)).intValue());
        localTextView.setText(((Integer)paramArrayList.get(m + 1)).intValue());
        ImageView localImageView3 = localImageView1;
        if (this.mCurType == 2)
        {
          localImageView2.setBackgroundResource(2130837582);
          localImageView2.setVisibility(0);
          localImageView3 = localImageView2;
        }
        int[] arrayOfInt = new int[3];
        arrayOfInt[0] = ((Integer)paramArrayList.get(m)).intValue();
        arrayOfInt[1] = ((Integer)paramArrayList.get(m + 1)).intValue();
        arrayOfInt[2] = (m / 2);
        localImageView3.setTag(arrayOfInt);
        if (this.mCurType == 1)
        {
          ViewGroup.LayoutParams localLayoutParams2 = localImageView1.getLayoutParams();
          if (localLayoutParams1 != null)
          {
            localLayoutParams2.width = -2;
            localLayoutParams2.height = -2;
            localImageView1.setLayoutParams(localLayoutParams2);
          }
        }
        if (arrayOfInt[2] == this.mCurIndex)
        {
          localImageView2.setVisibility(0);
          this.mCurIndexView = localImageView2;
          this.mCurIndexText = localTextView;
        }
        localImageView3.setOnClickListener(new View.OnClickListener(localTextView, localImageView2)
        {
          public void onClick(View paramView)
          {
            ImageView localImageView = (ImageView)paramView;
            int i;
            if (localImageView != null)
            {
              i = ((int[])localImageView.getTag())[2];
              if (((IFEditPhotoFragment.this.mCurIndex != i) && (IFEditPhotoFragment.this.mCurType == 1)) || (IFEditPhotoFragment.this.mCurType != 1))
              {
                if (IFEditPhotoFragment.this.mCurType == 1)
                {
                  IFEditPhotoFragment.this.mCurIndexView.setVisibility(4);
                  IFEditPhotoFragment.this.mCurIndexText.setTextColor(IFEditPhotoFragment.this.getResources().getColor(2130839385));
                  this.val$tv.setTextColor(IFEditPhotoFragment.this.getResources().getColor(2130839435));
                  this.val$frame.setVisibility(0);
                }
                IFEditPhotoFragment.this.mCurIndexView = this.val$frame;
                IFEditPhotoFragment.this.mCurIndexText = this.val$tv;
                IFEditPhotoFragment.this.mCurIndex = i;
                if (IFEditPhotoFragment.this.mCurType != 2)
                  break label173;
                IFEditPhotoFragment.this.actionFace(i);
              }
            }
            return;
            label173: IFEditPhotoFragment.this.mCurFrameIndex = i;
            IFEditPhotoFragment.this.actionFrame(i);
          }
        });
        this.mChoicesLayout.addView(localView2);
      }
    }
  }

  private void showNewsIcon(View paramView)
  {
    boolean bool = getShowNewIcon(getActivity());
    View localView = paramView.findViewById(2131362740);
    if (bool);
    for (int i = 0; ; i = 8)
    {
      localView.setVisibility(i);
      setShowNewIcon(getActivity(), false);
      return;
    }
  }

  private void startEffectTask(int paramInt)
  {
    if ((this.mEffectTask != null) && (this.mEffectTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    this.mEffectTask = new EffectTask(null);
    EffectTask localEffectTask = this.mEffectTask;
    Integer[] arrayOfInteger = new Integer[1];
    arrayOfInteger[0] = Integer.valueOf(paramInt);
    localEffectTask.execute(arrayOfInteger);
  }

  private void updateBtns()
  {
    ImageView localImageView1 = this.mFaceBtn;
    int i;
    int j;
    label40: int k;
    if (this.mCurType == 2)
    {
      i = 2130837778;
      localImageView1.setBackgroundResource(i);
      ImageView localImageView2 = this.mFrameBtn;
      if (this.mCurType != 1)
        break label127;
      j = 2130837780;
      localImageView2.setBackgroundResource(j);
      ImageView localImageView3 = this.mEffectBtn;
      if (this.mCurType != 0)
        break label135;
      k = 2130837776;
      label64: localImageView3.setBackgroundResource(k);
      showEffectsList(false, this.mEffectList);
      showFrameOrFaceList(false, this.mFrameList);
      showFrameOrFaceList(false, this.mFaceList);
      if (this.mCurType != 0)
        break label143;
      this.mCurIndex = 0;
      showEffectsList(true, this.mEffectList);
    }
    label127: 
    do
    {
      return;
      i = 2130837583;
      break;
      j = 2130837584;
      break label40;
      k = 2130837581;
      break label64;
      if (this.mCurType != 1)
        continue;
      this.mCurIndex = this.mCurFrameIndex;
      showFrameOrFaceList(true, this.mFrameList);
      return;
    }
    while (this.mCurType != 2);
    label135: label143: this.mCurIndex = 0;
    showFrameOrFaceList(true, this.mFaceList);
  }

  private void updateImageViewAfterChangeFrame()
  {
    if (!this.hasOutOfMemory)
    {
      this.mImageView.setImageBitmap(this.mTempBitmap);
      clearFrameChip();
    }
  }

  private void updateNewBitmap(Intent paramIntent, int paramInt1, int paramInt2)
  {
    Bitmap localBitmap = EditPictureModel.getBimapCanvas();
    if (localBitmap != null)
    {
      this.mPreviewBitmap = localBitmap;
      EditPictureModel.setBimap(this.mPreviewBitmap);
    }
    while (true)
    {
      this.mImageView.setImageBitmap(this.mPreviewBitmap);
      return;
      Toast.makeText(getActivity(), 2131428246, 0).show();
    }
  }

  private void updateTempBitmap(Bitmap paramBitmap)
  {
    if ((this.mTempBitmap != null) && (!this.mTempBitmap.isRecycled()) && (this.mTempBitmap != paramBitmap) && (this.mTempBitmap != this.mPreviewBitmap))
      this.mTempBitmap.recycle();
    this.mTempBitmap = null;
    this.mTempBitmap = paramBitmap;
  }

  public void clearFrameChip()
  {
    if (this.frameChips != null);
    for (int i = 0; ; i++)
    {
      if (i >= this.frameChips.length)
        return;
      if ((this.frameChips[i] == null) || (this.frameChips[i].isRecycled()))
        continue;
      this.frameChips[i].recycle();
      this.frameChips[i] = null;
    }
  }

  public boolean getShowNewIcon(Context paramContext)
  {
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    try
    {
      boolean bool = localSharedPreferences.getBoolean("water_mark_new_" + User.getInstance().getUID(), true);
      return bool;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 == -1)
    {
      this.hasEdit = true;
      updateNewBitmap(paramIntent, -1, paramInt1);
    }
    do
      return;
    while (paramInt2 == 0);
    finish();
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    if (paramInt == -1)
    {
      saveAndBack();
      return;
    }
    finish();
  }

  public void onClick(View paramView)
  {
    int i = -1;
    switch (paramView.getId())
    {
    default:
    case 2131362914:
    case 2131362928:
    case 2131362732:
    case 2131362736:
    case 2131362737:
    case 2131362738:
    case 2131362739:
    }
    do
    {
      return;
      finish();
      return;
      saveAndBack();
      return;
      this.hasEdit = true;
      changeType(i);
      updateBtns();
      this.mPreviewBitmap = actionRotate(this.mPreviewBitmap, 90);
      this.mImageView.setImageBitmap(this.mPreviewBitmap);
      return;
      saveThisEditStep();
      if (this.mCurType != 0)
        i = 0;
      changeType(i);
      updateBtns();
      return;
      saveThisEditStep();
      if (this.mCurType != 1)
        i = 1;
      changeType(i);
      updateBtns();
      return;
      saveThisEditStep();
      if (this.mCurType != 2)
        i = 2;
      changeType(i);
      updateBtns();
      return;
      changeType(i);
      updateBtns();
      saveThisEditStep();
    }
    while (this.mPreviewBitmap == null);
    EditPictureModel.setBimap(this.mPreviewBitmap);
    AnimationUtil.startFragmentForResult(this, new Intent(getActivity(), IFWaterMarkFragment.class), 305, 3);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      this.mFilePath = localBundle.getString("filePathName");
      this.mFileFrom = localBundle.getString("fileFrom");
    }
    if (TextUtils.isEmpty(this.mFilePath))
      KXLog.d("IFEditPhotoActivity", "Your filePath is empty !");
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903166, paramViewGroup, false);
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      requestFinish();
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (paramBundle != null)
      paramBundle.putString("TEST", "aaa");
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    toString();
    this.mProgressDialog = ProgressDialog.show(getActivity(), "", getResources().getText(2131428245), true, true, null);
    ImageCache.getInstance().clear();
    System.gc();
    EditPictureModel.setBitmapPath(this.mFilePath);
    EditPictureModel.setPicFrom(this.mFileFrom);
    EditPictureModel.setFrameClip(null);
    initData();
    initViews(paramView);
    initBitmap();
    showNewsIcon(paramView);
  }

  public void requestFinish()
  {
  }

  public void setShowNewIcon(Context paramContext, boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(paramContext).edit();
    localEditor.putBoolean("water_mark_new_" + User.getInstance().getUID(), paramBoolean);
    localEditor.commit();
  }

  private class EffectTask extends KXFragment.KXAsyncTask<Integer, Void, Bitmap>
  {
    private EffectTask()
    {
      super();
    }

    protected Bitmap doInBackgroundA(Integer[] paramArrayOfInteger)
    {
      switch (paramArrayOfInteger[0].intValue())
      {
      case 4:
      case 9:
      default:
        return null;
      case 2:
        Bitmap localBitmap4 = BitmapFactory.decodeResource(IFEditPhotoFragment.this.getResources(), 2130838712);
        return BitmapFilterUtils.lomo(IFEditPhotoFragment.this.mPreviewBitmap, localBitmap4);
      case 3:
        Bitmap localBitmap3 = BitmapFactory.decodeResource(IFEditPhotoFragment.this.getResources(), 2130838711);
        return BitmapFilterUtils.japan(IFEditPhotoFragment.this.mPreviewBitmap, localBitmap3);
      case 5:
        return BitmapFilterUtils.classic(IFEditPhotoFragment.this.mPreviewBitmap);
      case 6:
        Bitmap localBitmap2 = BitmapFactory.decodeResource(IFEditPhotoFragment.this.getResources(), 2130838710);
        return BitmapFilterUtils.forest(IFEditPhotoFragment.this.mPreviewBitmap, localBitmap2);
      case 7:
        Bitmap localBitmap1 = BitmapFactory.decodeResource(IFEditPhotoFragment.this.getResources(), 2130838709);
        return BitmapFilterUtils.blackWhite(IFEditPhotoFragment.this.mPreviewBitmap, localBitmap1);
      case 8:
        return BitmapFilterUtils.cold(IFEditPhotoFragment.this.mPreviewBitmap);
      case 10:
        return BitmapFilterUtils.strongPro(IFEditPhotoFragment.this.mPreviewBitmap);
      case 11:
        return BitmapFilterUtils.old(IFEditPhotoFragment.this.mPreviewBitmap);
      case 12:
      }
      return BitmapFilterUtils.sun(IFEditPhotoFragment.this.mPreviewBitmap);
    }

    protected void onPostExecuteA(Bitmap paramBitmap)
    {
      if (paramBitmap != null)
        IFEditPhotoFragment.this.updateTempBitmap(paramBitmap);
      while (true)
      {
        if (IFEditPhotoFragment.this.mProgressDialog != null)
          IFEditPhotoFragment.this.mProgressDialog.dismiss();
        if (IFEditPhotoFragment.this.mTempBitmap != null)
          IFEditPhotoFragment.this.mImageView.setImageBitmap(IFEditPhotoFragment.this.mTempBitmap);
        return;
        IFEditPhotoFragment.this.updateTempBitmap(IFEditPhotoFragment.this.mPreviewBitmap);
      }
    }

    protected void onPreExecuteA()
    {
      IFEditPhotoFragment.this.mProgressDialog.show();
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class LoadBitmapTask extends KXFragment.KXAsyncTask<Void, Void, Boolean>
  {
    Bitmap bitmap = null;

    private LoadBitmapTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(Void[] paramArrayOfVoid)
    {
      int i = FileUtil.getUploadPicMaxWid(IFEditPhotoFragment.this.getActivity());
      this.bitmap = FileUtil.loadBitmapFromFile(IFEditPhotoFragment.this.mPreviewBitmapPath, i, i);
      return Boolean.valueOf(true);
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      try
      {
        if (IFEditPhotoFragment.this.mProgressDialog != null)
          IFEditPhotoFragment.this.mProgressDialog.dismiss();
        if (paramBoolean.booleanValue())
        {
          if (this.bitmap == null)
          {
            Toast.makeText(IFEditPhotoFragment.this.getActivity(), 2131428272, 0).show();
            IFEditPhotoFragment.this.finish();
            return;
          }
          IFEditPhotoFragment.this.mPreviewBitmap = this.bitmap;
          IFEditPhotoFragment.this.mImageView.setImageBitmap(IFEditPhotoFragment.this.mPreviewBitmap);
          if (IFEditPhotoFragment.this.isNormalSize())
            return;
          DialogUtil.showMsgDlg(IFEditPhotoFragment.this.getActivity(), 2131428517, 2131428573, 2131428510, new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramDialogInterface, int paramInt)
            {
            }
          });
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("LoadBitmapTask", "onPostExecute", localException);
        return;
      }
      IFEditPhotoFragment.this.finish();
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
 * Qualified Name:     com.kaixin001.fragment.IFEditPhotoFragment
 * JD-Core Version:    0.6.0
 */