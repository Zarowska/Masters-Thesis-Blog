package blog.cirkle.app.service;

import java.util.Properties;

public interface SystemConfigService {

	Properties getSystemProperties();

	void setValue(String key, String value);

}
