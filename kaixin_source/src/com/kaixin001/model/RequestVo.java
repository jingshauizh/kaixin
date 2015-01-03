package com.kaixin001.model;

import com.kaixin001.parser.BaseParser;
import java.util.HashMap;

public class RequestVo
{
  private BaseParser parser;
  private HashMap<String, String> requestParam;
  private int successCode;
  private String url;

  public BaseParser getParser()
  {
    return this.parser;
  }

  public HashMap<String, String> getRequestParam()
  {
    return this.requestParam;
  }

  public int getSuccessCode()
  {
    return this.successCode;
  }

  public String getUrl()
  {
    return this.url;
  }

  public void setParser(BaseParser paramBaseParser)
  {
    this.parser = paramBaseParser;
  }

  public void setRequestParam(HashMap<String, String> paramHashMap)
  {
    this.requestParam = paramHashMap;
  }

  public void setSuccessCode(int paramInt)
  {
    this.successCode = paramInt;
  }

  public void setUrl(String paramString)
  {
    this.url = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.RequestVo
 * JD-Core Version:    0.6.0
 */