package test.springer.services;

import org.springframework.stereotype.Service;
import test.springer.data.MyDocument;
import test.springer.data.MyTicket;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by uv on 04.04.2016 for watermark
 */
@Service
public class SimpleDocumentStorageService implements DocumentStorageService {

    // doc storage
    static Map<Long, MyDocument> allDocuments = new ConcurrentHashMap<>();    // fancy threadsafe map for tickets

    @Override
    public MyDocument getDocumentForId(Long docId) {
        if (allDocuments.containsKey(docId)) {
            return allDocuments.get(docId);
        }
        throw new IllegalArgumentException("E5000 docId not found");
    }

    public MyDocument putDocument(MyDocument document) {
        if (allDocuments.containsValue(document))
            return null;
        else
            return allDocuments.put(document.getId(), document);
    }
}
