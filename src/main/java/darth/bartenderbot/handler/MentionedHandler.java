package darth.bartenderbot.handler;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.IOException;

public class MentionedHandler {
    public void sendprefix(Guild guild, Message message) {
        TextChannel channel = message.getTextChannel();
        Member member = message.getMember();
        JDA jda = channel.getJDA();
//        try {
//            YamlFile botConfig = new ConfigManager().accessConfig();
            if (message.getContentRaw().contains("prefix")) {
//                if (message.getContentRaw().split(" ")[1].equalsIgnoreCase("prefix")) {
//                    channel.sendMessage("My prefix is set to " + botConfig.get(guild.getId() + ".Prefix")).queue();
//                    return;
//                }
//            }
//        } catch (InvalidConfigurationException | IOException e) {
//            e.printStackTrace();
//        }
                channel.sendMessage("My prefix is ~").queue();
            }
        try {
            new CleverbotHandler().sendRequest(guild, "697355371056201858", channel, message, member, jda.getRoles());
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }
}
