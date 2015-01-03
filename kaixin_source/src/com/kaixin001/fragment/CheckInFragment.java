package com.kaixin001.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Paint;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.businesslogic.ShowMenuDialog;
import com.kaixin001.engine.CheckInEngine;
import com.kaixin001.engine.FriendsEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.item.AtListItem;
import com.kaixin001.model.EditPictureModel;
import com.kaixin001.model.FaceModel;
import com.kaixin001.model.FriendsModel;
import com.kaixin001.model.FriendsModel.Friend;
import com.kaixin001.model.NewsModel;
import com.kaixin001.model.Setting;
import com.kaixin001.util.ActivityUtil;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.FancyDialogAdapter;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXExifInterface;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.KXTextUtil;
import com.kaixin001.util.ScreenUtil;
import com.kaixin001.util.StringUtil;
import com.kaixin001.view.FaceKeyboardView;
import com.kaixin001.view.FaceKeyboardView.OnFaceSelectedListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckInFragment extends BaseFragment
  implements View.OnClickListener, DialogInterface.OnClickListener, TextWatcher, AdapterView.OnItemClickListener
{
  public static final String AT_FRIENDS = "at_firends";
  private static final int CHECKIN_MAXNUM = 140;
  public static final String CHECKIN_SHOW_DETAIL = "poi_show_detail";
  public static final int CHECK_IN_REQUEST = 12;
  public static final int FRIEND_SELECTED = 4;
  private static final String FROM_WEBPAGE = "from_webpage";
  public static final String TAG_AT = "@";
  public static final String TAG_NAME_LEFT = "(#";
  public static final String TAG_NAME_RIGHT = "#)";
  private static final String ms_TempPicName = "kx_Record_draft_pic_1.jpg";
  private ImageView btnLeft;
  private Button btnPrivaceLock;
  private ImageView btnRight;
  private EditText evCheckIn;
  FancyDialogAdapter fancyDialogAdapter = new FancyDialogAdapter();
  private ArrayList<FriendsModel.Friend> friendslist = new ArrayList();
  GetDataTask getDataTask;
  private int index = -1;
  private ImageView ivCheckInPhoto;
  private String lat;
  private String[] locks;
  private String lon;
  private ListView lvReferList;
  private FaceKeyboardView mFaceKeyboardView;
  private FaceModel mFaceModel;
  private String mFilePathName = "";
  private LoadPhotoTask mLoadPhotoTask = null;
  private HashMap<String, Bitmap> mNameBmpMap = new HashMap();
  private int mOrientation = 1;
  private ProgressDialog mProgressDialog = null;
  private String mUploadFilePathName = null;
  private String mUploadPhotoFrom = "camera";
  private ShowMenuDialog menuUtil;
  private String picPath;
  private String poiId;
  private String poiLocation;
  private String poiName;
  private String privacy = "0";
  private ReferListAdapter referAdapter;
  private ArrayList<AtListItem> referList = new ArrayList();
  private PostCheckInTask submitTask;
  private TextView tvPoiLocation;
  private TextView tvPoiName;
  private TextView tvTitle;
  private LinearLayout waitLayout;

  private void addFriendList(FriendsModel.Friend paramFriend)
  {
    String str = paramFriend.getFuid();
    int i = this.friendslist.size();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        this.friendslist.add(paramFriend);
      do
        return;
      while (((FriendsModel.Friend)this.friendslist.get(j)).getFuid().compareTo(str) == 0);
    }
  }

  private void backOrExit()
  {
    hideInputMethod();
    finish();
  }

  private void changeContentForOrientation()
  {
    int i = this.evCheckIn.getSelectionStart();
    int j = this.evCheckIn.getSelectionEnd();
    String str1 = this.evCheckIn.getText().toString();
    SpannableString localSpannableString;
    Matcher localMatcher;
    if (((1 == this.mOrientation) || (Setting.getInstance().isShowFaceIconInLandScape())) && (!TextUtils.isEmpty(str1)))
    {
      localSpannableString = StringUtil.convertTextToStateFace(str1);
      if (localSpannableString != null)
      {
        localMatcher = Pattern.compile("@([0-9]+)\\(\\#(\\S+?)\\#\\)").matcher(localSpannableString);
        if (localMatcher.find());
      }
      else
      {
        this.evCheckIn.setText(localSpannableString);
      }
    }
    while (true)
    {
      this.evCheckIn.setSelection(i, j);
      return;
      String str2 = localMatcher.group();
      String str3 = localMatcher.group(2);
      int k = localMatcher.start();
      int m = localMatcher.end();
      Bitmap localBitmap = (Bitmap)this.mNameBmpMap.get(str2);
      if (localBitmap == null)
      {
        localBitmap = ImageCache.createStringBitmap("@" + str3, this.evCheckIn.getPaint());
        this.mNameBmpMap.put(str2, localBitmap);
      }
      localSpannableString.setSpan(new ImageSpan(localBitmap), k, m, 33);
      break;
      this.evCheckIn.setText(str1);
    }
  }

  private void getAllFriends()
  {
    try
    {
      this.friendslist.clear();
      ArrayList localArrayList = FriendsModel.getInstance().getFriends();
      if (localArrayList != null)
        this.friendslist.addAll(localArrayList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void goBack()
  {
    if (this.evCheckIn.getText().toString().trim().length() > 0)
    {
      DialogUtil.showKXAlertDialog(getActivity(), 2131427451, 2131427382, 2131427383, this, this);
      return;
    }
    backOrExit();
  }

  private int handlerStrSpace(int paramInt1, String paramString, int paramInt2)
  {
    int i;
    if (paramInt2 >= 1)
    {
      paramInt1 = 1;
      i = 1;
      if (i < paramInt2);
    }
    else
    {
      return paramInt1;
    }
    String str1 = String.valueOf(paramString.charAt(i));
    String str2 = String.valueOf(paramString.charAt(i - 1));
    if (str1.equals(" "))
      if (!str2.equals(" "))
        paramInt1++;
    while (true)
    {
      i++;
      break;
      paramInt1++;
    }
  }

  private void hideInputMethod()
  {
    View localView = getActivity().getCurrentFocus();
    if (localView != null)
      ((InputMethodManager)getActivity().getSystemService("input_method")).hideSoftInputFromWindow(localView.getWindowToken(), 2);
  }

  private void initFaceKeyBoardLayout(View paramView)
  {
    this.mFaceKeyboardView = ((FaceKeyboardView)paramView.findViewById(2131361976));
    this.mFaceKeyboardView.setOnFaceSelectedListener(new FaceKeyboardView.OnFaceSelectedListener()
    {
      public void FaceSelected(int paramInt, String paramString, Bitmap paramBitmap)
      {
        switch (paramInt)
        {
        default:
          CheckInFragment.this.insertFaceIcon(paramString, paramBitmap);
        case -2:
        case -1:
        }
        int i;
        do
        {
          return;
          i = CheckInFragment.this.evCheckIn.getSelectionStart();
        }
        while (i <= 0);
        String str1 = CheckInFragment.this.evCheckIn.getText().toString();
        ArrayList localArrayList = FaceModel.getInstance().getAllFaceStringSortByLength();
        int j = 1;
        Iterator localIterator = localArrayList.iterator();
        if (!localIterator.hasNext());
        while (true)
        {
          CheckInFragment.this.evCheckIn.getText().delete(i - j, i);
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

  private void initMainView(View paramView)
  {
    this.tvPoiName = ((TextView)paramView.findViewById(2131362008));
    this.tvPoiLocation = ((TextView)paramView.findViewById(2131362009));
    TextView localTextView1 = this.tvPoiName;
    String str1;
    TextView localTextView2;
    if (TextUtils.isEmpty(this.poiName))
    {
      str1 = "";
      localTextView1.setText(str1);
      localTextView2 = this.tvPoiLocation;
      if (!TextUtils.isEmpty(this.poiLocation))
        break label193;
    }
    label193: for (String str2 = ""; ; str2 = this.poiLocation)
    {
      localTextView2.setText(str2);
      ((LinearLayout)paramView.findViewById(2131362011)).setOnClickListener(this);
      this.ivCheckInPhoto = ((ImageView)paramView.findViewById(2131362024));
      this.evCheckIn = ((EditText)paramView.findViewById(2131362012));
      this.evCheckIn.requestFocus();
      this.evCheckIn.addTextChangedListener(this);
      this.lvReferList = ((ListView)paramView.findViewById(2131362017));
      this.waitLayout = ((LinearLayout)paramView.findViewById(2131362018));
      this.evCheckIn.requestFocus();
      this.evCheckIn.addTextChangedListener(this);
      changeContentForOrientation();
      return;
      str1 = this.poiName;
      break;
    }
  }

  private void initViewHandles(View paramView)
  {
    this.lvReferList = ((ListView)paramView.findViewById(2131362017));
    this.referAdapter = new ReferListAdapter(getActivity().getApplicationContext());
    this.lvReferList.setAdapter(this.referAdapter);
    this.lvReferList.setOnItemClickListener(this);
  }

  private void initialAtData()
  {
    if (FriendsModel.getInstance().getFriends().size() != 0)
    {
      getAllFriends();
      return;
    }
    this.getDataTask = new GetDataTask(null);
    GetDataTask localGetDataTask = this.getDataTask;
    Integer[] arrayOfInteger = new Integer[1];
    arrayOfInteger[0] = Integer.valueOf(1);
    localGetDataTask.execute(arrayOfInteger);
  }

  private void insertFriendIntoContent(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    if ((paramInt1 < 0) || (paramInt1 > paramInt2) || (TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2)));
    while (true)
    {
      return;
      String str = "@" + paramString1 + "(#" + paramString2 + "#)";
      Bitmap localBitmap = (Bitmap)this.mNameBmpMap.get(str);
      if (localBitmap == null)
      {
        Paint localPaint = new Paint(this.evCheckIn.getPaint());
        localPaint.setColor(-16777216);
        localBitmap = ImageCache.createStringBitmap("@" + paramString2, localPaint);
      }
      if (localBitmap != null)
        this.mNameBmpMap.put(str, localBitmap);
      Editable localEditable1 = this.evCheckIn.getText();
      localEditable1.replace(paramInt1, paramInt2, "");
      if ((paramInt1 > 0) && (!" ".equals(localEditable1.subSequence(paramInt1 - 1, paramInt1).toString())))
      {
        localEditable1.insert(paramInt1, " ");
        paramInt1++;
      }
      Editable localEditable2 = localEditable1.insert(paramInt1, str + " ");
      if (paramInt1 + str.length() > 140)
        break;
      if ((1 != this.mOrientation) && (!Setting.getInstance().isShowFaceIconInLandScape()))
        continue;
      localEditable2.setSpan(new ImageSpan(localBitmap), paramInt1, paramInt1 + str.length(), 33);
      return;
    }
    Toast.makeText(getActivity(), 2131427900, 0).show();
  }

  private void loadInsertedPicture()
  {
    this.mLoadPhotoTask = new LoadPhotoTask(null);
    this.mLoadPhotoTask.execute(new Void[0]);
    this.mProgressDialog = ProgressDialog.show(getActivity(), "", getResources().getText(2131427649), true, true, new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramDialogInterface)
      {
        CheckInFragment.this.mProgressDialog = null;
        CheckInFragment.this.mLoadPhotoTask.cancel(true);
      }
    });
  }

  private void readyAtListData(ArrayList<AtListItem> paramArrayList)
  {
    if (paramArrayList == null);
    while (true)
    {
      return;
      int i = paramArrayList.size();
      this.referList.clear();
      int j = Math.min(i, 10);
      for (int k = 0; k < j; k++)
        this.referList.add((AtListItem)paramArrayList.get(k));
    }
  }

  private void searchListData(ArrayList<FriendsModel.Friend> paramArrayList, String paramString)
  {
    if ((paramArrayList == null) || (TextUtils.isEmpty(paramString)));
    int i;
    while (true)
    {
      return;
      i = paramArrayList.size();
      if (!StringUtil.isContainChinese(paramString))
        break;
      for (int k = 0; k < i; k++)
      {
        FriendsModel.Friend localFriend2 = (FriendsModel.Friend)paramArrayList.get(k);
        if (!localFriend2.getFname().contains(paramString))
          continue;
        addFriendList(localFriend2);
      }
    }
    int j = 0;
    label71: FriendsModel.Friend localFriend1;
    if (j < i)
    {
      localFriend1 = (FriendsModel.Friend)paramArrayList.get(j);
      if (!localFriend1.getFname().contains(paramString))
        break label112;
      addFriendList(localFriend1);
    }
    while (true)
    {
      j++;
      break label71;
      break;
      label112: if (searchPy(localFriend1.getPy(), paramString))
      {
        addFriendList(localFriend1);
        continue;
      }
      if (!searchPy(localFriend1.getFpy(), paramString))
        continue;
      addFriendList(localFriend1);
    }
  }

  private boolean searchPy(String[] paramArrayOfString, String paramString)
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length <= 0));
    while (true)
    {
      return false;
      int i = paramArrayOfString.length;
      for (int j = 0; j < i; j++)
        if (paramArrayOfString[j].startsWith(paramString))
          return true;
    }
  }

  private void setButtonHandlers(View paramView)
  {
    ((ImageView)paramView.findViewById(2131362014)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        CheckInFragment.this.showFaceListDialog(true);
      }
    });
    ((ImageView)paramView.findViewById(2131362015)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent(CheckInFragment.this.getActivity(), FriendsFragment.class);
        Bundle localBundle = new Bundle();
        localBundle.putInt("MODE", 2);
        localIntent.putExtras(localBundle);
        AnimationUtil.startFragmentForResult(CheckInFragment.this, localIntent, 4, 1);
      }
    });
    this.btnPrivaceLock = ((Button)paramView.findViewById(2131362016));
    this.btnPrivaceLock.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        DialogUtil.showValueListDlg(CheckInFragment.this.getActivity(), 2131428153, CheckInFragment.this.locks, CheckInFragment.this.fancyDialogAdapter, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramDialogInterface, int paramInt)
          {
            CheckInFragment.this.fancyDialogAdapter.setSelectedIndex(paramInt);
            CheckInFragment.this.privacy = String.valueOf(paramInt);
            CheckInFragment.this.btnPrivaceLock.setText(CheckInFragment.this.locks[paramInt]);
          }
        }
        , false, false);
      }
    });
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

  private void showMainLayout(boolean paramBoolean)
  {
    View localView1 = getView().findViewById(2131362004);
    View localView2 = getView().findViewById(2131362013);
    if (paramBoolean)
    {
      localView1.setVisibility(0);
      localView2.setVisibility(0);
      return;
    }
    localView1.setVisibility(8);
    localView2.setVisibility(8);
  }

  private void submitRecord()
  {
    if (!super.checkNetworkAndHint(true))
      return;
    this.btnRight.setEnabled(false);
    this.waitLayout.setVisibility(0);
    showMainLayout(false);
    String str = String.valueOf(this.evCheckIn.getText()).trim();
    this.submitTask = new PostCheckInTask(null);
    PostCheckInTask localPostCheckInTask = this.submitTask;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = KXTextUtil.tranformAtFriend(str);
    localPostCheckInTask.execute(arrayOfString);
  }

  public void afterTextChanged(Editable paramEditable)
  {
  }

  public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  protected void dealPhotoResult(String paramString1, String paramString2)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("filePathName", paramString1);
    localBundle.putString("fileFrom", paramString2);
    IntentUtil.launchEditPhotoForResult(getActivity(), this, 105, localBundle);
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    if (!isAtEditView(this.evCheckIn, paramMotionEvent))
      hideInputMethod();
    return super.dispatchTouchEvent(paramMotionEvent);
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 2);
  }

  public ShowMenuDialog getShowMenuDialog()
  {
    if (this.menuUtil == null)
      this.menuUtil = new ShowMenuDialog(this);
    return this.menuUtil;
  }

  public void inputFinish()
  {
    Intent localIntent = new Intent();
    if (getArguments().getBoolean("poi_show_detail", true))
    {
      String[] arrayOfString = new String[4];
      arrayOfString[0] = this.poiId;
      arrayOfString[1] = this.poiName;
      arrayOfString[2] = this.poiLocation;
      arrayOfString[3] = "0";
      localIntent.putExtra("params", arrayOfString);
    }
    setResult(-1, localIntent);
    finish();
  }

  protected void insertFaceIcon(int paramInt)
  {
    String str;
    Editable localEditable;
    int i;
    int j;
    if (this.mFaceModel.getStateFaceStrings() != null)
    {
      str = (String)this.mFaceModel.getStateFaceStrings().get(paramInt);
      localEditable = this.evCheckIn.getText();
      i = Selection.getSelectionStart(localEditable);
      j = Selection.getSelectionEnd(localEditable);
      if ((i == -1) || (j == -1))
      {
        i = localEditable.length();
        j = localEditable.length();
      }
      if (localEditable.length() <= 140 - str.length())
        break label105;
      Toast.makeText(getActivity(), 2131427899, 0).show();
    }
    label105: 
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
      return;
      localEditable = this.evCheckIn.getText();
      i = Selection.getSelectionStart(localEditable);
      int j = Selection.getSelectionEnd(localEditable);
      if ((i == -1) || (j == -1))
      {
        i = localEditable.length();
        j = localEditable.length();
      }
      if (localEditable.length() > 140 - paramString.length())
      {
        Toast.makeText(getActivity(), 2131427899, 0).show();
        return;
      }
      localEditable.replace(i, j, paramString.subSequence(0, paramString.length()));
    }
    while ((1 != this.mOrientation) && (!Setting.getInstance().isShowFaceIconInLandScape()));
    localEditable.setSpan(new ImageSpan(paramBitmap), i, i + paramString.length(), 33);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 == -1)
    {
      if (paramInt1 != 4)
        break label57;
      String str1 = paramIntent.getStringExtra("fuid");
      String str2 = paramIntent.getStringExtra("fname");
      int i = this.evCheckIn.getSelectionStart();
      insertFriendIntoContent(i, i, str1, str2);
    }
    label57: 
    do
      return;
    while (paramInt1 != 105);
    try
    {
      loadInsertedPicture();
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("CheckInActivity", "onActivityResult", localException);
    }
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    if (paramInt == -1)
    {
      if ((this.submitTask != null) && (!this.submitTask.isCancelled()))
        this.submitTask.cancel(true);
      backOrExit();
    }
  }

  public void onClick(View paramView)
  {
    paramView.requestFocus();
    if (paramView.equals(this.btnLeft))
      goBack();
    do
    {
      return;
      if (!paramView.equals(this.btnRight))
        continue;
      submitRecord();
      return;
    }
    while (paramView.getId() != 2131362011);
    getActivity().getApplicationContext().getSharedPreferences("from_webpage", 0).edit().putBoolean("fromwebpage", false).commit();
    if (TextUtils.isEmpty(this.mUploadFilePathName))
    {
      selectPictureFromGallery();
      return;
    }
    this.picPath = null;
    this.ivCheckInPhoto.setImageResource(2130838481);
    this.mUploadFilePathName = null;
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    if (this.mOrientation != paramConfiguration.orientation)
    {
      this.mOrientation = paramConfiguration.orientation;
      changeContentForOrientation();
    }
    super.onConfigurationChanged(paramConfiguration);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903068, paramViewGroup, false);
  }

  public void onDestroy()
  {
    cancelTask(this.mLoadPhotoTask);
    cancelTask(this.getDataTask);
    Iterator localIterator;
    if (this.mNameBmpMap != null)
      localIterator = this.mNameBmpMap.values().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        this.mNameBmpMap.clear();
        this.mNameBmpMap = null;
        super.onDestroy();
        return;
      }
      ((Bitmap)localIterator.next()).recycle();
    }
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    FriendsModel.Friend localFriend = (FriendsModel.Friend)this.friendslist.get(paramInt);
    int i = this.index;
    this.index = -1;
    insertFriendIntoContent(i, this.evCheckIn.getSelectionStart(), localFriend.getFuid(), localFriend.getFname());
    this.lvReferList.setVisibility(8);
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      goBack();
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  public void onPause()
  {
    this.index = -1;
    super.onPause();
  }

  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    if (isNeedReturn())
      return;
    String str1 = paramCharSequence.toString();
    String str2 = str1.trim();
    label49: int i;
    if (140 < handlerStrSpace(0, str2, str2.length()))
    {
      this.btnRight.setEnabled(false);
      if (str1.length() <= 0)
        break label285;
      if (((paramInt3 <= 0) || (!"@".equals(str1.substring(paramInt1, paramInt1 + paramInt3)))) && ((paramInt3 != 0) || (paramInt1 <= 0) || (!"@".equals(str1.substring(paramInt1 - 1, paramInt1)))))
        break label249;
      if (paramInt3 != 0)
        break label243;
      i = paramInt1 - 1;
      label116: this.index = i;
      getAllFriends();
      this.referAdapter.notifyDataSetChanged();
      this.lvReferList.setVisibility(0);
    }
    while (true)
    {
      if ((this.index >= 0) && (1 + this.index < paramInt1 + paramInt3))
      {
        String str3 = str1.substring(1 + this.index, paramInt1 + paramInt3);
        this.friendslist.clear();
        searchListData(FriendsModel.getInstance().getFriends(), str3);
        this.referAdapter.notifyDataSetChanged();
        this.lvReferList.setVisibility(0);
      }
      if (this.friendslist.size() != 0)
        break;
      this.lvReferList.setVisibility(8);
      return;
      this.btnRight.setEnabled(true);
      break label49;
      label243: i = paramInt1;
      break label116;
      label249: if ((this.index >= 0) && (this.index < str1.length()))
        continue;
      this.index = -1;
      this.lvReferList.setVisibility(8);
      continue;
      label285: this.lvReferList.setVisibility(8);
    }
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle == null)
      throw new IllegalArgumentException("parameters can not be null");
    this.poiId = localBundle.getString("poiid");
    this.poiName = localBundle.getString("poiname");
    this.poiLocation = localBundle.getString("poi_location");
    this.lon = localBundle.getString("lon");
    this.lat = localBundle.getString("lat");
    this.picPath = localBundle.getString("pic_path");
    if (!TextUtils.isEmpty(this.picPath))
      loadInsertedPicture();
    String str = localBundle.getString("default_word");
    this.mFaceModel = FaceModel.getInstance();
    this.mFilePathName = EditPictureModel.getBitmapPath();
    this.mUploadPhotoFrom = EditPictureModel.getPicFrom();
    setTitleBar(paramView);
    initMainView(paramView);
    initViewHandles(paramView);
    setButtonHandlers(paramView);
    initialAtData();
    initFaceKeyBoardLayout(paramView);
    this.mOrientation = ScreenUtil.getOrientation(getActivity());
    this.locks = getResources().getStringArray(2131492898);
    if (str != null)
    {
      this.evCheckIn.setText(str);
      this.evCheckIn.setSelection(str.length());
      changeContentForOrientation();
    }
  }

  public void requestFinish()
  {
    goBack();
  }

  protected void setTitleBar(View paramView)
  {
    this.btnLeft = ((ImageView)paramView.findViewById(2131362914));
    this.btnLeft.setOnClickListener(this);
    this.tvTitle = ((TextView)paramView.findViewById(2131362920));
    this.tvTitle.setText(2131428150);
    this.tvTitle.setVisibility(0);
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    this.btnRight = ((ImageView)paramView.findViewById(2131362928));
    this.btnRight.setImageResource(2130839272);
    this.btnRight.setOnClickListener(this);
  }

  public void showFaceListDialog(boolean paramBoolean)
  {
    ImageView localImageView = (ImageView)getView().findViewById(2131362014);
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

  private class GetDataTask extends KXFragment.KXAsyncTask<Integer, Void, Integer>
  {
    private GetDataTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Integer[] paramArrayOfInteger)
    {
      if ((paramArrayOfInteger == null) || (paramArrayOfInteger.length == 0))
        return Integer.valueOf(0);
      try
      {
        if (FriendsEngine.getInstance().getFriendsList(CheckInFragment.this.getActivity().getApplicationContext(), paramArrayOfInteger[0].intValue()))
          return Integer.valueOf(1);
        Integer localInteger = Integer.valueOf(0);
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if ((paramInteger == null) || (paramInteger.intValue() == 0));
      do
        return;
      while (paramInteger.intValue() != 1);
      CheckInFragment.this.getAllFriends();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class LoadPhotoTask extends KXFragment.KXAsyncTask<Void, Void, Boolean>
  {
    private Bitmap mLoadedBitmap = null;

    private LoadPhotoTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(Void[] paramArrayOfVoid)
    {
      CheckInFragment.this.mFilePathName = EditPictureModel.getBitmapPath();
      if (CheckInFragment.this.mFilePathName.length() < 1)
        return Boolean.valueOf(false);
      try
      {
        this.mLoadedBitmap = EditPictureModel.getBimap();
        KXExifInterface localKXExifInterface = ImageCache.getExifInfo(CheckInFragment.this.mFilePathName);
        CheckInFragment.this.mUploadFilePathName = ImageCache.saveBitmapToFile(CheckInFragment.this.getActivity(), this.mLoadedBitmap, localKXExifInterface, "kx_tmp_upload.jpg");
        if (TextUtils.isEmpty(CheckInFragment.this.mUploadFilePathName))
          return Boolean.valueOf(false);
      }
      catch (Exception localException)
      {
        return Boolean.valueOf(false);
      }
      return Boolean.valueOf(true);
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      try
      {
        CheckInFragment.this.ivCheckInPhoto.setImageBitmap(this.mLoadedBitmap);
        this.mLoadedBitmap = null;
        if (CheckInFragment.this.mProgressDialog != null)
        {
          CheckInFragment.this.mProgressDialog.dismiss();
          CheckInFragment.this.mProgressDialog = null;
        }
        return;
      }
      catch (Exception localException)
      {
        KXLog.e("LoadPhotoTask", "onPostExecute", localException);
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class PostCheckInTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private PostCheckInTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      return Integer.valueOf(CheckInEngine.getInstance().postCheckInRequest(CheckInFragment.this.getActivity(), CheckInFragment.this.privacy, paramArrayOfString[0], CheckInFragment.this.poiId, CheckInFragment.this.lat, CheckInFragment.this.lon, CheckInFragment.this.mUploadFilePathName));
    }

    protected void onCancelledA()
    {
      KXLog.d("HttpUtil.cancel", "PostCheckInTask onCancelled");
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      CheckInFragment.this.waitLayout.setVisibility(8);
      CheckInFragment.this.showMainLayout(true);
      CheckInFragment.this.btnRight.setEnabled(true);
      if (paramInteger.intValue() == 1)
      {
        Toast.makeText(CheckInFragment.this.getActivity(), 2131428185, 0).show();
        CheckInFragment.this.inputFinish();
        NewsModel.getMyHomeModel().setFirstRefresh(true);
        return;
      }
      if (paramInteger.intValue() == 14)
      {
        Toast.makeText(CheckInFragment.this.getActivity(), 2131428155, 0).show();
        return;
      }
      if (paramInteger.intValue() == 15)
      {
        Toast.makeText(CheckInFragment.this.getActivity(), 2131428156, 0).show();
        return;
      }
      Toast.makeText(CheckInFragment.this.getActivity(), 2131428154, 0).show();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class ReferListAdapter extends BaseAdapter
  {
    private LayoutInflater inflater;

    ReferListAdapter(Context arg2)
    {
      Context localContext;
      this.inflater = LayoutInflater.from(localContext);
    }

    public int getCount()
    {
      return CheckInFragment.this.friendslist.size();
    }

    public Object getItem(int paramInt)
    {
      return CheckInFragment.this.friendslist.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null)
      {
        paramView = this.inflater.inflate(2130903202, null);
        ViewHolder localViewHolder2 = new ViewHolder(null);
        localViewHolder2.realNameText = ((TextView)paramView.findViewById(2131362966));
        paramView.setTag(localViewHolder2);
      }
      ViewHolder localViewHolder1 = (ViewHolder)paramView.getTag();
      FriendsModel.Friend localFriend = (FriendsModel.Friend)CheckInFragment.this.friendslist.get(paramInt);
      localViewHolder1.realNameText.setText(localFriend.getFname());
      return paramView;
    }

    private class ViewHolder
    {
      public TextView realNameText;

      private ViewHolder()
      {
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.CheckInFragment
 * JD-Core Version:    0.6.0
 */