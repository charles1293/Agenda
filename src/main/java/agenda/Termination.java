package agenda;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

public class Termination {

    private LocalDate myStart;
    private ChronoUnit myFrequency;
    private LocalDate myTerminationInclusive;
    private long myNumberOfOccurrences;

    public LocalDate terminationDateInclusive() {
        return myTerminationInclusive;
    }

    public long numberOfOccurrences() {
        return myNumberOfOccurrences;
    }


    /**
     * Constructs a  termination at a given date
     * @param start the start time of this event
     * @param frequency one of :
     * <UL>
     * <LI>ChronoUnit.DAYS for daily repetitions</LI>
     * <LI>ChronoUnit.WEEKS for weekly repetitions</LI>
     * <LI>ChronoUnit.MONTHS for monthly repetitions</LI>
     * </UL>
     * @param terminationInclusive the date when this event ends
     * @see ChronoUnit#between(Temporal, Temporal)
     */
    public Termination(LocalDate start, ChronoUnit frequency, LocalDate terminationInclusive) {
        this.myStart = start;
        this.myFrequency = frequency;
        this.myTerminationInclusive = terminationInclusive;
        this.myNumberOfOccurrences = frequency.between(start, terminationInclusive) + 1;
    }

    /**
     * Constructs a fixed termination event ending after a number of iterations
     * @param start the start time of this event
     * @param frequency one of :
     * <UL>
     * <LI>ChronoUnit.DAYS for daily repetitions</LI>
     * <LI>ChronoUnit.WEEKS for weekly repetitions</LI>
     * <LI>ChronoUnit.MONTHS for monthly repetitions</LI>
     * </UL>
     * @param numberOfOccurrences the number of occurrences of this repetitive event
     */
    public Termination(LocalDate start, ChronoUnit frequency, long numberOfOccurrences) {
        this.myStart = start;
        this.myFrequency = frequency;
        this.myNumberOfOccurrences = numberOfOccurrences;
        this.myTerminationInclusive = start.plus(numberOfOccurrences - 1, frequency);
    }

}
