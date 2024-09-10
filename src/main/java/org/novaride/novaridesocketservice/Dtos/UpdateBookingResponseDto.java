package org.novaride.novaridesocketservice.Dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.novaride.modelentity.models.BookingStatus;
import org.novaride.modelentity.models.Driver;


import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateBookingResponseDto {
    private Long bookingId;
    private BookingStatus status;
    private Optional<Driver> driver;
}

