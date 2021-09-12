

public class Main {
    public static void main(final String[] args) {
        final String token = args[0];
        BotController controller = new BotController(token);
        controller.listen();
    }
}
