package yb.yadnyesh.springboot2.essentials.exception;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class ResourceNotFoundDetails extends ExceptionDetails {

}
