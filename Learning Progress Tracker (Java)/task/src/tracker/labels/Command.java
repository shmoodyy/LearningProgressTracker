package tracker.labels;

import tracker.Student;

import java.util.Scanner;

public enum Command {

    ADD_STUDENTS("add students"),
    ADD_POINTS("add points"),
    LIST("list"),
    FIND("find"),
    STATISTICS("statistics"),
    NOTIFY("notify"),
    BACK("back"),
    EXIT("exit");

    public static final Scanner scanner = new Scanner(System.in);

    private final String cmd;

    Command(String cmd) {
        this.cmd = cmd;
    }

    public String getCmd() {
        return cmd;
    }
    public static Command newCommand() {
        Command validCommand = null;
        String input;
        do {
            input = getValidInput();
            for (Command value : Command.values()) {
                if (input.equalsIgnoreCase(value.getCmd())) validCommand = value;
            }
            if (validCommand == null) System.out.println(Message.UNKNOWN.getMsg());
            else {
                switch (validCommand) {
                    case BACK           -> System.out.println(Message.FAILED_BACK.getMsg());
                    case ADD_STUDENTS   -> Student.addStudent();
                    case LIST           -> Student.listStudents();
                    case ADD_POINTS     -> Student.addPoints();
                    case FIND           -> Student.findStudent();
                    case STATISTICS     -> Student.statistics();
                    case NOTIFY         -> Student.notifyStudent();
                }
            }
        } while (validCommand == null);
        return validCommand;
    }

    public static String getValidInput() {
        String input;

        do {
            input = scanner.nextLine().strip();
                if (input.isEmpty()) {
                System.out.println(Message.NO_INPUT.getMsg());
            }
        } while (input.isEmpty());

        return input;
    }
}