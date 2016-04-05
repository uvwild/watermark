package test.springer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.springer.data.MyDocument;
import test.springer.data.MyTicket;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by uv on 03.04.2016 for watermark
 */
@Service
public class WatermarkService {

    static Logger logger = Logger.getLogger(WatermarkService.class.toString());

    @Autowired
    TicketStorageService ticketStorageService;


    @Autowired
    ObjectMapper myObjectMapper;    // objectmapper configured in WatermarkApplication

    public MyDocument watermarkNextTicket() {
        MyTicket ticket = ticketStorageService.getNextTodoTicket();
        try {
            return createWatermark(ticket);
        } catch (JsonProcessingException e) {
            logger.log(Level.WARNING, String.format("E1000 watermarking ticket %d for Document %d failed", ticket.getId(), ticket.getDocumentId()));
        }
        return null;  // no more tickets to watermark
    }

    public MyDocument createWatermark(MyTicket ticket) throws JsonProcessingException {
        MyDocument document = ticket.getDocument();
        // workaround the id field needed for object but not watermark
        SimpleBeanPropertyFilter theFilter = SimpleBeanPropertyFilter.serializeAllExcept("id");
        FilterProvider filters = new SimpleFilterProvider().addFilter("idFilter", theFilter);
        ObjectWriter writer = myObjectMapper.writerWithView(document.getJsonView()).with(filters);
        String json = writer.writeValueAsString(document);
        document.setWaterMark(json);
        return document;
    }

    /**
     * @param document
     * @return ticket for doc after storing document locally .... questionable design
     */
    public MyTicket getTicket(MyDocument document) {
        return ticketStorageService.getTicket(document);
    }

    public MyTicket getTicket(Long ticketId) {
        return ticketStorageService.get(ticketId);
    }

    public boolean hasTicket(Long ticketId) {
        return ticketStorageService.get(ticketId) != null;
    }

    public MyTicket getNextTodoTicket() {
        return ticketStorageService.getNextTodoTicket();
    }

    public long countWaterMarkedTickets() {
        return ticketStorageService.countWaterMarkedTickets();
    }

    public long countTodoTickets() {
        return ticketStorageService.countTodoTickets();
    }

    public void clearAllTickets() {
        ticketStorageService.clearAllTickets();
    }

}
