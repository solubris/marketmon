package solubris.marketmon;

import java.util.Date;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.TaskUtils;
import org.springframework.util.ErrorHandler;

public class NopTaskScheduler extends ThreadPoolTaskScheduler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void execute(Runnable task) {
	}
	// TaskScheduler implementation

	public ScheduledFuture schedule(Runnable task, Trigger trigger) {
		return null;
	}

	public ScheduledFuture schedule(Runnable task, Date startTime) {
		return null;
	}

	public ScheduledFuture scheduleAtFixedRate(Runnable task, Date startTime, long period) {
		return null;
	}

	public ScheduledFuture scheduleAtFixedRate(Runnable task, long period) {
		return null;
	}

	public ScheduledFuture scheduleWithFixedDelay(Runnable task, Date startTime, long delay) {
		return null;
	}

	public ScheduledFuture scheduleWithFixedDelay(Runnable task, long delay) {
		return null;
	}

}
