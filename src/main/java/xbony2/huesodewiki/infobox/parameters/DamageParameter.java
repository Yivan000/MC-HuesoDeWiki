package xbony2.huesodewiki.infobox.parameters;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import xbony2.huesodewiki.api.infobox.IInfoboxParameter;

public class DamageParameter implements IInfoboxParameter {

	@Override
	public boolean canAdd(ItemStack itemstack){
		return itemstack.getItem() instanceof ItemSword;
	}

	@Override
	public String parameterName(){
		return "damage";
	}

	@Override
	public String parameterText(ItemStack itemstack){
		return Float.toString(((ItemSword)itemstack.getItem()).getDamageVsEntity());
	}

}