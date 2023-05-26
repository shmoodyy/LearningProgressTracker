package tracker;

import org.junit.jupiter.api.*;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentTest {
    private boolean enrollmentPasses = true;

    static List<String> pointsStringFactory() {
        return List.of("10000 8 7 7 5", "10000 7 6 9 7", "10000 6 5 5 0"
                , "10001 8 0 8 6", "10001 7 0 0 0", "10001 9 0 0 5");
    }

    @ParameterizedTest
    @DisplayName("Adding Students, including one with a duplicate email")
    @ValueSource(strings = {"John Doe jdoe@yahoo.com", "Jane Spark jspark@gmail.com", "Jane Spark jspark@gmail.com"})
    void parameterizedTest1(String credentials) {
        String[] credentialsArray = credentials.split("\\s+");
        if (!Student.studentEnrollment(new Student(credentialsArray), credentialsArray.length - 1)) {
            enrollmentPasses = false;
        }
    }

    @ParameterizedTest
    @DisplayName("Adding points for the two already added students")
    @MethodSource("pointsStringFactory")
    void parameterizedTest2(String points) {
        Student.storePoints(points);
    }

    @AfterAll
    @DisplayName("Testing when email is taken:")
    @Test
    public void afterAll1() {
        assertFalse(enrollmentPasses);
    }

    @AfterAll
    @DisplayName("Testing list contains correct IDs")
    @Test
    void afterAll2() {
        int currentID = 10000;
        for (var entry : Student.studentMap.entrySet()) {
            assertEquals(currentID, entry.getKey());
            currentID++;
        }
    }

    @AfterAll
    @DisplayName("Testing course rankings output:")
    @Test
    public void afterAll3() {
        String expected = """
                Most popular: Java, Databases, Spring
                Least popular: DSA
                Highest activity: Java
                Lowest activity: DSA
                Easiest course: Java
                Hardest course: Spring
                """;
        Student.courseDetails("Java");
        Student.courseDetails("DSA");
        Student.courseDetails("Databases");
        Student.courseDetails("Spring");
        Assertions.assertEquals(expected, Student.courseRanks());
    }
}