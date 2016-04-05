package test.springer.services;

import org.springframework.stereotype.Service;
import test.springer.data.MyDocument;
import test.springer.data.MyTicket;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An simple implementation for the TicketStorageService based on ConcurrentHashMap
 * Created by uv on 31.03.2016 for watermark
 */
@Service
public class SimpleTicketStorageService<T> implements TicketStorageService {

    // local ticket storage
    static Map<Long, MyTicket> allTickets = new ConcurrentHashMap<>();    // fancy threadsafe map for tickets

    /**
     * @param document
     * @return old ticket for doc or new ticket if not found
     */
    public MyTicket getTicket(MyDocument document) {
        // avoid duplicate Tickets
        Optional<MyTicket> optionalTicket = allTickets.values().stream().filter(myTicket -> (myTicket.getDocumentId() == document.getId()))
                    .findFirst();
        if (optionalTicket.isPresent()) {
            return optionalTicket.get();
        } else {
            MyTicket myTicket = new MyTicket(document);
            synchronized (allTickets) {      // very pessimistic lock to make sure that the keySet().size() and the put are in the same "transaction"
                Long ticketId = Long.valueOf(allTickets.keySet().size());
                myTicket.setId(ticketId);
                allTickets.put(ticketId, myTicket);
            }
            return myTicket;
        }
    }

    @Override
    public MyTicket get(Long ticketId) {
        return allTickets.get(ticketId);
    }

    // count all watermarked tickets
    public long countWaterMarkedTickets() {
        return allTickets.keySet().stream().filter(key -> allTickets.get(key).getDocument().isWaterMarked()).count();
    }

    public long countTodoTickets() {
        return allTickets.keySet().stream().filter(key -> !(allTickets.get(key).getDocument().isWaterMarked())).count();
    }

    public void clearAllTickets() {
        allTickets.clear();
    }

    @Override
    public MyTicket getNextTodoTicket() {
        MyTicket foundTicket = allTickets.entrySet().stream()
                .filter(entry -> !(allTickets.get(entry.getKey()).getDocument().isWaterMarked()))
                .findFirst().get().getValue();
        return foundTicket;
    }
}
