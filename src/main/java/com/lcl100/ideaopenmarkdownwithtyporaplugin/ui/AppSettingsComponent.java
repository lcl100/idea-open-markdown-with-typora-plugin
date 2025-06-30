package com.lcl100.ideaopenmarkdownwithtyporaplugin.ui;


import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * @author lcl100
 * @create 2025-06-30 12:09
 */
public class AppSettingsComponent {
    private final JBPanel mainPanel;
    private final JBCheckBox useSystemDefaultForMarkdownCheckBox;
    private final TextFieldWithBrowseButton customPathField;
    private final ExtensionAppTablePanel extensionAppTablePanel;
    private final JBCheckBox useSystemDefaultForOthersCheckBox;

    public AppSettingsComponent() {
        // 手动创建所有组件
        this.useSystemDefaultForMarkdownCheckBox = new JBCheckBox("Use system default application for Markdown");
        this.customPathField = new TextFieldWithBrowseButton();
        this.useSystemDefaultForOthersCheckBox = new JBCheckBox("Use system default for unassigned file types");
        this.extensionAppTablePanel = new ExtensionAppTablePanel();

        // 创建主面板
        this.mainPanel = new JBPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 添加Markdown设置部分
        JPanel markdownPanel = new JPanel(new GridBagLayout());
        markdownPanel.setBorder(BorderFactory.createTitledBorder("Markdown Settings"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        markdownPanel.add(useSystemDefaultForMarkdownCheckBox, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        markdownPanel.add(new JLabel("Custom application path:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        markdownPanel.add(customPathField, gbc);

        // 添加文件关联设置部分
        JPanel associationPanel = new JPanel(new BorderLayout());
        associationPanel.setBorder(BorderFactory.createTitledBorder("File Extension Associations"));
        associationPanel.add(extensionAppTablePanel.getPanel(), BorderLayout.CENTER);

        // 添加默认设置
        JPanel defaultPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        defaultPanel.add(useSystemDefaultForOthersCheckBox);

        // 添加所有部分到主面板
        mainPanel.add(markdownPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(associationPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(defaultPanel);

        // 初始化事件监听器
        initComponents();
    }

    private void initComponents() {
        // 设置浏览按钮事件
        customPathField.addActionListener(e -> {
            FileChooserDescriptor descriptor = new FileChooserDescriptor(true, false, false, false, false, false);
            descriptor.setTitle("Select Markdown Application");
            VirtualFile file = FileChooser.chooseFile(descriptor, null, null);
            if (file != null) {
                customPathField.setText(file.getPath());
            }
        });

        // 切换启用状态
        useSystemDefaultForMarkdownCheckBox.addActionListener(e -> customPathField.setEnabled(!useSystemDefaultForMarkdownCheckBox.isSelected()));

        // 初始启用状态
        customPathField.setEnabled(!useSystemDefaultForMarkdownCheckBox.isSelected());
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

    public boolean useSystemDefaultForMarkdown() {
        return useSystemDefaultForMarkdownCheckBox.isSelected();
    }

    public void setUseSystemDefaultForMarkdown(boolean value) {
        useSystemDefaultForMarkdownCheckBox.setSelected(value);
        customPathField.setEnabled(!value);
    }

    public boolean useSystemDefaultForOthers() {
        return useSystemDefaultForOthersCheckBox.isSelected();
    }

    public void setUseSystemDefaultForOthers(boolean value) {
        useSystemDefaultForOthersCheckBox.setSelected(value);
    }

    public Map<String, String> getExtensionAppMap() {
        return extensionAppTablePanel.getData();
    }

    public void setExtensionAppMap(Map<String, String> map) {
        extensionAppTablePanel.setData(map);
    }
}