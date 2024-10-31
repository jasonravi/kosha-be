package com.dreamx.kosha.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

/**
 * @author deepika_rajani
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum Priority {

    LOW(1),
    MEDIUM(2),
    HIGH(3);

    private int priority;

    public static Priority getByPriority(int priority) {
        return Arrays.stream(Priority.values())
                .filter(p -> p.getPriority() == priority)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No Priority with value " + priority));
    }
}
