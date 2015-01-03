package com.tencent.tauth;

import com.tencent.tauth.http.Callback;

public class JsInterface
{
  private Callback mCallback;

  public JsInterface(Callback paramCallback)
  {
    this.mCallback = paramCallback;
  }

  // ERROR //
  public void onAddShare(java.lang.String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 27	com/tencent/tauth/http/ParseResoneJson:parseJson	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   4: astore 5
    //   6: ldc 29
    //   8: astore 6
    //   10: aload 5
    //   12: ldc 31
    //   14: invokevirtual 37	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   17: istore 8
    //   19: aload 5
    //   21: ldc 39
    //   23: invokevirtual 43	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   26: astore 9
    //   28: aload 9
    //   30: astore 6
    //   32: iload 8
    //   34: ifne +15 -> 49
    //   37: aload_0
    //   38: getfield 13	com/tencent/tauth/JsInterface:mCallback	Lcom/tencent/tauth/http/Callback;
    //   41: aload 5
    //   43: invokeinterface 49 2 0
    //   48: return
    //   49: aload_0
    //   50: getfield 13	com/tencent/tauth/JsInterface:mCallback	Lcom/tencent/tauth/http/Callback;
    //   53: iload 8
    //   55: aload 6
    //   57: invokeinterface 53 3 0
    //   62: return
    //   63: astore 4
    //   65: aload_0
    //   66: getfield 13	com/tencent/tauth/JsInterface:mCallback	Lcom/tencent/tauth/http/Callback;
    //   69: ldc 54
    //   71: aload 4
    //   73: invokevirtual 58	java/lang/NumberFormatException:getMessage	()Ljava/lang/String;
    //   76: invokeinterface 53 3 0
    //   81: aload 4
    //   83: invokevirtual 61	java/lang/NumberFormatException:printStackTrace	()V
    //   86: return
    //   87: astore_3
    //   88: aload_0
    //   89: getfield 13	com/tencent/tauth/JsInterface:mCallback	Lcom/tencent/tauth/http/Callback;
    //   92: ldc 54
    //   94: aload_3
    //   95: invokevirtual 62	org/json/JSONException:getMessage	()Ljava/lang/String;
    //   98: invokeinterface 53 3 0
    //   103: aload_3
    //   104: invokevirtual 63	org/json/JSONException:printStackTrace	()V
    //   107: return
    //   108: astore_2
    //   109: aload_0
    //   110: getfield 13	com/tencent/tauth/JsInterface:mCallback	Lcom/tencent/tauth/http/Callback;
    //   113: ldc 54
    //   115: aload_2
    //   116: invokevirtual 64	com/tencent/tauth/http/CommonException:getMessage	()Ljava/lang/String;
    //   119: invokeinterface 53 3 0
    //   124: aload_2
    //   125: invokevirtual 65	com/tencent/tauth/http/CommonException:printStackTrace	()V
    //   128: return
    //   129: astore 7
    //   131: iconst_m1
    //   132: istore 8
    //   134: goto -102 -> 32
    //
    // Exception table:
    //   from	to	target	type
    //   0	6	63	java/lang/NumberFormatException
    //   10	28	63	java/lang/NumberFormatException
    //   37	48	63	java/lang/NumberFormatException
    //   49	62	63	java/lang/NumberFormatException
    //   0	6	87	org/json/JSONException
    //   37	48	87	org/json/JSONException
    //   49	62	87	org/json/JSONException
    //   0	6	108	com/tencent/tauth/http/CommonException
    //   10	28	108	com/tencent/tauth/http/CommonException
    //   37	48	108	com/tencent/tauth/http/CommonException
    //   49	62	108	com/tencent/tauth/http/CommonException
    //   10	28	129	org/json/JSONException
  }

  public void onCancelAddShare(int paramInt)
  {
    this.mCallback.onCancel(paramInt);
  }

  public void onCancelGift()
  {
    this.mCallback.onCancel(0);
  }

  public void onCancelInvite()
  {
    this.mCallback.onCancel(0);
  }

  public void onCancelLogin()
  {
    this.mCallback.onCancel(0);
  }

  public void onCancelRequest()
  {
    this.mCallback.onCancel(0);
  }

