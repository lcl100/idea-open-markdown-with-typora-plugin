package com.lcl100.ideaopenmarkdownwithtyporaplugin.settings;

/**
 * @author lcl100
 * @create 2025-06-30 12:08
 */

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "md.open.settings.AppSettingsState",
        storages = @Storage("OpenMarkdownExternallySettings.xml")
)
public class AppSettingsState implements PersistentStateComponent<AppSettingsState> {

    public String customAppPath = "";
    public boolean useSystemDefault = true;

    public static AppSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(AppSettingsState.class);
    }

    @Nullable
    @Override
    public AppSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull AppSettingsState state) {
        this.customAppPath = state.customAppPath;
        this.useSystemDefault = state.useSystemDefault;
    }
}