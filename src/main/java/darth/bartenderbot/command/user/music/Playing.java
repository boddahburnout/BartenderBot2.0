package darth.bartenderbot.command.user.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.utils.Discord.EmbedWrapper;
import darth.bartenderbot.utils.MusicPlayer.PlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.IOException;

public class Playing extends Command {

    public Playing() {
        this.name = "playing";
        this.help = "See what's playing right now";
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
        MessageChannel channel = event.getChannel();
        PlayerManager manager = PlayerManager.getInstance();
        try {
            Double position = (double) manager.getGuildMusicManager(guild).player.getPlayingTrack().getPosition()/60000;
            Double duration = (double) manager.getGuildMusicManager(guild).player.getPlayingTrack().getDuration()/60000;
            channel.sendMessage(new EmbedWrapper().EmbedMessage(guild.getJDA().getSelfUser().getName(), null, null, new EmbedWrapper().GetGuildEmbedColor(guild), "The song playing is " + manager.getGuildMusicManager(guild).player.getPlayingTrack().getInfo().title + " (" + Math.round(position * 100.0) / 100.0 +"/"+ Math.round(duration * 100.0) / 100.0 +")", null, null, guild.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
            return;
        } catch (NullPointerException e) {
            try {
                channel.sendMessage(new EmbedWrapper().EmbedMessage(guild.getJDA().getSelfUser().getName(), null, null, new EmbedWrapper().GetGuildEmbedColor(guild), "Nothing is playing!", null, null, guild.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
            } catch (InvalidConfigurationException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return;
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
