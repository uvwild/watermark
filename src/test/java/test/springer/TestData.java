package test.springer;

import test.springer.data.MyBook;
import test.springer.data.MyDocument;
import test.springer.data.MyJournal;
import test.springer.data.Topic;

/**
 * place test data in interface for easy reuse
 * Created by uv on 04.04.2016 for watermark
 */
public interface TestData {

    String testBookWatermark = "{\"author\":\"Göthe\",\"content\":\"book\",\"title\":\"Feist\",\"topic\":\"media\"}";

    String testJournalWatermark = "{\"author\":\"Humphrey Bogart\",\"content\":\"journal\",\"title\":\"Vom Winde verklebt\"}";

    String testTicketJson = "{\"id\":0,\"document\":{\"content\":\"book\",\"title\":\"Feist\",\"author\":\"Göthe\"}}";

    default MyDocument createTestBook() {
        MyBook book = new MyBook();
        book.setAuthor("Göthe");
        book.setId(4711l);
        book.setTitle("Feist");
        book.setTopic(Topic.MEDIA);
        return book;
    }

    default  MyDocument createTestJournal() {
        MyJournal journal = new MyJournal();
        journal.setAuthor("Humphrey Bogart");
        journal.setTitle("Vom Winde verklebt");
        journal.setId(42l);
        return journal;
    }


}
