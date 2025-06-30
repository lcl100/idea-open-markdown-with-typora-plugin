package com.lcl100.ideaopenmarkdownwithtyporaplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.lcl100.ideaopenmarkdownwithtyporaplugin.settings.AppSettingsState;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class OpenMarkdownExternallyAction extends AnAction {

    @Override
    public void update(AnActionEvent e) {
        VirtualFile file = getSelectedFile(e);
        boolean isMdFile = file != null && "md".equalsIgnoreCase(file.getExtension());
        e.getPresentation().setEnabledAndVisible(isMdFile);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        VirtualFile file = getSelectedFile(e);
        if (file == null) return;

        try {
            openMarkdownFile(new File(file.getPath()));
        } catch (Exception ex) {
            Messages.showErrorDialog("Error opening file: " + ex.getMessage(), "Open Failed");
        }
    }

    private VirtualFile getSelectedFile(AnActionEvent e) {
        VirtualFile[] files = e.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY);
        return (files != null && files.length == 1) ? files[0] : null;
    }

    private void openMarkdownFile(File file) throws IOException {
        AppSettingsState settings = AppSettingsState.getInstance();

        if (!settings.useSystemDefault && !settings.customAppPath.isEmpty()) {
            openWithCustomApp(file, settings.customAppPath);
        } else {
            openWithSystemDefault(file);
        }
    }

    private void openWithCustomApp(File file, String appPath) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            Runtime.getRuntime().exec(new String[]{appPath, file.getAbsolutePath()});
        } else if (os.contains("mac")) {
            if (appPath.endsWith(".app")) {
                Runtime.getRuntime().exec(new String[]{"open", "-a", appPath, file.getAbsolutePath()});
            } else {
                Runtime.getRuntime().exec(new String[]{appPath, file.getAbsolutePath()});
            }
        } else {
            // Linux/Unix
            Runtime.getRuntime().exec(new String[]{appPath, file.getAbsolutePath()});
        }
    }

    private void openWithSystemDefault(File file) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            Runtime.getRuntime().exec("cmd /c start \"\" \"" + file.getAbsolutePath() + "\"");
        } else if (os.contains("mac")) {
            Runtime.getRuntime().exec(new String[]{"open", file.getAbsolutePath()});
        } else {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            } else {
                throw new UnsupportedOperationException("Desktop operations not supported");
            }
        }
    }
}