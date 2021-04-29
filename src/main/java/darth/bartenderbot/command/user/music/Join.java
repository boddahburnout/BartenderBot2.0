package darth.bartenderbot.command.user.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.utils.Discord.EmbedWrapper;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.IOException;

public class Join extends Command {

    public Join() {
        this.name = "join";
        this.category = new BotCategories().MusicCat();
        this.help = "Ask the bot to join the voice channel";
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
        if (!guild.getSelfMember().hasPermission(channel, Permission.VOICE_CONNECT)) {
            try {
                channel.sendMessage(new EmbedWrapper().EmbedMessage(guild.getJDA().getSelfUser().getName(), null, null, new EmbedWrapper().GetGuildEmbedColor(guild), "I do not have permissions to join a voice channel!", null, null, guild.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        VoiceChannel connectedChannel = member.getVoiceState().getChannel();
        if (connectedChannel == null) {
            try {
                channel.sendMessage(new EmbedWrapper().EmbedMessage(guild.getJDA().getSelfUser().getName(), null, null, new EmbedWrapper().GetGuildEmbedColor(guild), "You are not connected to a voice channel!", null, null, guild.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        AudioManager audioManager = guild.getAudioManager();
        audioManager.openAudioConnection(connectedChannel);
        // Obviously people do not notice someone/something connecting.
        try {
            channel.sendMessage(new EmbedWrapper().EmbedMessage(guild.getJDA().getSelfUser().getName(), null, null, new EmbedWrapper().GetGuildEmbedColor(guild), "Connected to the voice channel!", null, null, guild.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }
}
