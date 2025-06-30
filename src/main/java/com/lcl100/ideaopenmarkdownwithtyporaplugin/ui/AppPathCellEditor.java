package com.lcl100.ideaopenmarkdownwithtyporaplugin.ui;


import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

/**
 * @author lcl100
 * @create 2025-06-30 16:39
 */
public class AppPathCellEditor extends AbstractCellEditor implements TableCellEditor {
    private final TextFieldWithBrowseButton editor;
    private String currentValue;

    public AppPathCellEditor() {
        editor = new TextFieldWithBrowseButton();
        editor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileChooserDescriptor descriptor = new FileChooserDescriptor(true, false, false, false, false, false);
                descriptor.setTitle("Select Application");
                VirtualFile file = FileChooser.chooseFile(descriptor, null, null);
                if (file != null) {
                    editor.setText(file.getPath());
                }
            }
        });
    }

    @Override
    public Object getCellEditorValue() {
        return editor.getText();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        currentValue = (value != null) ? value.toString() : "";
        editor.setText(currentValue);
        return editor;
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        if (e instanceof MouseEvent) {
            return ((MouseEvent) e).getClickCount() >= 1;
        }
        return true;
    }
}