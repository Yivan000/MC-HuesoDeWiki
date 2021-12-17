package xbony2.huesodewiki.infobox.parameters;

import net.minecraft.world.item.ItemStack;
import xbony2.huesodewiki.api.infobox.IInfoboxParameter;

public class NameParameter implements IInfoboxParameter {

	@Override
	public boolean canAdd(ItemStack itemstack){
		return true;
	}

	@Override
	public String getParameterName(){
		return "name";
	}

	@Override
	public String getParameterText(ItemStack itemstack){
		return itemstack.getHoverName().getString();
	}
}
