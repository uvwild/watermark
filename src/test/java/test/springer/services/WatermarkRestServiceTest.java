package test.springer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.hamcrest.beans.SamePropertyValuesAs;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import test.springer.TestData;
import test.springer.WatermarkApplicationTests;
import test.springer.data.MyDocument;
import test.springer.data.MyTicket;
import test.springer.data.MyViews;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.beans.SamePropertyValuesAs.*;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by uv on 04.04.2016 for watermark
 */
@WebAppConfiguration
public class WatermarkRestServiceTest extends WatermarkApplicationTests implements TestData {

    @Autowired
    private WebApplicationContext context;

    //our mock mvc object for testing
    private MockMvc mvc;
    private MyDocument testBook;
    private String testBookJson;

    private ObjectReader ticketReader;
    private ObjectReader docReader;

    @Autowired
    ObjectMapper myObjectMapper;

    @Before
    public void setup() throws JsonProcessingException {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        testBook = createTestBook();
        testBookJson = makeJsonForDocument(testBook);
        ticketReader = myObjectMapper.readerWithView(MyViews.Ticket.class).forType(MyTicket.class);
        docReader = myObjectMapper.readerWithView(MyViews.Document.class).forType(MyDocument.class);
    }

    @Test
    public void testGetTicketForDocument() throws Exception {
        MvcResult result = mvc.perform(post(ApiDefinitions.getTicketForDoc).contentType(MediaType.APPLICATION_JSON_UTF8).content(testBookJson)).andExpect(status().isOk()).andReturn();
        String json = result.getResponse().getContentAsString();
        MyTicket ticket = ticketReader.readValue(json);
        assertThat(ticket.getDocumentId(), is(testBook.getId()));
    }

    @Test
    public void testGetWatermarkTicketForId() throws Exception {
        MvcResult result = mvc.perform(post(ApiDefinitions.getTicketForDoc).contentType(MediaType.APPLICATION_JSON_UTF8).content(testBookJson)).andExpect(status().isOk()).andReturn();
        String jsonTicket = result.getResponse().getContentAsString();
        MyTicket ticket = ticketReader.readValue(jsonTicket);
        MvcResult result2 = mvc.perform(get(ApiDefinitions.getTicketForDocId + "/4711").accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk()).andReturn();
        String jsonTicket2 = result2.getResponse().getContentAsString();
        MyTicket ticket2 = ticketReader.readValue(jsonTicket2);
        assertThat(ticket.getId(), is(ticket2.getId()));
        assertThat(ticket.getDocument(), samePropertyValuesAs(ticket2.getDocument()));
    }

    @Test
    public void testGetDocumentForTicketId() throws Exception {
        MvcResult resultTicket = mvc.perform(post(ApiDefinitions.getTicketForDoc).contentType(MediaType.APPLICATION_JSON_UTF8).content(testBookJson)).andExpect(
                status().isOk()).andReturn();
        String jsonticket = resultTicket.getResponse().getContentAsString();
        MyTicket ticket = ticketReader.readValue(jsonticket);

        MvcResult resultDoc = mvc.perform(get(ApiDefinitions.getDocForTicketId + "/0").accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk()).andReturn();
        String jsonDoc = resultDoc.getResponse().getContentAsString();
        MyDocument document = docReader.readValue(jsonDoc);
        assertThat(document.getId(), is(4711l));
    }

    @Test
    public void testIsWatermarkTicketFinished() throws Exception {
        // insert testbook to obtain ticket
        MvcResult resultTicket = mvc.perform(post(ApiDefinitions.getTicketForDoc).contentType(MediaType.APPLICATION_JSON_UTF8).content(testBookJson)).andExpect(
                status().isOk()).andReturn();
        String jsonticket = resultTicket.getResponse().getContentAsString();
        MyTicket ticket = ticketReader.readValue(jsonticket);

        // wait for processing the ticket
        String jsondoc = null;
        while (jsondoc == null) {
            Thread.sleep(500l);     // wait a moment for aysnc executor
            MvcResult finished = mvc.perform(post(ApiDefinitions.isTicketFinished + "/" + ticket.getId()).contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(
                    status().isOk()).andReturn();
            jsondoc = finished.getResponse().getContentAsString();
        }
        MyDocument document = docReader.readValue(jsondoc);
        assertThat(document.getId(), is(4711l));
    }

    // local helper
    private String makeJsonForDocument(MyDocument document) throws JsonProcessingException {
        ObjectWriter objectWriter = myObjectMapper.writerWithView(document.getJsonView());
        return objectWriter.writeValueAsString(document);
    }


}
