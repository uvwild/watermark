package test.springer.data;

/**
 * Created by uv on 31.03.2016 for watermark
 */

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MyDocument {

    private Long id;

    @JsonView(Views.Document.class)
    private DocType docType;
    @JsonView(Views.Document.class)
    private String title;
    @JsonView(Views.Document.class)
    private String author;

    Class jsonView;

    private String waterMark = null;

    // make sure we use the subclasses for creation
    protected MyDocument() {}

    public boolean isWaterMarked() {
        return (waterMark != null);
    }

}
