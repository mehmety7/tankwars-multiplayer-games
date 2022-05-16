package server.socket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Protocol {

    private Integer messageSize;
    private String methodType;
    private String message;
    private String status;

}
