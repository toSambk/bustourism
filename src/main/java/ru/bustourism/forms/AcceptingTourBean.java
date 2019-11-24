package ru.bustourism.forms;

import javax.validation.constraints.Min;

public class AcceptingTourBean {

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Min(value = 1, message = "Поле должно содержать число больше или равно 1")
    public Integer quantity;

}
