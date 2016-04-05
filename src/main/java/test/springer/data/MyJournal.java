package test.springer.data;

/**
 * Created by uv on 03.04.2016 for watermark
 */
public class MyJournal extends MyDocument {

    public MyJournal() {
        super.setContent(Content.JOURNAL);
        super.setJsonView(MyViews.Journal.class);
    }

}
