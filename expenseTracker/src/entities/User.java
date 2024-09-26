package entities;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;



public class User implements Serializable {

    @Serial
    public static final long serialVersionUID = 51651548644165L;

    private ArrayList<Expense> expenses = new ArrayList<>();

    public void add() {
        Scanner expenseScanner = new Scanner(System.in);

        System.out.println("Description: ");
        String description = expenseScanner.nextLine();

        System.out.println("Amount: ");
        double amount = expenseScanner.nextDouble();

        expenseScanner.close();

        Expense expense = new Expense(amount, description);
        expenses.add(expense);

        System.out.println("Sucessfully added!");
    }

    public void delete(UUID id) {
        try {
            expenses.removeIf(e -> e.getId().compareTo(id) == 0);
            System.out.println("Sucessfully deleted!");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        ;

    }

    public void update(String option, String newInfo, UUID id) {

        switch (option) {
            case "description":

                expenses = expenses.stream()
                        .map((e) -> {
                            if (e.getId().equals(id)) {
                                e.setDescription(newInfo);
                            }
                            ;

                            return e;
                        })
                        .collect(Collectors.toCollection(ArrayList::new));

                break;

            case "amount":
                expenses.stream()
                        .map((e) -> {
                            if (e.getId().equals(id)) {
                                e.setAmount(Double.parseDouble(newInfo));
                            }
                            ;

                            return e;
                        })
                        .collect(Collectors.toCollection(ArrayList::new));
                break;

            default:
                System.out.println("Invalid option.");
                break;
        }

        System.out.println("Sucessfully updated!");

    }

    public void list() {
        System.out.println("          ID          |         Description         |       Amount      |     Date ");
        System.out.println("‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");

        for (Expense expense : expenses) {
            String row = expense.getId() + "     " + expense.getDescription() + "     " + expense.getAmount() + "     "
                    + expense.getDate();
            System.out.println(row);
        }
    }
    
    public void summary() {
        double sum = 0;

        for (Expense expense : expenses) {
            sum += expense.getAmount();
        }

        System.out.printf("Total expenses: %.2f\n", sum);
    }

    public void summary(int month) {
        double sum = 0;

        for (Expense expense : expenses) {
            if(expense.getDate().getMonthValue() == month){
                sum += expense.getAmount();
            }
        }

        System.out.printf("Total expenses in %dth month: %.2f\n", month, sum);
    }

    public void export() {
        Path path = Paths.get("..", "lib", "expenses.csv");

        String head = "ID, Description, Amount, Date\n";
        String row;

        try {
            if (expenses.size() == 0) {
                Files.writeString(path, head, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
            } else {
                for (Expense expense : expenses) {
                    row = expense.getId() + ","
                            + expense.getDescription() + ","
                            + expense.getAmount() + ","
                            + expense.getDate() + "\n";

                    Files.writeString(path, head + row, StandardOpenOption.TRUNCATE_EXISTING,
                            StandardOpenOption.CREATE);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
