package server.socket;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Protocol {

    private Integer messageSize;
    private String methodType;
    private String message;
    private String status;

}
