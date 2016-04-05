package test.springer.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by uv on 31.03.2016 for watermark
 */
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MyTicket {


    @JsonView(MyViews.Ticket.class)
    private Long id;

    @JsonView(MyViews.Ticket.class)
    private MyDocument document;

    public Long getDocumentId() {
        return document.getId();
    }

    // provide default ctor also for object mapper
    public MyTicket() {}

    public MyTicket(MyDocument document) {
        assert  (document != null);
        this.document = document;
    }

}
