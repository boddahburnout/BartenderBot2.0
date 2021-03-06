package darth.bartenderbot.command.user.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.utils.Discord.EmbedWrapper;
import darth.leaflyapi.LeaflySearch;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class GrowInfo extends Command {
    public GrowInfo() {
        this.name = "growi";
        this.category = new BotCategories().UserCat();
        this.help = "Find grow info on strains";
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
        LeaflySearch leaflySearch = new LeaflySearch();
        Message message = event.getMessage();
        JSONObject strain;
        try {
            strain = leaflySearch.fechLeafly(event.getArgs());
            Map<String, Object> growInfo = leaflySearch.getGrowInfo(strain);
            StringBuilder sb = new StringBuilder();
            for (Object o : growInfo.keySet()) {
                if (!o.toString().trim().equals("growNotesPlain")) {
                    if (growInfo.get(o) != null) {
                        sb.append(o + ": " + growInfo.get(o) + "\n\n");
                    }
                }
            }
            Color color = Color.BLUE;
            try {
                Guild guild = event.getGuild();
                color = new EmbedWrapper().GetGuildEmbedColor(guild);
            } catch (InvalidConfigurationException | IOException | IllegalStateException e) {

            }
            String thumb;
            if (leaflySearch.getNugImage(strain) == null) {
                if (leaflySearch.getSymbol(strain) != null) {
                    thumb = strain.get("flowerImagePng").toString();
                } else {
                    thumb = null;
                }
            } else {
                thumb = leaflySearch.getNugImage(strain).toString();
            }
            message.getChannel().sendMessage(new EmbedWrapper().EmbedMessage(leaflySearch.getStrainName(strain).toString() + "grow info", "", "", color,  growInfo.get("growNotesPlain")+"\n\n"+sb.toString(), null, null, thumb, null)).queue();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}
