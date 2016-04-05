package test.springer.data;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * Created by uv on 31.03.2016 for watermark
 */
public enum Content {
    BOOK("book"), JOURNAL("journal");

    @Getter
    private String name;

    Content(String name) {
        this.name = name;
    }

    @Override
    @JsonValue
    public String toString() {
        return name;
    }

}
