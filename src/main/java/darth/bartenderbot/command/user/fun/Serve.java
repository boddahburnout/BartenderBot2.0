package darth.bartenderbot.command.user.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.handler.CommandHandler;
import darth.bartenderbot.utils.FS.FileUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Serve extends Command {

    public Serve() {
        this.name = "serve";
        this.help = "Serve a drink to yourself ot your friends";
        this.arguments = "optional <drink> <@user>";
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
        Guild guild = event.getGuild();
        Message message = event.getMessage();
        Map<String, String> commandData = null;
        try {
            commandData = new CommandHandler().getCommandData(guild, message);
            if (commandData.get("args").split(" ")[0].isEmpty()) { // No arguments no problem, everyone gets a drink!
                try {
                    File Category = new FileUtils().GetRandomFile(new File("drinks/"));
                    File Image = new FileUtils().GetRandomImage(Category);
                    //MessageEmbed embeded = new EmbededBuilder().EmbedMessage("test", "", Color.BLUE, "Since any drink will do, have some " + Category.getName().replaceAll("_", " "), null, null, null,  new File(Category.getAbsolutePath()).toURI().toURL().toString()+Image.getName());
                    message.getChannel().sendMessage("Since any drink will do, have some " + Category.getName().replaceAll("_", " ")).addFile(Image).queue();
                    //    channel.sendMessage("Test").embed(embeded).queue();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    message.getChannel().sendMessage("Please add some drinks first!").queue();
                }
                return;
            }
            try {
                List<User> mention = message.getMentionedUsers();
                if (!mention.isEmpty()) {
                    try {
                        List<User> user = message.getMentionedUsers();
                        File category = new FileUtils().GetDrinkByString(commandData.get("args").split(" ")[0].trim().replace(" ", "_"));
                        File Image = new FileUtils().GetRandomImage(category);
                        message.getChannel().sendMessage(message.getAuthor().getAsMention() + " ordered " + user.get(0).getAsMention() + " " + category.getName().replaceAll("_", " ") + "!").addFile(Image).queue();
                        return;
                    } catch (NullPointerException e1) {
                        message.getChannel().sendMessage("We do not serve " + commandData.get("args").trim() + " here at " + guild.getName()).queue();
                    }
                } else {
                    try {
                        File category = new FileUtils().GetDrinkByString(commandData.get("args").trim().replace(" ", "_"));
                        File Image = new FileUtils().GetRandomImage(category);
                        message.getChannel().sendMessage("Here is some " + commandData.get("args").trim() + " on the house!").addFile(Image).queue();
                    } catch (NullPointerException e2) {
                        message.getChannel().sendMessage("We do not serve " + commandData.get("args").trim() + " here at " + guild.getName()).queue();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}