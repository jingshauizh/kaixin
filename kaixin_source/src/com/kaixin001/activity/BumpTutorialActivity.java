package com.kaixin001.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.kaixin001.item.UserCard;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import com.kaixin001.view.KXSliderLayout;
import com.kaixin001.view.KXSliderLayout.OnScreenSlideListener;
import org.json.JSONException;
import org.json.JSONObject;

public class BumpTutorialActivity extends KXDownloadPicActivity
  implements View.OnClickListener
{
  public static final int CARD_REUQEST_CODE = 501;
  private static final String LOGTAG = "BumpTutorialActivity";
  public static final String RESULT_BUNDLE_KEY_USERCARD = "card";
  private View mEditButton;
  private GetUserCardTask mGetCardTask;
  private ImageView mIvSliderIndex;
  private View mLoadingCardLayout;
  private KXSliderLayout mSliderLayout;

  private void hasUpdatedCardResult(UserCard paramUserCard)
  {
    Intent localIntent = new Intent();
    localIntent.putExtra("card", paramUserCard);
    setResult(-1, localIntent);
    finish();
  }

  private void httpExceptionResult()
  {
    setResult(0);
    finish();
  }

  public static int luanchForCardResult(Activity paramActivity)
  {
    if (paramActivity == null)
      return -1;
    paramActivity.startActivityForResult(new Intent(paramActivity, BumpTutorialActivity.class), 501);
    return 501;
  }

  private void notUpdatedCardResult(UserCard paramUserCard)
  {
    this.mEditButton.setTag(paramUserCard);
    this.mEditButton.setOnClickListener(this);
    this.mLoadingCardLayout.setVisibility(8);
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt1 == 502) && (paramInt2 == -1))
    {
      paramIntent.putExtra("card", paramIntent.getParcelableExtra("card"));
      setResult(-1, paramIntent);
      finish();
    }
  }

  public void onClick(View paramView)
  {
    if (paramView.getId() == 2131361940)
      BumpCardActivity.launchForEditResult(this, (UserCard)paramView.getTag());
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903060);
    this.mIvSliderIndex = ((ImageView)findViewById(2131361941));
    this.mSliderLayout = ((KXSliderLayout)findViewById(2131361934));
    this.mSliderLayout.setOnScreenSliderListener(new KXSliderLayout.OnScreenSlideListener()
    {
      public void onSlideOverTheEnd()
      {
      }

      public void onSlideTo(int paramInt)
      {
        if (BumpTutorialActivity.this.mIvSliderIndex != null)
          BumpTutorialActivity.this.mIvSliderIndex.setImageLevel(paramInt);
      }
    });
    displayPicture((ImageView)findViewById(2131361939), User.getInstance().getLogo(), 2130837561);
    this.mLoadingCardLayout = findViewById(2131361942);
    this.mEditButton = findViewById(2131361940);
    this.mGetCardTask = new GetUserCardTask(null);
    this.mGetCardTask.execute(new Void[0]);
  }

  protected void onDestroy()
  {
    super.onDestroy();
    if ((this.mGetCardTask != null) && (!this.mGetCardTask.isCancelled()))
      this.mGetCardTask.cancel(true);
  }

  protected void onResume()
  {
    super.onResume();
  }

  private class GetUserCardTask extends AsyncTask<Void, Void, UserCard>
  {
    private GetUserCardTask()
    {
    }

    protected UserCard doInBackground(Void[] paramArrayOfVoid)
    {
      if ((paramArrayOfVoid == null) || (paramArrayOfVoid.length == 0))
        return null;
      String str = Protocol.getInstance().makeGetUserCardRequest();
      HttpProxy localHttpProxy = new HttpProxy(BumpTutorialActivity.this);
      try
      {
        localHttpProxy.httpGet(str, null, null);
        if (TextUtils.isEmpty(str))
          return null;
      }
      catch (Exception localException)
      {
        while (true)
          KXLog.e("BumpTutorialActivity", "doInBackground error", localException);
        try
        {
          JSONObject localJSONObject1 = new JSONObject(str);
          if (localJSONObject1.getInt("ret") == 1)
          {
            UserCard localUserCard = new UserCard();
            localUserCard.privacy = localJSONObject1.getInt("privacy");
            if (localJSONObject1.has("profile"))
            {
              JSONObject localJSONObject2 = localJSONObject1.getJSONObject("profile");
              localUserCard.name = localJSONObject2.getString("name");
              localUserCard.logo = localJSONObject2.getString("logo");
              localUserCard.city = localJSONObject2.getString("city");
              localUserCard.company = localJSONObject2.getString("com");
              localUserCard.post = localJSONObject2.getString("title");
              localUserCard.phone = localJSONObject2.getString("phone");
              localUserCard.mobile = localJSONObject2.getString("mobile");
              localUserCard.email = localJSONObject2.getString("email");
              localUserCard.from = localJSONObject2.getString("from");
              return localUserCard;
            }
          }
        }
        catch (JSONException localJSONException)
        {
          localJSONException.printStackTrace();
        }
      }
      return null;
    }

    protected void onPostExecute(UserCard paramUserCard)
    {
      super.onPostExecute(paramUserCard);
      if (paramUserCard == null)
      {
        BumpTutorialActivity.this.httpExceptionResult();
        return;
      }
      if (paramUserCard.hasBeenUpdatedCard())
      {
        BumpTutorialActivity.this.hasUpdatedCardResult(paramUserCard);
        return;
      }
      BumpTutorialActivity.this.notUpdatedCardResult(paramUserCard);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.BumpTutorialActivity
 * JD-Core Version:    0.6.0
 */