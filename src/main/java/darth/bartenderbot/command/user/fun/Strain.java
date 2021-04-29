package darth.bartenderbot.command.user.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.utils.Discord.EmbedWrapper;
import darth.leaflyapi.LeaflySearch;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.util.Map;

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
        String message = event.getMessage().toString();
        LeaflySearch leaflyApi = new LeaflySearch();
        if (message.length() == 0) {
            channel.sendMessage("Please provide a strain").queue();
            return;
        }
        try {
            JSONObject strain = leaflyApi.fechLeafly(message);
            Object[] PopularIn = leaflyApi.getPopularIn(strain);
            Object[] SimilarStrains = leaflyApi.getSimilarStrains(strain);
            Map<Object, Object> Cannabinoids = leaflyApi.getCannabinoids(strain);
            int rating;
            try {
                rating = Double.valueOf((Double) leaflyApi.getAverageRating(strain)).intValue();
            } catch (ClassCastException e) {
                rating = 0;
            }
            String weedmessage = leaflyApi.getDescriptionPlain(strain) + "\n\nPopular in: " + PopularIn[0] + "\n" + "\nCategory: " + leaflyApi.getCategory(strain) + "\n\nChemotype: " + leaflyApi.getChemotype(strain) + "\n\nTop Effects: " + leaflyApi.getTopEffect(strain) + "\n\nRating: " + rating;
            StringBuilder stringBuilder1 = new StringBuilder(weedmessage);
            stringBuilder1.append("\n\nCBD: " + Double.valueOf(Cannabinoids.get("CBD").toString()).intValue() + "%");
            stringBuilder1.append("\nTHC: " + Double.valueOf(Cannabinoids.get("THC").toString()).intValue() + "%");
            stringBuilder1.append("\n\nSimilar strains: \n");
            for (int i = 0; i < SimilarStrains.length; i++) {
                stringBuilder1.append(SimilarStrains[i] + "\n");
            }
            String strainmsg = stringBuilder1.toString().replaceAll("null", "0");
            try {
                channel.sendMessage(new EmbedWrapper().EmbedMessage(leaflyApi.getStrainName(strain).toString(), null, "", Color.BLUE, strainmsg, null, null, leaflyApi.getNugImage(strain).toString(), null)).queue();
            } catch (NullPointerException e) {
                channel.sendMessage(new EmbedWrapper().EmbedMessage(leaflyApi.getStrainName(strain).toString(), null, "", Color.BLUE, strainmsg, null, null, null, null)).queue();
            }
        } catch (ParseException e) {
            channel.sendMessage("Couldn't locate data!").queue();
            return;
        } catch (NullPointerException | IOException e) {
            channel.sendMessage("Couldn't find strain info").queue();
            return;
        }
        return;
    }
}
