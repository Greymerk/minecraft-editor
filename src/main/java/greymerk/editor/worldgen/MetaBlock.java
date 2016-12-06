package greymerk.editor.worldgen;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MetaBlock extends BlockBase implements IBlockState{

	private IBlockState state;
	private int flag;
    
	public MetaBlock(Block block){
		this.state = block.getDefaultState();
		flag = 2;
	}
	
	public MetaBlock(IBlockState state){
		this.state = state;
		flag = 2;
	}
	
	
	public MetaBlock(Block block, IProperty<?> ... properties){
		BlockStateContainer s = new BlockStateContainer(block, properties);
		this.state = s.getBaseState();
	}
	
	@SuppressWarnings("deprecation")
	public MetaBlock(JsonElement e){
		JsonObject json = (JsonObject)e;
		String name = json.get("name").getAsString();
		ResourceLocation location = new ResourceLocation(name);
		Block block = (Block) Block.REGISTRY.getObject(location);
		int meta = json.has("meta") ? json.get("meta").getAsInt() : 0;
		this.state = block.getStateFromMeta(meta);
		flag = json.has("flag") ? json.get("flag").getAsInt() : 2;
	}
	
	public void setState(IBlockState state){
		this.state = state;
	}

	public boolean set(IWorldEditor editor, Coord pos){
		return editor.setBlock(pos, this, true, true);
	}
		
	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid) {
		return editor.setBlock(pos, this, fillAir, replaceSolid);
	}

	@Override
	public <T extends Comparable<T>> T getValue(IProperty<T> property) {
		return state.getValue(property);
	}
	
	@Override
	public <T extends Comparable<T>, V extends T> IBlockState withProperty(IProperty<T> property, V value) {
		this.state = this.state.withProperty(property, value);
		return this.state;
	}
	
	@Override
	public <T extends Comparable<T>> IBlockState cycleProperty(IProperty<T> property) {
		return this.state.cycleProperty(property);
	}

	public IBlockState getState(){
		return this.state;
	}
	
	@Override
	public Block getBlock() {
		return this.state.getBlock();
	}
	
	public int getFlag(){
		return this.flag;
	}
	
	@Override
	public String toString(){
		return this.state.getBlock().getUnlocalizedName();
	}

	@Override
	public Material getMaterial() {
		return this.state.getMaterial();
	}

	@Override
	public boolean isFullBlock() {
		return this.state.isFullBlock();
	}

	@Override
	public boolean useNeighborBrightness() {
		return this.state.useNeighborBrightness();
	}

	@Override
	public MapColor getMapColor() {
		return this.state.getMapColor();
	}

	@Override
	public IBlockState withRotation(Rotation rot) {
		return this.state.withRotation(rot);
	}

	@Override
	public IBlockState withMirror(Mirror mirrorIn) {
		return this.state.withMirror(mirrorIn);
	}

	@Override
	public boolean isFullCube() {
		return this.state.isFullCube();
	}

	@Override
	public EnumBlockRenderType getRenderType() {
		return this.state.getRenderType();
	}

	@Override
	public boolean isBlockNormalCube() {
		return this.state.isBlockNormalCube();
	}

	@Override
	public boolean isNormalCube() {
		return this.state.isNormalCube();
	}

	@Override
	public boolean canProvidePower() {
		return this.state.canProvidePower();
	}

	@Override
	public int getWeakPower(IBlockAccess p_185911_1_, BlockPos p_185911_2_, EnumFacing p_185911_3_) {
		return this.state.getWeakPower(p_185911_1_, p_185911_2_, p_185911_3_);
	}

	@Override
	public boolean hasComparatorInputOverride() {
		return this.state.hasComparatorInputOverride();
	}

	@Override
	public int getComparatorInputOverride(World p_185888_1_, BlockPos p_185888_2_) {
		return this.state.getComparatorInputOverride(p_185888_1_, p_185888_2_);
	}

	@Override
	public float getBlockHardness(World p_185887_1_, BlockPos p_185887_2_) {
		return this.state.getBlockHardness(p_185887_1_, p_185887_2_);
	}

	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer p_185903_1_, World p_185903_2_, BlockPos p_185903_3_) {
		return this.state.getPlayerRelativeBlockHardness(p_185903_1_, p_185903_2_, p_185903_3_);
	}

	@Override
	public int getStrongPower(IBlockAccess p_185893_1_, BlockPos p_185893_2_, EnumFacing p_185893_3_) {
		return this.state.getStrongPower(p_185893_1_, p_185893_2_, p_185893_3_);
	}

	@Override
	public EnumPushReaction getMobilityFlag() {
		return this.state.getMobilityFlag();
	}

	@Override
	public IBlockState getActualState(IBlockAccess p_185899_1_, BlockPos p_185899_2_) {
		return this.state.getActualState(p_185899_1_, p_185899_2_);
	}

	@Override
	public boolean isOpaqueCube() {
		return this.state.isOpaqueCube();
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(World p_185890_1_, BlockPos p_185890_2_) {
		return this.state.getSelectedBoundingBox(p_185890_1_, p_185890_2_);
	}

	@Override
	public Collection<IProperty<?>> getPropertyNames() {
		return this.state.getPropertyNames();
	}

	@Override
	public ImmutableMap<IProperty<?>, Comparable<?>> getProperties() {
		return this.state.getProperties();
	}

	@Override
	public int getLightOpacity(IBlockAccess world, BlockPos pos) {
		return this.state.getLightOpacity(world, pos);
	}

	@Override
	public int getLightValue(IBlockAccess world, BlockPos pos) {
		return this.state.getLightValue(world, pos);
	}

	@Override
	public boolean isTranslucent() {
		return this.state.isTranslucent();
	}

	@Override
	public int getPackedLightmapCoords(IBlockAccess source, BlockPos pos) {
		return this.state.getPackedLightmapCoords(source, pos);
	}

	@Override
	public float getAmbientOcclusionLightValue() {
		return this.state.getAmbientOcclusionLightValue();
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, BlockPos pos, EnumFacing facing) {
		return this.state.shouldSideBeRendered(blockAccess, pos, facing);
	}

	@Override
	public void addCollisionBoxToList(World worldIn, BlockPos pos, AxisAlignedBB p_185908_3_, List<AxisAlignedBB> p_185908_4_, Entity p_185908_5_) {
		this.state.addCollisionBoxToList(worldIn, pos, p_185908_3_, p_185908_4_, p_185908_5_);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockAccess blockAccess, BlockPos pos) {
		return this.state.getBoundingBox(blockAccess, pos);
	}

	@Override
	public RayTraceResult collisionRayTrace(World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
		return this.state.collisionRayTrace(worldIn, pos, start, end);
	}

	@Override
	public boolean doesSideBlockRendering(IBlockAccess world, BlockPos pos, EnumFacing side) {
		return this.state.doesSideBlockRendering(world, pos, side);
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing side) {
		return this.state.isSideSolid(world, pos, side);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isFullyOpaque() {
		return this.state.isFullyOpaque();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public int getLightOpacity() {
		return this.state.getLightOpacity();
	}

	@Override
	public boolean onBlockEventReceived(World arg0, BlockPos arg1, int arg2, int arg3) {
		return this.state.onBlockEventReceived(arg0, arg1, arg2, arg3);
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getLightValue() {
		return this.state.getLightValue();
	}

	@Override
	public void neighborChanged(World worldIn, BlockPos pos, Block blockIn, BlockPos p_189546_4_) {
		this.state.neighborChanged(worldIn, pos, blockIn, p_189546_4_);
		
	}

	@Override
	public boolean canEntitySpawn(Entity entityIn) {
		return this.state.canEntitySpawn(entityIn);
	}

	@Override
	public boolean func_191057_i() {
		return this.state.func_191057_i();
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockAccess worldIn, BlockPos pos) {
		return this.state.getCollisionBoundingBox(worldIn, pos);
	}

	@Override
	public Vec3d func_191059_e(IBlockAccess p_191059_1_, BlockPos p_191059_2_) {
		return this.state.func_191059_e(p_191059_1_, p_191059_2_);
	}

	@Override
	public boolean func_191058_s() {
		return this.state.func_191058_s();
	}

}
