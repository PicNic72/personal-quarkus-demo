package com.example;

import com.ctc.wstx.stax.WstxInputFactory;
import com.jayway.jsonpath.JsonPath;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;

/**
 * Simple REST resource that exercises all four declared dependencies:
 *
 * - woodstox-core : parses an XML snippet via the Woodstox StAX factory
 * - json-path : queries a JSON string with a JSONPath expression
 * - org.json : builds a response JSON object
 * - spring-core : uses StringUtils for string manipulation
 */
@Path("/demo")
public class DemoResource {

    private static final String SAMPLE_XML = "<greeting><message>Hello from Woodstox</message></greeting>";

    private static final String SAMPLE_JSON = "{\"store\":{\"book\":{\"title\":\"Quarkus in Action\",\"price\":29.99}}}";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String demo() throws Exception {

        // 1. woodstox-core — parse XML and read the first text element
        String xmlText = parseFirstXmlText(SAMPLE_XML);

        // 2. json-path — extract a value using a JSONPath expression
        String bookTitle = JsonPath.read(SAMPLE_JSON, "$.store.book.title");

        // 3. spring-core — capitalize words via StringUtils
        // String capitalised = StringUtils.capitalize(bookTitle.toLowerCase());
        String capitalised = "";
        if (StringUtils.isEmpty(bookTitle)) {
            capitalised = "";
        } else {
            capitalised = StringUtils.capitalize(bookTitle.toLowerCase());
        }

        // 4. org.json — assemble the final response payload
        JSONObject response = new JSONObject();
        response.put("xmlMessage", xmlText);
        response.put("jsonPathResult", bookTitle);
        response.put("springCapitalized", capitalised);
        response.put("status", "ok");

        return response.toString(2);
    }

    /** Uses the Woodstox WstxInputFactory to stream-parse a tiny XML string. */
    private String parseFirstXmlText(String xml) throws Exception {
        XMLInputFactory factory = new WstxInputFactory();
        XMLStreamReader reader = factory.createXMLStreamReader(new StringReader(xml));
        try {
            while (reader.hasNext()) {
                int event = reader.next();
                if (event == XMLStreamReader.CHARACTERS && !reader.isWhiteSpace()) {
                    return reader.getText();
                }
            }
        } finally {
            reader.close();
        }
        return "";
    }
}
