package me.niccolomattei.api.telegram.scheduling;

import java.util.UUID;

public abstract interface Scheduler {
	
	public abstract void runTask(SchedulerTask scheduler);
	
	public abstract void runTaskLater(SchedulerTask scheduler, long delay);
	
	public abstract void runRepeatingTask(SchedulerTask scheduler, long delay, long period);

	public abstract void cancel(UUID taskId);

}
