package test.springer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.beanutils.BeanUtils;
import org.hamcrest.beans.SamePropertyValuesAs;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.springer.TestData;
import test.springer.WatermarkApplicationTests;
import test.springer.data.MyDocument;
import test.springer.data.MyTicket;

import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONAs;

/**
 * Created by uv on 04.04.2016 for watermark
 */
public class WatermarkServiceTest extends WatermarkApplicationTests implements TestData {

    @Autowired
    WatermarkService watermarkService;

    MyTicket bookTicket;
    MyTicket bookTicket2;
    MyTicket journalTicket;

    @Before
    public void init() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // the ticket store is persistet so clear first for the element counting tests to work
        watermarkService.clearAllTickets();
        MyDocument book = createTestBook();
        bookTicket = watermarkService.getTicket(createTestBook());
        MyDocument book2 = (MyDocument) BeanUtils.cloneBean(book);
        book2.setId(999l);
        bookTicket2 = watermarkService.getTicket(book2);
        journalTicket = watermarkService.getTicket(createTestJournal());
    }

    @Test
    public void testCreateTicket() throws Exception {
        MyTicket ticket = watermarkService.getTicket(createTestBook());
        assert (ticket != null);
        assertThat(ticket.getDocument(), SamePropertyValuesAs.samePropertyValuesAs(createTestBook()));
    }

    @Test
    public void testGetNextTodoTicket() throws Exception {
        assertThat(bookTicket.getId(),is(bookTicket2.getId() - 1l));
        assertThat(bookTicket2.getDocumentId(),is(999l));

        MyTicket ticket1 = watermarkService.getNextTodoTicket();
        assertThat(ticket1.getId(),is(bookTicket.getId()));
        MyDocument firstDocument = watermarkService.watermarkNextTicket();
        assertThat(firstDocument.getId(), is(bookTicket.getDocumentId()));

        MyTicket ticketNext2 = watermarkService.getNextTodoTicket();
        assertThat(ticketNext2.getId(),is(bookTicket2.getId()));
    }

    @Test
    public void testWatermarkNextTicket() throws Exception {
        long countb4 = watermarkService.countTodoTickets();
        watermarkService.watermarkNextTicket();
        watermarkService.watermarkNextTicket();
        long countAfter = watermarkService.countTodoTickets();
        assertThat(countAfter, is(countb4 - 2l));
    }

    @Test
    public void testCreateWatermarkBook() throws Exception {
        MyTicket ticket = watermarkService.getTicket(createTestBook());
        assert (ticket != null);
        MyDocument book = watermarkService.createWatermark(ticket);
        assert(watermarkService.getTicket(ticket.getId()) != null);
        assertThat(book.getWaterMark(), sameJSONAs(testBookWatermark).allowingExtraUnexpectedFields());
    }

    @Test
    public void testCreateWatermarkJournal() throws JsonProcessingException {
        MyTicket ticket = watermarkService.getTicket(createTestJournal());
        assert (ticket != null);
        MyDocument journal = watermarkService.createWatermark(ticket);
        assert(watermarkService.getTicket(ticket.getId()) != null);
        assertThat(journal.getWaterMark(), sameJSONAs(testJournalWatermark).allowingExtraUnexpectedFields());
    }

    @Test
    public void testCountWatermarkedTickets() throws Exception {
        assertThat(watermarkService.countWaterMarkedTickets(), is(0l));
        watermarkService.watermarkNextTicket();
        assertThat(watermarkService.countWaterMarkedTickets(), is(1l));
    }

    @Test
    public void testCountTodoTickets() throws Exception {
        assertThat(watermarkService.countTodoTickets(), is(3l));
    }
}
