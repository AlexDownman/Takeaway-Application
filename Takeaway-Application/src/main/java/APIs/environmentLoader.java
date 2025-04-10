package APIs;

import io.github.cdimascio.dotenv.Dotenv;

public class environmentLoader {
    public static Dotenv envLoader() {
        return Dotenv.configure().directory("Takeaway-Application/assets").filename("env.env").load();
    }
}
