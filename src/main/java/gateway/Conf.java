package gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Conf {

    private static final Logger logger = LoggerFactory.getLogger(Conf.class);

    private static final Map<String, URI> values = new HashMap<>();

    public static void loadConf() {
        Properties properties = new Properties();
        try (FileReader fr = new FileReader("conf");
             BufferedReader br = new BufferedReader(fr)) {
            properties.load(br);
            properties.forEach((key, value) -> {
                URI uri = URI.create(value.toString());
                int port = uri.getPort();
                if (port >= 0 && port <= 65535) {
                    values.put(key.toString(), uri);
                } else {
                    logger.error("--- Conf ---\n{}", port);
                }
            });
        } catch (IOException ignored) {
        }

        logger.info("--- Conf ---\n{}", values);

    }

    public static URI valueOf(String host) {
        return values.get(host);
    }
}