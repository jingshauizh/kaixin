package com.kaixin001.fragment;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.kaixin001.engine.ContactsEngine;
import com.kaixin001.engine.ContactsRelateEngine;
import com.kaixin001.engine.FriendsInfoEngine;
import com.kaixin001.model.ContactsInsertInfo;
import com.kaixin001.model.ContactsItemInfo;
import com.kaixin001.model.ContactsModel;
import com.kaixin001.model.FriendStatus;
import com.kaixin001.model.FriendsModel.Friend;
import com.kaixin001.network.HttpConnection;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.ImageDownloader;
import com.kaixin001.util.KXLog;
import com.kaixin001.view.AlphabetIndexerBar;
import com.kaixin001.view.AlphabetIndexerBar.OnIndexerChangedListener;
import com.kaixin001.view.KXTopTab;
import com.kaixin001.view.KXTopTabHost;
import com.kaixin001.view.KXTopTabHost.OnTabChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContactsFragment extends BaseFragment
  implements KXTopTabHost.OnTabChangeListener, View.OnClickListener, AdapterView.OnItemClickListener
{
  public static final int CODE_PHONEBOOK = 10;
  private ImageView btnLeft;
  private ImageView btnRight;
  private Cursor c = null;
  private ArrayList<ContactsItemInfo> checkedlist = new ArrayList();
  private ArrayList<ContactsItemInfo> friendslist = new ArrayList();
  private ListView lvFriendslist;
  private ContactsLinkAdapter mAdapter;
  private int mContactsLinkType = 11;
  private TextView mContactsLinkedNumDescrible = null;
  private InsertContactsTask mContactsTask = null;
  private ContentResolver mContentResolver = null;
  private ContactsItemInfo mCurrentItem = null;
  private int[] mLinkCheckFlag;
  private Long mLinkNum = Long.valueOf(0L);
  private ProgressDialog mProgressDialog;
  private KXTopTabHost mTabHost;
  private TextView mTextRight;
  private Long mTotalNum = Long.valueOf(0L);
  private int[] mUnLinkCheckFlag;
  private AlphabetIndexerBar mViewAlphabetIndexer = null;
  private int[] tabIndexArray = new int[2];
  private TextView tvListEmpty;
  private TextView tvTitle;

  private void doGetListData(int paramInt)
  {
    this.mContactsLinkType = ContactsModel.getInstance().getActiveLinkType();
    Iterator localIterator2;
    if (this.mContactsLinkType == 11)
    {
      this.friendslist.clear();
      if (ContactsModel.getInstance().getLinkedFriendList() != null)
      {
        localIterator2 = ContactsModel.getInstance().getLinkedFriendList().iterator();
        if (localIterator2.hasNext());
      }
      else
      {
        if (this.friendslist.size() != 0)
          break label126;
        showMainView(false);
        this.mViewAlphabetIndexer.setVisibility(8);
      }
    }
    while (true)
    {
      this.lvFriendslist.setSelection(this.tabIndexArray[paramInt]);
      this.mAdapter.notifyDataSetChanged();
      return;
      ContactsItemInfo localContactsItemInfo2 = (ContactsItemInfo)localIterator2.next();
      this.friendslist.add(localContactsItemInfo2);
      break;
      label126: showMainView(true);
      this.mViewAlphabetIndexer.setVisibility(0);
      this.mAdapter.notifyDataSetChanged();
      if (this.mLinkCheckFlag != null)
        continue;
      this.mLinkCheckFlag = new int[this.friendslist.size()];
      for (int j = 0; j < this.mLinkCheckFlag.length; j++)
        this.mLinkCheckFlag[j] = 1;
      continue;
      this.friendslist.clear();
      this.mViewAlphabetIndexer.setVisibility(8);
      Iterator localIterator1;
      if (ContactsModel.getInstance().getUnLinkedFriendList() != null)
        localIterator1 = ContactsModel.getInstance().getUnLinkedFriendList().iterator();
      while (true)
      {
        if (!localIterator1.hasNext())
        {
          if (this.friendslist.size() != 0)
            break label279;
          showMainView(false);
          break;
        }
        ContactsItemInfo localContactsItemInfo1 = (ContactsItemInfo)localIterator1.next();
        this.friendslist.add(localContactsItemInfo1);
      }
      label279: showMainView(true);
      this.mAdapter.notifyDataSetChanged();
      if (this.mUnLinkCheckFlag != null)
        continue;
      this.mUnLinkCheckFlag = new int[this.friendslist.size()];
      for (int i = 0; i < this.mUnLinkCheckFlag.length; i++)
        this.mUnLinkCheckFlag[i] = 0;
    }
  }

  private void handleContactsOptions(int paramInt)
  {
    while (true)
    {
      try
      {
        ArrayList localArrayList = ContactsRelateEngine.getInstance().loadContactsFuids(getActivity().getApplicationContext());
        String str = this.mCurrentItem.getFriend().getFuid();
        if (!localArrayList.contains(str))
          continue;
        ContactsRelateEngine.getInstance().deleteContactsData(getActivity().getApplicationContext(), str);
        if (paramInt != 0)
          continue;
        Intent localIntent = new Intent();
        localIntent.setAction("android.intent.action.PICK");
        localIntent.setData(ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(localIntent, 10);
        this.mCurrentItem.setLinkDialog(false);
        return;
        if (paramInt != 1)
          continue;
        this.mCurrentItem.setAdd(true);
        if (this.mContactsLinkType == 12)
        {
          this.mUnLinkCheckFlag[this.friendslist.indexOf(this.mCurrentItem)] = 1;
          this.mAdapter.notifyDataSetChanged();
          continue;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("ContactsActivity", "handleContactsOptions", localException);
        return;
      }
      this.mLinkCheckFlag[this.friendslist.indexOf(this.mCurrentItem)] = 1;
    }
  }

  private void initListViewData()
  {
    this.mContactsLinkType = ContactsModel.getInstance().getActiveLinkType();
    if (this.mContactsLinkType == 11)
    {
      this.friendslist.clear();
      Iterator localIterator2;
      if (ContactsModel.getInstance().getLinkedFriendList() != null)
      {
        localIterator2 = ContactsModel.getInstance().getLinkedFriendList().iterator();
        if (localIterator2.hasNext());
      }
      else
      {
        int k = this.friendslist.size();
        if (k > 0)
          this.mLinkCheckFlag = new int[k];
      }
      for (int m = 0; ; m++)
      {
        if (m >= this.mLinkCheckFlag.length)
        {
          return;
          ContactsItemInfo localContactsItemInfo2 = (ContactsItemInfo)localIterator2.next();
          this.friendslist.add(localContactsItemInfo2);
          break;
        }
        this.mLinkCheckFlag[m] = 1;
      }
    }
    this.friendslist.clear();
    Iterator localIterator1;
    if (ContactsModel.getInstance().getUnLinkedFriendList() != null)
      localIterator1 = ContactsModel.getInstance().getUnLinkedFriendList().iterator();
    while (true)
    {
      if (!localIterator1.hasNext())
      {
        int i = this.friendslist.size();
        if (i <= 0)
          break;
        this.mUnLinkCheckFlag = new int[i];
        for (int j = 0; j < this.mUnLinkCheckFlag.length; j++)
          this.mUnLinkCheckFlag[j] = 0;
        break;
      }
      ContactsItemInfo localContactsItemInfo1 = (ContactsItemInfo)localIterator1.next();
      this.friendslist.add(localContactsItemInfo1);
    }
  }

  private void setContactsLinkedNumDescrible()
  {
    this.mContactsLinkType = ContactsModel.getInstance().getActiveLinkType();
    if (this.mContactsLinkType == 11)
    {
      this.mContactsLinkedNumDescrible.setVisibility(0);
      String str = getResources().getString(2131427886);
      this.mContactsLinkedNumDescrible.setText(str.replace("*", this.mTotalNum).replace("#", this.mLinkNum));
      return;
    }
    this.mContactsLinkedNumDescrible.setVisibility(8);
  }

  private void showInsertFinishedDialog(String paramString)
  {
    DialogUtil.showMsgDlg(getActivity(), 2131427830, paramString, 2131427381, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        if ((ContactsFragment.this.mTabHost != null) && (ContactsFragment.this.mTabHost.getCurrentTab() == 0))
        {
          ContactsFragment.this.mTabHost.resetIndex();
          ContactsFragment.this.mTabHost.setCurrentTab(1);
          ContactsFragment.this.onTabChanged(1);
          return;
        }
        ContactsFragment.this.finish();
      }
    });
  }

  private void showMainView(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.lvFriendslist.setVisibility(0);
      this.tvListEmpty.setVisibility(8);
      return;
    }
    this.lvFriendslist.setVisibility(8);
    this.mContactsLinkType = ContactsModel.getInstance().getActiveLinkType();
    if (this.mContactsLinkType == 11)
      this.tvListEmpty.setText(2131427828);
    while (true)
    {
      this.tvListEmpty.setVisibility(0);
      return;
      this.tvListEmpty.setText(2131427829);
    }
  }

  private void showSelectContactsOptions()
  {
    DialogUtil.showSelectListDlg(getActivity(), 2131427729, getResources().getStringArray(2131492887), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        ContactsFragment.this.handleContactsOptions(paramInt);
      }
    }
    , new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        ContactsFragment.this.mContactsLinkType = ContactsModel.getInstance().getActiveLinkType();
        if (ContactsFragment.this.mContactsLinkType == 12)
        {
          ContactsFragment.this.mCurrentItem.setLinkDialog(true);
          ContactsFragment.this.mUnLinkCheckFlag[ContactsFragment.this.friendslist.indexOf(ContactsFragment.this.mCurrentItem)] = 0;
          ContactsFragment.this.mAdapter.notifyDataSetChanged();
        }
      }
    });
  }

  public void beforeTabChanged(int paramInt)
  {
    this.tabIndexArray[paramInt] = this.lvFriendslist.getFirstVisiblePosition();
  }

  public void insertToContacts()
  {
    if (HttpConnection.checkNetworkAvailable(getActivity()) < 0)
    {
      DialogUtil.showKXAlertDialog(getActivity(), 2131427387, null);
      return;
    }
    this.mProgressDialog = ProgressDialog.show(getActivity(), "", getResources().getText(2131427823), true, false, null);
    this.mContactsTask = new InsertContactsTask(null);
    this.mContactsTask.execute(new String[0]);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    long l = 0L;
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt2 == -1) && (paramInt1 == 10))
    {
      String str = null;
      if (paramIntent != null);
      while (true)
      {
        int j;
        try
        {
          this.c = getActivity().managedQuery(paramIntent.getData(), null, null, null, null);
          Cursor localCursor = this.c;
          str = null;
          if (localCursor == null)
            continue;
          boolean bool = this.c.moveToFirst();
          str = null;
          if (!bool)
            continue;
          str = this.c.getString(this.c.getColumnIndexOrThrow("display_name"));
          l = this.c.getLong(this.c.getColumnIndexOrThrow("_id"));
          int i = 0;
          ArrayList localArrayList = ContactsRelateEngine.getInstance().loadContactsCnames(getActivity().getApplicationContext());
          j = 0;
          if (j < localArrayList.size())
            continue;
          if ((str == null) || (i != localArrayList.size()))
            break;
          this.mCurrentItem.setAdd(false);
          this.mCurrentItem.setCname(str);
          this.mCurrentItem.setCid(l);
          if (this.mContactsLinkType != 12)
            break label290;
          this.mUnLinkCheckFlag[this.friendslist.indexOf(this.mCurrentItem)] = 1;
          this.mAdapter.notifyDataSetChanged();
          return;
          i++;
          if ((str != null) && (str.equals(localArrayList.get(j))))
          {
            DialogUtil.showKXAlertDialog(getActivity(), 2131427822, null);
            continue;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("ContactsLinkActivity", "onActivityResult", localException);
          return;
        }
        j++;
        continue;
        label290: this.mLinkCheckFlag[this.friendslist.indexOf(this.mCurrentItem)] = 1;
      }
    }
  }

  public void onClick(View paramView)
  {
    if (paramView.equals(this.btnLeft))
      finish();
    do
      return;
    while (!paramView.equals(this.mTextRight));
    int j;
    if (this.mLinkCheckFlag != null)
    {
      j = 0;
      if (j < this.mLinkCheckFlag.length);
    }
    else if (this.mUnLinkCheckFlag == null);
    for (int i = 0; ; i++)
    {
      if (i >= this.mUnLinkCheckFlag.length)
      {
        if (this.checkedlist.size() != 0)
          break label174;
        finish();
        return;
        if ((this.mLinkCheckFlag[j] == 1) && (ContactsModel.getInstance().getLinkedFriendList() != null))
          this.checkedlist.add((ContactsItemInfo)ContactsModel.getInstance().getLinkedFriendList().get(j));
        j++;
        break;
      }
      if ((this.mUnLinkCheckFlag[i] != 1) || (ContactsModel.getInstance().getUnLinkedFriendList() == null))
        continue;
      this.checkedlist.add((ContactsItemInfo)ContactsModel.getInstance().getUnLinkedFriendList().get(i));
    }
    label174: insertToContacts();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903088, paramViewGroup, false);
  }

  public void onDestroy()
  {
    cancelTask(this.mContactsTask);
    this.mLinkCheckFlag = null;
    this.mUnLinkCheckFlag = null;
    ContactsEngine.clear();
    ContactsModel.getInstance().clear();
    ContactsRelateEngine.getInstance();
    ContactsRelateEngine.clear();
    if (this.c != null)
      this.c.close();
    super.onDestroy();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    this.mCurrentItem = ((ContactsItemInfo)this.friendslist.get(paramInt));
    showSelectContactsOptions();
  }

  public void onTabChanged(int paramInt)
  {
    if (paramInt == 0)
      ContactsModel.getInstance().setActiveLinkType(11);
    while (true)
    {
      this.lvFriendslist.setSelection(0);
      setContactsLinkedNumDescrible();
      doGetListData(paramInt);
      return;
      this.lvFriendslist.setVerticalScrollBarEnabled(true);
      ContactsModel.getInstance().setActiveLinkType(12);
    }
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mContentResolver = getActivity().getContentResolver();
    setTitleBar(paramView);
    this.mContactsLinkedNumDescrible = ((TextView)paramView.findViewById(2131362140));
    if (ContactsModel.getInstance().getLinkedFriendList() != null)
      this.mLinkNum = Long.valueOf(ContactsModel.getInstance().getLinkedFriendList().size());
    if (ContactsModel.getInstance().getUnLinkedFriendList() != null)
      this.mTotalNum = Long.valueOf(this.mLinkNum.longValue() + ContactsModel.getInstance().getUnLinkedFriendList().size());
    setTab(paramView);
    if (!dataInited())
      initListViewData();
    this.lvFriendslist = ((ListView)paramView.findViewById(2131362142));
    this.tvListEmpty = ((TextView)paramView.findViewById(2131362139));
    this.mAdapter = new ContactsLinkAdapter(getActivity(), 2130903089, this.friendslist);
    this.lvFriendslist.setAdapter(this.mAdapter);
    this.lvFriendslist.setOnItemClickListener(this);
    this.mAdapter.notifyDataSetChanged();
    this.mViewAlphabetIndexer = ((AlphabetIndexerBar)getActivity().findViewById(2131362143));
    this.mViewAlphabetIndexer.showSearchIcon(false);
    this.mViewAlphabetIndexer.setVisibility(8);
    this.mViewAlphabetIndexer.setOnIndexerChangedListener(new AlphabetIndexerBar.OnIndexerChangedListener()
    {
      public void onIndexerChanged(String paramString)
      {
        if (paramString == null);
        Object[] arrayOfObject;
        do
        {
          return;
          arrayOfObject = ContactsFragment.this.mAdapter.getSections();
        }
        while ((arrayOfObject == null) || (arrayOfObject.length == 0));
        for (int i = -1 + arrayOfObject.length; ; i--)
        {
          int j = 0;
          if (i < 0);
          while (true)
          {
            ContactsFragment.this.lvFriendslist.setSelection(j);
            return;
            if (paramString.compareTo(arrayOfObject[i].toString()) < 0)
              break;
            j = ContactsFragment.this.mAdapter.getPositionForSection(i);
          }
        }
      }
    });
    if (this.friendslist.size() == 0)
    {
      this.mViewAlphabetIndexer.setVisibility(8);
      showMainView(false);
      return;
    }
    this.mViewAlphabetIndexer.setVisibility(0);
    showMainView(true);
  }

  protected void setTab(View paramView)
  {
    this.mTabHost = ((KXTopTabHost)paramView.findViewById(2131362138));
    KXTopTab localKXTopTab1 = new KXTopTab(getActivity());
    localKXTopTab1.setText(2131427826);
    this.mTabHost.addTab(localKXTopTab1);
    KXTopTab localKXTopTab2 = new KXTopTab(getActivity());
    localKXTopTab2.setText(2131427827);
    this.mTabHost.addTab(localKXTopTab2);
    this.mContactsLinkType = ContactsModel.getInstance().getActiveLinkType();
    if (this.mContactsLinkType == 11)
      this.mTabHost.setCurrentTab(0);
    while (true)
    {
      setContactsLinkedNumDescrible();
      this.mTabHost.setOnTabChangeListener(this);
      return;
      this.mTabHost.setCurrentTab(1);
    }
  }

  protected void setTitleBar(View paramView)
  {
    this.btnLeft = ((ImageView)paramView.findViewById(2131362914));
    this.btnLeft.setOnClickListener(this);
    this.tvTitle = ((TextView)paramView.findViewById(2131362920));
    this.tvTitle.setText(2131427793);
    this.tvTitle.setVisibility(0);
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    this.btnRight = ((ImageView)paramView.findViewById(2131362928));
    this.btnRight.setOnClickListener(this);
    this.btnRight.setImageResource(2130839022);
    this.btnRight.setVisibility(8);
    this.mTextRight = ((TextView)findViewById(2131362930));
    this.mTextRight.setVisibility(0);
    this.mTextRight.setText(2131427615);
    this.mTextRight.setOnClickListener(this);
  }

  private class ContactsLinkAdapter extends ArrayAdapter<ContactsItemInfo>
    implements SectionIndexer
  {
    private ArrayList<ContactsItemInfo> items;
    private ArrayList<Integer> mListSecPos = new ArrayList();
    private ArrayList<String> mListSections = new ArrayList();

    public ContactsLinkAdapter(int paramArrayList, ArrayList<ContactsItemInfo> arg3)
    {
      super(i, localList);
      this.items = localList;
    }

    public int getPositionForSection(int paramInt)
    {
      if ((paramInt < 0) || (paramInt >= this.mListSecPos.size()))
        return 0;
      return ((Integer)this.mListSecPos.get(paramInt)).intValue();
    }

    public int getSectionForPosition(int paramInt)
    {
      int i = 0;
      if (paramInt < 0)
        i = 0;
      while (true)
      {
        return i;
        if (paramInt >= ContactsFragment.this.friendslist.size())
          return -1 + this.mListSecPos.size();
        for (int j = 0; (j < this.mListSecPos.size()) && (paramInt >= ((Integer)this.mListSecPos.get(j)).intValue()); j++)
          i = j;
      }
    }

    public Object[] getSections()
    {
      return this.mListSections.toArray();
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      while (true)
      {
        try
        {
          ContactsItemInfo localContactsItemInfo = (ContactsItemInfo)this.items.get(paramInt);
          if (paramView != null)
            continue;
          paramView = ((LayoutInflater)ContactsFragment.this.getActivity().getSystemService("layout_inflater")).inflate(2130903089, null);
          ContactsFragment.ContactsLinkItemViewTag localContactsLinkItemViewTag = new ContactsFragment.ContactsLinkItemViewTag(ContactsFragment.this, paramView);
          paramView.setTag(localContactsLinkItemViewTag);
          int i = getSectionForPosition(paramInt);
          if ((paramInt == getPositionForSection(i)) && (!((String)this.mListSections.get(i)).equals(" ")))
          {
            j = 1;
            if (j == 0)
              continue;
            localContactsLinkItemViewTag.setSection((String)this.mListSections.get(i));
            localContactsLinkItemViewTag.updateFriend(localContactsItemInfo, paramInt);
            return paramView;
            localContactsLinkItemViewTag = (ContactsFragment.ContactsLinkItemViewTag)paramView.getTag();
            continue;
            localContactsLinkItemViewTag.setSection(null);
            continue;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("ContactsActivity", "getView", localException);
          return paramView;
        }
        int j = 0;
      }
    }

    public void notifyDataSetChanged()
    {
      this.mListSections.clear();
      this.mListSecPos.clear();
      Object localObject1 = "-1";
      int i = 0;
      if (i >= ContactsFragment.this.friendslist.size())
      {
        super.notifyDataSetChanged();
        return;
      }
      FriendsModel.Friend localFriend = ((ContactsItemInfo)ContactsFragment.this.friendslist.get(i)).getFriend();
      String[] arrayOfString = null;
      if (localFriend != null)
        arrayOfString = ((ContactsItemInfo)ContactsFragment.this.friendslist.get(i)).getFriend().getPy();
      String str;
      if ((arrayOfString == null) || (arrayOfString.length == 0))
      {
        str = "";
        label100: if (!TextUtils.isEmpty(str))
          break label159;
      }
      label159: for (Object localObject2 = localObject1; ; localObject2 = str.substring(0, 1).toUpperCase())
      {
        if (!((String)localObject2).equals(localObject1))
        {
          localObject1 = localObject2;
          this.mListSections.add(localObject1);
          this.mListSecPos.add(Integer.valueOf(i));
        }
        i++;
        break;
        str = arrayOfString[0];
        break label100;
      }
    }
  }

  private class ContactsLinkItemViewTag
  {
    private CheckBox importCheck;
    private ImageView ivContacts;
    private ImageView ivLogo;
    private TextView tvContactsName;
    private TextView tvName;
    private TextView tvSection = null;

    public ContactsLinkItemViewTag(View arg2)
    {
      Object localObject;
      this.tvName = ((TextView)localObject.findViewById(2131362148));
      this.tvContactsName = ((TextView)localObject.findViewById(2131362150));
      this.ivLogo = ((ImageView)localObject.findViewById(2131362147));
      this.ivContacts = ((ImageView)localObject.findViewById(2131362149));
      this.importCheck = ((CheckBox)localObject.findViewById(2131362146));
      this.tvSection = ((TextView)localObject.findViewById(2131362144));
      this.tvSection.setClickable(false);
    }

    public void setSection(String paramString)
    {
      if (TextUtils.isEmpty(paramString))
      {
        this.tvSection.setVisibility(8);
        return;
      }
      this.tvSection.setVisibility(0);
      this.tvSection.setText(paramString);
    }

    public void updateFriend(ContactsItemInfo paramContactsItemInfo, int paramInt)
      throws Exception
    {
      this.importCheck.setVisibility(0);
      this.tvContactsName.setVisibility(0);
      FriendsModel.Friend localFriend = paramContactsItemInfo.getFriend();
      String str1 = localFriend.getFlogo();
      ContactsFragment.this.displayPicture(this.ivLogo, str1, 2130838676);
      String str2 = localFriend.getFname();
      this.tvName.setText(str2);
      this.tvName.setPadding(0, 2, 0, 2);
      ContactsFragment.this.mContactsLinkType = ContactsModel.getInstance().getActiveLinkType();
      if (ContactsFragment.this.mContactsLinkType == 11)
      {
        this.ivContacts.setVisibility(0);
        if (paramContactsItemInfo.isAdd())
        {
          this.ivContacts.setBackgroundResource(2130837528);
          this.tvContactsName.setVisibility(8);
          this.importCheck.setTag(Integer.valueOf(paramInt));
          this.importCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
          {
            public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
            {
              int i = ((Integer)paramCompoundButton.getTag()).intValue();
              ContactsFragment.this.mContactsLinkType = ContactsModel.getInstance().getActiveLinkType();
              if (ContactsFragment.this.mContactsLinkType == 11)
                if (paramBoolean)
                  ContactsFragment.this.mLinkCheckFlag[i] = 1;
              while (true)
              {
                return;
                ContactsFragment.this.mLinkCheckFlag[i] = 0;
                return;
                if (!paramBoolean)
                  break;
                ContactsFragment.this.mUnLinkCheckFlag[i] = 1;
                ContactsFragment.this.mCurrentItem = ((ContactsItemInfo)ContactsFragment.this.friendslist.get(i));
                if (!ContactsFragment.this.mCurrentItem.isLinkDialog())
                  continue;
                ContactsFragment.this.showSelectContactsOptions();
                return;
              }
              ContactsFragment.this.mUnLinkCheckFlag[i] = 0;
            }
          });
          ContactsFragment.this.mContactsLinkType = ContactsModel.getInstance().getActiveLinkType();
          if (ContactsFragment.this.mContactsLinkType != 11)
            break label358;
          this.importCheck.setChecked(true);
        }
      }
      label358: 
      do
      {
        return;
        this.ivContacts.setBackgroundResource(2130838927);
        this.tvContactsName.setText(paramContactsItemInfo.getCname());
        this.ivContacts.setVisibility(0);
        this.tvContactsName.setVisibility(0);
        break;
        if (TextUtils.isEmpty(paramContactsItemInfo.getCname()))
        {
          if (paramContactsItemInfo.isAdd())
          {
            this.ivContacts.setVisibility(0);
            this.ivContacts.setBackgroundResource(2130837528);
          }
          while (true)
          {
            this.tvContactsName.setVisibility(8);
            break;
            this.ivContacts.setVisibility(8);
          }
        }
        if (paramContactsItemInfo.isAdd())
        {
          this.ivContacts.setVisibility(0);
          this.ivContacts.setBackgroundResource(2130837528);
          this.tvContactsName.setVisibility(8);
          break;
        }
        this.ivContacts.setBackgroundResource(2130838927);
        this.tvContactsName.setText(paramContactsItemInfo.getCname());
        this.ivContacts.setVisibility(0);
        this.tvContactsName.setVisibility(0);
        break;
        if (ContactsFragment.this.mUnLinkCheckFlag[paramInt] != 1)
          continue;
        this.importCheck.setChecked(true);
        return;
      }
      while (ContactsFragment.this.mUnLinkCheckFlag[paramInt] != 0);
      this.importCheck.setChecked(false);
    }
  }

  private class InsertContactsTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    int failedadd = 0;

    private InsertContactsTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      int i = 0;
      label154: label539: label543: 
      while (true)
      {
        int j;
        int m;
        int n;
        long l2;
        try
        {
          ArrayList localArrayList1 = FriendsInfoEngine.getInstance().getFriendsStatus(ContactsFragment.this.getActivity().getApplicationContext(), ContactsFragment.this.checkedlist);
          j = 0;
          int k = ContactsFragment.this.checkedlist.size();
          if (j >= k)
            return Integer.valueOf(i);
          str1 = "";
          String str2 = "";
          String str3 = "";
          ContactsItemInfo localContactsItemInfo = (ContactsItemInfo)ContactsFragment.this.checkedlist.get(j);
          str4 = localContactsItemInfo.getCname();
          FriendsModel.Friend localFriend = localContactsItemInfo.getFriend();
          str5 = null;
          str6 = null;
          if (localFriend == null)
            continue;
          str1 = localFriend.getFuid();
          str5 = localFriend.getFname();
          str6 = localFriend.getFlogo();
          l1 = localContactsItemInfo.getCid();
          if (localArrayList1 == null)
            continue;
          m = localArrayList1.size();
          n = 0;
          break label513;
          ImageCache localImageCache = ImageCache.getInstance();
          Bitmap localBitmap = localImageCache.createSafeImage(str6);
          if (localBitmap != null)
            continue;
          ImageDownloader.getInstance().downloadImageSync(str6);
          localBitmap = localImageCache.createSafeImage(str6);
          if (!localContactsItemInfo.isAdd())
            continue;
          ContactsInsertInfo localContactsInsertInfo2 = ContactsEngine.getInstance().insertAContacts(ContactsFragment.this.mContentResolver, str5, localBitmap, str2, str3);
          l2 = localContactsInsertInfo2.getOriginalFlag();
          long l4 = localContactsInsertInfo2.getStatusId();
          if (ContactsRelateEngine.getInstance().loadContactsCnames(ContactsFragment.this.getActivity()).contains(str5))
            break label523;
          ContactsRelateEngine.getInstance().saveContactsData(ContactsFragment.this.getActivity(), l2, str5, str5, str1, str6, l4);
          break label523;
          FriendStatus localFriendStatus = (FriendStatus)localArrayList1.get(n);
          if (localFriendStatus.getFuid().compareTo(str1) != 0)
            break label539;
          str2 = localFriendStatus.getStatus();
          str3 = localFriendStatus.getTimeStamp();
          continue;
          ContactsInsertInfo localContactsInsertInfo1 = ContactsEngine.getInstance().updateAContacts(ContactsFragment.this.mContentResolver, localBitmap, l1, str2, str3);
          l2 = localContactsInsertInfo1.getOriginalFlag();
          l3 = localContactsInsertInfo1.getStatusId();
          localArrayList2 = ContactsRelateEngine.getInstance().loadContactsCids(ContactsFragment.this.getActivity());
          if (localArrayList2.size() != 0)
            continue;
          ContactsRelateEngine.getInstance().saveContactsData(ContactsFragment.this.getActivity(), l1, str4, str5, str1, str6, l3);
        }
        catch (Exception localException)
        {
          String str1;
          String str4;
          String str5;
          String str6;
          long l1;
          long l3;
          ArrayList localArrayList2;
          KXLog.e("ContactsActivity", "doInBackground", localException);
          continue;
          if (!localArrayList2.contains(Long.valueOf(l1)))
            continue;
          ContactsRelateEngine.getInstance().updateContactsData(ContactsFragment.this.getActivity(), l1, str4, str5, str1, str6, l3);
          break label523;
          ContactsRelateEngine.getInstance().saveContactsData(ContactsFragment.this.getActivity(), l1, str4, str5, str1, str6, l3);
        }
        label500: this.failedadd = (1 + this.failedadd);
        while (true)
        {
          if (n < m)
            break label543;
          break label154;
          if (l2 <= 0L)
            break label500;
          i++;
          j++;
          break;
          n++;
        }
      }
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      int i = paramInteger.intValue();
      if (ContactsFragment.this.mProgressDialog != null)
        ContactsFragment.this.mProgressDialog.dismiss();
      if ((i > 0) && (this.failedadd == 0))
      {
        String str2 = ContactsFragment.this.getResources().getString(2131427825).replace("*", String.valueOf(i));
        ContactsFragment.this.showInsertFinishedDialog(str2);
      }
      if ((i >= 0) && (this.failedadd > 0))
      {
        String str1 = ContactsFragment.this.getResources().getString(2131427824).replace("*", String.valueOf(i)).replace("#", String.valueOf(this.failedadd));
        ContactsFragment.this.showInsertFinishedDialog(str1);
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
 * Qualified Name:     com.kaixin001.fragment.ContactsFragment
 * JD-Core Version:    0.6.0
 */