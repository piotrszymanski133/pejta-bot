import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class BotController {

    private final DiscordClient client;
    private final GatewayDiscordClient gateway;

    public BotController(String token){
        this.client = DiscordClient.create(token);
        this.gateway = client.login().block();
    }

    public void listen() {
        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            final MessageChannel channel = message.getChannel().block();

            if (message.getContent().startsWith("!losuj")){
                List<String> command = Arrays.asList(message.getContent().split("\\s+").clone());
                List<Integer> numbers = command.stream()
                        .filter(word -> word.matches("[0-9]+"))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                if (numbers.size() < 2) {
                    channel.createMessage("You should put min and max values!").block();
                }
                else {
                    Random random = new Random();
                    int randVal = random.nextInt(Collections.max(numbers) - (Collections.min(numbers))) + (Collections.min(numbers));
                    channel.createMessage(String.format("Your random value is %d", randVal)).block();
                }
            }
            if ("!ping".equals(message.getContent())) {
                channel.createMessage("Pong!").block();
            }
        });
        gateway.onDisconnect().block();
    }
}
