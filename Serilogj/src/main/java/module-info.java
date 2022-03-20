module Serilogj {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    exports serilogj;
    exports serilogj.core.enrichers;
    exports serilogj.core.filters;
    exports serilogj.core.pipeline;
    exports serilogj.core.sinks;
    exports serilogj.context;
    exports serilogj.sinks.coloredconsole;
    exports serilogj.sinks.seq;
    exports serilogj.sinks.periodicbatching;
    exports serilogj.sinks.rollingfile;
    exports serilogj.configuration;
    exports serilogj.debugging;
    exports serilogj.events;
    exports serilogj.formatting.display;
    exports serilogj.formatting.json;
    exports serilogj.parameters;
    exports serilogj.parsing;
    exports serilogj.policies;
    exports serilogj.reflection;

}