package agenda;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class Event {

    /**
     * The myTitle of this event
     */
    private String myTitle;
    
    /**
     * The starting time of the event
     */
    private LocalDateTime myStart;

    /**
     * The durarion of the event 
     */
    private Duration myDuration;

    private Repetition myRepetition;


    /**
     * Constructs an event
     *
     * @param title the title of this event
     * @param start the start time of this event
     * @param duration the duration of this event
     */
    public Event(String title, LocalDateTime start, Duration duration) {
        this.myTitle = title;
        this.myStart = start;
        this.myDuration = duration;
    }

    public void setRepetition(ChronoUnit frequency) {
        this.myRepetition = new Repetition(frequency);
    }

    public void addException(LocalDate date) {
        if (myRepetition != null) {
            myRepetition.addException(date);
        }
    }

    public void setTermination(LocalDate terminationInclusive) {
        if (myRepetition != null) {
            Termination termination = new Termination(myStart.toLocalDate(), myRepetition.getFrequency(), terminationInclusive);
            myRepetition.setTermination(termination);
        }
    }

    public void setTermination(long numberOfOccurrences) {
        if (myRepetition != null) {
            Termination termination = new Termination(myStart.toLocalDate(), myRepetition.getFrequency(), numberOfOccurrences);
            myRepetition.setTermination(termination);
        }
    }

    public int getNumberOfOccurrences() {
        if (myRepetition != null && myRepetition.getTermination() != null) {
            return (int) myRepetition.getTermination().numberOfOccurrences();
        }
        return 0;
    }

    public LocalDate getTerminationDate() {
        if (myRepetition != null && myRepetition.getTermination() != null) {
            return myRepetition.getTermination().terminationDateInclusive();
        }
        return null;
    }

    /**
     * Tests if an event occurs on a given day
     *
     * @param aDay the day to test
     * @return true if the event occurs on that day, false otherwise
     */

    public boolean isInDay(LocalDate aDay) {
        
        if (myRepetition != null && myRepetition.isException(aDay)) {
            return false;
        }

        LocalDate startDate = myStart.toLocalDate();
        
        if (startDate.isAfter(aDay)) {
            return false;
        }

        if (myRepetition == null) {
            LocalDateTime endDateTime = myStart.plus(myDuration);
            return !endDateTime.toLocalDate().isBefore(aDay);
        }

        
        ChronoUnit frequency = myRepetition.getFrequency();
        long unitsBetween = frequency.between(startDate, aDay);
        
        
        if (unitsBetween < 0) {
            return false;
        }
        
        LocalDate expectedOccurrence = startDate.plus(unitsBetween, frequency);
        if (!expectedOccurrence.equals(aDay)) {
            LocalDateTime occurrenceStart = myStart.plus(unitsBetween, frequency);
            LocalDateTime occurrenceEnd = occurrenceStart.plus(myDuration);
            if (aDay.isBefore(occurrenceStart.toLocalDate()) || aDay.isAfter(occurrenceEnd.toLocalDate())) {
                return false;
            }
        }
        
        return true;
    }


    
   
    /**
     * @return the myTitle
     */
    public String getTitle() {
        return myTitle;
    }

    /**
     * @return the myStart
     */
    public LocalDateTime getStart() {
        return myStart;
    }


    /**
     * @return the myDuration
     */
    public Duration getDuration() {
        return myDuration;
    }

    @Override
    public String toString() {
        return "Event{title='%s', start=%s, duration=%s}".formatted(myTitle, myStart, myDuration);
    }
}
