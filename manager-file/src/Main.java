import br.com.dio.persistence.FilePersistence;
import br.com.dio.persistence.IOFilePersistence;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        FilePersistence persistence = new IOFilePersistence("user.csv");
        System.out.println("====================");
        System.out.println(persistence.write("Lucas;lucas@lucas.com;15/02/1992;"));
        System.out.println("====================");
        System.out.println(persistence.write("Maria;maria@maria.com;12/02/1992;"));
        System.out.println("====================");
        System.out.println(persistence.write("Leandro;leandro@leandro.com;11/02/1992;"));
        System.out.println("====================");
        System.out.println(persistence.write("Bete;bete@bete.com;30/05/1992;"));
        System.out.println("====================");
        System.out.println(persistence.write("Bia;bia@bia.com;25/11/1992;"));
        System.out.println("====================");
        System.out.println(persistence.findAll());
        System.out.println("====================");
        System.out.println(persistence.remove("Bia;"));
        System.out.println("====================");
        System.out.println(persistence.remove("Carlos;"));
        System.out.println("====================");
        System.out.println(persistence.findBy("Lucas;"));
        System.out.println("====================");
        System.out.println(persistence.findBy("leandro@"));
        System.out.println("====================");
        System.out.println(persistence.findBy("25/11/1992;"));
        System.out.println("====================");
        System.out.println(persistence.replace("@maria.com;12/02", "Claudia;claudia@claudia.com;21/03/1992"));
        System.out.println("====================");
        System.out.println(persistence.findAll());

    }
}