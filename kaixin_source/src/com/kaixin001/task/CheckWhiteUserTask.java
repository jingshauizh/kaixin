package com.kaixin001.task;

import android.content.Context;
import android.os.AsyncTask;
import com.kaixin001.engine.CloudAlbumEngine;
import com.kaixin001.model.User;
import com.kaixin001.util.CloudAlbumManager;

public class CheckWhiteUserTask extends AsyncTask<Void, Void, Void>
{
  private Context mContext;

  public CheckWhiteUserTask(Context paramContext)
  {
    this.mContext = paramContext;
  }

  protected Void doInBackground(Void[] paramArrayOfVoid)
  {
    int i = CloudAlbumEngine.getInstance().checkWhiteUser(this.mContext.getApplicationContext());
    if (("99688312".equals(User.getInstance().getUID())) || ("101940966".equals(User.getInstance().getUID())) || ("150784446".equals(User.getInstance().getUID())) || ("150784460".equals(User.getInstance().getUID())))
      i = 1;
    CloudAlbumManager.getInstance().setWhiteUser(this.mContext.getApplicationContext(), User.getInstance().getUID(), i);
    return null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.task.CheckWhiteUserTask
 * JD-Core Version:    0.6.0
 */