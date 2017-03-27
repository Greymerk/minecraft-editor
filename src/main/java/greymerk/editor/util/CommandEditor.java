package greymerk.editor.util;

import greymerk.editor.EditorEventHandler;
import greymerk.editor.tools.ToolBox;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandEditor extends CommandBase {

	@Override
	public String getName() {
		return "editor";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		
		ArgumentParser ap = new ArgumentParser(args);
		
		if(!ap.hasEntry(0)){
			return;
		}
		
		if(ap.match(0, "boxes")){
			int count = EditorEventHandler.boxes.keySet().size();
			sender.sendMessage(new TextComponentString(TextFormat.apply("Boxes: " + count, TextFormat.GOLD)));
		}
		
		if(ap.match(0, "undo")){
			String name = sender.getName();
			ToolBox box = EditorEventHandler.boxes.get(name);
			if(box == null) return;
			box.undo();
			return;
		}
		
		if(ap.match(0, "redo")){
			String name = sender.getName();
			ToolBox box = EditorEventHandler.boxes.get(name);
			if(box == null) return;
			box.redo();
			return;
		}
		
	}
	
	@Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender){
        return true;
    }
	
	@Override
	public String getUsage(ICommandSender sender) {
		return "";
	}

}
