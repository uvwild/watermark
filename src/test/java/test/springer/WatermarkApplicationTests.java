package test.springer;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test.springer.data.*;
import test.springer.services.TicketService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WatermarkApplication.class)
public class WatermarkApplicationTests {

	@Test
	public void contextLoads() {
	}

	public MyDocument createTestBook() {
        MyBook book = new MyBook();
        book.setAuthor("Göthe");
        book.setId(4711l);
        book.setTitle("Feist");
        book.setTopic(Topic.MEDIA);
        return book;
    }

    public MyDocument createTestJournal() {
        MyJournal journal = new MyJournal();
        journal.setAuthor("Humphrey Bogart");
        journal.setTitle("Vom Winde verklebt");
        journal.setId(42l);
        return journal;
    }

    @Autowired
    TicketService ticketService;

    @Test
	public void testCreateBookJson() throws JsonProcessingException {
        MyTicket ticket = ticketService.createTicket(createTestBook());
        assert (ticket != null);
        ticketService.createWatermark(ticket);
        assert(ticketService.getTicket(ticket.getId()) == null);
    }

    @Test
    public void testCreateJournalJson() throws JsonProcessingException {
        MyTicket ticket = ticketService.createTicket(createTestBook());
        assert (ticket != null);
        ticketService.createWatermark(ticket);
        assert(ticketService.getTicket(ticket.getId()) == null);
    }

}
