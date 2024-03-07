package forms;

import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Constraints.Validate
public class ReservationForm implements Constraints.Validatable<List<ValidationError>> {


    public String date;
    public String timeFrom;
    public String timeTo;

    public LocalTime timeFromLocalTime;
    public LocalTime timeToLocalTime;
    public LocalDate dateLocalDate;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate now = LocalDate.now();

    @Override
    public List<ValidationError> validate() {


        List<ValidationError> validationErrors = new ArrayList<>();

        if (date == null || Objects.equals(date, "")) {
            validationErrors.add(new ValidationError("date", "You need to enter a date"));
        } else {
            dateLocalDate = setDateFromString(date);
            if (dateLocalDate.isBefore(now)) {
                validationErrors.add(new ValidationError("date", "The date is before the current date"));
            }
        }

        timeFromLocalTime = setTimeFromString(timeFrom);
        if (timeFromLocalTime == null) {
            validationErrors.add(new ValidationError("timeFrom", "You need to enter the time"));
        } else if (timeFromLocalTime.getMinute() % 15 != 0) {
            validationErrors.add(new ValidationError("timeFrom", "You need to put the minutes in 15 interval"));
        }

        timeToLocalTime = setTimeFromString(timeTo);
        if (timeToLocalTime == null) {
            validationErrors.add(new ValidationError("timeTo", "You need to enter the time"));
        } else if (timeToLocalTime.getMinute() % 15 != 0) {
            validationErrors.add(new ValidationError("timeTo", "You need to put the minutes in 15 interval"));
        }

        if (timeToLocalTime != null && timeFromLocalTime != null){
            if (timeFromLocalTime.isAfter(timeToLocalTime) || timeFromLocalTime.equals(timeToLocalTime)) {
                validationErrors.add(new ValidationError("timeFrom", "Time from can't be after Time to"));
            }
        }

        return validationErrors;
    }


    public LocalTime setTimeFromString(String time) {
        if (time == null || time.equals("")) {
            return null;
        }
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            return LocalTime.parse(time, timeFormatter);
        } catch (Exception e) {
            return null;
        }
    }

    public LocalDate setDateFromString(String date){
        if (date == null || date.isEmpty()){
            return null;
        }

        try {
            return LocalDate.parse(date, dateFormatter);
        } catch (Exception e){
            return null;
        }
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }



    public LocalTime getTimeFromLocalTime() {
        return timeFromLocalTime;
    }

    public void setTimeFromLocalTime(LocalTime timeFromLocalTime) {
        this.timeFromLocalTime = timeFromLocalTime;
    }

    public LocalTime getTimeToLocalTime() {
        return timeToLocalTime;
    }

    public void setTimeToLocalTime(LocalTime timeToLocalTime) {
        this.timeToLocalTime = timeToLocalTime;
    }

    public LocalDate getDateLocalDate() {
        return dateLocalDate;
    }

    public void setDateLocalDate(LocalDate dateLocalDate) {
        this.dateLocalDate = dateLocalDate;
    }


}
