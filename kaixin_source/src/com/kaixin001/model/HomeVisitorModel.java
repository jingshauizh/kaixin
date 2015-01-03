package com.kaixin001.model;

import com.kaixin001.item.HomeVisitorItem;
import java.util.ArrayList;

public class HomeVisitorModel extends KXModel
{
  private String ctime = "";
  private ArrayList<HomeVisitorItem> mHomeVisitorList = new ArrayList();
  private int total = 0;

  public void addVisitor(HomeVisitorItem paramHomeVisitorItem)
  {
    this.mHomeVisitorList.add(paramHomeVisitorItem);
  }

  public void clear()
  {
    this.mHomeVisitorList.clear();
  }

  public String getCtime()
  {
    return this.ctime;
  }

  public int getTotal()
  {
    return this.total;
  }

  public ArrayList<HomeVisitorItem> getVisitorList()
  {
    return this.mHomeVisitorList;
  }

  public void setCtime(String paramString)
  {
    this.ctime = paramString;
  }

  public void setTotal(int paramInt)
  {
    this.total = paramInt;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.HomeVisitorModel
 * JD-Core Version:    0.6.0
 */