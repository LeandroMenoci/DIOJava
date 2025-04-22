import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
//        var value = "java;java;java;java";
//        value = value.replaceFirst("j", "J");
//        value = value.replace("j", "J");

//        var values = value.split(";");
//        for (var v : values){
//            System.out.println(v);
//        }

        var value = """
                {"name":"Joao","age":"18"}""";
        Map<String, String> map = new HashMap<>();
        value = value.replace("{", "").replace("}", "").replace("\"", "");
        var valueArr = value.split(",");
        for (var v : valueArr) {
            var keyValue = v.split(":");
            map.put(keyValue[0], keyValue[1]);
        }
        System.out.println(map);
    }
}