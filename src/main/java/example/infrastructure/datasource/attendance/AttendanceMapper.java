package example.infrastructure.datasource.attendance;

import example.domain.model.attendance.TimeRecord;
import example.domain.model.user.UserIdentifier;
import example.domain.type.date.Date;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AttendanceMapper {
    long newWorkTimeIdentifier();
    void registerWorkTime(@Param("id") Long id, @Param("userId") UserIdentifier userId,
                          @Param("workDay") Date workDay, @Param("workTime")TimeRecord workTime);
    void registerWorkTimeMapper(@Param("workTimeId") Long workTimeId,
                                @Param("userId") UserIdentifier userId, @Param("workDay") Date workDay);
    void deleteWorkTimeMapper(@Param("userId") UserIdentifier userId, @Param("workDay") Date workDay);

    TimeRecord findBy(@Param("userId") UserIdentifier userId, @Param("workDay") Date workDay);
}
