package second;

public class Main {
    public static void main(String[] args) {
        User user = null;

        user = NullChecker.from(user)
                .map(u -> {
                    u.setName("Прокоп");
                    u.setItem("Укроп");
                })
                .ifNull(new User("Прокоп", "Укроп"));

        System.out.println(user.getName());  
        System.out.println(user.getItem());  
    }
}
