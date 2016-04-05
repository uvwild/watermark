package test.springer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.web.bind.annotation.*;
import test.springer.WatermarkingTask;
import test.springer.data.MyDocument;
import test.springer.data.MyTicket;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by uv on 31.03.2016 for watermark
 */
@RestController
public class WatermarkRestService implements ApiDefinitions {

    @Autowired
    private DocumentStorageService documentStorageService;

    @Autowired
    private WatermarkService watermarkService;

    @Autowired
    private WatermarkingTask watermarkingTask;


    /**
     * @param document
     * @return new ticket for this document - also currently the only way to store a document locally
     */
    @RequestMapping(path = ApiDefinitions.getTicketForDoc, method = RequestMethod.POST, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    public MyTicket getWatermarkTicketForDocument(@RequestBody MyDocument document) {
        if (document != null && document.getId() != null) {
            documentStorageService.putDocument(document);
            MyTicket ticket = watermarkService.getTicket(document);
            watermarkingTask.doWaterMarking();      // called aysnchronously due to annotation
            return ticket;
        } else
            return null;
    }

    /**
     * @param docId
     * @return new ticket for this document
     */
    @RequestMapping(path = ApiDefinitions.getTicketForDocId + "/{docId}", produces = "application/json;charset=UTF-8")
    public MyTicket getWatermarkTicketForId(@PathVariable Long docId) {
        MyDocument document = documentStorageService.getDocumentForId(docId);
        MyTicket ticket = watermarkService.getTicket(document);
        return ticket;
    }


    /**
     * @param ticketId
     * @return null when ticket not found
     */
    @RequestMapping(path = ApiDefinitions.getDocForTicketId + "/{ticketId}", produces = "application/json;charset=UTF-8")
    public MyDocument getDocumentForTicketId(@PathVariable Long ticketId) {
        MyTicket ticket = watermarkService.getTicket(ticketId);
        return documentStorageService.getDocumentForId(ticket.getDocumentId());
    }


    /**
     * @param ticketId
     * @return the watermarked doc or null or exception for wrong ticketId
     */
    @RequestMapping(path = ApiDefinitions.isTicketFinished + "/{ticketId}", produces = "application/json;charset=UTF-8")
    public MyDocument isWatermarkTicketFinished(@PathVariable Long ticketId) {
        MyDocument myDocument = getDocumentForTicketId(ticketId);
        if (myDocument != null) {
            if (myDocument.isWaterMarked())
                return myDocument;
            else
                return null;
        } else
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


