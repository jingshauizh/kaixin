package com.kaixin001.model;

import android.content.Context;
import android.content.res.AssetManager;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CityModel
{
  private static final String XML_FILE_NAME = "city.xml";
  private static final String XML_ITEM_ELE = "city";
  private static final String XML_STATE_ELE = "default";
  private static CityModel instance;
  private ArrayList<String[]> mCities = new ArrayList();
  private String[] mProvinces;

  public static CityModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new CityModel();
      CityModel localCityModel = instance;
      return localCityModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void loadData(Context paramContext)
  {
    this.mProvinces = null;
    this.mCities.clear();
    AssetManager localAssetManager = paramContext.getAssets();
    try
    {
      NodeList localNodeList = ((Element)DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(localAssetManager.open("city.xml")).getDocumentElement().getElementsByTagName("default").item(0)).getElementsByTagName("city");
      int i = localNodeList.getLength();
      Pattern localPattern = Pattern.compile("[, |]+");
      ArrayList localArrayList = new ArrayList();
      int j = 0;
      if (j >= i)
        this.mProvinces = new String[localArrayList.size()];
      for (int k = 0; ; k++)
      {
        if (k >= localArrayList.size())
        {
          return;
          Element localElement = (Element)localNodeList.item(j);
          String str = localElement.getAttribute("province");
          String[] arrayOfString = localPattern.split(localElement.getFirstChild().getNodeValue());
          localArrayList.add(str);
          this.mCities.add(arrayOfString);
          j++;
          break;
        }
        this.mProvinces[k] = ((String)localArrayList.get(k));
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    finally
    {
    }
    throw localObject;
  }

  public void clearAllData()
  {
    this.mProvinces = null;
    this.mCities.clear();
  }

  public String[] getCities(Context paramContext, int paramInt)
  {
    if ((this.mProvinces == null) || (this.mProvinces.length == 0))
      loadData(paramContext);
    return (String[])this.mCities.get(paramInt);
  }

  public String[] getProvinces(Context paramContext)
  {
    if ((this.mProvinces == null) || (this.mProvinces.length == 0))
      loadData(paramContext);
    return this.mProvinces;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.CityModel
 * JD-Core Version:    0.6.0
 */