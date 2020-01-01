package com.changwu.questionnaire.exception;


public class CustomException extends RuntimeException {

  private final String message;
  private final Integer status;

  public CustomException(String message,Integer status) {
    this.message = message;
    this.status = status;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public Integer getStatus() {
    return status;
  }
}