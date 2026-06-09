package ru.Natro.worldmanager.commands;

import java.util.ArrayList;

import java.util.Arrays;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import ru.Natro.worldmanager.WorldManager;
import ru.Natro.worldmanager.commands.subcommand.AddonCommand;
import ru.Natro.worldmanager.commands.subcommand.ClearlagCommand;
import ru.Natro.worldmanager.commands.subcommand.CopyCommand;
import ru.Natro.worldmanager.commands.subcommand.DeleteCommand;
import ru.Natro.worldmanager.commands.subcommand.GameruleCommand;
import ru.Natro.worldmanager.commands.subcommand.GenerateCommand;
import ru.Natro.worldmanager.commands.subcommand.HelpCommand;
import ru.Natro.worldmanager.commands.subcommand.InfoCommand;
import ru.Natro.worldmanager.commands.subcommand.LoadCommand;
import ru.Natro.worldmanager.commands.subcommand.ListCommand;
import ru.Natro.worldmanager.commands.subcommand.RegenerateCommand;
import ru.Natro.worldmanager.commands.subcommand.ReloadCommand;
import ru.Natro.worldmanager.commands.subcommand.RenameCommand;
import ru.Natro.worldmanager.commands.subcommand.SaveCommand;
import ru.Natro.worldmanager.commands.subcommand.SetdefaultCommand;
import ru.Natro.worldmanager.commands.subcommand.SetseedCommand;
import ru.Natro.worldmanager.commands.subcommand.SetspawnCommand;
import ru.Natro.worldmanager.commands.subcommand.SettingsCommand;
import ru.Natro.worldmanager.commands.subcommand.SpawnCommand;
import ru.Natro.worldmanager.commands.subcommand.StatusCommand;
import ru.Natro.worldmanager.commands.subcommand.SubCommand;
import ru.Natro.worldmanager.commands.subcommand.SyncCommand;
import ru.Natro.worldmanager.commands.subcommand.TeleportCommand;
import ru.Natro.worldmanager.commands.subcommand.UnloadCommand;
import ru.Natro.worldmanager.commands.subcommand.VersionCommand;

public class CommandMapping extends Command {

	private ArrayList<SubCommand> subcommands = new ArrayList<>();
	
	public CommandMapping() {
		super("worldmanager");
		this.setAliases(new String[] {"wm", "mw", "mv", "levelmanager", "lm"});
		this.setDescription("The main WorldManager Command"); 
		this.commandParameters.clear();
	}
	
	@Override
	public boolean execute(CommandSender sender, String arg1, String[] args) {
		
		if(args.length > 0) {
			
			String name = args[0].toLowerCase();
			
			for(SubCommand command : subcommands) {
				
				if(Arrays.asList(command.getAliases()).contains(name)) {
					command.execute(sender, arg1, args);
					return true;
				}
				
			}
			
		}
		sender.sendMessage(WorldManager.prefix + "§cUnknown Command. Use '/worldmanager help' to get a list of commands.");
		
		return true;
	}

	public void registerSubCommand(SubCommand subcommand) {
		subcommands.add(subcommand);
		addCommandParameters(subcommand.getName(), subcommand.getParameters());
	}
	
	public void register() {
		
		SubCommand[] subcommands = new SubCommand[] {
				new TeleportCommand(),
				new GenerateCommand(),
				new DeleteCommand(),
				new LoadCommand(),
				new UnloadCommand(),
				new ReloadCommand(),
				new ListCommand(),
				new SetspawnCommand(),
				new SettingsCommand(),
				new InfoCommand(),
				new SetseedCommand(),
				new RenameCommand(),
				new CopyCommand(),
				new RegenerateCommand(),
				new ClearlagCommand(),
				new GameruleCommand(),
				new SetdefaultCommand(),
				new SaveCommand(),
				new VersionCommand(),
				new SyncCommand(),
				new AddonCommand(),
				new SpawnCommand(),
				new StatusCommand(),
				new HelpCommand()
		};
		for(SubCommand subcommand : subcommands) registerSubCommand(subcommand);
		
		Server.getInstance().getCommandMap().register(this.getName(), this);
	}
	
}
