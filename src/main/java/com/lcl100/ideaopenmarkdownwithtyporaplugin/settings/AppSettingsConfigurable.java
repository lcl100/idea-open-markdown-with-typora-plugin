package com.lcl100.ideaopenmarkdownwithtyporaplugin.settings;

/**
 * @author lcl100
 * @create 2025-06-30 12:09
 */

import com.intellij.openapi.options.Configurable;
import com.lcl100.ideaopenmarkdownwithtyporaplugin.ui.AppSettingsComponent;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;
import java.util.Objects;

public class AppSettingsConfigurable implements Configurable {

    private AppSettingsComponent settingsComponent;

    @Override
    public String getDisplayName() {
        return "Markdown External Open";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        settingsComponent = new AppSettingsComponent();
        return settingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        AppSettingsState settings = AppSettingsState.getInstance();
        return !Objects.equals(settingsComponent.getCustomPath(), settings.customAppPath) ||
                settingsComponent.useSystemDefaultForMarkdown() != settings.useSystemDefaultForMarkdown ||
                settingsComponent.useSystemDefaultForOthers() != settings.useSystemDefaultForOthers ||
                !mapsEqual(settingsComponent.getExtensionAppMap(), settings.extensionAppMap);
    }

    private boolean mapsEqual(Map<String, String> map1, Map<String, String> map2) {
        if (map1 == null && map2 == null) return true;
        if (map1 == null || map2 == null) return false;
        if (map1.size() != map2.size()) return false;

        for (Map.Entry<String, String> entry : map1.entrySet()) {
            String key = entry.getKey();
            String value1 = entry.getValue();
            String value2 = map2.get(key);

            if (!Objects.equals(value1, value2)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void apply() {
        AppSettingsState settings = AppSettingsState.getInstance();
        settings.customAppPath = settingsComponent.getCustomPath();
        settings.useSystemDefaultForMarkdown = settingsComponent.useSystemDefaultForMarkdown();
        settings.useSystemDefaultForOthers = settingsComponent.useSystemDefaultForOthers();
        settings.extensionAppMap.clear();
        settings.extensionAppMap.putAll(settingsComponent.getExtensionAppMap());
    }

    @Override
    public void reset() {
        AppSettingsState settings = AppSettingsState.getInstance();
        settingsComponent.setCustomPath(settings.customAppPath);
        settingsComponent.setUseSystemDefaultForMarkdown(settings.useSystemDefaultForMarkdown);
        settingsComponent.setUseSystemDefaultForOthers(settings.useSystemDefaultForOthers);
        settingsComponent.setExtensionAppMap(settings.extensionAppMap);
    }

    @Override
    public void disposeUIResources() {
        // 不需要设置为null
    }
}