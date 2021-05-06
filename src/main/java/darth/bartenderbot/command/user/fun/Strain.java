package darth.bartenderbot.command.user.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.utils.Discord.EmbedWrapper;
import darth.bartenderbot.utils.String.StringUtils;
import darth.leaflyapi.LeaflySearch;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.awt.*;
import java.io.IOException;

public class Strain extends Command {

    public Strain() {
        this.name = "strain";
        this.category = new BotCategories().UserCat();
        this.help = "Look up an overview on a strains data";
        this.arguments = "<Strain>";
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
        MessageChannel channel = event.getChannel();
        String message = event.getArgs();
        LeaflySearch leaflyApi = new LeaflySearch();
        if (message.length() == 0) {
            channel.sendMessage("Please provide a strain").queue();
            return;
        }
        Color color = Color.BLUE;
        try {
             color = new EmbedWrapper().GetGuildEmbedColor(event.getGuild());
        } catch (InvalidConfigurationException | IOException e) {

        }
        try {
            JSONObject strain = leaflyApi.fechLeafly(message);
            try {
            channel.sendMessage(new EmbedWrapper().EmbedMessage(leaflyApi.getStrainName(strain).toString(), null, "", color, new StringUtils().FormatWeedData(strain), null, null, leaflyApi.getNugImage(strain).toString(), null)).queue();
        } catch (NullPointerException e) {
            channel.sendMessage(new EmbedWrapper().EmbedMessage(leaflyApi.getStrainName(strain).toString(), null, "", color, new StringUtils().FormatWeedData(strain), null, null, null, null)).queue();
        }
        } catch (ParseException e) {
            channel.sendMessage("Couldn't locate data!").queue();
            return;
        } catch (NullPointerException | IOException e) {
            channel.sendMessage("Couldn't find strain info").queue();
            e.printStackTrace();
            return;
        }
    }
}