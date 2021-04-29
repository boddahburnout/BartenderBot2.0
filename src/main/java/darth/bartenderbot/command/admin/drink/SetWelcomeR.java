package darth.bartenderbot.command.admin.drink;

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

public class SetWelcomeR extends Command {

    public SetWelcomeR()
    {
        this.name = "setwelcomer";
        this.aliases = new String[]{"setwr"};
        this.help = "Set the role to mention when welcoming a new user";
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
            }
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
        try {
            YamlFile botConfig = new ConfigManager().accessConfig();
            botConfig.set(guild.getId() + ".Welcome-Mention-Role", message.getMentionedRoles().get(0).getId());
            botConfig.save();
            message.getChannel().sendMessage("Role set to welcome message!").queue();
        } catch (IndexOutOfBoundsException e) {
            message.getChannel().sendMessage("You need to mention a role!").queue();
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }
}
