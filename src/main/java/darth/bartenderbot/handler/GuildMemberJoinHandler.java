package darth.bartenderbot.handler;

import darth.bartenderbot.config.ConfigManager;
import darth.bartenderbot.utils.FS.FileUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;

public class GuildMemberJoinHandler {
    public void welcomeMember(Guild guild, Member member) throws InvalidConfigurationException, IOException {
        YamlFile Config = new ConfigManager().accessConfig();
        if (Config.isSet(guild.getId() + "." + "Welcome-Channel")) {
            TextChannel channel = guild.getTextChannelById(Config.getString(guild.getId() + "." + "Welcome-Channel"));
            Role role = guild.getRoleById(Config.getString(guild.getId() + ".Welcome-Mention-Role"));
            File file = new FileUtils().GetRandomFile(new File("drinks/"));
            File image = new FileUtils().GetRandomImage(file);
            if (Config.contains(guild.getId() + ".Welcome-Mention-Role")) {
                channel.sendMessage("Welcome " + member.getAsMention() + " to " + channel.getGuild().getName() + " have some " + file.getName().replaceAll("_", " ") + " on the house! \n" + role.getAsMention() + " make our new friend comfortable!").addFile(image).queue();
            } else {
                channel.sendMessage("Welcome " + member.getAsMention() + " to " + channel.getGuild().getName() + " have some " + file.getName().replaceAll("_", " ") + " on the house!").addFile(image).queue();
            }
        }
    }
}