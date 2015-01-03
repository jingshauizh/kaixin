package com.kaixin001.util;

import android.text.TextUtils;
import com.kaixin001.util.LruFileCache.LruCache<Ljava.lang.String;Ljava.lang.Long;>;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LruFileCache
{
  private static final int DEFAULT_CACHE_SIZE = 31457280;
  private static final String TAG = "LruFileCache";
  private static LruFileCache instance;
  private LruCache<String, Long> mCurLruCache = null;
  private HashMap<String, LruCache<String, Long>> mLruCacheList = new HashMap();

  private void cacheFile(String paramString)
  {
    String str1 = geFileBaseDir(paramString);
    String str2 = getFileName(paramString);
    if ((!TextUtils.isEmpty(str1)) && (!TextUtils.isEmpty(str2)))
    {
      LruCache localLruCache = getCacheDir(str1, 31457280);
      if (localLruCache.size() == 0)
        initLruFileCache(str1, localLruCache);
      File localFile = new File(str1 + str2);
      if ((localFile != null) && (localFile.exists()) && (localFile.isFile()))
      {
        localFile.setLastModified(System.currentTimeMillis() / 1000L);
        localLruCache.put(str2, Long.valueOf(localFile.length()));
      }
    }
  }

  private String geFileBaseDir(String paramString)
  {
    File localFile = new File(paramString);
    String str = null;
    if (localFile != null)
    {
      str = localFile.getParent();
      if (!str.endsWith("/"))
        str = str + "/";
    }
    return str;
  }

  private String getFileName(String paramString)
  {
    File localFile = new File(paramString);
    String str = null;
    if (localFile != null)
      str = localFile.getName();
    return str;
  }

  public static LruFileCache getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new LruFileCache();
      LruFileCache localLruFileCache = instance;
      return localLruFileCache;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void initLruFileCache(String paramString, LruCache<String, Long> paramLruCache)
  {
    File localFile = new File(paramString);
    File[] arrayOfFile;
    ArrayList localArrayList1;
    ArrayList localArrayList2;
    int k;
    if ((localFile != null) && (localFile.exists()) && (localFile.isDirectory()))
    {
      arrayOfFile = localFile.listFiles();
      localArrayList1 = new ArrayList();
      localArrayList2 = new ArrayList();
      if ((arrayOfFile != null) && (arrayOfFile.length > 0))
      {
        int j = arrayOfFile.length;
        k = 0;
        if (k < j)
          break label91;
      }
    }
    for (int i = 0; ; i++)
    {
      if (i >= localArrayList1.size())
      {
        return;
        label91: String str = arrayOfFile[k].getName();
        Long localLong = Long.valueOf(localFile.lastModified());
        label207: for (int m = 0; ; m++)
        {
          int n = localArrayList1.size();
          int i1 = 0;
          if (m >= n);
          while (true)
          {
            if (i1 == 0)
            {
              localArrayList1.add(str);
              localArrayList2.add(localLong);
            }
            k++;
            break;
            long l = ((Long)localArrayList2.get(m)).longValue();
            if (localLong.longValue() <= l)
              break label207;
            localArrayList1.add(m, str);
            localArrayList2.add(m, localLong);
            i1 = 1;
          }
        }
      }
      paramLruCache.put((String)localArrayList1.get(i), Long.valueOf(arrayOfFile[i].length()));
    }
  }

  public LruCache<String, Long> getCacheDir(String paramString, int paramInt)
  {
    if (paramInt <= 0)
      paramInt = 31457280;
    boolean bool = TextUtils.isEmpty(paramString);
    Object localObject = null;
    if (!bool)
    {
      localObject = (LruCache)this.mLruCacheList.get(paramString);
      if (localObject == null)
      {
        localObject = new LruCache(paramInt, paramString)
        {
          protected void entryRemoved(boolean paramBoolean, String paramString, Long paramLong1, Long paramLong2)
          {
            File localFile = new File(this.val$dir + paramString);
            if ((localFile != null) && (localFile.isFile()))
              localFile.delete();
          }

          protected int sizeOf(String paramString, Long paramLong)
          {
            return (int)paramLong.longValue();
          }
        };
        this.mLruCacheList.put(paramString, localObject);
      }
    }
    this.mCurLruCache = ((LruCache)localObject);
    return (LruCache<String, Long>)localObject;
  }

  // ERROR //
  public File getFile(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: invokestatic 45	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   6: istore_3
    //   7: aconst_null
    //   8: astore 4
    //   10: iload_3
    //   11: ifne +50 -> 61
    //   14: new 61	java/io/File
    //   17: dup
    //   18: aload_1
    //   19: invokespecial 80	java/io/File:<init>	(Ljava/lang/String;)V
    //   22: astore 5
    //   24: aload 5
    //   26: ifnull +53 -> 79
    //   29: aload 5
    //   31: invokevirtual 84	java/io/File:exists	()Z
    //   34: ifeq +45 -> 79
    //   37: aload 5
    //   39: invokevirtual 87	java/io/File:isFile	()Z
    //   42: ifeq +37 -> 79
    //   45: aload_0
    //   46: getfield 30	com/kaixin001/util/LruFileCache:mCurLruCache	Lcom/kaixin001/util/LruFileCache$LruCache;
    //   49: ifnull +17 -> 66
    //   52: aload_0
    //   53: aload_1
    //   54: invokevirtual 170	com/kaixin001/util/LruFileCache:updateFile	(Ljava/lang/String;)V
    //   57: aload 5
    //   59: astore 4
    //   61: aload_0
    //   62: monitorexit
    //   63: aload 4
    //   65: areturn
    //   66: aload 5
    //   68: invokestatic 93	java/lang/System:currentTimeMillis	()J
    //   71: ldc2_w 94
    //   74: ldiv
    //   75: invokevirtual 99	java/io/File:setLastModified	(J)Z
    //   78: pop
    //   79: aload 5
    //   81: astore 4
    //   83: goto -22 -> 61
    //   86: astore_2
    //   87: aload_0
    //   88: monitorexit
    //   89: aload_2
    //   90: athrow
    //   91: astore_2
    //   92: goto -5 -> 87
    //
    // Exception table:
    //   from	to	target	type
    //   2	7	86	finally
    //   14	24	86	finally
    //   29	57	91	finally
    //   66	79	91	finally
  }

  // ERROR //
  public boolean removeFile(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: invokestatic 45	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   6: ifne +80 -> 86
    //   9: new 61	java/io/File
    //   12: dup
    //   13: aload_1
    //   14: invokespecial 80	java/io/File:<init>	(Ljava/lang/String;)V
    //   17: astore_3
    //   18: aload_3
    //   19: ifnull +76 -> 95
    //   22: aload_3
    //   23: invokevirtual 84	java/io/File:exists	()Z
    //   26: ifeq +69 -> 95
    //   29: aload_3
    //   30: invokevirtual 87	java/io/File:isFile	()Z
    //   33: ifeq +62 -> 95
    //   36: aload_0
    //   37: getfield 30	com/kaixin001/util/LruFileCache:mCurLruCache	Lcom/kaixin001/util/LruFileCache$LruCache;
    //   40: ifnull +50 -> 90
    //   43: aload_0
    //   44: aload_1
    //   45: invokespecial 36	com/kaixin001/util/LruFileCache:geFileBaseDir	(Ljava/lang/String;)Ljava/lang/String;
    //   48: astore 5
    //   50: aload_0
    //   51: aload_1
    //   52: invokespecial 39	com/kaixin001/util/LruFileCache:getFileName	(Ljava/lang/String;)Ljava/lang/String;
    //   55: astore 6
    //   57: aload 5
    //   59: invokestatic 45	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   62: ifne +33 -> 95
    //   65: aload 6
    //   67: invokestatic 45	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   70: ifne +25 -> 95
    //   73: aload_0
    //   74: aload 5
    //   76: iconst_m1
    //   77: invokevirtual 49	com/kaixin001/util/LruFileCache:getCacheDir	(Ljava/lang/String;I)Lcom/kaixin001/util/LruFileCache$LruCache;
    //   80: aload 6
    //   82: invokevirtual 174	com/kaixin001/util/LruFileCache$LruCache:remove	(Ljava/lang/Object;)Ljava/lang/Object;
    //   85: pop
    //   86: aload_0
    //   87: monitorexit
    //   88: iconst_0
    //   89: ireturn
    //   90: aload_3
    //   91: invokevirtual 177	java/io/File:delete	()Z
    //   94: pop
    //   95: goto -9 -> 86
    //   98: astore_2
    //   99: aload_0
    //   100: monitorexit
    //   101: aload_2
    //   102: athrow
    //   103: astore_2
    //   104: goto -5 -> 99
    //
    // Exception table:
    //   from	to	target	type
    //   2	18	98	finally
    //   22	86	103	finally
    //   90	95	103	finally
  }

  public void updateFile(String paramString)
  {
    monitorenter;
    try
    {
      String str1 = geFileBaseDir(paramString);
      String str2 = getFileName(paramString);
      if ((!TextUtils.isEmpty(str1)) && (!TextUtils.isEmpty(str2)))
      {
        LruCache localLruCache = getCacheDir(str1, -1);
        if ((localLruCache != null) && ((Long)localLruCache.get(str2) == null))
          cacheFile(paramString);
      }
      return;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public class LruCache<K, V>
  {
    private int mEvictionCount;
    private int mHitCount;
    private LruFileCache.onLruCacheRemoveItemListener mListener;
    private final LinkedHashMap<K, V> mMap;
    private int mMaxSize;
    private int mMissCount;
    private int mPutCount;
    private int mSize;

    public LruCache(int arg2)
    {
      int i;
      if (i <= 0)
        throw new IllegalArgumentException("maxSize <= 0");
      this.mMaxSize = i;
      this.mMap = new LinkedHashMap(0, 0.75F, true);
    }

    private int safeSizeOf(K paramK, V paramV)
    {
      int i = sizeOf(paramK, paramV);
      if (i < 0)
        throw new IllegalStateException("Negative size: " + paramK + "=" + paramV);
      return i;
    }

    // ERROR //
    private void trimToSize(int paramInt)
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield 73	com/kaixin001/util/LruFileCache$LruCache:mSize	I
      //   6: iflt +20 -> 26
      //   9: aload_0
      //   10: getfield 43	com/kaixin001/util/LruFileCache$LruCache:mMap	Ljava/util/LinkedHashMap;
      //   13: invokevirtual 77	java/util/LinkedHashMap:isEmpty	()Z
      //   16: ifeq +48 -> 64
      //   19: aload_0
      //   20: getfield 73	com/kaixin001/util/LruFileCache$LruCache:mSize	I
      //   23: ifeq +41 -> 64
      //   26: new 50	java/lang/IllegalStateException
      //   29: dup
      //   30: new 52	java/lang/StringBuilder
      //   33: dup
      //   34: aload_0
      //   35: invokespecial 81	java/lang/Object:getClass	()Ljava/lang/Class;
      //   38: invokevirtual 86	java/lang/Class:getName	()Ljava/lang/String;
      //   41: invokestatic 92	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
      //   44: invokespecial 55	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   47: ldc 94
      //   49: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   52: invokevirtual 68	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   55: invokespecial 69	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
      //   58: athrow
      //   59: astore_2
      //   60: aload_0
      //   61: monitorexit
      //   62: aload_2
      //   63: athrow
      //   64: aload_0
      //   65: getfield 73	com/kaixin001/util/LruFileCache$LruCache:mSize	I
      //   68: iload_1
      //   69: if_icmple +13 -> 82
      //   72: aload_0
      //   73: getfield 43	com/kaixin001/util/LruFileCache$LruCache:mMap	Ljava/util/LinkedHashMap;
      //   76: invokevirtual 77	java/util/LinkedHashMap:isEmpty	()Z
      //   79: ifeq +6 -> 85
      //   82: aload_0
      //   83: monitorexit
      //   84: return
      //   85: aload_0
      //   86: getfield 43	com/kaixin001/util/LruFileCache$LruCache:mMap	Ljava/util/LinkedHashMap;
      //   89: invokevirtual 98	java/util/LinkedHashMap:entrySet	()Ljava/util/Set;
      //   92: invokeinterface 104 1 0
      //   97: invokeinterface 110 1 0
      //   102: checkcast 112	java/util/Map$Entry
      //   105: astore_3
      //   106: aload_3
      //   107: invokeinterface 115 1 0
      //   112: astore 4
      //   114: aload_3
      //   115: invokeinterface 118 1 0
      //   120: astore 5
      //   122: aload_0
      //   123: getfield 43	com/kaixin001/util/LruFileCache$LruCache:mMap	Ljava/util/LinkedHashMap;
      //   126: aload 4
      //   128: invokevirtual 122	java/util/LinkedHashMap:remove	(Ljava/lang/Object;)Ljava/lang/Object;
      //   131: pop
      //   132: aload_0
      //   133: aload_0
      //   134: getfield 73	com/kaixin001/util/LruFileCache$LruCache:mSize	I
      //   137: aload_0
      //   138: aload 4
      //   140: aload 5
      //   142: invokespecial 124	com/kaixin001/util/LruFileCache$LruCache:safeSizeOf	(Ljava/lang/Object;Ljava/lang/Object;)I
      //   145: isub
      //   146: putfield 73	com/kaixin001/util/LruFileCache$LruCache:mSize	I
      //   149: aload_0
      //   150: iconst_1
      //   151: aload_0
      //   152: getfield 126	com/kaixin001/util/LruFileCache$LruCache:mEvictionCount	I
      //   155: iadd
      //   156: putfield 126	com/kaixin001/util/LruFileCache$LruCache:mEvictionCount	I
      //   159: aload_0
      //   160: monitorexit
      //   161: aload_0
      //   162: iconst_1
      //   163: aload 4
      //   165: aload 5
      //   167: aconst_null
      //   168: invokevirtual 130	com/kaixin001/util/LruFileCache$LruCache:entryRemoved	(ZLjava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
      //   171: goto -171 -> 0
      //
      // Exception table:
      //   from	to	target	type
      //   2	26	59	finally
      //   26	59	59	finally
      //   60	62	59	finally
      //   64	82	59	finally
      //   82	84	59	finally
      //   85	161	59	finally
    }

    protected V create(K paramK)
    {
      return null;
    }

    protected void entryRemoved(boolean paramBoolean, K paramK, V paramV1, V paramV2)
    {
      if (this.mListener != null)
        this.mListener.removeItem(paramK, paramV1);
    }

    public final void evictAll()
    {
      trimToSize(-1);
    }

    public final int evictionCount()
    {
      monitorenter;
      try
      {
        int i = this.mEvictionCount;
        monitorexit;
        return i;
      }
      finally
      {
        localObject = finally;
        monitorexit;
      }
      throw localObject;
    }

    public final V get(K paramK)
    {
      if (paramK == null)
        throw new NullPointerException("key == null");
      monitorenter;
      try
      {
        Object localObject2 = this.mMap.get(paramK);
        if (localObject2 != null)
        {
          this.mHitCount = (1 + this.mHitCount);
          return localObject2;
        }
        this.mMissCount = (1 + this.mMissCount);
        return localObject2;
      }
      finally
      {
        monitorexit;
      }
      throw localObject1;
    }

    public final int hitCount()
    {
      monitorenter;
      try
      {
        int i = this.mHitCount;
        monitorexit;
        return i;
      }
      finally
      {
        localObject = finally;
        monitorexit;
      }
      throw localObject;
    }

    public final int maxSize()
    {
      monitorenter;
      try
      {
        int i = this.mMaxSize;
        monitorexit;
        return i;
      }
      finally
      {
        localObject = finally;
        monitorexit;
      }
      throw localObject;
    }

    public final int missCount()
    {
      monitorenter;
      try
      {
        int i = this.mMissCount;
        monitorexit;
        return i;
      }
      finally
      {
        localObject = finally;
        monitorexit;
      }
      throw localObject;
    }

    public final V put(K paramK, V paramV)
    {
      if ((paramK == null) || (paramV == null))
        throw new NullPointerException("key == null || value == null");
      monitorenter;
      try
      {
        this.mPutCount = (1 + this.mPutCount);
        this.mSize += safeSizeOf(paramK, paramV);
        Object localObject2 = this.mMap.put(paramK, paramV);
        if (localObject2 != null)
          this.mSize -= safeSizeOf(paramK, localObject2);
        monitorexit;
        trimToSize(this.mMaxSize);
        return localObject2;
      }
      finally
      {
        monitorexit;
      }
      throw localObject1;
    }

    public final int putCount()
    {
      monitorenter;
      try
      {
        int i = this.mPutCount;
        monitorexit;
        return i;
      }
      finally
      {
        localObject = finally;
        monitorexit;
      }
      throw localObject;
    }

    public final V remove(K paramK)
    {
      if (paramK == null)
        throw new NullPointerException("key == null");
      monitorenter;
      try
      {
        Object localObject2 = this.mMap.remove(paramK);
        if (localObject2 != null)
          this.mSize -= safeSizeOf(paramK, localObject2);
        monitorexit;
        if (localObject2 != null)
          entryRemoved(false, paramK, localObject2, null);
        return localObject2;
      }
      finally
      {
        monitorexit;
      }
      throw localObject1;
    }

    public void setonLruCacheRemoveItemListener(LruFileCache.onLruCacheRemoveItemListener paramonLruCacheRemoveItemListener)
    {
      this.mListener = paramonLruCacheRemoveItemListener;
    }

    public final int size()
    {
      monitorenter;
      try
      {
        int i = this.mSize;
        monitorexit;
        return i;
      }
      finally
      {
        localObject = finally;
        monitorexit;
      }
      throw localObject;
    }

    protected int sizeOf(K paramK, V paramV)
    {
      return 1;
    }

    public final Map<K, V> snapshot()
    {
      monitorenter;
      try
      {
        LinkedHashMap localLinkedHashMap = new LinkedHashMap(this.mMap);
        monitorexit;
        return localLinkedHashMap;
      }
      finally
      {
        localObject = finally;
        monitorexit;
      }
      throw localObject;
    }

    public final String toString()
    {
      monitorenter;
      try
      {
        int i = this.mHitCount + this.mMissCount;
        int j = 0;
        if (i != 0)
          j = 100 * this.mHitCount / i;
        Object[] arrayOfObject = new Object[4];
        arrayOfObject[0] = Integer.valueOf(this.mMaxSize);
        arrayOfObject[1] = Integer.valueOf(this.mHitCount);
        arrayOfObject[2] = Integer.valueOf(this.mMissCount);
        arrayOfObject[3] = Integer.valueOf(j);
        String str = String.format("LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", arrayOfObject);
        return str;
      }
      finally
      {
        monitorexit;
      }
      throw localObject;
    }
  }

  public static abstract interface onLruCacheRemoveItemListener
  {
    public abstract void removeItem(Object paramObject1, Object paramObject2);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.LruFileCache
 * JD-Core Version:    0.6.0
 */