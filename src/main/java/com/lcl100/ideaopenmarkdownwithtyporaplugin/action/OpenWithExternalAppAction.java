package com.lcl100.ideaopenmarkdownwithtyporaplugin.action;



import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.lcl100.ideaopenmarkdownwithtyporaplugin.settings.AppSettingsState;

import java.awt.*;
import java.io.File;
import java.io.IOException;
/**
 * @author lcl100
 * @create 2025-06-30 14:53
 */
public class OpenWithExternalAppAction extends AnAction {
    private static final Logger LOG = Logger.getInstance(OpenWithExternalAppAction.class);

    @Override
    public void update(AnActionEvent e) {
        VirtualFile file = getSelectedFile(e);
        AppSettingsState settings = AppSettingsState.getInstance();

        boolean isSupported = isFileSupported(file, settings);
        e.getPresentation().setEnabledAndVisible(isSupported);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        VirtualFile file = getSelectedFile(e);
        if (file == null) return;

        try {
            openWithSystemDefault(new File(file.getPath()));
        } catch (Exception ex) {
            LOG.error("Error opening file externally", ex);
            Messages.showErrorDialog(
                    "Error opening file: " + ex.getMessage(),
                    "Open Failed"
            );
        }
    }

    private VirtualFile getSelectedFile(AnActionEvent e) {
        VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
        if (file != null) return file;

        VirtualFile[] files = e.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY);
        if (files != null && files.length > 0) return files[0];

        return null;
    }

    private boolean isFileSupported(VirtualFile file, AppSettingsState settings) {
        if (file == null || file.isDirectory()) return false;

        String extension = file.getExtension();
        if (extension == null) return false;

        // 检查文件扩展名是否在支持列表中
        String[] supportedExtensions = settings.supportedExtensions.split(",");
        for (String ext : supportedExtensions) {
            if (ext.trim().equalsIgnoreCase(extension)) {
                return true;
            }
        }

        return false;
    }

    private void openWithSystemDefault(File file) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", "\"\"", file.getAbsolutePath()});
        } else if (os.contains("mac")) {
            Runtime.getRuntime().exec(new String[]{"open", file.getAbsolutePath()});
        } else if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(file);
        } else {
            throw new UnsupportedOperationException("Desktop operations not supported on this platform");
        }
    }
}