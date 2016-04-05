package test.springer.data;

/**
 * Created by uv on 31.03.2016 for watermark
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import static test.springer.data.Content.BOOK;

@Getter @Setter
public class MyDocument {

    @JsonView(MyViews.Document.class)
    private Long id;

    @JsonView(MyViews.Document.class)
    private Content content;

    @JsonView(MyViews.Document.class)
    private String title;

    @JsonView(MyViews.Document.class)
    private String author;

    @JsonView(MyViews.Book.class)
    private Topic topic;

    private Class jsonView;

    private String waterMark = null;

    // make sure we use the subclasses for creation
    protected MyDocument() {}

    @JsonIgnore
    public boolean isWaterMarked() {
        return (waterMark != null);
    }

    /**
     * override the lombok setter to synchronize the jsonView class....
     * TODO questionable design....
     * @param content
     */
    public void setContent(Content content) {
        this.content = content;
        switch (content) {
            case BOOK:
                this.jsonView = MyViews.Book.class;
                break;
            case JOURNAL:
                this.jsonView = MyViews.Journal.class;
                break;
        }
    }
}
