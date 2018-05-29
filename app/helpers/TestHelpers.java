package helpers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;



public class TestHelpers {
    public static JsonNode createJsonNode(String jsonString)
            throws JsonParseException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(jsonString);
    }
}
