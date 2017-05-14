package xbony2.huesodewiki;

import static xbony2.huesodewiki.Utils.*;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

@Mod(modid = HuesoDeWiki.MODID, version = HuesoDeWiki.VERSION)
public class HuesoDeWiki {
	public static final String MODID = "huesodewiki";
	public static final String VERSION = "1.0.1c";
	
	public static KeyBinding key;
	private boolean isKeyDown = false;
	
	public static boolean use2SpaceStyle;
	
	public static Map<String, String> nameCorrections = new HashMap<String, String>();
	public static Map<String, String> linkCorrections = new HashMap<String, String>();
	
	public static final String[] DEFAULT_NAME_CORRECTIONS = new String[]{"Iron Chest", "Iron Chests", "Minecraft", "Vanilla"};
	public static final String[] DEFAULT_LINK_CORRECTIONS = new String[]{"Roots", "Roots (Mod)", "Esteemed Innovation", "Esteemed Innovation (Mod)"};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		key = new KeyBinding("key.copybasepage", Keyboard.KEY_SEMICOLON, "key.categories.huesodewiki");
		ClientRegistry.registerKeyBinding(key);
		MinecraftForge.EVENT_BUS.register(new RenderTickEventEventHanlder());
		
		Configuration config = new Configuration(new File(event.getModConfigurationDirectory(), "HuesoDeWiki.cfg"));
		config.load();
		use2SpaceStyle = config.getBoolean("Use2SpaceStyle", "Main", false, "Use \"2spacestyle\"- put an extra space in headers (like \"== Recipe ==\", as vs \"==Recipe==\").");
		String[] nameCorrections = config.getStringList("NameCorrections", "Main", DEFAULT_NAME_CORRECTIONS, "Name fixes. Is a map- first entry is the mod's internal name, second is the FTB Wiki's name.");
		String[] linkCorrections = config.getStringList("LinkCorrections", "Main", DEFAULT_LINK_CORRECTIONS, "Link fixes. Is a map- first entry is the mod's name, second is the FTB Wiki's page.");
		
		for(int i = 0; i < nameCorrections.length - 1; i += 2)
			this.nameCorrections.put(nameCorrections[i], nameCorrections[i + 1]);
		
		for(int i = 0; i < linkCorrections.length - 1; i += 2)
			this.nameCorrections.put(linkCorrections[i], linkCorrections[i + 1]);
		
		config.save();
	}
	
	private class RenderTickEventEventHanlder {
		@SubscribeEvent
		public void renderTickEvent(RenderTickEvent event){
			if(event.phase == Phase.START)
				if(Keyboard.isKeyDown(key.getKeyCode())){
					if(!isKeyDown){
						isKeyDown = true;
						Minecraft mc = Minecraft.getMinecraft();
						GuiScreen currentScreen = mc.currentScreen;
						
						if(currentScreen instanceof GuiContainer){
							Slot hovered = ((GuiContainer)currentScreen).getSlotUnderMouse();
							
							if(hovered == null)
								return;
							
							ItemStack itemstack = hovered.getStack();
							
							if(!itemstack.isEmpty())
								PageCreator.createPage(itemstack);
						}
					}
				}else
					isKeyDown = false;
		}
	}
}