package darth.bartenderbot.command.user.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.utils.Discord.EmbedWrapper;
import darth.bartenderbot.utils.MusicPlayer.PlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.IOException;

public class Pause extends Command {

    public Pause() {
        this.name = "pause";
        this.help = "Pause or resume the music";
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
        Message message = event.getMessage();
        try {
            if (!message.getMember().getVoiceState().inVoiceChannel()) {
                message.getChannel().sendMessage(new EmbedWrapper().EmbedMessage(guild.getJDA().getSelfUser().getName(), null, null, new EmbedWrapper().GetGuildEmbedColor(guild), "You aren't even listening smh", null, null, guild.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
                return;
            }
            if (!guild.getSelfMember().getVoiceState().inVoiceChannel()) {
                message.getChannel().sendMessage(new EmbedWrapper().EmbedMessage(guild.getJDA().getSelfUser().getName(), null, null, new EmbedWrapper().GetGuildEmbedColor(guild), "Ask me to join the voice channel first!", null, null, guild.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
                return;
            }
            PlayerManager player = PlayerManager.getInstance();
            AudioPlayer audioPlayer = player.getGuildMusicManager(guild).player;
            if (!audioPlayer.isPaused()) {
                audioPlayer.setPaused(true);
                message.getChannel().sendMessage(new EmbedWrapper().EmbedMessage(guild.getJDA().getSelfUser().getName(), null, null, new EmbedWrapper().GetGuildEmbedColor(guild), "Music has been paused", null, null, guild.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
            } else {
                audioPlayer.setPaused(false);
                message.getChannel().sendMessage(new EmbedWrapper().EmbedMessage(guild.getJDA().getSelfUser().getName(), null, null, new EmbedWrapper().GetGuildEmbedColor(guild), "Music has been resumed", null, null, guild.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
            }
            return;
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
