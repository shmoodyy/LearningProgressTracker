package tracker;

import tracker.labels.Command;
import tracker.labels.Message;

public class Main {
    public static void main(String[] args) {
        System.out.println(Message.WELCOME.getMsg());
        Command inputCommand;
        do {
            inputCommand = Command.newCommand();
        } while (inputCommand != Command.EXIT);
        System.out.println(Message.BYE.getMsg());
    }
}