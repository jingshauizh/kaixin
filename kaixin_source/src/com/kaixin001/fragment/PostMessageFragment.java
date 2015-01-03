package com.kaixin001.fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.MessageEngine;
import com.kaixin001.item.FriendInfo;
import com.kaixin001.model.FaceModel;
import com.kaixin001.model.Setting;
import com.kaixin001.util.ActivityUtil;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.StringUtil;
import com.kaixin001.view.FaceKeyboardView;
import com.kaixin001.view.FaceKeyboardView.OnFaceSelectedListener;
import java.util.ArrayList;
import java.util.Iterator;

public class PostMessageFragment extends BaseFragment
{
  public static final String EXTRA_MESSAGE_CONTENT = "MessageContent";
  public static final String EXTRA_PREVIOUS_ACTIVITY_NAME = "PreviousActivityName";
  private ArrayList<FriendInfo> checkedFriendsList = new ArrayList();
  private boolean mBack = false;
  private EditText mEditContent = null;
  private FaceKeyboardView mFaceKeyboardView;
  private FaceModel mFaceModel;
  boolean mIsSendingMessage = false;
  private int mOrientation = 1;
  PostMessageTask mPostMessageTask = null;
  String mPreviousActivityName = "";
  private final ArrayList<FriendInfo> mReceiverList = new ArrayList();
  private int mReceiverNumLimit = 30;

  private void GotoMessageDetail(String paramString)
  {
    Intent localIntent = new Intent(getActivity(), MessageDetailFragment.class);
    localIntent.putExtra("id", paramString);
    localIntent.putExtra("type", 202);
    localIntent.putExtra("mode", 1);
    startFragmentForResult(localIntent, 502, 1, true);
  }

  private void doSendMessage()
  {
    if (!super.checkNetworkAndHint(true))
      return;
    String str = this.mEditContent.getText().toString();
    if (this.mReceiverList.size() == 0)
    {
      DialogUtil.showKXAlertDialog(getActivity(), 2131427594, null);
      return;
    }
    if (str.length() == 0)
    {
      DialogUtil.showKXAlertDialog(getActivity(), 2131427595, null);
      return;
    }
    setSendingStatus(true);
    View localView = getView().findViewById(2131363475);
    getView().findViewById(2131363473).setVisibility(8);
    this.mEditContent.setVisibility(8);
    localView.setVisibility(0);
    this.mPostMessageTask = null;
    this.mPostMessageTask = new PostMessageTask(null);
    String[] arrayOfString = { str };
    this.mPostMessageTask.execute(arrayOfString);
  }

