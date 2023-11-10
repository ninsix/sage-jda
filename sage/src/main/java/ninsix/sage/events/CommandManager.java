package ninsix.sage.events;

import static ninsix.sage.core.Converter.format;
import ninsix.sage.core.DataLoader;
import static ninsix.sage.core.DataLoader.JAVA_VENDOR;
import static ninsix.sage.core.DataLoader.JAVA_VERSION;
import static ninsix.sage.core.DataLoader.OS_ARCH;
import static ninsix.sage.core.DataLoader.OS_NAME;
import static ninsix.sage.core.Main.NAME;
import static ninsix.sage.core.Main.VERSION;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

public class CommandManager extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        // make sure we handle the right command
        EmbedBuilder embed = new EmbedBuilder();

        switch (event.getName()) {

            case "ping" -> {
                System.out.println("[  slash  ] Command used: ping");
                long time = System.currentTimeMillis();
                event.reply("Pong!").setEphemeral(false) // reply or acknowledge
                        .flatMap(v
                                -> event.getHook().editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time) // then edit original
                        ).queue(); // Queue both reply and edit
            }

            case "man" -> {
                System.out.println("[  slash  ] Command used: man");
                String page = event.getOption("page", OptionMapping::getAsString);
                embed.setDescription(DataLoader.pages.get(page));
                event.replyEmbeds(embed.build()).queue();
            }
            
            case "info" -> {
                System.out.println("[  slash  ] Command used: info");
                embed.setAuthor("Ninsix");
                
                embed.addField("Bot details", 
                        "Bot Name: "+NAME
                        + "\nBot Version: "+VERSION
                        , true);
                embed.addField("Runtime details", 
                        "Java version: "+JAVA_VERSION
                        + "\nJava vendor: "+ JAVA_VENDOR
                        , true);
                embed.addField("System details", 
                        "\nOperating System: "+ OS_NAME
                        + "\nOS Architecture: "+ OS_ARCH
                        , true);
                event.replyEmbeds(embed.build()).queue();
            }
            case "reload" -> {
                System.out.println("[  slash  ] Command used: reload");
                embed.setDescription("### Loaded file:\n " + DataLoader.MANUAL);
                DataLoader.load_pages(DataLoader.MANUAL);
                event.replyEmbeds(embed.build()).setEphemeral(true).queue();
            }
            default -> {
                System.err.println("[  error  ] Command used: Unknown");
                event.reply(format("Unknown command"))
                        .setEphemeral(false) // reply or acknowledge
                        .queue(); // Queue both reply and edit
            }
        }
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        if (event.getName().equals("man") && event.getFocusedOption().getName().equals("page")) {
            List<Command.Choice> options = Stream.of(DataLoader.page_key)
                    .filter(word -> word.startsWith(event.getFocusedOption().getValue())) // only display words that start with the user's current input
                    .map(word -> new Command.Choice(word, word)) // map the words to choices
                    .collect(Collectors.toList());
            event.replyChoices(options).queue();
        }
    }
}
