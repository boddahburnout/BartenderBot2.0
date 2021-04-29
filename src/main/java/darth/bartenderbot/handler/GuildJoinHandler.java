package darth.bartenderbot.handler;

import darth.bartenderbot.config.ConfigManager;
import net.dv8tion.jda.api.entities.Guild;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.IOException;

public class GuildJoinHandler {
    public void appendGuild(Guild guild) {
        try {
            String prefix = "!";
            YamlFile botConfig = new ConfigManager().accessConfig();
            botConfig.load();
            if (!botConfig.isSet(guild.getId())) {
                botConfig.set(guild.getId() + "." + "Guild-Name", guild.getName());
                botConfig.set(guild.getId() + "." + "Prefix", prefix);
                botConfig.save();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
