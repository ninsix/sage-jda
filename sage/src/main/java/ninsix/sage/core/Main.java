package ninsix.sage.core;

import ninsix.sage.listener.CommandEvents;
import ninsix.sage.listener.ReadyEvents;
import java.util.Collections;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import static ninsix.sage.core.DataLoader.OS_ARCH;
import static ninsix.sage.core.DataLoader.OS_NAME;
import ninsix.sage.core.IO.input;
import ninsix.sage.core.IO.output;

public class Main {

    public static final String NAME = "Sage";
    public static final String VERSION = "0.3.1";

    public static void main(String[] args) {
        final String token = getToken(args);
        output.success("Bot token obtained");
        startBot(token);
    }

    private static void startBot(final String token) {
        output.log("Starting bot " + NAME + "...");
        DataLoader.loadPages(DataLoader.MANUAL);

        JDA jda = JDABuilder.createLight(token, Collections.emptyList())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new ReadyEvents(), new CommandEvents())
                .setActivity(Activity.playing(OS_NAME + " " + OS_ARCH))
                .build();

        jda.updateCommands().addCommands(
                Commands.slash("manual", "Get help from the manual")
                        .addOption(OptionType.STRING, "page", "Pages", true, true),
                Commands.slash("info", String.format("Get information of bot [%s]", NAME)),
                Commands.slash("reload", "Reload man pages"),
                Commands.slash("ping", "Calculate bot ping"),
                Commands.slash("convert", "Convert to other format")
                        .addOption(OptionType.STRING, "conversion", "Conversion type", true, true)
                        .addOption(OptionType.STRING, "string", "String to convert", true)
        ).queue();
    }

    private static String getToken(String[] args) {
        output.log("Trying to get token");

        // Get from argument 1
        if (args.length >= 1) {
            output.log("Got token from argument 1");
            return args[0];
        }

        // Get from environment variable
        String tokenv = "DISCORD_BOT_TOKEN";
        if (System.getenv(tokenv) != null) {
            output.log("Got from " + tokenv + " env");
            return System.getenv(tokenv);
        }

        // Get from user input
        String token = input.str("Enter bot token for " + NAME);
        if (token != null) {
            output.log("Got token from console input");
            return token;
        }

        // If none of above options are available
        output.error("Specify the token as an argument or as an environment variable " + tokenv);
        System.exit(1);
        return null;
    }
}
