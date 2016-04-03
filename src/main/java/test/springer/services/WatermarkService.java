package test.springer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import test.springer.data.MyDocument;
import test.springer.data.MyTicket;


/**
 * Created by uv on 31.03.2016 for watermark
 */
@RestController
public class WatermarkService {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private TicketService ticketService;

    /**
     * @param document
     * @return
     */
    @RequestMapping(name = "/getTicketForDoc", method = RequestMethod.PUT, consumes = "application/json; charset=UTF-8")
    public MyTicket getWatermarkTicket(MyDocument document) {
        MyTicket ticket = ticketService.createTicket(document);
        return ticket;
    }

    /**
     * @param ticketId
     * @return null when ticket not found
     */
    @RequestMapping("/getDocForTicketId/{ticketId}")
    public MyDocument getDocumentForTicket(Long ticketId) {
        MyTicket ticket = ticketService.getTicket(ticketId);
        return documentService.getDocumentForTicket(ticket);
    }


    /**
     * @param ticketId
     * @return the
     */
    @RequestMapping("/isTicketFinished/{ticketId}")
    public boolean isWatermarkTicketFinished(Long ticketId) {
        MyDocument myDocument = getDocumentForTicket(ticketId);
        if (myDocument!= null)
            return myDocument.isWaterMarked();
        else
            throw new IllegalArgumentException("E2000 no ticket found for Id " + ticketId);
    }

    @Autowired
    AsyncConfigurer asyncConfigurer;
//    /**
//     * @return if threads where active before
//     */
//    @RequestMapping("/startWatermarking")

//    public boolean startWatermarking() {
//        ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) asyncConfigurer.getAsyncExecutor();
//        if (executor.getActiveCount() > 1)
//            return true;    // already running
//        else {
//            executor.initialize();
//            return false;
//        }
//    }
//
//    @RequestMapping("/stopWatermarking")
//    public void stopWatermarking() {
//        executor.shutdown();
//    }

}


