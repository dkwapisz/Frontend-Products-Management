package org.pk.lab3.utils;

public record UpdatePair<T>(T valueBeforeUpdate, T valueAfterUpdate) {
    public static <T> UpdatePair<T> of(T valueBeforeUpdate, T valueAfterUpdate) {
        return new UpdatePair<>(valueBeforeUpdate, valueAfterUpdate);
    }
}

