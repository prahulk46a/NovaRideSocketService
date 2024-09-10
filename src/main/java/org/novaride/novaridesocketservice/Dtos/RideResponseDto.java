package org.novaride.novaridesocketservice.Dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RideResponseDto {
    public Boolean response;
    public Long bookingId;
}
