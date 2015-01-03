package com.kaixin001.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.ContactsEngine;
import com.kaixin001.engine.ContactsRelateEngine;
import com.kaixin001.item.ContactsItem;
import com.kaixin001.model.ContactsModel;
import com.kaixin001.model.FriendsModel;
import com.kaixin001.model.FriendsModel.Friend;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import java.util.Iterator;

public class ContactsPrepareFragment extends BaseFragment
  implements View.OnClickListener
{
  private Button btnImport;
  private ArrayList<FriendsModel.Friend> friendslist = new ArrayList();
  private MatchFriendTask mFriendTask = null;
  private ProgressDialog mProgressDialog;
  private TextView tvImport;

  private String compareName(String paramString, ArrayList<ContactsItem> paramArrayList)
  {
    String str = "";
    int i = 0;
    for (int j = 0; ; j++)
    {
      if (j >= paramArrayList.size())
      {
        if (i > 1)
          str = "";
        return str;
      }
      ContactsItem localContactsItem = (ContactsItem)paramArrayList.get(j);
      if (!paramString.equals(localContactsItem.getName()))
        continue;
      str = localContactsItem.getContactsId();
      i++;
    }
  }

  private void getAllFriends()
  {
    while (true)
    {
      try
      {
        if (this.friendslist == null)
          return;
        this.friendslist.clear();
        ArrayList localArrayList = FriendsModel.getInstance().getFriends();
        int i = 0;
        if (localArrayList == null)
          break label79;
        i = localArrayList.size();
        break label79;
        if (j < i)
        {
          FriendsModel.Friend localFriend = (FriendsModel.Friend)localArrayList.get(j);
          this.friendslist.add(localFriend);
          j++;
          continue;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("ContactsPrepareActivity", "getAllFriends", localException);
      }
      return;
      label79: int j = 0;
    }
  }

  private String getFormatName(String paramString)
  {
    return paramString.replaceAll("(\\(.*\\))|(\\[.*\\])|([a-zA-Z])|(\\@)", "");
  }

  // ERROR //
  private ArrayList<com.kaixin001.model.ContactsItemInfo> getRelatedItems(ArrayList<FriendsModel.Friend> paramArrayList)
  {
    // Byte code:
    //   0: new 25	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 26	java/util/ArrayList:<init>	()V
    //   7: astore_2
    //   8: invokestatic 132	com/kaixin001/engine/ContactsRelateEngine:getInstance	()Lcom/kaixin001/engine/ContactsRelateEngine;
    //   11: aload_0
    //   12: invokevirtual 136	com/kaixin001/fragment/ContactsPrepareFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
    //   15: invokevirtual 140	com/kaixin001/engine/ContactsRelateEngine:loadContactsRelatedObjs	(Landroid/content/Context;)Ljava/util/ArrayList;
    //   18: astore_3
    //   19: aload_3
    //   20: invokevirtual 73	java/util/ArrayList:size	()I
    //   23: istore 5
    //   25: iconst_0
    //   26: istore 6
    //   28: aconst_null
    //   29: astore 7
    //   31: iload 6
    //   33: iload 5
    //   35: if_icmplt +8 -> 43
    //   38: aload 7
    //   40: pop
    //   41: aload_2
    //   42: areturn
    //   43: aload_3
    //   44: iload 6
    //   46: invokevirtual 77	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   49: checkcast 142	com/kaixin001/model/ContactsRelatedModel
    //   52: astore 9
    //   54: aload 9
    //   56: invokevirtual 145	com/kaixin001/model/ContactsRelatedModel:getFuid	()Ljava/lang/String;
    //   59: astore 10
    //   61: new 147	com/kaixin001/model/ContactsItemInfo
    //   64: dup
    //   65: invokespecial 148	com/kaixin001/model/ContactsItemInfo:<init>	()V
    //   68: astore 11
    //   70: aload_1
    //   71: invokevirtual 73	java/util/ArrayList:size	()I
    //   74: istore 12
    //   76: iconst_0
    //   77: istore 13
    //   79: goto +110 -> 189
    //   82: aload 11
    //   84: iconst_0
    //   85: invokevirtual 152	com/kaixin001/model/ContactsItemInfo:setAdd	(Z)V
    //   88: aload 11
    //   90: aload 9
    //   92: invokevirtual 155	com/kaixin001/model/ContactsRelatedModel:getCname	()Ljava/lang/String;
    //   95: invokevirtual 159	com/kaixin001/model/ContactsItemInfo:setCname	(Ljava/lang/String;)V
    //   98: aload 11
    //   100: aload 9
    //   102: invokevirtual 163	com/kaixin001/model/ContactsRelatedModel:getCid	()Ljava/lang/Long;
    //   105: invokevirtual 169	java/lang/Long:longValue	()J
    //   108: invokevirtual 173	com/kaixin001/model/ContactsItemInfo:setCid	(J)V
    //   111: aload_2
    //   112: aload 11
    //   114: invokevirtual 112	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   117: pop
    //   118: iinc 6 1
    //   121: aload 11
    //   123: astore 7
    //   125: goto -94 -> 31
    //   128: aload_1
    //   129: iload 13
    //   131: invokevirtual 77	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   134: checkcast 109	com/kaixin001/model/FriendsModel$Friend
    //   137: astore 15
    //   139: aload 10
    //   141: aload 15
    //   143: invokevirtual 174	com/kaixin001/model/FriendsModel$Friend:getFuid	()Ljava/lang/String;
    //   146: invokevirtual 89	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   149: ifeq +26 -> 175
    //   152: aload 11
    //   154: aload 15
    //   156: invokevirtual 178	com/kaixin001/model/ContactsItemInfo:setFriend	(Lcom/kaixin001/model/FriendsModel$Friend;)V
    //   159: goto -77 -> 82
    //   162: astore 4
    //   164: ldc 114
    //   166: ldc 179
    //   168: aload 4
    //   170: invokestatic 121	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   173: aload_2
    //   174: areturn
    //   175: iinc 13 1
    //   178: goto +11 -> 189
    //   181: astore 4
    //   183: aload 7
    //   185: pop
    //   186: goto -22 -> 164
    //   189: iload 13
    //   191: iload 12
    //   193: if_icmplt -65 -> 128
    //   196: goto -114 -> 82
    //
    // Exception table:
    //   from	to	target	type
    //   19	25	162	java/lang/Exception
    //   70	76	162	java/lang/Exception
    //   82	118	162	java/lang/Exception
    //   128	159	162	java/lang/Exception
    //   43	70	181	java/lang/Exception
  }

  // ERROR //
  private ArrayList<com.kaixin001.model.ContactsItemInfo> getRepeatedItem(ArrayList<FriendsModel.Friend> paramArrayList)
  {
    // Byte code:
    //   0: new 25	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 26	java/util/ArrayList:<init>	()V
    //   7: astore_2
    //   8: new 25	java/util/ArrayList
    //   11: dup
    //   12: invokespecial 26	java/util/ArrayList:<init>	()V
    //   15: astore_3
    //   16: new 25	java/util/ArrayList
    //   19: dup
    //   20: invokespecial 26	java/util/ArrayList:<init>	()V
    //   23: astore 4
    //   25: aload_1
    //   26: invokevirtual 73	java/util/ArrayList:size	()I
    //   29: istore 6
    //   31: aconst_null
    //   32: astore 7
    //   34: iconst_0
    //   35: istore 8
    //   37: goto +219 -> 256
    //   40: aload 4
    //   42: iload 8
    //   44: invokestatic 185	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   47: invokevirtual 188	java/util/ArrayList:contains	(Ljava/lang/Object;)Z
    //   50: ifeq +6 -> 56
    //   53: goto +214 -> 267
    //   56: aload_3
    //   57: invokevirtual 97	java/util/ArrayList:clear	()V
    //   60: aload_1
    //   61: iload 8
    //   63: invokevirtual 77	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   66: checkcast 109	com/kaixin001/model/FriendsModel$Friend
    //   69: astore 9
    //   71: aload 9
    //   73: invokevirtual 191	com/kaixin001/model/FriendsModel$Friend:getFname	()Ljava/lang/String;
    //   76: astore 10
    //   78: aload_3
    //   79: aload 9
    //   81: invokevirtual 112	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   84: pop
    //   85: iload 8
    //   87: iconst_1
    //   88: iadd
    //   89: istore 12
    //   91: iload 12
    //   93: iload 6
    //   95: if_icmplt +25 -> 120
    //   98: aload_3
    //   99: invokevirtual 73	java/util/ArrayList:size	()I
    //   102: istore 13
    //   104: iload 13
    //   106: iconst_1
    //   107: if_icmple +160 -> 267
    //   110: aload 7
    //   112: astore 14
    //   114: iconst_0
    //   115: istore 15
    //   117: goto +156 -> 273
    //   120: aload_1
    //   121: iload 12
    //   123: invokevirtual 77	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   126: checkcast 109	com/kaixin001/model/FriendsModel$Friend
    //   129: astore 19
    //   131: aload 10
    //   133: aload 19
    //   135: invokevirtual 191	com/kaixin001/model/FriendsModel$Friend:getFname	()Ljava/lang/String;
    //   138: invokevirtual 89	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   141: ifeq +21 -> 162
    //   144: aload_3
    //   145: aload 19
    //   147: invokevirtual 112	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   150: pop
    //   151: aload 4
    //   153: iload 12
    //   155: invokestatic 185	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   158: invokevirtual 112	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   161: pop
    //   162: iinc 12 1
    //   165: goto -74 -> 91
    //   168: new 147	com/kaixin001/model/ContactsItemInfo
    //   171: dup
    //   172: invokespecial 148	com/kaixin001/model/ContactsItemInfo:<init>	()V
    //   175: astore 16
    //   177: aload 16
    //   179: iconst_0
    //   180: invokevirtual 152	com/kaixin001/model/ContactsItemInfo:setAdd	(Z)V
    //   183: aload 16
    //   185: aload_3
    //   186: iload 15
    //   188: invokevirtual 77	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   191: checkcast 109	com/kaixin001/model/FriendsModel$Friend
    //   194: invokevirtual 178	com/kaixin001/model/ContactsItemInfo:setFriend	(Lcom/kaixin001/model/FriendsModel$Friend;)V
    //   197: aload 16
    //   199: ldc 69
    //   201: invokevirtual 159	com/kaixin001/model/ContactsItemInfo:setCname	(Ljava/lang/String;)V
    //   204: aload 16
    //   206: iconst_1
    //   207: invokevirtual 194	com/kaixin001/model/ContactsItemInfo:setLinkDialog	(Z)V
    //   210: aload 16
    //   212: ldc2_w 195
    //   215: invokevirtual 173	com/kaixin001/model/ContactsItemInfo:setCid	(J)V
    //   218: aload_2
    //   219: aload 16
    //   221: invokevirtual 112	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   224: pop
    //   225: iinc 15 1
    //   228: aload 16
    //   230: astore 14
    //   232: goto +41 -> 273
    //   235: astore 5
    //   237: ldc 114
    //   239: ldc 197
    //   241: aload 5
    //   243: invokestatic 121	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   246: aload_2
    //   247: areturn
    //   248: astore 5
    //   250: aload 14
    //   252: pop
    //   253: goto -16 -> 237
    //   256: iload 8
    //   258: iload 6
    //   260: iconst_1
    //   261: isub
    //   262: if_icmplt -222 -> 40
    //   265: aload_2
    //   266: areturn
    //   267: iinc 8 1
    //   270: goto -14 -> 256
    //   273: iload 15
    //   275: iload 13
    //   277: if_icmplt -109 -> 168
    //   280: aload 14
    //   282: astore 7
    //   284: goto -17 -> 267
    //
    // Exception table:
    //   from	to	target	type
    //   25	31	235	java/lang/Exception
    //   40	53	235	java/lang/Exception
    //   56	85	235	java/lang/Exception
    //   98	104	235	java/lang/Exception
    //   120	162	235	java/lang/Exception
    //   177	225	235	java/lang/Exception
    //   168	177	248	java/lang/Exception
  }

  private void initTitle(View paramView)
  {
    paramView.findViewById(2131362919).setVisibility(8);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    localTextView.setVisibility(0);
    localTextView.setText(2131427795);
    ((ImageView)paramView.findViewById(2131362914)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (ContactsPrepareFragment.this.isMenuListVisibleInMain())
        {
          ContactsPrepareFragment.this.showSubActivityInMain();
          return;
        }
        ContactsPrepareFragment.this.showMenuListInMain();
      }
    });
    ((ImageView)paramView.findViewById(2131362928)).setVisibility(8);
  }

  private void matchFriends()
  {
    this.mProgressDialog = ProgressDialog.show(getActivity(), "", getResources().getText(2131427796), true, false, null);
    this.mFriendTask = new MatchFriendTask(null);
    this.mFriendTask.execute(new String[0]);
  }

  private void removeRecordNoInfriends(ArrayList<FriendsModel.Friend> paramArrayList)
  {
    while (true)
    {
      String str;
      int j;
      int k;
      try
      {
        ArrayList localArrayList = ContactsRelateEngine.getInstance().loadContactsFuids(getActivity());
        int i = paramArrayList.size();
        Iterator localIterator = localArrayList.iterator();
        if (!localIterator.hasNext())
          return;
        str = (String)localIterator.next();
        j = 0;
        k = 0;
        if (k >= i)
        {
          if (j != 0)
            continue;
          ContactsRelateEngine.getInstance().deleteContactsData(getActivity(), str);
          continue;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("ContactsPrepareActivity", "removeRecordNoInfriends", localException);
        return;
      }
      boolean bool = str.equals(((FriendsModel.Friend)paramArrayList.get(k)).getFuid());
      if (bool)
        j = 1;
      k++;
    }
  }

  public void onClick(View paramView)
  {
    matchFriends();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903090, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if ((this.mFriendTask != null) && (this.mFriendTask.getStatus() != AsyncTask.Status.FINISHED))
      this.mFriendTask.cancel(true);
    ContactsEngine.clear();
    ContactsRelateEngine.clear();
    super.onDestroy();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    initTitle(paramView);
    this.tvImport = ((TextView)paramView.findViewById(2131362153));
    this.tvImport.setText(2131427798);
    this.btnImport = ((Button)paramView.findViewById(2131362154));
    this.btnImport.setOnClickListener(this);
  }

  private class MatchFriendTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private MatchFriendTask()
    {
      super();
    }

    // ERROR //
    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      // Byte code:
      //   0: invokestatic 29	com/kaixin001/model/FriendsModel:getInstance	()Lcom/kaixin001/model/FriendsModel;
      //   3: invokevirtual 33	com/kaixin001/model/FriendsModel:getFriends	()Ljava/util/ArrayList;
      //   6: invokevirtual 39	java/util/ArrayList:size	()I
      //   9: ifne +58 -> 67
      //   12: invokestatic 44	com/kaixin001/engine/FriendsEngine:getInstance	()Lcom/kaixin001/engine/FriendsEngine;
      //   15: aload_0
      //   16: getfield 11	com/kaixin001/fragment/ContactsPrepareFragment$MatchFriendTask:this$0	Lcom/kaixin001/fragment/ContactsPrepareFragment;
      //   19: invokevirtual 50	com/kaixin001/fragment/ContactsPrepareFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
      //   22: invokestatic 55	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
      //   25: invokevirtual 59	com/kaixin001/model/User:getUID	()Ljava/lang/String;
      //   28: invokevirtual 63	com/kaixin001/engine/FriendsEngine:loadFriendsCache	(Landroid/content/Context;Ljava/lang/String;)Z
      //   31: ifne +36 -> 67
      //   34: invokestatic 44	com/kaixin001/engine/FriendsEngine:getInstance	()Lcom/kaixin001/engine/FriendsEngine;
      //   37: aload_0
      //   38: getfield 11	com/kaixin001/fragment/ContactsPrepareFragment$MatchFriendTask:this$0	Lcom/kaixin001/fragment/ContactsPrepareFragment;
      //   41: invokevirtual 50	com/kaixin001/fragment/ContactsPrepareFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
      //   44: invokevirtual 69	android/support/v4/app/FragmentActivity:getApplicationContext	()Landroid/content/Context;
      //   47: iconst_1
      //   48: invokevirtual 73	com/kaixin001/engine/FriendsEngine:getFriendsList	(Landroid/content/Context;I)Z
      //   51: ifne +16 -> 67
      //   54: iconst_0
      //   55: invokestatic 79	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   58: astore 40
      //   60: aload 40
      //   62: areturn
      //   63: astore 39
      //   65: aconst_null
      //   66: areturn
      //   67: aload_0
      //   68: getfield 11	com/kaixin001/fragment/ContactsPrepareFragment$MatchFriendTask:this$0	Lcom/kaixin001/fragment/ContactsPrepareFragment;
      //   71: invokestatic 82	com/kaixin001/fragment/ContactsPrepareFragment:access$0	(Lcom/kaixin001/fragment/ContactsPrepareFragment;)V
      //   74: aload_0
      //   75: iconst_0
      //   76: anewarray 84	java/lang/Void
      //   79: invokevirtual 88	com/kaixin001/fragment/ContactsPrepareFragment$MatchFriendTask:publishProgress	([Ljava/lang/Object;)V
      //   82: invokestatic 93	com/kaixin001/engine/ContactsEngine:getInstance	()Lcom/kaixin001/engine/ContactsEngine;
      //   85: aload_0
      //   86: getfield 11	com/kaixin001/fragment/ContactsPrepareFragment$MatchFriendTask:this$0	Lcom/kaixin001/fragment/ContactsPrepareFragment;
      //   89: invokevirtual 50	com/kaixin001/fragment/ContactsPrepareFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
      //   92: invokevirtual 97	android/support/v4/app/FragmentActivity:getContentResolver	()Landroid/content/ContentResolver;
      //   95: invokevirtual 101	com/kaixin001/engine/ContactsEngine:selectContentsNames	(Landroid/content/ContentResolver;)Ljava/util/ArrayList;
      //   98: astore_2
      //   99: new 35	java/util/ArrayList
      //   102: dup
      //   103: invokespecial 104	java/util/ArrayList:<init>	()V
      //   106: astore_3
      //   107: new 35	java/util/ArrayList
      //   110: dup
      //   111: invokespecial 104	java/util/ArrayList:<init>	()V
      //   114: astore 4
      //   116: aload_0
      //   117: getfield 11	com/kaixin001/fragment/ContactsPrepareFragment$MatchFriendTask:this$0	Lcom/kaixin001/fragment/ContactsPrepareFragment;
      //   120: aload_0
      //   121: getfield 11	com/kaixin001/fragment/ContactsPrepareFragment$MatchFriendTask:this$0	Lcom/kaixin001/fragment/ContactsPrepareFragment;
      //   124: invokestatic 108	com/kaixin001/fragment/ContactsPrepareFragment:access$1	(Lcom/kaixin001/fragment/ContactsPrepareFragment;)Ljava/util/ArrayList;
      //   127: invokestatic 112	com/kaixin001/fragment/ContactsPrepareFragment:access$2	(Lcom/kaixin001/fragment/ContactsPrepareFragment;Ljava/util/ArrayList;)Ljava/util/ArrayList;
      //   130: astore 6
      //   132: aload 6
      //   134: invokevirtual 39	java/util/ArrayList:size	()I
      //   137: istore 7
      //   139: iconst_0
      //   140: istore 8
      //   142: iload 8
      //   144: iload 7
      //   146: if_icmplt +149 -> 295
      //   149: aload_0
      //   150: getfield 11	com/kaixin001/fragment/ContactsPrepareFragment$MatchFriendTask:this$0	Lcom/kaixin001/fragment/ContactsPrepareFragment;
      //   153: aload_0
      //   154: getfield 11	com/kaixin001/fragment/ContactsPrepareFragment$MatchFriendTask:this$0	Lcom/kaixin001/fragment/ContactsPrepareFragment;
      //   157: invokestatic 108	com/kaixin001/fragment/ContactsPrepareFragment:access$1	(Lcom/kaixin001/fragment/ContactsPrepareFragment;)Ljava/util/ArrayList;
      //   160: invokestatic 116	com/kaixin001/fragment/ContactsPrepareFragment:access$3	(Lcom/kaixin001/fragment/ContactsPrepareFragment;Ljava/util/ArrayList;)V
      //   163: invokestatic 93	com/kaixin001/engine/ContactsEngine:getInstance	()Lcom/kaixin001/engine/ContactsEngine;
      //   166: aload_0
      //   167: getfield 11	com/kaixin001/fragment/ContactsPrepareFragment$MatchFriendTask:this$0	Lcom/kaixin001/fragment/ContactsPrepareFragment;
      //   170: invokevirtual 50	com/kaixin001/fragment/ContactsPrepareFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
      //   173: invokevirtual 97	android/support/v4/app/FragmentActivity:getContentResolver	()Landroid/content/ContentResolver;
      //   176: invokevirtual 119	com/kaixin001/engine/ContactsEngine:selectContactsIds	(Landroid/content/ContentResolver;)Ljava/util/ArrayList;
      //   179: astore 9
      //   181: invokestatic 124	com/kaixin001/engine/ContactsRelateEngine:getInstance	()Lcom/kaixin001/engine/ContactsRelateEngine;
      //   184: aload_0
      //   185: getfield 11	com/kaixin001/fragment/ContactsPrepareFragment$MatchFriendTask:this$0	Lcom/kaixin001/fragment/ContactsPrepareFragment;
      //   188: invokevirtual 50	com/kaixin001/fragment/ContactsPrepareFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
      //   191: invokevirtual 128	com/kaixin001/engine/ContactsRelateEngine:loadContactsCids	(Landroid/content/Context;)Ljava/util/ArrayList;
      //   194: astore 10
      //   196: aload 10
      //   198: invokevirtual 132	java/util/ArrayList:iterator	()Ljava/util/Iterator;
      //   201: astore 11
      //   203: aload 11
      //   205: invokeinterface 138 1 0
      //   210: ifne +127 -> 337
      //   213: aload_0
      //   214: getfield 11	com/kaixin001/fragment/ContactsPrepareFragment$MatchFriendTask:this$0	Lcom/kaixin001/fragment/ContactsPrepareFragment;
      //   217: aload_0
      //   218: getfield 11	com/kaixin001/fragment/ContactsPrepareFragment$MatchFriendTask:this$0	Lcom/kaixin001/fragment/ContactsPrepareFragment;
      //   221: invokestatic 108	com/kaixin001/fragment/ContactsPrepareFragment:access$1	(Lcom/kaixin001/fragment/ContactsPrepareFragment;)Ljava/util/ArrayList;
      //   224: invokestatic 141	com/kaixin001/fragment/ContactsPrepareFragment:access$4	(Lcom/kaixin001/fragment/ContactsPrepareFragment;Ljava/util/ArrayList;)Ljava/util/ArrayList;
      //   227: astore 14
      //   229: aload 14
      //   231: invokevirtual 39	java/util/ArrayList:size	()I
      //   234: istore 15
      //   236: iconst_0
      //   237: istore 16
      //   239: iload 16
      //   241: iload 15
      //   243: if_icmplt +154 -> 397
      //   246: aload_0
      //   247: getfield 11	com/kaixin001/fragment/ContactsPrepareFragment$MatchFriendTask:this$0	Lcom/kaixin001/fragment/ContactsPrepareFragment;
      //   250: invokestatic 108	com/kaixin001/fragment/ContactsPrepareFragment:access$1	(Lcom/kaixin001/fragment/ContactsPrepareFragment;)Ljava/util/ArrayList;
      //   253: invokevirtual 132	java/util/ArrayList:iterator	()Ljava/util/Iterator;
      //   256: astore 17
      //   258: aconst_null
      //   259: astore 18
      //   261: aload 17
      //   263: invokeinterface 138 1 0
      //   268: ifne +195 -> 463
      //   271: invokestatic 146	com/kaixin001/model/ContactsModel:getInstance	()Lcom/kaixin001/model/ContactsModel;
      //   274: aload_3
      //   275: invokevirtual 150	com/kaixin001/model/ContactsModel:setLinkedFriendList	(Ljava/util/ArrayList;)V
      //   278: invokestatic 146	com/kaixin001/model/ContactsModel:getInstance	()Lcom/kaixin001/model/ContactsModel;
      //   281: aload 4
      //   283: invokevirtual 153	com/kaixin001/model/ContactsModel:setUnLinkedFriendList	(Ljava/util/ArrayList;)V
      //   286: iconst_1
      //   287: invokestatic 79	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   290: astore 32
      //   292: aload 32
      //   294: areturn
      //   295: aload 6
      //   297: iload 8
      //   299: invokevirtual 157	java/util/ArrayList:get	(I)Ljava/lang/Object;
      //   302: checkcast 159	com/kaixin001/model/ContactsItemInfo
      //   305: astore 36
      //   307: aload 4
      //   309: aload 36
      //   311: invokevirtual 163	java/util/ArrayList:add	(Ljava/lang/Object;)Z
      //   314: pop
      //   315: aload_0
      //   316: getfield 11	com/kaixin001/fragment/ContactsPrepareFragment$MatchFriendTask:this$0	Lcom/kaixin001/fragment/ContactsPrepareFragment;
      //   319: invokestatic 108	com/kaixin001/fragment/ContactsPrepareFragment:access$1	(Lcom/kaixin001/fragment/ContactsPrepareFragment;)Ljava/util/ArrayList;
      //   322: aload 36
      //   324: invokevirtual 167	com/kaixin001/model/ContactsItemInfo:getFriend	()Lcom/kaixin001/model/FriendsModel$Friend;
      //   327: invokevirtual 170	java/util/ArrayList:remove	(Ljava/lang/Object;)Z
      //   330: pop
      //   331: iinc 8 1
      //   334: goto -192 -> 142
      //   337: aload 11
      //   339: invokeinterface 174 1 0
      //   344: checkcast 176	java/lang/Long
      //   347: astore 12
      //   349: aload 9
      //   351: aload 12
      //   353: invokevirtual 179	java/util/ArrayList:contains	(Ljava/lang/Object;)Z
      //   356: ifne -153 -> 203
      //   359: invokestatic 124	com/kaixin001/engine/ContactsRelateEngine:getInstance	()Lcom/kaixin001/engine/ContactsRelateEngine;
      //   362: aload_0
      //   363: getfield 11	com/kaixin001/fragment/ContactsPrepareFragment$MatchFriendTask:this$0	Lcom/kaixin001/fragment/ContactsPrepareFragment;
      //   366: invokevirtual 50	com/kaixin001/fragment/ContactsPrepareFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
      //   369: aload 12
      //   371: invokevirtual 183	java/lang/Long:longValue	()J
      //   374: invokevirtual 187	com/kaixin001/engine/ContactsRelateEngine:deleteContactsDataByCid	(Landroid/content/Context;J)Z
      //   377: pop
      //   378: goto -175 -> 203
      //   381: astore 5
      //   383: ldc 189
      //   385: ldc 191
      //   387: aload 5
      //   389: invokestatic 197	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
      //   392: iconst_0
      //   393: invokestatic 79	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   396: areturn
      //   397: aload 14
      //   399: iload 16
      //   401: invokevirtual 157	java/util/ArrayList:get	(I)Ljava/lang/Object;
      //   404: checkcast 159	com/kaixin001/model/ContactsItemInfo
      //   407: astore 33
      //   409: invokestatic 93	com/kaixin001/engine/ContactsEngine:getInstance	()Lcom/kaixin001/engine/ContactsEngine;
      //   412: aload_0
      //   413: getfield 11	com/kaixin001/fragment/ContactsPrepareFragment$MatchFriendTask:this$0	Lcom/kaixin001/fragment/ContactsPrepareFragment;
      //   416: invokevirtual 50	com/kaixin001/fragment/ContactsPrepareFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
      //   419: invokevirtual 97	android/support/v4/app/FragmentActivity:getContentResolver	()Landroid/content/ContentResolver;
      //   422: aload 33
      //   424: invokevirtual 200	com/kaixin001/model/ContactsItemInfo:getCid	()J
      //   427: invokevirtual 204	com/kaixin001/engine/ContactsEngine:getNumsByCid	(Landroid/content/ContentResolver;J)I
      //   430: iconst_1
      //   431: if_icmpne +26 -> 457
      //   434: aload_3
      //   435: aload 33
      //   437: invokevirtual 163	java/util/ArrayList:add	(Ljava/lang/Object;)Z
      //   440: pop
      //   441: aload_0
      //   442: getfield 11	com/kaixin001/fragment/ContactsPrepareFragment$MatchFriendTask:this$0	Lcom/kaixin001/fragment/ContactsPrepareFragment;
      //   445: invokestatic 108	com/kaixin001/fragment/ContactsPrepareFragment:access$1	(Lcom/kaixin001/fragment/ContactsPrepareFragment;)Ljava/util/ArrayList;
      //   448: aload 33
      //   450: invokevirtual 167	com/kaixin001/model/ContactsItemInfo:getFriend	()Lcom/kaixin001/model/FriendsModel$Friend;
      //   453: invokevirtual 170	java/util/ArrayList:remove	(Ljava/lang/Object;)Z
      //   456: pop
      //   457: iinc 16 1
      //   460: goto -221 -> 239
      //   463: aload 17
      //   465: invokeinterface 174 1 0
      //   470: checkcast 206	com/kaixin001/model/FriendsModel$Friend
      //   473: astore 20
      //   475: aload 20
      //   477: invokevirtual 209	com/kaixin001/model/FriendsModel$Friend:getFname	()Ljava/lang/String;
      //   480: astore 21
      //   482: aload_0
      //   483: getfield 11	com/kaixin001/fragment/ContactsPrepareFragment$MatchFriendTask:this$0	Lcom/kaixin001/fragment/ContactsPrepareFragment;
      //   486: aload 21
      //   488: invokestatic 213	com/kaixin001/fragment/ContactsPrepareFragment:access$5	(Lcom/kaixin001/fragment/ContactsPrepareFragment;Ljava/lang/String;)Ljava/lang/String;
      //   491: astore 22
      //   493: aload_0
      //   494: getfield 11	com/kaixin001/fragment/ContactsPrepareFragment$MatchFriendTask:this$0	Lcom/kaixin001/fragment/ContactsPrepareFragment;
      //   497: aload 22
      //   499: aload_2
      //   500: invokestatic 217	com/kaixin001/fragment/ContactsPrepareFragment:access$6	(Lcom/kaixin001/fragment/ContactsPrepareFragment;Ljava/lang/String;Ljava/util/ArrayList;)Ljava/lang/String;
      //   503: astore 23
      //   505: aload 23
      //   507: ldc 219
      //   509: invokevirtual 224	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   512: ifne +179 -> 691
      //   515: iconst_0
      //   516: istore 24
      //   518: iconst_0
      //   519: istore 25
      //   521: iload 25
      //   523: aload 10
      //   525: invokevirtual 39	java/util/ArrayList:size	()I
      //   528: if_icmplt +73 -> 601
      //   531: iload 24
      //   533: aload 10
      //   535: invokevirtual 39	java/util/ArrayList:size	()I
      //   538: if_icmplt +95 -> 633
      //   541: new 159	com/kaixin001/model/ContactsItemInfo
      //   544: dup
      //   545: invokespecial 225	com/kaixin001/model/ContactsItemInfo:<init>	()V
      //   548: astore 26
      //   550: aload 26
      //   552: iconst_0
      //   553: invokevirtual 229	com/kaixin001/model/ContactsItemInfo:setAdd	(Z)V
      //   556: aload 26
      //   558: aload 20
      //   560: invokevirtual 233	com/kaixin001/model/ContactsItemInfo:setFriend	(Lcom/kaixin001/model/FriendsModel$Friend;)V
      //   563: aload 26
      //   565: aload 21
      //   567: invokevirtual 237	com/kaixin001/model/ContactsItemInfo:setCname	(Ljava/lang/String;)V
      //   570: aload 26
      //   572: iconst_0
      //   573: invokevirtual 240	com/kaixin001/model/ContactsItemInfo:setLinkDialog	(Z)V
      //   576: aload 26
      //   578: aload 23
      //   580: invokestatic 244	java/lang/Integer:parseInt	(Ljava/lang/String;)I
      //   583: i2l
      //   584: invokevirtual 248	com/kaixin001/model/ContactsItemInfo:setCid	(J)V
      //   587: aload_3
      //   588: aload 26
      //   590: invokevirtual 163	java/util/ArrayList:add	(Ljava/lang/Object;)Z
      //   593: pop
      //   594: aload 26
      //   596: astore 18
      //   598: goto -337 -> 261
      //   601: aload 10
      //   603: iload 25
      //   605: invokevirtual 157	java/util/ArrayList:get	(I)Ljava/lang/Object;
      //   608: checkcast 176	java/lang/Long
      //   611: invokevirtual 183	java/lang/Long:longValue	()J
      //   614: aload 23
      //   616: invokestatic 244	java/lang/Integer:parseInt	(Ljava/lang/String;)I
      //   619: i2l
      //   620: lcmp
      //   621: ifeq -90 -> 531
      //   624: iinc 24 1
      //   627: iinc 25 1
      //   630: goto -109 -> 521
      //   633: new 159	com/kaixin001/model/ContactsItemInfo
      //   636: dup
      //   637: invokespecial 225	com/kaixin001/model/ContactsItemInfo:<init>	()V
      //   640: astore 28
      //   642: aload 28
      //   644: iconst_0
      //   645: invokevirtual 229	com/kaixin001/model/ContactsItemInfo:setAdd	(Z)V
      //   648: aload 28
      //   650: aload 20
      //   652: invokevirtual 233	com/kaixin001/model/ContactsItemInfo:setFriend	(Lcom/kaixin001/model/FriendsModel$Friend;)V
      //   655: aload 28
      //   657: ldc 219
      //   659: invokevirtual 237	com/kaixin001/model/ContactsItemInfo:setCname	(Ljava/lang/String;)V
      //   662: aload 28
      //   664: ldc2_w 249
      //   667: invokevirtual 248	com/kaixin001/model/ContactsItemInfo:setCid	(J)V
      //   670: aload 28
      //   672: iconst_1
      //   673: invokevirtual 240	com/kaixin001/model/ContactsItemInfo:setLinkDialog	(Z)V
      //   676: aload 4
      //   678: aload 28
      //   680: invokevirtual 163	java/util/ArrayList:add	(Ljava/lang/Object;)Z
      //   683: pop
      //   684: aload 28
      //   686: astore 18
      //   688: goto -427 -> 261
      //   691: new 159	com/kaixin001/model/ContactsItemInfo
      //   694: dup
      //   695: invokespecial 225	com/kaixin001/model/ContactsItemInfo:<init>	()V
      //   698: astore 30
      //   700: aload 30
      //   702: iconst_0
      //   703: invokevirtual 229	com/kaixin001/model/ContactsItemInfo:setAdd	(Z)V
      //   706: aload 30
      //   708: aload 20
      //   710: invokevirtual 233	com/kaixin001/model/ContactsItemInfo:setFriend	(Lcom/kaixin001/model/FriendsModel$Friend;)V
      //   713: aload 30
      //   715: ldc 219
      //   717: invokevirtual 237	com/kaixin001/model/ContactsItemInfo:setCname	(Ljava/lang/String;)V
      //   720: aload 30
      //   722: ldc2_w 249
      //   725: invokevirtual 248	com/kaixin001/model/ContactsItemInfo:setCid	(J)V
      //   728: aload 30
      //   730: iconst_1
      //   731: invokevirtual 240	com/kaixin001/model/ContactsItemInfo:setLinkDialog	(Z)V
      //   734: aload 4
      //   736: aload 30
      //   738: invokevirtual 163	java/util/ArrayList:add	(Ljava/lang/Object;)Z
      //   741: pop
      //   742: aload 30
      //   744: astore 18
      //   746: goto -485 -> 261
      //   749: astore 5
      //   751: aload 18
      //   753: pop
      //   754: goto -371 -> 383
      //
      // Exception table:
      //   from	to	target	type
      //   12	60	63	com/kaixin001/engine/SecurityErrorException
      //   99	139	381	java/lang/Exception
      //   149	203	381	java/lang/Exception
      //   203	236	381	java/lang/Exception
      //   246	258	381	java/lang/Exception
      //   295	331	381	java/lang/Exception
      //   337	378	381	java/lang/Exception
      //   397	457	381	java/lang/Exception
      //   550	594	381	java/lang/Exception
      //   642	684	381	java/lang/Exception
      //   700	742	381	java/lang/Exception
      //   261	292	749	java/lang/Exception
      //   463	515	749	java/lang/Exception
      //   521	531	749	java/lang/Exception
      //   531	550	749	java/lang/Exception
      //   601	624	749	java/lang/Exception
      //   633	642	749	java/lang/Exception
      //   691	700	749	java/lang/Exception
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if (paramInteger == null)
        return;
      if (ContactsPrepareFragment.this.mProgressDialog != null)
        ContactsPrepareFragment.this.mProgressDialog.dismiss();
      if (paramInteger.intValue() == 1)
      {
        Intent localIntent = new Intent(ContactsPrepareFragment.this.getActivity(), ContactsFragment.class);
        ContactsPrepareFragment.this.startFragment(localIntent, true, 1);
        return;
      }
      ContactsModel.getInstance().clear();
      Toast.makeText(ContactsPrepareFragment.this.getActivity(), 2131427799, 0).show();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
      if (ContactsPrepareFragment.this.mProgressDialog != null)
        ContactsPrepareFragment.this.mProgressDialog.setMessage(ContactsPrepareFragment.this.getText(2131427797));
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.ContactsPrepareFragment
 * JD-Core Version:    0.6.0
 */