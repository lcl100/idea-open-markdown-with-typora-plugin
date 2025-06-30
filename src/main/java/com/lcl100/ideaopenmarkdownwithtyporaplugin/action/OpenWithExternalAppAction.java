package com.lcl100.ideaopenmarkdownwithtyporaplugin.action;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
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
        if (file == null) {
            e.getPresentation().setEnabledAndVisible(false);
            return;
        }

        // 对任何文件类型都可见（但文件夹除外）
        boolean isFile = !file.isDirectory();
        e.getPresentation().setEnabledAndVisible(isFile);

        // 根据文件类型更新文本
        String extension = getFileExtension(file.getName());
        if (extension != null && !extension.isEmpty()) {
            e.getPresentation().setText("Open ." + extension + " with Custom App");
        } else {
            e.getPresentation().setText("Open with External App");
        }
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        VirtualFile file = getSelectedFile(e);
        if (file == null) return;

        try {
            openFileExternally(file);
        } catch (Exception ex) {
            LOG.error("Error opening file externally", ex);
            Messages.showErrorDialog("Error opening file: " + ex.getMessage(), "Open Failed");
        }
    }

    private VirtualFile getSelectedFile(AnActionEvent e) {
        // 多种方式获取文件（项目视图、编辑器等）
        VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
        if (file != null) return file;

        VirtualFile[] files = e.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY);
        if (files != null && files.length > 0) return files[0];

        return null;
    }

    private String getFileExtension(String filename) {
        if (filename == null) return "";
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex + 1);
    }

    private void openFileExternally(VirtualFile virtualFile) throws IOException {
        AppSettingsState settings = AppSettingsState.getInstance();
        File file = new File(virtualFile.getPath());
        String extension = getFileExtension(virtualFile.getName()).toLowerCase();

        // 检查是否有自定义关联程序
        if (!extension.isEmpty()) {
            String customApp = settings.extensionAppMap.get(extension);
            if (customApp != null && !customApp.isEmpty()) {
                openWithCustomApp(file, customApp);
                return;
            }
        }

        // 特殊处理 Markdown 文件
        if ("md".equals(extension) && !settings.useSystemDefaultForMarkdown && !settings.customAppPath.isEmpty()) {
            openWithCustomApp(file, settings.customAppPath);
            return;
        }

        // 使用系统默认程序
        if (settings.useSystemDefaultForOthers) {
            openWithSystemDefault(file);
        } else {
            // 如果没有设置默认程序且没有关联，则提示
            Messages.showInfoMessage("No application associated for ." + extension + " files", "Open Failed");
        }
    }

    private void openWithCustomApp(File file, String appPath) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder processBuilder;

        if (os.contains("win")) {
            processBuilder = new ProcessBuilder(appPath, file.getAbsolutePath());
        } else if (os.contains("mac")) {
            if (appPath.endsWith(".app")) {
                processBuilder = new ProcessBuilder("open", "-a", appPath, file.getAbsolutePath());
            } else {
                processBuilder = new ProcessBuilder(appPath, file.getAbsolutePath());
            }
        } else {
            processBuilder = new ProcessBuilder(appPath, file.getAbsolutePath());
        }

        try {
            Process process = processBuilder.start();
            // 可选：等待进程结束（通常不需要）
            // int exitCode = process.waitFor();
        } catch (IOException ex) {
            LOG.error("Failed to start application: " + appPath, ex);
            throw new IOException("Failed to start application: " + appPath, ex);
        }
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