package test.springer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.springer.data.MyDocument;
import test.springer.data.MyTicket;

/**
 * Created by uv on 31.03.2016 for watermark
 */
@Service
public class DocumentService {

    @Autowired
    private StorageService storageService;

    public MyDocument getDocumentForTicket(MyTicket ticket) {
        return storageService.getDocumentForId(ticket.getDocumentId());
    }
}
