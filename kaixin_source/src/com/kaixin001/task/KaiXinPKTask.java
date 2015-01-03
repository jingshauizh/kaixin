package com.kaixin001.task;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.kaixin001.engine.KXPKEngine;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.model.PKModel;
import com.kaixin001.util.IntentUtil;

public class KaiXinPKTask extends AsyncTask<Void, Void, Integer>
{
  private Context context;
  private BaseFragment fragment;
  private String title;

  public KaiXinPKTask(Context paramContext, BaseFragment paramBaseFragment, String paramString)
  {
    this.context = paramContext;
    this.fragment = paramBaseFragment;
    this.title = paramString;
  }

  protected Integer doInBackground(Void[] paramArrayOfVoid)
  {
    return Integer.valueOf(KXPKEngine.getInstance().pkInfoByTitle(this.context, this.title));
  }

  protected void onPostExecute(Integer paramInteger)
  {
    super.onPostExecute(paramInteger);
    if (paramInteger.intValue() == 1)
    {
      String str1 = PKModel.getInstance().getPkid();
      String str2 = PKModel.getInstance().getPkType();
      if ((!TextUtils.isEmpty(str1)) && (!TextUtils.isEmpty(str2)) && (str2.equals("3")))
      {
        IntentUtil.showPkFragment(this.fragment, str1);
        return;
      }
      IntentUtil.showTopicGroupActivity(this.fragment, this.title);
      return;
    }
    IntentUtil.showTopicGroupActivity(this.fragment, this.title);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.task.KaiXinPKTask
 * JD-Core Version:    0.6.0
 */