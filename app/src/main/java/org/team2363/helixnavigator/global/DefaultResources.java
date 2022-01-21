package org.team2363.helixnavigator.global;

public final class DefaultResources {
    private DefaultResources() {
    }

    public static void loadAllResources() {
        DefaultFieldImages.loadDefaultFieldImages();
        DefaultFieldConfigurations.loadDefaultFieldConfigurations();
    }
}