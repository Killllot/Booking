module Domain {
    exports domain.Controllers;
    exports domain.Exeption;
    exports domain.Service;
    requires Data;
    requires lombok;
    requires spring.beans;
    requires spring.context;
    requires spring.web;
    requires Serilogj;
    requires java.validation;
}