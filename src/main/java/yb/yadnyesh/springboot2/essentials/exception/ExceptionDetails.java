package yb.yadnyesh.springboot2.essentials.exception;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExceptionDetails {
        private String title;
        private int status;
        private String details;
        private LocalDateTime timeStamp;
        private String developerMessage;
}
