import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonSerializer {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String serialize(List<Student> students) throws JsonProcessingException {
        return mapper.writeValueAsString(students);
    }

    public static List<Student> deserialize(String json) throws JsonProcessingException {
        return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, Student.class));
    }
}
