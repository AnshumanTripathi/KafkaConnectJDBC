package com.kafkaconnect.source.models;

public class Person {

  private int id;
  private String firstName;

  public int getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public Person(int id, String firstName) {
    this.id = id;
    this.firstName = firstName;
  }

  @Override
  public String toString() {
    return id + " " + firstName;
  }
}
