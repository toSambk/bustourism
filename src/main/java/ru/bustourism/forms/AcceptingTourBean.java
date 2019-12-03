package ru.bustourism.forms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class AcceptingTourBean {

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Positive(message = "Поле должно содержать число больше или равно 1")
    @NotNull
    public Integer quantity;

}
