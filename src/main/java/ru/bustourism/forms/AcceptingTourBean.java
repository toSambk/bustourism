package ru.bustourism.forms;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;

public class AcceptingTourBean {

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @DecimalMin(value = "1", message = "Поле должно содержать число больше или равно 1")
    @Pattern(regexp = "[0-9]*", message = "Количество покупаемых мест должно содержать число")
    public String quantity;

}
