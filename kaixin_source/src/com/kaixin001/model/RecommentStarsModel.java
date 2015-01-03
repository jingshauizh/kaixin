package com.kaixin001.model;

import java.util.ArrayList;

public class RecommentStarsModel extends KXModel
{
  private static RecommentStarsModel instance = new RecommentStarsModel();
  private ArrayList<RecommentStarsTypeItem> types = new ArrayList();

  public static RecommentStarsModel getInstance()
  {
    return instance;
  }

  public void clear()
  {
    this.types.clear();
  }

  public ArrayList<RecommentStarsTypeItem> getTypseList()
  {
    return this.types;
  }

  public static class RecommentStarsTypeItem
  {
    public ArrayList<StrangerModel.Stranger> items = new ArrayList();
    public String mName;
    public String mType;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.RecommentStarsModel
 * JD-Core Version:    0.6.0
 */