> 注：master分支是从IDEA2024.1版开发的插件源代码，只能兼容IDEA2023.2.8之后的版本。
> 注：dev-2021分支是从IDEA2021.2版开发的插件源代码，能从IDEA2020.1.4开始兼容，兼容性更好。


### 中文版使用步骤

#### 1. 安装插件

- 下载插件 ZIP 文件
- 打开 IntelliJ IDEA → 设置 → 插件 → ⚙️ → 从磁盘安装插件
- 选择下载的 ZIP 文件
- 重启 IDEA 完成安装

#### 2. 配置文件关联

1. 打开设置：文件 → 设置 → 工具 → Markdown External Open
2. 在 **Markdown 设置** 部分：
   - 勾选/取消"使用系统默认应用程序打开 Markdown 文件"
   - 需要时指定自定义 Markdown 编辑器路径
3. 在 **文件扩展名关联** 部分：
   - 点击"添加"按钮新增一行
   - 在"扩展名"列输入文件扩展名（如：json）
   - 在"应用程序路径"列：
     - 点击单元格激活编辑器
     - 点击"浏览"按钮（...）
     - 选择应用程序可执行文件（如：VS Code）
   - 重复添加其他文件类型关联
   - 使用"删除"按钮移除不需要的关联
4. 在 **默认行为** 部分：
   - 勾选/取消"为未关联的文件类型使用系统默认程序"
5. 点击"应用"保存设置

#### 3. 使用右键菜单打开文件

- 在项目视图或编辑器中：
  - 对于 Markdown 文件：
    - 右键点击 → "Open Markdown with Typora"
    - 或使用快捷键 Ctrl+Alt+T
  - 对于关联的文件类型：
    - 右键点击 → "Open .<扩展名> with Custom App"
    - 示例：右键 json 文件 → "Open .json with Custom App"
    - 或使用快捷键 Ctrl+Alt+O
- 文件将使用配置的应用程序打开

#### 4. 管理关联设置

- 随时返回设置面板：
  - 添加/删除/修改文件关联
  - 扩展名不区分大小写（JSON = json）
  - 修改后立即生效





### English Version Usage Steps

#### 1. Install the Plugin

- Download the plugin ZIP file
- Open IntelliJ IDEA → Settings → Plugins → ⚙️ → Install Plugin from Disk
- Select the downloaded ZIP file
- Restart IDEA to complete installation

#### 2. Configure File Associations

1. Open Settings: File → Settings → Tools → Markdown External Open
2. In the **Markdown Settings** section:
   - Check/Uncheck "Use system default application for Markdown"
   - Specify custom Markdown editor path if needed
3. In the **File Extension Associations** section:
   - Click "Add" to create a new row
   - Enter file extension in "Extension" column (e.g., json)
   - In "Application Path" column:
     - Click the cell to activate editor
     - Click "Browse" button (...)
     - Select application executable (e.g., VS Code)
   - Repeat to add other file type associations
   - Use "Remove" to delete unwanted associations
4. In the **Default Behavior** section:
   - Check/Uncheck "Use system default for unassigned file types"
5. Click "Apply" to save settings

#### 3. Open Files via Context Menu

- In Project View or Editor:
  - For Markdown files:
    - Right-click → "Open Markdown with Typora"
    - Or use shortcut Ctrl+Alt+T
  - For associated file types:
    - Right-click → "Open .<extension> with Custom App"
    - Example: Right-click json file → "Open .json with Custom App"
    - Or use shortcut Ctrl+Alt+O
- Files will open with the configured application

#### 4. Manage Associations

- Return to settings anytime to:
  - Add/Remove/Modify file associations
  - Extensions are case-insensitive (JSON = json)
  - Changes take effect immediately