package pims.config;

import java.util.Properties;
 
public class Config {
	
   Properties configFile;
   
   public Config() {
	   
	   configFile = new Properties();
	   
	   try {
		   configFile.load(this.getClass().getClassLoader().getResourceAsStream("pims/config.properties"));
	   }catch(Exception e){
		   e.printStackTrace();
	   }
   }
 
   public String getProperty(String key) {
	   
	   String value = this.configFile.getProperty(key);
	   return value;
	   
   }
}