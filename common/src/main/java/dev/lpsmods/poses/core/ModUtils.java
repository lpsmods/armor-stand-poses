package dev.lpsmods.poses.core;

import dev.lpsmods.poses.Constants;
import net.minecraft.resources.Identifier;

public class ModUtils {
    public static Identifier makeId(String path) {
        return Identifier.fromNamespaceAndPath(Constants.MOD_ID, path);
    }
}
