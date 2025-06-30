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
    // Markdown 特定设置（原有设置）
    public boolean useSystemDefaultForMarkdown = true;

    // 通用文件设置（新增）
    public String supportedExtensions = "txt,log,ini,json,xml,properties,yml,yaml,csv"; // 默认支持的文件扩展名

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