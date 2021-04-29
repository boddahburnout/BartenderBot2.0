package darth.bartenderbot.utils.Discord;

import darth.bartenderbot.config.ConfigManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmbedWrapper {
    EmbedBuilder eb = new EmbedBuilder();
    public MessageEmbed EmbedMessage(String title, String Author, String url, Color color, String message, String autorurl, String imageurl, String thumb, String image) {
        eb.setTitle(title, url);
        eb.setColor(color);
        eb.setDescription(message);
        eb.setAuthor(Author, autorurl, imageurl);
        eb.setThumbnail(thumb);
        eb.setImage(image);
        return eb.build();
    }
    public Color GetGuildEmbedColor(Guild guild) throws InvalidConfigurationException, IOException {
        YamlFile botConfig = new ConfigManager().accessConfig();
        java.util.List<String> RGB = (List<String>) botConfig.getList(guild.getId() + ".Embed-rgb");
        if (!(RGB == null)) {
            Color embedcolor = new Color(Integer.parseInt(RGB.get(0)), Integer.parseInt(RGB.get(1)), Integer.parseInt(RGB.get(2)));
            return embedcolor;
        } else {
            RGB = new ArrayList<>();
            RGB.add("50");
            RGB.add("86");
            RGB.add("168");
            Color embedcolor = new Color(Integer.parseInt(RGB.get(0)), Integer.parseInt(RGB.get(1)), Integer.parseInt(RGB.get(2)));
            return embedcolor;
        }
    }
}