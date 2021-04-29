package darth.bartenderbot.command.admin.tools;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.config.ConfigManager;
import darth.bartenderbot.permission.PermCheck;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Setbr extends Command {

    public Setbr() {
        this.name = "setbr";
        this.help = "Set the bartender role for your guild allowing members to access staff cmd's";
        this.category = new BotCategories().AdminCat();
    }
    /**
     * The main body method of a {@link Command Command}.
     * <br>This is the "response" for a successful
     * {@link Command#run(CommandEvent) #run(CommandEvent)}.
     *
     * @param event The {@link CommandEvent CommandEvent} that
     *              triggered this Command
     */
    @Override
    protected void execute(CommandEvent event) {
        Guild guild = event.getGuild();
        Message message = event.getMessage();
        Member member = event.getMember();
        try {
            if (!new PermCheck().CheckGuildRole(guild, member)) {
                message.getChannel().sendMessage("That command is staff only!").queue();
                return;
            }
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        YamlFile botConfig = null;
        try {
            botConfig = new ConfigManager().accessConfig();
            List<Role> role = message.getMentionedRoles();
            List<String> roleName = new ArrayList<>();
            if (botConfig.getList("Perms.Guilds." + guild.getId()) != null) {
                roleName = (List<String>) botConfig.getList("Perms.Guilds." + guild.getId());
            }
            for (Role r : role) {
                roleName.add(r.getId());
            }
            botConfig.set("Perms.Guilds." + guild.getId(), roleName);
            message.getChannel().sendMessage("Perms have been set!").queue();
            botConfig.save();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}