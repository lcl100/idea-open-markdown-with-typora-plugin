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

    public AppSettingsComponent() {
        // 设置浏览按钮事件
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


        useSystemDefaultCheckBox.addActionListener(e -> customPathField.setEnabled(!useSystemDefaultCheckBox.isSelected()));
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public String getCustomPath() {
        return customPathField.getText();
    }

    public void setCustomPath(String path) {
        customPathField.setText(path);
    }

    public boolean useSystemDefault() {
        return useSystemDefaultCheckBox.isSelected();
    }

    public void setUseSystemDefault(boolean value) {
        useSystemDefaultCheckBox.setSelected(value);
        customPathField.setEnabled(!value);
    }
}