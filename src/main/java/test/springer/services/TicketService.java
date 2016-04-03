package test.springer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import test.springer.data.MyDocument;
import test.springer.data.MyTicket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by uv on 03.04.2016 for watermark
 */
@Service
public class TicketService {

    static Logger logger = Logger.getLogger(TicketService.class.toString());

    static Map<Long, MyTicket> activeTickets = new ConcurrentHashMap<>();    // fancy threadsafe map

    public MyTicket createTicket(MyDocument document) {
        MyTicket myTicket = new MyTicket(document);
        synchronized (activeTickets) {      // very pessimistic lock to make sure that the keySet().size() and the put are in the same "transaction"
            Long ticketId = Long.valueOf(activeTickets.keySet().size());
            myTicket.setId(ticketId);
            activeTickets.put(ticketId, myTicket);
        }
        return myTicket;
    }

    public MyTicket getNextTicket() {
        if (activeTickets.keySet().iterator().hasNext()) {
            Long nextId = activeTickets.keySet().iterator().next();
            return activeTickets.get(nextId);
        } else {
            return null;
        }
    }

    public void removeTicket(Long ticketId) {
        activeTickets.remove(ticketId);
    }


    public MyTicket getTicket(Long ticketId) {
        return activeTickets.get(ticketId);
    }

    public void watermarkNextTicket() {
        MyTicket ticket = getNextTicket();
        try {
            createWatermark(ticket);
        } catch (JsonProcessingException e) {
            logger.log(Level.WARNING, String.format("E1000 watermarking ticket %d for Document %d failed", ticket.getId(), ticket.getDocumentId()));
        }
    }

    public void createWatermark(MyTicket ticket) throws JsonProcessingException {
        MyDocument document = ticket.getDocument();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithView(document.getJsonView()).writeValueAsString(document);
        document.setWaterMark(json);
        removeTicket(ticket.getId());
    }
}
