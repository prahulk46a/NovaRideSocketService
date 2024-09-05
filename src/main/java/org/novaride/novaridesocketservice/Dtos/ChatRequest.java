package org.novaride.novaridesocketservice.Dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRequest {
    private String name;
    private String message;
}
