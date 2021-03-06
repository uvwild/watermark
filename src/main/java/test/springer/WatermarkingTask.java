package test.springer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import test.springer.services.WatermarkService;

import java.util.logging.Logger;

/** TODO TBD
 * Created by uv on 03.04.2016 for watermark
 */
@Component
public class WatermarkingTask {
    static Logger logger = Logger.getLogger(WatermarkingTask.class.getSimpleName());

    @Autowired
    private WatermarkService watermarkService;

    @Async
    public void doWaterMarking() {
        logger.fine("waterMarking called asynchronously");
        watermarkService.watermarkNextTicket();
    }
}
