package normal.wild.elements;

import javax.annotation.Nullable;

import ch.njol.skript.lang.util.SimpleExpression;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import ch.njol.skript.ScriptLoader;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import org.bukkit.event.player.PlayerFishEvent;

public class fishstate extends SimpleExpression<PlayerFishEvent.State> {
    static {
        Skript.registerExpression(fishstate.class, PlayerFishEvent.State.class, ExpressionType.SIMPLE, "fish[ing] state");
    }

    public Class<? extends PlayerFishEvent.State> getReturnType() {
        return PlayerFishEvent.State.class;
    }

    public boolean isSingle() {
        return true;
    }

    public boolean init(final Expression<?>[] args, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
        if (!ScriptLoader.isCurrentEvent(PlayerFishEvent.class)) {
            Skript.error("This Expression can only be used for fish event");
            return false;
        }
        return true;
    }

    public String toString(@Nullable final Event arg0, final boolean arg1) {
        return "fishing state";
    }

    @Nullable
    protected PlayerFishEvent.State[] get(final Event e) {
        return new PlayerFishEvent.State[] { ((PlayerFishEvent)e).getState() };
    }
}