  private void initFaceKeyBoardLayout()
  {
    this.mFaceKeyboardView = ((FaceKeyboardView)getView().findViewById(2131361976));
    this.mFaceKeyboardView.setOnFaceSelectedListener(new FaceKeyboardView.OnFaceSelectedListener()
    {
      public void FaceSelected(int paramInt, String paramString, Bitmap paramBitmap)
      {
        switch (paramInt)
        {
        default:
          PostMessageFragment.this.insertFaceIcon(paramString, paramBitmap);
        case -2:
        case -1:
        }
        int i;
        do
        {
          return;
          i = PostMessageFragment.this.mEditContent.getSelectionStart();
        }
        while (i <= 0);
        String str1 = PostMessageFragment.this.mEditContent.getText().toString();
        ArrayList localArrayList = FaceModel.getInstance().getAllFaceStringSortByLength();
        int j = 1;
        Iterator localIterator = localArrayList.iterator();
        if (!localIterator.hasNext());
        while (true)
        {
          PostMessageFragment.this.mEditContent.getText().delete(i - j, i);
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

  private void setSendingStatus(boolean paramBoolean)
  {
    ((ImageView)getView().findViewById(2131362928));
    View localView = getView().findViewById(2131363476);
    this.mIsSendingMessage = paramBoolean;
    if (this.mIsSendingMessage)
    {
      localView.setVisibility(8);
      return;
    }
    localView.setVisibility(0);
  }

  private void showConfirmExitDialog()
  {
    DialogUtil.showKXAlertDialog(getActivity(), 2131427451, 2131427382, 2131427383, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        PostMessageFragment.this.finish();
      }
    }
    , null);
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

  public void constructViews()
  {
    LinearLayout localLinearLayout1 = (LinearLayout)getView().findViewById(2131363471);
    localLinearLayout1.removeAllViews();
    int i = 0;
    if (i >= this.mReceiverList.size())
      return;
    LinearLayout localLinearLayout2 = new LinearLayout(getActivity());
    TableLayout.LayoutParams localLayoutParams = new TableLayout.LayoutParams();
    localLayoutParams.width = -1;
    localLayoutParams.height = -2;
    localLayoutParams.bottomMargin = 6;
    localLinearLayout2.setLayoutParams(localLayoutParams);
    for (int j = 0; ; j++)
    {
      if (j >= 2)
      {
        localLinearLayout1.addView(localLinearLayout2);
        i += 2;
        break;
      }
      int k = i + j;
      if (k >= this.mReceiverList.size())
        continue;
      View localView = getActivity().getLayoutInflater().inflate(2130903301, null, false);
      TextView localTextView = (TextView)localView.findViewById(2131363478);
      localTextView.setText(((FriendInfo)this.mReceiverList.get(k)).getName());
      localTextView.setClickable(true);
      localTextView.setOnClickListener(new ReceiverButtonOnClickListener(k));
      localLinearLayout2.addView(localView);
    }
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 4);
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
      localEditable = this.mEditContent.getText();
      i = Selection.getSelectionStart(localEditable);
      j = Selection.getSelectionStart(localEditable);
      if ((i == -1) || (j == -1))
      {
        i = localEditable.length();
        j = localEditable.length();
      }
      if (localEditable.length() <= 500 - str.length())
        break label91;
    }
    label91: 
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
      localEditable = this.mEditContent.getText();
      i = Selection.getSelectionStart(localEditable);
      int j = Selection.getSelectionEnd(localEditable);
      if ((i == -1) || (j == -1))
      {
        i = localEditable.length();
        j = localEditable.length();
      }
      if (localEditable.length() > 500 - paramString.length())
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
    Iterator localIterator;
    if (paramInt1 == 4)
      if (paramInt2 == -1)
      {
        if (paramIntent != null)
        {
          Bundle localBundle = paramIntent.getBundleExtra("bundle");
          if (localBundle != null)
            this.checkedFriendsList = ((ArrayList)localBundle.getSerializable("checkedFriendsList"));
        }
        this.mReceiverList.clear();
        if (this.checkedFriendsList != null)
        {
          localIterator = this.checkedFriendsList.iterator();
          if (localIterator.hasNext())
            break label110;
        }
        constructViews();
        ((InputMethodManager)getActivity().getSystemService("input_method")).showSoftInput(this.mEditContent, 1);
      }
    label110: 
    do
    {
      return;
      FriendInfo localFriendInfo = (FriendInfo)localIterator.next();
      String str1 = localFriendInfo.getName();
      String str2 = localFriendInfo.getFlogo();
      String str3 = localFriendInfo.getFuid();
      for (int i = 0; ; i++)
      {
        int j = this.mReceiverList.size();
        int k = 0;
        if (i >= j);
        while (true)
        {
          if (k != 0)
            break label258;
          if (str1.length() > 3)
            str1 = str1.substring(0, 3) + "...";
          this.mReceiverList.add(new FriendInfo(str1, str3, str2));
          break;
          if (!((FriendInfo)this.mReceiverList.get(i)).getFuid().equals(str3))
            break label260;
          k = 1;
        }
        break;
      }
    }
    while (paramInt1 != 502);
    label258: label260: finish();
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    int i;
    int j;
    if (this.mOrientation != paramConfiguration.orientation)
    {
      this.mOrientation = paramConfiguration.orientation;
      i = this.mEditContent.getSelectionStart();
      j = this.mEditContent.getSelectionEnd();
      if (1 != this.mOrientation)
        break label84;
      SpannableString localSpannableString = StringUtil.convertTextToMessageFace(this.mEditContent.getText().toString());
      this.mEditContent.setText(localSpannableString);
    }
    while (true)
    {
      this.mEditContent.setSelection(i, j);
      super.onConfigurationChanged(paramConfiguration);
      return;
      label84: this.mEditContent.setText(this.mEditContent.getText().toString());
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (CrashRecoverUtil.isCrashBefore())
    {
      CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
      IntentUtil.returnToDesktop(getActivity());
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903300, paramViewGroup, false);
  }

  public void onDestroy()
  {
    cancelTask(this.mPostMessageTask);
    super.onDestroy();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      if (this.mIsSendingMessage)
      {
        if (this.mPostMessageTask != null)
        {
          this.mPostMessageTask.cancel(true);
          this.mPostMessageTask = null;
          MessageEngine.getInstance().cancel();
        }
        setSendingStatus(false);
        getView().findViewById(2131363475).setVisibility(8);
        getView().findViewById(2131363473).setVisibility(0);
        this.mEditContent.setVisibility(0);
        return true;
      }
      if ((this.mEditContent.getText().toString().length() > 0) || (this.mReceiverList.size() > 0))
      {
        showConfirmExitDialog();
        return true;
      }
      finish();
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  // ERROR //
  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: aload_2
    //   3: invokespecial 518	com/kaixin001/fragment/BaseFragment:onViewCreated	(Landroid/view/View;Landroid/os/Bundle;)V
    //   6: aload_0
    //   7: invokespecial 520	com/kaixin001/fragment/PostMessageFragment:initFaceKeyBoardLayout	()V
    //   10: aload_0
    //   11: invokestatic 523	com/kaixin001/model/FaceModel:getInstance	()Lcom/kaixin001/model/FaceModel;
    //   14: putfield 295	com/kaixin001/fragment/PostMessageFragment:mFaceModel	Lcom/kaixin001/model/FaceModel;
    //   17: aload_0
    //   18: aload_1
    //   19: ldc_w 524
    //   22: invokevirtual 157	android/view/View:findViewById	(I)Landroid/view/View;
    //   25: checkcast 119	android/widget/EditText
    //   28: putfield 55	com/kaixin001/fragment/PostMessageFragment:mEditContent	Landroid/widget/EditText;
    //   31: invokestatic 322	com/kaixin001/model/Setting:getInstance	()Lcom/kaixin001/model/Setting;
    //   34: invokevirtual 527	com/kaixin001/model/Setting:getCType	()Ljava/lang/String;
    //   37: ldc_w 529
    //   40: invokevirtual 533	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   43: ifeq +36 -> 79
    //   46: aload_0
    //   47: getfield 55	com/kaixin001/fragment/PostMessageFragment:mEditContent	Landroid/widget/EditText;
    //   50: astore 24
    //   52: iconst_1
    //   53: anewarray 535	android/text/InputFilter
    //   56: astore 25
    //   58: aload 25
    //   60: iconst_0
    //   61: new 537	android/text/InputFilter$LengthFilter
    //   64: dup
    //   65: sipush 200
    //   68: invokespecial 539	android/text/InputFilter$LengthFilter:<init>	(I)V
    //   71: aastore
    //   72: aload 24
    //   74: aload 25
    //   76: invokevirtual 543	android/widget/EditText:setFilters	([Landroid/text/InputFilter;)V
    //   79: aload_0
    //   80: invokevirtual 547	com/kaixin001/fragment/PostMessageFragment:getArguments	()Landroid/os/Bundle;
    //   83: astore_3
    //   84: aload_3
    //   85: ifnull +255 -> 340
    //   88: aload_3
    //   89: ldc 11
    //   91: invokevirtual 551	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   94: astore 4
    //   96: aload 4
    //   98: ifnull +9 -> 107
    //   101: aload_0
    //   102: aload 4
    //   104: putfield 47	com/kaixin001/fragment/PostMessageFragment:mPreviousActivityName	Ljava/lang/String;
    //   107: aload_3
    //   108: ldc 8
    //   110: invokevirtual 551	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   113: astore 5
    //   115: aload 5
    //   117: invokestatic 347	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   120: ifne +48 -> 168
    //   123: aload_0
    //   124: getfield 55	com/kaixin001/fragment/PostMessageFragment:mEditContent	Landroid/widget/EditText;
    //   127: aload 5
    //   129: invokevirtual 461	android/widget/EditText:setText	(Ljava/lang/CharSequence;)V
    //   132: aload_0
    //   133: getfield 55	com/kaixin001/fragment/PostMessageFragment:mEditContent	Landroid/widget/EditText;
    //   136: aload_0
    //   137: getfield 55	com/kaixin001/fragment/PostMessageFragment:mEditContent	Landroid/widget/EditText;
    //   140: invokevirtual 123	android/widget/EditText:getText	()Landroid/text/Editable;
    //   143: invokeinterface 129 1 0
    //   148: invokestatic 460	com/kaixin001/util/StringUtil:convertTextToMessageFace	(Ljava/lang/String;)Landroid/text/SpannableString;
    //   151: invokevirtual 461	android/widget/EditText:setText	(Ljava/lang/CharSequence;)V
    //   154: aload_0
    //   155: getfield 55	com/kaixin001/fragment/PostMessageFragment:mEditContent	Landroid/widget/EditText;
    //   158: aload_0
    //   159: getfield 55	com/kaixin001/fragment/PostMessageFragment:mEditContent	Landroid/widget/EditText;
    //   162: invokevirtual 552	android/widget/EditText:length	()I
    //   165: invokevirtual 554	android/widget/EditText:setSelection	(I)V
    //   168: aload_3
    //   169: ldc_w 556
    //   172: invokevirtual 551	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   175: astore 6
    //   177: aload_3
    //   178: ldc_w 558
    //   181: invokevirtual 551	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   184: astore 7
    //   186: aload_3
    //   187: ldc_w 560
    //   190: invokevirtual 551	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   193: astore 8
    //   195: aload 6
    //   197: ifnull +87 -> 284
    //   200: aload 7
    //   202: ifnull +82 -> 284
    //   205: aload 7
    //   207: invokevirtual 145	java/lang/String:length	()I
    //   210: iconst_3
    //   211: if_icmple +31 -> 242
    //   214: new 415	java/lang/StringBuilder
    //   217: dup
    //   218: aload 7
    //   220: iconst_0
    //   221: iconst_3
    //   222: invokevirtual 419	java/lang/String:substring	(II)Ljava/lang/String;
    //   225: invokestatic 423	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   228: invokespecial 425	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   231: ldc_w 427
    //   234: invokevirtual 431	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   237: invokevirtual 432	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   240: astore 7
    //   242: aload_0
    //   243: getfield 39	com/kaixin001/fragment/PostMessageFragment:mReceiverList	Ljava/util/ArrayList;
    //   246: new 264	com/kaixin001/item/FriendInfo
    //   249: dup
    //   250: aload 7
    //   252: aload 6
    //   254: aload 8
    //   256: invokespecial 435	com/kaixin001/item/FriendInfo:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   259: invokevirtual 439	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   262: pop
    //   263: aload_0
    //   264: getfield 51	com/kaixin001/fragment/PostMessageFragment:checkedFriendsList	Ljava/util/ArrayList;
    //   267: new 264	com/kaixin001/item/FriendInfo
    //   270: dup
    //   271: aload 7
    //   273: aload 6
    //   275: aload 8
    //   277: invokespecial 435	com/kaixin001/item/FriendInfo:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   280: invokevirtual 439	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   283: pop
    //   284: aload_0
    //   285: aload_3
    //   286: ldc_w 562
    //   289: iconst_0
    //   290: invokevirtual 566	android/os/Bundle:getBoolean	(Ljava/lang/String;Z)Z
    //   293: putfield 57	com/kaixin001/fragment/PostMessageFragment:mBack	Z
    //   296: aload_3
    //   297: ldc_w 568
    //   300: invokevirtual 551	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   303: astore 9
    //   305: aload 9
    //   307: invokestatic 347	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   310: ifne +30 -> 340
    //   313: aload 9
    //   315: ldc_w 570
    //   318: invokevirtual 574	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   321: astore 10
    //   323: iconst_0
    //   324: istore 11
    //   326: aload 10
    //   328: arraylength
    //   329: istore 14
    //   331: iload 11
    //   333: iload 14
    //   335: iconst_3
    //   336: isub
    //   337: if_icmple +66 -> 403
    //   340: aload_0
    //   341: aload_1
    //   342: invokevirtual 577	com/kaixin001/fragment/PostMessageFragment:setTitleBar	(Landroid/view/View;)V
    //   345: aload_1
    //   346: ldc_w 578
    //   349: invokevirtual 157	android/view/View:findViewById	(I)Landroid/view/View;
    //   352: checkcast 580	android/widget/Button
    //   355: new 582	com/kaixin001/fragment/PostMessageFragment$1
    //   358: dup
    //   359: aload_0
    //   360: invokespecial 583	com/kaixin001/fragment/PostMessageFragment$1:<init>	(Lcom/kaixin001/fragment/PostMessageFragment;)V
    //   363: invokevirtual 584	android/widget/Button:setOnClickListener	(Landroid/view/View$OnClickListener;)V
    //   366: aload_1
    //   367: ldc_w 585
    //   370: invokevirtual 157	android/view/View:findViewById	(I)Landroid/view/View;
    //   373: checkcast 196	android/widget/ImageView
    //   376: new 587	com/kaixin001/fragment/PostMessageFragment$2
    //   379: dup
    //   380: aload_0
    //   381: invokespecial 588	com/kaixin001/fragment/PostMessageFragment$2:<init>	(Lcom/kaixin001/fragment/PostMessageFragment;)V
    //   384: invokevirtual 589	android/widget/ImageView:setOnClickListener	(Landroid/view/View$OnClickListener;)V
    //   387: aload_0
    //   388: invokevirtual 391	com/kaixin001/fragment/PostMessageFragment:constructViews	()V
    //   391: aload_0
    //   392: aload_0
    //   393: invokevirtual 65	com/kaixin001/fragment/PostMessageFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
    //   396: invokestatic 595	com/kaixin001/util/ScreenUtil:getOrientation	(Landroid/content/Context;)I
    //   399: putfield 53	com/kaixin001/fragment/PostMessageFragment:mOrientation	I
    //   402: return
    //   403: iload 11
    //   405: iconst_1
    //   406: iadd
    //   407: istore 15
    //   409: aload 10
    //   411: iload 11
    //   413: aaload
    //   414: astore 16
    //   416: iload 15
    //   418: iconst_1
    //   419: iadd
    //   420: istore 11
    //   422: aload 10
    //   424: iload 15
    //   426: aaload
    //   427: astore 17
    //   429: iload 11
    //   431: iconst_1
    //   432: iadd
    //   433: istore 18
    //   435: aload 10
    //   437: iload 11
    //   439: aaload
    //   440: astore 19
    //   442: aload_0
    //   443: getfield 39	com/kaixin001/fragment/PostMessageFragment:mReceiverList	Ljava/util/ArrayList;
    //   446: new 264	com/kaixin001/item/FriendInfo
    //   449: dup
    //   450: aload 17
    //   452: aload 16
    //   454: aload 19
    //   456: invokespecial 435	com/kaixin001/item/FriendInfo:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   459: invokevirtual 439	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   462: pop
    //   463: aload_0
    //   464: getfield 51	com/kaixin001/fragment/PostMessageFragment:checkedFriendsList	Ljava/util/ArrayList;
    //   467: new 264	com/kaixin001/item/FriendInfo
    //   470: dup
    //   471: aload 17
    //   473: aload 16
    //   475: aload 19
    //   477: invokespecial 435	com/kaixin001/item/FriendInfo:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   480: invokevirtual 439	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   483: pop
    //   484: iload 18
    //   486: istore 11
    //   488: goto -162 -> 326
    //   491: astore 12
    //   493: iload 11
    //   495: pop
    //   496: aload 12
    //   498: invokevirtual 598	java/lang/Exception:printStackTrace	()V
    //   501: goto -161 -> 340
    //   504: astore 12
    //   506: goto -10 -> 496
    //
    // Exception table:
    //   from	to	target	type
    //   326	331	491	java/lang/Exception
    //   422	429	491	java/lang/Exception
    //   409	416	504	java/lang/Exception
    //   435	484	504	java/lang/Exception
  }

  public void requestFinish()
  {
    showConfirmExitDialog();
  }

  protected void setTitleBar(View paramView)
  {
    ImageView localImageView1 = (ImageView)paramView.findViewById(2131362914);
    localImageView1.setVisibility(0);
    localImageView1.setOnClickListener(new View.OnClickListener(paramView)
    {
      public void onClick(View paramView)
      {
        if (PostMessageFragment.this.mIsSendingMessage)
        {
          if (PostMessageFragment.this.mPostMessageTask != null)
          {
            if (PostMessageFragment.this.mPostMessageTask.getStatus() == AsyncTask.Status.FINISHED)
              return;
            PostMessageFragment.this.mPostMessageTask.cancel(true);
            PostMessageFragment.this.mPostMessageTask = null;
            MessageEngine.getInstance().cancel();
          }
          PostMessageFragment.this.setSendingStatus(false);
          this.val$rootView.findViewById(2131363475).setVisibility(8);
          this.val$rootView.findViewById(2131363473).setVisibility(0);
          PostMessageFragment.this.mEditContent.setVisibility(0);
          return;
        }
        if ((PostMessageFragment.this.mEditContent.getText().toString().length() > 0) || (PostMessageFragment.this.mReceiverList.size() > 0))
        {
          PostMessageFragment.this.showConfirmExitDialog();
          return;
        }
        PostMessageFragment.this.finish();
      }
    });
    ImageView localImageView2 = (ImageView)paramView.findViewById(2131362928);
    localImageView2.setVisibility(0);
    localImageView2.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (!PostMessageFragment.this.mIsSendingMessage)
        {
          PostMessageFragment.this.hideKeyboard();
          PostMessageFragment.this.doSendMessage();
        }
      }
    });
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    localTextView.setText(2131427589);
    localTextView.setVisibility(0);
    localImageView2.setImageResource(2130839272);
  }

