package normal.wild.elements;

import ch.njol.skript.lang.util.SimpleExpression;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import ch.njol.skript.ScriptLoader;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import org.bukkit.event.player.PlayerFishEvent;

public class caught extends SimpleExpression<Entity> {
    static {
        Skript.registerExpression(caught.class, Entity.class, ExpressionType.SIMPLE, "caught (fish|item|entity)");
    }

    public Class<? extends Entity> getReturnType() {
        return Entity.class;
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
        return "caught fish";
    }

    @Nullable
    protected Entity[] get(final Event e) {
        return new Entity[] { ((PlayerFishEvent)e).getCaught() };
    }
}
