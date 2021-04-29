package darth.bartenderbot.command.user.useful;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.utils.Discord.EmbedWrapper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.awt.*;
import java.io.IOException;

public class Invite  extends Command {

    public Invite() {
        this.help = "Get an invite link to add the bot to your guild";
        this.category = new Category("User");
        this.name = "invite";
        this.guildOnly = false;
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
        MessageChannel channel = event.getChannel();

        Color color = Color.BLUE;
            try {
                color = new EmbedWrapper().GetGuildEmbedColor(event.getGuild());
            } catch (IllegalStateException e) {

            } catch (IOException | InvalidConfigurationException e) {

            }
        channel.sendMessage(new EmbedWrapper().EmbedMessage("Invite Link", jda.getSelfUser().getName(), null, color, "You can invite me with \n"+jda.getInviteUrl(), null, null, jda.getSelfUser().getEffectiveAvatarUrl(), null)).queue();
    }
}
