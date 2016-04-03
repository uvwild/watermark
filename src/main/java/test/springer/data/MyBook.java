package test.springer.data;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by uv on 03.04.2016 for watermark
 */
@Getter @Setter
public class MyBook extends MyDocument {

    @JsonView(Views.Document.class)
    @Getter @Setter
    private Topic topic;

    public MyBook() {
        super.setDocType(DocType.BOOK);
        super.setJsonView(Views.Book.class);
    }
}
