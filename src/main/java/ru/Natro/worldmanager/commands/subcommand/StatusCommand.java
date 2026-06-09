package ru.Natro.worldmanager.commands.subcommand;

import java.util.LinkedList;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import ru.Natro.worldmanager.WorldManager;
import ru.Natro.worldmanager.api.World;
import ru.Natro.worldmanager.utils.Cache;
import ru.Natro.worldmanager.utils.Updater;

public class StatusCommand extends SubCommand {

	public StatusCommand() {
		super("status");
		this.setAliases(new String[] {
				"status"
		});
	}

	@Override
	public CommandParameter[] getParameters() {
		
		LinkedList<CommandParameter> parameters = new LinkedList<>();
		parameters.add(CommandParameter.newEnum(this.getName(), this.getAliases()));
		return parameters.toArray(new CommandParameter[parameters.size()]);
		
	}
	
	@Override
	public boolean execute(CommandSender sender, String arg1, String[] args) {
		
		  if (!sender.hasPermission("worldmanager.admin") && !sender.hasPermission("worldmanager.status")) {

				 sender.sendMessage(WorldManager.prefix + "§cYou are lacking the permission §e'worldmanager." + args[0] + "'.");
				 return false;

			  } else {

				 String message = "§l§3WorldManager §eStatus\n§r";
				 message += ("§ePlugin Version: §7" + WorldManager.get().getDescription().getVersion() + "\n");
				 message += ("§eNewest Version: §7" + Updater.getNewestVersion() + "\n");
				 message += ("§eCached Worlds: §7" + Cache.getWorldCache().size() + "\n");
				 message += ("§eCached Players: §7" + Cache.getCachedPlayerGamemodes() + "\n");
				 message += "§eWorlds: §7";
				 for (World w : Cache.getWorldCache()) message += w.getAsLevel().getName() + ", ";
				 sender.sendMessage(message);

			  }
		
		return false;
	}

}
