package com.konifar.annict.model;

public enum Season {
  SPRING,
  SUMMER,
  AUTUMN,
  WINTER,
  ALL;

  @Override public String toString() {
    return super.toString().toLowerCase();
  }

  public String getName(int year) {
    return year + "-" + toString();
  }
}
