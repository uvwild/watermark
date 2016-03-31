package test.springer.data;

import lombok.Getter;

/**
 * Created by uv on 31.03.2016 for watermark
 */
@Getter
public class MyTicket {
    private Long id;
    private Long documentId;

    public MyTicket(MyDocument document) {
        this.documentId = document.getId();
    }
}
