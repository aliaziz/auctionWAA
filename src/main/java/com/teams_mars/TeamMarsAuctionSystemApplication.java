package com.teams_mars;


import com.teams_mars.seller_module.controller.ProductController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;

@SpringBootApplication
@ComponentScan({"com.teams_mars","com.teams_mars.seller_module.controller" })
public class TeamMarsAuctionSystemApplication {

    public static void main(String[] args) {
        // Below line will create "uploads" folder at startup if not created.
        new File(ProductController.uploadDirectory).mkdir();
        SpringApplication.run(TeamMarsAuctionSystemApplication.class, args);
    }

}
