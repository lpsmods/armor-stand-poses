package dev.lpsmods.poses.data;

import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Rotations;
import org.junit.jupiter.api.Test;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PoseDataTest {
    @org.junit.jupiter.api.BeforeAll
    static void initializeMinecraft() {
        net.minecraft.SharedConstants.tryDetectVersion();
        net.minecraft.server.Bootstrap.bootStrap();
    }
    @Test
    void bundledPosesDecodeAndPreserveRotationsInNbt() throws Exception {
        for (String name : List.of("athena", "brandish", "can_can_a", "can_can_b", "default", "entertain",
                "hero", "honor", "none", "riposte", "salute", "solemn", "zombie")) {
            try (var stream = getClass().getResourceAsStream("/data/minecraft/pose/" + name + ".json")) {
                assertNotNull(stream, name);
                var json = JsonParser.parseReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
                ArmorStandPose pose = ArmorStandPose.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow();
                assertEquals(pose.pose(), ArmorStandPose.Pose.fromStorage(pose.save().getCompoundOrEmpty("Pose")), name);
                assertTrue(pose.save().getBooleanOr("ShowArms", false));
            }
        }
    }

    @Test
    void rotationsRejectWrongLengthAndKeepValues() {
        assertTrue(RotationsProvider.CODEC.parse(JsonOps.INSTANCE, JsonParser.parseString("[1,2]")).isError());
        assertEquals(new Rotations(10, 20, 30), RotationsProvider.CODEC
                .parse(JsonOps.INSTANCE, JsonParser.parseString("[10,20,30]")).getOrThrow());
    }
}

