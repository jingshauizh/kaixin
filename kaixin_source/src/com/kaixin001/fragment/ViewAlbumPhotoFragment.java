package com.kaixin001.fragment;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.businesslogic.IPostUpCommand;
import com.kaixin001.businesslogic.PostUpTask;
import com.kaixin001.engine.AlbumListEngine;
import com.kaixin001.engine.AlbumPhotoEngine;
import com.kaixin001.engine.ObjCommentEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.item.AlbumInfo;
import com.kaixin001.item.KaixinPhotoItem;
import com.kaixin001.item.PhotoItem;
import com.kaixin001.model.AlbumListModel;
import com.kaixin001.model.AlbumPhotoModel;
import com.kaixin001.model.ItemManager;
import com.kaixin001.model.KaixinModelTemplate;
import com.kaixin001.model.KaixinUser;
import com.kaixin001.model.ObjCommentModel;
import com.kaixin001.model.User;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.ImageDownloader;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.view.IOnKXGalleryFling;
import com.kaixin001.view.KXDragImageView;
import com.kaixin001.view.KXGallery;
import com.kaixin001.view.KXImageView;
import com.kaixin001.view.KXSliderLayout2;
import com.kaixin001.view.KXSlidingDrawer;
import com.kaixin001.view.KXSlidingDrawer.OnStateChangedListener;
import com.kaixin001.view.KXSlidingDrawer.State;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class ViewAlbumPhotoFragment extends BaseFragment
  implements View.OnClickListener, ViewPager.OnPageChangeListener
{
  private static final String AVATAR_ALBUM_ID = "0";
  public static final String IS_FROM_VIEW_ALBUM = "isFromViewAlbum";
  private static final int MAX_RECOMMANDED_ALBUMS = 9;
  private static final int MSG_HIDE_BUTTONS = 113;
  private static final int MSG_NEXT_IMAGE = 116;
  private static final int MSG_UPDATE_VISITOR = 115;
  private static final int RECOMMANDED_ALBUMS_COLUMN_NUM_LAND = 4;
  private static final int RECOMMANDED_ALBUMS_COLUMN_NUM_PROT = 3;
  public static final int REQUEST_ALBUM = 11;
  private static final String TAG = ViewAlbumPhotoFragment.class.getSimpleName();
  private String activePid;
  int albumTotal;
  private int albumType;
  private Button btnComment;
  private ImageView btnLike;
  private ProgressBar btnLikeProgess;
  private ImageView btnOwner;
  private ImageView btnSaveAs;
  private Map<ImageView, String> coverMap = new HashMap();
  private AlbumInfo currAlbumUnit = null;
  private KXSlidingDrawer drawer = null;
  private String fname;
  private KXGallery gallery;
  private GetAlbumTask getAlbumTask;
  private GetVisitorsTask getVisitorTask;
  private ImageCache imageUtil = ImageCache.getInstance();
  private boolean isFromViewAlbumActiviy;
  private boolean isGettingAlbumList = false;
  private boolean isGotRecommandedAlbumListSucc = false;
  private boolean isShowOperations = true;
  private ImageView ivHasVisitor;
  private LayoutInflater layoutInflater = null;
  private View lytBottomBar;
  private RelativeLayout lytPhotoName;
  private FrameLayout lytSecondLayer;
  private int mActivePhotoIndex = 0;
  private AlbumAdapter mAlbumAdapter = new AlbumAdapter(null);
  private String mAlbumId = "";
  private String mAlbumTitle = "";
  private long mButtonShowDuration = 8000L;
  private final ObjCommentModel mCommentModel = new ObjCommentModel();
  private AlertDialog mDialog = null;
  private EditText mEditPassword = null;
  private Animation mFadeInAnimation;
  private Animation mFadeOutAnimation;
  private String mFuid = "";
  private GetAlbumlistTask mGetAlbumlistTask;
  GetPhotoListTask mGetPhotoListTask = null;
  private Timer mHideButtonTimer = new Timer();
  private HideButtonsTask mHideButtonsTask = new HideButtonsTask(null);
  private ImageDownloader mImgDownloader = ImageDownloader.getInstance();
  private String mLatestDownloadPhotoUrl = "";
  private long mLoadPhotoDelay = 500L;
  private Timer mLoadPhotoDelayTimer = new Timer();
  private ViewPager mPager;
  private String mPassword = "";
  private ArrayList<KaixinPhotoItem> mPhotoList = new ArrayList();
  private String mPid;
  private ProgressDialog mProgressDialog;
  private final ArrayList<AlbumInfo> mRecommandedAlbums = new ArrayList();
  private String mReplyContent = "";
  private long mShowNextPhotoDuration = 2000L;
  private ViewPhotoAdapter mViewPhotoAdapter = new ViewPhotoAdapter();
  PhotoNameOnClickListener photoNameOnClickListener = new PhotoNameOnClickListener();
  private int previousIndex = -1;
  private RelativeLayout titleBar;
  private TextView titleCenterText;
  private TextView tvPhotoName;

  private void backOrExit()
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

  private void constructAnimations()
  {
    this.mFadeInAnimation = new AlphaAnimation(0.0F, 1.0F);
    this.mFadeInAnimation.setDuration(500L);
    this.mFadeOutAnimation = new AlphaAnimation(1.0F, 0.0F);
    this.mFadeOutAnimation.setDuration(500L);
  }

  private void downloadImage(String paramString)
  {
    this.mLoadPhotoDelayTimer = new Timer();
    LoadPhotoDelayTimerTask localLoadPhotoDelayTimerTask = new LoadPhotoDelayTimerTask(paramString);
    this.mLoadPhotoDelayTimer.schedule(localLoadPhotoDelayTimerTask, this.mLoadPhotoDelay);
  }

  private void downloadPhotoList(int paramInt1, int paramInt2)
  {
    if ((this.mGetPhotoListTask != null) && (!this.mGetPhotoListTask.isCancelled()) && (this.mGetPhotoListTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    showProgressBar(true);
    this.mGetPhotoListTask = new GetPhotoListTask(null);
    Integer[] arrayOfInteger = new Integer[2];
    arrayOfInteger[0] = Integer.valueOf(paramInt1);
    arrayOfInteger[1] = Integer.valueOf(paramInt2);
    this.mGetPhotoListTask.execute(arrayOfInteger);
  }

  private void getAlbumList()
  {
    if (this.currAlbumUnit == null)
      return;
    String str = this.mEditPassword.getText().toString().trim();
    if (TextUtils.isEmpty(str))
    {
      Toast.makeText(getActivity(), 2131427482, 0).show();
      return;
    }
    this.getAlbumTask = new GetAlbumTask(null);
    this.getAlbumTask.execute(new String[] { str });
    this.mProgressDialog = ProgressDialog.show(getActivity(), "", getResources().getText(2131427483), true, true, new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramDialogInterface)
      {
        AlbumPhotoEngine.getInstance().cancel();
        ViewAlbumPhotoFragment.this.getAlbumTask.cancel(true);
      }
    });
  }

  private PhotoItem getCurrentPhoto()
  {
    if ((this.mPhotoList.isEmpty()) || (this.mActivePhotoIndex < 0) || (this.mActivePhotoIndex >= this.mPhotoList.size()))
      return null;
    PhotoItem localPhotoItem = (PhotoItem)this.mPhotoList.get(this.mActivePhotoIndex);
    this.activePid = localPhotoItem.pid;
    return localPhotoItem;
  }

  private PhotoItem getPhotoByPid(String paramString)
  {
    int i = this.mPhotoList.size();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return null;
      KaixinPhotoItem localKaixinPhotoItem = (KaixinPhotoItem)this.mPhotoList.get(j);
      if (paramString.equals(localKaixinPhotoItem.pid))
        return (PhotoItem)localKaixinPhotoItem;
    }
  }

  private int getPhotoTotal()
  {
    AlbumPhotoModel localAlbumPhotoModel = AlbumPhotoModel.getInstance();
    if (this.mAlbumId.equals(localAlbumPhotoModel.getAlbumId()))
    {
      this.albumTotal = localAlbumPhotoModel.mListPhotos.total;
      return localAlbumPhotoModel.mListPhotos.total;
    }
    return this.albumTotal;
  }

  private View getPicUnit(AlbumInfo paramAlbumInfo)
  {
    View localView = this.layoutInflater.inflate(2130903311, null);
    ImageView localImageView = (ImageView)localView.findViewById(2131362730);
    TextView localTextView = (TextView)localView.findViewById(2131363441);
    String str1 = paramAlbumInfo.albumsFeedPrivacy;
    if ((str1.compareTo("0") == 0) || ((str1.compareTo("1") == 0) && (paramAlbumInfo.albumsFeedIsFriend)))
    {
      displayPicture(localImageView, paramAlbumInfo.albumsFeedCoverpic, 2130838779);
      this.coverMap.put(localImageView, paramAlbumInfo.albumsFeedCoverpic);
    }
    while (true)
    {
      String str2 = paramAlbumInfo.albumsFeedAlbumtitle;
      if (str2.length() > 5)
        str2 = str2.substring(0, 5) + "...";
      localTextView.setText(str2);
      localView.setClickable(true);
      localView.setOnClickListener(new RecommandedAlbumListOnClickListener(paramAlbumInfo));
      return localView;
      if (str1.compareTo("2") == 0)
      {
        localImageView.setImageResource(2130837545);
        continue;
      }
      if (str1.compareTo("1") == 0)
      {
        localImageView.setImageResource(2130837544);
        continue;
      }
      if (str1.compareTo("3") != 0)
        continue;
      localImageView.setImageResource(2130837546);
    }
  }

  private void handlePhotoOperationOptions(int paramInt)
  {
    switch (paramInt)
    {
    default:
    case 0:
    case 1:
    }
    do
    {
      do
        return;
      while (this.mPhotoList == null);
      try
      {
        String str2 = FileUtil.savePicture(((KaixinPhotoItem)this.mPhotoList.get(this.mActivePhotoIndex)).large);
        if (str2 != null)
        {
          getActivity().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(str2))));
          Toast.makeText(getActivity(), getResources().getString(2131427492), 0).show();
          return;
        }
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
        Toast.makeText(getActivity(), getResources().getString(2131427494), 0).show();
        return;
      }
      Toast.makeText(getActivity(), getResources().getString(2131427494), 0).show();
      return;
    }
    while (this.mPhotoList == null);
    try
    {
      String str1 = ((KaixinPhotoItem)this.mPhotoList.get(this.mActivePhotoIndex)).large;
      Bitmap localBitmap = this.imageUtil.createSafeImage(str1);
      if (localBitmap != null)
      {
        getActivity().setWallpaper(localBitmap);
        Toast.makeText(getActivity(), getResources().getString(2131427495), 0).show();
        return;
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      Toast.makeText(getActivity(), getResources().getString(2131427496), 0).show();
      return;
    }
    Toast.makeText(getActivity(), getResources().getString(2131427496), 0).show();
  }

  private void initAlbum(Bundle paramBundle)
  {
    if (paramBundle == null)
      throw new IllegalArgumentException("input parameters can not be null");
    this.albumType = paramBundle.getInt("albumType", -1);
    this.mActivePhotoIndex = paramBundle.getInt("imgIndex", 0);
    this.mAlbumId = paramBundle.getString("albumId");
    this.mAlbumTitle = paramBundle.getString("title");
    this.mPid = paramBundle.getString("pid");
    this.isFromViewAlbumActiviy = paramBundle.getBoolean("isFromViewAlbum", false);
    if (this.albumType == -1)
      if (!"0".equals(this.mAlbumId))
        break label172;
    label172: for (this.albumType = 1; ; this.albumType = 2)
    {
      if (this.albumType == 2)
      {
        this.mFuid = paramBundle.getString("fuid");
        this.mPassword = paramBundle.getString("password");
        if (this.mPassword == null)
          this.mPassword = "";
      }
      if (!this.mAlbumId.equals(AlbumPhotoModel.getInstance().getAlbumId()))
        break;
      updatePhotoList();
      return;
    }
    downloadPhotoList(0, Math.max(24, 1 + this.mActivePhotoIndex));
  }

  private void initEditTextPassword()
  {
    if (this.mEditPassword != null)
    {
      ViewGroup localViewGroup = (ViewGroup)this.mEditPassword.getParent();
      if (localViewGroup != null)
        localViewGroup.removeView(this.mEditPassword);
      this.mEditPassword.setText("");
      return;
    }
    this.mEditPassword = new EditText(getActivity());
    this.mEditPassword.setLayoutParams(new TableLayout.LayoutParams(-2, -2));
    this.mEditPassword.setRawInputType(129);
    this.mEditPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
    EditText localEditText = this.mEditPassword;
    InputFilter[] arrayOfInputFilter = new InputFilter[1];
    arrayOfInputFilter[0] = new InputFilter.LengthFilter(20);
    localEditText.setFilters(arrayOfInputFilter);
  }

  private void setAlbumSlidingDrawer()
  {
    this.drawer = ((KXSlidingDrawer)findViewById(2131363956));
    this.drawer.setHandleAndContent(2131362920, 2131363968, 2131363525);
    this.drawer.setOnStateChangedListener(new KXSlidingDrawer.OnStateChangedListener()
    {
      public void onHideContent()
      {
        ViewAlbumPhotoFragment.this.titleCenterText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 2130837763, 0);
        if (ViewAlbumPhotoFragment.this.mActivePhotoIndex == ViewAlbumPhotoFragment.this.getPhotoTotal())
        {
          ViewAlbumPhotoFragment localViewAlbumPhotoFragment = ViewAlbumPhotoFragment.this;
          localViewAlbumPhotoFragment.mActivePhotoIndex = (-1 + localViewAlbumPhotoFragment.mActivePhotoIndex);
          ViewAlbumPhotoFragment.this.gallery.setSelection(ViewAlbumPhotoFragment.this.mActivePhotoIndex);
        }
        ViewAlbumPhotoFragment.this.startHideButtonsTimer();
        ViewAlbumPhotoFragment.this.updateViews();
      }

      public void onScrollContent()
      {
        String str = ViewAlbumPhotoFragment.this.fname + ViewAlbumPhotoFragment.this.getString(2131428270);
        ((TextView)ViewAlbumPhotoFragment.this.findViewById(2131363962)).setText(str);
        ViewAlbumPhotoFragment.this.lytPhotoName.setVisibility(8);
        ViewAlbumPhotoFragment.this.startGetAlbumList();
        ViewAlbumPhotoFragment.this.titleCenterText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 2130839252, 0);
        ViewAlbumPhotoFragment.this.mHideButtonTimer.cancel();
      }

      public void onShowContent()
      {
        ViewAlbumPhotoFragment.this.updateRecommandedAlbumCover();
        ViewAlbumPhotoFragment.this.lytPhotoName.setVisibility(8);
        ViewAlbumPhotoFragment.this.titleCenterText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 2130839252, 0);
        ViewAlbumPhotoFragment.this.mHideButtonTimer.cancel();
      }
    });
  }

  private void setAndShowRecommandedAlbumsContent(Configuration paramConfiguration)
  {
    TableLayout localTableLayout = (TableLayout)findViewById(2131363964);
    localTableLayout.removeAllViews();
    this.coverMap.clear();
    if (paramConfiguration == null)
      paramConfiguration = getResources().getConfiguration();
    int i = paramConfiguration.orientation;
    int j = 0;
    if (i == 2)
      j = 1;
    int k = this.mRecommandedAlbums.size();
    if (k > 0)
    {
      TableRow localTableRow1 = null;
      int m;
      TableRow localTableRow2;
      int n;
      if (j != 0)
      {
        m = 4;
        if (k >= m)
          break label213;
        localTableRow2 = new TableRow(getActivity());
        localTableLayout.addView(localTableRow2);
        n = 0;
        if (n < m)
          break label143;
      }
      while (true)
      {
        findViewById(2131363963).setVisibility(0);
        findViewById(2131363965).setVisibility(8);
        return;
        m = 3;
        break;
        label143: if (n < k)
          localTableRow2.addView(getPicUnit((AlbumInfo)this.mRecommandedAlbums.get(n)));
        while (true)
        {
          n++;
          break;
          View localView = getPicUnit((AlbumInfo)this.mRecommandedAlbums.get(k - 1));
          localTableRow2.addView(localView);
          localView.setVisibility(4);
        }
        label213: for (int i1 = 0; i1 < k; i1++)
        {
          if (i1 % m == 0)
          {
            localTableRow1 = new TableRow(getActivity());
            localTableLayout.addView(localTableRow1);
          }
          localTableRow1.addView(getPicUnit((AlbumInfo)this.mRecommandedAlbums.get(i1)));
        }
      }
    }
    findViewById(2131363963).setVisibility(8);
    findViewById(2131363965).setVisibility(0);
    findViewById(2131363966).setVisibility(8);
    ((TextView)findViewById(2131363967)).setText(2131428271);
  }

  private void setBottomButtons()
  {
    this.lytBottomBar = findViewById(2131363950);
    this.lytBottomBar.setVisibility(8);
    this.btnComment = ((Button)findViewById(2131363954));
    this.btnComment.setOnClickListener(this);
    this.btnSaveAs = ((ImageView)findViewById(2131363481));
    this.btnSaveAs.setOnClickListener(this);
    this.btnLike = ((ImageView)findViewById(2131363960));
    this.btnLike.setOnClickListener(this);
    this.btnLikeProgess = ((ProgressBar)findViewById(2131363961));
  }

  private void setGallery()
  {
    this.gallery = ((KXGallery)findViewById(2131363948));
    this.gallery.setAdapter(this.mAlbumAdapter);
    this.gallery.setOnItemClickListener(new GalleryOnItemClickListener(null));
    this.gallery.setOnItemSelectedListener(new GalleryOnItemSelectedListener(null));
    this.gallery.onFlingListener = new IOnKXGalleryFling()
    {
      public void onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
      {
        KXLog.d("KXGallery", "e1=" + paramMotionEvent1.toString() + "\ne2=" + paramMotionEvent2.toString() + "\nvelocityX=" + paramFloat1 + "\nvelocityY=" + paramFloat2);
        KXLog.d("KXGallery", "mActivePhotoIndex=" + ViewAlbumPhotoFragment.this.mActivePhotoIndex);
        if ((paramMotionEvent2.getX() < paramMotionEvent1.getX()) && (ViewAlbumPhotoFragment.this.mActivePhotoIndex == -1 + ViewAlbumPhotoFragment.this.getPhotoTotal()) && (ViewAlbumPhotoFragment.this.previousIndex == -1 + ViewAlbumPhotoFragment.this.getPhotoTotal()))
          ViewAlbumPhotoFragment.this.showRecommandedAlbums();
        ViewAlbumPhotoFragment.this.previousIndex = ViewAlbumPhotoFragment.this.mActivePhotoIndex;
      }
    };
    this.gallery.setSelection(this.mActivePhotoIndex);
  }

  private void setPager()
  {
    if (this.mPager != null)
    {
      this.mPager.removeAllViews();
      this.mPager = null;
    }
    this.mPager = ((ViewPager)findViewById(2131363955));
    this.mPager.setAdapter(this.mViewPhotoAdapter);
    this.mPager.setCurrentItem(this.mActivePhotoIndex);
    this.mPager.setOnPageChangeListener(this);
    this.mPager.setOffscreenPageLimit(1);
  }

  private void setTitleBar()
  {
    this.titleBar = ((RelativeLayout)findViewById(2131361802));
    this.titleBar.setBackgroundResource(2130837565);
    this.titleBar.setVisibility(8);
    findViewById(2131362916).setVisibility(8);
    findViewById(2131362919).setVisibility(8);
    this.titleCenterText = ((TextView)findViewById(2131362920));
    this.titleCenterText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 2130837763, 0);
    this.titleCenterText.setVisibility(0);
    ((ImageView)findViewById(2131362914)).setOnClickListener(this);
    this.btnOwner = ((ImageView)findViewById(2131362924));
    this.btnOwner.setImageResource(2130838142);
    this.btnOwner.setVisibility(0);
    this.btnOwner.setOnClickListener(this);
    ImageView localImageView = (ImageView)findViewById(2131362928);
    localImageView.setImageResource(2130838119);
    localImageView.setOnClickListener(this);
    this.lytPhotoName = ((RelativeLayout)findViewById(2131363958));
    this.ivHasVisitor = ((ImageView)findViewById(2131363959));
    this.tvPhotoName = ((TextView)findViewById(2131363951));
    this.lytPhotoName.setVisibility(8);
  }

  private void showAlbumActivity()
  {
    this.drawer.hideContent(false);
    if (this.isFromViewAlbumActiviy)
    {
      finish();
      return;
    }
    AlbumPhotoModel localAlbumPhotoModel = AlbumPhotoModel.getInstance();
    if (!this.mAlbumId.equals(localAlbumPhotoModel.getAlbumId()))
    {
      localAlbumPhotoModel.setAlbumId(this.mAlbumId);
      localAlbumPhotoModel.albumOwner = new KaixinUser();
      localAlbumPhotoModel.albumOwner.uid = this.mFuid;
      localAlbumPhotoModel.albumOwner.realname = this.fname;
      localAlbumPhotoModel.mListPhotos.setItemList(this.albumTotal, this.mPhotoList, 0);
    }
    IntentUtil.showViewAlbumFromViewPhoto(getActivity(), this, this.mAlbumId, this.mAlbumTitle, this.mFuid, this.fname, String.valueOf(getPhotoTotal()), this.mPassword);
  }

  private void showAlbumView(String paramString)
  {
    this.drawer.hideContent(false);
    if (this.isFromViewAlbumActiviy)
    {
      Intent localIntent = new Intent();
      localIntent.putExtra("albumId", this.currAlbumUnit.albumsFeedAlbumid);
      localIntent.putExtra("albumType", 2);
      localIntent.putExtra("title", this.currAlbumUnit.albumsFeedAlbumtitle);
      localIntent.putExtra("fuid", this.mFuid);
      localIntent.putExtra("fname", this.fname);
      localIntent.putExtra("pwd", paramString);
      localIntent.putExtra("pic_count", this.currAlbumUnit.albumsFeedPicnum);
      setResult(-1, localIntent);
      finishFragment(1);
      finish();
      return;
    }
    IntentUtil.showViewAlbumFromViewPhoto(getActivity(), this, this.currAlbumUnit.albumsFeedAlbumid, this.currAlbumUnit.albumsFeedAlbumtitle, this.mFuid, this.fname, this.currAlbumUnit.albumsFeedPicnum, paramString);
  }

  private void showInputPwd()
  {
    initEditTextPassword();
    if (this.mDialog != null)
    {
      if (this.mDialog.isShowing())
        this.mDialog.dismiss();
      this.mDialog = null;
    }
    this.mDialog = new AlertDialog.Builder(getActivity()).setMessage(2131427482).setTitle(2131427329).setView(this.mEditPassword).setPositiveButton(2131427381, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        ViewAlbumPhotoFragment.this.getAlbumList();
      }
    }).setNegativeButton(2131427587, null).show();
  }

  private void showObjCommentsActivity()
  {
    KaixinPhotoItem localKaixinPhotoItem = (KaixinPhotoItem)this.mPhotoList.get(this.mActivePhotoIndex);
    Intent localIntent = new Intent(getActivity(), ObjCommentFragment.class);
    localIntent.putExtra("id", localKaixinPhotoItem.pid);
    if ("0".equals(this.mAlbumId));
    for (String str = "4"; ; str = "1")
    {
      localIntent.putExtra("type", str);
      localIntent.putExtra("fuid", this.mFuid);
      localIntent.putExtra("mode", 3);
      startActivityForResult(localIntent, 3);
      return;
    }
  }

  private void showProgressBar(boolean paramBoolean)
  {
    if (isNeedReturn());
    View localView;
    do
    {
      return;
      localView = findViewById(2131363949);
    }
    while (localView == null);
    if (paramBoolean)
    {
      localView.setVisibility(0);
      return;
    }
    localView.setVisibility(8);
  }

  private void showRecommandedAlbums()
  {
    this.isShowOperations = true;
    updateViews();
    this.lytPhotoName.setVisibility(8);
    this.drawer.showContent(true);
  }

  private void showSavePhotoOptions()
  {
    DialogUtil.showSelectListDlg(getActivity(), 2131427491, getResources().getStringArray(2131492870), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        ViewAlbumPhotoFragment.this.handlePhotoOperationOptions(paramInt);
      }
    }
    , 1);
  }

  private void startGetAlbumList()
  {
    if (this.isGettingAlbumList);
    do
      return;
    while (this.isGotRecommandedAlbumListSucc);
    findViewById(2131363963).setVisibility(8);
    findViewById(2131363965).setVisibility(0);
    findViewById(2131363966).setVisibility(0);
    ((TextView)findViewById(2131363967)).setText(2131427599);
    this.mGetAlbumlistTask = new GetAlbumlistTask(null);
    this.mGetAlbumlistTask.execute(new Void[0]);
  }

  private void startHideButtonsTimer()
  {
    if (this.mHideButtonTimer != null)
    {
      this.mHideButtonTimer.cancel();
      this.mHideButtonTimer = null;
      this.mHideButtonsTask = null;
    }
    this.mHideButtonTimer = new Timer();
    this.mHideButtonsTask = new HideButtonsTask(null);
    this.mHideButtonTimer.schedule(this.mHideButtonsTask, this.mButtonShowDuration);
  }

  private void updatePhotoList()
  {
    this.mPhotoList.clear();
    AlbumPhotoModel localAlbumPhotoModel = AlbumPhotoModel.getInstance();
    this.mPhotoList.addAll(localAlbumPhotoModel.mListPhotos.getItemList());
    if (localAlbumPhotoModel.albumOwner != null)
    {
      this.mFuid = localAlbumPhotoModel.albumOwner.uid;
      this.fname = localAlbumPhotoModel.albumOwner.realname;
    }
    if (this.mPhotoList.size() != 0)
      this.mViewPhotoAdapter.notifyDataSetChanged();
  }

  private void updatePhotoName(String paramString1, String paramString2, int paramInt)
  {
    if ((paramInt == -1) || (paramInt == 0))
    {
      this.tvPhotoName.setText(paramString2);
      this.ivHasVisitor.setVisibility(8);
      this.tvPhotoName.setOnClickListener(null);
      return;
    }
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = paramString2;
    arrayOfObject[1] = Integer.valueOf(paramInt);
    String str = String.format("%s(浏览%s次)", arrayOfObject);
    this.tvPhotoName.setText(str);
    this.ivHasVisitor.setVisibility(0);
    this.photoNameOnClickListener.init(paramString1, paramInt);
    this.tvPhotoName.setOnClickListener(null);
  }

  private void updatePhotoViews(PhotoItem paramPhotoItem)
  {
    int i = 1 + this.mActivePhotoIndex;
    int j = getPhotoTotal();
    if (i > j)
      i = j;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(i);
    arrayOfObject[1] = Integer.valueOf(j);
    String str = String.format("%s/%s ", arrayOfObject);
    this.titleCenterText.setText(str);
    if (paramPhotoItem == null)
    {
      this.titleCenterText.setVisibility(4);
      this.lytPhotoName.setVisibility(8);
      this.btnSaveAs.setEnabled(false);
      this.btnComment.setText("+");
      this.btnComment.setEnabled(true);
      this.btnLike.setImageDrawable(getResources().getDrawable(2130838761));
      this.btnLike.setVisibility(0);
      this.btnLikeProgess.setVisibility(8);
      this.btnLike.setEnabled(false);
      return;
    }
    this.titleCenterText.setVisibility(0);
    updatePhotoName(this.activePid, paramPhotoItem.title, paramPhotoItem.visitorNum);
    if (this.isShowOperations)
      this.lytPhotoName.setVisibility(0);
    this.btnSaveAs.setEnabled(true);
    this.btnComment.setText(String.valueOf(paramPhotoItem.cnum));
    this.btnComment.setEnabled(true);
    this.btnLike.setEnabled(true);
    if (paramPhotoItem.selfUp == 1)
    {
      this.btnLike.setImageDrawable(getResources().getDrawable(2130838723));
      this.btnLike.setVisibility(0);
      this.btnLikeProgess.setVisibility(8);
      return;
    }
    if (paramPhotoItem.selfUp == -2)
    {
      this.btnLike.setVisibility(8);
      this.btnLikeProgess.setVisibility(0);
      return;
    }
    this.btnLike.setImageDrawable(getResources().getDrawable(2130838761));
    this.btnLike.setVisibility(0);
    this.btnLikeProgess.setVisibility(8);
  }

  private void updateRecommandedAlbumCover()
  {
    Iterator localIterator = this.coverMap.entrySet().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      displayPicture((ImageView)localEntry.getKey(), (String)localEntry.getValue(), 2130838779);
    }
  }

  private void updateViews()
  {
    int i;
    if (this.titleBar.getVisibility() == 0)
    {
      i = 1;
      if (!this.isShowOperations)
        break label140;
      if (i == 0)
      {
        this.lytSecondLayer.setVisibility(0);
        this.titleBar.setVisibility(0);
        this.titleBar.startAnimation(this.mFadeInAnimation);
        this.lytBottomBar.setVisibility(0);
        this.lytBottomBar.startAnimation(this.mFadeInAnimation);
        this.lytPhotoName.setVisibility(0);
        this.lytPhotoName.startAnimation(this.mFadeInAnimation);
      }
      startHideButtonsTimer();
    }
    while (true)
    {
      this.mPager.setCurrentItem(this.mActivePhotoIndex);
      if (this.mPhotoList.size() != 0)
        this.mViewPhotoAdapter.notifyDataSetChanged();
      PhotoItem localPhotoItem = getCurrentPhoto();
      if (localPhotoItem != null)
        updatePhotoViews(localPhotoItem);
      return;
      i = 0;
      break;
      label140: if (i == 0)
        continue;
      this.lytPhotoName.startAnimation(this.mFadeOutAnimation);
      this.titleBar.startAnimation(this.mFadeOutAnimation);
      this.titleBar.setVisibility(8);
      this.lytPhotoName.setVisibility(8);
      this.lytBottomBar.startAnimation(this.mFadeOutAnimation);
      this.lytBottomBar.setVisibility(8);
      this.lytSecondLayer.setVisibility(8);
    }
  }

  protected boolean handleMessage(Message paramMessage)
  {
    if (paramMessage == null)
      return false;
    if (paramMessage.what == 8001)
    {
      if (this.mPhotoList.size() != 0)
        this.mViewPhotoAdapter.notifyDataSetChanged();
      String str2 = (String)paramMessage.obj;
      if (this.mLatestDownloadPhotoUrl.equals(str2))
        showProgressBar(false);
      updateRecommandedAlbumCover();
      return true;
    }
    if (paramMessage.what == 116)
      if (1 + this.mActivePhotoIndex < this.mPhotoList.size())
        this.mPager.setCurrentItem(this.mActivePhotoIndex);
    do
      while (true)
      {
        return super.handleMessage(paramMessage);
        if (1 + this.mActivePhotoIndex != this.mPhotoList.size())
          continue;
        showRecommandedAlbums();
        continue;
        if (paramMessage.what != 113)
          break;
        if (this.mPhotoList.isEmpty())
          return true;
        if ((this.drawer.getState() != KXSlidingDrawer.State.HIDE) && (this.drawer.getState() != KXSlidingDrawer.State.IS_HIDING))
          continue;
        this.isShowOperations = false;
        this.mHideButtonTimer.cancel();
        updateViews();
        return true;
      }
    while (paramMessage.what != 115);
    String str1 = (String)paramMessage.obj;
    if (str1.equals(this.activePid))
    {
      PhotoItem localPhotoItem = getPhotoByPid(str1);
      updatePhotoName(str1, localPhotoItem.title, localPhotoItem.visitorNum);
    }
    return true;
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 == -1)
    {
      if (paramInt1 == 11)
      {
        this.albumType = paramIntent.getIntExtra("albumType", -1);
        this.mActivePhotoIndex = paramIntent.getIntExtra("imgIndex", 0);
        this.mAlbumId = paramIntent.getStringExtra("albumId");
        this.mAlbumTitle = paramIntent.getStringExtra("title");
        this.mFuid = paramIntent.getStringExtra("fuid");
        this.mPassword = paramIntent.getStringExtra("password");
        updatePhotoList();
        this.mPager.setCurrentItem(this.mActivePhotoIndex);
        PhotoItem localPhotoItem = getCurrentPhoto();
        if (localPhotoItem != null)
          updatePhotoViews(localPhotoItem);
        this.isGettingAlbumList = false;
        this.isGotRecommandedAlbumListSucc = false;
        startGetAlbumList();
      }
      if (paramInt1 == 3)
        this.mReplyContent = paramIntent.getStringExtra("content");
    }
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
    case 2131363954:
    case 2131363481:
    case 2131362924:
    case 2131362914:
    case 2131362928:
    case 2131363960:
    }
    PhotoItem localPhotoItem;
    do
    {
      do
      {
        do
          return;
        while (getCurrentPhoto() == null);
        this.mHideButtonTimer.cancel();
        showObjCommentsActivity();
        return;
      }
      while (getCurrentPhoto() == null);
      this.mHideButtonTimer.cancel();
      showSavePhotoOptions();
      return;
      this.drawer.hideContent(false);
      IntentUtil.showHomeFragment(this, this.mFuid, this.fname, null, "ViewAlbumPhotoFragment");
      return;
      backOrExit();
      return;
      this.mHideButtonTimer.cancel();
      showAlbumActivity();
      return;
      localPhotoItem = getCurrentPhoto();
    }
    while (localPhotoItem == null);
    if (User.getInstance().GetLookAround())
    {
      FragmentActivity localFragmentActivity = getActivity();
      String[] arrayOfString2 = new String[2];
      arrayOfString2[0] = getString(2131427338);
      arrayOfString2[1] = getString(2131427339);
      DialogUtil.showSelectListDlg(localFragmentActivity, 2131427729, arrayOfString2, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          switch (paramInt)
          {
          default:
            return;
          case 0:
            IntentUtil.returnToLogin(ViewAlbumPhotoFragment.this.getActivity(), false);
            return;
          case 1:
          }
          Bundle localBundle = new Bundle();
          localBundle.putInt("regist", 1);
          IntentUtil.returnToLogin(ViewAlbumPhotoFragment.this.getActivity(), localBundle, false);
        }
      }
      , 1);
      return;
    }
    if (localPhotoItem.selfUp == 1)
    {
      Message localMessage = Message.obtain();
      localMessage.what = 116;
      this.mHandler.sendMessageDelayed(localMessage, this.mShowNextPhotoDuration);
      return;
    }
    this.mHideButtonTimer.cancel();
    PostUpTask localPostUpTask = new PostUpTask(getActivity(), new IPostUpCommand(localPhotoItem)
    {
      public void onPreExec()
      {
        ViewAlbumPhotoFragment.this.btnLike.setVisibility(8);
        ViewAlbumPhotoFragment.this.btnLikeProgess.setVisibility(0);
      }

      public void onResultFailed()
      {
        String str = "我觉得这个挺赞的";
        try
        {
          if (ObjCommentEngine.getInstance().getObjCommentList(ViewAlbumPhotoFragment.this.getActivity().getApplicationContext(), this.val$photo.pid, "1", ViewAlbumPhotoFragment.this.mFuid, ViewAlbumPhotoFragment.this.mCommentModel))
          {
            if (ViewAlbumPhotoFragment.this.mCommentModel.isSelfUp())
              this.val$photo.selfUp = 1;
            str = ViewAlbumPhotoFragment.this.mCommentModel.likeStr;
          }
          if (this.val$photo.selfUp == 1)
            Toast.makeText(ViewAlbumPhotoFragment.this.getActivity(), "你已经赞过了", 0).show();
          while (this.val$photo.pid.equals(ViewAlbumPhotoFragment.this.activePid))
          {
            ViewAlbumPhotoFragment.this.updatePhotoViews(this.val$photo);
            Message localMessage = Message.obtain();
            localMessage.what = 116;
            ViewAlbumPhotoFragment.this.mHandler.sendMessageDelayed(localMessage, ViewAlbumPhotoFragment.this.mShowNextPhotoDuration);
            if (!ViewAlbumPhotoFragment.this.isShowOperations)
              break;
            ViewAlbumPhotoFragment.this.startHideButtonsTimer();
            return;
            Toast.makeText(ViewAlbumPhotoFragment.this.getActivity(), str, 0).show();
          }
        }
        catch (Exception localException)
        {
          KXLog.d(ViewAlbumPhotoFragment.TAG, "Exception", localException);
        }
      }

      public void onResultSuccess()
      {
        String str = "我觉得这个挺赞的";
        try
        {
          if (ObjCommentEngine.getInstance().getObjCommentList(ViewAlbumPhotoFragment.this.getActivity().getApplicationContext(), this.val$photo.pid, "1", ViewAlbumPhotoFragment.this.mFuid, ViewAlbumPhotoFragment.this.mCommentModel))
            str = ViewAlbumPhotoFragment.this.mCommentModel.likeStr;
          Toast.makeText(ViewAlbumPhotoFragment.this.getActivity(), str, 0).show();
          this.val$photo.selfUp = 1;
          if (this.val$photo.pid.equals(ViewAlbumPhotoFragment.this.activePid))
          {
            ViewAlbumPhotoFragment.this.updatePhotoViews(this.val$photo);
            Message localMessage = Message.obtain();
            localMessage.what = 116;
            ViewAlbumPhotoFragment.this.mHandler.sendMessageDelayed(localMessage, ViewAlbumPhotoFragment.this.mShowNextPhotoDuration);
          }
          return;
        }
        catch (Exception localException)
        {
          KXLog.d(ViewAlbumPhotoFragment.TAG, "Exception", localException);
        }
      }
    });
    String[] arrayOfString1 = new String[3];
    arrayOfString1[0] = "1";
    arrayOfString1[1] = localPhotoItem.pid;
    arrayOfString1[2] = this.mFuid;
    localPostUpTask.execute(arrayOfString1);
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    if (this.isGettingAlbumList);
    do
      return;
    while (this.mRecommandedAlbums.size() <= 0);
    setAndShowRecommandedAlbumsContent(paramConfiguration);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.layoutInflater = getActivity().getLayoutInflater();
    Bundle localBundle = getArguments();
    String str = null;
    if (localBundle != null)
      str = localBundle.getString("action");
    if ((str != null) && (!str.equals("com.kaixin001.VIEW_LARGE_PHOTO")) && (CrashRecoverUtil.isCrashBefore()))
    {
      CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
      IntentUtil.returnToDesktop(getActivity());
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903404, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if ((this.mGetPhotoListTask != null) && (this.mGetPhotoListTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetPhotoListTask.cancel(true);
      AlbumPhotoEngine.getInstance().cancel();
    }
    if (this.getVisitorTask != null)
    {
      this.getVisitorTask.stop = true;
      this.getVisitorTask.cancel(true);
    }
    if (this.mHideButtonTimer != null)
      this.mHideButtonTimer.cancel();
    if (this.mLoadPhotoDelayTimer != null)
      this.mLoadPhotoDelayTimer.cancel();
    if (!this.isFromViewAlbumActiviy)
      AlbumPhotoModel.getInstance().clear();
    cancelTask(this.mGetAlbumlistTask);
    cancelTask(this.getAlbumTask);
    this.mCommentModel.clear();
    super.onDestroy();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      if ((this.drawer.getState() == KXSlidingDrawer.State.IS_SHOWING) || (this.drawer.getState() == KXSlidingDrawer.State.SHOW))
      {
        this.drawer.hideContent(true);
        return true;
      }
      backOrExit();
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  public void onPageScrollStateChanged(int paramInt)
  {
  }

  public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
  {
  }

  public void onPageSelected(int paramInt)
  {
    View localView = this.mViewPhotoAdapter.getPrimary();
    if (localView == null)
    {
      localView = getActivity().getLayoutInflater().inflate(2130903406, null);
      ViewHolder localViewHolder = new ViewHolder(null);
      localViewHolder.imageView = ((ImageView)localView.findViewById(2131363969));
      localView.setTag(localViewHolder);
    }
    ((KXDragImageView)localView.findViewById(2131363969)).zoomTo(1.0F);
    this.mActivePhotoIndex = paramInt;
    this.activePid = null;
    int i = getPhotoTotal();
    if ((this.mPhotoList.size() < i) && (this.mPhotoList.size() - this.mActivePhotoIndex < 10))
      downloadPhotoList(this.mPhotoList.size(), 24);
    PhotoItem localPhotoItem = getCurrentPhoto();
    String str = null;
    if (localPhotoItem != null)
    {
      this.activePid = localPhotoItem.pid;
      str = localPhotoItem.large;
      if (localPhotoItem.visitorNum == -1)
        this.getVisitorTask.add(this.activePid);
    }
    updateViews();
    if (this.mLoadPhotoDelayTimer != null)
    {
      this.mLoadPhotoDelayTimer.cancel();
      this.mLoadPhotoDelayTimer = null;
    }
    if (str != null)
    {
      if (this.imageUtil.createSafeImage(str) == null)
      {
        downloadImage(str);
        showProgressBar(true);
        return;
      }
      showProgressBar(false);
      return;
    }
    showProgressBar(true);
  }

  public void onPause()
  {
    super.onPause();
  }

  public void onResume()
  {
    super.onResume();
    if (!TextUtils.isEmpty(this.mLatestDownloadPhotoUrl))
    {
      if (this.imageUtil.createSafeImage(this.mLatestDownloadPhotoUrl) == null)
      {
        this.mLoadPhotoDelayTimer = new Timer();
        LoadPhotoDelayTimerTask localLoadPhotoDelayTimerTask = new LoadPhotoDelayTimerTask(this.mLatestDownloadPhotoUrl);
        this.mLoadPhotoDelayTimer.schedule(localLoadPhotoDelayTimerTask, this.mLoadPhotoDelay);
        showProgressBar(true);
      }
    }
    else
      return;
    if (this.mPhotoList.size() != 0)
      this.mViewPhotoAdapter.notifyDataSetChanged();
    showProgressBar(false);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    KXSliderLayout2.setSlideEnable(false);
    enableSlideBack(false);
    this.lytSecondLayer = ((FrameLayout)findViewById(2131363956));
    initAlbum(getArguments());
    constructAnimations();
    setTitleBar();
    setPager();
    setBottomButtons();
    setAlbumSlidingDrawer();
    this.getVisitorTask = new GetVisitorsTask(null);
    this.getVisitorTask.execute(new String[0]);
    updateViews();
    if (!this.mPhotoList.isEmpty())
    {
      this.mPhotoList.size();
      PhotoItem localPhotoItem = getCurrentPhoto();
      if (localPhotoItem != null)
        updatePhotoViews(localPhotoItem);
    }
  }

  public void setImage(View paramView, int paramInt)
  {
    KaixinPhotoItem localKaixinPhotoItem = (KaixinPhotoItem)this.mPhotoList.get(paramInt);
    KXDragImageView localKXDragImageView = (KXDragImageView)paramView.findViewById(2131363969);
    Bitmap localBitmap1 = this.imageUtil.createSafeImage(localKaixinPhotoItem.large);
    Bitmap localBitmap2 = null;
    if (localBitmap1 == null)
    {
      localBitmap2 = ImageCache.getInstance().createSafeImage(localKaixinPhotoItem.thumbnail);
      downloadImage(localKaixinPhotoItem.large);
    }
    if (localBitmap1 != null)
    {
      localKXDragImageView.setImageBitmap(localBitmap1);
      localKXDragImageView.setScaleType(ImageView.ScaleType.MATRIX);
    }
    while (true)
    {
      localKXDragImageView.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          if ((ViewAlbumPhotoFragment.this.drawer.getState() == KXSlidingDrawer.State.IS_SHOWING) || (ViewAlbumPhotoFragment.this.drawer.getState() == KXSlidingDrawer.State.SHOW))
            return;
          ViewAlbumPhotoFragment localViewAlbumPhotoFragment = ViewAlbumPhotoFragment.this;
          if (ViewAlbumPhotoFragment.this.isShowOperations);
          for (boolean bool = false; ; bool = true)
          {
            localViewAlbumPhotoFragment.isShowOperations = bool;
            ViewAlbumPhotoFragment.this.updateViews();
            return;
          }
        }
      });
      return;
      if (localBitmap2 != null)
      {
        localKXDragImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        localKXDragImageView.setImageBitmap(localBitmap2);
        continue;
      }
      localKXDragImageView.setImageResource(2130838766);
    }
  }

  private class AlbumAdapter extends BaseAdapter
  {
    private AlbumAdapter()
    {
    }

    public int getCount()
    {
      return ViewAlbumPhotoFragment.this.mPhotoList.size();
    }

    public Object getItem(int paramInt)
    {
      if (ViewAlbumPhotoFragment.this.mPhotoList.size() == 0)
        return null;
      return ViewAlbumPhotoFragment.this.mPhotoList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (ViewAlbumPhotoFragment.this.isNeedReturn())
        return null;
      if (paramView == null)
      {
        paramView = ViewAlbumPhotoFragment.this.getActivity().getLayoutInflater().inflate(2130903406, paramViewGroup, false);
        ViewAlbumPhotoFragment.ViewHolder localViewHolder = new ViewAlbumPhotoFragment.ViewHolder(null);
        localViewHolder.imageView = ((ImageView)paramView.findViewById(2131363969));
        paramView.setTag(localViewHolder);
      }
      ImageView localImageView = ((ViewAlbumPhotoFragment.ViewHolder)paramView.getTag()).imageView;
      KaixinPhotoItem localKaixinPhotoItem = (KaixinPhotoItem)getItem(paramInt);
      if (localKaixinPhotoItem == null)
        return paramView;
      Bitmap localBitmap1 = ViewAlbumPhotoFragment.this.imageUtil.createSafeImage(localKaixinPhotoItem.large);
      Bitmap localBitmap2 = null;
      if (localBitmap1 == null)
      {
        localBitmap2 = ImageCache.getInstance().createSafeImage(localKaixinPhotoItem.thumbnail);
        ViewAlbumPhotoFragment.this.downloadImage(localKaixinPhotoItem.large);
      }
      if (localBitmap1 != null)
      {
        localImageView.setImageBitmap(localBitmap1);
        localImageView.setScaleType(ImageView.ScaleType.MATRIX);
      }
      while (true)
      {
        return paramView;
        if (localBitmap2 != null)
        {
          localImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
          localImageView.setImageBitmap(localBitmap2);
          continue;
        }
        localImageView.setImageResource(2130838766);
      }
    }
  }

  private class GalleryOnItemClickListener
    implements AdapterView.OnItemClickListener
  {
    private GalleryOnItemClickListener()
    {
    }

    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
    {
      if ((ViewAlbumPhotoFragment.this.drawer.getState() == KXSlidingDrawer.State.IS_SHOWING) || (ViewAlbumPhotoFragment.this.drawer.getState() == KXSlidingDrawer.State.SHOW))
        return;
      KXLog.d(ViewAlbumPhotoFragment.TAG, "onItemClick");
      ViewAlbumPhotoFragment localViewAlbumPhotoFragment = ViewAlbumPhotoFragment.this;
      if (ViewAlbumPhotoFragment.this.isShowOperations);
      for (boolean bool = false; ; bool = true)
      {
        localViewAlbumPhotoFragment.isShowOperations = bool;
        ViewAlbumPhotoFragment.this.updateViews();
        return;
      }
    }
  }

  private class GalleryOnItemSelectedListener
    implements AdapterView.OnItemSelectedListener
  {
    private GalleryOnItemSelectedListener()
    {
    }

    public void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
    {
      if (paramView == null)
      {
        paramView = ViewAlbumPhotoFragment.this.getActivity().getLayoutInflater().inflate(2130903406, paramAdapterView, false);
        ViewAlbumPhotoFragment.ViewHolder localViewHolder = new ViewAlbumPhotoFragment.ViewHolder(null);
        localViewHolder.imageView = ((ImageView)paramView.findViewById(2131363969));
        paramView.setTag(localViewHolder);
      }
      KXLog.d(ViewAlbumPhotoFragment.TAG, "onItemSelected index=" + paramInt);
      ((KXImageView)paramView.findViewById(2131363969)).zoomTo(1.0F);
      ViewAlbumPhotoFragment.this.mActivePhotoIndex = paramInt;
      ViewAlbumPhotoFragment.this.activePid = null;
      int i = ViewAlbumPhotoFragment.this.getPhotoTotal();
      if ((ViewAlbumPhotoFragment.this.mPhotoList.size() < i) && (ViewAlbumPhotoFragment.this.mPhotoList.size() - ViewAlbumPhotoFragment.this.mActivePhotoIndex < 10))
        ViewAlbumPhotoFragment.this.downloadPhotoList(ViewAlbumPhotoFragment.this.mPhotoList.size(), 24);
      PhotoItem localPhotoItem = ViewAlbumPhotoFragment.this.getCurrentPhoto();
      String str = null;
      if (localPhotoItem != null)
      {
        ViewAlbumPhotoFragment.this.activePid = localPhotoItem.pid;
        str = localPhotoItem.large;
        if (localPhotoItem.visitorNum == -1)
          ViewAlbumPhotoFragment.this.getVisitorTask.add(ViewAlbumPhotoFragment.this.activePid);
      }
      ViewAlbumPhotoFragment.this.updateViews();
      if (ViewAlbumPhotoFragment.this.mLoadPhotoDelayTimer != null)
      {
        ViewAlbumPhotoFragment.this.mLoadPhotoDelayTimer.cancel();
        ViewAlbumPhotoFragment.this.mLoadPhotoDelayTimer = null;
      }
      if (str != null)
      {
        if (ViewAlbumPhotoFragment.this.imageUtil.createSafeImage(str) == null)
        {
          ViewAlbumPhotoFragment.this.downloadImage(str);
          ViewAlbumPhotoFragment.this.showProgressBar(true);
          return;
        }
        ViewAlbumPhotoFragment.this.showProgressBar(false);
        return;
      }
      ViewAlbumPhotoFragment.this.showProgressBar(true);
    }

    public void onNothingSelected(AdapterView<?> paramAdapterView)
    {
    }
  }

  private class GetAlbumTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private String m_pwd;

    private GetAlbumTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
        return Integer.valueOf(-1);
      if ((ViewAlbumPhotoFragment.this.currAlbumUnit == null) || (ViewAlbumPhotoFragment.this.currAlbumUnit.albumsFeedAlbumid == null))
        return Integer.valueOf(-1);
      try
      {
        this.m_pwd = paramArrayOfString[0];
        if (ViewAlbumPhotoFragment.this.currAlbumUnit.albumsFeedAlbumid.compareTo("0") == 0)
          return Integer.valueOf(AlbumPhotoEngine.getInstance().getLogoPhotoList(ViewAlbumPhotoFragment.this.getActivity().getApplicationContext(), ViewAlbumPhotoFragment.this.mFuid, 0, 24));
        Integer localInteger = Integer.valueOf(AlbumPhotoEngine.getInstance().getAlbumPhotoList(ViewAlbumPhotoFragment.this.getActivity().getApplicationContext(), ViewAlbumPhotoFragment.this.currAlbumUnit.albumsFeedAlbumid, ViewAlbumPhotoFragment.this.mFuid, this.m_pwd, 0, 24));
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if ((paramInteger == null) || (ViewAlbumPhotoFragment.this.getView() == null) || (ViewAlbumPhotoFragment.this.getActivity() == null))
        return;
      try
      {
        ViewAlbumPhotoFragment.this.mProgressDialog.dismiss();
        if (paramInteger.intValue() == 1)
        {
          ViewAlbumPhotoFragment.this.showAlbumView(this.m_pwd);
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("AlbumListActivity", "onPostExecute", localException);
        return;
      }
      Toast.makeText(ViewAlbumPhotoFragment.this.getActivity(), 2131427484, 0).show();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class GetAlbumlistTask extends KXFragment.KXAsyncTask<Void, Void, Boolean>
  {
    private GetAlbumlistTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(Void[] paramArrayOfVoid)
    {
      try
      {
        Boolean localBoolean = Boolean.valueOf(AlbumListEngine.getInstance().getAlbumPhotoList(ViewAlbumPhotoFragment.this.getActivity().getApplicationContext(), ViewAlbumPhotoFragment.this.mFuid));
        return localBoolean;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return Boolean.valueOf(false);
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      if ((paramBoolean == null) || (ViewAlbumPhotoFragment.this.getView() == null) || (ViewAlbumPhotoFragment.this.getActivity() == null))
        return;
      ViewAlbumPhotoFragment.this.isGettingAlbumList = false;
      ViewAlbumPhotoFragment.this.mRecommandedAlbums.clear();
      while (true)
      {
        int j;
        try
        {
          if (!paramBoolean.booleanValue())
            break label262;
          ViewAlbumPhotoFragment.this.isGotRecommandedAlbumListSucc = true;
          if (User.getInstance().getUID().equals(ViewAlbumPhotoFragment.this.mFuid))
          {
            localArrayList = AlbumListModel.getMyAlbumList().getAlbumslist();
            if ((localArrayList == null) || (localArrayList.size() <= 0))
              continue;
            int i = localArrayList.size();
            j = 0;
            if ((ViewAlbumPhotoFragment.this.mRecommandedAlbums.size() < 9) && (j < i))
              break label163;
            ViewAlbumPhotoFragment.this.setAndShowRecommandedAlbumsContent(null);
            return;
          }
        }
        catch (Exception localException1)
        {
          ViewAlbumPhotoFragment.this.isGotRecommandedAlbumListSucc = false;
          KXLog.e("GetAlbumlistTask", "onPostExecute", localException1);
          return;
        }
        ArrayList localArrayList = AlbumListModel.getInstance().getAlbumslist();
        continue;
        label163: AlbumInfo localAlbumInfo = (AlbumInfo)localArrayList.get(j);
        label262: label289: if ((localAlbumInfo != null) && (ViewAlbumPhotoFragment.this.mAlbumId != null))
        {
          boolean bool = ViewAlbumPhotoFragment.this.mAlbumId.equals(localAlbumInfo.albumsFeedAlbumid);
          if (!bool)
          {
            try
            {
              int m = Integer.valueOf(localAlbumInfo.albumsFeedPicnum).intValue();
              k = m;
              if (k <= 0)
                break label289;
              ViewAlbumPhotoFragment.this.mRecommandedAlbums.add(localAlbumInfo);
            }
            catch (Exception localException2)
            {
              while (true)
              {
                localException2.printStackTrace();
                int k = 0;
              }
            }
            ViewAlbumPhotoFragment.this.isGotRecommandedAlbumListSucc = false;
            Toast.makeText(ViewAlbumPhotoFragment.this.getActivity(), 2131427741, 0).show();
            continue;
          }
        }
        j++;
      }
    }

    protected void onPreExecuteA()
    {
      ViewAlbumPhotoFragment.this.isGettingAlbumList = true;
      ViewAlbumPhotoFragment.this.isGotRecommandedAlbumListSucc = false;
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class GetPhotoListTask extends KXFragment.KXAsyncTask<Integer, Void, Integer>
  {
    private Integer start;

    private GetPhotoListTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Integer[] paramArrayOfInteger)
    {
      try
      {
        this.start = paramArrayOfInteger[0];
        int i = paramArrayOfInteger[1].intValue();
        AlbumPhotoModel localAlbumPhotoModel = AlbumPhotoModel.getInstance();
        if (!ViewAlbumPhotoFragment.this.mAlbumId.equals(localAlbumPhotoModel.getAlbumId()))
        {
          localAlbumPhotoModel.setAlbumId(ViewAlbumPhotoFragment.this.mAlbumId);
          localAlbumPhotoModel.mListPhotos.setItemList(ViewAlbumPhotoFragment.this.albumTotal, ViewAlbumPhotoFragment.this.mPhotoList, 0);
        }
        Integer localInteger = Integer.valueOf(AlbumPhotoEngine.getInstance().getAlbumPhotoList(ViewAlbumPhotoFragment.this.getActivity().getApplicationContext(), ViewAlbumPhotoFragment.this.mAlbumId, ViewAlbumPhotoFragment.this.mFuid, ViewAlbumPhotoFragment.this.mPassword, this.start.intValue(), i));
        return localInteger;
      }
      catch (Exception localException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if ((paramInteger == null) || (ViewAlbumPhotoFragment.this.getView() == null) || (ViewAlbumPhotoFragment.this.getActivity() == null));
      while (true)
      {
        return;
        if (paramInteger.intValue() != 1)
          break;
        ViewAlbumPhotoFragment.this.updatePhotoList();
        if (ViewAlbumPhotoFragment.this.mPhotoList.isEmpty())
        {
          Toast.makeText(ViewAlbumPhotoFragment.this.getActivity(), 2131427486, 0).show();
          ViewAlbumPhotoFragment.this.showProgressBar(false);
          ViewAlbumPhotoFragment.this.updateViews();
          return;
        }
        if (this.start.intValue() != 0)
          continue;
        for (int i = 0; ; i++)
        {
          if (i >= ViewAlbumPhotoFragment.this.mPhotoList.size());
          while (true)
          {
            ViewAlbumPhotoFragment.this.updateViews();
            return;
            String str = ((KaixinPhotoItem)ViewAlbumPhotoFragment.this.mPhotoList.get(i)).pid;
            if ((ViewAlbumPhotoFragment.this.mPid == null) || (!str.matches(ViewAlbumPhotoFragment.this.mPid)))
              break;
            ViewAlbumPhotoFragment.this.gallery.setSelection(i);
          }
        }
      }
      if (paramInteger.intValue() == -3)
        Toast.makeText(ViewAlbumPhotoFragment.this.getActivity(), 2131427486, 0).show();
      while (true)
      {
        ViewAlbumPhotoFragment.this.showProgressBar(false);
        ViewAlbumPhotoFragment.this.updateViews();
        return;
        Toast.makeText(ViewAlbumPhotoFragment.this.getActivity(), 2131427485, 0).show();
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class GetVisitorsTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private ItemManager<String> pids = new ItemManager();
    public volatile boolean stop = false;
    public volatile int wait = 0;

    private GetVisitorsTask()
    {
      super();
    }

    public void add(String paramString)
    {
      this.pids.addItem(paramString);
      synchronized (this.pids)
      {
        if (this.wait == -1)
          this.pids.notify();
        return;
      }
    }

    // ERROR //
    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 30	com/kaixin001/fragment/ViewAlbumPhotoFragment$GetVisitorsTask:stop	Z
      //   4: ifeq +8 -> 12
      //   7: iconst_1
      //   8: invokestatic 56	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   11: areturn
      //   12: aload_0
      //   13: getfield 28	com/kaixin001/fragment/ViewAlbumPhotoFragment$GetVisitorsTask:pids	Lcom/kaixin001/model/ItemManager;
      //   16: astore_3
      //   17: aload_3
      //   18: monitorenter
      //   19: aload_0
      //   20: getfield 28	com/kaixin001/fragment/ViewAlbumPhotoFragment$GetVisitorsTask:pids	Lcom/kaixin001/model/ItemManager;
      //   23: invokevirtual 60	com/kaixin001/model/ItemManager:getSize	()I
      //   26: ifne +15 -> 41
      //   29: aload_0
      //   30: iconst_m1
      //   31: putfield 32	com/kaixin001/fragment/ViewAlbumPhotoFragment$GetVisitorsTask:wait	I
      //   34: aload_0
      //   35: getfield 28	com/kaixin001/fragment/ViewAlbumPhotoFragment$GetVisitorsTask:pids	Lcom/kaixin001/model/ItemManager;
      //   38: invokevirtual 62	java/lang/Object:wait	()V
      //   41: aload_3
      //   42: monitorexit
      //   43: aload_0
      //   44: iconst_0
      //   45: putfield 32	com/kaixin001/fragment/ViewAlbumPhotoFragment$GetVisitorsTask:wait	I
      //   48: aload_0
      //   49: getfield 28	com/kaixin001/fragment/ViewAlbumPhotoFragment$GetVisitorsTask:pids	Lcom/kaixin001/model/ItemManager;
      //   52: iconst_0
      //   53: invokevirtual 66	com/kaixin001/model/ItemManager:removeItem	(Z)Ljava/lang/Object;
      //   56: checkcast 68	java/lang/String
      //   59: astore 5
      //   61: invokestatic 74	com/kaixin001/network/Protocol:getInstance	()Lcom/kaixin001/network/Protocol;
      //   64: ldc 76
      //   66: iconst_0
      //   67: aload_0
      //   68: getfield 18	com/kaixin001/fragment/ViewAlbumPhotoFragment$GetVisitorsTask:this$0	Lcom/kaixin001/fragment/ViewAlbumPhotoFragment;
      //   71: invokestatic 82	com/kaixin001/fragment/ViewAlbumPhotoFragment:access$8	(Lcom/kaixin001/fragment/ViewAlbumPhotoFragment;)Ljava/lang/String;
      //   74: aload 5
      //   76: invokevirtual 86	com/kaixin001/network/Protocol:makeGetPhotoVisitorRequest	(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      //   79: astore 6
      //   81: new 88	com/kaixin001/network/HttpProxy
      //   84: dup
      //   85: aload_0
      //   86: getfield 18	com/kaixin001/fragment/ViewAlbumPhotoFragment$GetVisitorsTask:this$0	Lcom/kaixin001/fragment/ViewAlbumPhotoFragment;
      //   89: invokevirtual 92	com/kaixin001/fragment/ViewAlbumPhotoFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
      //   92: invokespecial 95	com/kaixin001/network/HttpProxy:<init>	(Landroid/content/Context;)V
      //   95: astore 7
      //   97: aload 7
      //   99: aload 6
      //   101: aconst_null
      //   102: aconst_null
      //   103: invokevirtual 99	com/kaixin001/network/HttpProxy:httpGet	(Ljava/lang/String;Lcom/kaixin001/network/HttpRequestState;Lcom/kaixin001/network/HttpProgressListener;)Ljava/lang/String;
      //   106: astore 16
      //   108: aload 16
      //   110: astore 9
      //   112: new 101	org/json/JSONObject
      //   115: dup
      //   116: aload 9
      //   118: invokespecial 103	org/json/JSONObject:<init>	(Ljava/lang/String;)V
      //   121: astore 10
      //   123: aload 10
      //   125: ldc 105
      //   127: iconst_0
      //   128: invokevirtual 109	org/json/JSONObject:optInt	(Ljava/lang/String;I)I
      //   131: istore 11
      //   133: aload 10
      //   135: ldc 111
      //   137: iconst_0
      //   138: invokevirtual 109	org/json/JSONObject:optInt	(Ljava/lang/String;I)I
      //   141: istore 12
      //   143: iload 11
      //   145: iconst_1
      //   146: if_icmpne -146 -> 0
      //   149: aload_0
      //   150: getfield 18	com/kaixin001/fragment/ViewAlbumPhotoFragment$GetVisitorsTask:this$0	Lcom/kaixin001/fragment/ViewAlbumPhotoFragment;
      //   153: aload 5
      //   155: invokestatic 115	com/kaixin001/fragment/ViewAlbumPhotoFragment:access$9	(Lcom/kaixin001/fragment/ViewAlbumPhotoFragment;Ljava/lang/String;)Lcom/kaixin001/item/PhotoItem;
      //   158: astore 13
      //   160: aload 13
      //   162: ifnull -162 -> 0
      //   165: aload 13
      //   167: iload 12
      //   169: putfield 120	com/kaixin001/item/PhotoItem:visitorNum	I
      //   172: invokestatic 126	android/os/Message:obtain	()Landroid/os/Message;
      //   175: astore 14
      //   177: aload 14
      //   179: bipush 115
      //   181: putfield 129	android/os/Message:what	I
      //   184: aload 14
      //   186: aload 5
      //   188: putfield 133	android/os/Message:obj	Ljava/lang/Object;
      //   191: aload_0
      //   192: getfield 18	com/kaixin001/fragment/ViewAlbumPhotoFragment$GetVisitorsTask:this$0	Lcom/kaixin001/fragment/ViewAlbumPhotoFragment;
      //   195: getfield 137	com/kaixin001/fragment/ViewAlbumPhotoFragment:mHandler	Landroid/os/Handler;
      //   198: aload 14
      //   200: invokevirtual 143	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
      //   203: pop
      //   204: goto -204 -> 0
      //   207: astore_2
      //   208: iconst_0
      //   209: invokestatic 56	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   212: areturn
      //   213: astore 4
      //   215: aload_3
      //   216: monitorexit
      //   217: aload 4
      //   219: athrow
      //   220: astore 8
      //   222: invokestatic 147	com/kaixin001/fragment/ViewAlbumPhotoFragment:access$4	()Ljava/lang/String;
      //   225: ldc 149
      //   227: aload 8
      //   229: invokestatic 155	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
      //   232: aconst_null
      //   233: astore 9
      //   235: goto -123 -> 112
      //
      // Exception table:
      //   from	to	target	type
      //   12	19	207	java/lang/Exception
      //   43	97	207	java/lang/Exception
      //   112	143	207	java/lang/Exception
      //   149	160	207	java/lang/Exception
      //   165	204	207	java/lang/Exception
      //   217	220	207	java/lang/Exception
      //   222	232	207	java/lang/Exception
      //   19	41	213	finally
      //   41	43	213	finally
      //   215	217	213	finally
      //   97	108	220	java/lang/Exception
    }

    protected void onCancelledA()
    {
      this.pids.clear();
      this.stop = true;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class HideButtonsTask extends TimerTask
  {
    private HideButtonsTask()
    {
    }

    public void run()
    {
      if (ViewAlbumPhotoFragment.this.isNeedReturn())
        return;
      Message localMessage = Message.obtain();
      localMessage.what = 113;
      ViewAlbumPhotoFragment.this.mHandler.sendMessage(localMessage);
    }
  }

  private class LoadPhotoDelayTimerTask extends TimerTask
  {
    private String mPhotoUrl;

    LoadPhotoDelayTimerTask(String arg2)
    {
      Object localObject;
      this.mPhotoUrl = localObject;
    }

    public void run()
    {
      if (ViewAlbumPhotoFragment.this.isNeedReturn());
      do
        return;
      while (TextUtils.isEmpty(this.mPhotoUrl));
      ViewAlbumPhotoFragment.this.mImgDownloader.downloadImageSync(this.mPhotoUrl);
      ViewAlbumPhotoFragment.this.mLatestDownloadPhotoUrl = this.mPhotoUrl;
      Message localMessage = new Message();
      localMessage.what = 8001;
      localMessage.obj = this.mPhotoUrl;
      ViewAlbumPhotoFragment.this.mHandler.sendMessage(localMessage);
    }
  }

  public class PhotoNameOnClickListener
    implements View.OnClickListener
  {
    public String pid;
    public int visitorCount;

    public PhotoNameOnClickListener()
    {
    }

    public void init(String paramString, int paramInt)
    {
      this.pid = paramString;
      this.visitorCount = paramInt;
    }

    public void onClick(View paramView)
    {
      IntentUtil.showVisitorHistroy(ViewAlbumPhotoFragment.this, this.pid, ViewAlbumPhotoFragment.this.mFuid, this.visitorCount);
    }
  }

  public class RecommandedAlbumListOnClickListener
    implements View.OnClickListener
  {
    private AlbumInfo info;

    public RecommandedAlbumListOnClickListener(AlbumInfo arg2)
    {
      Object localObject;
      this.info = localObject;
    }

    public void onClick(View paramView)
    {
      ViewAlbumPhotoFragment.this.currAlbumUnit = this.info;
      String str = this.info.albumsFeedPrivacy;
      if (("0".equals(str)) || (("1".equals(str)) && (this.info.albumsFeedIsFriend)) || (("2".equals(str)) && (User.getInstance().getUID().equals(ViewAlbumPhotoFragment.this.mFuid))) || (("3".equals(str)) && (User.getInstance().getUID().equals(ViewAlbumPhotoFragment.this.mFuid))))
        ViewAlbumPhotoFragment.this.showAlbumView("");
      do
        return;
      while ((!"2".equals(str)) || (User.getInstance().getUID().equals(ViewAlbumPhotoFragment.this.mFuid)));
      ViewAlbumPhotoFragment.this.showInputPwd();
    }
  }

  private static class ViewHolder
  {
    public ImageView imageView;
  }

  class ViewPhotoAdapter extends PagerAdapter
  {
    private View currentView;
    private Map<Integer, View> map = new HashMap();

    public ViewPhotoAdapter()
    {
    }

    public void destroyItem(View paramView, int paramInt, Object paramObject)
    {
      ((ViewPager)paramView).removeView((View)paramObject);
    }

    public int getCount()
    {
      return ViewAlbumPhotoFragment.this.mPhotoList.size();
    }

    public int getItemPosition(Object paramObject)
    {
      return -2;
    }

    public View getPrimary()
    {
      return this.currentView;
    }

    public Object instantiateItem(View paramView, int paramInt)
    {
      View localView;
      if (this.map.containsKey(Integer.valueOf(paramInt)))
        localView = (View)this.map.get(Integer.valueOf(paramInt));
      while (true)
      {
        ViewAlbumPhotoFragment.this.setImage(localView, paramInt);
        ((ViewPager)paramView).addView(localView);
        return localView;
        localView = LayoutInflater.from(ViewAlbumPhotoFragment.this.getActivity()).inflate(2130903406, null);
        this.map.put(Integer.valueOf(paramInt), localView);
      }
    }

    public boolean isViewFromObject(View paramView, Object paramObject)
    {
      return paramView == paramObject;
    }

    public void setPrimaryItem(View paramView, int paramInt, Object paramObject)
    {
      this.currentView = ((View)paramObject);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.ViewAlbumPhotoFragment
 * JD-Core Version:    0.6.0
 */