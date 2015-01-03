package com.tencent.mm.sdk;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.Cursor;
import android.net.Uri;
import com.tencent.mm.sdk.plugin.MMPluginProviderConstants.Resolver;
import com.tencent.mm.sdk.plugin.MMPluginProviderConstants.SharedPref;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MMSharedPreferences
  implements SharedPreferences
{
  private final String[] columns = { "_id", "key", "type", "value" };
  private final ContentResolver i;
  private final HashMap<String, Object> j = new HashMap();
  private REditor k = null;

  public MMSharedPreferences(Context paramContext)
  {
    this.i = paramContext.getContentResolver();
  }

  private Object getValue(String paramString)
  {
    while (true)
    {
      try
      {
        Cursor localCursor = this.i.query(MMPluginProviderConstants.SharedPref.CONTENT_URI, this.columns, "key = ?", new String[] { paramString }, null);
        if (localCursor == null)
          return null;
        int m = localCursor.getColumnIndex("type");
        int n = localCursor.getColumnIndex("value");
        if (localCursor.moveToFirst())
        {
          localObject = MMPluginProviderConstants.Resolver.resolveObj(localCursor.getInt(m), localCursor.getString(n));
          localCursor.close();
          return localObject;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      Object localObject = null;
    }
  }

  public boolean contains(String paramString)
  {
    return getValue(paramString) != null;
  }

  public SharedPreferences.Editor edit()
  {
    if (this.k == null)
      this.k = new REditor(this.i);
    return this.k;
  }

  public Map<String, ?> getAll()
  {
    Cursor localCursor;
    try
    {
      localCursor = this.i.query(MMPluginProviderConstants.SharedPref.CONTENT_URI, this.columns, null, null, null);
      if (localCursor == null)
        return null;
      int m = localCursor.getColumnIndex("key");
      int n = localCursor.getColumnIndex("type");
      int i1 = localCursor.getColumnIndex("value");
      while (localCursor.moveToNext())
      {
        Object localObject = MMPluginProviderConstants.Resolver.resolveObj(localCursor.getInt(n), localCursor.getString(i1));
        this.j.put(localCursor.getString(m), localObject);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return this.j;
    }
    localCursor.close();
    HashMap localHashMap = this.j;
    return localHashMap;
  }

  public boolean getBoolean(String paramString, boolean paramBoolean)
  {
    Object localObject = getValue(paramString);
    if ((localObject != null) && ((localObject instanceof Boolean)))
      paramBoolean = ((Boolean)localObject).booleanValue();
    return paramBoolean;
  }

  public float getFloat(String paramString, float paramFloat)
  {
    Object localObject = getValue(paramString);
    if ((localObject != null) && ((localObject instanceof Float)))
      paramFloat = ((Float)localObject).floatValue();
    return paramFloat;
  }

  public int getInt(String paramString, int paramInt)
  {
    Object localObject = getValue(paramString);
    if ((localObject != null) && ((localObject instanceof Integer)))
      paramInt = ((Integer)localObject).intValue();
    return paramInt;
  }

  public long getLong(String paramString, long paramLong)
  {
    Object localObject = getValue(paramString);
    if ((localObject != null) && ((localObject instanceof Long)))
      paramLong = ((Long)localObject).longValue();
    return paramLong;
  }

  public String getString(String paramString1, String paramString2)
  {
    Object localObject = getValue(paramString1);
    if ((localObject != null) && ((localObject instanceof String)))
      return (String)localObject;
    return paramString2;
  }

  public Set<String> getStringSet(String paramString, Set<String> paramSet)
  {
    return null;
  }

  public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener)
  {
  }

  public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener)
  {
  }

  private static class REditor
    implements SharedPreferences.Editor
  {
    private ContentResolver i;
    private Map<String, Object> l = new HashMap();
    private Set<String> m = new HashSet();
    private boolean n = false;

    public REditor(ContentResolver paramContentResolver)
    {
      this.i = paramContentResolver;
    }

    public void apply()
    {
    }

    public SharedPreferences.Editor clear()
    {
      this.n = true;
      return this;
    }

    public boolean commit()
    {
      ContentValues localContentValues = new ContentValues();
      if (this.n)
      {
        this.i.delete(MMPluginProviderConstants.SharedPref.CONTENT_URI, null, null);
        this.n = false;
      }
      Iterator localIterator1 = this.m.iterator();
      while (localIterator1.hasNext())
      {
        String str = (String)localIterator1.next();
        this.i.delete(MMPluginProviderConstants.SharedPref.CONTENT_URI, "key = ?", new String[] { str });
      }
      Iterator localIterator2 = this.l.entrySet().iterator();
      while (localIterator2.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator2.next();
        if (!MMPluginProviderConstants.Resolver.unresolveObj(localContentValues, localEntry.getValue()))
          continue;
        ContentResolver localContentResolver = this.i;
        Uri localUri = MMPluginProviderConstants.SharedPref.CONTENT_URI;
        String[] arrayOfString = new String[1];
        arrayOfString[0] = ((String)localEntry.getKey());
        localContentResolver.update(localUri, localContentValues, "key = ?", arrayOfString);
      }
      return true;
    }

    public SharedPreferences.Editor putBoolean(String paramString, boolean paramBoolean)
    {
      this.l.put(paramString, Boolean.valueOf(paramBoolean));
      this.m.remove(paramString);
      return this;
    }

    public SharedPreferences.Editor putFloat(String paramString, float paramFloat)
    {
      this.l.put(paramString, Float.valueOf(paramFloat));
      this.m.remove(paramString);
      return this;
    }

    public SharedPreferences.Editor putInt(String paramString, int paramInt)
    {
      this.l.put(paramString, Integer.valueOf(paramInt));
      this.m.remove(paramString);
      return this;
    }

    public SharedPreferences.Editor putLong(String paramString, long paramLong)
    {
      this.l.put(paramString, Long.valueOf(paramLong));
      this.m.remove(paramString);
      return this;
    }

    public SharedPreferences.Editor putString(String paramString1, String paramString2)
    {
      this.l.put(paramString1, paramString2);
      this.m.remove(paramString1);
      return this;
    }

    public SharedPreferences.Editor putStringSet(String paramString, Set<String> paramSet)
    {
      return null;
    }

    public SharedPreferences.Editor remove(String paramString)
    {
      this.m.add(paramString);
      return this;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.MMSharedPreferences
 * JD-Core Version:    0.6.0
 */