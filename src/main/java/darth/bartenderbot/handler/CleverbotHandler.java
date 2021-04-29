package darth.bartenderbot.handler;

import com.michaelwflaherty.cleverbotapi.CleverBotQuery;
import darth.bartenderbot.config.ConfigManager;
import net.dv8tion.jda.api.entities.*;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.IOException;
import java.util.List;

public class CleverbotHandler {
    boolean mentioned = false;

    public void sendRequest(Guild guild, String botID, TextChannel channel, Message message, Member member, List<Role> roles) throws InvalidConfigurationException, IOException {
        for (Role role : roles) {
            if (message.getMentionedRoles().contains(role)) {
                mentioned = true;
            }
        }
        if (!mentioned) {
            YamlFile botConfig = new ConfigManager().accessConfig();
            String name = message.getContentRaw().split(" ") [0];
            String args = message.getContentRaw().replaceAll(name, "");
            System.out.println(args);
            CleverBotQuery bot = new CleverBotQuery(botConfig.getString("Global.CleverBot-Token"), args);
            bot.setConversationID("MXYxCTh2MQlBdldZQkVVUFVERUYJMUZ2MTYxODM4ODM5Mwk2NHZAQmFydGVuZGVyIEJvdC4JNjRpKmxvb2tzIGludG8geW91ciBleWVzKi4J");
            String response;
            try {
                bot.sendRequest();
                response = bot.getResponse();
                System.out.println("Cleverbot request made in " + guild.getIdLong() + " in channel " + channel.getGuild() + " by user " + member.getUser().getName());
            } catch (IOException e) {
                System.out.println(e);
                response = e.getMessage();
            }
            channel.sendMessage(response).queue();
        }
    }
}