  // ERROR //
  public void onGift(java.lang.String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 27	com/tencent/tauth/http/ParseResoneJson:parseJson	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   4: astore 5
    //   6: ldc 29
    //   8: astore 6
    //   10: aload 5
    //   12: ldc 31
    //   14: invokevirtual 37	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   17: istore 8
    //   19: aload 5
    //   21: ldc 39
    //   23: invokevirtual 43	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   26: astore 9
    //   28: aload 9
    //   30: astore 6
    //   32: iload 8
    //   34: ifne +15 -> 49
    //   37: aload_0
    //   38: getfield 13	com/tencent/tauth/JsInterface:mCallback	Lcom/tencent/tauth/http/Callback;
    //   41: aload 5
    //   43: invokeinterface 49 2 0
    //   48: return
    //   49: aload_0
    //   50: getfield 13	com/tencent/tauth/JsInterface:mCallback	Lcom/tencent/tauth/http/Callback;
    //   53: iload 8
    //   55: aload 6
    //   57: invokeinterface 53 3 0
    //   62: return
    //   63: astore 4
    //   65: aload_0
    //   66: getfield 13	com/tencent/tauth/JsInterface:mCallback	Lcom/tencent/tauth/http/Callback;
    //   69: ldc 54
    //   71: aload 4
    //   73: invokevirtual 58	java/lang/NumberFormatException:getMessage	()Ljava/lang/String;
    //   76: invokeinterface 53 3 0
    //   81: aload 4
    //   83: invokevirtual 61	java/lang/NumberFormatException:printStackTrace	()V
    //   86: return
    //   87: astore_3
    //   88: aload_0
    //   89: getfield 13	com/tencent/tauth/JsInterface:mCallback	Lcom/tencent/tauth/http/Callback;
    //   92: ldc 54
    //   94: aload_3
    //   95: invokevirtual 62	org/json/JSONException:getMessage	()Ljava/lang/String;
    //   98: invokeinterface 53 3 0
    //   103: aload_3
    //   104: invokevirtual 63	org/json/JSONException:printStackTrace	()V
    //   107: return
    //   108: astore_2
    //   109: aload_0
    //   110: getfield 13	com/tencent/tauth/JsInterface:mCallback	Lcom/tencent/tauth/http/Callback;
    //   113: ldc 54
    //   115: aload_2
    //   116: invokevirtual 64	com/tencent/tauth/http/CommonException:getMessage	()Ljava/lang/String;
    //   119: invokeinterface 53 3 0
    //   124: aload_2
    //   125: invokevirtual 65	com/tencent/tauth/http/CommonException:printStackTrace	()V
    //   128: return
    //   129: astore 7
    //   131: iconst_m1
    //   132: istore 8
    //   134: goto -102 -> 32
    //
    // Exception table:
    //   from	to	target	type
    //   0	6	63	java/lang/NumberFormatException
    //   10	28	63	java/lang/NumberFormatException
    //   37	48	63	java/lang/NumberFormatException
    //   49	62	63	java/lang/NumberFormatException
    //   0	6	87	org/json/JSONException
    //   37	48	87	org/json/JSONException
    //   49	62	87	org/json/JSONException
    //   0	6	108	com/tencent/tauth/http/CommonException
    //   10	28	108	com/tencent/tauth/http/CommonException
    //   37	48	108	com/tencent/tauth/http/CommonException
    //   49	62	108	com/tencent/tauth/http/CommonException
    //   10	28	129	org/json/JSONException
  }

  // ERROR //
  public void onInvite(java.lang.String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 27	com/tencent/tauth/http/ParseResoneJson:parseJson	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   4: astore 5
    //   6: ldc 29
    //   8: astore 6
    //   10: aload 5
    //   12: ldc 31
    //   14: invokevirtual 37	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   17: istore 8
    //   19: aload 5
    //   21: ldc 39
    //   23: invokevirtual 43	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   26: astore 9
    //   28: aload 9
    //   30: astore 6
    //   32: iload 8
    //   34: ifne +15 -> 49
    //   37: aload_0
    //   38: getfield 13	com/tencent/tauth/JsInterface:mCallback	Lcom/tencent/tauth/http/Callback;
    //   41: aload 5
    //   43: invokeinterface 49 2 0
    //   48: return
    //   49: aload_0
    //   50: getfield 13	com/tencent/tauth/JsInterface:mCallback	Lcom/tencent/tauth/http/Callback;
    //   53: iload 8
    //   55: aload 6
    //   57: invokeinterface 53 3 0
    //   62: return
    //   63: astore 4
    //   65: aload_0
    //   66: getfield 13	com/tencent/tauth/JsInterface:mCallback	Lcom/tencent/tauth/http/Callback;
    //   69: ldc 54
    //   71: aload 4
    //   73: invokevirtual 58	java/lang/NumberFormatException:getMessage	()Ljava/lang/String;
    //   76: invokeinterface 53 3 0
    //   81: aload 4
    //   83: invokevirtual 61	java/lang/NumberFormatException:printStackTrace	()V
    //   86: return
    //   87: astore_3
    //   88: aload_0
    //   89: getfield 13	com/tencent/tauth/JsInterface:mCallback	Lcom/tencent/tauth/http/Callback;
    //   92: ldc 54
    //   94: aload_3
    //   95: invokevirtual 62	org/json/JSONException:getMessage	()Ljava/lang/String;
    //   98: invokeinterface 53 3 0
    //   103: aload_3
    //   104: invokevirtual 63	org/json/JSONException:printStackTrace	()V
    //   107: return
    //   108: astore_2
    //   109: aload_0
    //   110: getfield 13	com/tencent/tauth/JsInterface:mCallback	Lcom/tencent/tauth/http/Callback;
    //   113: ldc 54
    //   115: aload_2
    //   116: invokevirtual 64	com/tencent/tauth/http/CommonException:getMessage	()Ljava/lang/String;
    //   119: invokeinterface 53 3 0
    //   124: aload_2
    //   125: invokevirtual 65	com/tencent/tauth/http/CommonException:printStackTrace	()V
    //   128: return
    //   129: astore 7
    //   131: iconst_m1
    //   132: istore 8
    //   134: goto -102 -> 32
    //
    // Exception table:
    //   from	to	target	type
    //   0	6	63	java/lang/NumberFormatException
    //   10	28	63	java/lang/NumberFormatException
    //   37	48	63	java/lang/NumberFormatException
    //   49	62	63	java/lang/NumberFormatException
    //   0	6	87	org/json/JSONException
    //   37	48	87	org/json/JSONException
    //   49	62	87	org/json/JSONException
    //   0	6	108	com/tencent/tauth/http/CommonException
    //   10	28	108	com/tencent/tauth/http/CommonException
    //   37	48	108	com/tencent/tauth/http/CommonException
    //   49	62	108	com/tencent/tauth/http/CommonException
    //   10	28	129	org/json/JSONException
  }

