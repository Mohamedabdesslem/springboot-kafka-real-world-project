package net.javaguides.springboot;


import net.javaguides.springboot.kafka.launcher.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaTemplateSpringBoot implements CommandLineRunner
{

    @Autowired
    private ExploreKTableLauncher exploreKTableLauncher;

    @Autowired
    private GreetingsLauncher greetingsLauncher;

    @Autowired
    private OrdersLauncher ordersLauncher;

    @Autowired
    private AgreggateLauncher agreggateLauncher;

    @Autowired
    private ExploreJoinsOperatorsLauncher exploreJoinsOperatorsLauncher;


    public static void main( String[] args )
    {

        SpringApplication.run(KafkaTemplateSpringBoot.class, args);
    }



    @Override
    public void run(String... args)  {

         //this.exploreKTableLauncher.exploreKTableLauncher();
        //this.agreggateLauncher.aggregateLauncher();
        this.exploreJoinsOperatorsLauncher.exploreKTableLauncher();

    }
}
