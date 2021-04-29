package darth.bartenderbot.command.admin.tools;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.permission.PermCheck;
import darth.bartenderbot.utils.Discord.EmbedWrapper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.awt.*;
import java.io.IOException;

public class Shutdown extends Command {

    public Shutdown() {
        this.name = "shutdown";
        this.help = "kill an instance of the bot";
        this.ownerCommand = true;
        this.category = new BotCategories().OwnerCat();
        this.guildOnly =  false;
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
        JDA jda = event.getJDA();
        Guild guild = event.getGuild();
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
        Color color = Color.BLUE;
        try {
            color = new EmbedWrapper().GetGuildEmbedColor(event.getGuild());
        } catch (IllegalStateException e) {

        } catch (IOException | InvalidConfigurationException e) {

        }
        channel.sendMessage(new EmbedWrapper().EmbedMessage("Shutdown", jda.getSelfUser().getName(), null, color, "Bot is terminating", null, null, jda.getSelfUser().getEffectiveAvatarUrl(),null)).queue();
        jda.shutdown();
        System.out.println("Shutdown completed!");
    }
}
