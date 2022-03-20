module Data {
    exports Data.mapper.Booking;
    exports Data.DTO.Booking;
    exports Data.DTO.Room;
    exports Data.DTO.User;
    exports Data.Entity;
    exports Data.Repository;
    requires lombok;
    requires com.fasterxml.jackson.annotation;
    requires java.validation;
    requires java.persistence;
    requires spring.data.commons;
}