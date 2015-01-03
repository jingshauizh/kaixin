package com.kaixin001.model;

import android.text.TextUtils;
import com.kaixin001.item.AlbumInfo;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class AlbumListModel extends KXModel
{
  private static AlbumListModel instance;
  private static AlbumListModel myAlbumList;
  private ArrayList<AlbumInfo> albumslist = new ArrayList();
  private JSONArray m_AlbumList;
  private int total = 0;

  public static AlbumListModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new AlbumListModel();
      AlbumListModel localAlbumListModel = instance;
      return localAlbumListModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public static AlbumListModel getMyAlbumList()
  {
    monitorenter;
    try
    {
      if (myAlbumList == null)
        myAlbumList = new AlbumListModel();
      AlbumListModel localAlbumListModel = myAlbumList;
      return localAlbumListModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void clear()
  {
    this.m_AlbumList = null;
    this.total = 0;
  }

  public int count()
  {
    if (this.m_AlbumList == null)
      return 0;
    return this.m_AlbumList.length();
  }

  public JSONArray getAlbumList()
  {
    return this.m_AlbumList;
  }

  public ArrayList<AlbumInfo> getAlbumslist()
  {
    return this.albumslist;
  }

  public int getPicTotal()
  {
    return this.total;
  }

  public void setAlbumList(JSONArray paramJSONArray)
  {
    this.m_AlbumList = paramJSONArray;
  }

  public void setAlbumslist(ArrayList<AlbumInfo> paramArrayList)
  {
    this.albumslist = paramArrayList;
  }

  public void setPicTotal()
  {
    this.total = 0;
    if (this.m_AlbumList == null)
      return;
    int i = this.m_AlbumList.length();
    int j = 0;
    while (j < i)
      try
      {
        String str = this.m_AlbumList.getJSONObject(j).getString("picnum");
        if (!TextUtils.isEmpty(str))
          this.total = (Integer.parseInt(str) + this.total);
        j++;
      }
      catch (Exception localException)
      {
        KXLog.e("AlbumListModel", "setPicTotal", localException);
      }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.AlbumListModel
 * JD-Core Version:    0.6.0
 */