package com.kaixin001.engine;

import android.content.Context;
import com.kaixin001.db.ContactsDBAdapter;
import com.kaixin001.model.ContactsRelatedModel;
import com.kaixin001.model.User;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;

public class ContactsRelateEngine
{
  private static volatile ContactsRelateEngine instance;

  public static void clear()
  {
    instance = null;
  }

  public static ContactsRelateEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new ContactsRelateEngine();
      ContactsRelateEngine localContactsRelateEngine = instance;
      return localContactsRelateEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public boolean deleteContactsData(Context paramContext, String paramString)
  {
    ContactsDBAdapter localContactsDBAdapter = new ContactsDBAdapter(paramContext);
    try
    {
      boolean bool = localContactsDBAdapter.deleteKXFriends(User.getInstance().getUID(), paramString);
      return bool;
    }
    catch (Exception localException)
    {
      KXLog.e("ContactsRelateEngine", "deleteContactsData", localException);
      return false;
    }
    finally
    {
      localContactsDBAdapter.close();
    }
    throw localObject;
  }

  public boolean deleteContactsDataByCid(Context paramContext, long paramLong)
  {
    ContactsDBAdapter localContactsDBAdapter = new ContactsDBAdapter(paramContext);
    try
    {
      boolean bool = localContactsDBAdapter.deleteKXFriends(User.getInstance().getUID(), Long.valueOf(paramLong));
      return bool;
    }
    catch (Exception localException)
    {
      KXLog.e("ContactsRelateEngine", "deleteContactsDataByCid", localException);
      return false;
    }
    finally
    {
      localContactsDBAdapter.close();
    }
    throw localObject;
  }

  public ArrayList<Long> loadContactsCids(Context paramContext)
  {
    ContactsDBAdapter localContactsDBAdapter = new ContactsDBAdapter(paramContext);
    ArrayList localArrayList1 = new ArrayList();
    try
    {
      ArrayList localArrayList2 = localContactsDBAdapter.getCids();
      return localArrayList2;
    }
    catch (Exception localException)
    {
      KXLog.e("ContactsRelateEngine", "LoadContactsCids", localException);
      return localArrayList1;
    }
    finally
    {
      localContactsDBAdapter.close();
    }
    throw localObject;
  }

  public ArrayList<String> loadContactsCnames(Context paramContext)
  {
    ContactsDBAdapter localContactsDBAdapter = new ContactsDBAdapter(paramContext);
    ArrayList localArrayList1 = new ArrayList();
    try
    {
      ArrayList localArrayList2 = localContactsDBAdapter.getCnames();
      return localArrayList2;
    }
    catch (Exception localException)
    {
      KXLog.e("ContactsRelateEngine", "LoadContactsCnames", localException);
      return localArrayList1;
    }
    finally
    {
      localContactsDBAdapter.close();
    }
    throw localObject;
  }

  public ArrayList<String> loadContactsFuids(Context paramContext)
  {
    ContactsDBAdapter localContactsDBAdapter = new ContactsDBAdapter(paramContext);
    ArrayList localArrayList1 = new ArrayList();
    try
    {
      ArrayList localArrayList2 = localContactsDBAdapter.getFuids();
      return localArrayList2;
    }
    catch (Exception localException)
    {
      KXLog.e("ContactsRelateEngine", "LoadContactsFuids", localException);
      return localArrayList1;
    }
    finally
    {
      localContactsDBAdapter.close();
    }
    throw localObject;
  }

  public ArrayList<ContactsRelatedModel> loadContactsRelatedObjs(Context paramContext)
  {
    ContactsDBAdapter localContactsDBAdapter = new ContactsDBAdapter(paramContext);
    ArrayList localArrayList1 = new ArrayList();
    try
    {
      ArrayList localArrayList2 = localContactsDBAdapter.getRelatedObjects();
      return localArrayList2;
    }
    catch (Exception localException)
    {
      KXLog.e("ContactsRelateEngine", "LoadContactsRelatedObjs", localException);
      return localArrayList1;
    }
    finally
    {
      localContactsDBAdapter.close();
    }
    throw localObject;
  }

  public boolean saveContactsData(Context paramContext, long paramLong1, String paramString1, String paramString2, String paramString3, String paramString4, long paramLong2)
  {
    ContactsDBAdapter localContactsDBAdapter = new ContactsDBAdapter(paramContext);
    try
    {
      long l = localContactsDBAdapter.insertContacts(User.getInstance().getUID(), paramLong1, paramString1, paramString2, paramString3, paramString4, paramLong2);
      return l != -1L;
    }
    catch (Exception localException)
    {
      KXLog.e("ContactsRelateEngine", "SaveContactsData", localException);
      return false;
    }
    finally
    {
      localContactsDBAdapter.close();
    }
    throw localObject;
  }

  public boolean updateContactsData(Context paramContext, long paramLong1, String paramString1, String paramString2, String paramString3, String paramString4, long paramLong2)
  {
    ContactsDBAdapter localContactsDBAdapter = new ContactsDBAdapter(paramContext);
    try
    {
      boolean bool = localContactsDBAdapter.updateAContacts(User.getInstance().getUID(), paramLong1, paramString1, paramString2, paramString3, paramString4, paramLong2);
      return bool;
    }
    catch (Exception localException)
    {
      KXLog.e("ContactsRelateEngine", "SaveContactsData", localException);
      return false;
    }
    finally
    {
      localContactsDBAdapter.close();
    }
    throw localObject;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.ContactsRelateEngine
 * JD-Core Version:    0.6.0
 */