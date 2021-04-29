package darth.bartenderbot.command.user.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.utils.MusicPlayer.PlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.IOException;

public class UpNext extends Command {

    public UpNext() {
        this.name = "upnext";
        this.help = "see the next song coming up";
        this.category = new BotCategories().MusicCat();
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
        TextChannel channel = event.getTextChannel();
        try {
            new PlayerManager().upNext(guild, channel);
        } catch (InterruptedException | InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
        return;
    }
}
