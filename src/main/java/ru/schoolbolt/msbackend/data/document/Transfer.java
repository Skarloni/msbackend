package ru.schoolbolt.msbackend.data.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import ru.schoolbolt.msbackend.data.model.Product;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Transfer implements IDocument {
    public int number;
    public String from;
    public String to;
    public List<Product> products;

    @Override
    public int getNumber() {
        return number;
    }
}
