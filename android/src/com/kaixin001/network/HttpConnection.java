package com.kaixin001.network;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.kaixin001.activity.KXApplication;
import com.kaixin001.mime.SimpleMultipartEntity;
import com.kaixin001.mime.content.ContentBody;
import com.kaixin001.mime.content.FileBody;
import com.kaixin001.mime.content.InputStreamBody;
import com.kaixin001.mime.content.StringBody;
import com.kaixin001.util.KXLog;

public class HttpConnection
{
	private static final int BUFFER_SIZE = 1024;
	private static final String LOGTAG = "HttpConnection";
	private KXApplication app;
	private HttpClient client;
	private boolean isCancelDownLoad;
	private HttpUriRequest request;
	private String requestBoundary = null;

	public HttpConnection(Context paramContext)
	{
		this.app = ((KXApplication) paramContext.getApplicationContext());
		this.client = this.app.getHttpClient();
	}

	public static boolean checkNetworkAndHint(boolean paramBoolean,
			Activity paramActivity)
	{
		return false;
	}

	public static int checkNetworkAvailable(Context paramContext)
	{

		return 1;
	}

	private HttpGet getHttpGet(String paramString,
			Map<String, Object> paramMap, Header[] paramArrayOfHeader)
			throws HttpException
	{
		StringBuffer localStringBuffer = new StringBuffer(paramString);
		if (localStringBuffer.indexOf("?") == -1)
			localStringBuffer.append("?");
		Iterator localIterator = null;
		HttpGet localHttpGet = new HttpGet();
		if (paramMap != null)
		{
			localIterator = paramMap.keySet().iterator();
			while (localIterator.hasNext())
			{
				String str = (String) localIterator.next();
				if (paramMap.get(str) == null)
					continue;
				if (!localStringBuffer.toString().endsWith("?"))
					localStringBuffer.append("&");
				localStringBuffer
						.append(str)
						.append("=")
						.append(URLEncoder.encode(String.valueOf(paramMap
								.get(str))));

			}
			KXLog.d("HttpConnection", "---------------------->GET REQUEST URL:"
					+ localStringBuffer.toString());
		}
		if (paramArrayOfHeader != null)
		{
			localHttpGet.setHeaders(paramArrayOfHeader);
		}
		try
		{
			localHttpGet.setURI(new URI(localStringBuffer.toString()));
		}
		catch (URISyntaxException localURISyntaxException)
		{
			throw new HttpException("URISyntaxException",
					localURISyntaxException);
		}
		return localHttpGet;

	}

	// used
	private HttpGet getHttpGet2(String paramString,
			Map<String, Object> paramMap, Header[] paramArrayOfHeader)
			throws HttpException
	{
		StringBuffer localStringBuffer = new StringBuffer(paramString);
		if (localStringBuffer.indexOf("?") == -1)
			localStringBuffer.append("?");
		Iterator localIterator = null;
		if (paramMap != null)
			localIterator = paramMap.keySet().iterator();

		HttpGet localHttpGet = null;
		if (!localIterator.hasNext())
		{
			KXLog.d("HttpConnection", "---------------------->GET REQUEST URL:"
					+ localStringBuffer.toString());
			localHttpGet = new HttpGet();
			if (paramArrayOfHeader != null)
				localHttpGet.setHeaders(paramArrayOfHeader);
		}
		try
		{
			localHttpGet.setURI(new URI(localStringBuffer.toString()));

			String str = (String) localIterator.next();
			if (paramMap.get(str) == null)

				if (!localStringBuffer.toString().endsWith("?"))
					localStringBuffer.append("&");
			localStringBuffer
					.append(str)
					.append("=")
					.append(URLEncoder.encode(String.valueOf(paramMap.get(str))));
		}
		catch (URISyntaxException localURISyntaxException)
		{
			throw new HttpException("URISyntaxException",
					localURISyntaxException);
		}
		return localHttpGet;

	}

