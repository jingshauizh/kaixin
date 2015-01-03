package com.kaixin001.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.Selection;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
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
import com.kaixin001.engine.FriendsEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.item.RecordInfo;
import com.kaixin001.item.RecordUploadTask;
import com.kaixin001.model.FaceModel;
import com.kaixin001.model.FriendsModel;
import com.kaixin001.model.FriendsModel.Friend;
import com.kaixin001.model.Setting;
import com.kaixin001.util.ActivityUtil;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.StringUtil;
import com.kaixin001.view.FaceKeyboardView;
import com.kaixin001.view.FaceKeyboardView.OnFaceSelectedListener;
import com.kaixin001.view.KXIntroView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ForwardWeiboFragment extends BaseFragment
  implements View.OnClickListener, TextWatcher, AdapterView.OnItemClickListener, DialogInterface.OnClickListener
{
  public static final int FORWARD_WEIBO_CODE = 1201;
  private static final int MAX_CHAR_OF_RECORD = 140;
  private static final String TAG_AT = "@";
  private static final String TAG_NAME_LEFT = "(#";
  private static final String TAG_NAME_RIGHT = "#)";
  private ImageView btnLeft;
  private ImageView btnRight;
  private EditText editWeibo;
  private ArrayList<FriendsModel.Friend> friendslist = new ArrayList();
  GetDataTask getDataTask;
  private int index = -1;
  private ListView lvReferList;
  private ListView lvRemoveList;
  private FaceKeyboardView mFaceKeyboardView;
  private HashMap<String, Bitmap> mNameBmpMap = new HashMap();
  private int mOrientation = 1;
  private RecordInfo recordInfo;
  private ReferListAdapter referAdapter;
  private RemovePersonListAdapter removeAdapter;
  private ArrayList<String> removelist = new ArrayList();
  private TextView tvTitle;
  private View view;
  private View view2;
  private View view3;
  private LinearLayout waitLayout;

  private void actionSmile()
  {
    ImageView localImageView = (ImageView)getView().findViewById(2131362354);
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

  private void back()
  {
    if (TextUtils.isEmpty(String.valueOf(this.editWeibo.getText()).trim()))
    {
      inputCancel();
      return;
    }
    DialogUtil.showKXAlertDialog(getActivity(), 2131427451, 2131427382, 2131427383, this, this);
  }

  public static String converToServerType(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      paramString = "";
    int i;
    do
    {
      return paramString;
      i = paramString.indexOf("[|s|]", 0);
    }
    while (-1 == i);
    StringBuffer localStringBuffer = new StringBuffer();
    int j = 0;
    if (-1 == i);
    String str1;
    String str2;
    int i2;
    int i3;
    while (true)
    {
      return localStringBuffer.toString();
      if (i > j)
        localStringBuffer.append(paramString.substring(j, i));
      int k = i + "[|s|]".length();
      int m = paramString.indexOf("[|m|]", k);
      if (-1 == m)
      {
        localStringBuffer.append(paramString.substring(k));
        continue;
      }
      str1 = paramString.substring(k, m);
      int n = m + "[|m|]".length();
      int i1 = paramString.indexOf("[|m|]", n);
      if (-1 == i1)
      {
        localStringBuffer.append(paramString.substring(n));
        continue;
      }
      str2 = paramString.substring(n, i1);
      i2 = i1 + "[|m|]".length();
      i3 = paramString.indexOf("[|e|]", i2);
      if (-1 != i3)
        break;
      localStringBuffer.append(paramString.substring(i2));
    }
    String str3 = paramString.substring(i2, i3);
    if (("0".equals(str3)) || ("-1".equals(str3)))
      localStringBuffer.append("@").append(str2);
    while (true)
    {
      j = i3 + "[|e|]".length();
      i = paramString.indexOf("[|s|]", j);
      if (-1 != i)
        break;
      localStringBuffer.append(paramString.substring(j));
      break;
      if ("-101".equals(str3))
      {
        if ("@".equals(str1))
          continue;
        localStringBuffer.append(str1);
        continue;
      }
      localStringBuffer.append(str1);
    }
  }

  private void getAllFriends()
  {
    try
    {
      this.friendslist.clear();
      ArrayList localArrayList = FriendsModel.getInstance().getFriends();
      if (localArrayList != null)
      {
        Iterator localIterator = localArrayList.iterator();
        while (true)
        {
          if (!localIterator.hasNext())
            return;
          FriendsModel.Friend localFriend = (FriendsModel.Friend)localIterator.next();
          this.friendslist.add(localFriend);
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void hideInputMethod()
  {
    View localView = getActivity().getCurrentFocus();
    if (localView != null)
      ((InputMethodManager)getActivity().getSystemService("input_method")).hideSoftInputFromWindow(localView.getWindowToken(), 2);
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

  private void inputCancel()
  {
    hideInputMethod();
    finish();
  }

  private void insertFriendIntoContent(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    if ((paramInt1 < 0) || (paramInt1 > paramInt2) || (TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2)));
    StringBuffer localStringBuffer;
    Bitmap localBitmap;
    Editable localEditable;
    do
    {
      return;
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("@").append(paramString1).append("(#").append(paramString2).append("#)");
      localBitmap = (Bitmap)this.mNameBmpMap.get(localStringBuffer.toString());
      if (localBitmap == null)
        localBitmap = ImageCache.createStringBitmap("@" + paramString2, this.editWeibo.getPaint());
      if (localBitmap != null)
        this.mNameBmpMap.put(localStringBuffer.toString(), localBitmap);
      localEditable = this.editWeibo.getText().replace(paramInt1, paramInt2, localStringBuffer.toString() + " ");
    }
    while (((1 != this.mOrientation) && (!Setting.getInstance().isShowFaceIconInLandScape())) || (paramInt1 + localStringBuffer.length() > 140));
    localEditable.setSpan(new ImageSpan(localBitmap), paramInt1, paramInt1 + localStringBuffer.length(), 33);
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
    paramView.findViewById(2131362354).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        ForwardWeiboFragment.this.actionSmile();
      }
    });
    paramView.findViewById(2131362355).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        ActivityUtil.hideInputMethod(ForwardWeiboFragment.this.getActivity());
        Intent localIntent = new Intent(ForwardWeiboFragment.this.getActivity(), FriendsFragment.class);
        Bundle localBundle = new Bundle();
        localBundle.putInt("MODE", 2);
        localIntent.putExtras(localBundle);
        AnimationUtil.startFragmentForResult(ForwardWeiboFragment.this, localIntent, 4, 1);
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
    this.view = getView().findViewById(2131362348);
    this.view2 = getView().findViewById(2131362351);
    this.view2.setVisibility(8);
    this.view3 = getView().findViewById(2131362353);
    this.view3.setVisibility(8);
    if (paramBoolean)
    {
      this.view.setVisibility(0);
      this.view2.setVisibility(0);
      this.view3.setVisibility(0);
      return;
    }
    this.view.setVisibility(8);
    this.view2.setVisibility(8);
    this.view3.setVisibility(8);
  }

  private void submitWeibo()
  {
    String str1 = String.valueOf(this.editWeibo.getText()).trim();
    if (TextUtils.isEmpty(str1))
      str1 = getString(2131427606);
    if (!super.checkNetworkAndHint(true))
      return;
    this.btnRight.setEnabled(false);
    showMainLayout(false);
    this.waitLayout.setVisibility(0);
    StringBuffer localStringBuffer = new StringBuffer("");
    Iterator localIterator = this.removelist.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        RecordUploadTask localRecordUploadTask = new RecordUploadTask(getActivity().getApplicationContext());
        localRecordUploadTask.initRecordUploadTask(null, str1, null, null, null, null, localStringBuffer.toString(), this.recordInfo.getRecordFeedRid(), 0);
        UploadTaskListEngine.getInstance().addUploadTask(localRecordUploadTask);
        Toast.makeText(getActivity(), 2131428310, 0).show();
        inputFinish();
        return;
      }
      String str2 = converToServerType((String)localIterator.next());
      localStringBuffer.append("‖");
      localStringBuffer.append(str2);
    }
  }

  public void afterTextChanged(Editable paramEditable)
  {
  }

  public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public void finish()
  {
    hideKeyboard();
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 4);
  }

  public void initRemoveListData(View paramView)
  {
    if ((this.recordInfo != null) && (this.recordInfo.isRepost()))
    {
      String str = this.recordInfo.getRecordFeedTitle();
      String[] arrayOfString;
      if (str.indexOf("[|s|]‖[|m|]10066329[|m|]-101[|e|]") != -1)
      {
        arrayOfString = str.split("\\[\\|s\\|\\]‖\\[\\|m\\|\\]10066329\\[\\|m\\|\\]-101\\[\\|e\\|\\]|‖");
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append("[|s|]").append(this.recordInfo.getRecordFeedFname()).append("[|m|]").append(this.recordInfo.getRecordFeedFuid()).append("[|m|]");
        if ((arrayOfString[0] != null) && (!arrayOfString[0].startsWith(localStringBuffer.toString())))
        {
          localStringBuffer.append("0[|e|]：").append(arrayOfString[0]);
          arrayOfString[0] = localStringBuffer.toString();
        }
      }
      for (int i = 0; ; i++)
      {
        if (i >= arrayOfString.length)
        {
          this.removelist.clear();
          if (!TextUtils.isEmpty(str))
            str = "‖" + str;
          this.removelist.add(str);
          this.lvRemoveList = ((ListView)paramView.findViewById(2131362352));
          this.lvRemoveList.setCacheColorHint(0);
          this.removeAdapter = new RemovePersonListAdapter(getActivity().getApplicationContext());
          this.lvRemoveList.setAdapter(this.removeAdapter);
          this.lvRemoveList.requestFocus();
          return;
          arrayOfString = str.split("‖");
          break;
        }
        this.removelist.add(arrayOfString[i]);
      }
    }
    this.view2 = paramView.findViewById(2131362351);
    this.view2.setVisibility(8);
  }

  public void inputFinish()
  {
    hideInputMethod();
    Intent localIntent = new Intent();
    if (this.recordInfo != null)
    {
      this.recordInfo.setRecordFeedTnum(String.valueOf(1 + Integer.valueOf(this.recordInfo.getRecordFeedTnum()).intValue()));
      localIntent.putExtra("rid", this.recordInfo.getRecordFeedRid());
      localIntent.putExtra("tnum", String.valueOf(this.recordInfo.getRecordFeedTnum()));
      localIntent.putExtra("cnum", String.valueOf(this.recordInfo.getRecordFeedCnum()));
      String str = this.editWeibo.getText().toString();
      if (TextUtils.isEmpty(str))
        str = "转发记录";
      localIntent.putExtra("content", str);
    }
    setResult(-1, localIntent);
    finishFragment(1201);
    finish();
  }

  protected void insertFaceIcon(int paramInt)
  {
    ArrayList localArrayList1 = FaceModel.getInstance().getStateFaceStrings();
    ArrayList localArrayList2 = FaceModel.getInstance().getStateFaceIcons();
    if (localArrayList1 != null)
    {
      String str = (String)localArrayList1.get(paramInt);
      Editable localEditable = this.editWeibo.getText();
      int i = Selection.getSelectionStart(localEditable);
      int j = Selection.getSelectionEnd(localEditable);
      if ((i == -1) || (j == -1))
      {
        i = localEditable.length();
        j = localEditable.length();
      }
      localEditable.replace(i, j, str);
      if (((1 == this.mOrientation) || (Setting.getInstance().isShowFaceIconInLandScape())) && (i + str.length() <= 140))
        localEditable.setSpan(new ImageSpan((Bitmap)localArrayList2.get(paramInt)), i, i + str.length(), 33);
    }
  }

  protected void insertFaceIcon(String paramString, Bitmap paramBitmap)
  {
    if ((TextUtils.isEmpty(paramString)) || (paramBitmap == null));
    Editable localEditable;
    int i;
    do
    {
      return;
      localEditable = this.editWeibo.getText();
      if (!this.editWeibo.isFocused())
        this.editWeibo.requestFocus();
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
    if ((paramInt2 == -1) && (paramInt1 == 4))
    {
      String str1 = paramIntent.getStringExtra("fuid");
      String str2 = paramIntent.getStringExtra("fname");
      int i = this.editWeibo.getSelectionStart();
      insertFriendIntoContent(i, i, str1, str2);
    }
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    if (paramInt == -1)
      inputCancel();
  }

  public void onClick(View paramView)
  {
    if (this.btnLeft.equals(paramView))
      back();
    do
      return;
    while (!this.btnRight.equals(paramView));
    submitWeibo();
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
      i = this.editWeibo.getSelectionStart();
      j = this.editWeibo.getSelectionEnd();
      str1 = this.editWeibo.getText().toString();
      if (((1 != this.mOrientation) && (!Setting.getInstance().isShowFaceIconInLandScape())) || (TextUtils.isEmpty(str1)))
        break label243;
      localSpannableString = StringUtil.convertTextToMessageFace(str1);
      if (localSpannableString != null)
      {
        localIterator = this.mNameBmpMap.keySet().iterator();
        if (localIterator.hasNext())
          break label134;
      }
      this.editWeibo.setText(localSpannableString);
    }
    while (true)
    {
      this.editWeibo.setSelection(i, j);
      super.onConfigurationChanged(paramConfiguration);
      return;
      label134: String str2 = (String)localIterator.next();
      Bitmap localBitmap = (Bitmap)this.mNameBmpMap.get(str2);
      int k = str1.indexOf(str2);
      if (k < 0)
      {
        localBitmap.recycle();
        this.mNameBmpMap.remove(str2);
        break;
      }
      do
      {
        localSpannableString.setSpan(new ImageSpan(localBitmap), k, k + str2.length(), 33);
        k = str1.indexOf(str2, k + str2.length());
      }
      while (k >= 0);
      break;
      label243: this.editWeibo.setText(str1);
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903125, paramViewGroup, false);
  }

  public void onDestroy()
  {
    Iterator localIterator;
    if (this.mNameBmpMap != null)
      localIterator = this.mNameBmpMap.values().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        this.mNameBmpMap.clear();
        this.mNameBmpMap = null;
        cancelTask(this.getDataTask);
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
    insertFriendIntoContent(i, this.editWeibo.getSelectionStart(), localFriend.getFuid(), localFriend.getFname());
    this.lvReferList.setVisibility(8);
  }

  public void onPause()
  {
    this.index = -1;
    super.onPause();
  }

  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    String str1 = paramCharSequence.toString();
    this.btnRight.setEnabled(true);
    int i;
    if (str1.length() > 0)
      if (((paramInt3 > 0) && ("@".equals(str1.substring(paramInt1, paramInt1 + paramInt3)))) || ((paramInt3 == 0) && (paramInt1 > 0) && ("@".equals(str1.substring(paramInt1 - 1, paramInt1)))))
        if (paramInt3 == 0)
        {
          i = paramInt1 - 1;
          this.index = i;
          getAllFriends();
          this.referAdapter.notifyDataSetChanged();
          this.lvReferList.setVisibility(0);
        }
    while (true)
    {
      if ((this.index >= 0) && (1 + this.index < paramInt1 + paramInt3))
      {
        String str2 = str1.substring(1 + this.index, paramInt1 + paramInt3);
        this.friendslist.clear();
        searchListData(FriendsModel.getInstance().getFriends(), str2);
        this.referAdapter.notifyDataSetChanged();
        this.lvReferList.setVisibility(0);
      }
      return;
      i = paramInt1;
      break;
      if ((this.index >= 0) && (this.index < str1.length()))
        continue;
      this.index = -1;
      this.lvReferList.setVisibility(8);
      continue;
      this.lvReferList.setVisibility(8);
    }
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle != null)
      this.recordInfo = ((RecordInfo)localBundle.getSerializable("recordInfo"));
    setTitleBar(paramView);
    this.editWeibo = ((EditText)paramView.findViewById(2131362349));
    EditText localEditText = this.editWeibo;
    InputFilter[] arrayOfInputFilter = new InputFilter[1];
    arrayOfInputFilter[0] = new InputFilter.LengthFilter(140);
    localEditText.setFilters(arrayOfInputFilter);
    this.editWeibo.addTextChangedListener(this);
    this.editWeibo.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        ForwardWeiboFragment.this.showFaceKeyboardView(false);
      }
    });
    this.lvReferList = ((ListView)paramView.findViewById(2131362350));
    this.referAdapter = new ReferListAdapter(getActivity().getApplicationContext());
    this.lvReferList.setAdapter(this.referAdapter);
    this.lvReferList.setOnItemClickListener(this);
    this.waitLayout = ((LinearLayout)paramView.findViewById(2131362356));
    setButtonHandlers(paramView);
    initRemoveListData(paramView);
    initialAtData();
    this.editWeibo.requestFocus();
    showKeyboard();
    this.mFaceKeyboardView = ((FaceKeyboardView)paramView.findViewById(2131361976));
    this.mFaceKeyboardView.setOnFaceSelectedListener(new FaceKeyboardView.OnFaceSelectedListener()
    {
      public void FaceSelected(int paramInt, String paramString, Bitmap paramBitmap)
      {
        switch (paramInt)
        {
        default:
          ForwardWeiboFragment.this.insertFaceIcon(paramString, paramBitmap);
        case -2:
        case -1:
        }
        int i;
        do
        {
          return;
          i = Selection.getSelectionStart(ForwardWeiboFragment.this.editWeibo.getText());
        }
        while (i <= 0);
        String str1 = ForwardWeiboFragment.this.editWeibo.getText().toString();
        ArrayList localArrayList = FaceModel.getInstance().getAllFaceStringSortByLength();
        int j = 1;
        Iterator localIterator = localArrayList.iterator();
        if (!localIterator.hasNext());
        while (true)
        {
          ForwardWeiboFragment.this.editWeibo.getText().delete(i - j, i);
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

  public void requestFinish()
  {
    back();
  }

  protected void setTitleBar(View paramView)
  {
    this.btnLeft = ((ImageView)paramView.findViewById(2131362914));
    this.btnLeft.setOnClickListener(this);
    this.btnLeft.setOnClickListener(this);
    this.tvTitle = ((TextView)paramView.findViewById(2131362920));
    this.tvTitle.setText(2131427371);
    this.tvTitle.setVisibility(0);
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    this.btnRight = ((ImageView)paramView.findViewById(2131362928));
    this.btnRight.setOnClickListener(this);
    this.btnRight.setImageResource(2130838282);
    this.btnRight.setOnClickListener(this);
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
        if (FriendsEngine.getInstance().getFriendsList(ForwardWeiboFragment.this.getActivity().getApplicationContext(), paramArrayOfInteger[0].intValue()))
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
      return ForwardWeiboFragment.this.friendslist.size();
    }

    public Object getItem(int paramInt)
    {
      return ForwardWeiboFragment.this.friendslist.get(paramInt);
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
      FriendsModel.Friend localFriend = (FriendsModel.Friend)ForwardWeiboFragment.this.friendslist.get(paramInt);
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

  private class RemovePersonListAdapter extends BaseAdapter
  {
    private LayoutInflater inflater;

    RemovePersonListAdapter(Context arg2)
    {
      Context localContext;
      this.inflater = LayoutInflater.from(localContext);
    }

    public int getCount()
    {
      return ForwardWeiboFragment.this.removelist.size();
    }

    public Object getItem(int paramInt)
    {
      return ForwardWeiboFragment.this.removelist.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null)
      {
        paramView = this.inflater.inflate(2130903201, null);
        ViewHolder localViewHolder2 = new ViewHolder(null);
        localViewHolder2.realNameText = ((KXIntroView)paramView.findViewById(2131362966));
        localViewHolder2.btnRemove = ((Button)paramView.findViewById(2131362967));
        paramView.setTag(localViewHolder2);
      }
      String str = (String)ForwardWeiboFragment.this.removelist.get(paramInt);
      ViewHolder localViewHolder1 = (ViewHolder)paramView.getTag();
      ArrayList localArrayList = RecordInfo.parseRecordTitleUtil(str);
      Iterator localIterator = localArrayList.iterator();
      while (true)
      {
        if (!localIterator.hasNext())
        {
          localViewHolder1.realNameText.setTitleList(localArrayList);
          localViewHolder1.btnRemove.setTag(Integer.valueOf(paramInt));
          localViewHolder1.btnRemove.setOnClickListener(new View.OnClickListener()
          {
            public void onClick(View paramView)
            {
              int i = ((Integer)paramView.getTag()).intValue();
              ForwardWeiboFragment.this.removelist.remove(i);
              ForwardWeiboFragment.this.removeAdapter.notifyDataSetChanged();
              if (ForwardWeiboFragment.this.removelist.isEmpty())
              {
                if (ForwardWeiboFragment.this.view2 != null)
                  ForwardWeiboFragment.this.view2.setVisibility(8);
              }
              else
                return;
              ForwardWeiboFragment.this.view2 = ForwardWeiboFragment.this.getView().findViewById(2131362351);
              ForwardWeiboFragment.this.view2.setVisibility(8);
            }
          });
          return paramView;
        }
        KXLinkInfo localKXLinkInfo = (KXLinkInfo)localIterator.next();
        if (!localKXLinkInfo.isUserName())
          continue;
        localKXLinkInfo.setType("-100");
      }
    }

    private class ViewHolder
    {
      public Button btnRemove;
      public KXIntroView realNameText;

      private ViewHolder()
      {
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.ForwardWeiboFragment
 * JD-Core Version:    0.6.0
 */