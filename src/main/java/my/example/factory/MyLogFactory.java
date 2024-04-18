package my.example.factory;

import my.example.logger.MyLogger;

import javax.enterprise.inject.Produces;

public class MyLogFactory {
    @Produces
    public MyLogger getMyLogger() {
        return MyLogger.getLogger("MY_LOGGER");
    }
}
