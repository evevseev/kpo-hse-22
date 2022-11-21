public class Student extends Person {
    private Boolean isPresent;
    private Integer lessonGrade;

    public Student() {
        super();
    }

    /**
     * Creates a new student.
     *
     * @param firstName Student's first name.
     * @param lastName  Student's last name.
     */
    public Student(String firstName, String lastName) {
        super(firstName, lastName);
    }

    /**
     * Returns student's presence.
     */
    public Boolean isPresent() {
        return isPresent;
    }

    /**
     * Sets student's presence status.
     */
    public void setPresent(Boolean present) {
        isPresent = present;
    }

    /**
     * Returns a grade from 1 to 10 for the lesson.
     */
    public Integer getLessonGrade() {
        return lessonGrade;
    }

    /**
     * Sets the grade for the lesson.
     *
     * @param lessonGrade
     * @throws GradeOutOfRangeException
     */
    public void setLessonGrade(Integer lessonGrade) throws GradeOutOfRangeException {
        if (lessonGrade != null && (lessonGrade < 1 || lessonGrade > 10)) {
            throw new GradeOutOfRangeException("Grade must be between 1 and 10");
        }
        this.lessonGrade = lessonGrade;
    }

    @Override
    public String toString() {
        return getFullName();
    }
}

