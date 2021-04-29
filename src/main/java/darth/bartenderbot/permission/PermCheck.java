package darth.bartenderbot.permission;

import darth.bartenderbot.config.ConfigManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.IOException;
import java.util.List;

public class PermCheck {
    boolean perms = false;
    public boolean CheckGuildRole(Guild guild, Member member) throws InvalidConfigurationException, IOException {
        List<Role> roles = member.getRoles();
        YamlFile botConfig = new ConfigManager().accessConfig();
        if (botConfig.getString("Perms.Global").contains(member.getUser().getId()) || member.getUser().getId().equals(guild.getOwnerId())) {
            perms = true;
        } else {
            for (Role r : roles) {
                try {
                    if (botConfig.getList("Perms.Guilds." + guild.getId()).contains(r.getId())) {
                        perms = true;
                        System.out.println("Bartender role perm");
                    }
                } catch (NullPointerException e) {
                    perms = false;
                }
            }
        }
        return perms;
    }
}
