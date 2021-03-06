package darth.bartenderbot.command.user.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.scryfall.api.ScryfallAPI;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.handler.CommandHandler;
import darth.bartenderbot.utils.Discord.EmbedWrapper;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class Mtg extends Command {

    public Mtg() {
        this.guildOnly = false;
        this.help = "Look up a Magic the Gathering Card";
        this.name = "mtg";
        this.category = new BotCategories().UserCat();
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
        Message message = event.getMessage();
        try {
            ScryfallAPI scryfallAPI = new ScryfallAPI();
            JSONObject apiResp;
            apiResp = scryfallAPI.scryfallRequest(event.getArgs());
            JSONObject images = scryfallAPI.getImageUrls(apiResp);
            JSONObject prices = scryfallAPI.getPrices(apiResp);
            JSONObject legal = scryfallAPI.getLegalities(apiResp);
            StringBuilder sb = new StringBuilder();

            if (scryfallAPI.getSet(apiResp) != null) {
                sb.append("Set: " + scryfallAPI.getSetName(apiResp) + "\n");
            }

            if (scryfallAPI.getUsd(prices) != null) {
                sb.append("Price (USD): " + scryfallAPI.getUsd(prices) + "\n");
            }
            if (scryfallAPI.getUsdFoil(prices) != null) {
                sb.append("Foil Price (USD): " + scryfallAPI.getUsdFoil(prices) + "\n");
            }

            if (scryfallAPI.getStandard(legal) != null) {
                sb.append("Standard: " + scryfallAPI.getStandard(legal).toString().replaceAll("_", " ") + "\n");
            }

            if (scryfallAPI.getModern(legal) != null) {
                sb.append("Modern: " + scryfallAPI.getModern(legal).toString().replaceAll("_", " ") + "\n");
            }

            if (scryfallAPI.getHistoric(legal) != null) {
                sb.append("Historic: " + scryfallAPI.getHistoric(legal).toString().replaceAll("_", " ") + "\n");
            }

            Color color = Color.BLUE;
            try {
                color = new EmbedWrapper().GetGuildEmbedColor(event.getGuild());
            } catch (InvalidConfigurationException | IOException | IllegalStateException e) {

            }

            message.getChannel().sendMessage(new EmbedWrapper().EmbedMessage(scryfallAPI.getCardName(apiResp).toString(), null, scryfallAPI.getGathererUrl(scryfallAPI.getRelatedUrl(apiResp)).toString(), color, sb.toString(), null, null, message.getJDA().getSelfUser().getEffectiveAvatarUrl(), scryfallAPI.getSmallImage(images).toString())).queue();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}