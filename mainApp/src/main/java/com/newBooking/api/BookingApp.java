package com.newBooking.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import serilogj.Log;
import serilogj.LoggerConfiguration;
import serilogj.core.enrichers.LogContextEnricher;
import serilogj.events.LogEventLevel;

import static serilogj.sinks.coloredconsole.ColoredConsoleSinkConfigurator.coloredConsole;
import static serilogj.sinks.rollingfile.RollingFileSinkConfigurator.rollingFile;
import static serilogj.sinks.seq.SeqSinkConfigurator.seq;


@EnableJpaRepositories("com.newBooking")
@EntityScan("com.newBooking.domain")
@ComponentScan("com.newBooking")
@PropertySource("classpath:mySecretProperties.properties")
@SpringBootApplication(scanBasePackages = "com.newBooking")
public class BookingApp {
    public static void main(String[] args) {
        Log.setLogger(new LoggerConfiguration()
                .setMinimumLevel(LogEventLevel.Verbose)
                .with(new LogContextEnricher())
                .writeTo(coloredConsole("[{Timestamp} {Level}] {Message} ({Operation}){NewLine}{Exception}"))
                .writeTo(rollingFile("test-{Date}.log"))
                .writeTo(seq("http://localhost:5341/"))
                .createLogger());

        SpringApplication.run(BookingApp.class, args);
    }
}
