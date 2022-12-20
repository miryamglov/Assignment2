import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Get user input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of stations: ");
        int numberOfVehicles = scanner.nextInt();

        System.out.print("Enter number of vehicles: ");
        int numberOfWashingStations = scanner.nextInt();

        System.out.print("Average time for washing a vehicle: ");
        double averageTimeForWashingAVehicle = scanner.nextDouble();

        System.out.print("Average time for a vehicle to arrive: ");
        double averageTimeForAVehicleToArrive = scanner.nextDouble();

        // Create the washer
        WehicleWasher washer = new WehicleWasher(numberOfVehicles);

        // Create a thread array so we can wait for all threads to finish
        Thread[] threads = new Thread[numberOfWashingStations];

        for (int i = 0; i < numberOfWashingStations; i++) {
            // Create a random vehicle
            Wehicle wehicle = randomVehicle();

            // Set the washer and time to wash
            wehicle.setWasher(washer);
            wehicle.setTimeToWash(poisson(averageTimeForWashingAVehicle) * 1000);

            // Create a thread and start it
            Thread thread = new Thread(wehicle);
            threads[i] = thread;
            thread.start();

            // Wait for the next vehicle to arrive
            try {
                Thread.sleep(poisson(averageTimeForAVehicleToArrive) * 1000);
            } catch (InterruptedException e) {
            }
        }

        // Wait for all threads to finish
        for (int i = 0; i < numberOfWashingStations; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
            }
        }

        // Print the statistics
        washer.printAverageForEachVehicleType();

        // Close the scanner
        scanner.close();
    }

    public static Wehicle randomVehicle() {
        int random = (int) (Math.random() * 4);
        switch (random) {
            case 0:
                return new Car();
            case 1:
                return new SUV();
            case 2:
                return new MiniBus();
            case 3:
                return new Truck();
            default:
                // This should never happen
                return new Car();
        }
    }

    private static int poisson(double mean) {
        int r = 0;
        double a = Math.random();
        double p = Math.exp(-mean);

        while (a > p) {
            r++;
            a = a - p;
            p = p * mean / r;
        }
        return r;
    }
}
