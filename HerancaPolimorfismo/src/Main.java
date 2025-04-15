//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Employee employee = new Employee();
        Salesman salesman = new Salesman();
        Manager manager = new Manager();

        System.out.println(employee instanceof Salesman);
    }
}