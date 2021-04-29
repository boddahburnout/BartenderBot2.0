package darth.bartenderbot.command.user.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.utils.Discord.EmbedWrapper;
import darth.bartenderbot.utils.MusicPlayer.PlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.IOException;
import java.util.Objects;

public class Skip extends Command {

    public Skip() {
        this.name = "skip";
        this.help = "ask the bot to skip the song currently playing";
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
        Member member = event.getMember();
        try {
            if (!Objects.requireNonNull(member.getVoiceState()).inVoiceChannel()) {
                channel.sendMessage(new EmbedWrapper().EmbedMessage(guild.getJDA().getSelfUser().getName(), null, null, new EmbedWrapper().GetGuildEmbedColor(guild), "Join the voice channel first!", null, null, guild.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
            }
            if (member.getVoiceState().isDeafened()) {
                channel.sendMessage(new EmbedWrapper().EmbedMessage(guild.getJDA().getSelfUser().getName(), null, null, new EmbedWrapper().GetGuildEmbedColor(guild), "Your not even listening your opinion does not matter", null, null, guild.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
            }
            if (!Objects.requireNonNull(guild.getSelfMember().getVoiceState()).inVoiceChannel()) {
                channel.sendMessage(new EmbedWrapper().EmbedMessage(guild.getJDA().getSelfUser().getName(), null, null, new EmbedWrapper().GetGuildEmbedColor(guild), "Ask me to join the voice channel first!", null, null, guild.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
            }
            PlayerManager manager = PlayerManager.getInstance();
            if (manager.getGuildMusicManager(guild).player.getPlayingTrack() == null) {
                channel.sendMessage(new EmbedWrapper().EmbedMessage(guild.getJDA().getSelfUser().getName(), null, null, new EmbedWrapper().GetGuildEmbedColor(guild), "We are cutting you off for tonight, nothing is even playing.", null, null, guild.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
            }
            channel.sendMessage(new EmbedWrapper().EmbedMessage(guild.getJDA().getSelfUser().getName(), null, null, new EmbedWrapper().GetGuildEmbedColor(guild), manager.getGuildMusicManager(guild).player.getPlayingTrack().getInfo().title + " has been skipped!", null, null, guild.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
            PlayerManager.getInstance().getGuildMusicManager(guild).scheduler.nextTrack();
        } catch (InvalidConfigurationException  | IOException e) {
            e.printStackTrace();
        }
    }
}
