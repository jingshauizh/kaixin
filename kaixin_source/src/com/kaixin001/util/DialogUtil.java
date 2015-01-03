package com.kaixin001.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

public class DialogUtil
{
  public static final Dialog createAlertDialog(Context paramContext, int paramInt1, int paramInt2, int paramInt3, DialogInterface.OnClickListener paramOnClickListener1, DialogInterface.OnClickListener paramOnClickListener2)
  {
    FancyDialog.Builder localBuilder = new FancyDialog.Builder(paramContext);
    localBuilder.setType(FancyDialog.FancyDialogType.NOTICE).setSTitle(2131427329).setTitle(paramInt1).setYesBtnWord(paramInt2).setNoBtnWord(paramInt3).setYesBtnListener(paramOnClickListener1).setNoBtnListener(paramOnClickListener2);
    return localBuilder.create();
  }

  public static Dialog createAlertDialog(Context paramContext, int paramInt, DialogInterface.OnClickListener paramOnClickListener)
  {
    FancyDialog.Builder localBuilder = new FancyDialog.Builder(paramContext);
    localBuilder.setType(FancyDialog.FancyDialogType.NOTICE).setSTitle(2131427329).setTitle(paramInt).setYesBtnWord(2131427381).setYesBtnListener(paramOnClickListener);
    return localBuilder.create();
  }

  public static Dialog createAlertDialog(Context paramContext, String paramString, DialogInterface.OnClickListener paramOnClickListener)
  {
    FancyDialog.Builder localBuilder = new FancyDialog.Builder(paramContext);
    localBuilder.setType(FancyDialog.FancyDialogType.NOTICE).setSTitle(2131427329).setTitle(paramString).setYesBtnWord(2131427381).setYesBtnListener(paramOnClickListener);
    return localBuilder.create();
  }

