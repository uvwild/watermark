package test.springer.data;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Created by uv on 03.04.2016 for watermark
 */
@Getter @Setter
public class MyBook extends MyDocument {

//      inheritance with beans and json views is not working
//    @Getter @Setter
//    @JsonView(MyViews.Book.class)
//    private Topic topic;

    public MyBook() {
        super.setContent(Content.BOOK);
        super.setJsonView(MyViews.Book.class);
    }

}
