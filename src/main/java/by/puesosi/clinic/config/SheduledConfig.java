package by.puesosi.clinic.config;

import by.puesosi.clinic.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class SheduledConfig {
    private Service service;

    @Autowired
    public SheduledConfig(Service service) {
        this.service = service;
    }

    @Scheduled(fixedDelay = 10*60*1000)
    public void clearUntimedTicket(){
        service.clearUntimedTicket();
    }

    @Scheduled(cron = "0 0 0 * * *")
//  @Scheduled(fixedDelay = 3*60*1000)
    public void addNextDayDB(){
        service.addNextDay();
    }
}
