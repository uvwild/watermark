package test.springer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.springer.data.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONAs;

/**
 * tests to verify the inheritance of properties in Beans
 * Created by uv on 04.04.2016 for watermark
 */
public class ObjectMapperTest implements TestData {

    @Autowired
    ObjectMapper myObjectMapper;        // this requires spring context

    @Before
    public void init() {            // so do it manually since we dont need the lot
        myObjectMapper = new WatermarkApplication().myObjectMapper();
    }

    @Test
    public void testEnumsAsJson() throws JsonProcessingException {
        String book = myObjectMapper.writeValueAsString(Content.BOOK);
        assertThat(book, is("\"" + Content.BOOK.toString() + "\""));

        String business = myObjectMapper.writeValueAsString(Topic.BUSINESS);
        assertThat(business, is("\"" + Topic.BUSINESS.toString() + "\""));
    }

    @Test
    public void testJsonMapperBook() throws JsonProcessingException {
        ObjectWriter bookWriter = myObjectMapper.writerWithView(MyViews.Book.class);
        String jsonBook = bookWriter.writeValueAsString(createTestBook());
        assertThat(jsonBook, sameJSONAs(testBookWatermark).allowingExtraUnexpectedFields());
    }

    @Test
    public void testJsonMapperJournal() throws JsonProcessingException {
        ObjectWriter journalWriter = myObjectMapper.writerWithView(MyViews.Journal.class);
        String jsonJournal = journalWriter.writeValueAsString(createTestJournal());
        assertThat(jsonJournal, sameJSONAs(testJournalWatermark).allowingExtraUnexpectedFields());
    }

    @Test
    public void testJsonMapperTicket() throws JsonProcessingException {
        ObjectWriter ticketWriter = myObjectMapper.writerWithView(MyViews.Ticket.class);
        MyTicket ticket = new MyTicket(createTestBook());
        ticket.setId(0l);
        String jsonTicket = ticketWriter .writeValueAsString(ticket);
        assertThat(jsonTicket, sameJSONAs(testTicketJson).allowingExtraUnexpectedFields());
    }

}

