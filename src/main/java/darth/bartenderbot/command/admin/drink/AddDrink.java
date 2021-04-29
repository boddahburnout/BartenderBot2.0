package darth.bartenderbot.command.admin.drink;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.config.ConfigManager;
import darth.bartenderbot.handler.CommandHandler;
import darth.bartenderbot.permission.PermCheck;
import darth.bartenderbot.utils.FS.FileUtils;
import darth.bartenderbot.utils.Image.ImageUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class AddDrink extends Command {

    public AddDrink()
    {
        this.name = "adrink";
        this.arguments = "<category> <url>";
        this.guildOnly = false;
        this.help = "Add a drink to a cat";
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
        MessageChannel channel = event.getChannel();
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
        try {
            YamlFile botConfig = new ConfigManager().accessConfig();
            Map<String, String> commandData = new CommandHandler().getCommandData(guild, message);
            File category = new FileUtils().GetDrinkByString(commandData.get("args").split(" ")[0]);
            if (category.isDirectory()) {
                ImageUtils.saveImage(commandData.get("args").split(" ")[1], category.getPath());
                channel.sendMessage("Image added to category " + commandData.get("args").split(" ")[0]).queue();
                botConfig.set("drink." + commandData.get("args").split(" ")[0] + "." + commandData.get("args").split(" ")[1].substring(commandData.get("args").split(" ")[1].lastIndexOf("/")).replaceAll("/", ""), message.getAuthor().getId());
                System.out.println(message.getAuthor().getName() + " Downloaded image to " + category);
                botConfig.save();
            } else if (!category.isDirectory()) {
                channel.sendMessage("You must create this category first with !addcat " + commandData.get("args").split(" ")[1]).queue();
            }
        } catch (FileNotFoundException e) {
            channel.sendMessage("Image could not be downloaded!").queue();
            e.printStackTrace();
            return;
        } catch (IOException e) {
            channel.sendMessage("Image could not be downloaded!").queue();
            e.printStackTrace();
            return;
        } catch (IndexOutOfBoundsException e) {
            channel.sendMessage("Not enough input! !adddrink <category> <url>").queue();
            e.printStackTrace();
            return;
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
