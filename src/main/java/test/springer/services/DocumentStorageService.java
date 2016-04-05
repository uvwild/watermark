package test.springer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.springer.data.MyDocument;
import test.springer.data.MyTicket;

/**
 * interface abstraction
 * Created by uv on 31.03.2016 for watermark
 */
public interface DocumentStorageService {

    MyDocument getDocumentForId(Long docId);

    MyDocument putDocument(MyDocument document);
}
