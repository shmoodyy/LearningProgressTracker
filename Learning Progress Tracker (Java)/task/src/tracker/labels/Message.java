package tracker.labels;

public enum Message {

    WELCOME("Learning Progress Tracker"),
    NO_INPUT("No input."),
    UNKNOWN("Unknown command!"),
    FAILED_BACK("Enter 'exit' to exit the program."),
    ENTER_STUDENTS("Enter student credentials or 'back' to return:"),
    STUDENT_ADDED("The student has been added."),
    INCORRECT_CREDS("Incorrect credentials."),
    INCORRECT_FIRST_NAME("Incorrect first name."),
    INCORRECT_LAST_NAME("Incorrect last name."),
    INCORRECT_EMAIL("Incorrect email."),
    EMAIL_TAKEN("This email is already taken."),
    STUDENTS_HEADING("Students:"),
    NO_STUDENTS("No students found."),
    ENTER_POINTS("Enter an id and points or 'back' to return:"),
    POINTS_UPDATED("Points updated."),
    INCORRECT_POINTS("Incorrect points format."),
    ENTER_ID("Enter an id or 'back' to return:"),
    INCORRECT_ID("No student is found for id="),
    ENTER_COURSE("Type the name of a course to see details or 'back' to quit:"),
    UNKNOWN_COURSE("Unknown course."),
    BYE("Bye!");

    private final String msg;

    Message(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}