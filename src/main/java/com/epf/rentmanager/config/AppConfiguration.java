package com.epf.rentmanager.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
// packages dans lesquels chercher les beans
@ComponentScan({"com.epf.rentmanager.service", "com.epf.rentmanager.dao", "com.epf.rentmanager.persistence"})
public class AppConfiguration {
}
