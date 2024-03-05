package dev.frankheijden.insights.api.concurrent.containers;

import dev.frankheijden.insights.api.InsightsPlugin;
import dev.frankheijden.insights.api.concurrent.ScanOptions;
import dev.frankheijden.insights.api.objects.chunk.ChunkCuboid;
import dev.frankheijden.insights.nms.core.ChunkEntity;
import dev.frankheijden.insights.nms.core.ChunkSection;
import dev.frankheijden.insights.nms.core.InsightsNMS;
import org.bukkit.Chunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class LoadedChunkContainer extends ChunkContainer {

    private final Chunk chunk;

    /**
     * Constructs a new LoadedChunkContainer, for scanning of a loaded chunk.
     */
    public LoadedChunkContainer(InsightsNMS nms, Chunk chunk, ChunkCuboid cuboid, ScanOptions options) {
        super(nms, chunk.getWorld(), chunk.getX(), chunk.getZ(), cuboid, options);
        this.chunk = chunk;
    }

    @Override
    public CompletableFuture<Boolean> getChunkSections(Consumer<@Nullable ChunkSection> sectionConsumer) {
        CompletableFuture<Boolean> result = new CompletableFuture<>();
        InsightsPlugin.getInstance().getScheduler().runTaskAtLocation(
            chunk.getBlock(0, 0, 0).getLocation(),
            () -> result.complete(nms.getLoadedChunkSections(chunk, sectionConsumer).join())
        );
        return result;
    }

    @Override
    public CompletableFuture<Boolean> getChunkEntities(Consumer<@NotNull ChunkEntity> entityConsumer) {
        CompletableFuture<Boolean> result = new CompletableFuture<>();
        InsightsPlugin.getInstance().getScheduler().runTaskAtLocation(
            chunk.getBlock(0, 0, 0).getLocation(),
            () -> result.complete(nms.getLoadedChunkEntities(chunk, entityConsumer).join())
        );
        return result;
    }
}
