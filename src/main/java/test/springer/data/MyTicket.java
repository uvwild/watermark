package test.springer.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.beanutils.BeanUtils;
import test.springer.services.TicketService;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by uv on 31.03.2016 for watermark
 */
@Getter @Setter
public class MyTicket {
    private MyDocument document;

    private Long id;

    public Long getDocumentId() {
        return document.getId();
    }
    public MyTicket(MyDocument document) {
        assert  (document != null);
        this.document = document;
    }

}
