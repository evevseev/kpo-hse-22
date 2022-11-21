import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * This class is responsible for serializing and deserializing the student list and future entities.
 */
public class JsonSerializer {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Serializes a list of students to JSON.
     * @param students
     * @return JSON string
     * @throws JsonProcessingException
     */
    public static String serialize(List<Student> students) throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(students);
    }

    /**
     * Deserializes a list of students from JSON.
     * @param json string containing JSON
     * @return List of students
     * @throws JsonProcessingException
     */
    public static List<Student> deserialize(String json) throws JsonProcessingException {
        return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, Student.class));
    }
}