	private HttpPost getHttpPost(String paramString,
			Map<String, Object> paramMap, Header[] paramArrayOfHeader,
			HttpProgressListener paramHttpProgressListener)
			throws HttpException
	{
		StringBuffer localStringBuffer = new StringBuffer(paramString);
		// KxMultipartEntity localKxMultipartEntity = new
		// KxMultipartEntity(paramHttpProgressListener);
		// MultipartEntity localKxMultipartEntity = new
		// MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
		SimpleMultipartEntity localKxMultipartEntity = new SimpleMultipartEntity(
				this.requestBoundary, paramHttpProgressListener);
		Iterator localIterator = null;
		if (paramMap != null)
		{
			localIterator = paramMap.keySet().iterator();

		}

		String str;
		Object localObject = null;
		while (localIterator.hasNext())
		{
			str = (String) localIterator.next();
			if (paramMap.get(str) == null)
			{
				continue;
			}

			if ((paramMap.get(str) instanceof File))
			{
				localObject = new FileBody((File) paramMap.get(str),
						"image/jpeg");

			}
			else
			{
				try
				{
					localObject = new StringBody(String.valueOf(paramMap
							.get(str)), Charset.forName("UTF-8"));
					localObject = String.valueOf(paramMap.get(str));
				}
				catch (UnsupportedEncodingException localUnsupportedEncodingException)
				{
					throw new HttpException("UnsupportedEncodingException",
							localUnsupportedEncodingException);
				}
			}
			if ((paramMap.get(str) instanceof InputStreamBody))
			{
				localObject = (InputStreamBody) paramMap.get(str);

			}
			if (localObject instanceof ContentBody)
			{
				localKxMultipartEntity.addPart(str, (ContentBody) localObject);
			}
			else
			{
				localKxMultipartEntity.addPart(str, localObject.toString());
			}

			Log.e("HttpConnection", "---------------------->key:[" + str
					+ "] value:[" + paramMap.get(str) + "]");

		}
		KXLog.d("HttpConnection", "---------------------->POST REQUEST URL:"
				+ localStringBuffer.toString());
		HttpPost localHttpPost = new HttpPost(localStringBuffer.toString());
		if (paramArrayOfHeader != null)
			localHttpPost.setHeaders(paramArrayOfHeader);
		localHttpPost.setEntity(localKxMultipartEntity);
		Log.e("debug", localKxMultipartEntity.getContentType().toString());
		return localHttpPost;

	}

	@SuppressWarnings("unused")
	private HttpPost getRequest(String paramString, HttpMethod paramHttpMethod,
			Map<String, Object> paramMap, Header[] paramArrayOfHeader,
			HttpProgressListener paramHttpProgressListener)
			throws HttpException
	{
		HttpPost localHttpPost = null;
		if (paramString != null)
		{
			if (paramHttpMethod == HttpMethod.POST)
			{
				localHttpPost = getHttpPost(paramString, paramMap,
						paramArrayOfHeader, paramHttpProgressListener);
				return localHttpPost;
			}
		}
		return localHttpPost;
		// return getHttpGet(paramString, paramMap, paramArrayOfHeader);

	}

	public static String inputStreamToString(InputStream paramInputStream)
			throws Exception
	{
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		byte[] arrayOfByte = new byte[1024];
		while (true)
		{
			int i = paramInputStream.read(arrayOfByte, 0, 1024);
			if (i == -1)
				return new String(localByteArrayOutputStream.toByteArray());
			localByteArrayOutputStream.write(arrayOfByte, 0, i);
		}
	}

	public void abortRequest()
	{
		if (this.request != null)
			this.request.abort();
	}

	public void cancelDownLoad()
	{
		this.isCancelDownLoad = true;
	}

	// ERROR //
	public long getRemoteFileSize(String paramString) throws HttpException
	{
		return 1;
	}

	// ERROR //
	public boolean httpDownload(String paramString1, String paramString2,
			boolean paramBoolean, HttpRequestState paramHttpRequestState,
			HttpProgressListener paramHttpProgressListener)
			throws HttpException
	{
		return false;
	}

