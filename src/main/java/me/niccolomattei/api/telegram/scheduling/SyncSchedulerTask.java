package me.niccolomattei.api.telegram.scheduling;

import me.niccolomattei.api.telegram.Bot;

import java.util.UUID;

/**
 * @deprecated Rework needed. Will do it soon.
 */
@Deprecated
public abstract class SyncSchedulerTask implements Runnable, SchedulerTask{
	
	protected UUID id = null;
	
	@Override
	public void runTask() {
		throw new UnsupportedOperationException("Deprecated");
		//Bot.currentBot.getScheduler().runTask(this);
	}
	
	@Override
	public void runTaskLater(long delay) {
		throw new UnsupportedOperationException("Deprecated");
		//Bot.currentBot.getScheduler().runTaskLater(this, delay);
	}
	
	@Override
	public void runRepeatingTask(long delay, long period) {
		throw new UnsupportedOperationException("Deprecated");
		//Bot.currentBot.getScheduler().runRepeatingTask(this, delay, period);
	}
	
	@Override
	public void cancel() {
		throw new UnsupportedOperationException("Deprecated");
		//Bot.currentBot.getScheduler().cancel(id);
	}
	
	@Override
	public UUID getId() {
		return id;
	}
	
	@Override
	public void setId(UUID uuid) {
		id = uuid;		
	}

}
