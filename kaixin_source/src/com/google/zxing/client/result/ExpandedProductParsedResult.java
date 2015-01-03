package com.google.zxing.client.result;

import java.util.Hashtable;

public class ExpandedProductParsedResult extends ParsedResult
{
  public static final String KILOGRAM = "KG";
  public static final String POUND = "LB";
  private final String bestBeforeDate;
  private final String expirationDate;
  private final String lotNumber;
  private final String packagingDate;
  private final String price;
  private final String priceCurrency;
  private final String priceIncrement;
  private final String productID;
  private final String productionDate;
  private final String sscc;
  private final Hashtable uncommonAIs;
  private final String weight;
  private final String weightIncrement;
  private final String weightType;

  ExpandedProductParsedResult()
  {
    super(ParsedResultType.PRODUCT);
    this.productID = "";
    this.sscc = "";
    this.lotNumber = "";
    this.productionDate = "";
    this.packagingDate = "";
    this.bestBeforeDate = "";
    this.expirationDate = "";
    this.weight = "";
    this.weightType = "";
    this.weightIncrement = "";
    this.price = "";
    this.priceIncrement = "";
    this.priceCurrency = "";
    this.uncommonAIs = new Hashtable();
  }

  public ExpandedProductParsedResult(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, Hashtable paramHashtable)
  {
    super(ParsedResultType.PRODUCT);
    this.productID = paramString1;
    this.sscc = paramString2;
    this.lotNumber = paramString3;
    this.productionDate = paramString4;
    this.packagingDate = paramString5;
    this.bestBeforeDate = paramString6;
    this.expirationDate = paramString7;
    this.weight = paramString8;
    this.weightType = paramString9;
    this.weightIncrement = paramString10;
    this.price = paramString11;
    this.priceIncrement = paramString12;
    this.priceCurrency = paramString13;
    this.uncommonAIs = paramHashtable;
  }

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ExpandedProductParsedResult));
    ExpandedProductParsedResult localExpandedProductParsedResult;
    do
    {
      return false;
      localExpandedProductParsedResult = (ExpandedProductParsedResult)paramObject;
    }
    while ((!this.productID.equals(localExpandedProductParsedResult.productID)) || (!this.sscc.equals(localExpandedProductParsedResult.sscc)) || (!this.lotNumber.equals(localExpandedProductParsedResult.lotNumber)) || (!this.productionDate.equals(localExpandedProductParsedResult.productionDate)) || (!this.bestBeforeDate.equals(localExpandedProductParsedResult.bestBeforeDate)) || (!this.expirationDate.equals(localExpandedProductParsedResult.expirationDate)) || (!this.weight.equals(localExpandedProductParsedResult.weight)) || (!this.weightType.equals(localExpandedProductParsedResult.weightType)) || (!this.weightIncrement.equals(localExpandedProductParsedResult.weightIncrement)) || (!this.price.equals(localExpandedProductParsedResult.price)) || (!this.priceIncrement.equals(localExpandedProductParsedResult.priceIncrement)) || (!this.priceCurrency.equals(localExpandedProductParsedResult.priceCurrency)) || (!this.uncommonAIs.equals(localExpandedProductParsedResult.uncommonAIs)));
    return true;
  }

  public String getBestBeforeDate()
  {
    return this.bestBeforeDate;
  }

  public String getDisplayResult()
  {
    return this.productID;
  }

  public String getExpirationDate()
  {
    return this.expirationDate;
  }

  public String getLotNumber()
  {
    return this.lotNumber;
  }

  public String getPackagingDate()
  {
    return this.packagingDate;
  }

  public String getPrice()
  {
    return this.price;
  }

  public String getPriceCurrency()
  {
    return this.priceCurrency;
  }

  public String getPriceIncrement()
  {
    return this.priceIncrement;
  }

  public String getProductID()
  {
    return this.productID;
  }

  public String getProductionDate()
  {
    return this.productionDate;
  }

  public String getSscc()
  {
    return this.sscc;
  }

  public Hashtable getUncommonAIs()
  {
    return this.uncommonAIs;
  }

  public String getWeight()
  {
    return this.weight;
  }

  public String getWeightIncrement()
  {
    return this.weightIncrement;
  }

  public String getWeightType()
  {
    return this.weightType;
  }

  public int hashCode()
  {
    return 31 * (31 * (31 * (31 * (31 * (31 * this.productID.hashCode() + this.sscc.hashCode()) + this.lotNumber.hashCode()) + this.productionDate.hashCode()) + this.bestBeforeDate.hashCode()) + this.expirationDate.hashCode()) + this.weight.hashCode() ^ 31 * (31 * (31 * (31 * (31 * this.weightType.hashCode() + this.weightIncrement.hashCode()) + this.price.hashCode()) + this.priceIncrement.hashCode()) + this.priceCurrency.hashCode()) + this.uncommonAIs.hashCode();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.ExpandedProductParsedResult
 * JD-Core Version:    0.6.0
 */