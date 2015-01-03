package com.kaixin001.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.location.Location;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.item.DiaryUploadTask;
import com.kaixin001.model.FaceModel;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.util.ActivityUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.KXUBLog;
import com.kaixin001.util.ScreenUtil;
import com.kaixin001.util.StringUtil;
import com.kaixin001.view.FaceKeyboardView;
import com.kaixin001.view.FaceKeyboardView.OnFaceSelectedListener;
import com.kaixin001.view.KXEditTextView;
import com.kaixin001.view.LocationMarkerView;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class WriteDiaryFragment extends BaseFragment
{
  private static final int CAN_ADD_LOCATION = 0;
  private static final int CAN_REMOVE_LOCATION = 1;
  private static final String E_SYMBOL_CONTENT = "[|ec|]";
  private static final String E_SYMBOL_FACE = "[|e|]";
  private static final String E_SYMBOL_ID = "[|ei|]";
  public static final String E_SYMBOL_PIC = "[|ep|]";
  private static final String E_SYMBOL_TITLE = "[|et|]";
  private static final int INSERT_PICTURE_NUM_LIMIT = 4;
  protected static final int INSERT_PIC_CAMERA = 301;
  protected static final int INSERT_PIC_GALLERY = 302;
  private static final int STATE_FINDING_LOCATION = 0;
  private static final int STATE_NO_LOCATION = 1;
  private static final int STATE_SET_LOCATION = 2;
  private static final String S_SYMBOL_CONTENT = "[|sc|]";
  private static final String S_SYMBOL_FACE = "[|s|]";
  private static final String S_SYMBOL_ID = "[|si|]";
  public static final String S_SYMBOL_PIC = "[|sp|]";
  private static final String S_SYMBOL_TITLE = "[|st|]";
  public static final String TAG = "WriteDiaryActivity";
  private String MDRAFT_FILENAME = "WriteDiaryDraft.kx";
  private boolean canAddLocation = false;
  private LocationMarkerView locationMarker;
  private KXEditTextView mEditContent = null;
  private EditText mEditTitle = null;
  private FaceKeyboardView mFaceKeyboardView;
  private FaceModel mFaceModel;
  private ImageView mInsertFaceButton = null;
  private ImageView mInsertLocationButton = null;
  private ImageView mInsertPictureButton = null;
  private int mInsertedPictureIdBase = 1;
  private HashMap<String, String> mInsertedPictureMap = new HashMap();
  private Boolean mIsPostingFlag = Boolean.valueOf(false);
  private LoadPhotoTask mLoadPhotoTask = null;
  private HashMap<String, Bitmap> mMapPhotos = new HashMap();
  boolean mNeedLocation = Setting.getInstance().gpsEnabled();
  private int mOrientation = 1;
  private ProgressDialog mProgressDialog = null;
  private String seletedLandmark;

  private void InsertPicture(int paramInt, Bitmap paramBitmap)
  {
    Editable localEditable;
    int i;
    int j;
    String str;
    if (paramBitmap != null)
    {
      localEditable = this.mEditContent.getText();
      i = Selection.getSelectionStart(localEditable);
      j = Selection.getSelectionStart(localEditable);
      if ((i == -1) || (j == -1))
      {
        i = localEditable.length();
        j = localEditable.length();
      }
      str = "[|sp|]" + paramInt + "[|ep|]";
      if (localEditable.length() <= -4 + (500 - str.length()))
        break label97;
    }
    label97: 
    do
    {
      return;
      localEditable.replace(i, j, "\n" + str + "\n");
      int k = paramBitmap.getWidth();
      if (k > 80)
        paramBitmap = Bitmap.createScaledBitmap(paramBitmap, 80, 80 * paramBitmap.getHeight() / k, true);
      this.mMapPhotos.put(str, paramBitmap);
    }
    while ((1 != this.mOrientation) && (!Setting.getInstance().isShowFaceIconInLandScape()));
    localEditable.setSpan(new ImageSpan(paramBitmap), i + 1, i + 1 + str.length(), 33);
  }

  private void LoadDiaryDraftContentElements(String paramString)
  {
    SpannableString localSpannableString = StringUtil.convertTextToMessageFace(paramString);
    int i = 0;
    String str1 = FileUtil.getCacheDir(getActivity().getCacheDir().getAbsolutePath(), User.getInstance().getUID());
    while (true)
    {
      int j = paramString.indexOf("[|sp|]", i);
      if (j < 0);
      int k;
      do
      {
        this.mEditContent.setText(localSpannableString);
        return;
        k = paramString.indexOf("[|ep|]", i);
      }
      while (k < 0);
      String str2 = paramString.substring(j + "[|sp|]".length(), k);
      File localFile = new File(str1, "kx_insert_tmp_" + str2 + ".jpg");
      if (localFile.exists())
      {
        Bitmap localBitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
        int m = localBitmap.getWidth();
        if (m > 80)
          localBitmap = Bitmap.createScaledBitmap(localBitmap, 80, 80 * localBitmap.getHeight() / m, true);
        this.mMapPhotos.put(paramString.substring(j, k + "[|ep|]".length()), localBitmap);
        if ((1 == this.mOrientation) || (Setting.getInstance().isShowFaceIconInLandScape()))
        {
          ImageSpan localImageSpan = new ImageSpan(localBitmap);
          localSpannableString.setSpan(localImageSpan, j, k + "[|ep|]".length(), 33);
        }
        this.mInsertedPictureMap.put(str2, localFile.getAbsolutePath());
      }
      i = k + "[|ep|]".length();
    }
  }

  private void LoadDiayDraft()
  {
    String str1 = FileUtil.getCacheData(getActivity().getCacheDir().getAbsolutePath(), User.getInstance().getUID(), this.MDRAFT_FILENAME);
    if (str1 == null);
    String str4;
    do
    {
      int n;
      int i1;
      do
      {
        return;
        deleteDiaryTextDraft();
        String str2 = "";
        int i = str1.indexOf("[|st|]");
        int j = str1.indexOf("[|et|]");
        if ((i != -1) && (j != -1))
          str2 = str1.substring(i + "[|st|]".length(), j);
        if (str2.length() > 0)
          this.mEditTitle.setText(str2);
        String str3 = "";
        int k = str1.indexOf("[|sc|]");
        int m = str1.indexOf("[|ec|]");
        if ((k != -1) && (m != -1))
          str3 = str1.substring(k + "[|sc|]".length(), m);
        if (str3.length() > 0)
          LoadDiaryDraftContentElements(str3);
        n = str1.indexOf("[|si|]");
        i1 = str1.indexOf("[|ei|]");
      }
      while ((n == -1) || (i1 == -1));
      str4 = str1.substring(n + "[|si|]".length(), i1);
    }
    while (str4.length() <= 0);
    this.mInsertedPictureIdBase = Integer.valueOf(str4).intValue();
  }

  private void LoadInsertedPicture(String paramString)
  {
    KXLog.d("WriteDiaryActivity", "LoadInsertedPicture");
    if (!new File(paramString).exists())
    {
      Toast.makeText(getActivity(), 2131427841, 0).show();
      return;
    }
    this.mLoadPhotoTask = new LoadPhotoTask(null);
    String[] arrayOfString = { paramString };
    this.mLoadPhotoTask.execute(arrayOfString);
    this.mProgressDialog = ProgressDialog.show(getActivity(), "", getResources().getText(2131427649), true, true, new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramDialogInterface)
      {
        WriteDiaryFragment.this.mProgressDialog = null;
        WriteDiaryFragment.this.mLoadPhotoTask.cancel(true);
      }
    });
  }

  // ERROR //
  private String SaveBitmapToFile(Bitmap paramBitmap, String paramString)
  {
    // Byte code:
    //   0: new 162	java/lang/StringBuilder
    //   3: dup
    //   4: ldc_w 262
    //   7: invokespecial 165	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   10: aload_2
    //   11: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   14: ldc_w 264
    //   17: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   20: invokevirtual 176	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   23: astore_3
    //   24: new 232	java/io/File
    //   27: dup
    //   28: aload_0
    //   29: invokevirtual 224	com/kaixin001/fragment/WriteDiaryFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
    //   32: invokevirtual 230	android/support/v4/app/FragmentActivity:getCacheDir	()Ljava/io/File;
    //   35: invokevirtual 235	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   38: invokestatic 240	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
    //   41: invokevirtual 243	com/kaixin001/model/User:getUID	()Ljava/lang/String;
    //   44: invokestatic 248	com/kaixin001/util/FileUtil:getCacheDir	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   47: aload_3
    //   48: invokespecial 267	java/io/File:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   51: astore 4
    //   53: aload 4
    //   55: invokestatic 360	com/kaixin001/util/FileUtil:makeEmptyFile	(Ljava/io/File;)Z
    //   58: ifne +5 -> 63
    //   61: aconst_null
    //   62: areturn
    //   63: aconst_null
    //   64: astore 5
    //   66: aload_1
    //   67: invokevirtual 190	android/graphics/Bitmap:getWidth	()I
    //   70: istore 8
    //   72: aconst_null
    //   73: astore 5
    //   75: iload 8
    //   77: sipush 600
    //   80: if_icmple +23 -> 103
    //   83: aload_1
    //   84: sipush 600
    //   87: sipush 600
    //   90: aload_1
    //   91: invokevirtual 193	android/graphics/Bitmap:getHeight	()I
    //   94: imul
    //   95: iload 8
    //   97: idiv
    //   98: iconst_1
    //   99: invokestatic 197	android/graphics/Bitmap:createScaledBitmap	(Landroid/graphics/Bitmap;IIZ)Landroid/graphics/Bitmap;
    //   102: astore_1
    //   103: new 362	java/io/FileOutputStream
    //   106: dup
    //   107: aload 4
    //   109: invokespecial 365	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   112: astore 9
    //   114: aload_1
    //   115: getstatic 371	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
    //   118: bipush 75
    //   120: aload 9
    //   122: invokevirtual 375	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   125: pop
    //   126: aload 9
    //   128: invokevirtual 378	java/io/FileOutputStream:flush	()V
    //   131: aload 4
    //   133: invokevirtual 235	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   136: astore 11
    //   138: aload 9
    //   140: invokestatic 384	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   143: aload 11
    //   145: areturn
    //   146: astore 7
    //   148: ldc 53
    //   150: ldc_w 386
    //   153: aload 7
    //   155: invokestatic 390	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   158: aload 5
    //   160: invokestatic 384	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   163: aconst_null
    //   164: areturn
    //   165: astore 6
    //   167: aload 5
    //   169: invokestatic 384	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   172: aload 6
    //   174: athrow
    //   175: astore 6
    //   177: aload 9
    //   179: astore 5
    //   181: goto -14 -> 167
    //   184: astore 7
    //   186: aload 9
    //   188: astore 5
    //   190: goto -42 -> 148
    //
    // Exception table:
    //   from	to	target	type
    //   66	72	146	java/lang/Exception
    //   83	103	146	java/lang/Exception
    //   103	114	146	java/lang/Exception
    //   66	72	165	finally
    //   83	103	165	finally
    //   103	114	165	finally
    //   148	158	165	finally
    //   114	138	175	finally
    //   114	138	184	java/lang/Exception
  }

  private void SaveDiaryDraft(String paramString1, String paramString2)
  {
    String str1 = "";
    if (paramString1.length() > 0)
      str1 = str1 + "[|st|]" + paramString1 + "[|et|]";
    if (paramString2.length() > 0)
      str1 = str1 + "[|sc|]" + paramString2 + "[|ec|]";
    String str2 = str1 + "[|si|]" + this.mInsertedPictureIdBase + "[|ei|]";
    FileUtil.setCacheData(getActivity().getCacheDir().getAbsolutePath(), User.getInstance().getUID(), this.MDRAFT_FILENAME, str2);
  }

  private void backOrExit()
  {
    if (this.mIsPostingFlag.booleanValue())
    {
      cancelTasks();
      showMainLayout(true);
      showProgressView(false, 2131427677);
      setPostingStatus(false);
      return;
    }
    String str1 = this.mEditTitle.getText().toString();
    String str2 = this.mEditContent.getText().toString();
    String str3 = str1.replace(" ", "").replace("\n", "");
    String str4 = str2.replace(" ", "").replace("\n", "");
    if ((str3.length() == 0) && (str4.length() == 0))
    {
      deleteDiaryDraft();
      finish();
      return;
    }
    showSaveDiaryDraftOptions();
  }

  private void cancelTasks()
  {
    if ((this.mLoadPhotoTask != null) && (this.mLoadPhotoTask.getStatus() != AsyncTask.Status.FINISHED))
      this.mLoadPhotoTask.cancel(true);
  }

  private void chgFilesName(HashMap<String, String> paramHashMap)
  {
    Set localSet = paramHashMap.entrySet();
    try
    {
      Iterator localIterator = localSet.iterator();
      while (true)
      {
        if (!localIterator.hasNext())
          return;
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        String str = FileUtil.getDynamicFilePath((String)localEntry.getValue());
        FileUtil.renameCachePath((String)localEntry.getValue(), str);
        localEntry.setValue(str);
      }
    }
    catch (Exception localException)
    {
      KXLog.e("WriteDiaryActivity", "chgFilesName", localException);
    }
  }

  private void deleteDiaryDraft()
  {
    this.mEditTitle.setText("");
    this.mEditContent.setText("");
    deleteDiaryTextDraft();
    deleteDiaryImgDraft();
  }

  private void deleteDiaryImgDraft()
  {
    Iterator localIterator;
    if (this.mInsertedPictureMap.size() > 0)
      localIterator = this.mInsertedPictureMap.entrySet().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      FileUtil.deleteFileWithoutCheckReturnValue((String)((Map.Entry)localIterator.next()).getValue());
    }
  }

  private void deleteDiaryTextDraft()
  {
    FileUtil.deleteFileWithoutCheckReturnValue(new File(FileUtil.getCacheDir(getActivity().getCacheDir().getAbsolutePath(), User.getInstance().getUID()), this.MDRAFT_FILENAME));
  }

  private void doPostDiary()
  {
    Editable localEditable1 = this.mEditTitle.getText();
    if (localEditable1.length() == 0)
      DialogUtil.showKXAlertDialog(getActivity(), 2131427673, null);
    Editable localEditable2;
    do
    {
      return;
      if (localEditable1.toString().replace(" ", "").replace("\n", "").length() == 0)
      {
        DialogUtil.showKXAlertDialog(getActivity(), 2131427673, null);
        return;
      }
      localEditable2 = this.mEditContent.getText();
      if (localEditable2.length() == 0)
      {
        DialogUtil.showKXAlertDialog(getActivity(), 2131427674, null);
        return;
      }
      int i;
      int j;
      for (String str1 = localEditable2.toString(); ; str1 = str1.substring(0, i) + str1.substring(j + "[|ep|]".length()))
      {
        i = str1.indexOf("[|sp|]", 0);
        if (i < 0);
        do
        {
          if (str1.replace("[|s|]", "").replace("[|e|]", "").replace(" ", "").replace("\n", "").length() != 0)
            break;
          DialogUtil.showKXAlertDialog(getActivity(), 2131427674, null);
          return;
          j = str1.indexOf("[|ep|]", 0);
        }
        while (j < 0);
      }
    }
    while (!super.checkNetworkAndHint(true));
    String str2 = localEditable2.toString();
    Iterator localIterator;
    DiaryUploadTask localDiaryUploadTask;
    if (this.mInsertedPictureMap.size() > 0)
    {
      localIterator = this.mInsertedPictureMap.entrySet().iterator();
      if (localIterator.hasNext());
    }
    else
    {
      localDiaryUploadTask = new DiaryUploadTask(getActivity().getApplicationContext());
      localDiaryUploadTask.setTitle(localEditable1.toString());
      localDiaryUploadTask.setContent(str2);
      int k = this.locationMarker.getVisibility();
      Location localLocation = null;
      if (k == 0)
      {
        this.seletedLandmark = this.locationMarker.getLandmark();
        localLocation = this.locationMarker.getLocation();
      }
      if (TextUtils.isEmpty(this.seletedLandmark))
        break label559;
      localDiaryUploadTask.setLocationName(this.seletedLandmark);
      localDiaryUploadTask.setLatitude(String.valueOf(localLocation.getLatitude()));
      localDiaryUploadTask.setLongitude(String.valueOf(localLocation.getLongitude()));
    }
    while (true)
    {
      if (this.mInsertedPictureMap.size() > 0)
      {
        chgFilesName(this.mInsertedPictureMap);
        localDiaryUploadTask.setInsertedPic(this.mInsertedPictureMap);
        this.mInsertedPictureMap = new HashMap();
      }
      UploadTaskListEngine.getInstance().addUploadTask(localDiaryUploadTask);
      Toast.makeText(getActivity().getApplication(), 2131428306, 0).show();
      finish();
      return;
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      StringBuilder localStringBuilder1 = new StringBuilder("[|sp|]");
      String str3 = (String)localEntry.getKey() + "[|ep|]";
      StringBuilder localStringBuilder2 = new StringBuilder("[|sp|]upload_image_");
      str2 = str2.replace(str3, (String)localEntry.getKey() + "[|ep|]");
      break;
      label559: localDiaryUploadTask.setLocationName("");
    }
  }

  private void initFaceKeyBoardLayout()
  {
    this.mFaceKeyboardView = ((FaceKeyboardView)findViewById(2131361976));
    this.mFaceKeyboardView.setOnFaceSelectedListener(new FaceKeyboardView.OnFaceSelectedListener()
    {
      public void FaceSelected(int paramInt, String paramString, Bitmap paramBitmap)
      {
        switch (paramInt)
        {
        default:
          WriteDiaryFragment.this.insertFaceIcon(paramString, paramBitmap);
        case -2:
        case -1:
        }
        int i;
        do
        {
          return;
          i = WriteDiaryFragment.this.mEditContent.getSelectionStart();
        }
        while (i <= 0);
        String str1 = WriteDiaryFragment.this.mEditContent.getText().toString();
        ArrayList localArrayList = FaceModel.getInstance().getAllFaceStringSortByLength();
        int j = 1;
        Iterator localIterator = localArrayList.iterator();
        if (!localIterator.hasNext());
        while (true)
        {
          WriteDiaryFragment.this.mEditContent.getText().delete(i - j, i);
          return;
          String str2 = (String)localIterator.next();
          if (!str1.endsWith(str2))
            break;
          j = str2.length();
        }
      }
    });
    this.mFaceKeyboardView.init(getActivity());
    showFaceKeyboardView(false);
  }

  private void initViewHandles()
  {
    this.locationMarker = ((LocationMarkerView)findViewById(2131363986));
    if (this.mNeedLocation)
    {
      this.locationMarker.toggleState();
      this.locationMarker.setVisibility(0);
    }
  }

  private void saveLocationPreference()
  {
  }

  private void setAddLocationButtonState(int paramInt)
  {
  }

  private void setButtonHandlers()
  {
    this.mInsertFaceButton = ((ImageView)findViewById(2131364066));
    this.mInsertFaceButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (WriteDiaryFragment.this.mEditContent.getText().length() > 490)
        {
          Toast.makeText(WriteDiaryFragment.this.getActivity(), 2131427676, 0).show();
          return;
        }
        WriteDiaryFragment.this.showFaceListDialog(true);
      }
    });
    this.mInsertPictureButton = ((ImageView)findViewById(2131364067));
    this.mInsertPictureButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (WriteDiaryFragment.this.mEditContent.getText().length() > 480)
        {
          Toast.makeText(WriteDiaryFragment.this.getActivity(), 2131427675, 0).show();
          return;
        }
        if (WriteDiaryFragment.this.mInsertedPictureMap.size() < 4)
        {
          WriteDiaryFragment.this.hideKeyboard();
          WriteDiaryFragment.this.selectPictureFromGallery();
          return;
        }
        String str = StringUtil.replaceTokenWith(WriteDiaryFragment.this.getResources().getString(2131427672), "*", String.valueOf(4));
        Toast.makeText(WriteDiaryFragment.this.getActivity(), str, 0).show();
      }
    });
    this.mInsertLocationButton = ((ImageView)findViewById(2131364068));
    if (!this.mNeedLocation)
    {
      this.mInsertLocationButton.setVisibility(8);
      return;
    }
    this.mInsertLocationButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        WriteDiaryFragment.this.locationMarker.setVisibility(0);
        WriteDiaryFragment.this.locationMarker.toggleState();
      }
    });
  }

  private void setLocationDescription(int paramInt, String paramString)
  {
    this.locationMarker.toggleState();
    this.locationMarker.setVisibility(0);
  }

  private void setPostingStatus(boolean paramBoolean)
  {
    Button localButton = (Button)findViewById(2131362928);
    this.mIsPostingFlag = Boolean.valueOf(paramBoolean);
    if (this.mIsPostingFlag.booleanValue())
    {
      localButton.setTextColor(getResources().getColor(2130839395));
      return;
    }
    localButton.setTextColor(getResources().getColor(2130839385));
  }

  private void showFaceKeyboardView(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mFaceKeyboardView.setVisibility(0);
      ActivityUtil.hideInputMethod(getActivity());
      return;
    }
    this.mFaceKeyboardView.setVisibility(8);
  }

  private void showInsertPictureOptions()
  {
    if (this.mEditContent.getText().length() > 500 - "\n[|sp|]1[|ep|]\n".length())
      return;
    selectPictureFromGallery();
  }

  private void showMainLayout(boolean paramBoolean)
  {
    if (this.mNeedLocation)
    {
      View localView2 = findViewById(2131364063);
      if (paramBoolean)
      {
        localView2.setVisibility(0);
        return;
      }
      localView2.setVisibility(8);
      return;
    }
    View localView1 = findViewById(2131364063);
    if (paramBoolean)
    {
      localView1.setVisibility(0);
      ((Button)findViewById(2131364068)).setVisibility(8);
      return;
    }
    localView1.setVisibility(8);
  }

  private void showProgressView(boolean paramBoolean, int paramInt)
  {
    View localView = findViewById(2131364069);
    if (paramBoolean)
    {
      ((TextView)localView.findViewById(2131364070)).setText(paramInt);
      localView.setVisibility(0);
      return;
    }
    localView.setVisibility(8);
  }

  private void showSaveDiaryDraftOptions()
  {
    DialogUtil.showSelectListDlg(getActivity(), 2131427708, getResources().getStringArray(2131492883), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        if (paramInt == 0)
        {
          String str1 = WriteDiaryFragment.this.mEditTitle.getText().toString();
          String str2 = WriteDiaryFragment.this.mEditContent.getText().toString();
          WriteDiaryFragment.this.SaveDiaryDraft(str1, str2);
        }
        while (true)
        {
          WriteDiaryFragment.this.finish();
          return;
          WriteDiaryFragment.this.deleteDiaryDraft();
        }
      }
    }
    , 1);
  }

  protected void dealPhotoResult(String paramString1, String paramString2)
  {
    try
    {
      LoadInsertedPicture(paramString1);
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("WriteDiaryActivity", "onActivityResult", localException);
    }
  }

  protected void insertFaceIcon(int paramInt)
  {
    KXLog.d("diaryActivity", "InsertOneFaceIcon");
    String str;
    Editable localEditable;
    int i;
    int j;
    if (this.mFaceModel.getStateFaceStrings() != null)
    {
      str = (String)this.mFaceModel.getStateFaceStrings().get(paramInt);
      localEditable = this.mEditContent.getText();
      i = Selection.getSelectionStart(localEditable);
      j = Selection.getSelectionEnd(localEditable);
      if ((i == -1) || (j == -1))
      {
        i = localEditable.length();
        j = localEditable.length();
      }
      if (localEditable.length() <= 500 - str.length())
        break label100;
    }
    label100: 
    do
    {
      return;
      localEditable.replace(i, j, str.subSequence(0, str.length()));
    }
    while ((1 != this.mOrientation) && (!Setting.getInstance().isShowFaceIconInLandScape()));
    localEditable.setSpan(new ImageSpan((Bitmap)this.mFaceModel.getStateFaceIcons().get(paramInt)), i, i + str.length(), 33);
  }

  protected void insertFaceIcon(String paramString, Bitmap paramBitmap)
  {
    if ((TextUtils.isEmpty(paramString)) || (paramBitmap == null));
    Editable localEditable;
    int i;
    do
    {
      int j;
      do
      {
        return;
        localEditable = this.mEditContent.getText();
        i = Selection.getSelectionStart(localEditable);
        j = Selection.getSelectionEnd(localEditable);
        if ((i != -1) && (j != -1))
          continue;
        i = localEditable.length();
        j = localEditable.length();
      }
      while (localEditable.length() > 500 - paramString.length());
      localEditable.replace(i, j, paramString.subSequence(0, paramString.length()));
    }
    while ((1 != this.mOrientation) && (!Setting.getInstance().isShowFaceIconInLandScape()));
    localEditable.setSpan(new ImageSpan(paramBitmap), i, i + paramString.length(), 33);
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    int i;
    int j;
    String str1;
    SpannableString localSpannableString;
    Iterator localIterator;
    if (this.mOrientation != paramConfiguration.orientation)
    {
      this.mOrientation = paramConfiguration.orientation;
      i = this.mEditContent.getSelectionStart();
      j = this.mEditContent.getSelectionEnd();
      str1 = this.mEditContent.getText().toString();
      if ((1 != this.mOrientation) && (!Setting.getInstance().isShowFaceIconInLandScape()))
        break label200;
      localSpannableString = StringUtil.convertTextToMessageFace(str1);
      if (localSpannableString != null)
      {
        Set localSet = this.mMapPhotos.keySet();
        if (localSet != null)
        {
          localIterator = localSet.iterator();
          if (localIterator.hasNext())
            break label135;
        }
      }
      this.mEditContent.setText(localSpannableString);
    }
    while (true)
    {
      this.mEditContent.setSelection(i, j);
      super.onConfigurationChanged(paramConfiguration);
      return;
      label135: String str2 = (String)localIterator.next();
      int k = str1.indexOf(str2);
      if (k < 0)
        break;
      localSpannableString.setSpan(new ImageSpan((Bitmap)this.mMapPhotos.get(str2)), k, k + str2.length(), 33);
      break;
      label200: this.mEditContent.setText(str1);
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903423, paramViewGroup, false);
  }

  public void onDestroy()
  {
    cancelTasks();
    if (this.mMapPhotos != null)
    {
      this.mMapPhotos.clear();
      this.mMapPhotos = null;
    }
    super.onDestroy();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      if (this.mIsPostingFlag.booleanValue())
      {
        cancelTasks();
        showMainLayout(true);
        showProgressView(false, 2131427677);
        setPostingStatus(false);
        return true;
      }
      String str1 = this.mEditTitle.getText().toString();
      String str2 = this.mEditContent.getText().toString();
      String str3 = str1.replace(" ", "").replace("\n", "");
      String str4 = str2.replace(" ", "").replace("\n", "");
      if ((str3.length() == 0) && (str4.length() == 0))
      {
        deleteDiaryDraft();
        finish();
        return true;
      }
      showSaveDiaryDraftOptions();
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 67)
      this.mEditContent.deletePicIfBefore();
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  public void onResume()
  {
    super.onResume();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mFaceModel = FaceModel.getInstance();
    this.mEditContent = ((KXEditTextView)findViewById(2131364065));
    this.mEditContent.addTextChangedListener(new ContentTextWatcher(null));
    this.mEditTitle = ((EditText)findViewById(2131364064));
    this.mEditTitle.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View paramView, boolean paramBoolean)
      {
        ImageView localImageView1 = (ImageView)WriteDiaryFragment.this.findViewById(2131364066);
        boolean bool1;
        ImageView localImageView2;
        boolean bool2;
        if (paramBoolean)
        {
          bool1 = false;
          localImageView1.setEnabled(bool1);
          localImageView2 = (ImageView)WriteDiaryFragment.this.findViewById(2131364067);
          bool2 = false;
          if (!paramBoolean)
            break label61;
        }
        while (true)
        {
          localImageView2.setEnabled(bool2);
          return;
          bool1 = true;
          break;
          label61: bool2 = true;
        }
      }
    });
    initViewHandles();
    setTitleBar();
    initFaceKeyBoardLayout();
    setButtonHandlers();
    LoadDiayDraft();
    this.mEditTitle.requestFocus();
    this.mOrientation = ScreenUtil.getOrientation(getActivity());
    KXUBLog.log("homepage_diary");
  }

  public void requestFinish()
  {
    backOrExit();
  }

  protected void setTitleBar()
  {
    ImageView localImageView1 = (ImageView)findViewById(2131362914);
    localImageView1.setVisibility(0);
    localImageView1.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        WriteDiaryFragment.this.hideKeyboard();
        WriteDiaryFragment.this.backOrExit();
      }
    });
    ImageView localImageView2 = (ImageView)findViewById(2131362928);
    localImageView2.setVisibility(0);
    localImageView2.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (!WriteDiaryFragment.this.mIsPostingFlag.booleanValue())
        {
          WriteDiaryFragment.this.hideKeyboard();
          WriteDiaryFragment.this.doPostDiary();
        }
      }
    });
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setText(2131427663);
    localTextView.setVisibility(0);
    localImageView2.setImageResource(2130839272);
  }

  public void showFaceListDialog(boolean paramBoolean)
  {
    ImageView localImageView = (ImageView)findViewById(2131364066);
    if (this.mFaceKeyboardView.getVisibility() == 0)
    {
      localImageView.setImageResource(2130838130);
      showFaceKeyboardView(false);
      ActivityUtil.showInputMethod(getActivity());
      return;
    }
    localImageView.setImageResource(2130838135);
    showFaceKeyboardView(true);
  }

  private class ContentTextWatcher
    implements TextWatcher
  {
    private ContentTextWatcher()
    {
    }

    public void afterTextChanged(Editable paramEditable)
    {
      String str1 = paramEditable.toString();
      ArrayList localArrayList1 = new ArrayList();
      int i = str1.indexOf("[|sp|]", 0);
      label29: Iterator localIterator;
      ArrayList localArrayList2;
      if (i < 0)
        if ((WriteDiaryFragment.this.mInsertedPictureMap.size() != localArrayList1.size()) && (WriteDiaryFragment.this.mInsertedPictureMap.size() > 0))
        {
          localIterator = WriteDiaryFragment.this.mInsertedPictureMap.entrySet().iterator();
          localArrayList2 = new ArrayList();
          if (localIterator.hasNext())
            break label162;
        }
      for (int i1 = 0; ; i1++)
      {
        if (i1 >= localArrayList2.size())
        {
          localArrayList1.clear();
          return;
          int j = str1.indexOf("[|ep|]", 0);
          if (j < 0)
            break label29;
          localArrayList1.add(str1.substring(i + "[|sp|]".length(), j));
          str1 = str1.substring(j + "[|ep|]".length());
          break;
          label162: String str2 = (String)((Map.Entry)localIterator.next()).getKey();
          label235: label237: for (int k = 0; ; k++)
          {
            int m = localArrayList1.size();
            int n = 0;
            if (k >= m);
            while (true)
            {
              if (n != 0)
                break label235;
              localArrayList2.add(str2);
              break;
              if (!str2.equals(localArrayList1.get(k)))
                break label237;
              n = 1;
            }
            break;
          }
        }
        String str3 = (String)localArrayList2.get(i1);
        FileUtil.deleteFileWithoutCheckReturnValue((String)WriteDiaryFragment.this.mInsertedPictureMap.get(str3));
        WriteDiaryFragment.this.mInsertedPictureMap.remove(str3);
        WriteDiaryFragment.this.mMapPhotos.remove("[|sp|]" + str3 + "[|ep|]");
      }
    }

    public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
    }

    public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
    }
  }

  private class LoadPhotoTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    private boolean isOutOfMemory = false;
    private Bitmap mLoadedBitmap = null;

    private LoadPhotoTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      if (paramArrayOfString.length < 1)
      {
        KXLog.d("WriteDiaryActivity", "doInBackground");
        return Boolean.valueOf(false);
      }
      try
      {
        String str = paramArrayOfString[0];
        KXLog.e("WriteDiaryActivity", "doInBackground" + str);
        if (str.length() > 0)
        {
          File localFile = new File(str);
          localOptions = new BitmapFactory.Options();
          l = localFile.length();
          if (l > 512000L)
            break label111;
        }
        for (localOptions.inSampleSize = 1; ; localOptions.inSampleSize = 2)
        {
          this.mLoadedBitmap = BitmapFactory.decodeFile(str, localOptions);
          return Boolean.valueOf(true);
          label111: if (l > 2048000L)
            break;
        }
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        while (true)
        {
          BitmapFactory.Options localOptions;
          long l;
          this.isOutOfMemory = true;
          System.gc();
          return Boolean.valueOf(false);
          localOptions.inSampleSize = (1 + (int)(Math.log(l / 512000L) / Math.log(2.0D)));
        }
      }
      catch (Exception localException)
      {
        KXLog.e("WriteDiaryActivity", "doInBackground", localException);
      }
      return Boolean.valueOf(false);
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      try
      {
        if ((!paramBoolean.booleanValue()) && (this.isOutOfMemory))
        {
          Toast.makeText(WriteDiaryFragment.this.getActivity(), 2131428311, 0).show();
          WriteDiaryFragment.this.finish();
          return;
        }
        if (this.mLoadedBitmap != null)
        {
          WriteDiaryFragment localWriteDiaryFragment = WriteDiaryFragment.this;
          int i = localWriteDiaryFragment.mInsertedPictureIdBase;
          localWriteDiaryFragment.mInsertedPictureIdBase = (i + 1);
          String str = WriteDiaryFragment.this.SaveBitmapToFile(this.mLoadedBitmap, String.valueOf(i));
          WriteDiaryFragment.this.mInsertedPictureMap.put(String.valueOf(i), str);
          WriteDiaryFragment.this.InsertPicture(i, this.mLoadedBitmap);
          this.mLoadedBitmap = null;
        }
        if (WriteDiaryFragment.this.mProgressDialog != null)
        {
          WriteDiaryFragment.this.mProgressDialog.dismiss();
          WriteDiaryFragment.this.mProgressDialog = null;
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("WriteDiaryActivity", "onPostExecute", localException);
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
 * Qualified Name:     com.kaixin001.fragment.WriteDiaryFragment
 * JD-Core Version:    0.6.0
 */