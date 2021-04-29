package darth.bartenderbot.command.user.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.scryfall.api.ScryfallAPI;
import darth.bartenderbot.handler.CommandHandler;
import darth.bartenderbot.utils.Discord.EmbedWrapper;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.IOException;
import java.util.Map;

public class ViewCard extends Command {

    public ViewCard() {
        this.name = "viewcard";
        this.help = "View a full sized mtg card";
        this.arguments = "<Card name>";
        this.cooldown = 1;
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
        Map<String, String> commandData = null;
        try {
            commandData = new CommandHandler().getCommandData(guild, message);
            ScryfallAPI scryfallAPI = new ScryfallAPI();
            JSONObject apiResp = scryfallAPI.scryfallRequest(commandData.get("args"));
            StringBuilder sb = new StringBuilder();
            JSONObject imageUrls = scryfallAPI.getImageUrls(apiResp);
            JSONObject relatedUrls = scryfallAPI.getRelatedUrl(apiResp);
            if (scryfallAPI.getLargeImage(imageUrls) != null) {
                sb.append(imageUrls);
            }
            message.getChannel().sendMessage(new EmbedWrapper().EmbedMessage(scryfallAPI.getCardName(apiResp).toString(), null, scryfallAPI.getGathererUrl(relatedUrls).toString(), new EmbedWrapper().GetGuildEmbedColor(guild), null, null, null, message.getJDA().getSelfUser().getEffectiveAvatarUrl(), scryfallAPI.getLargeImage(imageUrls).toString())).queue();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
