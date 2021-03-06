package test.springer.data;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * Created by uv on 03.04.2016 for watermark
 */
public enum Topic {
    BUSINESS("business"), SCIENCE("science"), MEDIA("media");

    @Getter
    private String name;

    Topic (String name) {
        this.name = name;
    }

    @Override
    @JsonValue     // annotate method to obtain lower case values for enum
    public String toString() {
        return name;
    }
}
