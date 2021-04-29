package darth.bartenderbot.command.user.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.utils.Discord.EmbedWrapper;
import darth.bartenderbot.utils.FS.FileUtils;
import darth.bartenderbot.utils.String.StringUtils;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Emotes extends Command {

    public Emotes() {
        this.name = "emotes";
        this.help = "See the available emotes to send in chat";
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
        TextChannel channel = event.getTextChannel();
        Map<String, String> commandData = new HashMap<>();
        File file = new File("emotes/");
        List<String> files = new FileUtils().FileScan(file.listFiles());
        String drinksmsg = new StringUtils().ListToString(files);
        MessageEmbed embed = null;
        try {
            embed = new EmbedWrapper().EmbedMessage(commandData.get("name"), "Bartender Bot", null, new EmbedWrapper().GetGuildEmbedColor(channel.getGuild()), drinksmsg, null, null, null, null);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        channel.sendMessage("Here are the emotes available" + ":").embed(embed).queue();
    }
}
