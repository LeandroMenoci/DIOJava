import br.com.dio.persistence.FilePersistence;
import br.com.dio.persistence.NIOFilePersistence;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        FilePersistence persistence = new NIOFilePersistence("user.csv");
        System.out.println(persistence.write("bianca;bianca@bianca.com;11/03/2000;"));
        System.out.println("===================");
        System.out.println(persistence.write("rubens;rubens@rubens.com;29/03/1992;"));
        System.out.println("===================");
        System.out.println(persistence.write("leandro;leandro@leandro.com;16/11/1998;"));
        System.out.println("===================");
        System.out.println(persistence.write("claudia;claudia@claudia.com;02/12/1999;"));
        System.out.println("===================");
        System.out.println(persistence.findAll());
        System.out.println("===================");

    }
}