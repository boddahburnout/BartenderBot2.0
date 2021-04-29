package darth.bartenderbot.event;

import darth.bartenderbot.Games.BlackJack;
import darth.bartenderbot.command.*;
import darth.bartenderbot.handler.*;
import darth.bartenderbot.permission.PermCheck;
import darth.bartenderbot.utils.Discord.EmbedWrapper;
import darth.bartenderbot.utils.FS.FileUtils;
import darth.bartenderbot.utils.MusicPlayer.PlayerManager;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.StatusChangeEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.simple.parser.ParseException;

import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Events extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        //Handle messages here
        Guild guild = event.getGuild();
        new GuildJoinHandler().appendGuild(guild);

//            if (message.isMentioned(event.getJDA().getSelfUser())) {
//                new MentionedHandler().sendprefix(guild, message);
//            }

//            try {
//                List<String> emotes = new FileUtils().FileScan(new File("emotes/").listFiles());
//
//                if (new CommandHandler().iscommand(guild, message)) {
//                    Map<String, String> commandData = new CommandHandler().getCommandData(guild, message);
//                    String cmd = commandData.get("name");
//
//                    if (emotes.contains(commandData.get("name"))) {
//                        File image = new FileUtils().GetRandomFile(new File("emotes/" + commandData.get("name")));
//                        List<User> user = event.getMessage().getMentionedUsers();
//                        try {
//                            channel.sendMessage(event.getAuthor().getAsMention() + " gave a " + commandData.get("name") + " to " + user.get(0).getAsMention()).addFile(image).queue();
//                        } catch (IndexOutOfBoundsException e) {
//                            channel.sendMessage("Mention a friend to use emotes!").queue();
//                            return;
//                        }
//                        return;
//                    }
//
    }
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Guild guild = event.getGuild();
        Member member = event.getMember();
        try {
            new GuildMemberJoinHandler().welcomeMember(guild, member);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        new GuildJoinHandler().appendGuild(event.getGuild());
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        new BlackJack().reactHandler(event.getGuild(), event);
    }

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        if (event.getGuild().getSelfMember().getVoiceState().inVoiceChannel()) {
            VoiceChannel vc = event.getGuild().getSelfMember().getVoiceState().getChannel();
            if (vc.getMembers().size() == 1) {
                event.getGuild().getAudioManager().closeAudioConnection();
                PlayerManager manager = PlayerManager.getInstance();
                manager.getGuildMusicManager(event.getGuild()).player.destroy();
            }
        }
    }
    @Override
    public void onStatusChange(StatusChangeEvent event) {

    }
}