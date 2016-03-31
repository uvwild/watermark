package test.springer.data;

/**
 * Created by uv on 31.03.2016 for watermark
 */

import lombok.Getter;

@Getter
public class MyDocument {

    private Long id;
    private DocType content;
    private String title;
    private String author;
    private String topic;
    private String waterMark;

    public Long getId() {
        return id;
    }

    public boolean isWaterMarked() {
        return (waterMark != null);
    }
}
