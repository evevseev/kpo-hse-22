package application;

import java.util.Scanner;

public class ConsoleCore {
    final static public String COMMAND_GET_BOOK = "/take";
    final static public String COMMAND_PUT_BOOK = "/put";
    final static public String COMMAND_ASK_IF_PRESENT = "/ask";
    final static public String COMMAND_LIST_TAKEN_BOOKS = "/list";
    final static public String COMMAND_SEE_ALL_BOOKS = "/all";
    final static public String COMMAND_EXIT = "/exit";

    final static private Scanner scanner = new Scanner(System.in);


    public void run() {
        boolean exitFlag = false;
        while (!exitFlag) {
            exitFlag = dispatcher(scanner.nextLine());
        }
    }

    private boolean dispatcher(String rawInput) {
        String[] split = rawInput.trim().split(" ", 2);

        if (split.length == 0) {
            return false;
        }

        String command = split[0].toLowerCase();
        String data = (split.length > 1) ? split[1] : "";

        switch (command) {
            case COMMAND_GET_BOOK:
                handleGetBook(data);
                break;
            case COMMAND_PUT_BOOK:
                handlePutBook(data);
                break;
            case COMMAND_ASK_IF_PRESENT:
                handleAskIfPresent(data);
                break;
            case COMMAND_LIST_TAKEN_BOOKS:
                handleAllBooks(data);
                break;
            case COMMAND_SEE_ALL_BOOKS:
                handleListBooks(data);
                break;
            case COMMAND_EXIT:
            default:
                return false;
        }
        return false;
    }

    // - Command Handlers
    private void handleGetBook(String data) {

    }

    private void handleListBooks(String data) {

    }

    private void handlePutBook(String data) {

    }

    private void handleAllBooks(String data) {

    }

    private void handleAskIfPresent(String data) {

    }
}
