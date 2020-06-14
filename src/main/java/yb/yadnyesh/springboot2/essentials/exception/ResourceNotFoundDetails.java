package yb.yadnyesh.springboot2.essentials.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResourceNotFoundDetails {
    private String title;
    private int status;
    private String details;
    private LocalDateTime timeStamp;
    private String developerMessage;
}
