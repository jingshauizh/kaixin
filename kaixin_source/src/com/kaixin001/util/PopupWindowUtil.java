package com.kaixin001.util;

import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import com.kaixin001.activity.MainActivity;
import com.kaixin001.activity.SettingActivity;
import com.kaixin001.adapter.SettingAdapter;
import com.kaixin001.model.Setting;
import com.kaixin001.view.KXIntroView;
import java.util.ArrayList;

public class PopupWindowUtil
{
  public static final String POPUP_FROM = "popup_delete_account";
  public static PopupWindow popup;

  public static PopupWindow createPopupWindow(int paramInt1, int paramInt2, boolean paramBoolean, Activity paramActivity, SettingAdapter paramSettingAdapter)
  {
    View localView = ((LayoutInflater)paramActivity.getSystemService("layout_inflater")).inflate(2130903332, null);
    ((ListView)localView.findViewById(2131363618)).setAdapter(paramSettingAdapter);
    popup = new PopupWindow(localView, paramInt1, paramInt2, paramBoolean);
    popup.setAnimationStyle(2131230724);
    localView.setFocusableInTouchMode(true);
    localView.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
      {
        if (PopupWindowUtil.popup != null)
          PopupWindowUtil.popup.dismiss();
        return false;
      }
    });
    return popup;
  }

  public static SettingAdapter createSettingAdapter(Activity paramActivity)
  {
    ArrayList localArrayList = new ArrayList();
    SettingAdapter localSettingAdapter = new SettingAdapter(paramActivity);
    localArrayList.add(new SettingListDate(paramActivity.getResources().getString(2131428488), new View.OnClickListener(paramActivity)
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent(PopupWindowUtil.this.getApplicationContext(), MainActivity.class);
        Bundle localBundle = new Bundle();
        localBundle.putString("fragment", "ContactsPrepareFragment");
        localBundle.putBoolean("isAddToBackStack", true);
        localIntent.putExtras(localBundle);
        PopupWindowUtil.this.startActivity(localIntent);
        PopupWindowUtil.popup.dismiss();
        UserHabitUtils.getInstance(paramView.getContext()).addUserHabit("click_menu_assoccontact");
      }
    }));
    localArrayList.add(new SettingListDate(paramActivity.getResources().getString(2131428548), new View.OnClickListener(paramActivity)
    {
      public void onClick(View paramView)
      {
        if (!Setting.getInstance().isTestVersion())
        {
          Intent localIntent = new Intent(PopupWindowUtil.this, MainActivity.class);
          Bundle localBundle = new Bundle();
          localBundle.putString("fragment", "AccountSwitchFragment");
          localBundle.putBoolean("isAddToBackStack", true);
          localIntent.putExtras(localBundle);
          PopupWindowUtil.this.startActivity(localIntent);
          PopupWindowUtil.popup.dismiss();
          UserHabitUtils.getInstance(paramView.getContext()).addUserHabit("click_menu_switch_account");
        }
      }
    }));
    localArrayList.add(new SettingListDate(paramActivity.getResources().getString(2131428489), new View.OnClickListener(paramActivity)
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent(PopupWindowUtil.this, MainActivity.class);
        Bundle localBundle = new Bundle();
        localBundle.putString("fragment", "UploadTaskListFragment");
        localBundle.putBoolean("isAddToBackStack", true);
        localIntent.putExtras(localBundle);
        PopupWindowUtil.this.startActivity(localIntent);
        PopupWindowUtil.popup.dismiss();
        UserHabitUtils.getInstance(paramView.getContext()).addUserHabit("click_menu_uploadstatus");
      }
    }));
    localArrayList.add(new SettingListDate(paramActivity.getResources().getString(2131428490), new View.OnClickListener(paramActivity)
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent(PopupWindowUtil.this, MainActivity.class);
        localIntent.putExtra("label", "");
        localIntent.putExtra("link", "");
        Bundle localBundle = new Bundle();
        localBundle.putString("fragment", "FeedbackFragment");
        localBundle.putBoolean("isAddToBackStack", true);
        localIntent.putExtras(localBundle);
        PopupWindowUtil.this.startActivity(localIntent);
        PopupWindowUtil.popup.dismiss();
        UserHabitUtils.getInstance(paramView.getContext()).addUserHabit("click_menu_feedback");
      }
    }));
    localArrayList.add(new SettingListDate(paramActivity.getResources().getString(2131428491), new View.OnClickListener(paramActivity)
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent(PopupWindowUtil.this, SettingActivity.class);
        PopupWindowUtil.this.startActivity(localIntent);
        PopupWindowUtil.popup.dismiss();
        UserHabitUtils.getInstance(paramView.getContext()).addUserHabit("click_menu_setting");
      }
    }));
    localArrayList.add(new SettingListDate(paramActivity.getResources().getString(2131428492), new View.OnClickListener(paramActivity)
    {
      public void onClick(View paramView)
      {
        if (!Setting.getInstance().isTestVersion())
          DialogUtil.showMsgDlgStd(PopupWindowUtil.this, 2131427697, 2131427369, new DialogInterface.OnClickListener(PopupWindowUtil.this)
          {
            // ERROR //
            public void onClick(android.content.DialogInterface paramDialogInterface, int paramInt)
            {
              // Byte code:
              //   0: invokestatic 33	com/kaixin001/model/Setting:getInstance	()Lcom/kaixin001/model/Setting;
              //   3: invokevirtual 37	com/kaixin001/model/Setting:isTestVersion	()Z
              //   6: ifne +234 -> 240
              //   9: invokestatic 42	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
              //   12: astore_3
              //   13: aconst_null
              //   14: astore 4
              //   16: new 44	com/kaixin001/db/UserDBAdapter
              //   19: dup
              //   20: aload_0
              //   21: getfield 21	com/kaixin001/util/PopupWindowUtil$7$1:val$activity	Landroid/app/Activity;
              //   24: invokespecial 47	com/kaixin001/db/UserDBAdapter:<init>	(Landroid/content/Context;)V
              //   27: astore 5
              //   29: aload 5
              //   31: invokevirtual 51	com/kaixin001/db/UserDBAdapter:getAllUserName	()Landroid/database/Cursor;
              //   34: astore 8
              //   36: aload 8
              //   38: aload 8
              //   40: ldc 53
              //   42: invokeinterface 59 2 0
              //   47: invokeinterface 63 2 0
              //   52: istore 9
              //   54: aload 5
              //   56: iload 9
              //   58: invokevirtual 66	com/kaixin001/db/UserDBAdapter:deleteUserInfoByUid	(I)I
              //   61: pop
              //   62: new 68	java/io/File
              //   65: dup
              //   66: new 70	java/lang/StringBuilder
              //   69: dup
              //   70: aload_0
              //   71: getfield 21	com/kaixin001/util/PopupWindowUtil$7$1:val$activity	Landroid/app/Activity;
              //   74: invokestatic 76	com/kaixin001/util/FileUtil:getKXCacheDir	(Landroid/content/Context;)Ljava/lang/String;
              //   77: invokestatic 82	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
              //   80: invokespecial 85	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
              //   83: ldc 87
              //   85: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
              //   88: iload 9
              //   90: invokevirtual 94	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
              //   93: invokevirtual 98	java/lang/StringBuilder:toString	()Ljava/lang/String;
              //   96: invokespecial 99	java/io/File:<init>	(Ljava/lang/String;)V
              //   99: invokestatic 103	com/kaixin001/util/FileUtil:deleteDirectory	(Ljava/io/File;)Z
              //   102: pop
              //   103: invokestatic 42	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
              //   106: invokevirtual 106	com/kaixin001/model/User:clearAccout	()V
              //   109: aload 5
              //   111: invokevirtual 51	com/kaixin001/db/UserDBAdapter:getAllUserName	()Landroid/database/Cursor;
              //   114: astore 12
              //   116: aload 12
              //   118: invokeinterface 110 1 0
              //   123: iconst_1
              //   124: if_icmplt +117 -> 241
              //   127: aload 5
              //   129: aload 12
              //   131: aload 12
              //   133: ldc 53
              //   135: invokeinterface 59 2 0
              //   140: invokeinterface 114 2 0
              //   145: invokevirtual 117	com/kaixin001/db/UserDBAdapter:switchLoginUser	(Ljava/lang/String;)V
              //   148: aload_3
              //   149: aload_0
              //   150: getfield 21	com/kaixin001/util/PopupWindowUtil$7$1:val$activity	Landroid/app/Activity;
              //   153: invokevirtual 121	com/kaixin001/model/User:loadUserData	(Landroid/content/Context;)Z
              //   156: pop
              //   157: aload_0
              //   158: getfield 21	com/kaixin001/util/PopupWindowUtil$7$1:val$activity	Landroid/app/Activity;
              //   161: invokevirtual 127	android/app/Activity:getApplicationContext	()Landroid/content/Context;
              //   164: aload_0
              //   165: getfield 21	com/kaixin001/util/PopupWindowUtil$7$1:val$activity	Landroid/app/Activity;
              //   168: invokevirtual 131	android/app/Activity:getResources	()Landroid/content/res/Resources;
              //   171: ldc 132
              //   173: invokevirtual 135	android/content/res/Resources:getString	(I)Ljava/lang/String;
              //   176: sipush 2000
              //   179: invokestatic 141	android/widget/Toast:makeText	(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;
              //   182: astore 15
              //   184: aload 15
              //   186: bipush 17
              //   188: iconst_0
              //   189: iconst_0
              //   190: invokevirtual 145	android/widget/Toast:setGravity	(III)V
              //   193: aload 15
              //   195: invokevirtual 148	android/widget/Toast:show	()V
              //   198: iconst_1
              //   199: putstatic 154	com/kaixin001/activity/MainActivity:isRefresh	Z
              //   202: aload_0
              //   203: getfield 21	com/kaixin001/util/PopupWindowUtil$7$1:val$activity	Landroid/app/Activity;
              //   206: invokestatic 160	com/kaixin001/util/IntentUtil:refreshLeftMenu	(Landroid/app/Activity;)V
              //   209: new 162	android/os/Handler
              //   212: dup
              //   213: invokespecial 163	android/os/Handler:<init>	()V
              //   216: new 165	com/kaixin001/util/PopupWindowUtil$7$1$1
              //   219: dup
              //   220: aload_0
              //   221: aload_0
              //   222: getfield 21	com/kaixin001/util/PopupWindowUtil$7$1:val$activity	Landroid/app/Activity;
              //   225: invokespecial 168	com/kaixin001/util/PopupWindowUtil$7$1$1:<init>	(Lcom/kaixin001/util/PopupWindowUtil$7$1;Landroid/app/Activity;)V
              //   228: ldc2_w 169
              //   231: invokevirtual 174	android/os/Handler:postDelayed	(Ljava/lang/Runnable;J)Z
              //   234: pop
              //   235: aload 5
              //   237: invokevirtual 177	com/kaixin001/db/UserDBAdapter:close	()V
              //   240: return
              //   241: new 179	com/kaixin001/businesslogic/LogoutAndExitProxy
              //   244: dup
              //   245: aload_0
              //   246: getfield 21	com/kaixin001/util/PopupWindowUtil$7$1:val$activity	Landroid/app/Activity;
              //   249: invokespecial 181	com/kaixin001/businesslogic/LogoutAndExitProxy:<init>	(Landroid/app/Activity;)V
              //   252: astore 13
              //   254: aload 13
              //   256: iconst_3
              //   257: aconst_null
              //   258: invokevirtual 185	com/kaixin001/businesslogic/LogoutAndExitProxy:logout	(ILcom/kaixin001/businesslogic/LogoutAndExitProxy$IOnLogoutListener;)V
              //   261: aload 13
              //   263: iconst_1
              //   264: invokevirtual 189	com/kaixin001/businesslogic/LogoutAndExitProxy:exit	(Z)V
              //   267: invokestatic 42	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
              //   270: iconst_1
              //   271: invokestatic 194	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
              //   274: invokevirtual 198	com/kaixin001/model/User:setLookAround	(Ljava/lang/Boolean;)V
              //   277: aload_0
              //   278: getfield 21	com/kaixin001/util/PopupWindowUtil$7$1:val$activity	Landroid/app/Activity;
              //   281: iconst_0
              //   282: invokestatic 202	com/kaixin001/util/IntentUtil:returnToLogin	(Landroid/app/Activity;Z)V
              //   285: goto -50 -> 235
              //   288: astore 7
              //   290: aload 5
              //   292: astore 4
              //   294: aload 7
              //   296: invokevirtual 205	java/lang/Exception:printStackTrace	()V
              //   299: aload 4
              //   301: invokevirtual 177	com/kaixin001/db/UserDBAdapter:close	()V
              //   304: return
              //   305: astore 6
              //   307: aload 4
              //   309: invokevirtual 177	com/kaixin001/db/UserDBAdapter:close	()V
              //   312: aload 6
              //   314: athrow
              //   315: astore 6
              //   317: aload 5
              //   319: astore 4
              //   321: goto -14 -> 307
              //   324: astore 7
              //   326: aconst_null
              //   327: astore 4
              //   329: goto -35 -> 294
              //
              // Exception table:
              //   from	to	target	type
              //   29	235	288	java/lang/Exception
              //   241	285	288	java/lang/Exception
              //   16	29	305	finally
              //   294	299	305	finally
              //   29	235	315	finally
              //   241	285	315	finally
              //   16	29	324	java/lang/Exception
            }
          });
        PopupWindowUtil.popup.dismiss();
        UserHabitUtils.getInstance(PopupWindowUtil.this).addUserHabit("click_menu_logout");
      }
    }));
    localSettingAdapter.addSetting(localArrayList);
    return localSettingAdapter;
  }

  public static PopupWindow setNewsFreshPopupWindow(Activity paramActivity, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    View localView = ((LayoutInflater)paramActivity.getSystemService("layout_inflater")).inflate(2130903041, null);
    ((KXIntroView)localView.findViewById(2131363108));
    popup = new PopupWindow(localView, paramInt1, paramInt2, paramBoolean);
    return popup;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.PopupWindowUtil
 * JD-Core Version:    0.6.0
 */