  public static void dismissDialog(Dialog paramDialog)
  {
    if ((paramDialog != null) && (paramDialog.isShowing()));
    try
    {
      paramDialog.dismiss();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public static Dialog showAppDlg(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, DialogInterface.OnClickListener paramOnClickListener1, DialogInterface.OnClickListener paramOnClickListener2)
  {
    FancyDialog localFancyDialog = new FancyDialog.AppDialogBuilder(paramContext, paramString1, paramString2, paramString3, paramString4, paramOnClickListener1, paramOnClickListener2).create();
    localFancyDialog.show();
    return localFancyDialog;
  }

  public static Dialog showKXAlertDialog(Context paramContext, int paramInt1, int paramInt2, int paramInt3, int paramInt4, DialogInterface.OnClickListener paramOnClickListener1, DialogInterface.OnClickListener paramOnClickListener2)
  {
    return showMsgDlg(paramContext, paramInt1, paramInt2, paramInt3, paramInt4, paramOnClickListener1, paramOnClickListener2);
  }

  public static Dialog showKXAlertDialog(Context paramContext, int paramInt1, int paramInt2, int paramInt3, DialogInterface.OnClickListener paramOnClickListener1, DialogInterface.OnClickListener paramOnClickListener2)
  {
    return showMsgDlg(paramContext, 2131427329, paramInt1, paramInt2, paramInt3, paramOnClickListener1, paramOnClickListener2);
  }

  public static Dialog showKXAlertDialog(Context paramContext, int paramInt, DialogInterface.OnClickListener paramOnClickListener)
  {
    if (paramContext == null)
      return null;
    return showMsgDlgStdConfirm(paramContext, 2131427329, paramInt, paramOnClickListener);
  }

  public static Dialog showKXAlertDialog(Context paramContext, String paramString, int paramInt1, int paramInt2, DialogInterface.OnClickListener paramOnClickListener1, DialogInterface.OnClickListener paramOnClickListener2)
  {
    return showMsgDlg(paramContext, 2131427329, paramString, paramInt1, paramInt2, paramOnClickListener1, paramOnClickListener2);
  }

  public static Dialog showKXAlertDialog(Context paramContext, String paramString, DialogInterface.OnClickListener paramOnClickListener)
  {
    if ((paramContext == null) || (paramString == null))
      return null;
    return showMsgDlgStdConfirm(paramContext, 2131427329, paramString, paramOnClickListener);
  }

  public static Dialog showKXAlertDialog(Context paramContext, String paramString1, String paramString2, int paramInt1, int paramInt2, DialogInterface.OnClickListener paramOnClickListener1, DialogInterface.OnClickListener paramOnClickListener2)
  {
    return showMsgDlg(paramContext, paramString1, paramString2, paramInt1, paramInt2, paramOnClickListener1, paramOnClickListener2);
  }

  public static Dialog showMsgDlg(Context paramContext, int paramInt1, int paramInt2, int paramInt3, int paramInt4, DialogInterface.OnClickListener paramOnClickListener1, DialogInterface.OnClickListener paramOnClickListener2)
  {
    FancyDialog.Builder localBuilder = new FancyDialog.Builder(paramContext);
    localBuilder.setType(FancyDialog.FancyDialogType.NOTICE).setSTitle(paramInt1).setTitle(paramInt2).setYesBtnWord(paramInt3).setNoBtnWord(paramInt4).setYesBtnListener(paramOnClickListener1).setNoBtnListener(paramOnClickListener2);
    FancyDialog localFancyDialog = localBuilder.create();
    localFancyDialog.show();
    return localFancyDialog;
  }

  public static Dialog showMsgDlg(Context paramContext, int paramInt1, int paramInt2, int paramInt3, DialogInterface.OnClickListener paramOnClickListener)
  {
    FancyDialog.Builder localBuilder = new FancyDialog.Builder(paramContext);
    localBuilder.setType(FancyDialog.FancyDialogType.NOTICE).setSTitle(paramInt1).setTitle(paramInt2).setYesBtnWord(paramInt3).setYesBtnListener(paramOnClickListener);
    FancyDialog localFancyDialog = localBuilder.create();
    localFancyDialog.show();
    return localFancyDialog;
  }

  public static Dialog showMsgDlg(Context paramContext, int paramInt1, String paramString, int paramInt2, int paramInt3, DialogInterface.OnClickListener paramOnClickListener1, DialogInterface.OnClickListener paramOnClickListener2)
  {
    FancyDialog.Builder localBuilder = new FancyDialog.Builder(paramContext);
    localBuilder.setType(FancyDialog.FancyDialogType.NOTICE).setSTitle(paramInt1).setTitle(paramString).setYesBtnWord(paramInt2).setNoBtnWord(paramInt3).setYesBtnListener(paramOnClickListener1).setNoBtnListener(paramOnClickListener2);
    FancyDialog localFancyDialog = localBuilder.create();
    localFancyDialog.show();
    return localFancyDialog;
  }

  public static Dialog showMsgDlg(Context paramContext, int paramInt1, String paramString, int paramInt2, DialogInterface.OnClickListener paramOnClickListener)
  {
    FancyDialog.Builder localBuilder = new FancyDialog.Builder(paramContext);
    localBuilder.setType(FancyDialog.FancyDialogType.NOTICE).setSTitle(paramInt1).setTitle(paramString).setYesBtnWord(paramInt2).setYesBtnListener(paramOnClickListener);
    FancyDialog localFancyDialog = localBuilder.create();
    localFancyDialog.show();
    return localFancyDialog;
  }

  public static Dialog showMsgDlg(Context paramContext, String paramString1, String paramString2, int paramInt1, int paramInt2, DialogInterface.OnClickListener paramOnClickListener1, DialogInterface.OnClickListener paramOnClickListener2)
  {
    FancyDialog.Builder localBuilder = new FancyDialog.Builder(paramContext);
    localBuilder.setType(FancyDialog.FancyDialogType.NOTICE).setSTitle(paramString1).setTitle(paramString2).setYesBtnWord(paramInt1).setNoBtnWord(paramInt2).setYesBtnListener(paramOnClickListener1).setNoBtnListener(paramOnClickListener2);
    FancyDialog localFancyDialog = localBuilder.create();
    localFancyDialog.show();
    return localFancyDialog;
  }

  public static Dialog showMsgDlg(Context paramContext, String paramString1, String paramString2, String paramString3, DialogInterface.OnClickListener paramOnClickListener)
  {
    FancyDialog.Builder localBuilder = new FancyDialog.Builder(paramContext);
    localBuilder.setType(FancyDialog.FancyDialogType.NOTICE).setSTitle(paramString1).setTitle(paramString2).setYesBtnWord(paramString3).setYesBtnListener(paramOnClickListener);
    FancyDialog localFancyDialog = localBuilder.create();
    localFancyDialog.show();
    return localFancyDialog;
  }

  public static Dialog showMsgDlg(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, DialogInterface.OnClickListener paramOnClickListener1, DialogInterface.OnClickListener paramOnClickListener2)
  {
    FancyDialog.Builder localBuilder = new FancyDialog.Builder(paramContext);
    localBuilder.setType(FancyDialog.FancyDialogType.NOTICE).setSTitle(paramString1).setTitle(paramString2).setYesBtnWord(paramString3).setNoBtnWord(paramString4).setYesBtnListener(paramOnClickListener1).setNoBtnListener(paramOnClickListener2);
    FancyDialog localFancyDialog = localBuilder.create();
    localFancyDialog.show();
    return localFancyDialog;
  }

  public static Dialog showMsgDlgLite(Context paramContext, int paramInt, DialogInterface.OnClickListener paramOnClickListener)
  {
    return showMsgDlg(paramContext, 0, paramInt, 2131427381, 2131427587, paramOnClickListener, null);
  }

  public static Dialog showMsgDlgLiteCancel(Context paramContext, int paramInt)
  {
    return showMsgDlg(paramContext, 0, paramInt, 0, 2131427587, null, null);
  }

  public static Dialog showMsgDlgLiteConfirm(Context paramContext, int paramInt, DialogInterface.OnClickListener paramOnClickListener)
  {
    return showMsgDlg(paramContext, 0, paramInt, 2131427381, 0, paramOnClickListener, null);
  }

  public static Dialog showMsgDlgStd(Context paramContext, int paramInt1, int paramInt2, DialogInterface.OnClickListener paramOnClickListener)
  {
    return showMsgDlg(paramContext, paramInt1, paramInt2, 2131427381, 2131427587, paramOnClickListener, null);
  }

  public static Dialog showMsgDlgStd(Context paramContext, int paramInt, String paramString, DialogInterface.OnClickListener paramOnClickListener)
  {
    return showMsgDlg(paramContext, paramInt, paramString, 2131427381, 2131427587, paramOnClickListener, null);
  }

  public static Dialog showMsgDlgStdCancel(Context paramContext, int paramInt1, int paramInt2)
  {
    return showMsgDlg(paramContext, paramInt1, paramInt2, 0, 2131427587, null, null);
  }

  public static Dialog showMsgDlgStdConfirm(Context paramContext, int paramInt1, int paramInt2, DialogInterface.OnClickListener paramOnClickListener)
  {
    return showMsgDlg(paramContext, paramInt1, paramInt2, 2131427381, 0, paramOnClickListener, null);
  }

  public static Dialog showMsgDlgStdConfirm(Context paramContext, int paramInt, String paramString, DialogInterface.OnClickListener paramOnClickListener)
  {
    return showMsgDlg(paramContext, paramInt, paramString, 2131427381, 0, paramOnClickListener, null);
  }

  public static Dialog showSelectListDlg(Context paramContext, int paramInt1, String[] paramArrayOfString, DialogInterface.OnClickListener paramOnClickListener, int paramInt2)
  {
    FancyDialog.Builder localBuilder1 = new FancyDialog.Builder(paramContext);
    FancyDialog.Builder localBuilder2 = localBuilder1.setType(FancyDialog.FancyDialogType.SELECTLIST).setSTitle(paramInt1).setItems(paramArrayOfString).setItemsListener(paramOnClickListener);
    if (paramInt2 == 1);
    for (int i = 2131427587; ; i = 0)
    {
      localBuilder2.setNoBtnWord(i);
      FancyDialog localFancyDialog = localBuilder1.create();
      localFancyDialog.show();
      return localFancyDialog;
    }
  }

  public static Dialog showSelectListDlg(Context paramContext, int paramInt, String[] paramArrayOfString, DialogInterface.OnClickListener paramOnClickListener1, DialogInterface.OnClickListener paramOnClickListener2)
  {
    FancyDialog.Builder localBuilder = new FancyDialog.Builder(paramContext);
    localBuilder.setType(FancyDialog.FancyDialogType.SELECTLIST).setSTitle(paramInt).setItems(paramArrayOfString).setItemsListener(paramOnClickListener1).setNoBtnWord(2131427587).setNoBtnListener(paramOnClickListener2);
    FancyDialog localFancyDialog = localBuilder.create();
    localFancyDialog.show();
    return localFancyDialog;
  }

  public static Dialog showSelectListDlg(Context paramContext, String paramString, String[] paramArrayOfString, DialogInterface.OnClickListener paramOnClickListener, int paramInt)
  {
    FancyDialog.Builder localBuilder1 = new FancyDialog.Builder(paramContext);
    FancyDialog.Builder localBuilder2 = localBuilder1.setType(FancyDialog.FancyDialogType.SELECTLIST).setSTitle(paramString).setItems(paramArrayOfString).setItemsListener(paramOnClickListener);
    if (paramInt == 1);
    for (int i = 2131427587; ; i = 0)
    {
      localBuilder2.setNoBtnWord(i);
      FancyDialog localFancyDialog = localBuilder1.create();
      localFancyDialog.show();
      return localFancyDialog;
    }
  }

  public static Dialog showSelectListDlg(Context paramContext, String paramString, String[] paramArrayOfString, DialogInterface.OnClickListener paramOnClickListener1, DialogInterface.OnClickListener paramOnClickListener2)
  {
    FancyDialog.Builder localBuilder = new FancyDialog.Builder(paramContext);
    localBuilder.setType(FancyDialog.FancyDialogType.SELECTLIST).setSTitle(paramString).setItems(paramArrayOfString).setItemsListener(paramOnClickListener1).setNoBtnWord(2131427587).setNoBtnListener(paramOnClickListener2);
    FancyDialog localFancyDialog = localBuilder.create();
    localFancyDialog.show();
    return localFancyDialog;
  }

  public static Dialog showSelectListDlgWithNoBtn(Context paramContext, int paramInt, String[] paramArrayOfString, DialogInterface.OnClickListener paramOnClickListener, boolean paramBoolean)
  {
    FancyDialog.Builder localBuilder = new FancyDialog.Builder(paramContext);
    localBuilder.setType(FancyDialog.FancyDialogType.SELECTLIST).setSTitle(paramInt).setItems(paramArrayOfString).setItemsListener(paramOnClickListener).setItemHorizontalCenter(paramBoolean);
    FancyDialog localFancyDialog = localBuilder.create();
    localFancyDialog.show();
    return localFancyDialog;
  }

  public static Dialog showUploadPhotoDlgStd(Context paramContext, String paramString, DialogInterface.OnClickListener paramOnClickListener1, DialogInterface.OnClickListener paramOnClickListener2)
  {
    return showUploadPhototMsgDlg(paramContext, paramString, 2131428518, 2131427597, paramOnClickListener1, paramOnClickListener2);
  }

  public static Dialog showUploadPhototMsgDlg(Context paramContext, String paramString, int paramInt1, int paramInt2, DialogInterface.OnClickListener paramOnClickListener1, DialogInterface.OnClickListener paramOnClickListener2)
  {
    FancyDialog.Builder localBuilder = new FancyDialog.Builder(paramContext);
    localBuilder.setType(FancyDialog.FancyDialogType.NOTICE).setTitle(paramString).setYesBtnWord(paramInt1).setNoBtnWord(paramInt2).setYesBtnListener(paramOnClickListener1).setNoBtnListener(paramOnClickListener2);
    FancyDialog localFancyDialog = localBuilder.create();
    localFancyDialog.show();
    return localFancyDialog;
  }

  public static Dialog showValueListDlg(Context paramContext, int paramInt, String[] paramArrayOfString, FancyDialogAdapter paramFancyDialogAdapter, DialogInterface.OnClickListener paramOnClickListener, boolean paramBoolean1, boolean paramBoolean2)
  {
    FancyDialog.Builder localBuilder1 = new FancyDialog.Builder(paramContext);
    FancyDialog.Builder localBuilder2 = localBuilder1.setType(FancyDialog.FancyDialogType.VALUELIST).setSTitle(paramInt).setScroll(paramBoolean2).setItems(paramArrayOfString).setAdapter(paramFancyDialogAdapter).setAdapterListener(paramOnClickListener);
    if (paramBoolean1);
    for (int i = 2131427587; ; i = 0)
    {
      localBuilder2.setNoBtnWord(i);
      FancyDialog localFancyDialog = localBuilder1.create();
      localFancyDialog.show();
      return localFancyDialog;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.DialogUtil
 * JD-Core Version:    0.6.0
 */