  public void showFaceListDialog(boolean paramBoolean)
  {
    ImageView localImageView = (ImageView)getView().findViewById(2131363477);
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

  private class PostMessageTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    private PostMessageTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      if (paramArrayOfString.length < 1)
        return Boolean.valueOf(false);
      try
      {
        String str = paramArrayOfString[0];
        ArrayList localArrayList = new ArrayList();
        for (int i = 0; ; i++)
        {
          if (i >= PostMessageFragment.this.mReceiverList.size())
            return Boolean.valueOf(MessageEngine.getInstance().doPostMessage(PostMessageFragment.this.getActivity().getApplicationContext(), localArrayList, str));
          localArrayList.add(((FriendInfo)PostMessageFragment.this.mReceiverList.get(i)).getFuid());
        }
      }
      catch (Exception localException)
      {
        KXLog.e("PostMessageActivity", "doInBackground", localException);
      }
      return Boolean.valueOf(false);
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      while (true)
      {
        StringBuffer localStringBuffer;
        Iterator localIterator;
        try
        {
          if (paramBoolean.booleanValue())
            break label246;
          i = 2131427593;
          PostMessageFragment.this.setSendingStatus(false);
          PostMessageFragment.this.getView().findViewById(2131363475).setVisibility(8);
          PostMessageFragment.this.getView().findViewById(2131363473).setVisibility(0);
          PostMessageFragment.this.mEditContent.setVisibility(0);
          if (!paramBoolean.booleanValue())
            break label230;
          String str = MessageEngine.getInstance().getLastPostMessageMid();
          if ((str == null) || (PostMessageFragment.this.mBack))
            continue;
          PostMessageFragment.this.GotoMessageDetail(str);
          return;
          Intent localIntent = new Intent();
          localStringBuffer = new StringBuffer();
          localIterator = PostMessageFragment.this.mReceiverList.iterator();
          if (!localIterator.hasNext())
          {
            localIntent.putExtra("friend", localStringBuffer.toString());
            PostMessageFragment.this.setResult(-1, localIntent);
            PostMessageFragment.this.finish();
            return;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("PostMessageActivity", "onPostExecute", localException);
          return;
        }
        FriendInfo localFriendInfo = (FriendInfo)localIterator.next();
        localStringBuffer.append(localFriendInfo.getFuid()).append(",");
        localStringBuffer.append(localFriendInfo.getName()).append(",");
        continue;
        label230: Toast.makeText(PostMessageFragment.this.getActivity(), i, 0).show();
        return;
        label246: int i = 2131427592;
      }
    }

    protected void onPreExecuteA()
    {
      if (PostMessageFragment.this.getView() == null);
      View localView;
      do
      {
        return;
        localView = PostMessageFragment.this.getView().findViewById(2131363476);
      }
      while (localView == null);
      localView.setVisibility(8);
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class ReceiverButtonOnClickListener
    implements View.OnClickListener
  {
    private int mPosition;

    public ReceiverButtonOnClickListener(int arg2)
    {
      int i;
      this.mPosition = i;
    }

    public void onClick(View paramView)
    {
      if (!PostMessageFragment.this.mIsSendingMessage)
      {
        if (PostMessageFragment.this.checkedFriendsList != null)
          PostMessageFragment.this.checkedFriendsList.remove(this.mPosition);
        PostMessageFragment.this.mReceiverList.remove(this.mPosition);
        PostMessageFragment.this.constructViews();
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.PostMessageFragment
 * JD-Core Version:    0.6.0
 */