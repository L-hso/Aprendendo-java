package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Expense implements Serializable{

    private UUID id = UUID.randomUUID();
    private LocalDate date = LocalDate.now();
    private double amount;
    private String description;

    public Expense(double amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public UUID getId() {
        return id;
    }

}
