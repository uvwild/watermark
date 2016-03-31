package test.springer.data;

/**
 * Created by uv on 31.03.2016 for watermark
 */
public enum DocType {
    BOOK("book"), JOURNAL("journal");

    private String type;

    DocType(String type) {
        this.type = type;
    }
}