	// ERROR // invoked
	public String httpRequest(String paramString, HttpMethod paramHttpMethod,
			Map<String, Object> paramMap, Header[] paramArrayOfHeader,
			HttpRequestState paramHttpRequestState,
			HttpProgressListener paramHttpProgressListener)
			throws HttpException
	{
		Log
				.e("httpRequest",
						"222String httpRequest(String paramString, HttpMethod paramHttpMethod,");

		// HttpClient client = new DefaultHttpClient();
		// AndroidHttpClient client=AndroidHttpClient.newInstance("");
		// HttpGet httpGet =
		// getHttpGet(paramString,paramMap,paramArrayOfHeader);

		this.requestBoundary = generateBoundary();

		HttpPost httpGet1 = getRequest(paramString, paramHttpMethod, paramMap,
				paramArrayOfHeader, paramHttpProgressListener);

		BasicHeader header = new BasicHeader("Content-Type", getContentType(
				this.requestBoundary, null));
		JSONObject json = null;
		String responeStr = null;
		try
		{
			// HttpPost httpGet1 = getMyHttpPost();
			httpGet1.setHeader(header);
			HttpResponse res = client.execute(httpGet1);
			int statusCode = res.getStatusLine().getStatusCode();
			Log.e("status", String.valueOf(statusCode));
			// Log.e("response", res.);
			if (statusCode == HttpStatus.SC_OK)
			{
				HttpEntity entity = res.getEntity();
				// json = new JSONObject(new JSONTokener(new
				// InputStreamReader(entity.getContent(), HTTP.UTF_8)));
				responeStr = EntityUtils.toString(entity, HTTP.UTF_8);
				Log.e("httpRequest", "responeStr=" + responeStr);
				// json = new JSONObject(responeStr);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.e("debug", e.toString());
			throw new RuntimeException(e);

		}
		finally
		{

			// client.getConnectionManager().shutdown();
		}
		// return json.toString();
		return responeStr;

	}

	private final char[] MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
			.toCharArray();

	private String generateBoundary()
	{
		StringBuilder localStringBuilder = new StringBuilder();
		Random localRandom = new Random();
		int i = 30 + localRandom.nextInt(11);
		for (int j = 0;; j++)
		{
			if (j >= i)
				return localStringBuilder.toString();
			localStringBuilder.append(MULTIPART_CHARS[localRandom
					.nextInt(MULTIPART_CHARS.length)]);
		}
	}

	private String getContentType(String paramString, Charset paramCharset)
	{

		StringBuilder localStringBuilder = new StringBuilder();
		localStringBuilder.append("multipart/form-data; boundary=");
		localStringBuilder.append(paramString);
		if (paramCharset != null)
		{
			localStringBuilder.append("; charset=");
			localStringBuilder.append(paramCharset.name());
		}
		return localStringBuilder.toString();

	}

	private HttpPost getMyHttpPost() throws UnsupportedEncodingException
	{
		HttpPost httppost = new HttpPost(
				"http://api.kaixin001.com/oauth/access_token");

		// Add your data
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

		nameValuePairs.add(new BasicNameValuePair("getsimi", "1"));
		nameValuePairs.add(new BasicNameValuePair("oauth_signature",
				"KSpN/+Oi8HYocccchnEGYJ7PXyY="));
		nameValuePairs.add(new BasicNameValuePair("device_name",
				"HUAWEI$!H30-T00"));
		nameValuePairs.add(new BasicNameValuePair("x_auth_username",
				"13916913181"));
		nameValuePairs
				.add(new BasicNameValuePair("x_auth_mode", "client_auth"));
		nameValuePairs.add(new BasicNameValuePair("oauth_version", "1.0"));
		nameValuePairs.add(new BasicNameValuePair("oauth_nonce",
				"e31ec4bcef8621fd833e0142928bfdc8"));
		nameValuePairs.add(new BasicNameValuePair("oauth_signature_method",
				"HMAC-SHA1"));
		nameValuePairs.add(new BasicNameValuePair("oauth_consumer_key",
				"87247717949570179fa41c43e20ed289"));
		nameValuePairs
				.add(new BasicNameValuePair("ctype", "03403AndroidClient"));
		nameValuePairs
				.add(new BasicNameValuePair("x_auth_password", "461670aa"));
		long l = System.currentTimeMillis() / 1000L;
		String str2 = String.valueOf(l);
		nameValuePairs.add(new BasicNameValuePair("oauth_timestamp", str2));

		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		return httppost;
	}

	// ERROR //
	public String httpRequest(HttpGet paramHttpGet)
	{
		return null;
	}
}

/*
 * Location:
 * C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name: com.kaixin001.network.HttpConnection JD-Core Version: 0.6.0
 */