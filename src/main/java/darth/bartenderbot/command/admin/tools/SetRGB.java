package darth.bartenderbot.command.admin.tools;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.config.ConfigManager;
import darth.bartenderbot.handler.CommandHandler;
import darth.bartenderbot.permission.PermCheck;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SetRGB extends Command {

    public SetRGB() {
        this.name = "setrgb";
        this.help = "Set the embedded color for your guild";
        this.arguments = "<r> <g> <b>";
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
            botConfig = (new ConfigManager()).accessConfig();
        Map<String, String> commandData = (new CommandHandler()).getCommandData(guild, message);
        List<String> RGB = Arrays.asList(((String)commandData.get("args")).split("[\\s]+"));
        botConfig.set(guild.getId() + ".Embed-rgb", RGB);
        botConfig.save();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.getChannel().sendMessage("Embed message color set!").queue();
    }
}
