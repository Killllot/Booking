package App;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import serilogj.Log;
import serilogj.LoggerConfiguration;
import serilogj.core.enrichers.LogContextEnricher;
import serilogj.events.LogEventLevel;

import static serilogj.sinks.coloredconsole.ColoredConsoleSinkConfigurator.coloredConsole;
import static serilogj.sinks.rollingfile.RollingFileSinkConfigurator.rollingFile;
import static serilogj.sinks.seq.SeqSinkConfigurator.seq;

@Configuration
@SpringBootApplication
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
