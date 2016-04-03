package test.springer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import test.springer.data.MyTicket;

/**
 * Created by uv on 03.04.2016 for watermark
 */
public class WatermarkingExecutor {

    @Autowired
    private TicketService ticketService;

    @Async
    public void doWaterMarking() {
        ticketService.watermarkNextTicket();
    }
}
