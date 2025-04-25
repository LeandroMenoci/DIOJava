import dao.ClientDAO;
import dao.GenericDAO;
import dao.UserDAO;
import domain.ClientDomain;
import domain.UserDomain;

public class Main {

    private static final GenericDAO<Integer, UserDomain> userDao =
            new UserDAO();
    private static final GenericDAO<String, ClientDomain> clientDao =
            new ClientDAO();

    public static void main(String[] args) {
        System.out.println("===USERDAO===");
        var user = new UserDomain(1, "Joao", 34);
        System.out.println(userDao.count());
        System.out.println(userDao.save(1, user));
        System.out.println(userDao.findAll());
        System.out.println(userDao.find(d -> d.getId().equals(1)));
        System.out.println(userDao.find(d -> d.getId().equals(2)));
        System.out.println(userDao.count());
        System.out.println(userDao.delete(new UserDomain(-1, "", -1)));
        System.out.println(userDao.delete(user));
        System.out.println(userDao.findAll());
        System.out.println(userDao.count());
        System.out.println("===USERDAO(FIM)===");

        System.out.println("===ClientDAO===");
        var client = new ClientDomain("A", "Maria", 34);
        System.out.println(clientDao.count());
        System.out.println(clientDao.save(1, client));
        System.out.println(clientDao.findAll());
        System.out.println(clientDao.find(d -> d.getId().equals("A")));
        System.out.println(clientDao.find(d -> d.getId().equals("2")));
        System.out.println(clientDao.count());
        System.out.println(clientDao.delete(new ClientDomain("", "", -1)));
        System.out.println(clientDao.delete(client));
        System.out.println(clientDao.findAll());
        System.out.println(clientDao.count());
        System.out.println("===USERDAO(FIM)===");
    }
}