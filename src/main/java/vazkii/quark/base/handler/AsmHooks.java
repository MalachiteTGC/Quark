package vazkii.quark.base.handler;

import net.minecraft.block.BlockState;
import net.minecraft.block.state.PistonBlockStructureHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.PistonTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.quark.automation.client.render.PistonTileEntityRenderer;
import vazkii.quark.automation.module.PistonsMoveTileEntitiesModule;
import vazkii.quark.tweaks.module.HoeHarvestingModule;
import vazkii.quark.tweaks.module.ImprovedSleepingModule;
import vazkii.quark.vanity.client.emote.EmoteHandler;
import vazkii.quark.vanity.module.ColorRunesModule;
import vazkii.quark.vanity.module.ItemSharingModule;

/**
 * @author WireSegal
 * Created at 10:10 AM on 8/15/19.
 */
@SuppressWarnings("unused")
public class AsmHooks {

	// ==========================================================================
	// Color Runes
	// ==========================================================================

	public static void setColorRuneTargetStack(LivingEntity living, EquipmentSlotType slot) {
		setColorRuneTargetStack(living.getItemStackFromSlot(slot));
	}

	public static void setColorRuneTargetStack(ItemStack stack) {
		ColorRunesModule.setTargetStack(stack);
	}

	public static int changeColor(int color) {
		if (color == 0xFF8040CC)
			return ColorRunesModule.changeColor(color);

		return color;
	}

	@OnlyIn(Dist.CLIENT)
	public static void applyRuneColor() {
		ColorRunesModule.applyColor();
	}

	// ==========================================================================
	// Piston Logic Replacing
	// ==========================================================================

	public static PistonBlockStructureHelper transformStructureHelper(PistonBlockStructureHelper helper, World world, BlockPos sourcePos, Direction facing, boolean extending) {
		return new QuarkPistonStructureHelper(helper, world, sourcePos, facing, extending);
	}

	// ==========================================================================
	// Pistons Move TEs
	// ==========================================================================

	public static boolean setPistonBlock(World world, BlockPos pos, BlockState state, int flags) {
		return PistonsMoveTileEntitiesModule.setPistonBlock(world, pos, state, flags);
	}

	public static boolean shouldPistonMoveTE(boolean parent, BlockState state) {
		return PistonsMoveTileEntitiesModule.shouldMoveTE(parent, state);
	}

	public static void postPistonPush(PistonBlockStructureHelper helper, World world, Direction direction, boolean extending) {
		PistonsMoveTileEntitiesModule.detachTileEntities(world, helper, direction, extending);
	}

	@OnlyIn(Dist.CLIENT)
	public static boolean renderPistonBlock(PistonTileEntity piston, double x, double y, double z, float pTicks) {
		return PistonTileEntityRenderer.renderPistonBlock(piston, x, y, z, pTicks);
	}

	// ==========================================================================
	// Emotes
	// ==========================================================================

	public static void updateEmotes(LivingEntity entity) {
		EmoteHandler.updateEmotes(entity);
	}

	// ==========================================================================
	// Fortune on Hoes
	// ==========================================================================

	public static boolean canFortuneApply(Enchantment enchantment, ItemStack stack) {
		return HoeHarvestingModule.canFortuneApply(enchantment, stack);
	}

	// ==========================================================================
	// Improved Sleeping
	// ==========================================================================

	public static boolean isEveryoneAsleep(boolean parent) {
		return ImprovedSleepingModule.isEveryoneAsleep(parent);
	}

	// ==========================================================================
	// Items In Chat
	// ==========================================================================

	public static ITextComponent createStackComponent(ITextComponent parent) {
		return ItemSharingModule.createStackComponent(parent);
	}

	public static int transformQuadRenderColor(int parent) {
		return ItemSharingModule.transformColor(parent);
	}

}