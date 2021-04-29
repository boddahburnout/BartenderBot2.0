package darth.bartenderbot.command.user.useful;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.utils.Discord.EmbedWrapper;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.text.NumberFormat;

public class Status extends Command {

    public Status() {
        this.name = "status";
        this.category = new BotCategories().UserCat();
        this.guildOnly = false;
        this.help = "Get status on the bots server hardware";
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

        Runtime runtime = Runtime.getRuntime();

        NumberFormat format = NumberFormat.getInstance();

        StringBuilder sb = new StringBuilder();
        long duration = ManagementFactory.getRuntimeMXBean().getUptime();
        long processors = runtime.availableProcessors();
        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        sb.append("Uptime: "+ duration/3600000L+" Hours"+"\n\n");
        sb.append("Total Processors: "+ processors+"\n");
        sb.append("max memory: " + format.format(maxMemory / 1073741824) + " GB\n");
        sb.append("total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / 1073741824) + " GB\n\n");
        File root = new File("/");
        if (!root.exists()) root = new File("C:");
        sb.append(String.format("Total space: %.2f GB\n",
                (double) root.getTotalSpace() / 1073741824));
        sb.append(String.format("Usable space: %.2f GB\n\n",
                (double) root.getUsableSpace() / 1073741824));

        Color color = Color.BLUE;
        try {
            color = new EmbedWrapper().GetGuildEmbedColor(event.getGuild());
        } catch (IllegalStateException e) {

        } catch (IOException | InvalidConfigurationException e) {

        }

        MessageEmbed embed = new EmbedWrapper().EmbedMessage("Performance",null ,null, color, sb.toString(), null, null, channel.getJDA().getSelfUser().getEffectiveAvatarUrl(), null);
        channel.sendMessage(embed).queue();
    }
}
