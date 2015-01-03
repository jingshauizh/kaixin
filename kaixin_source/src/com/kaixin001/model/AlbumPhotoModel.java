package com.kaixin001.model;

import com.kaixin001.item.KaixinPhotoItem;

public class AlbumPhotoModel extends KXModel
{
  private static AlbumPhotoModel instance;
  public KaixinUser albumOwner = null;
  public KaixinUser logoAlbumOwner = null;
  private String mAlbumId = null;
  public KaixinModelTemplate<KaixinPhotoItem> mListLogos = new KaixinModelTemplate();
  public KaixinModelTemplate<KaixinPhotoItem> mListPhotos = new KaixinModelTemplate();

  public static AlbumPhotoModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new AlbumPhotoModel();
      AlbumPhotoModel localAlbumPhotoModel = instance;
      return localAlbumPhotoModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void clear()
  {
    this.mListPhotos.clearItemList();
    this.mListLogos.clearItemList();
    this.mAlbumId = null;
    this.albumOwner = null;
    this.logoAlbumOwner = null;
  }

  public String getAlbumId()
  {
    return this.mAlbumId;
  }

  public void setAlbumId(String paramString)
  {
    this.mAlbumId = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.AlbumPhotoModel
 * JD-Core Version:    0.6.0
 */