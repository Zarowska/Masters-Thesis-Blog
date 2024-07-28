package blog.cirkle.app.service.impl;

import blog.cirkle.app.model.entity.SystemConfigEntry;
import blog.cirkle.app.repository.SystemConfigEntryRepository;
import blog.cirkle.app.service.SystemConfigService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SystemConfigServiceImpl implements SystemConfigService {

	private final SystemConfigEntryRepository systemConfigEntryRepository;
	private Properties properties;

	@Override
	@Transactional(readOnly = true)
	public Properties getSystemProperties() {
		if (properties == null) {
			fetchSystemProperties();
		}
		return properties;
	}

	@Override
	@Transactional
	public void setValue(String key, String value) {
		log.info("Setting system property {} to {}", key, value);
		Optional<SystemConfigEntry> entry = systemConfigEntryRepository.findByKey(key);
		if (entry.isPresent()) {
			entry.get().setValue(value);
		} else {
			systemConfigEntryRepository.save(new SystemConfigEntry(key, value));
		}
		properties = null;
	}

	private void fetchSystemProperties() {
		synchronized (this) {
			if (properties == null) {
				String propertiesAsString = systemConfigEntryRepository.findAll().stream()
						.map(e -> "%s=%s".formatted(e.getKey(), e.getValue())).collect(Collectors.joining("\n"));
				try (ByteArrayInputStream bais = new ByteArrayInputStream(propertiesAsString.getBytes())) {
					Properties props = new Properties();
					props.load(bais);
					this.properties = props;
				} catch (IOException e) {
					log.error("Failed to load system properties: {}", e.getMessage(), e);
					properties = new Properties();
				}
			}
		}
	}
}
