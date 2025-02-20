package duke.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task in the Duke application.
 * This class extends the base class Task and includes the start and end dates of the event.
 */
public class Event extends Task {

    private LocalDateTime from;
    private LocalDateTime to;
    private String printFromString;
    private String saveFromString;
    private String printToString;
    private String saveToString;

    /**
     * Constructs a new Event object with a title, start date, and end date.
     *
     * @param title    The title of the event task.
     * @param from     The start date of the event.
     * @param to       The end date of the event.
     * @param isMarked The marking status of the event.
     */
    public Event(String title, LocalDateTime from, LocalDateTime to, boolean isMarked) {
        super(title, isMarked);
        this.from = from;
        this.to = to;
        this.printFromString = this.from.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        this.printToString = this.to.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        this.saveFromString = this.from.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        this.saveToString = this.to.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
    @Override
    public Event clone() {
        return new Event(this.title, this.from, this.to, this.isMarked);
    }

    /**
     * Generates a string representation of the Event object.
     *
     * @return A formatted string indicating the status, title, start date, and end date of the event.
     */
    @Override
    public String toString() {
        String mark = super.isMarked ? "[X] " : "[ ] ";
        return "[E]" + mark + title + " (from: " + this.printFromString + " to: " + this.printToString + ")";
    }

    /**
     * Generates a formatted string to represent the Event object for saving.
     *
     * @return A formatted string for saving the Event object.
     */
    @Override
    public String toSave() {
        String res = "E";
        res += (isMarked ? "| 1" : "| 0");
        res += "| " + title;
        res += "| " + saveFromString;
        res += "| " + saveToString;
        return res;
    }

    /**
     * Retrieves the start date of the event.
     *
     * @return The start date of the event.
     */
    public LocalDateTime getFrom() {
        return this.from;
    }

    /**
     * Retrieves the end date of the event.
     *
     * @return The end date of the event.
     */
    public LocalDateTime getTo() {
        return to;
    }
}
