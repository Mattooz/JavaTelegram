package me.niccolomattei.api.telegram.scheduling;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class SyncScheduler implements Scheduler {

	private Map<UUID, TimerTask> tasks = new HashMap<>();

	Timer timer = null;

	{
		timer = new Timer();
	}

	@Override
	public void runTask(SchedulerTask task) {
		task.run();
	}

	@Override
	public void runTaskLater(final SchedulerTask task, long delay) {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				task.run();
			}
		}, delay);
	}

	@Override
	public void runRepeatingTask(final SchedulerTask task, long delay, long period) {

		TimerTask ttask = new TimerTask() {

			@Override
			public void run() {
				task.run();

			}
		};

		timer.scheduleAtFixedRate(ttask, delay, period);
		UUID uuid = UUID.randomUUID();
		task.setId(uuid);
		tasks.put(uuid, ttask);
	}

	@Override
	public void cancel(UUID taskId) {
		if (tasks.containsKey(taskId)) {
			tasks.get(taskId).cancel();
			tasks.remove(taskId);
		} else
			throw new SchedulerException("Task is not a repeating task or non-existant!");
	}
}
