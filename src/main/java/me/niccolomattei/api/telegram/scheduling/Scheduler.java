package me.niccolomattei.api.telegram.scheduling;

import java.util.UUID;

/**
 * @deprecated Rework needed. Will do it soon.
 */
@Deprecated
public interface Scheduler {
	
	void runTask(SchedulerTask scheduler);

	void runTaskLater(SchedulerTask scheduler, long delay);
	
	void runRepeatingTask(SchedulerTask scheduler, long delay, long period);

	void cancel(UUID taskId);

}
