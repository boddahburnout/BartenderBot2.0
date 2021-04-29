package darth.bartenderbot.command.admin.Emote;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.handler.CommandHandler;
import darth.bartenderbot.permission.PermCheck;
import darth.bartenderbot.utils.FS.FileUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class PreviewEmote extends Command {

    public PreviewEmote() {
        this.name = "previewemote";
        this.category = new BotCategories().UserCat();
        this.help = "Preview emotes in a category";
        this.aliases = new String[]{"pemote"};
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
            }
        } catch (InvalidConfigurationException  | IOException e) {
            e.printStackTrace();
        }
        try {
        Map<String, String> commandData = new CommandHandler().getCommandData(guild, message);
            File file = new FileUtils().GetEmoteByString(commandData.get("args").split(" ") [0]);
            File image = new FileUtils().GetImageByString(file, commandData.get("args").split(" ") [1]);
            channel.sendMessage("Here is the file: " + image.getName() + " from emote " + file.getName()).addFile(image).queue();
        } catch (NullPointerException e) {
            channel.sendMessage("File was not found!").queue();
        } catch (IndexOutOfBoundsException | InvalidConfigurationException | IOException e) {
            channel.sendMessage("Please specify an emote and file name!").queue();
        }
    }
}
