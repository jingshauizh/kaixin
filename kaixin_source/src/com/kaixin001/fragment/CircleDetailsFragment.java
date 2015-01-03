package com.kaixin001.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.businesslogic.ShowCirclePhoto;
import com.kaixin001.engine.CircleDetailsEngine;
import com.kaixin001.engine.ObjCommentEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.item.CircleDetailsItem;
import com.kaixin001.item.CircleDetailsItem.CircleDetailsContent;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.item.RecordInfo;
import com.kaixin001.model.CircleDetailsModel;
import com.kaixin001.model.CircleDetailsModel.CircleDetailsHeader;
import com.kaixin001.model.CircleDetailsModel.CircleDetailsHeaderMain;
import com.kaixin001.model.KaixinUser;
import com.kaixin001.model.User;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.KXTextUtil;
import com.kaixin001.util.ParseNewsInfoUtil;
import com.kaixin001.view.KXIntroView;
import com.kaixin001.view.KXIntroView.OnClickLinkListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CircleDetailsFragment extends BaseFragment
  implements View.OnClickListener, KXIntroView.OnClickLinkListener
{
  private String AT = "[|s|]@[|m|]10066329[|m|]-101[|e|]";
  private ImageView btnLeft;
  private ImageView btnRight;
  private ArrayList<CircleDetailsItem> commentList;
  private GetDataTask getDataTask;
  private ListView lvComment;
  private TextView mExttext = null;
  private View mFooter;
  private String mGid = null;
  private String mGname = null;
  private View mHheaderView = null;
  private ImageView mIvIcon = null;
  private ImageView mIvLine = null;
  ShowCirclePhoto mShowCirclePhoto = null;
  private ImageView mThumbnailImageView;
  private String[] mThumbnails;
  private String mTid = null;
  private KXIntroView mTitleView = null;
  private int mTotalReply = -1;
  private TextView mTvInfoResource = null;
  private CommentAdapter m_adapter;
  private String m_nTypeName;
  private ProgressBar proBar;
  private String repost_privacy;
  private String repost_uid;
  private String repost_visible;
  private TextView txtComment;
  private TextView txtWait;

  private void finishActivity()
  {
    Intent localIntent = new Intent();
    localIntent.putExtra("stid", this.mTid);
    localIntent.putExtra("gid", this.mGid);
    if (this.mTotalReply != -1)
    {
      localIntent.putExtra("replynum", this.mTotalReply);
      setResult(-1, localIntent);
    }
    finish();
  }

  private ShowCirclePhoto getPhotoShowUtil()
  {
    if (this.mShowCirclePhoto == null)
      this.mShowCirclePhoto = new ShowCirclePhoto(getActivity(), this);
    return this.mShowCirclePhoto;
  }

  private View getTitleView()
  {
    View localView = ((LayoutInflater)getActivity().getSystemService("layout_inflater")).inflate(2130903073, null);
    this.mIvIcon = ((ImageView)localView.findViewById(2131362039));
    this.mTitleView = ((KXIntroView)localView.findViewById(2131362041));
    this.mExttext = ((TextView)localView.findViewById(2131362044));
    this.mIvLine = ((ImageView)localView.findViewById(2131362046));
    this.mThumbnailImageView = ((ImageView)localView.findViewById(2131362042));
    this.mTvInfoResource = ((TextView)localView.findViewById(2131362045));
    return localView;
  }

  private String processTextForAt(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap = new HashMap();
    if (paramString.length() == 0)
      return null;
    int i = 0;
    int j = paramString.indexOf("@", i);
    label43: Iterator localIterator;
    if (j < 0)
      localIterator = localArrayList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        return paramString;
        int k = paramString.indexOf("#)", i);
        if ((k < 0) || (j >= k))
          break label43;
        String str1 = paramString.substring(j, k + 2);
        String str2 = str1.substring(1 + str1.indexOf("@"), str1.indexOf("(#")).trim();
        String str3 = str1.substring(1 + str1.indexOf("#"), str1.lastIndexOf("#")).trim();
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append(this.AT).append("[|s|]").append(str3).append("[|m|]").append(str2).append("[|m|]").append("0").append("[|e|]");
        localArrayList.add(str1);
        localHashMap.put(str1, localStringBuffer.toString());
        i = k + 1;
        break;
      }
      String str4 = (String)localIterator.next();
      paramString = paramString.replace(str4, (String)localHashMap.get(str4));
    }
  }

  private void refreshData(int paramInt)
  {
    if (!super.checkNetworkAndHint(true))
      return;
    this.proBar.setVisibility(0);
    this.txtWait.setVisibility(0);
    String str = String.valueOf(paramInt);
    this.getDataTask = new GetDataTask(null);
    this.getDataTask.execute(new String[] { str });
  }

  private void setList(int paramInt)
  {
    this.mTotalReply = paramInt;
    this.commentList.clear();
    ArrayList localArrayList = CircleDetailsModel.getInstance().getCommentList();
    if ((localArrayList != null) && (localArrayList.size() != 0))
    {
      this.commentList.addAll(localArrayList);
      if (localArrayList.size() < this.mTotalReply)
      {
        CircleDetailsItem localCircleDetailsItem = new CircleDetailsItem();
        localCircleDetailsItem.uid = "";
        this.commentList.add(localCircleDetailsItem);
      }
    }
  }

  private void setListHeader()
  {
    CircleDetailsModel.CircleDetailsHeader localCircleDetailsHeader = CircleDetailsModel.getInstance().getDetailsHeader();
    displayPicture(this.mIvIcon, localCircleDetailsHeader.icon50, 2130838676);
    if ((localCircleDetailsHeader.spic != null) && (!localCircleDetailsHeader.spic.equals("")))
    {
      this.mThumbnailImageView.setVisibility(0);
      displayPicture(this.mThumbnailImageView, localCircleDetailsHeader.spic, 2130838784);
      this.mThumbnailImageView.setOnClickListener(new View.OnClickListener(localCircleDetailsHeader)
      {
        public void onClick(View paramView)
        {
          CircleDetailsFragment.this.getPhotoShowUtil().showPhoto(CircleDetailsFragment.this.mTid, CircleDetailsFragment.this.mGid, this.val$headerData.picid, CircleDetailsFragment.this.mGname);
        }
      });
    }
    String str = KXTextUtil.getUserText(localCircleDetailsHeader.name, localCircleDetailsHeader.uid, false) + "：";
    StringBuffer localStringBuffer = new StringBuffer();
    int j;
    int k;
    if ((localCircleDetailsHeader.mInfoType != -1) && (localCircleDetailsHeader.mInfoType == 1))
    {
      j = localCircleDetailsHeader.mInviteUsersList.size();
      if (j > 0)
      {
        localStringBuffer.append("邀请 ");
        k = 0;
        if (k < j)
          break label291;
        localStringBuffer.append("加入圈子 ");
      }
    }
    int i = 0;
    if (i >= localCircleDetailsHeader.mHeaderList.size())
    {
      ArrayList localArrayList = ParseNewsInfoUtil.parseNewsLinkString(str + localStringBuffer);
      this.mTitleView.setTitleList(localArrayList);
      this.mTitleView.setOnClickLinkListener(this);
      this.mExttext.setText(KXTextUtil.formatTimestamp(1000L * localCircleDetailsHeader.ctime));
      if (localCircleDetailsHeader.inforesource.equals(""))
        break label530;
      this.mTvInfoResource.setText(localCircleDetailsHeader.inforesource);
      this.mTvInfoResource.setVisibility(0);
    }
    while (true)
    {
      this.mIvLine.setVisibility(0);
      return;
      label291: KaixinUser localKaixinUser = (KaixinUser)localCircleDetailsHeader.mInviteUsersList.get(k);
      localStringBuffer.append(KXTextUtil.getUserText(localKaixinUser.realname, localKaixinUser.uid, false));
      if (k < j - 1)
        localStringBuffer.append("、");
      k++;
      break;
      CircleDetailsModel.CircleDetailsHeaderMain localCircleDetailsHeaderMain = (CircleDetailsModel.CircleDetailsHeaderMain)localCircleDetailsHeader.mHeaderList.get(i);
      if (localCircleDetailsHeaderMain.mType == 0)
        localStringBuffer.append(localCircleDetailsHeaderMain.mText);
      while (true)
      {
        i++;
        break;
        if ((localCircleDetailsHeaderMain.mType == 3) || (localCircleDetailsHeaderMain.mType == 4))
        {
          localStringBuffer.append(localCircleDetailsHeaderMain.mTitle);
          continue;
        }
        if (localCircleDetailsHeaderMain.mType == 2)
        {
          localStringBuffer.append("[|s|]附链接[|m|]" + localCircleDetailsHeaderMain.mText + "[|m|]" + "-103" + "[|e|]");
          continue;
        }
        if (localCircleDetailsHeaderMain.mType != 1)
          continue;
        localStringBuffer.append(processTextForAt("@" + localCircleDetailsHeaderMain.mUid + "(#" + localCircleDetailsHeaderMain.mName + "#)"));
      }
      label530: this.mTvInfoResource.setVisibility(8);
    }
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt2 == -1) && (paramInt1 == 9))
    {
      this.commentList.clear();
      refreshData(0);
    }
  }

  public void onClick(View paramView)
  {
    if (paramView.equals(this.btnLeft))
      finishActivity();
    do
    {
      return;
      if (!paramView.equals(this.txtComment))
        continue;
      IntentUtil.replyCircleTalk(getActivity(), this, this.mGid, this.mTid);
    }
    while (!paramView.equals(this.mFooter));
    refreshData(-1 + this.commentList.size());
  }

  public void onClick(KXLinkInfo paramKXLinkInfo)
  {
    if ("6".equals(paramKXLinkInfo.getType()))
      if ("转发照片专辑".equals(this.m_nTypeName))
      {
        if ((this.mThumbnails != null) && (this.mThumbnails.length != 0))
          break label56;
        Toast.makeText(getActivity(), 2131427902, 0).show();
      }
    label56: 
    do
    {
      do
        return;
      while ((User.getInstance().getUID().equals(this.repost_uid)) || (!"1".equals(this.repost_privacy)) || (!"0".equals(this.repost_visible)));
      Toast.makeText(getActivity(), 2131427903, 0).show();
      return;
      if ("1".equals(paramKXLinkInfo.getType()))
      {
        "转发照片".equals(this.m_nTypeName);
        return;
      }
      if ((paramKXLinkInfo.isStar()) || (paramKXLinkInfo.isUserName()))
      {
        IntentUtil.showHomeFragment(this, paramKXLinkInfo.getId(), paramKXLinkInfo.getContent());
        return;
      }
      if (!paramKXLinkInfo.isUrlLink())
        continue;
      IntentUtil.showWebPage(getActivity(), this, paramKXLinkInfo.getId(), null);
      return;
    }
    while (!paramKXLinkInfo.isTopic());
    IntentUtil.showTopicGroupActivity(this, paramKXLinkInfo.getId());
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    String str = null;
    if (localBundle != null)
      str = localBundle.getString("action");
    if ((str != null) && (!str.equals("com.kaixin001.VIEW_OBJCOMMNET_DETAIL")) && (CrashRecoverUtil.isCrashBefore()))
    {
      CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
      IntentUtil.returnToDesktop(getActivity());
      return;
    }
    User.getInstance().loadDataIfEmpty(getActivity().getApplicationContext());
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903265, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if ((this.getDataTask != null) && (this.getDataTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.getDataTask.cancel(true);
      ObjCommentEngine.getInstance().cancel();
    }
    CircleDetailsModel.getInstance().clear();
    super.onDestroy();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      finishActivity();
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    localTextView.setText(2131427562);
    localTextView.setVisibility(0);
    this.btnLeft = ((ImageView)paramView.findViewById(2131362914));
    this.btnLeft.setOnClickListener(this);
    this.btnRight = ((ImageView)paramView.findViewById(2131362928));
    this.btnRight.setVisibility(8);
    this.proBar = ((ProgressBar)paramView.findViewById(2131363290));
    this.txtWait = ((TextView)paramView.findViewById(2131363291));
    this.lvComment = ((ListView)paramView.findViewById(16908298));
    this.txtComment = ((TextView)paramView.findViewById(2131363293));
    this.txtComment.setHint(2131428054);
    this.txtComment.setOnClickListener(this);
    Bundle localBundle = getArguments();
    this.mTid = localBundle.getString("tid");
    this.mGid = localBundle.getString("gid");
    this.mGname = localBundle.getString("gname");
    this.mHheaderView = getTitleView();
    this.lvComment.addHeaderView(this.mHheaderView);
    this.commentList = new ArrayList();
    this.m_adapter = new CommentAdapter(getActivity(), 2130903072, this.commentList);
    this.lvComment.setAdapter(this.m_adapter);
    this.lvComment.setVisibility(8);
    this.mFooter = getActivity().getLayoutInflater().inflate(2130903259, null);
    ((TextView)this.mFooter.findViewById(2131362073)).setText(2131427748);
    this.mFooter.setOnClickListener(this);
    refreshData(0);
  }

  private class CommentAdapter extends ArrayAdapter<CircleDetailsItem>
  {
    private ArrayList<CircleDetailsItem> items;

    public CommentAdapter(int paramArrayList, ArrayList<CircleDetailsItem> arg3)
    {
      super(i, localList);
      this.items = localList;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      CircleDetailsItem localCircleDetailsItem = (CircleDetailsItem)this.items.get(paramInt);
      if (localCircleDetailsItem.uid.equals(""))
        return CircleDetailsFragment.this.mFooter;
      LayoutInflater localLayoutInflater = (LayoutInflater)CircleDetailsFragment.this.getActivity().getSystemService("layout_inflater");
      if ((paramView == null) || (paramView.getId() != 2131362028))
        paramView = localLayoutInflater.inflate(2130903072, null);
      try
      {
        KXLog.d("CommentList>>", "Item Count=" + this.items.size() + "\n");
        if ((localCircleDetailsItem.getFuid().equals("-1")) && (TextUtils.isEmpty(localCircleDetailsItem.getStime())))
        {
          paramView = localLayoutInflater.inflate(2130903097, null);
          ((TextView)paramView.findViewById(2131361995)).setText(2131428133);
          return paramView;
        }
        CircleDetailsFragment.ObjCommentItemTag localObjCommentItemTag = (CircleDetailsFragment.ObjCommentItemTag)paramView.getTag();
        if (localObjCommentItemTag == null)
          localObjCommentItemTag = new CircleDetailsFragment.ObjCommentItemTag(CircleDetailsFragment.this, paramView, null);
        localObjCommentItemTag.setMainItem(getContext(), paramView, localCircleDetailsItem);
        paramView.setTag(localObjCommentItemTag);
        return paramView;
      }
      catch (Exception localException)
      {
        while (true)
          KXLog.e("CircleDetailsActivity", "CommentAdapter getView", localException);
      }
    }
  }

  private class FNameOnClickListener
    implements View.OnClickListener
  {
    private String fname;
    private String fuid;

    public FNameOnClickListener(String paramString1, String arg3)
    {
      this.fuid = paramString1;
      Object localObject;
      this.fname = localObject;
    }

    public void onClick(View paramView)
    {
      if ((this.fuid != null) && (this.fname != null))
        IntentUtil.showHomeFragment(CircleDetailsFragment.this, this.fuid, this.fname);
    }
  }

  private class GetDataTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private int mStart = -1;

    private GetDataTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      try
      {
        this.mStart = Integer.parseInt(paramArrayOfString[0]);
        if (CircleDetailsEngine.getInstance().getCircleDetailsList(CircleDetailsFragment.this.getActivity().getApplicationContext(), CircleDetailsFragment.this.mTid, CircleDetailsFragment.this.mGid, paramArrayOfString[0]))
          return Integer.valueOf(this.mStart);
        Integer localInteger = Integer.valueOf(-1);
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if (paramInteger == null)
      {
        CircleDetailsFragment.this.finish();
        return;
      }
      while (true)
      {
        try
        {
          CircleDetailsFragment.this.proBar.setVisibility(8);
          CircleDetailsFragment.this.txtWait.setVisibility(8);
          if (paramInteger.intValue() < 0)
          {
            String str = ObjCommentEngine.getInstance().getLastError();
            if (str != null)
              continue;
            str = CircleDetailsFragment.this.getString(2131428134);
            Toast.makeText(CircleDetailsFragment.this.getActivity(), str, 0).show();
            CircleDetailsModel.getInstance().addMainComment(0, "", "", "", "-1", "", "", "");
            if (CircleDetailsFragment.this.lvComment == null)
              continue;
            CircleDetailsFragment.this.lvComment.setDividerHeight(0);
            CircleDetailsFragment.this.setList(CircleDetailsModel.getInstance().getTotalReply());
            if (CircleDetailsFragment.this.m_adapter == null)
              break;
            CircleDetailsFragment.this.m_adapter.notifyDataSetChanged();
            return;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("CircleDetailsActivity", "onPostExecute", localException);
          return;
        }
        CircleDetailsFragment.this.lvComment.setVisibility(0);
        if (paramInteger.intValue() == 0)
          CircleDetailsFragment.this.setListHeader();
        if (CircleDetailsModel.getInstance().getCommentList().size() != 0)
          continue;
        CircleDetailsModel.getInstance().addMainComment(0, "", "", "", "-1", "", "", "");
        CircleDetailsFragment.this.lvComment.setDividerHeight(0);
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class ObjCommentItemTag
  {
    private KXIntroView exttext;
    private ImageView ivLogo;
    private TextView tvContent;
    private TextView tvTime;

    private ObjCommentItemTag(View arg2)
    {
      Object localObject;
      this.tvContent = ((TextView)localObject.findViewById(2131362033));
      this.tvTime = ((TextView)localObject.findViewById(2131362035));
      this.exttext = ((KXIntroView)localObject.findViewById(2131362036));
      this.ivLogo = ((ImageView)localObject.findViewById(2131362031));
    }

    private void setSubTag(Context paramContext, CircleDetailsItem paramCircleDetailsItem)
    {
      String str1 = paramCircleDetailsItem.getFname();
      String str2 = paramCircleDetailsItem.getFuid();
      if (User.getInstance().getUID().compareTo(str2) == 0);
      StringBuffer localStringBuffer;
      int i;
      for (String str3 = CircleDetailsFragment.this.getResources().getString(2131427565); ; str3 = paramCircleDetailsItem.getFname())
      {
        this.tvContent.setText(Html.fromHtml("<font color=\"#576b95\">" + str3 + "</font>"));
        this.tvContent.setOnClickListener(new CircleDetailsFragment.FNameOnClickListener(CircleDetailsFragment.this, str2, str1));
        this.tvTime.setText(KXTextUtil.formatTimestamp(1000L * Long.parseLong(paramCircleDetailsItem.getStime())));
        localStringBuffer = new StringBuffer();
        i = 0;
        if (i < paramCircleDetailsItem.mContentList.size())
          break;
        this.exttext.setOnClickLinkListener(CircleDetailsFragment.this);
        this.exttext.setTitleList(RecordInfo.parseObjCommentUtil(localStringBuffer.toString()));
        CircleDetailsFragment.this.displayPicture(this.ivLogo, paramCircleDetailsItem.getFlogo(), 2130838676);
        this.ivLogo.setOnClickListener(new CircleDetailsFragment.FNameOnClickListener(CircleDetailsFragment.this, str2, str1));
        return;
      }
      CircleDetailsItem.CircleDetailsContent localCircleDetailsContent = (CircleDetailsItem.CircleDetailsContent)paramCircleDetailsItem.mContentList.get(i);
      if ((localCircleDetailsContent.ntype == 0) || (localCircleDetailsContent.ntype == 2))
        localStringBuffer.append(localCircleDetailsContent.content);
      while (true)
      {
        i++;
        break;
        if ((localCircleDetailsContent.ntype == 3) || (localCircleDetailsContent.ntype == 4) || (localCircleDetailsContent.ntype != 1))
          continue;
        localStringBuffer.append(CircleDetailsFragment.this.AT).append("[|s|]").append(localCircleDetailsContent.uname).append("[|m|]").append(localCircleDetailsContent.uid).append("[|m|]").append("0").append("[|e|]");
      }
    }

    public void setMainItem(Context paramContext, View paramView, CircleDetailsItem paramCircleDetailsItem)
    {
      this.exttext.setVisibility(0);
      this.tvContent.setVisibility(0);
      this.tvTime.setVisibility(0);
      this.ivLogo.setVisibility(0);
      setSubTag(paramContext, paramCircleDetailsItem);
      paramView.setPadding(4, 4, 4, 4);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.CircleDetailsFragment
 * JD-Core Version:    0.6.0
 */