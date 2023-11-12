package ninsix.sage.listener;

import static ninsix.sage.core.Main.NAME;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import ninsix.sage.core.IO.output;

public class ReadyEvents implements EventListener {

    @Override
    public void onEvent(GenericEvent event) {
        if (event instanceof ReadyEvent) {
            output.success("Bot "+NAME+" is online");
        }
    }

}
