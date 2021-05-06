package darth.bartenderbot.handler;

import darth.bartenderbot.config.ConfigManager;
import darth.bartenderbot.utils.Discord.EmbedWrapper;
import darth.bartenderbot.utils.String.RandomPhrase;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.simple.parser.ParseException;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.awt.*;
import java.io.IOException;

public class GuildMemberJoinHandler {
    public void welcomeMember(Guild guild, Member member) throws InvalidConfigurationException, IOException, ParseException {
        YamlFile Config = new ConfigManager().accessConfig();
        if (Config.isSet(guild.getId() + "." + "Welcome-Channel")) {
            TextChannel channel = guild.getTextChannelById(Config.getString(guild.getId() + "." + "Welcome-Channel"));
            String[] drink = new RandomPhrase().getRandomJoinPhrase(guild, member.getUser());
            Color color = Color.BLUE;
            try {
                color = new EmbedWrapper().GetGuildEmbedColor(guild);
            } catch (InvalidConfigurationException | IOException e) {
                e.printStackTrace();
            }
            Role role = guild.getRoleById(Config.getString(guild.getId() + ".Welcome-Mention-Role"));
            channel.sendMessage(new EmbedWrapper().EmbedMessage("Welcome " + member.getUser().getName(), "", "", color, drink[0] + role.getAsMention(), null, null, null, drink[1])).queue();
        }
    }
}