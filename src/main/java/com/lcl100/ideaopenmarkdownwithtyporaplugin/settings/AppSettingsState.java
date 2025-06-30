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

import java.util.LinkedHashMap;
import java.util.Map;

@State(
        name = "OpenMarkdownExternallySettings",
        storages = @Storage("OpenMarkdownExternallySettings.xml")
)
public class AppSettingsState implements PersistentStateComponent<AppSettingsState> {

    // Markdown 特定设置
    public String customAppPath = "";
    public boolean useSystemDefault = true;
    public boolean useSystemDefaultForMarkdown = true;

    // 文件扩展名关联设置
    public Map<String, String> extensionAppMap = new LinkedHashMap<>();

    // 默认系统应用设置
    public boolean useSystemDefaultForOthers = true;

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
        this.customAppPath = state.customAppPath != null ? state.customAppPath : "";
        this.useSystemDefaultForMarkdown = state.useSystemDefaultForMarkdown;
        this.extensionAppMap = state.extensionAppMap != null ?
                new LinkedHashMap<>(state.extensionAppMap) : new LinkedHashMap<>();
        this.useSystemDefaultForOthers = state.useSystemDefaultForOthers;
    }
}