package net.magicaltech.world.gen;

import java.util.Random;

import net.magicaltech.init.MTBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class OreGen implements IWorldGenerator {
	
	//World Generators
	private WorldGenerator rose_quartz_nether;
	private WorldGenerator prophecy_iron_nether;
	
	private WorldGenerator rose_quartz;
	private WorldGenerator transistium;
	private WorldGenerator prophecy_iron;
	
	private WorldGenerator hardened_stone;
	
	public OreGen() {
		rose_quartz_nether = new WorldGenMinable(MTBlocks.rQNetherOre.getDefaultState(), 8, new NetherGenPredicate());
		prophecy_iron_nether = new WorldGenMinable(MTBlocks.plNetherOre.getDefaultState(), 8, new NetherGenPredicate());
		
		hardened_stone = new WorldGenMinable(MTBlocks.hStone.getDefaultState(), 8);
		rose_quartz = new WorldGenMinable(MTBlocks.rQOre.getDefaultState(), 8);
		prophecy_iron = new WorldGenMinable(MTBlocks.plOre.getDefaultState(), 8);
	}
	
	private void runGenerator(WorldGenerator generator, World world, Random rand, int chunk_X, int chunk_Z, int chancesToSpawn, int minHeight, int maxHeight) {
		if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
			throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");

		int heightDiff = maxHeight - minHeight + 1;
		for (int i = 0; i < chancesToSpawn; i ++) {
			int x = chunk_X * 16 + rand.nextInt(16);
		        int y = minHeight + rand.nextInt(heightDiff);
		        int z = chunk_Z * 16 + rand.nextInt(16);
		        generator.generate(world, rand, new BlockPos(x, y, z));
		}
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,IChunkProvider chunkProvider) {
		switch(world.provider.getDimension()) {
		case 1: //OverWorld
			this.runGenerator(hardened_stone, world, random, chunkX, chunkZ, 1, 0, 48);
			this.runGenerator(rose_quartz, world, random, chunkX, chunkZ, 1, 0, 48);
			this.runGenerator(transistium, world, random, chunkX, chunkZ, 1, 0, 48);
			this.runGenerator(prophecy_iron, world, random, chunkX, chunkZ, 1, 0, 48);
		case -1: //Nether
			this.runGenerator(rose_quartz_nether, world, random, chunkX, chunkZ, 1, 16, 64);
			this.runGenerator(prophecy_iron_nether, world, random, chunkX, chunkZ, 1, 16, 64);
		}
	}

}
