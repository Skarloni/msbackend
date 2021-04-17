package ru.schoolbolt.msbackend.data.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Product {
    @JsonSerialize(using = ToStringSerializer.class)
    public int code;
    public String name;
    @JsonSerialize(using = ToStringSerializer.class)
    public float price;
    @JsonSerialize(using = ToStringSerializer.class)
    public float sellingPrice;
    @JsonSerialize(using = ToStringSerializer.class)
    public int count;
}
