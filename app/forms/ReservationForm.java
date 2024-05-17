package forms;

import models.ReservationRequests;
import models.finder.ReservationFinder;
import models.view.ReservationsViewAdapter;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Constraints.Validate
public class ReservationForm implements Constraints.Validatable<List<ValidationError>> {


    public String date;
    public String timeFrom;
    public String timeTo;
    public Integer boatID;
    public LocalTime timeFromLocalTime;
    public LocalTime timeToLocalTime;
    public LocalDate dateLocalDate;
    public static final int TIME_INTERVAL = 15;
    public static final int TIME_BETWEEN_RESERVATIONS_REQUIREMENTS = 30;
    public static final int TIME_BETWEEN_RESERVATIONS_REQUIREMENTS_2 = TIME_BETWEEN_RESERVATIONS_REQUIREMENTS * -1;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    LocalDate now = LocalDate.now();

    @Override
    public List<ValidationError> validate() {

        ReservationFinder reservationFinder = new ReservationFinder();
        List<ReservationsViewAdapter> reservationsViewAdapterList = new ArrayList<>();
        List<ReservationRequests> reservationRequests = reservationFinder.findReservations();
        List<ValidationError> validationErrors = new ArrayList<>();
        for (int i = 0; i < reservationRequests.size(); i++){
            ReservationsViewAdapter r = new ReservationsViewAdapter(reservationRequests.get(i));
            reservationsViewAdapterList.add(r);
        }
        timeFromLocalTime = setTimeFromString(timeFrom);
        timeToLocalTime = setTimeFromString(timeTo);
        if  (Objects.equals(date, "")) {
            validationErrors.add(new ValidationError("date", "You need to enter a date"));
        } else {
            dateLocalDate = setDateFromString(date);
            if (dateLocalDate.isBefore(now)) {
                validationErrors.add(new ValidationError("date", "The date is before the current date"));
            }
        }


        if (timeFromLocalTime == null) {
            validationErrors.add(new ValidationError("timeFrom", "You need to enter the time"));
        } else {
            if (timeFromLocalTime.getMinute() % TIME_INTERVAL != 0) {
                validationErrors.add(new ValidationError("timeFrom", "You need to put the minutes in 15 interval"));
            }
            if (timeFromLocalTime.isBefore(LocalTime.parse("09:00:00"))) {
                validationErrors.add(new ValidationError("timeFrom", "The time is before opening time")) ;
            }
        }



        if (timeToLocalTime == null) {
            validationErrors.add(new ValidationError("timeTo", "You need to enter the time"));
        } else {
            if (timeToLocalTime.getMinute() % TIME_INTERVAL != 0) {
                validationErrors.add(new ValidationError("timeTo", "You need to put the minutes in 15 interval"));
            }
            if (timeToLocalTime.isAfter(LocalTime.parse("19:00:00"))){
                validationErrors.add(new ValidationError("timeTo", "The time is after closing time."));
            }
        }


        if (timeToLocalTime != null && timeFromLocalTime != null){
          if (timeFromLocalTime.isAfter(timeToLocalTime) || timeFromLocalTime.equals(timeToLocalTime)) {
                validationErrors.add(new ValidationError("timeFrom", "Time from can't be after Time to"));
          }
        } else {
            validationErrors.add(new ValidationError("timeFrom", "Enter both times"));
        }
        if (boatID == null){
            validationErrors.add(new ValidationError("boatID", "You must select one of the options"));
        } else {
            if (reservationsViewAdapterList.isEmpty()){
                System.out.println("hello");
            } else {
                for (int i = 0; reservationsViewAdapterList.size() > i; i++) {
                    if (reservationsViewAdapterList.get(i).timeFrom.equals(this.timeFromLocalTime)
                            && reservationsViewAdapterList.get(i).boatID == this.boatID
                            && this.dateLocalDate.isEqual(reservationsViewAdapterList.get(i).reservationDate)) {

                        validationErrors.add(new ValidationError("boatID", "The boat is already reserved for this time"));
                        break;
                    } else {
                        if ((!Objects.equals(timeFrom, "") && !Objects.equals(timeTo, ""))) {
                            LocalTime timeFromConvert = setTimeFromString(timeFrom);
                            LocalTime timeToConvert = setTimeToString(timeTo);

                            long timeBetweenTimeFromToTimeTo = ChronoUnit.MINUTES.between( reservationsViewAdapterList.get(i).timeTo, timeFromConvert);
                            long timeBetweenTimeToToTimeFrom = ChronoUnit.MINUTES.between( reservationsViewAdapterList.get(i).timeFrom, timeToConvert);
                            long timeBetweenTimeFromToTimeFrom = ChronoUnit.MINUTES.between( reservationsViewAdapterList.get(i).timeFrom, timeFromConvert);
                            long timeBetweenTimeToToTimeTo = ChronoUnit.MINUTES.between( reservationsViewAdapterList.get(i).timeTo, timeToConvert);


                            if (timeBetweenTimeFromToTimeFrom < 0) {
                                if(ifReservationsAreOnTheSameDateAndReservedTheSameBoat(reservationsViewAdapterList.get(i).boatID, this.boatID, dateLocalDate, reservationsViewAdapterList.get(i).reservationDate)) {
                                    if ((timeBetweenTimeToToTimeFrom <= TIME_BETWEEN_RESERVATIONS_REQUIREMENTS_2)) {
                                        System.out.println("hello");
                                    } else {
                                        validationErrors.add(new ValidationError("boatID", "The boat is already reserved, your reservations must be set at least 30 minutes after the last one"));
                                        break;
                                    }
                                }
                            } else {
                                if (ifReservationsAreOnTheSameDateAndReservedTheSameBoat(reservationsViewAdapterList.get(i).boatID, this.boatID, dateLocalDate, reservationsViewAdapterList.get(i).reservationDate)){
                                    if ((timeBetweenTimeFromToTimeTo >= TIME_BETWEEN_RESERVATIONS_REQUIREMENTS)
                                        ) {
                                        System.out.println("hello");
                                    } else {
                                        validationErrors.add(new ValidationError("boatID", "The boat is already reserved, your reservations must be set at least 30 minutes after the last one"));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
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
    public LocalTime setTimeToString(String time) {
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

    public Boolean ifReservationsAreOnTheSameDateAndReservedTheSameBoat(int boatID1, int boatID2, LocalDate reservationDate1, LocalDate reservationDate2) {
        return (boatID1 == boatID2) && (reservationDate1.isEqual(reservationDate2));
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

    public Integer getBoatID() {
        return boatID;
    }

    public void setBoatID(Integer boatID) {
        this.boatID = boatID;
    }


}
