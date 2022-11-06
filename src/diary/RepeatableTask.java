package diary;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RepeatableTask extends Task implements DiaryTask {

    public RepeatableTask(String header, String description, RepeatabilityType repeatabilityType) {
        super(header, description, repeatabilityType);
    }

    public RepeatableTask(String header, String description, String DateTime, String taskType, String repeatabilityType) {
        super(header, description, DateTime, taskType, repeatabilityType);
    }

    @Override
    public boolean containInDay(LocalDate day) {
        if (getDateTime().toLocalDate().compareTo(day) > 0){
            return false;
        }

        switch (getRepeatabilityType()){
            case DAILY:
                return true;
            case WEEKLY:
                return getDateTime().getDayOfWeek().equals(day.getDayOfWeek()) ? true : false;
            case MONTHLY:
                return getDateTime().getDayOfMonth() == day.getDayOfMonth() ? true : false;
            case ANNUALY:
                return getDateTime().getDayOfMonth() == day.getDayOfMonth() &&
                        getDateTime().getMonth().equals(day.getMonth()) ? true : false;
            default:
                return false;
        }
    }

}
