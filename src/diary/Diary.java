package diary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class Diary <T extends Task & DiaryTask> {
    Map<Integer, T> tasks = new HashMap<>();

    protected void addTask(T task){
        tasks.put(task.getId(), task);
    }

    protected Set<T> getDayTasks(LocalDate day){
        Set<T> dayTasks = new TreeSet<>(new Comparator<T>() {
            @Override
            public int compare(T task1, T task2) {
                return task1.getDateTime().toLocalTime().compareTo(task2.getDateTime().toLocalTime());
            }
        });

        tasks.forEach((k, v) -> {
            if (v.containInDay(day)) {
                dayTasks.add(v);
            }
        });

        return dayTasks;
    }

    protected void printDayTasks(LocalDate day, Set<T> dayTasks){
        StringBuilder builder = new StringBuilder();
        if (dayTasks.size() == 0){
            System.out.println("На дату " + day + " нет ни одной задачи");
        }else {
            System.out.println("На дату " + day + " следующий список задач: ");
            for (T task : dayTasks) {
                builder.append("ID: \"").append(task.getId()).append("\" Название: \"").append(task.getHeader()).append("\" Описание: \"").append(task.getDescription()).append("\" \n");
                builder.append(" Дата и время: ").append(task.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))).append("\n");
                builder.append(" Тип задачи: ").append(task.getTaskType().getTypeName()).append("\n");
                builder.append(" Тип повторяемости: ").append(task.getRepeatabilityType().getName()).append("\n\n");
            }
            System.out.println(builder.toString());
        }
    }

    protected void delTask(int taskId) throws TaskNotFoundException {
        if (!tasks.containsKey(taskId)){
            throw new TaskNotFoundException("Задачи с указанным ID нет.");
        }
        tasks.remove(taskId);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<Integer, T> entry : tasks.entrySet()) {
            builder.append("ID: \"").append(entry.getKey()).append("\" Название: \"").append(entry.getValue().getHeader()).append("\" Описание: \"").append(entry.getValue().getDescription()).append("\" \n");
            builder.append(" Дата и время: ").append(entry.getValue().getDateTime().format(DateTimeFormatter.ofPattern(Task.DATE_TYPE_FORMAT))).append("\n");
            builder.append(" Тип задачи: ").append(entry.getValue().getTaskType().getTypeName()).append("\n");
            builder.append(" Тип повторяемости: ").append(entry.getValue().getRepeatabilityType().getName()).append("\n\n");
        }
        return builder.toString();
    }

    protected Map<Integer, T> getTasks() {
        return tasks;
    }
}
