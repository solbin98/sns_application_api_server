package com.practice.chatapiserver.domain.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
public class TimeData {
    @DateTimeFormat
    private LocalDateTime created_at;
    private LocalDateTime modified_at;

    public TimeData() {
        this.created_at = LocalDateTime.now();
        this.modified_at = LocalDateTime.now();
    }
}
