package darth.bartenderbot.main;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import darth.bartenderbot.command.admin.Emote.AddChatEmote;
import darth.bartenderbot.command.admin.Emote.AddEmote;
import darth.bartenderbot.command.admin.Emote.AddEmoteCat;
import darth.bartenderbot.command.admin.Emote.PreviewEmote;
import darth.bartenderbot.command.admin.Emote.RenameECat;
import darth.bartenderbot.command.admin.drink.*;
import darth.bartenderbot.command.admin.tools.Ban;
import darth.bartenderbot.command.admin.tools.SetRGB;
import darth.bartenderbot.command.admin.tools.Setbr;
import darth.bartenderbot.command.admin.tools.Shutdown;
import darth.bartenderbot.command.admin.tools.TestJoin;
import darth.bartenderbot.command.admin.tools.Unban;
import darth.bartenderbot.command.user.fun.Drinks;
import darth.bartenderbot.command.user.fun.Echo;
import darth.bartenderbot.command.user.fun.Mtg;
import darth.bartenderbot.command.user.fun.Serve;
import darth.bartenderbot.command.user.fun.Strain;
import darth.bartenderbot.command.user.music.Join;
import darth.bartenderbot.command.user.music.Leave;
import darth.bartenderbot.command.user.music.Pause;
import darth.bartenderbot.command.user.music.Play;
import darth.bartenderbot.command.user.music.Playing;
import darth.bartenderbot.command.user.music.Skip;
import darth.bartenderbot.command.user.music.UpNext;
import darth.bartenderbot.command.user.useful.Guilds;
import darth.bartenderbot.command.user.useful.Invite;
import darth.bartenderbot.command.user.useful.Ping;
import darth.bartenderbot.command.user.useful.Status;
import darth.bartenderbot.config.ConfigManager;
import darth.bartenderbot.utils.FS.FileUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class DiscordBot {
    public static void main(String[] args) throws IOException, InvalidConfigurationException {
        //Check if token was provided

        CommandClientBuilder clientBuilder = new CommandClientBuilder();

        clientBuilder.setActivity(Activity.watching("for orders"));

        clientBuilder.setPrefix("~");

        clientBuilder.setOwnerId("292484423658766346");

        clientBuilder.addCommands(
                new Invite(),
                new Ping(),
                new Guilds(),
                new Mtg(),
                new Status(),
                new Strain(),
                new Echo(),
                new Drinks(),
                new PreviewEmote(),
                new Serve(),
                new ListDrinkImages(),

                new Join(),
                new Leave(),
                new Playing(),
                new Play(),
                new Pause(),
                new Skip(),
                new UpNext(),

                new TestJoin(),
                new Ban(),
                new Unban(),
                new AddEmote(),
                new SetRGB(),
                new Setbr(),
                new SetWelcome(),
                new SetWelcomeR(),
                new AddEmoteCat(),
                new RenameECat(),
                new AddDrink(),
                new RemoveDrinkC(),
                new RenameDrinkC(),
                new AddChatEmote(),
                new AddDrinkC(),

                new Shutdown()
        );

        new ConfigManager().setConfig();
        //Access the config
        YamlFile Config = new ConfigManager().accessConfig();
        //Call fs setup
        new FileUtils().fsCheck();
        //Sets token to a string
        String token = Config.getString("Global.Bot-Token");
        //Builds the bot and logs in
        try {
            JDA jda = JDABuilder.createDefault(token)
                    .addEventListeners(clientBuilder.build())
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .build();
            System.out.println(jda.getSelfUser().getName() + " Logged in!");
        } catch (LoginException e) {
            System.out.println("Bot token is either wrong, missing or just not set up. Please edit your config.yml before continuing to launch the bot");
        }
    }
}
