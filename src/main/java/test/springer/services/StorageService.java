package test.springer.services;

import org.springframework.beans.factory.annotation.Autowired;
import test.springer.data.DocumentDatabase;
import test.springer.data.MyDocument;

/**
 * Created by uv on 31.03.2016 for watermark
 */
public class StorageService {

    @Autowired
    DocumentDatabase documentDatabase;

    public MyDocument getDocumentForId(Long documentId) {
        return documentDatabase.getDocument(documentId);
    }
}
