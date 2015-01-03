package com.kaixin001.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.kaixin001.activity.LoginActivity;
import com.kaixin001.activity.MainActivity;
import com.kaixin001.model.User;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.UserHabitUtils;

public class AccountSwitchFragment extends BaseFragment
  implements View.OnClickListener, AdapterView.OnItemClickListener
{
  private static final String SP_ACCOUNTS_NAME = "sharedpreferences_accounts";
  public static String TAG = "AccountSwitchActivity";
  private AccountSwitchAdapter adapter;
  private ImageView btnLeft;
  private ImageView btnRight;
  private View footview = null;
  private boolean isEditDelete = false;
  public boolean isSwitchAccount = false;
  private ListView lvAccount = null;
  private TextView tvEditorComplete = null;

  private void addUseAccountSwitchDevice()
  {
    String str = ((TelephonyManager)getActivity().getSystemService("phone")).getDeviceId();
    SharedPreferences localSharedPreferences = getActivity().getSharedPreferences("sharedpreferences_accounts", 0);
    if (!localSharedPreferences.contains(str))
    {
      SharedPreferences.Editor localEditor = localSharedPreferences.edit();
      localEditor.putString(str, str);
      localEditor.commit();
      UserHabitUtils.getInstance(getActivity()).addUserHabit("use_switch_account_device:" + str);
      UserHabitUtils.getInstance(getActivity()).addUserHabit("use_switch_account_user:" + User.getInstance().getUID());
    }
  }

  private void gotoLoginActivity(boolean paramBoolean)
  {
    Intent localIntent = new Intent(getActivity(), LoginActivity.class);
    if (paramBoolean)
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("from", "AccountSwitchActivity");
      localIntent.putExtras(localBundle);
    }
    AnimationUtil.startActivity(getActivity(), localIntent, 3);
  }

  private void loadUserGotoDesktop()
  {
    User.getInstance().loadUserData(getActivity());
    MainActivity.isRefresh = true;
    IntentUtil.refreshLeftMenu(getActivity());
    Intent localIntent = new Intent(getActivity(), MainActivity.class);
    getActivity().startActivity(localIntent);
  }

  // ERROR //
  private void setListView(View paramView)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: ldc 185
    //   4: invokevirtual 191	android/view/View:findViewById	(I)Landroid/view/View;
    //   7: checkcast 193	android/widget/ListView
    //   10: putfield 41	com/kaixin001/fragment/AccountSwitchFragment:lvAccount	Landroid/widget/ListView;
    //   13: aload_0
    //   14: aload_0
    //   15: invokevirtual 65	com/kaixin001/fragment/AccountSwitchFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
    //   18: invokestatic 198	android/view/LayoutInflater:from	(Landroid/content/Context;)Landroid/view/LayoutInflater;
    //   21: ldc 199
    //   23: aconst_null
    //   24: invokevirtual 203	android/view/LayoutInflater:inflate	(ILandroid/view/ViewGroup;)Landroid/view/View;
    //   27: putfield 43	com/kaixin001/fragment/AccountSwitchFragment:footview	Landroid/view/View;
    //   30: aload_0
    //   31: getfield 43	com/kaixin001/fragment/AccountSwitchFragment:footview	Landroid/view/View;
    //   34: new 205	com/kaixin001/fragment/AccountSwitchFragment$1
    //   37: dup
    //   38: aload_0
    //   39: invokespecial 207	com/kaixin001/fragment/AccountSwitchFragment$1:<init>	(Lcom/kaixin001/fragment/AccountSwitchFragment;)V
    //   42: invokevirtual 211	android/view/View:setOnClickListener	(Landroid/view/View$OnClickListener;)V
    //   45: aload_0
    //   46: getfield 41	com/kaixin001/fragment/AccountSwitchFragment:lvAccount	Landroid/widget/ListView;
    //   49: aload_0
    //   50: getfield 43	com/kaixin001/fragment/AccountSwitchFragment:footview	Landroid/view/View;
    //   53: invokevirtual 214	android/widget/ListView:addFooterView	(Landroid/view/View;)V
    //   56: new 216	com/kaixin001/db/UserDBAdapter
    //   59: dup
    //   60: aload_0
    //   61: invokevirtual 65	com/kaixin001/fragment/AccountSwitchFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
    //   64: invokespecial 219	com/kaixin001/db/UserDBAdapter:<init>	(Landroid/content/Context;)V
    //   67: astore_2
    //   68: aload_2
    //   69: invokevirtual 223	com/kaixin001/db/UserDBAdapter:getAllUserName	()Landroid/database/Cursor;
    //   72: astore 4
    //   74: aload 4
    //   76: ifnull +66 -> 142
    //   79: aload 4
    //   81: invokeinterface 229 1 0
    //   86: iflt +56 -> 142
    //   89: aload_0
    //   90: new 231	com/kaixin001/fragment/AccountSwitchFragment$AccountSwitchAdapter
    //   93: dup
    //   94: aload_0
    //   95: aload_0
    //   96: invokevirtual 65	com/kaixin001/fragment/AccountSwitchFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
    //   99: aload 4
    //   101: invokespecial 234	com/kaixin001/fragment/AccountSwitchFragment$AccountSwitchAdapter:<init>	(Lcom/kaixin001/fragment/AccountSwitchFragment;Landroid/content/Context;Landroid/database/Cursor;)V
    //   104: putfield 236	com/kaixin001/fragment/AccountSwitchFragment:adapter	Lcom/kaixin001/fragment/AccountSwitchFragment$AccountSwitchAdapter;
    //   107: aload_0
    //   108: getfield 41	com/kaixin001/fragment/AccountSwitchFragment:lvAccount	Landroid/widget/ListView;
    //   111: aload_0
    //   112: getfield 236	com/kaixin001/fragment/AccountSwitchFragment:adapter	Lcom/kaixin001/fragment/AccountSwitchFragment$AccountSwitchAdapter;
    //   115: invokevirtual 240	android/widget/ListView:setAdapter	(Landroid/widget/ListAdapter;)V
    //   118: aload_0
    //   119: getfield 41	com/kaixin001/fragment/AccountSwitchFragment:lvAccount	Landroid/widget/ListView;
    //   122: aload_0
    //   123: invokevirtual 244	android/widget/ListView:setOnItemClickListener	(Landroid/widget/AdapterView$OnItemClickListener;)V
    //   126: return
    //   127: astore 5
    //   129: getstatic 34	com/kaixin001/fragment/AccountSwitchFragment:TAG	Ljava/lang/String;
    //   132: ldc 246
    //   134: invokestatic 251	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   137: return
    //   138: astore_3
    //   139: goto -10 -> 129
    //   142: return
    //
    // Exception table:
    //   from	to	target	type
    //   56	68	127	java/lang/Exception
    //   68	74	138	java/lang/Exception
    //   79	126	138	java/lang/Exception
  }

  private void setTitleBar(View paramView)
  {
    this.btnLeft = ((ImageView)paramView.findViewById(2131362914));
    this.btnLeft.setOnClickListener(this);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    localTextView.setText(getResources().getString(2131428548));
    localTextView.setVisibility(0);
    this.tvEditorComplete = ((TextView)paramView.findViewById(2131362930));
    this.tvEditorComplete.setText(getResources().getString(2131428425));
    this.tvEditorComplete.setVisibility(0);
    this.tvEditorComplete.setOnClickListener(this);
    this.btnRight = ((ImageView)paramView.findViewById(2131362928));
    this.btnRight.setVisibility(8);
  }

  // ERROR //
  private void updateListView()
  {
    // Byte code:
    //   0: new 216	com/kaixin001/db/UserDBAdapter
    //   3: dup
    //   4: aload_0
    //   5: invokevirtual 65	com/kaixin001/fragment/AccountSwitchFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
    //   8: invokespecial 219	com/kaixin001/db/UserDBAdapter:<init>	(Landroid/content/Context;)V
    //   11: astore_1
    //   12: aload_1
    //   13: invokevirtual 223	com/kaixin001/db/UserDBAdapter:getAllUserName	()Landroid/database/Cursor;
    //   16: astore_3
    //   17: aload_3
    //   18: ifnull +57 -> 75
    //   21: aload_3
    //   22: invokeinterface 229 1 0
    //   27: iflt +48 -> 75
    //   30: aload_0
    //   31: new 231	com/kaixin001/fragment/AccountSwitchFragment$AccountSwitchAdapter
    //   34: dup
    //   35: aload_0
    //   36: aload_0
    //   37: invokevirtual 65	com/kaixin001/fragment/AccountSwitchFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
    //   40: aload_3
    //   41: invokespecial 234	com/kaixin001/fragment/AccountSwitchFragment$AccountSwitchAdapter:<init>	(Lcom/kaixin001/fragment/AccountSwitchFragment;Landroid/content/Context;Landroid/database/Cursor;)V
    //   44: putfield 236	com/kaixin001/fragment/AccountSwitchFragment:adapter	Lcom/kaixin001/fragment/AccountSwitchFragment$AccountSwitchAdapter;
    //   47: aload_0
    //   48: getfield 41	com/kaixin001/fragment/AccountSwitchFragment:lvAccount	Landroid/widget/ListView;
    //   51: aload_0
    //   52: getfield 236	com/kaixin001/fragment/AccountSwitchFragment:adapter	Lcom/kaixin001/fragment/AccountSwitchFragment$AccountSwitchAdapter;
    //   55: invokevirtual 240	android/widget/ListView:setAdapter	(Landroid/widget/ListAdapter;)V
    //   58: return
    //   59: astore 4
    //   61: getstatic 34	com/kaixin001/fragment/AccountSwitchFragment:TAG	Ljava/lang/String;
    //   64: ldc_w 289
    //   67: invokestatic 251	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   70: return
    //   71: astore_2
    //   72: goto -11 -> 61
    //   75: return
    //
    // Exception table:
    //   from	to	target	type
    //   0	12	59	java/lang/Exception
    //   12	17	71	java/lang/Exception
    //   21	58	71	java/lang/Exception
  }

  public void onClick(View paramView)
  {
    if (paramView.equals(this.btnLeft))
      finish();
    do
      return;
    while (!paramView.equals(this.tvEditorComplete));
    if (this.tvEditorComplete.getText().equals(getResources().getString(2131428425)))
    {
      this.tvEditorComplete.setText(2131427615);
      this.isEditDelete = true;
    }
    while (true)
    {
      updateListView();
      return;
      if (!this.tvEditorComplete.getText().equals(getResources().getString(2131427615)))
        continue;
      this.tvEditorComplete.setText(2131428425);
      this.isEditDelete = false;
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    addUseAccountSwitchDevice();
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903040, paramViewGroup, false);
  }

  public void onDestroy()
  {
    super.onDestroy();
    if (this.isSwitchAccount)
      AnimationUtil.finishActivity(getActivity(), 1);
  }

  // ERROR //
  public void onItemClick(android.widget.AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    // Byte code:
    //   0: aload_0
    //   1: iconst_1
    //   2: putfield 47	com/kaixin001/fragment/AccountSwitchFragment:isSwitchAccount	Z
    //   5: aload_0
    //   6: invokevirtual 65	com/kaixin001/fragment/AccountSwitchFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
    //   9: aload_0
    //   10: invokevirtual 265	com/kaixin001/fragment/AccountSwitchFragment:getResources	()Landroid/content/res/Resources;
    //   13: ldc_w 328
    //   16: invokevirtual 272	android/content/res/Resources:getString	(I)Ljava/lang/String;
    //   19: sipush 1000
    //   22: invokestatic 334	android/widget/Toast:makeText	(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;
    //   25: astore 6
    //   27: aload 6
    //   29: bipush 17
    //   31: iconst_0
    //   32: iconst_0
    //   33: invokevirtual 338	android/widget/Toast:setGravity	(III)V
    //   36: aload 6
    //   38: invokevirtual 341	android/widget/Toast:show	()V
    //   41: aload_2
    //   42: invokevirtual 345	android/view/View:getTag	()Ljava/lang/Object;
    //   45: checkcast 347	com/kaixin001/fragment/AccountSwitchFragment$ViewHolder
    //   48: astore 7
    //   50: aload_0
    //   51: invokespecial 54	com/kaixin001/fragment/AccountSwitchFragment:updateListView	()V
    //   54: aload_2
    //   55: ldc_w 348
    //   58: invokevirtual 191	android/view/View:findViewById	(I)Landroid/view/View;
    //   61: checkcast 255	android/widget/ImageView
    //   64: ldc_w 349
    //   67: invokevirtual 352	android/widget/ImageView:setImageResource	(I)V
    //   70: aconst_null
    //   71: astore 8
    //   73: new 216	com/kaixin001/db/UserDBAdapter
    //   76: dup
    //   77: aload_0
    //   78: invokevirtual 65	com/kaixin001/fragment/AccountSwitchFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
    //   81: invokespecial 219	com/kaixin001/db/UserDBAdapter:<init>	(Landroid/content/Context;)V
    //   84: astore 9
    //   86: aload 9
    //   88: aload 7
    //   90: getfield 355	com/kaixin001/fragment/AccountSwitchFragment$ViewHolder:ivDelete	Landroid/widget/ImageView;
    //   93: invokevirtual 356	android/widget/ImageView:getTag	()Ljava/lang/Object;
    //   96: checkcast 358	java/lang/Integer
    //   99: invokestatic 364	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   102: invokevirtual 367	com/kaixin001/db/UserDBAdapter:switchLoginUser	(Ljava/lang/String;)V
    //   105: aload 9
    //   107: invokevirtual 370	com/kaixin001/db/UserDBAdapter:close	()V
    //   110: aload_0
    //   111: invokespecial 372	com/kaixin001/fragment/AccountSwitchFragment:loadUserGotoDesktop	()V
    //   114: aload_0
    //   115: invokevirtual 65	com/kaixin001/fragment/AccountSwitchFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
    //   118: checkcast 168	com/kaixin001/activity/MainActivity
    //   121: invokevirtual 375	com/kaixin001/activity/MainActivity:clearBackStack	()V
    //   124: aload_0
    //   125: iconst_0
    //   126: putfield 47	com/kaixin001/fragment/AccountSwitchFragment:isSwitchAccount	Z
    //   129: aload_0
    //   130: invokevirtual 65	com/kaixin001/fragment/AccountSwitchFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
    //   133: invokestatic 109	com/kaixin001/util/UserHabitUtils:getInstance	(Landroid/content/Context;)Lcom/kaixin001/util/UserHabitUtils;
    //   136: new 111	java/lang/StringBuilder
    //   139: dup
    //   140: ldc_w 377
    //   143: invokespecial 116	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   146: aload 7
    //   148: getfield 355	com/kaixin001/fragment/AccountSwitchFragment$ViewHolder:ivDelete	Landroid/widget/ImageView;
    //   151: invokevirtual 356	android/widget/ImageView:getTag	()Ljava/lang/Object;
    //   154: checkcast 358	java/lang/Integer
    //   157: invokestatic 364	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   160: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   163: invokevirtual 123	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   166: invokevirtual 126	com/kaixin001/util/UserHabitUtils:addUserHabit	(Ljava/lang/String;)V
    //   169: return
    //   170: astore 10
    //   172: aload 10
    //   174: invokevirtual 380	java/lang/Exception:printStackTrace	()V
    //   177: aload 8
    //   179: invokevirtual 370	com/kaixin001/db/UserDBAdapter:close	()V
    //   182: goto -72 -> 110
    //   185: astore 11
    //   187: aload 8
    //   189: invokevirtual 370	com/kaixin001/db/UserDBAdapter:close	()V
    //   192: aload 11
    //   194: athrow
    //   195: astore 11
    //   197: aload 9
    //   199: astore 8
    //   201: goto -14 -> 187
    //   204: astore 10
    //   206: aload 9
    //   208: astore 8
    //   210: goto -38 -> 172
    //
    // Exception table:
    //   from	to	target	type
    //   73	86	170	java/lang/Exception
    //   73	86	185	finally
    //   172	177	185	finally
    //   86	105	195	finally
    //   86	105	204	java/lang/Exception
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    setTitleBar(paramView);
    setListView(paramView);
  }

  class AccountSwitchAdapter extends CursorAdapter
  {
    private Cursor cursor;
    private LayoutInflater mInflater;

    public AccountSwitchAdapter(Context paramCursor, Cursor arg3)
    {
      super(localCursor);
      this.cursor = localCursor;
      this.mInflater = LayoutInflater.from(paramCursor);
    }

    public AccountSwitchAdapter(Context paramCursor, Cursor paramBoolean, boolean arg4)
    {
      super(paramBoolean, bool);
    }

    public void bindView(View paramView, Context paramContext, Cursor paramCursor)
    {
      AccountSwitchFragment.ViewHolder localViewHolder = (AccountSwitchFragment.ViewHolder)paramView.getTag();
      localViewHolder.showView(paramCursor);
      localViewHolder.setListener(paramContext);
    }

    public int getCount()
    {
      return this.cursor.getCount();
    }

    public View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
    {
      View localView = this.mInflater.inflate(2130903042, paramViewGroup, false);
      AccountSwitchFragment.ViewHolder localViewHolder = new AccountSwitchFragment.ViewHolder(AccountSwitchFragment.this, null);
      localViewHolder.initView(localView);
      localViewHolder.showView(paramCursor);
      localView.setTag(localViewHolder);
      return localView;
    }
  }

  private class ViewHolder
  {
    public ImageView ivDelete;
    public ImageView ivLoginCheckBtn;
    public TextView tvAccountName;

    private ViewHolder()
    {
    }

    public void initView(View paramView)
    {
      this.ivLoginCheckBtn = ((ImageView)paramView.findViewById(2131361804));
      this.tvAccountName = ((TextView)paramView.findViewById(2131361805));
      this.ivDelete = ((ImageView)paramView.findViewById(2131361806));
    }

    public void setListener(Context paramContext)
    {
      this.ivDelete.setOnClickListener(new View.OnClickListener(paramContext)
      {
        // ERROR //
        public void onClick(View paramView)
        {
          // Byte code:
          //   0: aload_1
          //   1: invokevirtual 34	android/view/View:getTag	()Ljava/lang/Object;
          //   4: checkcast 36	java/lang/Integer
          //   7: invokevirtual 40	java/lang/Integer:intValue	()I
          //   10: istore_2
          //   11: aconst_null
          //   12: astore_3
          //   13: new 42	com/kaixin001/db/UserDBAdapter
          //   16: dup
          //   17: aload_0
          //   18: getfield 21	com/kaixin001/fragment/AccountSwitchFragment$ViewHolder$1:val$context	Landroid/content/Context;
          //   21: invokespecial 44	com/kaixin001/db/UserDBAdapter:<init>	(Landroid/content/Context;)V
          //   24: astore 4
          //   26: aload 4
          //   28: iload_2
          //   29: invokevirtual 48	com/kaixin001/db/UserDBAdapter:deleteUserInfoByUid	(I)I
          //   32: pop
          //   33: new 50	java/io/File
          //   36: dup
          //   37: new 52	java/lang/StringBuilder
          //   40: dup
          //   41: aload_0
          //   42: getfield 19	com/kaixin001/fragment/AccountSwitchFragment$ViewHolder$1:this$1	Lcom/kaixin001/fragment/AccountSwitchFragment$ViewHolder;
          //   45: invokestatic 56	com/kaixin001/fragment/AccountSwitchFragment$ViewHolder:access$1	(Lcom/kaixin001/fragment/AccountSwitchFragment$ViewHolder;)Lcom/kaixin001/fragment/AccountSwitchFragment;
          //   48: invokevirtual 62	com/kaixin001/fragment/AccountSwitchFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
          //   51: invokestatic 68	com/kaixin001/util/FileUtil:getKXCacheDir	(Landroid/content/Context;)Ljava/lang/String;
          //   54: invokestatic 74	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
          //   57: invokespecial 77	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   60: ldc 79
          //   62: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   65: iload_2
          //   66: invokevirtual 86	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
          //   69: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   72: invokespecial 91	java/io/File:<init>	(Ljava/lang/String;)V
          //   75: invokestatic 95	com/kaixin001/util/FileUtil:deleteDirectory	(Ljava/io/File;)Z
          //   78: pop
          //   79: invokestatic 101	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
          //   82: invokevirtual 104	com/kaixin001/model/User:getUID	()Ljava/lang/String;
          //   85: new 52	java/lang/StringBuilder
          //   88: dup
          //   89: iload_2
          //   90: invokestatic 107	java/lang/String:valueOf	(I)Ljava/lang/String;
          //   93: invokespecial 77	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   96: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   99: invokevirtual 111	java/lang/String:equals	(Ljava/lang/Object;)Z
          //   102: istore 9
          //   104: iconst_0
          //   105: istore 10
          //   107: iload 9
          //   109: ifeq +12 -> 121
          //   112: invokestatic 101	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
          //   115: invokevirtual 114	com/kaixin001/model/User:clearAccout	()V
          //   118: iconst_1
          //   119: istore 10
          //   121: aload 4
          //   123: invokevirtual 118	com/kaixin001/db/UserDBAdapter:getAllUserName	()Landroid/database/Cursor;
          //   126: astore 11
          //   128: aload 11
          //   130: invokeinterface 123 1 0
          //   135: iconst_1
          //   136: if_icmplt +122 -> 258
          //   139: iload 10
          //   141: ifeq +50 -> 191
          //   144: aload_0
          //   145: getfield 19	com/kaixin001/fragment/AccountSwitchFragment$ViewHolder$1:this$1	Lcom/kaixin001/fragment/AccountSwitchFragment$ViewHolder;
          //   148: invokestatic 56	com/kaixin001/fragment/AccountSwitchFragment$ViewHolder:access$1	(Lcom/kaixin001/fragment/AccountSwitchFragment$ViewHolder;)Lcom/kaixin001/fragment/AccountSwitchFragment;
          //   151: invokevirtual 62	com/kaixin001/fragment/AccountSwitchFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
          //   154: aload_0
          //   155: getfield 19	com/kaixin001/fragment/AccountSwitchFragment$ViewHolder$1:this$1	Lcom/kaixin001/fragment/AccountSwitchFragment$ViewHolder;
          //   158: invokestatic 56	com/kaixin001/fragment/AccountSwitchFragment$ViewHolder:access$1	(Lcom/kaixin001/fragment/AccountSwitchFragment$ViewHolder;)Lcom/kaixin001/fragment/AccountSwitchFragment;
          //   161: invokevirtual 127	com/kaixin001/fragment/AccountSwitchFragment:getResources	()Landroid/content/res/Resources;
          //   164: ldc 128
          //   166: invokevirtual 133	android/content/res/Resources:getString	(I)Ljava/lang/String;
          //   169: sipush 2000
          //   172: invokestatic 139	android/widget/Toast:makeText	(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;
          //   175: astore 12
          //   177: aload 12
          //   179: bipush 17
          //   181: iconst_0
          //   182: iconst_0
          //   183: invokevirtual 143	android/widget/Toast:setGravity	(III)V
          //   186: aload 12
          //   188: invokevirtual 146	android/widget/Toast:show	()V
          //   191: aload 4
          //   193: aload 11
          //   195: aload 11
          //   197: ldc 148
          //   199: invokeinterface 152 2 0
          //   204: invokeinterface 153 2 0
          //   209: invokevirtual 156	com/kaixin001/db/UserDBAdapter:switchLoginUser	(Ljava/lang/String;)V
          //   212: invokestatic 101	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
          //   215: aload_0
          //   216: getfield 19	com/kaixin001/fragment/AccountSwitchFragment$ViewHolder$1:this$1	Lcom/kaixin001/fragment/AccountSwitchFragment$ViewHolder;
          //   219: invokestatic 56	com/kaixin001/fragment/AccountSwitchFragment$ViewHolder:access$1	(Lcom/kaixin001/fragment/AccountSwitchFragment$ViewHolder;)Lcom/kaixin001/fragment/AccountSwitchFragment;
          //   222: invokevirtual 62	com/kaixin001/fragment/AccountSwitchFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
          //   225: invokevirtual 160	com/kaixin001/model/User:loadUserData	(Landroid/content/Context;)Z
          //   228: pop
          //   229: aload_0
          //   230: getfield 19	com/kaixin001/fragment/AccountSwitchFragment$ViewHolder$1:this$1	Lcom/kaixin001/fragment/AccountSwitchFragment$ViewHolder;
          //   233: invokestatic 56	com/kaixin001/fragment/AccountSwitchFragment$ViewHolder:access$1	(Lcom/kaixin001/fragment/AccountSwitchFragment$ViewHolder;)Lcom/kaixin001/fragment/AccountSwitchFragment;
          //   236: invokestatic 163	com/kaixin001/fragment/AccountSwitchFragment:access$1	(Lcom/kaixin001/fragment/AccountSwitchFragment;)V
          //   239: aload_0
          //   240: getfield 19	com/kaixin001/fragment/AccountSwitchFragment$ViewHolder$1:this$1	Lcom/kaixin001/fragment/AccountSwitchFragment$ViewHolder;
          //   243: invokestatic 56	com/kaixin001/fragment/AccountSwitchFragment$ViewHolder:access$1	(Lcom/kaixin001/fragment/AccountSwitchFragment$ViewHolder;)Lcom/kaixin001/fragment/AccountSwitchFragment;
          //   246: invokevirtual 62	com/kaixin001/fragment/AccountSwitchFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
          //   249: invokestatic 169	com/kaixin001/util/IntentUtil:refreshLeftMenu	(Landroid/app/Activity;)V
          //   252: aload 4
          //   254: invokevirtual 172	com/kaixin001/db/UserDBAdapter:close	()V
          //   257: return
          //   258: new 174	com/kaixin001/businesslogic/LogoutAndExitProxy
          //   261: dup
          //   262: aload_0
          //   263: getfield 19	com/kaixin001/fragment/AccountSwitchFragment$ViewHolder$1:this$1	Lcom/kaixin001/fragment/AccountSwitchFragment$ViewHolder;
          //   266: invokestatic 56	com/kaixin001/fragment/AccountSwitchFragment$ViewHolder:access$1	(Lcom/kaixin001/fragment/AccountSwitchFragment$ViewHolder;)Lcom/kaixin001/fragment/AccountSwitchFragment;
          //   269: invokevirtual 62	com/kaixin001/fragment/AccountSwitchFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
          //   272: invokespecial 176	com/kaixin001/businesslogic/LogoutAndExitProxy:<init>	(Landroid/app/Activity;)V
          //   275: astore 14
          //   277: aload 14
          //   279: iconst_3
          //   280: aconst_null
          //   281: invokevirtual 180	com/kaixin001/businesslogic/LogoutAndExitProxy:logout	(ILcom/kaixin001/businesslogic/LogoutAndExitProxy$IOnLogoutListener;)V
          //   284: aload 14
          //   286: iconst_1
          //   287: invokevirtual 184	com/kaixin001/businesslogic/LogoutAndExitProxy:exit	(Z)V
          //   290: invokestatic 101	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
          //   293: iconst_1
          //   294: invokestatic 189	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
          //   297: invokevirtual 193	com/kaixin001/model/User:setLookAround	(Ljava/lang/Boolean;)V
          //   300: aload_0
          //   301: getfield 19	com/kaixin001/fragment/AccountSwitchFragment$ViewHolder$1:this$1	Lcom/kaixin001/fragment/AccountSwitchFragment$ViewHolder;
          //   304: invokestatic 56	com/kaixin001/fragment/AccountSwitchFragment$ViewHolder:access$1	(Lcom/kaixin001/fragment/AccountSwitchFragment$ViewHolder;)Lcom/kaixin001/fragment/AccountSwitchFragment;
          //   307: invokevirtual 62	com/kaixin001/fragment/AccountSwitchFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
          //   310: iconst_0
          //   311: invokestatic 197	com/kaixin001/util/IntentUtil:returnToLogin	(Landroid/app/Activity;Z)V
          //   314: goto -62 -> 252
          //   317: astore 6
          //   319: aload 4
          //   321: astore_3
          //   322: aload 6
          //   324: invokevirtual 200	java/lang/Exception:printStackTrace	()V
          //   327: aload_3
          //   328: invokevirtual 172	com/kaixin001/db/UserDBAdapter:close	()V
          //   331: return
          //   332: astore 5
          //   334: aload_3
          //   335: invokevirtual 172	com/kaixin001/db/UserDBAdapter:close	()V
          //   338: aload 5
          //   340: athrow
          //   341: astore 5
          //   343: aload 4
          //   345: astore_3
          //   346: goto -12 -> 334
          //   349: astore 6
          //   351: aconst_null
          //   352: astore_3
          //   353: goto -31 -> 322
          //
          // Exception table:
          //   from	to	target	type
          //   26	104	317	java/lang/Exception
          //   112	118	317	java/lang/Exception
          //   121	139	317	java/lang/Exception
          //   144	191	317	java/lang/Exception
          //   191	252	317	java/lang/Exception
          //   258	314	317	java/lang/Exception
          //   13	26	332	finally
          //   322	327	332	finally
          //   26	104	341	finally
          //   112	118	341	finally
          //   121	139	341	finally
          //   144	191	341	finally
          //   191	252	341	finally
          //   258	314	341	finally
          //   13	26	349	java/lang/Exception
        }
      });
    }

    public void showView(Cursor paramCursor)
    {
      int i = paramCursor.getInt(paramCursor.getColumnIndex("islogin"));
      String str = paramCursor.getString(paramCursor.getColumnIndex("name"));
      if (i == 1)
      {
        this.ivLoginCheckBtn.setImageResource(2130838705);
        this.tvAccountName.setTextColor(AccountSwitchFragment.this.getResources().getColorStateList(2130839398));
        this.tvAccountName.setText(str);
        this.ivDelete.setTag(Integer.valueOf(paramCursor.getInt(paramCursor.getColumnIndex("uid"))));
        if (!AccountSwitchFragment.this.isEditDelete)
          break label145;
        this.ivDelete.setVisibility(0);
      }
      while (true)
      {
        if (AccountSwitchFragment.this.isSwitchAccount)
          this.ivLoginCheckBtn.setImageResource(2130838706);
        return;
        this.ivLoginCheckBtn.setImageResource(2130838706);
        break;
        label145: this.ivDelete.setVisibility(8);
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.AccountSwitchFragment
 * JD-Core Version:    0.6.0
 */