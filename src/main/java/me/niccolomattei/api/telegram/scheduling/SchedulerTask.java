package me.niccolomattei.api.telegram.scheduling;

import java.util.UUID;

public interface SchedulerTask extends Runnable {

	void runTask();
	
	void runTaskLater(long delay);
	
	void runRepeatingTask(long delay, long period);
	
	void cancel();
	
	UUID getId();

	void setId(UUID id);

	
}
