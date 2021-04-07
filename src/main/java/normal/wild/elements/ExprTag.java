package normal.wild.elements;


import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.mojang.authlib.GameProfile;
import normal.wild.wildSK;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerFishEvent;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

public class ExprTag extends SimpleExpression<String> {
    static {
        Skript.registerExpression(ExprTag.class, String.class, ExpressionType.SIMPLE, "%player%['s] NameTag");
        Skript.registerExpression(ExprTag.class, String.class, ExpressionType.SIMPLE, "NameTag [of] %player%");
    }
    private Expression<Player> player;

    public Class<? extends String> getReturnType() {
        return String.class;
    }

    public boolean isSingle() {
        return true;
    }

    public boolean init(final Expression<?>[] args, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
        player = (Expression<Player>)args[0];
        return true;
    }

    public String toString(@Nullable final Event arg0, final boolean arg1) {
        return "Name Tag";
    }

    @Nullable
    protected String[] get(final Event e) {
        Player p = player.getSingle(e);
        GameProfile profile = ((CraftPlayer)p).getProfile();
        return new String[] { profile.getName() };
    }
    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(String.class);
        }
        return null;
    }
    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        try {
            Player p = player.getSingle(e);
            if (mode == Changer.ChangeMode.SET) {
                GameProfile playerProfile = ((CraftPlayer) p).getHandle().getProfile();
                Field ff = playerProfile.getClass().getDeclaredField("name");
                ff.setAccessible(true);
                ff.set(playerProfile, (String)delta[0]);
                for(Player other : Bukkit.getOnlinePlayers()){
                    other.hidePlayer(wildSK.getInstance(), p);
                    other.showPlayer(wildSK.getInstance(), p);
                }
            }
        } catch(NoSuchFieldException | IllegalAccessException noSuchFieldException){
            noSuchFieldException.printStackTrace();
        }

    }
}


