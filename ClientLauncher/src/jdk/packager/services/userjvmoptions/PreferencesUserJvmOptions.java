package jdk.packager.services.userjvmoptions;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import jdk.packager.services.UserJvmOptionsService;

public final class PreferencesUserJvmOptions implements UserJvmOptionsService {
  Preferences node = Preferences.userRoot().node(System.getProperty("app.preferences.id").replace(".", "/")).node("JVMUserOptions");
  
  public Map<String, String> getUserJVMOptions() {
    LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
    try {
      for (String str1 : this.node.childrenNames()) {
        String str2 = this.node.get(str1, null);
        if (str2 != null)
          linkedHashMap.put(str1, str2); 
      } 
    } catch (BackingStoreException backingStoreException) {}
    return (Map)linkedHashMap;
  }
  
  public void setUserJVMOptions(Map<String, String> paramMap) {
    try {
      this.node.clear();
      for (Map.Entry<String, String> entry : paramMap.entrySet())
        this.node.put((String)entry.getKey(), (String)entry.getValue()); 
      this.node.flush();
    } catch (BackingStoreException backingStoreException) {}
  }
  
  public Map<String, String> getUserJVMOptionDefaults() { throw new UnsupportedOperationException("Preferences backed UserJvmOptions do not enumerate their defaults"); }
}
