package darth.bartenderbot.command.admin.drink;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.handler.CommandHandler;
import darth.bartenderbot.permission.PermCheck;
import darth.bartenderbot.utils.Discord.EmbedWrapper;
import darth.bartenderbot.utils.FS.FileUtils;
import darth.bartenderbot.utils.String.StringUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ListDrinkImages extends Command {

    public ListDrinkImages() {
        this.name = "listdrinkimages";
        this.aliases = new String[]{"ldrinki"};
        this.category = new BotCategories().UserCat();
        this.help = "List images save to a drink cat";
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
        TextChannel channel = message.getTextChannel();
        Member member = event.getMember();
        try {
            if (!new PermCheck().CheckGuildRole(guild, member)) {
                message.getChannel().sendMessage("That command is staff only!").queue();
                return;
            }
        } catch (InvalidConfigurationException  | IOException e) {
            e.printStackTrace();
        }
        try {
        Map<String, String> commandData = new CommandHandler().getCommandData(guild, message);
            File[] files = new FileUtils().GetFilesByString(new File("emotes/"), commandData.get("args").split(" ") [0]);
            List<String> filenames = new FileUtils().FileScan(files);
            String drinkmsg = new StringUtils().ListToString(filenames);
            MessageEmbed embed = new EmbedWrapper().EmbedMessage(commandData.get("args").split(" ") [0], "Bartender Bot" ,null, Color.BLUE, drinkmsg, null, null, null, null);
            channel.sendMessage("Here are the images for this category!").embed(embed).queue();
        } catch (IndexOutOfBoundsException e) {
            channel.sendMessage("Not enough input! !listimages <category>").queue();
        } catch (NullPointerException | InvalidConfigurationException | IOException e) {
            channel.sendMessage("Category doesn't exist!").queue();
        }
    }
}
