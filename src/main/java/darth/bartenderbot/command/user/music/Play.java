package darth.bartenderbot.command.user.music;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.config.ConfigManager;
import darth.bartenderbot.handler.CommandHandler;
import darth.bartenderbot.utils.Discord.EmbedWrapper;
import darth.bartenderbot.utils.MusicPlayer.PlayerManager;
import net.dv8tion.jda.api.entities.*;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Play extends Command {

    public Play() {
        this.name = "play";
        this.help = "ask the bot to play a song in vc";
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
        Message message = event.getMessage();
        Member member = event.getMember();
        try {
            Map<String, String> commandData = new CommandHandler().getCommandData(guild, message);
            YamlFile botConfig = new ConfigManager().accessConfig();
            if (!Objects.requireNonNull(member.getVoiceState()).inVoiceChannel()) {
                message.getChannel().sendMessage(new EmbedWrapper().EmbedMessage(guild.getJDA().getSelfUser().getName(), null, null, new EmbedWrapper().GetGuildEmbedColor(guild), "Join the voice channel first!", null, null, guild.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
                return;
            }
            if (member.getVoiceState().isDeafened()) {
                message.getChannel().sendMessage(new EmbedWrapper().EmbedMessage(guild.getJDA().getSelfUser().getName(), null, null, new EmbedWrapper().GetGuildEmbedColor(guild), "Your not even listening your opinion does not matter", null, null, guild.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
                return;
            }
            if (!Objects.requireNonNull(guild.getSelfMember().getVoiceState()).inVoiceChannel()) {
                message.getChannel().sendMessage(new EmbedWrapper().EmbedMessage(guild.getJDA().getSelfUser().getName(), null, null, new EmbedWrapper().GetGuildEmbedColor(guild), "Ask me to join the voice channel first!", null, null, guild.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
                return;
            }
            PlayerManager manager = PlayerManager.getInstance();
            String token = botConfig.getString("Global.Youtube-Token");
            HttpTransport transport = new NetHttpTransport();
            JsonFactory jsonFactory = new JacksonFactory();
            //YouTube youTube = new YouTube( transport, jsonFactory , null);
            YouTube.Builder youtube = new YouTube.Builder(transport, jsonFactory, null).setApplicationName("Bartender Bot");
            YouTube.Search search1 = youtube.build().search();
            List<String> test = new ArrayList<>();
            test.add("snippet");
            List <SearchResult> results = search1.list(test).setQ(commandData.get("args")).setKey(token).execute().getItems();
            StringBuilder sb = new StringBuilder();
            //for (SearchResult r : results) {
            //    sb.append(r.getSnippet().getTitle()+" "+r.getId().getVideoId()+"\n\n");
            //}
            //message.getChannel().sendMessage(new EmbedWrapper().EmbedMessage("Search Results", null, null, new EmbedWrapper().GetGuildEmbedColor(guild), sb.toString(), null, null, message.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
            manager.loadAndPlay(channel, "https://www.youtube.com/watch?v="+results.get(0).getId().getVideoId());
            manager.getGuildMusicManager(guild).player.setVolume(10);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }
}
