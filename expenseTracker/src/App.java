import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;
import entities.User;

public class App {
    public static void main(String[] args) throws Exception {

        User user = deserizalizate();

        try {
            switch (args[0]) {
                case "add":
                    user.add();
                    break;

                case "update":
                    user.update(args[1], args[2], UUID.fromString(args[3]));
                    break;

                case "delete":
                    user.delete(UUID.fromString(args[1]));
                    break;

                case "list":
                    user.list();
                    break;

                case "summary":
                if(args.length > 1){
                    user.summary(Integer.parseInt(args[1]));
                } else {
                    user.summary();
                }
                    break;
        
                case "export":
                    user.export();
                    break;

                default:
                    System.err.println("None of user options was chosen");
            }

            serializate(user);

        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("None option was chosen");
        }
    }








    private static void serializate(User user) throws IOException, FileNotFoundException {
        FileOutputStream fos = new FileOutputStream("../lib/expenses.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.reset();
        oos.writeObject(user);
        oos.flush();
        oos.close();
        fos.close();
    }





    public static User deserizalizate() throws IOException, ClassNotFoundException, FileNotFoundException {
        User user = new User();
        ObjectInputStream ois;
        FileInputStream fis;

        try {
            fis = new FileInputStream("../lib/expenses.ser");
            ois = new ObjectInputStream(fis);

            user = (User) ois.readObject();

            ois.close();
            fis.close();

        } catch (EOFException e){
            System.err.println("File not found!");
        }

        return user;
    }
}