  // ERROR //
  public void onRequest(java.lang.String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 27	com/tencent/tauth/http/ParseResoneJson:parseJson	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   4: astore 5
    //   6: ldc 29
    //   8: astore 6
    //   10: aload 5
    //   12: ldc 31
    //   14: invokevirtual 37	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   17: istore 8
    //   19: aload 5
    //   21: ldc 39
    //   23: invokevirtual 43	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   26: astore 9
    //   28: aload 9
    //   30: astore 6
    //   32: iload 8
    //   34: ifne +15 -> 49
    //   37: aload_0
    //   38: getfield 13	com/tencent/tauth/JsInterface:mCallback	Lcom/tencent/tauth/http/Callback;
    //   41: aload 5
    //   43: invokeinterface 49 2 0
    //   48: return
    //   49: aload_0
    //   50: getfield 13	com/tencent/tauth/JsInterface:mCallback	Lcom/tencent/tauth/http/Callback;
    //   53: iload 8
    //   55: aload 6
    //   57: invokeinterface 53 3 0
    //   62: return
    //   63: astore 4
    //   65: aload_0
    //   66: getfield 13	com/tencent/tauth/JsInterface:mCallback	Lcom/tencent/tauth/http/Callback;
    //   69: ldc 54
    //   71: aload 4
    //   73: invokevirtual 58	java/lang/NumberFormatException:getMessage	()Ljava/lang/String;
    //   76: invokeinterface 53 3 0
    //   81: aload 4
    //   83: invokevirtual 61	java/lang/NumberFormatException:printStackTrace	()V
    //   86: return
    //   87: astore_3
    //   88: aload_0
    //   89: getfield 13	com/tencent/tauth/JsInterface:mCallback	Lcom/tencent/tauth/http/Callback;
    //   92: ldc 54
    //   94: aload_3
    //   95: invokevirtual 62	org/json/JSONException:getMessage	()Ljava/lang/String;
    //   98: invokeinterface 53 3 0
    //   103: aload_3
    //   104: invokevirtual 63	org/json/JSONException:printStackTrace	()V
    //   107: return
    //   108: astore_2
    //   109: aload_0
    //   110: getfield 13	com/tencent/tauth/JsInterface:mCallback	Lcom/tencent/tauth/http/Callback;
    //   113: ldc 54
    //   115: aload_2
    //   116: invokevirtual 64	com/tencent/tauth/http/CommonException:getMessage	()Ljava/lang/String;
    //   119: invokeinterface 53 3 0
    //   124: aload_2
    //   125: invokevirtual 65	com/tencent/tauth/http/CommonException:printStackTrace	()V
    //   128: return
    //   129: astore 7
    //   131: iconst_m1
    //   132: istore 8
    //   134: goto -102 -> 32
    //
    // Exception table:
    //   from	to	target	type
    //   0	6	63	java/lang/NumberFormatException
    //   10	28	63	java/lang/NumberFormatException
    //   37	48	63	java/lang/NumberFormatException
    //   49	62	63	java/lang/NumberFormatException
    //   0	6	87	org/json/JSONException
    //   37	48	87	org/json/JSONException
    //   49	62	87	org/json/JSONException
    //   0	6	108	com/tencent/tauth/http/CommonException
    //   10	28	108	com/tencent/tauth/http/CommonException
    //   37	48	108	com/tencent/tauth/http/CommonException
    //   49	62	108	com/tencent/tauth/http/CommonException
    //   10	28	129	org/json/JSONException
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.JsInterface
 * JD-Core Version:    0.6.0
 */