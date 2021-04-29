package darth.bartenderbot.utils.MusicPlayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import darth.bartenderbot.utils.Discord.EmbedWrapper;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    private static PlayerManager INSTANCE;
    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;
    AudioPlaylist audioPlaylist;

    public PlayerManager() {
        this.musicManagers = new HashMap<>();

        this.playerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    public synchronized GuildMusicManager getGuildMusicManager(Guild guild) {
        long guildId = guild.getIdLong();
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public void loadAndPlay(TextChannel channel, String trackUrl) {
        GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());
        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                if (channel != null) {
                    try {
                        channel.sendMessage(new EmbedWrapper().EmbedMessage(channel.getJDA().getSelfUser().getName(), null, null, new EmbedWrapper().GetGuildEmbedColor(channel.getGuild()),  "Adding to queue " + track.getInfo().title, null, null, channel.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
                    } catch (InvalidConfigurationException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                play(musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {

                audioPlaylist = playlist;

                AudioTrack firstTrack = playlist.getSelectedTrack();


                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }

                try {
                    channel.sendMessage(new EmbedWrapper().EmbedMessage(channel.getJDA().getSelfUser().getName(), null, null, new EmbedWrapper().GetGuildEmbedColor(channel.getGuild()),  "Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")", null, null, channel.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
                } catch (InvalidConfigurationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void noMatches() {
                try {
                    channel.sendMessage(new EmbedWrapper().EmbedMessage(channel.getJDA().getSelfUser().getName(), null, null, new EmbedWrapper().GetGuildEmbedColor(channel.getGuild()),  "Nothing found by " + trackUrl, null, null, channel.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
                } catch (InvalidConfigurationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void loadFailed(FriendlyException exception) {
                try {
                    channel.sendMessage(new EmbedWrapper().EmbedMessage(channel.getJDA().getSelfUser().getName(), null, null, new EmbedWrapper().GetGuildEmbedColor(channel.getGuild()),  "Could not play: " + exception.getMessage(), null, null, channel.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
                } catch (InvalidConfigurationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void upNext(Guild guild,TextChannel channel) throws InvalidConfigurationException, IOException, InterruptedException {
        //new GuildMusicManager(playerManager).scheduler.listqueue();
        //List<AudioTrack> playlist = audioPlaylist.getTracks();
        //StringBuilder sb = new StringBuilder();
        //for (AudioTrack track : playlist) {
        //    sb.append(track.getInfo().title + " " +track.getInfo().author+ " " + track.getDuration()+"\n");
        //}
        //channel.sendMessage(new EmbedWrapper().EmbedMessage("Queue", guild.getSelfMember().getNickname(), null, new EmbedWrapper().GetGuildEmbedColor(guild), sb.toString(), null, null, null, null)).queue();
    }

    private void play(GuildMusicManager musicManager, AudioTrack track) {
        musicManager.scheduler.queue(track);
    }

    public static synchronized PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }
}