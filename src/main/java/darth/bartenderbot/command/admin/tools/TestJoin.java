package darth.bartenderbot.command.admin.tools;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.config.ConfigManager;
import darth.bartenderbot.permission.PermCheck;
import darth.bartenderbot.utils.FS.FileUtils;
import net.dv8tion.jda.api.entities.*;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;

public class TestJoin extends Command {

    public TestJoin() {
        this.category = new BotCategories().AdminCat();
        this.help = "Send a fake join to test your servers welcome messages";
        this.guildOnly = true;
        this.name = "testjoin";
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
        YamlFile botConfig = null;
        try {
            botConfig = new ConfigManager().accessConfig();
            String id = String.valueOf(botConfig.get(guild.getId() + ".Welcome-Channel"));
            if (id == null) {
                message.getChannel().sendMessage("Welcome message has failed!").queue();
                return;
            }
            try {
                TextChannel idchannel = guild.getTextChannelById(id);
                File file = new FileUtils().GetRandomFile(new File("drinks/"));
                File image = new FileUtils().GetRandomImage(file);
                Role role = guild.getRoleById(String.valueOf(botConfig.get(guild.getId() + ".Welcome-Mention-Role")));
                //idchannel.sendMessage("Welcome " + event.getMember().getAsMention() + " to " + idchannel.getGuild().getName() + " have some " + file.getName().replaceAll("_", "") + " on the house!").addFile(image).queue();
                if (botConfig.contains(guild.getId() + ".Welcome-Mention-Role")) {
                    idchannel.sendMessage("Welcome " + message.getMember().getAsMention() + " to " + guild.getName() + " have some " + file.getName().replaceAll("_", " ") + " on the house! \n" + role.getAsMention() + " make our new friend comfortable!").addFile(image).queue();
                } else {
                    idchannel.sendMessage("Welcome " + message.getMember().getAsMention() + " to " + guild.getName() + " have some " + file.getName().replaceAll("_", " ") + " on the house!").addFile(image).queue();
                }
            } catch (NumberFormatException e) {
                message.getChannel().sendMessage("Welcome Channel not set!").queue();
            }
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}