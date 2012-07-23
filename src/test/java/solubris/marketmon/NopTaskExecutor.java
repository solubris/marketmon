package solubris.marketmon;

import org.springframework.scheduling.config.TaskExecutorFactoryBean;

public class NopTaskExecutor extends TaskExecutorFactoryBean {
	public void afterPropertiesSet() throws Exception {
	}

}
