package diary;

import java.time.LocalDate;

public class UnRepeatableTask extends Task implements DiaryTask{
    public UnRepeatableTask(String header, String description) {
        super(header, description, RepeatabilityType.ONEW_TYPE);
    }

    public UnRepeatableTask(String header, String description, String DateTime, String taskType) {
        super(header, description, DateTime, taskType, "однократно");
    }

    @Override
    public boolean containInDay(LocalDate day) {
        return (getDateTime().toLocalDate().equals(day)) ? true : false;
    }
}
