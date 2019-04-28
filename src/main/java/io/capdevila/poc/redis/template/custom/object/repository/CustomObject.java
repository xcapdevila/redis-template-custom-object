package io.capdevila.poc.redis.template.custom.object.repository;

import java.util.Objects;

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

  public String toRawJsonOnlyFields() {
    final StringBuffer sb = new StringBuffer("{");
    sb.append("\"field1\":\"").append(field1).append("\"").append(",");
    sb.append("\"field2\":\"").append(field2).append("\"");
    sb.append('}');
    return sb.toString();
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("CustomObject{");
    sb.append("field1='").append(field1).append('\'');
    sb.append(", field2='").append(field2).append('\'');
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    CustomObject that = (CustomObject) object;
    return Objects.equals(getField1(), that.getField1())
        && Objects.equals(getField2(), that.getField2());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getField1(), getField2());
  }
}
