package test.springer.data;

/**
 * Created by uv on 03.04.2016 for watermark
 */
public class MyJournal extends MyDocument {

    public MyJournal() {
        super.setDocType(DocType.JOURNAL);
        super.setJsonView(Views.Journal.class);
    }

}
