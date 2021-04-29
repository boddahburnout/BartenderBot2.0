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

public class RemoveDrinkC extends Command {

    public RemoveDrinkC() {
        this.name = "removedrinkc";
        this.aliases = new String[]{"remdrinkc"};
        this.category = new BotCategories().AdminCat();
        this.help = "Remove a drink cat";
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
            }
        } catch (InvalidConfigurationException  | IOException e) {
            e.printStackTrace();
        }
        try {
        Map<String, String> commandData = new CommandHandler().getCommandData(guild, message);
            boolean bool = new FileUtils().CategoryRemove(commandData.get("args").toLowerCase().replaceAll(" ", "_"));
            if (bool) {
                message.getChannel().sendMessage("Category deleted!").queue();
            } else {
                message.getChannel().sendMessage("Delete failed!").queue();
            }
        } catch (IndexOutOfBoundsException e) {
            message.getChannel().sendMessage("Not enough input! !removecat <category>").queue();
        } catch (NullPointerException | InvalidConfigurationException | IOException e) {
            message.getChannel().sendMessage("Category does not exist!").queue();
        }
    }
}
