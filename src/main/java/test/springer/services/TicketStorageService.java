package test.springer.services;

import test.springer.data.MyDocument;
import test.springer.data.MyTicket;

/**
 * extra service layer for decoupling the underlying storage..
 * Created by uv on 04.04.2016 for watermark
 */
public interface TicketStorageService {
    MyTicket getTicket(MyDocument document);

    MyTicket get(Long ticketId);

    long countWaterMarkedTickets();

    long countTodoTickets();

    void clearAllTickets();

    MyTicket getNextTodoTicket();
}
