package test.springer;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class WatermarkApplication implements AsyncConfigurer {

    static Logger logger = Logger.getLogger(WatermarkApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(WatermarkApplication.class, args);
	}

	@Bean
    WatermarkingTask getWatermarkingExecutor() {
		return new WatermarkingTask();
	}

    /**
     * @return a preconfigured threadpool task executor to run our watermarking task
     */
	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(7);
		executor.setMaxPoolSize(42);
		executor.setQueueCapacity(11);
		executor.setThreadNamePrefix("MyExecutor-");
		executor.initialize();
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return (ex, method, params) -> logger.log(Level.SEVERE, String.format("E0000 Uncaught Exception in method %s(...): %s", method.getName(), ex.getMessage()));
	}

    /**
     * @return a json mapper preconfigured for our use cases
     */
	@Bean
	public ObjectMapper myObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false); // this is ESSENTIAL configuration to make use of the JsonViews !!!!
		return mapper;
	}
}
