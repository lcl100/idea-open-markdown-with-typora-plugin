package com.lcl100.ideaopenmarkdownwithtyporaplugin.settings;

/**
 * @author lcl100
 * @create 2025-06-30 12:09
 */

import com.intellij.openapi.options.Configurable;
import com.lcl100.ideaopenmarkdownwithtyporaplugin.ui.AppSettingsComponent;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

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
        return !settingsComponent.getCustomPath().equals(settings.customAppPath) ||
                settingsComponent.useSystemDefault() != settings.useSystemDefault;
    }

    @Override
    public void apply() {
        AppSettingsState settings = AppSettingsState.getInstance();
        settings.customAppPath = settingsComponent.getCustomPath();
        settings.useSystemDefault = settingsComponent.useSystemDefault();
    }

    @Override
    public void reset() {
        AppSettingsState settings = AppSettingsState.getInstance();
        settingsComponent.setCustomPath(settings.customAppPath);
        settingsComponent.setUseSystemDefault(settings.useSystemDefault);
    }

    @Override
    public void disposeUIResources() {
        settingsComponent = null;
    }
}