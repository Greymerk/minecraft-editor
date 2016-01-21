package greymerk.editor;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid="Editor", name="Greymerk's Editor", version=Editor.version, acceptableRemoteVersions="*")

public class Editor {
	
	@Instance("Editor")
	public static Editor instance;
	public static final String version = "0.0.3";
	
	EditorEventHandler events = new EditorEventHandler();
	
	@SidedProxy(clientSide="greymerk.editor.ClientProxy", serverSide="greymerk.editor.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler void preinit(FMLPreInitializationEvent event){
		MinecraftForge.EVENT_BUS.register(events);
	}
	
	@EventHandler
	public void serverStart(FMLServerStartingEvent event){
		
	}
}
