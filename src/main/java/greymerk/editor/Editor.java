package greymerk.editor;

import greymerk.editor.util.CommandEditor;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid="editor", name="Greymerk's Editor", version=Editor.version, acceptableRemoteVersions="*")

public class Editor {
	
	@Instance("editor")
	public static Editor instance;
	public static final String version = "0.1.5";
	
	EditorEventHandler events = new EditorEventHandler();
	
	@SidedProxy(clientSide="greymerk.editor.ClientProxy", serverSide="greymerk.editor.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler void preinit(FMLPreInitializationEvent event){
		MinecraftForge.EVENT_BUS.register(events);
	}
	
	@EventHandler
	public void serverStart(FMLServerStartingEvent event){
		MinecraftServer server = event.getServer();
		ICommandManager command = server.getCommandManager();
		ServerCommandManager serverCommand = ((ServerCommandManager) command);
		serverCommand.registerCommand(new CommandEditor());
	}
	
	
	
}
