package agenda;

import java.time.LocalDate;
import java.util.*;

/**
 * Description : An agenda that stores events
 */
public class Agenda {
    
    private List<Event> myEvents = new ArrayList<>();
    
    /**
     * Adds an event to this agenda
     *
     * @param e the event to add
     */
    public void addEvent(Event e) {
        myEvents.add(e);
    }

    /**
     * Computes the events that occur on a given day
     *
     * @param day the day toi test
     * @return a list of events that occur on that day
     */
    public List<Event> eventsInDay(LocalDate day) {
        List<Event> result = new ArrayList<>();
        for (Event e : myEvents) {
            if (e.isInDay(day)) {
                result.add(e);
            }
        }
        return result;
    }
    
    /**
     * Trouver les événements de l'agenda en fonction de leur titre
     * @param title le titre à rechercher
     * @return les événements qui ont le même titre
     */
    public List<Event> findByTitle(String title) {
        List<Event> result = new ArrayList<>();
        for (Event e : myEvents) {
            if (e.getTitle().equals(title)) {
                result.add(e);
            }
        }
        return result;
    }
    
    /**
     * Déterminer s'il y a de la place dans l'agenda pour un événement (aucun autre événement au même moment)
     * @param e L'événement à tester (on se limitera aux événements sans répétition)
     * @return vrai s'il y a de la place dans l'agenda pour cet événement
     */
    public boolean isFreeFor(Event e) {
        LocalDate startDay = e.getStart().toLocalDate();
        LocalDate endDay = e.getStart().plus(e.getDuration()).toLocalDate();
        
        for (LocalDate day = startDay; !day.isAfter(endDay); day = day.plusDays(1)) {
            if (e.isInDay(day)) {
                for (Event existing : myEvents) {
                    if (existing.isInDay(day)) {
                        if (eventsOverlap(e, existing)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    
    private boolean eventsOverlap(Event e1, Event e2) {
        var start1 = e1.getStart();
        var end1 = e1.getStart().plus(e1.getDuration());
        var start2 = e2.getStart();
        var end2 = e2.getStart().plus(e2.getDuration());
        
        return start1.isBefore(end2) && start2.isBefore(end1);
    }
}
