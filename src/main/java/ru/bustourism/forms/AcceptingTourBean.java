package ru.bustourism.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class AcceptingTourBean {

    @Positive(message = "Поле должно содержать число больше или равно 1")
    @NotNull
    private Integer quantity;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
