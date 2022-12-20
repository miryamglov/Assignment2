import java.util.ArrayList;

public class WehicleWasher {
    // Vehicles waiting to be washed
    private ArrayList<Wehicle> queue = new ArrayList<Wehicle>();

    // Washed vehicles by type (for statistics)
    private ArrayList<Truck> washedTrucks = new ArrayList<Truck>();
    private ArrayList<MiniBus> washedMiniBuses = new ArrayList<MiniBus>();
    private ArrayList<SUV> washedSUVs = new ArrayList<SUV>();
    private ArrayList<Car> washedCars = new ArrayList<Car>();

    // Washing stations
    private Wehicle[] washingStations;

    public WehicleWasher(
            int numWashingStations) {
        washingStations = new Wehicle[numWashingStations];
    }

    public void addToQueue(Wehicle wehicle) {
        // Only one thread can add to the queue at a time
        synchronized (this) {
            queue.add(wehicle);
        }

        // Wait for a washing station to be free
        waitForWashingStation();
    }

    public void waitForWashingStation() {
        while (true) {
            for (int i = 0; i < washingStations.length; i++) {
                // If a washing station is free, wash the next vehicle
                if (washingStations[i] == null) {
                    washNext(i);
                    return;
                }
            }
            try {
                synchronized (this) {
                    // If no washing station is free, wait for a notification
                    wait();
                }
            } catch (InterruptedException e) {
            }
        }
    }

    // Only one thread can remove from the queue at a time
    public synchronized Wehicle removeFromQueue() {
        return queue.remove(0);
    }

    public void washNext(int station) {
        // If the queue is empty, return
        if (queue.size() == 0) {
            return;
        }

        // Remove the next vehicle from the queue and wash it
        Wehicle wehicle = removeFromQueue();

        // Add the vehicle to the washing station
        washingStations[station] = wehicle;

        // Wash the vehicle
        wehicle.wash();

        // Free the washing station
        washingStations[station] = null;

        // Add the vehicle to the correct list
        if (wehicle instanceof Truck) {
            washedTrucks.add((Truck) wehicle);
        } else if (wehicle instanceof MiniBus) {
            washedMiniBuses.add((MiniBus) wehicle);
        } else if (wehicle instanceof SUV) {
            washedSUVs.add((SUV) wehicle);
        } else if (wehicle instanceof Car) {
            washedCars.add((Car) wehicle);
        }

        synchronized (this) {
            // Notify all threads that a washing station is free
            notifyAll();
        }
    }

    public void printAverageForEachVehicleType() {
        // Print the average washing time for each vehicle type
        System.out.println("Average time for washing a truck: " + averageTimeFor(washedTrucks) + " seconds");
        System.out.println("Average time for washing a minibus: " + averageTimeFor(washedMiniBuses) + " seconds");
        System.out.println("Average time for washing a SUV: " + averageTimeFor(washedSUVs) + " seconds");
        System.out.println("Average time for washing a car: " + averageTimeFor(washedCars) + " seconds");
    }

    // Calculate the average washing time for a list of vehicles
    private <T extends Wehicle> double averageTimeFor(ArrayList<T> list) {
        double sum = 0;
        for (T t : list) {
            sum += ((Wehicle) t).getTimeToWash();
        }
        return sum / list.size() / 1000;
    }
}
