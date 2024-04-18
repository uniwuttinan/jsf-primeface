package my.example.logger;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class MyLogger extends Logger implements Serializable {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    protected MyLogger(String name, String resourceBundleName) {
        super(name, resourceBundleName);
    }

    // Factory method to create a MyLogger instance
    public static MyLogger getLogger(String name) {
        MyLogger logger = new MyLogger(name, null);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new MyLogFormatter());
        logger.addHandler(consoleHandler);
        return logger;
    }

    // Custom log formatter to format log messages
    private static class MyLogFormatter extends Formatter {
        @Override
        public String format(LogRecord record) {
            return "[MyLogger] " + record.getLevel() + " " + dateFormat.format(new Date()) + "  " + record.getMessage() + "\n";
        }
    }
}