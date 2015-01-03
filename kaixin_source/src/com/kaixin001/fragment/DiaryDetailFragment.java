package com.kaixin001.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.os.AsyncTask.Status;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebView.PictureListener;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.LoginActivity;
import com.kaixin001.engine.DiaryEngine;
import com.kaixin001.engine.RepostEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.model.DiaryModel;
import com.kaixin001.model.User;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DiaryDetailFragment extends BaseFragment
{
  public static final int DIARY_ERRORNUM_DELETED = 100;
  public static final int DIARY_ERRORNUM_DELETED1 = 4001;
  private String mCommentFlag = "";
  private String mCthreadCid = "";
  private String mDid = "";
  private String mFriendName = "";
  private String mFuid = "";
  GetDiaryTask mGetDiaryTask = null;
  private String mIntro = "";
  private DiaryModel mModel = DiaryModel.getInstance();
  private String mReplyContent = "";
  RepostTask mRepostTask = null;
  private String mTitle = "";
  private ProgressDialog m_ProgressDialog = null;

  private void doRepost()
  {
    if (!super.checkNetworkAndHint(true))
      return;
    this.mRepostTask = new RepostTask(null);
    this.mRepostTask.execute(new Void[0]);
    this.m_ProgressDialog = ProgressDialog.show(getActivity(), "", getResources().getText(2131427610), true, true, new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramDialogInterface)
      {
        if (DiaryDetailFragment.this.mRepostTask != null)
        {
          if (RepostEngine.getInstance() != null)
            RepostEngine.getInstance().cancel();
          DiaryDetailFragment.this.mRepostTask.cancel(true);
          DiaryDetailFragment.this.mRepostTask = null;
        }
      }
    });
  }

  private String handleNull(String paramString)
  {
    if (paramString == null)
      paramString = "";
    return paramString;
  }

  private String removeImageTag(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return "";
    while (true)
    {
      int i;
      try
      {
        i = paramString.indexOf("[|sp|]");
        break label112;
        paramString = paramString.replaceAll("\n", "");
        break;
        int j = paramString.indexOf("[|ep|]");
        if ((j <= i) || (j >= paramString.length()))
          continue;
        paramString = paramString.substring(0, i) + paramString.substring(j + "[|ep|]".length());
        int k = paramString.indexOf("[|sp|]");
        i = k;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        break;
      }
      label112: if (i >= 0)
        continue;
    }
    return paramString;
  }

  protected void doGetDiary()
  {
    if (!super.checkNetworkAndHint(true))
      return;
    this.mGetDiaryTask = new GetDiaryTask(null);
    this.mGetDiaryTask.execute(new Void[0]);
    this.mGetDiaryTask = null;
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt2 == -1) && (paramInt1 == 3))
      this.mReplyContent = paramIntent.getStringExtra("content");
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    String str = null;
    if (localBundle != null)
      str = localBundle.getString("action");
    if ((str != null) && (!str.equals("com.kaixin001.VIEW_DIARY_DETAIL")) && (CrashRecoverUtil.isCrashBefore()))
    {
      CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
      IntentUtil.returnToDesktop(getActivity());
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903094, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if (this.mGetDiaryTask != null)
    {
      if (this.mGetDiaryTask.getStatus() != AsyncTask.Status.FINISHED)
        this.mGetDiaryTask.cancel(true);
      this.mGetDiaryTask.cancel(true);
      if (DiaryEngine.getInstance() != null)
        DiaryEngine.getInstance().cancel();
    }
    if ((this.mRepostTask != null) && (this.mRepostTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mRepostTask.cancel(true);
      if (RepostEngine.getInstance() != null)
        RepostEngine.getInstance().cancel();
    }
    if (getView() != null)
    {
      WebView localWebView = (WebView)getView().findViewById(2131362166);
      localWebView.setVisibility(8);
      localWebView.clearCache(true);
      localWebView.destroy();
    }
    if (this.mModel != null)
      this.mModel.clear();
    super.onDestroy();
  }

  public void onPause()
  {
    super.onPause();
  }

  public void onResume()
  {
    super.onResume();
    WebView localWebView = (WebView)getView().findViewById(2131362166);
    if (localWebView != null)
    {
      localWebView.clearChildFocus(localWebView.getFocusedChild());
      localWebView.clearFocus();
    }
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    User.getInstance().loadDataIfEmpty(getActivity().getApplicationContext());
    Object localObject1 = "";
    Object localObject2 = "";
    Bundle localBundle = getArguments();
    String str1;
    if (localBundle != null)
    {
      str1 = localBundle.getString("fname");
      if (!TextUtils.isEmpty(str1))
        break label553;
      this.mFriendName = getString(2131427565);
    }
    while (true)
    {
      String str2 = localBundle.getString("fuid");
      if (str2 != null)
        localObject1 = str2;
      String str3 = localBundle.getString("did");
      if (str3 != null)
        localObject2 = str3;
      String str4 = localBundle.getString("title");
      if (str4 != null)
        this.mTitle = str4;
      String str5 = localBundle.getString("intro");
      if (str5 != null)
        this.mIntro = str5;
      String str6 = localBundle.getString("commentflag");
      if (str6 != null)
        this.mCommentFlag = str6;
      String str7 = localBundle.getString("cthread_cid");
      if (str7 != null)
        this.mCthreadCid = str7;
      View localView1 = paramView.findViewById(2131362165);
      WebView localWebView = (WebView)paramView.findViewById(2131362166);
      if (Build.VERSION.SDK_INT >= 11);
      try
      {
        localWebView.getClass().getMethod("removeJavascriptInterface", new Class[] { String.class }).invoke(localWebView, new Object[] { "searchBoxJavaBridge_" });
        localWebView.getClass().getMethod("removeJavascriptInterface", new Class[] { String.class }).invoke(localWebView, new Object[] { "accessibility" });
        label288: localWebView.getSettings().setJavaScriptEnabled(true);
        localWebView.setWebViewClient(new WebViewListener(null));
        localWebView.setPictureListener(new WebViewPictureListener(null));
        WebSettings localWebSettings = localWebView.getSettings();
        localWebSettings.setSupportZoom(true);
        localWebSettings.setBuiltInZoomControls(true);
        localWebSettings.setJavaScriptEnabled(true);
        localWebView.setHorizontalScrollBarEnabled(true);
        localWebView.setHorizontalScrollbarOverlay(true);
        localView2 = paramView.findViewById(2131362167);
        Button localButton = (Button)paramView.findViewById(2131362168);
        localButton.setText(String.valueOf(this.mModel.getCNum()));
        localButton.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            String str1 = DiaryDetailFragment.this.mModel.getDiaryId();
            String str2 = DiaryDetailFragment.this.mModel.getDiaryFuid();
            Intent localIntent;
            if ((str1.length() > 0) && (str2.length() > 0))
            {
              localIntent = new Intent(DiaryDetailFragment.this.getActivity(), ObjCommentFragment.class);
              DiaryDetailFragment.this.mTitle = DiaryDetailFragment.this.handleNull(DiaryDetailFragment.this.mTitle);
              DiaryDetailFragment.this.mIntro = DiaryDetailFragment.this.handleNull(DiaryDetailFragment.this.mIntro);
              DiaryDetailFragment.this.mIntro = DiaryDetailFragment.this.removeImageTag(DiaryDetailFragment.this.mIntro);
              localIntent.putExtra("fuid", str2);
              localIntent.putExtra("id", str1);
              localIntent.putExtra("type", "2");
              localIntent.putExtra("commentflag", DiaryDetailFragment.this.mCommentFlag);
              localIntent.putExtra("title", DiaryDetailFragment.this.mTitle);
              localIntent.putExtra("intro", DiaryDetailFragment.this.mIntro);
              if (!User.getInstance().getUID().equals(DiaryDetailFragment.this.mFuid))
                break label258;
              localIntent.putExtra("fname", User.getInstance().getRealName());
            }
            while (true)
            {
              localIntent.putExtra("cthread_cid", DiaryDetailFragment.this.mCthreadCid);
              localIntent.putExtra("mode", 3);
              DiaryDetailFragment.this.startActivityForResult(localIntent, 3);
              return;
              label258: localIntent.putExtra("fname", DiaryDetailFragment.this.mFriendName);
            }
          }
        });
        localView3 = paramView.findViewById(2131362169);
        localView3.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            if (User.getInstance().GetLookAround())
            {
              FragmentActivity localFragmentActivity = DiaryDetailFragment.this.getActivity();
              String[] arrayOfString = new String[2];
              arrayOfString[0] = DiaryDetailFragment.this.getString(2131427338);
              arrayOfString[1] = DiaryDetailFragment.this.getString(2131427339);
              DialogUtil.showSelectListDlg(localFragmentActivity, 2131427729, arrayOfString, new DialogInterface.OnClickListener()
              {
                public void onClick(DialogInterface paramDialogInterface, int paramInt)
                {
                  switch (paramInt)
                  {
                  default:
                    return;
                  case 0:
                    Intent localIntent2 = new Intent(DiaryDetailFragment.this.getActivity(), LoginActivity.class);
                    DiaryDetailFragment.this.startActivity(localIntent2);
                    DiaryDetailFragment.this.getActivity().finish();
                    return;
                  case 1:
                  }
                  Intent localIntent1 = new Intent(DiaryDetailFragment.this.getActivity(), LoginActivity.class);
                  Bundle localBundle = new Bundle();
                  localBundle.putInt("regist", 1);
                  localIntent1.putExtras(localBundle);
                  DiaryDetailFragment.this.startActivity(localIntent1);
                  DiaryDetailFragment.this.getActivity().finish();
                }
              }
              , 1);
              return;
            }
            DiaryDetailFragment.this.doRepost();
          }
        });
        if ((((String)localObject2).length() > 0) && (this.mModel.getDiaryId().equals(localObject2)) && (this.mModel.getDiaryContent().length() > 0))
        {
          localView1.setVisibility(8);
          localView2.setVisibility(0);
          localWebView.setVisibility(0);
          if (this.mModel.getDiaryRepFlag() == 1)
          {
            localView3.setVisibility(0);
            localWebView.loadDataWithBaseURL(null, "<html>" + this.mModel.getDiaryContent() + "</html>", "text/html", "UTF-8", null);
            setTitleBar(paramView);
            return;
            label553: this.mFriendName = str1;
          }
        }
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        while (true)
          localIllegalAccessException.printStackTrace();
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        while (true)
        {
          View localView2;
          View localView3;
          localInvocationTargetException.printStackTrace();
          continue;
          localView3.setVisibility(8);
          continue;
          localView1.setVisibility(0);
          localWebView.setVisibility(8);
          localView2.setVisibility(8);
          this.mDid = ((String)localObject2);
          this.mFuid = ((String)localObject1);
          doGetDiary();
        }
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        break label288;
      }
    }
  }

  protected void setTitleBar(View paramView)
  {
    ImageView localImageView = (ImageView)paramView.findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (!TextUtils.isEmpty(DiaryDetailFragment.this.mReplyContent))
        {
          Intent localIntent = new Intent();
          localIntent.putExtra("content", DiaryDetailFragment.this.mReplyContent);
          DiaryDetailFragment.this.setResult(-1, localIntent);
          DiaryDetailFragment.this.finishFragment(3);
        }
        DiaryDetailFragment.this.finish();
      }
    });
    ((ImageView)paramView.findViewById(2131362928)).setVisibility(8);
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    String str = this.mFriendName;
    if (str.length() > 5)
      str = str.substring(0, 5) + "...";
    localTextView.setText(str + getResources().getString(2131427596));
    localTextView.setVisibility(0);
  }

  private class GetDiaryTask extends KXFragment.KXAsyncTask<Void, Void, Boolean>
  {
    private GetDiaryTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(Void[] paramArrayOfVoid)
    {
      try
      {
        Boolean localBoolean = Boolean.valueOf(DiaryEngine.getInstance().doGetDiaryDetail(DiaryDetailFragment.this.getActivity().getApplicationContext(), DiaryDetailFragment.this.mDid, DiaryDetailFragment.this.mFuid));
        return localBoolean;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      if (paramBoolean == null)
      {
        DiaryDetailFragment.this.finish();
        return;
      }
      while (true)
      {
        View localView3;
        try
        {
          View localView1 = DiaryDetailFragment.this.getView().findViewById(2131362165);
          WebView localWebView = (WebView)DiaryDetailFragment.this.getView().findViewById(2131362166);
          View localView2 = DiaryDetailFragment.this.getView().findViewById(2131362167);
          localView1.setVisibility(8);
          if (!paramBoolean.booleanValue())
            break;
          localWebView.setVisibility(0);
          localView2.setVisibility(0);
          localView3 = DiaryDetailFragment.this.getView().findViewById(2131362169);
          if (DiaryDetailFragment.this.mModel.getDiaryRepFlag() == 1)
          {
            localView3.setVisibility(0);
            ((Button)DiaryDetailFragment.this.getView().findViewById(2131362168)).setText(String.valueOf(DiaryDetailFragment.this.mModel.getCNum()));
            localWebView.loadDataWithBaseURL(null, "<html>" + DiaryDetailFragment.this.mModel.getDiaryContent() + "</html>", "text/html", "utf-8", null);
            return;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("DiaryDetailActivity", "onPostExecute", localException);
          return;
        }
        localView3.setVisibility(8);
      }
      if ((DiaryModel.getInstance().getErrorNum() == 100) || (DiaryModel.getInstance().getErrorNum() == 4001))
      {
        Toast.makeText(DiaryDetailFragment.this.getActivity(), 2131427921, 0).show();
        return;
      }
      Toast.makeText(DiaryDetailFragment.this.getActivity(), 2131427547, 0).show();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class RepostTask extends KXFragment.KXAsyncTask<Void, Void, Boolean>
  {
    private RepostTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(Void[] paramArrayOfVoid)
    {
      try
      {
        Boolean localBoolean = Boolean.valueOf(RepostEngine.getInstance().doRepostDiary(DiaryDetailFragment.this.getActivity().getApplicationContext(), DiaryDetailFragment.this.mModel.getDiaryId(), DiaryDetailFragment.this.mModel.getDiaryFuid()));
        return localBoolean;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      if (paramBoolean == null)
      {
        DiaryDetailFragment.this.finish();
        return;
      }
      try
      {
        if (DiaryDetailFragment.this.m_ProgressDialog != null)
          DiaryDetailFragment.this.m_ProgressDialog.dismiss();
        if (paramBoolean.booleanValue())
        {
          Toast.makeText(DiaryDetailFragment.this.getActivity(), DiaryDetailFragment.this.getResources().getString(2131427603), 0).show();
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("DiaryDetailActivity", "onPostExecute", localException);
        return;
      }
      Toast.makeText(DiaryDetailFragment.this.getActivity(), 2131427604, 0).show();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class WebViewListener extends WebViewClient
  {
    private WebViewListener()
    {
    }

    public void onPageFinished(WebView paramWebView, String paramString)
    {
      super.onPageFinished(paramWebView, paramString);
      if (DiaryDetailFragment.this.isNeedReturn())
        return;
      DiaryDetailFragment.this.getView().findViewById(2131362925).setVisibility(8);
    }

    public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
    {
      super.onPageStarted(paramWebView, paramString, paramBitmap);
    }
  }

  private class WebViewPictureListener
    implements WebView.PictureListener
  {
    private WebViewPictureListener()
    {
    }

    public void onNewPicture(WebView paramWebView, Picture paramPicture)
    {
      if (DiaryDetailFragment.this.getView() != null)
        DiaryDetailFragment.this.getView().findViewById(2131362925).setVisibility(8);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.DiaryDetailFragment
 * JD-Core Version:    0.6.0
 */