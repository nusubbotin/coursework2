package diary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Task {
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final String DATE_TYPE_FORMAT = "dd.MM.yyyy HH:mm:ss";
    protected static int numSequence = 0;
    private int id;
    private String header;
    private String description;

    private LocalDateTime dateTime = null;

    private TaskType taskType = null;
    private RepeatabilityType repeatabilityType;


    protected enum TaskType{
        PERSONAL("личные"), WORK_TASKS("рабочие задачи");

        private String typeName;

        TaskType(String typeName) {
            this.typeName = typeName;
        }

        public String getTypeName() {
            return typeName;
        }
    }
    protected enum RepeatabilityType{
        ONEW_TYPE("однократно"), DAILY("ежедневно"), WEEKLY("еженедельно"), MONTHLY("ежемесячно"), ANNUALY("ежегодно");

        private String name;

        RepeatabilityType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public Task(String header, String description, Task.RepeatabilityType repeatabilityType) {
        this.id = nextSequence();
        this.header = header;
        this.description = description;
        this.repeatabilityType = repeatabilityType;
    }

    public Task(String header, String description, String DateTime, String taskType, String repeatabilityType) {
        this.id = nextSequence();
        this.header = header;
        this.description = description;
        setDateTime(DateTime);
        setTaskType(taskType);
        setRepeatabilityType(repeatabilityType);
    }

    public static int nextSequence() {
        return numSequence++;
    }

    public static void resetSequence() {
        numSequence = 0;
    }

    public void setHeader(String header) {
        if (header == null || header.isEmpty()) {
            throw new IllegalArgumentException("Некорректный параметр header. Заголовок не может быть пустым!");
        }
        this.header = header;
    }

    public void setDescription(String description) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Некорректный параметр description. Описание не может быть пустым!");
        }
        this.description = description;
    }

    public static LocalDateTime strToDateTime(String dateTimeStr) {
        try {
            return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(DATE_TYPE_FORMAT));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Некорректный формат даты. Задайте дату и время в формате " + DATE_TYPE_FORMAT);
        }
    }
    public void setDateTime(String dateTimeStr){
            dateTime = strToDateTime(dateTimeStr);
    }
    public static TaskType findTaskType(String name){
       for (TaskType value : TaskType.values()) {
            if (value.getTypeName().equals(name)){
                    return value;
                }
            }
       throw new IllegalArgumentException("Указан некорректный тип задания");
    }
    public void setTaskType(String taskName) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Тип задачи не может быть пустым!");
        }
        this.taskType = findTaskType(taskName);
    }

    public static int getNumSequence() {
        return numSequence;
    }

    public static void setNumSequence(int numSequence) {
        Task.numSequence = numSequence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public RepeatabilityType getRepeatabilityType() {
        return repeatabilityType;
    }

    public void setRepeatabilityType(RepeatabilityType repeatabilityType) {
        this.repeatabilityType = repeatabilityType;
    }

    public static RepeatabilityType findRepeatabilityType(String name){
        for (RepeatabilityType value : RepeatabilityType.values()) {
            if (value.getName().equals(name)){
                return value;
            }
        }
        throw new IllegalArgumentException("Указан некорректный тип повторяемости");
    }

    public void setRepeatabilityType(String repeatabilityName) {
        if (repeatabilityName == null || repeatabilityName.isEmpty()) {
            throw new IllegalArgumentException("Тип интервала не может быть пустым");
        }
        this.repeatabilityType = findRepeatabilityType(repeatabilityName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(header, task.header) && Objects.equals(description, task.description) && Objects.equals(dateTime, task.dateTime) && taskType == task.taskType && repeatabilityType == task.repeatabilityType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
