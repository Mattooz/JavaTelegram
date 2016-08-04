package me.niccolomattei.api.telegram.scheduling;

import java.util.UUID;

public interface SchedulerTask extends Runnable {

	public abstract void runTask();
	
	public abstract void runTaskLater(long delay);
	
	public abstract void runRepeatingTask(long delay, long period);
	
	public abstract void cancel();
	
	public abstract UUID getId();
	
	public abstract void setId(UUID id);

	
}
