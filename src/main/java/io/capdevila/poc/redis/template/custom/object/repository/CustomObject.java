package io.capdevila.poc.redis.template.custom.object.repository;

public class CustomObject {

  private String field1;

  private String field2;

  public CustomObject() {
  }

  public String getField1() {
    return field1;
  }

  public void setField1(String field1) {
    this.field1 = field1;
  }

  public String getField2() {
    return field2;
  }

  public void setField2(String field2) {
    this.field2 = field2;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("CustomObject{");
    sb.append("field1='").append(field1).append('\'');
    sb.append(", field2='").append(field2).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
