package com.kaixin001.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import com.kaixin001.db.ConfigDBAdapter;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.view.KXSliderLayout;
import com.kaixin001.view.KXSliderLayout.OnScreenSlideListener;
import java.util.ArrayList;

public class GuideActivity extends KXActivity
{
  private Boolean isSendNotice = Boolean.valueOf(false);
  private ArrayList<View> mListPage = new ArrayList();
  private SendNoticeTask sendNoticeTask = null;

  private void setGuideNavButton(int paramInt, KXSliderLayout paramKXSliderLayout)
  {
    View localView = (View)this.mListPage.get(paramInt);
    Button localButton = (Button)localView.findViewById(2131362504);
    CheckBox localCheckBox = (CheckBox)localView.findViewById(2131362505);
    RelativeLayout localRelativeLayout = (RelativeLayout)localView.findViewById(2131362503);
    if (paramInt == -1 + paramKXSliderLayout.getChildCount())
    {
      localRelativeLayout.setVisibility(0);
      localButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          GuideActivity.this.returnToDesktop();
        }
      });
      localCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
      {
        public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
        {
          GuideActivity.this.isSendNotice = Boolean.valueOf(paramBoolean);
        }
      });
      this.isSendNotice = Boolean.valueOf(localCheckBox.isChecked());
      if (User.getInstance().GetLookAround())
        localCheckBox.setVisibility(8);
      return;
    }
    localRelativeLayout.setVisibility(8);
  }

  private void setGuidePage(View paramView, int paramInt, KXSliderLayout paramKXSliderLayout)
  {
    if (paramView == null)
      return;
    paramView.setBackgroundResource(paramInt);
    this.mListPage.add(paramView);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903145);
    enableSlideBack(false);
    KXSliderLayout localKXSliderLayout = (KXSliderLayout)findViewById(2131361934);
    setGuidePage(findViewById(2131362500), 2130838310, localKXSliderLayout);
    setGuidePage(findViewById(2131362501), 2130838311, localKXSliderLayout);
    View localView = findViewById(2131362502);
    setGuidePage(localView, 2130838312, localKXSliderLayout);
    localView.setClickable(true);
    localView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        GuideActivity.this.returnToDesktop();
      }
    });
    setGuideNavButton(0, localKXSliderLayout);
    localKXSliderLayout.setOnScreenSliderListener(new KXSliderLayout.OnScreenSlideListener(localKXSliderLayout)
    {
      public void onSlideOverTheEnd()
      {
        GuideActivity.this.returnToDesktop();
      }

      public void onSlideTo(int paramInt)
      {
        GuideActivity.this.setGuideNavButton(paramInt, this.val$sliderLayout);
      }
    });
  }

  void returnToDesktop()
  {
    if (this.isSendNotice.booleanValue())
    {
      this.sendNoticeTask = new SendNoticeTask(null);
      this.sendNoticeTask.execute(new Void[0]);
      return;
    }
    ConfigDBAdapter.setConfig(this, User.getInstance().getUID(), "guided:android-3.9.9", "1", "");
    MainActivity.mNeedCheckDialogNotice = true;
    IntentUtil.returnToDesktop(this);
    finish();
  }

  private class SendNoticeTask extends AsyncTask<Void, Void, Integer>
  {
    private SendNoticeTask()
    {
    }

    protected Integer doInBackground(Void[] paramArrayOfVoid)
    {
      String str = Protocol.getInstance().makeGuideSendNotice(User.getInstance().getUID());
      HttpProxy localHttpProxy = new HttpProxy(GuideActivity.this);
      try
      {
        localHttpProxy.httpGet(str, null, null);
        label33: return Integer.valueOf(0);
      }
      catch (Exception localException)
      {
        break label33;
      }
    }

    protected void onPostExecute(Integer paramInteger)
    {
      ConfigDBAdapter.setConfig(GuideActivity.this, User.getInstance().getUID(), "guided:android-3.9.9", "1", "");
      IntentUtil.returnToDesktop(GuideActivity.this);
      GuideActivity.this.finish();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.GuideActivity
 * JD-Core Version:    0.6.0
 */