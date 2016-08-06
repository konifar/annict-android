package com.konifar.annict.model;

public enum Sort {
    ASC, DESC;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}