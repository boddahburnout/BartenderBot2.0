package darth.bartenderbot.event;

import darth.bartenderbot.Games.BlackJack;
import darth.bartenderbot.command.LeaflyCmd;
import darth.bartenderbot.handler.*;
import darth.bartenderbot.utils.FS.FileUtils;
import darth.bartenderbot.utils.Image.ImageUtils;
import darth.bartenderbot.utils.MusicPlayer.PlayerManager;
import darth.bartenderbot.utils.String.RandomPhrase;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import org.json.simple.parser.ParseException;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Events extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        //Handle messages here
        Guild guild = event.getGuild();
        Message message = event.getMessage();

        //System.out.println(new RandomPhrase().getRandomJoinPhrase(event.getAuthor()));
//        new GuildJoinHandler().appendGuild(guild);
            if (message.isMentioned(event.getJDA().getSelfUser())) {
                new MentionedHandler().sendprefix(guild, message);
            }

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
        } catch (InvalidConfigurationException | IOException | ParseException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void onGuildJoin(GuildJoinEvent event) {
//        new GuildJoinHandler().appendGuild(event.getGuild());
//    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        new BlackJack().reactHandler(event.getGuild(), event);
    }

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        if (Objects.requireNonNull(event.getGuild().getSelfMember().getVoiceState()).inVoiceChannel()) {
            VoiceChannel vc = event.getGuild().getSelfMember().getVoiceState().getChannel();
            assert vc != null;
            if (vc.getMembers().size() == 1) {
                event.getGuild().getAudioManager().closeAudioConnection();
                PlayerManager manager = PlayerManager.getInstance();
                manager.getGuildMusicManager(event.getGuild()).player.destroy();
            }
        }
    }
}