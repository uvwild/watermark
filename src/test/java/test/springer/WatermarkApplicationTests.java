package test.springer;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test.springer.data.*;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


/**
 * make this abstract so we can inherit the spring config from test subclasses
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WatermarkApplication.class)
abstract public class WatermarkApplicationTests {

    @Test
    @Ignore
    public void printEnumsValues() {
        Stream.of(Content.values()).forEach(value -> System.err.println(value));
        Stream.of(Topic.values()).forEach(value -> System.err.println(value));
    }
}
