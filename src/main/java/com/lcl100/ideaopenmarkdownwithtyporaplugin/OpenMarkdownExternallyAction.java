package com.lcl100.ideaopenmarkdownwithtyporaplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author lcl100
 * @create 2025-06-30 10:51
 */
public class OpenMarkdownExternallyAction extends AnAction {

    @Override
    public void update(@NotNull AnActionEvent e) {
        // 仅在选中.md文件时显示菜单项
        VirtualFile file = getSelectedFile(e);
        e.getPresentation().setEnabledAndVisible(file != null && "md".equalsIgnoreCase(file.getExtension()));
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        VirtualFile file = getSelectedFile(e);
        if (file == null) return;

        try {
            openFileWithExternalApp(new File(file.getPath()));
        } catch (Exception ex) {
            Messages.showErrorDialog("Error opening file: " + ex.getMessage(), "Open Failed");
        }
    }

    private VirtualFile getSelectedFile(AnActionEvent e) {
        VirtualFile[] files = e.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY);
        return (files != null && files.length == 1) ? files[0] : null;
    }

    private void openFileWithExternalApp(File file) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            // Windows: 使用系统关联程序
            Runtime.getRuntime().exec("cmd /c start \"\" \"" + file.getAbsolutePath() + "\"");
        } else if (os.contains("mac")) {
            // macOS: 使用open命令
            Runtime.getRuntime().exec(new String[]{"open", file.getAbsolutePath()});
        } else {
            // linux以及其他系统
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            } else {
                throw new UnsupportedOperationException("Desktop operations not supported");
            }
        }
    }
}