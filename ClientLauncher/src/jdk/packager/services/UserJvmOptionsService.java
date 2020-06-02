package jdk.packager.services;

import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import jdk.packager.services.userjvmoptions.LauncherUserJvmOptions;

public interface UserJvmOptionsService {
  static UserJvmOptionsService getUserJVMDefaults() {
    ServiceLoader<UserJvmOptionsService> serviceLoader = ServiceLoader.load(UserJvmOptionsService.class);
    Iterator<UserJvmOptionsService> iterator = serviceLoader.iterator();
    if (iterator.hasNext())
      return iterator.next(); 
    return (UserJvmOptionsService)new LauncherUserJvmOptions();
  }
  
  Map<String, String> getUserJVMOptions();
  
  void setUserJVMOptions(Map<String, String> paramMap);
  
  Map<String, String> getUserJVMOptionDefaults();
}
