package com.kaixin001.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.FilmEngine;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.UserHabitUtils;

public class FilmWriteCommentFragment extends BaseFragment
  implements View.OnClickListener, DialogInterface.OnClickListener
{
  protected ImageView mBtnLeft;
  protected ImageView mBtnRight;
  EditText mContent;
  private String mFid = "";
  TextView mLevel;
  private String mName = "";
  private ProgressDialog mProgressDialog;
  protected ProgressBar mRightProBar;
  private int mScore = 0;
  ImageView mStar1;
  ImageView mStar2;
  ImageView mStar3;
  ImageView mStar4;
  ImageView mStar5;
  private SubmitTask mSubmitTask;
  protected TextView mTextRight;
  TextView mTitle;
  protected TextView mTopTitle;

  private void backOrExit()
  {
    String str = this.mContent.getText().toString();
    if ((this.mScore > 0) || (!TextUtils.isEmpty(str)))
    {
      DialogUtil.showKXAlertDialog(getActivity(), 2131427451, 2131427382, 2131427383, this, this);
      return;
    }
    hideInputMethod();
    finish();
  }

  private void changeScore()
  {
    int i = 2130838629;
    ImageView localImageView1 = this.mStar1;
    int j;
    int k;
    label40: int m;
    label64: int n;
    label88: ImageView localImageView5;
    if (this.mScore >= 1)
    {
      j = i;
      localImageView1.setImageResource(j);
      ImageView localImageView2 = this.mStar2;
      if (this.mScore < 2)
        break label164;
      k = i;
      localImageView2.setImageResource(k);
      ImageView localImageView3 = this.mStar3;
      if (this.mScore < 3)
        break label171;
      m = i;
      localImageView3.setImageResource(m);
      ImageView localImageView4 = this.mStar4;
      if (this.mScore < 4)
        break label178;
      n = i;
      localImageView4.setImageResource(n);
      localImageView5 = this.mStar5;
      if (this.mScore < 5)
        break label185;
    }
    while (true)
    {
      localImageView5.setImageResource(i);
      String[] arrayOfString = getResources().getStringArray(2131492906);
      String str = "";
      if (this.mScore > 0)
        str = arrayOfString[(-1 + this.mScore)];
      this.mLevel.setText(str);
      return;
      j = 2130838628;
      break;
      label164: k = 2130838628;
      break label40;
      label171: m = 2130838628;
      break label64;
      label178: n = 2130838628;
      break label88;
      label185: i = 2130838628;
    }
  }

  private void hideInputMethod()
  {
    View localView = getActivity().getCurrentFocus();
    if (localView != null)
      ((InputMethodManager)getActivity().getSystemService("input_method")).hideSoftInputFromWindow(localView.getWindowToken(), 2);
  }

  private void initView(View paramView)
  {
    this.mTitle = ((TextView)paramView.findViewById(2131362283));
    this.mStar1 = ((ImageView)paramView.findViewById(2131362285));
    this.mStar1.setOnClickListener(this);
    this.mStar2 = ((ImageView)paramView.findViewById(2131362286));
    this.mStar2.setOnClickListener(this);
    this.mStar3 = ((ImageView)paramView.findViewById(2131362287));
    this.mStar3.setOnClickListener(this);
    this.mStar4 = ((ImageView)paramView.findViewById(2131362288));
    this.mStar4.setOnClickListener(this);
    this.mStar5 = ((ImageView)paramView.findViewById(2131362289));
    this.mStar5.setOnClickListener(this);
    this.mLevel = ((TextView)paramView.findViewById(2131362290));
    this.mContent = ((EditText)paramView.findViewById(2131362291));
    String str = getResources().getString(2131428542).replace("*", this.mName);
    this.mTitle.setText(str);
    this.mContent.clearFocus();
  }

  private void submitRecord()
  {
    if ((this.mSubmitTask != null) && (!this.mSubmitTask.isCancelled()))
      this.mSubmitTask.cancel(true);
    String str1 = this.mContent.getText().toString();
    if ((this.mScore <= 0) && (TextUtils.isEmpty(str1)))
    {
      DialogUtil.showKXAlertDialog(getActivity(), 2131428544, null);
      return;
    }
    hideInputMethod();
    String str2 = getResources().getString(2131428506);
    this.mProgressDialog = ProgressDialog.show(getActivity(), null, str2);
    this.mSubmitTask = new SubmitTask();
    SubmitTask localSubmitTask = this.mSubmitTask;
    String[] arrayOfString = new String[3];
    arrayOfString[0] = this.mFid;
    arrayOfString[1] = String.valueOf(this.mScore);
    arrayOfString[2] = str1;
    localSubmitTask.execute(arrayOfString);
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 4);
  }

  protected void initTitle(View paramView)
  {
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    this.mBtnRight = ((ImageView)paramView.findViewById(2131362928));
    this.mBtnRight.setVisibility(0);
    this.mBtnRight.setImageResource(2130839272);
    this.mBtnRight.setOnClickListener(this);
    this.mRightProBar = ((ProgressBar)paramView.findViewById(2131362929));
    this.mBtnLeft = ((ImageView)paramView.findViewById(2131362914));
    this.mBtnLeft.setOnClickListener(this);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    localTextView.setVisibility(0);
    localTextView.setText(2131428538);
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    if (paramInt == -1)
    {
      if ((this.mSubmitTask != null) && (!this.mSubmitTask.isCancelled()))
        this.mSubmitTask.cancel(true);
      finish();
    }
  }

  public void onClick(View paramView)
  {
    if (paramView.equals(this.mBtnLeft))
      backOrExit();
    do
    {
      return;
      if (paramView.equals(this.mBtnRight))
      {
        submitRecord();
        return;
      }
      if (paramView == this.mStar1)
      {
        this.mScore = 1;
        changeScore();
        return;
      }
      if (paramView == this.mStar2)
      {
        this.mScore = 2;
        changeScore();
        return;
      }
      if (paramView == this.mStar3)
      {
        this.mScore = 3;
        changeScore();
        return;
      }
      if (paramView != this.mStar4)
        continue;
      this.mScore = 4;
      changeScore();
      return;
    }
    while (paramView != this.mStar5);
    this.mScore = 5;
    changeScore();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    UserHabitUtils.getInstance(getActivity()).addUserHabit("visit_film_write_comment");
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903111, paramViewGroup, false);
  }

  public void onDestroy()
  {
    cancelTask(this.mSubmitTask);
    super.onDestroy();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      backOrExit();
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      this.mFid = localBundle.getString("fid");
      this.mName = localBundle.getString("name");
    }
    initTitle(paramView);
    initView(paramView);
  }

  public void requestFinish()
  {
    backOrExit();
  }

  class SubmitTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    String content;
    String fid;
    String score;

    SubmitTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length < 3))
        return Integer.valueOf(-1);
      this.fid = paramArrayOfString[0];
      this.score = paramArrayOfString[1];
      this.content = paramArrayOfString[2];
      return Integer.valueOf(FilmEngine.getInstance().postFilmComment(FilmWriteCommentFragment.this.getActivity().getApplicationContext(), this.fid, this.content, this.score));
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if (FilmWriteCommentFragment.this.mProgressDialog != null)
      {
        FilmWriteCommentFragment.this.mProgressDialog.dismiss();
        FilmWriteCommentFragment.this.mProgressDialog = null;
      }
      if (paramInteger.intValue() == 1)
      {
        Toast.makeText(FilmWriteCommentFragment.this.getActivity().getApplicationContext(), 2131428545, 0).show();
        FilmWriteCommentFragment.this.mHandler.postDelayed(new Runnable()
        {
          public void run()
          {
            if (FilmWriteCommentFragment.this.isNeedReturn())
              return;
            Intent localIntent = new Intent();
            localIntent.putExtra("score", FilmWriteCommentFragment.SubmitTask.this.score);
            localIntent.putExtra("content", FilmWriteCommentFragment.SubmitTask.this.content);
            FilmWriteCommentFragment.this.setResult(-1, localIntent);
            FilmWriteCommentFragment.this.finish();
          }
        }
        , 1000L);
        return;
      }
      Toast.makeText(FilmWriteCommentFragment.this.getActivity().getApplicationContext(), 2131428546, 0).show();
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
 * Qualified Name:     com.kaixin001.fragment.FilmWriteCommentFragment
 * JD-Core Version:    0.6.0
 */