package com.rustedbrain.study.course;


import com.rustedbrain.study.course.controller.repository.AdministratorRepository;
import com.rustedbrain.study.course.model.authorization.Administrator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner loadData(AdministratorRepository repository) {
        return (args) -> {
            // save a couple of customers
            repository.save(new Administrator("alexeymuhinout", "Bloodarkness1996", "alexeymuhinout@gmail.com"));
            repository.save(new Administrator("mogtarip", "mogtariperson1996", "mogtarmogtar@gmail.com"));
            repository.save(new Administrator("eleonoraKipa", "281093", "kiparoidzie@gmail.com"));
            repository.save(new Administrator("hardeathit", "hardeathit1992", "hardeathit@gmail.com"));

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Administrator customer : repository.findAll()) {
                log.info(customer.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            Administrator customer = repository.getOne(1L);
            log.info("Customer found with getOne(1L):");
            log.info("--------------------------------");
            log.info(customer.toString());
            log.info("");
        };
    }
}
