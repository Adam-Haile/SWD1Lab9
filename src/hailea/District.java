/*
 * Course: CS1011 - Saikath Bhattacharya
 * Fall 2022
 * Lab 9 - Parking Lots
 * Name: Adam Haile
 * Created: 11/02/22
 */

package hailea;

/**
 * Manages parking lots within a district.
 * @author Adam Haile
 */
public class District {

    ParkingLot[] lots;
    int numLots = 0;
    static final int MAX_LOTS = 20;
    int timeAllClosed = 0;
    int timeReopened = 0;

    /**
     * Set up a district with a set amount of parking lots in an array.
     */
    public District() {
        lots = new ParkingLot[MAX_LOTS];
    }

    /**
     * Adds a new ParkingLot to the District
     * @param name - String, the name of the Parking Lot
     * @param capacity - Integer, the capacity of the Parking Lot
     * @return The index of the Parking Lot in the District array
     */

    public int addLot(String name, int capacity) {
        int newIndex = numLots;
        if(newIndex<MAX_LOTS) {
            lots[newIndex] = new ParkingLot(name, capacity);
            numLots++;
        }
        // return the index of the new lot or -1 if the lot was not added.
        return newIndex<MAX_LOTS ? newIndex : -1;
    }

    /**
     * Gets the parking lot of the specified index within the district array
     * @param index - The index of the searched for parking lot
     * @return The Parking Lot at the specified index with in the District array
     */

    public ParkingLot getLot(int index) {
        for(ParkingLot lot : lots) {
            if(lots[index] == lot) {
                return lot;
            }
        }
        return null;
    }

    /**
     * Display status information for all three lots.
     * @see ParkingLot#toString() for the format for each.
     */
    public String toString() {
        StringBuilder tot = new StringBuilder();
        tot.append("District status:\n");
        for(ParkingLot lot : lots) {
            if(lot != null) {
                tot.append("  ").append(lot).append("\n");
            }
        }
        return tot.toString();
    }

    /**
     * Returns the number of remaining parking spots in the district
     * @return the number of remaining parking spots in the district
     */
    public int getNumberOfSpotsRemaining() {
        int collective = 0;
        for (ParkingLot lot : lots) {
            if(lot != null) {
                collective += lot.getNumberOfSpotsRemaining();
            }
        }
        return collective;
    }

    /**
     * Returns the amount of time all three lots have been
     * simultaneously closed.
     * @return number of minutes all three lots have been closed
     */
    public int getMinutesClosed() {
        return timeReopened - timeAllClosed;
    }

    /**
     * Checks the status of all three lots in the district and
     * returns true if they are all closed and false otherwise.
     * @return whether all three lots in the district are closed
     */
    public boolean isClosed() {
        boolean closeCheck = false;
        int lotTotalClosed = 0;
        int lotsChecked = 0;
        for (ParkingLot lot : lots) {
            if(lot != null) {
                if (lot.isClosed()) {
                    lotTotalClosed++;
                }
                lotsChecked++;
            }
        }

        if(lotTotalClosed == lotsChecked) {
            closeCheck = true;
        }

        return closeCheck;
    }

    /**
     * Record a vehicle entering a lot at a specified timestamp.
     * <p></p>
     * This calls ParkingLot.markVehicleEntry for the lot corresponding
     * to lotNumber (e.g., if lotNumber==1, call markVehicleEntry on lot1).
     * <p></p>
     * If lotNumber is out of range, the method should return without
     * doing anything else.
     * @param lotNumber Number of lot (should be within the District's array)
     * @param timestamp Entry timestamp in minutes since all lots were opened.
     */
    public void markVehicleEntry(int lotNumber, int timestamp) {
        lots[lotNumber].markVehicleEntry(timestamp);
        if(isClosed()) {
            timeAllClosed = timestamp;
        }
    }

    /**
     * Record a vehicle exiting a lot at a specified timestamp.
     * <p></p>
     * This calls ParkingLot.markVehicleExit for the lot corresponding
     * to lotNumber (e.g., if lotNumber==1, call markVehicleExit on lot1).
     * <p></p>
     * If lotNumber is out of range, the method should return without
     * doing anything else.
     * @param lotNumber Number of lot (should be within the District's array)
     * @param timestamp Exit timestamp in minutes since all lots were opened.
     */
    public void markVehicleExit(int lotNumber, int timestamp) {
        boolean closed = isClosed();
        lots[lotNumber].markVehicleExit(timestamp);
        if (closed) {
            if (!isClosed()) {
                timeReopened = timestamp;
            }
        }
    }
}
