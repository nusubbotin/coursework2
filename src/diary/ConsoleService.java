package diary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Scanner;

public class ConsoleService  {
    private Diary diary;

    public ConsoleService() {
        diary = new Diary();
    }

    public void startMenu(String[] args) {
        try (Scanner scanner = new Scanner(System.in).useDelimiter("\n")) {
            label:
            while (true) {
                printMenu();
                System.out.print("Выберите пункт меню: ");
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            inputTask(scanner);
                            break;
                        case 2:
                            deleteTask(scanner);
                            break;
                        case 3:
                            printDayTasks(scanner);
                            break;
                        case 4:
                            printDiary();
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println("Выберите пункт меню из списка!");
                }
            }
        }
    }

    private void printDayTasks(Scanner scanner) {
        String dateStr;
        LocalDate date = null;
        do{
            System.out.println("Введите интересующую дату в формате " + Task.DATE_FORMAT);
            dateStr =  scanner.next();
            try {
                date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(Task.DATE_FORMAT));
            } catch (DateTimeParseException e) {
                System.out.println("Некорректный формат даты.");
            }

        }while (date == null);

        System.out.printf("Список заданий на день %s:%n", date);
        diary.printDayTasks(date, diary.getDayTasks(date));
        System.out.println();
    }

    private void printDiary() {
        if(diary.getTasks().size() == 0) {
            System.out.print("Список задач пуст! \n");
        }else
        {
            System.out.print("Список всех задач: \n");
            System.out.println(diary);
        }
        System.out.println();
    }

    private void deleteTask(Scanner scanner) {
        Integer taskId = null;
        System.out.print("Введите ID задания: \n");
        do{
            try {
                if (scanner.hasNextInt()){
                    taskId = scanner.nextInt();
                    diary.delTask(taskId);
                    System.out.println("Задание удалено!");
                }else {
                    scanner.next();
                    System.out.println("ID должно быть целым числом");
                }
            } catch (TaskNotFoundException e){
                System.out.println(e.getMessage());
                return;
            } catch (Exception e){
                System.out.println("Вы ошиблись при вводе!");
            }
        }while(taskId == null);
        System.out.println();
    }

    private void inputTask(Scanner scanner) {
        String header;
        String description;
        String repeatabilityTypeStr;
        Task.RepeatabilityType repeatabilityType = null;

        String taskTypeStr;
        String dateTimeStr;

        UnRepeatableTask unRTask;
        RepeatableTask rTask;
        Task task = null;

        System.out.print("Введите название задачи: ");
        header =  scanner.next();

        System.out.print("Введите описание задачи: ");
        description =  scanner.next();

        do {
            System.out.print("Введите тип Повторяемости: \n");
            System.out.println(Arrays.toString(Arrays.stream(Task.RepeatabilityType.values()).toArray()));
            repeatabilityTypeStr =  scanner.next();
            try {
                repeatabilityType = Task.findRepeatabilityType(repeatabilityTypeStr);
            } catch (IllegalArgumentException e){
                System.out.println("Вы ошиблись при вводе! " + e.getMessage());
            }
        }while (repeatabilityType == null);

        if (repeatabilityType == Task.RepeatabilityType.ONEW_TYPE) {
            unRTask = new UnRepeatableTask(header, description);
            task = unRTask;
        }else {
            rTask = new RepeatableTask(header, description, repeatabilityType);
            task = rTask;
        }

        do{
            System.out.print("Введите тип задачи: \"личные\" или \"рабочие задачи\"\n");
            taskTypeStr =  scanner.next();
            try {
                task.setTaskType(taskTypeStr);
            } catch (Exception e){
                System.out.println("Вы ошиблись при вводе! " + e.getMessage());
            }
        }while (task.getTaskType() == null);

        do{
            System.out.print("Введите дату и время в формате: " + Task.DATE_TYPE_FORMAT + "\n");
            dateTimeStr =  scanner.next();
            try {
                task.setDateTime(dateTimeStr);
            } catch (Exception e){
                System.out.println("Вы ошиблись при вводе! " + e.getMessage());
            }
        }while (task.getDateTime() == null);

        diary.getTasks().put(task.getId(), task);
        System.out.println("Задание успешно создано!");
    }

    private void printMenu() {
        System.out.println(
                   "     1. Добавить задачу \n"+
                   "     2. Удалить задачу \n"+
                   "     3. Получить задачу на указанный день \n"+
                   "     4. Вывести список всех задач \n"+
                   "     0. Выход \n"
        );
    }
}
