package test.springer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/getTicketForDoc")
    public MyTicket getWatermarkTicket(MyDocument document) {
        MyTicket ticket = new MyTicket(document);
        return ticket;
    }

    @RequestMapping("/getDocForTicket")
    public MyDocument getDocumentForTicket(MyTicket ticket) {
        return documentService.getDocumentForTicket(ticket);
    }


    @RequestMapping("/isTicketFinished")
    public boolean isWatermarkTicketFinished(MyTicket ticket) {
        MyDocument myDocument = getDocumentForTicket(ticket);
        return myDocument.isWaterMarked();
    }

}
