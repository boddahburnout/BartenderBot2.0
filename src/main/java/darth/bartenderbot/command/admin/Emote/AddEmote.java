package darth.bartenderbot.command.admin.Emote;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.handler.CommandHandler;
import darth.bartenderbot.permission.PermCheck;
import darth.bartenderbot.utils.FS.FileUtils;
import darth.bartenderbot.utils.Image.ImageUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

public class AddEmote extends Command {

    public AddEmote() {
        this.name = "addemote";
        this.help = "add a emote image to an existing cat";
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
        MessageChannel channel = event.getChannel();
        Message message = event.getMessage();
        Member member = event.getMember();
        try {
            if (!new PermCheck().CheckGuildRole(guild, member)) {
                channel.sendMessage("That command is staff only!").queue();
                return;
            }
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> commandData = null;
        try {
            commandData = new CommandHandler().getCommandData(guild, message);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            File category = new FileUtils().GetEmoteByString(commandData.get("args").split(" ") [0]);
            ImageUtils.saveImage(commandData.get("args").split(" ") [1], category.getPath());
            channel.sendMessage("Image downloaded!").queue();
            System.out.println(message.getAuthor().getName() + " from guild " + guild.getName() + " Downloaded image to " + category.getName());
        } catch (FileNotFoundException e) {
            channel.sendMessage("Download failed!").queue();
            return;
        } catch (MalformedURLException e) {
            channel.sendMessage("Download failed!").queue();
            return;
        } catch (IOException e) {
            channel.sendMessage("Download failed!").queue();
            return;
        } catch (IndexOutOfBoundsException e) {
            channel.sendMessage("Please provide an emote category and url!").queue();
            return;
        }
    }
}
