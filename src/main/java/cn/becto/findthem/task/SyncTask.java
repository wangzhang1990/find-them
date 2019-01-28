package cn.becto.findthem.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.becto.findthem.service.FindthemService;

@Component
public class SyncTask {
	
	@Autowired
	private FindthemService findthemService;
	
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void syncData() throws Exception {
		System.out.println("................................................");
		findthemService.syncData();
	}
}
