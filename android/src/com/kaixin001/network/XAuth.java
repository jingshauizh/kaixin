package com.kaixin001.network;

import android.text.TextUtils;
import com.kaixin001.model.Setting;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class XAuth
{
  public static String CONSUMER_KEY = "87247717949570179fa41c43e20ed289";
  public static String CONSUMER_SECRET = "8207525c8aa35c89b29385057f5905c9";

  private static String constructRequestURL(String paramString)
  {
    int i = paramString.indexOf("?");
    if (-1 != i)
      paramString = paramString.substring(0, i);
    int j = paramString.indexOf("/", 8);
    String str = paramString.substring(0, j).toLowerCase();
    int k = str.indexOf(":", 8);
    if (-1 != k)
      if ((!str.startsWith("http://")) || (!str.endsWith(":80")))
    	  return str + paramString.substring(j);
   
    return str + paramString.substring(j);
  }

  public static String encode(String value) {
      String encoded = null;
      try {
          encoded = URLEncoder.encode(value, "utf-8");
      } catch (UnsupportedEncodingException ignore) {
      }
      StringBuffer buf = new StringBuffer(encoded.length());
      char focus;
      for (int i = 0; i < encoded.length(); i++) {
          focus = encoded.charAt(i);
          if (focus == '*') {
              buf.append("%2A");
          } else if (focus == '+') {
              buf.append("%20");
          } else if (focus == '%' && (i + 1) < encoded.length()
                  && encoded.charAt(i + 1) == '7' && encoded.charAt(i + 2) == 'E') {
              buf.append('~');
              i += 2;
          } else {
              buf.append(focus);
          }
      }
      return buf.toString();
  }

  public static String encodeUrl(HashMap<String, Object> paramHashMap)
  {
    if (paramHashMap == null)
      return "";
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 1;
    Iterator localIterator = paramHashMap.keySet().iterator();
    if (!localIterator.hasNext())
      return localStringBuilder.toString();
    String str = (String)localIterator.next();
    if (i != 0){
      i = 0;
   
      localStringBuilder.append(URLEncoder.encode(str) + "=" + URLEncoder.encode(String.valueOf(paramHashMap.get(str))));
      
      localStringBuilder.append("&");
    }
  return localStringBuilder.toString();
  }

  public static String generateSignature(String paramString1, String paramString2, String paramString3)
  {
	   String HMAC_SHA1 ="HmacSHA1"; 
	    byte[] byteHMAC = null;
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            StringBuilder localStringBuilder = new StringBuilder(String.valueOf(encode(paramString2))).append("&");
            localStringBuilder.append(paramString1);
            SecretKeySpec spec;
            if (null == paramString3) {
               // String oauthSignature = encode(consumerSecret) + "&";
            //String oauthSignature = encode(paramString2) + "&" + encode(paramString1);
                // spec = new SecretKeySpec(oauthSignature.getBytes(), HMAC_SHA1);
                //spec = new SecretKeySpec(oauthSignature.toString().getBytes(), "HmacSHA1");
            	
            	
            	String oauthSignature = encode(paramString2) + "&";
            	spec = new SecretKeySpec(oauthSignature.getBytes(), HMAC_SHA1);
            } else {
             
            	spec = new SecretKeySpec(encode(paramString3).getBytes(), "HmacSHA1");
            }
            mac.init(spec);
            byteHMAC = mac.doFinal(paramString1.toString().getBytes());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException ignore) {
            // should never happen
        } 
        return new BASE64Encoder().encode(byteHMAC);  
	  
	
    
    
  }

  public static Map<String, Object> generateURLParams(String paramString1, String paramString2, Map<String, Object> paramMap, String paramString3, String paramString4, String paramString5)
  {
    if (paramMap == null)
      paramMap = new HashMap();
    long l = System.currentTimeMillis() / 1000L;
    String str1 = md5(String.valueOf(l + new Random().nextInt()));
    paramMap.put("oauth_consumer_key", paramString3);
    paramMap.put("oauth_signature_method", "HMAC-SHA1");
    String str2 = String.valueOf(l);
    paramMap.put("oauth_timestamp", str2);
    paramMap.put("oauth_nonce", str1);
    paramMap.put("oauth_version", "1.0");
    String str3 = Setting.getInstance().getManufacturerName() + "$!" + Setting.getInstance().getDeviceName();
    paramMap.put("device_name", str3);
    StringBuffer localStringBuffer1 = new StringBuffer();
    Iterator localIterator = new TreeSet(paramMap.keySet()).iterator();
    while (localIterator.hasNext())
    { 
      String str4 = (String)localIterator.next();
      if ((paramMap.get(str4) instanceof File))
        continue;
      if (localStringBuffer1.length() != 0)
        localStringBuffer1.append("&");
      localStringBuffer1.append(encode(str4));
      localStringBuffer1.append("=");
      localStringBuffer1.append(encode((String)paramMap.get(str4)));
    }  
	StringBuffer localStringBuffer2 = new StringBuffer(paramString2).append("&").append(encode(constructRequestURL(paramString1)));
	if (localStringBuffer1.length() > 0)
	    localStringBuffer2.append("&").append(encode(localStringBuffer1.toString()));
	String str5 = generateSignature(localStringBuffer2.toString(), paramString4, paramString5);
	  //String str5 = "z+6NTz4KgLbeLqU5OgGy+rOBvzI=";
	paramMap.put("oauth_signature", str5);
	return paramMap;
    
  }

  public static Map<String, Object> getParams(String paramString1, String paramString2, String paramString3, String[] paramArrayOfString, String paramString4, String paramString5)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("ctype", Setting.getInstance().getCType());
    localHashMap.put("x_auth_mode", "client_auth");
    localHashMap.put("x_auth_username", paramString2);
    localHashMap.put("x_auth_password", paramString3);
    localHashMap.put("getsimi", "1");
    localHashMap.put("device_name", Setting.getInstance().getManufacturerName() + "$!" + Setting.getInstance().getDeviceName());
    if (!TextUtils.isEmpty(paramString4))
      localHashMap.put("rcode", paramString4);
    if (!TextUtils.isEmpty(paramString5))
      localHashMap.put("code", paramString5);
    if ((paramArrayOfString != null) && (paramArrayOfString.length > 0))
      localHashMap.put("scope", TextUtils.join(" ", paramArrayOfString));
    return generateURLParams(paramString1, "POST", localHashMap, CONSUMER_KEY, CONSUMER_SECRET, null);
  }

  public static String getParamsForEmail(String paramString)
  {
    String str1 = "POST" + "&" + encode("/users/sendpwd");
    String str2 = encode(paramString).toString();
    return generateSignature(str1 + "&" + str2, CONSUMER_SECRET, null);
  }

  public static String getParamsForLook(String paramString)
  {
    String str1 = "GET" + "&" + encode("/users/defaulttoken");
    String str2 = encode(paramString).toString();
    return generateSignature(str1 + "&" + str2, CONSUMER_SECRET, null);
  }

  public static Map<String, Object> getQQParams(String paramString1, String paramString2, String paramString3, String paramString4, String[] paramArrayOfString, String paramString5, String paramString6)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("ctype", Setting.getInstance().getCType());
    localHashMap.put("x_auth_mode", "client_auth");
    localHashMap.put("tencent_token", paramString2);
    localHashMap.put("expire", paramString3);
    localHashMap.put("openid", paramString4);
    localHashMap.put("getsimi", "1");
    localHashMap.put("device_name", Setting.getInstance().getManufacturerName() + "$!" + Setting.getInstance().getDeviceName());
    if (!TextUtils.isEmpty(paramString5))
      localHashMap.put("rcode", paramString5);
    if (!TextUtils.isEmpty(paramString6))
      localHashMap.put("code", paramString6);
    if ((paramArrayOfString != null) && (paramArrayOfString.length > 0))
      localHashMap.put("scope", TextUtils.join(" ", paramArrayOfString));
    return generateURLParams(paramString1, "POST", localHashMap, CONSUMER_KEY, CONSUMER_SECRET, null);
  }

  public static Map<String, Object> getWEIBOParams(String paramString1, String paramString2, String paramString3, String paramString4, String[] paramArrayOfString, String paramString5, String paramString6)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("ctype", Setting.getInstance().getCType());
    localHashMap.put("x_auth_mode", "client_auth");
    localHashMap.put("weibo_token", paramString2);
    localHashMap.put("expire", paramString3);
    localHashMap.put("indentity_id", paramString4);
    localHashMap.put("getsimi", "1");
    localHashMap.put("device_name", Setting.getInstance().getManufacturerName() + "$!" + Setting.getInstance().getDeviceName());
    if (!TextUtils.isEmpty(paramString5))
      localHashMap.put("rcode", paramString5);
    if (!TextUtils.isEmpty(paramString6))
      localHashMap.put("code", paramString6);
    if ((paramArrayOfString != null) && (paramArrayOfString.length > 0))
      localHashMap.put("scope", TextUtils.join(" ", paramArrayOfString));
    return generateURLParams(paramString1, "POST", localHashMap, CONSUMER_KEY, CONSUMER_SECRET, null);
  }

  public static String md5(String paramString)
  {
    Object localObject = paramString;
    if (paramString != null);
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramString.getBytes());
      localObject = new BigInteger(1, localMessageDigest.digest()).toString(16);
      if (((String)localObject).length() % 2 != 0)
      {
        String str = "0" + (String)localObject;
        localObject = str;
      }
      return localObject.toString();
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      localNoSuchAlgorithmException.printStackTrace();
    }
    return (String)localObject;
  }

  static class  BASE64Encoder {
	    private static final char last2byte = (char) Integer.parseInt("00000011", 2);
	    private static final char last4byte = (char) Integer.parseInt("00001111", 2);
	    private static final char last6byte = (char) Integer.parseInt("00111111", 2);
	    private static final char lead6byte = (char) Integer.parseInt("11111100", 2);
	    private static final char lead4byte = (char) Integer.parseInt("11110000", 2);
	    private static final char lead2byte = (char) Integer.parseInt("11000000", 2);
	    private static final char[] encodeTable = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

	    public BASE64Encoder() {
	    }

	    public  String encode(byte[] from) {
	        StringBuffer to = new StringBuffer((int) (from.length * 1.34) + 3);
	        int num = 0;
	        char currentByte = 0;
	        for (int i = 0; i < from.length; i++) {
	            num = num % 8;
	            while (num < 8) {
	                switch (num) {
	                    case 0:
	                        currentByte = (char) (from[i] & lead6byte);
	                        currentByte = (char) (currentByte >>> 2);
	                        break;
	                    case 2:
	                        currentByte = (char) (from[i] & last6byte);
	                        break;
	                    case 4:
	                        currentByte = (char) (from[i] & last4byte);
	                        currentByte = (char) (currentByte << 2);
	                        if ((i + 1) < from.length) {
	                            currentByte |= (from[i + 1] & lead2byte) >>> 6;
	                        }
	                        break;
	                    case 6:
	                        currentByte = (char) (from[i] & last2byte);
	                        currentByte = (char) (currentByte << 4);
	                        if ((i + 1) < from.length) {
	                            currentByte |= (from[i + 1] & lead4byte) >>> 4;
	                        }
	                        break;
	                }
	                to.append(encodeTable[currentByte]);
	                num += 6;
	            }
	        }
	        if (to.length() % 4 != 0) {
	            for (int i = 4 - to.length() % 4; i > 0; i--) {
	                to.append("=");
	            }
	        }
	        return to.toString();
	    }
	}


  
  public static void main(String [] args){
	  String paramString1 = "http://api.kaixin001.com/oauth/access_token";
	  HashMap<String,Object> localHashMap = new HashMap<String,Object>();
	   String CONSUMER_SECRET = "8207525c8aa35c89b29385057f5905c9";
	   localHashMap.put("getsimi", "1");
      localHashMap.put("device_name", "HUAWEI$!H30-T00");
      localHashMap.put("x_auth_username", "13916913181");
      localHashMap.put("x_auth_mode", "client_auth");
      localHashMap.put("oauth_version", "1.0");
      localHashMap.put("oauth_nonce", "1bec99b3ec650c3422a91b3725ed2578");
      localHashMap.put("oauth_signature_method", "HMAC-SHA1");
      localHashMap.put("oauth_consumer_key", "87247717949570179fa41c43e20ed289");
      localHashMap.put("ctype", "03403AndroidClient");
      localHashMap.put("x_auth_password", "461670aa");      
      localHashMap.put("oauth_timestamp", "1413097937");
      
      StringBuffer localStringBuffer1 = new StringBuffer();
      Iterator localIterator = new TreeSet(localHashMap.keySet()).iterator();
      while (localIterator.hasNext())
      { 
        String str4 = (String)localIterator.next();
        if ((localHashMap.get(str4) instanceof File))
          continue;
        if (localStringBuffer1.length() != 0)
          localStringBuffer1.append("&");
        localStringBuffer1.append(encode(str4));
        localStringBuffer1.append("=");
        localStringBuffer1.append(encode((String)localHashMap.get(str4)));
      }  
  	StringBuffer localStringBuffer2 = new StringBuffer("POST").append("&").append(encode(constructRequestURL(paramString1)));
  	if (localStringBuffer1.length() > 0)
  	    localStringBuffer2.append("&").append(encode(localStringBuffer1.toString()));
  	try{
  	 	String str5 = generateSignature(localStringBuffer2.toString(), CONSUMER_SECRET, null);
    	  //String str5 = "z+6NTz4KgLbeLqU5OgGy+rOBvzI=";
    	//paramMap.put("oauth_signature", str5);
    	System.out.println("oauth_signature="+str5);
    			//rovIHK61cTd3nyPTNacN4GZxn98=
  	}
  	catch(Exception e){
  		e.printStackTrace();
  	}
 
	  
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.network.XAuth
 * JD-Core Version:    0.6.0
 */