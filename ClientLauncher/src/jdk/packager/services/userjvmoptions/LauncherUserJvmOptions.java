package jdk.packager.services.userjvmoptions;

import java.security.AllPermission;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import jdk.packager.services.UserJvmOptionsService;

public final class LauncherUserJvmOptions implements UserJvmOptionsService {
  private static final Object semaphore = new Object();
  
  static  {
    try {
      checkAllPermissions();
      System.loadLibrary("packager");
    } catch (SecurityException securityException) {}
  }
  
  private static void checkAllPermissions() {
    SecurityManager securityManager = System.getSecurityManager();
    if (securityManager != null)
      securityManager.checkPermission(new AllPermission()); 
  }
  
  public Map<String, String> getUserJVMOptions() {
    checkAllPermissions();
    synchronized (semaphore) {
      LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
      for (String str : _getUserJvmOptionKeys())
        linkedHashMap.put(str, _getUserJvmOptionValue(str)); 
      return (Map)linkedHashMap;
    } 
  }
  
  public void setUserJVMOptions(Map<String, String> paramMap) {
    checkAllPermissions();
    synchronized (semaphore) {
      LinkedList linkedList1 = new LinkedList();
      LinkedList linkedList2 = new LinkedList();
      for (Map.Entry<String, String> entry : paramMap.entrySet()) {
        if (entry.getKey() == null)
          throw new IllegalArgumentException("For Launcher Backed UserJVMOptions keys in the UserJVMOptions map cannot be null."); 
        if (entry.getValue() == null)
          throw new IllegalArgumentException("For Launcher Backed UserJVMOptions values in the UserJVMOptions map cannot be null.  Keys are removed by absence, not by setting keys to null."); 
        linkedList1.add(entry.getKey());
        linkedList2.add(entry.getValue());
      } 
      _setUserJvmKeysAndValues((String[])linkedList1.toArray((Object[])new String[linkedList1.size()]), (String[])linkedList2
          .toArray((Object[])new String[linkedList2.size()]));
    } 
  }
  
  public Map<String, String> getUserJVMOptionDefaults() {
    checkAllPermissions();
    synchronized (semaphore) {
      LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
      for (String str : _getUserJvmOptionDefaultKeys())
        linkedHashMap.put(str, _getUserJvmOptionDefaultValue(str)); 
      return (Map)linkedHashMap;
    } 
  }
  
  private static native String _getUserJvmOptionDefaultValue(String paramString);
  
  private static native String[] _getUserJvmOptionDefaultKeys();
  
  private static native String _getUserJvmOptionValue(String paramString);
  
  private static native void _setUserJvmKeysAndValues(String[] paramArrayOfString1, String[] paramArrayOfString2);
  
  private static native String[] _getUserJvmOptionKeys();
}
