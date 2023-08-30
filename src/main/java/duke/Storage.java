package duke;

import duke.exceptions.DukeException;
import duke.exceptions.InvalidDateFormatException;
import duke.exceptions.InvalidInputException;
import duke.tasks.Deadline;
import duke.tasks.Event;
import duke.tasks.Task;
import duke.tasks.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Storage {
    private final String FILEPATH;
    public Storage(String FILEPATH){
        this.FILEPATH = FILEPATH;
    }

    public List<Task> load() {
        List<Task> list = new ArrayList<>();
        try {
            File f = new File(FILEPATH);
            Files.createDirectories(Paths.get("data"));
            f.createNewFile();
            Scanner s = new Scanner(f);
            list = new ArrayList<Task>();
            while (s.hasNext()) {
                loadTask(s.nextLine(), list);
            }
        } catch (IOException e) {
            list = new ArrayList<Task>();
        }
        return list;
    }

    public void loadTask(String input, List<Task> list) {
        boolean isMarked;
        int markedIndex = input.indexOf("|");
        isMarked = input.charAt(markedIndex + 2) == '1';
        int titleIndex = input.indexOf("|", markedIndex + 1) + 2;
        try {
            if (input.startsWith("T")) {
                String title = input.substring(titleIndex);
                list.add(new Todo(title, isMarked));
            } else if (input.startsWith("D")) {
                int byIndex = input.indexOf("|", titleIndex);
                String title = input.substring(titleIndex, byIndex);
                String dueDateString = input.substring(byIndex + 2);
                list.add(new Deadline(title,parseDate(dueDateString),isMarked));
            } else if (input.startsWith("E")){
                int fromIndex = input.indexOf("|", titleIndex);
                String title = input.substring(titleIndex, fromIndex);
                int toIndex = input.indexOf("|", fromIndex + 1);
                String from = input.substring(fromIndex + 2, toIndex);
                String to = input.substring( toIndex + 2);
                list.add(new Event(title,parseDate(from),parseDate(to),isMarked));
            } else {
                throw new InvalidInputException();
            }
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }
    public void save(TaskList tasks) {
        try {
            FileWriter fw = new FileWriter(FILEPATH);
            for (int i = 0; i < tasks.list.size(); i++) {
                fw.write(tasks.list.get(i).toSave() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public LocalDateTime parseDate(String input) throws InvalidDateFormatException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(input, formatter);
            return dateTime;
        } catch (Exception e) {
            throw new InvalidDateFormatException();
        }
    }
}
