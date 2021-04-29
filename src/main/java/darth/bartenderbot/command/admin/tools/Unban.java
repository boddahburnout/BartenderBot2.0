package darth.bartenderbot.command.admin.tools;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.config.ConfigManager;
import darth.bartenderbot.permission.PermCheck;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.IOException;
import java.util.List;

public class Unban extends Command {

    public Unban() {
        this.name = "unban";
        this.category = new BotCategories().AdminCat();
        this.help = "Remove a ban from a user";
        this.arguments = "<@user>";
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
        YamlFile botConfig = null;
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
        try {
            botConfig = new ConfigManager().accessConfig();
            List<Member> ban = message.getMentionedMembers();
            for (Member user : ban) {
                if (botConfig.getBoolean(guild.getId() + ".bans." + user.getUser().getId()) == true) {
                    botConfig.set(guild.getId() + ".bans." + user.getUser().getId(), false);
                    botConfig.save();
                } else {
                    message.getChannel().sendMessage("This user is already unbanned!").queue();
                }
            }
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}