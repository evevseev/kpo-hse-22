import java.util.List;
import java.util.Scanner;

public class ConsoleApplication {
    private static class DialogState {
        DialogState(Student student, Status status) {
            this.student = student;
            this.status = status;
        }

        public Student student;
        public Status status;

        private enum Status {
            MAIN_MENU, AWAITING_GRADE, AWAITING_PRESENCE,
        }
    }

    // COMMANDS
    private static final String RANDOM_STUDENT = "/r";
    private static final String LIST_STUDENTS = "/l";
    private static final String HELP = "/h";
    private static final String EXIT = "/e";

    // DIALOG STATE
    private final DialogState state = new DialogState(null, DialogState.Status.MAIN_MENU);

    // STUDENT LIST
    private final StudentList studentPicker;

    private final Scanner scanner = new Scanner(System.in);

    public ConsoleApplication(StudentList studentPicker) {
        this.studentPicker = studentPicker;
    }

    public void run() {
        boolean exitFlag = true;
        while (exitFlag) {
            exitFlag = parseCommand();
        }
    }

    public List<Student> getStudents() {
        return studentPicker.getStudents();
    }

    /**
     * Returns true if the application should continue running.
     */
    private boolean parseCommand() {
        switch (state.status) {
            case MAIN_MENU -> {
                return mainMenuStateHandler();
            }
            case AWAITING_GRADE -> awaitingGradeStateHandler();
            case AWAITING_PRESENCE -> awaitingPresenceStateHandler();
        }
        return true;
    }

    /**
     * Returns false if exit command was entered.
     */
    private boolean mainMenuStateHandler() {
        System.out.print("Enter command: ");
        String input = scanner.nextLine();
        switch (input) {
            case RANDOM_STUDENT -> selectRandomStudent();
            case LIST_STUDENTS -> printStudentsWithGrades();
            case HELP -> printHelp();
            case EXIT -> {
                return false;
            }
            default -> System.out.println("Unknown command. Type /h for help.");
        }
        return true;
    }

    private void awaitingGradeStateHandler() {
        System.out.print("Enter " + state.student.getFullName() + "'s grade (1-10): ");
        try {
            state.student.setLessonGrade(Integer.parseInt(scanner.nextLine()));
            state.status = DialogState.Status.MAIN_MENU;
        } catch (GradeOutOfRangeException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid grade format.");
        }
    }

    private void awaitingPresenceStateHandler() {
        System.out.print("Is " + state.student.getFullName() + " present (true/false): ");
        boolean isPresent = Boolean.parseBoolean(scanner.nextLine());
        state.student.setPresent(isPresent);
        if (isPresent) {
            state.status = DialogState.Status.AWAITING_GRADE;
        } else {
            state.status = DialogState.Status.MAIN_MENU;
        }
    }

    private void selectRandomStudent() {
        try {
            state.student = studentPicker.getRandomStudentToMark();
            if (state.student.isPresent() == null) {
                state.status = DialogState.Status.AWAITING_PRESENCE;
            } else {
                state.status = DialogState.Status.AWAITING_GRADE;
            }
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printStudentsWithGrades() {
        List<Student> students = studentPicker.getStudentsWithGrades();

        if (students.isEmpty()) {
            System.out.println("No students with grades.");
        } else {
            for (var student : students) {
                System.out.println(student.getFullName() + " " + student.getLessonGrade());
            }
        }
    }

    private void printHelp() {
        System.out.println("""
                Commands:
                • /r – selects random student, asks if present
                • /l – list of students who received a grade
                • /h – help, lists commands and how to use them
                • /e – exit and save
                """);
    }
}
