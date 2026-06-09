package ru.Natro.worldmanager.commands.subcommand;

import java.io.File;
import java.util.Comparator;
import java.util.LinkedList;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Level;
import ru.Natro.worldmanager.WorldManager;

public class DeleteCommand extends SubCommand {

    public DeleteCommand() {
        super("delete");
        this.setAliases(new String[] {
            "delete",
            "del",
            "remove",
            "purge"
        });
    }

    @Override
    public CommandParameter[] getParameters() {

        LinkedList < CommandParameter > parameters = new LinkedList < > ();
        parameters.add(CommandParameter.newEnum(this.getName(), this.getAliases()));
        parameters.add(CommandParameter.newType("world", true, CommandParamType.STRING));
        return parameters.toArray(new CommandParameter[parameters.size()]);

    }

    @Override
    public boolean execute(CommandSender sender, String arg1, String[] args) {
        if (!sender.hasPermission("worldmanager.admin") && !sender.hasPermission("worldmanager.delete")) {

            sender.sendMessage(WorldManager.prefix + "§cYou are lacking the permission §e'worldmanager.delete'.");
            return false;

        } else {

            try {
                if (args.length == 2) {
                	
                	String name = args[1];
                	if(name.equals("-c") && sender instanceof Player) name = ((Player) sender).getLevel().getName(); // with argument to prevent usage on accident
                    Level l = Server.getInstance().getLevelByName(name);
                    name = l.getName();
                    String folder = l.getFolderName();
                    
                    if (Server.getInstance().getLevelByName(name) != null) {
                    	
                        l.unload();
                        File regionfolder = new File(Server.getInstance().getDataPath() + "worlds/" + folder + "/region");
                        File worldfolder = new File(Server.getInstance().getDataPath() + "worlds/" + folder);
                        java.nio.file.Files.walk(regionfolder.toPath())
                            .sorted(java.util.Comparator.reverseOrder())
                            .map(java.nio.file.Path::toFile)
                            .forEach(File::delete);
                        java.nio.file.Files.walk(worldfolder.toPath())
                            .sorted(java.util.Comparator.reverseOrder())
                            .map(java.nio.file.Path::toFile)
                            .forEach(File::delete);
                        worldfolder.delete();

                        sender.sendMessage(WorldManager.prefix + "§7Deleted the world §8" + name + ".");
                    } else sender.sendMessage(WorldManager.prefix + "§cThis world is not loaded or does not exist.");

                } else sender.sendMessage(WorldManager.prefix + "§cDo /worldmanager delete [Name].");
            } catch (Exception e) {

            }
        }
        return false;
    }

}