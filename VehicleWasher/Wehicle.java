public abstract class Wehicle implements Runnable {
    private WehicleWasher washer;
    private double timeToWash;

    public void run() {
        // If the washer is null, throw an exception
        if (washer == null) {
            throw new RuntimeException("Washer is null");
        }
        log("Adding to queue");

        // Add the vehicle to the queue
        washer.addToQueue(this);
    }

    public void wash() {
        log("Washing");
        try {
            // Sleep for the time it takes to wash the vehicle
            Thread.sleep((int) (timeToWash));
        } catch (InterruptedException e) {
        }
        log("Washed");
    }

    public void setWasher(WehicleWasher washer) {
        this.washer = washer;
    }

    public WehicleWasher getWasher() {
        return washer;
    }

    public double getTimeToWash() {
        return timeToWash;
    }

    public void setTimeToWash(double timeToWash) {
        this.timeToWash = timeToWash;
    }

    private void log(String message) {
        // Log the message with the vehicle type
        WehicleLogger.instance.log(message + " | Vehicle type: " + getClass().getSimpleName());
    }
}