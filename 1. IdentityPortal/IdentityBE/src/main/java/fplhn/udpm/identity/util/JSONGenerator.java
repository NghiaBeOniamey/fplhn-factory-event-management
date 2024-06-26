package fplhn.udpm.identity.util;

import com.fasterxml.jackson.databind.ObjectMapper;


public class JSONGenerator {

    public static String generateJSON(Object request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(request);
    }

}
