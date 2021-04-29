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
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

public class AddChatEmote extends Command {

    public AddChatEmote() {
        this.name = "achatemote";
        this.aliases = new String[]{"achate"};
        this.category = new BotCategories().AdminCat();
        this.help = "download an emote image to a emote cat";
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
        try {
        Map<String, String> commandData = new CommandHandler().getCommandData(guild, message);
            File category = new FileUtils().GetEmoteByString(commandData.get("args").toLowerCase().split(" ") [0]);
            ImageUtils.saveImage(commandData.get("args").toLowerCase().split(" ") [1], category.getPath());
            message.getChannel().sendMessage("Image downloaded!").queue();
            //Config.set("emote." + input.get(1) + "." + input.get(2).substring(input.get(2).lastIndexOf("/")).replaceAll("/", ""), event.getAuthor().getId());
            //Config.write();
            System.out.println(message.getAuthor().getName() + " from guild " + guild.getName() + " Downloaded image to " + category.getName());
        } catch (FileNotFoundException e) {
            message.getChannel().sendMessage("Download failed!").queue();
            return;
        } catch (MalformedURLException e) {
            message.getChannel().sendMessage("Download failed!").queue();
            return;
        } catch (IOException e) {
            message.getChannel().sendMessage("Download failed!").queue();
            return;
        } catch (IndexOutOfBoundsException | InvalidConfigurationException e) {
            message.getChannel().sendMessage("Please provide an emote category and url!").queue();
            return;
        }
    }
}
