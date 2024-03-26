package com.example.initial;
import com.example.entities.Configuration;
import com.example.entities.Status;
import com.example.repositories.ConfigurationRepository;
import com.example.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Service
@Transactional
public class InitialLoad {

    private static final Logger LOGGER = LogManager.getLogger(InitialLoad.class);

    final UserRepository userRepository;

    final ConfigurationRepository configurationRepository;

    public InitialLoad(UserRepository userRepository, ConfigurationRepository configurationRepository) {
        this.userRepository = userRepository;
        this.configurationRepository = configurationRepository;
    }


    public void load() {

        if (!configurationRepository.findAll().isEmpty() && configurationRepository.findAll().get(0).getStatus().equals(Status.COMPLETED)) {
            LOGGER.info("Data has been loaded");
            return;
        }

        LOGGER.info("Initial Load started");

        BufferedReader bufferedReader;
        String line;
        try {
            ClassLoader classLoader = InitialLoad.class.getClassLoader();
            bufferedReader = new BufferedReader(new FileReader(classLoader.getResource("books-lite.csv").getFile()));
            Configuration configuration = new Configuration();
            configuration.setStatus(Status.COMPLETED);
            configurationRepository.save(configuration);
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split("\\t");
                if (s[0].equalsIgnoreCase("isbn10")) continue;
            }
            LOGGER.info("Initial Load finished");
        } catch (FileNotFoundException e) {
            LOGGER.error("Error in input file", e);
        } catch (IOException e) {
            LOGGER.error("Exception occurred", e);
        }
    }

}
