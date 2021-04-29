package darth.bartenderbot.command.admin.drink;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.handler.CommandHandler;
import darth.bartenderbot.permission.PermCheck;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class AddDrinkC extends Command {

    public AddDrinkC() {
        this.name = "adrinkc";
        this.category = new BotCategories().AdminCat();
        this.help = "Add a cat to download drinks too";
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
            File category = new File("drinks/" + commandData.get("args").toLowerCase().replaceAll(" ", "_"));
            boolean bool = category.mkdirs();
            if (bool) {
                message.getChannel().sendMessage("Category " + category.getName().replaceAll("_", " ") + " added!").queue();
            } else {
                if (category.isDirectory()) {
                    message.getChannel().sendMessage("Category exist already!").queue();
                    return;
                }
                message.getChannel().sendMessage("Creation failed!").queue();
            }
        } catch (IndexOutOfBoundsException | InvalidConfigurationException | IOException e) {
            message.getChannel().sendMessage("Not enough input! !addcat <catname>").queue();
        }
    }
}
