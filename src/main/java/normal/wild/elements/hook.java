package normal.wild.elements;

import javax.annotation.Nullable;

import ch.njol.skript.lang.util.SimpleExpression;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.event.Event;
import ch.njol.skript.ScriptLoader;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import org.bukkit.event.player.PlayerFishEvent;

public class hook extends SimpleExpression<FishHook> {
    static {
        Skript.registerExpression(hook.class, FishHook.class, ExpressionType.SIMPLE, "fish[ing] hook");
    }

    public Class<? extends FishHook> getReturnType() {
        return FishHook.class;
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
        return "fishing hook";
    }

    @Nullable
    protected FishHook[] get(final Event e) {
        return new FishHook[] { ((PlayerFishEvent)e).getHook() };
    }
}

