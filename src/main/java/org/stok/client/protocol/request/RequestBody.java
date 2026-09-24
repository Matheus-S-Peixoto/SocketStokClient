package org.stok.client.protocol.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

public class RequestBody {
    private String name;
    private String description;
    private BigDecimal amount;
    private String code;
    private Integer quantity;

    @JsonIgnore
    public boolean isEmpty() {
        return (getName() == null) && (getDescription() == null) && (getAmount() == null) && (getCode() == null) && (getQuantity() == null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setAmount(String amount) {
        if (!amount.isBlank()) {
            this.amount = new BigDecimal(amount);
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
