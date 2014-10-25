package com.kaixin001.network;

import org.json.JSONException;
import org.json.JSONObject;

public class HttpRequestState
{
  private String error;
  private String reponse;
  private String request;
  private int statusCode;
  private long taskId;

  public HttpRequestState(long paramLong)
  {
    this.taskId = paramLong;
  }

  public String getError()
  {
    return this.error;
  }

  public String getReponse()
  {
    return this.reponse;
  }

  public String getRequest()
  {
    return this.request;
  }

  public int getStatusCode()
  {
    return this.statusCode;
  }

  public long getTaskId()
  {
    return this.taskId;
  }

  public void parseData(String paramString)
    throws JSONException
  {
    if (paramString == null){
    	 return;
    }
    JSONObject localJSONObject;
   
     
    localJSONObject = new JSONObject(paramString);
    
   
    this.statusCode = localJSONObject.optInt("error_code");
    this.request = localJSONObject.optString("request");
    this.error = localJSONObject.optString("error");
  }

  public void setError(String paramString)
  {
    this.error = paramString;
  }

  public void setReponse(String paramString)
  {
    this.reponse = paramString;
  }

  public void setRequest(String paramString)
  {
    this.request = paramString;
  }

  public void setStatusCode(int paramInt)
  {
    this.statusCode = paramInt;
  }

  public void setTaskId(long paramLong)
  {
    this.taskId = paramLong;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.network.HttpRequestState
 * JD-Core Version:    0.6.0
 */