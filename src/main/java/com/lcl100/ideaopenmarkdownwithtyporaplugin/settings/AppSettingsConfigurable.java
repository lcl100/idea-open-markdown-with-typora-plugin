package com.lcl100.ideaopenmarkdownwithtyporaplugin.settings;

/**
 * @author lcl100
 * @create 2025-06-30 12:09
 */

import com.intellij.openapi.options.Configurable;
import com.lcl100.ideaopenmarkdownwithtyporaplugin.ui.AppSettingsComponent;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
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
        if (settingsComponent == null) return false;

        AppSettingsState settings = AppSettingsState.getInstance();
        return !settingsComponent.getCustomPath().equals(settings.customAppPath) || settingsComponent.useSystemDefault() != settings.useSystemDefaultForMarkdown || !Objects.equals(settingsComponent.getSupportedExtensions(), settings.supportedExtensions);
    }

    @Override
    public void apply() {
        if (settingsComponent == null) return;

        AppSettingsState settings = AppSettingsState.getInstance();
        settings.customAppPath = settingsComponent.getCustomPath();
        settings.useSystemDefaultForMarkdown = settingsComponent.useSystemDefault();
        settings.supportedExtensions = settingsComponent.getSupportedExtensions();
    }

    @Override
    public void reset() {
        if (settingsComponent == null) return;

        AppSettingsState settings = AppSettingsState.getInstance();
        settingsComponent.setCustomPath(settings.customAppPath);
        settingsComponent.setUseSystemDefault(settings.useSystemDefaultForMarkdown);
        settingsComponent.setSupportedExtensions(settings.supportedExtensions);
    }

    @Override
    public void disposeUIResources() {
        settingsComponent = null;
    }
}