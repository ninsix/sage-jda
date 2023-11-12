package ninsix.sage.listener;

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
import ninsix.sage.core.IO.output;
import org.jetbrains.annotations.NotNull;
import ninsix.sage.core.Converter;
import static ninsix.sage.core.DataLoader.manualPages;

public class CommandEvents extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        // make sure we handle the right command
        EmbedBuilder embed = new EmbedBuilder();

        switch (event.getName()) {

            case "ping" -> {
                output.log("Command used: ping");
                long time = System.currentTimeMillis();
                event.reply("Pong!").setEphemeral(false) // reply or acknowledge
                        .flatMap(v
                                -> event.getHook().editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time) // then edit original
                        ).queue(); // Queue both reply and edit
            }

            case "manual" -> {
                output.log("Command used: manual");
                String page = event.getOption("page", OptionMapping::getAsString);
                embed.setDescription(DataLoader.manualPages.get(page));
                event.replyEmbeds(embed.build()).queue();
            }

            case "info" -> {
                output.log("Command used: info");
                embed.setAuthor("Ninsix");

                embed.addField("Bot details",
                        "Bot Name: " + NAME
                        + "\nBot Version: " + VERSION,
                        true);
                embed.addField("Runtime details",
                        "Java version: " + JAVA_VERSION
                        + "\nJava vendor: " + JAVA_VENDOR,
                        true);
                embed.addField("System details",
                        "\nOperating System: " + OS_NAME
                        + "\nOS Architecture: " + OS_ARCH,
                        true);
                event.replyEmbeds(embed.build()).queue();
            }
            case "reload" -> {
                output.log("Command used: reload");
                embed.setDescription("### Loaded file:\n " + DataLoader.MANUAL);
                DataLoader.loadPages(DataLoader.MANUAL);
                event.replyEmbeds(embed.build()).setEphemeral(true).queue();
            }
            case "convert" -> {
                output.log("Command used: convert");
                String conversion = event.getOption("conversion", OptionMapping::getAsString);
                String convertext = event.getOption("string", OptionMapping::getAsString);
                switch (conversion) {
                    case "Text to binary" -> convertext = Converter.binary.from(convertext);
                    case "Binary to text" -> convertext = Converter.binary.toText(convertext);
                    case "Text to SbB" -> convertext = Converter.bBsecret.from(convertext);
                    case "SbB to text" -> convertext = Converter.bBsecret.toText(convertext);
                    default -> convertext = "Invalid option";
                }
                event.reply("```"+convertext+"```").queue();
            }
            default -> {
                output.error("Command used: unknown");
                event.reply("Error: Unknown command")
                        .setEphemeral(true) // reply or acknowledge
                        .queue(); // Queue both reply and edit
            }
        }
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        Stream<String> optionStream;

        switch (event.getName()) {
            
            case "manual" -> {
                switch (event.getFocusedOption().getName()) {
                    case "page" -> {
                        optionStream = Stream.of(manualPages.keySet().toArray(new String[manualPages.size()]));
                    }
                    default -> {
                        return;
                    }
                }
            }
            
            case "convert" -> {
                switch (event.getFocusedOption().getName()) {
                    case "conversion" -> {
                        String[] options = {"Text to binary", "Binary to text", "Text to SbB", "SbB to text"};
                        optionStream = Stream.of(options);
                    }
                    default -> {
                        return;
                    }
                }
            }
            default -> {
                return;
            }
        }

        List<Command.Choice> options = optionStream.filter(word -> word.startsWith(event.getFocusedOption().getValue())) // only display words that start with the user's current input
                .map(word -> new Command.Choice(word, word)) // map the words to choices
                .collect(Collectors.toList());
        event.replyChoices(options).queue();
    }
}
