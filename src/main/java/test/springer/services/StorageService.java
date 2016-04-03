package test.springer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.springer.data.DocumentDatabase;
import test.springer.data.MyDocument;

/**
 * An extra service layer for decoupling the underlying storage..
 * Created by uv on 31.03.2016 for watermark
 */
@Service
public class StorageService {

    @Autowired
    DocumentDatabase documentDatabase;

    public MyDocument getDocumentForId(Long documentId) {
        return documentDatabase.getDocument(documentId);
    }

    public MyDocument storeDocument(MyDocument document) {
        return documentDatabase.updateDocument(document);
    }

}
