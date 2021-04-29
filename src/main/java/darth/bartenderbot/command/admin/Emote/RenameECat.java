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

import java.io.IOException;
import java.util.Map;

public class RenameECat extends Command {

    public RenameECat() {
        this.name = "renameEcat";
        this.help = "rename an emote cat";
        this.aliases = new String[]{"recat"};
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
            boolean bool = new FileUtils().EmoteRename(commandData.get("args").split(" ") [0], commandData.get("args").split(" ") [1]);
            if (bool) {
                channel.sendMessage("Emotes " + commandData.get("args").split(" ") [0] + " has been renamed to " + commandData.get("args").split(" ") [1]).queue();
            } else {
                channel.sendMessage("Emote rename failed!").queue();
            }
        } catch (IndexOutOfBoundsException | InvalidConfigurationException | IOException e) {
            channel.sendMessage("Not enough input! !renameemote <emote> <newemote>").queue();
        }
    }
}
