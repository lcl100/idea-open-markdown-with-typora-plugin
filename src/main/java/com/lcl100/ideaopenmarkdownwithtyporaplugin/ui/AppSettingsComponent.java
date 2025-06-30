package com.lcl100.ideaopenmarkdownwithtyporaplugin.ui;

/**
 * @author lcl100
 * @create 2025-06-30 12:09
 */

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppSettingsComponent {
    private JPanel mainPanel;
    private JCheckBox useSystemDefaultCheckBox;
    private TextFieldWithBrowseButton customPathField;
    private JTextField supportedExtensionsField;

    public AppSettingsComponent() {
        // 确保组件不为null
        customPathField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileChooserDescriptor descriptor = new FileChooserDescriptor(true, false, false, false, false, false);
                descriptor.setTitle("Select Markdown Application");

                VirtualFile file = FileChooser.chooseFile(descriptor, null, null);
                if (file != null) {
                    customPathField.setText(file.getPath());
                }
            }
        });

        useSystemDefaultCheckBox.addActionListener(e -> {
            if (customPathField != null) {
                customPathField.setEnabled(!useSystemDefaultCheckBox.isSelected());
            }
        });
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    // 添加空值安全保护
    public String getCustomPath() {
        return (customPathField != null) ? customPathField.getText() : "";
    }

    public void setCustomPath(String path) {
        if (customPathField != null) {
            customPathField.setText(path != null ? path : "");
        }
    }

    public boolean useSystemDefault() {
        return (useSystemDefaultCheckBox != null) && useSystemDefaultCheckBox.isSelected();
    }

    public void setUseSystemDefault(boolean value) {
        if (useSystemDefaultCheckBox != null) {
            useSystemDefaultCheckBox.setSelected(value);
        }
        if (customPathField != null) {
            customPathField.setEnabled(!value);
        }
    }

    public String getSupportedExtensions() {
        return (supportedExtensionsField != null) ? supportedExtensionsField.getText() : "";
    }

    public void setSupportedExtensions(String extensions) {
        if (supportedExtensionsField != null) {
            supportedExtensionsField.setText(extensions != null ? extensions : "");
        }
    }
}