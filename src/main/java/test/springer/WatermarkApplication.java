package test.springer;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import test.springer.services.WatermarkingExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@EnableAsync
public class WatermarkApplication implements AsyncConfigurer {

    static Logger logger = Logger.getLogger(WatermarkApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(WatermarkApplication.class, args);
	}

	@Bean
	WatermarkingExecutor getWatermarkingExecutor() {
		return new WatermarkingExecutor();
	}

	@Override
	public ThreadPoolTaskExecutor getAsyncExecutor() {
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
}
