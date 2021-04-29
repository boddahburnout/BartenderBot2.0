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

public class Ban extends Command {

    public Ban() {
        this.name = "ban";
        this.category = new BotCategories().AdminCat();
        this.help = "Block a users access to the bot in your guild";
        this.guildOnly = true;
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
            List<Member> ban = message.getMentionedMembers();
            for (Member user : ban) {
                if (!botConfig.getBoolean(guild.getId() + ".bans." + user.getUser().getId())) {
                    botConfig.set(guild.getId() + ".bans." + user.getUser().getId(), true);
                    botConfig.save();
                    message.getChannel().sendMessage(user.getAsMention() + " Has been banned from using " + guild.getSelfMember().getNickname()).queue();
                } else {
                    message.getChannel().sendMessage("This user is already banned!").queue();
                }
            }
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}