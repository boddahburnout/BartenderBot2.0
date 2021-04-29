package darth.bartenderbot.command.admin.drink;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.handler.CommandHandler;
import darth.bartenderbot.permission.PermCheck;
import darth.bartenderbot.utils.FS.FileUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.IOException;
import java.util.Map;

public class RenameDrinkC extends Command {

    public RenameDrinkC() {
        this.name = "renamedrinkcat";
        this.aliases = new String[]{"rendrinkc"};
        this.help = "Rename a drink cat";
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
        Map<String, String> commandData;
        Member member = event.getMember();
        try {
            if (!new PermCheck().CheckGuildRole(guild, member)) {
                message.getChannel().sendMessage("That command is staff only!").queue();
            }
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
        try {
            commandData = new CommandHandler().getCommandData(guild, message);
            try {
                boolean bool = new FileUtils().CategoryRename(commandData.get("args").toLowerCase().split(" ")[0], commandData.get("args").toLowerCase().split(" ")[1]);
                if (bool) {
                    message.getChannel().sendMessage("Category " + commandData.get("args").toLowerCase().split(" ")[0] + " has been renamed to " + commandData.get("args").toLowerCase().split(" ")[1]).queue();
                } else {
                    message.getChannel().sendMessage("Category rename failed!").queue();
                }
            } catch (IndexOutOfBoundsException e) {
                message.getChannel().sendMessage("Not enough input! !renamecat <category>").queue();
            }
        } catch (InvalidConfigurationException  | IOException e) {
            e.printStackTrace();
        }
    }
}