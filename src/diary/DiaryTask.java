package diary;

import java.time.LocalDate;

public interface DiaryTask {
    public boolean containInDay(LocalDate day);
}
