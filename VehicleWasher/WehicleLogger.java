import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public final class WehicleLogger {
    private final long now = new Date().getTime();
    private final File file = new File("log.txt");
    private FileOutputStream fos;

    public static WehicleLogger instance = new WehicleLogger();

    public WehicleLogger() {
        try {
            // Create the log file
            file.createNewFile();
        } catch (Exception e) {
            System.out.println("Could not create log file");
        }
    }

    public synchronized void log(String message) {
        // Get the current thread
        Thread thread = Thread.currentThread();
        // Get the time passed since the start of the program
        int timePassed = (int) ((new Date().getTime() - now) / 1000);
        // Format the message
        message = String.format("[Vehicle ID: %s] Time: %s | %s", thread.getId(), timePassed, message);
        System.out.println(message);
        try {
            // Write the message to the log file
            fos = new FileOutputStream(file, true);
            fos.write(message.getBytes());
            fos.write("\n".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the file
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
