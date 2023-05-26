package tracker;

import tracker.labels.Command;
import tracker.labels.Message;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Student {

    final static String NAME_REGEX = "[a-zA-Z]+(?:[-'][a-zA-Z]+)*";
    final static String EMAIL_REGEX = "([a-zA-Z0-9]+([_.])*[a-zA-Z0-9]*@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+)";
    final static int MAX_JAVA_POINTS = 600;
    final static int MAX_DSA_POINTS = 400;
    final static int MAX_DB_POINTS = 480;
    final static int MAX_SPRING_POINTS = 550;
    String[] credentials;
    static int studentID = 10000;
    static Map<Integer, int[]> pointsMap = new HashMap<>();
    static Map<Integer, Student> studentMap = new HashMap<>();
    static Set<String> emailSet = new HashSet<>();
    static List<String> congratulations = new ArrayList<>();
    static double javaStudents, dsaStudents, dbStudents, springStudents;
    static double totalJavaPoints, totalDSAPoints, totalDBPoints, totalSpringPoints;
    static double javaAverage, dsaAverage, dbAverage, springAverage;
    static int javaActivity, dsaActivity, dbActivity, springActivity;
    static Set<Integer> graduates = new HashSet<>();
    static String mostPopular, leastPopular, highestActive, lowestActive, easiestCourse, hardestCourse;

    Student(String[] credentials) {
        this.credentials = credentials;
    }

    public static String getMostPopular() {
        return mostPopular;
    }

    public static void setMostPopular(String mostPopular) {
        Student.mostPopular = mostPopular;
    }

    public static String getLeastPopular() {
        return leastPopular;
    }

    public static void setLeastPopular(String leastPopular) {
        Student.leastPopular = leastPopular;
    }

    public static String getHighestActive() {
        return highestActive;
    }

    public static void setHighestActive(String highestActive) {
        Student.highestActive = highestActive;
    }

    public static String getLowestActive() {
        return lowestActive;
    }

    public static void setLowestActive(String lowestActive) {
        Student.lowestActive = lowestActive;
    }

    public static String getEasiestCourse() {
        return easiestCourse;
    }

    public static void setEasiestCourse(String easiestCourse) {
        Student.easiestCourse = easiestCourse;
    }

    public static String getHardestCourse() {
        return hardestCourse;
    }

    public static void setHardestCourse(String hardestCourse) {
        Student.hardestCourse = hardestCourse;
    }

    public static void statistics() {
        System.out.print(courseRanks());
        do {
            String input = Command.scanner.nextLine().toLowerCase().strip();
            if (input.equalsIgnoreCase(Command.BACK.getCmd())) {
                break;
            } else if (!input.matches("java|dsa|databases|spring")){
                System.out.println(Message.UNKNOWN_COURSE.getMsg());
            } else {
                switch (input) {
                    case "java"         -> courseDetails("Java");
                    case "dsa"          -> courseDetails("DSA");
                    case "databases"    -> courseDetails("Databases");
                    case "spring"       -> courseDetails("Spring");
                }
            }
        } while (true);
    }

    public static void ranker(String category, double javaStat, double dsaStat, double dbStat, double springStat) {
        List<String> most = new ArrayList<>(4);
        List<String> least = new ArrayList<>(4);
        StringBuilder alpha = new StringBuilder();
        StringBuilder omega = new StringBuilder();
        double max = Math.max(javaStat, Math.max(dsaStat, Math.max(dbStat, springStat)));
        if (max > 0.0) {
            most.add(javaStat == max ? "Java" : "");
            most.add(dsaStat == max ? "DSA" : "");
            most.add(dbStat == max ? "Databases" : "");
            most.add(springStat == max ? "Spring" : "");

            double min = Math.min(javaStat, Math.min(dsaStat, Math.min(dbStat, springStat)));
            least.add(javaStat == min && javaStat != max ? "Java" : "");
            least.add(dsaStat == min && dsaStat != max ? "DSA" : "");
            least.add(dbStat == min && dbStat != max ? "Databases" : "");
            least.add(springStat == min && springStat != max ? "Spring" : "");
        }

        for (String course : most) {
            if (course.length() > 0) alpha.append(alpha.length() > 0 ?  ", " + course : course);
        } for (String course : least) {
            if (course.length() > 0) omega.append(omega.length() > 0 ?  ", " + course : course);
        }

        setRanks(category, alpha, omega);
    }

    public static void setRanks(String category, StringBuilder alpha, StringBuilder omega) {
        switch (category) {
            case "popularity" -> {
                setMostPopular(alpha.length() > 0 ? alpha.toString() : "n/a");
                setLeastPopular(omega.length() > 0 ? omega.toString() : "n/a");
            } case "activity" -> {
                setHighestActive(alpha.length() > 0 ? alpha.toString() : "n/a");
                setLowestActive(omega.length() > 0 ? omega.toString() : "n/a");
            } case "difficulty" -> {
                setEasiestCourse(alpha.length() > 0 ? alpha.toString() : "n/a");
                setHardestCourse(omega.length() > 0 ? omega.toString() : "n/a");
            }
        }
    }

    public static void popularity() {
        for (var entry : pointsMap.entrySet()) {
            javaStudents += entry.getValue()[0] > 0 ? 1 : 0;
            dsaStudents += entry.getValue()[1] > 0 ? 1 : 0;
            dbStudents += entry.getValue()[2] > 0 ? 1 : 0;
            springStudents += entry.getValue()[3] > 0 ? 1 : 0;
        }
        ranker("popularity", javaStudents, dsaStudents, dbStudents, springStudents);
    }

    public static void activity() {
        ranker("activity", javaActivity, dsaActivity, dbActivity, springActivity);
    }

    public static void difficulty() {
        for (var entry : pointsMap.entrySet()) {
            totalJavaPoints += entry.getValue()[0];
            totalDSAPoints += entry.getValue()[1];
            totalDBPoints += entry.getValue()[2];
            totalSpringPoints += entry.getValue()[3];
        }
        javaAverage = totalJavaPoints / javaActivity;
        dsaAverage = totalDSAPoints / dsaActivity;
        dbAverage = totalDBPoints / dbActivity;
        springAverage = totalSpringPoints / springActivity;

        ranker("difficulty", javaAverage, dsaAverage, dbAverage, springAverage);
    }

    public static String courseRanks() {
        popularity();
        activity();
        difficulty();
        System.out.println(Message.ENTER_COURSE.getMsg());
        return String.format("""
                Most popular: %s
                Least popular: %s
                Highest activity: %s
                Lowest activity: %s
                Easiest course: %s
                Hardest course: %s
                """
                , getMostPopular(), getLeastPopular(), getHighestActive(), getLowestActive()
                , getEasiestCourse(), getHardestCourse()
        );
    }

    public static void courseDetails(String courseName) {
        System.out.println(courseName);
        System.out.print("""
                id    points    completed
                """);

        switch (courseName) {
            case "Java"         -> courseRoster(javaStudents, MAX_JAVA_POINTS,0);
            case "DSA"          -> courseRoster(dsaStudents, MAX_DSA_POINTS,1);
            case "Databases"    -> courseRoster(dbStudents, MAX_DB_POINTS,2);
            case "Spring"       -> courseRoster(springStudents, MAX_SPRING_POINTS,3);
        }
    }

    // Important sorting algorithm
    public static void courseRoster(double courseStudents, int MAX_COURSE_POINTS ,int courseIndex) {
        Map<Integer, Integer> studentCoursePointsMap = new HashMap<>();

        if (courseStudents > 0) {
            for (var entry : pointsMap.entrySet()) {
                studentCoursePointsMap.put(entry.getKey(), entry.getValue()[courseIndex]);
            }

            //Make list of new map entries for students and their course points
            List<Map.Entry<Integer, Integer>> sortedList = new ArrayList<>(studentCoursePointsMap.entrySet());

            // Sort list by value in descending order, and if their values are equal, sort by keys in ascending order
            sortedList.sort(Map.Entry.<Integer, Integer>comparingByValue().reversed() // sorting by descending points
                    .thenComparing(Map.Entry.comparingByKey())); // sorting by ascending studentID

            // LinkedHashMap to maintain the sorted order (it stores as added)
            Map<Integer, Integer> sortedMap = new LinkedHashMap<>();
            for (Map.Entry<Integer, Integer> entry : sortedList) {
                sortedMap.put(entry.getKey(), entry.getValue());
            }

            // Print table of sorted map (the LinkedHashMap)
            for (Map.Entry<Integer, Integer> entry : sortedMap.entrySet()) {
                if (entry.getValue() > 0) {
                    System.out.printf("""
                                    %-6d%-10d%.1f%%
                                    """, entry.getKey(), entry.getValue()
                            , BigDecimal.valueOf(((double) entry.getValue() / (double) MAX_COURSE_POINTS) * 100)
                                    .setScale(1, RoundingMode.HALF_UP));
                }
            }

        }
    }

    public static void addStudent() {
        System.out.println(Message.ENTER_STUDENTS.getMsg());
        int studentCount = 0;
        do {
            String credentials = Command.scanner.nextLine();
            String[] credentialsArray = credentials.split("\\s+");
            int emailIndex = credentialsArray.length - 1;
            if (credentials.equalsIgnoreCase(Command.BACK.getCmd())) {
                break;
            } else if (!credentials.contains(" ") || credentials.matches("\\s+") || credentialsArray.length < 3) {
                System.out.println(Message.INCORRECT_CREDS.getMsg());
            } else if (validCredentials(credentialsArray) && studentEnrollment(new Student(credentialsArray), emailIndex)) {
                studentCount++;
                System.out.println(Message.STUDENT_ADDED.getMsg());
            }
        } while (true);
        System.out.printf("Total %d students have been added.%n", studentCount);
    }

    public static boolean validCredentials(String[] credentialsArray) {
        int credentialsLength = credentialsArray.length;
        int emailIndex = credentialsArray.length - 1;
        for (int i = 0; i < credentialsLength; i++) {
            if (i == 0 && (credentialsArray[i].length() < 2 || !credentialsArray[i].matches(NAME_REGEX))) {
                System.out.println(Message.INCORRECT_FIRST_NAME.getMsg());
                return false;
            } else if (i != emailIndex && (credentialsArray[i].length() < 2 ||
                    !credentialsArray[i].matches(NAME_REGEX))) {
                System.out.println(Message.INCORRECT_LAST_NAME.getMsg());
                return false;
            } else if (i == emailIndex && (credentialsArray[i].length() < 2 ||
                    !credentialsArray[i].matches(EMAIL_REGEX))) {
                System.out.println(Message.INCORRECT_EMAIL.getMsg());
                return false;
            }
        }
        return true;
    }

    public static boolean studentEnrollment(Student student, int emailIndex) {
        if (emailSet.contains(student.credentials[emailIndex])) {
            System.out.println(Message.EMAIL_TAKEN.getMsg());
            return false;
        } else {
            int currentID = Student.studentID++;
            emailSet.add(student.credentials[emailIndex]);
            studentMap.put(currentID, student);
            pointsMap.put(currentID, new int[4]);
            return true;
        }
    }

    public static void listStudents() {
        if (studentMap.size() != 0) {
            System.out.println(Message.STUDENTS_HEADING.getMsg());
            studentMap.forEach((k, v) -> System.out.println(k));
        } else {
            System.out.println(Message.NO_STUDENTS.getMsg());
        }
    }

    public static void addPoints() {
        System.out.println(Message.ENTER_POINTS.getMsg());
        do {
            String input = Command.scanner.nextLine();
            if (input.equalsIgnoreCase(Command.BACK.getCmd())) {
                break;
            } else if (input.split("\\s+").length != 5) {
                System.out.println(Message.INCORRECT_POINTS.getMsg());
            } else {
                storePoints(input);
            }
        } while (true);
    }

    public static void storePoints(String input) {
        String[] pointsArray = input.split("\\s+");
        try {
            int[] points = new int[4];
            if (validPoints(pointsArray, points)) {
                int id = Integer.parseInt(pointsArray[0]);
                pointsMap.put(id, points);
                System.out.println(Message.POINTS_UPDATED.getMsg());
                if (pointsMap.get(id)[0] == MAX_JAVA_POINTS) {
                    congratulationMessage(id, "Java");
                } if (pointsMap.get(id)[1] == MAX_DSA_POINTS) {
                    congratulationMessage(id, "DSA");
                } if (pointsMap.get(id)[2] == MAX_DB_POINTS) {
                    congratulationMessage(id, "Databases");
                } if (pointsMap.get(id)[3] == MAX_SPRING_POINTS) {
                    congratulationMessage(id, "Spring");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(Message.INCORRECT_POINTS.getMsg());
        }
    }

    public static void congratulationMessage(int id, String course) {
        graduates.add(id);
        String fullName = studentMap.get(id).credentials[0]
                + " " + studentMap.get(id).credentials[1];
        String email = studentMap.get(id).credentials[2];
        congratulations.add(String.format("""
                            To: %s
                            Re: Your Learning Progress
                            Hello, %s! You have accomplished our %s course!
                            """, email, fullName, course));
    }

    public static boolean validPoints(String[] pointsArray, int[] points) {
        int number;
        for (int i = 0; i < pointsArray.length; i++) {
            if (i == 0) {
                try {
                    number = Integer.parseInt(pointsArray[i]);
                    if (!studentMap.containsKey(number)) {
                        System.out.println(Message.INCORRECT_ID.getMsg() + number + ".");
                        return false;
                    }
                } catch (Exception e) {
                    System.out.println(Message.INCORRECT_ID.getMsg() + pointsArray[i] + ".");
                }
            } else {
                number = Integer.parseInt(pointsArray[i]);
                if (number < 0) {
                    System.out.println(Message.INCORRECT_POINTS.getMsg());
                    return false;
                } else {
                    points[i - 1] = pointsMap.get(Integer.parseInt(pointsArray[0]))[i - 1] + number;
                    if (number > 0) {
                        if (i == 1) javaActivity++;
                        if (i == 2) dsaActivity++;
                        if (i == 3) dbActivity++;
                        if (i == 4) springActivity++;
                    }
                }
            }
        }
        return true;
    }

    public static void findStudent() {
        System.out.println(Message.ENTER_ID.getMsg());
        do {
            String input = Command.scanner.nextLine();
            if (input.equalsIgnoreCase(Command.BACK.getCmd())) {
                break;
            } else {
                try {
                    int idAsInt = Integer.parseInt(input);
                    if (!pointsMap.containsKey(idAsInt)) {
                        System.out.println(Message.INCORRECT_ID.getMsg() + input + ".");
                    } else {
                        System.out.printf("%d points: Java=%d; DSA=%d; Databases=%d; Spring=%d%n",
                                idAsInt, pointsMap.get(idAsInt)[0], pointsMap.get(idAsInt)[1]
                                , pointsMap.get(idAsInt)[2], pointsMap.get(idAsInt)[3]);
                    }
                } catch (Exception e) {
                    System.out.println(Message.INCORRECT_ID.getMsg() + input + ".");
                }
            }
        } while (true);
    }

    public static void notifyStudent() {
        int studentCount = graduates.size();
        if (congratulations.size() > 0) {
            congratulations.forEach(System.out::print);
        }
        congratulations.clear();
        graduates.clear();
        System.out.printf("Total %d students have been notified.%n", studentCount);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Arrays.equals(credentials, student.credentials);
    }

    @Override
    public int hashCode() {
        int result = 42;
        result = 31 * result + Arrays.hashCode(credentials);
        return result;
    }
}