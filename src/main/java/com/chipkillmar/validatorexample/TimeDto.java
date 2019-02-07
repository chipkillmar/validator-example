package com.chipkillmar.validatorexample;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.Instant;

/**
 * A DTO with a start time that must in the past or present.
 */
@Data
@AllArgsConstructor
public class TimeDto {

    @NotNull
    @PastOrPresent
    private Instant startTime;
}
