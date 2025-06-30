package com.lcl100.ideaopenmarkdownwithtyporaplugin.ui;


import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lcl100
 * @create 2025-06-30 16:29
 */
public class ExtensionAppTablePanel {
    private final JPanel panel;
    private final JBTable table;
    private final DefaultTableModel tableModel;
    private final JButton addButton;
    private final JButton removeButton;

    public ExtensionAppTablePanel() {
        // 创建表格模型：两列（扩展名，应用程序路径）
        tableModel = new DefaultTableModel(new Object[]{"Extension", "Application Path"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true; // 允许编辑
            }
        };
        table = new JBTable(tableModel);
        table.setStriped(true); // 斑马纹

        // 创建按钮
        addButton = new JButton("Add");
        removeButton = new JButton("Remove");

        // 添加按钮事件
        addButton.addActionListener(e -> addRow("", ""));
        removeButton.addActionListener(e -> removeSelectedRow());

        // 添加浏览按钮到表格的应用程序路径列
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(1).setCellEditor(new AppPathCellEditor());

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        // 构建主面板
        panel = FormBuilder.createFormBuilder().addComponent(new JBLabel("Custom file extension associations:")).addComponent(ToolbarDecorator.createDecorator(table).setAddAction(anActionButton -> addRow("", "")).setRemoveAction(anActionButton -> removeSelectedRow()).disableUpDownActions().createPanel()).addComponent(buttonPanel).addComponentFillVertically(new JPanel(), 0).getPanel();
    }

    private void addRow(String extension, String appPath) {
        tableModel.addRow(new Object[]{extension, appPath});
    }

    private void removeSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            tableModel.removeRow(selectedRow);
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setData(Map<String, String> extensionAppMap) {
        tableModel.setRowCount(0); // 清空表格
        for (Map.Entry<String, String> entry : extensionAppMap.entrySet()) {
            tableModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }
    }

    public Map<String, String> getData() {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String extension = (String) tableModel.getValueAt(i, 0);
            String appPath = (String) tableModel.getValueAt(i, 1);
            if (extension != null && !extension.trim().isEmpty()) {
                map.put(extension.trim().toLowerCase(), appPath != null ? appPath.trim() : "");
            }
        }
        return map;
    }
}