package ninsix.sage.core;

import ninsix.sage.events.CommandManager;
import ninsix.sage.events.ReadyListener;
import java.util.Collections;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.Scanner;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import static ninsix.sage.core.DataLoader.OS_ARCH;
import static ninsix.sage.core.DataLoader.OS_NAME;

public class Main {

    public static final String NAME = "Sage";
    public static final String VERSION = "0.2.0";

    public static void main(String[] args) {
        final String token = getToken(args);
        startBot(token);
    }

    private static void startBot(final String token) {
        System.out.println("Starting Sage " + VERSION);
        DataLoader.load_pages(DataLoader.MANUAL);
        
        JDA jda = JDABuilder.createLight(token, Collections.emptyList())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new ReadyListener(), new CommandManager())
                .setActivity(Activity.playing(OS_NAME + " " + OS_ARCH ))
                .build();

        jda.updateCommands().addCommands(
                Commands.slash("man", "The manual")
                        .addOption(OptionType.STRING, "page", "Pages", true, true),
                Commands.slash("info", String.format("Get information of bot [%s]", NAME)),
                Commands.slash("reload", "Reload man pages"),
                Commands.slash("ping", "Calculate bot ping")
        ).queue();
        
        // Add choices for the "page" option based on the keys of the pages_map
    }

    //( Get the bot token from the environment variable
    private static String getToken(String[] args) {
        if (System.getenv("DISCORD_BOT_TOKEN") == null) {
            if (args.length < 1) {
                Scanner input = new Scanner(System.in);
                if (input.hasNextLine()) {
                    System.out.printf("Enter bot [%s] token:%n> ", NAME);
                    return input.nextLine().trim();
                }
                input.close();
            } else {
                return args[0];
            }
        } else {
            return System.getenv("DISCORD_BOT_TOKEN");
        }
        System.err.println("Specify the token as an argument or as an environment variable DISCORD_BOT_TOKEN");
        System.exit(1);
        return null;
    }
}
