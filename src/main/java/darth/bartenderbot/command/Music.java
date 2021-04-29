package darth.bartenderbot.command;

import darth.bartenderbot.utils.Discord.EmbedWrapper;
import darth.bartenderbot.utils.MusicPlayer.PlayerManager;
import net.dv8tion.jda.api.entities.*;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.IOException;

public class Music {
    public void ListQueue(Guild guild, Message message) throws InvalidConfigurationException, IOException {
        PlayerManager manager = PlayerManager.getInstance();
        try {
            //SortedMap queue = manager.getGuildMusicManager(guild).scheduler.listqueue();
            StringBuilder sb = new StringBuilder();
            //for (int i = 0; queue.size() > i; i++) {
             //       int pos = i + 1;
             //       System.out.println(i);
             //       sb.append(pos+") "+queue.get(i) + "\n\n");
             //   }
            message.getChannel().sendMessage(new EmbedWrapper().EmbedMessage(manager.getGuildMusicManager(guild).player.getPlayingTrack().getInfo().title, guild.getSelfMember().getNickname(), null, new EmbedWrapper().GetGuildEmbedColor(guild), sb.toString(), null, null, guild.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
        } catch (NullPointerException e) {
            message.getChannel().sendMessage(new EmbedWrapper().EmbedMessage(guild.getJDA().getSelfUser().getName(), null, null, new EmbedWrapper().GetGuildEmbedColor(guild), "The queue is empty!", null, null, guild.getJDA().getSelfUser().getEffectiveAvatarUrl(), null)).queue();
        //} catch (InterruptedException e) {
         //   e.printStackTrace();
        }
    }
}
