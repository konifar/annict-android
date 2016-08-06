package com.konifar.annict.model;

public enum Season {

    SPRING,
    SUMMER,
    AUTUMN,
    WINTER,
    ALL;

    public String getName(int year) {
        return year + "-" + toString();
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
