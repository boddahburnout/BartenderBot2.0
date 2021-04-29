package darth.bartenderbot.command.user.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.handler.CommandHandler;
import darth.bartenderbot.utils.Discord.EmbedWrapper;
import darth.bartenderbot.utils.FS.FileUtils;
import darth.bartenderbot.utils.String.StringUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Drinks extends Command {

    public Drinks() {
        this.name = "drinks";
        this.help = "See a list a drinks to order";
        this.category = new BotCategories().UserCat();
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
        Map<String, String> commandData = null;
        try {
            commandData = new CommandHandler().getCommandData(guild, message);
            File file = new File("drinks/");
            List<String> files = new FileUtils().FileScan(file.listFiles());
            String drinksmsg = new StringUtils().ListToString(files);
            MessageEmbed embed = new EmbedWrapper().EmbedMessage(commandData.get("name"), "Bartender Bot", null, new EmbedWrapper().GetGuildEmbedColor(channel.getGuild()), drinksmsg.replaceAll("_", " "), null, null, null, null);
            channel.sendMessage("Here is what we are serving at " + channel.getGuild().getName() + ":").embed(embed).queue();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
