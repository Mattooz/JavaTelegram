package me.niccolomattei.api.telegram.scheduling;

import java.util.UUID;

/**
 * @deprecated Rework needed. Will do it soon.
 */
@Deprecated
public interface SchedulerTask extends Runnable {

	void runTask();
	
	void runTaskLater(long delay);
	
	void runRepeatingTask(long delay, long period);
	
	void cancel();
	
	UUID getId();

	void setId(UUID id);

	
}
