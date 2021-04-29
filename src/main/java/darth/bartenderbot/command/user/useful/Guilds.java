package darth.bartenderbot.command.user.useful;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.doc.standard.CommandInfo;
import darth.bartenderbot.utils.Discord.EmbedWrapper;
import darth.bartenderbot.utils.String.StringUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


@CommandInfo(
        name = {"guilds", "servers"},
        description = "See a list of guilds actively using the bot"
)

public class Guilds extends Command {

    public Guilds() {
        this.name = "guilds";
        this.aliases = new String[]{"servers"};
        this.category = new Category("User");
        this.guildOnly = false;
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
        Message message = event.getMessage();
        JDA jda = event.getJDA();
        ArrayList<String> id = new ArrayList<>();
        int servers = 0;
        for (Guild guilds : jda.getGuilds()) {
            id.add(guilds.getName());
            servers = servers + 1;
        }
        String msg = new StringUtils().ListToString(id);
        Color color = Color.BLUE;
        try {
            color = new EmbedWrapper().GetGuildEmbedColor(event.getGuild());
        } catch (IllegalStateException e) {

        } catch (IOException | InvalidConfigurationException e) {

        }
        MessageEmbed embed = new EmbedWrapper().EmbedMessage("I'm serving users in " + servers + " guilds", "Bartender Bot", null, color, msg, null, null, null, null);
        message.getChannel().sendMessage(embed).queue();
    }

}
