package org.novaride.novaridesocketservice.Dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatResponse {
    private String name;
    private String message;
    private String timeStamp;
}
