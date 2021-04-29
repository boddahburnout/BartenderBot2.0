package darth.bartenderbot.command.user.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.utils.Discord.EmbedWrapper;
import darth.bartenderbot.utils.MusicPlayer.PlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.IOException;

public class Leave extends Command {

    public Leave() {
        this.name = "leave";
        this.help = "ask the bot to leave the vc";
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
        // Gets the channel in which the bot is currently connected.
        VoiceChannel connectedChannel = guild.getSelfMember().getVoiceState().getChannel();
        // Checks if the bot is connected to a voice channel.
        if (connectedChannel == null) {
            // Get slightly fed up at the user.
            try {
                channel.sendMessage(new EmbedWrapper().EmbedMessage(guild.getJDA().getSelfUser().getName(), null, null, new EmbedWrapper().GetGuildEmbedColor(guild), "I am not connected to a voice channel!", null, null, guild.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        // Disconnect from the channel.
        guild.getAudioManager().closeAudioConnection();
        // Notify the user.
        PlayerManager manager = PlayerManager.getInstance();
        manager.getGuildMusicManager(guild).player.destroy();
        try {
            channel.sendMessage(new EmbedWrapper().EmbedMessage(guild.getJDA().getSelfUser().getName(), null, null, new EmbedWrapper().GetGuildEmbedColor(guild), "Disconnected from the voice channel!", null, null, guild.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }
}
