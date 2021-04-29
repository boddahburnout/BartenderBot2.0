package darth.bartenderbot.command.admin.Emote;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.handler.CommandHandler;
import darth.bartenderbot.permission.PermCheck;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class AddEmoteCat extends Command {

    public AddEmoteCat() {
        this.name = "addemotec";
        this.help = "Add a emote cat to download emotes to";
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
        Map<String, String> commandData = null;
        try {
            commandData = new CommandHandler().getCommandData(guild, message);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TextChannel channel = message.getTextChannel();
        try {
            File category = new File("emotes/" + commandData.get("args").toLowerCase());
            boolean bool = category.mkdirs();
            if (bool) {
                channel.sendMessage("Emote " + category.getName() + " added!").queue();
            } else {
                if (category.isDirectory()) {
                    channel.sendMessage("Emote exist already!").queue();
                    return;
                }
                channel.sendMessage("Emote creation failed!").queue();
            }
        } catch (IndexOutOfBoundsException e) {
            channel.sendMessage("Not enough input! !addcat <emotename>").queue();
        }
    }
}
