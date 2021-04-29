package darth.bartenderbot.command.user.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.doc.standard.CommandInfo;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.handler.CommandHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.IOException;
import java.util.Map;

@CommandInfo(
        name = {"echo"},
        description = "Make the bot speak for you"
)

public class Echo extends Command {

    public Echo() {
        this.name = "echo";
        this.guildOnly = true;
        this.category = new BotCategories().UserCat();
        this.arguments = "<Message>";
        this.help = "Make the bot speak for you";
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
        TextChannel channel = event.getTextChannel();
        Message message = event.getMessage();
        try {
            Map<String, String> commandData = new CommandHandler().getCommandData(guild, message);
            message.delete().queue();
            channel.sendMessage(commandData.get("args")).queue();
            System.out.println(message.getAuthor() + " in guild " + guild.toString() + " in channel " + channel.toString() + " echo'd the message " + commandData.get("args"));
            return;
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
