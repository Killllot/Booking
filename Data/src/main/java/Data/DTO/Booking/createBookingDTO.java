package Data.DTO.Booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class createBookingDTO {

    private Long id;
    private String comment;
    @NotNull(message = "Должно быть задано начало времени бронирования")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fromUtc;
    @NotNull(message = "Должен быть задан конец времени  бронирования")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime toUtc;
    @NotNull(message = "Должен быть задан бронирующий")
    private Long userId;
    @NotNull(message = "Должна быть бронируемая комната")
    private Long roomId;

}
