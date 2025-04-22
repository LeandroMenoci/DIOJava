public class Main {
    public static void main(String[] args) {

        var user = new User("Joao", 20);
        System.out.println(user);
        printValue(user);
        System.out.println(user);
    }

    private static void printValue(final User user) {
//        user = new User("Maria", 33); sem o final no parametro
        user.setName("Maria");
        user.setAge(33);
        System.out.println(user);

    }
}